/*
 * Copyright (c) 2019.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package maths
import test.BaseSpec

/** MathsSpec
  *
  * @author
  *   zhaihao
  * @version 1.0
  * 2019-01-07 11:01
  */
class MathsSpec extends BaseSpec {
  "test e" in {
    exp(1, 100).toString().take(22) ==> "2.71828182845904523536"
  }

  "sigmoid" in {
    sigmoid(-5)      ==> 0.0066928509242848554
    sigmoid(-5, 200) ==> BigDecimal("0.006692850924284855559361980381325179")
  }

  "percentile " in {
    percentile(1 to 100, 0.95) ==> 95.05
  }
}
