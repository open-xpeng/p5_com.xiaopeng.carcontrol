package com.xiaopeng.carcontrol;

import com.xiaopeng.carcontrol.util.LogUtils;

/* loaded from: classes.dex */
public class SmartSeatUtil {
    private static String TAG = "SmartSeatUtil";
    private static int[] pos = {0, 0, 0};

    public native void getPosFromHW(int g, int h, int w, int[] pos2);

    public native String stringFromJNI();

    static {
        System.loadLibrary("native-lib");
        LogUtils.d(TAG, "SmartSeatUtil load so successfully!");
    }

    public int[] getPosition(int gender, int height, int weight) {
        LogUtils.d(TAG, stringFromJNI());
        getPosFromHW(gender, height, weight, pos);
        return pos;
    }
}
