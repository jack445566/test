package com.itheima.web.filter;

import com.itheima.domain.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 传智@左
 * @date 2021/1/13 19:40
 */
@WebFilter(filterName = "LoginFilter",urlPatterns = {"/CartServlet", "/OrderServlet"})
public class LoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        //登录权限校验：登录了放行，否则跳转到首页
        //将父接口转换为子接口(使用Ctrl+Alt+U， 可以查看当前类向上类的所有关系结构)
        //(Ctrl+H) 可以查看当前类向下类的所有关系结构)
        HttpServletRequest request=(HttpServletRequest)req;
        HttpServletResponse response=(HttpServletResponse)resp;

        //获取登录数据
        User loginUser = (User) request.getSession().getAttribute("loginUser");
        if (loginUser==null){
            //跳转到首页
            response.sendRedirect(request.getContextPath()+"/index.jsp");
            return;
        }

        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
