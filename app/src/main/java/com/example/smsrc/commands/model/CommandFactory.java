package com.example.smsrc.commands.model;

public class CommandFactory {
    public static Command getCommand(String commandName){
        if(commandName.equals("ModeSwitch")){
            return new ModeSwitchCommand();
        }
        else{
            return null;
        }
    }
}
