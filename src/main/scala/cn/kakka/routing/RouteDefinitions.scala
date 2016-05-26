package kakka
package routing

import spray.routing._
import Directives._

trait RouteDefinitions {
  val routeDefinitions: Seq[BasicRoute]
  lazy val routes: Route = routeDefinitions.map(_.route).reduce(_ ~ _)
  implicit lazy val rejectionHandler: RejectionHandler = (routeDefinitions :+ BasicErrorHandler).map(_.rejectionHandler).reduce(_ orElse _)
}
