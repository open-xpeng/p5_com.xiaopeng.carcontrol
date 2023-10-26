package com.xiaopeng.speech.protocol.utils;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.TextView;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.regex.Pattern;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class StringUtil {
    private static final String TAG = "AIOS-Adapter-StringUtil";

    public static void highNightWords(Context context, TextView textView, int i, String str, String... strArr) {
        if (str == null) {
            str = "";
        }
        Log.i(TAG, "完整字符串：" + str);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(str);
        if (strArr != null) {
            for (String str2 : strArr) {
                Log.i(TAG, "需要高亮的字符串：" + str2);
                if (!TextUtils.isEmpty(str2) && str.contains(str2)) {
                    ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(context.getResources().getColor(i));
                    int indexOf = str.indexOf(str2);
                    spannableStringBuilder.setSpan(foregroundColorSpan, indexOf, str2.length() + indexOf, 34);
                }
            }
        }
        textView.setText(spannableStringBuilder);
    }

    public static String highNightWords(Context context, int i, String str, String... strArr) {
        if (str == null) {
            str = "";
        }
        Log.i(TAG, "完整字符串：" + str);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(str);
        if (strArr != null) {
            for (String str2 : strArr) {
                Log.i(TAG, "需要高亮的字符串：" + str2);
                if (!TextUtils.isEmpty(str2) && str.contains(str2)) {
                    ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(context.getResources().getColor(i));
                    int indexOf = str.indexOf(str2);
                    spannableStringBuilder.setSpan(foregroundColorSpan, indexOf, str2.length() + indexOf, 34);
                }
            }
        }
        return spannableStringBuilder.toString();
    }

    public static String getEncodedString(byte[] bArr) {
        String str;
        try {
            str = new String(bArr, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, e.toString());
            str = "";
        }
        return str.trim();
    }

    public static boolean isDecimalNumber(String str) {
        if (str == null || str.trim().equals("")) {
            return false;
        }
        return Pattern.compile("([+|-])?(([0-9]+.[0-9]*)|([0-9]*.[0-9]+)|([0-9]+))((e|E)([+|-])?[0-9]+)?").matcher(str).matches();
    }

    public static JSONObject joinJSONObject(JSONObject jSONObject, JSONObject jSONObject2) {
        Iterator<String> keys = jSONObject2.keys();
        Log.d(TAG, "Before join:" + jSONObject.toString());
        while (keys.hasNext()) {
            try {
                String next = keys.next();
                jSONObject.put(next, jSONObject2.get(next));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.d(TAG, "After join:" + jSONObject.toString());
        return jSONObject;
    }
}
