package kakka

import akka.actor.ActorSystem
import akka.io.IO
import com.typesafe.config.ConfigFactory
import kakka.commons.ConfigHolder
import spray.can.Http

object Api extends ServiceBootstrap with ConfigHolder{
  val role = "api"
  def run(implicit system: ActorSystem) = {
    val host = config.getString("hostname")
    val httpport = config.getInt("port")
    val api = system.actorOf(RestInterface.props, "api")
    IO(Http) ! Http.Bind(listener = api, interface = host, port = httpport)
  }
}
