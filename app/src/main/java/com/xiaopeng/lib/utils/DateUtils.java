package com.xiaopeng.lib.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/* loaded from: classes2.dex */
public class DateUtils {
    private static SimpleDateFormat sDailySimpleFormat = new SimpleDateFormat("MM.dd");
    private static SimpleDateFormat sMonthlySimpleFormat = new SimpleDateFormat("yyyy.MM");
    private static SimpleDateFormat sSimpleFormat1 = new SimpleDateFormat("MM.dd");
    private static SimpleDateFormat sSimpleFormat2 = new SimpleDateFormat("MM月dd日");
    private static SimpleDateFormat sSimpleFormat3 = new SimpleDateFormat("yyyy年MM月dd日");
    private static SimpleDateFormat sSimpleFormat4 = new SimpleDateFormat("yyyy.MM.dd");
    private static SimpleDateFormat sSimpleFormat5 = new SimpleDateFormat("HH:mm:ss");
    private static SimpleDateFormat sTimeSimpleFormat = new SimpleDateFormat("HH:mm");
    private static SimpleDateFormat sBirthdaySimpleFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat sSimpleFormat6 = new SimpleDateFormat("yyMMddHHmmss");
    private static SimpleDateFormat sSimpleFormat7 = new SimpleDateFormat("yyyy/MM/dd");
    private static SimpleDateFormat sSimpleFormat8 = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
    private static SimpleDateFormat sSimpleFormat9 = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
    private static SimpleDateFormat sSimpleFormat10 = new SimpleDateFormat("yyyyMMdd_HHmmss");
    private static SimpleDateFormat sSimpleFormat11 = new SimpleDateFormat("dd MMM yyyy");
    private static SimpleDateFormat sSimpleFormat12 = new SimpleDateFormat("dd.MMM yyyy");
    private static SimpleDateFormat sSimpleFormat13 = new SimpleDateFormat("dd MMM");
    private static SimpleDateFormat sSimpleFormat14 = new SimpleDateFormat("dd.MMM");
    private static SimpleDateFormat sSimpleFormat15 = new SimpleDateFormat("E,dd MMM");
    private static SimpleDateFormat sSimpleFormat16 = new SimpleDateFormat("E,dd.MMM");

    public static synchronized String formatDate1(long j) {
        String format;
        synchronized (DateUtils.class) {
            format = sSimpleFormat1.format(Long.valueOf(j));
        }
        return format;
    }

    public static synchronized String formatDate2(long j) {
        synchronized (DateUtils.class) {
            if (isToday(j)) {
                return "今天";
            }
            if (isYesterday(j)) {
                return "昨天";
            }
            return sSimpleFormat2.format(Long.valueOf(j));
        }
    }

    public static synchronized String formatDate3(long j) {
        synchronized (DateUtils.class) {
            if (isToday(j)) {
                return "今天";
            }
            if (isYesterday(j)) {
                return "昨天";
            }
            return sSimpleFormat3.format(Long.valueOf(j));
        }
    }

    public static synchronized String formatDate4(long j) {
        String format;
        synchronized (DateUtils.class) {
            format = sBirthdaySimpleFormat.format(Long.valueOf(j));
        }
        return format;
    }

    public static synchronized String formatDate5(long j) {
        String format;
        synchronized (DateUtils.class) {
            format = sSimpleFormat5.format(Long.valueOf(j));
        }
        return format;
    }

    public static synchronized String formatDate6(long j) {
        String format;
        synchronized (DateUtils.class) {
            format = sSimpleFormat6.format(Long.valueOf(j));
        }
        return format;
    }

    public static synchronized String formatDate7(long j) {
        String format;
        synchronized (DateUtils.class) {
            format = sSimpleFormat7.format(Long.valueOf(j));
        }
        return format;
    }

    public static synchronized String formatDate8(long j) {
        String format;
        synchronized (DateUtils.class) {
            format = sSimpleFormat8.format(Long.valueOf(j));
        }
        return format;
    }

    public static synchronized String formatDate9(long j) {
        String format;
        synchronized (DateUtils.class) {
            format = sSimpleFormat9.format(Long.valueOf(j));
        }
        return format;
    }

    public static synchronized String formatDate10(long j) {
        String format;
        synchronized (DateUtils.class) {
            format = sSimpleFormat10.format(Long.valueOf(j));
        }
        return format;
    }

    public static synchronized long dateToStamp(String str) {
        long j;
        synchronized (DateUtils.class) {
            try {
                j = sSimpleFormat10.parse(str).getTime();
            } catch (ParseException e) {
                e.printStackTrace();
                j = 0;
            }
        }
        return j;
    }

    public static long getDayBeginTimeInMillis(long j) {
        Calendar calendar = (Calendar) Calendar.getInstance().clone();
        calendar.setTimeInMillis(j);
        calendar.set(11, Calendar.getInstance().getActualMinimum(11));
        calendar.set(12, Calendar.getInstance().getActualMinimum(12));
        calendar.set(13, Calendar.getInstance().getActualMinimum(13));
        calendar.set(14, Calendar.getInstance().getActualMinimum(14));
        return calendar.getTimeInMillis();
    }

