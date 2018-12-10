package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtil {
  private static final String URL = "jdbc:mysql://127.0.0.1:3306/zgd";
  private static final String NAME = "root";
  private static final String PWD = "root";
  private static Connection conn = null;

  static {
    try {
      Class.forName("com.mysql.jdbc.Driver");
      conn = DriverManager.getConnection(URL, NAME, PWD);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static Connection getConnection() {
    return conn;
  }
}

