package kakka
package user

import kakka.commons.BaseJsonFormats
import org.joda.time.DateTime
import spray.json.DefaultJsonProtocol

/**
  * Created by skylai on 16/5/26.
  */
case class Authentication (
  id: String,
  userId: String,
  uid: String,
  provider: String,
  createdAt: Option[DateTime]
)

trait AuthenticationFormatter extends BaseJsonFormats{
  implicit val authenticationFormatter = jsonFormat5(Authentication)
}

object AuthenticationFormatter extends AuthenticationFormatter

