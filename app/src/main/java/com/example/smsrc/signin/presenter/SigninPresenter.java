package com.example.smsrc.signin.presenter;

import android.content.Context;
import android.util.Log;

import com.example.smsrc.cache.CacheManager;
import com.example.smsrc.permissions.models.Authenticate;
import com.example.smsrc.permissions.utils.AuthRoles;
import com.example.smsrc.permissions.utils.Crypto;
import com.example.smsrc.users.dals.UserRepository;


public class SigninPresenter {


    public boolean signUpUser(String username,
                              String password,
                              String confirmPassword,
                              UserRepository userRepository,
                              Authenticate authenticate,
                              CacheManager manager) {

        if(userRepository.getUserByUsername(username).size() != 0) {
            throw new RuntimeException("Username already exists");
        } else if (!password.equals(confirmPassword)) {
            throw new RuntimeException("Password mismatch");
        } else {
            //TODO roll
            try {
                userRepository.insert(username, Crypto.encrypt(password) , AuthRoles.OWNER);
                authenticate.authenticate(username, password);
            } catch (Exception e) {
                Log.e("SigninPresenter", e.getMessage());
                throw e;
            }
            manager.cacheUser(username);
            return true;
        }
    }


}
