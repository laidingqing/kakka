package cn.kakka.routing

import akka.actor.ActorContext
import kakka.BasicRoute
import kakka.commons.HttpConfig
import kakka.dao.Database
import spray.routing.Directives._

import scala.concurrent.ExecutionContext
/**
  * Created by skylai on 16/5/26.
  */
class OrderRoute(context: ActorContext, val httpConf: HttpConfig, val db: Database)(implicit val executor: ExecutionContext) extends BasicRoute{
  implicit val system = context.system
  val resource = "order"

  val route = pathPrefix("v1" / "orders"){
    pathEndOrSingleSlash{
      get{
        complete("order api!")
      }
    }
  }
}
