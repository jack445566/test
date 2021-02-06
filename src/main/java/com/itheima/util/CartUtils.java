package com.itheima.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.domain.Cart;
import com.itheima.domain.User;
import redis.clients.jedis.Jedis;

import java.io.IOException;


/**
 * @author 传智@左
 * @date 2021/1/13 9:34
 */
public class CartUtils {


    //将购物车数据写入或覆盖到缓存中
    // 写入或修改缓存 步骤：setCartToRedis(User user, Cart cart)
    public static void setCartToRedis(User user, Cart cart) {
        //获取Jedis连接对象
        Jedis jedis = null;
        try {
            jedis = JedisUtils.getJedis();

            //2.写入数据 确定key
            //拼接key,key的格式：cart_用户名
            String key = "cart_" + user.getUsername();

            //将cart对象转换为json字符串
            String jsonStr = new ObjectMapper().writeValueAsString(cart);

            //写入缓存
            jedis.set(key, jsonStr);


        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            //关闭资源
            if (jedis != null) {
                jedis.close();
            }
        }
    }


    // 读取缓存中（查询指定用户）的购物车数据返回：Cart getCartFromRedis(User user)
    public static Cart getCartFromRedis(User user) {

        Jedis jedis = null;
        Cart cart = null;
        try {
            //1.获取Jedis连接对象
            jedis = JedisUtils.getJedis();

            //读取key
            //2.拼接key,key的格式：cart_用户名
            String key = "cart_" + user.getUsername();

            //3.判断key是否存在
            if (jedis.exists(key)) {
                //3.1 存在，读取缓存json字符串数据，
                String jsonStr = jedis.get(key);
                //将json字符串转换为Cart对象
                cart = new ObjectMapper().readValue(jsonStr, Cart.class);
            } else {
                //3.2 不存在，创建一个Cart对象
                cart = new Cart();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            //4.关闭资源
            if (jedis != null) {
                jedis.close();
            }
        }
        //5.返回对象
        return cart;
    }


    //删除指定用户的购物车数据： void delCartToRedis(User user)
    public static void delCartToRedis(User user) {
        Jedis jedis = null;

        //1.获取Jedis连接对象
        jedis = JedisUtils.getJedis();

        //2.拼接key,key的格式：cart_用户名
        String key = "cart_" + user.getUsername();

        //3.删除key
        jedis.del(key);

        //4.关闭资源
        if (jedis != null) {
            jedis.close();
        }

    }
}
