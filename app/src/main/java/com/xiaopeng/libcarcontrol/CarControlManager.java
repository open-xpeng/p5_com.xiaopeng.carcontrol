package com.xiaopeng.libcarcontrol;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import com.xiaopeng.carcontrol.provider.CarControl;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public final class CarControlManager {
    private static final String TAG = "CarControlManager";
    private static volatile CarControlManager sInstance;
    private final Object mCallbackLock = new Object();
    private final List<CarControlCallback> mCallbacks = new ArrayList();
    private Handler mContentHandler;
    private ContentObserver mContentObserver;
    private Context mContext;
    private ContentObserver mQuickContentObserver;

    public static CarControlManager getInstance(Context context) {
        if (sInstance == null) {
            synchronized (CarControlManager.class) {
                if (sInstance == null) {
                    sInstance = new CarControlManager(context);
                }
            }
        }
        return sInstance;
    }

    private CarControlManager(Context context) {
        Log.d(TAG, TAG);
        this.mContext = context;
        HandlerThread handlerThread = new HandlerThread("CarControl-ContentObserver");
        handlerThread.start();
        this.mContentHandler = new Handler(handlerThread.getLooper());
        registerObserver();
    }

    public void release() {
        this.mCallbacks.clear();
        unregisterContentObserver();
        this.mContext = null;
    }

    public void registerCallback(CarControlCallback callback) {
        if (callback != null) {
            synchronized (this.mCallbackLock) {
                this.mCallbacks.add(callback);
            }
        }
    }

    public void unregisterCallback(CarControlCallback callback) {
        if (callback != null) {
            synchronized (this.mCallbackLock) {
                this.mCallbacks.remove(callback);
            }
        }
    }

    public boolean setHvacPower(boolean onOff) {
        return CarControl.System.putBool(this.mContext.getContentResolver(), CarControl.System.HVAC_POWER_SET, onOff);
    }

    public boolean isHvacPowerOn() {
        return CarControl.System.getBool(this.mContext.getContentResolver(), CarControl.System.HVAC_POWER, false);
    }

    public boolean isHvacAutoMode() {
        return CarControl.System.getBool(this.mContext.getContentResolver(), CarControl.System.HVAC_AUTO, false);
    }

    public int getHvacFanSpeed() {
        return CarControl.System.getInt(this.mContext.getContentResolver(), CarControl.System.HVAC_WIND_LEVEL, 0);
    }

    public boolean setHvacDriverTemp(float temp) {
        return CarControl.System.putFloat(this.mContext.getContentResolver(), CarControl.System.HVAC_DRV_TEMP_SET, temp);
    }

    public boolean setHvacDriverStep(boolean isUp) {
        return CarControl.System.putBool(this.mContext.getContentResolver(), CarControl.System.HVAC_DRV_TEMP_STEP_SET, isUp);
    }

    public float getHvacDriverTemp() {
        return CarControl.System.getFloat(this.mContext.getContentResolver(), CarControl.System.HVAC_DRV_TEMP, 18.0f);
    }

    public boolean setHvacDriverSyncMode(boolean enable) {
        return CarControl.System.putBool(this.mContext.getContentResolver(), CarControl.System.HVAC_DRV_SYNC_SET, enable);
    }

    public boolean isHvacDriverSyncEnabled() {
        return CarControl.System.getBool(this.mContext.getContentResolver(), CarControl.System.HVAC_DRV_SYNC, false);
    }

    public boolean setHvacPsnTemp(float temp) {
        return CarControl.System.putFloat(this.mContext.getContentResolver(), CarControl.System.HVAC_PSN_TEMP_SET, temp);
    }

    public boolean setHvacPsnStep(boolean isUp) {
        return CarControl.System.putBool(this.mContext.getContentResolver(), CarControl.System.HVAC_PSN_TEMP_STEP_SET, isUp);
    }

    public float getHvacPsnTemp() {
        return CarControl.System.getFloat(this.mContext.getContentResolver(), CarControl.System.HVAC_PSN_TEMP, 18.0f);
    }

    public boolean setHvacPsnSyncMode(boolean enable) {
        return CarControl.System.putBool(this.mContext.getContentResolver(), CarControl.System.HVAC_PSN_SYNC_SET, enable);
    }

    public boolean isHvacPsnSyncEnabled() {
        return CarControl.System.getBool(this.mContext.getContentResolver(), CarControl.System.HVAC_PSN_SYNC, false);
    }

    public boolean setHvacFrontDefrostEnable(boolean enable) {
        return CarControl.System.putBool(this.mContext.getContentResolver(), CarControl.System.HVAC_FRONT_DEFROST_SET, enable);
    }

    public boolean isHvacFrontDefrostEnable() {
        return CarControl.System.getBool(this.mContext.getContentResolver(), CarControl.System.HVAC_FRONT_DEFROST, false);
    }

    public boolean setHvacBackDefrostEnable(boolean enable) {
        return CarControl.System.putBool(this.mContext.getContentResolver(), CarControl.System.HVAC_BACK_DEFROST_SET, enable);
    }

    public boolean isHvacBackDefrostEnable() {
        return CarControl.System.getBool(this.mContext.getContentResolver(), CarControl.System.HVAC_BACK_DEFROST, false);
    }

    public int getHvacInnerAq() {
        return CarControl.System.getInt(this.mContext.getContentResolver(), CarControl.System.HVAC_PM25, 0);
    }

    public float getHvacExternalTemp() {
        return CarControl.System.getFloat(this.mContext.getContentResolver(), CarControl.System.HVAC_OUT_TEMP, 0.0f);
    }

    public int getHvacWindModeColor() {
        return CarControl.System.getInt(this.mContext.getContentResolver(), CarControl.System.HVAC_WIND_MODE_COLOR, 0);
    }

    public boolean getHvacAirPurgeMode() {
        return CarControl.System.getBool(this.mContext.getContentResolver(), CarControl.System.HVAC_AIR_PURGE_MODE, false);
    }

    public boolean getHvacBlowerCtrlType() {
        return CarControl.System.getBool(this.mContext.getContentResolver(), CarControl.System.HVAC_BLOWER_CTRL_TYPE, false);
    }

    public boolean setCentralLock(boolean locked) {
        return CarControl.System.putBool(this.mContext.getContentResolver(), CarControl.System.CENTRAL_LOCK_SET, locked);
    }

    public boolean isCentralLockOn() {
        return CarControl.System.getBool(this.mContext.getContentResolver(), CarControl.System.CENTRAL_LOCK, false);
    }

    public boolean isDrvSeatOccupied() {
        return CarControl.System.getBool(this.mContext.getContentResolver(), CarControl.System.DRV_OCCUPIED, false);
    }

    public boolean isPsnSeatOccupied() {
        return CarControl.System.getBool(this.mContext.getContentResolver(), CarControl.System.PSN_OCCUPIED, false);
    }

    public boolean isCarControlReady() {
        return CarControl.System.getBool(this.mContext.getContentResolver(), CarControl.System.CAR_CONTROL_READY, false);
    }

    public ChargeStatus getChargeStatus() {
        return ChargeStatus.fromVcuChargeStatus(CarControl.System.getInt(this.mContext.getContentResolver(), CarControl.System.CHARGE_STATUS, 0));
    }

    public int getElecPercent() {
        return CarControl.System.getInt(this.mContext.getContentResolver(), CarControl.System.BATTERY_PERCENT, 0);
    }

    public int getDriveDistance() {
        return CarControl.System.getInt(this.mContext.getContentResolver(), CarControl.System.AVAILABLE_DISTANCE, 0);
    }

    public boolean openRearTrunk() {
        return CarControl.Quick.putInt(this.mContext.getContentResolver(), CarControl.Quick.OPEN_REAR_TRUNK, 2);
    }

    public ChargePortState getChargePortState(boolean leftSide) {
        return ChargePortState.fromBcmValue(CarControl.Quick.getInt(this.mContext.getContentResolver(), leftSide ? CarControl.Quick.LEFT_CHARGE_PORT_STATE : CarControl.Quick.RIGHT_CHARGE_PORT_STATE, -1));
    }

    public boolean openChargePort(boolean leftSide) {
        return CarControl.Quick.putInt(this.mContext.getContentResolver(), leftSide ? CarControl.Quick.LEFT_CHARGE_PORT : CarControl.Quick.RIGHT_CHARGE_PORT, 1);
    }

    public boolean closeChargePort(boolean leftSide) {
        return CarControl.Quick.putInt(this.mContext.getContentResolver(), leftSide ? CarControl.Quick.LEFT_CHARGE_PORT : CarControl.Quick.RIGHT_CHARGE_PORT, 0);
    }

    public boolean resetChargePort(boolean leftSide) {
        return CarControl.Quick.putInt(this.mContext.getContentResolver(), CarControl.Quick.RESET_CHARGE_PORT, leftSide ? 1 : 2);
    }

    public boolean setDriveMode(int driveMode) {
        return CarControl.System.putInt(this.mContext.getContentResolver(), CarControl.System.DRIVE_MODE_SET, driveMode);
    }

    public int getDriveMode() {
        return CarControl.System.getInt(this.mContext.getContentResolver(), CarControl.System.DRIVE_MODE, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleHvacPowerChanged(boolean isPowerOn) {
        synchronized (this.mCallbackLock) {
            for (CarControlCallback carControlCallback : this.mCallbacks) {
                carControlCallback.onHvacPowerChanged(isPowerOn);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleHvacAutoChanged(boolean isAuto) {
        synchronized (this.mCallbackLock) {
            for (CarControlCallback carControlCallback : this.mCallbacks) {
                carControlCallback.onHvacAutoChanged(isAuto);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleHvacWindLevelChanged(int level) {
        synchronized (this.mCallbackLock) {
            for (CarControlCallback carControlCallback : this.mCallbacks) {
                carControlCallback.onHvacFanSpeedChanged(level);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleHvacDrvTempChanged(float temp) {
        synchronized (this.mCallbackLock) {
            for (CarControlCallback carControlCallback : this.mCallbacks) {
                carControlCallback.onHvacDriverTempChanged(temp);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleHvacDrvSyncChanged(boolean isSync) {
        synchronized (this.mCallbackLock) {
            for (CarControlCallback carControlCallback : this.mCallbacks) {
                carControlCallback.onHvacDriverSyncChanged(isSync);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleHvacPsnTempChanged(float temp) {
        synchronized (this.mCallbackLock) {
            for (CarControlCallback carControlCallback : this.mCallbacks) {
                carControlCallback.onHvacPsnTempChanged(temp);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleHvacPsnSyncChanged(boolean isSync) {
        synchronized (this.mCallbackLock) {
            for (CarControlCallback carControlCallback : this.mCallbacks) {
                carControlCallback.onHvacPsnSyncChanged(isSync);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleHvacFrontDefrostChanged(boolean enabled) {
        synchronized (this.mCallbackLock) {
            for (CarControlCallback carControlCallback : this.mCallbacks) {
                carControlCallback.onHvacFrontDefrostChanged(enabled);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleHvacBackDefrostChanged(boolean enabled) {
        synchronized (this.mCallbackLock) {
            for (CarControlCallback carControlCallback : this.mCallbacks) {
                carControlCallback.onHvacBackDefrostChanged(enabled);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleHvacWindModeColorChanged(int value) {
        synchronized (this.mCallbackLock) {
            for (CarControlCallback carControlCallback : this.mCallbacks) {
                carControlCallback.onHvacWindModeColorChanged(value);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleHvacAirPurgeModeChanged(boolean mode) {
        synchronized (this.mCallbackLock) {
            for (CarControlCallback carControlCallback : this.mCallbacks) {
                carControlCallback.onAirPurgeModeChanged(mode);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleHvacInnerAqChanged(int aqValue) {
        synchronized (this.mCallbackLock) {
            for (CarControlCallback carControlCallback : this.mCallbacks) {
                carControlCallback.onHvacInnerAqChanged(aqValue);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleHvacOutTempChanged(float temp) {
        synchronized (this.mCallbackLock) {
            for (CarControlCallback carControlCallback : this.mCallbacks) {
                carControlCallback.onHvacExternalTempChanged(temp);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleHvacBlowerCtrlType(boolean isAuto) {
        synchronized (this.mCallbackLock) {
            for (CarControlCallback carControlCallback : this.mCallbacks) {
                carControlCallback.onHvacBlowerCtrlTypeChange(isAuto);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleCentralLockChanged(boolean locked) {
        synchronized (this.mCallbackLock) {
            for (CarControlCallback carControlCallback : this.mCallbacks) {
                carControlCallback.onCentralLockChanged(locked);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleChargeStatusChanged(int status) {
        ChargeStatus fromVcuChargeStatus = ChargeStatus.fromVcuChargeStatus(status);
        synchronized (this.mCallbackLock) {
            for (CarControlCallback carControlCallback : this.mCallbacks) {
                carControlCallback.onChargeStatusChanged(fromVcuChargeStatus);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDrvSeatOccupied(boolean occupied) {
        synchronized (this.mCallbackLock) {
            for (CarControlCallback carControlCallback : this.mCallbacks) {
                carControlCallback.onDrvSeatOccupiedChanged(occupied);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handlePsnSeatOccupied(boolean occupied) {
        synchronized (this.mCallbackLock) {
            for (CarControlCallback carControlCallback : this.mCallbacks) {
                carControlCallback.onPsnSeatOccupiedChanged(occupied);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleCarControlReady(boolean isReady) {
        synchronized (this.mCallbackLock) {
            for (CarControlCallback carControlCallback : this.mCallbacks) {
                carControlCallback.onCarControlReadyChanged(isReady);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleElecPercentChanged(int percent) {
        synchronized (this.mCallbackLock) {
            for (CarControlCallback carControlCallback : this.mCallbacks) {
                carControlCallback.onElecPercentChanged(percent);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDriveDistanceChanged(int distance) {
        synchronized (this.mCallbackLock) {
            for (CarControlCallback carControlCallback : this.mCallbacks) {
                carControlCallback.onDriveDistanceChanged(distance);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleChargePortStateChanged(boolean leftSide, int state) {
        ChargePortState fromBcmValue = ChargePortState.fromBcmValue(state);
        synchronized (this.mCallbackLock) {
            for (CarControlCallback carControlCallback : this.mCallbacks) {
                carControlCallback.onChargePortStateChanged(leftSide, fromBcmValue);
            }
        }
    }

    private void handleAutoDriveModeChanged(boolean autoMode) {
        synchronized (this.mCallbackLock) {
            for (CarControlCallback carControlCallback : this.mCallbacks) {
                carControlCallback.onAutoDriveModeChanged(autoMode);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDriveModeChanged(int driveMode) {
        synchronized (this.mCallbackLock) {
            for (CarControlCallback carControlCallback : this.mCallbacks) {
                carControlCallback.onDriveModeChanged(driveMode);
            }
        }
    }

    private void registerObserver() {
        if (this.mContentObserver == null) {
            this.mContentObserver = new ContentObserver(this.mContentHandler) { // from class: com.xiaopeng.libcarcontrol.CarControlManager.1
                @Override // android.database.ContentObserver
                public void onChange(boolean selfChange, Uri uri) {
                    if (uri == null) {
                        return;
                    }
                    Log.d(CarControlManager.TAG, "onChange: " + uri);
                    String lastPathSegment = uri.getLastPathSegment();
                    if (lastPathSegment == null) {
                        return;
                    }
                    lastPathSegment.hashCode();
                    char c = 65535;
                    switch (lastPathSegment.hashCode()) {
                        case -2027240739:
                            if (lastPathSegment.equals(CarControl.System.HVAC_BLOWER_CTRL_TYPE)) {
                                c = 0;
                                break;
                            }
                            break;
                        case -1975165933:
                            if (lastPathSegment.equals(CarControl.System.BATTERY_PERCENT)) {
                                c = 1;
                                break;
                            }
                            break;
                        case -1967627652:
                            if (lastPathSegment.equals(CarControl.System.HVAC_WIND_LEVEL)) {
                                c = 2;
                                break;
                            }
                            break;
                        case -1613760547:
                            if (lastPathSegment.equals(CarControl.System.CHARGE_STATUS)) {
                                c = 3;
                                break;
                            }
                            break;
                        case -684943650:
                            if (lastPathSegment.equals(CarControl.System.HVAC_AUTO)) {
                                c = 4;
                                break;
                            }
                            break;
                        case -684506577:
                            if (lastPathSegment.equals(CarControl.System.HVAC_PM25)) {
                                c = 5;
                                break;
                            }
                            break;
                        case -681141809:
                            if (lastPathSegment.equals(CarControl.System.HVAC_WIND_MODE_COLOR)) {
                                c = 6;
                                break;
                            }
                            break;
                        case -535149397:
                            if (lastPathSegment.equals(CarControl.System.AVAILABLE_DISTANCE)) {
                                c = 7;
                                break;
                            }
                            break;
                        case -373706315:
                            if (lastPathSegment.equals(CarControl.System.CENTRAL_LOCK)) {
                                c = '\b';
                                break;
                            }
                            break;
                        case 255260086:
                            if (lastPathSegment.equals(CarControl.System.HVAC_POWER)) {
                                c = '\t';
                                break;
                            }
                            break;
                        case 498854741:
                            if (lastPathSegment.equals(CarControl.System.DRV_OCCUPIED)) {
                                c = '\n';
                                break;
                            }
                            break;
                        case 577486648:
                            if (lastPathSegment.equals(CarControl.System.DRIVE_MODE)) {
                                c = 11;
                                break;
                            }
                            break;
                        case 649261694:
                            if (lastPathSegment.equals(CarControl.System.HVAC_FRONT_DEFROST)) {
                                c = '\f';
                                break;
                            }
                            break;
                        case 1132513595:
                            if (lastPathSegment.equals(CarControl.System.HVAC_AIR_PURGE_MODE)) {
                                c = '\r';
                                break;
                            }
                            break;
                        case 1236238596:
                            if (lastPathSegment.equals(CarControl.System.CAR_CONTROL_READY)) {
                                c = 14;
                                break;
                            }
                            break;
                        case 1255743617:
                            if (lastPathSegment.equals(CarControl.System.HVAC_DRV_SYNC)) {
                                c = 15;
                                break;
                            }
                            break;
                        case 1255754170:
                            if (lastPathSegment.equals(CarControl.System.HVAC_DRV_TEMP)) {
                                c = 16;
                                break;
                            }
                            break;
                        case 1353101630:
                            if (lastPathSegment.equals(CarControl.System.HVAC_PSN_SYNC)) {
                                c = 17;
                                break;
                            }
                            break;
                        case 1353112183:
                            if (lastPathSegment.equals(CarControl.System.HVAC_PSN_TEMP)) {
                                c = 18;
                                break;
                            }
                            break;
                        case 1545300410:
                            if (lastPathSegment.equals(CarControl.System.HVAC_BACK_DEFROST)) {
                                c = 19;
                                break;
                            }
                            break;
                        case 1557084116:
                            if (lastPathSegment.equals(CarControl.System.HVAC_OUT_TEMP)) {
                                c = 20;
                                break;
                            }
                            break;
                        case 1823004050:
                            if (lastPathSegment.equals(CarControl.System.PSN_OCCUPIED)) {
                                c = 21;
                                break;
                            }
                            break;
                    }
                    float f = 32.0f;
                    switch (c) {
                        case 0:
                            CarControlManager carControlManager = CarControlManager.this;
                            carControlManager.handleHvacBlowerCtrlType(carControlManager.getBoolValue(lastPathSegment));
                            return;
                        case 1:
                            CarControlManager carControlManager2 = CarControlManager.this;
                            carControlManager2.handleElecPercentChanged(carControlManager2.getIntValue(lastPathSegment));
                            return;
                        case 2:
                            CarControlManager carControlManager3 = CarControlManager.this;
                            carControlManager3.handleHvacWindLevelChanged(carControlManager3.getIntValue(lastPathSegment));
                            return;
                        case 3:
                            CarControlManager carControlManager4 = CarControlManager.this;
                            carControlManager4.handleChargeStatusChanged(carControlManager4.getIntValue(lastPathSegment));
                            return;
                        case 4:
                            CarControlManager carControlManager5 = CarControlManager.this;
                            carControlManager5.handleHvacAutoChanged(carControlManager5.getBoolValue(lastPathSegment));
                            return;
                        case 5:
                            CarControlManager carControlManager6 = CarControlManager.this;
                            carControlManager6.handleHvacInnerAqChanged(carControlManager6.getIntValue(lastPathSegment));
                            return;
                        case 6:
                            CarControlManager carControlManager7 = CarControlManager.this;
                            carControlManager7.handleHvacWindModeColorChanged(carControlManager7.getIntValue(lastPathSegment));
                            return;
                        case 7:
                            CarControlManager carControlManager8 = CarControlManager.this;
                            carControlManager8.handleDriveDistanceChanged(carControlManager8.getIntValue(lastPathSegment));
                            return;
                        case '\b':
                            CarControlManager carControlManager9 = CarControlManager.this;
                            carControlManager9.handleCentralLockChanged(carControlManager9.getBoolValue(lastPathSegment));
                            return;
                        case '\t':
                            CarControlManager carControlManager10 = CarControlManager.this;
                            carControlManager10.handleHvacPowerChanged(carControlManager10.getBoolValue(lastPathSegment));
                            return;
                        case '\n':
                            CarControlManager carControlManager11 = CarControlManager.this;
                            carControlManager11.handleDrvSeatOccupied(carControlManager11.getBoolValue(lastPathSegment));
                            return;
                        case 11:
                            CarControlManager carControlManager12 = CarControlManager.this;
                            carControlManager12.handleDriveModeChanged(carControlManager12.getIntValue(lastPathSegment));
                            return;
                        case '\f':
                            CarControlManager carControlManager13 = CarControlManager.this;
                            carControlManager13.handleHvacFrontDefrostChanged(carControlManager13.getBoolValue(lastPathSegment));
                            return;
                        case '\r':
                            CarControlManager carControlManager14 = CarControlManager.this;
                            carControlManager14.handleHvacAirPurgeModeChanged(carControlManager14.getBoolValue(lastPathSegment));
                            return;
                        case 14:
                            CarControlManager carControlManager15 = CarControlManager.this;
                            carControlManager15.handleCarControlReady(carControlManager15.getBoolValue(lastPathSegment));
                            return;
                        case 15:
                            CarControlManager carControlManager16 = CarControlManager.this;
                            carControlManager16.handleHvacDrvSyncChanged(carControlManager16.getBoolValue(lastPathSegment));
                            return;
                        case 16:
                            float floatValue = CarControlManager.this.getFloatValue(lastPathSegment);
                            CarControlManager carControlManager17 = CarControlManager.this;
                            if (floatValue < 18.0f) {
                                f = 18.0f;
                            } else if (floatValue <= 32.0f) {
                                f = floatValue;
                            }
                            carControlManager17.handleHvacDrvTempChanged(f);
                            return;
                        case 17:
                            CarControlManager carControlManager18 = CarControlManager.this;
                            carControlManager18.handleHvacPsnSyncChanged(carControlManager18.getBoolValue(lastPathSegment));
                            return;
                        case 18:
                            float floatValue2 = CarControlManager.this.getFloatValue(lastPathSegment);
                            CarControlManager carControlManager19 = CarControlManager.this;
                            if (floatValue2 < 18.0f) {
                                f = 18.0f;
                            } else if (floatValue2 <= 32.0f) {
                                f = floatValue2;
                            }
                            carControlManager19.handleHvacPsnTempChanged(f);
                            return;
                        case 19:
                            CarControlManager carControlManager20 = CarControlManager.this;
                            carControlManager20.handleHvacBackDefrostChanged(carControlManager20.getBoolValue(lastPathSegment));
                            return;
                        case 20:
                            CarControlManager carControlManager21 = CarControlManager.this;
                            carControlManager21.handleHvacOutTempChanged(carControlManager21.getFloatValue(lastPathSegment));
                            return;
                        case 21:
                            CarControlManager carControlManager22 = CarControlManager.this;
                            carControlManager22.handlePsnSeatOccupied(carControlManager22.getBoolValue(lastPathSegment));
                            return;
                        default:
                            return;
                    }
                }
            };
        }
        if (this.mQuickContentObserver == null) {
            this.mQuickContentObserver = new ContentObserver(this.mContentHandler) { // from class: com.xiaopeng.libcarcontrol.CarControlManager.2
                @Override // android.database.ContentObserver
                public void onChange(boolean selfChange, Uri uri) {
                    if (uri == null) {
                        return;
                    }
                    Log.d(CarControlManager.TAG, "onChange: " + uri);
                    String lastPathSegment = uri.getLastPathSegment();
                    if (lastPathSegment == null) {
                        return;
                    }
                    lastPathSegment.hashCode();
                    if (lastPathSegment.equals(CarControl.Quick.LEFT_CHARGE_PORT_STATE)) {
                        CarControlManager carControlManager = CarControlManager.this;
                        carControlManager.handleChargePortStateChanged(true, CarControl.Quick.getInt(carControlManager.mContext.getContentResolver(), lastPathSegment, -1));
                    } else if (lastPathSegment.equals(CarControl.Quick.RIGHT_CHARGE_PORT_STATE)) {
                        CarControlManager carControlManager2 = CarControlManager.this;
                        carControlManager2.handleChargePortStateChanged(false, CarControl.Quick.getInt(carControlManager2.mContext.getContentResolver(), lastPathSegment, -1));
                    }
                }
            };
        }
        try {
            this.mContext.getContentResolver().registerContentObserver(CarControl.Quick.getUriFor(CarControl.Quick.LEFT_CHARGE_PORT_STATE), false, this.mQuickContentObserver);
            this.mContext.getContentResolver().registerContentObserver(CarControl.Quick.getUriFor(CarControl.Quick.RIGHT_CHARGE_PORT_STATE), false, this.mQuickContentObserver);
        } catch (Exception e) {
            Log.e(TAG, "register ContentObserver ac/dc port failed", e);
        }
    }

    public boolean registerSystemObserver() {
        try {
            this.mContext.getContentResolver().registerContentObserver(CarControl.System.CONTENT_URI, true, this.mContentObserver);
            return true;
        } catch (Exception e) {
            Log.e(TAG, "register ContentObserver " + CarControl.System.CONTENT_URI + " failed", e);
            return false;
        }
    }

    private void unregisterContentObserver() {
        this.mContext.getContentResolver().unregisterContentObserver(this.mContentObserver);
        this.mContext.getContentResolver().unregisterContentObserver(this.mQuickContentObserver);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getIntValue(String key) {
        return CarControl.System.getInt(this.mContext.getContentResolver(), key, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public float getFloatValue(String key) {
        return CarControl.System.getFloat(this.mContext.getContentResolver(), key, 0.0f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean getBoolValue(String key) {
        return CarControl.System.getBool(this.mContext.getContentResolver(), key, false);
    }
}
