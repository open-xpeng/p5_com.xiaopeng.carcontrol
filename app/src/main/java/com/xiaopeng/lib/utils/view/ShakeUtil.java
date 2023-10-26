package com.xiaopeng.lib.utils.view;

import android.util.SparseLongArray;

/* loaded from: classes2.dex */
public class ShakeUtil {
    private static SparseLongArray mHashMap = new SparseLongArray();

    public static synchronized boolean canExecute(int i) {
        boolean z;
        synchronized (ShakeUtil.class) {
            long currentTimeMillis = System.currentTimeMillis();
            if (Math.abs(currentTimeMillis - mHashMap.get(i)) > 300) {
                z = true;
                mHashMap.put(i, currentTimeMillis);
            } else {
                z = false;
            }
        }
        return z;
    }

    public static synchronized boolean canExecuteLong(int i) {
        boolean z;
        synchronized (ShakeUtil.class) {
            long currentTimeMillis = System.currentTimeMillis();
            if (Math.abs(currentTimeMillis - mHashMap.get(i)) > 1000) {
                z = true;
                mHashMap.put(i, currentTimeMillis);
            } else {
                z = false;
            }
        }
        return z;
    }

    public static synchronized boolean canExecuteLong(int i, int i2) {
        boolean z;
        synchronized (ShakeUtil.class) {
            long currentTimeMillis = System.currentTimeMillis();
            if (Math.abs(currentTimeMillis - mHashMap.get(i)) > i2) {
                z = true;
                mHashMap.put(i, currentTimeMillis);
            } else {
                z = false;
            }
        }
        return z;
    }
}
