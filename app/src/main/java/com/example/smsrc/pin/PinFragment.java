package com.example.smsrc.pin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.smsrc.R;
import com.example.smsrc.cache.CacheManager;
import com.google.android.material.textfield.TextInputEditText;

public class PinFragment extends Fragment {

    private CacheManager cacheManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cacheManager = new CacheManager(this.getContext());
        if(cacheManager.isPinCached()){
            view.findViewById(R.id.old_pin).setVisibility(View.VISIBLE);
        }
        initListeners(view);
    }

    private void initListeners(View view) {
        Button submitBtn = view.findViewById(R.id.submit_btn);
        submitBtn.setOnClickListener(e->{
            String oldPin = ((TextInputEditText)view.findViewById(R.id.old_pin)).getText().toString();
            String newPin = ((TextInputEditText)view.findViewById(R.id.new_pin)).getText().toString();
            String confirmPin = ((TextInputEditText)view.findViewById(R.id.confirm_pin)).getText().toString();
            String cachedPin = cacheManager.getCachedPin();

            if(checkOldPin(oldPin, cachedPin) && checkNewPins(newPin, confirmPin)){
                cacheManager.cachePin(newPin);
                promptMessage("SUCCESS");
            }
        });
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
        Toast.makeText(this.getContext(), message, Toast.LENGTH_LONG).show();
    }
}
