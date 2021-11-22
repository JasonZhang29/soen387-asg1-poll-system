package com.soen387asg2pollsystem;

import com.soen387asg2pollsystem.daoimpl.PollDaoImpl;
import com.soen387asg2pollsystem.model.Poll;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/PinServlet")
public class pinServlet extends HttpServlet {


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String poll_id = request.getParameter("poll_id");
        PollDaoImpl pollDaoimpl = new PollDaoImpl();
        String numStr = "0123456789";
        String newPin = "";
        for(int i = 0; i < 6; i++) {
            SecureRandom rand = new SecureRandom();
            int index = rand.nextInt(10);
            newPin += index;
        }
        try {
            if(pollDaoimpl.getPoll(poll_id).getId() != null){
                pollDaoimpl.insertPin(poll_id,newPin);
                request.setAttribute("newPin",newPin);
                RequestDispatcher rd = request.getRequestDispatcher("welcome.jsp");
                rd.forward(request,response);

            }
            else{
                response.sendRedirect("pin.jsp");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }



}