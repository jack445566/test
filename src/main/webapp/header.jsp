<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- 定义项目路径别名
<c:set> 是jstl的标签，作用：设置数据写入到页面域中，key=ctx，value=值
优点：以后只要通过${ctx}就可以获取项目路径
--%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<%--bootstrap--%>
<link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/common.css">
<script src="${pageContext.request.contextPath}/js/jquery-3.3.1.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/js/getParameter.js"></script>

<!-- 头部 start -->
<header id="header">
    <%--广告--%>
    <div class="top_banner">
        <img src="${pageContext.request.contextPath}/images/top_banner.jpg" alt="">
    </div>
    <%--右侧按钮--%>
    <div class="shortcut">

        <c:if test="${empty sessionScope.loginUser}">
        <!-- 未登录状态 -->
		<div class="login_out">
			<a id="loginBtn" data-toggle="modal" data-target="#loginModel" style="cursor: pointer;">登录</a>
			<a href="register.jsp" style="cursor: pointer;">注册</a>
		</div>
        </c:if>
        <!-- 登录状态 -->
        <c:if test="${not empty sessionScope.loginUser}">
		<div class="login">
			<span>欢迎回来，${loginUser.username}</span>
			<a href="${ctx}/UserServlet?action=userInfo" class="collection">个人中心</a>
			<a href="${ctx}/CartServlet?action=findAll" class="collection">购物车</a>
			<a href="${ctx}/UserServlet?action=loginOut">退出</a>
		</div>
        </c:if>
    </div>
    <%--搜索框--%>
    <div class="header_wrap">
        <div class="topbar">
            <div class="logo">
                <a href="/"><img src="${pageContext.request.contextPath}/images/logo.jpg" alt=""></a>
            </div>
            <div class="search">
                <input id="rname" name="rname" type="text" placeholder="请输入路线名称" class="search_input" value="${rname}"
                       autocomplete="off">
                <a href="javascript:void(0);" onclick="searchClick()" class="search-button">搜索</a>
                <script>
                    function searchClick() {
                        location.href="${ctx}/RouteServlet?action=findByPage&cid=${cid}&rname="+$("#rname").val();

                    }
                </script>

            </div>
            <div class="hottel">
                <div class="hot_pic">
                    <img src="${pageContext.request.contextPath}/images/hot_tel.jpg" alt="">
                </div>
                <div class="hot_tel">
                    <p class="hot_time">客服热线(9:00-6:00)</p>
                    <p class="hot_num">400-618-9090</p>
                </div>
            </div>
        </div>
    </div>
</header>
<!-- 头部 end -->
<!-- 首页导航 -->
<div class="navitem">
    <ul class="nav" id="categoryUI">
<%--        <li class="nav-active"><a href="index.jsp">首页</a></li>
        <li><a href="route_list.jsp">门票</a></li>
        <li><a href="route_list.jsp">酒店</a></li>
        <li><a href="route_list.jsp">香港车票</a></li>
        <li><a href="route_list.jsp">出境游</a></li>
        <li><a href="route_list.jsp">国内游</a></li>
        <li><a href="route_list.jsp">港澳游</a></li>
        <li><a href="route_list.jsp">抱团定制</a></li>
        <li><a href="route_list.jsp">全球自由行</a></li>
        <li><a href="favoriterank.jsp">收藏排行榜</a></li>--%>
    </ul>
</div>
<script>
    //目标：异步获取导航列表数据更新页面
    //1.提交异步请求获取导航类别列表数据
    $.ajax({
    //指定服务器访问地址
    url: "${ctx}/CategoryServlet", //servlet 地址
    data: {action: "findAll"}, //发送给服务器的参数

    success: function (categoryList) { //回调函数
        let html=``;
        // 拼接固定首页
        html+=`<li class="nav-active"><a href="index.jsp">首页</a></li>`;
        // 循环拼接每一个导航类别
        for(let category of categoryList){
            //获取cid
            let cid = category.cid;
            let cname = category.cname;
            html +=`<li>
            <a href="${ctx}/RouteServlet?action=findByPage&cid=\${cid}">\${category.cname}</a></li>`;
            //如果不适应es6的拼接字符串可以使用 html += '<li><a href="route_list.jsp">'+cname+'</a></li>';

        }
        // 拼接固定收藏排行榜
        html+=` <li><a href="favoriterank.jsp">收藏排行榜</a></li>`;

        // 将所有拼接好的html代码设置到ul(id=categoryUI)标签体内
        $("#categoryUI").html(html);

    },
   error:function (object) {
        console.log(object);
        alert("服务器忙...");
   }
    });


</script>

