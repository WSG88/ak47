package appiumtest.utils;

import io.appium.java_client.android.AndroidDriver;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 房源列表页（搜索结果页）的定位
 * 由于findelements定位不到元素不报错，导致了显示等待不起作用
 *
 * @author Administrator
 */
public class SearchResult {

  // 日志对象
  static Logger log = LoggerFactory.getLogger("SearchResult");

  public static List<WebElement> LocationList(AndroidDriver dr, String item) {
    try {
      switch (item) {
        //房源下的地铁线地铁站（这块当搜索地标时显示的是距离多少，通用元素）
        case "railway":
          //先使用findElement判定元素是否存在（用于显示等待）
          WebElement Single_railway =
              dr.findElement(By.id("com.loulifang.house:id/roomAddressText"));
          List<WebElement> railway =
              dr.findElements(By.id("com.loulifang.house:id/roomAddressText"));
          return railway;
        //tag标签（比如青客、付一压一）
        case "tags":
          WebElement Single_tags = dr.findElement(By.id("com.loulifang.house:id/tv_tag"));
          List<WebElement> tags = dr.findElements(By.id("com.loulifang.house:id/tv_tag"));
          return tags;
        //这块返回的小区名称+几居室（这块是一个元素，后续用的时候需要分割提取一下）
        case "community":
          WebElement Single_community = dr.findElement(By.id("com.loulifang.house:id/roomDes"));
          List<WebElement> community = dr.findElements(By.id("com.loulifang.house:id/roomDes"));
          return community;
        //返回当前屏幕显示的所有房源(这个定位包括搜索结果页以及我收藏的房源的房源列表)
        case "houseitem":
          WebElement Single_houseitem = dr.findElement(By.id("com.loulifang.house:id/llItemHouse"));
          List<WebElement> houseitem = dr.findElements(By.id("com.loulifang.house:id/llItemHouse"));
          return houseitem;
        //出租类型（整租合租）
        case "renttype":
          WebElement Single_renttype = dr.findElement(By.id("com.loulifang.house:id/romTypeText"));
          List<WebElement> renttype = dr.findElements(By.id("com.loulifang.house:id/romTypeText"));
          return renttype;
        //搜索结果页区域筛选列表（包括：不限、附近、商圈、地铁）
        case "RegionList":
          WebElement Single_region = dr.findElement(By.id("com.loulifang.house:id/textView"));
          List<WebElement> RegionList = dr.findElements(By.id("com.loulifang.house:id/textView"));
          return RegionList;
        //地铁站筛选下拉列表（在筛选区域选择地铁-地铁线后，地铁站的列表）
        case "railwayStation":
          WebElement Single_railwayStation = dr.findElement(By.id("com.loulifang.house:id/name"));
          List<WebElement> railwayStation = dr.findElements(By.id("com.loulifang.house:id/name"));
          return railwayStation;

        default:
          return null;
      }
    } catch (Exception e) {
      log.info("等待" + item);
      return null;
    }
  }

  public static WebElement Location(AndroidDriver dr, String item) {
    try {
      switch (item) {
        //这个只搜索结果页的搜索输入框
        case "searchText":
          WebElement searchText = dr.findElement(By.id("com.loulifang.house:id/searchText"));
          return searchText;
        //搜索结果页返回键
        case "searchback":
          WebElement searchback = dr.findElement(By.id("com.loulifang.house:id/searchBack"));
          return searchback;
        //区域
        case "Region":
          WebElement Region = dr.findElement(By.id("com.loulifang.house:id/qyText"));
          return Region;
        //租金
        case "rentmoney":
          WebElement rentmoney = dr.findElement(By.id("com.loulifang.house:id/moneyText"));
          return rentmoney;
        //排序
        case "sort":
          WebElement sort = dr.findElement(By.id("com.loulifang.house:id/mjText"));
          return sort;
        //更多筛选
        case "more":
          WebElement more = dr.findElement(By.id("com.loulifang.house:id/moreText"));
          return more;
        default:
          return null;
      }
    } catch (NoSuchElementException e) {
      log.info("等待" + item);
      return null;
    }
  }
  //	public static List<WebElement> ScreenLocationList(AndroidDriver dr, String item) {
  //
  //		return null;
  //	}
}
