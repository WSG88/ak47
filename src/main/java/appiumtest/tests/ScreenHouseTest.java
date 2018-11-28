package appiumtest.tests;

import appiumtest.page.BasePage;
import appiumtest.utils.AndroidDriverWait;
import appiumtest.utils.AssertionListener;
import appiumtest.utils.AssertionUtil;
import appiumtest.utils.InitDriver;
import appiumtest.utils.LogUtil;
import appiumtest.utils.UsualClass;
import appiumtest.utils.XlsUtil;
import io.appium.java_client.android.AndroidDriver;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

/**
 * 这块筛选主要测试一下app端筛选器以及房源数据是否正确 注意：每个test断言完后，需要回到筛选结果页 附近房源的筛选以及不限筛选未完善
 * 筛选地铁线、附近、地铁站在房源列表效验，区域商圈进房源详情里面校验字段（同搜索）
 * 筛选不限（目前与筛选附近房源做了关联关系）时，则通过点击若干个房源进入房源详情拿到区域板块再比对是否
 */
@Listeners({ AssertionListener.class }) public class ScreenHouseTest {
  private AndroidDriver driver;
  private AndroidDriverWait wait = null;

  @BeforeClass public void InitDriver() {
    driver = InitDriver.dr;
    wait = InitDriver.wait;
    // 在@test之前计划将页面到达搜索结果页再进行各个test操作
    LogUtil.info("开始筛选前先进入搜索结果页");
    // 尝试拿到搜索所有按钮，然后点击整租按钮
    // 等待首页所有按钮（整租合租等）
    List<WebElement> homepagebutton = wait.until(new ExpectedCondition<List<WebElement>>() {
      @Override public List<WebElement> apply(WebDriver d) {
        return BasePage.homeListLocation(driver, "homepagebutton");
      }
    });
    LogUtil.info("首页按钮个数" + homepagebutton.size() + "点击第一个进入搜索结果页");
    // 点击首页第一个按钮（整租）（确保第一个按钮可点击）
    WebElement button = homepagebutton.get(0);
    button.click();
  }

