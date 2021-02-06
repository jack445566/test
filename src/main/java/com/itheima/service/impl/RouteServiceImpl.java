package com.itheima.service.impl;

import com.itheima.dao.RouteDao;
import com.itheima.domain.PageBean;
import com.itheima.domain.Route;
import com.itheima.service.RouteService;
import com.itheima.util.DaoFactory;

import java.util.List;

/**
 * @author 传智@左
 * @date 2021/1/12 18:21
 */
public class RouteServiceImpl  implements RouteService {
        //实例dao
    private RouteDao routeDao= DaoFactory.getBean(RouteDao.class);

    /**
     * 查询指定类别的分页数据PageBean
     *
     * @param cid
     * @param curPage
     * @param
     * @return PageBean
     */
    @Override
    public PageBean findByPage(Integer cid, Integer curPage, Integer pageSize ,String rname) {

        //1.调用dao获取总条数count
        Integer count = routeDao.countByCid(cid ,rname);

        //2.调用dao获取当前页数据列表dataList
        Integer start=(curPage-1)*pageSize;
        Integer length=pageSize;
        List<Route> dataList = routeDao.findByPage(cid, start, length,rname);

        //3.调用PageBean工具方法返回PageBean
        return PageBean.getPageBean(curPage,pageSize,count,dataList);
    }

    /**
     * 根据rid获取线路全部数据
     *
     * @param rid
     * @return Route
     */
    @Override
    public Route findByRid(int rid) {
        return routeDao.findByRid(rid);
    }


}
