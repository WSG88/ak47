package appiumtest.tests;

import appiumtest.page.BasePage;
import appiumtest.utils.AssertionListener;
import appiumtest.utils.InitDriver;
import appiumtest.utils.LogUtil;
import appiumtest.utils.UsualClass;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners({ AssertionListener.class }) public class PublishPostTest {
  AndroidDriver<AndroidElement> driver;

  BasePage findbg;

  /**
   * 发帖删除帖子测试类
   */
  @BeforeClass public void setup() {
    driver = InitDriver.dr;
    findbg = new BasePage(driver);
    findbg.findElement("mineButton");
  }

  @Test public void publishRoom() {
    LogUtil.info("开始测试发帖");
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
    LogUtil.info("开始测试删除帖子");
    new BasePage(driver).deletePost();

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
