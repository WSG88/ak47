package appiumtest.utils;

public class ScreenHouseDataObject {
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
    return "ScreenHouseDataObject [ScreenNo="
        + ScreenNo
        + ", railway_Region="
        + railway_Region
        + ", railstation_Area="
        + railstation_Area
        + "]";
  }
}
