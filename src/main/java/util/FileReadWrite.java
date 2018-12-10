package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 文件读写测试
 */
public class FileReadWrite {
  public static void main(String args[]) {
    String pathname = "C:\\work\\Test\\2.txt";
  }

  public static void fileReader(String pathname) {
    InputStreamReader inputStreamReader = null;
    BufferedReader bufferedReader = null;
    try {
      File fileReader = new File(pathname);
      inputStreamReader = new InputStreamReader(new FileInputStream(fileReader));
      bufferedReader = new BufferedReader(inputStreamReader);
      String line = "";
      while (line != null) {
        line = bufferedReader.readLine();
        if (line != null && line.trim().length() > 0) {
          System.out.println(line);
          System.out.println(line.split(",").length);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (inputStreamReader != null) {
          inputStreamReader.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
      try {
        if (bufferedReader != null) {
          bufferedReader.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public static void fileWriter(String pathname) {
    BufferedWriter bufferedWriter = null;
    try {
      File fileWriter = new File(pathname);
      boolean b = fileWriter.createNewFile();
      if (b) {
        bufferedWriter = new BufferedWriter(new FileWriter(fileWriter));
        bufferedWriter.write("我会写入文件啦");
        bufferedWriter.write("\r\n");
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (bufferedWriter != null) {
          bufferedWriter.flush();
          bufferedWriter.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
