package org.apache.spark.sql.internal

import org.apache.spark.sql.SanSession

/**
  * Created by sandeep on 4/8/17.
  */
class SanSessionState(ganeshaSession: SanSession)
  extends SessionState(ganeshaSession) {

  self =>

  override val sqlParser: ParserInterface = new SanSparkSqlParser(conf, sparkSession)

}
