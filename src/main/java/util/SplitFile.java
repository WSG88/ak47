package util;

import java.io.File;

/**
 * Created by wsig on 2018-12-21.
 * 拆分大文件夹
 */
class SplitFile {
  public static void main(String[] args) {
    File file = new File("E:\\数据备份\\相机\\img0");
    String[] ss = file.list();
    String path = file.getAbsolutePath();
    String path1 = path + "6";
    System.out.println(ss.length);
    System.out.println("path=" + path);
    for (int i = 0; i < 490; i++) {
      String str = ss[i];
      System.out.println(str);
      if (!new File(path1).exists()) {
        new File(path1).mkdirs();
      }
      moveFile(path + "\\" + str, path1 + "\\" + str);
    }
  }

  public static void moveFile(String filename1, String filename2) {
    try {
      File file = new File(filename1);
      if (file.renameTo(new File(filename2))) {
        System.out.println("File is moved successful!");
      } else {
        System.out.println("File is failed to move!");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
