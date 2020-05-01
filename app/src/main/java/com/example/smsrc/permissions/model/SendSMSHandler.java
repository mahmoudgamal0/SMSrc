package com.example.smsrc.permissions.model;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import com.example.smsrc.ResultCodes;
import com.example.smsrc.permissions.interfaces.Handler;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class SendSMSHandler implements Handler {

    private PermissionChain chain;
    private Activity activity;
    private Context context;
    private String permission;
    private Handler nextHandler;

    public SendSMSHandler(PermissionChain chain, Activity activity) {
        this.chain = chain;
        this.activity = activity;
        this.context = activity.getApplicationContext();
        this.permission = Manifest.permission.SEND_SMS;
    }

    @Override
    public Handler next() {
        return this.nextHandler;
    }

    @Override
    public void handle() {
        if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
            chain.next();
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{permission}, ResultCodes.REQUEST_SEND_SMS);
        }
    }

    @Override
    public void setNext(Handler handler) {
        this.nextHandler = handler;
    }
}
