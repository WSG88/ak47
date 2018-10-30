package appiumtest.utils;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UsualClass {
  // 日志对象
  static Logger log = LoggerFactory.getLogger("UsualClass");

  public static void cleartext(AndroidDriver dr, String text) {
    // 获取文案的长度
    int length = text.length();
    for (int i = 0; i < length; i++) {
      dr.pressKeyCode(67);
    }
  }

  public static void back(final AndroidDriver dr) {

    AndroidDriverWait wait = new AndroidDriverWait(dr, 60);

    WebElement homepagebutton = wait.until(new ExpectedCondition<WebElement>() {
      @Override public WebElement apply(WebDriver d) {
        WebElement button = null;
        button = HomePage.Location(dr, "homepagebutton");
        if (button == null) {
          log.info("没找到首页按钮执行返回中");
          dr.pressKeyCode(4);
          return HomePage.Location(dr, "homepagebutton");
        } else {
          return button;
        }
      }
    });

    log.info("已返回至根页面");
  }
}
