package kakka
package persistence

import com.mongodb.casbah.Imports._
import com.mongodb.casbah.commons.MongoDBObject
import kakka.basket.Basket
import kakka.order.Order
import org.joda.time.DateTime
import kakka.product.Product
/**
  * Created by skylai on 16/5/30.
  */
trait MongoConversions {

  def basketToMongo(basket: Basket): MongoDBObject = {
    MongoDBObject(
      "user" -> basket.user,
      "sku" -> basket.sku,
      "count" -> basket.count,
      "created_at" -> basket.createdAt
    )
  }

  def productToMongo(product: Product): MongoDBObject = {
    val db = MongoDBObject.newBuilder
    //TODO mapping to db
    db.result()
  }

  def mongoToOrder(obj: DBObject): Order = {
    Order(id = obj.getAsOrElse[String]("id", ""),
      sessionId = obj.getAsOrElse[String]("session_id", ""),
      price = obj.getAsOrElse[BigDecimal]("price", 0),
      amount = obj.getAsOrElse[BigDecimal]("amount", 0),
      status = obj.getAsOrElse[Int]("status", 0),
      items = obj.getAsOrElse[List[String]]("items", Nil),
      createdAt = obj.getAs[DateTime]("created_at") getOrElse(new DateTime())
    )
  }

  def mongoToBasket(obj: DBObject): Basket = {
    Basket(id = obj.get("_id").toString(),
      sku = obj.getAsOrElse[String]("sku", ""),
      user = obj.getAsOrElse[String]("user", ""),
      count = obj.getAsOrElse[Int]("count", 0)
    )
  }
}