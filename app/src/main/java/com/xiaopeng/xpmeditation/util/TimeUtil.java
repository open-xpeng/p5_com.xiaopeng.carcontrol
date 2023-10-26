package com.xiaopeng.xpmeditation.util;

import android.text.format.DateFormat;
import android.text.format.Time;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.lib.framework.netchannelmodule.http.xmart.bizapi.BizConstants;
import com.xiaopeng.lib.utils.info.BuildInfoUtils;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import org.eclipse.paho.client.mqttv3.MqttTopic;

/* loaded from: classes2.dex */
public class TimeUtil {
    public static final long MILLIS_IN_DAY = 86400000;
    public static final long MILLIS_OF_SECOND = 1000;
    public static final long MINUTES_OF_HOUR = 60;
    public static final long ONE_DAY = 86400000;
    private static final String ONE_DAY_AGO = "天前";
    public static final long ONE_HOUR = 3600000;
    private static final String ONE_HOUR_AGO = "小时前";
    public static final long ONE_MINUTE = 60000;
    private static final String ONE_MINUTE_AGO = "分钟前";
    public static final long ONE_YEAR = 31536000000L;
    public static final int SECONDS_IN_DAY = 86400;
    public static final long SECONDS_OF_MINUTE = 60;
    private static final String SEVEN_DAY_AGO = "很久以前";
    public static final SimpleDateFormat DATE_FORMAT_DEFAULT = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public static final SimpleDateFormat DATE_FORMAT_YMD_HMS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat DATE_FORMAT_THIS_YEAR = new SimpleDateFormat("MM-dd HH:mm");
    public static final SimpleDateFormat DATE_FORMAT_YMD = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat DATE_FORMAT_MS = new SimpleDateFormat("mm:ss");
    public static final SimpleDateFormat DATE_FORMAT_HMS = new SimpleDateFormat("HH:mm:ss");
    public static final SimpleDateFormat DATE_FORMAT_HM = new SimpleDateFormat("HH:mm");
    public static final SimpleDateFormat DATE_FORMAT_HM_12 = new SimpleDateFormat("a h:mm");
    public static final SimpleDateFormat DATE_FORMAT_TIME_DETAIL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS");
    public static final SimpleDateFormat DATE_FORMAT_TIME_DETAIL_SLASH = new SimpleDateFormat("yyyy/MM/dd HH:mm");
    public static final SimpleDateFormat DATE_FORMAT_EMDHM_THIS_YEAR = new SimpleDateFormat("EEE MM/dd HH:mm");
    public static final SimpleDateFormat DATE_FORMAT_TIME_DETAIL_YMD = new SimpleDateFormat("yyyy/MM/dd");
    private static final SimpleDateFormat HOUR_OF_DAY = new SimpleDateFormat("HH:mm", Locale.CHINA);
    private static Date sFormatDate = new Date();
    private static String[] sMonths = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
    private static final Calendar CALENDAR = Calendar.getInstance();
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat();

    public static String parseTimeCurrentMillis(long time) {
        sFormatDate.setTime(time);
        return DATE_FORMAT_HMS.format(sFormatDate);
    }

    public static String parseTimeWithGMT(long time, SimpleDateFormat format) {
        sFormatDate.setTime(time);
        return format.format(sFormatDate);
    }

    public static String parseTimeHMSWithGMT(long time) {
        return parseTimeWithGMT(time, DATE_FORMAT_HMS);
    }

    public static String parseTimeMSWithGMT(long time) {
        return parseTimeWithGMT(time, DATE_FORMAT_MS);
    }

    public static String parseTimeHMWithGMT(long time) {
        if (DateFormat.is24HourFormat(App.getInstance().getApplicationContext())) {
            return parseTimeWithGMT(time, DATE_FORMAT_HM);
        }
        return parseTimeWithGMT(time, DATE_FORMAT_HM_12);
    }

    public static String parseTimeYMD(long time) {
        return parseTimeWithGMT(time, DATE_FORMAT_TIME_DETAIL_YMD);
    }

