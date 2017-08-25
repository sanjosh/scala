package io.sj.http_demo

import org.scalatra._

class StreamingServlet extends Http_demoStack {

  post("/") {
	println("got new post")
	println(request.body)
  }

}
