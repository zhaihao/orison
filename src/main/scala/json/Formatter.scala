/*
 * Copyright (c) 2019.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package json

import java.sql.Timestamp

import play.api.libs.json._

/** Formatter
  *
  * @author
  *   zhaihao
  * @version 1.0
  * 2018/4/3 14:34
  */
object Formatter {

  implicit val timestampFormat = new Format[Timestamp] {

    // 2017-06-21 18:03:19.0
    override def writes(t: Timestamp): JsValue = JsString(t.toString)

    override def reads(json: JsValue): JsResult[Timestamp] = json match {
      case JsNumber(d) => JsSuccess(new Timestamp(d.toLong))
      case JsString(s) =>
        scala.util.control.Exception
          .nonFatalCatch[Timestamp] opt Timestamp.valueOf(s) match {
          case Some(d) => JsSuccess(d)
          case _ =>
            JsError(Seq(JsPath() -> Seq(JsonValidationError("error.expected.date.format", s))))
        }
      case _ =>
        JsError(Seq(JsPath() -> Seq(JsonValidationError("error.expected.date"))))
    }
  }

  implicit val mapFormat = new OFormat[Map[String, Any]] {
    override def reads(json: JsValue) = {
      try {
        JsSuccess(
          json
            .as[JsObject]
            .value
            .view
            .mapValues {
              case v: JsNumber  => v.value
              case v: JsBoolean => v.value
              case v: JsString  => v.value
              case _ => throw new Exception
            }
            .toMap
        )
      } catch {
        case e: Exception => JsError(e.getMessage)
      }
    }

    override def writes(o: Map[String, Any]): JsObject = {
      JsObject(o.view.mapValues {
        case v: String  => JsString(v)
        case v: Int     => JsNumber(v)
        case v: Double  => JsNumber(v)
        case v: Long    => JsNumber(v)
        case v: Boolean => JsBoolean(v)
      }.toSeq)
    }
  }

}
