package com.example.smsrc.sms.model;

public class SMS {
    private String credentials;
    private String command;

    public SMS(String credentials, String command){
        this.credentials = credentials;
        this.command = command;
    }

    public String getCredentials() {
        return credentials;
    }

    public String getCommand() {
        return command;
    }
}
