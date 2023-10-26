package com.xiaopeng.carcontrol.model;

import com.xiaopeng.carcontrol.GlobalConstant;
import com.xiaopeng.carcontrol.carmanager.impl.VcuController;
import com.xiaopeng.carcontrol.config.CarBaseConfig;

/* loaded from: classes2.dex */
public class CarControlSyncDataValue {
    private int mLssState;
    private int mRecycleGrade;
    private int mDriveMode = GlobalConstant.DEFAULT.DRIVER_MODE;
    private boolean mAutoDriveMode = false;
    private int mSteerMode = 0;
    private boolean mAvh = true;
    private int mEsp = 1;
    private boolean mXPedal = false;
    private boolean mAntiSick = false;
    private boolean mAsWelcomeMode = false;
    private int mCdcMode = 1;
    private boolean mSinglePedal = false;
    private boolean mWheelTouchHint = true;
    private int mWheelTouchDirection = 1;
    private int mWheelTouchSpeed = 1;
    private int mWheelXKey = 0;
    private int mDoorBossKey = 0;
    private boolean mDoorBossKeySw = false;
    private boolean mWheelKeyProtect = true;
    private boolean mNGearProtectSw = true;
    private boolean mLightMeHomeSw = true;
    private int mLightMeHomeCfg = 1;
    private boolean mAutoLightSw = false;
    private int mDomeLightCfg = 1;
    private int mDomeLightBright = 3;
    private boolean mParkLampB = true;
    private boolean mLluSw = true;
    private boolean mLluUnlockSw = true;
    private boolean mLluLockSw = true;
    private boolean mLluChargeSw = true;
    private boolean mAtlSw = true;
    private boolean mAtlAutoBrightSw = true;
    private int mAtlBrightCfg = 100;
    private String mAtlEffectCfg = "stable_effect";
    private boolean mAtlDualColorSw = false;
    private int mAtlSingleColor = 14;
    private String mAtlDualColor = null;
    private boolean mParkAutoUnlock = false;
    private boolean mDriveAutoLock = true;
    private boolean mPollingLock = true;
    private boolean mPollingUnlock = true;
    private int mUnlockResponse = GlobalConstant.DEFAULT.UNLOCK_RESPONSE;
    private boolean mAutoDhc = GlobalConstant.DEFAULT.AUTO_DOOR_HANDLE;
    private int mChildLock = 1;
    private boolean mChildLeftLock = false;
    private boolean mChildRightLock = false;
    private boolean mLeftDoorHotKey = false;
    private boolean mRightDoorHotKey = false;
    private String mDrvSeatPos1 = null;
    private String mDrvSeatPos2 = null;
    private String mDrvSeatPos3 = null;
    private int mDrvSeatPosIdx = 0;
    private String mDrvSeatPosNew = null;
    private String mPsnSeatPos = null;
    private boolean mWelcomeMode = true;
    private boolean mPsnWelcomeMode = true;
    private boolean mRearSeatWelcomeMode = true;
    private boolean mDrvSeatEsb = true;
    private String mSteerPos = GlobalConstant.DEFAULT.STEER_POS;
    private String mMirrorPos = null;
    private int mMirrorReverseMode = 3;
    private boolean mNeedRestore = false;
    private boolean mMirrorAutoDown = true;
    private boolean mMirrorAutoFoldSw = true;
    private boolean mSensorTrunkSw = true;
    private int mTrunkFullOpenPos = 6;
    private boolean mHighSpdCloseWin = false;
    private boolean mLockCloseWin = true;
    private int mWiperInterval = 3;
    private int mWiperSensitivity = GlobalConstant.DEFAULT.WIPER_Sensitivity;
    private boolean mCwcSw = true;
    private boolean mMicrophoneMute = false;
    private int mHvacCircleMode = 2;
    private int mAqsLevel = 2;
    private int mAirProtectMode = 1;
    private boolean mHvacSelfDry = true;
    private boolean mHvacSmart = false;
    private boolean mAvasSw = true;
    private int mAvasEffect = 1;
    private int mAvasVolume = 100;
    private int mSayHiEffect = 1;
    private int mOtherVolume = 100;
    private int mBootEffect = 1;
    private int mBootEffectBeforeSw = 1;
    private boolean mMeterDefineTemperature = true;
    private boolean mMeterDefineWindPower = true;
    private boolean mMeterDefineWindMode = true;
    private boolean mMeterDefineMediaSource = CarBaseConfig.getInstance().isSupportSwitchMedia();
    private boolean mMeterDefineScreenLight = true;
    private boolean mSpeedLimit = false;
    private int mSpeedLimitValue = 80;
    private boolean mFcwSw = true;
    private boolean mBsdSw = true;
    private boolean mDowSw = true;
    private boolean mLdwSw = false;
    private boolean mRcwSw = false;
    private boolean mRctaSw = true;
    private boolean mAutoParkSw = false;
    private boolean mMemParkSw = false;
    private int mNraState = 2;
    private int mParkSoundCfg = 0;
    private boolean mRemoteCallSw = false;
    private boolean mKeyParkSw = false;
    private int mKeyParkCfg = 2;
    private boolean mPhoneParkSw = false;
    private int mPhoneParkCfg = 2;
    private boolean mLccSw = false;
    private boolean mIslcSw = false;
    private boolean mAlcSw = false;
    private boolean mNgpSafeExamResult = false;
    private boolean mNgpSw = false;
    private boolean mNgpFastLane = false;
    private boolean mNgpTruckOffset = false;
    private boolean mNgpTipWin = false;
    private boolean mNgpAutoLcs = false;
    private int mNgpLcMode = 1;
    private int mNgpRemindMode = 0;
    private boolean mCityNgpSw = false;
    private boolean mSideReversingWarning = true;
    private boolean mBrakeWarning = GlobalConstant.DEFAULT.EMERGENCY_BREAK_WARNING;
    private boolean mRsbWarning = true;
    private int mLampHeightLevel = 0;
    private boolean mAutoLampHeight = true;
    private int mSdcBrakeCloseCfg = 0;
    private boolean mCmsReverseSw = true;
    private boolean mCmsTurnSw = true;
    private boolean mCmsHighSpdSw = true;
    private boolean mCmsLowSpdSw = true;
    private boolean mCmsAutoBrightSw = true;
    private int mCmsBright = 50;
    private String mCmsPos = null;
    private int mCmsViewAngle = 1;
    private boolean mCmsObjectRecognizeSw = true;
    private boolean mCmsViewRecoverySw = true;

