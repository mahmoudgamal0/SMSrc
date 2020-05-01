package com.example.smsrc.permissions.model;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.example.smsrc.ResultCodes;
import com.example.smsrc.deviceAdmin.DeviceAdminActivity;
import com.example.smsrc.permissions.interfaces.Handler;

public class DeviceAdminHandler implements Handler {

    private PermissionChain chain;
    private Activity activity;
    private DevicePolicyManager dpm;
    private ComponentName policyAdmin;
    private Handler nextHandler;

    public DeviceAdminHandler(PermissionChain chain, Activity activity){
        this.chain = chain;
        this.activity = activity;
        this.dpm = (DevicePolicyManager)activity.getSystemService(Context.DEVICE_POLICY_SERVICE);
        this.policyAdmin = new ComponentName(activity.getApplicationContext(), DeviceAdminActivity.DeviceAdminActivityReceiver.class);
    }

    @Override
    public Handler next() {
        return this.nextHandler;
    }

    @Override
    public void handle() {
        if(dpm.isAdminActive(policyAdmin)){
            this.chain.next();
        } else {
            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, policyAdmin);
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "Some Text");
            activity.startActivityForResult(intent, ResultCodes.REQUEST_CODE_DEVICE_ADMIN);
        }
    }

    @Override
    public void setNext(Handler handler) {
        this.nextHandler = handler;
    }
}
