~/utils/DB/spark_july/spark/bin/spark-shell --jars target/scala-2.11/new_command_2.11-0.1.0-SNAPSHOT.jar

scala> val sansc = new org.apache.spark.sql.SanSession(sc)
sansc: org.apache.spark.sql.SanSession = org.apache.spark.sql.SanSession@240a2619

scala> sansc.sql("PRINTME yes")
creating physical PrintRunnableCommand
executing PrintCommand with yes
res0: org.apache.spark.sql.DataFrame = []


