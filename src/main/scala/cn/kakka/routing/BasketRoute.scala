package cn.kakka.routing

import akka.actor._
import kakka.{ApiMessages, BasicRoute}
import akka.pattern.ask
import akka.util.Timeout
import kakka.basket.BasketAction.AddToBasket

import scala.concurrent.duration._
import spray.routing._
import kakka.commons.{CORSDirectives, HttpConfig}
import spray.http.{MediaTypes, StatusCodes}
import kakka.basket.{Basket, BasketActor}
import kakka.basket.BasketActions.GetBasketList

import scala.concurrent.ExecutionContext
import scala.util.Success
import scala.util.Failure
import kakka.basket.BasketFormatter._
import kakka.dao.Database
import spray.util.LoggingContext
/**
  * Created by skylai on 16/5/26.
  */
class BasketRoute(context: ActorContext, val httpConf: HttpConfig, val db: Database)(implicit val executor: ExecutionContext) extends BasicRoute with CORSDirectives{
  implicit val system = context.system
  implicit val timeout = Timeout(10.seconds)
  implicit val ec = ExecutionContext.Implicits.global
  val resource = "basket"
  val basketActor = context.actorOf(BasketActor.props)

  val route = pathPrefix("v1" / "baskets"){
    pathEndOrSingleSlash{
      get{
        corsFilter(List("*")) {
            val future = (basketActor ? GetBasketList("1")).mapTo[Seq[Basket]]
            onComplete(future) {
              case Success(value) => complete(StatusCodes.OK, value)
              case Failure(ex)    => complete(StatusCodes.InternalServerError, ex.getMessage)
            }
        }
      }~
      post{
        corsFilter(List("*")) {
          val future = (basketActor ? AddToBasket("1", "asdfasdf", 1)).mapTo[Seq[String]]
          onComplete(future) {
            case Success(value) => complete(StatusCodes.OK, value)
            case Failure(ex)    => complete(StatusCodes.InternalServerError, ex.getMessage)
          }
        }
      }
    }~
    path(Segment){ basketId =>
      get{
        complete("basket get object api!")
      }~
      delete{
        complete("basket delete object api!")
      }
    }
  }
}
