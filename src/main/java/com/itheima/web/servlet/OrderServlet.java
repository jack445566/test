package com.itheima.web.servlet;

import com.itheima.domain.Address;
import com.itheima.domain.Cart;
import com.itheima.domain.Order;
import com.itheima.domain.User;
import com.itheima.service.AddressService;
import com.itheima.service.OrderService;
import com.itheima.util.BeanFactory;
import com.itheima.util.CartUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author 传智@左
 * @date 2021/1/13 20:22
 */
@WebServlet("/OrderServlet")
public class OrderServlet extends BaseServlet {

    private void findOrderList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //1.获取登录用户数据
       User loginUser = (User) request.getSession().getAttribute("loginUser");

        //2.根据用户uid获取用户地址列表
        List<Order> orderList = orderService.findAllByUid(loginUser.getUid());

        request.setAttribute("orderList",orderList);

        request.getRequestDispatcher("/home_orderlist.jsp").forward(request,response);
    }




        private OrderService orderService= (OrderService) BeanFactory.getBean("orderService");

    private void addOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //目标：处理添加订单请求

        //1.获取收货地址的aid数据(name=addressId)
        Integer aid = Integer.valueOf(request.getParameter("addressId"));

        //2.获取用户登录数据
        User loginUser = (User) request.getSession().getAttribute("loginUser");

        //3.调用业务层添加订单数据
        orderService.addOrder(aid,loginUser);
        //Order order = orderService.addOrder(aid, loginUser);

        /*String oid = order.getOid();
        Double total = order.getTotal();
*/
        //response.sendRedirect(request.getContextPath()+"/PayServlet?action=oid="+oid+"&total="+total);


        //4.跳转到支付pay.jsp页面
       response.sendRedirect(request.getContextPath()+"/pay.jsp");
    }


        //实例地址业务对象
    private AddressService addressService= (AddressService) BeanFactory.getBean("addressService");

        //目标：处理结算请求
    private void orderInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

            //1.获取登录用户数据
        User loginUser = (User) request.getSession().getAttribute("loginUser");

        //2.根据用户uid获取用户地址列表
        List<Address> addressList = addressService.findAllByUid(loginUser.getUid());

        //3.从缓存中获取购物车数据
        Cart cart = CartUtils.getCartFromRedis(loginUser);

        //4.将数据存储到请求域中
        request.setAttribute("addressList",addressList);
        request.setAttribute("cart",cart);

            //5.跳转页面order_info.jsp展现预览预订单信息数据
        request.getRequestDispatcher("/order_info.jsp").forward(request,response);

    }
}
