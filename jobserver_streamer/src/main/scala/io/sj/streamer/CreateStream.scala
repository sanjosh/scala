package io.sj.streamer

import com.typesafe.config.{Config, ConfigFactory}
import org.apache.spark._
import org.apache.spark.SparkContext._
import org.apache.spark.sql.types._
import org.apache.spark.rdd.RDD
import org.apache.spark.storage.StorageLevel
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.{SQLContext, Row, DataFrame}
import org.apache.spark.streaming.StreamingContext
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
object CreateStream extends SparkStreamingJob with NamedObjectSupport {

  implicit def dataFramePersister: NamedObjectPersister[NamedDataFrame] = new DataFramePersister

  private def rows(sc: SparkContext): RDD[Row] = {
  	sc.parallelize(List(Row(1, true), Row(2, false), Row(55, true)))
  }

  def runJob(ssc: StreamingContext, config: Config): Any = {

    val sqlContext = new SQLContext(ssc.sparkContext)
    val schema = StructType(
        StructField("i", IntegerType, true) ::
          StructField("b", BooleanType, false) :: Nil)

	val df = sqlContext.createDataFrame(rows(ssc.sparkContext), schema)
    namedObjects.update("df1", NamedDataFrame(df, true, StorageLevel.MEMORY_AND_DISK))

	ssc.start()
	ssc.awaitTermination()
  }

  def validate(ssc: StreamingContext, config: Config): SparkJobValidation = {
    SparkJobValid
  } 
}
