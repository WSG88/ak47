package appiumtest.tests;

import appiumtest.page.BasePage;
import appiumtest.utils.AndroidDriverWait;
import appiumtest.utils.AssertionListener;
import appiumtest.utils.AssertionUtil;
import appiumtest.utils.InitDriver;
import appiumtest.utils.LogUtil;
import appiumtest.utils.UsualClass;
import io.appium.java_client.android.AndroidDriver;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

/*
 * 这个首页测试类ceshi
 * 城市切换、banner点击以及滑动未完善）
 * 首页-按钮入口的测试方法已完善（主要测试一下整租、合租、公寓、视频找房、嗨住美家按钮入口）
 * 发布房源计划在发房测试类中完善
 */
@Listeners({ AssertionListener.class }) public class HomePageTest {
  private AndroidDriver driver;
  private AndroidDriverWait wait;

  @BeforeClass public void InitDriver() {
    driver = InitDriver.dr;
    wait = InitDriver.wait;
  }

  // 城市切换以及城市列表的校验
  @Test(enabled = false) public void CityTest() {
    // 启动等待(等待城市元素)
    try {
      wait.until(ExpectedConditions.presenceOfElementLocated(
          By.id("com.loulifang.house:id/tv_home_city_name")));
      System.out.println("城市定位成功，开始执行城市切换");
    } catch (NoSuchElementException e) {
      e.printStackTrace();
      System.out.println("城市定位失败");
    }

    // 取消存储权限（暂不）
    try {
      BasePage.findEBD(driver, "android:id/button2").isDisplayed();
      BasePage.findEBD(driver, "android:id/button2").click();
    } catch (Exception e) {
      System.out.println("没有找到暂不弹框");
    }

    // 获取首页城市定位
    WebElement city = BasePage.homeLocation(driver, "city");

    // 模拟点击首页城市按钮（页面跳转至选择城市页面）
    city.click();
    // 获取标题
    String title = BasePage.chooseCityLocation(driver, "title").getText();
    // 获取所有城市(并转string)
    List<WebElement> actualallcity = (List<WebElement>) BasePage.getAllCity(driver);
    List<String> actualallcity_String = new ArrayList<String>();
    for (WebElement e : actualallcity) {
      actualallcity_String.add(e.getText());
    }

    // 添加预期城市列表
    List<String> exceptallcity = new ArrayList<>();
    exceptallcity.add("上海");
    exceptallcity.add("北京");
    exceptallcity.add("杭州");
    exceptallcity.add("深圳");
    exceptallcity.add("广州");
    exceptallcity.add("南京");
    exceptallcity.add("郑州");
    exceptallcity.add("苏州");
    exceptallcity.add("武汉");
    exceptallcity.add("天津");

    // 验证页面标题
    try {
      Assert.assertEquals("选择城市", title);
    } catch (Exception e) {
      System.out.println("标题错误，期望：" + "选择城市,结果" + title);
    }

    // 断言所有的城市
    try {
      Assert.assertEquals(exceptallcity, actualallcity_String);
    } catch (Exception e) {
      System.out.println("城市列表错误，实际是");
      for (String s : actualallcity_String) {
        System.out.print(s + " ");
      }
    }
    // 初始城市页面（当前城市以及列表中的√的坐标）
    String actualcurrentcity = BasePage.chooseCityLocation(driver, "currentcity").getText();
    try {
      Assert.assertEquals("上海", actualcurrentcity);
    } catch (Exception e) {
      System.out.println("当前城市错误，实际是" + actualcurrentcity + "预期是：上海");
    }

    // 获取打钩的坐标(这块因为手机分辨率不一样后期做调整)
    Point bounds = BasePage.chooseCityLocation(driver, "Selectedsign").getLocation();
    try {
      Assert.assertEquals(969, bounds.x);
      Assert.assertEquals(541, bounds.y);
    } catch (Exception e) {
      System.out.println("勾选的城市位置错误,实际坐标:" + bounds.x + "," + bounds.y + "预期坐标：" + 969 + "," + 541);
    }

    // 模拟选择城市（点击北京）
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e1) {
      //
      e1.printStackTrace();
    }

