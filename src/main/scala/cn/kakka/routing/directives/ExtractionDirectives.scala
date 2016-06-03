package cn.kakka.routing.directives

import kakka.dao.Database
import kakka.user.User
import shapeless._
import spray.routing._
import spray.routing.Directives._

import scala.concurrent.ExecutionContext
/**
  * Created by skylai on 16/6/3.
  */
trait ExtractionDirectives {
  implicit val db: Database
  implicit val executor: ExecutionContext

  def userPathPrefix: Directive1[User] =
    for {
      userName <- pathPrefix(Segment)
      userOption <- onSuccess(db.users.findByUserName(userName))
      user <- provideNonOption(userOption)
    } yield user

  def provideNonOption[T](option: Option[T]): Directive1[T] =
    option match {
      case Some(value) => provide(value)
      case _ => reject
    }

  def pageable(takeMax: Int): Directive[::[Option[Int],::[Option[Int],HNil]]] =
    parameters('drop.as[Int].?, 'take.as[Int].?).hmap {
      case drop :: None :: HNil => drop :: Some(takeMax) :: HNil
      case drop :: Some(take) :: HNil if take > takeMax => drop :: Some(takeMax) :: HNil
      case drop :: take :: HNil => drop :: take :: HNil
    }
}
