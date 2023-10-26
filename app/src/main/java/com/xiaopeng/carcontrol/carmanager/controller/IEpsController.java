package com.xiaopeng.carcontrol.carmanager.controller;

import com.xiaopeng.carcontrol.carmanager.IBaseCallback;
import com.xiaopeng.carcontrol.carmanager.IBaseCarController;

/* loaded from: classes.dex */
public interface IEpsController extends IBaseCarController<Callback> {
    public static final int EPS_MODE_SOFT = 1;
    public static final int EPS_MODE_SPORT = 2;
    public static final int EPS_MODE_STANDARD = 0;

    /* loaded from: classes.dex */
    public interface Callback extends IBaseCallback {
        default void onSteeringAngleChanged(float angle) {
        }

        default void onSteeringEpsChanged(int eps) {
        }
    }

    float getSteeringAngle();

    int getSteeringEps();

    int getSteeringEpsSp();

    float getTorsionBarTorque();

    void setSteeringEps(int eps);

    void setSteeringEps(int eps, boolean storeEnable);
}
