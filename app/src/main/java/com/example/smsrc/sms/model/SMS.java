package com.example.smsrc.sms.model;

public class SMS {
    private String credentials;
    private String command;
    private String randomness;
    private String dstPhoneNumber;
    private String body;

    public SMS(String credentials, String command, String randomness){
        this.credentials = credentials;
        this.command = command;
        this.randomness = randomness;
    }

    public SMS(String credentials, String command, String randomness, String body){
        this(credentials, command, randomness);
        this.body = body;
    }

    public void setDstPhoneNumber(String phoneNumber){
        this.dstPhoneNumber = phoneNumber;
    }

    public String getCredentials() { return credentials; }

    public String getCommand() { return command; }

    public String getRandomness() { return randomness; }

    public String getDstPhoneNumber() { return dstPhoneNumber; }

    public String getBody() { return body; }
}
