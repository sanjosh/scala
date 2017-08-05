// this version 2.11 must match scalaVersion
// Using %% after groupId should automatically pick up scala version
// but it didnt work
lazy val sparkDependencies = Seq(
  "org.apache.spark" % "spark-core_2.11" % "2.1.0" % "provided",
  "org.apache.spark" % "spark-sql_2.11" % "2.1.0" % "provided",
  "org.apache.spark" % "spark-repl_2.11" % "2.1.0" % "provided"
)

lazy val commonSettings = Seq(
	organization := "io.sj",
	version := "0.1.0-SNAPSHOT",
	scalaVersion := "2.11.6",
	libraryDependencies ++= sparkDependencies,
	dependencyOverrides += "org.scala-lang" % "scala-compiler" % scalaVersion.value
)

lazy val assembleSettings = assemblyMergeStrategy in assembly := {
	case PathList("org", "apache", xs @ _*) => MergeStrategy.last
	case PathList("com", "google", xs @ _*) => MergeStrategy.last
    case PathList("META-INF", xs @ _*) => MergeStrategy.discard
	case "about.html" => MergeStrategy.rename
    case "overview.html" => MergeStrategy.rename
	case "plugin.properties" => MergeStrategy.last
	case "log4j.properties" => MergeStrategy.last
	case x => 
	  val oldStrategy  = (assemblyMergeStrategy in assembly).value
	  oldStrategy(x)
}

lazy val root = (project in file("."))
	.settings(commonSettings)
