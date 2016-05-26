package cn.kakka.routing

import akka.actor.ActorContext
import kakka.BasicRoute
import spray.routing.Directives._
/**
  * Created by skylai on 16/5/26.
  */
class BasketRoute(context: ActorContext) extends BasicRoute {
  implicit val system = context.system
  val resource = "basket"

  val route = pathPrefix("api" / "v1" / "baskets"){
    pathEndOrSingleSlash{
      get{
        complete("basket api!")
      }
    }
  }
}
