package com.example.smsrc.permissions.models;


import android.util.Log;

import com.example.smsrc.permissions.utils.Crypto;
import com.example.smsrc.users.dals.UserRepository;
import com.example.smsrc.users.models.User;

import java.util.List;

public class Authenticate {
    private UserRepository userRepository;

    public Authenticate(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public boolean authenticate(String username, String passcode){
        List<User> users = userRepository.getUserByUsername(username);

        Log.i("Authenticate", "Authenticating user");

        Log.d("Authenticate", "check if user exist");
        if(users.size() == 0)
            throw new RuntimeException("User doesn't exist");

        User user = users.get(0);

        Log.d("Authenticate", "check if password match");
        if(!user.getPasscode().equals(Crypto.encrypt(passcode)))
            throw new RuntimeException("Invalid Credentials");

        Log.i("Authenticate", "Authenticated successfully");
        return true;
    }
}
