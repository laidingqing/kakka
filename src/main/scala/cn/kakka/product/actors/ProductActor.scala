package kakka
package product

import akka.actor.{Actor, ActorLogging, Props}
import kakka.product.ProductActions.{AddProduct, GetProduct, ListProducts}
import spray.util.LoggingContext

import scala.util.{Failure, Success}

/**
  * Created by skylai on 16/5/31.
  */
class ProductActor()(implicit log: LoggingContext) extends Actor with ActorLogging{

  implicit val system = context.system
  implicit val executionContext = context.dispatcher

  override def receive = {
    case GetProduct(id) =>{
      sender () ! ""
    }
    case AddProduct(p) => {
      println("product Actor : AddProduct")
      sender () ! ""
    }
    case ListProducts(cat: Option[String]) => {
      println("product Actor : ListProducts")
      sender () ! ""
    }
    case _ => println("exit.")
  }

}

object ProductActor{
  def props = Props(new ProductActor())
}