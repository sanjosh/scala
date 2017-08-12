package org.apache.spark.sql.internal

import org.apache.spark.sql.SanSession
import org.apache.spark.sql.parser.SanSparkSqlParser
import org.apache.spark.sql.catalyst.parser.ParserInterface

/**
  * Created by sandeep on 4/8/17.
  */
class SanSessionState(sanSession: SanSession)
  extends SessionState(sanSession) {

  self =>

  override lazy val sqlParser: ParserInterface = new SanSparkSqlParser(conf, sanSession)

}
