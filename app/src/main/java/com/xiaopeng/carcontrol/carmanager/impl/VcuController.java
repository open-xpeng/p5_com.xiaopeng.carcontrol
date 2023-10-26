package com.xiaopeng.carcontrol.carmanager.impl;

import android.car.Car;
import android.car.CarNotConnectedException;
import android.car.hardware.CarPropertyValue;
import android.car.hardware.condition.CarConditionInfo;
import android.car.hardware.condition.CarConditionManager;
import android.car.hardware.vcu.CarVcuManager;
import android.content.ContentResolver;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.RemoteException;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.GlobalConstant;
import com.xiaopeng.carcontrol.carmanager.BaseCarController;
import com.xiaopeng.carcontrol.carmanager.CarClientWrapper;
import com.xiaopeng.carcontrol.carmanager.controller.IVcuController;
import com.xiaopeng.carcontrol.carmanager.impl.oldarch.VcuOldController;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import com.xiaopeng.carcontrol.model.DataSyncModel;
import com.xiaopeng.carcontrol.provider.CarControl;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.carcontrol.viewmodel.service.ShowCarControl;
import com.xiaopeng.lib.apirouter.ApiRouter;
import com.xiaopeng.speech.vui.constants.VuiConstants;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class VcuController extends BaseCarController<CarVcuManager, IVcuController.Callback> implements IVcuController {
    protected static final String TAG = "VcuController";
    private final CarVcuManager.CarVcuEventCallback mCarVcuEventCallback = new CarVcuManager.CarVcuEventCallback() { // from class: com.xiaopeng.carcontrol.carmanager.impl.VcuController.1
        public void onChangeEvent(CarPropertyValue carPropertyValue) {
            VcuController.this.handleCarEventsUpdate(carPropertyValue);
        }

        public void onErrorEvent(int propertyId, int errorCode) {
            if (errorCode == 6) {
                LogUtils.e(VcuController.TAG, "onErrorEvent: " + propertyId + ", errorCode: " + errorCode, false);
                VcuController.this.handleLossEventsUpdate(propertyId);
            }
        }
    };
    private ContentObserver mContentObserver;
    private String mCurrentUdDriveSubItemInfo;
    private Integer mNewDriveMode;

    private int covertAwdMode(int number) {
        if (number != 0) {
            if (number != 3) {
                if (number != 5) {
                    if (number == 7) {
                        return 3;
                    }
                    if (number == 10) {
                        return 4;
                    }
                }
                return 2;
            }
            return 1;
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    public void initXuiManager() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleLossEventsUpdate(int propertyId) {
        int i = propertyId != 557847045 ? propertyId != 557847118 ? -1 : 3 : 0;
        if (i != -1) {
            handleCarEventsUpdate(new CarPropertyValue<>(propertyId, Integer.valueOf(i)));
        }
    }

    public VcuController(Car carClient) {
        if (this.mIsMainProcess) {
            return;
        }
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$VcuController$LSJPQ0h5ZK4LK8cFbAtl3WRVMdE
            @Override // java.lang.Runnable
            public final void run() {
                VcuController.this.lambda$new$0$VcuController();
            }
        });
    }

    public /* synthetic */ void lambda$new$0$VcuController() {
        ContentResolver contentResolver = App.getInstance().getContentResolver();
        if (CarBaseConfig.getInstance().isSupportDriveModeNewArch()) {
            this.mNewDriveMode = Integer.valueOf(CarControl.PrivateControl.getInt(contentResolver, CarControl.PrivateControl.NEW_DRIVE_MODE_VALUE, GlobalConstant.DEFAULT.DRIVER_MODE));
            this.mCurrentUdDriveSubItemInfo = CarControl.PrivateControl.getString(contentResolver, CarControl.PrivateControl.USER_DEFINE_DRIVE_MODE_INFO);
        }
        registerContentObserver();
    }

    /* loaded from: classes.dex */
    public static class VcuControllerFactory {
        public static VcuController createCarController(Car carClient) {
            if (CarBaseConfig.getInstance().isNewVcuArch()) {
                return new VcuController(carClient);
            }
            return new VcuOldController(carClient);
        }
    }

    public static int getDefaultEnergyRecycleGrade() {
        if (CarBaseConfig.getInstance().isSupportDriveEnergyReset()) {
            return 3;
        }
        return GlobalConstant.DEFAULT.ENERGY_RECYCLE_GRADE;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    public void initCarManager(Car carClient) {
        LogUtils.d(TAG, "Init start");
        try {
            this.mCarManager = (CarVcuManager) carClient.getCarManager(CarClientWrapper.XP_VCU_SERVICE);
            if (this.mCarManager != 0) {
                this.mCarManager.registerPropCallback(this.mPropertyIds, this.mCarVcuEventCallback);
            }
            if (this.mCarConfig.isSupportCarCondition()) {
                registerCarCondition(carClient);
            }
        } catch (CarNotConnectedException e) {
            LogUtils.e(TAG, (String) null, (Throwable) e, false);
        }
        LogUtils.d(TAG, "Init end");
    }

    private void registerCarCondition(Car carClient) {
        try {
            CarConditionManager carConditionManager = (CarConditionManager) carClient.getCarManager("xp_condition");
            if (carConditionManager != null) {
                CarConditionManager.CarConditionCallback carConditionCallback = new CarConditionManager.CarConditionCallback() { // from class: com.xiaopeng.carcontrol.carmanager.impl.VcuController.2
                    public void onChangeEvent(CarPropertyValue carPropertyValue) {
                        int propertyId = carPropertyValue.getPropertyId();
                        if (propertyId == 557847064) {
                            LogUtils.d(VcuController.TAG, "onChangeEvent: " + carPropertyValue);
                            VcuController vcuController = VcuController.this;
                            vcuController.handleAccPedalStatusChange(((Integer) vcuController.getValue(carPropertyValue)).intValue());
                        } else if (propertyId == 559944229) {
                            LogUtils.d(VcuController.TAG, "onChangeEvent: " + carPropertyValue);
                            VcuController vcuController2 = VcuController.this;
                            vcuController2.handleRawCarSpeed(((Float) vcuController2.getValue(carPropertyValue)).floatValue());
                        } else {
                            LogUtils.w(VcuController.TAG, "handle unknown event: " + carPropertyValue);
                        }
                    }
                };
                CarConditionInfo.Builder builder = new CarConditionInfo.Builder();
                if (BaseFeatureOption.getInstance().shouldRecoverMirror()) {
                    builder.setLimit(559944229, new Float[]{Float.valueOf(3.0f), Float.valueOf(this.mCarConfig.getHighSpdFuncThreshold()), Float.valueOf(this.mCarConfig.getEpsSpdThreshold()), Float.valueOf(10.0f)});
                } else {
                    builder.setLimit(559944229, new Float[]{Float.valueOf(3.0f), Float.valueOf(this.mCarConfig.getHighSpdFuncThreshold()), Float.valueOf(this.mCarConfig.getEpsSpdThreshold())});
                }
                if (this.mCarConfig.isSupportEpbWarning()) {
                    builder.setLimit(557847064, new Integer[]{Integer.valueOf(this.mCarConfig.getEpbAccPedalThreshold())});
                }
                carConditionManager.registerCondition(builder.build(), carConditionCallback);
            }
        } catch (CarNotConnectedException | RemoteException e) {
            LogUtils.e(TAG, (String) null, (Throwable) e, false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    protected List<Integer> getRegisterPropertyIds() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(557847082);
        arrayList.add(557847041);
        arrayList.add(557847045);
        if (this.mIsMainProcess) {
            arrayList.add(557847042);
            arrayList.add(559944326);
            arrayList.add(559944315);
            arrayList.add(559944314);
            arrayList.add(557847193);
            arrayList.add(557847081);
            arrayList.add(557847080);
            arrayList.add(557847118);
        }
        arrayList.add(557847056);
        if (!this.mCarConfig.isSupportCarCondition()) {
            arrayList.add(559944229);
        }
        addExtPropertyIds(arrayList);
        return arrayList;
    }

    protected void addExtPropertyIds(List<Integer> propertyIds) {
        if (this.mCarConfig.isSupportSnowMode()) {
            propertyIds.add(557847085);
        }
        if (this.mIsMainProcess) {
            if (this.mCarConfig.isSupportEpbWarning() && !this.mCarConfig.isSupportCarCondition()) {
                propertyIds.add(557847064);
            }
            if (this.mCarConfig.isSupportTrailerMode()) {
                propertyIds.add(557847142);
            }
        }
        if (this.mCarConfig.isSupportNeutralGearProtect() && ((this.mCarConfig.isSupportUnity3D() && !this.mIsMainProcess) || !this.mCarConfig.isSupportUnity3D())) {
            propertyIds.add(557847101);
        }
        if (CarBaseConfig.getInstance().isSupportDriveModeNewArch()) {
            propertyIds.add(557847134);
        }
        if (CarBaseConfig.getInstance().isSupportXPedalNewArch()) {
            propertyIds.add(557847136);
        }
        if (CarBaseConfig.getInstance().isSupportXSport()) {
            propertyIds.add(557847179);
        }
        if (this.mCarConfig.isSupportSsa() && ((this.mCarConfig.isSupportUnity3D() && !this.mIsMainProcess) || (!this.mCarConfig.isSupportUnity3D() && this.mIsMainProcess))) {
            propertyIds.add(557847164);
        }
        if (this.mCarConfig.isSupportBrakeLight()) {
            propertyIds.add(557847058);
        }
        if (this.mCarConfig.isSupportVcuExhibitionNewVcuArch()) {
            propertyIds.add(557847137);
        }
        if (this.mCarConfig.isSupportAwdSetting()) {
            propertyIds.add(557847194);
        }
        if (CarBaseConfig.getInstance().isSupportVMCControl()) {
            propertyIds.add(557847196);
            propertyIds.add(557847197);
            propertyIds.add(557847198);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    protected void disconnect() {
        if (this.mCarManager != 0) {
            try {
                this.mCarManager.unregisterPropCallback(this.mPropertyIds, this.mCarVcuEventCallback);
            } catch (CarNotConnectedException e) {
                LogUtils.e(TAG, (String) null, (Throwable) e, false);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    public void handleCarEventsUpdate(final CarPropertyValue<?> value) {
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$VcuController$GAKqO29o__wcQbCCtD13pAwaKdY
            @Override // java.lang.Runnable
            public final void run() {
                VcuController.this.lambda$handleCarEventsUpdate$1$VcuController(value);
            }
        });
    }

    public /* synthetic */ void lambda$handleCarEventsUpdate$1$VcuController(final CarPropertyValue value) {
        this.mCarPropertyMap.put(Integer.valueOf(value.getPropertyId()), value);
        handleEventsUpdate(value);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    protected void handleEventsUpdate(CarPropertyValue<?> value) {
        switch (value.getPropertyId()) {
            case 557847041:
                LogUtils.i(TAG, "onChangeEvent: " + value, false);
                handleRecycleGradeUpdate(((Integer) getValue(value)).intValue());
                return;
            case 557847042:
                LogUtils.i(TAG, "onChangeEvent: " + value);
                handleElecPercentUpdate(((Integer) getValue(value)).intValue());
                return;
            case 557847045:
                LogUtils.i(TAG, "onChangeEvent: " + value, false);
                handleGearUpdate(((Integer) getValue(value)).intValue());
                return;
            case 557847056:
                LogUtils.i(TAG, "onChangeEvent: " + value, false);
                handleEvSysReady(((Integer) getValue(value)).intValue());
                return;
            case 557847058:
                handleBrakeLightChange(((Integer) getValue(value)).intValue());
                return;
            case 557847064:
                handleAccPedalStatusChange(((Integer) getValue(value)).intValue());
                return;
            case 557847080:
                LogUtils.i(TAG, "onChangeEvent: " + value);
                handleChargeGunStatusUpdate(((Integer) getValue(value)).intValue());
                return;
            case 557847081:
                LogUtils.i(TAG, "onChangeEvent: " + value);
                handleChargeStatusUpdate(((Integer) getValue(value)).intValue());
                return;
            case 557847082:
                LogUtils.i(TAG, "onChangeEvent: " + value, false);
                handleDriveModeUpdate(((Integer) getValue(value)).intValue());
                return;
            case 557847085:
                LogUtils.i(TAG, "onChangeEvent: " + value, false);
                handleSnowModeUpdate(((Integer) getValue(value)).intValue());
                return;
            case 557847101:
                LogUtils.i(TAG, "onChangeEvent: " + value, false);
                handleNGearWarningSwChange(((Integer) getValue(value)).intValue());
                return;
            case 557847118:
                LogUtils.i(TAG, "onChangeEvent: " + value, false);
                handleCruiseStateChange(((Integer) getValue(value)).intValue());
                return;
            case 557847134:
                handlePowerResponseModeUpdate(((Integer) getValue(value)).intValue());
                return;
            case 557847136:
                handleXPedalModeSwitchUpdate(((Integer) getValue(value)).intValue());
                return;
            case 557847137:
                handleExhibitionModeChange(((Integer) getValue(value)).intValue());
                return;
            case 557847142:
                handleTrailerModeSwitchUpdate(((Integer) getValue(value)).intValue());
                return;
            case 557847164:
                handleSsaSwUpdate(((Integer) getValue(value)).intValue());
                return;
            case 557847179:
                handleXSportDriveModeUpdate(((Integer) getValue(value)).intValue());
                return;
            case 557847193:
                handleAutoEasyLoadMode(((Integer) getValue(value)).intValue());
                return;
            case 557847194:
                handleAwdSettingChange(((Integer) getValue(value)).intValue());
                return;
            case 557847196:
                handleRwsChanged(((Integer) getValue(value)).intValue());
                return;
            case 557847197:
                handleZwalkChanged(((Integer) getValue(value)).intValue());
                return;
            case 557847198:
                handleVMCSystemChanged(((Integer) getValue(value)).intValue());
                return;
            case 559944229:
                handleRawCarSpeed(((Float) getValue(value)).floatValue());
                return;
            case 559944314:
                handleAvailableMileageUpdate(2, ((Float) getValue(value)).floatValue());
                return;
            case 559944315:
                handleAvailableMileageUpdate(1, ((Float) getValue(value)).floatValue());
                return;
            case 559944326:
                handleAvailableMileageUpdate(0, ((Float) getValue(value)).floatValue());
                return;
            default:
                LogUtils.w(TAG, "handle unknown event: " + value);
                return;
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController
    public void setDriveMode(int driveMode) {
        setDriveMode(driveMode, true);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController
    public void setDriveMode(int driveMode, boolean storeEnable) {
        if (storeEnable) {
            try {
                if (this.mIsMainProcess) {
                    if (!CarBaseConfig.getInstance().isSupportDriveModeNewArch()) {
                        boolean z = true;
                        if (driveMode == 10 || driveMode == 0 || driveMode == 1 || driveMode == 2) {
                            this.mDataSync.setDriveMode(driveMode);
                        }
                        this.mDataSync.setXpedal(driveMode == 5);
                        DataSyncModel dataSyncModel = this.mDataSync;
                        if (driveMode != 7) {
                            z = false;
                        }
                        dataSyncModel.setAntiSickness(z);
                    } else {
                        this.mDataSync.setDriveMode(driveMode);
                    }
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "setDriveMode: " + e.getMessage(), false);
                return;
            }
        }
        this.mCarManager.setDrivingMode(driveMode);
        LogUtils.i(TAG, "setDriveMode: " + driveMode, false);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController
    public void setDriveModeSp(final int driveMode) {
        LogUtils.d(TAG, "setDriveModeSp, driveMode: " + driveMode, false);
        if (this.mIsMainProcess) {
            this.mDataSync.setDriveMode(driveMode);
            if (!CarBaseConfig.getInstance().isSupportDriveModeNewArch()) {
                this.mDataSync.setXpedal(false);
                this.mDataSync.setAntiSickness(false);
            }
            ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$VcuController$_7HW_TlJRq91AIT5Z2mIh56NXJg
                @Override // java.lang.Runnable
                public final void run() {
                    VcuController.this.lambda$setDriveModeSp$2$VcuController(driveMode);
                }
            });
        }
    }

    public /* synthetic */ void lambda$setDriveModeSp$2$VcuController(final int driveMode) {
        CarControl.PrivateControl.putInt(App.getInstance().getContentResolver(), CarControl.PrivateControl.NEW_DRIVE_MODE_VALUE, driveMode);
        handleDriveModeUpdate(driveMode);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController
    public int getDriveMode() {
        try {
            try {
                return getIntProperty(557847082);
            } catch (Exception e) {
                LogUtils.e(TAG, "getDriveMode: " + e.getMessage(), false);
                return GlobalConstant.DEFAULT.DRIVER_MODE;
            }
        } catch (Exception unused) {
            return this.mCarManager.getDrivingMode();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController
    public int getDriveModeByUser() {
        return this.mDataSync.getDriveMode();
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController
    public void controlExhibitionMode(boolean enter) {
        if (this.mCarConfig.isSupportVcuExhibitionMode()) {
            LogUtils.d(TAG, "controlExhibitionMode enter = " + enter, false);
            if (enter) {
                setDriveMode(IVcuController.DRIVE_MODE_SHOW_MODE);
            } else {
                setDriveMode(this.mDataSync.getDriveMode());
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController
    public boolean isExhibitionMode() {
        if (this.mCarConfig.isSupportVcuExhibitionMode()) {
            return ShowCarControl.getInstance().isShowCarDriveDisable();
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController
    public void setAutoDriveMode(boolean enable) {
        if (this.mIsMainProcess) {
            this.mDataSync.setAutoDriveMode(enable);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController
    public boolean isAutoDriveModeEnabled() {
        if (this.mIsMainProcess) {
            return this.mDataSync.getAutoDriveMode();
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController
    public void setEnergyRecycleGrade(int grade) {
        setEnergyRecycleGrade(grade, true);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController
    public void setEnergyRecycleGrade(int grade, boolean needSave) {
        try {
            this.mCarManager.setEnergyRecycleLevel(grade);
            if (needSave && this.mIsMainProcess) {
                this.mDataSync.setRecycleGrade(grade);
                if (CarBaseConfig.getInstance().isSupportDriveModeNewArch()) {
                    return;
                }
                this.mDataSync.setXpedal(false);
                this.mDataSync.setAntiSickness(false);
            }
        } catch (Exception e) {
            LogUtils.e(TAG, "setEnergyRecycleGrade: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController
    public int getEnergyRecycleGrade() {
        try {
            try {
                return getIntProperty(557847041);
            } catch (Exception e) {
                LogUtils.e(TAG, "getEnergyRecycleGrade: " + e.getMessage(), false);
                return getDefaultEnergyRecycleGrade();
            }
        } catch (Exception unused) {
            return this.mCarManager.getEnergyRecycleLevel();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController
    public int getEnergyRecycleGradeByUser() {
        if (this.mIsMainProcess) {
            return this.mDataSync.getRecycleGrade();
        }
        return getDefaultEnergyRecycleGrade();
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController
    public int getGearLevel() {
        try {
            return this.mCarManager.getDisplayGearLevel();
        } catch (Exception e) {
            LogUtils.e(TAG, "getGearLevel: " + e.getMessage(), false);
            return 0;
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController
    public int getElecPercent() {
        try {
            try {
                return getIntProperty(557847042);
            } catch (Exception e) {
                LogUtils.e(TAG, "getElecPercent: " + e.getMessage(), false);
                return 255;
            }
        } catch (Exception unused) {
            return this.mCarManager.getElectricityPercent();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController
    public int getAvailableMileage() {
        return getAvailableMileage(true);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController
    public int getAvailableMileage(boolean byMileageMode) {
        float wltpAvailableDrivingDistanceFloat;
        int mileageMode = getMileageMode();
        boolean z = true;
        float f = -1.0f;
        if (!byMileageMode || mileageMode == 0) {
            try {
                try {
                    f = getFloatProperty(559944326);
                } catch (Exception e) {
                    LogUtils.e(TAG, "getAvailableMileage: " + e.getMessage(), false);
                }
            } catch (Exception unused) {
                f = this.mCarManager.getNedcAvalibleDrivingDistanceFloat();
            }
        } else if (mileageMode != 1) {
            if (mileageMode == 2) {
                try {
                    try {
                        f = getFloatProperty(559944314);
                    } catch (Exception unused2) {
                        wltpAvailableDrivingDistanceFloat = this.mCarManager.getCltcAvailableDrivingDistanceFloat();
                        f = wltpAvailableDrivingDistanceFloat;
                        z = false;
                        LogUtils.i(TAG, "getAvailableMileage, mileageMode: " + mileageMode + ", value: " + f + "inSignalCache: " + z, false);
                        return (int) f;
                    }
                } catch (Exception e2) {
                    LogUtils.e(TAG, "getAvailableMileage: " + e2.getMessage(), false);
                }
            }
            z = false;
        } else {
            try {
                try {
                    f = getFloatProperty(559944315);
                } catch (Exception e3) {
                    LogUtils.e(TAG, "getAvailableMileage: " + e3.getMessage(), false);
                }
            } catch (Exception unused3) {
                wltpAvailableDrivingDistanceFloat = this.mCarManager.getWltpAvailableDrivingDistanceFloat();
                f = wltpAvailableDrivingDistanceFloat;
                z = false;
                LogUtils.i(TAG, "getAvailableMileage, mileageMode: " + mileageMode + ", value: " + f + "inSignalCache: " + z, false);
                return (int) f;
            }
        }
        LogUtils.i(TAG, "getAvailableMileage, mileageMode: " + mileageMode + ", value: " + f + "inSignalCache: " + z, false);
        return (int) f;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController
    public int getMileageMode() {
        try {
            return this.mCarManager.getEnduranceMileageMode();
        } catch (Exception e) {
            LogUtils.e(TAG, "getMileageMode failed: " + e.getMessage(), false);
            return 0;
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController
    public float getConsumptionPer100Km() {
        int mileageMode = getMileageMode();
        if (mileageMode != 1) {
            return mileageMode != 2 ? 11.8f : 11.4f;
        }
        return 13.7f;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController
    public int getChargeStatus() {
        try {
            try {
                return getIntProperty(557847081);
            } catch (Exception e) {
                LogUtils.e(TAG, "getChargeStatus: " + e.getMessage(), false);
                return 0;
            }
        } catch (Exception unused) {
            return this.mCarManager.getChargeStatus();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController
    public int getChargeGunStatus() {
        try {
            try {
                return getIntProperty(557847080);
            } catch (Exception e) {
                LogUtils.e(TAG, "getChargeGunStatus: " + e.getMessage(), false);
                return 0;
            }
        } catch (Exception unused) {
            return this.mCarManager.getChargingGunStatus();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController
    public boolean getSnowMode() {
        if (this.mCarConfig.isSupportSnowMode()) {
            try {
                try {
                    if (getIntProperty(557847085) != 1) {
                        return false;
                    }
                } catch (Exception unused) {
                    if (this.mCarManager.getVcuSnowMode() != 1) {
                        return false;
                    }
                }
                return true;
            } catch (Exception e) {
                LogUtils.e(TAG, "getSnowMode: " + e.getMessage(), false);
                return false;
            }
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController
    public void setSnowMode(boolean enable) {
        if (this.mCarConfig.isSupportSnowMode()) {
            try {
                this.mCarManager.setVcuSnowMode(enable ? 1 : 0);
            } catch (Exception e) {
                LogUtils.e(TAG, "setSnowMode: " + e.getMessage(), false);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:?, code lost:
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:4:0x000a, code lost:
        if (getIntProperty(557847056) == 2) goto L6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:6:0x000d, code lost:
        r1 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x0018, code lost:
        if (r4.mCarManager.getEvSysReady() != 2) goto L5;
     */
    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean isEvSysReady() {
        /*
            r4 = this;
            r0 = 557847056(0x21401210, float:6.5076036E-19)
            r1 = 1
            r2 = 2
            r3 = 0
            int r0 = r4.getIntProperty(r0)     // Catch: java.lang.Exception -> L10
            if (r0 != r2) goto Ld
            goto Le
        Ld:
            r1 = r3
        Le:
            r3 = r1
            goto L38
        L10:
            C extends android.car.hardware.CarEcuManager r0 = r4.mCarManager     // Catch: java.lang.Exception -> L1b
            android.car.hardware.vcu.CarVcuManager r0 = (android.car.hardware.vcu.CarVcuManager) r0     // Catch: java.lang.Exception -> L1b
            int r0 = r0.getEvSysReady()     // Catch: java.lang.Exception -> L1b
            if (r0 != r2) goto Ld
            goto Le
        L1b:
            r0 = move-exception
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "isEvSysReady: "
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r0 = r0.getMessage()
            java.lang.StringBuilder r0 = r1.append(r0)
            java.lang.String r0 = r0.toString()
            java.lang.String r1 = "VcuController"
            com.xiaopeng.carcontrol.util.LogUtils.e(r1, r0, r3)
        L38:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.carcontrol.carmanager.impl.VcuController.isEvSysReady():boolean");
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController
    public boolean isEvHighVolReady() {
        int i;
        try {
            try {
                i = getIntProperty(557847056);
            } catch (Exception e) {
                LogUtils.e(TAG, "isEvHighVolReady: " + e.getMessage(), false);
                i = 0;
            }
        } catch (Exception unused) {
            i = this.mCarManager.getEvSysReady();
        }
        return i == 1 || i == 2;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController
    public float getCarSpeed() {
        try {
            return this.mCarManager.getRawCarSpeed();
        } catch (Exception e) {
            LogUtils.e(TAG, "getCarSpeed: " + e.getMessage(), false);
            return 0.0f;
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController
    public void setNGearWarningSwitch(boolean enable) {
        if (this.mCarConfig.isSupportNeutralGearProtect()) {
            try {
                this.mCarManager.setNGearWarningSwitch(enable ? 2 : 1);
                if (this.mIsMainProcess) {
                    this.mDataSync.setNGearWarningSwitch(enable);
                } else {
                    CarControl.PrivateControl.putBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.N_PROTECT, enable);
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "setNGearWarningSwitch: " + e.getMessage(), false);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x001f, code lost:
        if (r5.mCarManager.getNGearWarningSwitchStatus() != 2) goto L8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:?, code lost:
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:6:0x0012, code lost:
        if (getIntProperty(557847101) == 2) goto L7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x0014, code lost:
        r1 = true;
     */
    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean getNGearWarningSwitchStatus() {
        /*
            r5 = this;
            com.xiaopeng.carcontrol.config.CarBaseConfig r0 = r5.mCarConfig
            boolean r0 = r0.isSupportNeutralGearProtect()
            r1 = 0
            if (r0 == 0) goto L40
            r0 = 557847101(0x2140123d, float:6.507627E-19)
            r2 = 2
            r3 = 1
            int r0 = r5.getIntProperty(r0)     // Catch: java.lang.Exception -> L17
            if (r0 != r2) goto L15
        L14:
            r1 = r3
        L15:
            r3 = r1
            goto L3f
        L17:
            C extends android.car.hardware.CarEcuManager r0 = r5.mCarManager     // Catch: java.lang.Exception -> L22
            android.car.hardware.vcu.CarVcuManager r0 = (android.car.hardware.vcu.CarVcuManager) r0     // Catch: java.lang.Exception -> L22
            int r0 = r0.getNGearWarningSwitchStatus()     // Catch: java.lang.Exception -> L22
            if (r0 != r2) goto L15
            goto L14
        L22:
            r0 = move-exception
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r4 = "getNGearWarningSwitchStatus: "
            java.lang.StringBuilder r2 = r2.append(r4)
            java.lang.String r0 = r0.getMessage()
            java.lang.StringBuilder r0 = r2.append(r0)
            java.lang.String r0 = r0.toString()
            java.lang.String r2 = "VcuController"
            com.xiaopeng.carcontrol.util.LogUtils.e(r2, r0, r1)
        L3f:
            return r3
        L40:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.carcontrol.carmanager.impl.VcuController.getNGearWarningSwitchStatus():boolean");
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController
    public int getAccPedalStatus() {
        if (this.mCarConfig.isSupportEpbWarning()) {
            try {
                try {
                    return getIntProperty(557847064);
                } catch (Exception unused) {
                    return this.mCarManager.getAccPedalStatus();
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "getAccPedalStatus: " + e.getMessage(), false);
                return 0;
            }
        }
        return 0;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController
    public boolean isCruiseActive() {
        int i;
        try {
            try {
                i = getIntProperty(557847118);
            } catch (Exception e) {
                LogUtils.e(TAG, "isCruiseActive: " + e.getMessage(), false);
                i = 3;
            }
        } catch (Exception unused) {
            i = this.mCarManager.getCruiseControlStatus();
        }
        return i == 1 || i == 2;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController
    public void saveCurrentUserDefineDriveModeInfo(final String info) {
        if (CarBaseConfig.getInstance().isSupportDriveModeNewArch()) {
            if (this.mIsMainProcess) {
                this.mDataSync.setUserDefineDriveModeInfo(info);
                ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$VcuController$Dhc4DoIQsraj4lsglU11YPfsO9I
                    @Override // java.lang.Runnable
                    public final void run() {
                        CarControl.PrivateControl.putString(App.getInstance().getContentResolver(), CarControl.PrivateControl.USER_DEFINE_DRIVE_MODE_INFO, info);
                    }
                });
                return;
            }
            this.mCurrentUdDriveSubItemInfo = info;
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController
    public String getCurrentUserDefineDriveModeInfo() {
        if (CarBaseConfig.getInstance().isSupportDriveModeNewArch()) {
            if (this.mIsMainProcess) {
                return this.mDataSync.getUserDefineDriveModeInfo();
            }
            return (String) getContentProviderValue(CarControl.PrivateControl.USER_DEFINE_DRIVE_MODE_INFO, this.mCurrentUdDriveSubItemInfo, GlobalConstant.DEFAULT.USER_DEFINE_DRIVE_MODE_CFG);
        }
        return null;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController
    public String getDriveModeSubItemInfo(int mode) {
        if (CarBaseConfig.getInstance().isSupportDriveModeNewArch()) {
            if (mode != 1) {
                if (mode != 2) {
                    if (mode != 7) {
                        if (mode != 14) {
                            if (mode != 16) {
                                if (mode != 10) {
                                    if (mode != 11) {
                                        return null;
                                    }
                                    return getCurrentUserDefineDriveModeInfo();
                                }
                                return GlobalConstant.DEFAULT.NORMAL_DRIVE_MODE_NORMAL_CFG;
                            }
                            return GlobalConstant.DEFAULT.NORMAL_DRIVE_MODE_MUD_CFG;
                        }
                        return GlobalConstant.DEFAULT.NORMAL_DRIVE_MODE_ADAPTIVE_CFG;
                    }
                    return GlobalConstant.DEFAULT.NORMAL_DRIVE_MODE_COMFORT_CFG;
                }
                return GlobalConstant.DEFAULT.NORMAL_DRIVE_MODE_SPORT_CFG;
            }
            return GlobalConstant.DEFAULT.NORMAL_DRIVE_MODE_ECO_CFG;
        }
        return null;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController
    public int getXSportDrivingMode() {
        if (CarBaseConfig.getInstance().isSupportXSport()) {
            try {
                try {
                    return getIntProperty(557847179);
                } catch (Exception unused) {
                    return this.mCarManager.getXsportMode();
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "getXSportDrivingMode: " + e.getMessage(), false);
                return 0;
            }
        }
        return 0;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController
    public void setXSportDrivingMode(final int mode) {
        if (CarBaseConfig.getInstance().isSupportXSport()) {
            ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$VcuController$9C2EXcVshYOtiIA6TS1_YA8FJsg
                @Override // java.lang.Runnable
                public final void run() {
                    VcuController.this.lambda$setXSportDrivingMode$4$VcuController(mode);
                }
            });
        }
    }

    public /* synthetic */ void lambda$setXSportDrivingMode$4$VcuController(final int mode) {
        LogUtils.i(TAG, "setXSportDrivingMode, mode: " + mode, false);
        Uri.Builder builder = new Uri.Builder();
        if (!BaseFeatureOption.getInstance().isSupportXSportApp()) {
            builder.authority("com.xiaopeng.atlantis.aipower.Server").path("enterXSportDrvMode").appendQueryParameter("drvMode", String.valueOf(mode));
            if (this.mIsMainProcess && mode == 3) {
                this.mDataSync.setFirstEnterXpowerFlag(false);
            }
        } else {
            builder.authority("com.xiaopeng.xsport.XSportService").path("enterXSportDriveMode").appendQueryParameter("driveMode", String.valueOf(mode));
        }
        try {
            LogUtils.i(TAG, "setXSportDrivingMode, result: " + ((Integer) ApiRouter.route(builder.build())).intValue(), false);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(TAG, "setXSportDrivingMode() error.");
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController
    public void enterXSportDriveModeByType(final int mode, final int type) {
        if (CarBaseConfig.getInstance().isSupportXSport()) {
            ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$VcuController$LiLd2a_CtjdhdnuP7K2BdM-aoGQ
                @Override // java.lang.Runnable
                public final void run() {
                    VcuController.lambda$enterXSportDriveModeByType$5(mode, type);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$enterXSportDriveModeByType$5(final int mode, final int type) {
        LogUtils.i(TAG, "enterXSportDriveModeByType, mode: " + mode, false);
        Uri.Builder builder = new Uri.Builder();
        builder.authority("com.xiaopeng.xsport.XSportService").path("enterXSportDriveModeByType").appendQueryParameter("driveMode", String.valueOf(mode)).appendQueryParameter(VuiConstants.ELEMENT_TYPE, String.valueOf(type));
        try {
            LogUtils.i(TAG, "enterXSportDriveModeByType, result: " + ((Integer) ApiRouter.route(builder.build())).intValue(), false);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(TAG, "enterXSportDriveModeByType() error.");
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController
    public boolean getFirstEnterXpowerFlag() {
        if (this.mIsMainProcess) {
            return this.mDataSync.getFirstEnterXpowerFlag();
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController
    public void exitXSportDriveMode() {
        if (CarBaseConfig.getInstance().isSupportXSport()) {
            ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$VcuController$VZTvpXyHvOdzXAPoGrzqXNGn1gQ
                @Override // java.lang.Runnable
                public final void run() {
                    VcuController.lambda$exitXSportDriveMode$6();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$exitXSportDriveMode$6() {
        LogUtils.i(TAG, "exitXSportDriveMode", false);
        Uri.Builder builder = new Uri.Builder();
        builder.authority("com.xiaopeng.atlantis.aipower.Server").path("exitXSportDrvMode");
        try {
            LogUtils.i(TAG, "exitXSportDriveMode, result: " + ((Integer) ApiRouter.route(builder.build())).intValue(), false);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(TAG, "exitXSportDriveMode error.");
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController
    public int onModesDrivingXSport(int mode) {
        int i = 0;
        LogUtils.i(TAG, "onModesDrivingXsport, mode: " + mode, false);
        if (!CarBaseConfig.getInstance().isSupportXSport()) {
            return 0;
        }
        Uri.Builder builder = new Uri.Builder();
        builder.authority("com.xiaopeng.xsport.XSportService").path("enterXSportDriveMode").appendQueryParameter("driveMode", String.valueOf(mode));
        try {
            int intValue = ((Integer) ApiRouter.route(builder.build())).intValue();
            try {
                LogUtils.i(TAG, "onModesDrivingXsport, result: " + intValue, false);
                return intValue;
            } catch (Exception e) {
                e = e;
                i = intValue;
                e.printStackTrace();
                LogUtils.e(TAG, "onModesDrivingXsport() error.");
                return i;
            }
        } catch (Exception e2) {
            e = e2;
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController
    public void setPowerResponseMode(int mode) {
        if (CarBaseConfig.getInstance().isSupportDriveModeNewArch()) {
            try {
                this.mCarManager.setPowerResponseMode(mode);
                LogUtils.i(TAG, "setPowerResponseMode: " + mode, false);
            } catch (Exception e) {
                LogUtils.e(TAG, "setPowerResponseMode: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController
    public int getPowerResponseMode() {
        int i;
        if (this.mCarConfig.isSupportDriveModeNewArch()) {
            try {
                try {
                    i = getIntProperty(557847134);
                } catch (Exception unused) {
                    i = this.mCarManager.getPowerResponseMode();
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "getPowerResponseMode: " + e.getMessage(), false);
                i = 1;
            }
        } else {
            i = 0;
        }
        LogUtils.i(TAG, "getPowerResponseMode: " + i, false);
        return i;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController
    public void setMotorPowerMode(int mode) {
        if (CarBaseConfig.getInstance().isSupportMotorPower()) {
            try {
                this.mCarManager.setMotorPowerMode(mode);
                LogUtils.i(TAG, "setMotorPowerMode: " + mode, false);
            } catch (Exception e) {
                LogUtils.e(TAG, "setMotorPowerMode: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController
    public int getMotorPowerMode() {
        if (CarBaseConfig.getInstance().isSupportMotorPower()) {
            try {
                return this.mCarManager.getMotorPowerMode();
            } catch (Exception e) {
                LogUtils.e(TAG, "getMotorPowerMode: " + e.getMessage(), false);
                return 0;
            }
        }
        return 0;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController
    public void setNewDriveArchXPedalMode(boolean enable, boolean storeEnable) {
        if (this.mCarConfig.isSupportXPedalNewArch()) {
            try {
                this.mCarManager.setXpedalModeSwitchStatus(enable ? 1 : 0);
                if (storeEnable) {
                    if (this.mIsMainProcess) {
                        this.mDataSync.setNewDriveXPedalMode(enable);
                    } else {
                        CarControl.PrivateControl.putBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.NEW_DRIVE_XPEDAL_MODE, enable);
                    }
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "setXpedalModeSwitchStatus: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController
    public boolean getNewDriveArchXPedalMode() {
        if (this.mCarConfig.isSupportXPedalNewArch()) {
            try {
                try {
                    if (getIntProperty(557847136) != 1) {
                        return false;
                    }
                } catch (Exception unused) {
                    if (this.mCarManager.getXpedalModeSwitchStatus() != 1) {
                        return false;
                    }
                }
                return true;
            } catch (Exception e) {
                LogUtils.e(TAG, "getXPedalMode: " + e.getMessage(), false);
                return false;
            }
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController
    public boolean getNewDriveArchXPedalModeSp() {
        if (this.mIsMainProcess) {
            return this.mDataSync.istNewDriveXPedalModeEnable();
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController
    public boolean getCarStationaryStatus() {
        try {
            return this.mCarManager.getCarStationaryStatus() == 1;
        } catch (Exception e) {
            LogUtils.e(TAG, "getCarStationaryStatus: " + e.getMessage(), false);
            return false;
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController
    public boolean getBreakPedalStatus() {
        try {
            return this.mCarManager.getBreakPedalStatus() == 1;
        } catch (Exception e) {
            LogUtils.e(TAG, "getXPedalMode: " + e.getMessage(), false);
            return false;
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController
    public void setTrailerMode(boolean enable) {
        if (CarBaseConfig.getInstance().isSupportTrailerMode()) {
            try {
                this.mCarManager.setTrailerModeSwitchStatus(enable ? 1 : 0);
            } catch (Exception e) {
                LogUtils.e(TAG, "setTrailerModeSwitchStatus: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController
    public boolean getTrailerModeStatus() {
        if (CarBaseConfig.getInstance().isSupportTrailerMode()) {
            try {
                return this.mCarManager.getTrailerModeSwitchStatus() == 1;
            } catch (Exception e) {
                LogUtils.e(TAG, "getTrailerModeSwitchStatus: " + e.getMessage(), false);
                return false;
            }
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController
    public int getAsDriveModeStatus() {
        if (CarBaseConfig.getInstance().isSupportTrailerMode()) {
            try {
                return this.mCarManager.getAsDriveModeStatus();
            } catch (Exception e) {
                LogUtils.e(TAG, "getAsDriveModeStatus: " + e.getMessage(), false);
                return 2;
            }
        }
        return -1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController
    public boolean getSsaSw() {
        int i;
        try {
            try {
                i = getIntProperty(557847164);
            } catch (Error | Exception e) {
                LogUtils.e(TAG, "getSsaSwitchStatus failed: " + e.getMessage(), false);
                i = 0;
            }
        } catch (Exception unused) {
            i = this.mCarManager.getSsaSwitchStatus();
        }
        return i == 1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController
    public void setSsaSwEnable(final boolean enable) {
        try {
            this.mCarManager.setSsaSwitchStatus(enable ? 1 : 0);
            if ((!this.mCarConfig.isSupportUnity3D() || this.mIsMainProcess) && (this.mCarConfig.isSupportUnity3D() || !this.mIsMainProcess)) {
                return;
            }
            ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$VcuController$IYUJA10L8blSg90zwMz5re3ohuo
                @Override // java.lang.Runnable
                public final void run() {
                    VcuController.this.lambda$setSsaSwEnable$7$VcuController(enable);
                }
            });
        } catch (Error | Exception e) {
            LogUtils.e(TAG, "setSsaSwitchStatus failed: " + e.getMessage(), false);
        }
    }

    public /* synthetic */ void lambda$setSsaSwEnable$7$VcuController(final boolean enable) {
        handleSsaSwUpdate(enable ? 1 : 0);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController
    public boolean getParkDropdownMenuEnable() {
        return this.mDataSync.getParkDropdownMenuEnable();
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController
    public void setParkDropdownMenuEnable(boolean enable) {
        this.mDataSync.setParkDropdownMenuEnable(enable);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController
    public boolean getBrakeLightOnOff() {
        int i;
        try {
            try {
                i = getIntProperty(557847058);
            } catch (Error | Exception e) {
                LogUtils.e(TAG, "getBrakeLightOnOff failed: " + e.getMessage(), false);
                i = 0;
            }
        } catch (Exception unused) {
            i = this.mCarManager.getBrakeLightOnOff();
        }
        return i == 1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController
    public int getCreepVehSpd() {
        if (CarBaseConfig.getInstance().isSupportXSport()) {
            try {
                float[] xPortIntellCalcCfg = this.mCarManager.getXPortIntellCalcCfg();
                if (xPortIntellCalcCfg == null || xPortIntellCalcCfg.length <= 8) {
                    return -1;
                }
                return (int) xPortIntellCalcCfg[8];
            } catch (Exception e) {
                LogUtils.w(TAG, "getXPortIntellCalcCfg failed: " + e.getMessage(), false);
                return -1;
            }
        }
        return -1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController
    public int getAwdSetting() {
        if (CarBaseConfig.getInstance().isSupportAwdSetting()) {
            try {
                try {
                    return covertAwdMode(getIntProperty(557847194));
                } catch (Exception unused) {
                    return this.mCarManager.getAWDModeSw();
                }
            } catch (Exception e) {
                LogUtils.w(TAG, "getAwdSetting failed: " + e.getMessage(), false);
                return 2;
            }
        }
        return -1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController
    public void setAwdSetting(final int mode) {
        if (CarBaseConfig.getInstance().isSupportAwdSetting()) {
            this.mDataSync.setAwdSetting(mode);
            ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$VcuController$_873gg5rOBn1EYo0bqH7WJzSVwc
                @Override // java.lang.Runnable
                public final void run() {
                    VcuController.lambda$setAwdSetting$8(mode);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$setAwdSetting$8(final int mode) {
        LogUtils.i(TAG, "setAwdSetting, mode: " + mode, false);
        Uri.Builder builder = new Uri.Builder();
        builder.authority("com.xiaopeng.atlantis.aipower.Server").path("notifyAwdMode").appendQueryParameter("awdMode", String.valueOf(mode));
        try {
            ApiRouter.route(builder.build());
            LogUtils.i(TAG, "setAwdSetting,", false);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(TAG, "setAwdSetting() error.");
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController
    public boolean isExhibitionModeOn() {
        if (this.mCarConfig.isSupportVcuExhibitionNewVcuArch()) {
            try {
                try {
                    if (getIntProperty(557847137) != 1) {
                        return false;
                    }
                } catch (Exception unused) {
                    if (this.mCarManager.getExhibModeSwitchStatus() != 1) {
                        return false;
                    }
                }
                return true;
            } catch (Exception e) {
                LogUtils.e(TAG, "getExhibModeSwitchStatus: " + e.getMessage(), false);
                return false;
            }
        }
        return false;
    }

    private void mockVcuValue() {
        this.mCarPropertyMap.put(557847082, new CarPropertyValue<>(557847082, Integer.valueOf(GlobalConstant.DEFAULT.DRIVER_MODE)));
        this.mCarPropertyMap.put(557847041, new CarPropertyValue<>(557847041, Integer.valueOf(getDefaultEnergyRecycleGrade())));
        this.mCarPropertyMap.put(557847045, new CarPropertyValue<>(557847045, 4));
        this.mCarPropertyMap.put(557847042, new CarPropertyValue<>(557847042, 0));
        this.mCarPropertyMap.put(557847081, new CarPropertyValue<>(557847081, 0));
        this.mCarPropertyMap.put(557847080, new CarPropertyValue<>(557847080, 0));
        this.mCarPropertyMap.put(557847056, new CarPropertyValue<>(557847056, 0));
        this.mCarPropertyMap.put(559944229, new CarPropertyValue<>(559944229, Float.valueOf(0.0f)));
        mockExtVcuValue();
    }

    protected void mockExtVcuValue() {
        this.mCarPropertyMap.put(557847085, new CarPropertyValue<>(557847085, false));
        this.mCarPropertyMap.put(557847101, new CarPropertyValue<>(557847101, true));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void handleDriveModeUpdate(int mode) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IVcuController.Callback) it.next()).onDriveModeChanged(mode);
            }
        }
    }

    protected void handleRecycleGradeUpdate(int grade) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IVcuController.Callback) it.next()).onRecycleGradeChanged(grade);
            }
        }
    }

    private void handleGearUpdate(int gear) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IVcuController.Callback) it.next()).onGearChanged(gear);
            }
        }
    }

    private void handleElecPercentUpdate(int percent) {
        if (percent < 0 || percent > 100) {
            LogUtils.w(TAG, "Elec percent invalid: " + percent);
            return;
        }
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IVcuController.Callback) it.next()).onElecPercentChanged(percent);
            }
        }
    }

    private void handleAvailableMileageUpdate(int mode, float mileage) {
        if (mileage == 1023.0f || mileage == 0.0f) {
            LogUtils.w(TAG, "Mileage invalid: " + mileage, false);
        } else if (getMileageMode() != mode) {
            LogUtils.d(TAG, "Not in Current MileageMode" + mileage, false);
        } else {
            int i = (int) mileage;
            synchronized (this.mCallbackLock) {
                Iterator it = this.mCallbacks.iterator();
                while (it.hasNext()) {
                    ((IVcuController.Callback) it.next()).onAvailableMileageChanged(i);
                }
            }
        }
    }

    private void handleChargeStatusUpdate(int chargeStatus) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IVcuController.Callback) it.next()).onChargeStatusChanged(chargeStatus);
            }
        }
    }

    private void handleChargeGunStatusUpdate(int gunStatus) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IVcuController.Callback) it.next()).onChargeGunStatusChanged(gunStatus);
            }
        }
    }

    private void handleSnowModeUpdate(int state) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                IVcuController.Callback callback = (IVcuController.Callback) it.next();
                boolean z = true;
                if (state != 1) {
                    z = false;
                }
                callback.onSnowModeChanged(z);
            }
        }
    }

    private void handleEvSysReady(int state) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IVcuController.Callback) it.next()).onEvSysReady(state);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleRawCarSpeed(float value) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IVcuController.Callback) it.next()).onRawCarSpeedChanged(value);
            }
        }
    }

    private void handleNGearWarningSwChange(int value) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IVcuController.Callback) it.next()).onNGearWarningSwChanged(value == 2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleAccPedalStatusChange(int value) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IVcuController.Callback) it.next()).onAccPedalStatusChanged(value);
            }
        }
    }

    private void handleCruiseStateChange(int state) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                IVcuController.Callback callback = (IVcuController.Callback) it.next();
                boolean z = true;
                if (state != 1 && state != 2) {
                    z = false;
                }
                callback.onCruiseStateChanged(z);
            }
        }
    }

    private void handleXSportDriveModeUpdate(int mode) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IVcuController.Callback) it.next()).onXSportDriveModeChanged(mode);
            }
        }
    }

    private void handlePowerResponseModeUpdate(int mode) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IVcuController.Callback) it.next()).onPowerResponseModeChanged(mode);
            }
        }
    }

    private void handleXPedalModeSwitchUpdate(int state) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                IVcuController.Callback callback = (IVcuController.Callback) it.next();
                boolean z = true;
                if (state != 1) {
                    z = false;
                }
                callback.onXPedalModeSwitchChanged(z);
            }
        }
    }

    private void handleTrailerModeSwitchUpdate(int state) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                IVcuController.Callback callback = (IVcuController.Callback) it.next();
                boolean z = true;
                if (state != 1) {
                    z = false;
                }
                callback.onTrailerModeSwitchChanged(z);
            }
        }
    }

    private void handleSsaSwUpdate(int state) {
        boolean z = state == 1;
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IVcuController.Callback) it.next()).onSsaSwChanged(z);
            }
        }
    }

    private void handleBrakeLightChange(int status) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                IVcuController.Callback callback = (IVcuController.Callback) it.next();
                boolean z = true;
                if (status != 1) {
                    z = false;
                }
                callback.onBrakeLightChanged(z);
            }
        }
    }

    private void handleExhibitionModeChange(int status) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                IVcuController.Callback callback = (IVcuController.Callback) it.next();
                boolean z = true;
                if (status != 1) {
                    z = false;
                }
                callback.onShowModeChanged(z);
            }
        }
    }

    private void handleAwdSettingChange(int status) {
        synchronized (this.mCallbacks) {
            int covertAwdMode = covertAwdMode(status);
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IVcuController.Callback) it.next()).onAwdSettingChanged(covertAwdMode);
            }
        }
    }

    private void handleAutoEasyLoadMode(int mode) {
        synchronized (this.mCallbacks) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                IVcuController.Callback callback = (IVcuController.Callback) it.next();
                boolean z = true;
                if (mode != 1) {
                    z = false;
                }
                callback.onAutoEasyLoadModeChanged(z);
            }
        }
    }

    private void handleRwsChanged(int mode) {
        synchronized (this.mCallbacks) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                IVcuController.Callback callback = (IVcuController.Callback) it.next();
                boolean z = true;
                if (mode != 1) {
                    z = false;
                }
                callback.onRwsModeChanged(z);
            }
        }
    }

    private void handleZwalkChanged(int mode) {
        synchronized (this.mCallbacks) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                IVcuController.Callback callback = (IVcuController.Callback) it.next();
                boolean z = true;
                if (mode != 1) {
                    z = false;
                }
                callback.onZwalkModeChanged(z);
            }
        }
    }

    private void handleVMCSystemChanged(int mode) {
        synchronized (this.mCallbacks) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                IVcuController.Callback callback = (IVcuController.Callback) it.next();
                boolean z = true;
                if (mode != 1) {
                    z = false;
                }
                callback.onVMCSystemStateChanged(z);
            }
        }
    }

    protected void registerContentObserver() {
        if (this.mContentObserver == null) {
            this.mContentObserver = new ContentObserver(ThreadUtils.getHandler(0)) { // from class: com.xiaopeng.carcontrol.carmanager.impl.VcuController.3
                @Override // android.database.ContentObserver
                public void onChange(boolean selfChange, Uri uri) {
                    String lastPathSegment = uri.getLastPathSegment();
                    if (CarControl.PrivateControl.NEW_DRIVE_MODE_VALUE.equals(lastPathSegment)) {
                        int i = CarControl.PrivateControl.getInt(App.getInstance().getContentResolver(), CarControl.PrivateControl.NEW_DRIVE_MODE_VALUE, GlobalConstant.DEFAULT.DRIVER_MODE);
                        LogUtils.d(VcuController.TAG, "onChange: " + lastPathSegment + ", Pre Drive Mode: " + VcuController.this.mNewDriveMode + ", Current: " + i, false);
                        VcuController.this.mNewDriveMode = Integer.valueOf(i);
                        VcuController vcuController = VcuController.this;
                        vcuController.handleDriveModeUpdate(vcuController.mNewDriveMode.intValue());
                    } else if (CarControl.PrivateControl.USER_DEFINE_DRIVE_MODE_INFO.equals(lastPathSegment)) {
                        String string = CarControl.PrivateControl.getString(App.getInstance().getContentResolver(), CarControl.PrivateControl.USER_DEFINE_DRIVE_MODE_INFO);
                        LogUtils.d(VcuController.TAG, "onChange: " + lastPathSegment + ", Pre Info: " + string + ", Current: " + string, false);
                        VcuController.this.mCurrentUdDriveSubItemInfo = string;
                    }
                }
            };
        }
        if (CarBaseConfig.getInstance().isSupportDriveModeNewArch()) {
            App.getInstance().getContentResolver().registerContentObserver(CarControl.PrivateControl.getUriFor(CarControl.PrivateControl.NEW_DRIVE_MODE_VALUE), false, this.mContentObserver);
            App.getInstance().getContentResolver().registerContentObserver(CarControl.PrivateControl.getUriFor(CarControl.PrivateControl.USER_DEFINE_DRIVE_MODE_INFO), false, this.mContentObserver);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController
    public void setAutoEasyLoadMode(boolean enable) {
        if (this.mCarConfig.isSupportAirSuspension()) {
            try {
                this.mCarManager.setAutoEasyLoadingSwitchStatus(enable ? 1 : 0);
                if (this.mIsMainProcess) {
                    this.mDataSync.setAutoEasyLoadStatus(enable);
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "setAutoEasyLoadMode: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController
    public boolean getAutoEasyLoadMode() {
        if (this.mCarConfig.isSupportAirSuspension()) {
            try {
                try {
                    if (1 != getIntProperty(557847193)) {
                        return false;
                    }
                } catch (Exception unused) {
                    if (1 != this.mCarManager.getAutoEasyLoadingSwitchStatus()) {
                        return false;
                    }
                }
                return true;
            } catch (Exception e) {
                LogUtils.e(TAG, "getAutoEasyLoadMode: " + e.getMessage(), false);
                return false;
            }
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController
    public boolean getVMCRwsSwitchState() {
        if (CarBaseConfig.getInstance().isSupportVMCControl()) {
            try {
                try {
                    if (1 != getIntProperty(557847196)) {
                        return false;
                    }
                } catch (Exception e) {
                    LogUtils.e(TAG, "getVMCRwsSwitchState: " + e.getMessage(), false);
                    return false;
                }
            } catch (Exception unused) {
                if (1 != this.mCarManager.getVMCRwsSwitchState()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController
    public void setVMCRwsSwitch(boolean enable) {
        if (CarBaseConfig.getInstance().isSupportVMCControl()) {
            try {
                this.mCarManager.setVMCRwsSwitch(enable ? 1 : 0);
            } catch (Exception e) {
                LogUtils.e(TAG, "setVMCRwsSwitch: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController
    public boolean getVMCZWalkModeState() {
        if (CarBaseConfig.getInstance().isSupportVMCControl()) {
            try {
                try {
                    if (1 != getIntProperty(557847197)) {
                        return false;
                    }
                } catch (Exception e) {
                    LogUtils.e(TAG, "getVMCZWalkModeState: " + e.getMessage(), false);
                    return false;
                }
            } catch (Exception unused) {
                if (1 != this.mCarManager.getVMCZWalkModeState()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController
    public void setVMCZWalkModeSwitch(boolean enable) {
        if (CarBaseConfig.getInstance().isSupportVMCControl()) {
            try {
                this.mCarManager.setVMCZWalkModeSwitch(enable ? 1 : 0);
            } catch (Exception e) {
                LogUtils.e(TAG, "setVMCZWalkModeSwitch: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController
    public boolean getVMCSystemState() {
        if (CarBaseConfig.getInstance().isSupportVMCControl()) {
            try {
                try {
                    if (1 != getIntProperty(557847198)) {
                        return false;
                    }
                } catch (Exception e) {
                    LogUtils.e(TAG, "getVMCSystemState: " + e.getMessage(), false);
                    return false;
                }
            } catch (Exception unused) {
                if (1 != this.mCarManager.getVMCSystemState()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
