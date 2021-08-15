package com.example.hms.dao;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.hms.ModelClass.LoginInfo;

@Database(entities = {LoginInfo.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract userLoginDAO userDao();
}