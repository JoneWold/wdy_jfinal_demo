package com.wdy.utils;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * 时间类
 * Created by hdy on 2016/2/17.
 */
public abstract class Dates {
    public static final String FORMAT_DATE = "yyyy-MM-dd";
    public static final String FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_DATE_T_TIME = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String FORMAT_DATETIME_SSS = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String FORMAT_DATE_T_TIME_SSS = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    public static final String FORMAT_ISO = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public static final String FORMAT_ISO_SSS = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String FORMAT_NONE = "yyyyMMddHHmmssSSS";
    public static final String FORMAT_DATE_NONE = "yyyyMMdd";
    public static final String FORMAT_TIME_NONE = "HHmmss";
    public static final String FORMAT_TIME = "HH:mm:ss";
    public static final String FORMAT_DATETIME_NONE_SSS = "yyyyMMddHHmmss";
    public static final String FORMAT_DATETIME_NONE = "yyyyMMddHHmm";
    public static final String FORMAT_DATE_TOSTRING_DEFAULT = "EEE MMM dd HH:mm:ss zzz yyyy";

    private Dates() {
    }

    public static Date newDate() {
        return new Date();
    }

    /**
     * @param date 毫秒
     * @return
     */
    public static Date newDate(long date) {
        return new Date(date);
    }

    /**
     * @param year  年
     * @param month 月
     * @param day   日
     * @return
     */
    public static Date newDate(int year, int month, int day) {
        return newLocalDate(year, month, day).toDate();
    }

    /**
     * @param date   格式化后的时间
     * @param format 格式方式
     * @return
     */
    public static Date newDate(String date, String format) {
        Date d = null;

        try {
            if (format == null) {
                if (isFormat(date, FORMAT_DATE_TOSTRING_DEFAULT)) {
                    format = FORMAT_DATE_TOSTRING_DEFAULT;
                } else if (isFormat(date, FORMAT_DATE)) {
                    format = FORMAT_DATE;
                } else if (!isFormat(date, FORMAT_DATETIME) && !isFormat(date, FORMAT_DATE_T_TIME)) {
                    if (!isFormat(date, FORMAT_DATETIME_SSS) && !isFormat(date, FORMAT_DATE_T_TIME_SSS)) {
                        if (isFormat(date, FORMAT_ISO)) {
                            format = FORMAT_ISO;
                        } else if (isFormat(date, FORMAT_ISO_SSS)) {
                            format = FORMAT_ISO_SSS;
                        } else {
                            format = FORMAT_NONE;
                        }
                    } else if (StringUtils.contains(date, "T")) {
                        format = FORMAT_DATE_T_TIME_SSS;
                    } else {
                        format = FORMAT_DATETIME_SSS;
                    }
                } else if (StringUtils.contains(date, "T")) {
                    format = FORMAT_DATE_T_TIME;
                } else {
                    format = FORMAT_DATETIME;
                }
            }

            d = newDateFormat(format, Locale.ENGLISH).parse(date);
            return d;
        } catch (ParseException var4) {
            throw unchecked(var4);
        }
    }

    public static RuntimeException unchecked(Throwable ex) {
        return ex instanceof RuntimeException ? (RuntimeException) ex : new RuntimeException(ex);
    }

    public static Date newDate(String date) {
        return newDate(date, (String) null);
    }

    public static Date newDateTime(String date) {
        return newDate(date, FORMAT_DATETIME);
    }

    public static Date newDateWithDateTimeFormat(String date) {
        return newDate(date, FORMAT_DATETIME);
    }

    public static Date newDateISO(String date) {
        return newDate(date, FORMAT_ISO);
    }

    public static Date newDateISO_SSS(String date) {
        return newDate(date, FORMAT_ISO_SSS);
    }

    public static LocalDate newLocalDate() {
        return new LocalDate();
    }


    public static LocalDate newLocalDate(String date) {
        return newLocalDate(newDate(date));
    }

    public static LocalDate newLocalDate(String date, String format) {
        return newLocalDate(newDate(date, format));
    }

    public static LocalDate newLocalDate(Date date) {
        return new LocalDate(date);
    }

    public static LocalDate newLocalDate(int year, int month, int day) {
        return new LocalDate(year, month, day);
    }


    public static Date newDateTime(int year, int month, int day, int hour, int minute, int second) {
        return newLocalDateTime(year, month, day, hour, minute, second).toDate();
    }

    public static LocalDateTime newLocalDateTime(int year, int month, int day, int hour, int minute, int second) {
        return new LocalDateTime(year, month, day, hour, minute, second);
    }

