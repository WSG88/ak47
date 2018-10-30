package appiumtest.utils;

import io.appium.java_client.android.AndroidDriver;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

public class MyAssertion {

  public static boolean flag = true;

  public static List<Error> errors = new ArrayList<Error>();

  public static void AssertEquals(AndroidDriver driver, Object actual, Object expected) {
    try {
      Assert.assertEquals(actual, expected);
    } catch (Error e) {
      //截图
      ShotScreen(driver);
      //如果断言失败，则把异常进行存储在errorslist中
      errors.add(e);
      flag = false;
    }
  }

  public static void AssertEquals(AndroidDriver driver, Object actual, Object expected,
      String message) {
    try {
      Assert.assertEquals(actual, expected, message);
    } catch (Error e) {
      //截图
      ShotScreen(driver, message);
      errors.add(e);
      flag = false;
    }
  }

  public static void AssertTrue(AndroidDriver driver, boolean b) {
    try {
      Assert.assertTrue(b);
    } catch (Error e) {
      //截图
      ShotScreen(driver);
      errors.add(e);
      flag = false;
    }
  }

  //带自己错误信息的
  public static void AssertTrue(AndroidDriver driver, boolean b, String message) {
    try {
      Assert.assertTrue(b, message);
    } catch (Error e) {
      //截图
      ShotScreen(driver, message);
      errors.add(e);
      flag = false;
    }
  }

  //截图方法
  public static void ShotScreen(AndroidDriver driver) {
    Date d = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-sss");
    String time = sdf.format(d);
    File screen = ((RemoteWebDriver) driver).getScreenshotAs(OutputType.FILE);
    //			        File screenFile = new File("D:/JAVA1/com.loulifang.house/screenshot/"+time+".jpg");
    File screenFile = new File("screenshot/" + time + ".jpg");
    try {
      FileUtils.copyFile(screen, screenFile);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  //截图方法（带错误信息作为文件名）
  public static void ShotScreen(AndroidDriver driver, String err_message) {
    Date d = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-sss");
    String time = sdf.format(d);
    File screen = ((RemoteWebDriver) driver).getScreenshotAs(OutputType.FILE);
    //				        File screenFile = new File("D:/JAVA1/com.loulifang.house/screenshot/"+time+err_message+".jpg");
    File screenFile = new File("screenshot/" + time + err_message + ".jpg");
    try {
      FileUtils.copyFile(screen, screenFile);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

