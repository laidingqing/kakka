package kakka
package persistence

import com.mongodb.ServerAddress
import com.mongodb.casbah.Imports._
import com.mongodb.casbah.commons.MongoDBObject
import com.mongodb.casbah.{MongoClient, MongoCredential}
import kakka.commons.ConfigHolder

/**
  * Created by skylai on 16/5/30.
  */
trait Persistence extends ConfigHolder with MongoConversions{
  val collections: String
  val server = new ServerAddress(config.getString("db.host"), config.getInt("db.port"))
  val user = config.getString("db.user")
  val pwd = config.getString("db.pwd")
  val schema = config.getString("db.schema")

  val credentials = MongoCredential.createScramSha1Credential(user, schema, pwd.toCharArray())
  //不使用credential
  val mongoClient = MongoClient(server)

  lazy val db = mongoClient(schema)
  db.command(MongoDBObject("setParameter" -> 1, "textSearchEnabled" -> 1))

  //db("products").createIndex(MongoDBObject("title.ro" -> "text", "description.ro" -> "text", "keywords" -> "text"))
}
