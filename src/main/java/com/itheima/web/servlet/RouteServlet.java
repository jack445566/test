package com.itheima.web.servlet;

import com.itheima.domain.PageBean;
import com.itheima.domain.Route;
import com.itheima.service.RouteService;
import com.itheima.util.BeanFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 传智@左
 * @date 2021/1/12 19:51
 */
@WebServlet("/RouteServlet")
public class RouteServlet extends BaseServlet {

    //目标：处理线路详情查询请求
    private void findRouteByRid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //目标：处理线路详情查询请求
        //1.获取请求参数rid
        Integer rid = Integer.valueOf(request.getParameter("rid"));

        //2.调用业务根据rid获取线路所有数据Route对象
        Route route = routeService.findByRid(rid);

        //3.将Route对象数据存储到请求域中
        request.setAttribute("route",route);

        //6.跳转到详情页面route_detail.jsp展现数据
        request.getRequestDispatcher("/route_detail.jsp").forward(request,response);
        }


    //实例业务类
    private RouteService routeService = (RouteService) BeanFactory.getBean("routeService");

    private void findByPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //目标：处理分页请求
        //1.获取cid,curPage,pageSize,rname
        //cid: cid在分页筛选功能中，有可能值无效，所以要判断处理
        Integer cid =null;

        if (request.getParameter("cid")!=null&&!request.getParameter("cid").equals("")){
            cid=Integer.valueOf(request.getParameter("cid"));
        }

        //将cid存储到域中，方便页面上分页帅选
       request.setAttribute("cid", cid);

        //curPage 如果前端不传递就使用默认值1
        Integer curPage = 1;
        String curPageStr = request.getParameter("curPage");
        if (curPageStr != null && !curPageStr.equals("") ) {
            //有效，转换
            curPage = Integer.valueOf(curPageStr);
        }
        //pageSize    //pageSize: 如果前端不传递就使用默认值3
        Integer pageSize = 3;
        String pageSizeStr = request.getParameter("pageSize");
        if (pageSizeStr != null && pageSizeStr.equals("") ) {
            //有效，转换
            pageSize = Integer.valueOf(pageSizeStr);
        }

        //rname搜索条件
        String rname=request.getParameter("rname");
        request.setAttribute("rname",rname);

        //2.调用业务获取PageBean
        PageBean pageBean = routeService.findByPage(cid, curPage, pageSize,rname);

        //3.将PageBean存储到请求域中
        request.setAttribute("pageBean", pageBean);

        //4.跳转到route_list.jsp页面展现分页数据
        request.getRequestDispatcher("/route_list.jsp").forward(request, response);

    }
}
