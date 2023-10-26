package com.xiaopeng.carcontrol.helper;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;
import com.xiaopeng.carcontrol.util.LogUtils;

/* loaded from: classes2.dex */
public class AlarmHelper {
    private static final String TAG = "AlarmHelper";

    public static void setAlarmForPendingIntent(Context context, long alarmTime, PendingIntent pIntent) {
        ((AlarmManager) context.getSystemService(NotificationCompat.CATEGORY_ALARM)).setExact(0, alarmTime, pIntent);
    }

    public static void cancelAlarmForPendingIntent(Context context, PendingIntent pIntent) {
        if (pIntent == null) {
            LogUtils.i(TAG, "cancel the pending intent ,called with a null pIntent");
            return;
        }
        ((AlarmManager) context.getSystemService(NotificationCompat.CATEGORY_ALARM)).cancel(pIntent);
        pIntent.cancel();
    }

    public static void createOrUpdateBroadcastIntent(Context context, long alarmTime, Intent intent, int requestCode) {
        setAlarmForPendingIntent(context, alarmTime, PendingIntent.getBroadcast(context, requestCode, intent, 134217728));
    }

    public static boolean isExistBroadcastIntent(Context context, Intent intent, int requestCode) {
        return PendingIntent.getBroadcast(context, requestCode, intent, 536870912) != null;
    }

    public static void cancelBroadcastIntent(Context context, Intent intent, int requestCode) {
        PendingIntent broadcast = PendingIntent.getBroadcast(context, requestCode, intent, 536870912);
        if (broadcast != null) {
            cancelAlarmForPendingIntent(context, broadcast);
        }
    }

    public static void createOrUpdateActivityIntent(Context context, long alarmTime, Intent intent, int requestCode) {
        setAlarmForPendingIntent(context, alarmTime, PendingIntent.getActivity(context, requestCode, intent, 134217728));
    }

    public static boolean isExistActivityIntent(Context context, Intent intent, int requestCode) {
        return PendingIntent.getActivity(context, requestCode, intent, 536870912) != null;
    }

    public static void cancelActivityIntent(Context context, Intent intent, int requestCode) {
        PendingIntent activity = PendingIntent.getActivity(context, requestCode, intent, 536870912);
        if (activity != null) {
            cancelAlarmForPendingIntent(context, activity);
        }
    }

    public static void createOrUpdateServiceIntent(Context context, long alarmTime, Intent intent, int requestCode) {
        setAlarmForPendingIntent(context, alarmTime, PendingIntent.getService(context, requestCode, intent, 134217728));
    }

    public static boolean isExistServiceIntent(Context context, Intent intent, int requestCode) {
        return PendingIntent.getService(context, requestCode, intent, 536870912) != null;
    }

    public static void cancelServiceIntent(Context context, Intent intent, int requestCode) {
        PendingIntent service = PendingIntent.getService(context, requestCode, intent, 536870912);
        if (service != null) {
            cancelAlarmForPendingIntent(context, service);
        }
    }
}
