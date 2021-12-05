package com.pollsystem.soen387asg3pollsystem;

import com.pollsystem.daoimpl.PollDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/VoteServlet")
public class voteServlet extends HttpServlet {


    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int choice_id = Integer.parseInt(request.getParameter("poll_num"));
        HttpSession session = request.getSession();
        String poll_id = (String) session.getAttribute("poll_id");
        String pin = (String) session.getAttribute("pin");
        PollDaoImpl pollDaoimpl = new PollDaoImpl();
        if(pollDaoimpl.checkVote(poll_id,pin)){
            pollDaoimpl.updateVote(poll_id,pin,choice_id);
            String success = "Successful vote!";
            session.setAttribute("successful",success);
            response.sendRedirect("vote.jsp");

        }
        else{
            pollDaoimpl.insertVote(poll_id,pin,choice_id);
            String success = "Successful vote!";
            session.setAttribute("successful",success);
            response.sendRedirect("vote.jsp");
        }

    }



}