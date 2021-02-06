package com.itheima.dao;

import com.itheima.domain.RouteImg;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author 传智@左
 * @date 2021/1/13 0:20
 */
public interface RouteImgDao {

    //查询指定线路轮播图
    @Select("select * from tab_route_img where rid=#{rid}")
    List<RouteImg> findAllByRid(Integer rid);
}
