package com.pollsystem.soen387asg3pollsystem;

import com.pollsystem.daoimpl.PollDaoImpl;
import com.pollsystem.model.Poll;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/AccessServlet")
public class accessServlet extends HttpServlet {


    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String poll_id = request.getParameter("poll_id");
        PollDaoImpl pollDaoimpl = new PollDaoImpl();
        try{
            Poll p_find = pollDaoimpl.getPoll(poll_id);
            HttpSession session = request.getSession();
            if(p_find.getId() != null){
                session.setAttribute("p_find", p_find);
                response.sendRedirect("findPoll.jsp");
            }
            else{
                response.sendRedirect("display.jsp");

            }

        }
        catch (SQLException e){
            e.printStackTrace();
        }

    }


}