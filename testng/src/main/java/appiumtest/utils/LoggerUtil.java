package appiumtest.utils;

import java.io.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Reporter;

/**
 * Gjp日志工具类 {logger name 值为 [className].[methodName]() Line: [fileLine]} <br/>
 * 若要自定义可配置打印出执行的方法名和执行行号位置等信息，请参考RequestLoggerLogger.java<br/>
 *
 * @author Administrator
 */
public class LoggerUtil {

  /**
   * 获取最原始被调用的堆栈信息
   */

  public static StackTraceElement findCaller() {
    // 获取堆栈信息
    StackTraceElement[] callStack = Thread.currentThread().getStackTrace();
    if (null == callStack) return null;

    // 最原始被调用的堆栈信息
    StackTraceElement caller = null;
    // 日志类名称
    String logClassName = LoggerUtil.class.getName();
    // 循环遍历到日志类标识
    boolean isEachLogClass = false;

    // 遍历堆栈信息，获取出最原始被调用的方法信息
    for (StackTraceElement s : callStack) {
      // 遍历到日志类
      if (logClassName.equals(s.getClassName())) {
        isEachLogClass = true;
      }
      // 下一个非日志类的堆栈，就是最原始被调用的方法
      if (isEachLogClass) {
        if (!logClassName.equals(s.getClassName())) {
          isEachLogClass = false;
          caller = s;
          break;
        }
      }
    }

    return caller;
  }

  /**
   * 自动匹配请求类名，生成logger对象，此处 logger name 值为 [className].[methodName]() Line:
   * [fileLine]
   *
   * @author Administrator
   */
  private static Logger logger() {
    // 最原始被调用的堆栈对象
    StackTraceElement caller = findCaller();
    Logger log = null;

    if (null == caller) {
      log = LoggerFactory.getLogger(LoggerUtil.class);
    } else {
      log = LoggerFactory.getLogger(caller.getClassName()
          + "."
          + caller.getMethodName()
          + "() Line: "
          + caller.getLineNumber());
    }
    return log;
  }

  public static void trace(String msg) {
    //    	if(Config.SCREEN_SHOTPATH == 1)
    //    		trace(msg, null);
    //    	else if(Config.SCREEN_SHOTPATH == 2)
    //    		Reporter.log("<span style=\"color:#16A05D\"><H3>[trace] "+msg+"</H3>");
    //    	else if(Config.SCREEN_SHOTPATH == 3){
    trace(msg, null);
    Reporter.log("<span style=\"color:#16A05D\"><H3>[trace] " + msg + "</H3>");
    //    	}
  }

  public static void trace(String msg, Throwable e) {
    logger().trace(msg, e);
  }

  public static void debug(String msg) {
    debug(msg, null);
    Reporter.log("[debug] " + msg);
  }

  public static void debug(String msg, Throwable e) {
    logger().debug(msg, e);
  }

  public static void info(String msg) {
    info(msg, null);
    Reporter.log("[info] " + msg);
  }

  public static void info(String msg, Throwable e) {
    logger().info(msg, e);
  }

  public static void warn(String msg) {
    warn(msg, null);
  }

  public static void warn(String msg, Throwable e) {
    logger().warn(msg, e);
  }

  public static void error(String msg) {
    error(msg, null);
    Reporter.log("[error] " + msg);
  }

  public static void error(String msg, Throwable e) {
    logger().error(msg, e);
  }

  public static void screenShotLog(String comm, File file) {
    int width = 350;
    String absolute = "file:" + file.getAbsolutePath();
    Reporter.log("<a target='_blank' href=\"" + absolute + "\">");
    Reporter.log("<img width=\"" + width + "\" src=\"" + absolute + "\" />    " + comm);
    Reporter.log("</a><br />");
  }

  public static void assertReportFormat(String comm, String actual, String expected,
      String result) {
    String ptmsg;
    ptmsg = "┌───────────────────────┤ＣＨＥＣＫ　ＰＯＩＮＴ├───────────────────────┐<br>";
    ptmsg = ptmsg + "[目标]：" + comm + "<br>";
    ptmsg = ptmsg + "[实际]：" + actual + "　[预期]：" + expected + "<br>";
    if (result.contains("PASS")) {
      ptmsg = ptmsg + "└──────────────────────────────────────────────────────────────────┘";
      ptmsg = "<P style=\"font-size:1.1em;color:#1C9340\"><b>" + ptmsg + "</b></P>";
      Reporter.log(ptmsg);
      LoggerUtil.info("【检查点检查成功】  [实际]：" + actual + "　[预期]：" + expected);
    } else {
      ptmsg = ptmsg + "└──────────────────────────────────────────────────────────────────┘<br />";
      ptmsg = "<P style=\"font-size:1.1em;color:#ED1C24\"><b>" + ptmsg + "</b></P>";
      Reporter.log(ptmsg);
      LoggerUtil.info("【检查点检查失败】  [实际]：" + actual + "　[预期]：" + expected);
    }
  }
}