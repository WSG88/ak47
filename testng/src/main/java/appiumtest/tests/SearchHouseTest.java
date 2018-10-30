package appiumtest.tests;

import appiumtest.utils.AndroidDriverWait;
import appiumtest.utils.AssertionListener;
import appiumtest.utils.InitDriver;
import appiumtest.utils.SearchAssociationPage;
import appiumtest.utils.UsualClass;
import io.appium.java_client.android.AndroidDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

/**
 * 这个搜索类包括，搜索小区、地铁、区域、商圈、地标以及品牌
 *
 * @author Administrator
 */
@Listeners({ AssertionListener.class }) public class SearchHouseTest {
  //日志对象
  Logger log = LoggerFactory.getLogger("SearchHouseTest");
  private AndroidDriver driver;
  private AndroidDriverWait wait = null;

  @BeforeClass public void InitDriver() {
    driver = InitDriver.dr;
    wait = InitDriver.wait;
    //配置线程名字用于日志记录
    Thread.currentThread().setName("SearchHouseTest");
  }

  /**
   * 这个搜索类后期打算加入搜索联想页的校验，目前就一个搜索点击看搜索结果数据
   *
   * @param searchitem:指搜索关键字 searchtype：指搜索类型
   */

  @Test public void SearchTest() {
    log.info("显示开始测试搜索房源");
    SearchAssociationPage.WholesSarch(driver, wait);
  }

  // 点击搜索附近房源
  // SearchAssociationPage.Location(driver, "Nearhouse").click();

  @AfterClass public void afterClass() {
    UsualClass.back(driver);
    // driver.quit();
  }
}
