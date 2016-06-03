package kakka
package user

import kakka.commons.BaseJsonFormats
import kakka.dao.{BaseModel, Database, Table}
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.api.indexes.{Index, IndexType}
import reactivemongo.bson.{BSON, BSONArray, BSONDateTime, BSONDocument, BSONDocumentReader, BSONDocumentWriter, BSONObjectID}
import spray.json.DefaultJsonProtocol

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by skylai on 16/5/26.
  */
case class Profile (id: BSONObjectID = BSONObjectID("00" * 12),
  fullName: String,
  email: String,
  avatar: String,
  addresses: List[Address]
)extends BaseModel{

}

class ProfileTable(database: Database, collection: BSONCollection)(implicit executor: ExecutionContext) extends Table[Profile](database, collection) {
  implicit val addressReader = ProfileBSONFormat.AddressBSONReader
  implicit val addressWriter = ProfileBSONFormat.AddressBSONWriter

  implicit val reader = ProfileBSONFormat.ProfileBSONReader
  implicit val writer = ProfileBSONFormat.ProfileBSONWriter

  override def preInsert(p: Profile): Future[Profile] = {
    val id = BSONObjectID.generate
    Future.successful(p.copy(id = id))
  }
}

trait ProfileFormatter extends AddressFormatter with BaseJsonFormats{
  implicit val profileFormatter = jsonFormat5(Profile)
}

object ProfileFormatter extends ProfileFormatter


object ProfileBSONFormat {
  implicit object ProfileBSONReader extends BSONDocumentReader[Profile] {
    def read(doc: BSONDocument) = Profile(
      id = doc.getAs[BSONObjectID]("_id").get,
      fullName = doc.getAs[String]("full_name").get,
      email = doc.getAs[String]("email").get,
      avatar = doc.getAs[String]("avatar").get,
      addresses = doc.getAs[List[Address]]("addresses").getOrElse(Nil)
    )
  }

  implicit object ProfileBSONWriter extends BSONDocumentWriter[Profile] {
    def write(obj: Profile): BSONDocument = BSONDocument(
      "_id" -> obj.id,
      "full_name" -> obj.fullName,
      "email" -> obj.email,
      "avatar" -> obj.avatar,
      "addresses" -> BSONArray(obj.addresses.map{BSON.write(_)}.toList)
    )
  }

  implicit object AddressBSONReader extends BSONDocumentReader[Address] {
    def read(doc: BSONDocument) = Address(
      name = doc.getAs[String]("name").get,
      province = doc.getAs[String]("province").get,
      city = doc.getAs[String]("city").get,
      region = doc.getAs[String]("region").get,
      street = doc.getAs[String]("street").get,
      phone = doc.getAs[String]("phone").get,
      mobile = doc.getAs[String]("mobile").get,
      alias = doc.getAs[String]("alias").get
    )
  }

  implicit object AddressBSONWriter extends BSONDocumentWriter[Address] {
    def write(obj: Address): BSONDocument = BSONDocument(
      "name" -> obj.name,
      "province" -> obj.province,
      "city" -> obj.city,
      "region" -> obj.region,
      "street" -> obj.street,
      "phone" -> obj.phone,
      "mobile" -> obj.mobile,
      "alias" -> obj.alias
    )
  }
}
