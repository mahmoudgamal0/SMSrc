package com.example.smsrc.permissions.models;

import com.example.smsrc.permissions.utils.AuthRoles;
import com.example.smsrc.users.models.User;

public class Authorize {

    public boolean authorize(User user, String operation){
        String authLevel = user.getAuthLevel();
        if(authLevel.equals(AuthRoles.DELETE))
            return true;
        else if(authLevel.equals(AuthRoles.EDIT))
            return !operation.equals(AuthRoles.DELETE);
        else if(authLevel.equals(AuthRoles.ADD))
            return !operation.equals(AuthRoles.EDIT) && !operation.equals(AuthRoles.DELETE);

        throw new RuntimeException("Invalid Auth Role for user");
    }
}
