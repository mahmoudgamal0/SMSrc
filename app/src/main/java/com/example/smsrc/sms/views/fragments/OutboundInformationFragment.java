package com.example.smsrc.sms.views.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.smsrc.MainActivity;
import com.example.smsrc.R;
import com.example.smsrc.commands.model.CommandsContract;
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
        MainActivity mainActivity = (MainActivity)this.getActivity();
        mainActivity.hideNavigation();
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

        String encryptedPassword = Crypto.encrypt(password);
        String randomness = Crypto.generateRandomness();
        String credentials = Crypto.encrypt(username + randomness + encryptedPassword);

        Bundle bundle = getArguments();
        String commandFromBundle = bundle.getString("command");
        String command = Crypto.encrypt(commandFromBundle + randomness);

        SMS sms = new SMS(credentials, command, randomness);
        smsPresenter.sendSMS(sms, phoneNumber);
    }
}
