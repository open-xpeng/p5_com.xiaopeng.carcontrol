package com.xiaopeng.carcontrol.model;

/* loaded from: classes2.dex */
public class CarControlSyncDataEvent {
    private boolean alc;
    private boolean antisick;
    private boolean asWelcomeMode;
    private boolean atlAutoBright;
    private boolean atlBright;
    private boolean atlDualColor;
    private boolean atlDualColorSw;
    private boolean atlEffect;
    private boolean atlSingleColor;
    private boolean atlSw;
    private boolean autoDhc;
    private boolean autoDriveMode;
    private boolean autoLampHeight;
    private boolean autoLight;
    private boolean autoParkSound;
    private boolean autoParkSw;
    private boolean avasEffect;
    private boolean avasSw;
    private boolean avasVolume;
    private boolean avh;
    private boolean bootEffect;
    private boolean bootEffectBfSw;
    private boolean brakeWarning;
    private boolean bsd;
    private boolean cdcMode;
    private boolean childLeftLock;
    private boolean childLock;
    private boolean childRightLock;
    private boolean cityNgpSw;
    private boolean domeLight;
    private boolean domeLightBrightness;
    private boolean doorBossKey;
    private boolean doorBossKeySw;
    private boolean dow;
    private boolean driveAutoLock;
    private boolean driveMode;
    private boolean drvSeatEsb;
    private boolean drvSeatPos;
    private boolean empty;
    private boolean espMode;
    private boolean fcw;
    private boolean highSpdCloseWin;
    private boolean hvacAirProtect;
    private boolean hvacAqs;
    private boolean hvacCircle;
    private boolean hvacSelfDry;
    private boolean hvacSmart;
    private boolean islc;
    private boolean keyParkCfg;
    private boolean keyParkSw;
    private boolean lampHeightLevel;
    private boolean lcc;
    private boolean ldw;
    private boolean leftDoorHotKey;
    private boolean lightMeHome;
    private boolean lightMeHomeTime;
    private boolean lluChargeSw;
    private boolean lluLockSw;
    private boolean lluSw;
    private boolean lluUnlockSw;
    private boolean lockCloseWin;
    private boolean lss;
    private boolean mCmsAutoBrightSw;
    private boolean mCmsBright;
    private boolean mCmsHighSpdSw;
    private boolean mCmsLowSpdSw;
    private boolean mCmsObjectRecognizeSw;
    private boolean mCmsPos;
    private boolean mCmsReverseSw;
    private boolean mCmsTurnSw;
    private boolean mCmsViewAngle;
    private boolean mCmsViewRecoverySw;
    private boolean mCwcSw;
    private boolean mSdcBrakeCloseType;
    private boolean mWiperSensitivity;
    private boolean memParkSw;
    private boolean meterDefineMediaSource;
    private boolean meterDefineScreenLight;
    private boolean meterDefineTemperature;
    private boolean meterDefineWindMode;
    private boolean meterDefineWindPower;
    private boolean meterWarnSound;
    private boolean microphoneMute;
    private boolean mirrorAutoDown;
    private boolean mirrorAutoFold;
    private boolean mirrorPos;
    private boolean mirrorReverse;
    private boolean nGearProtectSw;
    private boolean ngpAutoLcs;
    private boolean ngpFastLane;
    private boolean ngpLcMode;
    private boolean ngpRemindMode;
    private boolean ngpSw;
    private boolean ngpTipWin;
    private boolean ngpTruckOffset;
    private boolean nra;
    private boolean otherVolume;
    private boolean parkAutoUnlock;
    private boolean parkLampB;
    private boolean phoneParkCfg;
    private boolean phoneParkSw;
    private boolean pollingLock;
    private boolean pollingUnlock;
    private boolean rcta;
    private boolean rcw;
    private boolean rearSeatWelcomeMode;
    private boolean recycleGrade;
    private boolean remoteCallSw;
    private boolean rightDoorHotKey;
    private boolean rsbWarning;
    private boolean sayHiEffect;
    private boolean sensorTrunkSw;
    private boolean sideReversingWarning;
    private boolean singlePedal;
    private boolean speedLimit;
    private boolean speedLimitValue;
    private boolean steerMode;
    private boolean steerPos;
    private boolean trunkFullOpenPos;
    private boolean unlockResponse;
    private boolean welcomeMode;
    private boolean wheelKeyProtect;
    private boolean wheelTouchDirection;
    private boolean wheelTouchHint;
    private boolean wheelTouchSpeed;
    private boolean wheelXKey;
    private boolean wiperInterval;
    private boolean xpedal;

