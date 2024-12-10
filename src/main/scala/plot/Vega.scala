///*
// * Copyright (c) 2019.
// * OOON.ME ALL RIGHTS RESERVED.
// * Licensed under the Mozilla Public License, version 2.0
// * Please visit http://ooon.me or mail to zhaihao@ooon.me
// */
//
//package plot
//import play.api.libs.json._
//import plot.dsl._
//
///** Vega
//  *
//  * @author
//  *   zhaihao
//  * @version 1.0
//  * @since 2019-03-22
//  *   11:34
//  */
//
//object Vega {
//
//  implicit val VegaWrites: Writes[Vega] = new Writes[Vega] {
//    override def writes(o: Vega): JsValue = {
//      Json.parse(o.viz).as[JsObject] + ("data" -> o.data.as[JsObject])
//    }
//  }
//
//}
//
//case class Vega() extends EmbedConfigDSL with DataDSL with VizDSL with RenderDSL {
//  import Vega._
//
//  def json = {
//    if (data == JsNull) {
//      throw new IllegalArgumentException("vega not data to viz")
//    }
//    Json.prettyPrint(Json.toJson(this))
//  }
//}
