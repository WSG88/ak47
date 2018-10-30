package ch.ch02;

public class BubbleSort {

  //https://www.cnblogs.com/shen-hua/p/5422676.html
  //它重复地走访过要排序的元素列，依次比较两个相邻的元素，如果他们的顺序（如从大到小、首字母从A到Z）错误就把他们交换过来
  //N个数字要排序完成，总共进行N-1趟排序，每i趟的排序次数为(N-i)次，所以可以用双重循环语句，外层控制循环多少趟，内层控制每一趟的循环次数

  public static void sort1(long[] arr) {
    for (int i = 0; i < arr.length - 1; i++) {//外层循环控制排序趟数
      for (int j = 0; j < arr.length - 1 - i; j++) {//内层循环控制每一趟排序多少次
        if (arr[j] > arr[j + 1]) {
          long tmp = arr[j];
          arr[j] = arr[j + 1];
          arr[j + 1] = tmp;
        }
      }
    }
  }

  public static void sort(long[] arr) {
    for (int i = 0; i < arr.length - 1; i++) {
      for (int j = arr.length - 1; j > i; j--) {
        if (arr[j] < arr[j - 1]) {
          long tmp = arr[j];
          arr[j] = arr[j - 1];
          arr[j - 1] = tmp;
        }
      }

      for (int k = 0; k < arr.length - 1; k++) {
        System.out.print(arr[k] + ",");
      }
      System.out.println("\n--------------------");
    }
  }
}
