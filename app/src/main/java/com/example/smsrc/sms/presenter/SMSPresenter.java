package com.example.smsrc.sms.presenter;

import com.example.smsrc.commands.presenter.CommandPresenter;
import com.example.smsrc.sms.handlers.SMSExecutor;
import com.example.smsrc.sms.handlers.SMSListener;
import com.example.smsrc.sms.handlers.SMSSender;
import com.example.smsrc.sms.model.SMS;

public class SMSPresenter {
    private SMSListener listener;
    private SMSSender sender;
    private SMSExecutor executor;
    private CommandPresenter commandPresenter;

    public SMSPresenter(){
        this.listener = new SMSListener(this);
        this.sender = new SMSSender();
        this.executor = new SMSExecutor();
        this.commandPresenter = new CommandPresenter();
    }

    public void sendSMS(SMS sms, String phoneNumber){
        // TODO call sender
    }

    public void executeSMS(SMS sms){
        // TODO wrap command
    }

}
