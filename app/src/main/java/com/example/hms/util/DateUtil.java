package com.example.hms.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtil {
    public static long stringDateToLong(String value){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        simpleDateFormat.setTimeZone(TimeZone.getDefault());
        try {
            return  simpleDateFormat.parse(value).getTime();
        } catch (ParseException e) {
            return  0;
        }
    }
}
