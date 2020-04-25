package com.example.smsrc.requester;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class Requester {

    private Context context;
    private Activity activity;

    private static class RequesterPermissions {
        static int READ_PHONE_STATE_PERMISSION_CODE = 0;
        static String READ_PHONE_STATE_PERMISSION = "READ_PHONE_STATE";
    }

    public Requester(Activity activity){
        this.activity = activity;
        this.context = activity.getApplicationContext();
    }

    public void requestPermission(RequesterCallback callback, String permission, Object[] args) {

        Log.i("Requester", "requesting permission"); //TODO permission name

        RequesterHandler handler = RequesterHandler.getHandler();
        handler.registerReceiver(callback, args);

        if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{permission}, this.getPermissionCode(permission));
        } else {
            handler.callbackSuccess();
        }
    }

    private int getPermissionCode(String permission){
        if(permission.equals(RequesterPermissions.READ_PHONE_STATE_PERMISSION))
            return RequesterPermissions.READ_PHONE_STATE_PERMISSION_CODE;
        return 0;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        RequesterHandler handler = RequesterHandler.getHandler();

        Log.d("Requester", "Permission request result received"); //TODO permission name

        if(requestCode == RequesterPermissions.READ_PHONE_STATE_PERMISSION_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                handler.callbackSuccess();
            } else {
                handler.callbackFail();
            }
        }
    }


}
