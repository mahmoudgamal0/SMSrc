package com.example.smsrc.sms.handlers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import com.example.smsrc.sms.model.SMS;
import com.example.smsrc.users.dals.UserRepository;

public class SMSListener extends BroadcastReceiver {

    private SMSExecutor executor;
    public SMSListener(){
        this.executor = new SMSExecutor();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){

            Log.i("SMSListener", "Received SMS");
            Bundle bundle = intent.getExtras();
            if (bundle != null){
                try{
                    // Parse Message
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    SmsMessage msg = SmsMessage.createFromPdu((byte[])pdus[0]);
                    String msg_from = msg.getOriginatingAddress();
                    String text = msg.getMessageBody();
                    String[] msgBody = text.split("\n");

                    Log.d("SMSListener", "Check if SMS to this app");

                    // FIXME apply another metric to discover app-specific messages
                    if(msgBody.length == 3){
                        // Execute Message
                        SMS sms = new SMS(msgBody[0], msgBody[1], msgBody[2]);
                        this.executor.setRepository(UserRepository.getUserRepository(context));
                        sms.setDstPhoneNumber(msg_from);
                        this.executor.setContext(context);
                        Log.d("SMSListener", "Pass to executor");
                        this.executor.execute(sms);
                    }

                } catch(Exception e){
                    Log.e("SMSListener", e.getMessage());
                }
            }
        }
    }
}