    public boolean isDriveMode() {
        return this.driveMode;
    }

    public void setDriveMode(boolean driveMode) {
        this.driveMode = driveMode;
    }

    public boolean isAutoDriveMode() {
        return this.autoDriveMode;
    }

    public void setAutoDriveMode(boolean autoDriveMode) {
        this.autoDriveMode = autoDriveMode;
    }

    public boolean isRecycleGrade() {
        return this.recycleGrade;
    }

    public void setRecycleGrade(boolean recycleGrade) {
        this.recycleGrade = recycleGrade;
    }

    public boolean isSteerMode() {
        return this.steerMode;
    }

    public void setSteerMode(boolean steerMode) {
        this.steerMode = steerMode;
    }

    public boolean isCdcMode() {
        return this.cdcMode;
    }

    public void setCdcMode(boolean cdcMode) {
        this.cdcMode = cdcMode;
    }

    public boolean isAvh() {
        return this.avh;
    }

    public void setAvh(boolean avh) {
        this.avh = avh;
    }

    public boolean isAsWelcomeMode() {
        return this.asWelcomeMode;
    }

    public void setAsWelcomeMode(boolean asWelcomeMode) {
        this.asWelcomeMode = asWelcomeMode;
    }

    public boolean isSinglePedal() {
        return this.singlePedal;
    }

    public void setSinglePedal(boolean singlePedal) {
        this.singlePedal = singlePedal;
    }

    public boolean isEspMode() {
        return this.espMode;
    }

    public void setEspMode(boolean espMode) {
        this.espMode = espMode;
    }

    public boolean isXpedal() {
        return this.xpedal;
    }

    public void setXpedal(boolean xpedal) {
        this.xpedal = xpedal;
    }

    public boolean isAntisick() {
        return this.antisick;
    }

    public void setAntisick(boolean antisick) {
        this.antisick = antisick;
    }

    public boolean isWheelTouchHint() {
        return this.wheelTouchHint;
    }

    public void setWheelTouchHint(boolean wheelTouchHint) {
        this.wheelTouchHint = wheelTouchHint;
    }

    public boolean isWheelTouchDirection() {
        return this.wheelTouchDirection;
    }

    public void setWheelTouchDirection(boolean wheelTouchDirection) {
        this.wheelTouchDirection = wheelTouchDirection;
    }

    public boolean isWheelTouchSpeed() {
        return this.wheelTouchSpeed;
    }

    public void setWheelTouchSpeed(boolean wheelTouchSpeed) {
        this.wheelTouchSpeed = wheelTouchSpeed;
    }

    public boolean isWheelXKey() {
        return this.wheelXKey;
    }

    public void setWheelXKey(boolean wheelXKey) {
        this.wheelXKey = wheelXKey;
    }

    public boolean isDoorBossKey() {
        return this.doorBossKey;
    }

    public void setDoorBossKey(boolean doorBossKey) {
        this.doorBossKey = doorBossKey;
    }

    public void setDoorBossKeySw(boolean doorBossKeySw) {
        this.doorBossKeySw = doorBossKeySw;
    }

    public boolean isWheelKeyProtect() {
        return this.wheelKeyProtect;
    }

    public void setWheelKeyProtect(boolean wheelKeyProtect) {
        this.wheelKeyProtect = wheelKeyProtect;
    }

    public boolean isNGearProtectSw() {
        return this.nGearProtectSw;
    }

    public void setNGearProtectSw(boolean nGearProtectSw) {
        this.nGearProtectSw = nGearProtectSw;
    }

    public boolean isLightMeHome() {
        return this.lightMeHome;
    }

    public void setLightMeHome(boolean lightMeHome) {
        this.lightMeHome = lightMeHome;
    }

    public boolean isLightMeHomeTime() {
        return this.lightMeHomeTime;
    }

    public void setLightMeHomeTime(boolean lightMeHomeTime) {
        this.lightMeHomeTime = lightMeHomeTime;
    }

