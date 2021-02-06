<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2021/1/15
  Time: 9:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
<h2>1完整api方式</h2>
<div id="QRCode"></div>

<h2>2简写api方式【简写】</h2>
<div id="QRCode2"></div>
<%--jquery--%>
<script src="js/jquery-3.3.1.js"></script>

<%--导入QRCode插件--%>
<script src="${pageContext.request.contextPath}/js/qrcode.min.js"></script>
<script>
    //生成二维码语法：new QRCode（”容器ID“，{配置二维码参数的json对象属性与值}）
    //参数1：容器ID，用于设置在哪个标签里面输出二维码
    //参数2：设置二维码的属性特征：比如二维码的高度，宽度，背景色，前景色

    //第一种方式：完整方式，特点设置属性较多
    new QRCode("QRCode",{
        texe:"http://www.baidu.com",
        width:300,
        height:300,
        colorDark: "blue",
        correctLevel: QRCode.CorrectLevel.M

    });

    //第二种方式：简写方式【推荐】，一切属性使用属性
    new QRCode("QRCode2","http://www.itheima.com");
</script>

</body>
</html>
