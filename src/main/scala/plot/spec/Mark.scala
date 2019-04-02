/*
 * Copyright (c) 2019.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package plot.spec
import play.api.libs.json.{Json, Writes}

/**
  * Mark
  *
  * @author zhaihao
  * @version 1.0
  * @since 2019-03-22 14:46
  */
sealed abstract class Mark(`type`: String)

case class AreaConfig(`type`:  String = "area")  extends Mark(`type`)
case class PointConfig(`type`: String = "point") extends Mark(`type`)
case class BarConfig(`type`:   String = "bar")   extends Mark(`type`)
case class TickConfig(`type`:  String = "tick")  extends Mark(`type`)
case class LineConfig(`type`:  String = "line")  extends Mark(`type`)

object Mark {
  val AreaConfigFormat  = Json.format[AreaConfig]
  val PointConfigFormat = Json.format[PointConfig]
  val BarConfigFormat   = Json.format[BarConfig]
  val TickConfigFormat  = Json.format[TickConfig]
  val LineConfigFormat  = Json.format[LineConfig]

  implicit val MarkWrite = new Writes[Mark] {
    override def writes(o: Mark) = o match {
      case a: AreaConfig  => AreaConfigFormat.writes(a)
      case a: PointConfig => PointConfigFormat.writes(a)
      case a: BarConfig   => BarConfigFormat.writes(a)
      case a: TickConfig  => TickConfigFormat.writes(a)
      case a: LineConfig  => LineConfigFormat.writes(a)
      case _ => throw new Exception("mark type is unsupported!")
    }
  }

  // for simple use
  val Area  = AreaConfig()
  val Point = PointConfig()
  val Bar   = BarConfig()
  val Tick  = TickConfig()
  val Line  = LineConfig()
}
