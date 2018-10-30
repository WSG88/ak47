package appiumtest.utils;

import io.appium.java_client.android.AndroidDriver;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Administrator
 */
public class HomePage {

  //日志对象
  static Logger log = LoggerFactory.getLogger("HomePageTest");

  // 首页元素定位
  public static WebElement Location(AndroidDriver dr, String item) {
    try {
      // 按照传过来的item参数给予return不同的元素
      switch (item) {
        // 定位底部首页按钮
        case "homepagebutton":
          WebElement homepagebutton = dr.findElement(By.id("com.loulifang.house:id/llfLyt"));
          return homepagebutton;
        // 城市按钮
        case "city":
          WebElement city = dr.findElement(By.id("com.loulifang.house:id/tv_home_city_name"));
          return city;
        // 搜索框
        case "Searchbox":
          WebElement Searchbox =
              dr.findElement(By.id("com.loulifang.house:id/tv_home_search_hint"));
          return Searchbox;
        default:
          return null;
      }
    } catch (NoSuchElementException e) {
      log.info("等待" + item);
      return null;
    }
  }

  public static List<WebElement> Locationlist(AndroidDriver dr, String item) {
    try {
      // 首页配置按钮定位
      switch (item) {
        case "homepagebutton":
          List<WebElement> homepagebutton =
              dr.findElements(By.id("com.loulifang.house:id/ivTabHome"));
          return homepagebutton;
        //首页配置按钮文案
        case "homepagebuttontext":
          List<WebElement> homepagebuttontext =
              dr.findElements(By.id("com.loulifang.house:id/tvTabHome"));
          return homepagebuttontext;
        default:
          return null;
      }
    } catch (NoSuchElementException e) {
      log.info("等待" + item);
      return null;
    }
  }
}
