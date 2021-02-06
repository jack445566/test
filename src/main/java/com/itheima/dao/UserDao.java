package com.itheima.dao;

import com.itheima.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

public interface UserDao {
    //根据用户名查找用户
    @Select("select * from tab_user where username=#{username}")
    User findByUserName(String username);

    //根据手机号查找用户
    @Select("select * from tab_user where telephone=#{telephone}")
    User findByTelephone(String telephone);

    //插入注册数据
    @Insert("insert into tab_user (username,password,telephone) values (#{username},#{password},#{telephone})")
    Integer insert(User user);

    //更新用户信息
    Integer update(User user);

    //根据uid查询用户
    User findByUid(Integer uid);

}
