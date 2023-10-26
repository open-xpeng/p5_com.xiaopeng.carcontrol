package com.alibaba.mtl.appmonitor.d;

import com.xiaopeng.speech.speechwidget.SpeechWidget;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: MonitorPointSampling.java */
/* loaded from: classes.dex */
class i extends a<JSONObject> {
    protected List<c> e;
    private String p;

    public i(String str, int i) {
        super(i);
        this.p = str;
    }

    public boolean a(int i, Map<String, String> map) {
        List<c> list = this.e;
        if (list != null && map != null) {
            for (c cVar : list) {
                Boolean a = cVar.a(i, map);
                if (a != null) {
                    return a.booleanValue();
                }
            }
        }
        return a(i);
    }

    public void b(JSONObject jSONObject) {
        a((i) jSONObject);
        try {
            JSONArray optJSONArray = jSONObject.optJSONArray(SpeechWidget.WIDGET_EXTRA);
            if (optJSONArray != null) {
                for (int i = 0; i < optJSONArray.length(); i++) {
                    JSONObject jSONObject2 = optJSONArray.getJSONObject(i);
                    c cVar = new c(this.n);
                    if (this.e == null) {
                        this.e = new ArrayList();
                    }
                    this.e.add(cVar);
                    cVar.b(jSONObject2);
                }
            }
        } catch (Exception unused) {
        }
    }
}
