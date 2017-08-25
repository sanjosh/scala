import io.sj.http_demo._
import org.scalatra._
import javax.servlet.ServletContext

class ScalatraBootstrap extends LifeCycle {
  override def init(context: ServletContext) {
    context.mount(new SanServlet, "/*")
    context.mount(new StreamingServlet, "/streaming/*")
  }
}
