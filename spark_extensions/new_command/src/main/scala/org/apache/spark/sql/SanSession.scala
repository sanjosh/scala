package org.apache.spark.sql

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.internal.Logging
import org.apache.spark.scheduler.{SparkListener, SparkListenerApplicationEnd}
import org.apache.spark.sql.SparkSession.Builder
import org.apache.spark.sql.internal.SanSessionState

/**
  * Created by sandeep on 4/8/17.
  */
class SanSession(@transient private val sc: SparkContext)
  extends SparkSession(sc) with Logging {

  self =>

  private[spark] lazy override val sessionState: SanSessionState =
    new SanSessionState(this)

  override def newSession(): SparkSession = {
    new SanSession(sparkContext);
  }

}

object SanSession {

  implicit class SanSessionBuilder(builder: Builder) {
    def getOrCreateSanSession(): SparkSession = synchronized {

      // get the current thread's active session
      var session: SparkSession = SparkSession.getActiveSession match {
        case Some(sparkSession: SanSession) =>
          if ((sparkSession ne null) && !sparkSession.sparkContext.isStopped) {
            sparkSession
          } else {
            null
          }
        case _=> null
      }
      if (session ne null) {
        return session
      }

      SparkSession.synchronized {
        session = SparkSession.getDefaultSession match {
          case Some(sparkSession: SanSession) =>
            if ((sparkSession ne null) && !sparkSession.sparkContext.isStopped) {
              sparkSession
            } else {
              null
            }
          case _ => null
        }
        if (session ne null) {
          return session
        }

        val sparkConf = new SparkConf()
        val sparkContext = SparkContext.getOrCreate(sparkConf)

        session = new SanSession(sparkContext)
        SparkSession.setDefaultSession(session)

        sparkContext.addSparkListener(new SparkListener {
          override def onApplicationEnd(applicationEnd: SparkListenerApplicationEnd): Unit = {
            SparkSession.setDefaultSession(null)
            SparkSession.sqlListener.set(null)
          }
        })

      }
      return session
    }
  }

}
