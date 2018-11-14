import 'package:flutter/material.dart';
import 'package:xiaowanghelp/house/hizhu/house_list_page.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'xiaowanghelp',
      theme: ThemeData(
        // This is the theme of your application.
        //
        // Try running your application with "flutter run". You'll see the
        // application has a blue toolbar. Then, without quitting the app, try
        // changing the primarySwatch below to Colors.green and then invoke
        // "hot reload" (press "r" in the console where you ran "flutter run",
        // or simply save your changes to "hot reload" in a Flutter IDE).
        // Notice that the counter didn't reset back to zero; the application
        // is not restarted.
        primarySwatch: Colors.blue,
      ),
      home: MyHomePage(title: 'xiaowanghelp'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key key, this.title}) : super(key: key);

  // This widget is the home page of your application. It is stateful, meaning
  // that it has a State object (defined below) that contains fields that affect
  // how it looks.

  // This class is the configuration for the state. It holds the values (in this
  // case the title) provided by the parent (in this case the App widget) and
  // used by the build method of the State. Fields in a Widget subclass are
  // always marked "final".

  final String title;

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  var keyTxt = "";
  var moneyMin = 0.0;
  var moneyMax = 0.0;
  TextEditingController searchController = TextEditingController();

  void onTextClear() {
    setState(() {
      searchController.clear();
      keyTxt = "";
    });
  }

  _toHizhu() {
    Navigator.of(context)
        .push(new MaterialPageRoute(builder: (BuildContext context) {
      return new HouseListPage(_getKeyTxt(), _getMoneyMinS(), _getMoneyMaxS());
    }));
  }

  _getNowTime() {
    DateTime now = new DateTime.now();
    return now.toString();
  }

  _getKeyTxt() {
    return keyTxt;
  }

  _getMoneyMin() {
    return moneyMin;
  }

  _getMoneyMinS() {
    return moneyMin * 100.0;
  }

  _getMoneyMax() {
    return moneyMax;
  }

  _getMoneyMaxS() {
    return moneyMax * 100.0;
  }

  @override
  Widget build(BuildContext context) {
    // This method is rerun every time setState is called, for instance as done
    // by the _incrementCounter method above.
    //
    // The Flutter framework has been optimized to make rerunning build methods
    // fast, so that you can just rebuild anything that needs updating rather
    // than having to individually change instances of widgets.
    return Scaffold(
      appBar: AppBar(
        // Here we take the value from the MyHomePage object that was created by
        // the App.build method, and use it to set our appbar title.
        title: Text(widget.title),
        actions: <Widget>[
          new IconButton(
            icon: new Icon(Icons.search),
            onPressed: () {},
          )
        ],
      ),
      body: Center(
        // Center is a layout widget. It takes a single child and positions it
        // in the middle of the parent.
        child: Column(
          // Column is also layout widget. It takes a list of children and
          // arranges them vertically. By default, it sizes itself to fit its
          // children horizontally, and tries to be as tall as its parent.
          //
          // Invoke "debug painting" (press "p" in the console, choose the
          // "Toggle Debug Paint" action from the Flutter Inspector in Android
          // Studio, or the "Toggle Debug Paint" command in Visual Studio Code)
          // to see the wireframe for each widget.
          //
          // Column has various properties to control how it sizes itself and
          // how it positions its children. Here we use mainAxisAlignment to
          // center the children vertically; the main axis here is the vertical
          // axis because Columns are vertical (the cross axis would be
          // horizontal).
          mainAxisAlignment: MainAxisAlignment.start,
          children: <Widget>[
            Text(
              '现在：' + _getNowTime(),
            ),
            Column(
              children: <Widget>[
                TextField(
                    decoration: InputDecoration(labelText: "位置关键字"),
                    controller: searchController,
                    maxLength: 10,
                    onChanged: (val) {
                      setState(() {
                        keyTxt = val;
                      });
                    }),
                RaisedButton(
                  onPressed: onTextClear,
                  child: Text('清空位置关键字'),
                ),
              ],
            ),
            Text(
              '租金小：' + _getMoneyMinS().toString(),
            ),
            Slider(
              value: _getMoneyMin(),
              max: 100.0,
              min: 0.0,
              activeColor: Colors.blue,
              onChanged: (double) {
                setState(() {
                  moneyMin = double.roundToDouble();
                });
              },
            ),
            Text(
              '租金大：' + _getMoneyMaxS().toString(),
            ),
            Slider(
              value: _getMoneyMax(),
              max: 100.0,
              min: 0.0,
              activeColor: Colors.blue,
              onChanged: (double) {
                setState(() {
                  moneyMax = double.roundToDouble();
                });
              },
            ),
            new MaterialButton(
              color: Colors.blue,
              textColor: Colors.white,
              child: new Text('嗨住租房'),
              onPressed: _toHizhu,
            ),
          ],
        ),
      ),
    );
  }
}
