class Debug {
  static d(String text) {
    DateTime now = new DateTime.now();
    print('[] ：$now $text');
  }

  static log(String tag, String text) {
    DateTime now = new DateTime.now();
    print('[$tag]  ：$now $text');
  }
}
