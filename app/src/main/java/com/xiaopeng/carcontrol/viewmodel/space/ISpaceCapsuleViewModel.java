package com.xiaopeng.carcontrol.viewmodel.space;

import com.xiaopeng.carcontrol.viewmodel.IBaseViewModel;

/* loaded from: classes2.dex */
public interface ISpaceCapsuleViewModel extends IBaseViewModel {
    VipSeatStatus getCapsuleSingleVipSeatStatus();

    VipSeatStatus getCapsuleVipSeatStatus();

    VipSeatStatus getDrvVipSeatStatus();

    VipSeatStatus getPsnVipSeatStatus();

    VipSeatStatus getRearSeatStatus();

    VipSeatStatus getRlSeatStatus();

    VipSeatStatus getRrSeatStatus();

    VipSeatStatus getSleepSecSeatStatus();

    String getSpaceCapsuleSleepMusic();

    boolean isFirstEnterSpaceCapsule();

    boolean isSpaceCapsuleSleepBgmOpen();

    void onModeStart(boolean isVipMode, boolean mIsVoiceSource);

    void setCapsuleSingleVipSeatAct(VipSeatAct vipSeatAct);

    void setCapsuleVipSeatAct(VipSeatAct vipSeatAct);

    void setDrvVipSeatAct(VipSeatAct vipSeatAct);

    void setNotFirstEnterSpaceCapsule();

    void setPsnVipSeatAct(VipSeatAct vipSeatAct);

    void setRearSeatAct(VipSeatAct vipSeatAct);

    void setRlSeatAct(VipSeatAct vipSeatAct);

    void setRrSeatAct(VipSeatAct vipSeatAct);

    void setSpaceCapsuleSleepBgmOpen(boolean isOpen);

    void setSpaceCapsuleSleepMusic(String uri);

    void setSpeechStart();

    void setSpeechStop();

    void setVipSleepSecSeatControl(VipSeatAct vipSeatAct);

    void stopCapsuleSeatMove();

    void stopDrvSeatMove();

    void stopPsnSeatMove();

    void stopRearSeatMove();

    void stopRlSeatMove();

    void stopRrSeatMove();
}
