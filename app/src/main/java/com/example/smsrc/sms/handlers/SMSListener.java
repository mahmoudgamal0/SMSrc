package com.example.smsrc.sms.handlers;

import com.example.smsrc.sms.presenter.SMSPresenter;

public class SMSListener {

    private SMSExecutor executor;
    public SMSListener(SMSExecutor executor){
        this.executor = executor;
    }

    public void newMessageReceived(String incomingMessage){
        // TODO parse
    }
}
