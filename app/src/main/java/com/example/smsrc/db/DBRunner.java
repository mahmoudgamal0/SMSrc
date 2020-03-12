package com.example.smsrc.db;

import com.example.smsrc.users.dals.UserDao;
import com.example.smsrc.users.models.User;
import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class DBRunner extends RoomDatabase {
    public abstract UserDao userDao();
}
