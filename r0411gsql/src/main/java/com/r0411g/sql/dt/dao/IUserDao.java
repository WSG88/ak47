package com.r0411g.sql.dt.dao;

import com.r0411g.sql.dt.pojo.User;

public interface IUserDao {

    /**
     * @Description
     *
     */
    int insertUser(User user);

    /**
     * @Description
     *
     */
    int deleteByuserNum(User user);

    /**
     * @Description
     *
     */
    int updateByUserNum(User user);

    /**
     * @Description
     *
     */
    User selectByUserNum(User user);
}