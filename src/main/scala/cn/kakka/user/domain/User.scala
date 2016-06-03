package kakka
package user

import kakka.commons.BaseJsonFormats
import kakka.dao.{BaseModel, Database, Table}
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.bson.{BSONDateTime, BSONDocument, BSONDocumentReader, BSONDocumentWriter, BSONObjectID}
import spray.json.DefaultJsonProtocol
import kakka.user.ProfileBSONFormat._
import kakka.user.UserBSONFormat.TokenBSONReader
import reactivemongo.api.indexes.{Index, IndexType}

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by skylai on 16/5/26.
  */
case class User (
  id: BSONObjectID = BSONObjectID("00" * 12),
  userName: String,
  password: String,
  token: Token,
  status: Int,
  createdAt: BSONDateTime = BSONDateTime(0),
  updatedAt: BSONDateTime = BSONDateTime(0),
  profileId: String
)extends BaseModel{
  require(userName.length > 3, "User names must be at least 3 characters long")
}

case class Token(token:String, expires: Long)

trait UserFormatter extends ProfileFormatter with BaseJsonFormats{
  implicit val tokenFormatter = jsonFormat2(Token)
  implicit val userFormatter = jsonFormat8(User)
}

object UserFormatter extends UserFormatter

class UserTable(database: Database, collection: BSONCollection)(implicit executor: ExecutionContext) extends Table[User](database, collection) {
  implicit val tokenReader = UserBSONFormat.TokenBSONReader
  implicit val tokenWriter = UserBSONFormat.TokenBSONWriter

  implicit val reader = UserBSONFormat.UserBSONReader
  implicit val writer = UserBSONFormat.UserBSONWriter

  def findByUserName(userName: String): Future[Option[User]] = queryOne(BSONDocument("user_name" -> userName))

  collection.indexesManager.ensure(Index(List("user_name" -> IndexType.Ascending), unique = true))


  override def preInsert(user: User): Future[User] = {
    val id = BSONObjectID.generate
    val now = BSONDateTime(System.currentTimeMillis)
    Future.successful(user.copy(id = id, createdAt = now))
  }
}

object UserBSONFormat {

  implicit object UserBSONReader extends BSONDocumentReader[User] {
    def read(doc: BSONDocument) = User(
      id = doc.getAs[BSONObjectID]("_id").get,
      userName = doc.getAs[String]("user_name").get,
      password = doc.getAs[String]("password").get,
      token = doc.getAs[Token]("token").get,
      createdAt = doc.getAs[BSONDateTime]("created_at").get,
      updatedAt = doc.getAs[BSONDateTime]("updated_at").get,
      status = doc.getAs[Int]("status").get,
      profileId = doc.getAs[String]("profile_id").get
    )
  }

  implicit object UserBSONWriter extends BSONDocumentWriter[User] {
    def write(obj: User): BSONDocument = BSONDocument(
      "_id" -> obj.id,
      "userName" -> obj.userName,
      "password" -> obj.password,
      "status" -> obj.status,
      "created_at" -> obj.createdAt,
      "updated_at" -> obj.updatedAt,
      "profile_id" -> obj.profileId
    )
  }

  implicit object TokenBSONReader extends BSONDocumentReader[Token] {
    def read(doc: BSONDocument) = Token(
      token = doc.getAs[String]("access_token").get,
      expires = doc.getAs[Long]("expired_at").get
    )
  }
  implicit object TokenBSONWriter extends BSONDocumentWriter[Token] {
    def write(obj: Token): BSONDocument = BSONDocument(
      "access_token" -> obj.token,
      "password" -> obj.expires
    )
  }
}