/*
 * Copyright (c) 2020-2024.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit <http://ooon.me> or mail to zhaihao@ooon.me
 */

package unit.groups

import squants.UnitOfMeasure
import squants.mass._
import squants.space._

/**
  * uscustomary
  *
  * @author zhaihao
  * @version 1.0
  * @since 2024-12-09 17:45
  */
object uscustomary {
  object mass {
    object UsCustomaryMasses extends UnitGroup[Mass] {
      val units: Set[UnitOfMeasure[Mass]] = Set(Ounces, Pounds, Kilopounds, Megapounds)
    }
  }

  object space {
    object UsCustomaryAreas extends UnitGroup[Area] {
      val units: Set[UnitOfMeasure[Area]] = Set(Acres, SquareFeet, SquareInches, SquareYards, SquareUsMiles)
    }

    object UsCustomaryLengths extends UnitGroup[Length] {
      val units: Set[UnitOfMeasure[Length]] = Set(Inches, Feet, Yards, UsMiles)
    }

    object UsCustomaryGeneralVolumes extends UnitGroup[Volume] {
      val units: Set[UnitOfMeasure[Volume]] = Set(CubicInches, CubicFeet, CubicYards, AcreFeet)
    }

    object UsCustomaryLiquidVolumes extends UnitGroup[Volume] {
      val units: Set[UnitOfMeasure[Volume]] = Set(Teaspoons, Tablespoons, FluidOunces, UsCups, UsPints, UsQuarts, UsGallons)
    }

    object UsCustomaryDryVolumes extends UnitGroup[Volume] {
      val units: Set[UnitOfMeasure[Volume]] = Set(UsDryPints, UsDryCups, UsDryQuarts, UsDryGallons)
    }
  }
}