    public CarControlSyncDataValue() {
        this.mRecycleGrade = GlobalConstant.DEFAULT.ENERGY_RECYCLE_GRADE;
        this.mRecycleGrade = VcuController.getDefaultEnergyRecycleGrade();
    }

    public int getDriveMode() {
        return this.mDriveMode;
    }

    public void setDriveMode(int driveMode) {
        this.mDriveMode = driveMode;
    }

    public boolean isAutoDriveMode() {
        return this.mAutoDriveMode;
    }

    public void setAutoDriveMode(boolean autoDriveMode) {
        this.mAutoDriveMode = autoDriveMode;
    }

    public int getRecycleGrade() {
        return this.mRecycleGrade;
    }

    public void setRecycleGrade(int recycleGrade) {
        this.mRecycleGrade = recycleGrade;
    }

    public int getSteerMode() {
        return this.mSteerMode;
    }

    public void setSteerMode(int steerMode) {
        this.mSteerMode = steerMode;
    }

    public int getCdcMode() {
        return this.mCdcMode;
    }

    public void setCdcMode(int cdcMode) {
        this.mCdcMode = cdcMode;
    }

    public boolean isAvh() {
        return this.mAvh;
    }

    public void setAvh(boolean avh) {
        this.mAvh = avh;
    }

    public boolean getAsWelcomeMode() {
        return this.mAsWelcomeMode;
    }

    public void setAsWelcomeMode(boolean asWelcomeMode) {
        this.mAsWelcomeMode = asWelcomeMode;
    }

    public int getEsp() {
        return this.mEsp;
    }

    public void setEsp(int esp) {
        this.mEsp = esp;
    }

    public boolean getXpedalSw() {
        return this.mXPedal;
    }

    public void setXpedalSw(boolean enable) {
        this.mXPedal = enable;
    }

    public boolean getAntiSickSw() {
        return this.mAntiSick;
    }

    public void setAntiSickSw(boolean enable) {
        this.mAntiSick = enable;
    }

