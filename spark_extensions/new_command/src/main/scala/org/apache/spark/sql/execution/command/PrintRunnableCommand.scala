package org.apache.spark.sql.execution.command

import org.apache.spark.internal.Logging
import org.apache.spark.sql.{PrintCommand, Row, SparkSession}

/**
  * Created by sandeep on 13/8/17.
  */

case class PrintRunnableCommand(command: PrintCommand) extends RunnableCommand with Logging {

  def run(sparkSession: SparkSession): Seq[Row] = {
    println("executing PrintCommand with " + command.parameter)
    //val row = Row(1, true, "printcommand")
    //Seq(row)
    Seq.empty
  }

  override val output = command.output
}
