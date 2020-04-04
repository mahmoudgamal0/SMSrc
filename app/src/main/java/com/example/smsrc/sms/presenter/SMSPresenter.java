package com.example.smsrc.sms.presenter;

import android.app.Activity;

import com.example.smsrc.commands.presenter.CommandPresenter;
import com.example.smsrc.sms.handlers.SMSSender;
import com.example.smsrc.sms.model.SMS;

public class SMSPresenter {
    private SMSSender sender;
    private CommandPresenter commandPresenter;
    private Activity activity;

    public SMSPresenter(Activity activity){
        this.sender = new SMSSender(activity);
        this.activity = activity;
        this.commandPresenter = new CommandPresenter();
    }

    public void sendSMS(SMS sms, String phoneNumber){
        sms.setDstPhoneNumber(phoneNumber);
        sender.send(sms);
    }

}
