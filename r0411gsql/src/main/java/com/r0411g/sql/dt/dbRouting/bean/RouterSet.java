package com.r0411g.sql.dt.dbRouting.bean;

import java.util.List;

/**
 * @Description
 *
 */
public class RouterSet {

    /**根据字符串*/
    public final static int RULE_TYPE_STR=3;

    public final static int ROUTER_TYPE_DB=0;

    public final static int ROUTER_TYPE_TABLE =1;

    public final static int ROUTER_TYPE_DBANDTABLE=2;

    /**数据库表的逻辑KEY,与数据源MAP配置中的key一致*/
    private List<String> dbKeyArray;

    /**数据库数量*/
    private int dbNumber;
    /**数据表数量*/
    private int tableNumber;
    /**数据表index样式*/
    private String tableIndexStyle;
    /**Id开始*/
    private String routeFieldStart;
    /**Id结束*/
    private String routeFieldEnd;
    /**规则类型*/
    private int ruleType;
    /**路由类型类型*/
    private int routeType;

    public static int getRULE_TYPE_STR() {
        return RULE_TYPE_STR;
    }

    public static int getROUTER_TYPE_DB() {
        return ROUTER_TYPE_DB;
    }

    public static int getROUTER_TYPE_TABLE() {
        return ROUTER_TYPE_TABLE;
    }

    public static int getROUTER_TYPE_DBANDTABLE() {
        return ROUTER_TYPE_DBANDTABLE;
    }

    public List<String> getDbKeyArray() {
        return dbKeyArray;
    }

    public void setDbKeyArray(List<String> dbKeyArray) {
        this.dbKeyArray = dbKeyArray;
    }

    public int getDbNumber() {
        return dbNumber;
    }

    public void setDbNumber(int dbNumber) {
        this.dbNumber = dbNumber;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public String getTableIndexStyle() {
        return tableIndexStyle;
    }

    public void setTableIndexStyle(String tableIndexStyle) {
        this.tableIndexStyle = tableIndexStyle;
    }

    public String getRouteFieldStart() {
        return routeFieldStart;
    }

    public void setRouteFieldStart(String routeFieldStart) {
        this.routeFieldStart = routeFieldStart;
    }

    public String getRouteFieldEnd() {
        return routeFieldEnd;
    }

    public void setRouteFieldEnd(String routeFieldEnd) {
        this.routeFieldEnd = routeFieldEnd;
    }

    public int getRuleType() {
        return ruleType;
    }

    public void setRuleType(int ruleType) {
        this.ruleType = ruleType;
    }

    public int getRouteType() {
        return routeType;
    }

    public void setRouteType(int routeType) {
        this.routeType = routeType;
    }
}
