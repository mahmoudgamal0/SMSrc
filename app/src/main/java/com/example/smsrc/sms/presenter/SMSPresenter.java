package com.example.smsrc.sms.presenter;

import android.app.Activity;
import android.util.Log;

import com.example.smsrc.commands.presenter.CommandPresenter;
import com.example.smsrc.sms.handlers.SMSSender;
import com.example.smsrc.sms.model.SMS;

public class SMSPresenter {
    private SMSSender sender;

    public SMSPresenter(Activity activity){
        this.sender = new SMSSender(activity);
    }

    public void sendSMS(SMS sms, String phoneNumber){
        sms.setDstPhoneNumber(phoneNumber);
        Log.i("SMSPresenter", "Sending message to "+phoneNumber);
        sender.send(sms);
    }

}
