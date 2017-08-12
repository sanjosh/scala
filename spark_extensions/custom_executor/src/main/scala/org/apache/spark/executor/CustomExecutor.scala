package org.apache.spark.executor

import java.nio.ByteBuffer
import java.net.URL

import org.apache.spark.SparkEnv
import org.apache.spark.internal.Logging

/**
  * Created by sandeep on 12/8/17.
  */
class CustomExecutor (
                      executorId: String,
                      executorHostname: String,
                      env: SparkEnv,
                      userClassPath: Seq[URL] = Nil,
                      isLocal: Boolean = false)
  extends Executor(executorId, executorHostname, env, userClassPath, isLocal) 
  with Logging {

  override def launchTask(
      context: ExecutorBackend,
      taskId: Long,
      attemptNumber: Int,
      taskName: String,
      serializedTask: ByteBuffer): Unit = {

	  logInfo("custom executor got new task")
	  super.launchTask(context, taskId, attemptNumber, taskName, serializedTask);

  }
}
