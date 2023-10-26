package com.unity3d.player;

import android.os.Build;

/* loaded from: classes.dex */
public final class j {
    static final boolean a;
    static final boolean b;
    static final boolean c;
    static final e d;

    static {
        a = Build.VERSION.SDK_INT >= 19;
        b = Build.VERSION.SDK_INT >= 21;
        boolean z = Build.VERSION.SDK_INT >= 23;
        c = z;
        d = z ? new h() : null;
    }
}
