package appiumtest.tests;

import appiumtest.utils.AndroidDriverWait;
import appiumtest.utils.AssertionListener;
import appiumtest.utils.InitDriver;
import appiumtest.utils.MyAssertion;
import appiumtest.utils.MyReleaseHousePage;
import appiumtest.utils.OwnerPage;
import appiumtest.utils.ReleaseHousePage;
import appiumtest.utils.SearchAssociationPage;
import appiumtest.utils.UsualClass;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 发布房源测试类
 *
 * @author Administrator
 */
@Listeners({ AssertionListener.class }) public class ReleaseHouseTest {
  public AndroidDriver driver;
  public AndroidDriverWait wait;
  // 日志对象
  Logger log = LoggerFactory.getLogger("ReleaseHouseTest");

  @BeforeClass public void beforeClass() {
    // 初始化driver以及wait
    driver = InitDriver.dr;
    wait = InitDriver.wait;
  }

  //firstmethod主要负责跳转至房源类型页面(且把未登录的时候进行登录)
  @Parameters({ "mobile", "password" }) @Test public void FirstMethod(String mobile,
      String password) {
    log.info("现在测试发房");
    //点击我的-我发布的房源(进入我发布的房源页面)
    WebElement owner = wait.until(new ExpectedCondition<WebElement>() {
      @Override public WebElement apply(WebDriver d) {
        return OwnerPage.Location(driver, "owner");
      }
    });
    owner.click();
    //先判定一下是否登录(获取昵称是否是理解登录来判定是否已经登录了账号)
    String nickname = OwnerPage.Location(driver, "nickname").getText();
    if (nickname.contains("立即登录")) {
      log.info("还未登录，先登录吧");
      OwnerPage.login(driver, mobile, password);
    } else {
      log.info("已登录，直接开始发房");
    }

    //如果登录了就直接点击我发布的房源
    WebElement myReleaseHousebtn = wait.until(new ExpectedCondition<WebElement>() {
      @Override public WebElement apply(WebDriver d) {
        return OwnerPage.Location(driver, "myReleaseHousebtn");
      }
    });
    myReleaseHousebtn.click();
    //我发布的房源页面点击发房按钮
    WebElement ReleaseHousebtn = wait.until(new ExpectedCondition<WebElement>() {
      @Override public WebElement apply(WebDriver d) {
        return MyReleaseHousePage.Location(driver, "ReleaseHousebtn");
      }
    });
    ReleaseHousebtn.click();
  }

