package com.itheima.service;

import com.itheima.domain.PageBean;
import com.itheima.domain.Route;

/**
 * @author 传智@左
 * @date 2021/1/12 18:19
 */
public interface RouteService {
    /**
     * 查询指定类别的分页数据PageBean
     * @param cid
     * @param curPage
     * @param pageSize
     * @param rname
     * @return PageBean
     */
    PageBean findByPage(Integer cid,Integer curPage,Integer pageSize ,String rname);

    /**
     * 根据rid获取线路全部数据
     * @param rid
     * @return Route
     */
    Route findByRid(int rid);

}
