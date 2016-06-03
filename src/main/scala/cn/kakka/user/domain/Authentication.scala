package kakka
package user

import kakka.commons.BaseJsonFormats
import reactivemongo.bson.BSONDateTime
import spray.json.DefaultJsonProtocol

/**
  * Created by skylai on 16/5/26.
  */
case class Authentication (
  id: String,
  userId: String,
  uid: String,
  provider: String,
  createdAt: BSONDateTime
)

trait AuthenticationFormatter extends BaseJsonFormats{
  implicit val authenticationFormatter = jsonFormat5(Authentication)
}

object AuthenticationFormatter extends AuthenticationFormatter