    public boolean isAutoLight() {
        return this.autoLight;
    }

    public void setAutoLight(boolean autoLight) {
        this.autoLight = autoLight;
    }

    public boolean isDomeLight() {
        return this.domeLight;
    }

    public void setDomeLight(boolean domeLight) {
        this.domeLight = domeLight;
    }

    public boolean isDomeLightBright() {
        return this.domeLightBrightness;
    }

    public void setDomeLightBright(boolean domeLightBrightness) {
        this.domeLightBrightness = domeLightBrightness;
    }

    public boolean isParkLampB() {
        return this.parkLampB;
    }

    public void setParkLampB(boolean parkLampB) {
        this.parkLampB = parkLampB;
    }

    public boolean isLluSw() {
        return this.lluSw;
    }

    public void setLluSw(boolean lluSw) {
        this.lluSw = lluSw;
    }

    public boolean isLluUnlockSw() {
        return this.lluUnlockSw;
    }

    public void setLluUnlockSw(boolean lluUnlockSw) {
        this.lluUnlockSw = lluUnlockSw;
    }

    public boolean isLluLockSw() {
        return this.lluLockSw;
    }

    public void setLluLockSw(boolean lluLockSw) {
        this.lluLockSw = lluLockSw;
    }

    public boolean isLluChargeSw() {
        return this.lluChargeSw;
    }

    public void setLluChargeSw(boolean lluChargeSw) {
        this.lluChargeSw = lluChargeSw;
    }

    public boolean isAtlSw() {
        return this.atlSw;
    }

    public void setAtlSw(boolean atlSw) {
        this.atlSw = atlSw;
    }

    public boolean isAtlAutoBright() {
        return this.atlAutoBright;
    }

    public void setAtlAutoBright(boolean atlAutoBright) {
        this.atlAutoBright = atlAutoBright;
    }

    public boolean isAtlBright() {
        return this.atlBright;
    }

    public void setAtlBright(boolean atlBright) {
        this.atlBright = atlBright;
    }

    public boolean isAtlEffect() {
        return this.atlEffect;
    }

    public void setAtlEffect(boolean atlEffect) {
        this.atlEffect = atlEffect;
    }

    public boolean isAtlDualColorSw() {
        return this.atlDualColorSw;
    }

    public void setAtlDualColorSw(boolean atlDualColorSw) {
        this.atlDualColorSw = atlDualColorSw;
    }

    public boolean isAtlSingleColor() {
        return this.atlSingleColor;
    }

    public void setAtlSingleColor(boolean atlSingleColor) {
        this.atlSingleColor = atlSingleColor;
    }

    public boolean isAtlDualColor() {
        return this.atlDualColor;
    }

    public void setAtlDualColor(boolean atlDualColor) {
        this.atlDualColor = atlDualColor;
    }

    public boolean isDriveAutoLock() {
        return this.driveAutoLock;
    }

    public void setDriveAutoLock(boolean driveAutoLock) {
        this.driveAutoLock = driveAutoLock;
    }

    public boolean isParkAutoUnlock() {
        return this.parkAutoUnlock;
    }

    public void setParkAutoUnlock(boolean parkAutoUnlock) {
        this.parkAutoUnlock = parkAutoUnlock;
    }

    public boolean isPollingLock() {
        return this.pollingLock;
    }

    public void setPollingLock(boolean pollingLock) {
        this.pollingLock = pollingLock;
    }

    public boolean isPollingUnlock() {
        return this.pollingUnlock;
    }

    public void setPollingUnlock(boolean pollingUnlock) {
        this.pollingUnlock = pollingUnlock;
    }

    public boolean isUnlockResponse() {
        return this.unlockResponse;
    }

    public void setUnlockResponse(boolean unlockResponse) {
        this.unlockResponse = unlockResponse;
    }

    public boolean isAutoDhc() {
        return this.autoDhc;
    }

    public void setAutoDhc(boolean autoDhc) {
        this.autoDhc = autoDhc;
    }

    public boolean isChildLock() {
        return this.childLock;
    }

    public void setChildLock(boolean childLock) {
        this.childLock = childLock;
    }

    public boolean isChildLeftLock() {
        return this.childLeftLock;
    }

