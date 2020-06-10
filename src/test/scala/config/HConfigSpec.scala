package config

import test.BaseSpec

/**
  * HConfigSpec
  *
  * @author zhaihao
  * @version 1.0
  * @since 2020/5/26 11:01 上午
  */
class HConfigSpec extends BaseSpec {

  object Root extends HConfig
  object A    extends HConfig("a")
  object B    extends HConfig("b")

  "namespace" in {
    Root.config.hasPath("a") ==> true
    Root.config.hasPath("b") ==> true

    A.config.getInt("a1")       ==> 1
    B.config.getString("b1.b2") ==> "bb"
  }
}
