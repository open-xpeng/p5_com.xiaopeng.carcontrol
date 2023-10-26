package com.xiaopeng.smartcontrol.sdk.base;

import android.text.TextUtils;
import com.xiaopeng.smartcontrol.sdk.client.hvac.HvacManager;
import com.xiaopeng.smartcontrol.sdk.client.powercenter.PowerCenterManager;
import com.xiaopeng.smartcontrol.sdk.client.vehicle.AtlManager;
import com.xiaopeng.smartcontrol.sdk.client.vehicle.CarBodyManager;
import com.xiaopeng.smartcontrol.sdk.client.vehicle.CarControlShowManager;
import com.xiaopeng.smartcontrol.sdk.client.vehicle.LampManager;
import com.xiaopeng.smartcontrol.sdk.client.vehicle.SeatManager;
import com.xiaopeng.smartcontrol.sdk.client.vehicle.WindowControlManager;
import com.xiaopeng.smartcontrol.sdk.client.vehicle.XpilotManager;
import com.xiaopeng.smartcontrol.utils.Constants;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes2.dex */
public class ManagerFactory {
    private static final ConcurrentHashMap<String, AbsBaseManager> mUnityManagerCache = new ConcurrentHashMap<>();

    public static synchronized AbsBaseManager getManager(String str) {
        AbsBaseManager absBaseManager;
        synchronized (ManagerFactory.class) {
            ConcurrentHashMap<String, AbsBaseManager> concurrentHashMap = mUnityManagerCache;
            absBaseManager = concurrentHashMap.get(str);
            if (absBaseManager == null) {
                try {
                    Class<?> cls = Class.forName(str);
                    if (AbsBaseManager.class.isAssignableFrom(cls)) {
                        AbsBaseManager absBaseManager2 = (AbsBaseManager) cls.getMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
                        if (absBaseManager2 != null) {
                            try {
                                concurrentHashMap.put(str, absBaseManager2);
                            } catch (Throwable th) {
                                th = th;
                                absBaseManager = absBaseManager2;
                                th.printStackTrace();
                                return absBaseManager;
                            }
                        }
                        absBaseManager = absBaseManager2;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            }
        }
        return absBaseManager;
    }

    public static String getClientClassName(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        if (Constants.CAR_CONTROL_MODULE.SEAT_IMPL.equals(str)) {
            return SeatManager.class.getName();
        }
        if (Constants.CAR_CONTROL_MODULE.HVAC_IMPL.equals(str)) {
            return HvacManager.class.getName();
        }
        if (Constants.CAR_CONTROL_MODULE.CAR_BODY_IMPL.equals(str)) {
            return CarBodyManager.class.getName();
        }
        if (Constants.CAR_CONTROL_MODULE.LAMP_IMPL.equals(str)) {
            return LampManager.class.getName();
        }
        if (Constants.CAR_CONTROL_MODULE.PowerCenter_IMPL.equals(str)) {
            return PowerCenterManager.class.getName();
        }
        if (Constants.CAR_CONTROL_MODULE.WINDOW_IMPL.equals(str)) {
            return WindowControlManager.class.getName();
        }
        if (Constants.CAR_CONTROL_MODULE.ATL_IMPL.equals(str)) {
            return AtlManager.class.getName();
        }
        if (Constants.CAR_CONTROL_MODULE.SHOW_IMPL.equals(str)) {
            return CarControlShowManager.class.getName();
        }
        if (Constants.CAR_CONTROL_MODULE.XPILOT_IMPL.equals(str)) {
            return XpilotManager.class.getName();
        }
        return null;
    }
}
