nc -lk 9999&
~/utils/DB/spark_july/spark/bin/spark-submit --master spark://localhost:7077 --class io.sj.stream.Stream1 target/scala-2.11/sparkstream_2.11-0.1.0-SNAPSHOT.jar
