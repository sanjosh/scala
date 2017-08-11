package org.apache.spark.repl

import java.io.File

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.internal.Logging
import org.apache.spark.repl.Main.initializeLogIfNecessary
import org.apache.spark.sql.SparkSession
import org.apache.spark.util.Utils

import scala.tools.nsc.GenericRunnerSettings

/**
  * Created by sandeep on 5/8/17.
  */
object MyMain extends Logging {

  initializeLogIfNecessary(true)
  //Signaling.cancelOnInterrupt()

  val conf = new SparkConf()
  val rootDir = conf.getOption("spark.repl.classdir").getOrElse(Utils.getLocalDir(conf))
  val outputDir = Utils.createTempDir(root = rootDir, namePrefix = "repl")

  var sparkContext: SparkContext = _
  var sparkSession: SparkSession = _
  // this is a public var because tests reset it.
  var interp: SparkILoop = _

  private var hasErrors = false

  private def scalaOptionError(msg: String): Unit = {
    hasErrors = true
    Console.err.println(msg)
  }

  def main(args: Array[String]): Unit = {
    doMain(args, new MySparkILoop)
  }

  // Visible for testing
  private[repl] def doMain(args: Array[String], _interp: SparkILoop): Unit = {
    interp = _interp
    val jars = Utils.getUserJars(conf, isShell = true).mkString(File.pathSeparator)
    val interpArguments = List(
      "-Yrepl-class-based",
      "-Yrepl-outdir", s"${outputDir.getAbsolutePath}",
      "-classpath", jars
    ) ++ args.toList

    val settings = new GenericRunnerSettings(scalaOptionError)
    settings.processArguments(interpArguments, true)

    if (!hasErrors) {
      interp.process(settings) // Repl starts and goes in loop of R.E.P.L
      Option(sparkContext).foreach(_.stop)
    }
  }
}
