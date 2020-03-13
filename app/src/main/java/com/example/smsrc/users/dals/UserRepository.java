package com.example.smsrc.users.dals;

import android.content.Context;
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
        dbRunner = Room.databaseBuilder(context, DBRunner.class, DB_NAME).build();
    }

    public void insert(String username, String passcode, String authlevel){
        User user = new User();
        user.setUsername(username);
        user.setPasscode(passcode);
        user.setAuthLevel(authlevel);
        dbRunner.userDao().insertUser(user);
    }

    public List<User> getUserByUsername(String username) {
        return dbRunner.userDao().getUserByUsername(username);
    }

    public void nukeTable(){
        dbRunner.userDao().nukeTable();
    }

}