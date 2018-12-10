package flower;

import com.alibaba.fastjson.JSON;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Sink;
import okio.Source;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 大众点评花店地址获取
 */
public class Dp {

  public static void main(String[] args) {
    String url1 = "https://www.dianping.com/search/keyword/135/0_%E9%B2%9C%E8%8A%B1";
    int page = 25;

    url1 = "https://www.dianping.com/search/keyword/5/0_%E9%B2%9C%E8%8A%B1";
    page = 2;

    String url = url1;
    try {
      List<Flower> fs = new ArrayList<Flower>();
      for (int i = 1; i < page; i++) {
        if (i > 1) {
          url = url1 + "/p" + i;
        }
        System.out.println(url);
        Thread.sleep(500);

        Document document;

        //document = Jsoup.connect(url).get();

        //                document = Jsoup.connect(url)
        //                        .userAgent("Mozilla/5.0(Macintosh;U;IntelMacOSX10_6_8;en-us)AppleWebKit/534.50(KHTML,likeGecko)Version/5.1Safari/534.50")
        //                        .get();

        document = Jsoup.parse(new File("D:\\DEMO\\ak47\\testng\\src\\test\\java\\flower\\dp.html"),
            "utf-8");

        Elements elements = document.select("div.txt");

        for (Element element : elements) {
          Flower flower = new Flower();
          Elements elements1 = element.getAllElements();
          for (Element element1 : elements1) {
            if (element1.text() != null && element1.text().length() > 0) {
              //                            System.out.println("        " + element1.tagName() + "," + element1.id() + "," + element1.className() + "," + element1.text());
              if ("h4".equals(element1.tagName())) {
                flower.name = element1.text();
              }
              if ("a".equals(element1.tagName()) && "review-num".equals(element1.className())) {
                flower.review = element1.text();
              }
              if ("a".equals(element1.tagName()) && "mean-price".equals(element1.className())) {
                flower.mean = element1.text();
              }
              if ("span".equals(element1.tagName()) && "tag".equals(element1.className())) {
                flower.tag = element1.text();
              }
              if ("span".equals(element1.tagName()) && "addr".equals(element1.className())) {
                flower.addr = element1.text();
              }
              if ("a".equals(element1.tagName())) {
                String relHref = element1.attr("href");
                if (relHref != null && relHref.contains("www.dianping.com/shop")) {
                  flower.href = relHref;
                }
              }
            }
          }
          fs.add(flower);
        }
      }
      System.out.println("                                                       " + fs.size());
      for (int j = 0; j < fs.size(); j++) {
        System.out.println(fs.get(j).toString());
      }
      String str = JSON.toJSONString(fs);
      System.out.println(" " + str);

      writeFile("./jst" + System.currentTimeMillis() + ".json", str);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void readLines(File file) {
    try {
      System.out.println(file.getAbsolutePath());
      Source fileSource = Okio.source(file);
      BufferedSource bufferedSource = Okio.buffer(fileSource);
      while (true) {
        String line = bufferedSource.readUtf8Line();
        if (line == null) break;
        System.out.print(line.trim());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void writeFile(String filePath, String data) {
    Sink sink;
    BufferedSink bSink = null;
    try {
      File file1 = new File(filePath);
      if (!file1.exists()) {
        file1.createNewFile();
      }
      sink = Okio.sink(file1);
      bSink = Okio.buffer(sink);
      bSink.writeUtf8(data);
      bSink.flush();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (null != bSink) {
          bSink.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}


