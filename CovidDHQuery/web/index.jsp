
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
  <head>
    <title>Home</title>  <!--css设计样式对齐-->
    <style>
      div{
        width:200px;
        height:55px;
        display:block;
        margin:0 auto;
      }
    </style>

  </head>

  <body>

  <form action="<%=request.getContextPath()%>/QueryServlet" method="get"> <!--表单提交位置，提交方式-->

    <div>
      <label>所在地域（洲）：</label>
        <input type="text" name="continent" value="${param.userName}" />
    </div>

    <div>
      <label>国家名：</label>
        <input type="text" name="country"/><br>
    </div>

    <div>
      <input type="submit" value="查询" id="send" style="margin-right:73px;"/>

      <input type="reset"  value="重置" id="res"/>
    </div>
  </form>

  <div  class="tip">${inputError}</div> <!--如果输入有误，提示用户-->
  <style type="text/css">
    .tip{
      width:230px;
      height:150px;
      margin:0 auto;
      color: #ec0433;
    }
  </style>

  </body>
</html>
