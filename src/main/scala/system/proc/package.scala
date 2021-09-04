/*
 * Copyright (c) 2019.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package system
import scala.sys.process.{Process, ProcessLogger}
import scala.util.Properties

/** package
  *
  * @author
  *   zhaihao
  * @version 1.0
  * @since 2019-03-29
  *   16:32
  */
package object proc {
  private val sep       = Properties.propOrEmpty("file.separator")
  private val classpath = Properties.propOrEmpty("java.class.path")
  private val path      = Properties.propOrEmpty("java.home") + sep + "bin" + sep + "java"

  def newProcess(clz: Class[_])(out: ProcessLogger): Process = {
    // only for scala object
    val className      = clz.getName.dropRight(1)
    val processBuilder = Process(Seq(path, "-cp", classpath, className))
    processBuilder.run(out)
  }

  def pid(process: Process): Int = {
    val procField = process.getClass.getDeclaredField("p")
    procField.synchronized {
      procField.setAccessible(true)
      val proc = procField.get(process)
      try {
        proc match {
          case unixProc if unixProc.getClass.getName == "java.lang.UNIXProcess" =>
            val pidField = unixProc.getClass.getDeclaredField("pid")
            pidField.synchronized {
              pidField.setAccessible(true)
              try {
                pidField.getInt(unixProc)
              } finally {
                pidField.setAccessible(false)
              }
            }
          // If someone wants to add support for Windows processes,
          // this would be the right place to do it:
          case _ => throw new RuntimeException("Cannot get PID of a " + proc.getClass.getName)
        }
      } finally {
        procField.setAccessible(false)
      }
    }
  }
}
