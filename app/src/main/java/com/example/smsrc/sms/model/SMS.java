package com.example.smsrc.sms.model;

public class SMS {
    private String credentials;
    private String command;
    private String dstPhoneNumber;
    public SMS(String credentials, String command){
        this.credentials = credentials;
        this.command = command;
    }

    public void setDstPhoneNumber(String phoneNumber){
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
