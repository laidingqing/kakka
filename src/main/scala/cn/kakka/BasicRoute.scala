package kakka

import cn.kakka.routing.directives.AuthenticationDirectives
import kakka.auth.KCHttpAuthenticator
import kakka.commons.HttpConfig
import kakka.dao.Database
import kakka.routing.ErrorHandler
import kakka.user.User
import spray.routing._
import kakka.user.UserFormatter._
import kakka.commons.BaseJsonFormats._

import scala.concurrent.ExecutionContext


trait BasicRoute extends Directives with AuthenticationDirectives with ErrorHandler {
  val route: Route
  val resource: String
  val versionNumber = 1
  val version = s"v$versionNumber"

  implicit val httpConf: HttpConfig
  implicit val db: Database
  implicit val executor: ExecutionContext

  val authenticator = new KCHttpAuthenticator(httpConf, db)
  def authenticate(): Directive1[User] = authenticate(authenticator)
  def authenticateOption(): Directive1[Option[User]] = authenticateOption(authenticator)
}

object ApiMessages {
  case class Message(message: String)

  val UnknownException = "Unknown exception"
  val UnsupportedService = "Sorry, provided service is not supported."
}
