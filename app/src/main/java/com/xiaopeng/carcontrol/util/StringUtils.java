package com.xiaopeng.carcontrol.util;

/* loaded from: classes2.dex */
public class StringUtils {
    public static String capitalizeFirstCharacter(String str) {
        if (str == null || str.length() == 0 || !str.matches("^[a-zA-Z]*")) {
            return str;
        }
        char[] charArray = str.toCharArray();
        if (charArray[0] >= 'a') {
            charArray[0] = (char) (charArray[0] - ' ');
        }
        for (int i = 1; i < charArray.length; i++) {
            if (charArray[i] <= 'Z') {
                charArray[i] = (char) (charArray[i] + ' ');
            }
        }
        return String.valueOf(charArray);
    }
}