  //发布整租房源(FirstMethod已完成页面跳转至房源类型页面)
  @Test(dependsOnMethods = { "FirstMethod" }) public void ReleaseWholeRentHouseText() {
    log.info("现在开始发房");
    int width = driver.manage().window().getSize().width;
    int height = driver.manage().window().getSize().height;
    //房源类型页面选择非转租类型
    ReleaseHousePage.Location(driver, "NoSubleasebtn").click();
    //点击下一步
    ReleaseHousePage.Location(driver, "nextbtn").click();
    //发布整租房源页面，断言一下租住类型（断言标题以及租住类型）
    MyAssertion.AssertEquals(driver, ReleaseHousePage.Location(driver, "title").getText(), "发布整租房源",
        "发房标题错误");
    MyAssertion.AssertEquals(driver, ReleaseHousePage.Location(driver, "renttype").getText(),
        "整租（非转租）", "租住类型");

    //定义一个图片张数变量(最开始有一个添加按钮)
    int Expectedpiccount = 1;
    //第一个图片执行相机上传
    ReleaseHousePage.camerasubmitpictrue(driver, wait, Expectedpiccount);
    Expectedpiccount++;
    //上传图片成功后再在发布房源一下拿下房源图片数量
    List<WebElement> housepicnumbers = ReleaseHousePage.Locationpiclist(driver);
    int actualpiccount = housepicnumbers.size();
    //去校验一下是否已经上传成功
    MyAssertion.AssertEquals(driver, actualpiccount - 1, Expectedpiccount - 1, "拍照后未成功添加图片");
    //调用相册上传图片（Expectedpiccount这个参数是一共多少个图包括添加按钮）
    Expectedpiccount =
        Expectedpiccount + ReleaseHousePage.Albumsubmitpictrue(driver, wait, Expectedpiccount);
    //断言（判定一下一共上传的图片数,Expectedpiccount这个变量中已经存在2个添加图片的按钮）
    housepicnumbers = ReleaseHousePage.Locationpiclist(driver);
    actualpiccount = housepicnumbers.size();
    //这块重新赋值一下（Expectedpiccount这个由于两次统计上了添加图片按钮，actualpiccount这个是回到发房页面重新拿的只存在一个添加图片按钮）
    Expectedpiccount = Expectedpiccount - 2;
    actualpiccount = actualpiccount - 1;
    log.info("预期上传图片数为：" + Expectedpiccount + "实际上传图片数为" + actualpiccount);
    MyAssertion.AssertEquals(driver, actualpiccount, Expectedpiccount, "相册上传后未成功添加图片");

    //搜索房源小区，封装的方法会返回一个预期的城市.小区(正式环境只发测试小区，这块写死了)
    List<String> expected = SearchAssociationPage.ReleaseHouseSearch(driver, wait, "测试小区");
    //定位实际城市以及小区
    String actual = ReleaseHousePage.Location(driver, "quarterstextview").getText();
    //由于存在中间的一个居中的.无法处理，只能用contains做效验了
    if (actual != null && expected.size() > 0) {
      log.info("实际小区是：" + actual);
      for (String s : expected) {
        MyAssertion.AssertTrue(driver, actual.contains(s), "实际的地址中不包含预期字段" + s);
      }
    } else {
      if (actual == null) {
        log.info("实际的小区是空值");
      } else {
        log.info("预期的小区是空值");
      }
    }

    //楼层
    ReleaseHousePage.Location(driver, "floorbtn").click();
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      //
      e.printStackTrace();
    }
    //楼层信息
    List<WebElement> floor = driver.findElements(By.id("android:id/numberpicker_input"));
    WebElement firstfloor = floor.get(0);
    WebElement totalfloor = floor.get(1);
    TouchAction action = new TouchAction(driver);
    //滑动楼层（这块实现了就滑动总楼层）
    if (floor.size() == 2) {
      //滑动总楼层3次
      for (int i = 0; i < 3; i++) {
        action.longPress(totalfloor, 2000)
            .moveTo(totalfloor.getLocation().x, totalfloor.getLocation().y - 300)
            .release()
            .perform();
      }
      try {
        Thread.sleep(5000);
      } catch (InterruptedException e) {
        //
        e.printStackTrace();
      }
      //重新获取一次最新的楼层信息
      floor = driver.findElements(By.id("android:id/numberpicker_input"));
      String firstfloorToString = floor.get(0).getText();
      String totalfloorToString = floor.get(1).getText();
      //点击确定按钮
      ReleaseHousePage.Location(driver, "confirmbtn").click();
      //选择完楼层后页面返回至发布房源页面，拿一下选择好的楼层
      String actualfloorToString = ReleaseHousePage.Location(driver, "floortextview").getText();
      String expectedfloorToString = firstfloorToString + "层/" + totalfloorToString + "层";
      //记录日志关于拿到的actualfloorToString、expectedfloorToString
      log.info("滑动后的楼层是：在" + firstfloorToString + "层，共" + totalfloorToString + "层");
      log.info("预期expectedfloorToString:"
          + expectedfloorToString
          + ",实际actualfloorToString:"
          + actualfloorToString);
      //断言（选择器的文案与发布房源显示的楼层进行断言判定）
      MyAssertion.AssertEquals(driver, actualfloorToString, expectedfloorToString, "选择的楼层与显示不一致");
    }
  }

  @AfterClass public void afterClass() {
    UsualClass.back(driver);
  }
}
