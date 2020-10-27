package demodaria

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class RecordedSimulation extends Simulation {

	val httpProtocol = http
		.baseURL("http://detectportal.firefox.com")
		.inferHtmlResources()

	val headers_0 = Map("Pragma" -> "no-cache")

    val uri1 = "ocsp.digicert.com"
    val uri2 = "http://detectportal.firefox.com/success.txt"

	val scn = scenario("RecordedSimulation")
		.exec(http("request_0")
			.get("/success.txt")
			.headers(headers_0))
		.pause(1)
		.exec(http("request_1")
			.get("/success.txt?ipv4")
			.headers(headers_0)
			.resources(http("request_2")
			.get(uri2 + "?ipv6")
			.headers(headers_0)))
		.pause(3)
		.exec(http("request_3")
			.post("http://" + uri1 + "/")
			.body(RawFileBody("RecordedSimulation_0003_request.txt")))
		.pause(39)
		.exec(http("request_4")
			.get("/success.txt")
			.headers(headers_0)
			.resources(http("request_5")
			.get(uri2 + "?ipv6")
			.headers(headers_0),
            http("request_6")
			.get(uri2 + "?ipv4")
			.headers(headers_0)))

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}