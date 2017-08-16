
# How to compile
> sbt assembly

# How to run
Spark driver port has to set to that of the running App or Spark Shell
Worker port has to be set to the one that the worker is listening on
> ./runme.sh $worker_port $driver_port

This will start a new executor.  

# How to find if newly added executor has been registered

In spark shell, define this function and call it
```
def currentActiveExecutors(sc: SparkContext): Seq[String] = {
  val allExecutors = sc.getExecutorMemoryStatus.map(_._1)
  val driverHost: String = sc.getConf.get("spark.driver.host")
  allExecutors.filter(! _.split(":")(0).equals(driverHost)).toList
}
```

Or check executors under Application Detail UI 
```
http://0.0.0.0:4040/executors/
```
or event timeline under
```
http://0.0.0.0:4040/jobs/
```
