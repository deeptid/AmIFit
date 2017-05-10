package com.deepti.amifit.util;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by deepti on 09/01/17.
 */

public class CalendarUtility {
    public static String TAG = CalendarUtility.class.getSimpleName();

    public static Date getCurrentDate() {
        Date today = new Date();
        return today;
    }

    public static int getCurrentYear() {
        Calendar now = Calendar.getInstance();   // Gets the current date and time
        int year = now.get(Calendar.YEAR);
        return year;
    }

    public static String getFirstDayofMonth(Date d) throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date dddd = calendar.getTime();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        return sdf1.format(dddd);
    }

    public static String getLastDayofMonth(Date d) throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date dddd = calendar.getTime();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        return sdf1.format(dddd);
    }

    public static String getFirstDayofWeek() throws Exception {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date[] a = new Date[1];

        a[0] = c.getTime();
        c.add(Calendar.DATE, 1);

        return df.format(a[0]);
    }

    public static String getLastDayofWeek() throws Exception {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date[] a = new Date[7];
        for (int i = 0; i < 7; i++) {
            a[i] = c.getTime();
            c.add(Calendar.DATE, 1);
        }
        return df.format(a[6]);
    }

    public static String getFirstDayofYear() throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_YEAR, 1);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(cal.getTime());
    }


    public static String getLastDayofYear() throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_YEAR, 366);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(cal.getTime());
    }


    public static String getDateInCustomFormat(Date date, String format) {
        SimpleDateFormat outputDate = new SimpleDateFormat(format);
        return outputDate.format(date);
    }

    public static String getTodaysDay() {
        String weekDay;
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);

        Calendar calendar = Calendar.getInstance();
        weekDay = dayFormat.format(calendar.getTime());

        return weekDay;
    }

    public static ArrayList<WeekorMonthDataPoints> getAllFirstandLastDayofWeek() {
        int year = getCurrentYear();
        int noOfWeeks = getTotalWeeksInYear(year);
        ArrayList<WeekorMonthDataPoints> dataPoints = new ArrayList<WeekorMonthDataPoints>();
        for (int i = 0; i <= noOfWeeks; i++) {
            String firstDay = getfirstDayOfaGivenWeek(i, year);
            String lastDay = getlastDayOfaGivenWeek(i, year);
            WeekorMonthDataPoints dp = new WeekorMonthDataPoints(firstDay, lastDay);
            dataPoints.add(dp);
        }
        return dataPoints;
    }

    public static ArrayList<WeekorMonthDataPoints> getAllFirstandLastDayofMonth() {
        int year = getCurrentYear();
        int noOfWeeks = 12;
        ArrayList<WeekorMonthDataPoints> dataPoints = new ArrayList<WeekorMonthDataPoints>();
        for (int i = 0; i < noOfWeeks; i++) {
            String firstDay = getfirstDayOfaGivenMonth(i, year);
            String lastDay = getlastDayOfaGivenMonth(i, year);
            WeekorMonthDataPoints dp = new WeekorMonthDataPoints(firstDay, lastDay);
            dataPoints.add(dp);
        }
        return dataPoints;
    }

    private static String getlastDayOfaGivenMonth(int month, int year) {
        Calendar gc = new GregorianCalendar();
        gc.set(Calendar.MONTH, month);
        gc.set(Calendar.DAY_OF_MONTH, 1);
        Date monthStart = gc.getTime();

        gc.add(Calendar.MONTH, 1);
        gc.add(Calendar.DAY_OF_MONTH, -1);
        Date monthEnd = gc.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(monthEnd);
    }

    private static String getfirstDayOfaGivenMonth(int month, int year) {
        Calendar gc = new GregorianCalendar();
        gc.set(Calendar.MONTH, month);
        gc.set(Calendar.DAY_OF_MONTH, 1);
        Date monthStart = gc.getTime();

        gc.add(Calendar.MONTH, 1);
        gc.add(Calendar.DAY_OF_MONTH, -1);
        Date monthEnd = gc.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(monthStart);
    }

    public static String getfirstDayOfaGivenWeek(int week, int year) {
        // Get calendar, clear it and set week number and year.
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.WEEK_OF_YEAR, week);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        // Now get the first day of week.
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        return df.format(calendar.getTime());
    }

    public static String getlastDayOfaGivenWeek(int week, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.WEEK_OF_YEAR, week);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        // Now get the first day of week.
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date[] a = new Date[7];
        for (int i = 0; i < 7; i++) {
            a[i] = calendar.getTime();
            calendar.add(Calendar.DATE, 1);
        }
        return df.format(a[6]);
    }

    private static int getTotalWeeksInYear(int year) {
        Calendar mCalendar = new GregorianCalendar(TimeZone.getDefault());
        mCalendar.setFirstDayOfWeek(Calendar.MONDAY);
        // Workaround
        mCalendar.set(year,
                Calendar.DECEMBER,
                31);
        int totalDaysInYear = mCalendar.get(Calendar.DAY_OF_YEAR);
        System.out.println(totalDaysInYear);
        int totalWeeks = totalDaysInYear / 7;
        return totalWeeks;
    }

    public static class WeekorMonthDataPoints {
        String firstDay;
        String lastDay;

        public WeekorMonthDataPoints(String firstDay, String lastDay) {
            this.firstDay = firstDay;
            this.lastDay = lastDay;
        }

        public String getFirstDay() {
            return firstDay;
        }

        public void setFirstDay(String firstDay) {
            this.firstDay = firstDay;
        }

        public String getLastDay() {
            return lastDay;
        }

        public void setLastDay(String lastDay) {
            this.lastDay = lastDay;
        }
    }
}

