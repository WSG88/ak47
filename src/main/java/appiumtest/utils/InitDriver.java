package appiumtest.utils;

import io.appium.java_client.android.AndroidDriver;
import java.net.URL;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class InitDriver {
  public static AndroidDriver dr = null;
  public static AndroidDriverWait wait = null;

  @Parameters({
      "platformName", "platformVersion", "deviceName", "udid", "port", "appPackage", "appActivity",
      "appWaitActivity"
  }) @Test
  public void init(String platformName, String platformVersion, String deviceName, String udid,
      int port, String appPackage, String appActivity, String appWaitActivity) throws Exception {

    DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
    desiredCapabilities.setCapability("platformName", platformName);
    desiredCapabilities.setCapability("platformVersion", platformVersion);
    desiredCapabilities.setCapability("deviceName", deviceName);
    desiredCapabilities.setCapability("udid", udid);

    desiredCapabilities.setCapability("appPackage", appPackage);
    desiredCapabilities.setCapability("appActivity", appActivity);
    desiredCapabilities.setCapability("appWaitActivity", appWaitActivity);

    desiredCapabilities.setCapability("unicodeKeyboard", "True");
    desiredCapabilities.setCapability("resetKeyboard", "True");
    // 每次启动时覆盖session，否则第二次后运行会报错不能新建session
    //desiredCapabilities.setCapability("sessionOverride", true);
    desiredCapabilities.setCapability("noReset", true);
    //desiredCapabilities.setCapability("automationName", "UIAutomator2");

    dr = new AndroidDriver(new URL("http://127.0.0.1:" + port + "/wd/hub"), desiredCapabilities);

    wait = new AndroidDriverWait(dr, 30);

    LogUtil.info("初始化driver成功");
  }

  @AfterTest public void afterTest() {
    LogUtil.info("afterTest");
  }
}