package com.example.smsrc.permissions.model;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.example.smsrc.ResultCodes;
import com.example.smsrc.permissions.interfaces.Handler;

public class NotificationManagerHandler implements Handler {

    private PermissionChain chain;
    private Activity activity;
    private NotificationManager nm;
    private Handler nextHandler;

    public NotificationManagerHandler(PermissionChain chain, Activity activity) {
        this.chain = chain;
        this.activity = activity;
        this.nm = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Override
    public Handler next() {
        return this.nextHandler;
    }

    @Override
    public void handle() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(nm.isNotificationPolicyAccessGranted()){
                chain.next();
            } else {
                Intent intent = new Intent(android.provider.Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                activity.startActivityForResult(intent, ResultCodes.REQUEST_ACTIVITY_NOTIFICATION);
            }
        }
    }

    @Override
    public void setNext(Handler handler) {
        this.nextHandler = handler;
    }
}
