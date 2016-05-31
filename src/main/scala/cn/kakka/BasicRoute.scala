package kakka

import kakka.routing.ErrorHandler
import spray.routing._


trait BasicRoute extends ErrorHandler {
  val route: Route
  val resource: String
  val versionNumber = 1
  val version = s"v$versionNumber"
}

object ApiMessages {
  case class Message(message: String)

  val UnknownException = "Unknown exception"
  val UnsupportedService = "Sorry, provided service is not supported."
}
