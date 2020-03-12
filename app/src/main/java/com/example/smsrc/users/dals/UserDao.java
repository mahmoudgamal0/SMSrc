package com.example.smsrc.users.dals;

import com.example.smsrc.users.models.User;
import java.util.List;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UserDao {

    @Insert
    void insertUser(User user);

    @Query("SELECT * FROM User WHERE username =:username")
    List<User> getUserByUsername(String username);

    @Query("DELETE FROM User")
    void nukeTable();
}
