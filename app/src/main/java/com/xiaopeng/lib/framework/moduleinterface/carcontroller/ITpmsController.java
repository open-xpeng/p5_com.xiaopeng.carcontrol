package com.xiaopeng.lib.framework.moduleinterface.carcontroller;

/* loaded from: classes2.dex */
public interface ITpmsController extends ILifeCycle {
    public static final int TYPE_NOT_FIX = 0;
    public static final int TYRE_FAIL = 3;
    public static final int TYRE_FIXED = 2;
    public static final int TYRE_FIXING = 1;

    /* loaded from: classes2.dex */
    public static class TirePressureAllEventMsg extends AbstractEventMsg<float[]> {
    }

    /* loaded from: classes2.dex */
    public static class TirePressureEventMsg extends AbstractEventMsg<Integer> {
    }

    void calibrateTirePressure() throws Exception;

    int[] getAllTirePerssureSensorStatus() throws Exception;

    int[] getAllTirePressureWarnings() throws Exception;

    int[] getAllTireTemperatureWarnings() throws Exception;

    int getTirePressure() throws Exception;

    float[] getTirePressureAll() throws Exception;

    boolean isAbnormalTirePressure() throws Exception;

    boolean isTirePressureSystemFault() throws Exception;
}
