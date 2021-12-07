
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-type" content="text/html; charset=UTF-8">
    <title>forgot page</title>
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

<main class="form-signup">
    <%--    sign up form    --%>
    <h2 class="h3 mb-3 fw-normal"> Forgot Password </h2>
    <form action="ForgotPassword" method="post">
        <label for="user_id" class="h3 mb-3 fw-normal">User ID:</label>
        <div class="form-floating">
            <input type="text" class="form-control" id="user_id" name="user_id" placeholder="ID" required><br>
        </div>
        <label for="email" class="h3 mb-3 fw-normal">Email:</label>
        <div class="form-floating">
            <input type="text" class="form-control" id="email" name="email" placeholder="Email" required><br>
        </div>

        <input class="w-100 btn btn-lg btn-primary" type="submit" value="Request Change Password">
    </form>

    <%--back button--%>
    <div style="text-align:right">
        <p><a href="login.jsp">Back</a> </p>
    </div>
</main>
</body>
</html>

