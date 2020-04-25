package com.example.smsrc.commands.model;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;

import com.example.smsrc.cache.CacheManager;
import com.example.smsrc.commands.interfaces.Command;
import com.example.smsrc.deviceAdmin.DeviceAdminActivity;

public class LockPhoneCommand implements Command {

    private DevicePolicyManager dpm;
    private ComponentName policyAdmin;
    private Context context;
    public LockPhoneCommand(Context context) {
        this.context = context;
        this.dpm = (DevicePolicyManager)context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        this.policyAdmin = new ComponentName(context, DeviceAdminActivity.DeviceAdminActivityReceiver.class);
    }

    @Override
    public void execute(String[] args) {
        if (!dpm.isAdminActive(policyAdmin)) {
            throw new RuntimeException("Cannot perform this command since device admin is not activated");
        }

        String pin = new CacheManager(context).getCachedPin();
        if(pin != null){
            dpm.resetPassword(pin, DevicePolicyManager.RESET_PASSWORD_REQUIRE_ENTRY);
        }

        dpm.lockNow();
    }
}
