package com.itheima.dao;

import com.itheima.domain.OrderItem;
import org.apache.ibatis.annotations.Insert;

/**
 * @author 传智@左
 * @date 2021/1/13 22:39
 */
public interface OrderItemDao {

    //插入订单子表数据
    @Insert("insert into tab_orderitem values(null,#{itemtime},#{state},#{num},#{subtotal},#{rid},#{oid})")
    Integer insert(OrderItem orderItem);
}