    public void setChildLeftLock(boolean childLeftLock) {
        this.childLeftLock = childLeftLock;
    }

    public boolean isChildRightLock() {
        return this.childRightLock;
    }

    public void setChildRightLock(boolean childRightLock) {
        this.childRightLock = childRightLock;
    }

    public boolean isLeftDoorHotKey() {
        return this.leftDoorHotKey;
    }

    public void setLeftDoorHotKey(boolean leftDoorHotKey) {
        this.leftDoorHotKey = leftDoorHotKey;
    }

    public boolean isRightDoorHotKey() {
        return this.rightDoorHotKey;
    }

    public void setRightDoorHotKey(boolean rightDoorHotKey) {
        this.rightDoorHotKey = rightDoorHotKey;
    }

    public boolean isWelcomeMode() {
        return this.welcomeMode;
    }

    public void setWelcomeMode(boolean welcomeMode) {
        this.welcomeMode = welcomeMode;
    }

    public boolean isRearSeatWelcomeMode() {
        return this.rearSeatWelcomeMode;
    }

    public void setRearSeatWelcomeMode(boolean rearSeatWelcomeMode) {
        this.rearSeatWelcomeMode = rearSeatWelcomeMode;
    }

    public boolean isDrvSeatEsb() {
        return this.drvSeatEsb;
    }

    public void setDrvSeatEsb(boolean drvSeatEsb) {
        this.drvSeatEsb = drvSeatEsb;
    }

    public boolean isRsbWarning() {
        return this.rsbWarning;
    }

    public void setRsbWarning(boolean rsbWarning) {
        this.rsbWarning = rsbWarning;
    }

    public boolean isMirrorPos() {
        return this.mirrorPos;
    }

    public void setMirrorPos(boolean mirrorPos) {
        this.mirrorPos = mirrorPos;
    }

    public boolean isMirrorReverse() {
        return this.mirrorReverse;
    }

    public void setMirrorReverse(boolean mirrorReverse) {
        this.mirrorReverse = mirrorReverse;
    }

    public boolean isHighSpdCloseWin() {
        return this.highSpdCloseWin;
    }

    public void setHighSpdCloseWin(boolean highSpdCloseWin) {
        this.highSpdCloseWin = highSpdCloseWin;
    }

    public boolean isLockCloseWin() {
        return this.lockCloseWin;
    }

    public void setLockCloseWin(boolean lockCloseWin) {
        this.lockCloseWin = lockCloseWin;
    }

    public boolean isWiperInterval() {
        return this.wiperInterval;
    }

    public void setWiperInterval(boolean wiperInterval) {
        this.wiperInterval = wiperInterval;
    }

    public boolean isMicrophoneMute() {
        return this.microphoneMute;
    }

    public void setMicrophoneMute(boolean microphoneMute) {
        this.microphoneMute = microphoneMute;
    }

    public void setWiperSensitivity(boolean wiperSensitivity) {
        this.mWiperSensitivity = wiperSensitivity;
    }

    public boolean isWiperSensitivity() {
        return this.mWiperSensitivity;
    }

    public void setCwcSw(boolean cwcSw) {
        this.mCwcSw = cwcSw;
    }

    public boolean isCwcSw() {
        return this.mCwcSw;
    }

    public boolean isHvacCircle() {
        return this.hvacCircle;
    }

    public void setHvacCircle(boolean hvacCircle) {
        this.hvacCircle = hvacCircle;
    }

    public boolean isHvacAqs() {
        return this.hvacAqs;
    }

    public void setHvacAqs(boolean hvacAqs) {
        this.hvacAqs = hvacAqs;
    }

    public boolean isHvacAirProtect() {
        return this.hvacAirProtect;
    }

    public void setHvacAirProtect(boolean hvacAirProtect) {
        this.hvacAirProtect = hvacAirProtect;
    }

    public boolean isHvacSelfDry() {
        return this.hvacSelfDry;
    }

    public void setHvacSelfDry(boolean hvacSelfDry) {
        this.hvacSelfDry = hvacSelfDry;
    }

    public boolean isHvacSmart() {
        return this.hvacSmart;
    }

    public void setHvacSmart(boolean hvacSmart) {
        this.hvacSmart = hvacSmart;
    }

