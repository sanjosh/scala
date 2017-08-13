package org.apache.spark

import org.apache.spark.ui.SparkUI

/**
  * Created by sandeep on 13/8/17.
  */
class SparkUIExtender {

}

object SparkUIExtender {
  def extend(sparkContext: SparkContext): Unit = {
    val ui = sparkContext.ui
    if (ui.isDefined) {
      ui.get.setAppId("mine")
    }
  }
}