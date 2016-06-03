package kakka
package user

import kakka.commons.UserNotFoundException
import kakka.commons.ExceptionMsg._
import kakka.dao.{DAO, Database}

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}

/**
  * Created by skylai on 16/5/26.
  */
class UserDao(db: Database)(implicit ec: ExecutionContext) extends DAO{

  val collections = "users"

  def createUser(user: User): Future[User] = {
    for{
      u <- db.users.insert(user)
    }yield u
  }

  def authenticateUser(username: String, password: String):Future[Option[User]] = {
    db.users.findByUserName(username).flatMap{
      case Some(user) => {
        validateUserPassword(user, password).map {
          case true => Some(user)
          case false => Option.empty[User]
        }
      }
      case None => Future.successful(Option.empty[User])
    }
  }

  def validateUserPassword(user: User, password: String): Future[Boolean] = {
    Future.successful(true)
  }
}
