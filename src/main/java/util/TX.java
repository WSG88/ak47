package util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 获取的网络视频地址无任何广告，为CDN最后返回的结果，可直接播放或下载。
 * 有些视频过大，CDN有分段处理，如果多段以“$”隔开。
 * 只对获取腾讯网络视频进行了整理，实际上各大网络视频获取方式都一样，
 * 只要分析下请求链接与参数，然后模拟请求整理成代码即可。
 */

/** 解析腾讯视频 */
public class TX {
  public static void main(String[] args) {
    String url = "https://v.qq.com/x/cover/8oec1592nwztc70.html?vid=b01973axj9x";
    url = "http://vv.video.qq.com/geturl?platform=1&otype=xml&vid=b01973axj9x";
    String s = new TX().getTencentMovieSource(url);
    System.out.println("视频源地址:" + s);
  }

  public String getTencentMovieSource(String url) {
    String html = this.getHtml(url, true).replaceAll("    ", "");
    System.out.println(html);
    String vid = this.getValue(html, "vid:", 1, "\",", 0);
    String urlXml = "http://vv.video.qq.com/geturl?platform=1&otype=xml&vid=" + vid;
    if (urlXml.indexOf("|") == -1) {
      return this.parseXmlSource(urlXml);
    } else {
      String urls = "";
      String[] uls = urlXml.replace("|", "-").split("-");
      for (int i = 0; i < uls.length; i++) {
        String htmls = "http://vv.video.qq.com/geturl?platform=1&otype=xml&vid=" + uls[i];
        urls += this.parseXmlSource(htmls) + "$";
      }
      return urls.substring(0, urls.lastIndexOf("$"));
    }
  }

  private String parseXmlSource(String urlXml) {
    String videoXml = getHtml(urlXml, false);
    return getValue(videoXml, "<url>", "</url>");
  }

  private String getHtml(String url, boolean isformat) {
    System.out.println("Request URL:" + url);
    try {
      URL u = new URL(url);
      HttpURLConnection httpConn = (HttpURLConnection) u.openConnection();
      // 设置user agent确保系统与浏览器版本兼容
      HttpURLConnection.setFollowRedirects(true);
      httpConn.setRequestMethod("GET");
      httpConn.setRequestProperty("User-Agent",
          "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET CLR 2.0.50727)");
      InputStream is = u.openStream();
      int length = 0;
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      while ((length = is.read()) != -1) {
        bos.write(length);
      }
      if (isformat) {
        return new String(bos.toByteArray(), "UTF-8").replace("\r", "").replace("\n", "");
      } else {
        return new String(bos.toByteArray(), "UTF-8");
      }
    } catch (Exception e) {
      e.printStackTrace();

      return "";
    }
  }

  private String getValue(String html, String s1, String s2) {
    try {
      String subHtml = html.substring(html.indexOf(s1));
      subHtml = subHtml.substring(s1.length());
      int s2Len = subHtml.indexOf(s2);
      return String.valueOf(subHtml.substring(0, s2Len));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "";
  }

  private String getValue(String html, String s1, int s1length, String s2, int s2length) {
    try {
      StringBuffer subHtml = new StringBuffer(html.substring(html.indexOf(s1)));
      return String.valueOf(
          subHtml.substring(s1.length() + s1length, subHtml.indexOf(s2) - s2length));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "";
  }
}

