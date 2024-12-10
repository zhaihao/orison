/*
 * Copyright (c) 2019.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package unsafe
import test.BaseSpec

/** UnsafeSpec
  *
  * @author
  *   zhaihao
  * @version 1.0
  * @since 2019-04-01
  *   11:50
  */
class UnsafeSpec extends BaseSpec {

  /** 不调用构造方法的情况下生成对象 */
  "allocateInstance" in {
    val user = unsafe.allocateInstance(classOf[User]).asInstanceOf[User]
    user.name ==> null
    user.age  ==> 0

    val user1 = new User
    user1.name ==> "Tom"
    user1.age  ==> 10
  }

  /** 返回成员属性内存地址相对于对象内存地址的偏移量，通过该方法可以计算一个对象在内存中的空间大小， 方法是通过反射得到它的所有Field(包括父类继承得到的)，找出Field中偏移量最大值，
    * 然后对该最大偏移值填充字节数即为对象大小；
    */
  "objectFieldOffset" in {
    val fields = classOf[User].getDeclaredFields
    fields.size ==> 2

    val nameField = classOf[User].getDeclaredField("name")
    val ageField  = classOf[User].getDeclaredField("age")

    unsafe.objectFieldOffset(nameField) ==> 16
    unsafe.objectFieldOffset(ageField)  ==> 12
  }

  /** 忽略任意限制 直接修改对象的内存 */
  "put" in {
    val user      = new User
    val nameField = classOf[User].getDeclaredField("name")
    val ageField  = classOf[User].getDeclaredField("age")
    unsafe.putInt(user, unsafe.objectFieldOffset(ageField), 9)
    unsafe.putObject(user, unsafe.objectFieldOffset(nameField), "Lucy")

    user.age  ==> 9
    user.name ==> "Lucy"
  }

  /** 忽略任意限制 直接读取对象的内存 */
  "get" in {
    val user      = new User
    val nameField = classOf[User].getDeclaredField("name")
    val ageField  = classOf[User].getDeclaredField("age")

    unsafe.getInt(user, unsafe.objectFieldOffset(ageField)) ==> 10
    unsafe
      .getObject(user, unsafe.objectFieldOffset(nameField))
      .toString ==> "Tom"
  }

  "arrayBaseOffset arrayIndexScale" in {
    val arr = Array(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    val a   = unsafe.arrayBaseOffset(classOf[Array[Int]])
    a ==> 16
    val b = unsafe.arrayIndexScale(classOf[Array[Int]])
    b ==> 4
    for (i <- arr.indices) {
      unsafe.getInt(arr, a.toLong + b * i) ==> arr(i)
    }

    val arrayByte = Array(1.toByte, 2.toByte, 3.toByte)
    for (i <- arrayByte.indices) {
      unsafe.getByte(arrayByte,
                     unsafe.arrayBaseOffset(classOf[Array[Byte]]).toLong
                       + unsafe.arrayIndexScale(classOf[Array[Byte]]) * i
      ) ==> arrayByte(i)
    }
  }

//  "eval" - {
//    "math expr" in {
//      Eval[Int]("1 + 1") ==> 2
//    }
//
//    "other type expr" in {
//      eval[Apple]("""
//                    |import unsafe.Apple
//                    |Apple(1,"apple")
//        """.stripMargin) ==> Apple(1, "apple")
//    }
//  }
}

class User {
  val name = "Tom"
  var age = 10
}

case class Apple(a: Int, b: String)
