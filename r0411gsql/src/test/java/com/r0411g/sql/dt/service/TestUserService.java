package com.r0411g.sql.dt.service;

import com.r0411g.sql.common.GsonUtils;
import com.r0411g.sql.dt.dbRouting.DbContextHolder;
import com.r0411g.sql.dt.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by supers on 2017/3/9.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-config.xml")
public class TestUserService {

    @Autowired
    private IUserService userService;

    /**
     * 测试分库分表插入
     *
     */
    @Test
    public void testInsertUser(){
        User user = new User();
        user.setUserNum("wwwwqqqq");
        user.setUserName("admin");
        user.setAge(23);
        user.setPassword("adf23");
        int re = userService.insertUser(user);
        System.out.println(DbContextHolder.getDbKey()+"库 "+DbContextHolder.getTableIndex()+"表 的插入结果:"+GsonUtils.toJson(re));
    }

    /**
     * 测试分库分表删除
     *
     */
    @Test
    public void testDeleteByuserNum(){
        User user = new User();
        user.setUserNum("wwwwqqqq");
        int re = userService.deleteByuserNum(user);
        System.out.println(DbContextHolder.getDbKey()+"库 "+DbContextHolder.getTableIndex()+"表 的删除结果:"+GsonUtils.toJson(re));
    }


    /**
     * 测试分库分表修改
     *
     */
    @Test
    public void testupdateByUserNum(){
        User user = new User();
        user.setUserNum("wwwwqqqq");
        user.setAge(34);
        int re = userService.updateByUserNum(user);
        System.out.println(DbContextHolder.getDbKey()+"库 "+DbContextHolder.getTableIndex()+"表 的更新结果:"+GsonUtils.toJson(re));
    }

    /**
     * 测试分库分表查询
     *
     */
    @Test
    public void testQueryUserByNum(){
        User user = new User();
        user.setId(1);
        user.setUserNum("wwwwqqqq");
        User userDb = userService.selectByUserNum(user);
        System.out.println(DbContextHolder.getDbKey()+"库 "+DbContextHolder.getTableIndex()+"表 的查询结果:"+GsonUtils.toJson(userDb));
    }
}
