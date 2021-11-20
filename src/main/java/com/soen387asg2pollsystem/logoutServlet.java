package com.soen387asg2pollsystem;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/Logout")
public class logoutServlet extends HttpServlet{

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        session.removeAttribute("userid");
        session.invalidate();
        response.sendRedirect("index.jsp");
    }
}
