package cn.kakka.routing

import akka.actor.ActorContext
import kakka.BasicRoute
import spray.routing.Directives._
/**
  * Created by skylai on 16/5/26.
  */
class ProductRoute(context: ActorContext) extends BasicRoute{
  implicit val system = context.system
  val resource = "product"
  val route = pathPrefix("api" / "v1" / "products"){
    pathEndOrSingleSlash{
      get{
        complete("product api!")
      }
    }
  }
}