    public boolean getSinglePedal() {
        return this.mSinglePedal;
    }

    public void setSinglePedal(boolean mSinglePedal) {
        this.mSinglePedal = mSinglePedal;
    }

    public boolean isWheelTouchHint() {
        return this.mWheelTouchHint;
    }

    public void setWheelTouchHint(boolean wheelTouchHint) {
        this.mWheelTouchHint = wheelTouchHint;
    }

    public int getWheelTouchDirection() {
        return this.mWheelTouchDirection;
    }

    public void setWheelTouchDirection(int wheelTouchDirection) {
        this.mWheelTouchDirection = wheelTouchDirection;
    }

    public int getWheelTouchSpeed() {
        return this.mWheelTouchSpeed;
    }

    public void setWheelTouchSpeed(int wheelTouchSpeed) {
        this.mWheelTouchSpeed = wheelTouchSpeed;
    }

    public int getWheelXKey() {
        return this.mWheelXKey;
    }

    public void setWheelXKey(int wheelXKey) {
        this.mWheelXKey = wheelXKey;
    }

    public int getDoorBossKey() {
        return this.mDoorBossKey;
    }

    public void setDoorBossKey(int doorBossKey) {
        this.mDoorBossKey = doorBossKey;
    }

    public boolean getDoorBossKeySw() {
        return this.mDoorBossKeySw;
    }

    public void setDoorBossKeySw(boolean doorBossKeySw) {
        this.mDoorBossKeySw = doorBossKeySw;
    }

    public boolean getWheelKeyProtect() {
        return this.mWheelKeyProtect;
    }

    public void setWheelKeyProtect(boolean enable) {
        this.mWheelKeyProtect = enable;
    }

    public boolean getNGearProtectSw() {
        return this.mNGearProtectSw;
    }

    public void setNGearProtectSw(boolean ngearProtectSw) {
        this.mNGearProtectSw = ngearProtectSw;
    }

    public boolean isLightMeHomeSw() {
        return this.mLightMeHomeSw;
    }

    public void setLightMeHomeSw(boolean lightMeHomeSw) {
        this.mLightMeHomeSw = lightMeHomeSw;
    }

    public int getLightMeHomeCfg() {
        return this.mLightMeHomeCfg;
    }

    public void setLightMeHomeCfg(int lightMeHomeCfg) {
        this.mLightMeHomeCfg = lightMeHomeCfg;
    }

    public boolean isAutoLightSw() {
        return this.mAutoLightSw;
    }

    public void setAutoLightSw(boolean autoLightSw) {
        this.mAutoLightSw = autoLightSw;
    }

    public int getDomeLightCfg() {
        return this.mDomeLightCfg;
    }

    public void setDomeLightCfg(int domeLightCfg) {
        this.mDomeLightCfg = domeLightCfg;
    }

    public int getDomeLightBright() {
        return this.mDomeLightBright;
    }

    public void setDomeLightBright(int brightness) {
        this.mDomeLightBright = brightness;
    }

    public boolean getParkLampB() {
        return this.mParkLampB;
    }

    public void setParkLampB(boolean parkLampB) {
        this.mParkLampB = parkLampB;
    }

    public boolean isLluSw() {
        return this.mLluSw;
    }

    public void setLluSw(boolean lluSw) {
        this.mLluSw = lluSw;
    }

    public boolean isLluUnlockSw() {
        return this.mLluUnlockSw;
    }

    public void setLluUnlockSw(boolean lluUnlockSw) {
        this.mLluUnlockSw = lluUnlockSw;
    }

    public boolean isLluLockSw() {
        return this.mLluLockSw;
    }

    public void setLluLockSw(boolean lluLockSw) {
        this.mLluLockSw = lluLockSw;
    }

    public boolean isLluChargeSw() {
        return this.mLluChargeSw;
    }

    public void setLluChargeSw(boolean lluChargeSw) {
        this.mLluChargeSw = lluChargeSw;
    }

    public boolean isAtlSw() {
        return this.mAtlSw;
    }

    public void setAtlSw(boolean atlSw) {
        this.mAtlSw = atlSw;
    }

    public boolean isAtlAutoBrightSw() {
        return this.mAtlAutoBrightSw;
    }

