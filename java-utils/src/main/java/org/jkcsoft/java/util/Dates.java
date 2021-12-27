/*
 * Copyright (c) Jim Coles (jameskcoles@gmail.com) 2018 through present.
 *
 * Licensed under the following license agreement:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Also see the LICENSE file in the repository root directory.
 */

package org.jkcsoft.java.util;

/**
 * Holds date utility methods used by application objects
 * to ensure consistency, code reuse, etc.  Primarily converts strings to
 * java.util.Date's and vice versa according to the default date format
 * string.
 * <p>
 * NOTES: Currently designed to hard code a single date format.  Possible
 * redesign is to hold a map of instances, one for each date format requested.
 * Could enum one or two common formats, e.g., short and long.  Could also
 * have the date format as a configurable parameter retrieved from a properties
 * file.
 * <p>
 * Methods not dependent of date format should remain as static.
 *
 * @author Jim Coles
 */

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

public class Dates {

    //----------------------------------------------------------------------
    // Static members
    //----------------------------------------------------------------------
    public static final long MS_PER_SEC = 1000L;
    public static final long MS_PER_MINUTE = MS_PER_SEC * 60L;
    public static final long MS_PER_HOUR = MS_PER_MINUTE * 60L;
    public static final long MS_PER_DAY = 24L * MS_PER_HOUR;

    public static final TimeZone TIME_ZONE_GMT = TimeZone.getTimeZone("GMT");

    public static final String DEFAULT_FORMAT = "M-dd-yyyy 'at' h:mm:ss a z";
    /**
     * 8-28-64 at 8:28:46 AM CST
     */
    public static final DateFormat FMTR_DEFAULT
            = new SimpleDateFormat(DEFAULT_FORMAT);

    public static final String ST_FMT_DATE_ONLY = "MMMMM d',' yyyy";
    /**
     * August 28, 1964
     */
    public static final DateFormat FMTR_DATE_ONLY
            = new SimpleDateFormat(ST_FMT_DATE_ONLY);

    public static final String ST_FMT_TODAY_NARRATIVE = "EEEE MMMMM d',' yyyy";
    /**
     * Saturday August 28, 1964
     */
    public static final DateFormat FMTR_TODAY_NARRATIVE
            = new SimpleDateFormat(ST_FMT_TODAY_NARRATIVE);

    public static final String ST_FMT_SHORT_TIMEZONE_ONLY = "zzz";
    /**
     * E.g., CST
     */
    public static final DateFormat FMTR_SHORT_TIMEZONE_ONLY
            = new SimpleDateFormat(ST_FMT_SHORT_TIMEZONE_ONLY);

    public static final String ST_FMT_DEFAULT_SHORT_FORMAT = "M-dd-yyyy 'at' h:mm:ss a";
    /**
     * 8-28-64 at 8:28:46 AM
     */
    public static final DateFormat FMTR_DEFAULT_SHORT_FORMAT
            = new SimpleDateFormat(ST_FMT_DEFAULT_SHORT_FORMAT);

    public static final String ST_FMT_DETAIL_MS = "M-dd-yyyy 'at' h:mm:ss.S a";
    /**
     * 8-28-64 at 8:28:46.378 AM
     */
    public static final DateFormat FMTR_DETAIL_MS
            = new SimpleDateFormat(ST_FMT_DETAIL_MS);

    public static final String ST_FMT_DEFAULT_LONG_FORMAT = "M-dd-yyyy 'at' h:mm:ss a zzz";
    /**
     * 8-28-64 at 8:28:28 AM Easter Standard Time
     */
    public static final DateFormat FMTR_DEFAULT_LONG_FORMAT
            = new SimpleDateFormat(ST_FMT_DEFAULT_LONG_FORMAT);

    public static final String ST_FMT_TODAY_LONG_FORMAT = "'Today at' h:mm:ss a zzz";
    /**
     * Today at 8:28:46 AM CST
     */
    public static final DateFormat FMTR_TODAY_LONG_FORMAT
            = new SimpleDateFormat(ST_FMT_TODAY_LONG_FORMAT);

    public static final String ST_TODAY_SHORT_FORMAT = "h:mm:ss a";
    /**
     * 8:28:46 AM
     */
    public static final DateFormat FMTR_TODAY_SHORT_FORMAT
            = new SimpleDateFormat(ST_TODAY_SHORT_FORMAT);

