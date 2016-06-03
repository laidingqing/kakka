package cn.kakka.routing

import akka.actor.ActorContext
import akka.pattern.ask
import akka.util.Timeout
import kakka.BasicRoute
import kakka.commons.{CORSDirectives, HttpConfig}
import kakka.dao.Database
import kakka.user.UserActions.GetUserId
import kakka.user.{User, UserActor}
import spray.http.StatusCodes
import spray.routing.Directives._
import spray.routing.authentication._

import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}
import kakka.user.UserFormatter._
import spray.routing.AuthenticationFailedRejection

/**
  * Created by skylai on 16/5/26.
  */
class UserRoute(context: ActorContext, val httpConf: HttpConfig, val db: Database)(implicit val executor: ExecutionContext) extends BasicRoute with CORSDirectives{
  implicit val system = context.system
  implicit val timeout = Timeout(10.seconds)
  implicit val ec = ExecutionContext.Implicits.global
  val resource = "user"

  val userActor = system.actorOf(UserActor.props(db))

  val route = pathPrefix("v1" / "users"){
    pathEndOrSingleSlash{
      get{
        complete("user api!")
      }
    }~
    path("token"){
      post {
        complete("")
      }

    }
  }
}