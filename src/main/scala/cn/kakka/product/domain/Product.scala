package kakka
package product

import cn.kakka.product.domain.ProductImage
import kakka.commons.BaseJsonFormats
import org.joda.time.DateTime
/**
  * Created by skylai on 16/5/26.
  */
case class Product(
     id: Option[String] = None,
     title: Map[String, String],
     sku: String,
     description: Map[String, String],
     properties: Map[String, String],
     price: Double = 0,
     quantity: Int,
     createdBy: String = "",
     createdAt: DateTime = new DateTime(),
     categories: List[String] = List.empty,
     images: List[String] = List.empty,
     keyWords: List[String] = List.empty
){

}

object ProductStatus extends Enumeration {
  type ProductStatus = Value
  val Created, Deleted = Value
}

trait ProductFormatter extends BaseJsonFormats{
  implicit val productFormatter = jsonFormat12(Product)

}

object ProductFormatter extends ProductFormatter

object ProductActions{
  case class AddProduct(product: Product)
  case class DeleteProduct(product: String)
  case class ListProducts(category: Option[String])
  case class GetProduct(id: String)
}