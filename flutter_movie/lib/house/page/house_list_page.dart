import 'package:flutter/material.dart';
import 'package:flutter_movie/house/bean/house.dart';
import 'package:flutter_movie/house/net/net_util.dart';
import 'package:flutter_movie/house/page/house_detail_page.dart';

class HouseListPage extends StatefulWidget {
  @override
  HouseListPageState createState() => new HouseListPageState();
}

class HouseListPageState extends State<HouseListPage> {
  List<House> houses = [];

  @override
  void initState() {
    super.initState();
    getHouseListData();
  }

  @override
  Widget build(BuildContext context) {
    var content;
    if (houses.isEmpty) {
      content = new Center(
        // 可选参数 child:
        child: new CircularProgressIndicator(),
      );
    } else {
      content = new ListView(children: buildHouseItems());
    }

    return new Scaffold(
      appBar: new AppBar(
        title: new Text('租房'),
        actions: <Widget>[
          new IconButton(
            icon: new Icon(Icons.search),
            onPressed: () {
              print('onclick search');
            },
          )
        ],
      ),
      body: content,
    );
  }

  buildHouseItems() {
    List<Widget> widgets = [];
    for (int i = 0; i < houses.length; i++) {
      House house = houses[i];
      var houseImage = new Padding(
        padding: const EdgeInsets.only(
          top: 10.0,
          left: 10.0,
          right: 10.0,
          bottom: 10.0,
        ),
        child: new Image.network(
          house.main_img_path.replaceFirst("https", "http"),
          width: 180.0,
          height: 140.0,
        ),
      );

      var houseInfo = new Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        mainAxisSize: MainAxisSize.min,
        children: <Widget>[
          new Text(
            house.estate_name,
            textAlign: TextAlign.left,
            style: new TextStyle(fontWeight: FontWeight.bold, fontSize: 14.0),
          ),
          new Text(house.room_money + "元/月"),
          new Text(
            house.room_name.toString(),
            style: new TextStyle(
              fontSize: 12.0,
              color: Colors.grey,
            ),
          ),
        ],
      );

      var houseItem = new GestureDetector(
        //点击事件
        onTap: () => navigateToMovieDetailPage(house, i),

        child: new Column(
          children: <Widget>[
            new Row(
              children: <Widget>[
                houseImage,
                new Expanded(
                  child: houseInfo,
                ),
                const Icon(Icons.keyboard_arrow_right),
              ],
            ),
            new Divider(),
          ],
        ),
      );

      widgets.add(houseItem);
    }
    return widgets;
  }

  // 跳转页面

  navigateToMovieDetailPage(House house, Object imageTag) {
    Navigator.of(context)
        .push(new MaterialPageRoute(builder: (BuildContext context) {
      return new HouseDetailPage(house, imageTag: imageTag);
    }));
  }

  // 网络请求

  getHouseListData() async {
    var url = "http://testsh.hizhu.com/v12/house/list.html";

    Map<String, dynamic> params = new Map();
    params['logicSort'] = "0";
    params['distance'] = "0";
    params['excluded_estate_id'] = "";
    params['key'] = "";
    params['sort'] = -1;
    params['pageno'] = 1;
    params['limit'] = 20;
    params['type_no'] = 0;
    params['key_self'] = 0;
    params['money_max'] = 0;
    params['money_min'] = 0;
    params['latitude'] = 0.0;
    params['longitude'] = 0.0;
    params['line_ids'] = [];
    params['other_ids'] = [];
    params['plate_ids'] = [];
    params['region_ids'] = [];
    params['stand_ids'] = [];

    var dio = getDio();

    var response = await dio.post(url, data: params);

    if (response.statusCode == 200) {
      var jsonData = response.data;

      setState(() {
        houses = House.getList(jsonData);
      });
    }
  }
}
