package kakka.auth

import kakka.auth.RichHttpAuthenticator._
import kakka.commons.{BaseJsonFormats, ConfigHolder, HttpConfig}
import kakka.dao.Database
import kakka.user.{User, UserDao}
import reactivemongo.bson.BSONObjectID
import spray.routing._
import spray.routing.authentication._
import kakka.user.UserFormatter._

import scala.concurrent._
/**
  * Created by skylai on 16/6/2.
  */
class KCHttpAuthenticator(httpConf: HttpConfig, db: Database) (implicit executionContext: ExecutionContext) extends ContextAuthenticator[User] with BaseJsonFormats{
  val userDao = new UserDao(db)
  val userPassAuthenticator =  new KCUserPassAuthenticator(httpConf, userDao)
  val bearerTokenAuthenticator = new OAuth2BearerTokenAuthenticator[User](httpConf.authRealm, httpConf.authBearerTokenSecret, id => db.users.find(BSONObjectID(id)))
  val basicAuthenticator = new BasicHttpAuthenticator[User](httpConf.authRealm, userPassAuthenticator)
  val authenticator = bearerTokenAuthenticator.withFallback(basicAuthenticator)

  def apply(ctx: RequestContext): Future[Authentication[User]] = authenticator(ctx)
}
