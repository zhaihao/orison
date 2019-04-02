/*
 * Copyright (c) 2019.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package plot.spec
import play.api.libs.json.{JsValue, Writes}
import plot.spec.transform.{Calculate, Filter}

/**
  * Transform
  *
  * @author zhaihao
  * @version 1.0
  * @since 2019-03-26 17:33
  */
trait Transform

object Transform {

  implicit val TransformWrite = new Writes[Transform] {
    override def writes(o: Transform): JsValue = o match {
      case a: Calculate => Calculate.CalculateFormat.writes(a)
      case a: Filter    => Filter.FilterFormat.writes(a)
      case _ => throw new Exception("transform is unsupported!")
    }
  }
}
