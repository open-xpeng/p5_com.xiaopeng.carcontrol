package com.alibaba.mtl.log.a;

import android.text.TextUtils;
import com.alibaba.mtl.log.e.i;
import com.alibaba.mtl.log.e.r;
import org.json.JSONObject;

/* compiled from: SystemConfig.java */
/* loaded from: classes.dex */
public class e {
    public static void i(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.has("SYSTEM")) {
                i.a("SystemConfig", "server system config ", str);
                JSONObject optJSONObject = jSONObject.optJSONObject("SYSTEM");
                if (optJSONObject != null) {
                    try {
                        if (optJSONObject.has("bg_interval")) {
                            a.f(optJSONObject.getInt("bg_interval") + "");
                        }
                    } catch (Throwable unused) {
                    }
                    try {
                        if (optJSONObject.has("fg_interval")) {
                            a.g(optJSONObject.getInt("fg_interval") + "");
                        }
                    } catch (Throwable unused2) {
                    }
                    i.a("SystemConfig", "UTDC.bSendToNewLogStore:", Boolean.valueOf(com.alibaba.mtl.log.a.r));
                    i.a("SystemConfig", "Config.BACKGROUND_PERIOD:", Long.valueOf(a.b()));
                    i.a("SystemConfig", "Config.FOREGROUND_PERIOD:", Long.valueOf(a.a()));
                    try {
                        if (optJSONObject.has("discard")) {
                            int i = optJSONObject.getInt("discard");
                            if (i == 1) {
                                a.A = true;
                                com.alibaba.mtl.log.d.a.a().stop();
                            } else if (i == 0) {
                                a.A = false;
                                com.alibaba.mtl.log.d.a.a().start();
                            }
                        } else if (a.A) {
                            a.A = false;
                            com.alibaba.mtl.log.d.a.a().start();
                        }
                    } catch (Throwable unused3) {
                    }
                    try {
                        if (!optJSONObject.has("cdb") || optJSONObject.getInt("cdb") <= f()) {
                            return;
                        }
                        r.a().b(new Runnable() { // from class: com.alibaba.mtl.log.a.e.1
                            @Override // java.lang.Runnable
                            public void run() {
                                com.alibaba.mtl.log.c.c.a().clear();
                            }
                        });
                    } catch (Throwable unused4) {
                    }
                }
            }
        } catch (Throwable th) {
            i.a("SystemConfig", "updateconfig", th);
        }
    }

    public static int f() {
        JSONObject jSONObject;
        String h = a.h();
        if (TextUtils.isEmpty(h)) {
            return 0;
        }
        try {
            JSONObject jSONObject2 = new JSONObject(h);
            if (jSONObject2.has("SYSTEM") && (jSONObject = jSONObject2.getJSONObject("SYSTEM")) != null && jSONObject.has("cdb")) {
                return jSONObject.getInt("cdb");
            }
            return 0;
        } catch (Throwable unused) {
            return 0;
        }
    }
}
