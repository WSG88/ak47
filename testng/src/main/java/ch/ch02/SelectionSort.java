package ch.ch02;

public class SelectionSort {
  //简单选择排序是最简单直观的一种算法，基本思想为每一趟从待排序的数据元素中选择最小（或最大）的一个元素作为首元素，直到所有元素排完为止，简单选择排序是不稳定排序。

  public static void sort(long[] arr) {
    int k = 0;
    long tmp = 0;
    for (int i = 0; i < arr.length - 1; i++) {
      k = i;
      for (int j = i; j < arr.length; j++) {
        if (arr[j] < arr[k]) {
          k = j;
        }
      }
      tmp = arr[i];
      arr[i] = arr[k];
      arr[k] = tmp;
    }
  }
}
