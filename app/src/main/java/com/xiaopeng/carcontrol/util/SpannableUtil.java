package com.xiaopeng.carcontrol.util;

import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;

/* loaded from: classes2.dex */
public class SpannableUtil {
    public static SpannableString getBottomAlignStr(String str, float scale, int startIndex, int endIndex) {
        if (str != null && startIndex < str.length() && endIndex <= str.length() && startIndex < endIndex) {
            SpannableString spannableString = new SpannableString(str);
            spannableString.setSpan(new RelativeSizeSpan(scale), startIndex, endIndex, 17);
            return spannableString;
        }
        return null;
    }
}
