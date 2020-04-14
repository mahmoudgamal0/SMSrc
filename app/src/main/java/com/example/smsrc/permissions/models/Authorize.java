package com.example.smsrc.permissions.models;

import android.util.Log;

import com.example.smsrc.commands.model.CommandsContract;
import com.example.smsrc.permissions.utils.AuthRoles;
import com.example.smsrc.users.models.User;

public class Authorize {

    public boolean authorize(User user, String operation){


        Log.i("Authorize", "check if user "+ user.getUsername() +
                "authorized for command " + operation);

        String authLevel = user.getAuthLevel();
        if(authLevel.equals(AuthRoles.OWNER))
            return true;
        else if(authLevel.equals(AuthRoles.LEVEL_ONE_GUEST))
            return !operation.equals(CommandsContract.CHANGE_PIN_CODE);
        else if(authLevel.equals(AuthRoles.LEVEL_TWO_GUEST))
            return false;

        Log.e("Authorize", "Role doesn't exist");
        throw new RuntimeException("Invalid Auth Role for user");
    }
}
