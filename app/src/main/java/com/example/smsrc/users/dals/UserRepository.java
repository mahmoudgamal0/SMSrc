package com.example.smsrc.users.dals;

import android.content.Context;
import android.util.Log;

import com.example.smsrc.db.DBRunner;
import com.example.smsrc.users.models.User;
import java.util.List;
import androidx.room.Room;


public class UserRepository {
    private DBRunner dbRunner;

    private static UserRepository userRepository;

    public static UserRepository getUserRepository(Context context) {
        if(userRepository == null) {
            userRepository = new UserRepository(context);
        }
        return userRepository;
    }

    private UserRepository(Context context) {
        String DB_NAME = "SMSrc.db";
        dbRunner = Room.databaseBuilder(context, DBRunner.class, DB_NAME).allowMainThreadQueries().build();
    }

    public void insert(String username, String passcode, String authlevel){
        Log.d("UserRepository", "inserting new user "+username);
        User user = new User();
        user.setUsername(username);
        user.setPasscode(passcode);
        user.setAuthLevel(authlevel);
        dbRunner.userDao().insertUser(user);
    }

    public List<User> getUserByUsername(String username) {
        Log.d("UserRepository", "attempt to return "+username);
        return dbRunner.userDao().getUserByUsername(username);
    }

    public void nukeTable(){
        Log.d("UserRepository", "deleting all table entries");
        dbRunner.userDao().nukeTable();
    }

    public List<User> getAllUsers() {
        Log.d("UserRepository", "get list of all users");
        return dbRunner.userDao().getAllUsers();
    }


    public void updateUserInfo(int id, String username, String authLevel) {
        Log.d("UserRepository", "update user user "+username);
        dbRunner.userDao().updateUserInfo(id, username, authLevel);
    }

    public void deleteUser(String username) {
        Log.d("UserRepository", "delete user: "+username);
        dbRunner.userDao().deleteUser(username);
    }
}