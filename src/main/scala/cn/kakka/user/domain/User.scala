package kakka
package user

import kakka.commons.BaseJsonFormats
import org.joda.time.DateTime
import spray.json.DefaultJsonProtocol

/**
  * Created by skylai on 16/5/26.
  */
case class User (
  id: String,
  username: String,
  password: String,
  status: Int,
  createdAt: DateTime = new DateTime(),
  updatedAt: DateTime = new DateTime(),
  profile: Profile
)

trait UserFormatter extends BaseJsonFormats{

  implicit val userFormatter = jsonFormat7(User)
}

object UserFormatter extends UserFormatter
