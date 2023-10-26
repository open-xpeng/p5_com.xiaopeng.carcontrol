package com.irdeto.securesdk;

import android.text.TextUtils;
import com.xiaopeng.carcontrol.quicksetting.QuickSettingConstants;

/* loaded from: classes.dex */
public class ISFException extends Exception {
    private static final String UNDEFINED = "undefined";
    private int errorCode;
    private String message;

    public ISFException(int i) {
        this(i, "");
    }

    public ISFException(int i, String str) {
        this.errorCode = i;
        this.message = TextUtils.isEmpty(str) ? UNDEFINED : str;
    }

    public ISFException(O000000o o000000o) {
        this(o000000o.O000000o(), o000000o.toString());
    }

    public ISFException(O000000o o000000o, String str) {
        this(o000000o.O000000o(), o000000o.toString());
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.message += QuickSettingConstants.JOINER + str;
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        return this.message;
    }
}
