package com.xiaopeng.carcontrol.util;

import com.google.gson.Gson;
import java.lang.reflect.Type;

/* loaded from: classes2.dex */
public class GsonUtil {
    private static Gson sGSon = new Gson();

    public static <T> T fromJson(String json, Class<T> classOfT) {
        return (T) sGSon.fromJson(json, (Class<Object>) classOfT);
    }

    public static <T> T fromJson(String json, Type typeOfT) {
        return (T) sGSon.fromJson(json, typeOfT);
    }

    public static String toJson(Object o) {
        return sGSon.toJson(o);
    }
}
