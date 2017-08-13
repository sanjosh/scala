package org.apache.spark.sql.execution.command

import org.apache.spark.sql.{PrintCommand, SparkSession}
import org.apache.spark.sql.catalyst.plans.logical.LogicalPlan
import org.apache.spark.sql.execution.{SparkPlan, SparkStrategy}

/**
  * Created by sandeep on 13/8/17.
  */
class DDLStrategy(sparkSession: SparkSession) extends SparkStrategy {
  def apply(plan: LogicalPlan): Seq[SparkPlan] = {
    plan match{
      case printcmd@PrintCommand(parameter) => {
        println("creating physical PrintRunnableCommand")
        ExecutedCommandExec(PrintRunnableCommand(printcmd)) :: Nil
      }
      case _ => {
        logInfo("failed")
        Nil
      }
    }
  }
}
