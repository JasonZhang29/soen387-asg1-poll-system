package com.soen387asg2pollsystem.dao;

import com.soen387asg2pollsystem.model.Poll;

import java.sql.SQLException;
import java.util.Set;

public interface PollDAO {
    Poll getPoll(String poll_id) throws SQLException;

    Set<Poll> getAllPoll(int user_id);

    boolean insertPoll(Poll poll);
    boolean insertChoice(Poll poll);
    boolean insertVote(String poll_id, String pin, int choice_id);
    boolean updatePoll(Poll poll);
    boolean updateChoice(Poll poll);
    boolean updateVote(String poll_id, String pin, int choice_id);


    boolean deletePoll(String poll_id);


    boolean check(String poll_id, String pin_id);
    boolean insertPin(String poll_id, String pin);
    boolean checkVote(String poll_id, String pin_id);

}
