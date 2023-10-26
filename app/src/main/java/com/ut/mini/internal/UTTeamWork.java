package com.ut.mini.internal;

import android.text.TextUtils;
import com.alibaba.mtl.appmonitor.AppMonitor;
import com.alibaba.mtl.log.a;
import com.alibaba.mtl.log.b;
import com.alibaba.mtl.log.c.c;
import com.ut.device.UTDevice;
import com.ut.mini.base.UTMIVariables;
import java.util.Map;

/* loaded from: classes.dex */
public class UTTeamWork {
    private static UTTeamWork a;

    public void disableNetworkStatusChecker() {
    }

    public void dispatchLocalHits() {
    }

    public void initialized() {
    }

    public static synchronized UTTeamWork getInstance() {
        UTTeamWork uTTeamWork;
        synchronized (UTTeamWork.class) {
            if (a == null) {
                a = new UTTeamWork();
            }
            uTTeamWork = a;
        }
        return uTTeamWork;
    }

    public void turnOnRealTimeDebug(Map<String, String> map) {
        AppMonitor.turnOnRealTimeDebug(map);
    }

    public void turnOffRealTimeDebug() {
        AppMonitor.turnOffRealTimeDebug();
    }

    public void saveCacheDataToLocal() {
        c.a().F();
    }

    public void setToAliyunOsPlatform() {
        UTMIVariables.getInstance().setToAliyunOSPlatform();
    }

    public String getUtsid() {
        try {
            String appkey = a.a() != null ? a.a().getAppkey() : null;
            String utdid = UTDevice.getUtdid(b.a().getContext());
            long longValue = Long.valueOf(a.B).longValue();
            if (!TextUtils.isEmpty(appkey) && !TextUtils.isEmpty(utdid)) {
                return utdid + "_" + appkey + "_" + longValue;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void closeAuto1010Track() {
        com.alibaba.mtl.log.c.a().o();
    }
}
