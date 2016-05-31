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

  /**
    * 创建商品
    * @param product
    * @return
    */
  def insert(product: Product*): Try[Seq[String]] = try{
    val mongos = product.map(p => productToMongo(p))
    db(collections).insert(mongos: _*)
    Success(mongos map { p => p.get("_id").getOrElse("?").toString() })
  }catch {
    case e: Exception => {
      e.printStackTrace()
      Failure(e)
    }
  }

  /**
    * 根据SKU查询商品
    * @param text
    * @return
    */
  def findBySKU(text: String): Try[Iterator[Product]] = try{
    val query = text match {
      case t => db(collections).find(MongoDBObject("sku" -> t))
    }

    Success(for { p <- query } yield {
      mongoToProduct(p)
    })
  }catch {
    case e: Exception => Failure(e)
  }

  /**
    * 根据编号查询商品
    * @param id
    * @return
    */
  def findById(id: String): Try[Option[Product]] = try{
    db(collections).findOne(MongoDBObject("_id" -> id)) match {
      case Some(obj) => Success(Some(mongoToProduct(obj)))
      case _         => Success(None)
    }

  }catch {
    case e: Exception => Failure(e)
  }

  /**
    * 查询所有商品
    * @return
    */
  def findAll(): Try[Iterator[Product]] = try{
    Success(for { p <- db(collections).find() } yield {
      mongoToProduct(p)
    })
  }catch {
    case e: Exception => Failure(e)
  }

  /**
    * 按分类查询商品
    * @param cat
    * @return
    */
  def categoryProducts(cat: String): Try[Iterator[Product]] = try {

    Success(for { p <- db(collections).find("categories" $in List(cat)) } yield {
      mongoToProduct(p)
    })
  } catch {
    case e: Exception => Failure(e)
  }
}
