package util;

import com.alibaba.fastjson.JSONObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wsig on 2018-11-30.
 */
class TestSql {
  static Connection con = null;

  static String driver, url, user, password, sql;

  public static void main(String[] args) {
    driver = "com.mysql.jdbc.Driver";
    url = "jdbc:mysql://localhost:3306/gaodu_statistics";
    user = "root";
    password = "root";
    try {
      Class.forName(driver);
      con = DriverManager.getConnection(url, user, password);
      Statement statement = con.createStatement();
      sql = "select * from statistic";
      //sql = "select * from statistic where ship_id='da8fc177-9cd2-18fd-9edf-dba07d7718b7'";
      //sql = "select count(*) as cn from statistic";

      ResultSet rs = statement.executeQuery(sql);
      //System.out.println(convertList(rs));
      List<Integer> ll = new ArrayList<>();
      while (rs.next()) {
        String id = rs.getString("id");
        if (!ll.contains(id.hashCode() & 15)) {
          ll.add(id.hashCode() & 15);
        }
      }
      System.out.println(ll);
      rs.close();
      System.out.println("ok ！！");
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (con != null) {
          con.close();
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  private static List convertList(ResultSet rs) throws SQLException {
    List<JSONObject> list = new ArrayList<>();
    ResultSetMetaData md = (ResultSetMetaData) rs.getMetaData();
    int column = md.getColumnCount();
    while (rs.next()) {
      Map<String, Object> rowData = new HashMap<>();
      for (int i = 1; i <= column; i++) {
        rowData.put(md.getColumnName(i).toString(), rs.getObject(i));
      }
      JSONObject json = new JSONObject(rowData);
      System.out.println("-------------------------------------------");
      System.out.println(json);
      list.add(json);
    }
    return list;
  }

  class Statistic {
    String ship_id = "";
    String type = "0";
    String view_no = "0";
    String call_no = "0";
    String call_success_no = "0";
    String reserve_no = "0";
    String house_no = "0";
    String refresh_no = "0";
    String liked_no = "0";
    String link_no = "0";
    String im_no = "0";
    String timestamp = "0";
    String city_code = "";
    String content = "";

    int getMonth(String millis) {
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
      Calendar calendar = Calendar.getInstance();
      calendar.setTimeInMillis(Long.parseLong(millis));
      Date date = calendar.getTime();
      System.out.println(simpleDateFormat.format(date));
      return date.getMonth() + 1;
    }
  }
}
