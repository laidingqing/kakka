package kakka
package user

import kakka.commons.BaseJsonFormats
import reactivemongo.bson.{BSONDocument, BSONDocumentReader, BSONDocumentWriter}
import spray.json.DefaultJsonProtocol

/**
  * Created by skylai on 16/5/26.
  */
case class Address (id: Option[String] = None,
     name: String,
     province: String,
     city: String,
     region: String,
     street: String,
     phone: String,
     mobile: String,
     alias: String
)

trait AddressFormatter extends BaseJsonFormats{
  implicit val addressFormatter = jsonFormat9(Address)
}