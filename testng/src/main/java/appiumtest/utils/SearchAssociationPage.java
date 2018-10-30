package appiumtest.utils;

import io.appium.java_client.android.AndroidDriver;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * 搜索联想页定位（单元素、元素列表）
 * 搜索房源（搜索1、地铁线2、小区3、品牌4、行政区5、商圈 6、地标）这块主要从excel中读取数据
 *
 */
public class SearchAssociationPage {

  // 日志对象
  static Logger log = LoggerFactory.getLogger("SearchAssociationPage");

  // 单个元素定位（例如按钮输入框等）
  public static WebElement Location(AndroidDriver dr, String item) {
    try {
      switch (item) {
        // 搜索输入框(发布房源搜索小区同首页搜索输入框)
        case "SearchEdit":
          WebElement SearchEdit = dr.findElement(By.id("com.loulifang.house:id/searchEdit"));
          return SearchEdit;
        // 搜索附近房源按钮
        case "Nearhouse":
          WebElement Nearhouse = dr.findElement(By.id("com.loulifang.house:id/llt_loc_bar"));
          return Nearhouse;
        // 取消按钮（可回到首页）
        case "cancelbutton":
          WebElement cancelbutton = dr.findElement(By.id("com.loulifang.house:id/cancelBtn"));
          return cancelbutton;
        default:
          return null;
      }
    } catch (NoSuchElementException e) {
      log.info("等待" + item + "元素");
      return null;
    }
  }

  /**
   * 定位所有的head（例如小区商圈区域等） 定位所有的textview(例如：新梅共和城或者地标中的名称等)
   * 定位所有的address（搜索联想页中所有的地图） 考虑是需要拿到元素进行点击，文案的校验就测试类转String
   */
  public static List<WebElement> LocationList(AndroidDriver dr, String item) {
    try {
      switch (item) {
        // 所有小区商圈等定位
        case "head":
          WebElement single_head = dr.findElement(By.id("com.loulifang.house:id/headNo"));
          List<WebElement> heads = dr.findElements(By.id("com.loulifang.house:id/headNo"));
          return heads;
        // 所有小区名地铁名等定位
        case "textview":
          WebElement single_textview = dr.findElement(By.id("com.loulifang.house:id/textView"));
          List<WebElement> textview = dr.findElements(By.id("com.loulifang.house:id/textView"));
          return textview;
        // 定位所有地址
        case "address":
          WebElement single_address =
              dr.findElement(By.id("com.loulifang.house:id/textViewAddress"));
          List<WebElement> address =
              dr.findElements(By.id("com.loulifang.house:id/textViewAddress"));
          return address;
        default:
          return null;
      }
    } catch (NoSuchElementException e) {
      log.info("等待" + item + "元素");
      return null;
    }
  }

