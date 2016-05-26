
package kakka

import akka.actor.ActorSystem
import com.typesafe.config.Config

trait ServiceBootstrap {
  val role: String
  val config: Config
  def run(implicit system: ActorSystem): Unit
}
