package com.itheima.dao;

import com.itheima.domain.Route;
import org.apache.http.conn.routing.RouteTracker;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author 传智@左
 * @date 2021/1/12 18:11
 */
public interface RouteDao {

    //查询总条数
    Integer countByCid(@Param("cid") Integer cid,
                       @Param("rname") String rname);


    //查询当前页数据列表
    List<Route> findByPage(@Param("cid") Integer cid,
                           @Param("start") Integer start,
                           @Param("length") Integer length,
                           @Param("rname") String rname);

    //查询指定线路的线路关联查询类别、商家、轮播图片列表数据
    @Select("select* from tab_route where rid=#{rid}")
    @Results({
            //每个表单条SQL语句执行，只要字段名与属性名一样，就可以不映射，会自动映射
            // 但是关联查询其他表的字段，必须手动映射，否则会没有数据
            @Result(id = true, property = "rid", column = "rid"),
            @Result(property = "cid", column = "cid"),
            @Result(property = "sid", column = "sid"),

            //  关联查询表1对1：类别表
            @Result(property = "category", column = "cid",
                    one = @One(select = "com.itheima.dao.CategoryDao.findByCid")),

            //  关联查询表1对1：商家表
            @Result(property = "seller", column = "sid",
                    one = @One(select = "com.itheima.dao.SellerDao.findBySid")),

            //  关联查询表1对多：轮播图列表
            @Result(property = "routeImgList", column = "rid",
                    many = @Many(select = "com.itheima.dao.RouteImgDao.findAllByRid")
            )
    })
    Route findByRid(Integer rid);

    //xml注解实现封装4个表数据
    Route findByRid2(Integer rid);

    //查询指定线路的线路关联查询类别、商家、轮播图片列表数据【表连接1条sql语句的xml实现】
    Route findByRid3(int rid);

}