    for (WebElement e : actualallcity) {
      if (e.getText().equals("北京")) {
        e.click();
      }
    }
    // 断言判定选择北京后在首页是否显示北京这个城市
    try {
      Assert.assertEquals("北京", BasePage.homeLocation(driver, "city").getText());
    } catch (Exception e) {
      System.out.println(
          "选择北京后返回首页文案显示错误，实际值：" + BasePage.homeLocation(driver, "city").getText() + "预期:北京");
    }
  }

  /**
   * 这个测试方法主要测试首页按钮（整租、合租、公寓、视频找房、嗨住美家） 目前程序逻辑是1:整租2：合租3：公寓4：发布房源5：视频房源
   * 6：嗨住美家（这块位置最好不要变）
   */
  @Test public void PagebuttonTest() {
    LogUtil.info("********************");
    LogUtil.info("现在开始测试首页各入口，点击首页各按钮");
    // 等待首页所有按钮（整租合租等）
    List<WebElement> homepagebutton = wait.until(new ExpectedCondition<List<WebElement>>() {
      @Override public List<WebElement> apply(WebDriver d) {
        return BasePage.homeListLocation(driver, "homepagebutton");
      }
    });
    LogUtil.info("首页按钮个数" + homepagebutton.size());
    // 首页按钮文案存在buttonText中
    List<String> buttonText = new ArrayList<>();
    // 拿到首页所有按钮的文案元素（整租、合租、公寓等）
    List<WebElement> buttonElement = BasePage.homeListLocation(driver, "homepagebuttontext");
    // 转为string类型
    if (buttonElement.size() == 0) {
      System.out.println("长度等于0");
    } else {
      for (WebElement e : buttonElement) {
        buttonText.add(e.getText());
        LogUtil.info("预期的按钮文案" + e.getText());
      }
    }
    // 模拟每个按钮点击，主要检查一下搜索的房源是否符合预期(i表示首页按钮的下标索引，这块可以处理一下哪些按钮跑与不跑)
    for (int i = 0; i < homepagebutton.size(); i++) {
      LogUtil.info("********************");
      // 这个button变量指每次循环到的那个按钮
      WebElement button = null;
      // 如果i==5是发布房源按钮，我这边先continue
      if (homepagebutton.size() == 6) {
        if (i == homepagebutton.size() - 1) {
          LogUtil.info("发布房源先跳过");
          continue;
        }
        //如果7个图标则跳过最后的2个按钮（懒人找房、发布房源）
      } else if (homepagebutton.size() == 7) {
        if (i == homepagebutton.size() - 1 || i == homepagebutton.size() - 2) {
          LogUtil.info("发布房源、懒人找房先跳过");
          continue;
        }
      }
      //记录日志当前点击的按钮以及按钮的名称
      LogUtil.info("点击第" + (i + 1) + "个按钮" + buttonText.get(i));
      // 这块需要加个等待，保证这个按钮能点击，否则执行click没有效果
      button = wait.until(ExpectedConditions.elementToBeClickable(homepagebutton.get(i)));
      // 循环点击
      button.click();
      // 等待房源列表（等待出租类型）
      List<WebElement> renttype = wait.until(new ExpectedCondition<List<WebElement>>() {
        @Override public List<WebElement> apply(WebDriver d) {
          return BasePage.SearchResultListLocation(driver, "renttype");
        }
      });
      // 等待更多按钮
      WebElement more = wait.until(new ExpectedCondition<WebElement>() {
        @Override public WebElement apply(WebDriver d) {
          return BasePage.SearchResultLocation(driver, "more");
        }
      });
      // 拿到更多筛选处
      String moretostring = more.getText();
      /**
       * 下方代码主要处理各种按钮情况所所有出来的房源的断言
       */
      // 整租断言以及逻辑
      if (i == 0) {
        // 预期搜索结果就是从首页按钮文案来
        String expectedscreen = buttonText.get(0);
        LogUtil.info("搜索结果页，应该默认选中" + expectedscreen + "，实际是：" + moretostring);
        AssertionUtil.AssertEquals(driver, moretostring, expectedscreen, expectedscreen + "未被选中");
        // 拿到出去类型进行比较（是否整租的房源）(把后面的点去掉)
        String actualscreen = null;
        for (WebElement Element : renttype) {
          actualscreen = Element.getText().substring(0, 2);
          AssertionUtil.AssertEquals(driver, actualscreen, expectedscreen,
              "有房源不是" + expectedscreen + "类型的");
          LogUtil.info("搜索出来的房源出租类型是：" + actualscreen);
        }
        // 点击返回按钮至首页
        BasePage.SearchResultLocation(driver, "searchback").click();
        // 合租断言以及逻辑
      } else if (i == 1) {
        String expectedscreen = buttonText.get(1);
        LogUtil.info("搜索结果页，应该默认选中" + expectedscreen + "，实际是：" + moretostring);
        AssertionUtil.AssertEquals(driver, moretostring, expectedscreen, expectedscreen + "未被选中");
        // 拿到出去类型进行比较（是否整租的房源）(把后面的点去掉)
        String actualscreen = null;
        for (WebElement Element : renttype) {
          actualscreen = Element.getText().substring(0, 2);
          AssertionUtil.AssertEquals(driver, actualscreen, expectedscreen,
              "有房源不是" + expectedscreen + "类型的");
          LogUtil.info("搜索出来的房源出租类型是：" + actualscreen);
        }
        // 点击返回按钮至首页
        BasePage.SearchResultLocation(driver, "searchback").click();
        // 公寓断言以及逻辑
      } else if (i == 2) {
        LogUtil.info("公寓这块目前是ab测试，先continue");
        // 点击返回按钮至首页
        BasePage.SearchResultLocation(driver, "searchback").click();
      } else if (i == 3 || i == 4) {
        // 由于视频找房那个更多筛选是“实拍视频”与首页的按钮并不一致，所以自己定义一个变量作为预期
        if (i == 4) {
          // 这块校验搜索关键字
          final String expectedscreen = "实拍视频";
          LogUtil.info("搜索结果页，预期默认选中，" + expectedscreen);
          LogUtil.info("实际是：" + moretostring);
          AssertionUtil.AssertEquals(driver, moretostring, expectedscreen, expectedscreen + "未被选中");
          // 嗨住美家筛选关键字与首页按钮是一致的，都是嗨住美家
        } else if (i == 3) {
          // 这块校验搜索关键字
          String expectedscreen = buttonText.get(3);
          LogUtil.info("搜索结果页，预期默认选中，" + expectedscreen);
          LogUtil.info("实际是：" + moretostring);
          AssertionUtil.AssertEquals(driver, moretostring, expectedscreen, expectedscreen + "未被选中");
        }
        // 这块校验搜索的房源的标签（视频找房与嗨住美家一致，都是看房源标签，拿前三个的标签遍历是否大于等于3）
        // 定义一个计数变量
        int tagcounts = 0;
        // 检查筛选的房源是否有“视频房源”以及“嗨住美家”，这块检查一下集中公寓是否>=3,首先拿到所有的房源标签
        List<WebElement> tags = BasePage.SearchResultListLocation(driver, "tags");
        for (WebElement tag : tags) {
          // 判定实际拿到的tags有几个是符合预期的（预期是从筛选结果的更多筛选关键字，对于变量是moretostring）
          LogUtil.info("房源标签是" + tag.getText());
          if (tag.getText().equals(moretostring)) {
            tagcounts++;
            LogUtil.info("出现了" + tagcounts + "次" + moretostring + "标签");
          }
        }
        // 一屏一般能显示3个房（目前这块先取3个吧）
        AssertionUtil.AssertTrue(driver, tagcounts >= 3, "预期是3个，实际是" + tagcounts + "个");
        // 点击返回按钮至首页
        BasePage.SearchResultLocation(driver, "searchback").click();
      } else {
        LogUtil.error("还没有覆盖此按钮，目前只有整租、合租、公寓、视频找房、嗨住美家");
      }
    }
  }

  @AfterClass public void afterClass() {
    UsualClass.back(driver);
    // driver.quit();
  }
}
