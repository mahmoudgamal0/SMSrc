package com.example.smsrc.commands.interfaces;

public class CommandsContract {

    public static final String PLAY_SOUND = "PlaySound";
    public static final String MODE_SWITCH = "ModeSwitch";
    public static final String LOCK_PHONE = "LockPhone";
    public static final String WIPE_DATA = "WipeData";
    public static final String ENCRYPT_STORAGE = "EncryptStorage";
    public static final String DISABLE_CAMERA = "DisableCamera";
    public static final String IS_ALIVE = "IsAlive";

    public static final String[] allCommands =
            {
                    PLAY_SOUND,
                    MODE_SWITCH,
                    LOCK_PHONE,
                    WIPE_DATA,
                    ENCRYPT_STORAGE,
                    DISABLE_CAMERA,
                    IS_ALIVE
            };
}
