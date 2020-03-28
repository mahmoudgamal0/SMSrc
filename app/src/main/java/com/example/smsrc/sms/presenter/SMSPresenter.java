package com.example.smsrc.sms.presenter;

import com.example.smsrc.commands.presenter.CommandPresenter;
import com.example.smsrc.sms.handlers.SMSSender;
import com.example.smsrc.sms.model.SMS;

public class SMSPresenter {
    private SMSSender sender;
    private CommandPresenter commandPresenter;

    public SMSPresenter(){
        this.sender = new SMSSender();
        this.commandPresenter = new CommandPresenter();
    }

    public void sendSMS(SMS sms, String phoneNumber){
        // TODO call sender
    }

}
