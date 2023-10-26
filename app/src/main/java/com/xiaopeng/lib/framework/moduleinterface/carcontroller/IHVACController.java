package com.xiaopeng.lib.framework.moduleinterface.carcontroller;

/* loaded from: classes2.dex */
public interface IHVACController extends ILifeCycle {

    /* loaded from: classes2.dex */
    public static class HVACAutoModeBlowLevelEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class HVACAutoModeEventMsg extends AbstractEventMsg<Boolean> {
    }

    /* loaded from: classes2.dex */
    public static class HVACCirculationModeEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class HVACEconEventMsg extends AbstractEventMsg<Boolean> {
    }

    /* loaded from: classes2.dex */
    public static class HVACFrontDefrostModeEventMsg extends AbstractEventMsg<Boolean> {
    }

    /* loaded from: classes2.dex */
    public static class HVACInnerTempEventMsg extends AbstractEventMsg<Float> {
    }

    /* loaded from: classes2.dex */
    public static class HVACPowerModeEventMsg extends AbstractEventMsg<Boolean> {
    }

    /* loaded from: classes2.dex */
    public static class HVACQualityInnerPM25ValueEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class HVACQualityOutsideLevelEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class HVACQualityOutsideStatusEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class HVACQualityPurgeModeEventMsg extends AbstractEventMsg<Boolean> {
    }

    /* loaded from: classes2.dex */
    public static class HVACTempACModeEventMsg extends AbstractEventMsg<Boolean> {
    }

    /* loaded from: classes2.dex */
    public static class HVACTempDriverValueEventMsg extends AbstractEventMsg<Float> {
    }

    /* loaded from: classes2.dex */
    public static class HVACTempPTCStatusEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class HVACTempPsnValueEventMsg extends AbstractEventMsg<Float> {
    }

    /* loaded from: classes2.dex */
    public static class HVACTempSyncModEventMsg extends AbstractEventMsg<Boolean> {
    }

    /* loaded from: classes2.dex */
    public static class HVACWindBlowModeEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class HVACWindSpeedLevelEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class OutsideAirTempEventMsg extends AbstractEventMsg<Float> {
    }

    int[] getCompressorErrorInfo() throws Exception;

    boolean getHVACAutoMode() throws Exception;

    int getHVACAutoModeBlowLevel() throws Exception;

    int getHVACCirculationMode() throws Exception;

    boolean getHVACEcon() throws Exception;

    boolean getHVACFrontDefrostMode() throws Exception;

    float getHVACInnerTemp() throws Exception;

    boolean getHVACPowerMode() throws Exception;

    int getHVACQualityInnerPM25Value() throws Exception;

    int getHVACQualityOutsideLevel() throws Exception;

    int getHVACQualityOutsideStatus() throws Exception;

    boolean getHVACQualityPurgeMode() throws Exception;

    boolean getHVACTempACMode() throws Exception;

    float getHVACTempDriverValue() throws Exception;

    float getHVACTempPsnValue() throws Exception;

    boolean getHVACTempSyncMode() throws Exception;

    int getHVACWindBlowMode() throws Exception;

    int getHVACWindSpeedLevel() throws Exception;

    float getOutsideAirTemp() throws Exception;

    int getPtcError() throws Exception;

    int getTempPTCStatus() throws Exception;

    boolean isError() throws Exception;

    void setHVACAutoMode() throws Exception;

    void setHVACAutoMode(boolean z) throws Exception;

    void setHVACAutoModeBlowLevel(int i) throws Exception;

    void setHVACCirculationMode() throws Exception;

    void setHVACCirculationMode(int i) throws Exception;

    void setHVACEcon(boolean z) throws Exception;

    void setHVACFrontDefrostMode() throws Exception;

    void setHVACFrontDefrostMode(boolean z) throws Exception;

    @Deprecated
    void setHVACPowerMode() throws Exception;

    void setHVACPowerMode(boolean z) throws Exception;

    void setHVACQualityPurgeMode() throws Exception;

    void setHVACQualityPurgeMode(boolean z) throws Exception;

    void setHVACTempACMode() throws Exception;

    void setHVACTempACMode(boolean z) throws Exception;

    void setHVACTempDriverDown() throws Exception;

    void setHVACTempDriverDown(float f) throws Exception;

    void setHVACTempDriverUp() throws Exception;

    void setHVACTempDriverUp(float f) throws Exception;

    void setHVACTempDriverValue(float f) throws Exception;

    void setHVACTempPsnDown() throws Exception;

    void setHVACTempPsnDown(float f) throws Exception;

    void setHVACTempPsnUp() throws Exception;

    void setHVACTempPsnUp(float f) throws Exception;

    void setHVACTempPsnValue(float f) throws Exception;

    void setHVACTempSyncMode() throws Exception;

    void setHVACTempSyncMode(boolean z) throws Exception;

    void setHVACWindBlowMode(int i) throws Exception;

    void setHVACWindSpeedDown() throws Exception;

    void setHVACWindSpeedDown(int i) throws Exception;

    void setHVACWindSpeedLevel(int i) throws Exception;

    void setHVACWindSpeedUp() throws Exception;

    void setHVACWindSpeedUp(int i) throws Exception;

    void setTempPTCStatus(int i) throws Exception;
}
