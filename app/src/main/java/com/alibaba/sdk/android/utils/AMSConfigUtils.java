package com.alibaba.sdk.android.utils;

import android.content.Context;
import android.util.Log;
import androidx.constraintlayout.core.motion.utils.TypedValues;

/* loaded from: classes.dex */
public class AMSConfigUtils {
    private static final String ACCOUNT_ID = "ams_accountId";
    private static final String APP_KEY = "ams_appKey";
    private static final String APP_SECRET = "ams_appSecret";
    private static final String HTTPDNS_SECRET_KEY = "ams_httpdns_secretKey";
    private static final String PACKAGE_NAME = "ams_packageName";

    private static int getResourceString(Context context, String str) {
        return context.getResources().getIdentifier(str, TypedValues.Custom.S_STRING, context.getPackageName());
    }

    public static String getStringStr(Context context, String str) {
        try {
            return context.getResources().getString(getResourceString(context, str));
        } catch (Exception unused) {
            Log.e("AMSConfigUtils", str + " is NULL");
            return null;
        }
    }

    public static String getAccountId(Context context) {
        return getStringStr(context, ACCOUNT_ID);
    }

    public static String getHttpdnsSecretKey(Context context) {
        return getStringStr(context, HTTPDNS_SECRET_KEY);
    }

    public static String getAppKey(Context context) {
        return getStringStr(context, APP_KEY);
    }

    public static String getAppSecret(Context context) {
        return getStringStr(context, APP_SECRET);
    }

    public static String getPackageName(Context context) {
        return getStringStr(context, PACKAGE_NAME);
    }
}
