package kakka
package routing

import akka.actor.ActorContext
import spray.routing._
import Directives._
import spray.http.MediaTypes._

class IndexRoute(context: ActorContext) extends BasicRoute {
  implicit val system = context.system
  val resource = "index"

  val route = {
    pathPrefix("static") {
      getFromResourceDirectory("static")
    } ~ pathEndOrSingleSlash {
      getFromResource("index.html")
    }
  }
}
