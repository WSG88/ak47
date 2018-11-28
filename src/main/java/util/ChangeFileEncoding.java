package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by wsig on 2018-10-16.
 *
 * 改变文件编码格式
 */
public class ChangeFileEncoding {
  public static int fileCount = 0;
  public static String sourceFileRoot = "将要转换文件所在的根目录"; // 将要转换文件所在的根目录
  public static String sourceCharset = "GB2312"; // 源文件编码
  public static String targetCharset = "utf8"; // 目标文件编码

  public static void main(String[] args) throws IOException {
    File fileDir = new File(sourceFileRoot);
    convert(fileDir);
    System.out.println("Total Dealed : " + fileCount + "Files");
  }

  public static void convert(File file) throws IOException {
    // 如果是文件则进行编码转换，写入覆盖原文件
    if (file.isFile()) {
      // 只处理.java结尾的代码文件
      if (!file.getPath().contains(".java")) {
        return;
      }
      InputStreamReader isr = new InputStreamReader(new FileInputStream(file), sourceCharset);
      BufferedReader br = new BufferedReader(isr);
      StringBuffer sb = new StringBuffer();
      String line = null;
      while ((line = br.readLine()) != null) {
        // 注意写入换行符
        line = URLEncoder.encode(line, "utf8");
        sb.append(line + "\r\n");//windows 平台下 换行符为 \r\n
      }
      br.close();
      isr.close();

      File targetFile = new File(file.getPath());
      OutputStreamWriter osw =
          new OutputStreamWriter(new FileOutputStream(targetFile), targetCharset);
      BufferedWriter bw = new BufferedWriter(osw);
      // 以字符串的形式一次性写入
      bw.write(URLDecoder.decode(sb.toString(), "utf8"));
      bw.close();
      osw.close();

      System.out.println("Deal:" + file.getPath());
      fileCount++;
    } else {
      //利用递归对目录下的每个以.java结尾的文件进行编码转换
      for (File subFile : file.listFiles()) {
        convert(subFile);
      }
    }
  }
}
