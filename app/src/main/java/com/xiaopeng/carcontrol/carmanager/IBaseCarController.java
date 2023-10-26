package com.xiaopeng.carcontrol.carmanager;

import com.xiaopeng.carcontrol.carmanager.IBaseCallback;

/* loaded from: classes.dex */
public interface IBaseCarController<T extends IBaseCallback> {
    public static final int DRIVER_ZONE_ID = 1;
    public static final int PASSENGER_ZONE_ID = 2;

    void registerBusiness(String... keys);

    void registerCallback(T callback);

    void unregisterBusiness(String... keys);

    void unregisterCallback(T callback);
}
