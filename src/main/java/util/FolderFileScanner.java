package util;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by wsig on 2018-09-18.
 * 文件扫描
 */
public class FolderFileScanner {
  private static ArrayList<Object> scanFiles = new ArrayList<Object>();
  /**
   * linkedList实现
   **/
  private static LinkedList<File> queueFiles = new LinkedList<File>();

  public static void main(String[] args) {

    FolderFileScanner.scanFilesWithNoRecursion("D:\\DEV\\phone_bak\\image");
    FolderFileScanner.scanFilesWithRecursion("D:\\DEV\\phone_bak\\image");

    System.out.println("FolderFileScanner.scanFiles.size()" + FolderFileScanner.scanFiles.size());
    System.out.println("FolderFileScanner.queueFiles.size()" + FolderFileScanner.queueFiles.size());
    for (int i = 0; i < FolderFileScanner.scanFiles.size(); i++) {
      String str = (String) FolderFileScanner.scanFiles.get(i);
      if (str != null && str.endsWith(".JPG")) {
        System.out.println(str);
        //        new File(str).deleteOnExit();
        new File(str).renameTo(new File(str.replace(".JPG", "")));
      }
    }
  }

  /**
   * TODO:递归扫描指定文件夹下面的指定文件
   */
  public static ArrayList<Object> scanFilesWithRecursion(String folderPath) {
    ArrayList<String> dirctorys = new ArrayList<String>();
    File directory = new File(folderPath);
    if (directory.isDirectory()) {
      File[] filelist = directory.listFiles();
      for (int i = 0; i < filelist.length; i++) {
        /**如果当前是文件夹，进入递归扫描文件夹**/
        if (filelist[i].isDirectory()) {
          dirctorys.add(filelist[i].getAbsolutePath());
          /**递归扫描下面的文件夹**/
          scanFilesWithRecursion(filelist[i].getAbsolutePath());
        }
        /**非文件夹**/
        else {
          scanFiles.add(filelist[i].getAbsolutePath());
        }
      }
    }
    return scanFiles;
  }

  /**
   * TODO:非递归方式扫描指定文件夹下面的所有文件
   */
  public static ArrayList<Object> scanFilesWithNoRecursion(String folderPath) {
    File directory = new File(folderPath);
    if (directory.isDirectory()) {
      //首先将第一层目录扫描一遍
      File[] files = directory.listFiles();
      //遍历扫出的文件数组，如果是文件夹，将其放入到linkedList中稍后处理
      for (int i = 0; i < files.length; i++) {
        if (files[i].isDirectory()) {
          queueFiles.add(files[i]);
        } else {
          //暂时将文件名放入scanFiles中
          scanFiles.add(files[i].getAbsolutePath());
        }
      }

      //如果linkedList非空遍历linkedList
      while (!queueFiles.isEmpty()) {
        //移出linkedList中的第一个
        File headDirectory = queueFiles.removeFirst();
        File[] currentFiles = headDirectory.listFiles();
        for (int j = 0; j < currentFiles.length; j++) {
          if (currentFiles[j].isDirectory()) {
            //如果仍然是文件夹，将其放入linkedList中
            queueFiles.add(currentFiles[j]);
          } else {
            scanFiles.add(currentFiles[j].getAbsolutePath());
          }
        }
      }
    }

    return scanFiles;
  }
}
