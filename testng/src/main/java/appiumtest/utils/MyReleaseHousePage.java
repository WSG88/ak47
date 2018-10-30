package appiumtest.utils;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 我发布的房源页面（点击我的-我发布的房源按钮）
 *
 * @author Administrator
 */
public class MyReleaseHousePage {
  //日志对象
  static Logger log = LoggerFactory.getLogger("MyReleaseHousePage");

  // 首页元素定位
  public static WebElement Location(AndroidDriver dr, String item) {
    try {
      // 按照传过来的item参数给予return不同的元素
      switch (item) {
        // 发房页面
        case "ReleaseHousebtn":
          WebElement ReleaseHousebtn = dr.findElement(By.name("发房"));
          return ReleaseHousebtn;
        default:
          return null;
      }
    } catch (NoSuchElementException e) {
      log.info("等待" + item);
      return null;
    }
  }
}
