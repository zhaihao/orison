import com.typesafe.scalalogging.Logger

/**
  * package
  *
  * @author zhaihao
  * @version 1.0
  * @since 2020/5/19 6:34 下午
  */
package object util {
  def timed[T](log: Logger, label: String = "")(t: => T): T = {
    val start   = System.nanoTime
    val result  = t
    val elapsed = System.nanoTime - start
    log.debug(label + " took " + (elapsed / 1e6) + " ms")
    result
  }
}
