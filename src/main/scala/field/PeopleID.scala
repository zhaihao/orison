/*
 * Copyright (c) 2018.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package field

import java.time.LocalDate

/**
  * PeopleID
  *
  * @author zhaihao
  * @version 1.0 2018/3/31 17:06
  */
object PeopleID {

  /**
    * 根据身份证判定性别
    *
    * @param pid 二代身份证
    * @return 1 - 男，0 - 女
    */
  def sex(pid: String): Int = pid.charAt(16) % 2

  /**
    * 根据身份证返回年龄
    *
    * @param pid 二代身份证
    * @return 年龄
    */
  def age(pid: String): Int =
    LocalDate.now().getYear - pid.substring(6, 10).toInt

  /**
    * 根据身份证返回城市 id
    *
    * @param pid 二代身份证
    * @return 城市代码
    */
  def city(pid: String): Int = pid.substring(0, 6).toInt
}
