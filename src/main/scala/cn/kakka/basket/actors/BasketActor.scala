package kakka
package basket

import akka.actor.{Actor, ActorLogging, Props}
import kakka.basket.BasketAction.AddToBasket
import kakka.basket.BasketActions.GetBasketList

import scala.util.{Failure, Success}

/**
  * Created by skylai on 16/5/27.
  */
class BasketActor extends Actor with ActorLogging{

  implicit val system = context.system
  implicit val executionContext = context.dispatcher

  val persistence = new BasketPersistence()

  override def receive = {
    case GetBasketList(userId) => {
      println("basket Actor : GetBasketList")

      persistence.findBySKU("asdfasdf") match {
        case Success(baskets) => sender () ! baskets.toSeq
        case Failure(e) => Seq.empty
      }
    }
    case AddToBasket(user, sku, count) => {
      persistence.insert(Basket(id="", user = user, sku = sku, count = count)) match {
        case Success(ids) => sender () ! ids
        case Failure(e) => println("error:", e)
      }
    }
  }
}

object BasketActor{
  def props = Props(new BasketActor)
}