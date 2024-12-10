/*
 * Copyright (c) 2020-2024.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit <http://ooon.me> or mail to zhaihao@ooon.me
 */

package unit.groups

import squants.UnitOfMeasure
import squants.mass._
import squants.space.{AstronomicalUnits, Length, LightYears, Parsecs}
/**
  * misc
  *
  * @author zhaihao
  * @version 1.0
  * @since 2024-12-09 17:41
  */
object misc {
  /** U.K. Customary units for mass */
  object UkCustomaryMasses extends UnitGroup[Mass] {
    val units: Set[UnitOfMeasure[Mass]] = Set(Ounces, Pounds, Stone, Kilopounds, Megapounds)
  }

  /** Mass units in the Troy system */
  object TroyMasses extends UnitGroup[Mass] {
    val units: Set[UnitOfMeasure[Mass]] = Set(Pennyweights, TroyGrains, TroyOunces, TroyPounds)
  }

  object AstronomicalLengthUnitGroup extends UnitGroup[Length] {
    val units: Set[UnitOfMeasure[Length]] = Set(AstronomicalUnits, LightYears, Parsecs)
  }
}
