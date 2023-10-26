package com.alibaba.mtl.appmonitor.b;

import android.content.Context;
import com.alibaba.mtl.appmonitor.SdkMeta;
import com.alibaba.mtl.appmonitor.a.f;
import com.alibaba.mtl.appmonitor.a.h;
import com.alibaba.mtl.appmonitor.c.d;
import com.alibaba.mtl.appmonitor.c.e;
import com.alibaba.mtl.appmonitor.f.c;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONObject;

/* compiled from: ExceptionEventBuilder.java */
/* loaded from: classes.dex */
public class b {
    /* renamed from: a  reason: collision with other method in class */
    public static void m19a(Context context, Throwable th) {
        if (th != null) {
            try {
                h hVar = (h) com.alibaba.mtl.appmonitor.c.a.a().a(h.class, new Object[0]);
                hVar.e = f.ALARM.a();
                HashMap hashMap = new HashMap();
                hashMap.put("meta", SdkMeta.getSDKMetaData());
                d dVar = (d) com.alibaba.mtl.appmonitor.c.a.a().a(d.class, new Object[0]);
                dVar.put(a(context, th));
                hashMap.put("data", dVar);
                hVar.m.put(f.ALARM.m17a(), new JSONObject(hashMap).toString());
                hVar.v = "APPMONITOR";
                hVar.w = "sdk-exception";
                c.a(hVar);
                com.alibaba.mtl.appmonitor.c.a.a().a((com.alibaba.mtl.appmonitor.c.a) dVar);
            } catch (Throwable th2) {
                th2.printStackTrace();
            }
        }
    }

    /* renamed from: a  reason: collision with other method in class */
    public static void m20a(Throwable th) {
        m19a((Context) null, th);
    }

    private static JSONObject a(Context context, Throwable th) throws IOException {
        JSONObject jSONObject = (JSONObject) com.alibaba.mtl.appmonitor.c.a.a().a(e.class, new Object[0]);
        if (context != null) {
            try {
                jSONObject.put("pname", com.alibaba.mtl.log.e.b.a(context));
            } catch (Exception unused) {
            }
        }
        jSONObject.put("page", "APPMONITOR");
        jSONObject.put("monitorPoint", "sdk-exception");
        jSONObject.put("arg", th.getClass().getSimpleName());
        jSONObject.put("successCount", 0);
        jSONObject.put("failCount", 1);
        ArrayList arrayList = new ArrayList();
        String a = a(th);
        if (a != null) {
            JSONObject jSONObject2 = (JSONObject) com.alibaba.mtl.appmonitor.c.a.a().a(e.class, new Object[0]);
            jSONObject2.put("errorCode", a);
            jSONObject2.put("errorCount", 1);
            arrayList.add(jSONObject2);
        }
        jSONObject.put("errors", arrayList);
        return jSONObject;
    }

    private static String a(Throwable th) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(th.getClass().getName());
        StackTraceElement[] stackTrace = th.getStackTrace();
        if (stackTrace != null) {
            for (StackTraceElement stackTraceElement : stackTrace) {
                sb.append(stackTraceElement.toString());
            }
        }
        String sb2 = sb.toString();
        return com.alibaba.mtl.appmonitor.f.b.d(sb2) ? th.toString() : sb2;
    }
}
