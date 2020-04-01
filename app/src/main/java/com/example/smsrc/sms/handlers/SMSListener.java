package com.example.smsrc.sms.handlers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import com.example.smsrc.sms.model.SMS;

public class SMSListener extends BroadcastReceiver {

    private SMSExecutor executor;
    public SMSListener(){
        this.executor = new SMSExecutor();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
            Bundle bundle = intent.getExtras();
            if (bundle != null){
                try{
                    // Parse Message
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    SmsMessage msg = SmsMessage.createFromPdu((byte[])pdus[0]);
                    String msg_from = msg.getOriginatingAddress(); // Can be used later
                    String[] msgBody = msg.getMessageBody().split("\n");

                    // FIXME apply another metric to discover app-specific messages
                    if(msgBody.length == 2){
                        // Execute Message
                        SMS sms = new SMS(msgBody[0], msgBody[1]);
                        sms.setDstPhoneNumber(msg_from);
                        this.executor.execute(sms);
                    }

                } catch(Exception e){
                    Log.d("Exception caught", e.getMessage());
                }
            }
        }
    }
}
