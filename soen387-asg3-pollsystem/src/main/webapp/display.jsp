<%@ page import="com.pollsystem.model.Poll" %>
<%@ page import="com.pollsystem.daoimpl.PollDaoImpl" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.util.*" %>
<%--
  Created by IntelliJ IDEA.
  User: liuhe
  Date: 2021-10-12
  Time: 15:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page errorPage="error.jsp" %>
<html>
<head>
    <meta http-equiv="Content-type" content="text/html; charset=UTF-8">
    <title>Display</title>
    <link href = "bootstrap/css/bootstrap.css" rel = "stylesheet" type="text/css">
    <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
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
<h2>Display the current Poll(s)</h2>

<%
    if("post".equalsIgnoreCase(request.getMethod()) && request.getParameter("update")!=null){
        String poll_id = request.getParameter("poll_id");
        String title = request.getParameter("title");
        String question = request.getParameter("question");
        ArrayList<String> choices = new ArrayList<>();
        String choice1 = request.getParameter("choice1");
        String choice2 = request.getParameter("choice2");
        String choice3 = request.getParameter("choice3");
        choices.add(choice1);
        choices.add(choice2);
        choices.add(choice3);

        PollDaoImpl pollDaoimpl = new PollDaoImpl();
        try{
            Poll cp = pollDaoimpl.getPoll(poll_id);
            cp.update_Poll(title,question,choices);
            if(pollDaoimpl.updatePoll(cp) && pollDaoimpl.updateChoice(cp)){
                PrintWriter output = response.getWriter();
                output.println("successful update!");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
%>


<%
        if("post".equalsIgnoreCase(request.getMethod()) && request.getParameter("run")!=null){
            String poll_id = request.getParameter("poll_id");
            PollDaoImpl pollDaoimpl = new PollDaoImpl();
            try{
                Poll cp = pollDaoimpl.getPoll(poll_id);
                cp.run_Poll();
                if(pollDaoimpl.updatePoll(cp)){
                    PrintWriter output = response.getWriter();
                    output.println("successful run!");
                }
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }
%>

<%
        if("post".equalsIgnoreCase(request.getMethod()) && request.getParameter("release")!=null){
            String poll_id = request.getParameter("poll_id");
            PollDaoImpl pollDaoimpl = new PollDaoImpl();
            try{
                Poll cp = pollDaoimpl.getPoll(poll_id);
                cp.release_Poll();
                if(pollDaoimpl.updatePoll(cp)){
                    PrintWriter output = response.getWriter();
                    output.println("successful release!");
                }
            }
            catch (SQLException e){
                e.printStackTrace();
            }

        }
%>
<%
        if("post".equalsIgnoreCase(request.getMethod()) && request.getParameter("unrelease")!=null){
            String poll_id = request.getParameter("poll_id");
            PollDaoImpl pollDaoimpl = new PollDaoImpl();
            try{
                Poll cp = pollDaoimpl.getPoll(poll_id);
                cp.unrelease_Poll();
                if(pollDaoimpl.updatePoll(cp)){
                    PrintWriter output = response.getWriter();
                    output.println("successful unrelease!");
                }
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }
%>

<%
        if("post".equalsIgnoreCase(request.getMethod()) && request.getParameter("clear")!=null){
            String poll_id = request.getParameter("poll_id");
            PollDaoImpl pollDaoimpl = new PollDaoImpl();
            try{
                Poll cp = pollDaoimpl.getPoll(poll_id);
                cp.clear_Poll();
                if(pollDaoimpl.updatePoll(cp)){
                    PrintWriter output = response.getWriter();
                    output.println("successful clear!");
                }
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }
%>


<%
        if("post".equalsIgnoreCase(request.getMethod()) && request.getParameter("close")!=null){
            String poll_id = request.getParameter("poll_id");
            PollDaoImpl pollDaoimpl = new PollDaoImpl();
            try{
                Poll cp = pollDaoimpl.getPoll(poll_id);
                cp.setPoll_status(Poll.status.valueOf("closed"));
                if(pollDaoimpl.updatePoll(cp)){
                    PrintWriter output = response.getWriter();
                    output.println("successful close!");
                }
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }
%>

<%
    if("post".equalsIgnoreCase(request.getMethod()) && request.getParameter("delete")!=null){
        String poll_id = request.getParameter("poll_id");
        PollDaoImpl pollDaoimpl = new PollDaoImpl();
        Poll cp = pollDaoimpl.getPoll(poll_id);
        PrintWriter output = response.getWriter();
        if(cp.getId() != null && cp.getPoll_status() == Poll.status.created && cp.getVote().isEmpty()){
            if(pollDaoimpl.deletePoll(poll_id)){
                output.println("successful delete!");
            }
        }
        else{
            output.println("You can not delete it!");
        }

    }
%>

        <%

            int user_id = (int) session.getAttribute("userid");
            //int user_id = Integer.parseInt(request.getParameter("user_id"));
            PollDaoImpl pollDao = new PollDaoImpl();
            Set<Poll> poll_array = pollDao.getAllPoll(user_id);
            //Poll p = pollDao.getPoll("0000000000");


        %>
        <div class="col-md-7 col-lg-8">
            <table id="tbl-student" class="table table-responsive table-bordered" cellpadding = "0" width="100%">
                <thead>
                <tr>
                    <th>Title </th>
                    <th>Question </th>
                    <th>Choice1 </th>
                    <th>Choice2 </th>
                    <th>Choice3 </th>
                    <th>Status </th>
                    <th>Poll ID</th>
                </tr>
                </thead>
                <%
                    for (Poll p : poll_array)
                    {

                %>
                <tr>
                    <td><%=p.getTitle()%> </td>
                    <td><%=p.getQuestion()%> </td>
                    <td><%=p.getChoice().get(0)%></td>
                    <td><%=p.getChoice().get(1)%></td>
                    <td><%=p.getChoice().get(2)%></td>
                    <td style="color:darkgreen"><%=p.getPoll_status()%> </td>
                    <td style="color:darkred"><%=(String)p.getId()%></td>
                </tr>

                <%

                    }
                %>

            </table>


            <h2> Actions </h2>
            <table>
                <td>
                    <form action="create.jsp" method="post">
                        <input type="submit" class="btn btn-info" id="create_new" value="Create New" name="create_new">
                    </form>
                </td>
                <td>
                    <form action="access.jsp" method="post">
                        <input type="submit" class="btn btn-info" id="access_one" value="Access one" name="access_one">
                    </form>
                </td>
                <td>
                    <form action="update.jsp" method="post">
                        <input type="submit" class="btn btn-info" id="update" value="Update" name="run">
                    </form>
                </td>
                <td>
                    <form action="run.jsp" method="post">
                        <input type="submit" id="run" value="Run" name="run" class="btn btn-info">
                    </form>
                </td>

                <td>
                    <form action = "clear.jsp" method="post">
                        <input type="submit" id="clear" value="Clear" name="clear" class="btn btn-info" >
                    </form>
                </td>

                <td>
                    <form action = "release.jsp" method="post">
                        <input type="submit" id="release" value="Release" name="release" class="btn btn-info" >
                    </form>
                </td>

                <td>
                    <form action = "unrelease.jsp" method="post">
                        <input type="submit" id="unrelease" value="UnRelease" name="unrelease" class="btn btn-info" >
                    </form>
                </td>

                <td>
                    <form action = "close.jsp" method="post">
                        <input type="submit" id="close" value="Close" name="close" class="btn btn-info" >
                    </form>
                </td>

                <td>
                    <form action = "delete.jsp" method="post">
                        <input type="submit" id="delete" value="Delete" name="delete" class="btn btn-info" >
                    </form>
                </td>
                </tr>
            </table>

            <br><br>



        </div>
<div align = "right">
    <form action="Logout" method="post">
        <input type="submit" value="Logout">
    </form>
</div>

</body>
</html>
