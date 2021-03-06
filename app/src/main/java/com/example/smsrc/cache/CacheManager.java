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

    public void cacheUser(String username) { putString("username", username); }

    public String getCachedUser() {
        return sharedPreferences.getString("username", null);
    }

    public void cachePin(String pin) {
        String key = getCachedUser() + "pin";
        putString(key, pin);
    }

    public String getCachedPin() {
        String key = getCachedUser() + "pin";
        return sharedPreferences.getString(key, null);
    }

    public void clearCache(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("username");
        editor.remove("pin");
        editor.apply();
    }

    public void clearUser(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("username");
        editor.apply();
    }

    public boolean isUserCached() {
        return sharedPreferences.contains("username");
    }

    public boolean isPinCached() { return sharedPreferences.contains(getCachedUser() + "pin"); }

    private void putString(String key, String val){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, val);
        editor.apply();
    }
}
