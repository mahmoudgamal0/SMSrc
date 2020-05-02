package com.example.smsrc.auth.models;

import android.util.Log;

import com.example.smsrc.commands.interfaces.CommandsContract;
import com.example.smsrc.auth.utils.AuthRoles;
import com.example.smsrc.users.models.User;

public class Authorize {

    public boolean authorizeCommand(User user, String operation){
        Log.i("Authorize", "check if user "+ user.getUsername() +
                "authorized for command " + operation);

        String authLevel = user.getAuthLevel();
        if(authLevel.equals(AuthRoles.OWNER))
            return true;
        else if(authLevel.equals(AuthRoles.LEVEL_ONE_GUEST))
            return checkAuth(operation, AuthRoles.LEVEL_ONE_GUEST_COMMANDS);
        else if(authLevel.equals(AuthRoles.LEVEL_TWO_GUEST))
            return checkAuth(operation, AuthRoles.LEVEL_TWO_GUEST_COMMANDS);

        Log.e("Authorize", "Role doesn't exist");
        throw new RuntimeException("Invalid Auth Role for user");
    }

    public boolean authorizeManagement(User user, String operation){
        String authLevel = user.getAuthLevel();
        if(authLevel.equals(AuthRoles.OWNER))
            return true;
        else if(authLevel.equals(AuthRoles.LEVEL_ONE_GUEST))
            return checkAuth(operation, AuthRoles.LEVEL_ONE_GUEST_MANAGER_ROLES);
        else if(authLevel.equals(AuthRoles.LEVEL_TWO_GUEST))
            return checkAuth(operation, AuthRoles.LEVEL_TWO_GUEST_MANAGER_ROLES);

        Log.e("Authorize", "Role doesn't exist");
        throw new RuntimeException("Invalid Auth Role for user");
    }

    private boolean checkAuth(String command, String[] authRoleCommands){
        for(String s : authRoleCommands){
            if(command.equals(s)){
                return true;
            }
        }
        return false;
    }
}
