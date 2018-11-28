package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Sink;
import okio.Source;

public class CinTxt {
  public static void main(String args[]) {
    try {
      String pathname = "C:\\work\\Test\\2.txt";
      File filename = new File(pathname);
      InputStreamReader reader =
          new InputStreamReader(new FileInputStream(filename)); // 建立一个输入流对象reader
      BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
      String line = "";
      line = br.readLine();
      while (line != null) {
        line = br.readLine(); // 一次读入一行数据
        if (line != null && line.trim().length() > 0) {
          System.out.println(line);
          System.out.println(line.split(",").length);
        }
      }
      File writename = new File(".\\3.txt");
      writename.createNewFile(); // 创建新文件
      BufferedWriter out = new BufferedWriter(new FileWriter(writename));
      out.write("我会写入文件啦\r\n"); // \r\n即为换行
      out.flush(); // 把缓存区内容压入文件
      out.close(); // 最后记得关闭文件
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
