package com.example.smsrc.sms.model;

public class SMS {
    private String credentials;
    private String command;
    private String dstPhoneNumber;
    public SMS(String credentials, String command, String phoneNumber){
        this.credentials = credentials;
        this.command = command;
        this.dstPhoneNumber = phoneNumber;
    }

    public String getCredentials() {
        return credentials;
    }

    public String getCommand() {
        return command;
    }

    public String getDstPhoneNumber() { return dstPhoneNumber; }
}
