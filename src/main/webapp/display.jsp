<%@ page import="com.soen387asg2pollsystem.model.Poll" %>
<%@ page import="com.soen387asg2pollsystem.daoimpl.PollDaoImpl" %>
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
<h2>Display the current Poll</h2>

    <%
        PrintWriter pw = response.getWriter();
        if("post".equalsIgnoreCase(request.getMethod()) && request.getParameter("create")!=null){

                String title=request.getParameter("title");
                String question=request.getParameter("question");
                ArrayList<String> choices = new ArrayList<>();
                String choice1 = request.getParameter("choice1");
                String choice2 = request.getParameter("choice2");
                String choice3 = request.getParameter("choice3");
                choices.add(choice1);
                choices.add(choice2);
                choices.add(choice3);
                Poll cp = new Poll();
                int userid = (int) session.getAttribute("userid");
                cp.create_Poll(userid,title,question,choices);
                pw.println(cp.getId());
            pw.println(cp.getReleaseDate());
            pw.println(cp.getChoice());
            pw.println(cp.getVote());
                PollDaoImpl pollDaoimpl = new PollDaoImpl();
                pollDaoimpl.insertPoll(cp);
                pollDaoimpl.insertChoice(cp);


        }

    %>

<%--    &lt;%&ndash;%>
<%--        if("post".equalsIgnoreCase(request.getMethod()) && request.getParameter("update")!=null){--%>

<%--        String title=request.getParameter("title");--%>
<%--        String question=request.getParameter("question");--%>
<%--        String[] choices = new String[3];--%>
<%--        String choice1 = request.getParameter("choice1");--%>
<%--        String choice2 = request.getParameter("choice2");--%>
<%--        String choice3 = request.getParameter("choice3");--%>
<%--        choices[0] = choice1;--%>
<%--        choices[1] = choice2;--%>
<%--        choices[2] = choice3;--%>
<%--        p.update_Poll(title,question,choices);--%>

<%--        }--%>
<%--    %>--%>
<%--    &lt;%&ndash;%>
<%--        if("post".equalsIgnoreCase(request.getMethod()) && request.getParameter("run")!=null){--%>

<%--        p.run_Poll();--%>
<%--        }--%>
<%--    %>--%>
<%--    &lt;%&ndash;%>
<%--        if("post".equalsIgnoreCase(request.getMethod()) && request.getParameter("release")!=null){--%>

<%--                p.release_Poll();--%>
<%--        }--%>
<%--    %>--%>
<%--    &lt;%&ndash;%>
<%--        if("post".equalsIgnoreCase(request.getMethod()) && request.getParameter("unrelease")!=null){--%>
<%--                p.unrelease_Poll();--%>
<%--        }--%>
<%--    %>--%>

<%--    &lt;%&ndash;%>
<%--        if("post".equalsIgnoreCase(request.getMethod()) && request.getParameter("clear")!=null){--%>

<%--                p.clear_Poll();--%>
<%--        }--%>
<%--%>--%>


<%--    &lt;%&ndash;%>
<%--        if("post".equalsIgnoreCase(request.getMethod()) && request.getParameter("close")!=null){--%>
<%--                 p.close_Poll();--%>
<%--                 RequestDispatcher rd = request.getRequestDispatcher("create.jsp");--%>
<%--                 rd.forward(request,response);--%>
<%--        }--%>
<%--    %>--%>

        <%

            int user_id = (int) session.getAttribute("userid");
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
                    <td style="color:darkred"><%=p.getId()%></td>
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
                    <form action="update.jsp" method="post">
                        <input type="submit" class="btn btn-info" id="update" value="Update" name="run">
                    </form>
                </td>
                <td>
                    <form action="display.jsp" method="post">
                        <input type="submit" id="run" value="Run" name="run" class="btn btn-info">
                    </form>
                </td>

                <td>
                    <form action = "display.jsp" method="post">
                        <input type="submit" id="clear" value="Clear" name="clear" class="btn btn-info" >
                    </form>
                </td>

                <td>
                    <form action = "display.jsp" method="post">
                        <input type="submit" id="release" value="Release" name="release" class="btn btn-info" >
                    </form>
                </td>

                <td>
                    <form action = "display.jsp" method="post">
                        <input type="submit" id="unrelease" value="UnRelease" name="unrelease" class="btn btn-info" >
                    </form>
                </td>

                <td>
                    <form action = "display.jsp" method="post">
                        <input type="submit" id="close" value="Close" name="close" class="btn btn-info" >
                    </form>
                </td>
                </tr>
            </table>

<%--            &lt;%&ndash;%>
<%--                if(p.getPoll_status() == Poll.status.released){--%>
<%--                    Enumeration<String> keys = p.get_Poll_Result().keys();--%>
<%--                    String[] choices = new String[3];--%>
<%--                    int i =0;--%>
<%--                    while(keys.hasMoreElements()){--%>
<%--                        choices[i] = keys.nextElement();--%>
<%--                        i++;--%>
<%--                    }--%>

<%--            %>--%>
<%--            <script type="text/javascript">--%>
<%--                google.charts.load("current", {packages:["corechart"]});--%>
<%--                google.charts.setOnLoadCallback(drawChart);--%>
<%--                function drawChart() {--%>
<%--                    var data = google.visualization.arrayToDataTable([--%>
<%--                        ['Choices', 'Results'],--%>
<%--                        ['<%=choices[0]%>',   <%=p.get_Poll_Result().get(choices[0])%>],--%>
<%--                        ['<%=choices[1]%>',   <%=p.get_Poll_Result().get(choices[1])%>],--%>
<%--                        ['<%=choices[2]%>',   <%=p.get_Poll_Result().get(choices[2])%>]--%>
<%--                    ]);--%>

<%--                    var options = {--%>
<%--                        title: 'The Poll Result',--%>
<%--                        pieHole: 0.4,--%>
<%--                    };--%>

<%--                    var chart = new google.visualization.PieChart(document.getElementById('donutchart'));--%>
<%--                    chart.draw(data, options);--%>
<%--                }--%>
<%--            </script>--%>
<%--            <div id="donutchart" style="width: 900px; height: 500px;"></div>--%>

<%--            &lt;%&ndash;%>
<%--                }--%>
<%--            %>--%>

        </div>
<div align = "right">
    <form action="Logout" method="post">
        <input type="submit" value="Logout">
    </form>
</div>

</body>
</html>
