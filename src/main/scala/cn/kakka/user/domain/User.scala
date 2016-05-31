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
  username: Option[String],
  password: Option[String],
  status: Int,
  createdAt: DateTime,
  updatedAt: Option[DateTime]
)

trait UserFormatter extends BaseJsonFormats{

  implicit val userFormatter = jsonFormat6(User)
}

object UserFormatter extends UserFormatter
