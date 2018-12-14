package com.judian.watch.videos.Utils;

import android.annotation.SuppressLint;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/*
 * Created by Administrator on 2016/12/26 0026.
 */

public class TimeUtils {


    @SuppressLint("SimpleDateFormat")
    public static String getYearMonthAndDay(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(new Date(time));
    }


    public static String getTime(int ms) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
        String hms = formatter.format(ms);
        return hms;
    }
}