import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ExecCmd {
  static String str1 = "";

  public static void main(String[] args) {

    str1 = "adb connect  127.0.0.1:62001 ";
    execCmd(str1);
    str1 = "adb connect  127.0.0.1:26944 ";
    execCmd(str1);
    str1 = "adb connect  127.0.0.1:21503 ";
    execCmd(str1);
    str1 = "adb_server connect 127.0.0.1:7555 ";
    execCmd(str1);

    str1 = "adb devices ";
    execCmd(str1);
    str1 = "adb shell getprop ro.product.model ";
    execCmd(str1);
    str1 = "adb shell getprop ro.build.version.release ";
    execCmd(str1);
    str1 = "adb shell getprop ro.product.name ";
    execCmd(str1);

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
        stringBuffer.append("\n");
        stringBuffer.append(line);
      }
      System.out.println(stringBuffer.toString());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

