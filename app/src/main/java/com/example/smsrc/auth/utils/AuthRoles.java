package com.example.smsrc.auth.utils;

import com.example.smsrc.commands.interfaces.CommandsContract;

public class AuthRoles {
    public static String ADD = "add";
    public static String EDIT = "edit";
    public static String DELETE = "delete";

    public static String OWNER = "owner";
    public static String LEVEL_ONE_GUEST = "level 1 guest";
    public static String LEVEL_TWO_GUEST = "level 2 guest";

    public static String[] getRoles(){
        return new String[]{OWNER, LEVEL_ONE_GUEST, LEVEL_TWO_GUEST};
    }

    public static String[] LEVEL_TWO_GUEST_COMMANDS = {
            CommandsContract.PLAY_SOUND,
            CommandsContract.IS_ALIVE
    };

    public static String[] LEVEL_ONE_GUEST_COMMANDS = {
            CommandsContract.MODE_SWITCH,
            CommandsContract.PLAY_SOUND,
            CommandsContract.IS_ALIVE
    };
}
