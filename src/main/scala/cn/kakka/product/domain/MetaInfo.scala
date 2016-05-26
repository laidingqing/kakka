package kakka
package product

import org.joda.time.DateTime
import spray.json.DefaultJsonProtocol

/**
  * Created by skylai on 16/5/26.
  */
case class MetaInfo (createdBy: Option[String], createdAt: Option[DateTime])

trait MetaInfoFormatter extends DefaultJsonProtocol{
  implicit val basketFormatter = jsonFormat2(MetaInfo)
}

object MetaInfoFormatter extends MetaInfoFormatter
