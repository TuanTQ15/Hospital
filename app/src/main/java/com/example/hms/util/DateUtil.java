package com.example.hms.util;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public  class DateUtil {

    public static String convertTime(long time,String PATTERN_DATE){
        Date date = new Date(time);
        Format format = new SimpleDateFormat(PATTERN_DATE);
        return format.format(date);
    }
    public static long convertTimeToLong(String date){
        long milliseconds=0;
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try {
            Date d = f.parse(date);
            milliseconds = d.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return milliseconds;
    }
}
