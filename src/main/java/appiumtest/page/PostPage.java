package appiumtest.page;

import appiumtest.BaseConfig;
import appiumtest.utils.LogUtil;
import com.alibaba.fastjson.JSONObject;
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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PostPage {

  public AndroidDriver<AndroidElement> driver;

  private Map<String, String> stringHashMap = new HashMap<String, String>();

  public PostPage(AndroidDriver<AndroidElement> driver) {
    this.driver = driver;
  }

  private Map<String, String> getStringHashMap() {
    if (stringHashMap == null || stringHashMap.isEmpty()) {
      stringHashMap = getElementMap();
    }
    return stringHashMap;
  }

  private Map<String, String> getElementMap() {

    BufferedReader bufferedReader = null;
    try {
      bufferedReader = new BufferedReader(new FileReader(new File(BaseConfig.FILE_JSON)));
    } catch (FileNotFoundException e1) {
      //
      e1.printStackTrace();
    }

    String jsonContent = null;
    try {
      jsonContent = IOUtils.toString(bufferedReader);
      jsonContent.replace("\r\n", "");
    } catch (IOException e) {
      //
      e.printStackTrace();
    }
    System.out.println("jsonContent 0 =" + jsonContent);
    jsonContent = JSONMinify.minify(jsonContent);
    System.out.println("jsonContent 1 =" + jsonContent);

    return (Map<String, String>) JSONObject.parse(jsonContent);
  }

  public AndroidElement findE(String name) {
    final String id = getStringHashMap().get(name);
    LogUtil.debug("元素开始查：" + name + ": " + id);
    AndroidElement element = null;
    WebDriverWait driverWait = new WebDriverWait(driver, 15);
    try {
      element = driverWait.until(new ExpectedCondition<AndroidElement>() {
        @Override public AndroidElement apply(WebDriver d) {
          return findEBy(id);
        }
      });
      LogUtil.debug("元素已找到：" + name + ": " + id);
    } catch (Exception e) {
      LogUtil.error("元素未找到：" + name + ": " + id);
    }
    return element;
  }

  private AndroidElement findEBy(String id) {
    AndroidElement element = null;
    String type = id.split("@")[0];
    String s = id.split("@")[1];
    switch (type) {
      case "id":
        element = driver.findElementById(s);
        break;
      case "xpath":
        element = driver.findElementByXPath(s);
        break;
      case "desc":
        element = driver.findElementByAccessibilityId(s);
        break;
      case "text":
        element = driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + s + "\")");
        break;
      case "class":
        element = driver.findElementByClassName(s);
        break;
      case "name"://name和text都是用于定位text，但是text用于定位7.0以上的系统
        element = driver.findElementByName(s);
        break;
      default:
        LogUtil.error("暂不支持的by类型：" + type);
        break;
    }
    return element;
  }

  public ArrayList<AndroidElement> findEsBy(String name) {
    ArrayList<AndroidElement> elements = new ArrayList<AndroidElement>();
    String id = getStringHashMap().get(name);
    String type = id.split("@")[0];
    String s = id.split("@")[1];
    switch (type) {
      case "id":
        elements = (ArrayList<AndroidElement>) driver.findElementsById(s);
        break;
      case "xpath":
        elements = (ArrayList<AndroidElement>) driver.findElementsByXPath(s);
        break;
      case "class":
        elements = (ArrayList<AndroidElement>) driver.findElementsByClassName(s);
        break;
      default:
        LogUtil.error("暂不支持的by类型：" + type);
        break;
    }
    return elements;
  }

  public static class JSONMinify {
    public static String minify(String jsonString) {
      boolean in_string = false;
      boolean in_multiline_comment = false;
      boolean in_singleline_comment = false;
      char string_opener = 'x'; // unused value, just something that makes compiler happy

      StringBuilder out = new StringBuilder();
      for (int i = 0; i < jsonString.length(); i++) {
        // get next (c) and next-next character (cc)

        char c = jsonString.charAt(i);
        String cc = jsonString.substring(i, Math.min(i + 2, jsonString.length()));

        // big switch is by what mode we're in (in_string etc.)
        if (in_string) {
          if (c == string_opener) {
            in_string = false;
            out.append(c);
          } else if (c
              == '\\') { // no special treatment needed for \\u, it just works like this too
            out.append(cc);
            ++i;
          } else {
            out.append(c);
          }
        } else if (in_singleline_comment) {
          if (c == '\r' || c == '\n') in_singleline_comment = false;
        } else if (in_multiline_comment) {
          if (cc.equals("*/")) {
            in_multiline_comment = false;
            ++i;
          }
        } else {
          // we're outside of the special modes, so look for mode openers (comment start, string start)
          if (cc.equals("/*")) {
            in_multiline_comment = true;
            ++i;
          } else if (cc.equals("//")) {
            in_singleline_comment = true;
            ++i;
          } else if (c == '"' || c == '\'') {
            in_string = true;
            string_opener = c;
            out.append(c);
          } else if (!Character.isWhitespace(c)) out.append(c);
        }
      }
      return out.toString();
    }
  }
}
