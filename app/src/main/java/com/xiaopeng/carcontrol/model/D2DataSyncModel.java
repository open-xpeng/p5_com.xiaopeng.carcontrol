package com.xiaopeng.carcontrol.model;

import androidx.collection.ArrayMap;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.lib.framework.moduleinterface.syncmodule.SyncData;
import com.xiaopeng.lib.framework.moduleinterface.syncmodule.SyncRestoreEvent;
import com.xiaopeng.xvs.xid.sync.api.ICarControlSync;

/* loaded from: classes2.dex */
public class D2DataSyncModel extends DataSyncModel {
    private static final String TAG = "D2DataSyncModel";

    @Override // com.xiaopeng.carcontrol.model.DataSyncModel
    protected void handleSyncRestoreEvent(SyncRestoreEvent event) {
        CarControlSyncDataEvent carControlSyncDataEvent = new CarControlSyncDataEvent();
        ArrayMap arrayMap = new ArrayMap(this.mSyncFullMap);
        StringBuilder sb = new StringBuilder();
        for (SyncData syncData : event.list) {
            sb.append("data = ").append(syncData).append("\n");
            fillSyncData(syncData, carControlSyncDataEvent);
            if (isGuest()) {
                arrayMap.remove(syncData.key);
            }
        }
        LogUtils.i(TAG, sb.toString(), false);
        if (isGuest()) {
            for (SyncData syncData2 : arrayMap.values()) {
                fillSyncData(syncData2, carControlSyncDataEvent);
            }
        }
        if (event.list.isEmpty()) {
            carControlSyncDataEvent.setDataEmpty(true);
        }
        this.mIsDataSynced = true;
        if (this.mDataSyncChangeListener != null) {
            this.mDataSyncChangeListener.onAccountDataSynced(carControlSyncDataEvent);
        }
    }

    @Override // com.xiaopeng.carcontrol.model.DataSyncModel
    public void setDriveAutoLock(boolean enable) {
        LogUtils.i(TAG, "setDriveAutoLock: " + enable);
        this.mSyncDataValue.setDriveAutoLock(enable);
        if (!isGuest()) {
            save(ICarControlSync.DRIVE_AUTO_LOCK, String.valueOf(enable));
        }
        SharedPreferenceUtil.setDriveAutoLock(enable);
    }

    @Override // com.xiaopeng.carcontrol.model.DataSyncModel
    public boolean getDriveAutoLock() {
        if (isGuest()) {
            return SharedPreferenceUtil.getDriveAutoLock();
        }
        return this.mSyncDataValue.isDriveAutoLock();
    }

    @Override // com.xiaopeng.carcontrol.model.DataSyncModel
    public void setMirrorAutoDownSw(boolean enabled) {
        LogUtils.i(TAG, "setMirrorAutoDownSw: " + enabled);
        this.mSyncDataValue.setMirrorAutoDown(enabled);
        if (!isGuest()) {
            save(ICarControlSync.MIRROR_AUTO_DOWN, String.valueOf(enabled));
        }
        SharedPreferenceUtil.setMirrorAutoDown(enabled);
    }

    @Override // com.xiaopeng.carcontrol.model.DataSyncModel
    public boolean getMirrorAutoDownSw() {
        if (isGuest()) {
            return SharedPreferenceUtil.getMirrorAutoDown();
        }
        return this.mSyncDataValue.getMirrorAutoDown();
    }

    @Override // com.xiaopeng.carcontrol.model.DataSyncModel
    public boolean getMeterDefineTemperature() {
        if (isGuest()) {
            return SharedPreferenceUtil.getMeterMenuTemperature();
        }
        return this.mSyncDataValue.isMeterDefineTemperature();
    }

    @Override // com.xiaopeng.carcontrol.model.DataSyncModel
    public void setMeterDefineTemperature(boolean value) {
        LogUtils.i(TAG, "setMeterDefineTemperature value = " + value);
        this.mSyncDataValue.setMeterDefineTemperature(value);
        if (!isGuest()) {
            save(ICarControlSync.METER_DEFINE_TEMPERATURE, String.valueOf(value));
        }
        SharedPreferenceUtil.setMeterMenuTemperature(value);
    }

    @Override // com.xiaopeng.carcontrol.model.DataSyncModel
    public boolean getMeterDefineWindPower() {
        if (isGuest()) {
            return SharedPreferenceUtil.getMeterMenuWindPower();
        }
        return this.mSyncDataValue.isMeterDefineWindPower();
    }

