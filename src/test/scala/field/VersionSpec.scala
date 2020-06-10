package field

import test.BaseSpec

/**
  * VersionSpec
  *
  * @author zhaihao
  * @version 1.0
  * @since 2020/6/10 3:27 下午
  */
class VersionSpec extends BaseSpec {

  "version 解析" in {
    println(Version("1.0.6-SNAPSHOT"))
  }

  "version 比较" in {
    Version("1.0.6-SNAPSHOT") == Version("1.0.6-SNAPSHOT") ==> true
    (Version("1.0.6") > Version("1.0.6-SNAPSHOT"))         ==> true
    Version("1.0.6") > Version("1.0.4")                    ==> true
    Version("1.1.6") > Version("1.0.6")                    ==> true
  }

  "无法比较的version" in {
    assertThrows[IllegalArgumentException] {
      Version("1.1.6.1") > Version("1.0.6")
    }

  }
}
