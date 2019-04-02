/*
 * Copyright (c) 2019.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package plot.spec.encoding

import plot.AggOp

/**
  * Aggregation
  *
  * @author zhaihao
  * @version 1.0 2019-03-24 16:16
  */
class Aggregation {}

object AggOp {
  val Count:     AggOp = "count"
  val Valid:     AggOp = "valid"
  val Missing:   AggOp = "missing"
  val Distinct:  AggOp = "distinct"
  val Sum:       AggOp = "sum"
  val Mean:      AggOp = "mean"
  val Average:   AggOp = "average"
  val Variance:  AggOp = "variance"
  val Variancep: AggOp = "variancep"
  val Stdev:     AggOp = "stdev"
  val Stdevp:    AggOp = "stdevp"
  val Stderr:    AggOp = "stderr"
  val Median:    AggOp = "median"
  val Q1:        AggOp = "q1"
  val Q3:        AggOp = "q3"
  val Ci0:       AggOp = "ci0"
  val Ci1:       AggOp = "ci1"
  val Min:       AggOp = "min"
  val Max:       AggOp = "max"
  val Argmin:    AggOp = "argmin"
  val Argmax:    AggOp = "argmax"
}
