package kakka
package product
package dao

import kakka.persistence.Persistence
import com.mongodb.casbah.Imports._
import scala.util.{Failure, Success, Try}

/**
  * Created by skylai on 16/5/30.
  */
class ProductPersistence() extends Persistence{

  val collections = "products"

  def insert(product: Product*): Try[Seq[String]] = try{
    val mongos = product.map(p => productToMongo(p))
    db(collections).insert(mongos: _*)
    Success(mongos map { p => p.get("_id").getOrElse("?").toString() })
  }catch {
    case e: Exception => Failure(e)
  }

}
