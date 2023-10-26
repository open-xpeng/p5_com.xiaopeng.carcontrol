package com.xiaopeng.carcontrol.viewmodel.xpu;

import com.xiaopeng.carcontrol.viewmodel.IBaseViewModel;
import com.xiaopeng.carcontrol.viewmodel.scu.ScuIslaMode;
import com.xiaopeng.carcontrol.viewmodel.scu.ScuResponse;

/* loaded from: classes2.dex */
public interface IXpuViewModel extends IBaseViewModel {
    public static final int LSS_SEN_ST_HIGH = 2;
    public static final int LSS_SEN_ST_LOW = 0;
    public static final int LSS_SEN_ST_MEDIUM = 1;

    void deleteCity(int id);

    void downloadCity(int id);

    void getAllCities();

    int getAsLockScenario();

    int getAsTargetMaxHeightRequest();

    int getAsTargetMinHeightRequest();

    ScuResponse getCityNgpSt();

    boolean getCngpSafeExamResult();

    ScuResponse getLccCrossBarriersSw();

    ScuResponse getLccTrafficLightSw();

    int getLssSensitivity();

    NedcState getNedcState();

    int getNgpCustomSpeedKph();

    int getNgpCustomSpeedMode();

    int getNgpCustomSpeedPercent();

    int getNgpOvertakeMode();

    int getNgpPreferLaneCfg();

    int getNraState();

    boolean getNraSw();

    ScuResponse getRaebState();

    ScuResponse getRemoteCarCallSw();

    ScuIslaMode getSimpleSasMode();

    boolean getSoundSw();

    boolean getTurnAssistantSw();

    boolean getVoiceSw();

    boolean getXngpSafeExamResult();

    boolean getXpuConnected();

    boolean isXpuNedcActivated();

    void setCityNgpSw(boolean enable);

    void setLccCrossBarriersSw(boolean enable);

    void setLccTrafficLightSw(boolean enable);

    void setLssSensitivity(int value);

    void setNedcSwitch(boolean active);

    void setNgpCustomSpeedKph(int value);

    void setNgpCustomSpeedMode(int mode);

    void setNgpCustomSpeedPercent(int value);

    void setNgpOvertakeMode(int mode);

    void setNgpPreferLaneCfg(int mode);

    void setNraState(int state);

    void setNraSwEnable(boolean enable);

    void setRaebEnable(boolean enable);

    void setRemoteCalCallSw(boolean enable);

    void setSimpleSasMode(ScuIslaMode islaMode);

    void setSoundSw(boolean enable);

    void setTurnAssistantEnable(boolean enable);

    void setVoiceSw(boolean enable);
}
