package com.xiaopeng.carcontrol.debug;

import android.app.Application;
import android.content.Intent;
import com.xiaopeng.carcontrol.util.Utils;

/* loaded from: classes2.dex */
public class DemoManager {
    public static void init(Application application) {
        if (Utils.isUserRelease()) {
            return;
        }
        application.startService(new Intent(application, XpDemoService.class));
    }
}
