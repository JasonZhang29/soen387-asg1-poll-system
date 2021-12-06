package com.pollsystem.daoimpl;

import com.pollsystem.dao.UserDAO;
import com.pollsystem.db.DBConnection;
import com.pollsystem.model.User;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.SecureRandom;
import java.sql.*;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class UserDaoImpl implements UserDAO {

    @Override
    public User getUser(int id) {
        // DB connection
        Connection connection = DBConnection.getConnection();

        try {

            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE user_id=" + id);

            if(rs.next())
            {
//                com.example.pollsystemproject.model.User user = new com.example.pollsystemproject.model.User();
//                user.setUserId( rs.getInt("user_id") );
//                user.setFirstName( rs.getString("first_name") );
//                user.setLastName( rs.getString("Last_name") );
//                user.setEmail( rs.getString("email"));
//                user.setPassword( rs.getString("password") );
                //return user;
                return extractUserFromResultSet(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch(SQLException e){
                e.printStackTrace();
            }
        }

        return null;
    }



    @Override
    public Set<User> getAllUsers() {

        Connection connection = DBConnection.getConnection();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users");

            Set users = new HashSet();

            while(rs.next())
            {
                User user = extractUserFromResultSet(rs);
                users.add(user);
            }

            return users;

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch(SQLException e){
                e.printStackTrace();
            }
        }

        return null;
    }

//    @Override
//    public User getUserByUserIDAndPassword(int id, String password) {
//        // TODO: Try it yourself
//        // Hint : Use 'AND' in your query
//
//        return null;
//    }

    @Override
    public boolean insertUser(User user) {

        Connection connection = DBConnection.getConnection();

        try {
            String query = "INSERT INTO users (user_id,first_name,last_name,email,password,hash) VALUES (?, ?, ?, ?, ?, ?)";
            // Passing Statement.RETURN_GENERATED_KEYS to make getGeneratedKeys() work
            PreparedStatement ps = connection.prepareStatement(query);

            ps.setInt(1, user.getUserId());
            ps.setString(2, user.getFirstName());
            ps.setString(3, user.getLastName());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getPassword());
            ps.setString(6, user.getMyhash());

            ps.executeUpdate();
            int i = ps.executeUpdate();

            if(i != 0) {
                //this.sendMail(user.getEmail(), user.getMyhash());
                System.out.println("success!!!");
                return true;
            }

        }
        catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                connection.close();
            }
            catch(SQLException e){
                e.printStackTrace();
            }
        }

        return false;
    }

    @Override
    public boolean updateUser(User user) {

        Connection connection = DBConnection.getConnection();

        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE users SET first_name=?, last_name=?, email=?, password=? WHERE user_id=?");

            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPassword());
            ps.setInt(5,user.getUserId());

            int i = ps.executeUpdate();

            if(i == 1) {
                return true;
            }

        }
        catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                connection.close();
            }
            catch(SQLException e){
                e.printStackTrace();
            }
        }

        return false;
    }

    @Override
    public boolean deleteUser(int id) {

        Connection connection = DBConnection.getConnection();

        try {
            Statement stmt = connection.createStatement();
            int i = stmt.executeUpdate("DELETE FROM users WHERE user_id=" + id);

            if(i == 1) {
                return true;
            }

        }
        catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                connection.close();
            }
            catch(SQLException e){
                e.printStackTrace();
            }
        }

        return false;
    }

    @Override
    public boolean check(int id, String pass) {
        // DB connection
        Connection connection = DBConnection.getConnection();
        User user = new User();
        String hash = user.doHashing(pass);
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM users WHERE user_id=? and password=?");
            ps.setInt(1, id);
            ps.setString(2, hash);
            ResultSet rs = ps.executeQuery();


            if(rs.next())
            {
                int active = rs.getInt("active");
                if(active == 1){
                    return true;
                }
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            }
            catch(SQLException e){
                e.printStackTrace();
            }
        }
        return false;

    }

    private User extractUserFromResultSet(ResultSet rs) throws SQLException {

        User user = new User();
        user.setUserId( rs.getInt("user_id") );
        user.setFirstName( rs.getString("first_name") );
        user.setLastName( rs.getString("last_name") );
        user.setEmail( rs.getString("email"));
        user.setPassword( rs.getString("password") );
        user.setMyhash(rs.getString("hash"));
        user.setActive(rs.getInt("active"));
        return user;
    }

    @Override
    public boolean sendMail(String recipient, String myhash) {
        System.out.println("preparing to send email");
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        String from = "************";
        String pass = "8888888888";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.required", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {

            message.setFrom(new InternetAddress(from));
            InternetAddress toAddress = new InternetAddress(recipient);

            message.addRecipient(Message.RecipientType.TO, toAddress);

            String subject = "Email Verification from Poll System";
            message.setSubject(subject);

            String body = "<h1>Hello, Please verify your account:</h1><br/><a href='http://localhost:8080/soen387_asg3_pollsystem_war_exploded/ActivateAccount?key1="+
                    recipient+"&key2="+myhash+"'>Link</a>";
            message.setContent(body,"text/html");

            Transport transport = session.getTransport("smtp");

            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            System.out.println("Email send successfully");
            return true;
        } catch (MessagingException ae) {
            ae.printStackTrace();
        }
        return false;
    }


}
