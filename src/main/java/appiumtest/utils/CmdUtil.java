package appiumtest.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.List;

public class CmdUtil {

  String OSType;

  public CmdUtil() {
    OSType = System.getProperty("os.name");
  }

  public static boolean isSameAct(String act) {
    CmdUtil cmd = new CmdUtil();
    String res = cmd.runCommandProcess("adb shell dumpsys window | findstr mCurrentFocus");
    if (res.contains(act)) {
      return true;
    }
    return false;
  }

  public String runCommandProcess(String command) {
    BufferedReader br = null;
    try {
      br = getBufferedReader(command);
    } catch (IOException e) {
      e.printStackTrace();
    }
    String line;
    StringBuffer allLine = new StringBuffer();
    try {
      while ((line = br.readLine()) != null) {
        allLine.append(line);
        allLine.append("\n");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    LogUtil.debug("命令结果：" + allLine);
    return allLine.toString();
  }

  private BufferedReader getBufferedReader(String command) throws IOException {
    Process process = runCmdToProcess(command);
    SequenceInputStream sis =
        new SequenceInputStream(process.getInputStream(), process.getErrorStream());
    InputStreamReader isr = new InputStreamReader(sis);
    return new BufferedReader(isr);
  }

  private Process runCmdToProcess(String cmd) throws IOException {
    List<String> commands = new ArrayList<>();
    if (OSType.contains("Windows")) {
      commands.add("cmd");
      commands.add("/c");
    } else {
      commands.add("/bin/sh");
      commands.add("-c");
    }
    commands.add(cmd);
    ProcessBuilder builder = new ProcessBuilder(commands);
    Process process = builder.start();
    LogUtil.debug("执行命令：" + commands.toString());
    return process;
  }
}
