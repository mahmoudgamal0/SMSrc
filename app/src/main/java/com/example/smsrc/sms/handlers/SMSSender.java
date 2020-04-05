package com.example.smsrc.sms.handlers;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsManager;
import android.widget.Toast;

import com.example.smsrc.requester.Requester;
import com.example.smsrc.requester.RequesterCallback;
import com.example.smsrc.sms.model.SMS;

public class SMSSender extends RequesterCallback {
    private static final String SENT = "SMS_SENT";
    private static final String DELIVERED = "SMS_DELIVERED";
    private Context context;
    private Activity activity;
    public SMSSender(Activity activity){
        this.activity = activity;
        this.context = activity.getApplicationContext();
    }

    private void _send(SMS sms){
        if (sms == null || sms.getDstPhoneNumber().isEmpty()){
            return;
        }
        PendingIntent sentPI = PendingIntent.getBroadcast(context, 0, new Intent(SENT), 0);
        PendingIntent deliveredPI = PendingIntent.getBroadcast(context, 0, new Intent(DELIVERED), 0);

        context.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                if (getResultCode() == Activity.RESULT_OK)
                    Toast.makeText(context,"sending successfully",Toast.LENGTH_SHORT).show();
            }
        }, new IntentFilter(SENT));

        context.registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                if(getResultCode() == Activity.RESULT_OK)
                    Toast.makeText(context,"delivered successfully",Toast.LENGTH_SHORT).show();
            }
        }, new IntentFilter(DELIVERED));

        SmsManager smsManger = SmsManager.getDefault();
        String text = sms.getCredentials()+"\n"+sms.getCommand()+"\n"+ sms.getRandomness();
        smsManger.sendTextMessage(sms.getDstPhoneNumber(), null, text, sentPI, deliveredPI);
    }

    public void send(SMS sms) {
        Requester requester = new Requester(activity);
        requester.requestPermission(this, Manifest.permission.READ_PHONE_STATE, new Object[]{sms});
    }

    @Override
    public void onSuccess(Object[] args) {
        if(args.length > 0 && args[0] instanceof SMS){
            this._send((SMS)args[0]);
        }
    }
}
