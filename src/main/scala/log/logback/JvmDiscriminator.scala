package log.logback

import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.sift.AbstractDiscriminator
import sun.jvmstat.monitor.{MonitoredHost, MonitoredVmUtil, VmIdentifier}

/**
  * JvmDiscriminator
  *
  * @author zhaihao
  * @version 1.0
  * @since 2020/12/11 5:21 下午
  */
class JvmDiscriminator extends AbstractDiscriminator[ILoggingEvent] {
  var jvmName: String = _
  val key: String = "jvmName"
  var defaultValue: String = _

  override def start() = {
    val pid = _root_.system.pid.toInt
    jvmName = JvmDiscriminator.jvmName(pid).getOrElse(defaultValue)
  }

  override def getDiscriminatingValue(e: ILoggingEvent) = jvmName

  override def getKey = key

  def setDefaultValue(defaultValue: String) = this.defaultValue = defaultValue

  def getDefaultValue = defaultValue
}

object JvmDiscriminator {
  val regex = """.*org\.scalatest\.tools\.Runner -s .+(Jvm\d+).*""".r

  def jvmName(pid: Int) = {
    val vmID    = new VmIdentifier("//" + pid + "?mode=r")
    val mh      = MonitoredHost.getMonitoredHost("localhost")
    val vm      = mh.getMonitoredVm(vmID)
    val command = MonitoredVmUtil.commandLine(vm)
    regex.findFirstIn(command)
  }
}
