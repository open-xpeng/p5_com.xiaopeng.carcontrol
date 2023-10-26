package com.xiaopeng.lib.framework.moduleinterface.carcontroller.v2extend;

import com.xiaopeng.lib.framework.moduleinterface.carcontroller.AbstractEventMsg;

/* loaded from: classes2.dex */
public interface IScuController {
    public static final int DISABLE_PARK_BY_MEMORY = 1;
    public static final int DISABLE_PARK_BY_MEMORY_WITH_LICENSE = 2;
    public static final int ENABLE_PARK_BY_MEMORY = 2;
    public static final int ENABLE_PARK_BY_MEMORY_WITH_LICENSE = 1;
    public static final int PARK_BY_MEMORY_IN_INITIALIZATION = 0;
    public static final int SCU_FSD_SW_OFF = 0;
    public static final int SCU_FSD_SW_ON = 1;
    public static final int SWITCH_PARK_BY_MEMORY_WITHOUT_LICENSE = 3;

    /* loaded from: classes2.dex */
    public static class AllParklotDataEventMsg extends AbstractEventMsg<float[]> {
    }

    /* loaded from: classes2.dex */
    public static class ErrorTipsUpdateEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class FsdSwitchEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class LeftAvmDataUpdateEventMsg extends AbstractEventMsg<float[]> {
    }

    /* loaded from: classes2.dex */
    public static class ParkByMemorySwEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class RightAvmDataUpdateEventMsg extends AbstractEventMsg<float[]> {
    }

    /* loaded from: classes2.dex */
    public static class SensorDataUpdateEventMsg extends AbstractEventMsg<float[]> {
    }

    /* loaded from: classes2.dex */
    public static class SlotForPatkUpdateEventMsg extends AbstractEventMsg<float[]> {
    }

    /* loaded from: classes2.dex */
    public static class V2AltimeterEventMsg extends AbstractEventMsg<float[]> {
    }

    /* loaded from: classes2.dex */
    public static class V2FrontRadarDataEventMsg extends AbstractEventMsg<float[]> {
    }

    /* loaded from: classes2.dex */
    public static class V2FrontRadarFaultStEventMsg extends AbstractEventMsg<int[]> {
    }

    /* loaded from: classes2.dex */
    public static class V2FrontRadarLevelEventMsg extends AbstractEventMsg<int[]> {
    }

    /* loaded from: classes2.dex */
    public static class V2ScuAvmBox1UpdateEventMsg extends AbstractEventMsg<float[]> {
    }

    /* loaded from: classes2.dex */
    public static class V2ScuAvmBox2UpdateEventMsg extends AbstractEventMsg<float[]> {
    }

    /* loaded from: classes2.dex */
    public static class V2ScuSlot1UpdateEventMsg extends AbstractEventMsg<float[]> {
    }

    /* loaded from: classes2.dex */
    public static class V2ScuSlot2UpdateEventMsg extends AbstractEventMsg<float[]> {
    }

    /* loaded from: classes2.dex */
    public static class V2ScuSlot3UpdateEventMsg extends AbstractEventMsg<float[]> {
    }

    /* loaded from: classes2.dex */
    public static class V2ScuSlot4UpdateEventMsg extends AbstractEventMsg<float[]> {
    }

    /* loaded from: classes2.dex */
    public static class V2ScuSlot5UpdateEventMsg extends AbstractEventMsg<float[]> {
    }

    /* loaded from: classes2.dex */
    public static class V2SlotThetaEventMsg extends AbstractEventMsg<int[]> {
    }

    /* loaded from: classes2.dex */
    public static class V2TailRadarDataEventMsg extends AbstractEventMsg<float[]> {
    }

    /* loaded from: classes2.dex */
    public static class V2TailRadarFaultStEventMsg extends AbstractEventMsg<int[]> {
    }

    /* loaded from: classes2.dex */
    public static class V2TailRadarLevelEventMsg extends AbstractEventMsg<int[]> {
    }

    /* loaded from: classes2.dex */
    public static class V2TargetParkingPositionEventMsg extends AbstractEventMsg<int[]> {
    }

    float[] getAltimeterV2() throws Exception;

    int getFsdSwitchState() throws Exception;

    int getParkByMemorySwSt() throws Exception;

    int[] getTargetParkingPositionV2() throws Exception;

    void setFsdSwitch(int i) throws Exception;

    void setParkByMemorySw(int i) throws Exception;
}
