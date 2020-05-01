package com.example.smsrc.sms.handlers;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;
import com.example.smsrc.sms.model.SMS;

public class SMSSender {
    private static final String SENT = "SMS_SENT";
    private static final String DELIVERED = "SMS_DELIVERED";
    private Context context;

    public SMSSender(Context context){
        this.context = context;
    }

    public void send(SMS sms){
        Log.i("SMSSender", "Attempt to send SMS");
        if (sms == null || sms.getDstPhoneNumber().isEmpty()){
            return;
        }
        PendingIntent sentPI = PendingIntent.getBroadcast(context, 0, new Intent(SENT), 0);
        PendingIntent deliveredPI = PendingIntent.getBroadcast(context, 0, new Intent(DELIVERED), 0);

        context.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                if (getResultCode() == Activity.RESULT_OK) {

                    Log.d("SMSSender", "Send successful");
                    Toast.makeText(context,"sending successfully",Toast.LENGTH_SHORT).show();
            }}
        }, new IntentFilter(SENT));

        context.registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                if(getResultCode() == Activity.RESULT_OK){
                    Log.d("SMSSender", "delivery successful");
                    Toast.makeText(context,"delivered successfully",Toast.LENGTH_SHORT).show();
            }}
        }, new IntentFilter(DELIVERED));

        SmsManager smsManger = SmsManager.getDefault();
        String text = sms.getMessage();
        smsManger.sendTextMessage(sms.getDstPhoneNumber(), null, text, sentPI, deliveredPI);
    }
}