  /**
   * 这个方法兼容所有类型的搜索 如果搜索类型是地铁线与小区可以在房源列表中进行断言，如果是行政区以及商圈需要进入房源详情进行断言（这块已经做了相应的校验）
   * 主流程做到点击搜索联想词第一个，然后在搜索结果中开始分支写判定逻辑（分支从显示等待houselist房源列表开始）
   *
   * @param searchitem 搜索内容
   * @param type 搜素类型（地铁线type=1、小区type=2、品牌type=3、行政区type=4、商圈type=5、、地标type=6）
   */
  public static void WholesSarch(final AndroidDriver driver, AndroidDriverWait wait) {
    log.info("开始测试搜索");
    // 写死一个map用于存储搜索关键字以及搜索类型
    List<SearchHouseDataObject> SearchData = new ArrayList<>();
    // 拿到excel配置的数据
    SearchData = ReadExcel.readSearchHouseDate();
    if (!SearchData.isEmpty()) {
      log.info("搜索数据已成功传递到测试类中");
    } else {
      log.info("搜索数据传递失败");
    }

    // (开始执行搜索操作)等待底部首页按钮
    WebElement homepagebutton = wait.until(new ExpectedCondition<WebElement>() {
      @Override public WebElement apply(WebDriver d) {
        return HomePage.Location(driver, "homepagebutton");
      }
    });
    // 点击底部首页按钮（确保他在首页）
    homepagebutton.click();
    // 定位搜索输入框
    WebElement Searchbox = HomePage.Location(driver, "Searchbox");
    // 点击首页输入框
    Searchbox.click();
    // 等待搜索输入框
    WebElement SearchEdit = wait.until(new ExpectedCondition<WebElement>() {
      @Override public WebElement apply(WebDriver arg0) {

        return SearchAssociationPage.Location(driver, "SearchEdit");
      }
    });
    /**
     * 遍历map，进行不同类型的搜索
     */
    //		List<Integer> searchtype = new ArrayList<>();
    //		searchtype = SearchData.keySet();
    for (SearchHouseDataObject obj : SearchData) {
      //获取搜索类型typeno
      int typeno = obj.getSearchtype();
      // 搜索关键字
      String searchkey = obj.getSearchkeys();
      log.info("搜索关键字" + searchkey);
      SearchEdit.sendKeys(searchkey);
      // 点击联想页中的第一个
      List<WebElement> textview = new ArrayList<>();
      textview = wait.until(new ExpectedCondition<List<WebElement>>() {
        @Override public List<WebElement> apply(WebDriver dr) {

          return SearchAssociationPage.LocationList(driver, "textview");
        }
      });
      /**
       * 这块存在偶现点击搜索联想页中第一个无响应的问题 尝试解决问题，强行线程停止3秒
       */
      try {
        Thread.sleep(3000);
      } catch (InterruptedException e1) {
        //
        e1.printStackTrace();
      }
      // 点击第一个搜索联想词(确保一下第一个按钮能点击)
      WebElement currentclick = null;
      try {
        currentclick = wait.until(ExpectedConditions.elementToBeClickable(textview.get(0)));
      } catch (Exception e) {
        try {
          Thread.sleep(3000);
        } catch (InterruptedException e1) {
          //
          e1.printStackTrace();
        }
      }
      currentclick.click();
      // 根据搜索的类型做不同的校验
      if (typeno == 1) {
        log.info("※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※");
        log.info("搜索地铁线" + searchkey + "号线");
        // 等待搜索结果（拿到railway对象）
        List<WebElement> RailwayList = new ArrayList<>();
        RailwayList = wait.until(new ExpectedCondition<List<WebElement>>() {
          @Override public List<WebElement> apply(WebDriver dr) {

            return SearchResult.LocationList(driver, "railway");
          }
        });
        // new一个set对象用于去重
        Set<Integer> railwayset = new HashSet<>();
        // 遍历railwaylist提取地铁线
        String RailwayTostring = null;
        try {
          for (WebElement element : RailwayList) {
            RailwayTostring = element.getText();
            int RailwayNo = Integer.valueOf(
                RailwayTostring.substring(RailwayTostring.indexOf("离") + 1,
                    RailwayTostring.indexOf("号")));
            railwayset.add(RailwayNo);
          }
        } catch (Exception e) {
          e.printStackTrace();
          log.error("抓取的实际地铁线失败" + RailwayTostring);
          // MyAssertold.asserttrue(driver, false);
          MyAssertion.AssertTrue(driver, false, "抓取的实际地铁线失败");
        }
        // 断言（当railwayset里面有2个或者2个以上的地铁线，说明就是有问题）
        if (railwayset.size() == 1) {
          int RailwayNo = 0;
          for (int a : railwayset) {
            RailwayNo = a;
          }
          // 期望的地铁线
          int expectedrailway = 0;
          try {
            expectedrailway = Integer.valueOf(obj.getSearchkeys());
          } catch (Exception e) {
            log.info("读取数据表里面的地铁线信息，只要写数字，无需写号线，实际是" + obj.getSearchkeys());
            continue;
          }

          // MyAssertold.asserttrue(driver, RailwayNo == expectedrailway);
          MyAssertion.AssertEquals(driver, RailwayNo, expectedrailway, "搜索出来的地铁线与预期的不符");
        } else {
          MyAssertion.AssertTrue(driver, false, "存在多个地铁线");
        }
        // 返回搜索联想页并清空搜索输入框
        SearchResult.Location(driver, "searchback").click();
        UsualClass.cleartext(driver, Location(driver, "SearchEdit").getText());
        // 如果搜索小区
      } else if (typeno == 2) {
        log.info("※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※");
        // 等待搜索结果（拿到community小区对象）
        log.info("开始搜索小区:" + searchkey);
        List<WebElement> community = new ArrayList<>();

        community = wait.until(new ExpectedCondition<List<WebElement>>() {
          @Override public List<WebElement> apply(WebDriver dr) {

            return SearchResult.LocationList(driver, "community");
          }
        });

        log.info("定位到的小区个数：" + community.size());
        // 需要处理一下拿到的community的list（将拿到的coounity进行分割一下拿出小区名）
        String[] Actual_Community = new String[community.size()];
        for (int i = 0; i < community.size(); i++) {
          Actual_Community[i] = community.get(i)
              .getText()
              .substring(0, community.get(i).getText().indexOf("·"))
              .trim();
          log.info("小区：" + Actual_Community[i]);
        }
        // 判定搜索的小区名字是不是都是预期(先判定搜索结果是不是未空)
        if (Actual_Community.length <= 0) {
          log.error("未搜索到房源,Actual_Community小区数组的长度是" + Actual_Community.length);
          // MyAssertold.asserttrue(driver, false);
          MyAssertion.AssertTrue(driver, false, "搜索房源无结果");
        } else {
          for (int i = 0; i < Actual_Community.length; i++) {
            // MyAssertold.assertequal(driver, searchkey, Actual_Community[i]);
            MyAssertion.AssertEquals(driver, Actual_Community[i], searchkey, "搜索出来的地铁线与预期的不符");
          }
        }
        // 返回搜索联想页并青客搜索输入框
        SearchResult.Location(driver, "searchback").click();
        UsualClass.cleartext(driver, Location(driver, "SearchEdit").getText());

        // 搜索品牌类型
      } else if (typeno == 3) {
        log.info("※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※");
        log.info("开始测试品牌:" + searchkey);
        // 等待搜索结果（拿到taglist对象）
        List<WebElement> taglist = new ArrayList<>();

        taglist = wait.until(new ExpectedCondition<List<WebElement>>() {
          @Override public List<WebElement> apply(WebDriver dr) {

            return SearchResult.LocationList(driver, "tags");
          }
        });

        int countbrand = 0;
        // 判断(把搜索的相关品牌个数拿出来)
        if (taglist.size() <= 0) {
          log.error("没有标签");
        } else {
          for (WebElement tag : taglist) {
            if (tag.getText().equals(searchkey)) {
              countbrand++;
            }
          }
          log.info("一共发现" + countbrand + "个" + searchkey);
        }
        // 断言：（判断搜索的品牌是不是每个房源都带上）(一屏一般至少显示3个房源)
        // MyAssertold.asserttrue(driver, countbrand >= 3);
        MyAssertion.AssertTrue(driver, countbrand >= 3, "搜索房源其中有一个不是搜索的品牌");
        // 返回搜索联想页并青客搜索输入框
        SearchResult.Location(driver, "searchback").click();
        UsualClass.cleartext(driver, Location(driver, "SearchEdit").getText());

        // 当搜索的是区域商圈时，这块都需要点击进入房源详情页去校验
      } else if (typeno == 4 || typeno == 5) {
        if (typeno == 4) {
          log.info("※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※");
          log.info("现在开始测试区域" + searchkey);
        } else {
          log.info("※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※");
          log.info("现在开始测试版块" + searchkey);
        }
        // 等待房源列表
        List<WebElement> houselist = new ArrayList<>();
        // 拿到houselist房源列表
        houselist = wait.until(new ExpectedCondition<List<WebElement>>() {
          @Override public List<WebElement> apply(WebDriver dr) {

            return SearchResult.LocationList(driver, "houseitem");
          }
        });
        int counts = houselist.size();
        log.info("房源数量——" + counts);
        // 点击第一个房源进入房源详情
        houselist.get(0).click();
        log.info("点击房源列表中的第一个，进入房源详情,尝试寻找地址元素");
        // 这块存在房源详情页未加载完导致无法滑动情况（这块先尝试拿一下价格再尝试滑动）
        WebElement price = wait.until(new ExpectedCondition<WebElement>() {
          @Override public WebElement apply(WebDriver arg0) {
            return HouseDetail.Location(driver, "price");
          }
        });
        // 滑动页面至指定的位置(已实现动态寻找对象)
        WebElement address = wait.until(new ExpectedCondition<WebElement>() {
          @Override public WebElement apply(WebDriver arg0) {

            int width = driver.manage().window().getSize().width;
            int height = driver.manage().window().getSize().height;
            // 这块有个问题，就是还未跳转至房源详情就开始滑动会报错（这块我先拿个价格作为进入房源详情的依据）
            HouseDetail.Location(driver, "price");
            // 尝试屏幕往下滑动
            driver.swipe(width / 2, height * 3 / 4, width / 2, height / 2, 500);
            log.info("scroll=======" + height * 3 / 4 + " " + height / 4);
            return HouseDetail.Location(driver, "address");
          }
        });
        if (address != null) {
          log.info("地址成功拿到:" + address.getText());
        } else {
          log.error("没拿到房源详情的地址:" + address.getText());
        }
        String[] AddressToString = address.getText().split("-");
        for (int i = 0; i < AddressToString.length; i++) {
          if (i == 0) {
            log.info("区域:" + AddressToString[0]);
          } else if (i == 1) {
            log.info("商圈:" + AddressToString[1]);
          } else {
            log.info("地址:" + AddressToString[2]);
          }
        }
        if (typeno == 4) {
          log.info("预期的区域：" + searchkey + ",实际的区域：" + AddressToString[0].trim());
          // MyAssertold.assertequal(driver, searchkey, AddressToString[0].trim());
          MyAssertion.AssertEquals(driver, AddressToString[0].trim(), searchkey, "房源详情显示的区域与搜索的不符");
        } else if (typeno == 5) {
          log.info("预期的商圈：" + searchkey + ",实际的商圈：" + AddressToString[1].trim());
          // MyAssertold.assertequal(driver, searchkey, AddressToString[1].trim());
          MyAssertion.AssertEquals(driver, AddressToString[1].trim(), searchkey, "房源详情显示的商圈与搜索的不符");
        }
        // 返回搜索结果页
        HouseDetail.Location(driver, "back").click();
        // 返回搜索联想页
        SearchResult.Location(driver, "searchback").click();
        // 清空搜索输入框
        UsualClass.cleartext(driver, Location(driver, "SearchEdit").getText());
        // 搜索地标
      } else if (typeno == 6) {
        // 现在开始进行测试地标
        log.info("※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※");
        log.info("现在开始测试地标" + searchkey);
        // 等待房源列表
        List<WebElement> houselist = new ArrayList<>();
        // 拿到houselist房源列表
        houselist = wait.until(new ExpectedCondition<List<WebElement>>() {
          @Override public List<WebElement> apply(WebDriver dr) {

            return SearchResult.LocationList(driver, "houseitem");
          }
        });
        int counts = houselist.size();
        log.info("房源数量——" + counts);
        // 先判定一下默认的排序
        WebElement sort = SearchResult.Location(driver, "sort");
        // MyAssertold.assertequal(driver, "距离从近到远", sort.getText());
        MyAssertion.AssertEquals(driver, sort.getText(), "距离从近到远", "搜索地标默认排序错误");

        // 再去拿一下距离多少米的排序（地铁线与距离是同一个元素）(这块计划往下滑动几屏多拿几个数据，然后最后一个与第一个比)
        // 将抓来的webelememt对象gettext都存入distance里面
        List<String> distance = new ArrayList<>();
        // 将string的距离转为integer，后续做比较
        List<Integer> actualdistance_number = new ArrayList<>();
        // 每次返回抓到的距离元素对象
        List<WebElement> Init_distance = new ArrayList<>();
        for (int j = 0; j < 5; j++) {
          int height = driver.manage().window().getSize().getHeight();
          int weight = driver.manage().window().getSize().getWidth();
          // 这块我就抓第一页与第九页的距离数据（然后最后一个与第一个比较）
          Init_distance = SearchResult.LocationList(driver, "railway");
          // 先判定一下是否拿到距离数，判定一下Init_distance数链的大小
          if (Init_distance.size() != 0) {
            for (WebElement WebElement : Init_distance) {
              distance.add(WebElement.getText());
              log.info("加入距离:" + WebElement.getText());
            }
          } else {
            log.info("未拿到距离数据");
          }
          // 往下滑动页面再抓
          driver.swipe(weight / 2, height * 9 / 10, weight / 2, height / 10, 500);
        }

        //先判断一下拿到的距离中是否包含搜索关键字
        for (String str : distance) {
          MyAssertion.AssertTrue(driver, str.contains(searchkey),
              "拿到的地址中没有存在关键字地标，预期包含" + searchkey + "实际：" + str);
        }
        //再将拿到的距离进行截取 将拿到的元素进行分割转化为int型（多少米）
        for (int i = 0; i < distance.size(); i++) {
          String distancetoString = distance.get(i);
          // 截取距离数字并给distance_number赋值
          try {
            actualdistance_number.add(Integer.valueOf(
                distancetoString.substring(distancetoString.indexOf("站") + 1,
                    distancetoString.indexOf("米"))));
            // 截取后的数字日志记录一下
            log.info("截取后的距离数字，第" + (i + 1) + "个是:" + actualdistance_number.get(i));
          } catch (Exception e) {
            for (StackTraceElement s : e.getStackTrace()) {
              log.error("获取的距离截取转换失败" + s);
            }
          }
        }
        // 现在需要把拿到的integer里面的list的数据进行校验（拿list里面最后一个与第一个比一下）
        Integer[] expecteddistance_number = new Integer[actualdistance_number.size()];
        for (int i = 0; i < actualdistance_number.size(); i++) {
          expecteddistance_number[i] = actualdistance_number.get(i);
        }
        // 数组排序
        Arrays.sort(expecteddistance_number);
        // 循环比对预期与实际的排序问题（主要i=0查看一下是否按照距离从近到远排列）
        for (int i = 0; i < expecteddistance_number.length; i++) {
          // MyAssertold.assertequal(driver, expecteddistance_number[i],
          // actualdistance_number.get(i));
          MyAssertion.AssertEquals(driver, actualdistance_number.get(i), expecteddistance_number[i],
              "实际的排序未按照距离从近到远排序");
        }
        // 返回搜索联想页
        SearchResult.Location(driver, "searchback").click();
        // 清空搜索输入框
        UsualClass.cleartext(driver, Location(driver, "SearchEdit").getText());
      }
    }

    //		// 在搜索联想页点击取消按钮回到首页
    //		Location(driver, "cancelbutton").click();
  }

