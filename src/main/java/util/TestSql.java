package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by wsig on 2018-11-30.
 */
class TestSql {
  public static void main(String[] args) {
    Connection con;
    String driver = "com.mysql.jdbc.Driver";
    String url = "jdbc:mysql://localhost:3306/gaodu_statistics";
    String user = "root";
    String password = "root";
    try {
      Class.forName(driver);
      con = DriverManager.getConnection(url, user, password);
      Statement statement = con.createStatement();
      String sql = "select * from statistic limit 100";
      //sql = "select * from statistic where ship_id='da8fc177-9cd2-18fd-9edf-dba07d7718b7'";
      ResultSet rs = statement.executeQuery(sql);
      while (rs.next()) {

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

        System.out.println(rs.getString("id"));
        System.out.println(rs.getString("timestamp"));

        int timestampDate = getMonth(rs.getString("timestamp") + "000");
        System.out.println("timestampDate=" + timestampDate);
      }
      rs.close();
      con.close();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      System.out.println("数据库数据成功获取！！");
    }
  }

  public static int getMonth(String millis) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    Calendar clendar = Calendar.getInstance();
    clendar.setTimeInMillis(Long.parseLong(millis));
    Date date = clendar.getTime();
    System.out.println(sdf.format(date));
    return date.getMonth() + 1;
  }
}
