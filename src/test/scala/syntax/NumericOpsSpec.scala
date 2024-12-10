package syntax

import test.BaseSpec

/** NumericOpsSpec
  *
  * @author
  *   zhaihao
  * @version 1.0
  * @since 2020/9/10
  *   11:13 上午
  */
class NumericOpsSpec extends BaseSpec {

  import maths.power._
  "pow" in {
    (2 ^* 3) ==> 8
  }
}
