package org.apache.spark.sql

import org.apache.spark.sql.catalyst.expressions.Attribute
import org.apache.spark.sql.catalyst.plans.logical.{Command, LogicalPlan}

/**
  * Created by sandeep on 13/8/17.
  */
case class PrintCommand(parameter: String) extends Command {
  override def output: Seq[Attribute] = Seq.empty
}
