package com.r0411g.sql.dt.service.impl;

import com.r0411g.sql.dt.service.IUserService;
import com.r0411g.sql.dt.dao.IUserDao;
import com.r0411g.sql.dt.dbRouting.annotation.Router;
import com.r0411g.sql.dt.pojo.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description
 *
 */
@Service("userService")
public class UserServiceImpl implements IUserService {

    @Resource
    private IUserDao userDao;

    @Router
    public int insertUser(User user) {
        return this.userDao.insertUser(user);
    }

    @Router
    public int deleteByuserNum(User user) {
        return this.userDao.deleteByuserNum(user);
    }

    @Router
    public int updateByUserNum(User user) {
        return this.userDao.updateByUserNum(user);
    }

    @Router
    public User selectByUserNum(User user) {
        return this.userDao.selectByUserNum(user);
    }
}
