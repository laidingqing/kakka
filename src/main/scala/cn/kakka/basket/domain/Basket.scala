package kakka
package basket

import spray.json.DefaultJsonProtocol
/**
  * Created by skylai on 16/5/26.
  */
case class Basket (id: String, product: String)

trait BasketFormatter extends DefaultJsonProtocol{
  implicit val basketFormatter = jsonFormat2(Basket)
}

object BasketFormatter extends BasketFormatter

object BasketAction{
  case class AddBasketItem(product: String)
  case class DeleteBasketItem(product: String)
  case class ListBasket(list: List[String])
}

