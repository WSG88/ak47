package appiumtest.tests;

import appiumtest.page.BasePage;
import appiumtest.utils.AndroidDriverWait;
import appiumtest.utils.AssertionListener;
import appiumtest.utils.AssertionUtil;
import appiumtest.utils.InitDriver;
import appiumtest.utils.LogUtil;
import appiumtest.utils.UsualClass;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 * 这个房源详情的测试类，目前先做收藏、预约
 */
@Listeners({ AssertionListener.class }) public class HouseDetailTest {
  public AndroidDriver driver;
  public AndroidDriverWait wait;
  AndroidDriverWait specialwait;
  int width, height;
  // 1表示断言或者各种异常，需要调用aftermothod来回到首页，默认为0不需要回到首页
  int isException = 0;

  // checkandcancelBook这个方法主要检查预约的时间以及预约的房源，并且取消预约
  public void checkandcancelBook(String expecteddatechooseToString,
      String expectedtimechooseToString, String expectedroomnoToString) {
    // 调用UsualClass.back方法回到根目录
    UsualClass.back(driver);
    // 再点击我的-预约清单，去查看一下第一条预约的单子（断言预约的时间以及房源编号）
    BasePage.ownerLocation(driver, "owner").click();
    BasePage.ownerLocation(driver, "bookhouselistbtn").click();
    // 先判定一下预约清单里面是否有houselist
    List<WebElement> houselist = wait.until(new ExpectedCondition<List<WebElement>>() {
      @Override public List<WebElement> apply(WebDriver d) {
        return BasePage.SearchResultListLocation(driver, "houseitem");
      }
    });
    LogUtil.info("预约清单的房源数量" + houselist.size());
    if (houselist.size() == 0) {
      AssertionUtil.AssertTrue(driver, false, "预约成功后发现预约清单页面一个房源都没有");
    } else {
      // 效验一下预约的时间(实际拿到的预约时间存在一个标题，这块字符串截取一下)
      String bookhousetime = BasePage.bookHouseListLocation(driver, "bookhousetime").getText();
      String acutalbookhousetime = bookhousetime.substring(bookhousetime.indexOf(":") + 1).trim();
      LogUtil.info("预期预约房源的时间点："
          + expecteddatechooseToString
          + expectedtimechooseToString
          + "实际预约房源的时间点"
          + acutalbookhousetime);

      AssertionUtil.AssertEquals(driver, acutalbookhousetime.replace(" ", ""),
          expecteddatechooseToString.replace(" ", "") + expectedtimechooseToString.replace(" ", ""),
          "预约的时间有误");
      // 如果有房源则点击第一个进入房源详情页比对一下房间编号
      houselist.get(0).click();
      WebElement actualroomno = wait.until(new ExpectedCondition<WebElement>() {
        @Override public WebElement apply(WebDriver d) {
          return BasePage.houseDetailLocation(driver, "RoomNo");
        }
      });
      LogUtil.info(
          "预期的预约房间编号：" + expectedroomnoToString + ",实际的预约房间编号:" + actualroomno.getText().trim());
      AssertionUtil.AssertEquals(driver, actualroomno.getText().trim(), expectedroomnoToString,
          "预约的房源编号不对");
    }
    // 取消预约
    BasePage.bookHouseListLocation(driver, "back").click();
    BasePage.bookHouseListLocation(driver, "cancelbook").click();
    // 弹窗（确定要取消预约吗）
    driver.findElement(By.id("android:id/button1")).click();
    // 回到根目录，再次启动搜索开始预约
    UsualClass.back(driver);
  }

  @BeforeClass public void beforeClass() {
    // 初始化driver以及wait
    driver = InitDriver.dr;
    wait = InitDriver.wait;
    // 定义屏幕大小
    width = driver.manage().window().getSize().width;
    height = driver.manage().window().getSize().height;
  }

  /**
   * 在每次Test之前都需要找到测试小区并进入房源详情页，然后进行预约以及收藏
   */
  // 这个方法下方的预约以及收藏Test都需要依赖他，这个方法主要实现进入测试小区的第一个房源的房源详情
  @Test public void FirstMethod() {
    BasePage.UsualSearch(driver, wait, "测试小区");
  }

