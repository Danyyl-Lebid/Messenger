package com.github.messenger.utils;

import java.util.Date;

public class DateUtils {

    private static final long MILLISECONDS_IN_MINUTE = 60_000L;

    public static Date addMinutes(Date date, int minutes){
        return new Date(date.getTime() + minutes * MILLISECONDS_IN_MINUTE);
    }

    public static Date getCurrentDate(){
        return new Date();
    }


}