  // beforeclass已到搜索结果页
  // 地铁线筛选
  @Test(priority = 1) public void ScreenHouseTest() {
    LogUtil.info("现在开始测试搜索房源");
    // 拿取excel的数据
    List<XlsUtil.ScreenHouseDataObject> ScreenHouseData = XlsUtil.readScreenHouseData();
    // 显示等待区域筛选(region是区域的下拉按钮)
    WebElement Region = wait.until(new ExpectedCondition<WebElement>() {
      @Override public WebElement apply(WebDriver d) {
        return BasePage.SearchResultLocation(driver, "Region");
      }
    });
    // 点击区域筛选下拉
    Region.click();
    // 去获取一下地铁（筛选下拉）
    List<WebElement> RegionList = BasePage.SearchResultListLocation(driver, "RegionList");
    List<String> RegionListToString = new ArrayList<>();
    // 遍历一下，并转为string
    for (WebElement e : RegionList) {
      RegionListToString.add(e.getText());
      LogUtil.info("实际的区域下拉列表是：" + e.getText());
    }
    // 预期的区域下拉
    List<String> ExpectedRegionList = new ArrayList<>();
    ExpectedRegionList.add("不限");
    ExpectedRegionList.add("附近");
    ExpectedRegionList.add("商圈");
    ExpectedRegionList.add("地铁");
    // 断言（查看一下区域筛选下拉选项）
    AssertionUtil.AssertEquals(driver, RegionListToString, ExpectedRegionList, "区域下拉列表显示错误");
    // 遍历list数据，获取list数据中对象的搜索类型做不同的校验
    for (int i = 0; i < ScreenHouseData.size(); i++) {
      if (i != 0) {
        // 第一次前面代码已经点开了区域，所以这块需要处理一下
        Region = wait.until(new ExpectedCondition<WebElement>() {
          @Override public WebElement apply(WebDriver d) {
            return BasePage.SearchResultLocation(driver, "Region");
          }
        });
        // 点击区域，显示区域筛选下拉
        Region.click();
      }
      String ScreenNo = ScreenHouseData.get(i).getScreenNo();
      LogUtil.info("现在开始筛选，筛选类型："
          + ScreenNo
          + "筛选地铁线是："
          + ScreenHouseData.get(i).getRailway_Region()
          + "筛选的地铁站是"
          + ScreenHouseData.get(i).getRailstation_Area());
      // 1、表示地铁线与地铁站筛选2、表示区域商圈筛选
      if (ScreenNo.equals("1") || ScreenNo.equals("2")) {
        if (ScreenNo.equals("1")) {
          // 点击地铁（进行地铁的筛选）（点击区域下拉列表最后一个）
          RegionList.get(RegionList.size() - 1).click();
        } else {
          // 点击区域（进行区域的筛选）（点击区域下拉列表最后第二个）
          RegionList.get(RegionList.size() - 2).click();
        }
      } else {
        LogUtil.error("暂时还没有这个筛选,只有地铁与区域");
      }
      // 点击地铁线
      driver.findElement(By.name(ScreenHouseData.get(i).getRailway_Region())).click();
      // railwayStations表示通过id定位到的地铁站或者商圈
      List<WebElement> railwayStations =
          BasePage.SearchResultListLocation(driver, "railwayStation");
      // 如果搜索数据地铁站不在第一屏，实现轮寻滑动
      boolean isfind = false;
      for (WebElement Element : railwayStations) {
        if (Element.getText().equals(ScreenHouseData.get(i).getRailstation_Area())) {
          wait.until(ExpectedConditions.elementToBeClickable(Element));
          Element.click();
          isfind = true;
          break;
        }
      }
      // 如果第一屏找不到,滑动再找
      while (isfind == false) {
        int width = driver.manage().window().getSize().width;
        int height = driver.manage().window().getSize().height;
        UsualClass.swipe(driver, width / 2, height * 3 / 4, width / 2, height / 2, 500);
        railwayStations = BasePage.SearchResultListLocation(driver, "railwayStation");
        for (WebElement Element : railwayStations) {
          if (Element.getText().equals(ScreenHouseData.get(i).getRailstation_Area())) {
            wait.until(ExpectedConditions.elementToBeClickable(Element));
            Element.click();
            isfind = true;
            break;
          }
        }
      }
      // 筛选后的区域筛选器文案
      Region = wait.until(new ExpectedCondition<WebElement>() {
        @Override public WebElement apply(WebDriver d) {
          return BasePage.SearchResultLocation(driver, "Region");
        }
      });
      String RegionToString = Region.getText();

      // 断言：需要分（比如筛选1号线，区域筛选那则显示“1号线”，筛选1-上海马戏城则显示上海马戏城）包括筛选的房源效验
      if (ScreenHouseData.get(i).getRailstation_Area().equals("不限")) {
        // 获取筛选后区域筛选按钮的文案
        LogUtil.info("断言日志记录：筛选的是地铁线或者行政区，预期是"
            + ScreenHouseData.get(i).getRailway_Region()
            + ",实际是："
            + RegionToString);
        AssertionUtil.AssertEquals(driver, RegionToString,
            ScreenHouseData.get(i).getRailway_Region(), "区域筛选器文案未更改");
      } else {
        LogUtil.info("断言日志记录：筛选的是地铁站或者是商圈，预期是"
            + ScreenHouseData.get(i).getRailstation_Area()
            + ",实际是："
            + RegionToString);
        AssertionUtil.AssertEquals(driver, RegionToString,
            ScreenHouseData.get(i).getRailstation_Area(), "区域筛选器文案未更改");
      }
      // 断言筛选出来的房源数据,如果是地铁线或者地铁站的时候直接比对房源列表
      if (ScreenNo.equals("1")) {
        List<WebElement> init_railway = new ArrayList<>();
        List<String> actual_railway = new ArrayList<>();
        int height = driver.manage().window().getSize().getHeight();
        int weight = driver.manage().window().getSize().getWidth();
        // 循环拿3页数据
        for (int j = 0; j < 3; j++) {
          init_railway = wait.until(new ExpectedCondition<List<WebElement>>() {
            @Override public List<WebElement> apply(WebDriver d) {
              return BasePage.SearchResultListLocation(driver, "railway");
            }
          });
          // 遍历拿到的init_railway地铁信息
          for (WebElement Element : init_railway) {
            actual_railway.add(Element.getText());
          }
          // 往下滑动页面再抓
          UsualClass.swipe(driver, weight / 2, height * 9 / 10, weight / 2, height / 10, 500);
        }
        // 拿到的地铁线actual_railway与区域筛选器的文案RegionToString做contins操作
        for (String actual : actual_railway) {
          LogUtil.info("预期筛选的地铁是" + RegionToString + ",实际拿到的地铁位置信息是" + actual);
          AssertionUtil.AssertTrue(driver, actual.contains(RegionToString),
              "房源下的地铁信息显示有误,实际是：" + actual + "预期时：" + RegionToString);
        }
      } else if (ScreenNo.equals("2")) {
        // houselist房源列表，后期选择随机点击进入房源详情
        List<WebElement> houselist = new ArrayList<>();
        // 循环5页进行随机进入房源详情
        for (int j = 0; j < 3; j++) {

          houselist = wait.until(new ExpectedCondition<List<WebElement>>() {
            @Override public List<WebElement> apply(WebDriver d) {
              return BasePage.SearchResultListLocation(driver, "houseitem");
            }
          });
          // 每次拿到houselist然后进行随机进入房源详情
          // 生成随机数
          int random_number = (int) (Math.random() * houselist.size());
          if (random_number == 0) {
            random_number = 1;
          }
          LogUtil.info("houselist长度" + houselist.size() + "随机数" + random_number);
          // 点击房源
          houselist.get(random_number).click();
          // 这块有个问题，就是还未跳转至房源详情就开始滑动会报错（这块我先拿个价格作为进入房源详情的依据）
          WebElement price = wait.until(new ExpectedCondition<WebElement>() {
            @Override public WebElement apply(WebDriver arg0) {
              return BasePage.houseDetailLocation(driver, "price");
            }
          });
          // 房源详情页进行轮寻（地址：包括行政区商圈）
          WebElement address = wait.until(new ExpectedCondition<WebElement>() {
            @Override public WebElement apply(WebDriver arg0) {

              // 获取屏幕高与宽
              int width = driver.manage().window().getSize().width;
              int height = driver.manage().window().getSize().height;
              // 尝试屏幕往下滑动
              UsualClass.swipe(driver, width / 2, height * 3 / 4, width / 2, height / 2, 500);
              LogUtil.info("scroll=======" + height * 3 / 4 + " " + height / 4);
              return BasePage.houseDetailLocation(driver, "address");
            }
          });
          // 将房源详情页拿到的地址记录日志
          String actualaddress = address.getText();
          LogUtil.info("地址成功拿到:" + actualaddress);
          // 将房源详情页拿到的actualaddress进行分割
          String[] AddressToString = actualaddress.split("-");
          // 这块关于筛选区域与商圈做个分开的效验（分区域与商圈）
          if (ScreenHouseData.get(i).getRailstation_Area().equals("不限")) {
            // 房源详情拿到的实际值与筛选器区域上的文案做对比（行政区断言）
            LogUtil.info("预期的区域：" + RegionToString + ",实际的区域：" + AddressToString[0].trim());
            AssertionUtil.AssertEquals(driver, AddressToString[0].trim(), RegionToString,
                "房源详情显示的区域与搜索的不符");
          } else {
            // 商圈断言
            LogUtil.info("预期的商圈：" + RegionToString + ",实际的商圈：" + AddressToString[1].trim());
            AssertionUtil.AssertEquals(driver, AddressToString[1].trim(), RegionToString,
                "房源详情显示的商圈与搜索的不符");
          }
          // 从房源详情页 返回房源列表页
          BasePage.houseDetailLocation(driver, "back").click();
          // 往下滑动两屏
          int width = driver.manage().window().getSize().width;
          int height = driver.manage().window().getSize().height;
          //这块有时存在滑动问题
          try {
            Thread.sleep(1000);
          } catch (InterruptedException e1) {
            //
            e1.printStackTrace();
          }
          for (int k = 0; k < 2; k++) {
            UsualClass.swipe(driver, width / 2, height * 9 / 10, width / 2, height / 10, 500);
          }
        }
      } else {
        LogUtil.error("只有地铁以及区域的断言");
      }
      /**
       * 租金筛选（筛选需要时间，这块需要加显示等待） 排序切换 这两块后期再做 （数据表excel与ScreenHouseDataObject类中也已经加了字段）
       */

    }
  }

