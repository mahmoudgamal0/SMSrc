package com.example.smsrc.deviceAdmin;
import android.app.Activity;
import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import android.widget.Toast;

import java.io.Serializable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DeviceAdminActivity extends Activity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static class DeviceAdminActivityReceiver extends DeviceAdminReceiver implements Serializable {
        void showToast(Context context, String msg) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onEnabled(Context context, Intent intent) {
            showToast(context, "Enabled");
        }

        @Override
        public CharSequence onDisableRequested(Context context, Intent intent) {
            return "Are you sure you want to disable this application?";
        }

        @Override
        public void onDisabled(Context context, Intent intent) {
            showToast(context,"Disabled");
        }

        @Override
        public void onPasswordChanged(@NonNull Context context, @NonNull Intent intent, @NonNull UserHandle user) {
            showToast(context, "Password Changed");
        }
    }
}