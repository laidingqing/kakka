package kakka
package user

import akka.actor.{Actor, ActorLogging, Props}
import kakka.dao.Database
import kakka.user.UserActions.{AuthenticationUser, GetUserId, NewToken}
import spray.util.LoggingContext

import scala.concurrent._

/**
  * Created by skylai on 16/6/1.
  */
class UserActor(val db: Database)(implicit log: LoggingContext) extends Actor with ActorLogging{

  implicit val system = context.system
  implicit val executionContext = context.dispatcher

  val userDao =  new UserDao(db)

  override def receive = {
    case AuthenticationUser(username, password) => {
      sender() ! userDao.authenticateUser(username, password)
    }
    case _ => println("hello user actor.")
  }
}

object UserActor{
  def props(db: Database) = Props(new UserActor(db:Database))
}

object UserActions{
  case class AuthenticationUser(username:String, password: String)
  case class NewToken(user: User)
  case class NewUser(user: User)
  case class GetUser(id: String)
  case class GetUserId(token: String, current: Long)
}