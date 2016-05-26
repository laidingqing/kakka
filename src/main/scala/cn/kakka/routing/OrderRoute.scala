package cn.kakka.routing

import akka.actor.ActorContext
import kakka.BasicRoute
import spray.routing.Directives._
/**
  * Created by skylai on 16/5/26.
  */
class OrderRoute(context: ActorContext) extends BasicRoute{
  implicit val system = context.system
  val resource = "order"

  val route = pathPrefix("api" / "v1" / "orders"){
    pathEndOrSingleSlash{
      get{
        complete("order api!")
      }
    }
  }
}
