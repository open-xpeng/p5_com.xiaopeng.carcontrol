package com.xiaopeng.carcontrol.viewmodel.scu;

import com.xiaopeng.carcontrol.viewmodel.IBaseViewModel;

/* loaded from: classes2.dex */
public interface IScuViewModel extends IBaseViewModel {
    public static final int MRR_CLOSE_REASON_TELESCOPE = 1;
    public static final int NGP_XPILOT_ST_INIT = 0;
    public static final int NGP_XPILOT_ST_NOT_ACTIVATED = 3;
    public static final int NGP_XPILOT_ST_OFF = 2;
    public static final int NGP_XPILOT_ST_ON = 1;
    public static final int SDC_DOOR_OBSTACLE_DANGER = 1;
    public static final int SDC_DOOR_OBSTACLE_SAFETY = 2;
    public static final int SDC_DOOR_OBSTACLE_UNKNOWN = 3;

    void closeMrrByUser(int reason);

    void dismissCloseMrrDialog();

    ScuResponse getAccState();

    ScuResponse getAccStateForMirror();

    ScuResponse getAlcState();

    boolean getApaSafeExamResult();

    ScuResponse getAutoParkSw();

    ScuResponse getBsdState();

    ScuResponse getDowState();

    DsmState getDsmState();

    ScuResponse getElkState();

    FcwSensitivity getFcwSensitivity();

    ScuResponse getFcwState();

    ScuResponse getIhbState();

    boolean getIslaConfirmMode();

    ScuIslaMode getIslaMode();

    ScuIslaSpdRange getIslaSpdRange();

    ScuResponse getIslaState();

    ScuResponse getIslcState();

    ScuResponse getKeyParkSw();

    boolean getLccSafeExamResult();

    ScuResponse getLccState();

    ScuResponse getLccWorkSt();

    ScuResponse getLdwState();

    ScuResponse getLkaState();

    ScuLssMode getLssMode();

    boolean getMemParkSafeExamResult();

    ApResponse getMemoryParkSw();

    NgpChangeLaneMode getNgpChangeLaneMode();

    boolean getNgpQuickLaneSw();

    int getNgpRemindMode();

    boolean getNgpSafeExamResult();

    ScuResponse getNgpState();

    boolean getNgpTipsWindowSw();

    boolean getNgpTruckOffsetSw();

    boolean getNgpVoiceChangeLane();

    ScuResponse getPhoneParkSw();

    ScuResponse getRctaState();

    ScuResponse getRcwState();

    boolean getRemoteCameraSw();

    int getSdcLeftRadarDisLevel();

    int getSdcRightRadarDisLevel();

    ScuResponse getSlaState();

    int getSpecialSasMode();

    int getTtsBroadcastType();

    int getVpaState();

    ScuResponse getXngpState();

    int getXpuXpilotState();

    boolean isAutoPilotNeedTts();

    boolean isCngpRunning();

    boolean isLccVideoWatched();

    boolean isMemParkVideoWatched();

    boolean isNgpRunning();

    boolean isXpuXpilotActivated();

    void openMrrByUser(int reason);

    void setAlcEnable(boolean enable);

    void setAutoParkSw(boolean enable);

    void setAutoPilotNeedTts(boolean enable);

    void setBsdEnable(boolean enable);

    void setDistractionSwitch(boolean enable);

    void setDowEnable(boolean enable);

    void setDsmSw(boolean on);

    void setElkEnable(boolean enable);

    void setFcwEnable();

    void setFcwSensitivity(FcwSensitivity value);

    void setIhbEnable(boolean enable, boolean showToast);

    void setIslaConfirmMode(boolean enable);

    void setIslaMode(ScuIslaMode islaMode);

    void setIslaSpdRange(ScuIslaSpdRange spdRange);

    void setIslcEnable();

    void setKeyParkSw(boolean enable);

    void setLccEnable(boolean enable);

    void setLccVideoWatched(boolean watched);

    void setLdwEnable();

    void setLssMode(ScuLssMode lssMode);

    void setMemParkVideoWatched(boolean watched);

    void setMemoryParkSw(boolean enable);

    void setNgpChangeLaneMode(NgpChangeLaneMode mode);

    void setNgpEnable(boolean enable);

    void setNgpQuickLaneSw(boolean enable);

    void setNgpRemindMode(int mode);

    void setNgpTipsWindow(boolean enable);

    void setNgpTruckOffsetSw(boolean enable);

    void setNgpVoiceChangeLane(boolean enable);

    void setPhoneParkSw(boolean enable);

    void setRctaEnable();

    void setRcwEnable(boolean enable);

    void setRemoteCameraSw(boolean on);

    void setSpecialSasMode(int mode);

    void setTtsBroadcastType(int type);

    void setXngpEnable(boolean enable);

    void showCloseMrrDialog();
}
