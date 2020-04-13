package com.example.smsrc.commands.model;

import android.content.Context;
import android.media.AudioManager;

public class ModeSwitchCommand implements Command {

    private Context context;

    public ModeSwitchCommand(Context context){
        this.context = context;
    }
    @Override
    public void execute(String[] args) {
        AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
    }
}
