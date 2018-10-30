package appiumtest.tests;

import appiumtest.utils.LoggerUtil;
import com.alibaba.fastjson.JSONObject;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {

  public AndroidDriver<AndroidElement> driver;
  public Map<String, String> elesMap = new HashMap<String, String>();

  public BasePage(AndroidDriver<AndroidElement> driver) {
    this.driver = driver;
    elesMap = this.getElesMap();
  }

  /*
   *
   * 根据外部的类的调用，获取对应类名的json文件，生成元素信息map
   *
   * */
  public Map<String, String> getElesMap() {
    String filePath = System.getProperty("user.dir")
        + "/src/findroommate/"
        + getClass().getSimpleName()
        + ".json";
    BufferedReader bufferedReader = null;
    try {
      bufferedReader = new BufferedReader(new FileReader(new File(filePath)));
    } catch (FileNotFoundException e1) {
      //
      e1.printStackTrace();
    }

    String jsonContent = null;
    try {
      jsonContent = IOUtils.toString(bufferedReader);
    } catch (IOException e) {
      //
      e.printStackTrace();
    }

    return (Map<String, String>) JSONObject.parse(jsonContent);
  }

  /*
   *
   * 显示等待15s，根据元素map key值获取定位信息， 返回元素对象
   *
   * */
  public AndroidElement findElement(String name) {
    final String id = this.elesMap.get(name);
    AndroidElement el = null;
    WebDriverWait wait = new WebDriverWait(driver, 15);
    try {
      el = wait.until(new ExpectedCondition<AndroidElement>() {
        @Override public AndroidElement apply(WebDriver d) {
          return findEleBy(id);
        }
      });
      LoggerUtil.debug("元素已找到：" + name + ": " + id);
    } catch (Exception e) {
      LoggerUtil.error("未找到元素：" + name + ": " + id);
    }
    return el;
  }

  /*
   *
   * 关键字驱动，兼容多种定位方式
   *
   * */
  private AndroidElement findEleBy(String id) {
    AndroidElement element = null;
    String byTpye = id.split("@")[0];
    String eleLoctorInfo = id.split("@")[1];
    switch (byTpye) {
      case "id":
        element = driver.findElementById(eleLoctorInfo);
        break;
      case "xpath":
        element = driver.findElementByXPath(eleLoctorInfo);
        break;
      case "desc":
        element = driver.findElementByAccessibilityId(eleLoctorInfo);
        break;
      case "text":
        element = driver.findElementByAndroidUIAutomator(
            "new UiSelector().text(\"" + eleLoctorInfo + "\")");
        break;
      case "class":
        element = driver.findElementByClassName(eleLoctorInfo);
        break;
      case "name"://name和text都是用于定位text，但是text用于定位7.0以上的系统
        element = driver.findElementByName(eleLoctorInfo);
        break;
      default:
        LoggerUtil.error("暂不支持的by类型：" + byTpye);
        break;
    }
    return element;
  }

  /*
   *
   * 返回具有相同定位元素信息的list
   *
   * */
  ArrayList<AndroidElement> findElesBy(String name) {
    String id = this.elesMap.get(name);
    ArrayList<AndroidElement> elements = new ArrayList<AndroidElement>();
    String byTpye = id.split("@")[0];
    String eleLoctorInfo = id.split("@")[1];
    switch (byTpye) {
      case "id":
        elements = (ArrayList<AndroidElement>) driver.findElementsById(eleLoctorInfo);
        break;
      case "xpath":
        elements = (ArrayList<AndroidElement>) driver.findElementsByXPath(eleLoctorInfo);
        break;
      case "class":
        elements = (ArrayList<AndroidElement>) driver.findElementsByClassName(eleLoctorInfo);
        break;
      default:
        LoggerUtil.error("暂不支持的by类型：" + byTpye);
        break;
    }
    return elements;
  }

  /*
   *
   * 逐一点击元素列表中的元素，一般用于多选
   * num 代表多选几个
   *
   * */
  public void clickAllEles(ArrayList<AndroidElement> als, int num) {

    for (int x = 0; x < num; x++) {
      if (x > als.size() - 1) break;
      als.get(x).click();
    }
  }

  public void swipe(int x, int y, int toX, int toY) {
    TouchAction action = new TouchAction(driver).longPress(x, y, 1).moveTo(toX, toY).release();
    action.perform();
  }

  public void swipeToUp() {
    int width = driver.manage().window().getSize().width;
    int height = driver.manage().window().getSize().height;

    swipe(width / 2, height * 3 / 4, width / 2, height / 4);
    LoggerUtil.debug(
        "往上滑动：" + width / 2 + "_" + height * 3 / 4 + " to " + width / 2 + "_" + height / 4);
  }

  /*
   *
   * toast的识别需要使用uiautomator2
   * 仅使用了xpath的方式来识别
   *
   * */
  public boolean findToast(String name) {
    boolean isFind = false;
    String id = this.elesMap.get(name);
    String eleLoctorInfo = id.substring(6);
    WebDriverWait wait = new WebDriverWait(driver, 15);
    try {
      wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(eleLoctorInfo)));
      isFind = true;
    } catch (Exception e) {
      LoggerUtil.error(name + "未找到");
    }
    return isFind;
  }

  /*
   *
   * 判定当前页面元素是否存在
   *
   * */
  //	public boolean eleIsExist(String name) {
  //		boolean isExist = false;
  //
  //		try {
  //			findElement(name);
  //			isExist = true;
  //		} catch (Exception e) {
  //			LoggerUtil.error("isExist:" +isExist);
  //
  //		}
  //
  //		return isExist;
  //
  //	}
}
