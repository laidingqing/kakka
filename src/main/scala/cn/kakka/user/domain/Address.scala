package kakka
package user

import kakka.commons.BaseJsonFormats
import spray.json.DefaultJsonProtocol

/**
  * Created by skylai on 16/5/26.
  */
case class Address (
     name: String,
     province: String,
     city: String,
     region: String,
     address: String,
     phone: String,
     mobile: String,
     alias: String
)

trait AddressFormatter extends BaseJsonFormats{
  implicit val addressFormatter = jsonFormat8(Address)
}

object AddressFormatter extends AddressFormatter
