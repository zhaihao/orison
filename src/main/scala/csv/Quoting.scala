/*
 * Copyright (c) 2019.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */
package csv

sealed trait Quoting extends Product with Serializable
case object QUOTE_ALL extends Quoting
case object QUOTE_MINIMAL extends Quoting
case object QUOTE_NONE extends Quoting
case object QUOTE_NONNUMERIC extends Quoting
