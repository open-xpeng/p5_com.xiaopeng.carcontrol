package com.alibaba.mtl.log.e;

import android.text.TextUtils;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/* compiled from: StringUtils.java */
/* loaded from: classes.dex */
public class p {
    public static String convertObjectToString(Object obj) {
        if (obj != null) {
            if (obj instanceof String) {
                return ((String) obj).toString();
            }
            if (obj instanceof Integer) {
                return "" + ((Integer) obj).intValue();
            }
            if (obj instanceof Long) {
                return "" + ((Long) obj).longValue();
            }
            if (obj instanceof Double) {
                return "" + ((Double) obj).doubleValue();
            }
            if (obj instanceof Float) {
                return "" + ((Float) obj).floatValue();
            }
            if (obj instanceof Short) {
                return "" + ((int) ((Short) obj).shortValue());
            }
            if (obj instanceof Byte) {
                return "" + ((int) ((Byte) obj).byteValue());
            }
            if (obj instanceof Boolean) {
                return ((Boolean) obj).toString();
            }
            if (obj instanceof Character) {
                return ((Character) obj).toString();
            }
            return obj.toString();
        }
        return "";
    }

    public static Map<String, String> b(Map<String, String> map) {
        if (map != null) {
            HashMap hashMap = new HashMap();
            for (String str : map.keySet()) {
                if (str instanceof String) {
                    String str2 = map.get(str);
                    if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
                        try {
                            hashMap.put(URLEncoder.encode(str, "UTF-8"), URLEncoder.encode(str2, "UTF-8"));
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            return hashMap;
        }
        return map;
    }

    public static String d(Map<String, String> map) {
        if (map != null) {
            boolean z = true;
            StringBuffer stringBuffer = new StringBuffer();
            for (String str : map.keySet()) {
                String convertObjectToString = convertObjectToString(map.get(str));
                String convertObjectToString2 = convertObjectToString(str);
                if (convertObjectToString != null && convertObjectToString2 != null) {
                    if (z) {
                        stringBuffer.append(convertObjectToString2 + "=" + convertObjectToString);
                        z = false;
                    } else {
                        stringBuffer.append(",").append(convertObjectToString2 + "=" + convertObjectToString);
                    }
                }
            }
            return stringBuffer.toString();
        }
        return null;
    }
}
