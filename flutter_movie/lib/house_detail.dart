import 'package:dio/dio.dart';

main() async {
  var url = "http://testsh.hizhu.com/v2/house/detail.html";
  Map<String, dynamic> params = new Map();
  params['room_id'] = "25333fe9-b404-4ab7-aaa6-361a83829315";

  Options options = new Options();
  Map<String, dynamic> map = new Map();
  map['ClientVer'] = "5.4.1";
  map['CityCode'] = "001009001";
  map['ScreenSize'] = "810x1440";
  map['Session'] = "";
  map['Platform'] = "android";
  map['Udid'] = "434159512668216";
  map['os_model'] = "Netease_MuMu";
  map['md5'] = "90f8a9fe3f48619e8f829c33dd1395fd";
  map['channel'] = "devoffline";
  map['OSVer'] = "android6.0.1";
  options.headers = map;

  Dio dio = new Dio(options);
  Response response = await dio.post(url, data: params);
  print(response.data);
}
