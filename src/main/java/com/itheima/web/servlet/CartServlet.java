package com.itheima.web.servlet;

import com.itheima.domain.Cart;
import com.itheima.domain.CartItem;
import com.itheima.domain.Route;
import com.itheima.domain.User;
import com.itheima.service.RouteService;
import com.itheima.util.BeanFactory;
import com.itheima.util.CartUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;

/**
 * @author 传智@左
 * @date 2021/1/13 12:20
 */
@WebServlet("/CartServlet")
public class CartServlet extends BaseServlet {


    //目标：处理删除购物车项数据
    private void  delCartItem(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //1.获取请求数据rid
        Integer rid = Integer.valueOf(request.getParameter("rid"));

        //2.从缓存中获取购物车数据
        User loginUser = (User) request.getSession().getAttribute("loginUser");
        Cart cart = CartUtils.getCartFromRedis(loginUser);

        //3.从购物车里面购物车项集合数据
        LinkedHashMap<Integer, CartItem> cartItemMap = cart.getCartItemMap();

        //4.删除指定购物车项数据
        cartItemMap.remove(rid);

        //5.将最新的购物车数据更新到缓存中
        CartUtils.setCartToRedis(loginUser,cart);

        //6.将最新的购物车数据放到请求域中
        request.setAttribute("cart",cart);

        //7.跳转到cart.jsp页面展现
        request.getRequestDispatcher("/cart.jsp").forward(request,response);
    }


    //目标：处理查询购物车数据v
    private void  findAll(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //1.登录校验
        //获取登录用户
        User loginUser = (User) request.getSession().getAttribute("loginUser");
        //如果没有登录返回首页
        if (loginUser==null){
            response.sendRedirect(request.getContextPath()+"/index.jsp");
            return;
        }
        //2.从缓存里面获取购物车数据
        Cart cart = CartUtils.getCartFromRedis(loginUser);

        //3.将购物车数据存储到请求域中
        request.setAttribute("cart",cart);

        //4.跳转到cart.jsp页面展现
        request.getRequestDispatcher("/cart.jsp").forward(request,response);
    }



    //实例RouteService业务对象
    private RouteService routeService= (RouteService) BeanFactory.getBean("routeService");

    private void  addCart(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //目标：处理添加购物车请求

            //1.登录校验
        //获取登录用户
        User loginUser = (User) request.getSession().getAttribute("loginUser");
        //如果没有没有登录跳转到首页
        if (loginUser==null){
            response.sendRedirect(request.getContextPath()+"/index.jsp");
            return;
        }

        //2.获取请求数据rid与num
        Integer rid = Integer.valueOf(request.getParameter("rid"));
        Integer num = Integer.valueOf(request.getParameter("num"));

        //3.从缓存里面读取购物车数据
        Cart cart = CartUtils.getCartFromRedis(loginUser);
        //获取购物项集合
        LinkedHashMap<Integer, CartItem> cartItemMap = cart.getCartItemMap();

        //定义购物项，用于操作当前商品的购物项数据
        CartItem cartItem=null;

        //4.判断购物车数据是否包含当前商品
        if (cartItemMap.containsKey(rid)){
            //4.1 包含，更新购物车项数量
            cartItem=cartItemMap.get(rid);
            cartItem.setNum(cartItem.getNum()+num);

        }else {
            //4.2 不包含，封装创建新的购物项数据，最后加入购物车
            cartItem=new CartItem();
            cartItem.setNum(num);
            //根据rid查询route对象
            Route route = routeService.findByRid(rid);
            cartItem.setRoute(route);
            //新购物项加入购物车
            cart.getCartItemMap().put(rid,cartItem);

        }
            //5.更新缓存里面的购物车数据
        CartUtils.setCartToRedis(loginUser,cart);

            //6.将当前的购物车项数据写入到请求域中
            request.setAttribute("cartItem",cartItem);

        //7.跳转到cart_success.jsp页面显示当前购物项数据
        request.getRequestDispatcher("/cart_success.jsp").forward(request,response);
    }
}
