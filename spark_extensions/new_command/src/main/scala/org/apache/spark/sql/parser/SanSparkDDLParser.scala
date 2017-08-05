package org.apache.spark.sql.parser

import scala.util.parsing.combinator.PackratParsers
import scala.util.parsing.combinator.syntactical.StandardTokenParsers

abstract class SanSparkAbstractDDLParser extends StandardTokenParsers with PackratParsers {

}

class SanSparkDDLParser extends SanSparkAbstractDDLParser {

}

