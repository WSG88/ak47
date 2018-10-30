package appiumtest.tests;

import appiumtest.utils.InitDriver;
import appiumtest.utils.UsualClass;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class PublishPostCase {
  AndroidDriver<AndroidElement> driver;

  FindRoommateBasePage findbg;
  MinePage minepg;
  // 日志对象
  Logger log = LoggerFactory.getLogger("PublishPostCase");

  /**
   * 发帖删除帖子测试类
   */
  @BeforeClass public void setup() {
    //	  AppiumServiceBuilder builder = new AppiumServiceBuilder()
    //				.withArgument(GeneralServerFlag.SESSION_OVERRIDE)
    //				.withIPAddress("127.0.0.1").usingPort(8000);
    //		AppiumDriverLocalService service = AppiumDriverLocalService.buildService(builder);
    //		service.start();
    //
    //		DesiredCapabilities cap = new DesiredCapabilities();
    //
    //		// 设置启动参数
    //		cap.setCapability("automationName", "uiautomator2");
    //		cap.setCapability("platformName", "Android");
    //		cap.setCapability("platformVersion", "5.0.2");
    //		cap.setCapability("deviceName", "vivox9");
    //		cap.setCapability("udid", "7POFC6HI5HPF5L6T");// 6b00d640 //MFQ8FMJZJVPNRGZH
    //		cap.setCapability("appPackage", "com.loulifang.house");
    //		cap.setCapability("appActivity", "com.loulifang.house.activities.TMainActivity");// TMainActivity
    //		cap.setCapability("unicodeKeyboard", "True");
    //		cap.setCapability("resetKeyboard", "True");
    //		cap.setCapability("newCommandTimeout", 180);
    //		cap.setCapability("systemPort", 8209);
    //
    //		// 初始化AndroidDriver
    //		String url = "http://127.0.0.1:4723/wd/hub";
    //		try {
    //			driver = new AndroidDriver<AndroidElement>(new URL(url), cap);
    //		} catch (MalformedURLException e) {
    //			//
    //			e.printStackTrace();
    //		}
    driver = InitDriver.dr;
    findbg = new FindRoommateBasePage(driver);
    findbg.findElement("mineButton");
    //		OwnerPage.login(driver, "15575993304", "180205");
  }

  @Test public void publishRoom() {
    log.info("开始测试发帖");
    findbg.openPublishPage();
    findbg.inputText("标题", "其他内容");
    findbg.selectLocation("jingan_jingansi", "subway_line7");
    findbg.selectPrice();
    findbg.selectImages();
    findbg.clickPublish();
  }

  @AfterClass
  //发帖数据清除
  public void setdown() {
    log.info("开始测试删除帖子");
    minepg = new MinePage(driver);
    minepg.deletePost();

    boolean isFind = minepg.findToast("deletedSucessToast");
    // Assert.verify(isFind,"发布失败，没有找到删除成功的toast");
    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      //
      e.printStackTrace();
    }
    //需要回到根页面
    UsualClass.back(driver);
  }
}
