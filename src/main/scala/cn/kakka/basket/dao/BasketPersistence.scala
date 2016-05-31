package kakka
package basket

import kakka.persistence.{MongoConversions, Persistence}
import com.mongodb.casbah.Imports._
import scala.util.{Failure, Success, Try}

/**
  * Created by skylai on 16/5/30.
  */
class BasketPersistence extends Persistence with MongoConversions{

  val collections: String = "baskets"

  def insert(basket: Basket): Try[String] = try {
    val mongo = basketToMongo(basket)
    db(collections).insert(mongo)
    Success(mongo.get("_id").getOrElse("?").toString())
  }catch {
    case e: Exception => Failure(e)
  }

  def findBySKU(text: String): Try[Iterator[Basket]] = try{
    val query = text match {
      case t => db(collections).find(MongoDBObject("sku" -> t))
    }

    Success(for { p <- query } yield {
      mongoToBasket(p)
    })
  }catch {
    case e: Exception => Failure(e)
  }
}