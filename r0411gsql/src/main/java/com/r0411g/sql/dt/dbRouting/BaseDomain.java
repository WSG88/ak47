package com.r0411g.sql.dt.dbRouting;

import java.io.Serializable;

/**
 * 所有pojo必须继承此bean 才能使用分库分表
 *
 */
public class BaseDomain  implements Serializable {

    private String userNum;

    private String tableIndex;

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }

    public String getTableIndex() {
        this.tableIndex = DbContextHolder.getTableIndex();
        return tableIndex;
    }

    public void setTableIndex(String tableIndex) {
        this.tableIndex = tableIndex;
    }
}
