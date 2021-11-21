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
  <title>welcome</title>
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

<h2>Welcome</h2><br>
<%

    session.removeAttribute("poll_id");
    session.removeAttribute("pin");
    session.removeAttribute("p_vote");
    session.removeAttribute("successful");
    //request.changeSessionId();
    //out.println(session.getId());

%>
<div class="col-md-7 col-lg-8">
  <form   action="EntryServlet" method="get">
    <div align="left">
      <label class="form-label">Poll ID: </label>
      <input type="text" class="form-control"  name="poll_id" required>
    </div><br>
    <div align="left">
      <label class="form-label">PIN #: </label>
      <input type="text" class="form-control"  name="pin" required>
    </div><br>
    <input type="submit" id="entry" value="Entry" name="entry" class="btn btn-info">
  </form><br>
  <div class="col-md-7 col-lg-8">
    ${newPin}<br>
  </div>

  <div align = "right">
  <form action="pin.jsp" method="post">
    <input type="submit" id="request" name="request" value="Request a PIN" class="btn btn-primary">
  </form>
</div>
  <div align = "right">
    <p><a href="index.jsp">Click Back</a> </p>
  </div>
</div>




</body>
</html>
