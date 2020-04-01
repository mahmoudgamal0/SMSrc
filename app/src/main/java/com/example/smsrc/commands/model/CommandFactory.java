package com.example.smsrc.commands.model;

public class CommandFactory {
    public static Command getCommand(String commandName){
        if(commandName.equals(CommandsContract.MODE_SWITCH)){
            return new ModeSwitchCommand();
        } else if(commandName.equals(CommandsContract.PLAY_SOUND)){
            return new PlaySoundCommand();
        } else if(commandName.equals(CommandsContract.CHANGE_PIN_CODE)){
            return new ChangePinCodeCommand();
        } else{
            return null;
        }
    }
}
