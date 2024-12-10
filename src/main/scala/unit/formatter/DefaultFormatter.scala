/*
 * Copyright (c) 2020-2024.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit <http://ooon.me> or mail to zhaihao@ooon.me
 */

package unit.formatter

import squants.Quantity
import unit.groups.UnitGroup
/**
  * DefaultFormatter
  *
  * @author zhaihao
  * @version 1.0
  * @since 2024-12-09 17:38
  */
class DefaultFormatter[A <: Quantity[A]](unitGroup: UnitGroup[A]) extends Formatter[A] {
  override def inBestUnit(quantity: Quantity[A]): A = {
    if (unitGroup.units.isEmpty)
      quantity.in(quantity.unit)
    else {
      val unit =
        unitGroup.sortedUnits
          .takeWhile { u => quantity.to(u).abs >= 1.0 }
          .lastOption
          .getOrElse(unitGroup.sortedUnits.head)
      quantity.in(unit)
    }
  }
}
