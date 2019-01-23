package net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

public class HttpUtil {

  public static String get(String url, String param) {
    String result = "";
    BufferedReader bufferedReader = null;
    try {
      String urlNameString = url + "?" + param;
      URL mURL = new URL(urlNameString);
      URLConnection connection = getUrlConnection(mURL);
      connection.connect();

      bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        result += line;
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (bufferedReader != null) {
          bufferedReader.close();
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return result;
  }

  public static String post(String url, String param) {
    String result = "";
    BufferedReader bufferedReader = null;
    PrintWriter printWriter = null;
    try {
      URL mURL = new URL(url);
      URLConnection connection = getUrlConnection(mURL);
      connection.setDoOutput(true);
      connection.setDoInput(true);
      printWriter = new PrintWriter(connection.getOutputStream());
      printWriter.print(param);
      printWriter.flush();

      bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        result += line;
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (printWriter != null) {
          printWriter.close();
        }
        if (bufferedReader != null) {
          bufferedReader.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return result;
  }

  private static URLConnection getUrlConnection(URL mURL) throws IOException {
    URLConnection connection = mURL.openConnection();
    connection.setRequestProperty("accept", "*/*");
    connection.setRequestProperty("connection", "Keep-Alive");
    connection.setRequestProperty("user-agent",
        "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
    return connection;
  }
}
