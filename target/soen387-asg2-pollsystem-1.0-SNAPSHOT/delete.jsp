<%--
  Created by IntelliJ IDEA.
  User: liuhe
  Date: 2021-11-20
  Time: 18:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <meta http-equiv="Content-type" content="text/html; charset=UTF-8">
  <title>delete</title>
  <link href = "bootstrap/css/bootstrap.css" rel = "stylesheet" type="text/css">
  <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">
  <!-- for including headers -->
  <script src="//code.jquery.com/jquery-1.10.2.js"></script>
  <script>
    $(function(){
      $("#header").load("header.html");
    });
  </script>
</head>
<body>
<div id="header"></div>
<%
  response.setHeader("Cache-Control","no-cache");//avoid back page after logout
  response.setHeader("Cache-Control","no-store");
  response.setHeader("Pragma","no-cache");
  response.setDateHeader ("Expires", 0);

  if(session.getAttribute("userid")==null){
    response.sendRedirect("index.jsp");
  }
%>
<h2>Delete a Poll</h2><br>
<div class="col-md-7 col-lg-8">
  <form   action="display.jsp" method="post">
    <div align="left">
      <label class="form-label">Poll ID: </label>
      <input type="text" class="form-control"  name="poll_id" required>
    </div><br>

    <input type="submit" id="delete" value="Delete" name="delete" class="btn btn-info">

  </form>
  <div align = "right">
    <p><a href="display.jsp">Click Back</a> </p>
  </div>
</div>

<div align = "right">
  <form action="Logout" method="post">
    <input type="submit" value="Logout">
  </form>
</div>


</body>
</html>
