# does not work
/usr/lib/jvm/java-8-openjdk-amd64/jre//bin/java -Dscala.usejavacp=true -Xmx1g -jar /home/sandeep/dev/scala/spark_extensions/new_repl/target/scala-2.11/spark_repl.jar org.apache.spark.deploy.SparkSubmit --master spark://localhost:7077 --class org.apache.spark.repl.MyMain --name SparkShell2 custom-shell
