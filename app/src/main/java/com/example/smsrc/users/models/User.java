package com.example.smsrc.users.models;

import java.io.Serializable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String username;
    private String passcode;
    private String authLevel;

    public User(){

    }

    public User(String username, String passcode, String authlevel) {
        this.username = username;
        this.passcode = passcode;
        this.authLevel = authlevel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasscode() {
        return passcode;
    }

    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }

    public String getAuthLevel() {
        return authLevel;
    }

    public void setAuthLevel(String authLevel) {
        this.authLevel = authLevel;
    }
}