    public boolean isAvasSw() {
        return this.avasSw;
    }

    public void setAvasSw(boolean avasSw) {
        this.avasSw = avasSw;
    }

    public boolean isAvasEffect() {
        return this.avasEffect;
    }

    public void setAvasEffect(boolean avasEffect) {
        this.avasEffect = avasEffect;
    }

    public boolean isAvasVolume() {
        return this.avasVolume;
    }

    public void setAvasVolume(boolean avasVolume) {
        this.avasVolume = avasVolume;
    }

    public boolean isSayHiEffect() {
        return this.sayHiEffect;
    }

    public void setSayHiEffect(boolean sayHiEffect) {
        this.sayHiEffect = sayHiEffect;
    }

    public boolean isOtherVolume() {
        return this.otherVolume;
    }

    public void setOtherVolume(boolean otherVolume) {
        this.otherVolume = otherVolume;
    }

    public boolean isBootEffect() {
        return this.bootEffect;
    }

    public void setBootEffect(boolean bootEffect) {
        this.bootEffect = bootEffect;
    }

    public boolean isBootEffectBeforeSw() {
        return this.bootEffectBfSw;
    }

    public void setBootEffectBeforeSw(boolean bootEffectBfSw) {
        this.bootEffectBfSw = bootEffectBfSw;
    }

    public boolean isFcw() {
        return this.fcw;
    }

    public void setFcw(boolean fcw) {
        this.fcw = fcw;
    }

    public boolean isBsd() {
        return this.bsd;
    }

    public void setBsd(boolean bsd) {
        this.bsd = bsd;
    }

    public boolean isDow() {
        return this.dow;
    }

    public void setDow(boolean dow) {
        this.dow = dow;
    }

    public boolean isLdw() {
        return this.ldw;
    }

    public void setLdw(boolean ldw) {
        this.ldw = ldw;
    }

    public boolean isRcw() {
        return this.rcw;
    }

    public void setRcw(boolean rcw) {
        this.rcw = rcw;
    }

    public boolean isRcta() {
        return this.rcta;
    }

    public void setRcta(boolean rcta) {
        this.rcta = rcta;
    }

    public boolean isAutoParkSw() {
        return this.autoParkSw;
    }

    public void setAutoParkSw(boolean autoParkSw) {
        this.autoParkSw = autoParkSw;
    }

    public boolean isMemParkSw() {
        return this.memParkSw;
    }

    public void setMemParkSw(boolean memParkSw) {
        this.memParkSw = memParkSw;
    }

    public boolean isAutoParkSound() {
        return this.autoParkSound;
    }

    public void setAutoParkSound(boolean autoParkSound) {
        this.autoParkSound = autoParkSound;
    }

    public boolean isRemoteCallSw() {
        return this.remoteCallSw;
    }

    public void setRemoteCallSw(boolean remoteCallSw) {
        this.remoteCallSw = remoteCallSw;
    }

    public boolean isKeyParkSw() {
        return this.keyParkSw;
    }

    public void setKeyParkSw(boolean keyParkSw) {
        this.keyParkSw = keyParkSw;
    }

    public boolean isKeyParkCfg() {
        return this.keyParkCfg;
    }

    public void setKeyParkCfg(boolean keyParkCfg) {
        this.keyParkCfg = keyParkCfg;
    }

    public boolean isPhoneParkSw() {
        return this.phoneParkSw;
    }

    public void setPhoneParkSw(boolean phoneParkSw) {
        this.phoneParkSw = phoneParkSw;
    }

    public boolean isPhoneParkCfg() {
        return this.phoneParkCfg;
    }

    public void setPhoneParkCfg(boolean phoneParkCfg) {
        this.phoneParkCfg = phoneParkCfg;
    }

    public boolean isLcc() {
        return this.lcc;
    }

    public void setLcc(boolean lcc) {
        this.lcc = lcc;
    }

    public boolean isIslc() {
        return this.islc;
    }

    public void setIslc(boolean islc) {
        this.islc = islc;
    }

    public boolean isAlc() {
        return this.alc;
    }

    public void setAlc(boolean alc) {
        this.alc = alc;
    }

    public boolean isNra() {
        return this.nra;
    }

