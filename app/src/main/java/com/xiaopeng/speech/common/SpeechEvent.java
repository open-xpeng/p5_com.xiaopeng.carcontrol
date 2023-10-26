package com.xiaopeng.speech.common;

import android.text.TextUtils;

/* loaded from: classes2.dex */
public class SpeechEvent {
    public static final String COMMAND_SCHEME = "command://";
    public static final String DATA_API_SCHEME = "data://";
    public static final String NATIVE_API_SCHEME = "native://";

    public static boolean isCommand(String str) {
        return !TextUtils.isEmpty(str) && str.startsWith(COMMAND_SCHEME);
    }

    public static boolean isNativeApi(String str) {
        return !TextUtils.isEmpty(str) && str.startsWith(NATIVE_API_SCHEME);
    }
}