    // NOTE: MM is month, mm is minutes


    private static final DateFormat _defaultFormatter = FMTR_DEFAULT_LONG_FORMAT;

    static {
        _defaultFormatter.setLenient(true);
    }

    //----------------------------------------------------------------------
    // Static public methods
    //----------------------------------------------------------------------

    public static long nowMillis() {
        return System.currentTimeMillis();
    }

    public static Date nowDate() {
        return new Date(nowMillis());
    }

    public static Timestamp nowTs() {
        return new Timestamp(nowMillis());
    }

    public static Calendar nowCalendar() {
        return Calendar.getInstance();
    }


    /**
     * Convert a string with of format mm-dd-yyyy to a java.lang.Date.getTime()
     */
    public static long toDateLong(String strDate, DateFormat formatter) {
        Date date = toDate(strDate, formatter);
        if (date != null)
            return date.getTime();
        else
            return -1;
    }

    public static long toDateLong(String strDate) {
        return toDateLong(strDate, _defaultFormatter);
    }

    /**
     * Convert a string to a Date.
     */
    public static Date toDate(String strDate, DateFormat formatter) {
        Date date = null;
        if (strDate != null && !("".equals(strDate))) {
            ParsePosition pos = new ParsePosition(0);
            date = formatter.parse(strDate, pos);
        }
        return date;
    }

    public static Date toDate(String strDate) {
        return toDate(strDate, _defaultFormatter);
    }

    public static String toDateString(Date date, String stFmt) {
        DateFormat formatter = new SimpleDateFormat(stFmt);
        return toDateString(date, formatter);
    }

    public static String toDateString(long lngDate, DateFormat formatter) {
        return toDateString(new Date(lngDate), formatter);
    }

    public static String toDateString(long lngDate) {
        return toDateString(new Date(lngDate));
    }

    public static String toDateString(Date date) {
        return toDateString(date, _defaultFormatter);
    }

    public static String toSmartDateString(Date date, DateFormat today,
                                           DateFormat other) {
        String stDate = "?";
        if (date != null) {
            Calendar calInDate = Calendar.getInstance();
            calInDate.setTime(date);
            Calendar calNow = Calendar.getInstance();

            if (calInDate.get(Calendar.YEAR) == calNow.get(Calendar.YEAR)
                    && calInDate.get(Calendar.MONTH) == calNow.get(Calendar.MONTH)
                    && calInDate.get(Calendar.DAY_OF_MONTH) == calNow.get(Calendar.DAY_OF_MONTH)) {
                stDate = toDateString(date.getTime(), today);
            } else {
                stDate = toDateString(date.getTime(), other);
            }
        }
        return stDate;
    }

    public static String toDateString(Date date, DateFormat format) {
        if (date == null)
            return "(no date specified)";
        return format.format(date);
    }

    public static String getDateFormatStr() {
        return ST_FMT_DEFAULT_SHORT_FORMAT;
    }

    public static boolean isValidDate(String dateStr) {
        boolean retVal = false;
        try {
            toDate(dateStr);
            retVal = true;
        } catch (Exception ex) {
        }
        return retVal;
    }


    public static DateFormat getFormatter(String pattern, String stZone) {
        DateFormat dater;
        dater = (DateFormat) daters.get(pattern);
        if (dater == null) {
            // don't sync unless we are putting
            synchronized (daters) {
                dater = new SimpleDateFormat(pattern);
                dater.setTimeZone(TimeZone.getTimeZone(stZone));
                daters.put(pattern, dater);
            }
            dater.setTimeZone(TimeZone.getTimeZone(stZone));
        }
        return dater;
    }


    /**
     * gets end of day for a date object that is missing the current time
     */
    public static Timestamp getEndOfDay(Timestamp date) {
        return new Timestamp(date.getTime() + (MS_PER_DAY - 1L));

    }

    // Support GMT dates...
    public static Map daters = new HashMap();

    public static String formatDateGMT(java.util.Date date, String pattern) {
        if (date == null) return null;

        DateFormat dater = null;
        synchronized (daters) {
            dater = (DateFormat) daters.get(pattern);
            if (dater == null) {
                dater = new SimpleDateFormat(pattern);
                dater.setTimeZone(TIME_ZONE_GMT);
                daters.put(pattern, dater);
            }
        }
        return dater.format(date);
    }

    public static final List DISPLAY_DAYS = new Vector();

