package com.xiaopeng.lludancemanager.helper;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import com.xiaopeng.carcontrol.helper.AlarmHelper;
import com.xiaopeng.carcontrol.util.LogUtils;

/* loaded from: classes2.dex */
public class LluDanceAlarmHelper {
    public static final String ACTIVITY_CHANGED_RECEIVER_ACTION = "com.xiaopeng.intent.action.ACTIVITY_CHANGED";
    public static final String DANCE_ORDER_TIME_ALARM_ACTION = "com.xiaopeng.llu.dance.ALARM_TIME_OUT";
    public static final String EXTRA_WINDOW_LEVEL = "android.intent.extra.WINDOW_LEVEL";
    public static final String ORDER_PLAY_DANCE_ITEM_RES_EFFECT_NAME = "order_play_dance_item_res_effect_name";
    public static final String ORDER_PLAY_DANCE_ITEM_RES_ID = "order_play_dance_item_res_id";
    public static final String ORDER_PLAY_DANCE_ITEM_TIME = "order_play_dance_item_time";
    public static final String ORDER_PLAY_DANCE_TITLE = "order_play_dance_item_title";
    private static final int ORDER_PLAY_TIME_REQUEST_CODE = 1;
    private static final String TAG = "LluDanceAlarmHelper";

    private static Intent buildIntentForDanceAlarm() {
        return new Intent(DANCE_ORDER_TIME_ALARM_ACTION);
    }

    public static void setAlarmForDance(Context context, String resId, String effectName, long orderedTime, String title) {
        Intent buildIntentForDanceAlarm = buildIntentForDanceAlarm();
        buildIntentForDanceAlarm.putExtra(ORDER_PLAY_DANCE_ITEM_RES_ID, resId);
        buildIntentForDanceAlarm.putExtra(ORDER_PLAY_DANCE_ITEM_RES_EFFECT_NAME, effectName);
        buildIntentForDanceAlarm.putExtra(ORDER_PLAY_DANCE_ITEM_TIME, orderedTime);
        buildIntentForDanceAlarm.putExtra(ORDER_PLAY_DANCE_TITLE, title);
        String str = TAG;
        LogUtils.d(str, "schedule to play res id = " + resId);
        AlarmHelper.createOrUpdateBroadcastIntent(context, orderedTime, buildIntentForDanceAlarm, 1);
        LogUtils.d(str, "time sync for llu dance, system current time = " + System.currentTimeMillis() + "trigger at millis = " + orderedTime);
    }

    public static PendingIntent alarmUpForDance(Context context) {
        PendingIntent broadcast = PendingIntent.getBroadcast(context, 1, buildIntentForDanceAlarm(), 536870912);
        LogUtils.d(TAG, "alarm up = " + broadcast);
        return broadcast;
    }

    public static void cancelAlarmForDance(Context context) {
        AlarmHelper.cancelBroadcastIntent(context, buildIntentForDanceAlarm(), 1);
        LogUtils.d(TAG, "cancel alarm for dance");
    }
}
