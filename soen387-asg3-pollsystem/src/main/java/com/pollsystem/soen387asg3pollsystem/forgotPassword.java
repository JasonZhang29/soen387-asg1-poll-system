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

@WebServlet(name = "forgotPassword", value = "/ForgotPassword")
public class forgotPassword extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int user_id = Integer.parseInt(request.getParameter("user_id"));
        String email = request.getParameter("email");

        UserDaoImpl userDao = new UserDaoImpl();
        User user = userDao.getUser(user_id);

        if(user.getEmail().equals(email)){
            String user_email = user.getEmail();
            user.setActive(0);
            //Genrate Hash Code which helps in Activation Link
            String myHash;
            Random random = new Random();
            random.nextInt(999999);
            myHash = DigestUtils.md5Hex("" + random);
            user.setMyhash(myHash);
            userDao.updateUser(user);
            boolean sendEmail_success = userDao.sendMail(user_email,myHash,1);
            if(sendEmail_success){
                response.sendRedirect("verify.jsp");
            }else {
                response.sendRedirect("index.jsp");
            }

        }else {
            response.sendRedirect("forgot.jsp");
        }
//        PrintWriter out = response.getWriter();
//        out.println("Hello to Sq, result =" + hash);
        //response.sendRedirect("login.jsp");
    }
}

