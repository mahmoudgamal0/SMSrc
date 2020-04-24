package com.example.smsrc.commands.model;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;

import com.example.smsrc.deviceAdmin.DeviceAdminActivity;

public class ChangePinCodeCommand implements Command {

    private Context context;
    private DevicePolicyManager dpm;
    private ComponentName policyAdmin;

    public ChangePinCodeCommand(Context context) {
        this.context = context;
        this.dpm = (DevicePolicyManager)context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        policyAdmin = new ComponentName(context, DeviceAdminActivity.DeviceAdminActivityReceiver.class);
    }

    @Override
    public void execute(String[] args) {
        if(args == null) {
            // TODO to be handled
            return;
        } else if (!dpm.isAdminActive(policyAdmin)) {
            throw new RuntimeException("Cannot perform this command since device admin is not activated");
        }

        String newPin = args[0];
        dpm.setPasswordExpirationTimeout(policyAdmin, 1);
        dpm.resetPassword(newPin, DevicePolicyManager.RESET_PASSWORD_REQUIRE_ENTRY);
        dpm.lockNow();
    }
}
