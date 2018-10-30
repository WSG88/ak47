package appiumtest.utils;

import io.appium.java_client.android.AndroidDriver;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChooseCity {
  // 日志对象
  static Logger log = LoggerFactory.getLogger("ChooseCity");

  public static WebElement Location(AndroidDriver dr, String item) {
    try {
      //获取定位元素
      switch (item) {
        //获取标题元素
        case "title":
          WebElement title = dr.findElement(By.id("com.loulifang.house:id/title"));
          return title;
        //获取当前城市
        case "currentcity":
          WebElement currentcity = dr.findElement(By.id("com.loulifang.house:id/locCityText"));
          return currentcity;
        //获取选中城市的√符号
        case "Selectedsign":
          WebElement Selected = dr.findElement(By.id("com.loulifang.house:id/rightImg"));
          return Selected;
        default:
          return null;
      }
    } catch (NoSuchElementException e) {
      log.info("等待" + item);
      return null;
    }
  }

  //定位所有城市列表
  public static List<WebElement> Allcity(AndroidDriver dr) {
    try {
      List<WebElement> Allcity = new ArrayList<>();
      List<WebElement> list = dr.findElements(By.id("com.loulifang.house:id/textView"));
      for (WebElement webelement : list) {
        Allcity.add(webelement);
      }
      return Allcity;
    } catch (NoSuchElementException e) {
      log.info("等待城市列表");
      return null;
    }
  }
}
