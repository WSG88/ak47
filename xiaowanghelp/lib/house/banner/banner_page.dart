import 'dart:ui';

import 'package:carousel_slider/carousel_slider.dart';
import 'package:flutter/material.dart';
import 'package:flutter_swiper/flutter_swiper.dart';

//轮播
class BannerPage extends StatefulWidget {
  BannerPage({Key key, this.title}) : super(key: key);

  final String title;

  @override
  BannerPageState createState() => new BannerPageState();
}

class BannerPageState extends State<BannerPage> {
  @override
  Widget build(BuildContext context) {
    return new Scaffold(
      appBar: new AppBar(
        title: new Text(widget.title),
      ),
      body: new Swiper(
        itemBuilder: (BuildContext context, int index) {
          return new Image.network(
            "https://public.wutongwan.org/public-20180722-FmbUCGuDoixhEPlhep7WPrb6ADuJ?imageView2/1/w/380/h/285",
            width: window.physicalSize.width,
            height: 200.0,
            fit: BoxFit.fill,
          );
        },
        itemCount: 3,
        pagination: new SwiperPagination(),
        control: new SwiperControl(),
      ),
    );
  }
}

class Page2 extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new Scaffold(
        appBar: new AppBar(
          title: new Text("Page2"),
        ),
        body: new CarouselSlider(
            items: [1, 2, 3, 4, 5].map((i) {
              return new Builder(
                builder: (BuildContext context) {
                  return new Container(
                      width: MediaQuery.of(context).size.width,
                      margin: new EdgeInsets.symmetric(horizontal: 5.0),
                      decoration: new BoxDecoration(color: Colors.amber),
                      child: new Image.network(
                        'http://i2.yeyou.itc.cn/2014/huoying/hd_20140925/hyimage06.jpg',
                        fit: BoxFit.fill,
                      ));
                },
              );
            }).toList(),
            height: 180.0,
            autoPlay: true));
  }
}
