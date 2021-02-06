package com.itheima.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @author 黑马程序员
 */
//@WebServlet("/BaseServlet")  这个是父类Servlet，只是为了抽取service方法给子类重用，所以不对外提供访问路径
public class BaseServlet extends HttpServlet {
    //去掉了doGet与doPost，改名为service
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            //目标：处理多请求优化

            //1.获取请求标识符参数数据action
            String action = request.getParameter("action");
//            System.out.println(action);

            //2.根据action的值不同，分别处理不同的请求
        /*if("register".equals(action)){
            //处理用户注册请求
            register(request,response);
        }else if("checkUserName".equals(action)){
            //处理用户名脚部校验请求
            checkUserName(request,response);
        }else if("sendSms".equals(action)){
            //处理用户手机验证码发送异步请求
            sendSms(request,response);
        }*/

            //使用反射优化上面冗余的判断
            //1.获取当前类的字节码对象
            Class clazz = this.getClass();
            //2.根据字节码对象获取当前类的处理请求方法对象Method
            Method method = clazz.getDeclaredMethod(action, HttpServletRequest.class, HttpServletResponse.class);

            //3.暴力反射允许访问
            method.setAccessible(true);

            //4.执行方法
            method.invoke(this,request,response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }


    }

    //将指定的对象转换为json字符串输出
    protected void toJsonString(HttpServletResponse response,Object object)throws Exception{
        response.setContentType("application/json;charset=utf8");
        String jsonData = new ObjectMapper().writeValueAsString(object);
        response.getWriter().print(jsonData);
    }
}