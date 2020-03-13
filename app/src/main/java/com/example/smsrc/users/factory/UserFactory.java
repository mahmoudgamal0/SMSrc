package com.example.smsrc.users.factory;

import android.content.Context;

import com.example.smsrc.users.dals.UserRepository;

public class UserFactory {

    public UserRepository getRepository(Context context){
        return new UserRepository(context);
    }
}