    public void setAtlAutoBrightSw(boolean atlAutoBrightSw) {
        this.mAtlAutoBrightSw = atlAutoBrightSw;
    }

    public int getAtlBrightCfg() {
        return this.mAtlBrightCfg;
    }

    public void setAtlBrightCfg(int atlBrightCfg) {
        this.mAtlBrightCfg = atlBrightCfg;
    }

    public String getAtlEffectCfg() {
        return this.mAtlEffectCfg;
    }

    public void setAtlEffectCfg(String atlEffectCfg) {
        this.mAtlEffectCfg = atlEffectCfg;
    }

    public boolean isAtlDualColorSw() {
        return this.mAtlDualColorSw;
    }

    public void setAtlDualColorSw(boolean atlDualColorSw) {
        this.mAtlDualColorSw = atlDualColorSw;
    }

    public int getAtlSingleColor() {
        return this.mAtlSingleColor;
    }

    public void setAtlSingleColor(int atlSingleColor) {
        this.mAtlSingleColor = atlSingleColor;
    }

    public String getAtlDualColor() {
        return this.mAtlDualColor;
    }

    public void setAtlDualColor(String atlDualColor) {
        this.mAtlDualColor = atlDualColor;
    }

    public boolean isParkAutoUnlock() {
        return this.mParkAutoUnlock;
    }

    public void setParkAutoUnlock(boolean parkAutoUnlock) {
        this.mParkAutoUnlock = parkAutoUnlock;
    }

    public boolean isDriveAutoLock() {
        return this.mDriveAutoLock;
    }

    public void setDriveAutoLock(boolean driveAutoLock) {
        this.mDriveAutoLock = driveAutoLock;
    }

    public boolean isPollingLock() {
        return this.mPollingLock;
    }

    public void setPollingLock(boolean pollingLock) {
        this.mPollingLock = pollingLock;
    }

    public boolean isPollingUnlock() {
        return this.mPollingUnlock;
    }

    public void setPollingUnlock(boolean pollingUnlock) {
        this.mPollingUnlock = pollingUnlock;
    }

    public int getUnlockResponse() {
        return this.mUnlockResponse;
    }

    public void setUnlockResponse(int unlockResponse) {
        this.mUnlockResponse = unlockResponse;
    }

    public boolean isAutoDhc() {
        return this.mAutoDhc;
    }

    public void setAutoDhc(boolean autoDhc) {
        this.mAutoDhc = autoDhc;
    }

    public int getChildLock() {
        return this.mChildLock;
    }

    public void setChildLock(int state) {
        this.mChildLock = state;
    }

    public boolean getChildLeftLock() {
        return this.mChildLeftLock;
    }

    public void setChildLeftLock(boolean childLeftLock) {
        this.mChildLeftLock = childLeftLock;
    }

    public boolean getChildRightLock() {
        return this.mChildRightLock;
    }

    public void setChildRightLock(boolean childRightLock) {
        this.mChildRightLock = childRightLock;
    }

    public boolean getLeftDoorHotKey() {
        return this.mLeftDoorHotKey;
    }

    public void setLeftDoorHotKey(boolean leftDoorHotKey) {
        this.mLeftDoorHotKey = leftDoorHotKey;
    }

    public boolean getRightDoorHotKey() {
        return this.mRightDoorHotKey;
    }

    public void setRightDoorHotKey(boolean leftDoorHotKey) {
        this.mRightDoorHotKey = leftDoorHotKey;
    }

    public String getDrvSeatPos1() {
        return this.mDrvSeatPos1;
    }

    public void setDrvSeatPos1(String drvSeatPos1) {
        this.mDrvSeatPos1 = drvSeatPos1;
    }

    public String getDrvSeatPos2() {
        return this.mDrvSeatPos2;
    }

    public void setDrvSeatPos2(String drvSeatPos2) {
        this.mDrvSeatPos2 = drvSeatPos2;
    }

    public String getDrvSeatPos3() {
        return this.mDrvSeatPos3;
    }

    public void setDrvSeatPos3(String drvSeatPos3) {
        this.mDrvSeatPos3 = drvSeatPos3;
    }

    public int getDrvSeatPosIdx() {
        return this.mDrvSeatPosIdx;
    }

