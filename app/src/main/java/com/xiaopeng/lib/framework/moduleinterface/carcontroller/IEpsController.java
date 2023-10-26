package com.xiaopeng.lib.framework.moduleinterface.carcontroller;

/* loaded from: classes2.dex */
public interface IEpsController extends ILifeCycle {
    public static final int EPS_POWER_ASSISTED_SOFT = 1;
    public static final int EPS_POWER_ASSISTED_SPORT = 2;
    public static final int EPS_POWER_ASSISTED_STANDARD = 0;
    public static final int POWER_STATUS_ASSISTED_SOFT = 1;
    public static final int POWER_STATUS_ASSISTED_SPORT = 2;
    public static final int POWER_STATUS_ASSISTED_STANDARD = 0;

    /* loaded from: classes2.dex */
    public static class SteeringAngleEventMsg extends AbstractEventMsg<Float> {
    }

    /* loaded from: classes2.dex */
    public static class SteeringAngleSpdEventMsg extends AbstractEventMsg<Float> {
    }

    /* loaded from: classes2.dex */
    public static class SteeringWheelEPSEventMsg extends AbstractEventMsg<Integer> {
    }

    float getSteeringAngle() throws Exception;

    float getSteeringAngleSpd() throws Exception;

    int getSteeringWheelEPS() throws Exception;

    void setSteeringWheelEPS(int i) throws Exception;
}
