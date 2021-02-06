package com.itheima.dao;

import com.itheima.domain.Address;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AddressDao {

    //查询指定用户的收货人地址列表数据
    @Select("select *from tab_address where uid=#{uid}")
    List<Address>  findAllByUid(Integer uid);

    //插入收获人地址数据
    @Insert("insert into tab_address values(null,#{user.uid},#{contact},#{address},#{telephone},#{isdefault})")
    Integer insert(Address address);

    //根据aid查询地址数据
    @Select("select * from tab_address where aid=#{aid}")
    Address findByAid(Integer aid);
}
