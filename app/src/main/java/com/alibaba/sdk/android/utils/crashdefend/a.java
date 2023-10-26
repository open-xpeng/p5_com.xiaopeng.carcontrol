package com.alibaba.sdk.android.utils.crashdefend;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import com.alibaba.sdk.android.beacon.Beacon;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONObject;

/* compiled from: BeaconConfigManager.java */
/* loaded from: classes.dex */
public class a {
    private static a a;

    /* renamed from: a  reason: collision with other field name */
    private static List<Beacon> f118a = new ArrayList();
    private Context mContext;

    /* compiled from: BeaconConfigManager.java */
    /* renamed from: com.alibaba.sdk.android.utils.crashdefend.a$a  reason: collision with other inner class name */
    /* loaded from: classes.dex */
    public interface InterfaceC0013a {
        void update();
    }

    private a(Context context) {
        this.mContext = null;
        this.mContext = context;
    }

    /* compiled from: BeaconConfigManager.java */
    /* loaded from: classes.dex */
    public static class b implements Beacon.OnUpdateListener {
        private Beacon a;

        /* renamed from: a  reason: collision with other field name */
        private InterfaceC0013a f119a;

        /* renamed from: a  reason: collision with other field name */
        private d f120a;

        public b(d dVar, InterfaceC0013a interfaceC0013a) {
            this.f120a = dVar;
            this.f119a = interfaceC0013a;
        }

        protected void a(int i) {
            d dVar = this.f120a;
            if (dVar != null) {
                int i2 = dVar.c;
                this.f120a.c = i;
                InterfaceC0013a interfaceC0013a = this.f119a;
                if (interfaceC0013a == null || i2 == i) {
                    return;
                }
                interfaceC0013a.update();
            }
        }

        public void a(Beacon beacon) {
            this.a = beacon;
        }

        @Override // com.alibaba.sdk.android.beacon.Beacon.OnUpdateListener
        public void onUpdate(List<Beacon.Config> list) {
            Log.i("BeaconConfigManager", "beacon onUpdate");
            try {
                Beacon beacon = this.a;
                if (beacon != null) {
                    beacon.stop();
                }
                synchronized (a.f118a) {
                    a.f118a.remove(this.a);
                }
                if (this.f120a == null || list == null || list.size() <= 0) {
                    return;
                }
                for (Beacon.Config config : list) {
                    if (("___" + this.f120a.f129a + "_service___").equalsIgnoreCase(config.key)) {
                        JSONObject jSONObject = new JSONObject(config.value);
                        if (jSONObject.has("status")) {
                            String string = jSONObject.getString("status");
                            if ("disabled".equalsIgnoreCase(string)) {
                                a(2);
                                Log.i("BeaconConfigManager", "beacon onUpdate:disable");
                            } else if ("enable".equalsIgnoreCase(string)) {
                                a(1);
                                Log.i("BeaconConfigManager", "beacon onUpdate:enable");
                            } else {
                                a(0);
                                Log.i("BeaconConfigManager", "beacon onUpdate:normal");
                            }
                        } else {
                            a(0);
                            Log.i("BeaconConfigManager", "beacon onUpdate:unknown");
                        }
                    }
                }
            } catch (Exception e) {
                Log.e("BeaconConfigManager", "onUpdate Exception " + e.getMessage());
            }
        }
    }

    public static a a(Context context) {
        if (a == null) {
            synchronized (a.class) {
                if (a == null) {
                    a = new a(context);
                }
            }
        }
        return a;
    }

    public void a(d dVar, InterfaceC0013a interfaceC0013a) {
        if (this.mContext != null) {
            HashMap hashMap = new HashMap();
            hashMap.put("sdkId", dVar.f129a);
            hashMap.put("sdkVer", dVar.f131b);
            hashMap.put("osType", "os.android");
            hashMap.put("osVer", "" + Build.VERSION.RELEASE);
            hashMap.put("beaconVer", "1.0.1");
            hashMap.put("devBrand", Build.BRAND);
            hashMap.put("devModel", Build.MODEL);
            Beacon build = new Beacon.Builder().appKey("24527540").appSecret("56fc10fbe8c6ae7d0d895f49c4fb6838").extras(hashMap).build();
            b bVar = new b(dVar, interfaceC0013a);
            bVar.a(build);
            build.addUpdateListener(bVar);
            build.start(this.mContext);
            f118a.add(build);
        }
    }
}
