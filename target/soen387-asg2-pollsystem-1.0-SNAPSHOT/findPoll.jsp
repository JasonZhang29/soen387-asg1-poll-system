<%@ page import="com.soen387asg2pollsystem.model.Poll" %>
<%@ page import="java.util.Enumeration" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.io.PrintWriter" %>
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
  <title>find</title>
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
<h2>Find a Poll</h2><br>
<h2>Display the Poll</h2>
<%
  Poll p_find = (Poll) session.getAttribute("p_find");
%>
<div class="col-md-7 col-lg-8">
  <table id="tbl-access" class="table table-responsive table-bordered" cellpadding = "0" width="100%">
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
    <tr>
      <td><%=p_find.getTitle()%> </td>
      <td><%=p_find.getQuestion()%> </td>
      <td><%=p_find.getChoice().get(0)%></td>
      <td><%=p_find.getChoice().get(1)%></td>
      <td><%=p_find.getChoice().get(2)%></td>
      <td style="color:darkgreen"><%=p_find.getPoll_status()%> </td>
      <td style="color:darkred"><%=p_find.getId()%></td>
    </tr>
  </table>

  <%
    if(p_find.getPoll_status() == Poll.status.released){
      PrintWriter pw = response.getWriter();
      //pw.println( p_find.get_Poll_Result());

      Enumeration<String> keys = p_find.get_Poll_Result().keys();
      String[] choices = new String[3];
      int i =0;
      while(keys.hasMoreElements()){
        choices[i] = keys.nextElement();
        i++;
      }

  %>
  <script type="text/javascript">
    google.charts.load("current", {packages:["corechart"]});
    google.charts.setOnLoadCallback(drawChart);
    function drawChart() {
      var data = google.visualization.arrayToDataTable([
        ['Choices', 'Results'],
        ['<%=choices[0]%>',   <%=p_find.get_Poll_Result().get(choices[0])%>],
        ['<%=choices[1]%>',   <%=p_find.get_Poll_Result().get(choices[1])%>],
        ['<%=choices[2]%>',   <%=p_find.get_Poll_Result().get(choices[2])%>]
      ]);

      var options = {
        title: 'The Poll Result',
        pieHole: 0.4,
      };

      var chart = new google.visualization.PieChart(document.getElementById('donutchart'));
      chart.draw(data, options);
    }
  </script>
  <div id="donutchart" style="width: 900px; height: 500px;"></div>

  <%
    }
  %>


</div>







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
