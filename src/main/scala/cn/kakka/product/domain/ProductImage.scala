package cn.kakka.product.domain

import kakka.commons.BaseJsonFormats

/**
  * Created by skylai on 16/5/31.
  */
case class ProductImage (id: String, productId: String, path: String){

}

trait ProductImageFormatter extends BaseJsonFormats{
  implicit val productImageFormatter = jsonFormat3(ProductImage)
}

object ProductImageFormatter extends ProductImageFormatter

