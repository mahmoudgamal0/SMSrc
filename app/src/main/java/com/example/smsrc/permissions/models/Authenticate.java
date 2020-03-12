package com.example.smsrc.permissions.models;

import android.content.Context;

import com.example.smsrc.users.dals.UserRepository;
import com.example.smsrc.users.models.User;

import java.util.List;

public class Authenticate {
    private Context context;

    public Authenticate(Context context){
        this.context = context;
    }

    public void authenticate(String username, String passcode){
        UserRepository userRepository = new UserRepository(context);
        List<User> users = userRepository.getUserByUsername(username);

        if(users.size() == 0)
            throw new RuntimeException("User doesn't exist");

        User user = users.get(0);

        // TODO bcrypt passcode
        if(!user.getPasscode().equals(passcode))
           throw new RuntimeException("Invalid Credentials");
    }
}
