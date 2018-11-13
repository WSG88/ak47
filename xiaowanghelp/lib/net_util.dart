import 'dart:convert';
import 'dart:io';

import 'package:dio/dio.dart';

main() {
  test();
}

test() async {
  var url = "http://testsh.hizhu.com/map/regionlist.html";
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
  Map<String, dynamic> heads = new Map();
  heads['ClientVer'] = "5.4.1";
  heads['CityCode'] = "001009001";
  heads['ScreenSize'] = "810x1440";
  heads['Session'] = "";
  heads['Platform'] = "android";
  heads['Udid'] = "434159512668216";
  heads['os_model'] = "Netease_MuMu";
  heads['md5'] = "90f8a9fe3f48619e8f829c33dd1395fd";
  heads['channel'] = "devoffline";
  heads['OSVer'] = "android6.0.1";
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
//  heads['ScreenSize'] = width.toString() + "x" + height.toString();
//
//  DeviceInfoPlugin deviceInfo = new DeviceInfoPlugin();
//  if (Platform.isIOS) {
//    heads['Platform'] = "ios";
//    IosDeviceInfo iosInfo = await deviceInfo.iosInfo;
//    heads['os_model'] = iosInfo.model;
//    heads['OSVer'] = "ios" + iosInfo.systemVersion;
//  } else if (Platform.isAndroid) {
//    heads['Platform'] = "android";
//    AndroidDeviceInfo androidInfo = await deviceInfo.androidInfo;
//    heads['os_model'] = androidInfo.model;
//    heads['OSVer'] = "android" + androidInfo.version.release;
//  }
//
//  heads['CityCode'] = "001009001";
//  heads['Session'] = "";
//  heads['Udid'] = "434159512668216";
//  heads['md5'] = "90f8a9fe3f48619e8f829c33dd1395fd";
//  heads['channel'] = "devoffline";
//  heads['ClientVer'] = "5.4.1";
//  options.headers = heads;
//  return options;
//}
