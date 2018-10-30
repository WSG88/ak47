package appiumtest.tests;

import appiumtest.utils.AssertionListener;
import appiumtest.utils.SendEmailDemo;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.TestNG;
import org.testng.annotations.Listeners;

//实现监听文件变动执行testng
@Listeners({ AssertionListener.class }) public class ExecuteEntrance {

  /**
   * @param args
   */
  private static long lastTimeapp;
  private static long lastTimereport;
  private static File file = null;
  private static File filereporter = null;
  private static int status = 0;
  private static Runnable executetask;
  private static Runnable emailTask;

  public static void main(String[] args) {

    // 日志对象
    final Logger log = LoggerFactory.getLogger("ExecuteEntrance");

    try {
      file = new File("hizhu.apk");
      filereporter = new File("./test-output/emailable-report.html");
      log.info("已找到apk包");
    } catch (Exception e) {
      log.info("没有找打apk包");
    }

    // 首先文件的最近一次修改时间戳
    lastTimeapp = file.lastModified();
    lastTimereport = filereporter.lastModified();
    // 定时任务，每秒来判断一下文件是否发生变动，即判断lastModified是否改变
    ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);
    executetask = new Runnable() {
      @Override public void run() {
        //往消息队列中加入线程

        if (file.lastModified() > lastTimeapp) {

          log.info("file update! time : " + file.lastModified());
          lastTimeapp = file.lastModified();
          //实现运行testng
          TestNG testNG = new TestNG();
          List<String> suites = new ArrayList<String>();
          suites.add(".\\testng.xml");
          //suites.add(".\\test-output\\testng-failed.xml");
          testNG.setTestSuites(suites);
          testNG.run();
          status = 1;
          System.out.println("更改status为" + status);
        }
      }
    };
    scheduledExecutorService.scheduleAtFixedRate(executetask, 0, 10, TimeUnit.SECONDS);

    //邮件
    emailTask = new Runnable() {
      @Override public void run() {

        if (status == 1) {
          if (filereporter.lastModified() > lastTimereport) {
            log.info("测试报告更新时间file update! time : " + filereporter.lastModified());
            lastTimereport = filereporter.lastModified();
            //调用发邮件
            SendEmailDemo send = new SendEmailDemo();
            String result = send.sendEmail();
            log.info("邮件发送情况" + result);
          }
        }
      }
    };
    scheduledExecutorService.scheduleAtFixedRate(emailTask, 0, 10, TimeUnit.SECONDS);
  }
}
