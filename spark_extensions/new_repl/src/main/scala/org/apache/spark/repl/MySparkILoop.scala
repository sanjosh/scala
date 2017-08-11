package org.apache.spark.repl

import java.io.BufferedReader

import scala.tools.nsc.interpreter.JPrintWriter

/**
  * Created by sandeep on 5/8/17.
  */
class MySparkILoop(in0: Option[BufferedReader], out: JPrintWriter) extends SparkILoop(in0, out) {

  def this() = this(None, new JPrintWriter(Console.out, true))

  override def printWelcome(): Unit = {
    echo ("Welcome to custom shell")
  }
}
