package appiumtest.page;

import appiumtest.BaseConfig;
import appiumtest.utils.AndroidDriverWait;
import appiumtest.utils.AssertionUtil;
import appiumtest.utils.CmdUtil;
import appiumtest.utils.LogUtil;
import appiumtest.utils.UsualClass;
import appiumtest.utils.XlsUtil;
import com.alibaba.fastjson.JSONObject;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.io.IOUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {

  public AndroidDriver<AndroidElement> driver;
  public Map<String, String> stringHashMap = new HashMap<String, String>();

  public BasePage(AndroidDriver<AndroidElement> driver) {
    this.driver = driver;
    stringHashMap = this.getElementMap();
  }

  /**
   * 我的-点击预约清单按钮-预约清单页面
   */
  public static WebElement bookHouseListLocation(AndroidDriver dr, String item) {
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
      LogUtil.info("等待" + item);
      return null;
    }
  }

  public static WebElement bookHouseLocation(AndroidDriver dr, String item) {
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
      e.printStackTrace();
      try {
        Thread.sleep(2000);
      } catch (InterruptedException e1) {
        e1.printStackTrace();
      }
      LogUtil.info("等待" + item);
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

  public static WebElement chooseCityLocation(AndroidDriver dr, String item) {
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
      LogUtil.info("等待" + item);
      return null;
    }
  }

  public static List<WebElement> getAllCity(AndroidDriver dr) {
    try {
      List<WebElement> webElements =
          (List<WebElement>) dr.findElements(By.id("com.loulifang.house:id/textView"));
      return webElements;
    } catch (Exception e) {
      LogUtil.info("等待城市列表");
      return null;
    }
  }

  public static WebElement homeLocation(AndroidDriver dr, String item) {
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
      LogUtil.info("等待" + item);
      return null;
    }
  }

  public static List<WebElement> homeListLocation(AndroidDriver dr, String item) {
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
      LogUtil.info("等待" + item);
      return null;
    }
  }

  public static WebElement houseDetailLocation(AndroidDriver dr, String item) {
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
      e.printStackTrace();
      try {
        Thread.sleep(2000);
      } catch (InterruptedException e1) {
        e1.printStackTrace();
      }
      LogUtil.info("等待" + item);
      return null;
    }
  }

  public static WebElement myReleaseHouseLocation(AndroidDriver dr, String item) {
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
      LogUtil.info("等待" + item);
      return null;
    }
  }

  public static WebElement ownerLocation(AndroidDriver dr, String item) {
    try {
      switch (item) {
        //底部我的按钮定位
        case "owner":
          WebElement owner = dr.findElement(By.id("com.loulifang.house:id/mineLyt"));
          return owner;
        //个人信息定位
        case "Personal_information":
          WebElement Personal_information =
              dr.findElement(By.id("com.loulifang.house:id/profileLyt"));
          return Personal_information;
        //设置
        case "Set_up":
          WebElement Set_up = dr.findElement(By.id("com.loulifang.house:id/mySettingLyt"));
          return Set_up;
        //立即登录按钮（只有在未登录时存在）
        case "loginbutton":
          WebElement loginbutton = dr.findElement(By.id("com.loulifang.house:id/nameText"));
          return loginbutton;
        //我的页面昵称（只在登录的情况）
        case "nickname":
          WebElement nickname = dr.findElement(By.id("com.loulifang.house:id/nameText"));
          return nickname;
        //我的页面手机号（只在登录的情况）
        case "mobile":
          WebElement mobile = dr.findElement(By.id("com.loulifang.house:id/phoneNumberText"));
          return mobile;
        //设置页面的退出登录
        case "loginout":
          WebElement loginout = dr.findElement(By.id("com.loulifang.house:id/quitBtn"));
          return loginout;
        //设置关于按钮
        case "about":
          WebElement about = dr.findElement(By.id("com.loulifang.house:id/aboutBtn"));
          return about;
        //登录页面的用户名
        case "UsernameEdit":
          WebElement UsernameEdit = dr.findElement(By.id("com.loulifang.house:id/userName"));
          return UsernameEdit;
        //登录页面的密码
        case "password":
          WebElement password = dr.findElement(By.id("com.loulifang.house:id/verCode"));
          return password;
        //登录页面的登录按钮
        case "confirmloginbutton":
          WebElement confirmloginbutton = dr.findElement(By.id("com.loulifang.house:id/confirm"));
          return confirmloginbutton;
        //我的-收藏房源
        case "collecthousebtn":
          WebElement collecthousebtn = dr.findElement(By.id("com.loulifang.house:id/myCollectLyt"));
          return collecthousebtn;
        //我的-预约清单按钮
        case "bookhouselistbtn":
          WebElement bookhouselistbtn =
              dr.findElement(By.id("com.loulifang.house:id/myAppointLyt"));
          return bookhouselistbtn;
        //预约清单数
        case "bookhouselistcount":
          WebElement bookhouselistcount =
              dr.findElement(By.id("com.loulifang.house:id/myAppointCnt"));
          return bookhouselistcount;
        //我的-我发布的房源按钮
        case "myReleaseHousebtn":
          WebElement myReleaseHousebtn = dr.findElement(By.id("com.loulifang.house:id/myRoomsLyt"));
          return myReleaseHousebtn;
        default:
          return null;
      }
    } catch (NoSuchElementException e) {
      LogUtil.info("等待" + item);
      return null;
    }
  }

  //登录方法（点击我的页面再登录）
  public static void login(AndroidDriver driver, String mobile, String password) {
    //优化登录方法（使用location的定位去完成，减少耦合度）
    //点击我的按钮
    ownerLocation(driver, "owner").click();
    //点击立即登录
    ownerLocation(driver, "loginbutton").click();
    //输入手机号验证码登录
    ownerLocation(driver, "UsernameEdit").sendKeys(mobile);
    ownerLocation(driver, "password").sendKeys(password);
    ownerLocation(driver, "confirmloginbutton").click();
  }

  //登录方法（用于点击预约收藏时未登录时的情况）
  public static void simplelogin(AndroidDriver driver, String mobile, String password) {
    //输入手机号验证码登录
    ownerLocation(driver, "UsernameEdit").sendKeys(mobile);
    ownerLocation(driver, "password").sendKeys(password);
    ownerLocation(driver, "confirmloginbutton").click();
  }

  //退出登录
  public static void loginout(AndroidDriver dr) {
    AndroidDriverWait wait = new AndroidDriverWait(dr, 60);
    ;
    //点击我的按钮
    ownerLocation(dr, "owner").click();
    //点击设置按钮
    ownerLocation(dr, "Set_up").click();
    WebElement loginout = wait.until(
        ExpectedConditions.presenceOfElementLocated(By.id("com.loulifang.house:id/quitBtn")));
    loginout.click();
    //确定退出
    wait.until(ExpectedConditions.presenceOfElementLocated(By.id("android:id/button1"))).click();
  }

  // 首页元素定位
  public static WebElement releaseHouseLocation(AndroidDriver dr, String item) {
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
          List<WebElement> submintypes1 = dr.findElements(By.id("android:id/text1"));
          if (submintypes1.size() != 0) {
            return submintypes1.get(0);
          } else {
            LogUtil.info("没有找到上传图片的方式");
          }
          // 以相册的形式上传
        case "Albumsubmitpic":
          List<WebElement> submintypes = dr.findElements(By.id("android:id/text1"));
          if (submintypes.size() != 0) {
            return submintypes.get(1);
          } else {
            LogUtil.info("没有找到上传图片的方式");
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
      LogUtil.info("等待" + item);
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
      LogUtil.info("等待housepicnumbers图片成员");
      return null;
    }
  }

  /**
   * 这个参数只已经上传的图片个数，用于定位添加图片按钮
   */
  // 相机拍照并上传（仅用于发布房源上传图片）
  public static void camerasubmitpictrue(final AndroidDriver dr, AndroidDriverWait wait,
      int piccount) {
    // 获取已经上传的图片数，用于点击图片添加按钮
    List<WebElement> currentpicnumber = Locationpiclist(dr);
    // 点击最后一个才是添加按钮
    currentpicnumber.get(currentpicnumber.size() - 1).click();
    releaseHouseLocation(dr, "camerasubmitpic").click();
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
        return releaseHouseLocation(dr, "confirmpic");
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
    releaseHouseLocation(dr, "Albumsubmitpic").click();
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
      LogUtil.info("从相册中能拿到" + piclist.size() + "张图片");
    } else {
      LogUtil.info("相册中没有图片");
    }
    dr.findElement(By.id("com.loulifang.house:id/menu_item_add_image")).click();
    return i;
  }

  // 单个元素定位（例如按钮输入框等）
  public static WebElement searchAssociationLocation(AndroidDriver dr, String item) {
    try {
      switch (item) {
        // 搜索输入框(发布房源搜索小区同首页搜索输入框)
        case "SearchEdit":
          WebElement SearchEdit = dr.findElement(By.id("com.loulifang.house:id/searchEdit"));
          return SearchEdit;
        // 搜索附近房源按钮
        case "Nearhouse":
          WebElement Nearhouse = dr.findElement(By.id("com.loulifang.house:id/llt_loc_bar"));
          return Nearhouse;
        // 取消按钮（可回到首页）
        case "cancelbutton":
          WebElement cancelbutton = dr.findElement(By.id("com.loulifang.house:id/cancelBtn"));
          return cancelbutton;
        default:
          return null;
      }
    } catch (java.util.NoSuchElementException e) {
      LogUtil.info("等待" + item + "元素");
      return null;
    }
  }

  /**
   * 定位所有的head（例如小区商圈区域等） 定位所有的textview(例如：新梅共和城或者地标中的名称等)
   * 定位所有的address（搜索联想页中所有的地图） 考虑是需要拿到元素进行点击，文案的校验就测试类转String
   */
  public static List<WebElement> SearchAssociationListLocation(AndroidDriver dr, String item) {
    try {
      switch (item) {
        // 所有小区商圈等定位
        case "head":
          WebElement single_head = dr.findElement(By.id("com.loulifang.house:id/headNo"));
          List<WebElement> heads = dr.findElements(By.id("com.loulifang.house:id/headNo"));
          return heads;
        // 所有小区名地铁名等定位
        case "textview":
          WebElement single_textview = dr.findElement(By.id("com.loulifang.house:id/textView"));
          List<WebElement> textview = dr.findElements(By.id("com.loulifang.house:id/textView"));
          return textview;
        // 定位所有地址
        case "address":
          WebElement single_address =
              dr.findElement(By.id("com.loulifang.house:id/textViewAddress"));
          List<WebElement> address =
              dr.findElements(By.id("com.loulifang.house:id/textViewAddress"));
          return address;
        default:
          return null;
      }
    } catch (java.util.NoSuchElementException e) {
      LogUtil.info("等待" + item + "元素");
      return null;
    }
  }

  /**
   * 这个方法兼容所有类型的搜索 如果搜索类型是地铁线与小区可以在房源列表中进行断言，如果是行政区以及商圈需要进入房源详情进行断言（这块已经做了相应的校验）
   * 主流程做到点击搜索联想词第一个，然后在搜索结果中开始分支写判定逻辑（分支从显示等待houselist房源列表开始）
   *
   * 搜索内容
   * 搜素类型（地铁线type=1、小区type=2、品牌type=3、行政区type=4、商圈type=5、、地标type=6）
   */
  public static void wholesSearch(final AndroidDriver driver, AndroidDriverWait wait) {
    LogUtil.info("开始测试搜索");
    // 写死一个map用于存储搜索关键字以及搜索类型
    List<XlsUtil.SearchHouseDataObject> SearchData = new ArrayList<>();
    // 拿到excel配置的数据
    SearchData = XlsUtil.readSearchHouseDate();
    if (!SearchData.isEmpty()) {
      LogUtil.info("搜索数据已成功传递到测试类中");
    } else {
      LogUtil.info("搜索数据传递失败");
    }

    // (开始执行搜索操作)等待底部首页按钮
    WebElement homepagebutton = wait.until(new ExpectedCondition<WebElement>() {
      @Override public WebElement apply(WebDriver d) {
        return homeLocation(driver, "homepagebutton");
      }
    });
    // 点击底部首页按钮（确保他在首页）
    homepagebutton.click();
    // 定位搜索输入框
    WebElement Searchbox = homeLocation(driver, "Searchbox");
    // 点击首页输入框
    Searchbox.click();
    // 等待搜索输入框
    WebElement SearchEdit = wait.until(new ExpectedCondition<WebElement>() {
      @Override public WebElement apply(WebDriver arg0) {

        return searchAssociationLocation(driver, "SearchEdit");
      }
    });
    /**
     * 遍历map，进行不同类型的搜索
     */
    //		List<Integer> searchtype = new ArrayList<>();
    //		searchtype = SearchData.keySet();
    for (XlsUtil.SearchHouseDataObject obj : SearchData) {
      //获取搜索类型typeno
      int typeno = obj.getSearchtype();
      // 搜索关键字
      String searchkey = obj.getSearchkeys();
      LogUtil.info("搜索关键字" + searchkey);
      SearchEdit.sendKeys(searchkey);
      // 点击联想页中的第一个
      List<WebElement> textview = new ArrayList<>();
      textview = wait.until(new ExpectedCondition<List<WebElement>>() {
        @Override public List<WebElement> apply(WebDriver dr) {

          return SearchAssociationListLocation(driver, "textview");
        }
      });
      /**
       * 这块存在偶现点击搜索联想页中第一个无响应的问题 尝试解决问题，强行线程停止3秒
       */
      try {
        Thread.sleep(3000);
      } catch (InterruptedException e1) {
        //
        e1.printStackTrace();
      }
      // 点击第一个搜索联想词(确保一下第一个按钮能点击)
      WebElement currentclick = null;
      try {
        currentclick = wait.until(ExpectedConditions.elementToBeClickable(textview.get(0)));
      } catch (Exception e) {
        try {
          Thread.sleep(3000);
        } catch (InterruptedException e1) {
          //
          e1.printStackTrace();
        }
      }
      currentclick.click();
      // 根据搜索的类型做不同的校验
      if (typeno == 1) {
        LogUtil.info("※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※");
        LogUtil.info("搜索地铁线" + searchkey + "号线");
        // 等待搜索结果（拿到railway对象）
        List<WebElement> RailwayList = new ArrayList<>();
        RailwayList = wait.until(new ExpectedCondition<List<WebElement>>() {
          @Override public List<WebElement> apply(WebDriver dr) {

            return SearchResultListLocation(driver, "railway");
          }
        });
        // new一个set对象用于去重
        Set<Integer> railwayset = new HashSet<>();
        // 遍历railwaylist提取地铁线
        String RailwayTostring = null;
        try {
          for (WebElement element : RailwayList) {
            RailwayTostring = element.getText();
            int RailwayNo = Integer.valueOf(
                RailwayTostring.substring(RailwayTostring.indexOf("离") + 1,
                    RailwayTostring.indexOf("号")));
            railwayset.add(RailwayNo);
          }
        } catch (Exception e) {
          e.printStackTrace();
          LogUtil.error("抓取的实际地铁线失败" + RailwayTostring);
          AssertionUtil.AssertTrue(driver, false, "抓取的实际地铁线失败");
        }
        // 断言（当railwayset里面有2个或者2个以上的地铁线，说明就是有问题）
        if (railwayset.size() == 1) {
          int RailwayNo = 0;
          for (int a : railwayset) {
            RailwayNo = a;
          }
          // 期望的地铁线
          int expectedrailway = 0;
          try {
            expectedrailway = Integer.valueOf(obj.getSearchkeys());
          } catch (Exception e) {
            LogUtil.info("读取数据表里面的地铁线信息，只要写数字，无需写号线，实际是" + obj.getSearchkeys());
            continue;
          }

          AssertionUtil.AssertEquals(driver, RailwayNo, expectedrailway, "搜索出来的地铁线与预期的不符");
        } else {
          AssertionUtil.AssertTrue(driver, false, "存在多个地铁线");
        }
        // 返回搜索联想页并清空搜索输入框
        SearchResultLocation(driver, "searchback").click();
        UsualClass.clearEdit(driver, searchAssociationLocation(driver, "SearchEdit").getText());
        // 如果搜索小区
      } else if (typeno == 2) {
        LogUtil.info("※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※");
        // 等待搜索结果（拿到community小区对象）
        LogUtil.info("开始搜索小区:" + searchkey);
        List<WebElement> community = new ArrayList<>();

        community = wait.until(new ExpectedCondition<List<WebElement>>() {
          @Override public List<WebElement> apply(WebDriver dr) {

            return SearchResultListLocation(driver, "community");
          }
        });

        LogUtil.info("定位到的小区个数：" + community.size());
        // 需要处理一下拿到的community的list（将拿到的coounity进行分割一下拿出小区名）
        String[] Actual_Community = new String[community.size()];
        for (int i = 0; i < community.size(); i++) {
          Actual_Community[i] = community.get(i)
              .getText()
              .substring(0, community.get(i).getText().indexOf("·"))
              .trim();
          LogUtil.info("小区：" + Actual_Community[i]);
        }
        // 判定搜索的小区名字是不是都是预期(先判定搜索结果是不是未空)
        if (Actual_Community.length <= 0) {
          LogUtil.error("未搜索到房源,Actual_Community小区数组的长度是" + Actual_Community.length);
          AssertionUtil.AssertTrue(driver, false, "搜索房源无结果");
        } else {
          for (int i = 0; i < Actual_Community.length; i++) {
            AssertionUtil.AssertEquals(driver, Actual_Community[i], searchkey, "搜索出来的地铁线与预期的不符");
          }
        }
        // 返回搜索联想页并青客搜索输入框
        SearchResultLocation(driver, "searchback").click();
        UsualClass.clearEdit(driver, searchAssociationLocation(driver, "SearchEdit").getText());

        // 搜索品牌类型
      } else if (typeno == 3) {
        LogUtil.info("※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※");
        LogUtil.info("开始测试品牌:" + searchkey);
        // 等待搜索结果（拿到taglist对象）
        List<WebElement> taglist = new ArrayList<>();

        taglist = wait.until(new ExpectedCondition<List<WebElement>>() {
          @Override public List<WebElement> apply(WebDriver dr) {

            return SearchResultListLocation(driver, "tags");
          }
        });

        int countbrand = 0;
        // 判断(把搜索的相关品牌个数拿出来)
        if (taglist.size() <= 0) {
          LogUtil.error("没有标签");
        } else {
          for (WebElement tag : taglist) {
            if (tag.getText().equals(searchkey)) {
              countbrand++;
            }
          }
          LogUtil.info("一共发现" + countbrand + "个" + searchkey);
        }
        // 断言：（判断搜索的品牌是不是每个房源都带上）(一屏一般至少显示3个房源)
        AssertionUtil.AssertTrue(driver, countbrand >= 3, "搜索房源其中有一个不是搜索的品牌");
        // 返回搜索联想页并青客搜索输入框
        SearchResultLocation(driver, "searchback").click();
        UsualClass.clearEdit(driver, searchAssociationLocation(driver, "SearchEdit").getText());

        // 当搜索的是区域商圈时，这块都需要点击进入房源详情页去校验
      } else if (typeno == 4 || typeno == 5) {
        if (typeno == 4) {
          LogUtil.info("※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※");
          LogUtil.info("现在开始测试区域" + searchkey);
        } else {
          LogUtil.info("※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※");
          LogUtil.info("现在开始测试版块" + searchkey);
        }
        // 等待房源列表
        List<WebElement> houselist = new ArrayList<>();
        // 拿到houselist房源列表
        houselist = wait.until(new ExpectedCondition<List<WebElement>>() {
          @Override public List<WebElement> apply(WebDriver dr) {

            return SearchResultListLocation(driver, "houseitem");
          }
        });
        int counts = houselist.size();
        LogUtil.info("房源数量——" + counts);
        // 点击第一个房源进入房源详情
        houselist.get(0).click();
        LogUtil.info("点击房源列表中的第一个，进入房源详情,尝试寻找地址元素");
        // 这块存在房源详情页未加载完导致无法滑动情况（这块先尝试拿一下价格再尝试滑动）
        WebElement price = wait.until(new ExpectedCondition<WebElement>() {
          @Override public WebElement apply(WebDriver arg0) {
            return houseDetailLocation(driver, "price");
          }
        });
        // 滑动页面至指定的位置(已实现动态寻找对象)
        WebElement address = wait.until(new ExpectedCondition<WebElement>() {
          @Override public WebElement apply(WebDriver arg0) {

            int width = driver.manage().window().getSize().width;
            int height = driver.manage().window().getSize().height;
            // 这块有个问题，就是还未跳转至房源详情就开始滑动会报错（这块我先拿个价格作为进入房源详情的依据）
            houseDetailLocation(driver, "price");
            // 尝试屏幕往下滑动
            UsualClass.swipe(driver, width / 2, height * 3 / 4, width / 2, height / 2, 500);
            LogUtil.info("scroll=======" + height * 3 / 4 + " " + height / 4);
            return houseDetailLocation(driver, "address");
          }
        });
        if (address != null) {
          LogUtil.info("地址成功拿到:" + address.getText());
        } else {
          LogUtil.error("没拿到房源详情的地址:" + address.getText());
        }
        String[] AddressToString = address.getText().split("-");
        for (int i = 0; i < AddressToString.length; i++) {
          if (i == 0) {
            LogUtil.info("区域:" + AddressToString[0]);
          } else if (i == 1) {
            LogUtil.info("商圈:" + AddressToString[1]);
          } else {
            LogUtil.info("地址:" + AddressToString[2]);
          }
        }
        if (typeno == 4) {
          LogUtil.info("预期的区域：" + searchkey + ",实际的区域：" + AddressToString[0].trim());
          AssertionUtil.AssertEquals(driver, AddressToString[0].trim(), searchkey,
              "房源详情显示的区域与搜索的不符");
        } else if (typeno == 5) {
          LogUtil.info("预期的商圈：" + searchkey + ",实际的商圈：" + AddressToString[1].trim());
          AssertionUtil.AssertEquals(driver, AddressToString[1].trim(), searchkey,
              "房源详情显示的商圈与搜索的不符");
        }
        // 返回搜索结果页
        houseDetailLocation(driver, "back").click();
        // 返回搜索联想页
        SearchResultLocation(driver, "searchback").click();
        // 清空搜索输入框
        UsualClass.clearEdit(driver, searchAssociationLocation(driver, "SearchEdit").getText());
        // 搜索地标
      } else if (typeno == 6) {
        // 现在开始进行测试地标
        LogUtil.info("※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※");
        LogUtil.info("现在开始测试地标" + searchkey);
        // 等待房源列表
        List<WebElement> houselist = new ArrayList<>();
        // 拿到houselist房源列表
        houselist = wait.until(new ExpectedCondition<List<WebElement>>() {
          @Override public List<WebElement> apply(WebDriver dr) {

            return SearchResultListLocation(driver, "houseitem");
          }
        });
        int counts = houselist.size();
        LogUtil.info("房源数量——" + counts);
        // 先判定一下默认的排序
        WebElement sort = SearchResultLocation(driver, "sort");
        AssertionUtil.AssertEquals(driver, sort.getText(), "距离从近到远", "搜索地标默认排序错误");

        // 再去拿一下距离多少米的排序（地铁线与距离是同一个元素）(这块计划往下滑动几屏多拿几个数据，然后最后一个与第一个比)
        // 将抓来的webelememt对象gettext都存入distance里面
        List<String> distance = new ArrayList<>();
        // 将string的距离转为integer，后续做比较
        List<Integer> actualdistance_number = new ArrayList<>();
        // 每次返回抓到的距离元素对象
        List<WebElement> Init_distance = new ArrayList<>();
        for (int j = 0; j < 5; j++) {
          int height = driver.manage().window().getSize().getHeight();
          int weight = driver.manage().window().getSize().getWidth();
          // 这块我就抓第一页与第九页的距离数据（然后最后一个与第一个比较）
          Init_distance = SearchResultListLocation(driver, "railway");
          // 先判定一下是否拿到距离数，判定一下Init_distance数链的大小
          if (Init_distance.size() != 0) {
            for (WebElement WebElement : Init_distance) {
              distance.add(WebElement.getText());
              LogUtil.info("加入距离:" + WebElement.getText());
            }
          } else {
            LogUtil.info("未拿到距离数据");
          }
          // 往下滑动页面再抓
          UsualClass.swipe(driver, weight / 2, height * 9 / 10, weight / 2, height / 10, 500);
        }

        //先判断一下拿到的距离中是否包含搜索关键字
        for (String str : distance) {
          AssertionUtil.AssertTrue(driver, str.contains(searchkey),
              "拿到的地址中没有存在关键字地标，预期包含" + searchkey + "实际：" + str);
        }
        //再将拿到的距离进行截取 将拿到的元素进行分割转化为int型（多少米）
        for (int i = 0; i < distance.size(); i++) {
          String distancetoString = distance.get(i);
          // 截取距离数字并给distance_number赋值
          try {
            actualdistance_number.add(Integer.valueOf(
                distancetoString.substring(distancetoString.indexOf("站") + 1,
                    distancetoString.indexOf("米"))));
            // 截取后的数字日志记录一下
            LogUtil.info("截取后的距离数字，第" + (i + 1) + "个是:" + actualdistance_number.get(i));
          } catch (Exception e) {
            for (StackTraceElement s : e.getStackTrace()) {
              LogUtil.error("获取的距离截取转换失败" + s);
            }
          }
        }
        // 现在需要把拿到的integer里面的list的数据进行校验（拿list里面最后一个与第一个比一下）
        Integer[] expecteddistance_number = new Integer[actualdistance_number.size()];
        for (int i = 0; i < actualdistance_number.size(); i++) {
          expecteddistance_number[i] = actualdistance_number.get(i);
        }
        // 数组排序
        Arrays.sort(expecteddistance_number);
        // 循环比对预期与实际的排序问题（主要i=0查看一下是否按照距离从近到远排列）
        for (int i = 0; i < expecteddistance_number.length; i++) {
          AssertionUtil.AssertEquals(driver, actualdistance_number.get(i),
              expecteddistance_number[i], "实际的排序未按照距离从近到远排序");
        }
        // 返回搜索联想页
        SearchResultLocation(driver, "searchback").click();
        // 清空搜索输入框
        UsualClass.clearEdit(driver, searchAssociationLocation(driver, "SearchEdit").getText());
      }
    }
  }

  // 通用搜索(这块用于预约收藏，目前都是测试小区)
  public static void UsualSearch(final AndroidDriver driver, AndroidDriverWait wait,
      String searchkeyword) {
    if (searchkeyword != null) {
      LogUtil.info("已拿到搜索关键字是" + searchkeyword + ",现在开始进行搜索");
      // (开始执行搜索操作)等待底部首页按钮
      WebElement homepagebutton = wait.until(new ExpectedCondition<WebElement>() {
        @Override public WebElement apply(WebDriver d) {
          return homeLocation(driver, "homepagebutton");
        }
      });
      // 点击底部首页按钮（确保他在首页）
      homepagebutton.click();
      // 定位搜索输入框
      WebElement Searchbox = homeLocation(driver, "Searchbox");
      // 点击首页输入框
      Searchbox.click();
      // 等待搜索输入框
      WebElement SearchEdit = wait.until(new ExpectedCondition<WebElement>() {
        @Override public WebElement apply(WebDriver arg0) {

          return searchAssociationLocation(driver, "SearchEdit");
        }
      });
      // 在搜索输入框中输入关键字
      SearchEdit.sendKeys(searchkeyword);
      // 点击联想页中的第一个
      List<WebElement> textview = new ArrayList<>();
      textview = wait.until(new ExpectedCondition<List<WebElement>>() {
        @Override public List<WebElement> apply(WebDriver dr) {

          return SearchAssociationListLocation(driver, "textview");
        }
      });
      /**
       * 这块存在偶现点击搜索联想页中第一个无响应的问题 尝试解决问题，强行线程停止3秒
       */
      try {
        Thread.sleep(3000);
      } catch (InterruptedException e1) {
        //
        e1.printStackTrace();
      }
      // 点击第一个搜索联想词(确保一下第一个按钮能点击)
      WebElement currentclick =
          wait.until(ExpectedConditions.elementToBeClickable(textview.get(0)));
      currentclick.click();
      // 等待搜索结果房源（获取房源列表）
      List<WebElement> houselist = new ArrayList<>();
      // 拿到houselist房源列表
      houselist = wait.until(new ExpectedCondition<List<WebElement>>() {
        @Override public List<WebElement> apply(WebDriver dr) {

          return SearchResultListLocation(driver, "houseitem");
        }
      });
      // 点击房源列表中的第一个进入房源详情（确保第一个房源是没有收藏过以及没有预约过，且是职业房东的房）
      houselist.get(0).click();
    } else {
      LogUtil.info("搜索关键字是空，不进行搜索");
    }
  }

  // 这个搜索用于发布房源-搜索小区(搜索完之后返回一个预期的搜索小区)
  public static List<String> ReleaseHouseSearch(final AndroidDriver driver, AndroidDriverWait wait,
      String searchkeyword) {
    // 发布房源页面定位小区
    releaseHouseLocation(driver, "quarters").click();
    // 搜索输入框中搜索关键字
    searchAssociationLocation(driver, "SearchEdit").sendKeys(searchkeyword);
    // 点击联想页中的第一个
    List<WebElement> textview = new ArrayList<>();
    textview = wait.until(new ExpectedCondition<List<WebElement>>() {
      @Override public List<WebElement> apply(WebDriver dr) {

        return SearchAssociationListLocation(driver, "textview");
      }
    });
    /**
     * 这块存在偶现点击搜索联想页中第一个无响应的问题 尝试解决问题，强行线程停止3秒
     */
    try {
      Thread.sleep(3000);
    } catch (InterruptedException e1) {
      //
      e1.printStackTrace();
    }
    // 点击第一个搜索联想词(确保一下第一个按钮能点击)
    WebElement currentclick = wait.until(ExpectedConditions.elementToBeClickable(textview.get(0)));
    //拿一下搜索关键字作为预期
    String expectedsearchquarters = currentclick.getText();
    //拿一下城市
    String expectedcity = releaseHouseLocation(driver, "citychoose").getText();
    currentclick.click();
    List<String> expected = new ArrayList<>();
    expected.add(expectedcity);
    expected.add(expectedsearchquarters);
    return expected;
  }

  public static List<WebElement> SearchResultListLocation(AndroidDriver dr, String item) {
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
      LogUtil.info("等待" + item);
      return null;
    }
  }

  public static WebElement SearchResultLocation(AndroidDriver dr, String item) {
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
      LogUtil.info("等待" + item);
      return null;
    }
  }

  /*
   *
   * 根据外部的类的调用，获取对应类名的json文件，生成元素信息map
   *
   * */
  public Map<String, String> getElementMap() {
    String filePath = BaseConfig.FILE_JSON;
    BufferedReader bufferedReader = null;
    try {
      bufferedReader = new BufferedReader(new FileReader(new File(filePath)));
    } catch (FileNotFoundException e1) {
      //
      e1.printStackTrace();
    }

    String jsonContent = null;
    try {
      jsonContent = IOUtils.toString(bufferedReader);
    } catch (IOException e) {
      //
      e.printStackTrace();
    }

    return (Map<String, String>) JSONObject.parse(jsonContent);
  }

  /*
   *
   * 显示等待15s，根据元素map key值获取定位信息， 返回元素对象
   *
   * */
  public AndroidElement findElement(String name) {
    final String id = this.stringHashMap.get(name);
    AndroidElement el = null;
    WebDriverWait wait = new WebDriverWait(driver, 15);
    try {
      el = wait.until(new ExpectedCondition<AndroidElement>() {
        @Override public AndroidElement apply(WebDriver d) {
          return findEleBy(id);
        }
      });
      LogUtil.debug("元素已找到：" + name + ": " + id);
    } catch (Exception e) {
      LogUtil.error("未找到元素：" + name + ": " + id);
    }
    return el;
  }

  /*
   *
   * 关键字驱动，兼容多种定位方式
   *
   * */
  private AndroidElement findEleBy(String id) {
    AndroidElement element = null;
    String byTpye = id.split("@")[0];
    String eleLoctorInfo = id.split("@")[1];
    switch (byTpye) {
      case "id":
        element = driver.findElementById(eleLoctorInfo);
        break;
      case "xpath":
        element = driver.findElementByXPath(eleLoctorInfo);
        break;
      case "desc":
        element = driver.findElementByAccessibilityId(eleLoctorInfo);
        break;
      case "text":
        element = driver.findElementByAndroidUIAutomator(
            "new UiSelector().text(\"" + eleLoctorInfo + "\")");
        break;
      case "class":
        element = driver.findElementByClassName(eleLoctorInfo);
        break;
      case "name"://name和text都是用于定位text，但是text用于定位7.0以上的系统
        element = driver.findElementByName(eleLoctorInfo);
        break;
      default:
        LogUtil.error("暂不支持的by类型：" + byTpye);
        break;
    }
    return element;
  }

  /*
   *
   * 返回具有相同定位元素信息的list
   *
   * */
  ArrayList<AndroidElement> findElesBy(String name) {
    String id = this.stringHashMap.get(name);
    ArrayList<AndroidElement> elements = new ArrayList<AndroidElement>();
    String byTpye = id.split("@")[0];
    String eleLoctorInfo = id.split("@")[1];
    switch (byTpye) {
      case "id":
        elements = (ArrayList<AndroidElement>) driver.findElementsById(eleLoctorInfo);
        break;
      case "xpath":
        elements = (ArrayList<AndroidElement>) driver.findElementsByXPath(eleLoctorInfo);
        break;
      case "class":
        elements = (ArrayList<AndroidElement>) driver.findElementsByClassName(eleLoctorInfo);
        break;
      default:
        LogUtil.error("暂不支持的by类型：" + byTpye);
        break;
    }
    return elements;
  }

  /*
   *
   * 逐一点击元素列表中的元素，一般用于多选
   * num 代表多选几个
   *
   * */
  public void clickAllEles(ArrayList<AndroidElement> als, int num) {

    for (int x = 0; x < num; x++) {
      if (x > als.size() - 1) break;
      als.get(x).click();
    }
  }

  public void swipe(int x, int y, int toX, int toY) {
    TouchAction action =
        new TouchAction(driver).longPress(x, y, Duration.ofSeconds(1)).moveTo(toX, toY).release();
    action.perform();
  }

  public void swipeToUp() {
    int width = driver.manage().window().getSize().width;
    int height = driver.manage().window().getSize().height;

    swipe(width / 2, height * 3 / 4, width / 2, height / 4);
    LogUtil.debug(
        "往上滑动：" + width / 2 + "_" + height * 3 / 4 + " to " + width / 2 + "_" + height / 4);
  }

  /*
   *
   * toast的识别需要使用uiautomator2
   * 仅使用了xpath的方式来识别
   *
   * */
  public boolean findToast(String name) {
    boolean isFind = false;
    String id = this.stringHashMap.get(name);
    String eleLoctorInfo = id.substring(6);
    WebDriverWait wait = new WebDriverWait(driver, 15);
    try {
      wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(eleLoctorInfo)));
      isFind = true;
    } catch (Exception e) {
      LogUtil.error(name + "未找到");
    }
    return isFind;
  }

  /*
   *
   * 判定当前页面元素是否存在
   *
   * */
  public boolean eleIsExist(String name) {
    boolean isExist = false;

    try {
      findElement(name);
      isExist = true;
    } catch (Exception e) {
      LogUtil.error("isExist:" + isExist);
    }

    return isExist;
  }

  public void deletePost() {

    findElement("mineButton").click();

    while (findElement("myPostButton") == null) {
      swipeToUp();
    }
    findElement("myPostButton").click();
    findElement("deleteButtons").click();
    findElement("deleteEnsureButton").click();
  }



  public void openPublishPage() {
    // 无房->发房
    findElement("findRoommateButton").click();
    findElement("publishIcon").click();
    findElement("hasntRoomButton").click();

    // 判断是否需要登录，进入发帖页面
    CmdUtil cmd = new CmdUtil();
    String res = cmd.runCommandProcess("adb shell dumpsys window | findstr mCurrentFocus");
    if (res.contains("LoginActivity")) {
      BasePage.simplelogin(driver, "15575993304", "180205");
      findElement("hasntRoomButton").click();
    }//常规流程先登录再发帖，故这里登录写死了
  }

  public void inputText(String title, String otherContent) {
    findElement("titleInput").sendKeys(otherContent);
    findElement("otherContentInput").sendKeys(otherContent);
  }

  /*
   *
   * 目前仅写了地铁和商圈每个选一个。后期这块场景用的多，可以修改扩展一下
   * 格式要求："jingan_jingansi","subway_line7"
   * 父目录和子目录用_隔开
   *
   * */
  public void selectLocation(String... location) {
    String businessCircle = location[0];
    String subway = location[1];

    findElement("locationButton").click();

    if (businessCircle != null) {
      findElement("businessCircle").click();
      findElement(businessCircle.split("_")[0]).click();
      findElement(businessCircle.split("_")[1]).click();
    }
    if (subway != null) {
      findElement("subway").click();
      findElement(subway.split("_")[0]).click();
      findElement(subway.split("_")[1]).click();
    }

    findElement("locationEnsuredButton").click();
  }

  public void selectPrice() {
    AndroidElement ae = findElement("priceGlider");
    int xlength = ae.getSize().width;
    Point leftPoint = ae.getCenter().moveBy(-(xlength / 2), 0);
    Point rightPoint = ae.getCenter().moveBy(xlength / 2, 0);
    //起步价滑动
    swipe(leftPoint.x + 10, leftPoint.y, leftPoint.x + xlength / 4, leftPoint.y);
    //最高价滑动
    swipe(rightPoint.x - 10, rightPoint.y, rightPoint.x - xlength / 4, rightPoint.y);
  }

  public void selectImages() {
    // 选择房间照片
    findElement("addImagesButton").click();
    findElement("albumButton").click();
    findElement("screenshotAlbum").click();
    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      //
      e.printStackTrace();
    }
    clickAllEles(findElesBy("images"), 3);
    findElement("imagesEnsuredButton").click();
  }

  public void clickPublish() {
    findElement("publishButton").click();
  }
}