    public void setNra(boolean nra) {
        this.nra = nra;
    }

    public boolean isNgpSw() {
        return this.ngpSw;
    }

    public void setNgpSw(boolean ngpSw) {
        this.ngpSw = ngpSw;
    }

    public boolean isNgpFastLane() {
        return this.ngpFastLane;
    }

    public void setNgpFastLane(boolean ngpFastLane) {
        this.ngpFastLane = ngpFastLane;
    }

    public boolean isNgpTruckOffset() {
        return this.ngpTruckOffset;
    }

    public void setNgpTruckOffset(boolean ngpTruckOffset) {
        this.ngpTruckOffset = ngpTruckOffset;
    }

    public boolean isNgpTipWin() {
        return this.ngpTipWin;
    }

    public void setNgpTipWin(boolean ngpTipWin) {
        this.ngpTipWin = ngpTipWin;
    }

    public boolean isNgpAutoLcs() {
        return this.ngpAutoLcs;
    }

    public void setNgpAutoLcs(boolean ngpAutoLcs) {
        this.ngpAutoLcs = ngpAutoLcs;
    }

    public boolean isNgpLcMode() {
        return this.ngpLcMode;
    }

    public void setNgpLcMode(boolean ngpLcMode) {
        this.ngpLcMode = ngpLcMode;
    }

    public boolean isNgpRemindMode() {
        return this.ngpRemindMode;
    }

    public void setNgpRemindMode(boolean ngpRemindMode) {
        this.ngpRemindMode = ngpRemindMode;
    }

    public boolean isCityNgpSw() {
        return this.cityNgpSw;
    }

    public void setCityNgpSw(boolean cityNgpSw) {
        this.cityNgpSw = cityNgpSw;
    }

    public boolean isDataEmpty() {
        return this.empty;
    }

    public void setDataEmpty(boolean empty) {
        this.empty = empty;
    }

    public boolean isDrvSeatPos() {
        return this.drvSeatPos;
    }

    public void setDrvSeatPos(boolean seatPos) {
        this.drvSeatPos = seatPos;
    }

    public boolean isMeterWarnSound() {
        return this.meterWarnSound;
    }

    public void setMeterWarnSound(boolean meterWarnSound) {
        this.meterWarnSound = meterWarnSound;
    }

    public boolean isMeterDefineTemperature() {
        return this.meterDefineTemperature;
    }

    public void setMeterDefineTemperature(boolean meterDefineTemperature) {
        this.meterDefineTemperature = meterDefineTemperature;
    }

    public boolean isMeterDefineWindPower() {
        return this.meterDefineWindPower;
    }

    public void setMeterDefineWindPower(boolean meterDefineWindPower) {
        this.meterDefineWindPower = meterDefineWindPower;
    }

    public boolean isMeterDefineWindMode() {
        return this.meterDefineWindMode;
    }

    public void setMeterDefineWindMode(boolean meterDefineWindMode) {
        this.meterDefineWindMode = meterDefineWindMode;
    }

    public boolean isMeterDefineMediaSource() {
        return this.meterDefineMediaSource;
    }

    public void setMeterDefineMediaSource(boolean meterDefineMediaSource) {
        this.meterDefineMediaSource = meterDefineMediaSource;
    }

    public boolean isMeterDefineScreenLight() {
        return this.meterDefineScreenLight;
    }

    public void setMeterDefineScreenLight(boolean meterDefineScreenLight) {
        this.meterDefineScreenLight = meterDefineScreenLight;
    }

    public boolean isSpeedLimit() {
        return this.speedLimit;
    }

    public void setSpeedLimit(boolean speedLimit) {
        this.speedLimit = speedLimit;
    }

    public boolean isSpeedLimitValue() {
        return this.speedLimitValue;
    }

    public void setSpeedLimitValue(boolean speedLimitValue) {
        this.speedLimitValue = speedLimitValue;
    }

    public boolean isMirrorAutoDown() {
        return this.mirrorAutoDown;
    }

    public void setMirrorAutoDown(boolean enable) {
        this.mirrorAutoDown = enable;
    }

    public boolean isMirrorAutoFold() {
        return this.mirrorAutoFold;
    }

    public void setMirrorAutoFold(boolean enable) {
        this.mirrorAutoFold = enable;
    }

