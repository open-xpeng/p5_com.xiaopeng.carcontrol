package com.alibaba.sdk.android.httpdns.a;

import android.content.Context;
import android.util.Log;
import com.alibaba.sdk.android.beacon.Beacon;
import com.alibaba.sdk.android.httpdns.b;
import com.alibaba.sdk.android.httpdns.i;
import java.util.HashMap;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class a {
    private static a a;
    private Context mContext = null;

    /* renamed from: a  reason: collision with other field name */
    private com.alibaba.sdk.android.httpdns.d.a f78a = null;

    /* renamed from: a  reason: collision with other field name */
    private Beacon f77a = null;
    private boolean h = true;

    /* renamed from: a  reason: collision with other field name */
    private final Beacon.OnUpdateListener f76a = new Beacon.OnUpdateListener() { // from class: com.alibaba.sdk.android.httpdns.a.a.1
        @Override // com.alibaba.sdk.android.beacon.Beacon.OnUpdateListener
        public void onUpdate(List<Beacon.Config> list) {
            a.this.b(list);
        }
    };

    /* renamed from: a  reason: collision with other field name */
    private final Beacon.OnServiceErrListener f75a = new Beacon.OnServiceErrListener() { // from class: com.alibaba.sdk.android.httpdns.a.a.2
        @Override // com.alibaba.sdk.android.beacon.Beacon.OnServiceErrListener
        public void onErr(Beacon.Error error) {
            Log.e("HTTPDNS:BeaconManager", "beacon error. errorCode:" + error.errCode + ", errorMsg:" + error.errMsg);
        }
    };

    private a() {
    }

    public static a a() {
        if (a == null) {
            synchronized (a.class) {
                if (a == null) {
                    a = new a();
                }
            }
        }
        return a;
    }

    private boolean a(Beacon.Config config) {
        if (config == null || !config.key.equalsIgnoreCase("___httpdns_service___")) {
            return false;
        }
        String str = config.value;
        if (str != null) {
            Log.d("HTTPDNS:BeaconManager", "httpdns configs:" + str);
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.has("ut")) {
                    d(jSONObject.getString("ut"));
                }
                if (jSONObject.has("ip-ranking")) {
                    i(jSONObject.getString("ip-ranking"));
                }
                if (jSONObject.has("status")) {
                    j(jSONObject.getString("status"));
                }
            } catch (JSONException e) {
                Log.e("HTTPDNS:BeaconManager", "parse push configs failed.", e);
                return false;
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(List<Beacon.Config> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        for (Beacon.Config config : list) {
            if (config.key.equalsIgnoreCase("___httpdns_service___")) {
                a(config);
            }
        }
    }

    private boolean d(String str) {
        if (str == null || this.f78a == null) {
            return false;
        }
        Log.d("HTTPDNS:BeaconManager", "is report enabled:" + str);
        if (str.equalsIgnoreCase("disabled")) {
            this.f78a.d(false);
        } else {
            this.f78a.d(true);
        }
        return true;
    }

    private void i(String str) {
        if (str != null) {
            Log.d("HTTPDNS:BeaconManager", "is IP probe enabled:" + str);
            this.h = !str.equalsIgnoreCase("disabled");
        }
    }

    private void j(String str) {
        if (str != null) {
            b.a(!"disabled".equals(str));
            i.e("[beacon] httpdns enable: " + b.a());
        }
    }

    public void a(Context context, String str) {
        this.mContext = context;
        if (context != null) {
            HashMap hashMap = new HashMap();
            hashMap.put("sdkId", "httpdns");
            hashMap.put("accountId", str);
            Beacon build = new Beacon.Builder().appKey("24657847").appSecret("f30fc0937f2b1e9e50a1b7134f1ddb10").loopInterval(7200000L).extras(hashMap).build();
            this.f77a = build;
            build.addUpdateListener(this.f76a);
            this.f77a.addServiceErrListener(this.f75a);
            this.f77a.start(this.mContext.getApplicationContext());
        }
    }

    public void a(com.alibaba.sdk.android.httpdns.d.a aVar) {
        this.f78a = aVar;
    }

    public boolean f() {
        return this.h;
    }
}
