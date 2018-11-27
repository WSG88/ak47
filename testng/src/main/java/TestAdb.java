import appiumtest.utils.CmdUtil;

/**
 * Created by wsig on 2018-10-31.
 */

public class TestAdb {
  public static void main(String[] args) {
    CmdUtil cmd = new CmdUtil();
    String res = cmd.runCommandProcess("adb shell dumpsys window | findstr mCurrentFocus");
    System.out.println(res);
    res = cmd.runCommandProcess("adb shell getprop ro.build.version.release");
    System.out.println(res);
    res = cmd.runCommandProcess("adb devices -l");
    System.out.println(res);
    res = cmd.runCommandProcess("adb install xx");
    System.out.println(res);
  }
}
