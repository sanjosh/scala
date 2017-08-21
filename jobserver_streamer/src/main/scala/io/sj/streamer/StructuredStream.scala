package io.sj.streamer

import java.util.concurrent.TimeUnit

import com.typesafe.config.{Config, ConfigFactory}
import org.apache.spark._
import org.apache.spark.SparkContext._
import org.apache.spark.sql.types._
import org.apache.spark.rdd.RDD
import org.apache.spark.storage.StorageLevel
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.{SQLContext, Row, DataFrame}
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.sql.streaming.ProcessingTime
import org.scalactic._
import scala.util.Try

import spark.jobserver.SparkSessionJob
import spark.jobserver._
import spark.jobserver.SparkStreamingJob
import spark.jobserver.api.{SparkJob => NewSparkJob, _}
import spark.jobserver.{SparkJob, SparkJobInvalid, SparkJobValid, SparkJobValidation}
import spark.jobserver.api.{JobEnvironment, SingleProblem, ValidationProblem}

/**
 * Create a stream, name it and write it to output
 */
object StructuredStream extends SparkSessionJob with NamedObjectSupport {

  type JobData = Config
  type JobOutput = Long
  implicit def dataFramePersister: NamedObjectPersister[NamedDataFrame] = new DataFramePersister

  private def rows(sc: SparkContext): RDD[Row] = {
  	sc.parallelize(List(Row(1, true), Row(2, false), Row(55, true)))
  }

  def runJob(spark: SparkSession, runtime: JobEnvironment, config: JobData): JobOutput = { 

	//val df: DataFrame = spark.sql("DROP TABLE if exists `default`.`test`")
    //namedObjects.update("df1", NamedDataFrame(df, true, StorageLevel.MEMORY_AND_DISK))

	import spark.implicits._

	val lines = spark.readStream.format("socket")
	  .option("host", "localhost")
	  .option("port", 9999)
	  .load.as[String]

    val words = lines.flatMap(_.split("\\W+"))

    val counter = words.groupBy("value").count

    val query = counter.writeStream
	  .format("console")
	  .outputMode("complete")
	  .trigger(ProcessingTime(10, TimeUnit.SECONDS))
	  .start("groupby.json")

	query.awaitTermination(1000 * 6000)

	0
  }

  def validate(spark: SparkSession, runtime: JobEnvironment, config: JobData): 
  JobData Or Every[ValidationProblem] = Good(config)
}
