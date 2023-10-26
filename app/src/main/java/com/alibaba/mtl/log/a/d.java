package com.alibaba.mtl.log.a;

import com.alibaba.mtl.log.e.i;
import com.lzy.okgo.cookie.SerializableCookie;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: HostConfigMgr.java */
/* loaded from: classes.dex */
public class d {
    private static d a = new d();
    private String S;
    private Map<String, c> u = Collections.synchronizedMap(new HashMap());

    public static d a() {
        return a;
    }

    public void b(String str) {
        JSONObject jSONObject;
        i.a("HostConfigMgr", "host config:" + str);
        if (str != null) {
            try {
                JSONObject jSONObject2 = new JSONObject(str);
                JSONObject jSONObject3 = jSONObject2.getJSONObject("content");
                if (jSONObject3 != null && (jSONObject = jSONObject3.getJSONObject("hosts")) != null) {
                    Iterator<String> keys = jSONObject.keys();
                    while (keys.hasNext()) {
                        String next = keys.next();
                        if (next != null) {
                            c cVar = new c();
                            JSONObject jSONObject4 = jSONObject.getJSONObject(next);
                            if (jSONObject4 != null) {
                                cVar.R = next.substring(1);
                                cVar.Q = jSONObject4.getString(SerializableCookie.HOST);
                                JSONArray jSONArray = jSONObject4.getJSONArray("eids");
                                if (jSONArray != null) {
                                    cVar.a = new ArrayList<>();
                                    for (int i = 0; i < jSONArray.length(); i++) {
                                        cVar.a.add(jSONArray.getString(i));
                                    }
                                }
                            }
                            this.u.put(cVar.R + "", cVar);
                        }
                    }
                }
                this.S = jSONObject2.getString("timestamp");
            } catch (Throwable unused) {
            }
        }
    }

    public Map<String, c> b() {
        return this.u;
    }
}
