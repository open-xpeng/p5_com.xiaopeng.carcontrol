package com.xiaopeng.xui.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;
import java.util.HashMap;

/* loaded from: classes2.dex */
public class XFontUtils {
    private static final String BASE_FONT_DIR = "font/";
    private static final String BASE_SYSTEM_FONT_DIR = "/system/fonts/";
    public static final String FONT_XP_MEDIUM = "Xpeng-Medium.ttf";
    public static final String FONT_XP_NUMBER = "Xpeng-Number.ttf";
    public static final String FONT_XP_NUMBER_BOLD = "Xpeng-Number-Bold.ttf";
    private static HashMap<String, Typeface> fonts = new HashMap<>();

    public static Typeface getAssetsTypeface(Context context, String str) {
        Typeface typeface = fonts.get(str);
        if (typeface == null) {
            try {
                typeface = Typeface.createFromAsset(context.getAssets(), getFontPath(str));
                if (typeface != null) {
                    fonts.put(str, typeface);
                }
            } catch (Exception unused) {
            }
        }
        return typeface;
    }

    public static Typeface getSystemTypeface(Context context, String str) {
        Typeface typeface = fonts.get(str);
        if (typeface == null) {
            try {
                typeface = Typeface.createFromFile(getSystemFontPath(str));
            } catch (Exception unused) {
            }
            fonts.put(str, typeface);
        }
        return typeface;
    }

    private static String getFontPath(String str) {
        return BASE_FONT_DIR + str;
    }

    private static String getSystemFontPath(String str) {
        return BASE_SYSTEM_FONT_DIR + str;
    }

    public static void applyTypeface(TextView textView, String str) {
        applyTypeface(textView, str, false);
    }

    public static void applyTypeface(TextView textView, String str, boolean z) {
        if (textView == null) {
            return;
        }
        Typeface systemTypeface = getSystemTypeface(textView.getContext(), str);
        if (systemTypeface == null) {
            systemTypeface = getAssetsTypeface(textView.getContext(), str);
        }
        if (systemTypeface != null) {
            textView.setTypeface(systemTypeface);
            textView.setIncludeFontPadding(z);
        }
    }
}
