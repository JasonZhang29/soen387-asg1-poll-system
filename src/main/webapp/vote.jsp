<%@ page import="com.soen387asg2pollsystem.model.Poll" %>
<%@ page import="java.util.Enumeration" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="com.soen387asg2pollsystem.daoimpl.PollDaoImpl" %>
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
  <title>vote</title>
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

<h2>Vote a Poll</h2><br>
<%--<h4>Display the Poll</h4>--%>
<%

  Poll p = (Poll) session.getAttribute("p_vote");
  String poll_id = (String) session.getAttribute("poll_id");
  String pin = (String) session.getAttribute("pin");
  PrintWriter outs = response.getWriter();
  outs.println(poll_id);
  outs.println(pin);
  String success = (String) session.getAttribute("successful");
  if(success != null){
    outs.println(success);
  }
%>
<%

  if(p.getPoll_status() == Poll.status.running)
  {

%>
<div class="col-md-7 col-lg-8">
  <form action="VoteServlet" method="post">
    <h2>Title: <%=p.getTitle()%></h2>
    <br>
    <h3>Question: <%=p.getQuestion()%></h3>

    <label for="poll_num">
      Please select your choice:
    </label>
    <select class="custom-select" name="poll_num" id="poll_num">
      <option value=1 ><%=p.getChoice().get(0)%></option>
      <option value=2 ><%=p.getChoice().get(1)%></option>
      <option value=3 ><%=p.getChoice().get(2)%></option>
    </select>
    <br><br>
    <input type="submit" name="vote" value="Vote">
  </form>
<br>
</div>

<%
}else {

  PrintWriter pw = response.getWriter();
  if(p.getPoll_status() != Poll.status.released){
    pw.println("<h2>No poll is running!</h2>");
  }
  else
  {
    pw.println("<h3>The Poll is released, Please check its result:</h3>");

      Enumeration<String> keys = p.get_Poll_Result().keys();
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
        ['<%=choices[0]%>',   <%=p.get_Poll_Result().get(choices[0])%>],
        ['<%=choices[1]%>',   <%=p.get_Poll_Result().get(choices[1])%>],
        ['<%=choices[2]%>',   <%=p.get_Poll_Result().get(choices[2])%>]
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
  <div class="col-sm-4">
    <div align = "center">
      <form action="DownloadServlet" method="get">
        <input class="btn" type="submit" name="download" value="Download">
      </form>
    </div>
  </div>

  <%
    }
    }
  %>


</div>







<div align = "right">
  <p><a href="welcome.jsp">Click Back</a> </p>
</div>
<div align="center">
  <form action="index.jsp" method="post">
    <input class="btn-warning" type="submit" name="exit" value="Exit">
  </form>
</div>
</div>




</body>
</html>