    static {
        for (int idx = 1; idx <= 31; idx++) {
            DISPLAY_DAYS.add(new IntegerBean(idx));
        }
    }

    public static final List DISPLAY_YEARS = new Vector();

    static {
        java.util.Calendar cal = Calendar.getInstance(TIME_ZONE_GMT);
        int thisYear = cal.get(Calendar.YEAR);
        for (int year = thisYear - 5; year <= (thisYear + 1); year++) {
            DISPLAY_YEARS.add(new IntegerBean(year));
        }
    }

    public static final List DISPLAY_MONTHS = new Vector();

    static {
//        java.util.Calendar cal = Calendar.getInstance(TIME_ZONE_GMT);
        DISPLAY_MONTHS.add(new MonthBean(0, "month.jan", "January"));
        DISPLAY_MONTHS.add(new MonthBean(1, "month.feb", "February"));
        DISPLAY_MONTHS.add(new MonthBean(2, "month.mar", "March"));
        DISPLAY_MONTHS.add(new MonthBean(3, "month.apr", "April"));
        DISPLAY_MONTHS.add(new MonthBean(4, "month.may", "May"));
        DISPLAY_MONTHS.add(new MonthBean(5, "month.jun", "June"));
        DISPLAY_MONTHS.add(new MonthBean(6, "month.jul", "July"));
        DISPLAY_MONTHS.add(new MonthBean(7, "month.aug", "August"));
        DISPLAY_MONTHS.add(new MonthBean(8, "month.sep", "September"));
        DISPLAY_MONTHS.add(new MonthBean(9, "month.oct", "October"));
        DISPLAY_MONTHS.add(new MonthBean(10, "month.nov", "November"));
        DISPLAY_MONTHS.add(new MonthBean(11, "month.dec", "December"));
    }

    public static String timeDeltaToDHMSString(long deltaTimeInSeconds) {
//        Calendar cal = Calendar.getInstance();
//        cal.setTimeInMillis(t);
//        cal.get(Calendar.)

        long days = -1;
        long hours = -1;
        long minutes = -1;
        long seconds = -1;
        try {
            int SECS_PER_MINUTE = 60;
            int SECS_PER_HOUR = 3600;
            int SECS_PER_DAY = SECS_PER_HOUR * 24;

            days = deltaTimeInSeconds / (SECS_PER_DAY);
            long daySeconds = days * SECS_PER_DAY;
            hours = (deltaTimeInSeconds - daySeconds) / SECS_PER_HOUR;
            long hourSeconds = hours * SECS_PER_HOUR;
            minutes = (deltaTimeInSeconds - daySeconds - hourSeconds) / SECS_PER_MINUTE;
            long minuteSeconds = minutes * SECS_PER_MINUTE;
            seconds = deltaTimeInSeconds - daySeconds - hourSeconds - minuteSeconds;
        } catch (RuntimeException e) {
            LogHelper.error(Dates.class, "Oops - computing HMS", e);
        }

//        double hoursFloor = Math.IEEEremainder(timeSeconds, 1000);

        return
                (days > 0 ? (days + " day" + plural(days) + ", ") : "") +
                        (hours > 0 ? (hours + " hour" + plural(hours) + ", ") : "") +
                        (minutes > 0 ? minutes + " minute" + plural(minutes) + ", " : "") +
                        seconds + " second" + plural(seconds);
    }

    private static String plural(long value) {
        return value == 1 ? "" : "s";
    }

    public static class MonthBean {
        int monthNum;
        String monthKey;
        String defaultName;

        public MonthBean(int monthNum, String monthKey, String defaultName) {
            this.monthNum = monthNum;
            this.monthKey = monthKey;
            this.defaultName = defaultName;
        }

        public String getMonthKey() {
            return monthKey;
        }

        public void setMonthKey(String monthKey) {
            this.monthKey = monthKey;
        }

        public int getMonthNum() {
            return monthNum;
        }

        public int getMonthNumOneBased() {
            return monthNum;
        }

        public int getMonthNumZeroBased() {
            return monthNum - 1;
        }

        public void setMonthNum(int monthNum) {
            this.monthNum = monthNum;
        }

        public String getDefaultName() {
            return defaultName;
        }

        public void setDefaultName(String defaultName) {
            this.defaultName = defaultName;
        }
    }

    public static class IntegerBean {
        int value;

        public IntegerBean(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}