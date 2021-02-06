package com.itheima.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author 黑马程序员
 *
 * 目标：拦截所有资源的请求，处理post提交中文乱码
 */
@WebFilter(filterName = "EncodingFilter",urlPatterns = "/*")
public class EncodingFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        //ServletRequest 父接口，没有提供getMethod()方法
        //HttpServletRequest 子接口，提供getMethod()方法
        //将父接口转换为子接口
        HttpServletRequest request = (HttpServletRequest) req;

        //1、判断请求的类型是否是post
        //获取请求方法类型
        String method = request.getMethod();
        //判断
        if("post".equalsIgnoreCase(method)) {

            //2、如果请求类型是post ,解决乱码
            request.setCharacterEncoding("utf-8");
        }

        chain.doFilter(request, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
