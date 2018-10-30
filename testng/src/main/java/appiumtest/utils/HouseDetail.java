package appiumtest.utils;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 房源详情页的工具类
 */
public class HouseDetail {

  //日志对象
  static Logger log = LoggerFactory.getLogger("HouseDetail");

  public static WebElement Location(AndroidDriver dr, String item) {
    try {
      switch (item) {
        //这块address包括区域-版块-地址
        case "address":
          WebElement address = dr.findElement(By.id("com.loulifang.house:id/addressText"));
          return address;
        //返回按钮
        case "back":
          WebElement back = dr.findElement(By.id("com.loulifang.house:id/back0"));
          return back;
        case "housemessage":
          WebElement housemessage = dr.findElement(By.id("com.loulifang.house:id/roomDesText"));
          return housemessage;
        //价格
        case "price":
          WebElement price = dr.findElement(By.id("com.loulifang.house:id/priceText"));
          return price;
        //房间编号
        case "RoomNo":
          WebElement RoomNo = dr.findElement(By.id("com.loulifang.house:id/tv_desc_id_txt"));
          return RoomNo;
        //底部收藏按钮
        case "collectbutton":
          WebElement collectbutton =
              dr.findElement(By.id("com.loulifang.house:id/llt_btm_btn_collect_btn"));
          return collectbutton;
        //成功收藏房源后会弹出我收藏的房源
        case "mycollecthouse":
          WebElement mycollecthouse =
              dr.findElement(By.id("com.loulifang.house:id/tv_detail_btm_collected_tip"));
          return mycollecthouse;
        //房源详情预约按钮
        case "bookhousebtn":
          WebElement bookhousebtn =
              dr.findElement(By.id("com.loulifang.house:id/llt_btm_btn_appoint_btn"));
          return bookhousebtn;
        //房东姓名
        case "houseownerName":
          WebElement houseownerName = dr.findElement(By.id("com.loulifang.house:id/tv_land_name"));
          return houseownerName;
        //重复预约的弹窗提示以及按钮
        case "rebookmessage":
          WebElement rebookmessage = dr.findElement(By.id("android:id/message"));
          return rebookmessage;
        case "rebookbtn":
          WebElement rebookbtn = dr.findElement(By.id("android:id/button1"));
          return rebookbtn;
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
}
