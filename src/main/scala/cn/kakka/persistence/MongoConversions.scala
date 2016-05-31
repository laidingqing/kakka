package kakka
package persistence

import com.mongodb.casbah.Imports._
import com.mongodb.casbah.commons.MongoDBObject
import kakka.basket.Basket
import kakka.order.Order
import org.joda.time.DateTime
import kakka.product.{Category, Product}
/**
  * Created by skylai on 16/5/30.
  */
trait MongoConversions {

  def basketToMongo(basket: Basket): MongoDBObject = {
    MongoDBObject(
      "user" -> basket.user,
      "sku" -> basket.sku,
      "quantity" -> basket.quantity,
      "created_at" -> basket.createdAt
    )
  }

  def productToMongo(obj: Product): MongoDBObject = {
    val db = MongoDBObject.newBuilder
    db += "title" -> MongoDBObject(obj.title.toList)
    db += "description" -> MongoDBObject(obj.description.toList)
    db += "properties" -> MongoDBObject(obj.properties.toList)
    db += "price" -> obj.price
    db += "quantity" -> obj.quantity
    db += "sku" -> obj.sku
    db += "categories" -> obj.categories
    db += "images" -> obj.images
    db += "keywords" -> obj.keyWords
    db.result
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
      quantity = obj.getAsOrElse[Int]("quantity", 0)
    )
  }


  def mongoToProduct(obj: DBObject): Product = {
    try {
      Product(id = obj.getAs[ObjectId]("_id").map(_.toString),
        sku = obj.getAsOrElse[String]("sku", ""),
        title = obj.getAsOrElse[Map[String, String]]("name", Map.empty),
        description = obj.getAsOrElse[Map[String, String]]("description", Map.empty),
        properties = obj.getAsOrElse[Map[String, String]]("properties", Map.empty),
        price = obj.getAsOrElse[Double]("price", 0.0),
        quantity = obj.getAs[Int]("quantity") getOrElse 0,
        categories = obj.getAsOrElse[List[String]]("categories", Nil),
        images = obj.getAsOrElse[List[String]]("images", Nil),
        keyWords = obj.getAsOrElse[List[String]]("keywords", Nil))
    } catch {
      case e: Exception =>
        e.printStackTrace()
        throw e
    }
  }
}