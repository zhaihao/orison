/*
 * Copyright (c) 2019.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package plot.spec.encoding
import play.api.libs.json._

/**
  * Scale
  *
  * @author zhaihao
  * @version 1.0
  * @since 2019-03-26 17:08
  */
case class Scale(domain:    Option[Seq[Any]] = None,
                 range:     Option[Seq[Any]] = None,
                 rangeStep: Option[Int]      = None)

object Scale {
  implicit val ScaleFormat = new Format[Scale] {
    override def writes(o: Scale) =
      JsObject(
        Seq.empty[(String, JsValue)] ++
          o.domain.map { x =>
            ("domain", x.head match {
              case _: String => Json.toJson(x.asInstanceOf[Seq[String]])
              case _: Int    => Json.toJson(x.asInstanceOf[Seq[Int]])
              case _ => throw new Exception("scala domain type is not unsupported")
            })
          } ++
          o.range.map { x =>
            ("range", x.head match {
              case _: String => Json.toJson(x.asInstanceOf[Seq[String]])
              case _: Int    => Json.toJson(x.asInstanceOf[Seq[Int]])
              case _ => throw new Exception("scala range type is not unsupported")
            })
          } ++
          o.rangeStep.map(x => ("rangeStep", JsNumber(x)))
      )
    override def reads(json: JsValue) = ???
  }
}