    public void setDrvSeatPosIdx(int drvSeatPosIdx) {
        this.mDrvSeatPosIdx = drvSeatPosIdx;
    }

    public String getDrvSeatPosNew() {
        return this.mDrvSeatPosNew;
    }

    public void setDrvSeatPosNew(String drvSeatPosNew) {
        this.mDrvSeatPosNew = drvSeatPosNew;
    }

    public String getPsnSeatPos() {
        return this.mPsnSeatPos;
    }

    public void setPsnSeatPos(String psnSeatPos) {
        this.mPsnSeatPos = psnSeatPos;
    }

    public boolean isWelcomeMode() {
        return this.mWelcomeMode;
    }

    public void setWelcomeMode(boolean welcomeMode) {
        this.mWelcomeMode = welcomeMode;
    }

    public boolean isPsnWelcomeMode() {
        return this.mPsnWelcomeMode;
    }

    public void setPsnWelcomeMode(boolean welcomeMode) {
        this.mPsnWelcomeMode = welcomeMode;
    }

    public boolean isRearSeatWelcomeMode() {
        return this.mRearSeatWelcomeMode;
    }

    public void setRearSeatWelcomeMode(boolean welcomeMode) {
        this.mRearSeatWelcomeMode = welcomeMode;
    }

    public boolean isDrvSeatEsb() {
        return this.mDrvSeatEsb;
    }

    public void setDrvSeatEsb(boolean drvSeatEsb) {
        this.mDrvSeatEsb = drvSeatEsb;
    }

    public String getMirrorPos() {
        return this.mMirrorPos;
    }

    public void setMirrorPos(String mirrorPos) {
        this.mMirrorPos = mirrorPos;
    }

    public int getMirrorReverseMode() {
        return this.mMirrorReverseMode;
    }

    public void setMirrorReverseMode(int mirrorReverseMode) {
        this.mMirrorReverseMode = mirrorReverseMode;
    }

    public boolean getMirrorAutoFoldEnable() {
        return this.mMirrorAutoFoldSw;
    }

    public void setMirrorAutoFoldEnable(boolean enable) {
        this.mMirrorAutoFoldSw = enable;
    }

    public boolean getMirrorAutoDown() {
        return this.mMirrorAutoDown;
    }

    public void setMirrorAutoDown(boolean mirrorAutoDown) {
        this.mMirrorAutoDown = mirrorAutoDown;
    }

    public boolean isNeedRestore() {
        return this.mNeedRestore;
    }

    public void setNeedRestore(boolean needRestore) {
        this.mNeedRestore = needRestore;
    }

    public boolean getSensorTrunkSw() {
        return this.mSensorTrunkSw;
    }

    public void setSensorTrunkSw(boolean sensorTrunkSw) {
        this.mSensorTrunkSw = sensorTrunkSw;
    }

    public int getTrunkFullOpenPos() {
        return this.mTrunkFullOpenPos;
    }

    public void setTrunkFullOpenPos(int trunkFullOpenPos) {
        this.mTrunkFullOpenPos = trunkFullOpenPos;
    }

    public boolean isHighSpdCloseWin() {
        return this.mHighSpdCloseWin;
    }

    public void setHighSpdCloseWin(boolean highSpdCloseWin) {
        this.mHighSpdCloseWin = highSpdCloseWin;
    }

    public boolean isLockCloseWin() {
        return this.mLockCloseWin;
    }

    public void setLockCloseWin(boolean lockCloseWin) {
        this.mLockCloseWin = lockCloseWin;
    }

    public int getWiperInterval() {
        return this.mWiperInterval;
    }

    public void setWiperInterval(int wiperInterval) {
        this.mWiperInterval = wiperInterval;
    }

    public int getHvacCircleMode() {
        return this.mHvacCircleMode;
    }

    public void setHvacCircleMode(int hvacCircleMode) {
        this.mHvacCircleMode = hvacCircleMode;
    }

    public int getAqsLevel() {
        return this.mAqsLevel;
    }

    public void setAqsLevel(int aqsLevel) {
        this.mAqsLevel = aqsLevel;
    }

    public int getAirProtectMode() {
        return this.mAirProtectMode;
    }

    public void setAirProtectMode(int airProtectMode) {
        this.mAirProtectMode = airProtectMode;
    }

