package kakka
package product

import akka.actor.{Actor, ActorLogging, Props}
import kakka.product.ProductActions.GetProduct
import org.joda.time.DateTime

/**
  * Created by skylai on 16/5/31.
  */
class ProductActor() extends Actor with ActorLogging{

  implicit val system = context.system
  implicit val executionContext = context.dispatcher

  override def receive = {
    case GetProduct(msg) =>{
      sender() ! Product(id = "sdfasdf",
        name = "test",
        description = "test descript",
        defaultPrice = 0,
        count = 100,
        createdBy = "",
        createdAt = new DateTime(),
        category = new Category(id = "ddd", name = "生鲜")
      )
    }
    case _ => println("exit.")
  }

}

object ProductActor{
  def props = Props(new ProductActor())
}