  // 这块如果没有登录时需要拿手机号以及密码
  @Parameters({ "mobile", "password" }) @Test(dependsOnMethods = { "FirstMethod" }, priority = 1)
  public void CollectionHouseTest(String mobile, String password) {

    LogUtil.info("现在开始测试收藏房源");
    // 等待房详情页底部的收藏按钮
    WebElement CollectionButton = wait.until(new ExpectedCondition<WebElement>() {
      @Override public WebElement apply(WebDriver d) {
        return BasePage.houseDetailLocation(driver, "collectbutton");
      }
    });

    WebElement expectedRoomNo = wait.until(new ExpectedCondition<WebElement>() {
      @Override public WebElement apply(WebDriver d) {
        return BasePage.houseDetailLocation(driver, "RoomNo");
      }
    });
    // 拿一下收藏的房源编号(作为预期的房源编号)
    String expectedRoomNoToString = BasePage.houseDetailLocation(driver, "RoomNo").getText();
    // 点击收藏按钮
    CollectionButton.click();
    // 如果没有登录的情况会跳转至登录页面,登录了直接去点击我收藏的房源
    WebElement mycollecthouse = null;
    // 尝试去拿我收藏的房源按钮(new一个5秒的特殊的等待对象，只在这个方法有效)

    // 这个specialwait主要用于检查是否登录的
    specialwait = new AndroidDriverWait(driver, 5);
    try {
      mycollecthouse = specialwait.until(new ExpectedCondition<WebElement>() {
        @Override public WebElement apply(WebDriver d) {
          return BasePage.houseDetailLocation(driver, "mycollecthouse");
        }
      });
      // 找到我收藏的房源后点击此按钮（页面跳转至我收藏的房源）
      mycollecthouse.click();
    } catch (Exception e) {
      LogUtil.info("没有找到我收藏的房源按钮，说明没有登录，现在执行登录操作");
      try {
        BasePage.simplelogin(driver, mobile, password);
      } catch (Exception e1) {
        LogUtil.info("未跳转登录页面，可能是已经收藏的情况,尝试再次点击收藏按钮");
        CollectionButton.click();
      }
      // 登录成功后再次拿我收藏的房源按钮
      mycollecthouse = specialwait.until(new ExpectedCondition<WebElement>() {
        @Override public WebElement apply(WebDriver d) {
          return BasePage.houseDetailLocation(driver, "mycollecthouse");
        }
      });
      // 找到我收藏的房源后点击此按钮（页面跳转至我收藏的房源）
      mycollecthouse.click();
    }

    // 等待我收藏的房源列表
    List<WebElement> MyCollectHouselist = new ArrayList<>();
    MyCollectHouselist = wait.until(new ExpectedCondition<List<WebElement>>() {
      @Override public List<WebElement> apply(WebDriver d) {
        return BasePage.SearchResultListLocation(driver, "houseitem");
      }
    });
    // 先判断一下我收藏的房源页面是否存在房源
    if (MyCollectHouselist.size() == 0) {
      AssertionUtil.AssertTrue(driver, false, "收藏房源成功后发现我收藏的房源中没有显示任何房源");
    } else {
      // 拿第一个房源进入房源详情
      MyCollectHouselist.get(0).click();
      WebElement actualroomno = wait.until(new ExpectedCondition<WebElement>() {
        @Override public WebElement apply(WebDriver d) {
          return BasePage.houseDetailLocation(driver, "RoomNo");
        }
      });
      String actualroomnotoString = actualroomno.getText();
      //进行取消收藏
      CollectionButton.click();
      // 断言（判断两个房源的roomno是否一致）
      LogUtil.info("预期的房间编号：" + actualroomnotoString + ",实际的房间编号：" + actualroomnotoString);
      AssertionUtil.AssertEquals(driver, actualroomnotoString, expectedRoomNoToString, "收藏的房源编号有误");
      // 点击返回按钮返回房源详情页
      for (int i = 0; i < 2; i++) {
        driver.pressKeyCode(4);
        try {
          Thread.sleep(2000);
        } catch (InterruptedException e) {
          //
          e.printStackTrace();
        }
      }
      // 去尝试拿一下预约按钮
      try {
        WebElement bookhousebtn = wait.until(new ExpectedCondition<WebElement>() {
          @Override public WebElement apply(WebDriver d) {
            return BasePage.houseDetailLocation(driver, "bookhousebtn");
          }
        });
        LogUtil.info("收藏成功，直接开始测试预约功能");
      } catch (Exception e) {
        isException = 1;
        LogUtil.info("由于收藏按钮存在问题，先返回首页再进行下方的预约测试");
      }
    }
  }

