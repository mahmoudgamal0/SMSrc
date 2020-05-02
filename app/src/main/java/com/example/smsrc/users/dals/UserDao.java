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

    @Query("SELECT * FROM USER")
    List<User> getAllUsers();

    @Query("UPDATE USER SET username =:username, authLevel =:authLevel WHERE id =:id")
    void updateUserInfo(int id, String username, String authLevel);

    @Query("DELETE FROM USER WHERE username =:username")
    void deleteUser(String username);
}
