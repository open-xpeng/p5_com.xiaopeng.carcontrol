package com.xiaopeng.lib.apirouter.server;

import android.os.IBinder;
import android.util.Log;
import android.util.Pair;
import java.lang.reflect.Field;
import java.util.HashMap;

/* loaded from: classes2.dex */
class ManifestHelperMapping {
    private static final String HELPER_CLASS_NAME = "com.xiaopeng.lib.apirouter.server.ManifestHelper";
    private static final String MAPPING = "mapping";

    ManifestHelperMapping() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static HashMap<String, Pair<IBinder, String>> reflectMapping() {
        Field field;
        try {
            Class<?> cls = Class.forName(HELPER_CLASS_NAME);
            if (cls == null || (field = cls.getField(MAPPING)) == null) {
                return null;
            }
            return (HashMap) field.get(cls);
        } catch (Exception e) {
            Log.e("AutoCodeMatcher", "reflectMapping", e);
            return null;
        }
    }
}