    public boolean isHvacSelfDry() {
        return this.mHvacSelfDry;
    }

    public void setHvacSelfDry(boolean hvacSelfDry) {
        this.mHvacSelfDry = hvacSelfDry;
    }

    public boolean isHvacSmart() {
        return this.mHvacSmart;
    }

    public void setHvacSmart(boolean hvacSmart) {
        this.mHvacSmart = hvacSmart;
    }

    public boolean isAvasSw() {
        return this.mAvasSw;
    }

    public void setAvasSw(boolean avasSw) {
        this.mAvasSw = avasSw;
    }

    public int getAvasEffect() {
        return this.mAvasEffect;
    }

    public void setAvasEffect(int avasEffect) {
        this.mAvasEffect = avasEffect;
    }

    public int getAvasVolume() {
        return this.mAvasVolume;
    }

    public void setAvasVolume(int avasVolume) {
        this.mAvasVolume = avasVolume;
    }

    public int getSayHiEffect() {
        return this.mSayHiEffect;
    }

    public void setSayHiEffect(int sayHiEffect) {
        this.mSayHiEffect = sayHiEffect;
    }

    public int getOtherVolume() {
        return this.mOtherVolume;
    }

    public void setOtherVolume(int otherVolume) {
        this.mOtherVolume = otherVolume;
    }

    public int getBootEffect() {
        return this.mBootEffect;
    }

    public void setBootEffect(int bootEffect) {
        this.mBootEffect = bootEffect;
    }

    public int getBootEffectBeforeSw() {
        return this.mBootEffectBeforeSw;
    }

    public void setBootEffectBeforeSw(int bootEffectBeforeSw) {
        this.mBootEffectBeforeSw = bootEffectBeforeSw;
    }

    public boolean isMeterDefineTemperature() {
        return this.mMeterDefineTemperature;
    }

    public void setMeterDefineTemperature(boolean meterDefineTemperature) {
        this.mMeterDefineTemperature = meterDefineTemperature;
    }

    public boolean isMeterDefineWindPower() {
        return this.mMeterDefineWindPower;
    }

    public void setMeterDefineWindPower(boolean meterDefineWindPower) {
        this.mMeterDefineWindPower = meterDefineWindPower;
    }

    public boolean isMeterDefineWindMode() {
        return this.mMeterDefineWindMode;
    }

    public void setMeterDefineWindMode(boolean meterDefineWindMode) {
        this.mMeterDefineWindMode = meterDefineWindMode;
    }

    public boolean isMeterDefineMediaSource() {
        return this.mMeterDefineMediaSource;
    }

    public void setMeterDefineMediaSource(boolean meterDefineMediaSource) {
        this.mMeterDefineMediaSource = meterDefineMediaSource;
    }

    public boolean isMeterDefineScreenLight() {
        return this.mMeterDefineScreenLight;
    }

    public void setMeterDefineScreenLight(boolean meterDefineScreenLight) {
        this.mMeterDefineScreenLight = meterDefineScreenLight;
    }

    public boolean isSpeedLimit() {
        return this.mSpeedLimit;
    }

    public void setSpeedLimit(boolean spdLimit) {
        this.mSpeedLimit = spdLimit;
    }

    public int getSpeedLimitValue() {
        return this.mSpeedLimitValue;
    }

    public void setSpeedLimitValue(int spdLimitValue) {
        this.mSpeedLimitValue = spdLimitValue;
    }

    public boolean isFcwSw() {
        return this.mFcwSw;
    }

    public void setFcwSw(boolean fcwSw) {
        this.mFcwSw = fcwSw;
    }

    public boolean isBsdSw() {
        return this.mBsdSw;
    }

    public void setBsdSw(boolean bsdSw) {
        this.mBsdSw = bsdSw;
    }

    public boolean isDowSw() {
        return this.mDowSw;
    }

    public void setDowSw(boolean dowSw) {
        this.mDowSw = dowSw;
    }

    public boolean isLdwSw() {
        return this.mLdwSw;
    }

    public void setLdwSw(boolean ldwSw) {
        this.mLdwSw = ldwSw;
    }

    public boolean isRcwSw() {
        return this.mRcwSw;
    }

    public void setRcwSw(boolean rcwSw) {
        this.mRcwSw = rcwSw;
    }

