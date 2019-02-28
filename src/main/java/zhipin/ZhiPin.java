package zhipin;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by wsig on 2019-01-08.
 */
class ZhiPin {

  public static void main(String[] args) {
    int page = 1;
    String key = "测试";
    for (int i = 1; i < 50; i++) {
      page = 1;
      list(key, page);
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  public static void list(String key, int page) {
    try {
      Document doc;

      //String url = "https://www.zhipin.com/job_detail/?query="
      //    + URLEncoder.encode(key)
      //    + "&city=101010100&page="
      //    + page;
      //System.out.println(url);
      //doc = Jsoup.connect(url).get();

      File input = new File("D:\\DEMO\\ak47\\src\\main\\java\\zhipin\\list.html");
      doc = Jsoup.parse(input, "UTF-8", "http://www.zhipin.com/");

      Elements elements = doc.select("#main > div > div.job-list > ul > li");
      System.out.println(elements.size());
      for (Element element : elements) {
        String job_href = element.select("div > div.info-primary > h3 > a").attr("href");
        String job_title =
            element.select(" div > div.info-primary > h3 > a > div.job-title").text();
        String job_money = element.select(" div > div.info-primary > h3 > a > span").text();

        String sp = "<em class=\"vline\"></em>";

        try {
          String job_html = element.select(" div > div.info-primary > p").html();
          String job_address = job_html.split(sp)[0];
          String job_year = job_html.split(sp)[1];
          String job_edu = job_html.split(sp)[2];
        } catch (Exception e) {
          e.printStackTrace();
        }

        String job_company = element.select(" div > div.info-company > div > h3 > a").text();

        try {
          String job_company_html = element.select(" div > div.info-company > div > p").html();
          String job_company_desc1 = job_company_html.split(sp)[0];
          String job_company_desc2 = job_company_html.split(sp)[1];
          String job_company_desc3 = job_company_html.split(sp)[2];
        } catch (Exception e) {
          e.printStackTrace();
        }

        String job_publish_people = element.select(" div > div.info-publis > h3").text();
        String job_publish_time = element.select(" div > div.info-publis > p").text();

        detail("https://www.zhipin.com" + job_href);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void detail(String url) {
    try {
      Document doc;
      System.out.println(url);
      doc = Jsoup.connect(url).get();

      //File input = new File("D:\\DEMO\\ak47\\src\\main\\java\\zhipin\\detail.html");
      //doc = Jsoup.parse(input, "UTF-8", "http://www.zhipin.com/");

      String job_desc = doc.select(
          "#main > div.job-box > div > div.job-detail > div.detail-content > div:nth-child(1) > div")
          .text();
      System.out.println(job_desc);

      String job_company = doc.select(
          "#main > div.job-box > div > div.job-detail > div.detail-content > div.job-sec.company-info > div")
          .text();
      System.out.println(job_company);

      fileWriter("test_.txt", job_desc + "\n");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void fileWriter(String pathname, String content) {
    try {
      RandomAccessFile randomAccessFile = new RandomAccessFile(pathname, "rw");
      randomAccessFile.seek(randomAccessFile.length());
      randomAccessFile.write(content.getBytes());
      randomAccessFile.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
