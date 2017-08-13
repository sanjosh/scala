package org.apache.spark.sql.parser

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.catalyst.parser.{AbstractSqlParser, ParseException}
import org.apache.spark.sql.internal.SQLConf
import org.apache.spark.sql.catalyst.plans.logical._
import org.apache.spark.sql.execution.SparkSqlAstBuilder

/**
  * Created by sandeep on 4/8/17.
  */
class SanSparkSqlParser(conf: SQLConf, sparkSession: SparkSession) extends AbstractSqlParser {

  val astBuilder = new SanSqlAstBuilder(conf)

  override def parsePlan(sqlText: String): LogicalPlan = {

    try {
      super.parsePlan(sqlText)
    } catch {
      case ce: ParseException =>
        throw ce
      case ex: Throwable =>
        try {
          astBuilder.parser.parse(sqlText)
        } catch {
          case mce: ParseException =>
            throw mce
          case e: Throwable =>
            sys
              .error("\n" + "BaseSqlParser>>>> " + ex.getMessage + "\n" + "CarbonSqlParser>>>> " +
                     e.getMessage)
        }
    }
  }
}

class SanSqlAstBuilder(conf: SQLConf) extends SparkSqlAstBuilder(conf) {

  val parser = new SanSparkExtendedSqlParser

}
