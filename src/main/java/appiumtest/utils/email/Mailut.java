package appiumtest.utils.email;

import java.io.File;
import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

/**
 * 邮件发送操作类
 *
 * @author Administrator
 */
public class Mailut {

  /**
   * 发送邮件
   *
   * @param user 发件人邮箱
   * @param password 授权码（注意不是邮箱登录密码）
   * @param from 发件人
   * @param to 接收者邮箱
   * @param subject 邮件主题
   * @param content 邮件内容
   * @return success 发送成功 failure 发送失败
   * @throws Exception
   */
  public String sendMail(String user, String password, String host, String from, String to,
      String subject, String content, File attachment) throws Exception {
    if (to != null) {
      Properties props = System.getProperties();
      props.put("mail.smtp.host", host);
      props.put("mail.smtp.auth", "true");
      MailAuthenticator auth = new MailAuthenticator();
      MailAuthenticator.USERNAME = user;
      MailAuthenticator.PASSWORD = password;
      Session session = Session.getInstance(props, auth);
      // session = MailUtils.createSession("smtp.163.com", 用户名, 密码);
      session.setDebug(true);
      try {
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        if (!to.trim().equals("")) {
          message.addRecipient(Message.RecipientType.TO, new InternetAddress(to.trim()));
        }
        message.setSubject(subject);
        MimeBodyPart mbp1 = new MimeBodyPart(); // 正文
        mbp1.setContent(content, "text/html;charset=utf-8");
        Multipart mp = new MimeMultipart(); // 整个邮件：正文+附件
        mp.addBodyPart(mbp1);
        if (attachment != null) {
          BodyPart attachmentBodyPart = new MimeBodyPart();
          DataSource source = new FileDataSource(attachment);
          attachmentBodyPart.setDataHandler(new DataHandler(source));

          // MimeUtility.encodeWord可以避免文件名乱码
          attachmentBodyPart.setFileName(MimeUtility.encodeWord(attachment.getName()));
          mp.addBodyPart(attachmentBodyPart);
        }
        // mp.addBodyPart(mbp2);
        message.setContent(mp);
        message.setSentDate(new Date());
        message.saveChanges();
        Transport trans = session.getTransport("smtp");
        trans.send(message);
        System.out.println(message.toString());
      } catch (Exception e) {
        e.printStackTrace();
        return "failure";
      }
      return "success";
    } else {
      return "failure";
    }
  }
}
