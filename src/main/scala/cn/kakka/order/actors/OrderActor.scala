package kakka
package order

/**
  * Created by skylai on 16/5/26.
  */

sealed trait OrderStatus

object OrderStatus{
  case object Active extends OrderStatus
  case object Canceled extends OrderStatus

}