package com.example.smsrc.cache;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.smsrc.R;
import com.example.smsrc.users.models.User;

public class CacheManager {

    private SharedPreferences sharedPreferences;

    public CacheManager(Context context) {
        sharedPreferences = context.getSharedPreferences("Auth",Context.MODE_PRIVATE);
    }

    public void cacheUser(String username) {
        //TODO string s
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString( "username", username);
        editor.apply();
    }

    public String getCachedUser() {
        return sharedPreferences.getString("username", null);
    }

    public void clearCache(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("username");
        editor.apply();
    }

    public boolean isUserCached() {
        return sharedPreferences.contains("username");
    }
}