<!-- 登录模态框 -->
<div class="modal fade" id="loginModel" tabindex="-1" role="dialog" aria-labelledby="loginModelLable">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <%--头部--%>
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="loginModelLable">
                    <ul id="myTab" class="nav nav-tabs" style="width: auto">
                        <li class="active">
                            <a href="#pwdReg" data-toggle="tab">
                                密码登录
                            </a>
                        </li>
                        <li><a href="#telReg" data-toggle="tab">短信登录</a></li>
                    </ul>
                    <span id="loginErrorMsg" style="color: red;"></span>
                </h4>

            </div>
            <%--内容--%>
            <div id="myTabContent" class="tab-content">
                <%--密码登录--%>
                <div class="tab-pane fade in active" id="pwdReg">
                    <form id="pwdLoginForm" action="#" method="post">
                        <input type="hidden" name="action" value="pwdLogin">
                        <div class="modal-body">
                            <div class="form-group">
                                <label>用户名</label>
                                <input type="text" class="form-control" id="login_username" name="username"
                                       placeholder="请输入用户名">
                            </div>
                            <div class="form-group">
                                <label>密码</label>
                                <input type="password" class="form-control" id="login_password" name="password"
                                       placeholder="请输入密码">
                            </div>
                        </div>
                        <div class="modal-footer">
							<span id="pwdLoginSpan" style="color:red"></span>
                            <input type="reset" class="btn btn-primary" value="重置">
                            <input type="button" id="pwdLogin" class="btn btn-primary" value="登录"/>
                        </div>
                    </form>
                </div>

                    <script>
                        //目标：发送密码登录异步请求
                        //1.给登录按钮注册点击事件
                        $("#pwdLogin").click(function () {

                        //2.获取用户名与密码值
                            //获取表单数据方式1： 一个一个表单元素获取值
                            //      $("#username").val()
                            //      $("#password").val()
                            //获取表单数据方式2： 表单的jq对象.serialize() 可以获取全部的表单元素数据
                            //      获取数据格式："name1=value1&name2=value2..."
                            let allData = $("#pwdLoginForm").serialize();

                        //3.提交异步登录请求，传递表单数据用户名与密码
                            if ($("#login_username").val()&&$("#login_password").val()){

                               //用户名与密码有值后，发送一部请求
                                $.ajax({
                                    url:"${ctx}/UserServlet",
                                    data:allData,
                                    success:function (resultInfo) {
                                              //4.获取异步返回的结果resultInfo

                                        if (resultInfo.success){
                                            //4.1 如果成功，跳转到首页
                                           // location.href="${ctx}/index.jsp";

                                            //  目标：登录成功以后就刷新当前页面即可
                                            location.reload();//刷新当前页面即可
                                        }else {
                                            //4.2 如果失败，显示错误消息
                                            $("#pwdLoginSpan").text(resultInfo.message);

                                        }

                                    },
                                    error:function (object) {
                                        console.log(object);
                                        alert("服务器忙...")
                                    }
                                });

                            }

                        });

                    </script>

                <%--短信登录--%>
                <div class="tab-pane fade" id="telReg">
                    <form id="telLoginForm" method="post" action="#">
                        <input type="hidden" name="action" value="telLogin">
                        <div class="modal-body">
                            <div class="form-group">
                                <label>手机号</label>
                                <input type="text" class="form-control" name="telephone" id="login_telephone"
                                       placeholder="请输入手机号">
                            </div>
                            <div class="form-group">
                                <label>手机验证码</label>
                                <input type="text" class="form-control" id="login_check" name="smsCode"
                                       placeholder="请输入手机验证码">
                            </div>
                            <input type="button" id="login_sendSmsCode"value="发送手机验证"/>
                        </div>
                        <div class="modal-footer">
							<span id="telLoginSpan" style="color:red"></span>
                            <input type="reset" class="btn btn-primary" value="重置">
                            <input type="button" class="btn btn-primary" id="telLogin" value="登录"/>
                        </div>
                    </form>

                    <script>
                        //目标：发送手机验证码异步登录请求
                        //目标1：实现发送手机验证码点击事件，实现发送短信异步请求
                        //1.给“发送手机验证码”注册点击事件
                        $("#login_sendSmsCode").click(function () {

                            //获取输入的手机号码
                            let telephone = $("#login_telephone").val();

                            if (telephone){
                                //3.发送短信异步请求（手机号+action=sendSms）
                                $.ajax({
                                   url: "${ctx}/UserServlet",
                                   data:{action:"sendSms",telephone:telephone},
                                    success:function (resultInfo) {
                                        //4.获取异步返回的结果，弹框显示返回的消息
                                        if (resultInfo.success){
                                            //倒计时
                                            countDown2(60);

                                        }
                                        alert(resultInfo.message);
                                    },
                                    error:function (object) {
                                        console.log(object);
                                        alert("服务器忙。。。。")
                                    }
                                });
                            }else {
                                alert("请输入手机号码")
                            }
                        });

                        //目标2： 60秒倒计时代码
                        function countDown2(count) {

                            //设置发送手机验证码不可用
                            $("#login_sendSmsCode").prop("disabled",true);
                            $("#login_sendSmsCode").val(count+"秒后，可以发送手机验证码");

                            let taskId2=setInterval(function () {
                                    count--;//递减，每秒 递减1次

                                if (count==0){
                                    $("#login_sendSmsCode").val("发送手机验证码");
                                    $("#login_sendSmsCode").prop("disabled",false);

                                    //关闭定时器
                                    clearInterval(taskId2);
                                    return;
                                }
                                $("#login_sendSmsCode").val(count+"秒后，可以发送手机验证码");
                            },1000);

                        }


                        //目标3：登录按钮发送异步请求
                        //1.给登录按钮注册点击事件
                        $("#telLogin").click(function () {

                        //2.获取表单所有数据
                    //jquery提供一个语法，可以获取表单项所有数据,返回的是key=value格式
                    //语法：表单的jq对象.serialize()
                    //获取的数据格式："name1=value1&name2=value2..."
                     //疑问：为什么使用这个语法？ 答：不用一个一个表单项获取
                            let allData = $("#telLoginForm").serialize();

                        //3.提交手机验证码登录异步请求，传递手机号与验证码+action
                            $.ajax({
                                url:"${ctx}/UserServlet",
                                data:allData,
                                success:function (resultInfo) {
                                //4.获取返回数据resultInfo
                                    if (resultInfo.success){
                                         //4.1 登录成功，跳转到首页
                                        location.href="${ctx}/index.jsp";

                                    }else {
                                          //4.2 登录失败，显示错误消息
                                        $("#telLoginSpan").text(resultInfo.message);
                                    }
                                },
                                error:function (object) {
                                    console.log(object);
                                    alter("服务器忙");
                                }
                            });
                        });

                    </script>
                </div>
            </div>
        </div>
    </div>
</div>
