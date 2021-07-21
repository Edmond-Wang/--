
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Result</title>
    </head>
<body> <!--对结果数据在网页上打印-->
    <div class="info">
        <p><span style="font-family: 黑体; ">${update}</span></p>
        <br>
        <p><span style="color: #2f83a5;"> ${country}的疫情信息：</span><p>
        <p><span style="color: #aa3333;">累计确诊 ${total}</span><p>
        <p><span style="color: #0c0303;">累计死亡 ${dead}</span><p>
        <p><span style="color: #e2b631;">新增确诊 ${today}</span><p>
        <p><span style="color: #74d94a;">危险等级 ${level}</span><p>
        <input type="button" value="返回首页" onclick="window.location.href='index.jsp';"/>
    </div>
    <style type="text/css">
        .info{
            text-align: center; /*让div内部文字居中*/
            background-color: #e1eee9;
            border-radius: 20px;
            width: 300px;
            height: 350px;
            margin: 0 auto;
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
        }
    </style>



</body>
</html>
