package com.itheima.service;

import com.itheima.domain.Category;

import java.util.List;

/**
 * @author 传智@左
 * @date 2021/1/10 20:23
 */
public interface CategoryService {
    /**
      * 查询所有类别列表数据
     * @param
     * @return
     */
    List<Category> findAll() throws Exception;

}
