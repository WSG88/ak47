package appiumtest.tests;

import appiumtest.utils.AndroidDriverWait;
import appiumtest.utils.AssertionListener;
import appiumtest.utils.HomePage;
import appiumtest.utils.InitDriver;
import appiumtest.utils.MyAssertion;
import appiumtest.utils.OwnerPage;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Reporter;

/*
 * 这个类中包括两个test：登录以及登出
 */

@Listeners({ AssertionListener.class }) public class LoginTest {

  // 日志对象
  Logger log = LoggerFactory.getLogger("LoginTest");
  int status;
  private AndroidDriver driver;
  private AndroidDriverWait wait;

  @BeforeClass
  // 此处的beforeclass的入参拿的是xml的参数
  public void InitDriver() {
    driver = InitDriver.dr;
    // 配置线程名字用于日志记录
    Thread.currentThread().setName("LoginTest");
    // 建立一个AndroidDriverwait对象用于这个类的等待处理
    wait = InitDriver.wait;
  }

  @Parameters({ "mobile", "password" }) @Test(priority = 1)
  public void logintest(String mobile, String password) {
    log.info("现在开始测试登陆");
    // 取消存储权限（暂不）
    // driver.findElement(By.id("android:id/button2")).click();
    // driver.findElement(By.className("android.widget.Button")).click();

    // 启动等待

    wait.until(new ExpectedCondition<WebElement>() {
      @Override public WebElement apply(WebDriver d) {
        return OwnerPage.Location(driver, "owner");
      }
    });
    log.info("现在开始登录");
    // 调用登录方法
    OwnerPage.login(driver, mobile, password);
    // 等待操作进行校验(等待我的按钮出现)
    WebElement minebutton = wait.until(
        ExpectedConditions.presenceOfElementLocated(By.id("com.loulifang.house:id/mineLyt")));
    // 点击我的按钮
    minebutton.click();
    // 得到用户名与密码
    String name = OwnerPage.Location(driver, "nickname").getText();
    String login_mobile = OwnerPage.Location(driver, "mobile").getText();
    // 断言
    String actualmobile = login_mobile.replace(" ", "");
    log.info("实际的电话号码" + actualmobile);
    MyAssertion.AssertEquals(driver, actualmobile, mobile, "登录的账号与预期的不符");
    //		MyAssertion.AssertEquals(driver, name, "测试中介1", "登录的账号昵称不对");
    log.info("登录成功");
    Reporter.log("报告：登录成功");
  }

  @Test(priority = 2)
  // 退出登录测试
  public void loginouttest() {
    // 启动等待
    wait.until(new ExpectedCondition<WebElement>() {
      @Override public WebElement apply(WebDriver d) {
        return OwnerPage.Location(driver, "owner");
      }
    });
    log.info("现在开始登出");
    // 调用登出公共方法
    OwnerPage.loginout(driver);
    // 断言前加个等待时间（等待名字区域存在然后再断言是否是“立即登录”按钮）
    WebElement loginbutton = wait.until(new ExpectedCondition<WebElement>() {
      @Override public WebElement apply(WebDriver arg0) {

        return OwnerPage.Location(driver, "loginbutton");
      }
    });
    // 断言判定是否变为立即登录
    String name_text = loginbutton.getText();
    //		MyAssertold.asserttrue(driver, name_text.equals("立即登录"));
    MyAssertion.AssertEquals(driver, name_text, "立即登录", "登出后的默认按钮文案");
    log.info("退出登录成功");
  }

  @AfterMethod public void afterMethod() {

    HomePage.Location(driver, "homepagebutton").click();
  }
}