    public static LocalDateTime newLocalDateTime(String date) {
        return newLocalDateTime(newDate(date));
    }

    public static LocalDateTime newLocalDateTime(String date, String format) {
        return newLocalDateTime(newDate(date, format));
    }

    public static LocalDateTime newLocalDateTime(Date date) {
        return new LocalDateTime(date);
    }


    public static SimpleDateFormat newDateFormat(String format) {
        return new SimpleDateFormat(format);
    }

    public static SimpleDateFormat newDateFormat(String format, Locale locale) {
        return new SimpleDateFormat(format, locale);
    }

    public static SimpleDateFormat newDateFormatForDate() {
        return newDateFormat(FORMAT_DATE);
    }

    public static SimpleDateFormat newDateFormatForDateTime() {
        return newDateFormat(FORMAT_DATETIME);
    }

    public static SimpleDateFormat newDateFormatForDateISO() {
        return newDateFormat(FORMAT_ISO);
    }

    public static SimpleDateFormat newDateFormatForTime() {
        return newDateFormat(FORMAT_TIME);
    }

    public static String newDateString(String pattern) {
        return toString(newDate(), pattern);
    }

    public static String newDateStringOfFormatDate() {
        return toString(newDate(), FORMAT_DATE);
    }

    public static String newDateStringOfFormatDateTime() {
        return toString(newDate(), FORMAT_DATETIME);
    }

    public static String newDateStringOfFormatDateISO() {
        return toString(newDate(), FORMAT_ISO);
    }

    public static String newDateStringOfFormatNone() {
        return toString(newDate(), FORMAT_NONE);
    }

    public static String newDateStringOfFormatDateTimeNone() {
        return toString(newDate(), FORMAT_DATETIME_NONE);
    }

    public static String newDateStringOfFormatDatetimeNoneSSS() {
        return toString(newDate(), FORMAT_DATETIME_NONE_SSS);
    }

    public static String newDateStringOfFormatDateNone() {
        return toString(newDate(), FORMAT_DATE_NONE);
    }

    public static String newDateStringOfFormatTimeNone() {
        return toString(newDate(), FORMAT_TIME_NONE);
    }


    /**
     * 今天开始
     *
     * @return
     */
    public static Date getStartOfToday() {
        return getStartOfTheDate(new Date());
    }

    /**
     * 今天结束
     *
     * @return
     */
    public static Date getEndOfToday() {
        return getEndOfTheDate(new Date());
    }

    public static Date getStartOfThe(String date, String format) {
        LocalDate dt = newLocalDate(newDate(date, format));
        return dt.toLocalDateTime(new LocalTime(0, 0, 0)).toDate();
    }

    public static Date getEndOfThe(String date, String format) {
        LocalDate d = newLocalDate(newDate(date, format));
        LocalDateTime dt = d.toLocalDateTime(new LocalTime(23, 59, 59));
        return dt.toDate();
    }

    public static Date getStartOfTheDate(String date) {
        LocalDate dt = newLocalDate(date);
        return dt.toLocalDateTime(new LocalTime(0, 0, 0)).toDate();
    }

    public static Date getStartOfTheDate(Date date) {
        LocalDate dt = newLocalDate(date);
        return dt.toLocalDateTime(new LocalTime(0, 0, 0)).toDate();
    }

    public static Date getEndOfTheDate(String date) {
        LocalDate d = newLocalDate(date);
        LocalDateTime dt = d.toLocalDateTime(new LocalTime(23, 59, 59));
        return dt.toDate();
    }

    public static Date getEndOfTheDate(Date date) {
        LocalDate d = newLocalDate(date);
        LocalDateTime dt = d.toLocalDateTime(new LocalTime(23, 59, 59));
        return dt.toDate();
    }

    public static Date getStartOfTheDateTime(String datetime) {
        LocalDateTime dt = newLocalDateTime(datetime);
        dt = dt.toLocalDate().toLocalDateTime(new LocalTime(0, 0, 0));
        return dt.toDate();
    }

    public static Date getStartOfTheDateTime(Date datetime) {
        LocalDateTime dt = newLocalDateTime(datetime);
        dt = dt.toLocalDate().toLocalDateTime(new LocalTime(0, 0, 0));
        return dt.toDate();
    }

    public static Date getEndOfTheDateTime(String datetime) {
        LocalDateTime dt = newLocalDateTime(datetime);
        dt = dt.toLocalDate().toLocalDateTime(new LocalTime(23, 59, 59));
        return dt.toDate();
    }

