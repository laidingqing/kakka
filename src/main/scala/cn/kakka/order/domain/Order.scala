package kakka
package order

import kakka.commons.BaseJsonFormats
import org.joda.time.DateTime
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
  items: List[String],
  createdAt: DateTime
)

trait OrderFormatter extends BaseJsonFormats{
  implicit val orderFormatter = jsonFormat7(Order)
}

object OrderFormatter extends OrderFormatter
