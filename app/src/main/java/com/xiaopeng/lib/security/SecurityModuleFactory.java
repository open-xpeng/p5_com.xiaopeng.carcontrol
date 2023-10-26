package com.xiaopeng.lib.security;

import android.os.Build;
import com.xiaopeng.lib.security.none.NoneSecurity;
import com.xiaopeng.lib.security.xmartv1.RandomKeySecurity;

/* loaded from: classes2.dex */
public final class SecurityModuleFactory {
    public static ISecurityModule getSecurityModule() {
        if (Build.VERSION.SDK_INT == 19) {
            return NoneSecurity.getInstance();
        }
        return RandomKeySecurity.getInstance();
    }
}
