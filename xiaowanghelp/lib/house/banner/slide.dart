import 'dart:async';

import 'package:flutter/material.dart';

class Slide extends StatefulWidget {
  final List data;

  Slide({@required this.data});

  @override
  _SlideState createState() => new _SlideState();

/**
    const DATA = [
    {"image": "https://pic1.zhimg.com/v2-f2822a917d63b5852bd2b1c42d75ae30.jpg", "id": 9689108, "title": "本周热门精选 · 热血漫画一样的比赛"},
    {"image": "https://pic1.zhimg.com/v2-b7f18474894be76bdb2439b6d954a53c.jpg", "id": 9689102, "title": "普吉沉船幸存者：一家五口，只剩我一个；而我们当时什么都不知道"},
    {"image": "https://pic3.zhimg.com/v2-60203aa6aae05cbb32bff35343fc86a6.jpg", "id": 9689097, "title": "英格兰等了 28 年，再度打入世界杯四强；这次他们离大力神杯还有多远？"},
    {"image": "https://pic1.zhimg.com/v2-cdf1a3a325ba6418e6546307b1edfae0.jpg", "id": 9689053, "title": "人类祖先一拍脑门的决定，诞生了一夫一妻制的婚姻，还有白头偕老的爱情"},
    {"image": "https://pic1.zhimg.com/v2-d978ef5f7f6f67e43b351faee54451c4.jpg", "id": 9668185, "title": "知道哪些医学上的小常识可以保护自己？"}
    ];

    new Slide(data: DATA);
 */
}

// 轮播图组件
class _SlideState extends State<Slide> {
  int index = 0; // 当前位置
  PageController controller = new PageController();
  bool running = false;

  // 圆点
  Widget dot(bool action) {
    return new Container(
      height: 7.0,
      width: 7.0,
      margin: new EdgeInsets.all(3.0),
      decoration: new BoxDecoration(
        color: action
            ? Color.fromRGBO(255, 255, 255, 0.82)
            : Color.fromRGBO(255, 255, 255, 0.36),
        borderRadius: new BorderRadius.all(const Radius.circular(10.0)),
      ),
    );
  }

  // 轮播
  void run() async {
    this.running = true;
    while (this.running) {
      await Future.delayed(new Duration(seconds: 5));
      this.setState(() {
        this.index = this.index == widget.data.length - 1 ? 0 : this.index + 1;
        this.controller.animateToPage(
              this.index,
              duration: new Duration(milliseconds: 600),
              curve: Curves.easeInOut,
            );
      });
    }
  }

  @override
  void initState() {
    super.initState();
    this.run();
  }

  @override
  Widget build(BuildContext context) {
    return new Container(
      height: 220.0,
      color: Colors.black12,
      child: new Stack(
        alignment: Alignment.bottomCenter,
        children: <Widget>[
          new PageView.custom(
            controller: this.controller,
            onPageChanged: (index) {
              this.setState(() {
                this.index = index;
              });
            },
            // 内容
            childrenDelegate: new SliverChildBuilderDelegate(
              (context, index) {
                var item = widget.data[index];
                return new Stack(
                  fit: StackFit.expand,
                  children: <Widget>[
                    new Image.network(
                      item['image'],
                      fit: BoxFit.cover,
                    ),
                    // 遮罩层
                    new Container(
                      color: Colors.black26,
                      alignment: Alignment.bottomCenter,
                      padding: new EdgeInsets.fromLTRB(12.0, 0.0, 12.0, 24.0),
                      child: new Text(
                        item['title'],
                        style: new TextStyle(
                          color: Colors.white,
                          fontSize: 20.0,
                          fontWeight: FontWeight.w400,
                        ),
                      ),
                    ),
                  ],
                );
              },
              childCount: widget.data.length,
            ),
          ),
          new Container(
            height: 25.0,
            alignment: Alignment.center,
            child: new Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: Array.map<Widget>(widget.data, (item, index) {
                return dot(index == this.index);
              }),
            ),
          )
        ],
      ),
    );
  }
}

class Array {
  static List<T> map<T>(List list, Function cb) {
    List _list = new List<T>();
    for (var i = 0; i < list.length; i++) {
      _list.add(cb(list[i], i));
    }
    return _list;
  }
}
