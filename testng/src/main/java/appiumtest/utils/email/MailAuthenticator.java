package appiumtest.utils.email;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class MailAuthenticator extends Authenticator {

  public static String USERNAME = "";
  public static String PASSWORD = "";

  public MailAuthenticator() {
  }

  protected PasswordAuthentication getPasswordAuthentication() {
    return new PasswordAuthentication(USERNAME, PASSWORD);
  }
}
