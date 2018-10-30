package appiumtest.utils;

import io.appium.java_client.android.AndroidDriver;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 点击我的-我发布的房源-发房进入 发布房源页面,
 *
 * @author Administrator
 */
public class ReleaseHousePage {
  // 日志对象
  static Logger log = LoggerFactory.getLogger("ReleaseHousePage");

  // 从相机与从相册中上传
  static List<WebElement> submintypes;

  // 首页元素定位
  public static WebElement Location(AndroidDriver dr, String item) {
    try {
      // 按照传过来的item参数给予return不同的元素
      switch (item) {
        // 房源类型页面定位-整租
        case "wholerentbtn":
          WebElement wholerentbtn = dr.findElement(By.id("com.loulifang.house:id/rl_publish_zz"));
          return wholerentbtn;
        // 房源类型页面定位-合租
        case "Jointrentbtn":
          WebElement Jointrentbtn = dr.findElement(By.id("com.loulifang.house:id/rl_publish_hz"));
          return Jointrentbtn;
        // 房源类型页面定位-非转租
        case "NoSubleasebtn":
          WebElement NoSubleasebtn =
              dr.findElement(By.id("com.loulifang.house:id/llt_publish_zzf"));
          return NoSubleasebtn;
        // 房源类型页面定位-转租
        case "Subleasebtn":
          WebElement Subleasebtn = dr.findElement(By.id("com.loulifang.house:id/llt_publish_zzs"));
          return Subleasebtn;
        // 房源类型页面定位-下一步
        case "nextbtn":
          WebElement nextbtn = dr.findElement(By.id("com.loulifang.house:id/btn_publish_next"));
          return nextbtn;
        // 发布房源页面的标题
        case "title":
          WebElement title = dr.findElement(By.id("com.loulifang.house:id/title"));
          return title;
        // 发布房源页面的租住类型
        case "renttype":
          WebElement renttype =
              dr.findElement(By.id("com.loulifang.house:id/room_release_rent_type_text"));
          return renttype;
        // 上传图片按钮
        case "submitpicbtn":
          WebElement submitpicbtn = dr.findElement(By.id("com.loulifang.house:id/imageView"));
          return submitpicbtn;
        // 以相机形式上传
        case "camerasubmitpic":
          submintypes = dr.findElements(By.id("android:id/text1"));
          if (submintypes.size() != 0) {
            return submintypes.get(0);
          } else {
            log.info("没有找到上传图片的方式");
          }
          // 以相册的形式上传
        case "Albumsubmitpic":
          submintypes = dr.findElements(By.id("android:id/text1"));
          if (submintypes.size() != 0) {
            return submintypes.get(1);
          } else {
            log.info("没有找到上传图片的方式");
          }
          // 拍照完点击确定按钮（用于选取相机拍摄的图片,在拍照页面）
        case "confirmpic":
          WebElement confirmpic = dr.findElement(By.id("com.sec.android.app.camera:id/okay"));
          return confirmpic;
        //发房小区搜索
        case "quarters":
          WebElement quarters =
              dr.findElement(By.id("com.loulifang.house:id/room_release_estate_select"));
          return quarters;
        //搜索小区后的小区显示（例如：上海.测试小区）
        case "quarterstextview":
          WebElement quartersText =
              dr.findElement(By.id("com.loulifang.house:id/room_release_estate_name"));
          return quartersText;
        //这个定位是发布房源-小区，在搜索小区页面存在一个城市下拉
        case "citychoose":
          WebElement citychoose = dr.findElement(By.id("com.loulifang.house:id/tvCityBtn"));
          return citychoose;
        //楼层选择
        case "floorbtn":
          WebElement floorbtn =
              dr.findElement(By.id("com.loulifang.house:id/room_release_floor_select"));
          return floorbtn;
        //楼层选择后的文案
        case "floortextview":
          WebElement floortextview =
              dr.findElement(By.id("com.loulifang.house:id/room_release_floor_name"));
          return floortextview;
        //房屋户型
        case "housetypebtn":
          WebElement housetypebtn =
              dr.findElement(By.id("com.loulifang.house:id/room_release_house_type_select"));
          return housetypebtn;
        //户型选择后的文案
        case "housetypetextview":
          WebElement housetypetextview =
              dr.findElement(By.id("com.loulifang.house:id/room_release_house_type_name"));
          return housetypetextview;
        //户型选择完之后点击确定按钮（在楼层页面）
        case "confirmbtn":
          WebElement confirmbtn = dr.findElement(By.name("确定"));
          return confirmbtn;
        default:
          return null;
      }
    } catch (NoSuchElementException e) {
      log.info("等待" + item);
      return null;
    }
  }

  // 获取上传的图片成员（主要用于相机拍摄以及相册选取图片后回到发布房源页面，用于定位添加图片按钮）
  public static List<WebElement> Locationpiclist(AndroidDriver dr) {
    try {

      // 这个主要定位的是发布房源已经上传了多少个图（每次相册以及拍照结束后都需要拿一下）
      WebElement housepicnumber = dr.findElement(By.id("com.loulifang.house:id/imageView"));
      List<WebElement> housepicnumbers = dr.findElements(By.id("com.loulifang.house:id/imageView"));
      return housepicnumbers;
    } catch (NoSuchElementException e) {
      log.info("等待housepicnumbers图片成员");
      return null;
    }
  }

  /**
   * @param dr
   * @param wait
   * @param piccount这个参数只已经上传的图片个数，用于定位添加图片按钮
   */
  // 相机拍照并上传（仅用于发布房源上传图片）
  public static void camerasubmitpictrue(final AndroidDriver dr, AndroidDriverWait wait,
      int piccount) {
    // 获取已经上传的图片数，用于点击图片添加按钮
    List<WebElement> currentpicnumber = Locationpiclist(dr);
    // 点击最后一个才是添加按钮
    currentpicnumber.get(currentpicnumber.size() - 1).click();
    ReleaseHousePage.Location(dr, "camerasubmitpic").click();
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      //
      e.printStackTrace();
    }
    // 进行拍照
    dr.pressKeyCode(27);
    // 拍照完图片选取
    WebElement confirmpic = wait.until(new ExpectedCondition<WebElement>() {
      @Override public WebElement apply(WebDriver d) {
        return ReleaseHousePage.Location(dr, "confirmpic");
      }
    });
    confirmpic.click();
  }

  // 相机选择图片上传，返回一个上传的图片数（仅用于发布房源上传图片）
  public static int Albumsubmitpictrue(final AndroidDriver dr, AndroidDriverWait wait,
      int piccount) {
    // 获取已经上传的图片数，用于点击图片添加按钮
    List<WebElement> currentpicnumber = Locationpiclist(dr);
    // 点击最后一个才是添加按钮
    currentpicnumber.get(currentpicnumber.size() - 1).click();
    //点击从相册中选取
    ReleaseHousePage.Location(dr, "Albumsubmitpic").click();
    // 打开相册选择图片
    dr.findElement(By.id("com.loulifang.house:id/image_view_album_image")).click();
    // 获取相册中所有的图片
    List<WebElement> piclist =
        dr.findElements(By.id("com.loulifang.house:id/image_view_image_select"));
    // 随机从相册中选择5个
    int i = 1;
    if (piclist.size() > 0) {
      for (i = 1; i <= 5; i++) {
        piclist.get(i - 1).click();
      }
      log.info("从相册中能拿到" + piclist.size() + "张图片");
    } else {
      log.info("相册中没有图片");
    }
    dr.findElement(By.id("com.loulifang.house:id/menu_item_add_image")).click();
    return i;
  }
}
