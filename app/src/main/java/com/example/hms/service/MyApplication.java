package com.example.hms.service;

import android.app.Application;
import android.graphics.Bitmap;

import androidx.room.Room;

import com.example.hms.BuildConfig;
import com.example.hms.dao.AppDatabase;
import com.paypal.checkout.PayPalCheckout;
import com.paypal.checkout.config.CheckoutConfig;
import com.paypal.checkout.config.Environment;
import com.paypal.checkout.config.SettingsConfig;
import com.paypal.checkout.createorder.CurrencyCode;
import com.paypal.checkout.createorder.UserAction;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class MyApplication extends Application {
    private static final String YOUR_CLIENT_ID = "Abp1hkUSyYnfX1G3uzugtyrF1BtE-xWPKkrmlNqjpvb9-Z0U-SI-U2P2K_rh2N4MIfq2-z9R7o3SNUHI";
    @Override
    public void onCreate() {
        super.onCreate();
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "myDatabase").allowMainThreadQueries().build();
        CheckoutConfig config = new CheckoutConfig(
                this,
                YOUR_CLIENT_ID,
                Environment.SANDBOX,
                String.format("%s://paypalpay", BuildConfig.APPLICATION_ID),
                CurrencyCode.USD,
                UserAction.PAY_NOW,
                new SettingsConfig(
                        true,
                        false
                )
        );
        PayPalCheckout.setConfig(config);
    }

    private static AppDatabase db;
    public static AppDatabase getDb(){
        return db;
    }
}
