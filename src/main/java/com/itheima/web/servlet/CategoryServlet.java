package com.itheima.web.servlet;

import com.itheima.domain.Category;
import com.itheima.service.CategoryService;
import com.itheima.util.BeanFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author 传智@左
 * @date 2021/1/10 21:05
 */
@WebServlet("/CategoryServlet")
public class CategoryServlet extends BaseServlet {

    //实例业务
    private CategoryService categoryService= (CategoryService) BeanFactory.getBean("categoryService");

    private void findAll(HttpServletRequest request, HttpServletResponse response) throws  Exception {

        List<Category> categoryList=categoryService.findAll();

        toJsonString(response,categoryList);
    }


}