    public static Calendar getStartOfDay(Calendar calendar) {
        Calendar calendar2 = (Calendar) calendar.clone();
        calendar2.set(11, calendar.getActualMinimum(11));
        calendar2.set(12, calendar.getActualMinimum(12));
        calendar2.set(13, calendar.getActualMinimum(13));
        calendar2.set(14, calendar.getActualMinimum(14));
        return calendar2;
    }

    public static Calendar getEndOfDay(Calendar calendar) {
        Calendar calendar2 = (Calendar) calendar.clone();
        calendar2.set(11, calendar.getActualMaximum(11));
        calendar2.set(12, calendar.getActualMaximum(12));
        calendar2.set(13, calendar.getActualMaximum(13));
        calendar2.set(14, calendar.getActualMaximum(14));
        return calendar2;
    }

    public static synchronized String getDailyFormatString(long j) {
        String format;
        synchronized (DateUtils.class) {
            format = sDailySimpleFormat.format(Long.valueOf(j));
        }
        return format;
    }

    public static synchronized String getDailyFormatString(Calendar calendar) {
        String format;
        synchronized (DateUtils.class) {
            format = sDailySimpleFormat.format(Long.valueOf(calendar.getTimeInMillis()));
        }
        return format;
    }

    public static synchronized String getBirthdayFormatString(long j) {
        String format;
        synchronized (DateUtils.class) {
            format = sBirthdaySimpleFormat.format(Long.valueOf(j));
        }
        return format;
    }

    public static synchronized Date getBirthdayParseString(String str) {
        Date parse;
        synchronized (DateUtils.class) {
            try {
                parse = sBirthdaySimpleFormat.parse(str);
            } catch (ParseException e) {
                e.printStackTrace();
                return new Date();
            }
        }
        return parse;
    }

    public static boolean isToday(Calendar calendar) {
        return calendar.get(1) == Calendar.getInstance().get(1) && calendar.get(6) == Calendar.getInstance().get(6);
    }

    public static boolean isToday(long j) {
        Calendar calendar = (Calendar) Calendar.getInstance().clone();
        calendar.setTimeInMillis(j);
        return calendar.get(1) == Calendar.getInstance().get(1) && calendar.get(6) == Calendar.getInstance().get(6);
    }

    public static boolean isYesterday(long j) {
        Calendar calendar = (Calendar) Calendar.getInstance().clone();
        calendar.setTimeInMillis(j);
        return calendar.get(1) == Calendar.getInstance().get(1) && calendar.get(6) == Calendar.getInstance().get(6) - 1;
    }

    public static Calendar getMondayOfWeek(Calendar calendar) {
        Calendar calendar2 = (Calendar) calendar.clone();
        int i = calendar2.get(7) - 1;
        calendar2.add(5, (-(i != 0 ? i : 7)) + 1);
        calendar2.set(11, calendar2.getActualMinimum(11));
        calendar2.set(12, calendar2.getActualMinimum(12));
        calendar2.set(13, calendar2.getActualMinimum(13));
        calendar2.set(14, calendar2.getActualMinimum(14));
        return calendar2;
    }

    public static Calendar getSundayOfWeek(Calendar calendar) {
        Calendar calendar2 = (Calendar) calendar.clone();
        int i = calendar2.get(7) - 1;
        if (i == 0) {
            i = 7;
        }
        calendar2.add(5, (-i) + 7);
        calendar2.set(11, calendar2.getActualMaximum(11));
        calendar2.set(12, calendar2.getActualMaximum(12));
        calendar2.set(13, calendar2.getActualMaximum(13));
        calendar2.set(14, calendar2.getActualMaximum(14));
        return calendar2;
    }

    public static synchronized String getWeeklyFormatString(Calendar calendar) {
        String str;
        synchronized (DateUtils.class) {
            str = sDailySimpleFormat.format(Long.valueOf(getMondayOfWeek(calendar).getTimeInMillis())) + " - " + sDailySimpleFormat.format(Long.valueOf(getSundayOfWeek(calendar).getTimeInMillis()));
        }
        return str;
    }

    public static boolean isThisWeek(Calendar calendar) {
        return calendar.get(3) == Calendar.getInstance().get(3) && calendar.get(1) == Calendar.getInstance().get(1);
    }

    public static Calendar getStartOfMonth(Calendar calendar) {
        Calendar calendar2 = (Calendar) calendar.clone();
        calendar2.set(5, calendar2.getActualMinimum(5));
        calendar2.set(11, calendar2.getActualMinimum(11));
        calendar2.set(12, calendar2.getActualMinimum(12));
        calendar2.set(13, calendar2.getActualMinimum(13));
        calendar2.set(14, calendar2.getActualMinimum(14));
        return calendar2;
    }

