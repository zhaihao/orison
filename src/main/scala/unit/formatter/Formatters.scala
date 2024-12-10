/*
 * Copyright (c) 2020-2024.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit <http://ooon.me> or mail to zhaihao@ooon.me
 */

package unit.formatter

import squants.Quantity
import squants.information.Information
import squants.mass.*
import squants.space.Length
import unit.groups.UnitGroup
import unit.groups.information.{IECInformation, MetricInformation}
import unit.groups.misc.{AstronomicalLengthUnitGroup, TroyMasses}
/**
  * Formatters
  *
  * @author zhaihao
  * @version 1.0
  * @since 2024-12-09 17:39
  */
object Formatters {
  val AstroUnitFormatter: DefaultFormatter[Length] = new DefaultFormatter(AstronomicalLengthUnitGroup)

  val TroyFormatter: DefaultFormatter[Mass] = new DefaultFormatter(TroyMasses)

  val InformationMetricFormatter: DefaultFormatter[Information] =  new DefaultFormatter(MetricInformation)

  val InformationIECFormatter: DefaultFormatter[Information] = new DefaultFormatter(IECInformation)
}

object implicits {
  implicit def implicitFormatter[A <: Quantity[A]](implicit implicitUnitGroup: UnitGroup[A]): Formatter[A] = {
    new DefaultFormatter[A](implicitUnitGroup)
  }
}

object syntax {
  implicit class FormattedQuantity[A <: Quantity[A]](quantity: Quantity[A]) {
    def inBestUnit(implicit formatter: Formatter[A]): Quantity[A] = {
      formatter.inBestUnit(quantity)
    }
  }
}
