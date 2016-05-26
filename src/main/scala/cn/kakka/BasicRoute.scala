package kakka

import kakka.routing.ErrorHandler
import spray.routing._


trait BasicRoute extends ErrorHandler {
  val route: Route
  val resource: String
  val versionNumber = 1
  val version = s"v$versionNumber"
}
