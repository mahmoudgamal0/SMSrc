package com.example.smsrc.restore.model;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;

import com.example.smsrc.deviceAdmin.DeviceAdminActivity;


public class RestoreStateModel {

    private DevicePolicyManager dpm;
    private ComponentName policyAdmin;

    public RestoreStateModel(Context context){
        this.dpm = (DevicePolicyManager)context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        this.policyAdmin = new ComponentName(context, DeviceAdminActivity.DeviceAdminActivityReceiver.class);
    }

    public void restorePhone() {
        // TODO
    }
}
