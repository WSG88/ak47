import java.io.File;

/**
 * Created by wsig on 2018-10-11.
 */
class TestRenameTo {
  public static void main(String[] args) {
    System.out.println("1111111111111111111111111");
    System.out.println("1111111111111111111111111");
    System.out.println("1111111111111111111111111");
  }

  public static void renameTo() {
    File file = new File("F:\\xcache\\Python图灵学院");
    File[] fs = file.listFiles();
    for (File f : fs) {
      f.renameTo(new File(f.getAbsolutePath() + ".mp4"));
    }
  }
}
