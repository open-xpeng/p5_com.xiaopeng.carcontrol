package com.xiaopeng.carcontrol.direct;

import com.xiaopeng.carcontrol.util.CarStatusUtils;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes2.dex */
public class ElementDirectManager {
    private static final Map<String, IElementDirect> sElementDirectList = new ConcurrentHashMap();

    public static void init(IElementDirect instance) {
        sElementDirectList.put(CarStatusUtils.getHardwareCarType(), instance);
    }

    public static IElementDirect getElementDirect() {
        return sElementDirectList.get(CarStatusUtils.getHardwareCarType());
    }
}
