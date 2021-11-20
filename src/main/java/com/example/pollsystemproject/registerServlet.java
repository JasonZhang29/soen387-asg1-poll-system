package com.example.pollsystemproject;

import com.example.pollsystemproject.daoimpl.UserDaoImpl;
import com.example.pollsystemproject.model.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "registerServlet", value = "/registerServlet")
public class registerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int user_id = Integer.parseInt(request.getParameter("user_id"));
        String pass = request.getParameter("pass");
        String first_name = request.getParameter("first_name");
        String last_name = request.getParameter("last_name");
        String email = request.getParameter("email");

        UserDaoImpl userDao = new UserDaoImpl();
        User newUser = new User(user_id, first_name, last_name, email, pass);
        userDao.insertUser(newUser);
        response.sendRedirect("login.jsp");
    }
}
