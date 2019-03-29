/*
 * Copyright (c) 2019.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package jvm
import test.BaseSpec

/**
 * JVMSpec 
 *
 * @author zhaihao
 * @version 1.0 
 * @since 2019-03-29 17:07
 */
 class JVMSpec extends BaseSpec{

 "availableProcessors" in { println(jvm.availableProcessors) }

 "freeMemory" in { println(jvm.freeMemory) }

 "maxMemory" in { println(jvm.maxMemory) }

 "totalMemory" in { println(jvm.totalMemory) }

 "pid" in { println(jvm.pid) }

 "up time" in { println(jvm.upTime) }

 "start time" in { println(jvm.startTime) }
}
