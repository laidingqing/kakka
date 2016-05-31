package kakka
package user

import kakka.commons.BaseJsonFormats
import org.joda.time.DateTime
import spray.json.DefaultJsonProtocol

/**
  * Created by skylai on 16/5/26.
  */
case class Message (
  id: String,
  userId: String,
  subject: String,
  content: String,
  category: Int,
  createdAt: DateTime,
  read: Boolean
)

trait MessageFormatter extends BaseJsonFormats{
  implicit val messageFormatter = jsonFormat7(Message)
}

object MessageFormatter extends MessageFormatter

