package org.apache.spark.sql.execution.command

import org.apache.spark.internal.Logging
import org.apache.spark.sql.{Row, SparkSession}

/**
  * Created by sandeep on 13/8/17.
  */

case class PrintRunnableCommand(parameter: String) extends RunnableCommand with Logging {
  def run(sparkSession: SparkSession): Seq[Row] = {
    logInfo("executing PrintCommand " + parameter)
    Seq.empty
  }
}
