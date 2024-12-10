/*
 * Copyright (c) 2020-2024.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit <http://ooon.me> or mail to zhaihao@ooon.me
 */

package unit.groups

import squants.{Dimension, Quantity, SiUnit, UnitOfMeasure}
import squants.electro.*
import squants.mass.{AreaDensity, KilogramsPerHectare, Mass, Tonnes}
import squants.motion.{KilometersPerHour, Velocity}
import squants.space.*
import squants.time.{Days, Hours, Minutes, Time}
/**
  * si
  *
  * @author zhaihao
  * @version 1.0
  * @since 2024-12-09 17:42
  */
object si {

  object strict {
    object implicits {
      implicit def mkSiUnitGroup[A <: Quantity[A]](implicit dimension: Dimension[A]): UnitGroup[A] = {
        new UnitGroup[A] {
          val units: Set[UnitOfMeasure[A]] = dimension.units.filter(_.isInstanceOf[SiUnit])
        }
      }
    }
  }
  
  object expanded {
    import strict.implicits.mkSiUnitGroup
    object electro {
      object ExpandedSiElectricCharge extends UnitGroup[ElectricCharge] {
        val units: Set[UnitOfMeasure[ElectricCharge]] = mkSiUnitGroup(ElectricCharge).units ++
          Set(MilliampereSeconds, MilliampereHours, AmpereHours, Abcoulombs)
      }
    }

    object mass {
      object ExpandedSiAreaDensities extends UnitGroup[AreaDensity] {
        val units: Set[UnitOfMeasure[AreaDensity]] = mkSiUnitGroup(AreaDensity).units + KilogramsPerHectare
      }

      object ExpandedSiMasses extends UnitGroup[Mass] {
        val units: Set[UnitOfMeasure[Mass]] = mkSiUnitGroup(Mass).units + Tonnes
      }
    }

    object motion {
      object ExpandedSiVelocities extends UnitGroup[Velocity] {
        val units: Set[UnitOfMeasure[Velocity]] = mkSiUnitGroup(Velocity).units + KilometersPerHour
      }
    }

    object space {
      object ExpandedSiAngles extends UnitGroup[Angle] {
        val units: Set[UnitOfMeasure[Angle]] = mkSiUnitGroup(Angle).units ++ Set(Degrees, Arcminutes)
      }

      object ExpandedSiAreas extends UnitGroup[Area] {
        val units: Set[UnitOfMeasure[Area]] = mkSiUnitGroup(Area).units + Hectares
      }

      object ExpandedSiLengths extends UnitGroup[Length] {
        val units: Set[UnitOfMeasure[Length]] = mkSiUnitGroup(Length).units + AstronomicalUnits
      }

      object ExpandedSiVolumes extends UnitGroup[Volume] {
        val units: Set[UnitOfMeasure[Volume]] = mkSiUnitGroup(Volume).units ++
          Set(Nanolitres, Microlitres, Millilitres, Centilitres, Litres, Hectolitres)
      }
    }

    object time {
      object ExpandedSiTimes extends UnitGroup[Time] {
        val units: Set[UnitOfMeasure[Time]] = mkSiUnitGroup(Time).units ++ Set(Minutes, Hours, Days)
      }
    }
  }
  

}
