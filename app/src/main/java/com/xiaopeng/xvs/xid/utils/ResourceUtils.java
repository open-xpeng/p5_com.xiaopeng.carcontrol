package com.xiaopeng.xvs.xid.utils;

import android.content.res.Resources;
import com.xiaopeng.xvs.xid.XId;

/* loaded from: classes2.dex */
public class ResourceUtils {
    public static String getString(int i, Object... objArr) {
        return XId.getApplication().getResources().getString(i, objArr);
    }

    public static boolean getBoolean(int i) {
        return XId.getApplication().getResources().getBoolean(i);
    }

    public static Resources getResources() {
        return XId.getApplication().getResources();
    }
}
