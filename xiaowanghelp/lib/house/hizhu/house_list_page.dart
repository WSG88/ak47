import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:flutter_refresh/flutter_refresh.dart';
import 'package:xiaowanghelp/constants.dart';
import 'package:xiaowanghelp/house/hizhu/house.dart';
import 'package:xiaowanghelp/house/hizhu/house_detail_page.dart';
import 'package:xiaowanghelp/net_util.dart';

class HouseListPage extends StatefulWidget {
  @override
  HouseListPageState createState() => new HouseListPageState();
}

class HouseListPageState extends State<HouseListPage> {
  List<House> houses = [];
  int page = 1;

  @override
  void initState() {
    super.initState();
    getHouseListData(page);
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
//      content = new ListView(children: buildHouseItems());

      content = new Refresh(
        onFooterRefresh: onFooterRefresh,
        onHeaderRefresh: onHeaderRefresh,
        childBuilder: (BuildContext context,
            {ScrollController controller, ScrollPhysics physics}) {
          return new Container(
              child: new ListView.builder(
                  itemCount: houses.length,
                  controller: controller,
                  physics: physics,
                  itemBuilder: (context, i) => renderHouseRow(i)));
        },
      );
    }
    return new Scaffold(
      appBar: new AppBar(
        title: new Text('嗨住租房'),
      ),
      body: content,
    );
  }

  buildHouseItems() {
    List<Widget> widgets = [];
    for (int i = 0; i < houses.length; i++) {
      widgets.add(renderHouseRow(i));
    }
    return widgets;
  }

  renderHouseRow(int i) {
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
        width: 60.0,
        height: 50.0,
        fit: BoxFit.fill,
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
      key: new Key(i.toString()),
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
    return houseItem;
  }

  // 跳转页面

  navigateToMovieDetailPage(House house, Object imageTag) {
    Navigator.of(context)
        .push(new MaterialPageRoute(builder: (BuildContext context) {
      return new HouseDetailPage(house, imageTag: imageTag);
    }));
  }

  // 网络请求

  Future<Null> onFooterRefresh() {
    return new Future.delayed(new Duration(seconds: 2), () {
      setState(() {
        page++;
        getHouseListData(page);
      });
    });
  }

  Future<Null> onHeaderRefresh() {
    return new Future.delayed(new Duration(seconds: 2), () {
      setState(() {
        page = 1;
        getHouseListData(page);
      });
    });
  }

  getHouseListData(int page) async {
    var url = Cont.hizhu_house_list;

    Map<String, dynamic> params = new Map();
    params['money_max'] = 0;
    params['money_min'] = 0;
    params['key'] = "通河新村";
    params['pageno'] = page;

    params['key_self'] = "1";
    params['sort'] = -1;
    params['limit'] = 20;
    params['type_no'] = 0;
    params['latitude'] = 0.0;
    params['longitude'] = 0.0;
    params['line_ids'] = [];
    params['other_ids'] = [];
    params['plate_ids'] = [];
    params['region_ids'] = [];
    params['stand_ids'] = [];
    params['distance'] = "0";
    params['logicSort'] = "0";
    params['excluded_estate_id'] = "";

    var dio = new Dio(getOptionsHizhu());

    var response = await dio.post(url, data: params);

    if (response.statusCode == 200) {
      var jsonData = response.data;

      setState(() {
        if (page == 1) {
          houses = House.getList(jsonData);
        } else {
          houses.addAll(House.getList(jsonData));
        }
      });
    }
  }
}
