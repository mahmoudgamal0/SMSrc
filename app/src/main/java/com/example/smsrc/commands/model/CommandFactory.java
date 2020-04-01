package com.example.smsrc.commands.model;

import android.content.Context;

public class CommandFactory {
    public static Command getCommand(String commandName, Context context){
        if(commandName.equals("ModeSwitch")){
            return new ModeSwitchCommand();
        }
        else if(commandName.equals("PlaySound")){
            return new PlaySoundCommand(context);
        }else if(commandName.equals("ChangePinCode")){
            return new ChangePinCodeCommand();
        }else{
            return null;
        }
    }
}
