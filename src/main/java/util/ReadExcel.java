package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ReadExcel {
  public static void main(String[] args) {
    ReadExcel obj = new ReadExcel();
    File file = new File("E:/tj.xls");
    List<List> excelList = obj.readExcel(file);
    for (List<String> innerList : excelList) {
      String txt = "<Contact>\n"
          + "        <Name>"
          + (innerList.get(0) + "_" + innerList.get(1))
          + "</Name>\n"
          + "        <Starred>0</Starred>\n"
          + "        <PhoneList>\n"
          + "            <Phone Type=\"2\">"
          + innerList.get(2)
          + "</Phone>\n"
          + "        </PhoneList>\n"
          + "        <Account value=\"0\">\n"
          + "            <Name></Name>\n"
          + "            <Type></Type>\n"
          + "        </Account>\n"
          + "    </Contact>";
      System.out.print(txt);
    }
  }

  // 去读Excel的方法readExcel，该方法的入口参数为一个File对象
  public List readExcel(File file) {
    try {
      // 创建输入流，读取Excel
      InputStream is = new FileInputStream(file.getAbsolutePath());
      // jxl提供的Workbook类
      Workbook wb = Workbook.getWorkbook(is);
      // Excel的页签数量
      int sheet_size = wb.getNumberOfSheets();
      for (int index = 0; index < sheet_size; index++) {
        List<List> outerList = new ArrayList<List>();
        // 每个页签创建一个Sheet对象
        Sheet sheet = wb.getSheet(index);
        // sheet.getRows()返回该页的总行数
        for (int i = 0; i < sheet.getRows(); i++) {
          List<String> innerList = new ArrayList<String>();
          // sheet.getColumns()返回该页的总列数
          for (int j = 0; j < sheet.getColumns(); j++) {
            String cellinfo = sheet.getCell(j, i).getContents();
            if (cellinfo == null || cellinfo.length() == 0) {
              continue;
            }
            innerList.add(cellinfo);
          }
          outerList.add(i, innerList);
        }
        return outerList;
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (BiffException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}
