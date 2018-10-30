package appiumtest.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReadExcel {
  //读搜索数据表
  public static List<SearchHouseDataObject> readSearchHouseDate() {
    Logger log = LoggerFactory.getLogger("readSourceDate");
    Workbook workbook = null;
    List<SearchHouseDataObject> searchhousedatalist = new ArrayList<>();
    try {
      workbook = Workbook.getWorkbook(new File("dataprovider.xls"));
      Sheet sheet = workbook.getSheet(0);//使用第一个工作表
      int colnum = sheet.getColumns();//获取列数，如果一定要3列，直接改3就行
      int row = sheet.getRows();//获取行数
      for (int i = 1; i < row; i++) {
        SearchHouseDataObject Searchobj =
            new SearchHouseDataObject(Integer.valueOf(sheet.getCell(0, i).getContents()),
                sheet.getCell(1, i).getContents());
        searchhousedatalist.add(Searchobj);
      }
    } catch (BiffException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      workbook.close();
    }
    if (searchhousedatalist.size() != 0) {
      log.info("excel读取搜索数据成功");
    } else {
      log.info("excel读取搜索数据失败");
    }
    return searchhousedatalist;
  }

  //读筛选数据表
  public static List<ScreenHouseDataObject> readScreenHouseData() {
    Logger log = LoggerFactory.getLogger("readSourceDate");
    Workbook workbook = null;
    //将读取excel的数据存在list中
    List<ScreenHouseDataObject> datalist = new ArrayList<>();
    try {
      workbook = Workbook.getWorkbook(new File("dataprovider.xls"));
      Sheet sheet = workbook.getSheet(1);//使用第一个工作表
      //			int colnum = sheet.getColumns();//获取列数，如果一定要3列，直接改3就行
      int row = sheet.getRows();//获取行数
      for (int i = 1; i < row; i++) {
        datalist.add(new ScreenHouseDataObject(sheet.getCell(0, i).getContents(),
            sheet.getCell(1, i).getContents(), sheet.getCell(2, i).getContents()));
      }
    } catch (BiffException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      workbook.close();
    }
    if (!datalist.isEmpty()) {
      log.info("excel读取搜索数据成功");
    } else {
      log.info("excel读取搜索数据失败");
    }
    return datalist;
  }

  //测试excel数据读取
  public static void main(String[] args) {
    //	   Map<Integer,String>test= ReadExcel.readSearchHouseDate();
    //	   for(Integer i:test.keySet()) {
    //		   System.out.println(i+"--"+test.get(i));
    //	   }
    List<ScreenHouseDataObject> list1 = readScreenHouseData();
    for (ScreenHouseDataObject o : list1) {
      System.out.println(o.toString());
    }
    // System.out.println(list1.size());
  }
}


