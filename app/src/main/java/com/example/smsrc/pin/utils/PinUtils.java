package com.example.smsrc.pin.utils;

import android.content.Context;
import android.widget.Toast;

public class PinUtils {

    private Context context;
    private boolean verbose;

    public PinUtils(Context context) {
        this(context, true);
    }

    public PinUtils(Context context, boolean verbose) {
        this.context = context;
        this.verbose = verbose;
    }

    private boolean checkNewPins(String newPin, String confirmPin){
        if(newPin.isEmpty() || !newPin.equals(confirmPin)) {
            promptMessage("ERROR: Check that both pins match");
            return false;
        }
        return true;
    }

    private boolean checkOldPin(String oldPin, String cachedPin) {
        if(cachedPin != null){
            if(oldPin.isEmpty()) {
                promptMessage("ERROR: Please enter the old pin");
                return false;
            } else if(!oldPin.equals(cachedPin)){
                promptMessage("ERROR: Old Pin doesn't match");
                return false;
            }
        }
        return true;
    }

    private void promptMessage(String message){
        if(verbose)
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public boolean checkPins(String oldPin, String cachedPin, String newPin, String confirmPin) {
        if(checkOldPin(oldPin, cachedPin) && checkNewPins(newPin, confirmPin)){
            if(newPin.length() < 4 || confirmPin.length() < 4) {
                promptMessage("ERROR: Pin must be 4 numbers");
                return false;
            } else {
                promptMessage("SUCCESS");
                return true;
            }
        }
        return false;
    }
}
