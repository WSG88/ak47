import 'dart:convert';
import 'dart:io';

import 'package:dio/dio.dart';

main() {
  map_region_list1();
}

map_region_list1() async {
  var url = "http://api.hizhu.com/service/store/list.html";
  var jsonData =
      '''{"pageno":1,"limit":10,"sort":-1,"region_ids":[],"plate_ids":[],"money_max":99999,"money_min":0,"logicSort":"0","key":"","key_self":"0","type_no":0,"search_id":"","latitude":"","longitude":"","distance":"0","other_ids":[],"city_code":"001009001","customer_id":"9a893919-ff3c-2000-0863-44b9cf15ea1b","ab_test":"","recommend":0,"update_time":0}
''';
  Map<String, dynamic> params = json.decode(jsonData);
  var headData =
      '''{"CityCode":"001009001","ScreenSize":"560x960","Platform":"pc","Udid":"114.94.213.210","DeviceId":"654321","ClientVer":"4.1","Md5":"c961d4ff8fdca9bccb0e2367d10d2cc7","Session":"TJ_2018112249222"}
''';

  var options = new Options();
  Map<String, dynamic> heads = json.decode(headData);
  options.headers = heads;

  var dio = new Dio(options);

  var response = await dio.post(url, data: params);

  getPr(response);
  getPr(response.data.toString());
}

map_region_list() async {
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
//getOptions() async {
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
