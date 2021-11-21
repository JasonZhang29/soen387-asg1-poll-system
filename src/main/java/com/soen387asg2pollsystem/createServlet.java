package com.soen387asg2pollsystem;

import com.soen387asg2pollsystem.daoimpl.PollDaoImpl;
import com.soen387asg2pollsystem.model.Poll;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/CreateServlet")
public class createServlet extends HttpServlet {


    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
        HttpSession session = request.getSession();
        int userid = (int) session.getAttribute("userid");
        cp.create_Poll(userid,title,question,choices);
        PollDaoImpl pollDaoimpl = new PollDaoImpl();


        if(pollDaoimpl.insertPoll(cp) && pollDaoimpl.insertChoice(cp)){
            response.sendRedirect("display.jsp");
        }
        else
        {
            response.sendRedirect("index.jsp");
        }
    }


}