package kakka
package routing

import akka.actor.ActorContext
import spray.routing._
import Directives._
import kakka.commons.HttpConfig
import kakka.dao.Database
import spray.http.MediaTypes._
import spray.httpx.encoding.Gzip

import scala.concurrent.ExecutionContext

class IndexRoute(context: ActorContext, val httpConf: HttpConfig, val db: Database)(implicit val executor: ExecutionContext) extends BasicRoute {
  implicit val system = context.system
  val resource = "index"

  val route = {
    pathPrefix("static") {
      getFromResourceDirectory("static")
    } ~ pathEndOrSingleSlash {
      encodeResponse(Gzip) {
        getFromResource("index.html")
      }
    }
  }
}
