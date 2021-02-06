package com.itheima.service.impl;

import cn.hutool.core.util.IdUtil;
import com.itheima.dao.AddressDao;
import com.itheima.dao.OrderDao;
import com.itheima.dao.OrderItemDao;

import com.itheima.domain.*;
import com.itheima.service.OrderService;
import com.itheima.util.CartUtils;
import com.itheima.util.DaoFactory;
import com.itheima.util.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 传智@左
 * @date 2021/1/13 22:43
 */
public class OrderServiceImpl implements OrderService {

    //实例dao对象
    private OrderDao orderDao = DaoFactory.getBean(OrderDao.class);
    /**
     * 添加订单业务方法
     *
     * @param aid  收获地址aid
     * @param user 下单的用户对象
     * @return Boolean 返回添加订单是否成功
     */
    //添加订单业务方法
    @Override
    public Order addOrder(Integer aid, User user) {

        //这里要操作3条sql语句，必须保证同一个事务
        //1.获取sqlSession
        SqlSession sqlSession = null;
        Order order=null;

        try {

            sqlSession = MyBatisUtils.openSession();

            //2.通过同一个sqlSession获取3个Dao代理类
            AddressDao addressDao = sqlSession.getMapper(AddressDao.class);
            OrderDao orderDao = sqlSession.getMapper(OrderDao.class);
            OrderItemDao orderItemDao = sqlSession.getMapper(OrderItemDao.class);

            //3.调用dao根据aid查询地址数据
            Address address = addressDao.findByAid(aid);

            //4.从redis缓存中获取购物车数据
            Cart cart = CartUtils.getCartFromRedis(user);

            //5.封装Order订单主表数据插入到数据库

            //实例Order订单主表对象
             order = new Order();
            //oid: 使用唯一值，uuid
            String oid = IdUtil.simpleUUID();
            order.setOid(oid);
            //下单时间
            Date orderTime = new Date();
            order.setOrdertime(orderTime);
            //订单总金额
            order.setTotal(cart.getCartTotal());
            //支付状态，0表示未支付，1表示已支付
            order.setState(0);
            //收获地址
            order.setAddress(address.getAddress());
            //收获联系人
            order.setContact(address.getContact());
            //收获联系人电话
            order.setTelephone(address.getTelephone());
            //设置订单的所属用户
            order.setUid(user.getUid());

            //调用dao方法插入数据库
            orderDao.insert(order);

            //6.封装OrderItem订单子表数据插入到数据库
            for (Map.Entry<Integer, CartItem> entry : cart.getCartItemMap().entrySet()) {
                //获取购物项对象
                CartItem cartItem = entry.getValue();

                //创建订单字表对题类对象封装数据
                OrderItem orderItem = new OrderItem();

                //下单时间
                orderItem.setItemtime(orderTime);

                //支付状态，0表示未支付，1代表已支付
                orderItem.setState(0);

                //购买你数量
                orderItem.setNum(cartItem.getNum());

                //小计金额
                orderItem.setSubtotal(cartItem.getSubTotal());

                //商品rid
                orderItem.setRid(cartItem.getRoute().getRid());

                //订单主表的id
                orderItem.setOid(oid);

                //调用dao写入订单子表数据到数据库中
                orderItemDao.insert(orderItem);
            }
            //7.上面全部成功，则删除缓存购物车数据
            CartUtils.delCartToRedis(user);

            //8.sqlsession提交事务并关闭
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();

            //如果发生异常，回滚
            if (sqlSession != null) {
                sqlSession.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
            //9.返回true
            return order;
    }

    @Override
    public List<Order> findAllByUid(int uid) {
        return orderDao.findAllByUid(uid);
    }

}






















