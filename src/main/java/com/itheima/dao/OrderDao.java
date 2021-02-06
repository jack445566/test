package com.itheima.dao;

import com.itheima.domain.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author 传智@左
 * @date 2021/1/13 22:36
 */
public interface OrderDao {

    //插入订单主表数据
    @Insert("insert into tab_order values(#{oid},#{ordertime},#{total},#{state},#{address},#{contact},#{telephone},#{uid},1`````````````````````)")
    Integer insert(Order order);

    //根据oid查询订单数据
    @Select("select * from tab_order where oid=#{oid}")
    Order findByOid(String oid);


    List<Order> findAllByUid(int uid);
}
