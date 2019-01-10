package util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by wsig on 2019-01-08.
 */
class TestAnglew {

  public static void fileWriter(String pathname, String content) {
    BufferedWriter writer = null;
    try {
      File file = new File(pathname);
      if (!file.exists()) {
        file.createNewFile();
      }
      writer = new BufferedWriter(new FileWriter(file));
      writer.write(content);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (writer != null) {
          writer.flush();
          writer.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public static void main(String[] args) {
    try {

      Document doc = Jsoup.connect("http://www.anglew.com/product/").get();
      Elements newsHeadlines =
          doc.select("body > div.content > div.leftr > div:nth-child(1) > div.nr > h3 > a");
      for (Element headline : newsHeadlines) {
        String url = headline.absUrl("href");
        String title = headline.attr("title");
        System.out.println("-------------------------------------" + title + " " + url);
        //main1(url);
      }
      StringBuffer sb = new StringBuffer();
      sb.append("{");
      sb.append("\"type\":[");
      for (Element headline : newsHeadlines) {
        sb.append("{");
        sb.append("\"title\":\"" + headline.attr("title") + "\"");
        sb.append(",");
        sb.append("\"url\":\"" + headline.absUrl("href") + "\"");
        sb.append("}");
        sb.append(",");
      }
      sb.append("]");
      sb.append("}");
      String sbb = sb.toString().replace(",]}", "]}");
      fileWriter("D:\\DEMO\\ak47\\src\\main\\java\\util\\TestAnglew.json", sbb);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void main1(String url) {
    try {
      //http://www.anglew.com/tlmxl.html
      Document doc = Jsoup.connect(url).get();
      Elements newsHeadlines = doc.select("dt > a");
      for (Element headline : newsHeadlines) {
        String title = headline.attr("title");
        String href = headline.absUrl("href");
        System.out.println("-------------------------------------" + title + " = " + href);
        main2(href);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void main2(String url) {
    try {
      //"http://www.anglew.com/Products/ageegd.html"
      Document doc = Jsoup.connect(url).get();
      Elements newsHeadlines = doc.select(
          "#printableview > div.pleft > div > div > div > div.proviewbox > div.probigshow > a");
      for (Element headline : newsHeadlines) {
        System.out.println(headline.absUrl("href"));
      }
      newsHeadlines = doc.select("#printableview > div.pright > div:nth-child(1) > h4");
      for (Element headline : newsHeadlines) {
        System.out.println(headline.text());
      }
      newsHeadlines = doc.select("#printableview > div.pright > div > div.cps.pd_short");
      for (Element headline : newsHeadlines) {
        System.out.println(headline.text());
      }
      newsHeadlines = doc.select("#detailvalue0");
      for (Element headline : newsHeadlines) {
        Elements news = headline.getElementsByTag("img");
        for (Element nn : news) {
          String imgUrl = "http://www.anglew.com/" + nn.attr("src");
          if (imgUrl.endsWith("jpg") && imgUrl.contains("product")) {
            System.out.println(imgUrl);
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
