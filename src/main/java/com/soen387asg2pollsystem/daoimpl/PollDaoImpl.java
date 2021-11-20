package com.soen387asg2pollsystem.daoimpl;

import com.soen387asg2pollsystem.model.Poll;
import com.soen387asg2pollsystem.dao.PollDAO;
import com.soen387asg2pollsystem.db.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Set;

public class PollDaoImpl implements PollDAO{
    @Override
    public Poll getPoll(String poll_id) throws SQLException {
        // DB connection
        Connection connection = DBConnection.getConnection();

        try {

            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM polls WHERE poll_id=" + poll_id);
            ResultSet rs_choice = stmt.executeQuery("SELECT * FROM choices WHERE poll_id=" + poll_id);
            if(rs.next())
            {
                return extractPollFromResultSet(rs, rs_choice);
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
    public Set<Poll> getAllPoll(int user_id) {
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

    private Poll extractPollFromResultSet(ResultSet rs, ResultSet rs_choice) throws SQLException {

        Poll p = new Poll();
        p.setId( rs.getString("poll_id") );
        p.setTitle( rs.getString("title") );
        p.setQuestion( rs.getString("question") );
        String status = rs.getString("poll_status");
        Poll.status st = Poll.status.valueOf(status);
        p.setPoll_status(st);
        ArrayList<String> new_choices = new ArrayList<>();

        while (rs_choice.next()){
            String c1 = rs_choice.getString("choice_context");
            new_choices.add(c1);
        }
        p.setChoice(new_choices);




        return p;
    }


}
