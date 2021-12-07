package com.pollsystem.soen387asg3pollsystem;

import com.pollsystem.daoimpl.UserDaoImpl;
import com.pollsystem.db.DBConnection;
import com.pollsystem.model.User;
import org.omg.PortableServer.POAPackage.ServantAlreadyActive;

import javax.servlet.RequestDispatcher;
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

@WebServlet("/ResetPassword")
public class resetPassword extends HttpServlet {


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        response.setContentType(("text/html;charset=UTF-8"));
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
        String email = request.getParameter("key1");
        //System.out.println(email);
        String hash = request.getParameter("key2");
        //System.out.println(hash);
        Connection con = DBConnection.getConnection();
        try {
            PreparedStatement pst = con.prepareStatement("SELECT user_id FROM users WHERE email=? AND hash=? AND active='0'");
            pst.setString(1, email);
            pst.setString(2, hash);
            ResultSet rs = pst.executeQuery();
            if (rs.next()){
                UserDaoImpl userDaoimpl = new UserDaoImpl();
                User user_forgot = userDaoimpl.getUser(rs.getInt("user_id"));
                request.setAttribute("user_forgot", user_forgot);
                RequestDispatcher rd = request.getRequestDispatcher("reset.jsp");
                rd.forward(request,response);

            }
            else {
                response.sendRedirect("index.jsp");

            }

        }catch(Exception ex){
            System.out.println("ResetPassword File :: "+ex);

        }

    }


}
