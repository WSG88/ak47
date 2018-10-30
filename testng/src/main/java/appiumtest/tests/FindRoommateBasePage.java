package appiumtest.tests;

import appiumtest.utils.CommandPromptUtil;
import appiumtest.utils.OwnerPage;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.Point;

public class FindRoommateBasePage extends BasePage {

  //	public boolean roomMaker ;
  //	private ArrayList<AndroidElement> roomMakers ;
  public FindRoommateBasePage(AndroidDriver<AndroidElement> driver) {
    super(driver);
  }

  public void openPublishPage() {
    // 无房->发房
    findElement("findRoommateButton").click();
    findElement("publishIcon").click();
    findElement("hasntRoomButton").click();

    // 判断是否需要登录，进入发帖页面
    CommandPromptUtil cmd = new CommandPromptUtil();
    String res = cmd.runCommandThruProcess("adb shell dumpsys window | findstr mCurrentFocus");
    if (res.contains("LoginActivity")) {
      OwnerPage.simplelogin(driver, "15575993304", "180205");
      findElement("hasntRoomButton").click();
    }//常规流程先登录再发帖，故这里登录写死了
  }

  public void inputText(String title, String otherContent) {
    findElement("titleInput").sendKeys(otherContent);
    findElement("otherContentInput").sendKeys(otherContent);
  }

  /*
   *
   * 目前仅写了地铁和商圈每个选一个。后期这块场景用的多，可以修改扩展一下
   * 格式要求："jingan_jingansi","subway_line7"
   * 父目录和子目录用_隔开
   *
   * */
  public void selectLocation(String... location) {
    String businessCircle = location[0];
    String subway = location[1];

    findElement("locationButton").click();

    if (businessCircle != null) {
      findElement("businessCircle").click();
      findElement(businessCircle.split("_")[0]).click();
      findElement(businessCircle.split("_")[1]).click();
    }
    if (subway != null) {
      findElement("subway").click();
      findElement(subway.split("_")[0]).click();
      findElement(subway.split("_")[1]).click();
    }

    findElement("locationEnsuredButton").click();
  }

  public void selectPrice() {
    AndroidElement ae = findElement("priceGlider");
    int xlength = ae.getSize().width;
    Point leftPoint = ae.getCenter().moveBy(-(xlength / 2), 0);
    Point rightPoint = ae.getCenter().moveBy(xlength / 2, 0);
    //起步价滑动
    swipe(leftPoint.x + 10, leftPoint.y, leftPoint.x + xlength / 4, leftPoint.y);
    //最高价滑动
    swipe(rightPoint.x - 10, rightPoint.y, rightPoint.x - xlength / 4, rightPoint.y);
  }

  public void selectImages() {
    // 选择房间照片
    findElement("addImagesButton").click();
    findElement("albumButton").click();
    findElement("screenshotAlbum").click();
    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      //
      e.printStackTrace();
    }
    clickAllEles(findElesBy("images"), 3);
    findElement("imagesEnsuredButton").click();
  }

  public void clickPublish() {
    findElement("publishButton").click();
  }

  //	public boolean judgeRoomMaker() {
  //		roomMakers
  //		return roomMaker;
  //
  //	}
  //
  //	public void setRoomMakers() {
  //		ArrayList<AndroidElement> rms = findElesBy("isRoomMakers");
  //		roomMakers.addAll(rms);
  //
  //	}
}