  // 通用搜索(这块用于预约收藏，目前都是测试小区)
  public static void UsualSearch(final AndroidDriver driver, AndroidDriverWait wait,
      String searchkeyword) {
    if (searchkeyword != null) {
      log.info("已拿到搜索关键字是" + searchkeyword + ",现在开始进行搜索");
      // (开始执行搜索操作)等待底部首页按钮
      WebElement homepagebutton = wait.until(new ExpectedCondition<WebElement>() {
        @Override public WebElement apply(WebDriver d) {
          return HomePage.Location(driver, "homepagebutton");
        }
      });
      // 点击底部首页按钮（确保他在首页）
      homepagebutton.click();
      // 定位搜索输入框
      WebElement Searchbox = HomePage.Location(driver, "Searchbox");
      // 点击首页输入框
      Searchbox.click();
      // 等待搜索输入框
      WebElement SearchEdit = wait.until(new ExpectedCondition<WebElement>() {
        @Override public WebElement apply(WebDriver arg0) {

          return SearchAssociationPage.Location(driver, "SearchEdit");
        }
      });
      // 在搜索输入框中输入关键字
      SearchEdit.sendKeys(searchkeyword);
      // 点击联想页中的第一个
      List<WebElement> textview = new ArrayList<>();
      textview = wait.until(new ExpectedCondition<List<WebElement>>() {
        @Override public List<WebElement> apply(WebDriver dr) {

          return SearchAssociationPage.LocationList(driver, "textview");
        }
      });
      /**
       * 这块存在偶现点击搜索联想页中第一个无响应的问题 尝试解决问题，强行线程停止3秒
       */
      try {
        Thread.sleep(3000);
      } catch (InterruptedException e1) {
        //
        e1.printStackTrace();
      }
      // 点击第一个搜索联想词(确保一下第一个按钮能点击)
      WebElement currentclick =
          wait.until(ExpectedConditions.elementToBeClickable(textview.get(0)));
      currentclick.click();
      // 等待搜索结果房源（获取房源列表）
      List<WebElement> houselist = new ArrayList<>();
      // 拿到houselist房源列表
      houselist = wait.until(new ExpectedCondition<List<WebElement>>() {
        @Override public List<WebElement> apply(WebDriver dr) {

          return SearchResult.LocationList(driver, "houseitem");
        }
      });
      // 点击房源列表中的第一个进入房源详情（确保第一个房源是没有收藏过以及没有预约过，且是职业房东的房）
      houselist.get(0).click();
    } else {
      log.info("搜索关键字是空，不进行搜索");
    }
  }

