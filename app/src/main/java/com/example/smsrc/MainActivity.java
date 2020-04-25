package com.example.smsrc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import com.example.smsrc.requester.Requester;
import com.example.smsrc.deviceAdmin.DeviceAdminActivity;

public class MainActivity extends AppCompatActivity {

    private DevicePolicyManager dpm;
    private NotificationManager nm;
    private ComponentName policyAdmin;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dpm = (DevicePolicyManager)getSystemService(Context.DEVICE_POLICY_SERVICE);
        nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        policyAdmin = new ComponentName(this.getApplicationContext(), DeviceAdminActivity.DeviceAdminActivityReceiver.class);

        startResultChain();
    }

    public void hideNavigation(){
        findViewById(R.id.home_bottom_nav).setVisibility(View.GONE);
    }

    public void showNavigation(){
        findViewById(R.id.home_bottom_nav).setVisibility(View.VISIBLE);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void startResultChain() {
        if (!nm.isNotificationPolicyAccessGranted()) {
            checkNotificationActivity();
        } else if(!dpm.isAdminActive(policyAdmin)){
            checkDeviceAdmin();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkNotificationActivity() {
        if (!nm.isNotificationPolicyAccessGranted()) {
            Intent intent = new Intent(android.provider.Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
            startActivityForResult(intent, ResultCodes.REQUEST_ACTIVITY_NOTIFICATION);
        }
    }

    private void checkDeviceAdmin() {
        if(!dpm.isAdminActive(policyAdmin)){
            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, policyAdmin);
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "Some Text");
            startActivityForResult(intent, ResultCodes.REQUEST_CODE_DEVICE_ADMIN);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Requester requester = new Requester(this);
        requester.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == ResultCodes.REQUEST_ACTIVITY_NOTIFICATION){
            checkDeviceAdmin();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
