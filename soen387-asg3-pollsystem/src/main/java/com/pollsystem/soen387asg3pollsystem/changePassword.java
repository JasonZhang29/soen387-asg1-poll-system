package com.pollsystem.soen387asg3pollsystem;

import com.pollsystem.daoimpl.UserDaoImpl;
import com.pollsystem.model.User;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;

@WebServlet("/ChangePassword")
public class changePassword extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int user_id = Integer.parseInt(request.getParameter("user_id"));
        String old_pass = request.getParameter("old_pass");
        String new_pass = request.getParameter("new_pass");

        UserDaoImpl userDao = new UserDaoImpl();
        User user = userDao.getUser(user_id);
        if(user.getActive() == 1 && userDao.check(user_id, old_pass)){
            String hash = user.doHashing(new_pass);
            user.setPassword(hash);
            userDao.updateUser(user);
            response.sendRedirect("login.jsp");
        }else {
            response.sendRedirect("index.jsp");
        }
//        PrintWriter out = response.getWriter();
//        out.println("Hello to Sq, result =" + hash);
        //response.sendRedirect("login.jsp");
    }
}