    public static String parseTimeThisYear(long time) {
        return parseTimeWithGMT(time, DATE_FORMAT_THIS_YEAR);
    }

    public static String parseTimeYMDHM(long time) {
        return parseTimeWithGMT(time, DATE_FORMAT_TIME_DETAIL_YMD);
    }

    public static String parseTimeAsShortAsPossibleWithGMT(long time) {
        if (((time / 60) / 60) / 1000 > 0) {
            return parseTimeHMSWithGMT(time);
        }
        return parseTimeMSWithGMT(time);
    }

    public static Date parseHMTime(String time) {
        try {
            return DATE_FORMAT_HM.parse(time);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isInTimeline(long current, String start, String end) {
        boolean z = false;
        try {
            SimpleDateFormat simpleDateFormat = DATE_FORMAT_HM;
            Date parse = simpleDateFormat.parse(start);
            Date parse2 = simpleDateFormat.parse(end);
            Time time = new Time();
            time.set(current);
            Time time2 = new Time();
            time2.set(current);
            time2.hour = parse.getHours();
            time2.minute = parse.getMinutes();
            Time time3 = new Time();
            time3.set(current);
            time3.hour = parse2.getHours();
            time3.minute = parse2.getMinutes();
            if (time2.after(time3)) {
                time2.set(time2.toMillis(true) - 86400000);
                boolean z2 = time.after(time2) && time.before(time3);
                Time time4 = new Time();
                time4.set(time2.toMillis(true) + 86400000);
                if (time.after(time4)) {
                    return true;
                }
                return z2;
            }
            if (time.after(time2) && time.before(time3)) {
                z = true;
            }
            return z;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static long getSeconds(long milliseconds) {
        return (milliseconds - (toMinutes(milliseconds) * 60000)) / 1000;
    }

    public static long toMinutes(long milliseconds) {
        return milliseconds / 60000;
    }

    public static long toHours(long milliseconds) {
        return milliseconds / ONE_HOUR;
    }

    public static long toDays(long date) {
        return toHours(date) / 24;
    }

    public static long getMinutes(long milliseconds) {
        return (milliseconds - (((toHours(milliseconds) * 1000) * 60) * 60)) / 60000;
    }

    public static long getHours(long milliseconds) {
        return ((milliseconds / ONE_HOUR) + 8) % 24;
    }

    public static String getMSFormatTime(long milliseconds) {
        return String.format("%02d:%02d", Long.valueOf(getMinutes(milliseconds)), Long.valueOf(getSeconds(milliseconds)));
    }

    public static String getHMFormatTime(long milliseconds) {
        return String.format("%02d:%02d", Long.valueOf(getHours(milliseconds)), Long.valueOf(getMinutes(milliseconds)));
    }

    public static String getHMAMPMFormatTime(long milliseconds) {
        long hours = getHours(milliseconds);
        if (hours >= 12) {
            hours -= 12;
        }
        return String.format("%02d:%02d", Long.valueOf(hours), Long.valueOf(getMinutes(milliseconds)));
    }

    public static String getPropHMSFormatTime(long milliseconds) {
        long hours = getHours(milliseconds);
        return hours == 0 ? getMSFormatTime(milliseconds) : String.format("%02d:%02d:%02d", Long.valueOf(hours), Long.valueOf(getMinutes(milliseconds)), Long.valueOf(getSeconds(milliseconds)));
    }

    public static String getFullHMSFormatTime(long milliseconds) {
        return String.format("%02d:%02d:%02d", Long.valueOf(getHours(milliseconds)), Long.valueOf(getMinutes(milliseconds)), Long.valueOf(getSeconds(milliseconds)));
    }

    public static String getFormattedTime(String formatStr, long milliseconds) {
        Calendar calendar = CALENDAR;
        calendar.setTimeInMillis(milliseconds);
        SimpleDateFormat simpleDateFormat = DATE_FORMAT;
        simpleDateFormat.applyLocalizedPattern(formatStr);
        return simpleDateFormat.format(calendar.getTime());
    }

    public static boolean isToyear(long milliseconds) {
        Calendar calendar = CALENDAR;
        calendar.setTimeInMillis(milliseconds);
        int i = calendar.get(1);
        calendar.setTimeInMillis(System.currentTimeMillis());
        return i == calendar.get(1);
    }

    public static String parseTimeWithMilliseconds(long milliseconds) {
        Calendar calendar = CALENDAR;
        calendar.setTimeInMillis(milliseconds);
        int i = calendar.get(1);
        int i2 = calendar.get(5);
        StringBuilder sb = new StringBuilder();
        sb.append(i).append(MqttTopic.TOPIC_LEVEL_SEPARATOR).append(calendar.get(2) + 1).append(MqttTopic.TOPIC_LEVEL_SEPARATOR).append(i2);
        return sb.toString();
    }

    public static String getLastLoginAtTimeStr(long timeStamp) {
        return getTimeStr(timeStamp * 1000);
    }

    public static String getTimeStr(long timeStamp) {
        long time = new Date().getTime() - timeStamp;
        if (time < 60000) {
            return "1分钟前";
        }
        if (time < ONE_HOUR) {
            long minutes = toMinutes(time);
            return (minutes > 0 ? minutes : 1L) + ONE_MINUTE_AGO;
        } else if (time < 86400000) {
            return ((int) Math.rint(((float) toMinutes(time)) / 60.0f)) + ONE_HOUR_AGO;
        } else if (time < 604800000) {
            long days = toDays(time);
            return (days > 0 ? days : 1L) + ONE_DAY_AGO;
        } else {
            return SEVEN_DAY_AGO;
        }
    }

    public static boolean isSameDayOfMillis(final long ms1, final long ms2) {
        long j = ms1 - ms2;
        return j < 86400000 && j > -86400000 && toDay(ms1) == toDay(ms2);
    }

    public static boolean isYesterday(long time) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date parse = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
            if (time < parse.getTime()) {
                return time > parse.getTime() - 86400000;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static long toDay(long millis) {
        return (millis + TimeZone.getDefault().getOffset(millis)) / 86400000;
    }

    public static long getOldDate(int distanceDay) {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(10, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        calendar.set(5, calendar.get(5) + distanceDay);
        return calendar.getTimeInMillis();
    }

    public static String getToDayOfWeek() {
        switch (Calendar.getInstance().get(7)) {
            case 1:
                return "7";
            case 2:
                return "1";
            case 3:
                return "2";
            case 4:
                return BizConstants.CLIENT_TYPE_CDU;
            case 5:
                return BuildInfoUtils.BID_LAN;
            case 6:
                return BuildInfoUtils.BID_PT_SPECIAL_1;
            case 7:
                return BuildInfoUtils.BID_PT_SPECIAL_2;
            default:
                return "";
        }
    }

    public static String getYesterDayOfWeek() {
        switch (Calendar.getInstance().get(7)) {
            case 1:
                return BuildInfoUtils.BID_PT_SPECIAL_2;
            case 2:
                return "7";
            case 3:
                return "1";
            case 4:
                return "2";
            case 5:
                return BizConstants.CLIENT_TYPE_CDU;
            case 6:
                return BuildInfoUtils.BID_LAN;
            case 7:
                return BuildInfoUtils.BID_PT_SPECIAL_1;
            default:
                return "";
        }
    }

    public static long getTodayTweTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(10, 12);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        return calendar.getTimeInMillis();
    }

    public static int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(2) + 1;
    }

    public static int getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(5);
    }

    public static String getDayString(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int i = calendar.get(5);
        if (i <= 9) {
            return "0" + i;
        }
        return i + "";
    }

    public static String getMonthString(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int i = calendar.get(2);
        String[] strArr = sMonths;
        if (i >= strArr.length) {
            return strArr[0];
        }
        return strArr[i];
    }

    public static long getDayMillis(Date date) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        calendar.setTime(date);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        return calendar.getTimeInMillis();
    }
}
