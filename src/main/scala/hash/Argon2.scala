/*
 * Copyright (c) 2020-2023.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit <http://ooon.me> or mail to zhaihao@ooon.me
 */

package hash

import com.typesafe.scalalogging.StrictLogging
import config.HConfig
import de.mkammerer.argon2.Argon2Factory.Argon2Types
import de.mkammerer.argon2.{Argon2Constants, Argon2Factory, Argon2Helper}
import syntax.config._

/** Argon2
  *
  * @author zhaihao
  * @version 1.0
  * @since 2020/6/27
  *   16:35
  */
object Argon2 extends HConfig with StrictLogging {

  private val ARGON2_TYPE = config.getOrElse("argon2.type", "i") match {
    case "i"  => Argon2Types.ARGON2i
    case "d"  => Argon2Types.ARGON2d
    case "id" => Argon2Types.ARGON2id
    case _    => throw new Exception("argon2 type error")
  }

  private val DEFAULT_SALT_LENGTH =
    config.getOrElse("argon2.salt.length", Argon2Constants.DEFAULT_SALT_LENGTH)

  private val DEFAULT_HASH_LENGTH =
    config.getOrElse("argon2.hash.length", Argon2Constants.DEFAULT_HASH_LENGTH)

  private val INSTANCE    = Argon2Factory.create(ARGON2_TYPE, DEFAULT_SALT_LENGTH, DEFAULT_HASH_LENGTH)
  private val MEMORY_COST = config.getOrElse("argon2.cost.memory", 65536)
  private val TIME_COST   = config.getOrElse("argon2.cost.time", 1000)
  private val PARALLELISM = 1

  private val ITERATIONS = {
    logger.debug("argon2 is computing optimal iterations for this machine.")
    val i = Argon2Helper.findIterations(INSTANCE, TIME_COST, MEMORY_COST, PARALLELISM)
    logger.debug("argon2 optimal iterations for this machine is " + i)
    i
  }

  def hash(str: String) = INSTANCE.hash(ITERATIONS, MEMORY_COST, PARALLELISM, str.getBytes)

  def hash(bytes: Array[Byte]) = INSTANCE.hash(ITERATIONS, MEMORY_COST, PARALLELISM, bytes)

  def hash(chars: Array[Char]) = INSTANCE.hash(ITERATIONS, MEMORY_COST, PARALLELISM, chars)

  def verify(hash: String, origin: String) = INSTANCE.verify(hash, origin.getBytes)

  def verify(hash: String, origin: Array[Byte]) = INSTANCE.verify(hash, origin)

  def verify(hash: String, origin: Array[Char]) = INSTANCE.verify(hash, origin)
}
