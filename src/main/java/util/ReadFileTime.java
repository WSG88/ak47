package util;

import java.io.File;

/**
 * 读取文件创建时间和最后修改时间
 */
public class ReadFileTime {

  public static void main(String[] args) {
    String filep = "E:\\img1";
    File file = new File(filep);
    String[] ss = file.list();
    for (int i = 0; i < ss.length; i++) {
      System.out.println(ss[i]);
    }
  }
}
