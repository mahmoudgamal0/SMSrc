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

import com.example.smsrc.R;
import com.example.smsrc.commands.interfaces.CommandsContract;

public class CommandFragment extends Fragment {

    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_command, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        initListeners(view);
    }


    private void initListeners(View view) {
        Button playSoundBtn = view.findViewById(R.id.play_sound_button);
        Button modeSwitchBtn = view.findViewById(R.id.change_mode_button);
        Button lockPhoneBtn = view.findViewById(R.id.lock_phone_button);
        Button wipeDataBtn = view.findViewById(R.id.wipe_data_button);
        Button encryptStorageBtn = view.findViewById(R.id.encrypt_storage_button);
        Button disableCameraBtn = view.findViewById(R.id.disable_camera_button);

        playSoundBtn.setOnClickListener(e->navigateToNext(CommandsContract.PLAY_SOUND));
        modeSwitchBtn.setOnClickListener(e->navigateToNext(CommandsContract.MODE_SWITCH));
        lockPhoneBtn.setOnClickListener(e->navigateToNext(CommandsContract.LOCK_PHONE));
        wipeDataBtn.setOnClickListener(e->navigateToNext(CommandsContract.WIPE_DATA));
        encryptStorageBtn.setOnClickListener(e->navigateToNext(CommandsContract.ENCRYPT_STORAGE));
        disableCameraBtn.setOnClickListener(e->navigateToNext(CommandsContract.DISABLE_CAMERA));
    }

    private void navigateToNext(String command) {
        Bundle bundle = new Bundle();
        bundle.putString("command", command);
        navController.navigate(R.id.action_commandFragment_to_outboundInformationFragment, bundle);
    }
}
