/*
 * Copyright (c) 2018.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package config

import org.scalatest.BeforeAndAfter
import test.BaseSpec

import scala.util.Properties

/**
  * EnvSpec
  *
  * @author zhaihao
  * @version 1.0 06/02/2018 16:30
  */
class EnvSpec extends BaseSpec with BeforeAndAfter {

  before {
    Properties.setProp("env", "test")
  }

  "env" in {
    // 继承，或者 with
    class App extends Serializable with Env {
      def holdTest = env
    }

    new App().holdTest ==> Some("test")
  }
}
