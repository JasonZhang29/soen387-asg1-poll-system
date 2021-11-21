package com.soen387asg2pollsystem.daoimpl;

import com.soen387asg2pollsystem.model.Poll;
import com.soen387asg2pollsystem.dao.PollDAO;
import com.soen387asg2pollsystem.db.DBConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PollDaoImpl implements PollDAO{
    @Override
    public Poll getPoll(String poll_id) throws SQLException {
        // DB connection
        Connection connection = DBConnection.getPollConnection();

        try {

            Statement stmt = connection.createStatement();
            Statement stmt_c = connection.createStatement();
            Statement stmt_v = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM polls WHERE poll_id=" + poll_id);
            ResultSet rs_choice = stmt_c.executeQuery("SELECT * FROM choices WHERE poll_id=" + poll_id);
            ResultSet rs_vote = stmt_v.executeQuery("select v.pin, c.choice_context,v.poll_id\n" +
                    "from votes v\n" +
                    "join choices c on v.choice_id = c.choice_id and c.poll_id = v.poll_id and v.poll_id =" + poll_id);
            Poll poll = new Poll();
            if(rs.next()) {
                extractPollFromResultSet(poll, rs);
                if(poll.getPoll_status() == Poll.status.closed){
                    throw new SQLException("Error! This poll is closed!");
                }
            }
            ArrayList<String> new_choices = new ArrayList<>();
            while (rs_choice.next()){
                String c1 = rs_choice.getString("choice_context");
                new_choices.add(c1);
            }
            poll.setChoice(new_choices);
            Hashtable<String,String> new_vote = new Hashtable<>();
            while(rs_vote.next()){
                String pin = rs_vote.getString("pin");
                String choice = rs_vote.getString("choice_context");
                new_vote.put(pin,choice);
            }
            poll.setVote(new_vote);

            return poll;

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
    public Set<Poll> getAllPoll(int user_id) {
        Connection connection = DBConnection.getPollConnection();
        try {
            Statement stmt = connection.createStatement();
            Statement stmt_c = connection.createStatement();
            Statement stmt_v = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM polls WHERE user_id=" + user_id);

            Set<Poll> polls = new HashSet<>();
            while(rs.next())
            {
                Poll new_poll = new Poll();
                extractPollFromResultSet(new_poll, rs);
                ResultSet rs_choice = stmt_c.executeQuery("SELECT * FROM choices WHERE poll_id=" + new_poll.getId());
                ArrayList<String> new_choices = new ArrayList<>();
                while (rs_choice.next()){
                    String c1 = rs_choice.getString("choice_context");
                    new_choices.add(c1);
                }
                new_poll.setChoice(new_choices);
                Hashtable<String,String> new_vote = new Hashtable<>();
                ResultSet rs_vote = stmt_v.executeQuery("select v.pin, c.choice_context,v.poll_id\n" +
                        "from votes v\n" +
                        "join choices c on v.choice_id = c.choice_id and c.poll_id = v.poll_id and v.poll_id =" + new_poll.getId());
                while(rs_vote.next()){
                    String pin = rs_vote.getString("pin");
                    String choice = rs_vote.getString("choice_context");
                    new_vote.put(pin,choice);
                }
                new_poll.setVote(new_vote);
                polls.add(new_poll);


            }
            return polls;
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            try {
                connection.close();
            } catch(SQLException e){
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    public boolean insertPoll(Poll poll) {
        Connection connection = DBConnection.getPollConnection();
        try{
            String query = "INSERT INTO polls (poll_id,user_id,title,question,poll_status,release_date) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, poll.getId());
            ps.setInt(2, poll.getUserid());
            ps.setString(3, poll.getTitle());
            ps.setString(4, poll.getQuestion());
            ps.setString(5, String.valueOf(poll.getPoll_status()));
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDate = poll.getReleaseDate().format(dtf);
            ps.setString(6,formattedDate);
            int i = ps.executeUpdate();

            if(i == 1) {
                return true;
            }

        }catch (SQLException ex) {
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
    public boolean insertChoice(Poll poll) {
        Connection connection = DBConnection.getPollConnection();
        try{

            for(int k=0; k<poll.getChoice().size();k++)
            {
                String query_choice = "INSERT INTO choices (choice_id,choice_context,poll_id) VALUES (?, ?, ?)";
                PreparedStatement sta = connection.prepareStatement(query_choice);
                sta.setInt(1,k+1);
                sta.setString(2,poll.getChoice().get(k));
                sta.setString(3,poll.getId());
                sta.executeUpdate();
            }
            return true;
        }catch (SQLException ex) {
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
    public boolean insertVote(String poll_id, String pin, int choice_id) {
        Connection connection = DBConnection.getPollConnection();
        try{
            String query = "INSERT INTO votes (poll_id,pin,choice_id) VALUES (?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, poll_id);
            ps.setString(2, pin);
            ps.setInt(3, choice_id);

            int i = ps.executeUpdate();

            if(i == 1) {
                return true;
            }

        }catch (SQLException ex) {
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
    public boolean updatePoll(Poll poll) {
        Connection connection = DBConnection.getPollConnection();

        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE polls SET title=?, question=?, poll_status=? WHERE poll_id=?");

            ps.setString(1, poll.getTitle());
            ps.setString(2, poll.getQuestion());
            ps.setString(3, String.valueOf(poll.getPoll_status()));
            ps.setString(4, poll.getId());

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
    public boolean updateChoice(Poll poll) {
        Connection connection = DBConnection.getPollConnection();
        ArrayList<String> choices = poll.getChoice();
        try {

            String query_choice1 = "UPDATE choices SET choice_context=? WHERE  choice_id=? and poll_id=?";
            PreparedStatement sta1 = connection.prepareStatement(query_choice1);
            sta1.setString(1,choices.get(0));
            sta1.setInt(2,1);
            sta1.setString(3,poll.getId());
            sta1.executeUpdate();
            String query_choice2 = "UPDATE choices SET choice_context=? WHERE  choice_id=? and poll_id=?";
            PreparedStatement sta2 = connection.prepareStatement(query_choice2);
            sta2.setString(1,choices.get(1));
            sta2.setInt(2,2);
            sta2.setString(3,poll.getId());
            sta2.executeUpdate();
            String query_choice3 = "UPDATE choices SET choice_context=? WHERE  choice_id=? and poll_id=?";
            PreparedStatement sta3 = connection.prepareStatement(query_choice3);
            sta3.setString(1,choices.get(2));
            sta3.setInt(2,3);
            sta3.setString(3,poll.getId());
            sta3.executeUpdate();



            return true;

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
    public boolean updateVote(String poll_id, String pin, int choice_id) {
        Connection connection = DBConnection.getPollConnection();

        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE votes SET choice_id=? WHERE poll_id=? and pin=?");

            ps.setInt(1, choice_id);
            ps.setString(2, poll_id);
            ps.setString(3, pin);

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
    public boolean deletePoll(String poll_id) {
        Connection connection = DBConnection.getPollConnection();

        try{
            Statement stmt = connection.createStatement();
            int i = stmt.executeUpdate("DELETE FROM polls WHERE poll_id=" + poll_id);
            Statement stmt_c = connection.createStatement();
            int j = stmt_c.executeUpdate("DELETE FROM choices WHERE poll_id=" + poll_id);
            Statement stmt_v = connection.createStatement();
            int k = stmt_v.executeUpdate("DELETE FROM votes WHERE poll_id=" + poll_id);

            if(i == 1 && j == 1 && k==1) {
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
    public boolean check(String poll_id, String pin_id) {
        Connection connection = DBConnection.getPollConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM pins WHERE poll_id=? and pin=?");
            ps.setString(1, poll_id);
            ps.setString(2, pin_id);
            ResultSet rs = ps.executeQuery();

            if(rs.next())
            {
                return true;
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

    @Override
    public boolean insertPin(String poll_id, String pin) {
        Connection connection = DBConnection.getPollConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO pins (pin,poll_id) VALUES (?, ?)");
            ps.setString(1, pin);
            ps.setString(2, poll_id);
            int i = ps.executeUpdate();

            if(i == 1) {
                return true;
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

    @Override
    public boolean checkVote(String poll_id, String pin_id) {
        Connection connection = DBConnection.getPollConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM votes WHERE poll_id=? and pin=?");
            ps.setString(1, poll_id);
            ps.setString(2, pin_id);
            ResultSet rs = ps.executeQuery();

            if(rs.next())
            {
                return true;
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

    private void extractPollFromResultSet(Poll p, ResultSet rs) throws SQLException {

        p.setId( rs.getString("poll_id"));
        p.setUserid(rs.getInt("user_id"));
        p.setTitle( rs.getString("title"));
        p.setQuestion( rs.getString("question"));
        String status = rs.getString("poll_status");
        Poll.status st = Poll.status.valueOf(status);
        p.setPoll_status(st);
        String date = rs.getString("release_date");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
        p.setReleaseDate(dateTime);

        //return p;
    }





}
