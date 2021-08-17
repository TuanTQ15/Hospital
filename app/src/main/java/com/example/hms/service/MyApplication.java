package com.example.hms.service;

import android.app.Application;

import androidx.room.Room;

import com.example.hms.dao.AppDatabase;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "myDatabase").allowMainThreadQueries().build();
    }

    private static AppDatabase db;
    public static AppDatabase getDb(){
        return db;
    }
}
