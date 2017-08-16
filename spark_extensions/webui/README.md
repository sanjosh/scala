
# compile

> sbt package

# RUN

> $SPARK_HOME/bin/spark-shell --jars target/scala-2.11/custom_webui_2.11-0.1.0-SNAPSHOT.jar --master spark://localhost:7077

```
scala> import org.apache.spark.SparkUIExtender
import org.apache.spark.SparkUIExtender

scala> val myui = SparkUIExtender.extend(sc)
myui: Unit = ()
```

You will now see new "Custom" tab on http://127.0.0.1:4040/jobs 


