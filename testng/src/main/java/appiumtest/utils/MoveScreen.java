package appiumtest.utils;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * 这个方法如果加一个webelement入参，发现就会报找不到元素的异常，后期用的话就只能单独写了
 * 这个方法先保留，后期做为参考用
 * 目前已实现滑动寻找元素，暂时还未在各测试类中使用
 */
public class MoveScreen {

  public void MoveToElement(final AndroidDriver dr) {

    WebDriverWait wait = new WebDriverWait(dr, 60);
    //自己写
    WebElement Test = wait.until(new ExpectedCondition<WebElement>() {

      @Override public WebElement apply(WebDriver arg0) {

        int width = dr.manage().window().getSize().width;
        int height = dr.manage().window().getSize().height;
        dr.swipe(width / 2, height * 3 / 4, width / 2, height / 2, 500);
        System.out.println("scroll=======" + height * 3 / 4 + " " + height / 4);
        return dr.findElement(By.id("com.loulifang.house:id/landlordDescTxt"));
      }
    });
    System.out.println("文案：" + Test.getText());
  }
}
