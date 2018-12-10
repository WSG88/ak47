package appiumtest.tests;

import appiumtest.page.BasePage;
import appiumtest.utils.AndroidDriverWait;
import appiumtest.utils.AssertionListener;
import appiumtest.utils.InitDriver;
import appiumtest.utils.LogUtil;
import appiumtest.utils.UsualClass;
import io.appium.java_client.android.AndroidDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

/**
 * 这个搜索类包括，搜索小区、地铁、区域、商圈、地标以及品牌
 */
@Listeners({ AssertionListener.class }) public class HouseSearchTest {
  private AndroidDriver driver;
  private AndroidDriverWait wait = null;

  @BeforeClass public void InitDriver() {
    driver = InitDriver.dr;
    wait = InitDriver.wait;
  }

  /**
   * 这个搜索类后期打算加入搜索联想页的校验，目前就一个搜索点击看搜索结果数据
   */

  @Test public void SearchTest() {
    LogUtil.info("显示开始测试搜索房源");
    BasePage.wholesSearch(driver, wait);
  }

  @AfterClass public void afterClass() {
    UsualClass.back(driver);
    // driver.quit();
  }
}
