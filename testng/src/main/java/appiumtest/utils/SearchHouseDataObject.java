package appiumtest.utils;

public class SearchHouseDataObject {

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
    return "SearchHouseDataObject [searchtype=" + searchtype + ", searchkeys=" + searchkeys + "]";
  }
}
