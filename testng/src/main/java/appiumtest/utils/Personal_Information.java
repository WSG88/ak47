package appiumtest.utils;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * 我的-个人信息页面的工具类
 * location（个人信息页面的所有定位）
 * UpdateInformation（这个方法还没有完善，注释了）
 *
 * @author Administrator
 */

public class Personal_Information {

  public static WebElement Location(AndroidDriver dr, String item) {
    switch (item) {
      //头像
      case "head":
        WebElement head = dr.findElement(By.id("com.loulifang.house:id/rl_avatar"));
        return head;
      //昵称
      case "nickname":
        WebElement nickname = dr.findElement(By.id("com.loulifang.house:id/updateUserNameLyt"));
        return nickname;
      //手机号码
      case "mobile":
        WebElement mobile = dr.findElement(By.id("com.loulifang.house:id/updatePhoneNumberLyt"));
        return mobile;
      //性别
      case "sex":
        WebElement sex = dr.findElement(By.id("com.loulifang.house:id/myGenderLyt"));
        return sex;
      //年龄
      case "age":
        WebElement age = dr.findElement(By.id("com.loulifang.house:id/myAgeLyt"));
        return age;
      //星座
      case "constellation":
        WebElement constellation = dr.findElement(By.id("com.loulifang.house:id/myConstLyt"));
        return constellation;
      //行业
      case "industry":
        WebElement industry = dr.findElement(By.id("com.loulifang.house:id/myJobLyt"));
        return industry;
      //个人简介
      case "personal_profile":
        WebElement personal_profile = dr.findElement(By.id("com.loulifang.house:id/descLyt"));
        return personal_profile;
      case "save_informations":
        WebElement save_informations = dr.findElement(By.id("com.loulifang.house:id/submitBtn"));
        return save_informations;
    }
    return null;
  }
}