  // 附近房源（可以按照房源列表上的距离判定）（未完成）
  @Test(priority = 2) public void NearHouseTest() {
    LogUtil.info("现在测试筛选附近房源");
    // 点击选择附近房源筛选
    WebElement Region = wait.until(new ExpectedCondition<WebElement>() {
      @Override public WebElement apply(WebDriver d) {
        return BasePage.SearchResultLocation(driver, "Region");
      }
    });
    // 点击区域筛选下拉
    Region.click();
    // 点击附近房源(先拿到区域下拉)
    List<WebElement> RegionList = BasePage.SearchResultListLocation(driver, "RegionList");
    // 点击区域下拉的第二个按钮（附近）
    RegionList.get(1).click();
    // 点击1千米，获取距离列表
    WebElement expectedscreen = BasePage.SearchResultListLocation(driver, "railwayStation").get(1);
    String expectedscreentoString = expectedscreen.getText();
    LogUtil.info("预期筛选器文案：" + expectedscreentoString);
    expectedscreen.click();
    // 先判定区域筛选器文案
    Region = wait.until(new ExpectedCondition<WebElement>() {
      @Override public WebElement apply(WebDriver d) {
        return BasePage.SearchResultLocation(driver, "Region");
      }
    });
    String actualscreentoString = Region.getText();
    LogUtil.info("实际筛选器文案：" + actualscreentoString);
    AssertionUtil.AssertEquals(driver, actualscreentoString, expectedscreentoString, "筛选器文案有误");

    //筛选的数据校验
    List<WebElement> init_distances = new ArrayList<>();
    List<String> actual_distances = new ArrayList<>();
    int height = driver.manage().window().getSize().getHeight();
    int weight = driver.manage().window().getSize().getWidth();
    // 循环拿3页数据
    for (int j = 0; j < 3; j++) {
      init_distances = wait.until(new ExpectedCondition<List<WebElement>>() {
        @Override public List<WebElement> apply(WebDriver d) {
          return BasePage.SearchResultListLocation(driver, "railway");
        }
      });
      // 遍历拿到的init_distances距离信息
      for (WebElement Element : init_distances) {
        actual_distances.add(Element.getText());
      }
      // 往下滑动页面再抓
      UsualClass.swipe(driver, weight / 2, height * 9 / 10, weight / 2, height / 10, 500);
    }
    // 拿到的地铁线actual_railway与区域筛选器的文案RegionToString做contins操作
    if (actual_distances.size() > 0) {
      for (String actual : actual_distances) {
        //拿到距离之后进行分割一下，取出里面的距离数字
        int actualnumber =
            Integer.valueOf(actual.substring(actual.indexOf("您") + 1, actual.indexOf("米")));
        LogUtil.info("实际的距离值：" + actualnumber);
        AssertionUtil.AssertTrue(driver, actualnumber <= 1000,
            "筛选的房源距离有大于1000的情况，实际是：" + actualnumber);
      }
    } else {
      LogUtil.info("未拿到距离字段");
    }
  }

