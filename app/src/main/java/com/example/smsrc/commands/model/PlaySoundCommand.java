package com.example.smsrc.commands.model;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;

import com.example.smsrc.MainActivity;

import java.util.Timer;
import java.util.TimerTask;

public class PlaySoundCommand implements Command {

    private Context context;
    public PlaySoundCommand(Context context){
        this.context = context;
    }

    @Override
    public void execute(String[] args) {
        AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        Ringtone ringtone = RingtoneManager.getRingtone(context,
                RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));
        ringtone.play();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                ringtone.stop();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 10000);
    }
}
