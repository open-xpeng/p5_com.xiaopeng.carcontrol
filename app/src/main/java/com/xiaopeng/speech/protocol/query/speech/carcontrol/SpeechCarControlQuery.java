package com.xiaopeng.speech.protocol.query.speech.carcontrol;

import com.xiaopeng.speech.SpeechQuery;

/* loaded from: classes2.dex */
public class SpeechCarControlQuery extends SpeechQuery<ISpeechCarControlQueryCaller> {
    /* JADX INFO: Access modifiers changed from: protected */
    public boolean getRearFogLamp(String str, String str2) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getRearFogLamp();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean getNearLampState(String str, String str2) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getNearLampState();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean getLocationLampState(String str, String str2) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getLocationLampState();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean getFarLampState(String str, String str2) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getFarLampState();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getHeadLampGroup(String str, String str2) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getHeadLampGroup();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean getInternalLight(String str, String str2) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getInternalLight();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean getEmergencyBrakeWarning(String str, String str2) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getEmergencyBrakeWarning();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getATWSState(String str, String str2) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getATWSState();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getOled(String str, String str2) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getOled();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getLightMeHome(String str, String str2) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getLightMeHome();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean getDriveAutoLock(String str, String str2) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getDriveAutoLock();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean getParkingAutoUnlock(String str, String str2) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getParkingAutoUnlock();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean getDoorLockState(String str, String str2) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getDoorLockState();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getTrunk(String str, String str2) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getTrunk();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean getChairWelcomeMode(String str, String str2) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getChairWelcomeMode();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean getElectricSeatBelt(String str, String str2) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getElectricSeatBelt();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean getRearSeatBeltWarning(String str, String str2) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getRearSeatBeltWarning();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getUnlockResponse(String str, String str2) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getUnlockResponse();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int[] getDoorsState(String str, String str2) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getDoorsState();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public float[] getWindowsState(String str, String str2) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getWindowsState();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int[] getChairDirection(String str, String str2) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getChairDirection();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean getSeatErrorState(String str, String str2) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getSeatErrorState();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int[] getChairLocationValue(String str, String str2) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getChairLocationValue();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean getWelcomeModeBackStatus(String str, String str2) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getWelcomeModeBackStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getDrivingMode(String str, String str2) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getDrivingMode();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getSteeringWheelEPS(String str, String str2) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getSteeringWheelEPS();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getIcmAlarmVolume(String str, String str2) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getIcmAlarmVolume();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean getSpeedLimitWarningSwitch(String str, String str2) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getSpeedLimitWarningSwitch();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getSpeedLimitWarningValue(String str, String str2) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getSpeedLimitWarningValue();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public double getMeterMileageA(String str, String str2) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getMeterMileageA();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public double getMeterMileageB(String str, String str2) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getMeterMileageB();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public double getDriveTotalMileage(String str, String str2) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getDriveTotalMileage();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public double getLastChargeMileage(String str, String str2) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getLastChargeMileage();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public double getLastStartUpMileage(String str, String str2) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getLastStartUpMileage();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getFrontCollisionSecurity(String str, String str2) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getFrontCollisionSecurity();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getIntelligentSpeedLimit(String str, String str2) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getIntelligentSpeedLimit();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getLaneChangeAssist(String str, String str2) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getLaneChangeAssist();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getSideReversingWarning(String str, String str2) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getSideReversingWarning();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getLaneDepartureWarning(String str, String str2) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getLaneDepartureWarning();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getBlindAreaDetectionWarning(String str, String str2) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getBlindAreaDetectionWarning();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean getRadarWarningVoiceStatus(String str, String str2) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getRadarWarningVoiceStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getEnergyRecycleLevel(String str, String str2) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getEnergyRecycleLevel();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getShiftStatus(String str, String str2) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getShiftStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getDriverSeatStatus(String str, String str2) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getDriverSeatStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public double getSteerWheelRotationAngle(String str, String str2) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getSteerWheelRotationAngle();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public double getCarSpeed(String str, String str2) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getCarSpeed();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean getIcmConnectionState(String str, String str2) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getIcmConnectionState();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getBCMIgStatus(String str, String str2) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getBCMIgStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getICMWindBlowMode(String str, String str2) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getICMWindBlowMode();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public double getICMDriverTempValue(String str, String str2) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getICMDriverTempValue();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getICMWindLevel(String str, String str2) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getICMWindLevel();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isCarTrip(String str, String str2) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).isCarTrip();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getWiperInterval(String str, String str2) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getWiperInterval();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public float[] getTirePressureAll(String str, String str2) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getTirePressureAll();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int[] getAllTirePressureWarnings(String str, String str2) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getAllTirePressureWarnings();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getACCStatus(String str, String str2) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getACCStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getLCCStatus(String str, String str2) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getLCCStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getWindowLockStatus(String str, String str2) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getWindowLockStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getLowSocStatus(String str, String str2) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getLowSocStatus();
    }

    protected int getRiseSpeakerStatus(String str, String str2) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getRiseSpeakerStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getCruiseControlStatus(String str, String str2) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getCruiseActive();
    }
}
