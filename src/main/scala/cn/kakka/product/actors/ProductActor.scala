package kakka
package product

import akka.actor.{Actor, ActorLogging, Props}
import kakka.product.ProductActions.{AddProduct, GetProduct}
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
        case Failure(e) => Seq.empty
      }
    }
    case _ => println("exit.")
  }

}

object ProductActor{
  def props = Props(new ProductActor())
}