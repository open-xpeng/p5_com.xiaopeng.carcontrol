package com.alibaba.mtl.appmonitor.f;

import com.alibaba.mtl.appmonitor.SdkMeta;
import com.alibaba.mtl.appmonitor.a.d;
import com.alibaba.mtl.appmonitor.a.f;
import com.alibaba.mtl.appmonitor.a.h;
import com.alibaba.mtl.appmonitor.model.UTDimensionValueSet;
import com.alibaba.mtl.log.e.i;
import com.alibaba.mtl.log.model.LogField;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

/* compiled from: UTUtil.java */
/* loaded from: classes.dex */
public class c {
    public static void b(Map<UTDimensionValueSet, List<d>> map) {
        Integer eventId;
        for (Map.Entry<UTDimensionValueSet, List<d>> entry : map.entrySet()) {
            StringBuilder sb = new StringBuilder();
            StringBuilder sb2 = new StringBuilder();
            UTDimensionValueSet key = entry.getKey();
            List<d> value = entry.getValue();
            if (value.size() != 0 && (eventId = key.getEventId()) != null) {
                f a = f.a(eventId.intValue());
                int i = 0;
                h hVar = (h) com.alibaba.mtl.appmonitor.c.a.a().a(h.class, new Object[0]);
                hVar.e = eventId.intValue();
                if (key.getMap() != null) {
                    hVar.m.putAll(key.getMap());
                }
                HashMap hashMap = new HashMap();
                hashMap.put("meta", SdkMeta.getSDKMetaData());
                com.alibaba.mtl.appmonitor.c.d dVar = (com.alibaba.mtl.appmonitor.c.d) com.alibaba.mtl.appmonitor.c.a.a().a(com.alibaba.mtl.appmonitor.c.d.class, new Object[0]);
                for (d dVar2 : value) {
                    dVar.put(dVar2.a());
                    if (i == 0) {
                        sb.append(dVar2.o);
                        sb2.append(dVar2.p);
                    } else {
                        sb.append(",");
                        sb.append(dVar2.o);
                        sb2.append(",");
                        sb2.append(dVar2.p);
                    }
                    i++;
                    com.alibaba.mtl.appmonitor.c.a.a().a((com.alibaba.mtl.appmonitor.c.a) dVar2);
                }
                hashMap.put("data", dVar);
                hVar.m.put(a.m17a(), new JSONObject(hashMap).toString());
                String sb3 = sb.toString();
                String sb4 = sb2.toString();
                hVar.m.put(LogField.ARG1.toString(), sb3);
                hVar.m.put(LogField.ARG2.toString(), sb4);
                hVar.v = sb3;
                hVar.w = sb4;
                b(hVar);
                com.alibaba.mtl.appmonitor.c.a.a().a((com.alibaba.mtl.appmonitor.c.a) dVar);
            }
            com.alibaba.mtl.appmonitor.c.a.a().a((com.alibaba.mtl.appmonitor.c.a) key);
        }
    }

    public static void a(UTDimensionValueSet uTDimensionValueSet, d dVar) {
        Integer eventId = uTDimensionValueSet.getEventId();
        if (eventId != null) {
            f a = f.a(eventId.intValue());
            h hVar = (h) com.alibaba.mtl.appmonitor.c.a.a().a(h.class, new Object[0]);
            hVar.e = 6699;
            if (uTDimensionValueSet.getMap() != null) {
                hVar.m.putAll(uTDimensionValueSet.getMap());
            }
            HashMap hashMap = new HashMap();
            hashMap.put("meta", SdkMeta.getSDKMetaData());
            hashMap.put("_event_id", eventId);
            com.alibaba.mtl.appmonitor.c.d dVar2 = (com.alibaba.mtl.appmonitor.c.d) com.alibaba.mtl.appmonitor.c.a.a().a(com.alibaba.mtl.appmonitor.c.d.class, new Object[0]);
            dVar2.put(dVar.a());
            com.alibaba.mtl.appmonitor.c.a.a().a((com.alibaba.mtl.appmonitor.c.a) dVar);
            hashMap.put("data", dVar2);
            hVar.m.put(a.m17a(), new JSONObject(hashMap).toString());
            hVar.m.put(LogField.EVENTID.toString(), String.valueOf(6699));
            b(hVar);
            com.alibaba.mtl.appmonitor.c.a.a().a((com.alibaba.mtl.appmonitor.c.a) dVar2);
        }
    }

    public static void a(h hVar) {
        if (hVar == null) {
            return;
        }
        com.alibaba.mtl.log.a.a(hVar.u, String.valueOf(hVar.e), hVar.v, hVar.w, hVar.x, hVar.m);
        com.alibaba.mtl.appmonitor.c.a.a().a((com.alibaba.mtl.appmonitor.c.a) hVar);
    }

    public static void b(h hVar) {
        i.a("UTUtil", "upload without flowback. args:", hVar.m);
        com.alibaba.mtl.appmonitor.e.a.a().a(hVar.m);
        com.alibaba.mtl.appmonitor.c.a.a().a((com.alibaba.mtl.appmonitor.c.a) hVar);
    }
}
