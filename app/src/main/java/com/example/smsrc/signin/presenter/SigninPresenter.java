package com.example.smsrc.signin.presenter;

import android.content.Context;

import com.example.smsrc.permissions.models.Authenticate;
import com.example.smsrc.permissions.utils.Crypto;
import com.example.smsrc.users.dals.UserRepository;


public class SigninPresenter {


    public boolean signUpUser(String username,
                              String password, String confirmPassword, Context context) {

        UserRepository userRepository = new UserRepository(context);
        Authenticate authenticate = new Authenticate(userRepository);


        if(userRepository.getUserByUsername(username).size() != 0) {
            return false;
        } else if (!password.equals(confirmPassword)) {
            return false;
        } else {
            //TODO roll
            try {
                userRepository.insert(username, Crypto.encrypt(password) , null);
                authenticate.authenticate(username, password);
            } catch (Exception e) {
                return false;
            }
            return true;
        }
    }


}
