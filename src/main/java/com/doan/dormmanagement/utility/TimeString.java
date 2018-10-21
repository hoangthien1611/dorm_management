package com.doan.dormmanagement.utility;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeString {
    public static int[] getPreviousMonth() {
        Date today = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        int month = cal.get(Calendar.MONTH); // 0 being January
        int year = cal.get(Calendar.YEAR);
        int[] time = new int[2];

        time[0] = month;
        time[1] = year;
        if (month == 0) {
            time[0] = 12;
            time[1] = year - 1;
        }
        return time;
    }

    public static int[] getCurrentMonth() {
        Date today = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        int month = cal.get(Calendar.MONTH) + 1; // 0 being January
        int year = cal.get(Calendar.YEAR);
        int[] time = new int[2];
        time[0] = month;
        time[1] = year;

        return time;
    }

    public static String getCurrentTimeStamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        Date now = new Date();
        String strDate = sdf.format(now);
        return strDate;
    }

    public static String convertYearMonthtoString(int year, int month) {
        return month < 10 ? year + "-0" + month : year + "-" + month;
    }

    public static String convertTimeStampToTimeString(Timestamp time) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.mmm");
        try{
            return sdf.format(time);
        }catch(Exception e){
            return "";
        }
    }
}
