/*
 * Copyright (c) 2020-2023.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit <http://ooon.me> or mail to zhaihao@ooon.me
 */

package crypto

import java.util.Base64
import javax.crypto.spec.{IvParameterSpec, PBEKeySpec, SecretKeySpec}
import javax.crypto.{Cipher, SecretKeyFactory}

/**
  * AESImpl
  *
  * @author zhaihao
  * @version 1.0
  * @since 2023/1/17 21:03
  */
class AESImpl(
    version:            Int,
    transforms:         String,
    secretKeyAlgorithm: String,
    salt:               Option[String] = Some("foewoe7EishovooRaiz1eeng0aa9aid7")
) {

  private def getCipher(mode: Int, password: String) = {
    val iv         = new IvParameterSpec(new Array[Byte](16))
    val spec       = new PBEKeySpec(password.toCharArray, salt.get.getBytes, 65536, version)
    val keyFactory = SecretKeyFactory.getInstance(secretKeyAlgorithm)
    val key        = new SecretKeySpec(keyFactory.generateSecret(spec).getEncoded, "AES")
    val cipher     = Cipher.getInstance(transforms)
    cipher.init(mode, key, iv)
    cipher
  }

  def encrypt(content: Array[Byte], password: String): String = {
    val cipher = getCipher(Cipher.ENCRYPT_MODE, password)
    val result = cipher.doFinal(content)
    Base64.getEncoder.encodeToString(result)
  }

  def decrypt(content: String, password: String): String = {
    val cipher = getCipher(Cipher.DECRYPT_MODE, password)
    val result = cipher.doFinal(Base64.getDecoder.decode(content))
    new String(result)
  }
}
