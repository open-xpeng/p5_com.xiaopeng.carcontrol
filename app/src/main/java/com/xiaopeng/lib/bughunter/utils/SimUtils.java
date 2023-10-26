package com.xiaopeng.lib.bughunter.utils;

import android.text.TextUtils;
import java.lang.reflect.Array;

/* loaded from: classes2.dex */
public class SimUtils {
    private static int min(int i, int i2, int i3) {
        if (i2 < i) {
            i = i2;
        }
        return i3 < i ? i3 : i;
    }

    public static double getSim(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return 0.0d;
        }
        return sim(str, str2);
    }

    private static int ld(String str, String str2) {
        int length = str.length();
        int length2 = str2.length();
        if (length == 0) {
            return length2;
        }
        if (length2 == 0) {
            return length;
        }
        int[][] iArr = (int[][]) Array.newInstance(int.class, length + 1, length2 + 1);
        for (int i = 0; i <= length; i++) {
            iArr[i][0] = i;
        }
        for (int i2 = 0; i2 <= length2; i2++) {
            iArr[0][i2] = i2;
        }
        for (int i3 = 1; i3 <= length; i3++) {
            int i4 = i3 - 1;
            char charAt = str.charAt(i4);
            for (int i5 = 1; i5 <= length2; i5++) {
                int i6 = i5 - 1;
                iArr[i3][i5] = min(iArr[i4][i5] + 1, iArr[i3][i6] + 1, iArr[i4][i6] + (charAt == str2.charAt(i6) ? 0 : 1));
            }
        }
        return iArr[length][length2];
    }

    private static double sim(String str, String str2) {
        return 1.0d - (ld(str, str2) / Math.max(str.length(), str2.length()));
    }
}
