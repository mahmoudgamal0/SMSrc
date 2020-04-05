package com.example.smsrc.sms.presenter;

import android.content.Context;

import com.example.smsrc.commands.presenter.CommandPresenter;
import com.example.smsrc.sms.handlers.SMSSender;
import com.example.smsrc.sms.model.SMS;

public class SMSPresenter {
    private SMSSender sender;
    private CommandPresenter commandPresenter;
    private Context context;
    public SMSPresenter(Context _context){
        this.sender = new SMSSender(_context);
        this.context = _context;
        this.commandPresenter = new CommandPresenter();
    }

    public void sendSMS(SMS sms, String phoneNumber){
        sms.setDstPhoneNumber(phoneNumber);
        sender.send(sms);
    }

}
