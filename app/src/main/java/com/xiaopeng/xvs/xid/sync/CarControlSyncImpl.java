package com.xiaopeng.xvs.xid.sync;

import com.xiaopeng.xvs.xid.sync.api.ICarControlSync;

/* loaded from: classes2.dex */
class CarControlSyncImpl implements ICarControlSync {
    SyncProviderWrapper mWrapper;

    /* JADX INFO: Access modifiers changed from: package-private */
    public CarControlSyncImpl(SyncProviderWrapper syncProviderWrapper) {
        this.mWrapper = syncProviderWrapper;
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getKeyImsStreamingMedia(String str) {
        return this.mWrapper.get(ICarControlSync.KEY_IMS_STREAMING_MEDIA, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void setKeyImsStreamingMedia(String str) {
        this.mWrapper.put(ICarControlSync.KEY_IMS_STREAMING_MEDIA, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getKeyXPilotNra(String str) {
        return this.mWrapper.get(ICarControlSync.KEY_XPILOT_NRA, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void setKeyXPilotNra(String str) {
        this.mWrapper.put(ICarControlSync.KEY_XPILOT_NRA, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getCrossBarriers(String str) {
        return this.mWrapper.get(ICarControlSync.CROSS_BARRIERS, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void setCrossBarriers(String str) {
        this.mWrapper.put(ICarControlSync.CROSS_BARRIERS, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getTrafficLight(String str) {
        return this.mWrapper.get(ICarControlSync.TRAFFIC_LIGHT, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void setTrafficLight(String str) {
        this.mWrapper.put(ICarControlSync.TRAFFIC_LIGHT, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getCmsAutoBrightnessSw(String str) {
        return this.mWrapper.get(ICarControlSync.CMS_AUTO_BRIGHTNESS_SW, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void setCmsAutoBrightnessSw(String str) {
        this.mWrapper.put(ICarControlSync.CMS_AUTO_BRIGHTNESS_SW, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getCmsBrightness(String str) {
        return this.mWrapper.get(ICarControlSync.CMS_BRIGHTNESS, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void setCmsBrightness(String str) {
        this.mWrapper.put(ICarControlSync.CMS_BRIGHTNESS, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getCmsHighSpdSw(String str) {
        return this.mWrapper.get(ICarControlSync.CMS_HIGH_SPD_SW, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void setCmsHighSpdSw(String str) {
        this.mWrapper.put(ICarControlSync.CMS_HIGH_SPD_SW, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getCmsLowSpdSw(String str) {
        return this.mWrapper.get(ICarControlSync.CMS_LOW_SPD_SW, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void setCmsLowSpdSw(String str) {
        this.mWrapper.put(ICarControlSync.CMS_LOW_SPD_SW, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getCmsReverseSw(String str) {
        return this.mWrapper.get(ICarControlSync.CMS_REVERSE_SW, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void setCmsReverseSw(String str) {
        this.mWrapper.put(ICarControlSync.CMS_REVERSE_SW, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getCmsTurnExtnSw(String str) {
        return this.mWrapper.get(ICarControlSync.CMS_TURN_EXTN_SW, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void setCmsTurnExtnSw(String str) {
        this.mWrapper.put(ICarControlSync.CMS_TURN_EXTN_SW, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getCmsObjectRecognizeSw(String str) {
        return this.mWrapper.get(ICarControlSync.CMS_OBJECT_RECOGNIZE_SW, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void setCmsObjectRecognizeSw(String str) {
        this.mWrapper.put(ICarControlSync.CMS_OBJECT_RECOGNIZE_SW, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getCmsViewRecoverySw(String str) {
        return this.mWrapper.get(ICarControlSync.CMS_VIEW_RECOVERY_SW, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void setCmsViewRecoverySw(String str) {
        this.mWrapper.put(ICarControlSync.CMS_VIEW_RECOVERY_SW, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getCmsPos(String str) {
        return this.mWrapper.get(ICarControlSync.CMS_POS, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void setCmsPos(String str) {
        this.mWrapper.put(ICarControlSync.CMS_POS, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getCmsViewAngle(String str) {
        return this.mWrapper.get(ICarControlSync.CMS_VIEW_ANGLE, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void setCmsViewAngle(String str) {
        this.mWrapper.put(ICarControlSync.CMS_VIEW_ANGLE, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getSinglePedal(String str) {
        return this.mWrapper.get(ICarControlSync.SINGLE_PEDAL, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void setSinglePedal(String str) {
        this.mWrapper.put(ICarControlSync.SINGLE_PEDAL, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getDomeLightBright(String str) {
        return this.mWrapper.get(ICarControlSync.DOME_LIGHT_BRIGHT, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void setDomeLightBright(String str) {
        this.mWrapper.put(ICarControlSync.DOME_LIGHT_BRIGHT, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getWheelXKey(String str) {
        return this.mWrapper.get(ICarControlSync.WHEEL_X_KEY, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void setWheelXKey(String str) {
        this.mWrapper.put(ICarControlSync.WHEEL_X_KEY, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getDoorBossKey(String str) {
        return this.mWrapper.get(ICarControlSync.DOOR_BOSS_KEY, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void setDoorBossKey(String str) {
        this.mWrapper.put(ICarControlSync.DOOR_BOSS_KEY, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getDoorBossKeySW(String str) {
        return this.mWrapper.get(ICarControlSync.DOOR_BOSS_KEY_SW, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void setDoorBossKeySW(String str) {
        this.mWrapper.put(ICarControlSync.DOOR_BOSS_KEY_SW, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getLightMeHome(String str) {
        return this.mWrapper.get(ICarControlSync.LIGHT_ME_HOME, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void setLightMeHome(String str) {
        this.mWrapper.put(ICarControlSync.LIGHT_ME_HOME, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getLightMeHomeTime(String str) {
        return this.mWrapper.get(ICarControlSync.LIGHT_ME_HOME_TIME, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void setLightMeHomeTime(String str) {
        this.mWrapper.put(ICarControlSync.LIGHT_ME_HOME_TIME, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getParkLampB(String str) {
        return this.mWrapper.get(ICarControlSync.PARK_LAMP_B, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void setParkLampB(String str) {
        this.mWrapper.put(ICarControlSync.PARK_LAMP_B, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getLluSW(String str) {
        return this.mWrapper.get(ICarControlSync.LLU_SW, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void setLluSW(String str) {
        this.mWrapper.put(ICarControlSync.LLU_SW, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getUnLockSW(String str) {
        return this.mWrapper.get(ICarControlSync.LLU_UNLOCK_SW, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void setUnLockSW(String str) {
        this.mWrapper.put(ICarControlSync.LLU_UNLOCK_SW, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getLockSW(String str) {
        return this.mWrapper.get(ICarControlSync.LLU_LOCK_SW, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void setLockSW(String str) {
        this.mWrapper.put(ICarControlSync.LLU_LOCK_SW, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getChargeSW(String str) {
        return this.mWrapper.get(ICarControlSync.LLU_CHARGE_SW, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void setChargeSW(String str) {
        this.mWrapper.put(ICarControlSync.LLU_CHARGE_SW, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getATLSW(String str) {
        return this.mWrapper.get(ICarControlSync.ATL_SW, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void setATLSW(String str) {
        this.mWrapper.put(ICarControlSync.ATL_SW, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getATLLight(String str) {
        return this.mWrapper.get(ICarControlSync.ATL_BRIGHT, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void setATLLight(String str) {
        this.mWrapper.put(ICarControlSync.ATL_BRIGHT, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getATLEffect(String str) {
        return this.mWrapper.get(ICarControlSync.ATL_EFFECT, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void setATLEffect(String str) {
        this.mWrapper.put(ICarControlSync.ATL_EFFECT, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getATLDualColorSW(String str) {
        return this.mWrapper.get(ICarControlSync.ATL_DUAL_COLOR_SW, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void setATLDualColorSW(String str) {
        this.mWrapper.put(ICarControlSync.ATL_DUAL_COLOR_SW, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getATLSingleColor(String str) {
        return this.mWrapper.get(ICarControlSync.ATL_SINGLE_COLOR, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void setATLSingleColor(String str) {
        this.mWrapper.put(ICarControlSync.ATL_SINGLE_COLOR, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getATLDualColor(String str) {
        return this.mWrapper.get(ICarControlSync.ATL_DUAL_COLOR, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void setATLDualColor(String str) {
        this.mWrapper.put(ICarControlSync.ATL_DUAL_COLOR, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getStopAutoUnLock(String str) {
        return this.mWrapper.get(ICarControlSync.STOP_AUTO_UNLOCK, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void setStopAutoUnLock(String str) {
        this.mWrapper.put(ICarControlSync.STOP_AUTO_UNLOCK, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getUnLockResponse(String str) {
        return this.mWrapper.get(ICarControlSync.UNLOCK_RESPONSE, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void setUnLockResponse(String str) {
        this.mWrapper.put(ICarControlSync.UNLOCK_RESPONSE, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getAutoDHC(String str) {
        return this.mWrapper.get(ICarControlSync.AUTO_DHC, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void setAutoDHC(String str) {
        this.mWrapper.put(ICarControlSync.AUTO_DHC, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getChildLeftLock(String str) {
        return this.mWrapper.get(ICarControlSync.CHILD_LEFT_LOCK, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void setChildLeftLock(String str) {
        this.mWrapper.put(ICarControlSync.CHILD_LEFT_LOCK, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getChildRightLock(String str) {
        return this.mWrapper.get(ICarControlSync.CHILD_RIGHT_LOCK, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void setChildRightLock(String str) {
        this.mWrapper.put(ICarControlSync.CHILD_RIGHT_LOCK, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getChairPositionOne(String str) {
        return this.mWrapper.get(ICarControlSync.CHAIR_POSITION, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void setChairPositionOne(String str) {
        this.mWrapper.put(ICarControlSync.CHAIR_POSITION, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getChairPositionTwo(String str) {
        return this.mWrapper.get("CHAIR_POSITION_TWO", str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void setChairPositionTwo(String str) {
        this.mWrapper.put("CHAIR_POSITION_TWO", str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getChairPositionThree(String str) {
        return this.mWrapper.get("CHAIR_POSITION_THREE", str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void setChairPositionThree(String str) {
        this.mWrapper.put("CHAIR_POSITION_THREE", str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getChairPosIndex(String str) {
        return this.mWrapper.get(ICarControlSync.CHAIR_POS_INDEX, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void setChairPosIndex(String str) {
        this.mWrapper.put(ICarControlSync.CHAIR_POS_INDEX, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getWelcomeMode(String str) {
        return this.mWrapper.get("WELCOME_MODE", str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void setWelcomeMode(String str) {
        this.mWrapper.put("WELCOME_MODE", str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getDrvSeatESB(String str) {
        return this.mWrapper.get(ICarControlSync.DRV_SEAT_ESB, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void setDrvSeatESB(String str) {
        this.mWrapper.put(ICarControlSync.DRV_SEAT_ESB, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getMirrorPos(String str) {
        return this.mWrapper.get(ICarControlSync.MIRROR_POS, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void setMirrorPos(String str) {
        this.mWrapper.put(ICarControlSync.MIRROR_POS, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getMirrorReverse(String str) {
        return this.mWrapper.get(ICarControlSync.MIRROR_REVERSE, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void setMirrorReverse(String str) {
        this.mWrapper.put(ICarControlSync.MIRROR_REVERSE, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getHighSPDCloseWin(String str) {
        return this.mWrapper.get(ICarControlSync.HIGH_SPD_CLOSE_WIN, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void setHighSPDCloseWin(String str) {
        this.mWrapper.put(ICarControlSync.HIGH_SPD_CLOSE_WIN, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getLockCloseWin(String str) {
        return this.mWrapper.get(ICarControlSync.LOCK_CLOSE_WIN, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void setLockCloseWin(String str) {
        this.mWrapper.put(ICarControlSync.LOCK_CLOSE_WIN, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getWiperSensitivity(String str) {
        return this.mWrapper.get(ICarControlSync.WIPER_SENSITIVITY, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void setWiperSensitivity(String str) {
        this.mWrapper.put(ICarControlSync.WIPER_SENSITIVITY, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getAvasEffectNew(String str) {
        return this.mWrapper.get(ICarControlSync.AVAS_EFFECT_NEW, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void setAvasEffectNew(String str) {
        this.mWrapper.put(ICarControlSync.AVAS_EFFECT_NEW, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getAvasEffect(String str) {
        return this.mWrapper.get(ICarControlSync.AVAS_EFFECT, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void setAvasEffect(String str) {
        this.mWrapper.put(ICarControlSync.AVAS_EFFECT, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getAvasSayHiEffect(String str) {
        return this.mWrapper.get(ICarControlSync.AVAS_SAYHI_EFFECT, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void setAvasSayHiEffect(String str) {
        this.mWrapper.put(ICarControlSync.AVAS_SAYHI_EFFECT, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getAvasBootEffect(String str) {
        return this.mWrapper.get(ICarControlSync.AVAS_BOOT_EFFECT, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void setAvasBootEffect(String str) {
        this.mWrapper.put(ICarControlSync.AVAS_BOOT_EFFECT, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getAvasBootEffectBefore(String str) {
        return this.mWrapper.get(ICarControlSync.AVAS_BOOT_EFFECT_BEFORE_SW, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void setAvasBootEffectBefore(String str) {
        this.mWrapper.put(ICarControlSync.AVAS_BOOT_EFFECT_BEFORE_SW, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getChairPosNew(String str) {
        return this.mWrapper.get(ICarControlSync.CHAIR_POSITION_NEW, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void setChairPosNew(String str) {
        this.mWrapper.put(ICarControlSync.CHAIR_POSITION_NEW, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void setChairPosNew(int i, String str) {
        this.mWrapper.put(i, ICarControlSync.CHAIR_POSITION_NEW, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getChairPosPsn(String str) {
        return this.mWrapper.get(ICarControlSync.CHAIR_POSITION_PSN, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void setChairPosPsn(String str) {
        this.mWrapper.put(ICarControlSync.CHAIR_POSITION_PSN, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getXPilotLcc(String str) {
        return this.mWrapper.get(ICarControlSync.XPILOT_LCC, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void putXPilotLcc(String str) {
        this.mWrapper.put(ICarControlSync.XPILOT_LCC, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getXPilotAlc(String str) {
        return this.mWrapper.get(ICarControlSync.XPILOT_ALC, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void putXPilotAlc(String str) {
        this.mWrapper.put(ICarControlSync.XPILOT_ALC, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getNgpSafeExam(String str) {
        return this.mWrapper.get(ICarControlSync.NGP_SAFE_EXAM, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void putNgpSafeExam(String str) {
        this.mWrapper.put(ICarControlSync.NGP_SAFE_EXAM, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getNgpSw(String str) {
        return this.mWrapper.get(ICarControlSync.NGP_SW, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void putNgpSw(String str) {
        this.mWrapper.put(ICarControlSync.NGP_SW, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getDriveAutoLock(String str) {
        return this.mWrapper.get(ICarControlSync.DRIVE_AUTO_LOCK, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void putDriveAutoLock(String str) {
        this.mWrapper.put(ICarControlSync.DRIVE_AUTO_LOCK, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getMeterTemperature(String str) {
        return this.mWrapper.get(ICarControlSync.METER_DEFINE_TEMPERATURE, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void putMeterTemperature(String str) {
        this.mWrapper.put(ICarControlSync.METER_DEFINE_TEMPERATURE, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getMeterWindPower(String str) {
        return this.mWrapper.get(ICarControlSync.METER_DEFINE_WIND_POWER, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void putMeterWindPower(String str) {
        this.mWrapper.put(ICarControlSync.METER_DEFINE_WIND_POWER, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getMeterWindMode(String str) {
        return this.mWrapper.get(ICarControlSync.METER_DEFINE_WIND_MODE, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void putMeterWindMode(String str) {
        this.mWrapper.put(ICarControlSync.METER_DEFINE_WIND_MODE, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getMeterMediaSource(String str) {
        return this.mWrapper.get(ICarControlSync.METER_DEFINE_MEDIA_SOURCE, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void putMeterMediaSource(String str) {
        this.mWrapper.put(ICarControlSync.METER_DEFINE_MEDIA_SOURCE, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getMeterScreenLight(String str) {
        return this.mWrapper.get(ICarControlSync.METER_DEFINE_SCREEN_LIGHT, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void putMeterScreenLight(String str) {
        this.mWrapper.put(ICarControlSync.METER_DEFINE_SCREEN_LIGHT, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getSpeedLimitSw(String str) {
        return this.mWrapper.get(ICarControlSync.SPEED_LIMIT, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void putSpeedLimitSw(String str) {
        this.mWrapper.put(ICarControlSync.SPEED_LIMIT, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getSpeedLimitValue(String str) {
        return this.mWrapper.get(ICarControlSync.SPEED_LIMIT_VALUE, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void putSpeedLimitValue(String str) {
        this.mWrapper.put(ICarControlSync.SPEED_LIMIT_VALUE, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getRearSeatWarning(String str) {
        return this.mWrapper.get(ICarControlSync.REAR_SEAT_WARNING, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void putRearSeatWarning(String str) {
        this.mWrapper.put(ICarControlSync.REAR_SEAT_WARNING, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getEbwSw(String str) {
        return this.mWrapper.get(ICarControlSync.BRAKE_WARNING, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void putEbwSw(String str) {
        this.mWrapper.put(ICarControlSync.BRAKE_WARNING, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getLdwSw(String str) {
        return this.mWrapper.get(ICarControlSync.LANE_DEPARTURE_WARNING, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void putLdwSw(String str) {
        this.mWrapper.put(ICarControlSync.LANE_DEPARTURE_WARNING, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getBsdSw(String str) {
        return this.mWrapper.get(ICarControlSync.BLIND_DETECTION_WARNING, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void putBsdSw(String str) {
        this.mWrapper.put(ICarControlSync.BLIND_DETECTION_WARNING, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getRctaSw(String str) {
        return this.mWrapper.get(ICarControlSync.SIDE_REVERSING_WARNING, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void putRctaSw(String str) {
        this.mWrapper.put(ICarControlSync.SIDE_REVERSING_WARNING, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getAutoParkSound(String str) {
        return this.mWrapper.get(ICarControlSync.AUTO_PARKING_NEW, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void putAutoParkSound(String str) {
        this.mWrapper.put(ICarControlSync.AUTO_PARKING_NEW, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getIsLcSw(String str) {
        return this.mWrapper.get(ICarControlSync.SMART_SPEED_LIMIT, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void putIsLcSw(String str) {
        this.mWrapper.put(ICarControlSync.SMART_SPEED_LIMIT, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getSteerMode(String str) {
        return this.mWrapper.get(ICarControlSync.STEER_MODE, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void putSteerMode(String str) {
        this.mWrapper.put(ICarControlSync.STEER_MODE, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getNgpTipWin(String str) {
        return this.mWrapper.get(ICarControlSync.NGP_TIP_WIN, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void putNgpTipWin(String str) {
        this.mWrapper.put(ICarControlSync.NGP_TIP_WIN, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getNgpLcMode(String str) {
        return this.mWrapper.get(ICarControlSync.NGP_LC_MODE, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void putNgpLcMode(String str) {
        this.mWrapper.put(ICarControlSync.NGP_LC_MODE, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getNgpRemindMode(String str) {
        return this.mWrapper.get(ICarControlSync.NGP_REMIND_MODE, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void putNgpRemindMode(String str) {
        this.mWrapper.put(ICarControlSync.NGP_REMIND_MODE, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getAutoParkSw(String str) {
        return this.mWrapper.get("XPILOT_AUTO_PARK_SW", str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void putAutoParkSw(String str) {
        this.mWrapper.put("XPILOT_AUTO_PARK_SW", str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getLssState(String str) {
        return this.mWrapper.get("XPILOT_AUTO_PARK_SW", str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void putLssState(String str) {
        this.mWrapper.put("XPILOT_AUTO_PARK_SW", str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getMemParkSw(String str) {
        return this.mWrapper.get(ICarControlSync.MEM_PARK_SW, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void putMemParkSw(String str) {
        this.mWrapper.put(ICarControlSync.MEM_PARK_SW, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getDriveMode(String str) {
        return this.mWrapper.get(ICarControlSync.DRIVE_MODE, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void putDriveMode(String str) {
        this.mWrapper.put(ICarControlSync.DRIVE_MODE, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getRecycleGrade(String str) {
        return this.mWrapper.get(ICarControlSync.KEY_RECYCLE_GRADE, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void putRecycleGrade(String str) {
        this.mWrapper.put(ICarControlSync.KEY_RECYCLE_GRADE, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getXpedalMode(String str) {
        return this.mWrapper.get(ICarControlSync.XPEDAL_MODE, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void putXpedalMode(String str) {
        this.mWrapper.put(ICarControlSync.XPEDAL_MODE, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getAntisickMode(String str) {
        return this.mWrapper.get(ICarControlSync.ANTISICK_MODE, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void putAntisickMode(String str) {
        this.mWrapper.put(ICarControlSync.ANTISICK_MODE, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getMicrophoneMute(String str) {
        return this.mWrapper.get(ICarControlSync.MICROPHONE_MUTE, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void putMicrophoneMute(String str) {
        this.mWrapper.put(ICarControlSync.MICROPHONE_MUTE, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getAutoHoldSw(String str) {
        return this.mWrapper.get(ICarControlSync.CHASSIS_AVH, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void putAutoHoldSw(String str) {
        this.mWrapper.put(ICarControlSync.CHASSIS_AVH, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getWheelKeyProtectSw(String str) {
        return this.mWrapper.get(ICarControlSync.WHEEL_KEY_PROTECT, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void putWheelKeyProtectSw(String str) {
        this.mWrapper.put(ICarControlSync.WHEEL_KEY_PROTECT, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getNGearProtectSw(String str) {
        return this.mWrapper.get(ICarControlSync.N_GEAR_PROTECT, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void putNGearProtectSw(String str) {
        this.mWrapper.put(ICarControlSync.N_GEAR_PROTECT, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getMirrorAutoDownSw(String str) {
        return this.mWrapper.get(ICarControlSync.MIRROR_AUTO_DOWN, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void putMirrorAutoDownSw(String str) {
        this.mWrapper.put(ICarControlSync.MIRROR_AUTO_DOWN, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getLampHeightLevel(String str) {
        return this.mWrapper.get(ICarControlSync.LAMP_HEIGHT_LEVEL, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void putLampHeightLevel(String str) {
        this.mWrapper.put(ICarControlSync.LAMP_HEIGHT_LEVEL, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getAutoLampHeight(String str) {
        return this.mWrapper.get(ICarControlSync.AUTO_LAMP_HEIGHT, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void putAutoLampHeight(String str) {
        this.mWrapper.put(ICarControlSync.AUTO_LAMP_HEIGHT, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getSteerPos(String str) {
        return this.mWrapper.get(ICarControlSync.STEER_POS, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void putSteerPos(String str) {
        this.mWrapper.put(ICarControlSync.STEER_POS, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getLeftDoorHotKey(String str) {
        return this.mWrapper.get(ICarControlSync.LEFT_DOOR_HOT_KEY, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void putLeftDoorHotKey(String str) {
        this.mWrapper.put(ICarControlSync.LEFT_DOOR_HOT_KEY, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getRightDoorHotKey(String str) {
        return this.mWrapper.get(ICarControlSync.RIGHT_DOOR_HOT_KEY, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void putRightDoorHotKey(String str) {
        this.mWrapper.put(ICarControlSync.RIGHT_DOOR_HOT_KEY, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getAdaptiveMode(String str) {
        return this.mWrapper.get(ICarControlSync.ADAPTIVE_MODE, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void setAdaptiveMode(String str) {
        this.mWrapper.put(ICarControlSync.ADAPTIVE_MODE, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getCityNgpSw(String str) {
        return this.mWrapper.get(ICarControlSync.CITY_NGP_SW, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void setCityNgpSw(String str) {
        this.mWrapper.put(ICarControlSync.CITY_NGP_SW, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getCwcSw(String str) {
        return this.mWrapper.get(ICarControlSync.CWC_SW, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void setCwcSw(String str) {
        this.mWrapper.put(ICarControlSync.CWC_SW, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getSdcBreakCloseType(String str) {
        return this.mWrapper.get(ICarControlSync.SDC_BREAK_CLOSE, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void setSdcBreakCloseType(String str) {
        this.mWrapper.put(ICarControlSync.SDC_BREAK_CLOSE, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getCdcMode(String str) {
        return this.mWrapper.get(ICarControlSync.CDC_MODE, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void setCdcMode(String str) {
        this.mWrapper.put(ICarControlSync.CDC_MODE, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getSensorTrunkSw(String str) {
        return this.mWrapper.get(ICarControlSync.SENSOR_TRUNK_SW, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void setSensorTrunkSw(String str) {
        this.mWrapper.put(ICarControlSync.SENSOR_TRUNK_SW, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getTrunkOpenPos(String str) {
        return this.mWrapper.get(ICarControlSync.TRUNK_OPEN_POS, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void setTrunkOpenPos(String str) {
        this.mWrapper.put(ICarControlSync.TRUNK_OPEN_POS, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getAsWelcomeMode(String str) {
        return this.mWrapper.get(ICarControlSync.AS_WELCOME_MODE, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void setAsWelcomeMode(String str) {
        this.mWrapper.put(ICarControlSync.AS_WELCOME_MODE, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public String getRearSeatWelcomeMode(String str) {
        return this.mWrapper.get(ICarControlSync.REAR_SEAT_WELCOME_MODE, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarControlSync
    public void setRearSeatWelcomeMode(String str) {
        this.mWrapper.put(ICarControlSync.REAR_SEAT_WELCOME_MODE, str);
    }
}
