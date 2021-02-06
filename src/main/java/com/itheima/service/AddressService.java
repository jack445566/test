package com.itheima.service;

import com.itheima.domain.Address;

import java.util.List;

public interface AddressService {
    /**
     * 查询指定用户的收货人地址列表数据
     * @param uid
     * @return  List<Address>用户收货地址列表
     */
    List<Address> findAllByUid(Integer uid);

    /**
     * 插入收货人地址数据
     * @param address
     * @return boolean
     */
    boolean addAddress(Address address);
}
