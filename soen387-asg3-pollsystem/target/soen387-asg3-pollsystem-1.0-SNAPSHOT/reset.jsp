<%@ page import="com.pollsystem.daoimpl.UserDaoImpl" %>
<%@ page import="com.pollsystem.model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-type" content="text/html; charset=UTF-8">
    <title>reset page</title>
    <link href = "bootstrap/css/bootstrap.css" rel = "stylesheet" type="text/css">
    <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="bootstrap/css/signin.css" rel="stylesheet" type="text/css">
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

    User user_forgot = (User) request.getAttribute("user_forgot");

%>

<main class="form-signup">

    <h2 class="h3 mb-3 fw-normal"> Set New Password </h2>
    <form action="SetPasswordServlet" method="post">
        <label for="user_id" class="h3 mb-3 fw-normal">User ID:</label>
        <div class="form-floating">
            <input type="text" class="form-control" id="user_id" name="user_id" value=<%=user_forgot.getUserId()%>> <br>
        </div>
        <label for="new_pass" class="h3 mb-3 fw-normal">New Password:</label>
        <div class="form-floating">
            <input type="password" class="form-control" id="new_pass" name="new_pass" placeholder="new password" required><br>
        </div>
        <input class="w-100 btn btn-lg btn-primary" type="submit" value="Set New Password">
    </form>

    <%--back button--%>
    <div style="text-align:right">
        <p><a href="login.jsp">Back</a> </p>
    </div>
</main>
</body>
</html>


