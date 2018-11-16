import 'dart:convert';
import 'dart:io';

import 'package:dio/dio.dart';

main() {
}

map_regionlist() async {
  var url = "http://testsh.haizhu.com/map/regionlist.html";
  var jsonData =
      '''{"distance":"3000","key":"","latitude":0.0,"longitude":0.0,"money_max":0,"money_min":0,"other_ids":[]}
''';
  Map<String, dynamic> params = json.decode(jsonData);

  var dio = new Dio(getOptionsHizhu());

  var response = await dio.post(url, data: params);

  if (response.statusCode == HttpStatus.ok) {
    getPr(response.data.toString());
  }
}

getOptionsHizhu() {
  var options = new Options();
  var jsonData =
      '''{"ClientVer":"5.5","CityCode":"001009001","ScreenSize":"810x1440","Session":"","Udid":"434159512668216","os_model":"Netease_MuMu","md5":"8890f8a9fe3f48619e8f829c33dd1395fd","channel":"111","OSVer":"ios11"}
''';
  Map<String, dynamic> heads = json.decode(jsonData);
  options.headers = heads;
  getPr(heads);
  return options;
}

///打印日志
getPr(dynamic s) {
  print(s);
}
//getOptionsHizhu1() async {
//  var options = new Options();
//  Map<String, dynamic> heads = new Map();
//
//  var width = window.physicalSize.width;
//  var height = window.physicalSize.height;
//
//  DeviceInfoPlugin deviceInfo = new DeviceInfoPlugin();
//  if (Platform.isIOS) {
//  } else if (Platform.isAndroid) {
//  }
//
//  return options;
//}