    public static Calendar getEndOfMonth(Calendar calendar) {
        Calendar calendar2 = (Calendar) calendar.clone();
        calendar2.set(5, calendar2.getActualMaximum(5));
        calendar2.set(11, calendar2.getActualMaximum(11));
        calendar2.set(12, calendar2.getActualMaximum(12));
        calendar2.set(13, calendar2.getActualMaximum(13));
        calendar2.set(14, calendar2.getActualMaximum(14));
        return calendar2;
    }

    public static synchronized String getMonthlyFormatString(Calendar calendar) {
        String format;
        synchronized (DateUtils.class) {
            format = sMonthlySimpleFormat.format(Long.valueOf(calendar.getTimeInMillis()));
        }
        return format;
    }

    public static boolean isThisMonth(Calendar calendar) {
        return calendar.get(2) == Calendar.getInstance().get(2) && calendar.get(1) == Calendar.getInstance().get(1);
    }

    public static long getAccuracyToHour(long j) {
        Calendar calendar = (Calendar) Calendar.getInstance().clone();
        calendar.setTimeInMillis(j);
        calendar.set(14, calendar.getActualMinimum(14));
        calendar.set(13, calendar.getActualMinimum(13));
        calendar.set(12, calendar.getActualMinimum(12));
        return calendar.getTimeInMillis();
    }

    public static long getAccuracyToDay(long j) {
        Calendar calendar = (Calendar) Calendar.getInstance().clone();
        calendar.setTimeInMillis(j);
        calendar.set(14, calendar.getActualMinimum(14));
        calendar.set(13, calendar.getActualMinimum(13));
        calendar.set(12, calendar.getActualMinimum(12));
        calendar.set(11, calendar.getActualMinimum(11));
        return calendar.getTimeInMillis();
    }

    public static synchronized String getTimeFormatString(long j) {
        String format;
        synchronized (DateUtils.class) {
            format = sTimeSimpleFormat.format(Long.valueOf(j));
        }
        return format;
    }

    public static long getSomeMinuteAgo(Calendar calendar, int i) {
        Calendar calendar2 = (Calendar) calendar.clone();
        calendar2.add(12, i * (-1));
        return calendar2.getTime().getTime();
    }

    public static Date getSomeMinuteAfter(Calendar calendar, int i) {
        Calendar calendar2 = (Calendar) calendar.clone();
        calendar2.add(12, i);
        return calendar2.getTime();
    }

    public static List<Date> getBeforeAndAfterHalfHour(Date date) {
        ArrayList arrayList = new ArrayList();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(12, -30);
        Date time = calendar.getTime();
        calendar.add(12, 60);
        Date time2 = calendar.getTime();
        arrayList.add(time);
        arrayList.add(time2);
        return arrayList;
    }

    public static int dateInterval(long j, long j2) {
        if (j2 > j) {
            long j3 = j2 + j;
            j = j3 - j;
            j2 = j3 - j;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(j);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(j2);
        int i = calendar.get(1);
        int i2 = calendar2.get(1);
        int i3 = calendar.get(6);
        int i4 = calendar2.get(6);
        return i - i2 > 0 ? numerical(0, i3, i4, i, i2, calendar2) : i3 - i4;
    }

    public static int numerical(int i, int i2, int i3, int i4, int i5, Calendar calendar) {
        int i6 = i2 - i3;
        int i7 = i4 - i5;
        ArrayList arrayList = new ArrayList();
        if (calendar.getActualMaximum(6) == 366) {
            i6++;
        }
        for (int i8 = 0; i8 < i7; i8++) {
            calendar.set(1, calendar.get(1) + 1);
            int actualMaximum = calendar.getActualMaximum(6);
            if (actualMaximum != 366) {
                i6 += actualMaximum;
            } else {
                arrayList.add(Integer.valueOf(actualMaximum));
            }
            if (i8 == i7 - 1 && i7 > 1 && actualMaximum == 366) {
                i6--;
            }
        }
        for (int i9 = 0; i9 < arrayList.size(); i9++) {
            if (arrayList.size() >= 1) {
                i6 += ((Integer) arrayList.get(i9)).intValue();
            }
        }
        return i6;
    }

    public static String getYearMonthAndDayFormatString(Locale locale, long j) {
        String country = locale.getCountry();
        country.hashCode();
        if (country.equals("AT") || country.equals("DE")) {
            return sSimpleFormat12.format(Long.valueOf(j));
        }
        return sSimpleFormat11.format(Long.valueOf(j));
    }

    public static String getMonthAndDayFormatString(Locale locale, long j) {
        String country = locale.getCountry();
        country.hashCode();
        if (country.equals("AT") || country.equals("DE")) {
            return sSimpleFormat14.format(Long.valueOf(j));
        }
        return sSimpleFormat13.format(Long.valueOf(j));
    }

    public static String getWeekAndMonthAndDayFormatString(Locale locale, long j) {
        String country = locale.getCountry();
        country.hashCode();
        if (country.equals("AT") || country.equals("DE")) {
            return sSimpleFormat16.format(Long.valueOf(j));
        }
        return sSimpleFormat15.format(Long.valueOf(j));
    }
}
