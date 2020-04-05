package com.example.smsrc.commands.views.fragments;

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
import com.example.smsrc.users.dals.UserRepository;
import com.example.smsrc.users.models.User;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class PlaySoundFragment extends Fragment {

    private NavController navController;
    private SMSPresenter smsPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_play_sound, container, false);
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
        Button sendButton = view.findViewById(R.id.play_sound_send);
        sendButton.setOnClickListener(e->{
            sendSms(view);
            navController.popBackStack();
        });
    }

    private void sendSms(View view) {
        String phoneNumber = ((TextInputEditText)view.findViewById(R.id.play_sound_number)).getText().toString();
        String username = ((TextInputEditText)view.findViewById(R.id.play_sound_username)).getText().toString();
        String password = ((TextInputEditText)view.findViewById(R.id.play_sound_password)).getText().toString();

        String randomness = Crypto.generateRandomness();
        UserRepository userRepository = UserRepository.getUserRepository(view.getContext());
        List<User> users = userRepository.getUserByUsername(username);

        String credentials = Crypto.encrypt(username + users.get(0).getPasscode(), randomness);
        String command = Crypto.encrypt(CommandsContract.PLAY_SOUND, randomness);

        SMS sms = new SMS(credentials, command, randomness);

        smsPresenter.sendSMS(sms, phoneNumber);
    }


}
