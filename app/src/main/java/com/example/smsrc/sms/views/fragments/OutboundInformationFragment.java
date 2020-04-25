package com.example.smsrc.sms.views.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.smsrc.MainActivity;
import com.example.smsrc.R;
import com.example.smsrc.permissions.utils.Crypto;
import com.example.smsrc.sms.model.SMS;
import com.example.smsrc.sms.presenter.SMSPresenter;
import com.google.android.material.textfield.TextInputEditText;

public class OutboundInformationFragment extends Fragment {

    private NavController navController;
    private SMSPresenter smsPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_outbound_information, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        smsPresenter = new SMSPresenter(getActivity());
        initListeners(view);
    }

    private void initListeners(View view) {
        Button sendButton = view.findViewById(R.id.out_send);
        sendButton.setOnClickListener(e->{
            sendSms(view);
            navController.popBackStack();
        });
    }

    private void sendSms(View view) {
        String phoneNumber = ((TextInputEditText)view.findViewById(R.id.out_number)).getText().toString();
        String username = ((TextInputEditText)view.findViewById(R.id.out_username)).getText().toString();
        String password = ((TextInputEditText)view.findViewById(R.id.out_password)).getText().toString();

        if(
                phoneNumber.length() == 0 ||
                username.length() == 0 ||
                password.length() == 0
        ) {

            Log.e("PlaySoundFragment", "empty fields");
            Toast.makeText(getContext() ,"please enter all the fields before sending",Toast.LENGTH_LONG).show();
            return;
        }

        Log.i("PlaySoundFragment", "creating SMS to send");

        String encryptedPassword = Crypto.encrypt(password);
        String randomness = Crypto.generateRandomness();
        String credentials = Crypto.encrypt(username + randomness + encryptedPassword);

        Bundle bundle = getArguments();
        String commandFromBundle = bundle.getString("command");
        String command = Crypto.encrypt(commandFromBundle + randomness);

        SMS sms = new SMS(credentials, command, randomness);

        Log.i("PlaySoundFragment", "created SMS");
        try {
            smsPresenter.sendSMS(sms, phoneNumber);
        } catch (Exception e) {
            Toast.makeText(getContext() ,e.getMessage() ,Toast.LENGTH_LONG).show();
            Log.e("PlaySoundFragment", e.getMessage());
        }
        Log.i("PlaySoundFragment", "sent SMS");
    }
}
