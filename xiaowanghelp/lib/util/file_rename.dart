import 'dart:io';

main(args) async {
  //文件
  File file = new File("E:/image/2ef39fe119657c2f");
  //目录
  var parentDir = file.parent;
  //目录下文件
  Stream<FileSystemEntity> entityList =
      parentDir.list(recursive: false, followLinks: false);
  //
  await for (FileSystemEntity entity in entityList) {
    var path = entity.path;

    print(path);

    if (path.endsWith(".jpg.jpg")) {
      entity.rename(entity.path.replaceAll(".jpg.jpg", "") + ".jpg");
    }
  }
}
