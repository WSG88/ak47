package appiumtest.utils;

import io.appium.java_client.android.AndroidDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class InitDriver {
  public static AndroidDriver dr = null;
  public static AndroidDriverWait wait = null;
  public static int netstatus;
  //日志对象
  static Logger log = LoggerFactory.getLogger("InitDriver");

  @Parameters({ "DeviceName", "UDID", "System_version", "port" }) @Test
  public void Init(String DeviceName, String UDID, String System_version, int port) {
    BeforeToolClass.AppConfigure(DeviceName, UDID, System_version, port);
    dr = BeforeToolClass.driver;
    if (dr == null) {
      log.info("初始化driver失败");
    } else {
      log.info("初始化driver成功");
      wait = new AndroidDriverWait(dr, 30);
      netstatus = dr.getNetworkConnection().value;
    }
  }
  //整个测试完之后卸载app，未后期发新包安装做准备
  //	@AfterTest
  //	public void afterTest() {
  //		dr.removeApp("com.loulifang.house");
  //	}
}