import 'dart:io';
import 'dart:ui';

import 'package:device_info/device_info.dart';
import 'package:dio/dio.dart';

getDio() {
  var options = getOptions();

  return new Dio(options);
}

getOptions() async {
  var options = new Options();
  Map<String, dynamic> heads = new Map();

  var width = window.physicalSize.width;
  var height = window.physicalSize.height;
  heads['ScreenSize'] = width.toString() + "x" + height.toString();

  DeviceInfoPlugin deviceInfo = new DeviceInfoPlugin();
  if (Platform.isIOS) {
    heads['Platform'] = "ios";
    IosDeviceInfo iosInfo = await deviceInfo.iosInfo;
    heads['os_model'] = iosInfo.model;
    heads['OSVer'] = "ios" + iosInfo.systemVersion;
  } else if (Platform.isAndroid) {
    heads['Platform'] = "android";
    AndroidDeviceInfo androidInfo = await deviceInfo.androidInfo;
    heads['os_model'] = androidInfo.model;
    heads['OSVer'] = "android" + androidInfo.version.release;
  }

  heads['CityCode'] = "001009001";
  heads['Session'] = "";
  heads['Udid'] = "434159512668216";
  heads['md5'] = "90f8a9fe3f48619e8f829c33dd1395fd";
  heads['channel'] = "devoffline";
  heads['ClientVer'] = "5.4.1";
  options.headers = heads;
  return options;
}
