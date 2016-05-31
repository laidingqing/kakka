package kakka
package basket

/**
  * Created by skylai on 16/5/27.
  */

object BasketActions {
  case class GetBasketList(user: String)
  case class PutBasket(productId: String, amount: BigDecimal, quantity: Int)
  case class DeleteBasket(productId: String, amount: BigDecimal)
}
