package io.sj.stream

import java.util.concurrent.TimeUnit

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.streaming
import org.apache.spark.sql.streaming.ProcessingTime


/**
  * Created by sandeep on 20/8/17.
  */
object Stream1 {

  def addNewListener(): Unit = {
  	
  }

  def main(args: Array[String]): Unit = {

    val spark = SparkSession
		.builder()
		.appName("Spark SQL stream")
		//.config("spark.some.config.option", "some-value")
		.getOrCreate()

	import spark.implicits._
  	
    val lines = spark.readStream.format("socket")
	  .option("host", "localhost")
	  .option("port", 9999)
	  .load.as[String]

    val words = lines.flatMap(_.split("\\W+"))

    val counter = words.groupBy("value").count

    val query = counter.writeStream
	  .format("parquet")
	  .outputMode("append")
	  .trigger(ProcessingTime(10, TimeUnit.SECONDS))
	  .option("checkpointLocation", "/home/sandeep")
	  .start("groupby.json")

    val counter2 = words.groupBy("value").count

    val query2 = counter.writeStream
	  .format("parquet")
	  .outputMode("append")
	  .trigger(ProcessingTime(10, TimeUnit.SECONDS))
	  .option("checkpointLocation", "/home/sandeep")
	  .start("groupby2.json")

	query.awaitTermination(1000 * 60)
	query2.awaitTermination(1000 * 60)
  }
}
