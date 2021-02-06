<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/webbase.css">
    <link rel="stylesheet" href="css/pages-seckillOrder.css">
    <title>我的订单</title>

</head>
<body>
<!--引入头部-->
<%@include file="header.jsp"%>
<div class="container-fluid">
    <!--header-->
    <div id="account">
        <div class="py-container">
            <div class="yui3-g home">
                <!--左侧列表-->
                <%@include file="home_left.jsp"%>
                <!--右侧主内容-->
                <div class="yui3-u-5-6 order-pay">
                    <div class="body">
                        <div class="table-title">
                            <table class="sui-table  order-table">
                                <tr>
                                    <thead>
                                    <th width="18%">图片</th>
                                    <th width="15%">商品</th>
                                    <th width="15%">单价</th>
                                    <th width="10%">数量</th>
                                    <th width="8%">商品操作</th>
                                    <th width="10%">实付款</th>
                                    </thead>
                                </tr>
                            </table>
                        </div>

                        <div class="order-detail">
                            <c:forEach items="${orderList}" var="order">
                                <div class="orders">
                                    <!--order1-->
                                    <div class="choose-title">
                                        <label>
                    <span>
                        <%--时间格式标签，需要导入jstl中格式化标签库
    <%@ taglib prefix="fm" uri="http://java.sun.com/jsp/jstl/fmt" %>
                        --%>
                        <fm:formatDate value="${order.ordertime}" pattern="yyyy-MM-dd HH:mm:ss"></fm:formatDate>
                        订单编号：${order.oid}  店铺：${order.seller.sname} <a>和我联系</a></span>
                                        </label>
                                        <a class="sui-btn btn-info share-btn">分享</a>
                                    </div>
                                    <table class="sui-table table-bordered order-datatable">
                                        <tbody>
                                        <c:forEach items="${order.orderItemList}" var="orderItem">
                                            <tr>
                                                <td width="15%">
                                                    <div class="typographic">
                                                        <img src="${ctx}/${orderItem.route.rimage}" width="150" height="80">

                                                    </div>
                                                </td>
                                                <td width="35%">
                                                    <div>
                                                        <a href="#" class="block-text">${orderItem.route.rname}</a>
                                                    </div>
                                                </td>
                                                <td width="5%" class="center">
                                                    <ul class="unstyled">
                                                        <li>¥${orderItem.route.price}</li>
                                                    </ul>
                                                </td>
                                                <td width="5%" class="center">1</td>
                                                <td width="8%" class="center">
                                                    售后服务
                                                </td>
                                                <td width="10%" class="center" >
                                                    <ul class="unstyled">
                                                        <li>¥${orderItem.route.price}</li>
                                                    </ul>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </c:forEach>
                            <div class="clearfix"></div>
                        </div>
                        <div style="margin-top: 50px">
                            <div class="page_num_inf">
                                <i></i> 共
                                <span>12</span>页<span>132</span>条
                            </div>
                            <div class="pageNum">
                                <ul>
                                    <li><a href="">首页</a></li>
                                    <li class="threeword"><a href="#">上一页</a></li>
                                    <li class="curPage"><a href="#">1</a></li>
                                    <li><a href="#">2</a></li>
                                    <li><a href="#">3</a></li>
                                    <li><a href="#">4</a></li>
                                    <li><a href="#">5</a></li>
                                    <li><a href="#">6</a></li>
                                    <li><a href="#">7</a></li>
                                    <li><a href="#">8</a></li>
                                    <li><a href="#">9</a></li>
                                    <li><a href="#">10</a></li>
                                    <li class="threeword"><a href="javascript:;">下一页</a></li>
                                    <li class="threeword"><a href="javascript:;">末页</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>
</div>
</div>
<!--引入尾部-->
<%@include file="footer.jsp"%>
<script type="text/javascript" src="js/plugins/citypicker/distpicker.data.js"></script>
<script type="text/javascript" src="js/plugins/citypicker/distpicker.js"></script>
</body>
</html>
