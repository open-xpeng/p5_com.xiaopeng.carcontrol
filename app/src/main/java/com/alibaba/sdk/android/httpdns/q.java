package com.alibaba.sdk.android.httpdns;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class q {
    private boolean enabled;
    private String[] f;

    /* JADX INFO: Access modifiers changed from: package-private */
    public q(String str) {
        this.enabled = true;
        try {
            JSONObject jSONObject = new JSONObject(str);
            i.d("Schedule center response:" + jSONObject.toString());
            if (jSONObject.has("service_status")) {
                this.enabled = jSONObject.getString("service_status").equals("disable") ? false : true;
            }
            if (jSONObject.has("service_ip")) {
                JSONArray jSONArray = jSONObject.getJSONArray("service_ip");
                this.f = new String[jSONArray.length()];
                for (int i = 0; i < jSONArray.length(); i++) {
                    this.f[i] = (String) jSONArray.get(i);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String[] c() {
        return this.f;
    }

    public boolean isEnabled() {
        return this.enabled;
    }
}
