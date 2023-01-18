/*
 * Copyright (c) 2020-2023.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit <http://ooon.me> or mail to zhaihao@ooon.me
 */

/**
  * package
  *
  * @author zhaihao
  * @version 1.0
  * @since 2023/1/17 22:13
  */
package object crypto {
  val AES128 = new AESImpl(128, "AES/CBC/PKCS5PADDING", 16, "PBKDF2WithHmacSHA256")
  val AES192 = new AESImpl(192, "AES/CBC/PKCS5PADDING", 16, "PBKDF2WithHmacSHA256")
  val AES256 = new AESImpl(256, "AES/CBC/PKCS5PADDING", 16, "PBKDF2WithHmacSHA256")

}
