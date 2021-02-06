package com.itheima.service.impl;

import com.itheima.dao.AddressDao;
import com.itheima.domain.Address;
import com.itheima.service.AddressService;
import com.itheima.util.DaoFactory;

import java.util.List;

public class AddressServiceImpl implements AddressService {

    //实例dao
    private AddressDao addressDao= DaoFactory.getBean(AddressDao.class);

    /**
     * 查询指定用户的收货人地址列表数据
     *
     * @param uid
     * @return List<Address>
     */
    @Override
    public List<Address> findAllByUid(Integer uid) {

        return addressDao.findAllByUid(uid);
    }

    /**
         * 插入收货人地址数据
         *
         * @param address
         * @return boolean
         */
    @Override
    public boolean addAddress(Address address) {
        return addressDao.insert(address)>0;
    }



}
