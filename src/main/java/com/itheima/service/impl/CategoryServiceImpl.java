package com.itheima.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.dao.CategoryDao;
import com.itheima.domain.Category;
import com.itheima.service.CategoryService;
import com.itheima.util.DaoFactory;
import com.itheima.util.JedisUtils;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * @author 黑马程序员
 */
public class CategoryServiceImpl implements CategoryService {

    //实例dao
    private CategoryDao categoryDao = DaoFactory.getBean(CategoryDao.class);

    /**
     * 查询所有类别列表数据
     *
     * @return List<Category>
     */
    @Override
    public List<Category> findAll()throws Exception {

        List<Category> categoryList = null;
        ObjectMapper objectMapper = new ObjectMapper();

        //1.查询redis里面存储的类别列表数据返回json字符串数据
        //获取jedis连接对象
        Jedis jedis = JedisUtils.getJedis();
        //查询数据
        String jsonData = jedis.get("categoryList");

        //2.判断json字符串是否有数据
        if(jsonData==null) {
            //3.如果json字符串数据无效，调用数据库获取，并写入缓存
            categoryList = categoryDao.findAll();
            //转换为json字符串
            jsonData = objectMapper.writeValueAsString(categoryList);
            //写入缓存
            jedis.set("categoryList",jsonData);
        }else{
            //4.如果json字符串数据有效，将json字符串转换为java对象返回
            //  objectMapper.readValue(jsonData, List.class)
            //  作用：将json字符串转换为指定类型的java对象
            categoryList= objectMapper.readValue(jsonData, List.class);
        }
        //关闭jedis， 将连接返回连接池
        jedis.close();

        return categoryList;
    }
}
