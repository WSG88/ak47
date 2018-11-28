package appiumtest.tests;

import appiumtest.page.BasePage;
import appiumtest.page.PostPage;
import appiumtest.utils.AssertionListener;
import appiumtest.utils.CmdUtil;
import appiumtest.utils.InitDriver;
import appiumtest.utils.LogUtil;
import appiumtest.utils.UsualClass;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import java.util.ArrayList;
import org.openqa.selenium.Point;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

@Listeners({ AssertionListener.class }) public class PostTest {
  AndroidDriver<AndroidElement> driver;

  PostPage basePage;

  public static void threadSleep() {
    try {
      Thread.sleep(3000);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @BeforeClass public void setup() {
    driver = InitDriver.dr;
    basePage = new PostPage(driver);
    basePage.findE("mineButton");
  }

  @Parameters({ "mobile", "password" }) @Test public void testPost(String mobile, String password) {
    LogUtil.info("开始测试发帖");
    openPublishPage(mobile, password);
    inputText("标题", "其他内容");
    selectLocation("jingan_jingansi", "subway_line7");
    selectPrice();
    selectImages();
    clickPublish();
  }

  @AfterClass public void after() {
    LogUtil.info("开始测试删除帖子");
    deletePost();

    //需要回到根页面
    UsualClass.back(driver);
  }

  public void inputText(String title, String otherContent) {
    basePage.findE("titleInput").sendKeys(otherContent);
    basePage.findE("otherContentInput").sendKeys(otherContent);
  }

  public void selectLocation(String... location) {
    String businessCircle = location[0];
    String subway = location[1];

    basePage.findE("locationButton").click();

    if (businessCircle != null) {
      basePage.findE("businessCircle").click();
      basePage.findE(businessCircle.split("_")[0]).click();
      basePage.findE(businessCircle.split("_")[1]).click();
    }
    if (subway != null) {
      basePage.findE("subway").click();
      basePage.findE(subway.split("_")[0]).click();
      basePage.findE(subway.split("_")[1]).click();
    }

    basePage.findE("locationEnsuredButton").click();
  }

  public void selectPrice() {
    AndroidElement ae = basePage.findE("priceGlider");
    int xlength = ae.getSize().width;
    Point leftPoint = ae.getCenter().moveBy(-(xlength / 2), 0);
    Point rightPoint = ae.getCenter().moveBy(xlength / 2, 0);
    //起步价滑动
    UsualClass.swipe(driver, leftPoint.x + 10, leftPoint.y, leftPoint.x + xlength / 4, leftPoint.y);
    //最高价滑动
    UsualClass.swipe(driver, rightPoint.x - 10, rightPoint.y, rightPoint.x - xlength / 4,
        rightPoint.y);
  }

  public void openPublishPage(String mobile, String password) {
    // 无房->发房
    basePage.findE("findRoommateButton").click();
    basePage.findE("publishIcon").click();
    basePage.findE("hasntRoomButton").click();
    threadSleep();
    if (CmdUtil.isSameAct("LoginActivity")) {
      BasePage.simplelogin(driver, mobile, password);
    }
  }

  public void clickPublish() {
    basePage.findE("publishButton").click();
  }

  public void clickAllEles(ArrayList<AndroidElement> als, int num) {

    for (int x = 0; x < num; x++) {
      if (x > als.size() - 1) break;
      als.get(x).click();
    }
  }

  public void selectImages() {
    // 选择房间照片
    basePage.findE("addImagesButton").click();
    basePage.findE("albumButton").click();
    basePage.findE("screenshotAlbum").click();

    threadSleep();

    clickAllEles(basePage.findEsBy("images"), 3);
    basePage.findE("imagesEnsuredButton").click();
  }

  public void deletePost() {
    threadSleep();

    basePage.findE("mineButton").click();

    while (basePage.findE("myPostButton") == null) {
      UsualClass.swipeToUp(driver);
    }
    basePage.findE("myPostButton").click();
    basePage.findE("deleteButtons").click();
    basePage.findE("deleteEnsureButton").click();

    threadSleep();
  }
}
