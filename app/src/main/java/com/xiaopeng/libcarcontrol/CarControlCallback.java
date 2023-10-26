package com.xiaopeng.libcarcontrol;

/* loaded from: classes2.dex */
public abstract class CarControlCallback {
    /* JADX INFO: Access modifiers changed from: protected */
    public void onAirPurgeModeChanged(boolean airPurge) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onAutoDriveModeChanged(boolean autoMode) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onCarControlReadyChanged(boolean isReady) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onCentralLockChanged(boolean locked) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onChargePortStateChanged(boolean leftSide, ChargePortState state) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onChargeStatusChanged(ChargeStatus status) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onDriveDistanceChanged(int distance) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onDriveModeChanged(int driveMode) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onDrvSeatOccupiedChanged(boolean occupied) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onElecPercentChanged(int percent) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onHvacAutoChanged(boolean isAuto) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onHvacBackDefrostChanged(boolean enabled) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onHvacBlowerCtrlTypeChange(boolean isAuto) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onHvacDriverSyncChanged(boolean isSync) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onHvacDriverTempChanged(float temp) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onHvacExternalTempChanged(float temp) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onHvacFanSpeedChanged(int level) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onHvacFrontDefrostChanged(boolean enabled) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onHvacInnerAqChanged(int aqValue) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onHvacPowerChanged(boolean isPowerOn) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onHvacPsnSyncChanged(boolean isSync) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onHvacPsnTempChanged(float temp) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onHvacWindModeColorChanged(int value) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onPsnSeatOccupiedChanged(boolean occupied) {
    }
}
