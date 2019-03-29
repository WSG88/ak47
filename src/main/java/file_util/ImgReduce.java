package file_util;

/**
 * 图片压缩处理
 */
public class ImgReduce {

  //public static void reduceImg(String s, float rate) {
  //  reduceImg(s, 0, 0, rate);
  //}
  //
  //public static void reduceImg(String s, int w, int h, float rate) {
  //  try {
  //    File file = new File(s);
  //    if (!file.exists()) {
  //      System.out.println("文件不存在");
  //    }
  //
  //    if (rate > 0) {
  //      int[] results = getImgWidthHeight(file);
  //      if (results == null || results[0] == 0 || results[1] == 0) {
  //        return;
  //      } else {
  //        w = (int) (results[0] * rate);
  //        h = (int) (results[1] * rate);
  //      }
  //    }
  //
  //    Image image = ImageIO.read(file);
  //    BufferedImage bufferedImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
  //    bufferedImage.getGraphics()
  //        .drawImage(image.getScaledInstance(w, h, Image.SCALE_SMOOTH), 0, 0, null);
  //
  //    String filePath = file.getAbsolutePath();
  //    filePath = filePath.replace(".jpg", "_new.jp");
  //    System.out.println(filePath);
  //
  //    FileOutputStream fileOutputStream = new FileOutputStream(filePath);
  //    JPEGImageEncoder jpegImageEncoder = JPEGCodec.createJPEGEncoder(fileOutputStream);
  //    jpegImageEncoder.encode(bufferedImage);
  //    fileOutputStream.close();
  //  } catch (Exception e) {
  //    e.printStackTrace();
  //  }
  //}
  //
  //public static int[] getImgWidthHeight(File file) {
  //  InputStream inputStream = null;
  //  BufferedImage bufferedImage;
  //  int[] results = { 0, 0 };
  //  try {
  //    inputStream = new FileInputStream(file);
  //    bufferedImage = ImageIO.read(inputStream);
  //    results[0] = bufferedImage.getWidth(null);
  //    results[1] = bufferedImage.getHeight(null);
  //  } catch (Exception e) {
  //    e.printStackTrace();
  //  } finally {
  //    try {
  //      if (inputStream != null) {
  //        inputStream.close();
  //      }
  //    } catch (IOException e) {
  //      e.printStackTrace();
  //    }
  //  }
  //  return results;
  //}
  //
  //public static void main(String[] args) {
  //  File file = new File("C:\\Users\\DELL\\Desktop\\my");
  //  File[] fs = file.listFiles();
  //  for (File f : fs) {
  //    reduceImg(f.getAbsolutePath(), 0.3f);
  //  }
  //}
}
