package appiumtest.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class getPhoneDeviceInfo {
  public static void main(String[] args) {
    String str1 = "adb shell getprop ro.product.model";
    execCmd(str1);
    str1 = "adb shell getprop ro.build.version.release";
    execCmd(str1);
    str1 = "adb shell getprop ro.product.name";
    execCmd(str1);
    str1 = "adb devices ";
    execCmd(str1);

    //        str1 = "adb connect  127.0.0.1:62001";
    //        execCmd(str1);
    //        str1 = "adb connect  127.0.0.1:26944";
    //        execCmd(str1);
    //        str1 = "adb connect  127.0.0.1:21503";
    //        execCmd(str1);
    //        str1 = "adb_server connect 127.0.0.1:7555";
    //        execCmd(str1);

    System.out.println("finish...");
  }

  private static void execCmd(String str1) {
    try {
      Runtime runtime = Runtime.getRuntime();
      Process po = runtime.exec(str1);
      BufferedReader br = new BufferedReader(new InputStreamReader(po.getInputStream()));
      String line;
      StringBuffer stringBuffer = new StringBuffer();
      while ((line = br.readLine()) != null) {
        stringBuffer.append(line);
        stringBuffer.append("\n");
      }
      System.out.println(stringBuffer.toString());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static void adbInstall() {
    try {
      File dir = new File("");
      File file = new File(dir.getAbsolutePath() + "\\hizhu.apk");
      if (file.exists()) {
        Runtime.getRuntime().exec("adb install " + file.getAbsolutePath());
        System.out.println("adb install " + file.getAbsolutePath() + " ok ");
      } else {
        System.out.println("文件不存在" + file.getAbsolutePath());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
