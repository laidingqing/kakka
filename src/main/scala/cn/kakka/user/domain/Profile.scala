package kakka
package user

import kakka.commons.BaseJsonFormats
import spray.json.DefaultJsonProtocol

/**
  * Created by skylai on 16/5/26.
  */
case class Profile (
  fullName: Option[String],
  email: Option[String],
  avatar: Option[String],
  address: List[Address]
)

trait ProfileFormatter extends BaseJsonFormats{
  implicit val profileFormatter = jsonFormat4(Profile)
}

object ProfileFormatter extends ProfileFormatter

