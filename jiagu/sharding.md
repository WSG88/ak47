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


根据数据分析应该实际操作哪个数据表。因此，分表的字段值必须存在于传入的数据中，否则会进行联合查询。
情况1:知道多条记录的ID
思路：与上面的修改、删除及单条查询相同，先通过这些ID定位到多张分表，然后分别查询这些分表，最后将所有的结果UNION返回即可。
情况2:不知道任何记录ID
思路A：如果数据表不是很多的时候，可以UNOIN多个分表，当然，需要对各个分表进行索引和查询优化，如果实现了数据库集群，结果会更好。
思路B：通过建立一张字典表，该表主要记录了查询条件关键字与所属分表的对应关系，这样当输入关键字查询时，
      先通过关键字从该表中检索出涉及的分表，然后再针对这些分表进行查询，并返货UNION结果即可，
      这总办法相对A的办法更加彻底和具有可行性。

不停机修改mysql表结构
同样还是members表，前期设计的表结构不尽合理，随着数据库不断运行，其冗余数据也是增长巨大，使用了下面的方法来处理：
先创建一个临时表：
CREATE TABLE members_tmp LIKE members
然后修改members_tmp的表结构为新结构，接着使用上面那个for循环来导出数据，因为1000万的数据一次性导出是不对的，
mid是主键，一个区间一个区间的导，基本是一次导出5万条吧，这里略去了
接着重命名将新表替换上去：
RENAME TABLE members TO members_bak,members_tmp TO members;