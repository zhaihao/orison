/*
 * Copyright (c) 2020-2023.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit <http://ooon.me> or mail to zhaihao@ooon.me
 */

package pb

import java.io.PrintStream
import scala.collection.immutable.LazyList.cons

/**
  * Format
  *
  * @author zhaihao
  * @version 1.0
  * @since 2023/1/4 22:41
  */
trait BarFormat {
  def leftBoundary:  String
  def bar:           String
  def empty:         String
  def rightBoundary: String
}

trait AsciiBarFormat extends BarFormat {
  override def leftBoundary:  String = "|"
  override def bar:           String = "#"
  override def empty:         String = "-"
  override def rightBoundary: String = "|"
}

trait UnicodeBarFormat extends BarFormat {
  override def leftBoundary:  String = "|"
  override def bar:           String = "\u2588"
  override def empty:         String = " "
  override def rightBoundary: String = "|"
}

trait Scaling {
  def scale(num:     Double): String
  def scaleRate(num: Double) = f"$num%.3f"
}

trait OrdersOfMagnitudeScaling extends Scaling {
  private val units = Seq("", "K", "M", "G", "T", "P", "E", "Z", "Y")

  protected val divisor: Double = 1000

  override def scale(num: Double): String = {
    require(num >= 0 && divisor > 0)
    val (unit: String, value: Double) = units.to(LazyList).zip(scale(num, divisor)).takeWhile(_._2 > 1d).lastOption.getOrElse(("", num))
    s"${formatValue(value)}$unit"
  }

  private def formatValue(value: Double): String =
    if (value > 10) f"$value%.1f" else f"$value%.2f"

  private def scale(num: Double, divisor: Double): LazyList[Double] =
    cons(num, scale(num / divisor, divisor))
}

//noinspection ScalaUnusedSymbol
trait BinaryScaling extends OrdersOfMagnitudeScaling {
  override protected val divisor = 1024
}

trait NoFormatScaling extends Scaling {
  override def scale(num: Double): String = {
    num.toInt.toString
  }
}

abstract class BarFormatter(unit: String = "it", ncols: Int = 10) extends Scaling with AsciiBarFormat {

  def format(n: Int, total: Int, elapsed: Long): String = {
    require(n <= total && total > 0, s"Current n is $n, total is $total")
    require(n >= 0, "n should be greater or equal to 0")

    val leftBarStr  = leftBar(n, total)
    val rightBarStr = rightBar(n, total, elapsed)

    val nBars = Math.max(1, ncols - leftBarStr.length - rightBarStr.length - 2)
    val bar   = if (nBars > 6) " " + progressBar(n, total, nBars) + " " else "|"

    s"$leftBarStr$bar$rightBarStr"
  }

  def format(n: Int, elapsed: Long): String = rightBar(n, elapsed)

  private def formatInterval(int: Long): String = {
    val day = int / 86400.0
    if (day >= 1) f"$day%.1f day"
    else {
      val h = int / 3600 % 24
      val m = int / 60   % 60
      val s = int        % 60
      f"$h%02d:$m%02d:$s%02d"
    }

  }

  private def leftBar(n: Int, total: Int): String = {
    val v = 100d * n / total
    f"$v%5.1f%%"
  }

  private def progressBar(n: Int, total: Int, nBars: Int): String = {
    val bodyLength = nBars - leftBoundary.length - rightBoundary.length
    val frac       = n.toDouble / total
    val done       = (frac * bodyLength).toInt
    val remaining  = bodyLength - done

    s"$leftBoundary${bar * done}${empty * remaining}$rightBoundary"
  }

  private def rightBar(n: Int, total: Int, elapsed: Long): String = {

    val elapsedSecs: Double = 1d * elapsed / 1000
    val rate:        Double = n / elapsedSecs
    val elapsedFmt = formatInterval(elapsed / 1000)

    val remainingFmt = if (n == 0) "--:--:--" else formatInterval(((total - n) / rate).toLong)

    s"${scale(n)}/${scale(total)} [$elapsedFmt < $remainingFmt, ${formatRate(rate)}]"
  }

  private def rightBar(n: Int, elapsed: Long): String = {
    val elapsedSecs = 1d * elapsed / 1000
    val rate        = n.toDouble / elapsedSecs
    s"${scale(n)} [${formatInterval(elapsed)}, ${formatRate(rate)}]"
  }

//  override def scale(num: Double):      String = f"$num%.1f"
  private def formatRate(rate: Double): String = s"${scaleRate(rate)} $unit/s"
}

class Bar private (total: Int, barFormatter: BarFormatter) {
  private lazy val console = new PrintStream(System.out, false, "UTF-8")
//  private val renderInterval: Long = 1000

  private def erase = console.print("\u001b[2K")

  private val startTime: Long = System.currentTimeMillis()
  private var n = 0
  private val step = 1
  private var lastLen = 0
  private var lastRenderTime: Long = 0

  def update(f: => Any): Unit = {
    erase
    f
    val curTime = System.currentTimeMillis()
    val elapsed: Long = curTime - startTime
    render(elapsed)
    lastRenderTime = curTime
  }

  def inc(): Unit = {
    n += step
    val curTime = System.currentTimeMillis()
    val elapsed: Long = curTime - startTime
    render(elapsed, n == total)
    lastRenderTime = curTime
  }

  private def render(elapsed: Long, finish: Boolean = false): Unit = {
    val barLine: String = if (total == Bar.UnknownTotal) {
      barFormatter.format(n, elapsed)
    } else {
      barFormatter.format(n, total, elapsed)
    }
    val padding: String = " " * Math.max(lastLen - barLine.length, 0)
    console.flush()
    if (finish) console.print(s"$barLine$padding\n")
    else console.print(s"$barLine$padding\r")

    lastLen = barLine.length
  }
}

object Bar {
  private val UnknownTotal: Int = -1

  def apply(total: Int, barFormatter: BarFormatter): Bar = new Bar(total, barFormatter)

  def apply(total: Int): Bar = new Bar(total, new BarFormatter(ncols = 100) with NoFormatScaling with UnicodeBarFormat)

  def apply(total: Int, unit: String): Bar =
    new Bar(total, new BarFormatter(ncols = 100, unit = unit) with NoFormatScaling with UnicodeBarFormat)

  def apply(barFormatter: BarFormatter): Bar = new Bar(UnknownTotal, barFormatter)

  def apply(): Bar = new Bar(UnknownTotal, new BarFormatter() with NoFormatScaling)
}
