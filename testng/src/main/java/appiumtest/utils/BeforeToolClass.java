package appiumtest.utils;

import io.appium.java_client.android.AndroidDriver;
import java.io.File;
import java.net.URL;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Parameters;

@Parameters({ "DeviceName", "UDID", "System_version", "port" }) public class BeforeToolClass {
  public static AndroidDriver driver;
  // 日志对象
  static Logger log = LoggerFactory.getLogger("BeforeToolClass");

  // 启动app
  public static AndroidDriver AppConfigure(String DeviceName, String UDID, String System_version,
      int port) {

    DesiredCapabilities cap = new DesiredCapabilities();
    cap.setCapability("platformName", "Android");
    cap.setCapability("deviceName", DeviceName);
    cap.setCapability("udid", UDID);
    cap.setCapability("platformVersion", System_version);
    // 将上面获取到的包名和Activity名设置为值
    cap.setCapability("appPackage", "com.loulifang.house");
    cap.setCapability("appActivity", "com.loulifang.house.activities.WelcomeActivity");
    cap.setCapability("appWaitActivity", "com.loulifang.house.activities.WelcomeActivity");
    // 初始化键盘（搞成appium的键盘）
    cap.setCapability("unicodeKeyboard", "True");
    cap.setCapability("resetKeyboard", "True");
    // 每次启动时覆盖session，否则第二次后运行会报错不能新建session
    cap.setCapability("sessionOverride", true);
    cap.setCapability("noReset", true);
    // 安卓7.0
    cap.setCapability("automationName", "UIAutomator2");
    // if(Integer.valueOf(System_version.substring(0, 1))>=7) {
    // cap.setCapability("automationName", "UIAutomator2");
    // }
    // //连接苹果机
    //// driver = new AndroidDriver(new URL("http://192.168.20.23:"+port+"/wd/hub"), cap);

    //按项目制定目录下按照apk包
    try {
      File subjectdirectory = new File("");// 设定为当前文件夹
      File fileapp = new File(subjectdirectory.getAbsolutePath() + "\\hizhu.apk");
      Process process = null;
      if (fileapp.exists()) {
        process = Runtime.getRuntime()
            .exec("cmd.exe /k adb install " + subjectdirectory.getAbsolutePath() + "\\hizhu.apk");
        while (true) {
          try {
            driver = new AndroidDriver(new URL("http://127.0.0.1:" + port + "/wd/hub"), cap);
            if (driver.isAppInstalled("com.loulifang.house")) {
              log.info("成功安装");
              break;
            } else {
              log.info("未安装");
              Thread.sleep(1000);
            }
          } catch (Exception e) {
            log.info("driver为空值，正在安装apk，请稍等");
            Thread.sleep(3000);
          }
        }
      } else {
        log.info("apk文件不存在");
        log.info("查询apk的路径是：" + subjectdirectory.getAbsolutePath() + "\\hizhu.apk");
        return null;
      }
    } catch (Exception e) {

    }
    return driver;
  }
}
