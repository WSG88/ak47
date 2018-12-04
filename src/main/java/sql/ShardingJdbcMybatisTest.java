package sql;

import com.google.common.collect.Lists;
import io.shardingsphere.api.config.MasterSlaveRuleConfiguration;
import io.shardingsphere.api.config.ShardingRuleConfiguration;
import io.shardingsphere.api.config.TableRuleConfiguration;
import io.shardingsphere.api.config.strategy.InlineShardingStrategyConfiguration;
import io.shardingsphere.api.config.strategy.StandardShardingStrategyConfiguration;
import io.shardingsphere.shardingjdbc.api.MasterSlaveDataSourceFactory;
import io.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.sql.DataSource;
import org.apache.commons.dbcp.BasicDataSource;

/**
 * 测试分库分表规则
 */
public class ShardingJdbcMybatisTest {
  public static String getTimeStamp(String date) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    long time = 0;
    try {
      time = sdf.parse(date).getTime() / 1000;
    } catch (ParseException e) {
    }
    return String.valueOf(time);
  }

  public static void main(String[] args) throws Exception {
    DataSource dataSource = getDataSource();
    Connection connection = dataSource.getConnection();

    DataSource dataSource1 = test2();
    Connection connection1 = dataSource1.getConnection();

    String sql = "insert into"
        + "    statistic(id,ship_id,type,view_no,call_no,call_success_no,reserve_no,house_no,refresh_no,liked_no,link_no,im_no,content,timestamp,city_code)"
        + "    values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

    PreparedStatement preparedStatement = connection.prepareStatement(sql);

    for (int i = 20180505; i < 20181204; i++) {
      String ss = getTimeStamp(i + "");
      String sql1 = "select * from statistic where timestamp=" + ss + ";";
      PreparedStatement preparedStatement1 = connection1.prepareStatement(sql1);
      ResultSet resultSet1 = preparedStatement1.executeQuery();
      while (resultSet1.next()) {
        String id = resultSet1.getString(1);
        int type = resultSet1.getInt(2);
        String ship_id = resultSet1.getString(3);
        int view_no = resultSet1.getInt(4);
        int call_no = resultSet1.getInt(5);
        int call_success_no = resultSet1.getInt(6);
        int reserve_no = resultSet1.getInt(7);
        int house_no = resultSet1.getInt(8);
        int refresh_no = resultSet1.getInt(9);
        int liked_no = resultSet1.getInt(10);
        int link_no = resultSet1.getInt(11);
        int im_no = resultSet1.getInt(12);
        String content = resultSet1.getString(13);
        int timestamp = resultSet1.getInt(14);
        String city_code = resultSet1.getString(15);
        try {
          preparedStatement.setString(1, id);
          preparedStatement.setString(2, ship_id);
          preparedStatement.setInt(3, type);
          preparedStatement.setInt(4, view_no);
          preparedStatement.setInt(5, call_no);
          preparedStatement.setInt(6, call_success_no);
          preparedStatement.setInt(7, reserve_no);
          preparedStatement.setInt(8, house_no);
          preparedStatement.setInt(9, refresh_no);
          preparedStatement.setInt(10, liked_no);
          preparedStatement.setInt(11, link_no);
          preparedStatement.setInt(12, im_no);
          preparedStatement.setString(13, content);
          preparedStatement.setInt(14, timestamp);
          preparedStatement.setString(15, city_code);
          preparedStatement.execute();
        } catch (SQLException e) {
        }
      }
    }
    System.out.println("ok");
  }

  static DataSource getDataSource() throws SQLException {
    ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
    shardingRuleConfig.getTableRuleConfigs().add(getOrderTableRuleConfiguration());
    shardingRuleConfig.setMasterSlaveRuleConfigs(getMasterSlaveRuleConfigurations());
    return ShardingDataSourceFactory.createDataSource(createDataSourceMap(), shardingRuleConfig,
        new HashMap<>(), new Properties());
  }

  static TableRuleConfiguration getOrderTableRuleConfiguration() {
    TableRuleConfiguration result = new TableRuleConfiguration();
    result.setLogicTable("statistic");
    result.setActualDataNodes("ds_${0..1}.statistic${0..15}");
    // 配置分库 + 分表策略
    result.setDatabaseShardingStrategyConfig(
        new InlineShardingStrategyConfiguration("timestamp", "ds_${timestamp & 2}"));
    result.setTableShardingStrategyConfig(
        new InlineShardingStrategyConfiguration("timestamp", "statistic${timestamp % 1500 / 100}"));

    StandardShardingStrategyConfiguration standardStrategy =
        new StandardShardingStrategyConfiguration("id", new MyPreciseShardingAlgorithm());
    result.setTableShardingStrategyConfig(standardStrategy);

    return result;
  }

  static List<MasterSlaveRuleConfiguration> getMasterSlaveRuleConfigurations() {
    MasterSlaveRuleConfiguration masterSlaveRuleConfig1 =
        new MasterSlaveRuleConfiguration("ds_0", "master0",
            Arrays.asList("master0slave0", "master0slave1"));
    MasterSlaveRuleConfiguration masterSlaveRuleConfig2 =
        new MasterSlaveRuleConfiguration("ds_1", "master1",
            Arrays.asList("master1slave0", "master1slave1"));
    return Lists.newArrayList(masterSlaveRuleConfig1, masterSlaveRuleConfig2);
  }

  static Map<String, DataSource> createDataSourceMap() {
    // 配置真实数据源
    Map<String, DataSource> dataSourceMap = new HashMap<>();
    // 配置第一个数据源
    BasicDataSource master0 = new BasicDataSource();
    master0.setDriverClassName("com.mysql.jdbc.Driver");
    master0.setUrl("jdbc:mysql://localhost:3306/gaodu_statistics");
    master0.setUsername("root");
    master0.setPassword("root");
    dataSourceMap.put("master0", master0);
    // 配置第一个从库
    BasicDataSource master0slave0 = new BasicDataSource();
    master0slave0.setDriverClassName("com.mysql.jdbc.Driver");
    master0slave0.setUrl("jdbc:mysql://localhost:3306/gaodu_statistics");
    master0slave0.setUsername("root");
    master0slave0.setPassword("root");
    dataSourceMap.put("master0slave0", master0slave0);
    // 配置第二个从库
    BasicDataSource master0slave1 = new BasicDataSource();
    master0slave1.setDriverClassName("com.mysql.jdbc.Driver");
    master0slave1.setUrl("jdbc:mysql://localhost:3306/gaodu_statistics");
    master0slave1.setUsername("root");
    master0slave1.setPassword("root");
    dataSourceMap.put("master0slave1", master0slave1);

    // 配置第二个数据源
    BasicDataSource master1 = new BasicDataSource();
    master1.setDriverClassName("com.mysql.jdbc.Driver");
    master1.setUrl("jdbc:mysql://localhost:3306/gaodu_statistics");
    master1.setUsername("root");
    master1.setPassword("root");
    dataSourceMap.put("master1", master1);
    // 配置第一个从库
    BasicDataSource master1slave0 = new BasicDataSource();
    master1slave0.setDriverClassName("com.mysql.jdbc.Driver");
    master1slave0.setUrl("jdbc:mysql://localhost:3306/gaodu_statistics");
    master1slave0.setUsername("root");
    master1slave0.setPassword("root");
    dataSourceMap.put("master1slave0", master1slave0);
    // 配置第二个从库
    BasicDataSource master1slave1 = new BasicDataSource();
    master1slave1.setDriverClassName("com.mysql.jdbc.Driver");
    master1slave1.setUrl("jdbc:mysql://localhost:3306/gaodu_statistics");
    master1slave1.setUsername("root");
    master1slave1.setPassword("root");
    dataSourceMap.put("master1slave1", master1slave1);
    return dataSourceMap;
  }

  //读写分离
  private static DataSource test2() {
    try {
      return MasterSlaveDataSourceFactory.createDataSource(createDataSourceMap(),
          getMasterSlaveRuleConfigurations().get(0), new HashMap<String, Object>(),
          new Properties());
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }
}