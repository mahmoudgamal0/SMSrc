package com.example.smsrc.commands.model;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;

import com.example.smsrc.commands.interfaces.Command;
import com.example.smsrc.deviceAdmin.DeviceAdminActivity;
import androidx.annotation.RequiresApi;

public class WipeDataCommand implements Command {

    private DevicePolicyManager dpm;
    private ComponentName policyAdmin;

    public WipeDataCommand(Context context) {
        this.dpm = (DevicePolicyManager)context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        this.policyAdmin = new ComponentName(context, DeviceAdminActivity.DeviceAdminActivityReceiver.class);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    public void execute(String[] args) {
        if (!dpm.isAdminActive(policyAdmin)) {
            throw new RuntimeException("Cannot perform this command since device admin is not activated");
        }

        dpm.wipeData(DevicePolicyManager.WIPE_SILENTLY);
        dpm.wipeData(DevicePolicyManager.WIPE_EXTERNAL_STORAGE);
        dpm.wipeData(0);
    }
}
