package com.example.smsrc.permissions.model;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import com.example.smsrc.ResultCodes;
import com.example.smsrc.permissions.interfaces.Handler;

import java.util.ArrayList;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class CompatPermissionsHandler implements Handler {

    private PermissionChain chain;
    private Activity activity;
    private Context context;
    private String[] permissions;
    private Handler nextHandler;

    public CompatPermissionsHandler(PermissionChain chain, Activity activity) {
        this.chain = chain;
        this.activity = activity;
        this.context = activity.getApplicationContext();
        this.permissions = new String[]{
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.SEND_SMS,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECEIVE_SMS
        };
    }

    @Override
    public Handler next() {
        return this.nextHandler;
    }

    @Override
    public void handle() {
        ArrayList<String> requestedPermissions = new ArrayList<>();
        for(String p: permissions){
            if(ContextCompat.checkSelfPermission(context, p) != PackageManager.PERMISSION_GRANTED)
                requestedPermissions.add(p);
        }
        if (requestedPermissions.size() == 0) {
            chain.next();
        } else {
            ActivityCompat.requestPermissions(activity, requestedPermissions.toArray(new String[0]), ResultCodes.REQUEST_READ_PHONE_STATE);
        }
    }

    @Override
    public void setNext(Handler handler) {
        this.nextHandler = handler;
    }
}
