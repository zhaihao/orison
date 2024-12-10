/*
 * Copyright (c) 2020-2024.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit <http://ooon.me> or mail to zhaihao@ooon.me
 */

package unit.groups

import squants.UnitOfMeasure
import squants.information._
/**
  * information
  *
  * @author zhaihao
  * @version 1.0
  * @since 2024-12-09 17:40
  */
object information {
  /** Base-2 units for Information defined by the IEC */
  object IECInformation extends UnitGroup[Information] {
    val units: Set[UnitOfMeasure[Information]] =
      Set(Bytes, Kibibytes, Mebibytes, Gibibytes, Tebibytes, Pebibytes, Exbibytes, Zebibytes, Yobibytes)
  }

  /** Base-10 units for Information */
  object MetricInformation extends UnitGroup[Information] {
    val units: Set[UnitOfMeasure[Information]] =
      Set(Bytes, Kilobytes, Megabytes, Gigabytes, Terabytes, Petabytes, Exabytes, Zettabytes, Yottabytes)
  }

}
