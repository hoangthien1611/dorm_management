package com.doan.dormmanagement.utility;

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

    public static String convertYearMonthtoString(int year, int month) {
        return month < 10 ? year + "-0" + month : year + "-" + month;
    }
}
