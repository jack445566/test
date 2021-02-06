<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>购物车列表</title>


</head>
<body>
<!--引入头部-->
<%@include file="header.jsp"%>
<div class="container">
    <c:if test="${cart.cartNum<=0}">
    <div class="row" style="margin: 100px 200px;text-align: center">
        购物车内暂时没有商品，登录后将显示您之前加入的商品
    </div>
    </c:if>
<c:if test="${cart.cartNum>=0}">
    <div class="row">

        <div style="margin:0 auto; margin-top:20px">
            <div style="font-weight: bold;font-size: 15px;margin-bottom: 10px">商品数量：${cart.cartNum}</div>
            <table class="table">
                <tbody>
                <tr bgcolor="#f5f5f5" class="table-bordered">
                    <th>图片</th>
                    <th>商品</th>
                    <th>价格</th>
                    <th>数量</th>
                    <th>小计</th>
                    <th>操作</th>
                </tr>
                <c:forEach items="${cart.cartItemMap}" var="entry">
                <tr class="table-bordered">
                    <td width="180" width="40%">
                        <input type="hidden" name="id" value="22">
                        <img src="${ctx}/${entry.value.route.rimage}" width="170" height="100">
                    </td>
                    <td width="30%">
                        <a href="${ctx}/RouteServlet?action=findRouteByRid&rid=${entry.value.route.rid}" target="_blank">${entry.value.route.rname}</a>
                    </td>
                    <td width="10%">
                        ￥${entry.value.route.price}
                    </td>
                    <td width="14%">
                        ×${entry.value.num}
                    </td>
                    <td width="15%">
                        <span class="subtotal">￥${entry.value.subTotal}</span>
                    </td>
                    <td>
                        <a href="javascript:;" class="delete">删除</a>
                    </td>

                </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

    <div>
        <div style="text-align:right;">
             商品金额: <strong style="color:#ff6600;">￥${cart.cartTotal}</strong>
        </div>
        <div style="text-align:right;margin-top:10px;margin-bottom:10px;">
                <a href="javascript:location.href='${ctx}/OrderServlet?action=orderInfo';">
					<input type="button" width="100" value="结算" name="submit" border="0" style="background-color: #ea4a36;
						height:45px;width:120px;color:white;font-size: 15px">
            </a>
        </div>
    </div>
    </c:if>
</div>
<!--引入尾部-->
<%@include file="footer.jsp"%>
</body>
</html>
