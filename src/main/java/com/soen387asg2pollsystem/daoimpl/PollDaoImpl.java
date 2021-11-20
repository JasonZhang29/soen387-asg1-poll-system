package com.soen387asg2pollsystem.daoimpl;

import com.soen387asg2pollsystem.model.Poll;
import com.soen387asg2pollsystem.dao.PollDAO;
import com.soen387asg2pollsystem.db.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
            ResultSet rs_vote = stmt_v.executeQuery("SELECT * FROM votes WHERE poll_id=" + poll_id);
            Poll poll = new Poll();
            if(rs.next()) {
                extractPollFromResultSet(poll, rs);
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
                String choice = rs_vote.getString("choice_id");
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
                ResultSet rs_vote = stmt_v.executeQuery("SELECT * FROM votes WHERE poll_id=" + new_poll.getId());
                while(rs_vote.next()){
                    String pin = rs_vote.getString("pin");
                    String choice = rs_vote.getString("choice_id");
                    new_vote.put(pin,choice);
                }
                new_poll.setVote(new_vote);
                polls.add(new_poll);


            }
            return polls;
        }
        catch (SQLException ex) {
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

    @Override
    public boolean insertPoll(Poll poll) {
        return false;
    }

    @Override
    public boolean updatePoll(Poll poll) {
        return false;
    }

    @Override
    public boolean deletePoll(String poll_id) {
        return false;
    }

    @Override
    public boolean check(String poll_id, String pin_id) {
        return false;
    }

    private void extractPollFromResultSet(Poll p, ResultSet rs) throws SQLException {

        p.setId( rs.getString("poll_id") );
        p.setUserid(rs.getInt("user_id"));
        p.setTitle( rs.getString("title") );
        p.setQuestion( rs.getString("question") );
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
