package com.xiaopeng.lludancemanager;

import android.icu.text.SimpleDateFormat;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.util.LogUtils;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/* loaded from: classes2.dex */
public class LluDanceOrderTimeHelper {
    private static final String TAG = "LluDanceOrderTimeHelper";
    private static final SimpleDateFormat sOrderTimeFormat = new SimpleDateFormat("HH:mm");

    public static String getDateFormatForTimeInMills(long timeInMills) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMills);
        return sOrderTimeFormat.format(calendar.getTime());
    }

    public static long parseToTimeInMillsFromString(String timeString, boolean autoNextDay) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTimeInMillis(sOrderTimeFormat.parse(timeString).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar2 = Calendar.getInstance();
        int i = calendar.get(11);
        int i2 = calendar2.get(11);
        int i3 = calendar.get(12);
        int i4 = calendar2.get(12);
        int i5 = calendar.get(13);
        int i6 = calendar2.get(13);
        int i7 = calendar.get(14);
        int i8 = calendar2.get(14);
        String str = TAG;
        LogUtils.d(str, "parseToTimeInMillsFromString        ordered hour = " + i + "      current hour = " + i2 + "       ordered minute = " + i3 + "      current minute = " + i4 + "       ordered second = " + i5 + "       current second = " + i6 + "       ordered millisecond = " + i7 + "     current millisecond = " + i8);
        if (autoNextDay && (i < i2 || ((i == i2 && i3 < i4) || ((i == i2 && i3 == i4 && i5 < i6) || (i == i2 && i3 == i4 && i5 == i6 && i7 < i8))))) {
            calendar2.set(6, calendar2.get(6) + 1);
            LogUtils.d(str, "order play time to next day, target day = " + calendar2.get(6));
        }
        calendar2.set(11, i);
        calendar2.set(12, i3);
        LogUtils.d(str, "order play time ordered hour = " + i + "    minute = " + i3);
        calendar2.set(13, 0);
        calendar2.set(14, 0);
        return calendar2.getTimeInMillis();
    }

    public static long parseToTimeInMillsFromString(String timeString) {
        return parseToTimeInMillsFromString(timeString, true);
    }

    public static String getTimeLeftFormat(long timeInMills) {
        return String.format(Locale.getDefault(), App.getInstance().getString(R.string.llu_dance_fragment_order_time_count_down_formatter), Long.valueOf(TimeUnit.MILLISECONDS.toHours(timeInMills) % 60), Long.valueOf(TimeUnit.MILLISECONDS.toMinutes(timeInMills) % 60), Long.valueOf(TimeUnit.MILLISECONDS.toSeconds(timeInMills) % 60));
    }
}
