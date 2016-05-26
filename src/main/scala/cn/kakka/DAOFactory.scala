package kakka

import kakka.basket.BasketDAO
import kakka.order.OrderDAO
import kakka.product.ProductDAO
import kakka.user.UserDAO

/**
  * Created by skylai on 16/5/26.
  */
trait DAOFactory {
  val userDao: UserDAO
  val orderDao: OrderDAO
  val basketDao: BasketDAO
  val productDao: ProductDAO
}
