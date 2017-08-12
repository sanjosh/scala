sbt assembly

first attempt
~/utils/DB/spark_july/spark/bin/spark-submit target/scala-2.11/spark_repl.jar

Full command line for bin/spark-shell
/usr/lib/jvm/java-8-openjdk-amd64/jre//bin/java -cp /home/sandeep/utils/DB/spark_july/spark/conf/:/home/sandeep/utils/DB/spark_july/spark/assembly/target/scala-2.11/jars/* -Dscala.usejavacp=true -Xmx1g org.apache.spark.deploy.SparkSubmit --master spark://localhost:7077 --class org.apache.spark.repl.Main --name Spark shell spark-shell

