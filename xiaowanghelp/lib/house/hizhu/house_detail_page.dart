import 'dart:io';
import 'dart:ui';

import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:meta/meta.dart';
import 'package:xiaowanghelp/constants.dart';
import 'package:xiaowanghelp/house/banner/slide.dart';
import 'package:xiaowanghelp/house/hizhu/house.dart';
import 'package:xiaowanghelp/net_util.dart';

class HouseDetailPage extends StatefulWidget {
  final House house;
  final Object imageTag;

  HouseDetailPage(
    this.house, {
    @required this.imageTag,
  });

  @override
  HouseDetailPageState createState() => new HouseDetailPageState();
}

class HouseDetailPageState extends State<HouseDetailPage> {
  House houseDetail;

  @override
  void initState() {
    super.initState();
    getHouseDetail();
  }

  @override
  Widget build(BuildContext context) {
    var content;
    if (houseDetail == null) {
      content = new Center(
        child: new CircularProgressIndicator(),
      );
    } else {
      content = setData(houseDetail);
      new Padding(
        padding: const EdgeInsets.only(
          top: 10.0,
          left: 10.0,
          right: 10.0,
          bottom: 10.0,
        ),
        child: new Scrollbar(
          child: content,
        ),
      );
    }

    return new Scaffold(
      appBar: new AppBar(
        title: new Text(widget.house.estate_name),
      ),
      body: content,
    );
  }

  Widget renderItem(title, img, index) {
    return new Container(
      padding: new EdgeInsets.all(12.0),
      margin: new EdgeInsets.fromLTRB(12.0, 16.0, 12.0, 0.0),
      // 圆角和阴影
      decoration: new BoxDecoration(
        color: Colors.white,
        shape: BoxShape.rectangle,
        borderRadius: new BorderRadius.all(const Radius.circular(2.0)),
        boxShadow: [
          new BoxShadow(
            offset: new Offset(0.0, 1.2),
            blurRadius: 1.0,
            color: const Color(0xaadddddd),
          ),
        ],
      ),
      // 内容
      child: new Row(
        crossAxisAlignment: CrossAxisAlignment.start, // 顶端对齐
        children: <Widget>[
          new Expanded(
            child: new Padding(
              padding: new EdgeInsets.only(right: 12.0),
              child: new Text(title),
            ),
          ),
          // 圆角的图片
          new Container(
            width: 72.0,
            height: 64.0,
            decoration: new BoxDecoration(
              color: Colors.black12,
              image: new DecorationImage(
                image: new NetworkImage(img),
                fit: BoxFit.cover,
              ),
              shape: BoxShape.rectangle,
              borderRadius: new BorderRadius.all(const Radius.circular(2.0)),
            ),
          ),
        ],
      ),
    );
  }

  setData(House houseDetail) {
    var title = houseDetail.estate_name +
        " - " +
        houseDetail.room_name +
        " \n " +
        houseDetail.room_money +
        "元/月";
    var img = houseDetail.image_urls[0];
    var houseImage = renderItem(title, img, 0);

    List datas = [];
    for (var i = 0; i < houseDetail.image_urls.length; i++) {
      var map = new Map();
      map["id"] = i + 1;
      map["image"] = houseDetail.image_urls[i];
      map["title"] = houseDetail.image_urls[i];
      datas.add(map);
    }

    var banner = new Slide(data: datas);

    var houseInfo = new Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      mainAxisSize: MainAxisSize.min,
      children: <Widget>[
        new Text(
          houseDetail.estate_name,
          textAlign: TextAlign.left,
          style: new TextStyle(fontWeight: FontWeight.bold, fontSize: 14.0),
        ),
        new Text(houseDetail.room_money + "元/月"),
        new Text(
          houseDetail.room_name.toString(),
          style: new TextStyle(
            fontSize: 12.0,
            color: Colors.grey,
          ),
        ),
      ],
    );
    return new Padding(
      padding: const EdgeInsets.only(
        top: 10.0,
        left: 10.0,
        right: 10.0,
        bottom: 10.0,
      ),
      child: new Scrollbar(
        child: new Column(
          children: <Widget>[
            banner,
            houseImage,
            houseInfo,
          ],
        ),
      ),
    );
  }

  // 网络请求

  getHouseDetail() async {
    var url = Cont.hizhu_house_detail;

    Map<String, dynamic> params = new Map();
    params['room_id'] = widget.house.room_id;

    var dio = new Dio(getOptionsHizhu());

    var response = await dio.post(url, data: params);

    if (response.statusCode == HttpStatus.ok) {
      var jsonData = response.data;
      setState(() {
        houseDetail = House.getItem(jsonData);
      });
    }
  }
}
