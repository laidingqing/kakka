package kakka
package commons

import com.typesafe.config.ConfigFactory

trait ConfigHolder {
  val config = ConfigFactory.load()
}
