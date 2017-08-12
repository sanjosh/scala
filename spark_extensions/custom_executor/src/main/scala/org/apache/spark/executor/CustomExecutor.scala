package org.apache.spark.executor

import java.net.URL

import org.apache.spark.SparkEnv

/**
  * Created by sandeep on 12/8/17.
  */
class CustomExecutor (
                      executorId: String,
                      executorHostname: String,
                      env: SparkEnv,
                      userClassPath: Seq[URL] = Nil,
                      isLocal: Boolean = false)
  extends Executor(executorId, executorHostname, env, userClassPath, isLocal) {

  // override launchTask
  //       execBackend.statusUpdate(taskId, TaskState.RUNNING, EMPTY_BYTE_BUFFER)
  // call execBackend.statusUpdate(taskId, TaskState.FINISHED, serializedResult)
  }
