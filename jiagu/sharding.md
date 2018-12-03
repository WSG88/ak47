数据库 ![图片](.jpg "区块链")
====================
[分库分表](https://blog.csdn.net/huanxue517/article/details/70145570)
---------------------
表示同一个Dao操作，会操作到同一个库的不同的表里去。即变动SQL语句指向的数据库表。
#### 样例
> ###1 Mybatis + 分库分表插件
> - 根据mapper xml生成sql；由于重写了methodProxy，可以在不依赖于sqlSession的情况下根据mapper xml生成待执行的sql语句。
> - sql 解析；利用mybatis自身的api获取分表字段的值
> - sql路由；根据分表字段的值取模，找到对应的数据源
> - 到指定的数据源执行sql；此时才根据数据源生成sqlSession，然后执行响应的语句。
> ###2 Spring 多数据源分库分表
> - Spring 2.0.1以后的版本已经支持配置多数据源，通过继承AbstractRoutingDataSource可以是实现多数据源的动态转换，即运行的时候动态加载不同的数据源。

https://blog.csdn.net/varyall/article/details/79893625

https://www.cnblogs.com/sunny3096/p/8595058.html


php业务提供方数据表分表，使用hashCode

      function hashCode64($str) {
          $str = (string)$str;
          $hash = 0;
          $len = strlen($str);
          if ($len == 0 )
              return $hash;
       
          for ($i = 0; $i < $len; $i++) {
              $h = $hash << 5;
              $h -= $hash;
              $h += ord($str[$i]);
              $hash = $h;
              $hash &= 0xFFFFFFFF;
          }
          return $hash;
      }
 
 
      function hashCode32( $s )
      {
          $h = 0;
          $len = strlen($s);
          for($i = 0; $i < $len; $i++)
          {
              $h = overflow32(31 * $h + ord($s[$i]));
          }
       
          return $h;
      }
       
      function overflow32($v)
      {
          $v = $v % 4294967296;
          if ($v > 2147483647) return $v - 4294967296;
          elseif ($v < -2147483648) return $v + 4294967296;
          else return $v;
      }