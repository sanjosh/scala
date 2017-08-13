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
        astBuilder.parser.parse(sqlText)
      case ex: Throwable =>
        sys.error("\n" + "BaseSqlParser>>>> " + ex.getMessage 
			+ "\n" + "CarbonSqlParser>>>> " + ex.getMessage)
    }
  }
}

class SanSqlAstBuilder(conf: SQLConf) extends SparkSqlAstBuilder(conf) {

  val parser = new SanSparkExtendedSqlParser

}
