package com.itheima.dao;

import com.itheima.domain.Category;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author 传智@左
 * @date 2021/1/10 20:20
 */
public interface CategoryDao {
    //查询所有类别列表数据
    @Select("select * from tab_category order by cid")
    List<Category> findAll();

    //查询指定类别数据
    @Select("select * from tab_category where cid =#{cid}")
    Category findByCid(Integer cid);
}
