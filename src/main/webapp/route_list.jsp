<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>线路列表</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/search.css">
</head>
<body>
<!--引入头部-->

<%@include file="header.jsp"%>
<div class="page_one">
    <div class="contant">
        <div class="crumbs">
            <img src="${pageContext.request.contextPath}/images/search.png" alt="">
            <p>黑马旅行><span>搜索结果</span></p>
        </div>
        <div class="xinxi clearfix">
            <%--线路列表 start--%>
            <div class="left">
                <div class="header">
                    <span>商品信息</span>
                    <span class="jg">价格</span>
                </div>
                <ul>
                    <%--使用jstl和el从域中获取分页数据PageBean展现数据--%>
                    <c:forEach items="${pageBean.dataList}" var="route">
                    <li>
                        <div class="img"><img src="${ctx}/${route.rimage}" width="300px" alt=""></div>
                        <div class="text1">
                            <p>${route.rname}</p>
                            <br/>
                            <p>${route.routeIntroduce}</p>
                        </div>
                        <div class="price">
                            <p class="price_num">
                                <span>&yen;</span>
                                <span>${route.price}</span>
                                <span>起</span>
                            </p>
                             <p><a href="${ctx}/RouteServlet?action=findRouteByRid&rid=${route.rid}">查看详情</a></p>

                        </div>
                    </li>
                    </c:forEach>
                </ul>
                <div class="page_num_inf">
                    <i></i> 共
                    <span>${pageBean.totalPage}</span>页<span>${pageBean.count}</span>条
                </div>
                <div class="pageNum">
                    <ul>
                        <li><a href="${ctx}/RouteServlet?action=findByPage&curPage=1&cid=${cid}&rname=${rname}">首页</a></li>
                        <%--上一页显示要求：当前页大于1--%>
                        <c:if test="${pageBean.curPage>1}">
                        <li class="threeword">
                            <a href="${ctx}/RouteServlet?action=findByPage&cid=${cid}&curPage=${pageBean.prePage}&rname=${rname}">上一页</a></li>
                        </c:if>
                        <%--
                        目标：实现百度分页栏数字效果
                        根据PageBean中起始数字与结束数字实现
                        --%>
                        <c:forEach begin="${pageBean.beginPage}" end="${pageBean.endPage}" var="pageNo">
                        <li  ${pageBean.curPage==pageNo?'class="curPage"':''}>
                            <a href="${ctx}/RouteServlet?action=findByPage&cid=${cid}&curPage=${pageNo}&rname=${rname}">${pageNo}</a>
                        </li>

                        </c:forEach>
                        <%--下一页要求：当前页小于 总页数--%>
                        <c:if test="${pageBean.curPage<pageBean.totalPage}">
                        <li class="threeword">
                            <a href="${ctx}/RouteServlet?action=findByPage&cid=${cid}&curPage=${pageBean.nextPage}&rname=${rname}">下一页</a></li>
                        </c:if>
                        <li class="threeword">
                            <a href="${ctx}/RouteServlet?action=findByPage&cid=${cid}&curPage=${pageBean.totalPage}&rname=${rname}">末页</a></li>
                    </ul>
                </div>
            </div>
            <%--线路列表--%>
            <%--热门推荐 start--%>
            <div class="right">

                <div class="top">
                    <div class="hot">HOT</div>
                    <span>热门推荐</span>
                </div>
                <ul>
                    <li>
                        <div class="left"><img src="${pageContext.request.contextPath}/images/04-search_09.jpg" alt=""></div>
                        <div class="right">
                            <p>清远新银盏温泉度假村酒店/自由行套...</p>
                            <p>网付价<span>&yen;<span>899</span>起</span>
                            </p>
                        </div>
                    </li>
                    <li>
                        <div class="left"><img src="${pageContext.request.contextPath}/images/04-search_09.jpg" alt=""></div>
                        <div class="right">
                            <p>清远新银盏温泉度假村酒店/自由行套...</p>
                            <p>网付价<span>&yen;<span>899</span>起</span>
                            </p>
                        </div>
                    </li>
                    <li>
                        <div class="left"><img src="${pageContext.request.contextPath}/images/04-search_09.jpg" alt=""></div>
                        <div class="right">
                            <p>清远新银盏温泉度假村酒店/自由行套...</p>
                            <p>网付价<span>&yen;<span>899</span>起</span>
                            </p>
                        </div>
                    </li>
                    <li>
                        <div class="left"><img src="${pageContext.request.contextPath}/images/04-search_09.jpg" alt=""></div>
                        <div class="right">
                            <p>清远新银盏温泉度假村酒店/自由行套...</p>
                            <p>网付价<span>&yen;<span>899</span>起</span>
                            </p>
                        </div>
                    </li>
                    <li>
                        <div class="left"><img src="${pageContext.request.contextPath}/images/04-search_09.jpg" alt=""></div>
                        <div class="right">
                            <p>清远新银盏温泉度假村酒店/自由行套...</p>
                            <p>网付价<span>&yen;<span>899</span>起</span>
                            </p>
                        </div>
                    </li>
                </ul>
            </div>
            <%--热门推荐 end--%>
        </div>
    </div>
</div>

<!--引入头部-->
<%@include file="footer.jsp"%>
</body>
</html>
