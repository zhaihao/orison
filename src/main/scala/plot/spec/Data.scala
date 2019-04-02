/*
 * Copyright (c) 2019.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package plot.spec

import play.api.libs.json.Json

/**
  * Data
  *
  * @author zhaihao
  * @version 1.0
  * @since 2019-03-21 16:56
  */
case class Data(url: Option[String] = None, values: Option[Seq[Map[String, Any]]] = None)

object Data {

  // intellij idea warning no use
  // do not remove this import, this is it's bug.
  import json.Formatter._
  implicit val DataFormat = Json.format[Data]
}
