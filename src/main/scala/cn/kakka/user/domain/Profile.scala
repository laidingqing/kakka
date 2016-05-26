package kakka
package user

import spray.json.DefaultJsonProtocol

/**
  * Created by skylai on 16/5/26.
  */
case class Profile (
  fullName: Option[String],
  email: Option[String],
  avatar: Option[String]
)

trait ProfileFormatter extends DefaultJsonProtocol{
  implicit val profileFormatter = jsonFormat3(Profile)
}

object ProfileFormatter extends ProfileFormatter

