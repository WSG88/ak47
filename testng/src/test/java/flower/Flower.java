package flower;

import java.io.Serializable;

/**
 * Created by wsig on 2018-10-16.
 */
class Flower implements Serializable {
  String name;
  String review;
  String mean;
  String tag;
  String addr;
  String href;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getReview() {
    return review;
  }

  public void setReview(String review) {
    this.review = review;
  }

  public String getMean() {
    return mean;
  }

  public void setMean(String mean) {
    this.mean = mean;
  }

  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

  public String getAddr() {
    return addr;
  }

  public void setAddr(String addr) {
    this.addr = addr;
  }

  public String getHref() {
    return href;
  }

  public void setHref(String href) {
    this.href = href;
  }

  @Override public String toString() {
    return "Flower{"
        + "name='"
        + name
        + '\''
        + ", review='"
        + review
        + '\''
        + ", mean='"
        + mean
        + '\''
        + ", tag='"
        + tag
        + '\''
        + ", addr='"
        + addr
        + '\''
        + ", href='"
        + href
        + '\''
        + '}';
  }
}
