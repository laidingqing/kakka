package kakka
package product

import spray.json.DefaultJsonProtocol

/**
  * Created by skylai on 16/5/26.
  */
case class Product(
     id: String,
     name: String,
     description: String,
     defaultPrice: BigDecimal = 0,
     category: String,
     count: Int,
     meta: Option[MetaInfo]
)

trait ProductFormatter extends DefaultJsonProtocol{
  implicit val productFormatter = jsonFormat7(Product)
}

object ProductFormatter extends ProductFormatter


object ProductMessage{
  case class AddProduct(product: Product)
  case class DeleteProduct(product: String)
  case class ListProduct(list: List[Product])
}