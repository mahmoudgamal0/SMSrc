package com.example.smsrc.login.presenter;

import android.content.Context;

import com.example.smsrc.permissions.models.Authenticate;
import com.example.smsrc.permissions.utils.Crypto;
import com.example.smsrc.users.dals.UserRepository;

public class LoginPresenter {

    public boolean loginUser(String username, String password, Context context) {

        UserRepository userRepository = new UserRepository(context);
        Authenticate authenticate = new Authenticate(userRepository);

        try {
            authenticate.authenticate(username, Crypto.encrypt(password));
        } catch (Exception e) {
            return false;
        }

        return true;
    }


}
