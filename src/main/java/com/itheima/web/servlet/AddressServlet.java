package com.itheima.web.servlet;

import com.itheima.domain.Address;
import com.itheima.domain.User;
import com.itheima.service.AddressService;
import com.itheima.util.BeanFactory;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
@MultipartConfig
@WebServlet("/AddressServlet")
public class AddressServlet extends BaseServlet {

    //实例业务
    private AddressService addressService= (AddressService) BeanFactory.getBean("addressService");


    //目标：处理添加地址请求
    private void addAddress(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, InvocationTargetException, IllegalAccessException {

        //1.登录校验
        User loginUser= (User) request.getSession().getAttribute("loginUser");
        if (loginUser==null){
            response.sendRedirect(request.getContextPath()+"/index.jsp");
            return;
        }
        //2.将表单数据封装到Address对象中
        Address address=new Address();
        Map<String,String[]> map=request.getParameterMap();
        BeanUtils.populate(address,map);

        //3.将登录数据User对象封装到Address对象中
        address.setUser(loginUser);

        //4.调用业务添加数据
        addressService.addAddress(address);

        //5.提交查询收货人地址列表请求
        response.sendRedirect(request.getContextPath()+"/AddressServlet?action=findListByUid");

    }

    //目标：处理查询收货人地址列表请求
   private void findListByUid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

       //1.登录校验
       User loginUser= (User) request.getSession().getAttribute("loginUser");

       if (loginUser==null){

           response.sendRedirect(request.getContextPath()+"/index.jsp");
           return;
       }

       //2.获取uid
       Integer uid = loginUser.getUid();

       //3.调用业务根据uid获取收获人地址列表
       List<Address> addressesList=addressService.findAllByUid(uid);

       //4.将数据存储请求域中
       request.setAttribute("addressList",addressesList);

       //5.跳转到home_address.jsp页面展现数据
       request.getRequestDispatcher("/home_address.jsp").forward(request,response);


   }
}
