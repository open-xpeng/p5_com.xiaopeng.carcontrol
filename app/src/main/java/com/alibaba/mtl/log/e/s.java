package com.alibaba.mtl.log.e;

import java.util.Map;
import org.eclipse.paho.android.service.MqttServiceConstants;

/* compiled from: UTAdapter.java */
/* loaded from: classes.dex */
public class s {
    public static void send(Map<String, String> map) {
        Object a;
        try {
            Object a2 = o.a("com.ut.mini.UTAnalytics", "getInstance");
            if (a2 == null || (a = o.a(a2, "getDefaultTracker")) == null) {
                return;
            }
            o.a(a, MqttServiceConstants.SEND_ACTION, new Object[]{map}, Map.class);
        } catch (Exception unused) {
        }
    }
}
