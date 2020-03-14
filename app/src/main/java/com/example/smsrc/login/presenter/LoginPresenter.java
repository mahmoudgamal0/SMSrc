package com.example.smsrc.login.presenter;

import android.content.Context;

import com.example.smsrc.cache.CacheManager;
import com.example.smsrc.permissions.models.Authenticate;
import com.example.smsrc.permissions.utils.Crypto;
import com.example.smsrc.users.dals.UserRepository;

public class LoginPresenter {

    public boolean loginUser(String username, String password,
                             UserRepository userRepository,
                             CacheManager manager) {

        Authenticate authenticate = new Authenticate(userRepository);

        try {
            authenticate.authenticate(username, password);
        } catch (Exception e) {
            throw e;
        }
        manager.cacheUser(username);
        return true;
    }


}