    @Override // com.xiaopeng.carcontrol.model.DataSyncModel
    public void setMeterDefineWindPower(boolean value) {
        LogUtils.i(TAG, "setMeterDefineWindPower value = " + value);
        this.mSyncDataValue.setMeterDefineWindPower(value);
        if (!isGuest()) {
            save(ICarControlSync.METER_DEFINE_WIND_POWER, String.valueOf(value));
        }
        SharedPreferenceUtil.setMeterMenuWindPower(value);
    }

    @Override // com.xiaopeng.carcontrol.model.DataSyncModel
    public boolean getMeterDefineWindMode() {
        if (isGuest()) {
            return SharedPreferenceUtil.getMeterMenuWindMode();
        }
        return this.mSyncDataValue.isMeterDefineWindMode();
    }

    @Override // com.xiaopeng.carcontrol.model.DataSyncModel
    public void setMeterDefineWindMode(boolean value) {
        LogUtils.i(TAG, "setMeterDefineWindMode value = " + value);
        this.mSyncDataValue.setMeterDefineWindMode(value);
        if (!isGuest()) {
            save(ICarControlSync.METER_DEFINE_WIND_MODE, String.valueOf(value));
        }
        SharedPreferenceUtil.setMeterMenuWindMode(value);
    }

    @Override // com.xiaopeng.carcontrol.model.DataSyncModel
    public boolean getMeterDefineMediaSource() {
        if (isGuest()) {
            return SharedPreferenceUtil.getMeterMenuMediaSource();
        }
        return this.mSyncDataValue.isMeterDefineMediaSource();
    }

    @Override // com.xiaopeng.carcontrol.model.DataSyncModel
    public void setMeterDefineMediaSource(boolean value) {
        LogUtils.i(TAG, "setMeterDefineMediaSource value = " + value);
        this.mSyncDataValue.setMeterDefineMediaSource(value);
        if (!isGuest()) {
            save(ICarControlSync.METER_DEFINE_MEDIA_SOURCE, String.valueOf(value));
        }
        SharedPreferenceUtil.setMeterMenuMediaSource(value);
    }

    @Override // com.xiaopeng.carcontrol.model.DataSyncModel
    public boolean getMeterDefineScreenLight() {
        if (isGuest()) {
            return SharedPreferenceUtil.getMeterMenuScreenLight();
        }
        return this.mSyncDataValue.isMeterDefineScreenLight();
    }

    @Override // com.xiaopeng.carcontrol.model.DataSyncModel
    public void setMeterDefineScreenLight(boolean value) {
        LogUtils.i(TAG, "setMeterDefineScreenLight value = " + value);
        this.mSyncDataValue.setMeterDefineScreenLight(value);
        if (!isGuest()) {
            save(ICarControlSync.METER_DEFINE_SCREEN_LIGHT, String.valueOf(value));
        }
        SharedPreferenceUtil.setMeterMenuScreenLight(value);
    }

    @Override // com.xiaopeng.carcontrol.model.DataSyncModel
    public boolean getSpeedLimit() {
        if (isGuest()) {
            return SharedPreferenceUtil.getSpeedLimit();
        }
        return this.mSyncDataValue.isSpeedLimit();
    }

    @Override // com.xiaopeng.carcontrol.model.DataSyncModel
    public void setSpeedLimit(boolean value) {
        LogUtils.i(TAG, "setSpeedLimit value = " + value);
        this.mSyncDataValue.setSpeedLimit(value);
        if (!isGuest()) {
            save(ICarControlSync.SPEED_LIMIT, String.valueOf(value));
        }
        SharedPreferenceUtil.setSpeedLimit(value);
    }

    @Override // com.xiaopeng.carcontrol.model.DataSyncModel
    public int getSpeedLimitValue() {
        if (isGuest()) {
            return SharedPreferenceUtil.getSpeedLimitValue();
        }
        return this.mSyncDataValue.getSpeedLimitValue();
    }

    @Override // com.xiaopeng.carcontrol.model.DataSyncModel
    public void setSpeedLimitValue(int value) {
        LogUtils.i(TAG, "setSpeedLimitValue value = " + value);
        this.mSyncDataValue.setSpeedLimitValue(value);
        if (!isGuest()) {
            save(ICarControlSync.SPEED_LIMIT_VALUE, String.valueOf(value));
        }
        SharedPreferenceUtil.setSpeedLimitValue(value);
    }