  /**
   * @param mobile 用于未登录的情况下
   * @param password 用于未登录的情况下 BookHouseTest这个测试类主要检测预约以及重复预约以及取消预约后的重新预约
   */
  // 预约房源测试类依赖的方法已完成进入房源详情（测试小区第一个）
  @Parameters({ "mobile", "password" }) @Test(dependsOnMethods = { "FirstMethod" }, priority = 2)
  public void BookHouseTest(String mobile, String password) {

    // 显示等待拿到预约按钮(这块后期改一下，有些房源没有预约)(这块需要先去拿一下预约按钮，如果收藏成功了就直接能找到预约按钮)
    WebElement bookhousebtn = null;
    try {
      bookhousebtn = wait.until(new ExpectedCondition<WebElement>() {
        @Override public WebElement apply(WebDriver d) {
          return BasePage.houseDetailLocation(driver, "bookhousebtn");
        }
      });
    } catch (Exception e) {
      // 遇到找不到预约按钮，可能是收藏用例失败，需要再次执行firstmothod
      if (isException == 1) {
        FirstMethod();
        bookhousebtn = wait.until(new ExpectedCondition<WebElement>() {
          @Override public WebElement apply(WebDriver d) {
            return BasePage.houseDetailLocation(driver, "bookhousebtn");
          }
        });
      }
    }

    // 拿一下房源编号
    WebElement roomno = wait.until(new ExpectedCondition<WebElement>() {
      @Override public WebElement apply(WebDriver d) {
        return BasePage.houseDetailLocation(driver, "RoomNo");
      }
    });

    String expectedroomnoToString = roomno.getText().trim();
    // 拿一下出租类型+小区名+几居室(housemessage)
    String expectedhousemessage =
        BasePage.houseDetailLocation(driver, "housemessage").getText().trim();
    // 预期的房源价格（房源详情的）
    String expectedprice = BasePage.houseDetailLocation(driver, "price").getText();
    // 拿一下房东的姓名（用于在预约页面效验）
    WebElement houseownerName = wait.until(new ExpectedCondition<WebElement>() {
      @Override public WebElement apply(WebDriver d) {
        // 轮寻去拿
        UsualClass.swipe(driver, width / 2, height * 3 / 4, width / 2, height / 4, 500);
        return BasePage.houseDetailLocation(driver, "houseownerName");
      }
    });
    // 预期的房东姓名
    String expectedhouseownerName = houseownerName.getText();
    // 点击预约按钮
    bookhousebtn.click();
    // Try一下正常的流程，catch可能就是可能没有登录
    try {
      // 这个specialwait主要用于检查是否登录的
      AndroidDriverWait specialwait = new AndroidDriverWait(driver, 5);
      // 这块使用5秒钟的specialwait(如果在5秒钟内能拿到预约房源价格说明客户已登录状态)
      WebElement price = specialwait.until(new ExpectedCondition<WebElement>() {
        @Override public WebElement apply(WebDriver d) {
          return BasePage.bookHouseLocation(driver, "price");
        }
      });
    } catch (Exception e) {
      // 登录方法
      LogUtil.info("没有找到预约页面的标题，视为没有登录，现在开始登录");
      try {
        BasePage.simplelogin(driver, mobile, password);
      } catch (Exception e1) {
        WebElement rebookbtn = wait.until(new ExpectedCondition<WebElement>() {
          @Override public WebElement apply(WebDriver d) {
            return BasePage.houseDetailLocation(driver, "rebookbtn");
          }
        });
        if (rebookbtn != null) {
          String rebookmessage = rebookbtn.getText();
          LogUtil.info("已经预约过了");
          // 已经预约过的房源目前未处理，直接return了
          return;
          // checkandcancelBook(expecteddatechooseToString,expectedtimechooseToString,expectedroomnoToString);
        } else {
          LogUtil.info("既没有跳转至登录页，也没有已预约提示");
          AssertionUtil.AssertTrue(driver, false, "既没有跳转至登录页，也没有已预约提示");
          return;
        }
      }

      // 判断登录成功后页面是否直接跳转至帮我预约页面
      WebElement bookpagetitle = wait.until(new ExpectedCondition<WebElement>() {
        @Override public WebElement apply(WebDriver d) {
          return BasePage.bookHouseLocation(driver, "bookpagetitle");
        }
      });
      String bookpagetitleToString = bookpagetitle.getText();
      LogUtil.info("登录成功后，页面是否直接进预约页面，实际标题是：" + bookpagetitleToString + ",预期的标题是：帮我预约");
      AssertionUtil.AssertEquals(driver, bookpagetitle.getText(), "帮我预约", " 登录成功后未直接跳转预约页面");
    }
    // 页面跳转至预约页面，这块主要先校验预约的房源信息以及价格还有房东的姓名
    // 效验房源信息（出租类型+小区+几居室）
    String actualhousemessage =
        (BasePage.bookHouseLocation(driver, "renttype").getText() + BasePage.bookHouseLocation(
            driver, "roomdes").getText()).trim();
    LogUtil.info("预期的房源信息：" + expectedhousemessage + ",实际的房源信息" + actualhousemessage);
    AssertionUtil.AssertEquals(driver, actualhousemessage, expectedhousemessage,
        "预约页面的房源信息与刚进来的房源信息不一致");
    // 价格
    String actualprice = BasePage.bookHouseLocation(driver, "price").getText();
    LogUtil.info("预期价格：" + expectedprice + ",实际价格：" + actualprice);
    AssertionUtil.AssertEquals(driver, actualprice, expectedprice, "预约页面的房源价格与刚进来的房源价格不一致");
    // 房源发布人
    String actualhouseownerName = BasePage.bookHouseLocation(driver, "houseownerName").getText();
    LogUtil.info("预期房东名字：" + expectedhouseownerName + ",实际房东名字：" + actualhouseownerName);
    AssertionUtil.AssertEquals(driver, actualhouseownerName, expectedhouseownerName,
        "预约页面与房源详情页面房东名字不一致");

    // 开始实现预约流程
    TouchAction action = new TouchAction(driver);
    List<AndroidElement> choose = BasePage.LocationList(driver);
    AndroidElement datechoose = null;
    AndroidElement timechoose = null;
    if (choose.size() == 2) {
      datechoose = choose.get(0);
      timechoose = choose.get(1);
    } else {
      LogUtil.info("时间控件没拿到");
    }
    // 判断一下是否为空
    if (datechoose == null) {
      LogUtil.info("选择时间控件是空值，无法滑动");
    } else {
      // 滑动日期控件
      for (int i = 0; i < 2; i++) {
        // 滑动日期控件
        action.longPress(datechoose, Duration.ofSeconds(1))
            .moveTo(datechoose.getLocation().x, datechoose.getLocation().y - 300)
            .release()
            .perform();
      }
    }
    // 这个地方有一个动画效果存在一定延时
    try {
      Thread.sleep(10000);
    } catch (InterruptedException e1) {
      //
      e1.printStackTrace();
    }
    // 滑动后的时间是,作为预期的预约日期与时间
    String expecteddatechooseToString = datechoose.getText();
    String expectedtimechooseToString = timechoose.getText();
    LogUtil.info("滑动后的预约时间是" + expecteddatechooseToString + expectedtimechooseToString);
    // 点击 确认预约按钮
    BasePage.bookHouseLocation(driver, "confirmbook").click();
    // 页面返回房源详情页时需要拿到接口返回的预约成功与失败，所以这块需要加一个时序
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e1) {
      //
      e1.printStackTrace();
    }
    // 预约成功后页面返回房源详情页，再次点击预约按钮（覆盖一下重复预约）(需要拿一下弹框的提示)
    bookhousebtn.click();
    // 弹出已预约的弹窗
    String rebookmessage = BasePage.houseDetailLocation(driver, "rebookmessage").getText();
    LogUtil.info("重复预约的提示：" + rebookmessage);
    AssertionUtil.AssertEquals(driver, rebookmessage, "您已预约过了", "重复预约的提示文案错误");
    LogUtil.info("点击知道了按钮");
    BasePage.houseDetailLocation(driver, "rebookbtn").click();
    // 调用checkandcancelBook去检查预约的房源，并取消预约
    checkandcancelBook(expecteddatechooseToString, expectedtimechooseToString,
        expectedroomnoToString);
    // 程序执行到这一步时，默认未测试无异常
    isException = 0;
  }

  @AfterMethod public void afterMothod() {
    // 当用例执行异常时执行返回，否则无需返回
    if (isException == 1) {
      LogUtil.info("用例执行异常，返回首页中");
      UsualClass.back(driver);
    }
  }

  @AfterClass public void aftertest() {
    LogUtil.info("测试完毕执行afterclass");
    UsualClass.back(driver);
  }
}
