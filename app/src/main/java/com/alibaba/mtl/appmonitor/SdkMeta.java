package com.alibaba.mtl.appmonitor;

import android.content.Context;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.alibaba.mtl.log.e.i;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class SdkMeta {
    public static final String SDK_VERSION = "2.6.4.4_for_bc";
    private static final Map<String, String> d;

    public static Map<String, String> getSDKMetaData() {
        com.alibaba.mtl.log.a.getContext();
        Map<String, String> map = d;
        if (!map.containsKey("sdk-version")) {
            map.put("sdk-version", SDK_VERSION);
        }
        return map;
    }

    static {
        HashMap hashMap = new HashMap();
        d = hashMap;
        hashMap.put("sdk-version", SDK_VERSION);
    }

    public static void setExtra(Map<String, String> map) {
        if (map != null) {
            d.putAll(map);
        }
    }

    public static String getString(Context context, String str) {
        if (context == null) {
            return null;
        }
        int i = 0;
        try {
            i = context.getResources().getIdentifier(str, TypedValues.Custom.S_STRING, context.getPackageName());
        } catch (Throwable th) {
            i.a("SdkMeta", "getString Id error", th);
        }
        if (i != 0) {
            return context.getString(i);
        }
        return null;
    }
}
