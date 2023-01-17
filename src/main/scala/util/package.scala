import com.typesafe.scalalogging.Logger

import scala.annotation.tailrec

/** package
  *
  * @author
  *   zhaihao
  * @version 1.0
  * @since 2020/5/19
  *   6:34 下午
  */
package object util {
  def timed[T](log: Logger, label: String = "")(t: => T): T = {
    val start   = System.nanoTime
    val result  = t
    val elapsed = System.nanoTime - start
    log.debug(label + " took " + (elapsed / 1e6) + " ms")
    result
  }

  @tailrec
  def retryErr[A](times: Int = 3, delay: Long = 100)(f: => A)(g: PartialFunction[Throwable, Any]): A = {
    try {
      f
    } catch {
      case e: Exception =>
        g(e)
        if (times == 1) throw e
        else {
          Thread.sleep(delay)
          retryErr(times - 1, delay)(f)(g)
        }
    }
  }

  @tailrec
  def retry[A](times: Int = 3, delay: Long = 100)(f: => A): A = {
    try {
      f
    } catch {
      case e: Exception =>
        if (times == 1) throw e
        else {
          Thread.sleep(delay)
          retry(times - 1, delay)(f)
        }

    }
  }

  @tailrec
  def repeat[A](times: Int)(logic: => A): Unit = {
    times match {
      case 0 =>
      case _ => logic; repeat(times - 1)(logic)
    }
  }

}
