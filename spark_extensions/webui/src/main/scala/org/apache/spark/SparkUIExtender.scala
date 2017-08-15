package org.apache.spark

import org.apache.spark.ui.{SparkUI, SparkUITab, UIUtils, WebUIPage}
import javax.servlet.http.HttpServletRequest

import scala.xml.Node

/**
  * Created by sandeep on 13/8/17.
  */

class CustomPage(parent: CustomTab) extends WebUIPage("") {
  override def render(request: HttpServletRequest): Seq[Node] = {
    val content =
      <div>
        {
           <div id="custom"></div>
        }
      </div>
    UIUtils.headerSparkPage("custom", content, parent, useDataTables = false)
  }
}
class CustomTab(parent: SparkUI) extends SparkUITab(parent, "custom") {
   attachPage(new CustomPage(this))
}

object SparkUIExtender {
  def extend(sparkContext: SparkContext): Unit = {
    val ui = sparkContext.ui
    if (ui.isDefined) {
      ui.get.attachTab(new CustomTab(ui.get))
    }
  }
}
