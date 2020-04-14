package com.example.smsrc.sms.handlers;

import android.content.Context;
import android.util.Log;

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

        Log.i("SMSExecutor", "executing SMS Protocol has started");
        Log.d("SMSExecutor", "check if credentials exist");
        String hash;
        User user = null;
        List<User> allUsers = repository.getAllUsers();
        for (User u: allUsers) {

            hash = Crypto.encrypt(u.getUsername() + sms.getRandomness() + u.getPasscode());
            if(hash.equals(sms.getCredentials())) {
                user = u;
                break;
            }
        }

        if(user == null)
            throw new RuntimeException("Credentials not found");

        Log.d("SMSExecutor", "check if command exists");

        Command command = null;
        for (String commandName: CommandsContract.allCommands) {
            hash = Crypto.encrypt(commandName + sms.getRandomness());
            if(hash.equals(sms.getCommand())) {
                Log.i("SMSExecutor", "check if user authorized to carry this command");
                if (authorize.authorize(user, commandName)) {
                    command = CommandFactory.getCommand(commandName,context);
                    break;
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
