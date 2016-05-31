package kakka
package commons

import org.joda.time.format.ISODateTimeFormat
import org.joda.time.{DateTime, DateTimeZone}
import spray.httpx.SprayJsonSupport
import spray.json._
/**
  * Created by skylai on 16/5/27.
  */
trait BaseJsonFormats extends DefaultJsonProtocol with SprayJsonSupport{

  implicit def dateFormat = new JsonFormat[DateTime] {
    def write(dateTime: DateTime): JsValue = {
      JsString(dateTime.toDateTime(DateTimeZone.UTC).toString(ISODateTimeFormat.dateTimeNoMillis()))
    }

    def read(json: JsValue): DateTime = {
      json match {
        case JsString(s) if s.contains(".") => ISODateTimeFormat.dateTime().parseDateTime(s).toDateTime(DateTimeZone.UTC)
        case JsString(s) => ISODateTimeFormat.dateTimeNoMillis().parseDateTime(s).toDateTime(DateTimeZone.UTC)
        case _ => throw new DeserializationException("Expected string for datetime")
      }
    }
  }

}
