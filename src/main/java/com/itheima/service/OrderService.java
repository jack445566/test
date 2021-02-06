package com.itheima.service;

import com.itheima.domain.Order;
import com.itheima.domain.User;

import java.util.List;

/**
 * @author 传智@左
 * @date 2021/1/13 22:42
 */
public interface OrderService {

    /**
     * 添加订单业务方法
     * @param aid
     * @param user
     * @return Boolean
     */
    Order addOrder(Integer aid, User user) ;

    List<Order> findAllByUid(int uid);

}