    public boolean isSensorTrunkSw() {
        return this.sensorTrunkSw;
    }

    public void setSensorTrunkSw(boolean sensorTrunkSw) {
        this.sensorTrunkSw = sensorTrunkSw;
    }

    public boolean isTrunkFullOpenPos() {
        return this.trunkFullOpenPos;
    }

    public void setTrunkFullOpenPos(boolean trunkFullOpenPos) {
        this.trunkFullOpenPos = trunkFullOpenPos;
    }

    public boolean isSideReversingWarning() {
        return this.sideReversingWarning;
    }

    public void setSideReversingWarning(boolean sideReversingWarning) {
        this.sideReversingWarning = sideReversingWarning;
    }

    public boolean isBrakeWarning() {
        return this.brakeWarning;
    }

    public void setBrakeWarning(boolean brakeWarning) {
        this.brakeWarning = brakeWarning;
    }

    public boolean isLss() {
        return this.lss;
    }

    public void setLss(boolean lss) {
        this.lss = lss;
    }

    public boolean isLampHeightLevel() {
        return this.lampHeightLevel;
    }

    public void setLampHeightLevel(boolean lampHeightLevel) {
        this.lampHeightLevel = lampHeightLevel;
    }

    public boolean isAutoLampHeight() {
        return this.autoLampHeight;
    }

    public void setAutoLampHeight(boolean autoLampHeight) {
        this.autoLampHeight = autoLampHeight;
    }

    public boolean isSteerPos() {
        return this.steerPos;
    }

    public void setSteerPos(boolean steerPos) {
        this.steerPos = steerPos;
    }

    public boolean isSdcBrakeCloseType() {
        return this.mSdcBrakeCloseType;
    }

    public void setSdcBrakeCloseType(boolean sdcBrakeCloseType) {
        this.mSdcBrakeCloseType = sdcBrakeCloseType;
    }

    public boolean isCmsReverseSw() {
        return this.mCmsReverseSw;
    }

    public void setCmsReverseSw(boolean mCmsReverseSw) {
        this.mCmsReverseSw = mCmsReverseSw;
    }

    public boolean isCmsTurnSw() {
        return this.mCmsTurnSw;
    }

    public void setCmsTurnSw(boolean mCmsTurnSw) {
        this.mCmsTurnSw = mCmsTurnSw;
    }

    public boolean isCmsHighSpdSw() {
        return this.mCmsHighSpdSw;
    }

    public void setCmsHighSpdSw(boolean mCmsHighSpdSw) {
        this.mCmsHighSpdSw = mCmsHighSpdSw;
    }

    public boolean isCmsLowSpdSw() {
        return this.mCmsLowSpdSw;
    }

    public void setCmsLowSpdSw(boolean mCmsLowSpdSw) {
        this.mCmsLowSpdSw = mCmsLowSpdSw;
    }

    public boolean isCmsAutoBrightSw() {
        return this.mCmsAutoBrightSw;
    }

    public void setCmsAutoBrightSw(boolean mCmsAutoBrightSw) {
        this.mCmsAutoBrightSw = mCmsAutoBrightSw;
    }

    public boolean isCmsBright() {
        return this.mCmsBright;
    }

    public void setCmsBright(boolean mCmsBright) {
        this.mCmsBright = mCmsBright;
    }

    public boolean isCmsPos() {
        return this.mCmsPos;
    }

    public void setCmsPos(boolean mCmsPos) {
        this.mCmsPos = mCmsPos;
    }

    public boolean isCmsViewAngle() {
        return this.mCmsViewAngle;
    }

    public void setCmsViewAngle(boolean mCmsViewAngle) {
        this.mCmsViewAngle = mCmsViewAngle;
    }

    public boolean isCmsObjectRecognizeSw() {
        return this.mCmsObjectRecognizeSw;
    }

    public void setCmsObjectRecognizeSw(boolean objectRecognize) {
        this.mCmsObjectRecognizeSw = objectRecognize;
    }

    public boolean isCmsViewRecoverySw() {
        return this.mCmsViewRecoverySw;
    }

    public void setCmsViewRecoverySw(boolean viewRecovery) {
        this.mCmsViewRecoverySw = viewRecovery;
    }
}
