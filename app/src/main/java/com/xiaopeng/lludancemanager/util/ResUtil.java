package com.xiaopeng.lludancemanager.util;

import android.content.Context;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.util.LogUtils;

/* loaded from: classes2.dex */
public class ResUtil {
    public static final String DRAWABLE = "drawable";
    public static final String RAW = "raw";
    public static String TAG = "ResUtil";
    private static Context mContext = App.getInstance().getApplicationContext();

    public static int getDrawableResByName(String imgName) {
        return mContext.getResources().getIdentifier(imgName, DRAWABLE, mContext.getPackageName());
    }

    public static int getRawResByName(String rawName) {
        return mContext.getResources().getIdentifier(rawName, RAW, mContext.getPackageName());
    }

    public static String getString(int stringId) {
        if (stringId <= 0) {
            LogUtils.w(TAG, "Invalid String resource id");
            return null;
        }
        try {
            return mContext.getResources().getString(stringId);
        } catch (Exception unused) {
            LogUtils.e(TAG, "getString id=" + stringId + " failed, return null");
            return "";
        }
    }

    public static String getStringByIdentity(String identity) {
        return getString(mContext.getResources().getIdentifier(identity, TypedValues.Custom.S_STRING, App.getInstance().getPackageName()));
    }
}
