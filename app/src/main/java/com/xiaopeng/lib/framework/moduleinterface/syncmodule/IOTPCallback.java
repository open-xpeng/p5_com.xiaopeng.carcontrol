package com.xiaopeng.lib.framework.moduleinterface.syncmodule;

/* loaded from: classes2.dex */
public interface IOTPCallback {
    void onError(String str, Integer num, String str2);

    void onGetOTP(String str, String str2, String str3, long j);
}
