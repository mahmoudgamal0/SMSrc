package com.example.smsrc.permissions.models;


import com.example.smsrc.permissions.utils.Crypto;
import com.example.smsrc.users.dals.UserRepository;
import com.example.smsrc.users.models.User;

import java.util.List;

public class Authenticate {
    private UserRepository userRepository;

    public Authenticate(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public void authenticate(String username, String passcode){
        List<User> users = userRepository.getUserByUsername(username);

        if(users.size() == 0)
            throw new RuntimeException("User doesn't exist");

        User user = users.get(0);

        if(!user.getPasscode().equals(Crypto.encrypt(passcode)))
            throw new RuntimeException("Invalid Credentials");
    }
}
