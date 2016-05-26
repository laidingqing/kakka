package kakka

import akka.actor.ActorSystem
import akka.io.IO
import com.typesafe.config.ConfigFactory
import spray.can.Http

object Api extends ServiceBootstrap {
  val role = "api"
  val config = ConfigFactory.load()
  def run(implicit system: ActorSystem) = {
    val host = ProjectInfo.ip
    val httpport = ProjectInfo.httpPort
    val api = system.actorOf(RestInterface.props, "api")
    IO(Http) ! Http.Bind(listener = api, interface = host, port = httpport)
  }
}
