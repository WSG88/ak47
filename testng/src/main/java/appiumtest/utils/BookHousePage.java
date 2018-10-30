package appiumtest.utils;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 这个页面是房源详情页，点击预约按钮页面跳转至“帮我预约”页面
 */
public class BookHousePage {

  static Logger log = LoggerFactory.getLogger("BookHousePage");

  public static WebElement Location(AndroidDriver dr, String item) {
    try {
      switch (item) {
        //预约页面的标题（点击预约按钮之后页面跳转至预约）
        case "bookpagetitle":
          WebElement bookpagetitle = dr.findElement(By.id("com.loulifang.house:id/title"));
          return bookpagetitle;
        //出租类型
        case "renttype":
          WebElement renttype = dr.findElement(By.id("com.loulifang.house:id/romTypeText"));
          return renttype;
        //小区以及居室
        case "roomdes":
          WebElement roomdes = dr.findElement(By.id("com.loulifang.house:id/roomDes"));
          return roomdes;
        //房东姓名
        case "houseownerName":
          WebElement houseownerName = dr.findElement(By.id("com.loulifang.house:id/tv_agent_name"));
          return houseownerName;
        //房源价格
        case "price":
          WebElement price = dr.findElement(By.id("com.loulifang.house:id/price"));
          return price;
        //预约日期选择器
        case "datechoose":
          WebElement datechoose = dr.findElement(By.id("android:id/numberpicker_input"));
          return datechoose;
        //预约时间选择器
        case "timechoose":
          WebElement timechoose = dr.findElement(By.id("android:id/numberpicker_input"));
          return timechoose;
        //确认预约按钮
        case "confirmbook":
          WebElement confirmbook =
              dr.findElement(By.id("com.loulifang.house:id/tvAppointmentSure"));
          return confirmbook;
        default:
          return null;
      }
    } catch (Exception e) {
      //			for(StackTraceElement err:e.getStackTrace()) {
      //				log.error(err.toString());
      //			}
      try {
        Thread.sleep(2000);
      } catch (InterruptedException e1) {
        //
        e1.printStackTrace();
      }
      log.info("等待" + item);
      return null;
    }
  }

  public static List<AndroidElement> LocationList(AndroidDriver dr) {
    List<AndroidElement> datechoose = new ArrayList<>();
    try {
      datechoose = dr.findElements(By.id("android:id/numberpicker_input"));
      return datechoose;
    } catch (Exception e) {
      System.out.println("时间选择器没找到");
      return null;
    }
  }
}
