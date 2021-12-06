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
        String hash = newUser.doHashing(pass);
        newUser.setPassword(hash);
        //Genrate Hash Code which helps in Activation Link
        String myHash;
        Random random = new Random();
        random.nextInt(999999);
        myHash = DigestUtils.md5Hex("" + random);
        newUser.setMyhash(myHash);
        userDao.insertUser(newUser);
        boolean sendEmail_success = userDao.sendMail(email,myHash);
        if(sendEmail_success){
            response.sendRedirect("verify.jsp");
        }else {
            response.sendRedirect("index.jsp");
        }
//        PrintWriter out = response.getWriter();
//        out.println("Hello to Sq, result =" + hash);
        //response.sendRedirect("login.jsp");
    }
}
