package com.itheima.test;

import com.itheima.util.JedisUtils;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * @author 传智@左
 * @date 2021/1/10 20:05
 */
public class JedisTest2 {
   @Test
    public void test(){
       Jedis jedis = JedisUtils.getJedis();

       jedis.set("checkCode","1234");

       String checkCode = jedis.get("checkCode");

       System.out.println("checkCode="+checkCode);

       jedis.lpush("myList2","a","b","c");
       jedis.rpush("myList2","one","two","three");

       List<String> myList2=jedis.lrange("myList2",0,-1);

       System.out.println(myList2);

       jedis.setex("name8",300,"刘亦菲");
       String name8 = jedis.get("name8");
       System.out.println("name8="+name8);

       jedis.del("checkCode");
      System.out.println("查询已删除checkCode = " + jedis.get("checkCode"));
      jedis.setnx("name9","杨幂");
      jedis.close();

   }
}
