package com.edinstudio.android.baseframework.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by albert on 15-5-20.
 */
public class TimeUtil {
    private static final String COMMON_TIME_FORMAT = "yyyy-MM-dd HH:mm:ssZ";

    public static String format(String utcTime, String fromFormat, String toFormat) {
        try {
            Date date = new SimpleDateFormat(fromFormat).parse(utcTime);
            return new SimpleDateFormat(toFormat).format(date);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return "illegal argument";
        } catch (NullPointerException e) {
            e.printStackTrace();
            return "null pointer";
        } catch (ParseException e) {
            e.printStackTrace();
            return "parse exception";
        }
    }

    public static String format(String utcTime, String toFormat) {
        return format(utcTime, COMMON_TIME_FORMAT, toFormat);
    }

    public static Date convertToDate(String utcTime, String format) throws NullPointerException, ParseException {
        return new SimpleDateFormat(format).parse(utcTime);
    }

    public static Date convertToDate(String utcTime) throws NullPointerException, ParseException {
        return convertToDate(utcTime, COMMON_TIME_FORMAT);
    }

    public static String convertToString(Date date, String format) {
        try {
            return new SimpleDateFormat(format).format(date);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return "illegal argument";
        } catch (NullPointerException e) {
            e.printStackTrace();
            return "null pointer";
        }
    }

    public static String convertToString(Date date) {
        return convertToString(date, COMMON_TIME_FORMAT);
    }

    public static boolean isSameDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return false;
        }
        final Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        final Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA)) &&
                (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)) &&
                (cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
    }

    public static boolean isToday(Date date) {
        return isSameDay(date, new Date());
    }

    public static boolean isSameMonth(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return false;
        }
        final Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        final Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA)) &&
                (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)) &&
                (cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH));
    }

    public static boolean isThisMonth(Date date) {
        return isSameMonth(date, new Date());
    }

    public static boolean isSameYear(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return false;
        }
        final Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        final Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA)) &&
                (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR));
    }

    public static boolean isThisYear(Date date) {
        return isSameYear(date, new Date());
    }
}
