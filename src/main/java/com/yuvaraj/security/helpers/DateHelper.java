package com.yuvaraj.security.helpers;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Minutes;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateHelper {


    public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";
    public static final String TIME_ZONE = "Asia/Kuala_Lumpur";

    public static Date nowDate() {
        return new DateTime(DateTimeZone.forID(TIME_ZONE)).toDate();
    }

    public static DateTime nowDateTime() {
        return new DateTime(DateTimeZone.forID(TIME_ZONE));
    }

    public static String convertDateForEndResult(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(TIME_ZONE));
        return simpleDateFormat.format(date);
    }

    public static Date nowDateAddMinutes(int minutes) {
        DateTime nowDateTime = nowDateTime();
        return nowDateTime.plusMinutes(minutes).toDate();
    }

    public static Date nowDateAddSeconds(int seconds) {
        DateTime nowDateTime = nowDateTime();
        return nowDateTime.plusSeconds(seconds).toDate();
    }

    public static Date dateAddMinutes(Date date, int minutes) {
        DateTime nowDateTime = new DateTime(date.getTime());
        return nowDateTime.plusMinutes(minutes).toDate();
    }

    public static int getMinutesLeft(Date date1, Date date2) {
        DateTime date1DateTime = new DateTime(date1.getTime());
        DateTime date2DateTime = new DateTime(date2.getTime());
        return Minutes.minutesBetween(date1DateTime, date2DateTime).getMinutes();
    }

    public static Date dateAddSeconds(Date date, int seconds) {
        DateTime nowDateTime = new DateTime(date.getTime());
        return nowDateTime.plusSeconds(seconds).toDate();
    }

    public static Date convertTimeStampToDate(long timeStamp) {
        Timestamp ts = new Timestamp(timeStamp);
        return new Date(ts.getTime());
    }

}
