package example

import org.scalatra.test.scalatest.ScalatraFlatSpec
import skinny.json4s.Json4sJSONStringOps
import skinny.micro.WebApp
import skinny.micro.contrib.Json4sJSONParamsAutoBinderSupport

class JSONParamsAutoBinderSupportSpec extends ScalatraFlatSpec {

  behavior of "JSONParamsAutoBinderFeature"

  object Controller extends WebApp with Json4sJSONParamsAutoBinderSupport {
    post("/") {
      params.getAs[String]("name") should equal(Some("Alice"))
    }
  }
  addFilter(Controller, "/*")

  it should "accepts json body as params" in {
    val body = Json4sJSONStringOps.toJSONString(Map("name" -> "Alice"))
    val headers = Map("Content-Type" -> "application/json")
    post("/", body, headers) {
      status should equal(200)
    }
  }

}
