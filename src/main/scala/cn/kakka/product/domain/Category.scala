package kakka
package product

import kakka.commons.BaseJsonFormats

/**
  * Created by skylai on 16/5/31.
  */
case class Category (id: String, name: String, parent: String = "0")

trait CategoryFormatter extends BaseJsonFormats{
  implicit val categoryFormatter = jsonFormat3(Category)
}

object CategoryFormatter extends CategoryFormatter