    public static Date getEndOfTheDateTime(Date datetime) {
        LocalDateTime dt = newLocalDateTime(datetime);
        dt = dt.toLocalDate().toLocalDateTime(new LocalTime(23, 59, 59));
        return dt.toDate();
    }

    public static String toString(Date date, String format) {
        if (format == null) {
            format = FORMAT_DATETIME;
        }

        return newDateFormat(format).format(date);
    }

    public static String toString(Date date) {
        return toString(date, (String) null);
    }

    public static String toStringWithDateFormatDate(Date date) {
        return toString(date, FORMAT_DATE);
    }

    public static String toStringWithDateFormatDateTime(Date date) {
        return toString(date, FORMAT_DATETIME);
    }

    public static String toStringWithDateFormatISO(Date date) {
        return toString(date, FORMAT_ISO);
    }

    /**
     * 判断是否在之后
     *
     * @param date
     * @param when
     * @return
     */
    public static boolean isAfter(Date date, Date when) {
        return date.after(when);
    }

    public static boolean isAfterOfNow(Date date) {
        return isAfter(date, newDate());
    }

    /**
     * 判断是否在之前
     *
     * @param date
     * @param when
     * @return
     */
    public static boolean isBefore(Date date, Date when) {
        return date.before(when);
    }

    public static boolean isBeforeOfNow(Date date) {
        return isBefore(date, newDate());
    }

    /**
     * 是否是今天
     *
     * @param date
     * @return
     */
    public static boolean isToday(Date date) {
        return isAfter(date, getStartOfToday()) && isBefore(date, getEndOfToday());
    }

    /**
     * 判断时间是否在时间段内
     *
     * @param now
     * @param startTime
     * @param endTime
     */
    public static boolean isBelong(Date now, String startTime, String endTime) {
        SimpleDateFormat simpleDateFormat = newDateFormatForTime();
        Date beginDate = null;
        Date endDate = null;
        try {
            now = simpleDateFormat.parse(simpleDateFormat.format(now));
            beginDate = simpleDateFormat.parse(startTime);
            endDate = simpleDateFormat.parse(endTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return belongCalendar(now, beginDate, endDate);
    }

    /**
     * 判断时间是否在时间段内
     *
     * @param nowTime
     * @param beginTime
     * @param endTime
     * @return
     */
    public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 加多少月
     *
     * @param date
     * @param n    月数
     * @return
     */
    public static Date addMonth(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(2, n);
        return cal.getTime();
    }

    public static Date addMonthOfNow(int n) {
        return addMonth(newDate(), n);
    }

    /**
     * 加多少天
     *
     * @param date
     * @param n    天数
     * @return
     */
    public static Date addDay(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(7, n);
        return cal.getTime();
    }

    public static Date addDayOfNow(int n) {
        return addDay(newDate(), n);
    }

    /**
     * 加多少小时
     *
     * @param date
     * @param n    小时数
     * @return
     */

    public static Date addHour(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(10, n);
        return cal.getTime();
    }

    public static Date addHourOfNow(int n) {
        return addHour(newDate(), n);
    }

    /**
     * 加多少分钟
     *
     * @param date
     * @param n    分钟数
     * @return
     */
    public static Date addMinute(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(12, n);
        return cal.getTime();
    }

    public static Date addMinuteOfNow(int n) {
        return addMinute(newDate(), n);
    }

    /**
     * 加多少秒
     *
     * @param date
     * @param n    秒数
     * @return
     */
    public static Date addSecond(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(13, n);
        return cal.getTime();
    }

    public static Date addSecond(int n) {
        return addSecond(newDate(), n);
    }

    public static int getLengthOfFormat(String format) {
        return format.replaceAll("'", "").length();
    }

    public static boolean isFormat(String date, String format) {
        return date.length() == getLengthOfFormat(format);
    }

    public static int getMondayPlus() {
        Calendar cd = Calendar.getInstance();
        int dayOfWeek = cd.get(7);
        return dayOfWeek == 1 ? -6 : 2 - dayOfWeek;
    }

    /**
     * 本周一
     *
     * @return
     */
    public static String getCurrentMonday() {
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(5, mondayPlus);
        Date monday = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        return df.format(monday);
    }

    /**
     * 本周末
     *
     * @return
     */
    public static String getPreviousSunday() {
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(5, mondayPlus + 6);
        Date monday = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        return df.format(monday);
    }

}
