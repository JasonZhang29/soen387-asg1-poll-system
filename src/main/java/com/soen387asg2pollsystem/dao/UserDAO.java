package com.soen387asg2pollsystem.dao;

import com.soen387asg2pollsystem.model.User;

import java.sql.SQLException;
import java.util.Set;

// UserDAO (Data Access Object) can do CRUD operations, it can Create, Retreive, Updata, Delete from our table.
public interface UserDAO {

    User getUser(int id) throws SQLException;

    Set<User> getAllUsers();

    //User getUserByUserIDAndPassword(int id, String password);

    boolean insertUser(User user);

    boolean updateUser(User user);

    boolean deleteUser(int id);

    boolean check(int id, String pass);
}