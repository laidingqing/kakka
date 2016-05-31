package kakka
package product

import kakka.commons.BaseJsonFormats
import org.joda.time.DateTime
/**
  * Created by skylai on 16/5/26.
  */
case class Product(
     id: String,
     name: String,
     description: String,
     defaultPrice: BigDecimal = 0,
     count: Int,
     createdBy: String = "",
     createdAt: DateTime = new DateTime(),
     category: Category
){

}

object ProductStatus extends Enumeration {
  type ProductStatus = Value
  val Created, Deleted = Value
}

trait ProductFormatter extends BaseJsonFormats{
  implicit val categoryFormatter = jsonFormat3(Category)
  implicit val productFormatter = jsonFormat8(Product)

}

object ProductFormatter extends ProductFormatter

object ProductActions{
  case class AddProduct(product: Product)
  case class DeleteProduct(product: String)
  case class ListProduct(category: Category)
  case class GetProduct(id: String)
}