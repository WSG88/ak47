import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';

class HomePage extends StatefulWidget {
  @override
  HomePageState createState() => new HomePageState();
}

class HomePageState extends State<HomePage> {
  bool like = false;
  int likeCount = 40;

  onLike() {
    setState(() {
      likeCount = this.like ? likeCount - 1 : likeCount + 1;
      like = !like;
    });
  }

  textStyle({int size = 15, color = Colors.black87}) {
    return new TextStyle(
      fontSize: size.toDouble(),
      decoration: TextDecoration.none,
      color: color,
      fontWeight: FontWeight.w300,
      height: 1.2,
    );
  }

  buttonColumn(IconData icon, String label) {
    return new Column(
      children: <Widget>[
        new Icon(
          icon,
          color: Colors.blue[500],
          size: 20.0,
        ),
        new Text(label,
            style: this.textStyle(size: 13, color: Colors.blue[500])),
      ],
    );
  }

  @override
  Widget build(BuildContext context) {
    return new Scaffold(
        body: new Container(
            color: const Color(0xfffcfcfc),
            child: new ListView(
              // <-- 改为 ListView
              children: <Widget>[
                new Image.network(
                  'https://flutter.io/images/homepage/header-illustration.png',
                  fit: BoxFit.cover,
                ),
                new Container(
                  padding: new EdgeInsets.all(16.0),
                  child: new Row(
                    children: <Widget>[
                      new Flexible(
                        flex: 1,
                        fit: FlexFit.tight,
                        child: new Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: <Widget>[
                            new Text('Oeschinen Lake Campground',
                                style: this.textStyle()),
                            new Text('Kandersteg, Switzerland',
                                style: this.textStyle(
                                    size: 13, color: Colors.black54)),
                          ],
                        ),
                      ),
                      new IconButton(
                        padding: new EdgeInsets.all(0.0),
                        icon: this.like
                            ? new Icon(Icons.star)
                            : new Icon(Icons.star_border),
                        color: Colors.red[500],
                        onPressed: this.onLike,
                      ),
                      new Text(this.likeCount.toString(),
                          style: this.textStyle(size: 14)),
                    ],
                  ),
                ),
                new Container(
                  padding: new EdgeInsets.all(10.0),
                  child: new Row(
                    mainAxisAlignment: MainAxisAlignment.spaceAround,
                    children: <Widget>[
                      this.buttonColumn(Icons.call, 'CALL'),
                      this.buttonColumn(Icons.near_me, 'ROUTE'),
                      this.buttonColumn(Icons.share, 'SHARE'),
                    ],
                  ),
                ),
                new Container(
                    padding: new EdgeInsets.all(20.0),
                    child: new Text(
                      'Lake Oeschinen lies at the foot of the Blüemlisalp in the Bernese Alps.' +
                          'Situated 1,578 meters above sea level, it is one of the larger Alpine Lakes. ',
                      style: this
                          .textStyle(size: 13, color: const Color(0xff454647)),
                    ))
              ],
            )));
  }
}
