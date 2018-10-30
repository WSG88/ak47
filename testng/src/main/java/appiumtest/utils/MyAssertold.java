package appiumtest.utils;

import io.appium.java_client.android.AndroidDriver;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

/**
 * 这个类主要二次封装testng中的asserttrue方法
 * aserttrue（false）时需要截图，为true时则直接调用testng的asserttrue（true）
 */
public class MyAssertold {

  public static void asserttrue(AndroidDriver dr, boolean b) {
    if (b == false) {
      ShotScreen(dr);
      Assert.assertTrue(false);
    } else {
      Assert.assertTrue(true);
    }
  }

  //assertequal重新封装一下加了个截图
  public static void assertequal(AndroidDriver dr, String expected, String actual) {
    if (actual.equals(expected)) {
      Assert.assertEquals(expected, actual);
    } else {
      ShotScreen(dr);
      Assert.assertEquals(expected, actual);
    }
  }

  //assertequal方法重载一下
  public static void assertequal(AndroidDriver dr, int expected, int actual) {
    if (actual == expected) {
      Assert.assertEquals(actual, expected);
    } else {
      ShotScreen(dr);
      Assert.assertEquals(actual, expected);
    }
  }

  //截图方法
  private static void ShotScreen(AndroidDriver driver) {
    Date d = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-sss");
    String time = sdf.format(d);
    File screen = ((RemoteWebDriver) driver).getScreenshotAs(OutputType.FILE);
    File screenFile = new File("D:/screenshot/" + time + ".jpg");
    try {
      FileUtils.copyFile(screen, screenFile);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
