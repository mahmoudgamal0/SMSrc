package com.example.smsrc.sms.model;

public class SMS {
    private String credentials;
    private String command;
    private String randomness;

    public SMS(String credentials, String command, String randomness){
        this.credentials = credentials;
        this.command = command;
        this.randomness = randomness;
    }

    public String getCredentials() {
        return credentials;
    }

    public String getCommand() {
        return command;
    }

    public String getRandomness() {
        return randomness;
    }
}