    public boolean isRctaSw() {
        return this.mRctaSw;
    }

    public void setRctaSw(boolean rctaSw) {
        this.mRctaSw = rctaSw;
    }

    public boolean isAutoParkSw() {
        return this.mAutoParkSw;
    }

    public void setAutoParkSw(boolean autoParkSw) {
        this.mAutoParkSw = autoParkSw;
    }

    public boolean getMemParkSw() {
        return this.mMemParkSw;
    }

    public void setMemParkSw(boolean memParkSw) {
        this.mMemParkSw = memParkSw;
    }

    public int getLssState() {
        return this.mLssState;
    }

    public void setLssState(int state) {
        this.mLssState = state;
    }

    public int getParkSoundCfg() {
        return this.mParkSoundCfg;
    }

    public void setParkSoundCfg(int parkSoundCfg) {
        this.mParkSoundCfg = parkSoundCfg;
    }

    public boolean isRemoteCallSw() {
        return this.mRemoteCallSw;
    }

    public void setRemoteCallSw(boolean remoteCallSw) {
        this.mRemoteCallSw = remoteCallSw;
    }

    public boolean isKeyParkSw() {
        return this.mKeyParkSw;
    }

    public void setKeyParkSw(boolean keyParkSw) {
        this.mKeyParkSw = keyParkSw;
    }

    public int getKeyParkCfg() {
        return this.mKeyParkCfg;
    }

    public void setKeyParkCfg(int keyParkCfg) {
        this.mKeyParkCfg = keyParkCfg;
    }

    public boolean isPhoneParkSw() {
        return this.mPhoneParkSw;
    }

    public void setPhoneParkSw(boolean phoneParkSw) {
        this.mPhoneParkSw = phoneParkSw;
    }

    public int getPhoneParkCfg() {
        return this.mPhoneParkCfg;
    }

    public void setPhoneParkCfg(int phoneParkCfg) {
        this.mPhoneParkCfg = phoneParkCfg;
    }

    public boolean isLccSw() {
        return this.mLccSw;
    }

    public void setLccSw(boolean laaSw) {
        this.mLccSw = laaSw;
    }

    public boolean isIslcSw() {
        return this.mIslcSw;
    }

    public void setIslcSw(boolean islcSw) {
        this.mIslcSw = islcSw;
    }

    public boolean isAlcSw() {
        return this.mAlcSw;
    }

    public void setAlcSw(boolean lcaSw) {
        this.mAlcSw = lcaSw;
    }

    public int getNraState() {
        return this.mNraState;
    }

    public void setNraState(int state) {
        this.mNraState = state;
    }

    public boolean getNgpSafeExamResult() {
        return this.mNgpSafeExamResult;
    }

    public void setNgpSafeExamResult(boolean ngpSafeExamResult) {
        this.mNgpSafeExamResult = ngpSafeExamResult;
    }

    public boolean getNgpSw() {
        return this.mNgpSw;
    }

    public void setNgpSw(boolean ngpSw) {
        this.mNgpSw = ngpSw;
    }

    public boolean isNgpFastLane() {
        return this.mNgpFastLane;
    }

    public void setNgpFastLane(boolean ngpFastLane) {
        this.mNgpFastLane = ngpFastLane;
    }

    public boolean isNgpTruckOffset() {
        return this.mNgpTruckOffset;
    }

    public void setNgpTruckOffset(boolean ngpTruckOffset) {
        this.mNgpTruckOffset = ngpTruckOffset;
    }

    public boolean isNgpTipWin() {
        return this.mNgpTipWin;
    }

    public void setNgpTipWin(boolean ngpTipWin) {
        this.mNgpTipWin = ngpTipWin;
    }

    public boolean isNgpAutoLcs() {
        return this.mNgpAutoLcs;
    }

    public void setNgpAutoLcs(boolean ngpAutoLcs) {
        this.mNgpAutoLcs = ngpAutoLcs;
    }

    public int getNgpLcMode() {
        return this.mNgpLcMode;
    }

    public void setNgpLcMode(int ngpLcMode) {
        this.mNgpLcMode = ngpLcMode;
    }

    public int getNgpRemindMode() {
        return this.mNgpRemindMode;
    }

