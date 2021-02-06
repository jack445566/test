package com.itheima.test;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;

public class JedisTest1 {


        /*
         * 目标：基本使用jedis进行数据CRUD
         * */
    @Test
    public void test(){

        //1.创建Jedis的连接池JedisPool
        //创建连接池配置类|:JedisPoolConfig配置最大连接数和用户等待连接的超时时间

        //1.1创建连接池配置对象
        JedisPoolConfig config=new JedisPoolConfig();

        //1.2 配置最大连接数和等待的超时时间
        config.setMaxTotal(10);
        config.setMaxWaitMillis(3000);//30秒

        //1.3创建连接池对象
        // 语法：new JedisPool(JedisPoolConfig连接池配置对象,
        //                      String redis服务器ip地址,
        //                      int port端口号)
        JedisPool jedisPool=new JedisPool(config,"localhost",6379);

        //2.从连接池获取1个连接对象Jedis
        Jedis jedis = jedisPool.getResource();

        //3.操作数据
        //3.1 操作string类型
        //写入
        jedis.set("checkCode","1234");

        //读取
        String checkCode = jedis.get("checkCode");

        //打印
        System.out.println("checkCode:"+checkCode);

        //3.2 操作list类型
        //写入
        jedis.lpush("myList2","a","b","c");
        jedis.rpush("myList2","one","two","three");
        //读取
        List<String> myList2=jedis.lrange("myList2",0,-1);
        //打印
        System.out.println(myList2);

        //3.3设置key与value，并且有超时时间
        jedis.setex("name8",300,"刘亦菲");
        String name8 = jedis.get("name8");
        System.out.println("name8="+name8);

        //3.4删除key
        jedis.del("checkCode");
        System.out.println("查询已删除checkCode="+jedis.get("checkCode"));

        //3.5如果key存在不写数据，否则写入数据
        jedis.setnx("name9","杨幂");

        //4.关闭连接
        jedis.close();

        //将连接池关闭
        jedisPool.close();


    }
}

