package com.irdeto.securesdk.upgrade;

import android.content.Context;

/* loaded from: classes.dex */
public interface Upgradelistener {
    int onComplete(int i);

    int onFail(int i);

    int onNewVersion(Context context, String str, String str2);

    int onProgress(int i);

    int onSuccess(int i);
}
