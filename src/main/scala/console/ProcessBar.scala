/*
 * Copyright (c) 2020.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit <http://ooon.me> or mail to zhaihao@ooon.me
 */

package console

import java.io.PrintStream
import java.time.{Instant, ZoneId}
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

import scala.collection.immutable.Stream.cons

/**
  * ProcessBar
  *
  * @author zhaihao
  * @version 1.0
  * @since 2020/7/4 21:53
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
  def scale(num: Double): String
}

trait OrdersOfMagnitudeScaling extends Scaling {
  private val units = Seq("", "K", "M", "G", "T", "P", "E", "Z", "Y")

  protected val divisor: Double = 1000

  override def scale(num: Double): String = {
    require(num >= 0 && divisor > 0)
    val (unit: String, value: Double) =
      units.toStream.zip(scale(num, divisor)).takeWhile(_._2 > 1d).lastOption.getOrElse(("", num))
    s"${formatValue(value)}$unit"
  }

  private def formatValue(value: Double): String =
    if (value > 10) f"$value%.1f" else f"$value%.2f"

  private def scale(num: Double, divisor: Double): Stream[Double] =
    cons(num, scale(num / divisor, divisor))
}

trait BinaryScaling extends OrdersOfMagnitudeScaling {
  override protected val divisor = 1024
}

class BarFormatter(unit: String = "it", ncols: Int = 10) extends Scaling with AsciiBarFormat {
  private val longFmt  = DateTimeFormatter.ofPattern("HH:mm:ss")
  private val shortFmt = DateTimeFormatter.ofPattern("mm:ss")

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
    val inst = Instant.ofEpochMilli(int).atZone(ZoneId.systemDefault()).toLocalDateTime
    if (TimeUnit.MILLISECONDS.toHours(int) >= 1) longFmt.format(inst) else shortFmt.format(inst)
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
    val rate:        Double = n.toDouble / elapsedSecs
    val elapsedFmt   = formatInterval(elapsed)
    val remainingFmt = formatInterval((1000 * (total - n) / rate).toLong)

    s"${scale(n)}/${scale(total)} [$elapsedFmt < $remainingFmt, ${formatRate(rate)}]"
  }

  private def rightBar(n: Int, elapsed: Long): String = {
    val elapsedSecs = 1d * elapsed / 1000
    val rate        = n.toDouble / elapsedSecs
    s"${scale(n)} [${formatInterval(elapsed)}, ${formatRate(rate)}]"
  }

  override def scale(num:      Double): String = f"$num%.1f"
  private def formatRate(rate: Double): String = s"${scale(rate)} $unit/s"
}

trait Updater {
  def update(incr: Int): Unit
}

class ProgressBar private (total: Int, barFormatter: BarFormatter) {
  private lazy val console = new PrintStream(System.err, true, "UTF-8")
  private val renderInterval: Long = 100

  private var startTime: Long = _
  private var n       = 0
  private var lastLen = 0
  private var lastRenderTime: Long = 0

  private def now(): Long = TimeUnit.NANOSECONDS.toMillis(System.nanoTime)

  def update(incr: Int): Unit = {
    require(incr >= 0)
    n += incr
    val curTime = now()
    if (curTime - lastRenderTime > renderInterval || n == total) {
      val elapsed: Long = curTime - startTime
      render(elapsed)
      lastRenderTime = curTime
    }
  }

  private def render(elapsed: Long): Unit = {
    val barLine: String = if (total == ProgressBar.UnknownTotal) {
      barFormatter.format(n, elapsed)
    } else {
      barFormatter.format(n, total, elapsed)
    }
    val padding: String = " " * Math.max(lastLen - barLine.length, 0)

    console.print(s"\r$barLine$padding")

    lastLen = barLine.length
  }

  def meter[A](block: Updater => A): Unit = {
    start()
    block(update)
    stop()
  }

  def start(): Unit = {
    startTime = now()
    n = 0
    lastLen = 0
  }

  def stop(): Unit = {
    console.println(" Done.")
  }
}

object ProgressBar {
  private val UnknownTotal: Int = -1

  def apply(total:        Int, barFormatter: BarFormatter): ProgressBar = new ProgressBar(total, barFormatter)
  def apply(total:        Int): ProgressBar = new ProgressBar(total, new BarFormatter())
  def apply(barFormatter: BarFormatter): ProgressBar = new ProgressBar(UnknownTotal, barFormatter)
  def apply(): ProgressBar = new ProgressBar(UnknownTotal, new BarFormatter())
}
