package com.example.smsrc.auth.models;

import android.util.Log;

import com.example.smsrc.commands.interfaces.CommandsContract;
import com.example.smsrc.auth.utils.AuthRoles;
import com.example.smsrc.users.models.User;

public class Authorize {

    public boolean authorize(User user, String operation){


        Log.i("Authorize", "check if user "+ user.getUsername() +
                "authorized for command " + operation);

        String authLevel = user.getAuthLevel();
        if(authLevel.equals(AuthRoles.OWNER))
            return true;
        else if(authLevel.equals(AuthRoles.LEVEL_ONE_GUEST))
            return !operation.equals(CommandsContract.LOCK_PHONE);
        else if(authLevel.equals(AuthRoles.LEVEL_TWO_GUEST))
            return false;

        Log.e("Authorize", "Role doesn't exist");
        throw new RuntimeException("Invalid Auth Role for user");
    }
}
