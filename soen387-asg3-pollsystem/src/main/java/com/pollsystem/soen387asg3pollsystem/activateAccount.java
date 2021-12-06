package com.pollsystem.soen387asg3pollsystem;

import com.pollsystem.daoimpl.UserDaoImpl;
import com.pollsystem.db.DBConnection;
import org.omg.PortableServer.POAPackage.ServantAlreadyActive;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/ActivateAccount")
public class activateAccount extends HttpServlet {


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        response.setContentType(("text/html;charset=UTF-8"));
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
        String email = request.getParameter("key1");
        System.out.println(email);
        String hash = request.getParameter("key2");
        System.out.println(hash);
        Connection con = DBConnection.getConnection();
        try {
            PreparedStatement pst = con.prepareStatement("SELECT email,hash,active FROM users WHERE email=? AND hash=? AND active='0'");
            pst.setString(1, email);
            pst.setString(2, hash);
            ResultSet rs = pst.executeQuery();
            if (rs.next()){
                PreparedStatement pst1 = con.prepareStatement("UPDATE users SET active='1' WHERE email=? AND hash=?");
                pst1.setString(1,email);
                pst1.setString(2,hash);
                int i = pst1.executeUpdate();
                if(i != 0){
                    response.sendRedirect("login.jsp");
                }else{
                    response.sendRedirect("index.jsp");
                }
            }

        }catch(Exception ex){
            System.out.println("ActivateAccount File :: "+ex);

        }




    }


}