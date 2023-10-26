package com.xiaopeng.xui.utils;

/* loaded from: classes2.dex */
public class XCharacterUtils {
    public static boolean isChinese(char c) {
        return c >= 19968 && c <= 40869;
    }

    public static boolean isFullAngle(char c) {
        return c > 255;
    }

    public static boolean isFullAngleSymbol(char c) {
        return (c >= 65281 && c <= 65374) || c == 12288;
    }

    public static boolean isHalfAngleSymbol(char c) {
        return (c >= '!' && c <= '~') || c == ' ';
    }

    public static boolean isNotEmoJi(char c) {
        return c == 0 || c == '\t' || c == '\n' || c == '\r' || (c >= ' ' && c <= 55295) || ((c >= 57344 && c <= 65533) || (c >= 0 && c <= 65535));
    }

    public static boolean isFullAngleUseRegex(char c) {
        return String.valueOf(c).matches("[^\\x00-\\xff]");
    }
}