    public void setNgpRemindMode(int ngpRemindMode) {
        this.mNgpRemindMode = ngpRemindMode;
    }

    public boolean getCityNgpSw() {
        return this.mCityNgpSw;
    }

    public void setCityNgpSw(boolean cityNgpSw) {
        this.mCityNgpSw = cityNgpSw;
    }

    public boolean isSideReversingWarning() {
        return this.mSideReversingWarning;
    }

    public void setSideReversingWarning(boolean sideReversingWarning) {
        this.mSideReversingWarning = sideReversingWarning;
    }

    public boolean isBrakeWarning() {
        return this.mBrakeWarning;
    }

    public void setBrakeWarning(boolean brakeWarning) {
        this.mBrakeWarning = brakeWarning;
    }

    public boolean isRsbWarning() {
        return this.mRsbWarning;
    }

    public void setRsbWarning(boolean rsbWarning) {
        this.mRsbWarning = rsbWarning;
    }

    public int getWiperSensitivity() {
        return this.mWiperSensitivity;
    }

    public void setWiperSensitivity(int level) {
        this.mWiperSensitivity = level;
    }

    public boolean isCwcSwEnabled() {
        return this.mCwcSw;
    }

    public void setCwcSw(boolean enabled) {
        this.mCwcSw = enabled;
    }

    public boolean isMicrophoneMute() {
        return this.mMicrophoneMute;
    }

    public void setMicrophoneMute(boolean mute) {
        this.mMicrophoneMute = mute;
    }

    public void setSteerPos(String pos) {
        this.mSteerPos = pos;
    }

    public String getSteerPos() {
        return this.mSteerPos;
    }

    public int getLampHeightLevel() {
        return this.mLampHeightLevel;
    }

    public void setLampHeightLevel(int level) {
        this.mLampHeightLevel = level;
    }

    public boolean isAutoLampHeight() {
        return this.mAutoLampHeight;
    }

    public void setAutoLampHeight(boolean auto) {
        this.mAutoLampHeight = auto;
    }

    public int getSdcBrakeCloseCfg() {
        return this.mSdcBrakeCloseCfg;
    }

    public void setSdcBrakeCloseCfg(int cfg) {
        this.mSdcBrakeCloseCfg = cfg;
    }

    public boolean getCmsReverseSw() {
        return this.mCmsReverseSw;
    }

    public void setCmsReverseSw(boolean enable) {
        this.mCmsReverseSw = enable;
    }

    public boolean getCmsTurnSw() {
        return this.mCmsTurnSw;
    }

    public void setCmsTurnSw(boolean enable) {
        this.mCmsTurnSw = enable;
    }

    public boolean getCmsHighSpdSw() {
        return this.mCmsHighSpdSw;
    }

    public void setCmsHighSpdSw(boolean enable) {
        this.mCmsHighSpdSw = enable;
    }

    public boolean getCmsLowSpdSw() {
        return this.mCmsLowSpdSw;
    }

    public void setCmsLowSpdSw(boolean enable) {
        this.mCmsLowSpdSw = enable;
    }

    public boolean getCmsAutoBrightSw() {
        return this.mCmsAutoBrightSw;
    }

    public void setCmsAutoBrightSw(boolean enable) {
        this.mCmsAutoBrightSw = enable;
    }

    public int getCmsBright() {
        return this.mCmsBright;
    }

    public void setCmsBright(int brightness) {
        this.mCmsBright = brightness;
    }

    public void setCmsPos(String pos) {
        this.mCmsPos = pos;
    }

    public String getCmsPos() {
        return this.mCmsPos;
    }

    public void setCmsViewAngle(int viewAngle) {
        this.mCmsViewAngle = viewAngle;
    }

    public int getCmsViewAngle() {
        return this.mCmsViewAngle;
    }

    public boolean getCmsObjectRecognizeSw() {
        return this.mCmsObjectRecognizeSw;
    }

    public void setCmsObjectRecognizeSw(boolean objectRecognize) {
        this.mCmsObjectRecognizeSw = objectRecognize;
    }

    public boolean getCmsViewRecoverySw() {
        return this.mCmsViewRecoverySw;
    }

    public void setCmsViewRecoverySw(boolean viewRecovery) {
        this.mCmsViewRecoverySw = viewRecovery;
    }
}
