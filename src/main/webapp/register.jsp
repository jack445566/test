<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title>注册</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/register.css">
</head>
<body>
<!--引入头部-->
<%@include file="header.jsp"%>
<!-- 头部 end -->
<div class="rg_layout">
    <div class="rg_form clearfix">
        <%--左侧--%>
        <div class="rg_form_left">
            <p>新用户注册</p>
            <p>USER REGISTER</p>
        </div>
        <div class="rg_form_center">
            <!--注册表单-->
            <form id="registerForm" action="${pageContext.request.contextPath}/UserServlet" method="post">
                <!--提交处理请求的标识符-->
                <input type="hidden" name="action" value="register">
                <table style="margin-top: 25px;width: 558px">
                    <tr>
                        <td class="td_left">
                            <label for="username">用户名</label>
                        </td>
                        <td class="td_right">
                            <input type="text" id="username" name="username" placeholder="请输入账号">
							<span id="userInfo" style="font-size:10px"></span>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_left">
                            <label for="telephone">手机号</label>
                        </td>
                        <td class="td_right">
                            <input type="text" id="telephone" name="telephone" placeholder="请输入您的手机号">
                        </td>
                    </tr>
                    <tr>
                        <td class="td_left">
                            <label for="password">密码</label>
                        </td>
                        <td class="td_right">
                            <input type="password" id="password" name="password" placeholder="请输入密码">
                        </td>
                    </tr>
                    <tr>
                        <td class="td_left">
                            <label for="smsCode">验证码</label>
                        </td>
                        <td class="td_right check">
                            <input type="text" id="smsCode" name="smsCode" class="check" placeholder="请输入验证码">
                          
                            <input id="sendSmsCode" value="发送手机验证码" class="btn btn-link"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_left">
                        </td>
                        <td class="td_right check">
                            <%-- 设置注册按钮初始化状态：不可用和背景颜色为绿色--%>
                            <input type="submit" class="submit" value="注册" disabled="disabled" style="background:gray;">
                            <span id="msg" style="color: red;">${errorMsg}</span>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
        <%--右侧--%>
        <div class="rg_form_right">
            <p>
                已有账号？
                <a href="javascript:$('#loginBtn').click()">立即登录</a>
            </p>
        </div>
    </div>
</div>
<!--引入尾部-->
<%@include file="footer.jsp"%>
<script>
    //目标：发送异步校验用户名请求
    //1.给用户名文本框注册失去焦点事件
    $("#username").blur(function () {

        //2.获取输入的用户名
        let name = $(this).val();

        if (name){
            //3.用户名有效，发送异步请求，传递（用户名+action）
            $.ajax({
                url:"${ctx}/UserServlet",
                data:{action:"checkUserName",username:name},
                success:function (resultInfo) {
                    //4.获取异步响应结果ResultInfo的json对象
                    if (resultInfo.success){

                        //4.1 用户名可用，显示绿色的消息，设置注册按钮可用

                        //字体颜色为绿色
                        $("#userInfo").css("color","green");
                        //设置注册按钮
                        $("[type=submit]").prop("disabled",false).css("background","#ffd800");
                    }else {
                        //4.2 用户名已被注册，显示红色的消息，设置注册按钮不可用
                        //字体颜色为绿色
                        $("#userInfo").css("color","red");
                        //设置注册按钮
                        $("[type=submit]").prop("disabled",true).css("background","gray");

                    }
                    //显示消息
                    $("#userInfo").text(resultInfo.message);
                },
                error:function (obj) {

                }
            });
        }
    });


    //目标：发送异步手机验证码请求
    //1.给“发送手机验证码”注册点击事件
    $("#sendSmsCode").click(function () {

        //2.获取输入的手机号
        let telephone = $("#telephone").val();

        if (telephone){
        //3.发送短信异步请求（手机号+action=sendSms）
            $.ajax({
                url: "${ctx}/UserServlet",
                data: {action: "sendSms",telephone:telephone},
                success:function (resultInfo) {

                //4.获取异步返回的结果，弹框显示返回的消息
                    if (resultInfo.success){
                        alert(resultInfo.message);
                        //倒计时
                        countDown(60);
                    }

                },
                error:function (obj) {
                    console.log(obj);
                    alert("服务器忙。。。")
                }
            });
        }

    });

    // 实现按照指定秒数进行倒计时操作
    function countDown(count) {

        //设置显示倒计时信息
        $("#sendSmsCode").val(count+"秒后，可发送手机验证码");
        //设置不可用
        $("#sendSmsCode").prop("disabled",true);

        //设置定时器进行倒计时显示数据
       let taskId= setInterval(function () {
            //秒数递减
            count--;
            if (count==0){
                //停止定时器
                clearInterval(taskId);
                $("#sendSmsCode").val("发送手机验证码");
                //设置可用
                $("#sendSmsCode").prop("disabled",false);
                return;
            }
            $("#sendSmsCode").val(count+"秒后，可发送手机验证码");
        },1000);

    }

</script>


</body>
</html>
