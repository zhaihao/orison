/*
 * Copyright (c) 2019.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package plot
import play.api.libs.json._
import plot.dsl._

/**
  * Vega
  *
  * @author zhaihao
  * @version 1.0
  * @since 2019-03-22 11:34
  */
case class Vega(width: Option[Int] = None, height: Option[Int] = None)
    extends VegaConfigDSL
    with DataDSL
    with MarkDSL
    with RenderDSL
    with EncodingDSL
    with TitleDSL
    with TransformDSL
    with DescriptionDSL
    with JsonDSL

case class Title(text:       Option[String]      = None,
                 anchor:     Option[TitleAnchor] = None,
                 angle:      Option[Double]      = None,
                 baseline:   Option[BaseLine]    = None,
                 color:      Option[String]      = None,
                 font:       Option[String]      = None,
                 fontSize:   Option[Int]         = None,
                 fontWeight: Option[Int]         = None,
                 frame:      Option[String]      = None,
                 limit:      Option[Int]         = None,
                 offset:     Option[Int]         = None,
                 orient:     Option[Orient]      = None,
                 zindex:     Option[Int]         = None)

object Title {
  implicit val TitleFormat = Json.format[Title]
}

object TitleAnchor {
  val Start:  TitleAnchor = "start"
  val Middle: TitleAnchor = "middle"
  val End:    TitleAnchor = "end"
}

object BaseLine {
  val Top:        BaseLine = "top"
  val Middle:     BaseLine = "middle"
  val Bottom:     BaseLine = "bottom"
  val Alphabetic: BaseLine = "alphabetic"
}

object Orient {
  val Top:    Orient = "top"
  val Bottom: Orient = "bottom"
  val Left:   Orient = "left"
  val Right:  Orient = "right"
}

object Vega {

  implicit val VegaWrite = new Writes[Vega] {
    override def writes(o: Vega) =
      JsObject(
        Seq("$schema"                 -> JsString(plot.schema)) ++
          o.width.map("width"         -> JsNumber(_)) ++
          o.height.map("height"       -> JsNumber(_)) ++
          o.title.map("title"         -> Json.toJson(_)) ++
          o.data.map("data"           -> Json.toJson(_)) ++
          o.mark.map("mark"           -> Json.toJson(_)) ++
          o.encoding.map("encoding"   -> Json.toJson(_)) ++
          o.transform.map("transform" -> Json.toJson(_))
      )
  }
}