    @Override // com.xiaopeng.carcontrol.model.DataSyncModel
    public int getWiperInterval() {
        return SharedPreferenceUtil.getWiperIntervalGear();
    }

    @Override // com.xiaopeng.carcontrol.model.DataSyncModel
    public void setWiperInterval(int interval) {
        LogUtils.i(TAG, "setWiperInterval: " + interval);
        SharedPreferenceUtil.setWiperIntervalGear(interval);
    }

    @Override // com.xiaopeng.carcontrol.model.DataSyncModel
    public void setCiuWiperInterval(int interval) {
        LogUtils.i(TAG, "setCiuWiperInterval: " + interval);
        SharedPreferenceUtil.setCiuWiperIntervalGear(interval);
    }

    @Override // com.xiaopeng.carcontrol.model.DataSyncModel
    public int getCiuWiperInterval() {
        return SharedPreferenceUtil.getCiuWiperIntervalGear();
    }

    @Override // com.xiaopeng.carcontrol.model.DataSyncModel
    public void setCiuRainEnable(boolean enable) {
        LogUtils.i(TAG, "setCiuRainEnable: " + enable);
        SharedPreferenceUtil.setCiuRainSwEnabled(enable);
    }

    @Override // com.xiaopeng.carcontrol.model.DataSyncModel
    public boolean isCiuRainEnable() {
        return SharedPreferenceUtil.isCiuRainSwEnabled();
    }

    @Override // com.xiaopeng.carcontrol.model.DataSyncModel
    public void setAvasSw(boolean enable) {
        LogUtils.i(TAG, "setAvasSw: " + enable);
        SharedPreferenceUtil.setAvasLowSpdEnable(enable);
    }

    @Override // com.xiaopeng.carcontrol.model.DataSyncModel
    public boolean getAvasSw() {
        return SharedPreferenceUtil.isAvasLowSpdEnabled();
    }

    @Override // com.xiaopeng.carcontrol.model.DataSyncModel
    public void setAvasEffect(int effect) {
        LogUtils.i(TAG, "setAvasEffect: " + effect);
        this.mSyncDataValue.setAvasEffect(effect);
        if (!isGuest()) {
            save(ICarControlSync.AVAS_EFFECT_NEW, String.valueOf(effect));
        }
        SharedPreferenceUtil.setAvasLowSpdEffect(effect);
    }

    @Override // com.xiaopeng.carcontrol.model.DataSyncModel
    public void setEbwEnable(boolean enabled) {
        LogUtils.i(TAG, "setEbwEnable value = " + enabled);
        this.mSyncDataValue.setBrakeWarning(enabled);
        if (!isGuest()) {
            save(ICarControlSync.BRAKE_WARNING, String.valueOf(enabled));
        }
        SharedPreferenceUtil.setEbwSw(enabled);
    }

    @Override // com.xiaopeng.carcontrol.model.DataSyncModel
    public boolean getEbwEnable() {
        if (isGuest()) {
            return SharedPreferenceUtil.getEbwSw();
        }
        return this.mSyncDataValue.isBrakeWarning();
    }

    @Override // com.xiaopeng.carcontrol.model.DataSyncModel
    public void setLdwSw(boolean enable) {
        LogUtils.i(TAG, "setLdwSw: " + enable);
        this.mSyncDataValue.setLdwSw(enable);
        if (!isGuest()) {
            save(ICarControlSync.LANE_DEPARTURE_WARNING, String.valueOf(enable));
        }
        SharedPreferenceUtil.setLdwSw(enable);
    }

    @Override // com.xiaopeng.carcontrol.model.DataSyncModel
    public boolean getLdwSw() {
        if (isGuest()) {
            return SharedPreferenceUtil.getLdwSw();
        }
        return this.mSyncDataValue.isLdwSw();
    }

    @Override // com.xiaopeng.carcontrol.model.DataSyncModel
    public void setElkSw(boolean enable) {
        LogUtils.i(TAG, "setElkSw: " + enable);
        SharedPreferenceUtil.setElkSw(enable);
    }

    @Override // com.xiaopeng.carcontrol.model.DataSyncModel
    public boolean getElkSw() {
        return SharedPreferenceUtil.getElkSw();
    }

    @Override // com.xiaopeng.carcontrol.model.DataSyncModel
    public void setBsdSw(boolean enable) {
        LogUtils.i(TAG, "setBsdSw: " + enable);
        this.mSyncDataValue.setBsdSw(enable);
        if (!isGuest()) {
            save(ICarControlSync.BLIND_DETECTION_WARNING, String.valueOf(enable));
        }
        SharedPreferenceUtil.setBsdSw(enable);
    }

