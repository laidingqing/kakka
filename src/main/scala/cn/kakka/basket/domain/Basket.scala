package kakka
package basket

import com.mongodb.casbah.commons.MongoDBObject
import kakka.basket.BasketStatus.BasketStatus
import kakka.commons.BaseJsonFormats
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import spray.json.{DefaultJsonProtocol, JsString, JsValue, JsonFormat}
/**
  * Created by skylai on 16/5/26.
  */
case class Basket (id: String,
                   user: String,
                   sku: String,
                   count: Int = 1,
                   createdAt: DateTime = new DateTime(),
                   status:BasketStatus = BasketStatus.Created){

}

object BasketStatus extends Enumeration {
  type BasketStatus = Value
  val Created, Payed, Deleted = Value
  val v = Value

  implicit object BasketStatusFormat extends JsonFormat[BasketStatus] {
    override def read(json: JsValue) = json match {
      case JsString("status") => BasketStatus.v
      case _ => throw new Exception("Invalid JsValue type for ProductStatus conversion: must be JsString")
    }

    override def write(c: BasketStatus) = c match {
      case BasketStatus.v => JsString("v")
      case _ => JsString("")
    }
  }
}

trait BasketFormatter extends BaseJsonFormats{
  implicit val basketFormatter = jsonFormat6(Basket)
}

object BasketFormatter extends BasketFormatter

object BasketAction{
  case class AddToBasket(user:String, sku: String, count: Int)
  case class DeleteBasketItem(id: String)
  case class Baskets(list: List[Basket])
}

