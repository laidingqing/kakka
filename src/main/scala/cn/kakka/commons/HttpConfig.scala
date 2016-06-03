package kakka.commons

import java.io.File

import com.typesafe.config.ConfigFactory
import kakka.utils.RichConfig._
import scala.concurrent.duration.FiniteDuration

/**
  * Created by skylai on 16/6/3.
  */
case class HttpConfig(
                       interface: String,
                       port: Int,
                       authRealm: String,
                       authBearerTokenSecret: Array[Byte],
                       authBearerTokenLifetime: FiniteDuration,
                       authPasswordValidationDelay: FiniteDuration,
                       webDir: Option[File]
)

object HttpConfig {
  def load(): HttpConfig = {
    val raw = ConfigFactory.load().getConfig("kakka.http")

    HttpConfig(
      interface = raw.getString("interface"),
      port = raw.getInt("port"),
      authRealm = raw.getString("auth.realm"),
      authBearerTokenSecret = raw.getByteArray("auth.bearer-token.secret"),
      authBearerTokenLifetime = raw.getFiniteDuration("auth.bearer-token.lifetime"),
      authPasswordValidationDelay = raw.getFiniteDuration("auth.password-validation.delay"),
      webDir = raw.getOptionalString("web-dir").map(new File(_))
    )
  }
}