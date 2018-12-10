package util;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 个推推送测试
 */
public class GexinAppPush {

  //定义常量, appId、appKey、masterSecret 采用本文档 "第二步 获取访问凭证 "中获得的应用配置7
  private static String appId = "MK3XtHhFmv97ItHERcrz5677";
  private static String appKey = "QiD4lK2oVX6knIXhADvlT477";
  private static String masterSecret = "hiA2QhYCGL8lTSWySzR2h777";
  private static String url = "http://sdk.open.api.igexin.com/apiex.htm";

  public static void main(String[] args) throws IOException {

    IGtPush push = new IGtPush(url, appKey, masterSecret);

    // 定义"点击链接打开通知模板"，并设置标题、内容、链接
    getLinkTemplate();

    List<String> appIds = new ArrayList<String>();
    appIds.add(appId);

    //// 定义"AppMessage"类型消息对象，设置消息内容模板、发送的目标App列表、是否支持离线发送、以及离线消息有效期(单位毫秒)
    //AppMessage appMessage = new AppMessage();
    //appMessage.setData(transmissionTemplateDemo());
    //appMessage.setAppIdList(appIds);
    //appMessage.setOffline(true);
    //appMessage.setOfflineExpireTime(1000 * 600);
    //
    //IPushResult ret = push.pushMessageToApp(appMessage);
    //System.out.println(ret.getResponse().toString());

    SingleMessage message = new SingleMessage();
    message.setOffline(true);
    // 离线有效时间，单位为毫秒，可选
    message.setOfflineExpireTime(24 * 3600 * 1000);
    message.setData(transmissionTemplateDemo());
    // 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
    message.setPushNetWorkType(0);
    String CID = "";
    Target target = new Target();
    target.setAppId(appId);
    target.setClientId(CID);
    //target.setAlias(Alias);
    IPushResult ret = null;
    try {
      ret = push.pushMessageToSingle(message, target);
    } catch (RequestException e) {
      e.printStackTrace();
      ret = push.pushMessageToSingle(message, target, e.getRequestId());
    }
    if (ret != null) {
      System.out.println(ret.getResponse().toString());
    } else {
      System.out.println("服务器响应异常");
    }
  }

  private static LinkTemplate getLinkTemplate() {
    LinkTemplate template = new LinkTemplate();
    template.setAppId(appId);
    template.setAppkey(appKey);
    template.setTitle("请填写通知标题");
    template.setText("请填写通知内容");
    template.setUrl("http://getui.com");
    return template;
  }

  public static TransmissionTemplate transmissionTemplateDemo() {
    TransmissionTemplate template = new TransmissionTemplate();
    template.setAppId(appId);
    template.setAppkey(appKey);
    // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
    template.setTransmissionType(2);
    template.setTransmissionContent("{\n"
        + "\t\"task\": 1,\n"
        + "\t\"push_id\": \"KI452ZVC90\",\n"
        + "\t\"data\": {\n"
        + "\t\t\"notify_id\": \"SXUI128XSAUG\",\n"
        + "\t\t\"web_url\": \"https://www.baidu.com\",\n"
        + "\t\t\"title\": \"111111\",\n"
        + "\t\t\"content\": \"SXUI128XSAUG\"\n"
        + "\t}\n"
        + "}");
    // 设置定时展示时间
    // template.setDuration("2015-01-16 11:40:00", "2015-01-16 12:24:00");
    return template;
  }
}