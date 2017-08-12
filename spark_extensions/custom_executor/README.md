
# how to compile
sbt assembly

# How to run
runme.sh

# command line from unmodified executor

/usr/lib/jvm/java-8-openjdk-amd64/jre/bin/java -cp /home/sandeep/utils/DB/spark_july/spark/conf/:/home/sandeep/utils/DB/spark_july/spark/assembly/target/scala-2.11/jars/* -Xmx1024M -Dspark.driver.port=46561 org.apache.spark.executor.CoarseGrainedExecutorBackend --driver-url spark://CoarseGrainedScheduler@0.0.0.0:46561 --executor-id 0 --hostname 0.0.0.0 --cores 4 --app-id app-20170812151722-0000 --worker-url spark://Worker@0.0.0.0:38173

# to find current active executors 

def currentActiveExecutors(sc: SparkContext): Seq[String] = {
  val allExecutors = sc.getExecutorMemoryStatus.map(_._1)
  val driverHost: String = sc.getConf.get("spark.driver.host")
  allExecutors.filter(! _.split(":")(0).equals(driverHost)).toList
}

