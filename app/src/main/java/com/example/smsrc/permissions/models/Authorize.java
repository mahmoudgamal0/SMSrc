package com.example.smsrc.permissions.models;

import com.example.smsrc.commands.model.CommandsContract;
import com.example.smsrc.permissions.utils.AuthRoles;
import com.example.smsrc.users.models.User;

public class Authorize {

    public boolean authorize(User user, String operation){
        String authLevel = user.getAuthLevel();
        if(authLevel.equals(AuthRoles.OWNER))
            return true;
        else if(authLevel.equals(AuthRoles.LEVEL_ONE_GUEST))
            return !operation.equals(CommandsContract.CHANGE_PIN_CODE);
        else if(authLevel.equals(AuthRoles.LEVEL_TWO_GUEST))
            return false;
        throw new RuntimeException("Invalid Auth Role for user");
    }
}
