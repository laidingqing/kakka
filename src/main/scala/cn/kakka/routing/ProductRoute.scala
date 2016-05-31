package cn.kakka.routing

import akka.actor._
import akka.pattern.ask
import akka.actor.ActorContext
import akka.util.Timeout

import scala.concurrent.duration._
import kakka.BasicRoute
import kakka.product.{Product, ProductActor}
import kakka.product.ProductActions._
import kakka.product.ProductFormatter._
import kakka.commons.CORSDirectives
import spray.http.StatusCodes

import scala.concurrent.duration._
import spray.util.LoggingContext

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}
/**
  * Created by skylai on 16/5/26.
  */
class ProductRoute(val context: ActorContext)(implicit log: LoggingContext) extends BasicRoute with CORSDirectives{
  implicit val system = context.system
  implicit val timeout = Timeout(10.seconds)
  implicit val ec = ExecutionContext.Implicits.global
  val resource = "product"
  val productActor = system.actorOf(ProductActor.props)

  val route = pathPrefix("api" / "v1" / "products"){
    pathEndOrSingleSlash{
      get{
        corsFilter(List("*")) {
          val future = (productActor ? GetProduct("id:00000")).mapTo[Product]
          onComplete(future) {
            case Success(value) => complete(StatusCodes.OK, value)
            case Failure(ex)    => complete(StatusCodes.InternalServerError, ex.getMessage)
          }
        }
      }
    }
  }
}
