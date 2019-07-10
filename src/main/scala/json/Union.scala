/*
 * Copyright (c) 2019.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package json
import play.api.libs.json._
import scala.reflect.ClassTag

/**
  * Union
  *
  * @author zhaihao
  * @version 1.0
  * @since 2019-07-10 13:19
  */
class Union[A](typeField: String,
               readWith:  PartialFunction[String, JsValue => JsResult[A]],
               writeWith: PartialFunction[A, JsValue]) {

  def and[B](typeTag: String)(implicit ev: B <:< A, ct: ClassTag[B], f: Format[B]) = {
    val readCase: PartialFunction[String, JsValue => JsResult[A]] = {
      case `typeTag` =>
        jsValue: JsValue =>
          Json.fromJson(jsValue)(f).asInstanceOf[JsResult[A]]
    }

    val writeCase: PartialFunction[A, JsValue] = {
      case value: B =>
        Json.toJson(value)(f).as[JsObject] ++ JsObject(Seq(typeField -> JsString(typeTag)))
    }

    new Union(typeField, readWith.orElse(readCase), writeWith.orElse(writeCase))
  }

  private val defaultReads: PartialFunction[String, JsValue => JsResult[A]] = {
    case attemptedType =>
      _ =>
        JsError(__ \ typeField, s"$attemptedType is not a recognised $typeField")
  }

  def format: Format[A] = {
    val reads = Reads[A] { json =>
      (json \ typeField).validate[String].flatMap { typeTag =>
        readWith.orElse(defaultReads)(typeTag)(json)
      }
    }
    val writes = Writes[A](writeWith)
    Format(reads, writes)
  }
}

object Union {

  def from[A](typeField: String) =
    new Union[A](typeField, PartialFunction.empty, PartialFunction.empty)
}
