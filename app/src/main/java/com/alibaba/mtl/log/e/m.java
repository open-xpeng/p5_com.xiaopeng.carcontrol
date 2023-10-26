package com.alibaba.mtl.log.e;

import android.content.Context;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import java.io.UnsupportedEncodingException;
import java.util.Random;

/* compiled from: PhoneInfoUtils.java */
/* loaded from: classes.dex */
public class m {
    private static final Random a = new Random();

    public static final String getUniqueID() {
        Random random = a;
        int nextInt = random.nextInt();
        int nextInt2 = random.nextInt();
        byte[] bytes = f.getBytes((int) (System.currentTimeMillis() / 1000));
        byte[] bytes2 = f.getBytes((int) System.nanoTime());
        byte[] bytes3 = f.getBytes(nextInt);
        byte[] bytes4 = f.getBytes(nextInt2);
        byte[] bArr = new byte[16];
        System.arraycopy(bytes, 0, bArr, 0, 4);
        System.arraycopy(bytes2, 0, bArr, 4, 4);
        System.arraycopy(bytes3, 0, bArr, 8, 4);
        System.arraycopy(bytes4, 0, bArr, 12, 4);
        return c.encodeToString(bArr, 2);
    }

    public static String getImei(Context context) {
        String str = null;
        if (context != null) {
            try {
                String string = context.getSharedPreferences("UTCommon", 0).getString("_ie", "");
                if (!TextUtils.isEmpty(string)) {
                    String str2 = new String(c.decode(string.getBytes(), 2), "UTF-8");
                    if (!TextUtils.isEmpty(str2)) {
                        return str2;
                    }
                }
            } catch (Exception unused) {
            }
            try {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
                if (telephonyManager != null) {
                    str = telephonyManager.getDeviceId();
                }
            } catch (Exception unused2) {
            }
        }
        if (TextUtils.isEmpty(str)) {
            str = getUniqueID();
        }
        if (context != null) {
            try {
                SharedPreferences.Editor edit = context.getSharedPreferences("UTCommon", 0).edit();
                edit.putString("_ie", new String(c.encode(str.getBytes("UTF-8"), 2)));
                edit.commit();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return str;
    }

    public static String getImsi(Context context) {
        String str = null;
        if (context != null) {
            try {
                String string = context.getSharedPreferences("UTCommon", 0).getString("_is", "");
                if (!TextUtils.isEmpty(string)) {
                    String str2 = new String(c.decode(string.getBytes(), 2), "UTF-8");
                    if (!TextUtils.isEmpty(str2)) {
                        return str2;
                    }
                }
            } catch (Exception unused) {
            }
            try {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
                if (telephonyManager != null) {
                    str = telephonyManager.getSubscriberId();
                }
            } catch (Exception unused2) {
            }
        }
        if (TextUtils.isEmpty(str)) {
            str = getUniqueID();
        }
        if (context != null) {
            try {
                SharedPreferences.Editor edit = context.getSharedPreferences("UTCommon", 0).edit();
                edit.putString("_is", new String(c.encode(str.getBytes("UTF-8"), 2)));
                edit.commit();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return str;
    }
}
