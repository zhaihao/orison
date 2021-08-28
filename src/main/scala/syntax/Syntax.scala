/*
 * Copyright (c) 2019.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package syntax

/** Syntax
  *
  * @author
  *   zhaihao
  * @version 1.0
  * @since 2019-04-01
  *   11:40
  */
trait Syntax {

  // 单个 import
  object id      extends ToIdOps
  object file    extends ToFileOps
  object string  extends ToStringOps
  object config  extends ToConfigOps
  object numeric extends ToNumericOps
  object feature extends ToFutureOps
}

// 批量 import
trait ToDataOps extends ToIdOps

// 批量 import
trait ToTypeClassOps
