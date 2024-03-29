/*
 * Copyright (c) 2018.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package syntax
import test.BaseSpec

/** StringOpsSpec
  *
  * @author
  *   zhaihao
  * @version 1.0
  * 2017-12-21 20:14
  */
class StringOpsSpec extends BaseSpec {
  import syntax.string._

  "underscores" in {
    "user3Id".underscores ==> "user_3_id"
  }

  "camel" in {
    "user_3_id".camel ==> "user3Id"
  }

  "string toInt" in {
    "A".toRadixInt(16)  ==> 10
    "a".toRadixInt(16)  ==> 10
    "b".toRadixInt(16)  ==> 11
    "101".toRadixInt(2) ==> 5
    "10".toRadixInt(8)  ==> 8
  }

  "string padStart" in {
    "10".padStart(5, '0')    ==> "00010"
    "12345".padStart(5, '0') ==> "12345"
    "".padStart(5, '0')      ==> "00000"
  }

  "string padEnd" in {
    "10".padEnd(5, '0')    ==> "10000"
    "12345".padEnd(5, '0') ==> "12345"
    "".padEnd(5, '0')      ==> "00000"
  }

  "string repeat" in {
    "10".repeat(3) ==> "101010"
    "10".repeat(0) ==> ""
  }

  "string subStringL" in {

    "Hello".subStringR(1)  ==> "o"
    "Hello".subStringR(0)  ==> ""
    "Hello".subStringR(30) ==> "Hello"
  }

  "hasText" in {
    "123".hasText   ==> true
    "123\n".hasText ==> true
    "12\t3".hasText ==> true

    val s: String = null
    s.hasText        ==> false
    "".hasText       ==> false
    "  ".hasText     ==> false
    "\n\t\r".hasText ==> false
  }

}
