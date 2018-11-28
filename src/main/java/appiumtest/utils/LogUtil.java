package appiumtest.utils;

import org.testng.Reporter;

/**
 * Created by wsig on 2018-10-31.
 */
public class LogUtil {

  public static void info(String message) {
    System.out.println(message);
    Reporter.log(message);
  }

  public static void warn(String message) {
    System.out.println(message);
    Reporter.log(message);
  }

  public static void error(String message) {
    System.out.println(message);
    Reporter.log(message);
  }

  public static void fatal(String message) {
    System.out.println(message);
    Reporter.log(message);
  }

  public static void debug(String message) {
    System.out.println(message);
    Reporter.log(message);
  }
}
