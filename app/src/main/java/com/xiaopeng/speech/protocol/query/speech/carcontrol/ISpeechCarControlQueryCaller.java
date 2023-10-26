package com.xiaopeng.speech.protocol.query.speech.carcontrol;

import com.xiaopeng.speech.IQueryCaller;

/* loaded from: classes2.dex */
public interface ISpeechCarControlQueryCaller extends IQueryCaller {
    int getACCStatus();

    int getATWSState();

    int[] getAllTirePressureWarnings();

    int getBCMIgStatus();

    int getBlindAreaDetectionWarning();

    float getCarSpeed();

    int[] getChairDirection();

    int[] getChairLocationValue();

    boolean getChairWelcomeMode();

    int getCruiseActive();

    boolean getDoorLockState();

    int[] getDoorsState();

    boolean getDriveAutoLock();

    float getDriveTotalMileage();

    int getDriverSeatStatus();

    int getDrivingMode();

    boolean getElectricSeatBelt();

    boolean getEmergencyBrakeWarning();

    int getEnergyRecycleLevel();

    boolean getFarLampState();

    int getFrontCollisionSecurity();

    int getHeadLampGroup();

    float getICMDriverTempValue();

    int getICMWindBlowMode();

    int getICMWindLevel();

    int getIcmAlarmVolume();

    boolean getIcmConnectionState();

    int getIntelligentSpeedLimit();

    boolean getInternalLight();

    int getLCCStatus();

    int getLaneChangeAssist();

    int getLaneDepartureWarning();

    float getLastChargeMileage();

    float getLastStartUpMileage();

    int getLightMeHome();

    boolean getLocationLampState();

    int getLowSocStatus();

    float getMeterMileageA();

    float getMeterMileageB();

    boolean getNearLampState();

    int getOled();

    boolean getParkingAutoUnlock();

    boolean getRadarWarningVoiceStatus();

    boolean getRearFogLamp();

    boolean getRearSeatBeltWarning();

    int getRiseSpeakerStatus();

    boolean getSeatErrorState();

    int getShiftStatus();

    int getSideReversingWarning();

    boolean getSpeedLimitWarningSwitch();

    int getSpeedLimitWarningValue();

    float getSteerWheelRotationAngle();

    int getSteeringWheelEPS();

    float[] getTirePressureAll();

    int getTrunk();

    int getUnlockResponse();

    boolean getWelcomeModeBackStatus();

    int getWindowLockStatus();

    float[] getWindowsState();

    int getWiperInterval();

    boolean isCarTrip();
}