  // 这个搜索用于发布房源-搜索小区(搜索完之后返回一个预期的搜索小区)
  public static List<String> ReleaseHouseSearch(final AndroidDriver driver, AndroidDriverWait wait,
      String searchkeyword) {
    // 发布房源页面定位小区
    ReleaseHousePage.Location(driver, "quarters").click();
    // 搜索输入框中搜索关键字
    Location(driver, "SearchEdit").sendKeys(searchkeyword);
    // 点击联想页中的第一个
    List<WebElement> textview = new ArrayList<>();
    textview = wait.until(new ExpectedCondition<List<WebElement>>() {
      @Override public List<WebElement> apply(WebDriver dr) {

        return SearchAssociationPage.LocationList(driver, "textview");
      }
    });
    /**
     * 这块存在偶现点击搜索联想页中第一个无响应的问题 尝试解决问题，强行线程停止3秒
     */
    try {
      Thread.sleep(3000);
    } catch (InterruptedException e1) {
      //
      e1.printStackTrace();
    }
    // 点击第一个搜索联想词(确保一下第一个按钮能点击)
    WebElement currentclick = wait.until(ExpectedConditions.elementToBeClickable(textview.get(0)));
    //拿一下搜索关键字作为预期
    String expectedsearchquarters = currentclick.getText();
    //拿一下城市
    String expectedcity = ReleaseHousePage.Location(driver, "citychoose").getText();
    currentclick.click();
    List<String> expected = new ArrayList<>();
    expected.add(expectedcity);
    expected.add(expectedsearchquarters);
    return expected;
  }
}
