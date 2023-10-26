package com.irdeto.securesdk.upgrade;

import android.content.Context;

/* loaded from: classes.dex */
public class UpgradeManager {
    public static int O000000o(Context context, Upgradelistener upgradelistener) {
        return nupgradeCheck(context, upgradelistener);
    }

    private static native int nupgradeCheck(Context context, Object obj);
}
