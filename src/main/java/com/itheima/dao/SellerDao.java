package com.itheima.dao;

import com.itheima.domain.Seller;
import org.apache.ibatis.annotations.Select;

/**
 * @author 传智@左
 * @date 2021/1/13 0:18
 */
public interface SellerDao {

    //查询吸顶商家
    @Select("select *from tab_seller where sid=#{sid}")
    Seller findBySid(Integer sid);
}
