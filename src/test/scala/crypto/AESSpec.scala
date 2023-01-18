/*
 * Copyright (c) 2020-2023.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit <http://ooon.me> or mail to zhaihao@ooon.me
 */

package crypto

import com.typesafe.scalalogging.StrictLogging
import test.BaseSpec

/**
  * AESSpec
  *
  * @author zhaihao
  * @version 1.0
  * @since 2023/1/17 22:18
  */
class AESSpec extends BaseSpec with StrictLogging {
  val oriStr   = "hello world"
  val password = "12345"

  "aes" - {
    "128" in {
      val str = AES128.encrypt(oriStr.getBytes, password)
      logger.info(s"encrypt: $str")
      val str2 = AES128.decrypt(str, password)
      logger.info(s"decrypt: $str2")
    }
    "192" in {
      val str = AES192.encrypt(oriStr.getBytes, password)
      logger.info(s"encrypt: $str")
      val str2 = AES192.decrypt(str, password)
      logger.info(s"decrypt: $str2")
    }
    "256" in {
      val str = AES256.encrypt(oriStr.getBytes, password)
      logger.info(s"encrypt: $str")
      val str2 = AES256.decrypt(str, password)
      logger.info(s"decrypt: $str2")
    }

    "123" in {
      logger.info(AES128.decrypt("rb2+qyiAWMHvLDUmJSp3wA==", password))
    }
  }
}
