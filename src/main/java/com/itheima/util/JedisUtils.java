package com.itheima.util;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ResourceBundle;

/**
 * @author 传智@左
 * @date 2021/1/10 19:52
 */

public class JedisUtils {

    private static JedisPool jedisPool;


    static {
        //目标：解析jedis.properties文件获取数据
        /*
         * ResourceBundle类作用：专门解析properties配置文件
         *    语法：ResourceBundle.getBundle("类路径下的properties文件名");
         * */
        ResourceBundle resourceBundle = ResourceBundle.getBundle("jedis");
        int maxTotal = Integer.parseInt(resourceBundle.getString("maxTotal"));
        int maxWaitMillis = Integer.parseInt(resourceBundle.getString("maxWaitMillis"));
        String host = resourceBundle.getString("host");
        int port = Integer.parseInt(resourceBundle.getString("port"));

        //创建连接池配置对象
        JedisPoolConfig config = new JedisPoolConfig();

        //配置最大连接数和等待的超时时间
        config.setMaxTotal(maxTotal);

        config.setMaxWaitMillis(maxWaitMillis);//30秒

        //创建连接池对象
        jedisPool = new JedisPool(config, host, port);
    }
    /**
     * 对外提供jedis连接对象
     * @return Jedis
     */
    public static Jedis getJedis(){

        return jedisPool.getResource();
    }

}
