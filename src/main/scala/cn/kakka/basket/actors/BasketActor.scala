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

  override def receive = {
    case GetBasketList(userId) => {
      println("basket Actor : GetBasketList")
      sender () ! ""
    }
    case AddToBasket(user, sku, count) => {
      sender () ! ""
    }
  }
}

object BasketActor{
  def props = Props(new BasketActor)
}