package com.example.hms.dao;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.hms.ModelClass.LoginInfoModel;

@Database(entities = {LoginInfoModel.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract userLoginDAO userDao();
}