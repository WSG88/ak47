package appiumtest.utils;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * 我的页面方法的封装，作为公共方法
 * 公共方法有：定位、登录、退出登录(登录、登陆定位的方法未进行单独的封装)
 *
 * 设置页面的定位是否需要拆分出来？？？
 */
public class OwnerPage {

  // 日志对象
  static Logger log = LoggerFactory.getLogger("OwnerPage");

  public static WebElement Location(AndroidDriver dr, String item) {
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
      log.info("等待" + item);
      return null;
    }
  }

  //登录方法（点击我的页面再登录）
  public static void login(AndroidDriver driver, String mobile, String password) {
    //优化登录方法（使用location的定位去完成，减少耦合度）
    //点击我的按钮
    Location(driver, "owner").click();
    //点击立即登录
    Location(driver, "loginbutton").click();
    //输入手机号验证码登录
    Location(driver, "UsernameEdit").sendKeys(mobile);
    Location(driver, "password").sendKeys(password);
    Location(driver, "confirmloginbutton").click();
  }

  //登录方法（用于点击预约收藏时未登录时的情况）
  public static void simplelogin(AndroidDriver driver, String mobile, String password) {
    //输入手机号验证码登录
    Location(driver, "UsernameEdit").sendKeys(mobile);
    Location(driver, "password").sendKeys(password);
    Location(driver, "confirmloginbutton").click();
  }

  //退出登录
  public static void loginout(AndroidDriver dr) {
    AndroidDriverWait wait = new AndroidDriverWait(dr, 60);
    ;
    //点击我的按钮
    Location(dr, "owner").click();
    //点击设置按钮
    Location(dr, "Set_up").click();
    WebElement loginout = wait.until(
        ExpectedConditions.presenceOfElementLocated(By.id("com.loulifang.house:id/quitBtn")));
    loginout.click();
    //确定退出
    wait.until(ExpectedConditions.presenceOfElementLocated(By.id("android:id/button1"))).click();
    //	   dr.findElement(By.id("android:id/button1")).click();

  }
}
