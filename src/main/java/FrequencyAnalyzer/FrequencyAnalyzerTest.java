package FrequencyAnalyzer;

import com.kennycason.kumo.CollisionMode;
import com.kennycason.kumo.WordCloud;
import com.kennycason.kumo.WordFrequency;
import com.kennycason.kumo.bg.CircleBackground;
import com.kennycason.kumo.font.KumoFont;
import com.kennycason.kumo.font.scale.SqrtFontScalar;
import com.kennycason.kumo.nlp.FrequencyAnalyzer;
import com.kennycason.kumo.nlp.tokenizers.ChineseWordTokenizer;
import com.kennycason.kumo.palette.LinearGradientColorPalette;
import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;
import java.util.List;

/**
 * Created by wsig on 2019-02-27.
 */
class FrequencyAnalyzerTest {
  public static void main(String[] args) {
    //建立词频分析器，设置词频，以及词语最短长度，此处的参数配置视情况而定即可
    FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
    frequencyAnalyzer.setWordFrequenciesToReturn(600);
    frequencyAnalyzer.setMinWordLength(2);

    //引入中文解析器
    frequencyAnalyzer.setWordTokenizer(new ChineseWordTokenizer());
    //设置图片分辨率
    Dimension dimension = new Dimension(1920, 1080);
    //此处的设置采用内置常量即可，生成词云对象
    WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
    //设置边界及字体
    wordCloud.setPadding(2);
    java.awt.Font font = new java.awt.Font("STSong-Light", 2, 20);
    //设置词云显示的三种颜色，越靠前设置表示词频越高的词语的颜色
    wordCloud.setColorPalette(
        new LinearGradientColorPalette(Color.RED, Color.BLUE, Color.GREEN, 30, 30));
    wordCloud.setKumoFont(new KumoFont(font));
    //设置背景色
    wordCloud.setBackgroundColor(new Color(255, 255, 255));
    //设置背景图片
    //wordCloud.setBackground(new PixelBoundryBackground("E:\\爬虫/google.jpg"));
    //设置背景图层为圆形
    wordCloud.setBackground(new CircleBackground(255));
    wordCloud.setFontScalar(new SqrtFontScalar(12, 45));

    //指定文本文件路径，生成词频集合
    try {
      List<WordFrequency> wordFrequencyList = frequencyAnalyzer.load("test_.txt");
      wordCloud.build(wordFrequencyList);
      wordCloud.writeToFile("test_.png");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
