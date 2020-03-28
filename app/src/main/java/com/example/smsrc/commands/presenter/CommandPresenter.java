package com.example.smsrc.commands.presenter;

import com.example.smsrc.commands.handlers.CommandExecutioner;
import com.example.smsrc.commands.model.Command;

public class CommandPresenter {
    private CommandExecutioner executioner;

    public CommandPresenter(){
        this.executioner = new CommandExecutioner();
    }

    public void executeCommand(Command command){
        executioner.execute(command);
    }
}
