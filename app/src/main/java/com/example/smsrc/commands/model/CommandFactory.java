package com.example.smsrc.commands.model;

public class CommandFactory {
    public static Command getCommand(String commandName){
        if(commandName.equals("ModeSwitch")){
            return new ModeSwitchCommand();
        }
        else if(commandName.equals("PlaySound")){
            return new PlaySoundCommand();
        }else if(commandName.equals("ChangePinCode")){
            return new ChangePinCodeCommand();
        }else{
            return null;
        }
    }
}
