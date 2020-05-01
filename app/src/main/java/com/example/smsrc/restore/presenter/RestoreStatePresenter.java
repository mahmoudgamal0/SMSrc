package com.example.smsrc.restore.presenter;

import android.content.Context;

import com.example.smsrc.auth.utils.Crypto;
import com.example.smsrc.cache.CacheManager;
import com.example.smsrc.restore.model.RestoreStateModel;
import com.example.smsrc.users.dals.UserRepository;
import com.example.smsrc.users.models.User;

public class RestoreStatePresenter {

    private RestoreStateModel model;
    private CacheManager cacheManager;
    private UserRepository repository;

    public RestoreStatePresenter(Context context){
        this.model = new RestoreStateModel(context);
        this.cacheManager = new CacheManager(context);
        this.repository = UserRepository.getUserRepository(context);
    }

    public void restorePhoneState(){
        this.model.restorePhone();
    }

    public boolean validateUser(String password, String pin){
        boolean correctPassword = false;
        boolean correctPin = false;

        String cachedUsername = cacheManager.getCachedUser();
        String cachedPin = cacheManager.getCachedPin();

        User user = this.repository.getUserByUsername(cachedUsername).get(0);
        String hashedPassword = Crypto.encrypt(password);

        if(hashedPassword.equals(user.getPasscode())) correctPassword = true;
        if(cachedPin == null || cachedPin.equals(pin)) correctPin = true;

        return correctPassword && correctPin;
    }
}
