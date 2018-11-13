import 'dart:convert';

class House {
  String room_id;
  String estate_name;
  String room_name;
  String room_money;
  String main_img_path;
  List<dynamic> image_urls;

  static List<House> getList(String jsonData) {
    List<House> houses = new List<House>();
    try {
      var data = json.decode(jsonData);
      var results = data['data'];
      var houseList = results['house_list'];
      for (int i = 0; i < houseList.length; i++) {
        houses.add(fromMap(houseList[i]));
      }
    } catch (e) {
      print(e);
    }
    return houses;
  }

  static House getItem(String jsonData) {
    var data = json.decode(jsonData);
    var results = data['data'];
    return fromMap(results);
  }

  static House fromMap(Map<String, dynamic> map) {
    var house = new House();
    house.room_id = map["room_id"];
    house.estate_name = map["estate_name"];
    house.room_name = map["room_name"];
    house.room_money = map["room_money"];
    house.main_img_path = map["main_img_path"];
    if (map.containsKey("image_urls")) {
      house.image_urls = map["image_urls"];
    } else {
      house.image_urls = [];
    }
    return house;
  }

  @override
  String toString() {
    return 'House{room_id: $room_id, estate_name: $estate_name, room_name: $room_name, room_money: $room_money, main_img_path: $main_img_path, image_urls: $image_urls}';
  }
}
