# ensure spark driver port is set to that of running App or Spark Shell
# ensure worker port is set to the one worker is listening on
/usr/lib/jvm/java-8-openjdk-amd64/jre/bin/java -Xmx1024M -Dspark.driver.port=46561 -jar /home/sandeep/dev/scala/spark_extensions/custom_executor/target/scala-2.11/custom_executor.jar --driver-url spark://CoarseGrainedScheduler@0.0.0.0:46561 --executor-id 1 --hostname 0.0.0.0 --cores 1 --app-id app-20170812151799-0000 --worker-url spark://Worker@0.0.0.0:38173

