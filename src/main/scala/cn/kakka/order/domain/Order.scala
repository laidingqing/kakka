package kakka
package order

import spray.json.DefaultJsonProtocol

/**
  * Created by skylai on 16/5/26.
  */
case class Order (
  id: String,
  sessionId: String,
  price: BigDecimal,
  amount: BigDecimal,
  status: Int,
  items: List[String]
)

trait OrderFormatter extends DefaultJsonProtocol{
  implicit val orderFormatter = jsonFormat6(Order)
}

object OrderFormatter extends OrderFormatter
