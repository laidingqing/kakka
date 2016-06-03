package kakka
package commons

import kakka.auth.OAuth2AccessTokenResponse
import reactivemongo.bson.{BSONDateTime, BSONObjectID}
import spray.httpx.SprayJsonSupport
import spray.json._
import java.util.Date

/**
  * Created by skylai on 16/5/27.
  */
trait BaseJsonFormats extends DefaultJsonProtocol with SprayJsonSupport{

  implicit object BSONObjectIDFormat extends JsonFormat[BSONObjectID] {
    def write(id: BSONObjectID) = JsString(id.stringify)
    def read(value: JsValue) =
      value match {
        case JsString(str) => BSONObjectID(str)
        case _ => deserializationError("BSON ID expected: " + value)
      }
  }

  implicit object BSONDateTimeFormat extends JsonFormat[BSONDateTime] {
    def write(dateTime: BSONDateTime) = JsNumber(dateTime.value)
    def read(value: JsValue) =
      value match {
        case JsNumber(dateTimeTicks) => BSONDateTime(dateTimeTicks.toLong)
        case _ => deserializationError(s"Date time ticks expected. Got '$value'")
      }
  }


  implicit object OAuth2AccessTokenResponseFormat extends RootJsonFormat[OAuth2AccessTokenResponse] {
    def write(res: OAuth2AccessTokenResponse) = JsObject(
      "token_type" -> JsString(res.tokenType),
      "access_token" -> JsString(res.accessToken),
      "expires_in" -> JsNumber(res.expiresIn)
    )
    def read(value: JsValue) =
      value.asJsObject.getFields("token_type", "access_token", "expires_in") match {
        case Seq(JsString(tokenType), JsString(accessToken), JsNumber(expiresIn)) =>
          OAuth2AccessTokenResponse(tokenType, accessToken, expiresIn.toLong)
        case _ => throw new DeserializationException("OAuth2 token response expected")
      }
  }

  implicit object DateFormat extends JsonFormat[Date] {
    def write(date: Date) = JsNumber(date.getTime)
    def read(value: JsValue) =
      value match {
        case JsNumber(dateTicks) => new Date(dateTicks.toLong)
        case _ => deserializationError(s"Date time ticks expected. Got '$value'")
      }
  }
}

object BaseJsonFormats extends BaseJsonFormats
