package com.example.smsrc.commands.model;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;

import com.example.smsrc.commands.interfaces.Command;
import com.example.smsrc.deviceAdmin.DeviceAdminActivity;

public class WipeDataCommand implements Command {

    private DevicePolicyManager dpm;
    private ComponentName policyAdmin;

    public WipeDataCommand(Context context) {
        this.dpm = (DevicePolicyManager)context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        this.policyAdmin = new ComponentName(context, DeviceAdminActivity.DeviceAdminActivityReceiver.class);
    }

    @Override
    public void execute(String[] args) {
        if (!dpm.isAdminActive(policyAdmin)) {
            throw new RuntimeException("Cannot perform this command since device admin is not activated");
        }

        dpm.wipeData(0);
    }
}
