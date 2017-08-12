package org.apache.spark.sql.parser

import org.apache.spark.sql.catalyst.plans.logical.LogicalPlan

import scala.util.parsing.combinator.PackratParsers
import scala.util.parsing.combinator.syntactical.StandardTokenParsers

abstract class SanSparkAbstractDDLParser extends StandardTokenParsers with PackratParsers {
  def parse(input: String): LogicalPlan = {

  }
}

class SanSparkDDLParser extends SanSparkAbstractDDLParser {

}

