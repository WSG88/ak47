package appiumtest.tests;

import appiumtest.page.BasePage;
import appiumtest.utils.AndroidDriverWait;
import appiumtest.utils.AssertionListener;
import appiumtest.utils.AssertionUtil;
import appiumtest.utils.InitDriver;
import appiumtest.utils.LogUtil;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

@Listeners({ AssertionListener.class }) public class LoginTest {

  private AndroidDriver driver;
  private AndroidDriverWait wait;

  @BeforeClass public void InitDriver() {
    driver = InitDriver.dr;
    wait = InitDriver.wait;
  }

  @Parameters({ "mobile", "password" }) @Test(priority = 1)
  public void loginTest(String mobile, String password) {
    LogUtil.info("现在开始测试登陆");
    wait.until(new ExpectedCondition<WebElement>() {
      @Override public WebElement apply(WebDriver d) {
        return BasePage.ownerLocation(driver, "owner");
      }
    });

    WebElement loginbutton = wait.until(new ExpectedCondition<WebElement>() {
      @Override public WebElement apply(WebDriver arg0) {
        return BasePage.ownerLocation(driver, "loginbutton");
      }
    });
    String name_text = loginbutton.getText();
    if ("立即登录".equals(name_text)) {
      LogUtil.info("现在开始登录");
      BasePage.login(driver, mobile, password);
      WebElement minebutton = wait.until(
          ExpectedConditions.presenceOfElementLocated(By.id("com.loulifang.house:id/mineLyt")));
      minebutton.click();
      String login_mobile = BasePage.ownerLocation(driver, "mobile").getText();
      //
      String actualmobile = login_mobile.replace(" ", "");
      LogUtil.info("实际的电话号码" + actualmobile);
      AssertionUtil.AssertEquals(driver, actualmobile, mobile, "登录的账号与预期的不符");
      LogUtil.info("登录成功");
    } else {
      LogUtil.info("已经登录");
    }
  }

  @Test(priority = 2) public void logoutTest() {
    wait.until(new ExpectedCondition<WebElement>() {
      @Override public WebElement apply(WebDriver d) {
        return BasePage.ownerLocation(driver, "owner");
      }
    });
    LogUtil.info("现在开始登出");
    BasePage.loginout(driver);

    WebElement loginbutton = wait.until(new ExpectedCondition<WebElement>() {
      @Override public WebElement apply(WebDriver arg0) {
        return BasePage.ownerLocation(driver, "loginbutton");
      }
    });

    String name_text = loginbutton.getText();
    AssertionUtil.AssertEquals(driver, name_text, "立即登录", "登出后的默认按钮文案");
    LogUtil.info("退出登录成功");
  }

  @AfterMethod public void afterMethod() {
    BasePage.homeLocation(driver, "homepagebutton").click();
  }
}
