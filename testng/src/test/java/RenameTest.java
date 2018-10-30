import java.io.File;

/**
 * Created by wsig on 2018-10-10.
 */
class RenameTest {

  public static void main(String[] args) {
    File file = new File("F:\\xcache\\Python图灵学院");
    File[] fs = file.listFiles();
    for (File f : fs) {
      f.renameTo(new File(f.getAbsolutePath() + ".mp4"));
    }
  }
}
