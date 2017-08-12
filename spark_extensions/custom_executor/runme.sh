# Works
# ensure spark driver port is set to that of running App or Spark Shell
# ensure worker port is set to the one worker is listening on
worker_port=$1
driver_port=$2
/usr/lib/jvm/java-8-openjdk-amd64/jre/bin/java -Xmx1024M -Dspark.driver.port=${driver_port} -jar /home/sandeep/dev/scala/spark_extensions/custom_executor/target/scala-2.11/custom_executor.jar --driver-url spark://CoarseGrainedScheduler@0.0.0.0:${driver_port} --executor-id 1 --hostname 0.0.0.0 --cores 1 --app-id app-20170812151799-0000 --worker-url spark://Worker@0.0.0.0:${worker_port}

