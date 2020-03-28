package com.example.smsrc.sms.handlers;

import com.example.smsrc.sms.presenter.SMSPresenter;

public class SMSListener {

    private SMSPresenter presenter;
    public SMSListener(SMSPresenter presenter){
        this.presenter = presenter;
    }

    public void newMessageReceived(String incomingMessage){
        // TODO parse
        // TODO call presenter
    }
}
