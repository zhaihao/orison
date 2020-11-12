package test

import org.scalatest.Tag

/**
  * tag
  *
  * @author zhaihao
  * @version 1.0
  * @since 2020/11/12 3:01 下午
  */
object tag {

  /**
    * 手动执行的 test，批量测试时不要执行
    */
  object ManualTest extends Tag("ManualTest")
  object DBTest     extends Tag("DBTest")
}