    @Override // com.xiaopeng.carcontrol.model.DataSyncModel
    public boolean getBsdSw() {
        if (isGuest()) {
            return SharedPreferenceUtil.getBsdSw();
        }
        return this.mSyncDataValue.isBsdSw();
    }

    @Override // com.xiaopeng.carcontrol.model.DataSyncModel
    public void setDowSw(boolean enable) {
        LogUtils.i(TAG, "setDowSw: " + enable);
        SharedPreferenceUtil.setDowEnable(enable);
    }

    @Override // com.xiaopeng.carcontrol.model.DataSyncModel
    public boolean getDowSw() {
        return SharedPreferenceUtil.getDowSw();
    }

    @Override // com.xiaopeng.carcontrol.model.DataSyncModel
    public boolean getSideReversingWarning() {
        if (isGuest()) {
            return SharedPreferenceUtil.getSideReverseWarningSw();
        }
        return this.mSyncDataValue.isSideReversingWarning();
    }

    @Override // com.xiaopeng.carcontrol.model.DataSyncModel
    public void setSideReversingWarning(boolean enable) {
        LogUtils.i(TAG, "setSideReversingWarning enable = " + enable);
        this.mSyncDataValue.setSideReversingWarning(enable);
        if (!isGuest()) {
            save(ICarControlSync.SIDE_REVERSING_WARNING, String.valueOf(enable));
        }
        SharedPreferenceUtil.setSideReverseWarningSw(enable);
    }

    @Override // com.xiaopeng.carcontrol.model.DataSyncModel
    public void setLccSw(boolean enable) {
        LogUtils.i(TAG, "setLaaSw: " + enable);
        SharedPreferenceUtil.setLccSw(enable);
    }

    @Override // com.xiaopeng.carcontrol.model.DataSyncModel
    public void setLccSw(boolean enable, boolean needSaveToAccount) {
        LogUtils.i(TAG, "setLaaSw: " + enable);
        SharedPreferenceUtil.setLccSw(enable);
    }

    @Override // com.xiaopeng.carcontrol.model.DataSyncModel
    public boolean getLccSw() {
        boolean lccSw = SharedPreferenceUtil.getLccSw();
        LogUtils.d(TAG, "getLaaSw: " + lccSw);
        return lccSw;
    }

    @Override // com.xiaopeng.carcontrol.model.DataSyncModel
    public void setIslcSw(boolean enable) {
        LogUtils.i(TAG, "setIslcSw: " + enable);
        this.mSyncDataValue.setIslcSw(enable);
        if (!isGuest()) {
            save(ICarControlSync.SMART_SPEED_LIMIT, String.valueOf(enable));
        }
        SharedPreferenceUtil.setIslcSw(enable);
    }

    @Override // com.xiaopeng.carcontrol.model.DataSyncModel
    public boolean getIslcSw() {
        if (isGuest()) {
            return SharedPreferenceUtil.getIslcSw();
        }
        return this.mSyncDataValue.isIslcSw();
    }

    @Override // com.xiaopeng.carcontrol.model.DataSyncModel
    public void setAlcSw(boolean enable) {
        LogUtils.i(TAG, "setLcaSw: " + enable);
        SharedPreferenceUtil.setAlcSw(enable);
    }

    @Override // com.xiaopeng.carcontrol.model.DataSyncModel
    public void setAlcSw(boolean enable, boolean needSaveToAccount) {
        LogUtils.i(TAG, "setLcaSw: " + enable);
        SharedPreferenceUtil.setAlcSw(enable);
    }

    @Override // com.xiaopeng.carcontrol.model.DataSyncModel
    public boolean getAlcSw() {
        boolean alcSw = SharedPreferenceUtil.getAlcSw();
        LogUtils.d(TAG, "getLcaSw: " + alcSw);
        return alcSw;
    }

    @Override // com.xiaopeng.carcontrol.model.DataSyncModel
    public boolean getAutoParkSw() {
        boolean autoParkSw = SharedPreferenceUtil.getAutoParkSw();
        LogUtils.d(TAG, "getAutoParkSw: " + autoParkSw);
        return autoParkSw;
    }

    @Override // com.xiaopeng.carcontrol.model.DataSyncModel
    public void setAutoParkSw(boolean enable) {
        LogUtils.i(TAG, "setAutoParkSw: " + enable);
        SharedPreferenceUtil.setAutoParkSw(enable);
    }
}
