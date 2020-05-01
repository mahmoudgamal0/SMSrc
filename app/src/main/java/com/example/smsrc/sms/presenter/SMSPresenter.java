package com.example.smsrc.sms.presenter;

import android.content.Context;
import android.util.Log;
import com.example.smsrc.sms.handlers.SMSSender;
import com.example.smsrc.sms.model.SMS;

public class SMSPresenter {
    private SMSSender sender;

    public SMSPresenter(Context context){
        this.sender = new SMSSender(context);
    }

    public void sendSMS(SMS sms, String phoneNumber){
        sms.setDstPhoneNumber(phoneNumber);

        Log.i("SMSPresenter", "Sending message to "+phoneNumber);
        sender.send(sms);
    }

}
