package com.itheima.web.servlet;

import com.sun.javafx.scene.shape.PathUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 传智@左
 * @date 2021/1/15 10:42
 */
@WebServlet("/PayServlet")
public class PayServlet extends BaseServlet {
    /**
      *
      * @param
      */
    private void createURL(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String oid = request.getParameter("oid");
        String total = request.getParameter("total");


    }


}
