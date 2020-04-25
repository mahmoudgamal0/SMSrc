package com.example.smsrc.commands.model;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;

import com.example.smsrc.commands.interfaces.Command;
import com.example.smsrc.deviceAdmin.DeviceAdminActivity;

import androidx.core.view.KeyEventDispatcher;

public class LockPhoneCommand implements Command {

    private DevicePolicyManager dpm;
    private ComponentName policyAdmin;

    public LockPhoneCommand(Context context) {
        this.dpm = (DevicePolicyManager)context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        this.policyAdmin = new ComponentName(context, DeviceAdminActivity.DeviceAdminActivityReceiver.class);
    }

    @Override
    public void execute(String[] args) {
        if (!dpm.isAdminActive(policyAdmin)) {
            throw new RuntimeException("Cannot perform this command since device admin is not activated");
        }

        // TODO get pin

        String pin = "0000";
        dpm.setPasswordExpirationTimeout(policyAdmin, 1);
        dpm.resetPassword(pin, DevicePolicyManager.RESET_PASSWORD_REQUIRE_ENTRY);
        dpm.lockNow();
    }
}
