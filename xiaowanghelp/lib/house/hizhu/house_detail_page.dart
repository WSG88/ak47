import 'dart:io';
import 'dart:ui';

import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:xiaowanghelp/house/hizhu/house.dart';
import 'package:xiaowanghelp/net_util.dart';
import 'package:meta/meta.dart';

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

  setData(House houseDetail) {
    var houseImage = new Hero(
      tag: widget.imageTag,
      child: new Center(
        child: new Image.network(
          houseDetail.image_urls[0].replaceFirst("https", "http"),
          width: window.physicalSize.width,
          height: 200.0,
          fit: BoxFit.fill,
        ),
      ),
    );

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
            houseImage,
            houseInfo,
          ],
        ),
      ),
    );
  }

  // 网络请求

  getHouseDetail() async {
    var url = "http://testsh.hizhu.com/v2/house/detail.html";

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
