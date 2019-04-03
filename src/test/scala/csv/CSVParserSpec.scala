/*
 * Copyright (c) 2019.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package csv
import test.BaseSpec

/**
  * CSVParserSpec
  *
  * @author zhaihao
  * @version 1.0
  * @since 2019-04-03 11:54
  */
class CSVParserSpec extends BaseSpec {
  "parse string" in {
    csv.CSVParser.parse("a,b,c", '\\', ',', '"')             ==> Some(List("a", "b", "c"))
    csv.CSVParser.parse("\"a\",\"b\",\"c\"", '\\', ',', '"') ==> Some(List("a", "b", "c"))
  }
}
