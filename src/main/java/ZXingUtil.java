import com.google.zxing.BarcodeFormat;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 * Created by wsig on 2019-04-02.
 */
class ZXingUtil {
  public static void main(String[] args) {

    String fileContent =
        "{\"s\": \"money\",\"u\": \"2088521328947850\",\"a\": \"0.01\",\"m\":\"1111111\"}";
    String fileName = System.currentTimeMillis() + ".jpg";
    String filePath = ".";
    String fileFormat = "jpg";
    int width = 200;
    int height = 200;

    //生成
    String ss = createQR(fileContent, fileFormat, filePath, fileName, width, height);
    //解析
    getQR(ss);
  }

  private static String getQR(String filePath) {
    try {
      File file = new File(filePath);
      BufferedImage bufferedImage = ImageIO.read(file);
      LuminanceSource luminanceSource = new BufferedImageLuminanceSource(bufferedImage);
      Binarizer binarizer = new HybridBinarizer(luminanceSource);
      BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
      Result result = new MultiFormatReader().decode(binaryBitmap, null);
      String res = result.toString();
      System.out.println("getQR = " + res);
      return res;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "";
  }

  private static String createQR(String fileContent, String fileFormat, String filePath,
      String fileName, int width, int height) {
    try {
      File file = new File(filePath, fileName);
      BitMatrix bitMatrix =
          new MultiFormatWriter().encode(fileContent, BarcodeFormat.QR_CODE, width, height, null);
      BufferedImage bufferedImage = new BufferedImage(bitMatrix.getWidth(), bitMatrix.getHeight(),
          BufferedImage.TYPE_INT_RGB);
      for (int x = 0; x < width; x++) {
        for (int y = 0; y < height; y++) {
          bufferedImage.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
        }
      }
      if (ImageIO.write(bufferedImage, fileFormat, file)) {
        String res = file.getAbsolutePath();
        System.out.println("createQR = " + res);
        return res;
      } else {
        return "";
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "";
  }

  static class BufferedImageLuminanceSource extends LuminanceSource {

    private final BufferedImage image;
    private final int left;
    private final int top;

    BufferedImageLuminanceSource(BufferedImage image) {
      this(image, 0, 0, image.getWidth(), image.getHeight());
    }

    BufferedImageLuminanceSource(BufferedImage image, int left, int top, int width, int height) {
      super(width, height);

      int sourceWidth = image.getWidth();
      int sourceHeight = image.getHeight();
      if (left + width > sourceWidth || top + height > sourceHeight) {
        throw new IllegalArgumentException("Crop rectangle does not fit within image data.");
      }

      for (int y = top; y < top + height; y++) {
        for (int x = left; x < left + width; x++) {
          if ((image.getRGB(x, y) & 0xFF000000) == 0) {
            image.setRGB(x, y, 0xFFFFFFFF);
          }
        }
      }

      this.image = new BufferedImage(sourceWidth, sourceHeight, BufferedImage.TYPE_BYTE_GRAY);
      this.image.getGraphics().drawImage(image, 0, 0, null);
      this.left = left;
      this.top = top;
    }

    @Override public byte[] getRow(int y, byte[] row) {
      if (y < 0 || y >= getHeight()) {
        throw new IllegalArgumentException("Requested row is outside the image: " + y);
      }
      int width = getWidth();
      if (row == null || row.length < width) {
        row = new byte[width];
      }
      image.getRaster().getDataElements(left, top + y, width, 1, row);
      return row;
    }

    @Override public byte[] getMatrix() {
      int width = getWidth();
      int height = getHeight();
      int area = width * height;
      byte[] matrix = new byte[area];
      image.getRaster().getDataElements(left, top, width, height, matrix);
      return matrix;
    }

    @Override public boolean isCropSupported() {
      return true;
    }

    @Override public LuminanceSource crop(int left, int top, int width, int height) {
      return new BufferedImageLuminanceSource(image, this.left + left, this.top + top, width,
          height);
    }

    @Override public boolean isRotateSupported() {
      return true;
    }

    @Override public LuminanceSource rotateCounterClockwise() {

      int sourceWidth = image.getWidth();
      int sourceHeight = image.getHeight();

      AffineTransform transform = new AffineTransform(0.0, -1.0, 1.0, 0.0, 0.0, sourceWidth);

      BufferedImage rotatedImage =
          new BufferedImage(sourceHeight, sourceWidth, BufferedImage.TYPE_BYTE_GRAY);

      Graphics2D g = rotatedImage.createGraphics();
      g.drawImage(image, transform, null);
      g.dispose();

      int width = getWidth();
      return new BufferedImageLuminanceSource(rotatedImage, top, sourceWidth - (left + width),
          getHeight(), width);
    }
  }
}
