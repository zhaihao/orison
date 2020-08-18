/*
 * Copyright (c) 2019.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package syntax
import com.typesafe.config.{Config, ConfigFactory}
import test.BaseSpec

/**
  * ConfigOpsSpec
  *
  * @author zhaihao
  * @version 1.0 2018-01-19 11:44
  */
class ConfigOpsSpec extends BaseSpec {

  import syntax.config._

  val config = ConfigFactory.parseString("""
                                           |a=1
                                           |b=2
    """.stripMargin)

  "opt" in {
    config.opt[Int]("a")    ==> Some(1)
    config.opt[String]("b") ==> Some("2")
  }

  "getOrElse" in {
    config.getOrElse[Int]("a", 0) ==> 1
    config.getOrElse[Int]("c", 0) ==> 0
  }

  "config list" in {
    val c = ConfigFactory.parseString("""
                                        |users = [
                                        | {
                                        |   name=Tom
                                        |   age=10
                                        | },
                                        | {
                                        |   name="Jack"
                                        |   age=11
                                        | }
                                        |]
                                        |
                                        |students=[]
    """.stripMargin)

    val list: Option[List[Config]] = c.opt[List[Config]]("users")
    list.get.head.opt[String]("name")   ==> Some("Tom")
    c.opt[List[Config]]("abc")          ==> None
    c.opt[List[Config]]("students").get ==> List.empty[Config]
  }

  "json render" in {
    println(config.json)
      """{
        |    "a" : 1,
        |    "b" : 2
        |}
        |""".stripMargin
  }

}
