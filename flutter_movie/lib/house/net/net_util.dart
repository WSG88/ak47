import 'package:dio/dio.dart';

getDio() {
  var options = getOptions();

  return new Dio(options);
}

getOptions() {
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
  return options;
}
