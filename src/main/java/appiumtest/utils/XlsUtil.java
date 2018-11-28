package appiumtest.utils;

import appiumtest.BaseConfig;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import jxl.Sheet;
import jxl.Workbook;

public class XlsUtil {
  public static String string = BaseConfig.FILE_XLS;

  public static List<SearchHouseDataObject> readSearchHouseDate() {
    File file = new File(string);
    return readSearchHouseDate(file);
  }

  public static List<SearchHouseDataObject> readSearchHouseDate(File file) {
    Workbook workbook = null;
    List<SearchHouseDataObject> searchHouseDataObjects = new ArrayList<>();
    try {
      workbook = Workbook.getWorkbook(file);
      Sheet sheet = workbook.getSheet(0);
      int row = sheet.getRows();
      for (int i = 1; i < row; i++) {
        SearchHouseDataObject searchHouseDataObject =
            new SearchHouseDataObject(Integer.valueOf(sheet.getCell(0, i).getContents()),
                sheet.getCell(1, i).getContents());
        searchHouseDataObjects.add(searchHouseDataObject);
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (workbook != null) {
        workbook.close();
      }
    }
    return searchHouseDataObjects;
  }

  public static List<ScreenHouseDataObject> readScreenHouseData() {
    File file = new File(string);
    return readScreenHouseData(file);
  }

  public static List<ScreenHouseDataObject> readScreenHouseData(File file) {
    Workbook workbook = null;
    List<ScreenHouseDataObject> screenHouseDataObjects = new ArrayList<>();
    try {
      workbook = Workbook.getWorkbook(file);
      Sheet sheet = workbook.getSheet(1);
      int row = sheet.getRows();
      for (int i = 1; i < row; i++) {
        screenHouseDataObjects.add(new ScreenHouseDataObject(sheet.getCell(0, i).getContents(),
            sheet.getCell(1, i).getContents(), sheet.getCell(2, i).getContents()));
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (workbook != null) {
        workbook.close();
      }
    }
    return screenHouseDataObjects;
  }

  public static void main(String[] args) {
    File file = new File(string);
    List<ScreenHouseDataObject> screenHouseDataObjects = readScreenHouseData(file);
    for (ScreenHouseDataObject o : screenHouseDataObjects) {
      System.out.println(o.toString());
    }
    System.out.println("-----------------------------------");
    List<SearchHouseDataObject> searchHouseDataObjects = readSearchHouseDate(file);
    for (SearchHouseDataObject o : searchHouseDataObjects) {
      System.out.println(o.toString());
    }
  }

  public static class ScreenHouseDataObject {
    /**
     * ScreenNo筛选类型（1、是地铁类型的筛选 2、是区域类型的筛选）
     * railway_Region（地铁线或者行政区）
     * railstation_Area（地铁站或者商圈）
     * <p>
     * 以下字段还未加在测试类中使用
     * min_money(最小租金)
     * max_money（最大租金）
     * sort(0：默认排序1：价格从高到低2：价格从低到高3：面积从大到小)
     * rent_type（出租类型、整租合租）
     * room_type（户型：一室两室等）
     * house_type（房源类型：公寓、小区住宅）
     */
    private String ScreenNo;
    private String railway_Region;
    private String railstation_Area;
    private int min_money;
    private int max_money;
    private int sort;
    private String rent_type;
    private String room_type;
    private String house_type;

    public ScreenHouseDataObject(String screenNo, String railway_Region, String railstation_Area) {
      super();
      ScreenNo = screenNo;
      this.railway_Region = railway_Region;
      this.railstation_Area = railstation_Area;
    }

    public ScreenHouseDataObject(String screenNo, String railway_Region, String railstation_Area,
        int min_money, int max_money, int sort, String rent_type, String room_type,
        String house_type) {
      super();
      ScreenNo = screenNo;
      this.railway_Region = railway_Region;
      this.railstation_Area = railstation_Area;
      this.min_money = min_money;
      this.max_money = max_money;
      this.sort = sort;
      this.rent_type = rent_type;
      this.room_type = room_type;
      this.house_type = house_type;
    }

    public ScreenHouseDataObject() {
      super();
    }

    public int getMin_money() {
      return min_money;
    }

    public void setMin_money(int min_money) {
      this.min_money = min_money;
    }

    public int getMax_money() {
      return max_money;
    }

    public void setMax_money(int max_money) {
      this.max_money = max_money;
    }

    public int getSort() {
      return sort;
    }

    public void setSort(int sort) {
      this.sort = sort;
    }

    public String getRent_type() {
      return rent_type;
    }

    public void setRent_type(String rent_type) {
      this.rent_type = rent_type;
    }

    public String getRoom_type() {
      return room_type;
    }

    public void setRoom_type(String room_type) {
      this.room_type = room_type;
    }

    public String getHouse_type() {
      return house_type;
    }

    public void setHouse_type(String house_type) {
      this.house_type = house_type;
    }

    public String getScreenNo() {
      return ScreenNo;
    }

    public void setScreenNo(String screenNo) {
      ScreenNo = screenNo;
    }

    public String getRailway_Region() {
      return railway_Region;
    }

    public void setRailway_Region(String railway_Region) {
      this.railway_Region = railway_Region;
    }

    public String getRailstation_Area() {
      return railstation_Area;
    }

    public void setRailstation_Area(String railstation_Area) {
      this.railstation_Area = railstation_Area;
    }

    @Override public String toString() {
      return "ScreenHouseDataObject{"
          + "ScreenNo='"
          + ScreenNo
          + '\''
          + ", railway_Region='"
          + railway_Region
          + '\''
          + ", railstation_Area='"
          + railstation_Area
          + '\''
          + ", min_money="
          + min_money
          + ", max_money="
          + max_money
          + ", sort="
          + sort
          + ", rent_type='"
          + rent_type
          + '\''
          + ", room_type='"
          + room_type
          + '\''
          + ", house_type='"
          + house_type
          + '\''
          + '}';
    }
  }

  public static class SearchHouseDataObject {

    private int searchtype;
    private String searchkeys;

    public SearchHouseDataObject() {
      super();
    }

    public SearchHouseDataObject(int searchtype, String searchkeys) {
      super();
      this.searchtype = searchtype;
      this.searchkeys = searchkeys;
    }

    public int getSearchtype() {
      return searchtype;
    }

    public void setSearchtype(int searchtype) {
      this.searchtype = searchtype;
    }

    public String getSearchkeys() {
      return searchkeys;
    }

    public void setSearchkeys(String searchkeys) {
      this.searchkeys = searchkeys;
    }

    @Override public String toString() {
      return "SearchHouseDataObject{"
          + "searchtype="
          + searchtype
          + ", searchkeys='"
          + searchkeys
          + '\''
          + '}';
    }
  }
}