  //不限的筛选依赖于附近房源
  @Test(priority = 3, dependsOnMethods = { "NearHouseTest" })
  // 不限筛选
  public void UnlimitedHouseTest() {
    LogUtil.info("现在测试筛选不限");
    WebElement Region = wait.until(new ExpectedCondition<WebElement>() {
      @Override public WebElement apply(WebDriver d) {
        return BasePage.SearchResultLocation(driver, "Region");
      }
    });
    // 点击区域筛选下拉
    Region.click();
    // 点击不限筛选按钮(先拿到区域下拉)
    List<WebElement> RegionList = BasePage.SearchResultListLocation(driver, "RegionList");
    //点击第一个“不限”
    RegionList.get(0).click();
    //校验筛选器文案
    Region = wait.until(new ExpectedCondition<WebElement>() {
      @Override public WebElement apply(WebDriver d) {
        return BasePage.SearchResultLocation(driver, "Region");
      }
    });
    String actualscreentoString = Region.getText();
    LogUtil.info("实际筛选器文案：" + actualscreentoString);
    AssertionUtil.AssertEquals(driver, actualscreentoString, "区域", "筛选器文案有误");

    //筛选数据校验断言（直接效验房源列表地址处是否显示距您多少米）
    List<WebElement> init_address = new ArrayList<>();
    init_address = wait.until(new ExpectedCondition<List<WebElement>>() {
      @Override public List<WebElement> apply(WebDriver d) {
        return BasePage.SearchResultListLocation(driver, "railway");
      }
    });

    for (WebElement actual : init_address) {
      //遍历一下拿到的地址中是否存在“距您”
      String actualToString = actual.getText();
      LogUtil.info("筛选不限后，地址显示：" + actualToString);
      AssertionUtil.AssertTrue(driver, !actualToString.contains("距您"),
          "筛选了不限发现还是显示：" + actualToString);
    }
  }

  //这块为了防止筛选中存在错误（断言错误），而未回到房源搜索结果页，从而导致了影响下面的测试
  //（比如筛选第一个用例有误后，如果没有返回搜索结果页就会影响附近房源test）
  @AfterMethod public void afterMothod() {
    System.out.println("每次执行完test执行返回搜索结果页操作");

    WebElement searchText = BasePage.SearchResultLocation(driver, "searchText");
    while (searchText == null) {
      driver.pressKeyCode(4);
      try {
        Thread.sleep(3000);
      } catch (InterruptedException e) {
        //
        e.printStackTrace();
      }
      searchText = BasePage.SearchResultLocation(driver, "searchText");
    }
  }

  @AfterClass public void afterClass() {

    UsualClass.back(driver);
  }
}
