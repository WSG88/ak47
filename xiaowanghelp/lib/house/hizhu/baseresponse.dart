import 'dart:convert';

class BaseResponse {
  final String status;
  final String message;
  final dynamic data;

  BaseResponse({
    this.status,
    this.message,
    this.data,
  });

  static BaseResponse decodeData(String jsonData) {
    var data = json.decode(jsonData);
    return fromMap(data);
  }

  static BaseResponse fromMap(Map<String, dynamic> map) {
    return new BaseResponse(
        status: map["status"], message: map["message"], data: map["data"]);
  }

  @override
  String toString() {
    return 'BaseData{status: $status, message: $message, data: $data}';
  }
}
