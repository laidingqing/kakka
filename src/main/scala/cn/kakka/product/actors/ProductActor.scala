package kakka
package product

import akka.actor.{Actor, ActorLogging, Props}
import kakka.product.ProductActions.{AddProduct, GetProduct, ListProducts}
import kakka.product.dao.ProductPersistence
import org.joda.time.DateTime
import spray.util.LoggingContext

import scala.util.{Failure, Success}

/**
  * Created by skylai on 16/5/31.
  */
class ProductActor()(implicit log: LoggingContext) extends Actor with ActorLogging{

  implicit val system = context.system
  implicit val executionContext = context.dispatcher

  val persistence = new ProductPersistence()

  override def receive = {
    case GetProduct(id) =>{
      persistence.findById(id) match {
        case Success(p) => sender() ! p
        case Failure(e) => Some(None)
      }
    }
    case AddProduct(p) => {
      println("product Actor : AddProduct")
      persistence.insert(p) match {
        case Success(ids) => sender() ! ids.toSeq
        case Failure(e) => Failure(e)
      }
    }
    case ListProducts(cat: Option[String]) => {
      println("product Actor : ListProducts")
      persistence.findAll() match {
        case Success(products) => sender() ! products.toSeq
        case Failure(e) => Failure(e)
      }
    }
    case _ => println("exit.")
  }

}

object ProductActor{
  def props = Props(new ProductActor())
}