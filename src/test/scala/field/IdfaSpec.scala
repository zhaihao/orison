/*
 * Copyright (c) 2018.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package field

import test.BaseSpec

/** IdfaSpec
  *
  * @author
  *   zhaihao
  * @version 1.0
  * 2017-12-27 19:05
  */
//noinspection SpellCheckingInspection
class IdfaSpec extends BaseSpec {
  "idfa 字段处理" in {
    Idfa.normalize(Some("95955F33-BFBD-48BA-A630-866D2DAE482D")) ==> Some("95955f33-bfbd-48ba-a630-866d2dae482d")
    Idfa.normalize(Some("95955f33-bfbd-48ba-a630-866d2dae482d")) ==> Some("95955f33-bfbd-48ba-a630-866d2dae482d")
    Idfa.normalize(Some("95955f3-bfbd-48ba-a630-866d2dae482d"))  ==> None
    Idfa.normalize(None)                                         ==> None
  }

}
