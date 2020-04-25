package com.example.smsrc.commands.factory;

import android.content.Context;

import com.example.smsrc.commands.model.DisableCameraCommand;
import com.example.smsrc.commands.model.EncryptStorageCommand;
import com.example.smsrc.commands.model.LockPhoneCommand;
import com.example.smsrc.commands.interfaces.Command;
import com.example.smsrc.commands.interfaces.CommandsContract;
import com.example.smsrc.commands.model.ModeSwitchCommand;
import com.example.smsrc.commands.model.PlaySoundCommand;
import com.example.smsrc.commands.model.WipeDataCommand;

public class CommandFactory {
    public static Command getCommand(String commandName, Context context){
        switch (commandName) {
            case CommandsContract.MODE_SWITCH:
                return new ModeSwitchCommand(context);
            case CommandsContract.PLAY_SOUND:
                return new PlaySoundCommand(context);
            case CommandsContract.LOCK_PHONE:
                return new LockPhoneCommand();
            case CommandsContract.WIPE_DATA:
                return new WipeDataCommand();
            case CommandsContract.ENCRYPT_STORAGE:
                return new EncryptStorageCommand();
            case CommandsContract.DISABLE_CAMERA:
                return new DisableCameraCommand();
            default:
                return null;
        }
    }
}
