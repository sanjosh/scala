package org.apache.spark.sql.parser

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.internal.SQLConf

/**
  * Created by sandeep on 4/8/17.
  */
class SanSparkSqlParser(conf: SQLConf, sparkSession: SparkSession) extends AbstractSqlParser {
  val astBuilder = new SanSqlAstBuilder(conf)

  override def parsePlan(sqlText: String): LogicalPlan = {

    try {
      super.parsePlan(sqlText)
    } catch {
      case ce: MalformedCarbonCommandException =>
        throw ce
      case ex =>
        try {
          astBuilder.parser.parse(sqlText)
        } catch {
          case mce: MalformedCarbonCommandException =>
            throw mce
          case e =>
            sys
              .error("\n" + "BaseSqlParser>>>> " + ex.getMessage + "\n" + "CarbonSqlParser>>>> " +
                     e.getMessage)
        }
    }
  }
}

class SanSqlAstBuilder(conf: SQLConf) extends SanSqlAstBuilder(conf) {

  val parser = new SanSparkDDLParser

}
