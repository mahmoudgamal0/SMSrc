package com.example.smsrc.sms.model;

public class LifeLineSMS extends SMS {

    private String body;
    public LifeLineSMS(String body){
        super(null, null, null);
        this.body = body;
    }

    @Override
    public String getMessage() {
        return body;
    }
}
