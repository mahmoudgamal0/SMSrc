package com.example.smsrc.sms.handlers;

import android.content.Context;

import com.example.smsrc.commands.model.Command;
import com.example.smsrc.commands.model.CommandFactory;
import com.example.smsrc.commands.model.CommandsContract;
import com.example.smsrc.permissions.models.Authenticate;
import com.example.smsrc.permissions.models.Authorize;
import com.example.smsrc.permissions.utils.Crypto;
import com.example.smsrc.sms.model.SMS;
import com.example.smsrc.users.dals.UserRepository;
import com.example.smsrc.users.models.User;

import java.util.List;

public class SMSExecutor {

    private Context context;
    private UserRepository repository;
    private Authorize authorize;
    public void setRepository(UserRepository userRepository) {
        repository = userRepository;
        authorize = new Authorize();
    }


    public void execute(SMS sms) {

        String hash;
        User user = null;
        List<User> allUsers = repository.getAllUsers();
        for (User u: allUsers) {

            hash = Crypto.encrypt(u.getUsername() + u.getPasscode(), sms.getRandomness());
            if(hash.equals(sms.getCredentials())) {
                user = u;
                break;
            }
        }

        if(user == null)
            throw new RuntimeException("Credentials not found");

        Command command = null;
        for (String commandName: CommandsContract.allCommands) {
            hash = Crypto.encrypt(commandName, sms.getRandomness());
            if(hash.equals(sms.getCommand())) {
                if (authorize.authorize(user, commandName)) {
                    command = CommandFactory.getCommand(commandName,context);
                } else
                    throw new RuntimeException("UnAuthorized command");
            }
        }

        if(command == null)
            throw new RuntimeException("No such command");
        command.execute( null);

    }

    public void setContext(Context context) {
        this.context = context;
    }
}
