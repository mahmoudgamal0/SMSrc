package com.example.smsrc.commands.handlers;

import com.example.smsrc.commands.interfaces.Command;

public class CommandExecutioner {
    public void execute(Command command) {
        command.execute(new String[]{});
    }
}
