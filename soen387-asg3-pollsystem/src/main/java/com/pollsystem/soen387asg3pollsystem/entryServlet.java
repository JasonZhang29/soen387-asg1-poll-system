package com.pollsystem.soen387asg3pollsystem;

import com.pollsystem.daoimpl.PollDaoImpl;
import com.pollsystem.model.Poll;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/EntryServlet")
public class entryServlet extends HttpServlet {


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String poll_id = request.getParameter("poll_id");
        String pin = request.getParameter("pin");
        PollDaoImpl pollDaoimpl = new PollDaoImpl();
        try{
            if(pollDaoimpl.check(poll_id,pin)){
                Poll p_vote = pollDaoimpl.getPoll(poll_id);
                HttpSession session = request.getSession();
                session.setAttribute("p_vote",p_vote);
                session.setAttribute("poll_id", poll_id);
                session.setAttribute("pin", pin);
                RequestDispatcher rd = request.getRequestDispatcher("vote.jsp");
                rd.forward(request,response);

            }
            else{
                response.sendRedirect("welcome.jsp");
            }

        }
        catch (SQLException e){
            e.printStackTrace();
        }

    }



}