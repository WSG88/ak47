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
import org.testng.Assert;

public class AssertionUtil {

  public static boolean flag = true;

  public static List<Error> errors = new ArrayList<Error>();

  public static void AssertEquals(AndroidDriver driver, Object actual, Object expected,
      String message) {
    try {
      Assert.assertEquals(actual, expected, message);
    } catch (Error e) {
      ShotScreen(driver, message);
      errors.add(e);
      flag = false;
    }
  }

  public static void AssertTrue(AndroidDriver driver, boolean b, String message) {
    try {
      Assert.assertTrue(b, message);
    } catch (Error e) {
      ShotScreen(driver, message);
      errors.add(e);
      flag = false;
    }
  }

  static void ShotScreen(AndroidDriver driver, String err_message) {
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-sss");
    String time = sdf.format(date);
    File screen = (driver).getScreenshotAs(OutputType.FILE);
    File screenFile = new File("screenshot/" + time + err_message + ".jpg");
    try {
      FileUtils.copyFile(screen, screenFile);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

