package scalatra_compatible

import org.scalatra.test.scalatest.ScalatraWordSpec
import skinny.micro.SkinnyMicroServlet

class RemoveRouteServlet extends SkinnyMicroServlet {
  val foo = get("/foo") { "foo" }

  post("/remove") {
    removeRoute("GET", foo)
  }

  notFound {
    "not found"
  }
}

class RemoveRouteSpec extends ScalatraWordSpec {
  addServlet(classOf[RemoveRouteServlet], "/*")

  "a route" should {
    "not run" when {
      "it has been removed" in {
        get("/foo") {
          body should equal("foo")
        }
        post("/remove") {}
        get("/foo") {
          body should equal("not found")
        }
      }
    }
  }
}
