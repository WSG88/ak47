package appiumtest.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.List;

public class CommandPromptUtil {

  String OSType = null;

  public CommandPromptUtil() {
    OSType = System.getProperty("os.name");
  }

  public String runCommandThruProcess(String command) {
    BufferedReader br = null;
    try {
      br = getBufferedReader(command);
    } catch (IOException e) {
      //
      e.printStackTrace();
    }

    String line;
    StringBuffer allLine = new StringBuffer();
    try {
      while ((line = br.readLine()) != null) {
        allLine.append(line);
      }
    } catch (IOException e) {
      //
      e.printStackTrace();
    }
    LoggerUtil.debug("命令结果：" + allLine);
    return allLine.toString();
  }

  public List<String> runCommand(String command) {
    BufferedReader br = null;
    try {
      br = getBufferedReader(command);
    } catch (IOException e) {
      //
      e.printStackTrace();
    }
    String line;
    List<String> allLine = new ArrayList<>();
    try {
      while ((line = br.readLine()) != null) {
        //        		allLine.add(line.replaceAll("[\\[\\](){}]","_").split("_")[1]);
        allLine.add(line);
      }
    } catch (IOException e) {
      //
      e.printStackTrace();
    }
    LoggerUtil.debug("命令结果：" + allLine);
    return allLine;
  }

  private BufferedReader getBufferedReader(String command) throws IOException {
    List<String> commands = new ArrayList<>();
    if (OSType.contains("Windows")) {
      commands.add("cmd");
      commands.add("/c");
    } else {
      commands.add("/bin/sh");
      commands.add("-c");
    }
    commands.add(command);
    ProcessBuilder builder = new ProcessBuilder(commands);
    final Process process = builder.start();
    LoggerUtil.debug("执行命令：" + commands.toString());

    SequenceInputStream sis =
        new SequenceInputStream(process.getInputStream(), process.getErrorStream());
    InputStreamReader isr = new InputStreamReader(sis);
    return new BufferedReader(isr);
  }

  public Process runCmdToProcess(String cmd) throws IOException {
    Process pr = null;
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
    pr = builder.start();
    LoggerUtil.debug("执行命令：" + commands.toString());
    return pr;
  }
}
