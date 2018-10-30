package appiumtest.utils;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 我的-点击预约清单按钮-预约清单页面
 *
 * @author Administrator
 */
public class BookhouseListPage {
  // 日志对象
  static Logger log = LoggerFactory.getLogger("BookhouseListPage");

  public static WebElement Location(AndroidDriver dr, String item) {
    try {
      switch (item) {
        //取消预约按钮
        case "cancelbook":
          WebElement cancelbook = dr.findElement(By.id("com.loulifang.house:id/tvStateBtn"));
          return cancelbook;
        //返回按钮
        case "back":
          WebElement back = dr.findElement(By.id("com.loulifang.house:id/back"));
          return back;
        //预约时间（这块存在一个标题，需要字符串substring一下）
        case "bookhousetime":
          WebElement bookhousetime =
              dr.findElement(By.id("com.loulifang.house:id/tvTimeReasonText"));
          return bookhousetime;
        default:
          return null;
      }
    } catch (NoSuchElementException e) {
      log.info("等待" + item);
      return null;
    }
  }
}
