package com.example.smsrc.commands.model;

import android.content.Context;

import com.example.smsrc.commands.interfaces.Command;
import com.example.smsrc.sms.model.LifeLineSMS;
import com.example.smsrc.sms.model.SMS;
import com.example.smsrc.sms.presenter.SMSPresenter;

public class IsAliveCommand implements Command {
    private SMSPresenter smsPresenter;

    public IsAliveCommand(Context context) {
        this.smsPresenter = new SMSPresenter(context);
    }

    @Override
    public void execute(String[] args) {
        if (args == null || args.length == 0){
            return;
        }
        String body = "Phone Is ON";
        String destPhone = args[0];
        SMS sms = new LifeLineSMS(body);
        smsPresenter.sendSMS(sms, destPhone);
    }
}
