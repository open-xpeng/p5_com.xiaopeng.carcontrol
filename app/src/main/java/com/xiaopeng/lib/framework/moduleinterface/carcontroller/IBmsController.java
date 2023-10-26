package com.xiaopeng.lib.framework.moduleinterface.carcontroller;

/* loaded from: classes2.dex */
public interface IBmsController extends ILifeCycle {
    public static final int DC_CHARGE_STOP_REASON = 4;
    public static final int DTC_CODE_ERROR = 3;
    public static final int DTC_CODE_NORMAL = 0;

    /* loaded from: classes2.dex */
    public static class ACMaxCurrentEventMsg extends AbstractEventMsg<Float> {
    }

    /* loaded from: classes2.dex */
    public static class BatteryCurrEventMsg extends AbstractEventMsg<Float> {
    }

    /* loaded from: classes2.dex */
    public static class BatteryTempMaxEventMsg extends AbstractEventMsg<Float> {
    }

    /* loaded from: classes2.dex */
    public static class BatteryTempMaxNumEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class BatteryTempMinNumEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class BatteryTotalVoltEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class BmsFailureLvlEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class CellVoltMaxEventMsg extends AbstractEventMsg<Float> {
    }

    /* loaded from: classes2.dex */
    public static class CellVoltMaxNumEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class CellVoltMinEventMsg extends AbstractEventMsg<Float> {
    }

    /* loaded from: classes2.dex */
    public static class CellVoltMinNumEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class DCCurrentEventMsg extends AbstractEventMsg<Float> {
    }

    /* loaded from: classes2.dex */
    public static class DCVoltEventMsg extends AbstractEventMsg<Float> {
    }

    /* loaded from: classes2.dex */
    public static class DcChargeStopReasonEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class DtcChargeCurrentOverEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class DtcErrorStopCurrentEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class InsulationStEventMsg extends AbstractEventMsg<Integer> {
    }

    float getAcMaxCurrent() throws Exception;

    int getAddedElectricity();

    float getBatteryCurrent() throws Exception;

    int getBatteryTotalVolt() throws Exception;

    int getBmsFailureLvl() throws Exception;

    float getDCCurrent() throws Exception;

    float getDCVolt() throws Exception;

    int getDcChargeStopReason() throws Exception;

    int getDtcChargeCurrentOver() throws Exception;

    int getDtcErrorStopCurrent() throws Exception;

    int getInsulationResistance() throws Exception;

    float getTempMax() throws Exception;

    int getTempMaxNum() throws Exception;

    int getTempMinNum() throws Exception;

    float getVoltMax() throws Exception;

    int getVoltMaxNum() throws Exception;

    float getVoltMin() throws Exception;

    int getVoltMinNum() throws Exception;
}
