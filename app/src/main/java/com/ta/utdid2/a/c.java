package com.ta.utdid2.a;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import com.ta.utdid2.b.a.d;
import com.ta.utdid2.b.a.h;
import com.ta.utdid2.b.a.i;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: AidStorageController.java */
/* loaded from: classes.dex */
public class c {
    private static final String TAG = "com.ta.utdid2.a.c";
    private static Map<String, String> a = new ConcurrentHashMap();
    private static Map<String, Long> b = new ConcurrentHashMap();

    public static void a(Context context, String str, String str2, String str3) {
        if (context == null) {
            Log.e(TAG, "no context!");
            return;
        }
        String c = c(str, str3);
        long currentTimeMillis = System.currentTimeMillis();
        a.put(c, str2);
        b.put(c, Long.valueOf(currentTimeMillis));
        SharedPreferences sharedPreferences = context.getSharedPreferences("OfJbkLdFbPOMbGyP", 0);
        if (Build.VERSION.SDK_INT >= 9) {
            h.a(sharedPreferences.edit().putString("EvQwnbilKezpOJey".concat(c), str2));
            h.a(sharedPreferences.edit().putLong("rKrMJgyAEbVtSQGi".concat(c), currentTimeMillis));
            return;
        }
        sharedPreferences.edit().putString("EvQwnbilKezpOJey".concat(c), str2).commit();
        sharedPreferences.edit().putLong("rKrMJgyAEbVtSQGi".concat(c), currentTimeMillis).commit();
    }

    /* renamed from: a  reason: collision with other method in class */
    public static String m71a(Context context, String str, String str2) {
        if (context == null) {
            Log.e(TAG, "no context!");
            return "";
        }
        String c = c(str, str2);
        String str3 = a.get(c);
        if (d.e) {
            Log.d(TAG, "cache AID:" + str3);
        }
        if (i.m74a(str3)) {
            String string = context.getSharedPreferences("OfJbkLdFbPOMbGyP", 0).getString("EvQwnbilKezpOJey".concat(c), "");
            a.put(c, string);
            return string;
        }
        return str3;
    }

    public static long a(Context context, String str, String str2) {
        if (context == null) {
            Log.e(TAG, "no context!");
            return 0L;
        }
        String c = c(str, str2);
        Long valueOf = Long.valueOf(b.containsKey(c) ? b.get(c).longValue() : 0L);
        if (d.e) {
            Log.d(TAG, "cache AIDGenTime:" + valueOf);
        }
        if (valueOf.longValue() == 0) {
            valueOf = Long.valueOf(context.getSharedPreferences("OfJbkLdFbPOMbGyP", 0).getLong("rKrMJgyAEbVtSQGi".concat(c), 0L));
            b.put(c, valueOf);
        }
        return valueOf.longValue();
    }

    private static String c(String str, String str2) {
        String encodeToString;
        if (Build.VERSION.SDK_INT >= 8) {
            encodeToString = com.ta.utdid2.b.a.c.encodeToString(str.concat(str2).getBytes(), 2);
        } else {
            encodeToString = com.ta.utdid2.b.a.b.encodeToString(str.concat(str2).getBytes(), 2);
        }
        if (d.e) {
            Log.d(TAG, "encodedName:" + encodeToString);
        }
        return encodeToString;
    }
}
