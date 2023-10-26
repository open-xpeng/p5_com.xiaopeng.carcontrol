package com.xiaopeng.carcontrol.carmanager.impl;

import android.car.Car;
import android.car.CarNotConnectedException;
import android.car.hardware.CarPropertyValue;
import android.car.hardware.msb.CarMsbManager;
import android.car.hardware.msm.CarMsmManager;
import android.car.hardware.srs.CarSrsManager;
import android.content.ContentResolver;
import android.database.ContentObserver;
import android.net.Uri;
import android.provider.Settings;
import android.text.TextUtils;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.GlobalConstant;
import com.xiaopeng.carcontrol.carmanager.BaseCarController;
import com.xiaopeng.carcontrol.carmanager.CarClientWrapper;
import com.xiaopeng.carcontrol.carmanager.XuiClientWrapper;
import com.xiaopeng.carcontrol.carmanager.controller.IMsmController;
import com.xiaopeng.carcontrol.carmanager.impl.oldarch.MsmOldController;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import com.xiaopeng.carcontrol.provider.CarControl;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ResUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.carcontrolmodule.R;
import com.xiaopeng.xuimanager.XUIServiceNotConnectedException;
import com.xiaopeng.xuimanager.seatmassager.Seat;
import com.xiaopeng.xuimanager.seatmassager.SeatMassagerManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public class MsmController extends BaseCarController<CarMsmManager, IMsmController.Callback> implements IMsmController {
    public static final boolean DEBUG = false;
    protected static final String TAG = "MsmController";
    private CarMsbManager mCarMsbManager;
    protected CarSrsManager mCarSrsManager;
    private ContentObserver mContentObserver;
    private final List<Integer> mMsbPropIds;
    private SeatMassagerManager mSeatMassagerManager;
    private final List<Integer> mSrsPropIds;
    private final String SEAT_MESSAGE = "seatMessageTag";
    private final CarMsmManager.CarMsmEventCallback mCarMsmEventCallback = new CarMsmManager.CarMsmEventCallback() { // from class: com.xiaopeng.carcontrol.carmanager.impl.MsmController.1
        public void onErrorEvent(int propertyId, int zone) {
        }

        public void onChangeEvent(CarPropertyValue carPropertyValue) {
            LogUtils.i(MsmController.TAG, "onMsmChangeEvent: " + carPropertyValue, false);
            MsmController.this.handleCarEventsUpdate(carPropertyValue);
        }
    };
    private final CarMsbManager.CarMsbEventCallback mCarMsbEventCallback = new CarMsbManager.CarMsbEventCallback() { // from class: com.xiaopeng.carcontrol.carmanager.impl.MsmController.2
        public void onChangeEvent(CarPropertyValue carPropertyValue) {
            LogUtils.i(MsmController.TAG, "onMsbChangeEvent: " + carPropertyValue, false);
            MsmController.this.handleCarEventsUpdate(carPropertyValue);
        }

        public void onErrorEvent(int propertyId, int zone) {
            LogUtils.e(MsmController.TAG, "onMsbErrorEvent: " + propertyId, false);
        }
    };
    private final CarSrsManager.CarSrsEventCallback mCarSrsEventCallback = new CarSrsManager.CarSrsEventCallback() { // from class: com.xiaopeng.carcontrol.carmanager.impl.MsmController.3
        public void onChangeEvent(CarPropertyValue carPropertyValue) {
            int propertyId = carPropertyValue.getPropertyId();
            if (propertyId != 557849636 && propertyId != 557849679) {
                LogUtils.i(MsmController.TAG, "onSrsChangeEvent: " + carPropertyValue, false);
            }
            MsmController.this.handleCarEventsUpdate(carPropertyValue);
        }

        public void onErrorEvent(int propertyId, int zone) {
            LogUtils.e(MsmController.TAG, "onSrsErrorEvent: " + propertyId, false);
        }
    };
    private Boolean mPsnWelcomeSw = null;
    private Boolean mEsbSw = null;

    private int getChangeValue(int direction, int value) {
        if (direction == 1) {
            value += 2;
        } else if (direction == 2) {
            value -= 2;
        }
        if (value > 100) {
            value = 100;
        }
        if (value < 0) {
            return 0;
        }
        return value;
    }

    private boolean isSeatPosValid(int pos) {
        return pos >= 0 && pos <= 100;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    public boolean dependOnXuiManager() {
        return true;
    }

    protected boolean isMsbSupport() {
        return true;
    }

    protected boolean isSrsSupport() {
        return true;
    }

    public MsmController(Car carClient) {
        ArrayList arrayList = new ArrayList();
        this.mMsbPropIds = arrayList;
        if (isMsbSupport()) {
            arrayList.add(557849635);
        }
        ArrayList arrayList2 = new ArrayList();
        this.mSrsPropIds = arrayList2;
        if (isSrsSupport()) {
            if (this.mIsMainProcess) {
                arrayList2.add(557849679);
                arrayList2.add(557849612);
                if (!this.mCarConfig.isSupportUnity3D()) {
                    arrayList2.add(557849636);
                }
            } else {
                arrayList2.add(557849636);
            }
            addSrsExtPropertyIds(arrayList2);
            if (this.mCarConfig.isSupportPsnSrsSwitch()) {
                arrayList2.add(557849819);
            }
        }
        if (this.mIsMainProcess) {
            return;
        }
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$MsmController$pKjr3urmW97m2HIrTRKifzHx2ok
            @Override // java.lang.Runnable
            public final void run() {
                MsmController.this.lambda$new$0$MsmController();
            }
        });
    }

    public /* synthetic */ void lambda$new$0$MsmController() {
        ContentResolver contentResolver = App.getInstance().getContentResolver();
        this.mPsnWelcomeSw = Boolean.valueOf(CarControl.PrivateControl.getBool(contentResolver, CarControl.PrivateControl.PSN_SEAT_WELCOME_SW, true));
        LogUtils.i(TAG, "PsnWelcomeMode init value: " + this.mPsnWelcomeSw, false);
        this.mEsbSw = Boolean.valueOf(CarControl.PrivateControl.getBool(contentResolver, CarControl.PrivateControl.ESB, true));
        registerContentObserver();
    }

    /* loaded from: classes.dex */
    public static class MsmControllerFactory {
        public static MsmController createCarController(Car carClient) {
            if (CarBaseConfig.getInstance().isNewMsmArch()) {
                return new MsmController(carClient);
            }
            return new MsmOldController(carClient);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    public void initCarManager(Car carClient) {
        CarSrsManager carSrsManager;
        CarMsbManager carMsbManager;
        LogUtils.d(TAG, "Init start");
        try {
            this.mCarManager = (CarMsmManager) carClient.getCarManager(CarClientWrapper.XP_MSM_SERVICE);
            if (isMsbSupport()) {
                this.mCarMsbManager = (CarMsbManager) carClient.getCarManager("xp_msb");
            }
            if (isSrsSupport()) {
                this.mCarSrsManager = (CarSrsManager) carClient.getCarManager("xp_srs");
            }
            if (this.mCarManager != 0) {
                this.mCarManager.registerPropCallback(this.mPropertyIds, this.mCarMsmEventCallback);
            }
            if (isMsbSupport() && (carMsbManager = this.mCarMsbManager) != null) {
                carMsbManager.registerPropCallback(this.mMsbPropIds, this.mCarMsbEventCallback);
            }
            if (isSrsSupport() && (carSrsManager = this.mCarSrsManager) != null) {
                carSrsManager.registerPropCallback(this.mSrsPropIds, this.mCarSrsEventCallback);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogUtils.d(TAG, "Init end");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    public void initXuiManager() {
        if (this.mCarConfig.isSupportSeatMassage() || this.mCarConfig.isSupportSeatRhythm()) {
            SeatMassagerManager seatMassagerManager = XuiClientWrapper.getInstance().getSeatMassagerManager();
            this.mSeatMassagerManager = seatMassagerManager;
            if (seatMassagerManager == null) {
                LogUtils.e(TAG, "SeatMassagerManager Is Null", false);
                return;
            }
            try {
                seatMassagerManager.registerListener(new SeatMassagerManager.EventListener() { // from class: com.xiaopeng.carcontrol.carmanager.impl.MsmController.4
                    public void onStartMassager(int id, String effect, int intensity) {
                        LogUtils.d("seatMessageTag", "onStartMassager: id=" + id + ", effect=" + effect + ", intensity=" + intensity);
                        MsmController.this.handleMassagerStatusChange(id, effect, intensity, true);
                    }

                    public void onStopMassager(int id, String effect, int intensity) {
                        LogUtils.d("seatMessageTag", "onStopMassager: id=" + id + ", effect=" + effect + ", intensity=" + intensity);
                        MsmController.this.handleMassagerStatusChange(id, effect, intensity, false);
                    }

                    public void onErrorMassager(int id, String effect, int opCode, int errCode) {
                        LogUtils.d("seatMessageTag", "onErrorMassager: id=" + id + ", effect=" + effect + ", opCode=" + opCode + ", errCode=" + errCode);
                        MsmController.this.handleMassageFailed(id, effect, opCode, errCode);
                    }

                    public void onChangeEffectMassager(int id, String effect) {
                        MsmController.this.handleMassagerEffectChange(id, effect);
                        LogUtils.d("seatMessageTag", "onChangeEffectMassager: id=" + id + ", effect=" + effect);
                    }

                    public void onChangeIntensityMassager(int id, int intensity) {
                        LogUtils.d("seatMessageTag", "onChangeIntensityMassager: id=" + id + ", intensity=" + intensity);
                        MsmController.this.handleMassagerIntensityChange(id, intensity);
                    }

                    public void onStartVibrate(Set<Integer> ids, String effect, int position) {
                        LogUtils.d("seatMessageTag", "onStartVibrate: id=" + Arrays.toString(ids.toArray()) + ", effect=" + effect + ", position=" + position);
                    }

                    public void onStopVibrate(Set<Integer> ids, String effect, int position) {
                        LogUtils.d("seatMessageTag", "onStopVibrate: id=" + Arrays.toString(ids.toArray()) + ", effect=" + effect + ", position=" + position);
                    }

                    public void onChangeIntensityVibrate(Set<Integer> ids, int intensity) {
                        LogUtils.d("seatMessageTag", "onChangeIntensityVibrate: id=" + Arrays.toString(ids.toArray()) + ", intensity=" + intensity);
                    }

                    public void onErrorVibrate(Set<Integer> ids, String effect, int opCode, int errCode) {
                        LogUtils.d("seatMessageTag", "onErrorVibrate: id=" + Arrays.toString(ids.toArray()) + ", effect=" + effect + ", opCode=" + opCode + ", errCode=" + errCode);
                    }

                    public void onChangeIntensityRhythm(Set<Integer> ids, int intensity) {
                        LogUtils.d("seatMessageTag", "onChangeIntensityRhythm: id=" + Arrays.toString(ids.toArray()) + ", intensity=" + intensity);
                        MsmController.this.handleRhythmIntensityChange(ids, intensity);
                    }

                    public void onChangeStatusRhythm(Set<Integer> ids, boolean enable) {
                        LogUtils.d("seatMessageTag", "onChangeStatusRhythm: id=" + Arrays.toString(ids.toArray()) + ", enable=" + enable);
                        MsmController.this.handleRhythmStatusChange(ids, enable);
                    }

                    public void onChangePatternRhythm(int mode) {
                        LogUtils.d("seatMessageTag", "onChangePatternRhythm: mode=" + mode);
                        MsmController.this.handleRhythmModeChange(mode);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    protected List<Integer> getRegisterPropertyIds() {
        ArrayList arrayList = new ArrayList();
        if (this.mIsMainProcess) {
            arrayList.add(557849686);
            arrayList.add(557849687);
            arrayList.add(557849688);
        }
        if (this.mCarConfig.isSupportMsmP() && this.mIsMainProcess) {
            arrayList.add(557849810);
            arrayList.add(557849812);
        }
        if (this.mCarConfig.isSupportVipSeat() && this.mIsMainProcess) {
            arrayList.add(557849813);
            arrayList.add(557849814);
        }
        addExtPropertyIds(arrayList);
        return arrayList;
    }

    protected void addExtPropertyIds(List<Integer> propertyIds) {
        if (this.mIsMainProcess) {
            propertyIds.add(557849689);
        }
        if (BaseFeatureOption.getInstance().isSupportPsnSeatVerControl() && this.mCarConfig.isSupportMsmP()) {
            propertyIds.add(557849811);
        }
        if (this.mCarConfig.isSupportDrvCushion()) {
            propertyIds.add(557849959);
        }
        if (this.mCarConfig.isSupportPsnLeg()) {
            propertyIds.add(557849960);
        }
        if (this.mCarConfig.isSupportPsnWelcomeMode() && ((this.mCarConfig.isSupportUnity3D() && !this.mIsMainProcess) || !this.mCarConfig.isSupportUnity3D())) {
            propertyIds.add(557847672);
        }
        if (this.mCarConfig.isSupportRearSeatCtrl()) {
            propertyIds.add(557849890);
            propertyIds.add(557849891);
            if (this.mCarConfig.isSupportRearSeatLeg()) {
                propertyIds.add(557849961);
                propertyIds.add(557849962);
            }
        }
        addSecRowPropertyIds(propertyIds);
        addTrdRowPropertyIds(propertyIds);
    }

    protected void addSecRowPropertyIds(List<Integer> propertyIds) {
        if (CarBaseConfig.getInstance().isSupportSecRowLegVer()) {
            propertyIds.add(557850032);
            propertyIds.add(557850037);
        }
        if (CarBaseConfig.getInstance().isSupportSecRowHor()) {
            propertyIds.add(557850030);
            propertyIds.add(557850035);
        }
        if (CarBaseConfig.getInstance().isSupportSecRowZeroGravity()) {
            propertyIds.add(557850021);
            propertyIds.add(557850022);
        }
        if (CarBaseConfig.getInstance().isSupportSecRowHeadRest()) {
            propertyIds.add(557850033);
            propertyIds.add(557850034);
            propertyIds.add(557850038);
            propertyIds.add(557850039);
        }
        if (CarBaseConfig.getInstance().isSupportSecRowZeroGravity()) {
            propertyIds.add(557850031);
            propertyIds.add(557850036);
        }
    }

    protected void addTrdRowPropertyIds(List<Integer> propertyIds) {
        if (CarBaseConfig.getInstance().isSupportTrdRowSeatControl()) {
            propertyIds.add(557850040);
            propertyIds.add(557850042);
        }
        if (CarBaseConfig.getInstance().isSupportTrdRowHeadRest()) {
            propertyIds.add(557850041);
            propertyIds.add(557850043);
        }
        if (CarBaseConfig.getInstance().isSupportTrdRowMidHeadRest()) {
            propertyIds.add(557850044);
        }
        if (CarBaseConfig.getInstance().isSupportTrdRowFold()) {
            propertyIds.add(557850025);
            propertyIds.add(557850026);
        }
        if (CarBaseConfig.getInstance().isSupportTrdRowStow()) {
            propertyIds.add(557850027);
        }
    }

    protected void addSrsExtPropertyIds(List<Integer> propertyIds) {
        if (this.mIsMainProcess) {
            propertyIds.add(557849613);
            if (this.mCarConfig.isSupportVipSeat()) {
                propertyIds.add(557849800);
                propertyIds.add(557849801);
                propertyIds.add(557849802);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    protected void disconnect() {
        CarSrsManager carSrsManager;
        CarMsbManager carMsbManager;
        if (this.mCarManager != 0) {
            try {
                this.mCarManager.unregisterPropCallback(this.mPropertyIds, this.mCarMsmEventCallback);
            } catch (CarNotConnectedException e) {
                LogUtils.e(TAG, (String) null, (Throwable) e, false);
            }
        }
        if (isMsbSupport() && (carMsbManager = this.mCarMsbManager) != null) {
            try {
                carMsbManager.unregisterPropCallback(this.mMsbPropIds, this.mCarMsbEventCallback);
            } catch (CarNotConnectedException e2) {
                LogUtils.e(TAG, (String) null, (Throwable) e2, false);
            }
        }
        if (!isSrsSupport() || (carSrsManager = this.mCarSrsManager) == null) {
            return;
        }
        try {
            carSrsManager.unregisterPropCallback(this.mSrsPropIds, this.mCarSrsEventCallback);
        } catch (CarNotConnectedException e3) {
            LogUtils.e(TAG, (String) null, (Throwable) e3, false);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    public void handleCarEventsUpdate(final CarPropertyValue<?> value) {
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$MsmController$ACijDHbKtQ1MYn2246AV5O6_psI
            @Override // java.lang.Runnable
            public final void run() {
                MsmController.this.lambda$handleCarEventsUpdate$1$MsmController(value);
            }
        });
    }

    public /* synthetic */ void lambda$handleCarEventsUpdate$1$MsmController(final CarPropertyValue value) {
        this.mCarPropertyMap.put(Integer.valueOf(value.getPropertyId()), value);
        handleEventsUpdate(value);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    protected void handleEventsUpdate(CarPropertyValue<?> value) {
        switch (value.getPropertyId()) {
            case 557847672:
                handlePsnSeatWelcomeModeUpdate(((Integer) getValue(value)).intValue());
                return;
            case 557849612:
                handleDriverBeltStatus(((Integer) getValue(value)).intValue());
                return;
            case 557849613:
                handlePsnBeltStatus(((Integer) getValue(value)).intValue());
                return;
            case 557849635:
                handleEsbMode(((Integer) getValue(value)).intValue());
                return;
            case 557849636:
                handleBackBeltStatus(((Integer) getValue(value)).intValue());
                return;
            case 557849679:
                handlePsnOnSeat(((Integer) getValue(value)).intValue());
                return;
            case 557849686:
                handleDSeatHorz(((Integer) getValue(value)).intValue());
                return;
            case 557849687:
                handleDSeatVer(((Integer) getValue(value)).intValue());
                return;
            case 557849688:
                handleDSeatTilt(((Integer) getValue(value)).intValue());
                return;
            case 557849689:
                handleDSeatLeg(((Integer) getValue(value)).intValue());
                return;
            case 557849800:
                handleRlOnSeat(((Integer) getValue(value)).intValue());
                return;
            case 557849801:
                handleRmOnSeat(((Integer) getValue(value)).intValue());
                return;
            case 557849802:
                handleRrOnSeat(((Integer) getValue(value)).intValue());
                return;
            case 557849810:
                handlePSeatHorz(((Integer) getValue(value)).intValue());
                return;
            case 557849811:
                handlePSeatVer(((Integer) getValue(value)).intValue());
                return;
            case 557849812:
                handlePSeatTilt(((Integer) getValue(value)).intValue());
                return;
            case 557849813:
                handleDrvHeadrestStatus(((Integer) getValue(value)).intValue());
                return;
            case 557849814:
                handlePsnHeadrestStatus(((Integer) getValue(value)).intValue());
                return;
            case 557849819:
                handlePsnSrsEnableStatus(((Integer) getValue(value)).intValue());
                return;
            case 557849890:
                handleRLSeatTilt(((Integer) getValue(value)).intValue());
                return;
            case 557849891:
                handleRRSeatTilt(((Integer) getValue(value)).intValue());
                return;
            case 557849935:
                handleDrvSeatLumberCenterPressed(((Integer) getValue(value)).intValue());
                return;
            case 557849937:
                handlePsnSeatLumberCenterPressed(((Integer) getValue(value)).intValue());
                return;
            case 557849959:
                handleDSeatCushion(((Integer) getValue(value)).intValue());
                return;
            case 557849960:
                handlePSeatLeg(((Integer) getValue(value)).intValue());
                return;
            case 557849961:
                handleRLSeatLeg(((Integer) getValue(value)).intValue());
                return;
            case 557849962:
                handleRRSeatLeg(((Integer) getValue(value)).intValue());
                return;
            case 557850021:
                handleSecRowLtEzOrZeroGStatusChanged(((Integer) getValue(value)).intValue());
                return;
            case 557850022:
                handleSecRowRtEzOrZeroGStatusChanged(((Integer) getValue(value)).intValue());
                return;
            case 557850025:
                handleTrdRowLtFoldStatusChanged(((Integer) getValue(value)).intValue());
                return;
            case 557850026:
                handleTrdRowRtFoldStatusChanged(((Integer) getValue(value)).intValue());
                return;
            case 557850027:
                handleTrdRowStowStatusChanged(((Integer) getValue(value)).intValue());
                return;
            case 557850030:
                handleSecRowLtSeatHorzPosChanged(((Integer) getValue(value)).intValue());
                return;
            case 557850031:
                handleSecRowLtSeatZeroGravPosChanged(((Integer) getValue(value)).intValue());
                return;
            case 557850032:
                handleSecRowLtSeatLeSuHeigPosChanged(((Integer) getValue(value)).intValue());
                return;
            case 557850033:
                handleSecRowLtSeatHeReHeigPosChanged(((Integer) getValue(value)).intValue());
                return;
            case 557850034:
                handleSecRowLtSeatHeReHorzPosChanged(((Integer) getValue(value)).intValue());
                return;
            case 557850035:
                handleSecRowRtSeatHorzPosChanged(((Integer) getValue(value)).intValue());
                return;
            case 557850036:
                handleSecRowRtSeatZeroGravPosChanged(((Integer) getValue(value)).intValue());
                return;
            case 557850037:
                handleSecRowRtSeatLeSuHeigPosChanged(((Integer) getValue(value)).intValue());
                return;
            case 557850038:
                handleSecRowRtSeatHeReHeigPosChanged(((Integer) getValue(value)).intValue());
                return;
            case 557850039:
                handleSecRowRtSeatHeReHorzPosChanged(((Integer) getValue(value)).intValue());
                return;
            case 557850040:
                handleTrdRowLtSeatTiltPosChanged(((Integer) getValue(value)).intValue());
                return;
            case 557850041:
                handleTrdRowLtSeatHeadVerticalPosChanged(((Integer) getValue(value)).intValue());
                return;
            case 557850042:
                handleTrdRowRtSeatTiltPosChanged(((Integer) getValue(value)).intValue());
                return;
            case 557850043:
                handleTrdRowRtSeatHeadVerticalPosChanged(((Integer) getValue(value)).intValue());
                return;
            case 557850044:
                handleTrdRowMidSeatHeadVerticalPosChanged(((Integer) getValue(value)).intValue());
                return;
            default:
                LogUtils.w(TAG, "handle unknown event: " + value);
                return;
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public boolean getPsnSrsEnable() {
        if (this.mCarConfig.isSupportPsnSrsSwitch()) {
            try {
                try {
                    if (getIntProperty(557849819) == 1) {
                        return true;
                    }
                } catch (Exception unused) {
                    if (this.mCarSrsManager.getPassengerCrashOccurSwSt() == 1) {
                        return true;
                    }
                }
                return false;
            } catch (Exception e) {
                LogUtils.e(TAG, "getPassengerCrashOccurSwSt: " + e.getMessage(), false);
                return true;
            }
        }
        return true;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setPsnSrsEnable(boolean enable) {
        if (this.mCarConfig.isSupportPsnSrsSwitch()) {
            try {
                this.mCarSrsManager.setPassengerCrashOccurSw(enable ? 1 : 0);
                if (this.mIsMainProcess) {
                    this.mDataSync.setPsnSrsEnable(enable);
                } else {
                    CarControl.PrivateControl.putBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.PSN_SRS_ENABLE, enable);
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "setPassengerCrashOccurSw: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public int getDriverSeatLumberSwitchMode() {
        try {
            return this.mCarManager.getDriverSeatLumberSwitchMode();
        } catch (Exception e) {
            LogUtils.e(TAG, "getDriverSeatLumberSwitchMode: " + e.getMessage(), false);
            return 0;
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public int getPassengerSeatLumberSwitchMode() {
        try {
            return this.mCarManager.getPassengerSeatLumberSwitchMode();
        } catch (Exception e) {
            LogUtils.e(TAG, "getPassengerSeatLumberSwitchMode: " + e.getMessage(), false);
            return 0;
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public boolean isDrvTiltMovingSafe() {
        return !this.mCarConfig.isSupportVipSeat() || getDSeatTiltPos() >= BaseFeatureOption.getInstance().getSeatTiltMovingSafePos();
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public boolean isPsnTiltMovingSafe() {
        return !this.mCarConfig.isSupportVipSeat() || getPSeatTiltPos() >= BaseFeatureOption.getInstance().getSeatTiltMovingSafePos();
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public boolean getPsnSeatWelcomeMode() {
        if (this.mCarConfig.isSupportPsnWelcomeMode()) {
            try {
                try {
                    if (getIntProperty(557847672) != 1) {
                        return false;
                    }
                } catch (Error | Exception e) {
                    LogUtils.e(TAG, "getPassengerWelcomeSwitch failed: " + e.getMessage(), false);
                }
            } catch (Exception unused) {
                if (this.mCarManager.getPassengerWelcomeSwitch() != 1) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setPsnSeatWelcomeMode(boolean enable) {
        if (this.mCarConfig.isSupportPsnWelcomeMode()) {
            try {
                this.mCarManager.setPassengerWelcomeSwitch(enable ? 1 : 0);
                if (this.mIsMainProcess) {
                    this.mDataSync.setPsnWelcomeMode(enable);
                } else {
                    this.mPsnWelcomeSw = Boolean.valueOf(enable);
                    CarControl.PrivateControl.putBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.PSN_SEAT_WELCOME_SW, enable);
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "setPsnSeatWelcomeMode failed: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setDrvWelcomeModeActiveSt(boolean active) {
        try {
            this.mCarManager.setDriverWelcomeActive(active ? 1 : 0);
        } catch (Exception e) {
            LogUtils.e(TAG, "setDriverWelcomeActive failed: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setPsnWelcomeModeActiveSt(boolean active) {
        try {
            this.mCarManager.setPassengerWelcomeActive(active ? 1 : 0);
        } catch (Exception e) {
            LogUtils.e(TAG, "setPassengerWelcomeActive failed: " + e.getMessage(), false);
        }
    }

    private /* synthetic */ void lambda$setMassagerIntensity$2(final int strength, Integer index) {
        handleMassagerIntensityChange(index.intValue(), strength);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setMassagerIntensity(Set<Integer> ids, int strength) {
        SeatMassagerManager seatMassagerManager = this.mSeatMassagerManager;
        if (seatMassagerManager != null) {
            try {
                LogUtils.d(TAG, "setMassagerIntensity=" + Arrays.toString(ids.toArray()) + ", " + strength + ", " + seatMassagerManager.setMassagerIntensity(ids, strength), false);
            } catch (Throwable th) {
                LogUtils.e(TAG, "setMassagerIntensity failed: " + th.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public int getMassagerIntensity(int index) {
        SeatMassagerManager seatMassagerManager = this.mSeatMassagerManager;
        if (seatMassagerManager != null) {
            try {
                int massagerIntensity = seatMassagerManager.getMassagerIntensity(index);
                LogUtils.d(TAG, "getMassagerIntensity=" + index + ", " + massagerIntensity, false);
                return massagerIntensity;
            } catch (Throwable th) {
                LogUtils.e(TAG, "getMassagerIntensity failed: " + th.getMessage(), false);
                return 2;
            }
        }
        return 2;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setVibrateIntensity(int position, int intensity) {
        SeatMassagerManager seatMassagerManager = this.mSeatMassagerManager;
        if (seatMassagerManager != null) {
            try {
                LogUtils.d(TAG, "setVibrateIntensity=" + position + ", " + intensity + ", " + seatMassagerManager.setVibrateIntensity(position, intensity), false);
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public int getVibrateIntensity(int position) {
        SeatMassagerManager seatMassagerManager = this.mSeatMassagerManager;
        if (seatMassagerManager != null) {
            try {
                int vibrateIntensity = seatMassagerManager.getVibrateIntensity(position);
                LogUtils.d(TAG, "getVibrateIntensity=" + vibrateIntensity, false);
                return vibrateIntensity;
            } catch (Throwable th) {
                th.printStackTrace();
                return 10;
            }
        }
        return 10;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setRhythmIntensity(Set<Integer> ids, int intensity) {
        SeatMassagerManager seatMassagerManager = this.mSeatMassagerManager;
        if (seatMassagerManager != null) {
            try {
                LogUtils.d(TAG, "setRhythmIntensity=" + Arrays.toString(ids.toArray()) + ", " + intensity + ", " + seatMassagerManager.setRhythmIntensity(ids, intensity), false);
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public int getRhythmIntensity(int position) {
        SeatMassagerManager seatMassagerManager = this.mSeatMassagerManager;
        if (seatMassagerManager != null) {
            try {
                int rhythmIntensity = seatMassagerManager.getRhythmIntensity(position);
                LogUtils.d(TAG, "getRhythmIntensity=" + rhythmIntensity, false);
                return rhythmIntensity;
            } catch (Throwable th) {
                th.printStackTrace();
                return 3;
            }
        }
        return 3;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setRhythmEnable(Set<Integer> ids, boolean enable) {
        SeatMassagerManager seatMassagerManager = this.mSeatMassagerManager;
        if (seatMassagerManager != null) {
            try {
                LogUtils.d(TAG, "setRhythmEnable=" + Arrays.toString(ids.toArray()) + ", " + enable + ", " + seatMassagerManager.setRhythmEnable(ids, enable), false);
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public boolean isRhythmRunning(int position) {
        SeatMassagerManager seatMassagerManager = this.mSeatMassagerManager;
        if (seatMassagerManager != null) {
            try {
                boolean z = true;
                if (seatMassagerManager.getRhythmEnable(position) != 1) {
                    z = false;
                }
                LogUtils.d(TAG, "isRhythmRunning=" + position + ", " + z, false);
                return z;
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void startMassager(int id, int intensity, String effectId) {
        Seat seat = new Seat(id, intensity);
        try {
            SeatMassagerManager seatMassagerManager = this.mSeatMassagerManager;
            if (seatMassagerManager != null) {
                LogUtils.d(TAG, "startMassager=" + id + ", " + effectId + ", " + seatMassagerManager.startMassager(seat, effectId), false);
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void startMassager(Set<Integer> ids, String effectId) {
        final HashSet hashSet = new HashSet();
        ids.forEach(new Consumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$MsmController$SrYOJyRgpSRbgDO0uH05C1mjeX0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                MsmController.this.lambda$startMassager$4$MsmController(hashSet, (Integer) obj);
            }
        });
        try {
            SeatMassagerManager seatMassagerManager = this.mSeatMassagerManager;
            if (seatMassagerManager != null) {
                LogUtils.d(TAG, "startMassager=" + Arrays.toString(ids.toArray()) + ", " + effectId + ", " + seatMassagerManager.startMassager(hashSet, effectId), false);
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    private /* synthetic */ void lambda$startMassager$3(final Set seats, final String effectId, Integer index) {
        int massagerIntensity = getMassagerIntensity(index.intValue());
        seats.add(new Seat(index.intValue(), massagerIntensity));
        handleMassagerStatusChange(index.intValue(), effectId, massagerIntensity, true);
    }

    public /* synthetic */ void lambda$startMassager$4$MsmController(final Set seats, Integer index) {
        seats.add(new Seat(index.intValue(), getMassagerIntensity(index.intValue())));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void startMassager(Set<Integer> ids, final int intensity, String effectId) {
        final HashSet hashSet = new HashSet();
        ids.forEach(new Consumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$MsmController$rqipTT6lFXIqsE26j-W4Mq2MsVE
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                hashSet.add(new Seat(((Integer) obj).intValue(), intensity));
            }
        });
        try {
            SeatMassagerManager seatMassagerManager = this.mSeatMassagerManager;
            if (seatMassagerManager != null) {
                LogUtils.d(TAG, "startMassager=" + Arrays.toString(ids.toArray()) + ", " + effectId + ", " + seatMassagerManager.startMassager(hashSet, effectId), false);
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    private /* synthetic */ void lambda$startMassager$5(final Set seats, final int intensity, final String effectId, Integer index) {
        seats.add(new Seat(index.intValue(), intensity));
        handleMassagerStatusChange(index.intValue(), effectId, intensity, true);
    }

    private /* synthetic */ void lambda$stopMassager$7(Integer index) {
        handleMassagerStatusChange(index.intValue(), getMassagerEffectId(index.intValue()), getMassagerIntensity(index.intValue()), false);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void stopMassager(Set<Integer> ids) {
        SeatMassagerManager seatMassagerManager = this.mSeatMassagerManager;
        if (seatMassagerManager != null) {
            try {
                LogUtils.d(TAG, "stopMassager=" + Arrays.toString(ids.toArray()) + ", " + seatMassagerManager.stopMassager(ids), false);
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public boolean isMassagerRunning(int index) {
        SeatMassagerManager seatMassagerManager = this.mSeatMassagerManager;
        if (seatMassagerManager != null) {
            try {
                return seatMassagerManager.getMassagerStatus(index) == 1;
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void doVibrate(int id, String effect, int loop, int position) {
        SeatMassagerManager seatMassagerManager = this.mSeatMassagerManager;
        if (seatMassagerManager != null) {
            try {
                LogUtils.d(TAG, "doVibrate=" + id + ", " + effect + ", " + loop + ", " + position + ", " + seatMassagerManager.doVibrate(id, effect, loop, position), false);
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void stopVibrate() {
        SeatMassagerManager seatMassagerManager = this.mSeatMassagerManager;
        if (seatMassagerManager != null) {
            try {
                LogUtils.d(TAG, "stopVibrate=" + seatMassagerManager.stopVibrate(), false);
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setRhythmMode(int mode) {
        SeatMassagerManager seatMassagerManager = this.mSeatMassagerManager;
        if (seatMassagerManager != null) {
            try {
                LogUtils.d(TAG, "setRhythmMode=" + mode + ", " + seatMassagerManager.setRhythmPattern(mode), false);
            } catch (XUIServiceNotConnectedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public int getRhythmMode() {
        SeatMassagerManager seatMassagerManager = this.mSeatMassagerManager;
        if (seatMassagerManager != null) {
            try {
                int rhythmPattern = seatMassagerManager.getRhythmPattern();
                LogUtils.d(TAG, "getRhythmMode=" + rhythmPattern, false);
                return rhythmPattern;
            } catch (XUIServiceNotConnectedException e) {
                e.printStackTrace();
                return -1;
            }
        }
        return -1;
    }

    private /* synthetic */ void lambda$setMassagerEffect$8(final String effectId, Integer index) {
        handleMassagerEffectChange(index.intValue(), effectId);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setMassagerEffect(Set<Integer> ids, String effectId) {
        SeatMassagerManager seatMassagerManager = this.mSeatMassagerManager;
        if (seatMassagerManager != null) {
            try {
                LogUtils.d(TAG, "setMassagerEffect=" + Arrays.toString(ids.toArray()) + ", " + effectId + ", " + seatMassagerManager.setMassagerEffect(ids, effectId), false);
            } catch (XUIServiceNotConnectedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public String getMassagerEffectId(int index) {
        SeatMassagerManager seatMassagerManager = this.mSeatMassagerManager;
        if (seatMassagerManager != null) {
            try {
                String massagerEffect = seatMassagerManager.getMassagerEffect(index);
                LogUtils.d(TAG, "getMassagerEffectId=" + index + ", " + massagerEffect, false);
                return massagerEffect;
            } catch (XUIServiceNotConnectedException e) {
                e.printStackTrace();
                return "";
            }
        }
        return "";
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public List<String> getMassagerEffectIDs() {
        if (this.mSeatMassagerManager != null) {
            try {
                return new ArrayList(this.mSeatMassagerManager.showMassagerEffect());
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
        return new ArrayList();
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public List<String> getVibrateEffectIDs() {
        if (this.mSeatMassagerManager != null) {
            try {
                return new ArrayList(this.mSeatMassagerManager.showVibrateEffect());
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
        return new ArrayList();
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setDSeatHorzMove(int control, int direction) {
        try {
            this.mCarManager.setDriverSeatHorizMove(control, direction);
        } catch (Exception e) {
            LogUtils.e(TAG, "setDSeatHorzMove: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setDSeatVerMove(int control, int direction) {
        try {
            this.mCarManager.setDriverSeatVertiMove(control, direction);
        } catch (Exception e) {
            LogUtils.e(TAG, "setDSeatVerMove: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setDSeatTiltMove(int control, int direction) {
        try {
            this.mCarManager.setDriverSeatBackMove(control, direction);
        } catch (Exception e) {
            LogUtils.e(TAG, "setDSeatTiltMove: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setDSeatLegMove(int control, int direction) {
        try {
            this.mCarManager.setDriverLegVertiMove(control, direction);
        } catch (Exception e) {
            LogUtils.e(TAG, "setDSeatLegMove: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setDSeatCushionMove(int control, int direction) {
        try {
            this.mCarManager.setDriverSeatCushTiltPos(control, direction);
        } catch (Exception e) {
            LogUtils.e(TAG, "setDSeatCushionMove:" + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setDSeatLumbHorzMove(int control, int direction) {
        try {
            this.mCarManager.setDriverLumbHorzMove(control, direction);
        } catch (Exception e) {
            LogUtils.e(TAG, "setDSeatLumbHorzMove: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setDSeatLumbVerMove(int control, int direction) {
        try {
            this.mCarManager.setDriverLumbVertiMove(control, direction);
        } catch (Exception e) {
            LogUtils.e(TAG, "setDSeatLumbVerMove: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setPSeatHorzMove(int control, int direction) {
        try {
            this.mCarManager.setPsnSeatHorizMove(control, direction);
        } catch (Exception e) {
            LogUtils.e(TAG, "setPSeatHorzMove: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setPSeatVerMove(int control, int direction) {
        if (!this.mCarConfig.isSupportMsmPVerticalMove()) {
            LogUtils.w(TAG, "Not support adjust PSN seat in vertical direction");
            return;
        }
        try {
            this.mCarManager.setPsnSeatVertiMove(control, direction);
        } catch (Exception e) {
            LogUtils.e(TAG, "setPsnSeatVertiMove: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setPSeatTiltMove(int control, int direction) {
        try {
            this.mCarManager.setPsnSeatBackMove(control, direction);
        } catch (Exception e) {
            LogUtils.e(TAG, "setPSeatTiltMove: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setPSeatLegMove(int control, int direction) {
        try {
            this.mCarManager.setPassengerSeatCushExt(control, direction);
        } catch (Exception e) {
            LogUtils.e(TAG, "setPSeatLegMove, e=" + e, false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setPSeatLumbHorzMove(int control, int direction) {
        try {
            this.mCarManager.setPassengerSeatLumbHorzPos(control, direction);
        } catch (Exception e) {
            LogUtils.e(TAG, "setPassengerSeatLumbHorzPos, e=" + e, false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setPSeatLumbVerMove(int control, int direction) {
        try {
            this.mCarManager.setPassengerSeatLumbVerticalPos(control, direction);
        } catch (Exception e) {
            LogUtils.e(TAG, "setPassengerSeatLumbVerticalPos, e=" + e, false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setRLSeatTiltMove(int control, int direction) {
        try {
            this.mCarManager.setSecrowLtSeatTiltReq(control, direction);
        } catch (Exception e) {
            LogUtils.e(TAG, "setRLSeatTiltMove, e=" + e, false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setRLSeaHorzMove(int control, int direction) {
        try {
            this.mCarManager.setSecRowLeftHorizMove(control, direction);
        } catch (Exception e) {
            LogUtils.e(TAG, "setRLSeatTiltMove, e=" + e, false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setRLSeatLegMove(int control, int direction) {
        try {
            this.mCarManager.setSecRowLeftSeatLegHorzPosReq(control, direction);
        } catch (Exception e) {
            LogUtils.e(TAG, "setSecRowLeftSeatCushExtReq, e=" + e, false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setRLSeatLegVerMove(int control, int direction) {
        try {
            this.mCarManager.setSecRowLeftLegVertiMove(control, direction);
        } catch (Exception e) {
            LogUtils.e(TAG, "setRLSeatLegVerMove, e=" + e, false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setRLSeatLumbHorzMove(int control, int direction) {
        try {
            this.mCarManager.setSecRowLeftSeatLumbHorzMove(control, direction);
        } catch (Exception e) {
            LogUtils.e(TAG, "setRLSeatLumbHorzMove, e=" + e, false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setRLSeatLumbVerMove(int control, int direction) {
        try {
            this.mCarManager.setSecRowLeftSeatLumbVertiMove(control, direction);
        } catch (Exception e) {
            LogUtils.e(TAG, "setRLSeatLumbVerMove, e=" + e, false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setRLSeatHeadRestHorzMove(int control, int direction) {
        try {
            this.mCarManager.setSecRowLeftHeadHorizMove(control, direction);
        } catch (Exception e) {
            LogUtils.e(TAG, "setRLSeatHeadRestHorzMove, e=" + e, false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setRLSeatHeadRestVerMove(int control, int direction) {
        try {
            this.mCarManager.setSecRowLeftHeadVertiMove(control, direction);
        } catch (Exception e) {
            LogUtils.e(TAG, "setRLSeatHeadRestVerMove, e=" + e, false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setRRSeatTiltMove(int control, int direction) {
        try {
            this.mCarManager.setSecrowRtSeatTiltReq(control, direction);
        } catch (Exception e) {
            LogUtils.e(TAG, "setRRSeatTiltMove, e=" + e, false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setRRSeaHorzMove(int control, int direction) {
        try {
            this.mCarManager.setSecRowRightHorizMove(control, direction);
        } catch (Exception e) {
            LogUtils.e(TAG, "setRRSeaHorzMove, e=" + e, false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setRRSeatLegMove(int control, int direction) {
        try {
            this.mCarManager.setSecRowRightSeatLegHorzPosReq(control, direction);
        } catch (Exception e) {
            LogUtils.e(TAG, "setSecRowRightSeatCushExtReq, e=" + e, false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setRRSeatLegVerMove(int control, int direction) {
        try {
            this.mCarManager.setSecRowRightLegVertiMove(control, direction);
        } catch (Exception e) {
            LogUtils.e(TAG, "setRRSeatLegVerMove, e=" + e, false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setRRSeatLumbHorzMove(int control, int direction) {
        try {
            this.mCarManager.setSecRowRightSeatLumbHorzMove(control, direction);
        } catch (Exception e) {
            LogUtils.e(TAG, "setRRSeatLumbHorzMove, e=" + e, false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setRRSeatLumbVerMove(int control, int direction) {
        try {
            this.mCarManager.setSecRowRightSeatLumbVertiMove(control, direction);
        } catch (Exception e) {
            LogUtils.e(TAG, "setRRSeatLumbVerMove, e=" + e, false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setRRSeatHeadRestHorzMove(int control, int direction) {
        try {
            this.mCarManager.setSecRowRightHeadHorizMove(control, direction);
        } catch (Exception e) {
            LogUtils.e(TAG, "setRRSeatHeadRestHorzMove, e=" + e, false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setRRSeatHeadRestVerMove(int control, int direction) {
        try {
            this.mCarManager.setSecRowRightHeadVertiMove(control, direction);
        } catch (Exception e) {
            LogUtils.e(TAG, "setRRSeatHeadRestVerMove, e=" + e, false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setTrdRowLeftSeatTiltMove(int control, int direction) {
        try {
            this.mCarManager.setTrdRowLeftSeatTiltMove(control, direction);
        } catch (Exception e) {
            LogUtils.e(TAG, "setTrdRowLeftSeatTiltMove, e=" + e, false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setTrdRowRightSeatTiltMove(int control, int direction) {
        try {
            this.mCarManager.setTrdRowRightSeatTiltMove(control, direction);
        } catch (Exception e) {
            LogUtils.e(TAG, "setTrdRowRightSeatTiltMove, e=" + e, false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setTrdRowLeftSeatHeadVerMove(int control, int direction) {
        try {
            this.mCarManager.setTrdRowLeftHeadVertiMove(control, direction);
        } catch (Exception e) {
            LogUtils.e(TAG, "setTrdRowLeftSeatHeadVerMove, e=" + e, false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setTrdRowRightSeatHeadVerMove(int control, int direction) {
        try {
            this.mCarManager.setTrdRowRightHeadVertiMove(control, direction);
        } catch (Exception e) {
            LogUtils.e(TAG, "setTrdRowRightSeatHeadVerMove, e=" + e, false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setTrdRowMidSeatHeadVerMove(int control, int direction) {
        try {
            this.mCarManager.setTrdRowMidHeadVertiMove(control, direction);
        } catch (Exception e) {
            LogUtils.e(TAG, "setTrdRowMidSeatHeadVerMove, e=" + e, false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setDSeatHorzPos(int pos) {
        try {
            this.mCarManager.setDriverSeatHorizPosition(this.mCarConfig.matchSeatSetPos(pos));
        } catch (Exception e) {
            LogUtils.e(TAG, "setDSeatHorzPos: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public int getDSeatHorzPos() {
        int i;
        int i2 = this.mCarConfig.getMSMSeatDefaultPos()[0];
        if (this.mCarConfig.isSupportMsmD()) {
            try {
                try {
                    i = getIntProperty(557849686);
                } catch (Exception e) {
                    LogUtils.e(TAG, "getDSeatHorzPos: " + e.getMessage(), false);
                    i = this.mCarConfig.getMSMSeatDefaultPos()[0];
                }
            } catch (Exception unused) {
                i = this.mCarManager.getDriverSeatHorizPosition();
            }
            return !isSeatPosValid(i) ? this.mCarConfig.getMSMSeatDefaultPos()[0] : i;
        }
        return i2;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setDSeatVerPos(int pos) {
        try {
            this.mCarManager.setDriverSeatVertiPosition(this.mCarConfig.matchSeatSetPos(pos));
        } catch (Exception e) {
            LogUtils.e(TAG, "setDSeatVerPos: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public int getDSeatVerPos() {
        int i;
        int i2 = this.mCarConfig.getMSMSeatDefaultPos()[1];
        if (this.mCarConfig.isSupportMsmD()) {
            try {
                try {
                    i = getIntProperty(557849687);
                } catch (Exception e) {
                    LogUtils.e(TAG, "getDSeatVerPos: " + e.getMessage(), false);
                    i = this.mCarConfig.getMSMSeatDefaultPos()[1];
                }
            } catch (Exception unused) {
                i = this.mCarManager.getDriverSeatVertiPosition();
            }
            return !isSeatPosValid(i) ? this.mCarConfig.getMSMSeatDefaultPos()[1] : i;
        }
        return i2;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setDSeatTiltPos(int pos) {
        try {
            this.mCarManager.setDriverSeatBackPosition(this.mCarConfig.matchSeatSetPos(pos));
        } catch (Exception e) {
            LogUtils.e(TAG, "setDSeatTiltPos: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public int getDSeatTiltPos() {
        int i;
        int i2 = this.mCarConfig.getMSMSeatDefaultPos()[2];
        if (this.mCarConfig.isSupportMsmD()) {
            try {
                try {
                    i = getIntProperty(557849688);
                } catch (Exception e) {
                    LogUtils.e(TAG, "getDSeatTiltPos: " + e.getMessage(), false);
                    i = this.mCarConfig.getMSMSeatDefaultPos()[2];
                }
            } catch (Exception unused) {
                i = this.mCarManager.getDriverSeatBackPosition();
            }
            return !isSeatPosValid(i) ? this.mCarConfig.getMSMSeatDefaultPos()[2] : i;
        }
        return i2;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setDSeatLegPos(int pos) {
        try {
            this.mCarManager.setDriverSeatLegPosition(this.mCarConfig.matchSeatSetPos(pos));
        } catch (Exception e) {
            LogUtils.e(TAG, "setDSeatLegPos: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public int getDSeatLegPos() {
        int i;
        int i2 = this.mCarConfig.getMSMSeatDefaultPos()[3];
        if (this.mCarConfig.isSupportDrvLeg()) {
            try {
                try {
                    i = getIntProperty(557849689);
                } catch (Exception e) {
                    LogUtils.e(TAG, "getDSeatLegPos: " + e.getMessage(), false);
                    i = this.mCarConfig.getMSMSeatDefaultPos()[3];
                }
            } catch (Exception unused) {
                i = this.mCarManager.getDriverSeatLegPosition();
            }
            return !isSeatPosValid(i) ? this.mCarConfig.getMSMSeatDefaultPos()[3] : i;
        }
        return i2;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public int getDSeatCushionPos() {
        int i = this.mCarConfig.getMSMSeatDefaultPos()[4];
        if (this.mCarConfig.isSupportDrvCushion()) {
            try {
                try {
                    return getIntProperty(557849959);
                } catch (Exception e) {
                    LogUtils.e(TAG, "getDriverSeatLegHorzPosition:" + e.getMessage(), false);
                    return this.mCarConfig.getMSMSeatDefaultPos()[4];
                }
            } catch (Exception unused) {
                return this.mCarManager.getDriverSeatLegHorzPosition();
            }
        }
        return i;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setDSeatCushionPos(int pos) {
        if (this.mCarConfig.isSupportDrvCushion()) {
            try {
                this.mCarManager.setDriverSeatLegHorzPosition(pos);
            } catch (Exception e) {
                LogUtils.e(TAG, "setDSeatCushionPos, e=" + e, false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setPSeatHorzPos(int pos) {
        if (this.mCarConfig.isSupportMsmP()) {
            try {
                this.mCarManager.setPassengerSeatHorizontalPosition(this.mCarConfig.matchSeatSetPos(pos));
            } catch (Exception e) {
                LogUtils.e(TAG, "setPSeatHorzPos: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public int getPSeatHorzPos() {
        int i = this.mCarConfig.getMSMSeatDefaultPos()[0];
        if (this.mCarConfig.isSupportMsmP()) {
            try {
                try {
                    i = getIntProperty(557849810);
                } catch (Exception e) {
                    int i2 = this.mCarConfig.getMSMSeatDefaultPos()[0];
                    LogUtils.e(TAG, "getPSeatHorzPos: " + e.getMessage(), false);
                    i = i2;
                }
            } catch (Exception unused) {
                i = this.mCarManager.getPassengerSeatHorizontalPosition();
            }
        }
        return !isSeatPosValid(i) ? this.mCarConfig.getMSMSeatDefaultPos()[0] : i;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public int getPSeatVerPos() {
        int i = this.mCarConfig.getMSMSeatDefaultPos()[1];
        if (BaseFeatureOption.getInstance().isSupportPsnSeatVerControl() && this.mCarConfig.isSupportMsmP()) {
            try {
                i = getIntProperty(557849811);
            } catch (Exception e) {
                try {
                    i = this.mCarManager.getPassengerSeatVerticalPosition();
                } catch (Exception unused) {
                    LogUtils.e(TAG, "getPSeatVerPos, e=" + e, false);
                    i = this.mCarConfig.getMSMSeatDefaultPos()[1];
                }
            }
        }
        return !isSeatPosValid(i) ? this.mCarConfig.getMSMSeatDefaultPos()[1] : i;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setPSeatVerPos(int pos) {
        try {
            this.mCarManager.setPassengerSeatVerticalPosition(pos);
        } catch (Exception e) {
            LogUtils.e(TAG, "setPSeatVerPos, e=" + e, false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public boolean isDrvHeadrestNormal() {
        int i;
        if (this.mCarConfig.isSupportHeadrestStatus()) {
            try {
                try {
                    i = getIntProperty(557849813);
                } catch (Exception unused) {
                    i = this.mCarManager.getDriverHeadrestStatus();
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "isDrvHeadrestNormal: " + e.getMessage(), false);
                i = 0;
            }
            LogUtils.e(TAG, "getDriverHeadrestStatus: " + i, false);
            return i == 0;
        }
        return true;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public boolean isPsnHeadrestNormal() {
        int i;
        if (this.mCarConfig.isSupportHeadrestStatus()) {
            try {
                try {
                    i = getIntProperty(557849814);
                } catch (Exception unused) {
                    i = this.mCarManager.getPassengerHeadrestStatus();
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "isPsnHeadrestNormal: " + e.getMessage(), false);
                i = 0;
            }
            LogUtils.e(TAG, "getPassengerHeadrestStatus: " + i, false);
            return i == 0;
        }
        return true;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setPSeatTiltPos(int pos) {
        if (this.mCarConfig.isSupportMsmP()) {
            try {
                this.mCarManager.setPassengerSeatBackPosition(this.mCarConfig.matchSeatSetPos(pos));
            } catch (Exception e) {
                LogUtils.e(TAG, "setPSeatTiltPos: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public int getPSeatTiltPos() {
        int i = this.mCarConfig.getMSMSeatDefaultPos()[2];
        if (this.mCarConfig.isSupportMsmP()) {
            try {
                try {
                    i = getIntProperty(557849812);
                } catch (Exception e) {
                    LogUtils.e(TAG, "getPSeatTiltPos: " + e.getMessage(), false);
                    i = this.mCarConfig.getMSMSeatDefaultPos()[2];
                }
            } catch (Exception unused) {
                i = this.mCarManager.getPassengerSeatBackPosition();
            }
        }
        return !isSeatPosValid(i) ? this.mCarConfig.getMSMSeatDefaultPos()[2] : i;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setPSeatLegPos(int pos) {
        if (CarBaseConfig.getInstance().isSupportPsnLeg()) {
            try {
                this.mCarManager.setPassengerSeatLegHorzPosition(pos);
            } catch (Exception e) {
                LogUtils.e(TAG, "setPSeatLegPos, e=" + e, false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public int getPSeatLegPos() {
        int i = this.mCarConfig.getMSMSeatDefaultPos()[4];
        if (this.mCarConfig.isSupportPsnLeg()) {
            try {
                try {
                    i = getIntProperty(557849960);
                } catch (Exception e) {
                    LogUtils.e(TAG, "getPassengerSeatLegHorzPosition:" + e.getMessage(), false);
                    i = this.mCarConfig.getMSMSeatDefaultPos()[4];
                }
            } catch (Exception unused) {
                i = this.mCarManager.getPassengerSeatLegHorzPosition();
            }
        }
        return !isSeatPosValid(i) ? this.mCarConfig.getMSMSeatDefaultPos()[2] : i;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setSeatMenuNotShowKeepTime(boolean drv, long keepTime) {
        if (this.mIsMainProcess) {
            if (drv) {
                this.mFunctionModel.setDrvMenuNotShowTime(keepTime);
            } else {
                this.mFunctionModel.setPsnMenuNotShowTime(keepTime);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public boolean isInSeatMenuShowTime(boolean drv) {
        if (this.mIsMainProcess) {
            if (drv) {
                return this.mFunctionModel.isDrvMenuInShowTime();
            }
            return this.mFunctionModel.isPsnMenuInShowTime();
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setRLSeatTiltPos(int pos) {
        if (this.mCarConfig.isSupportRearSeatCtrl()) {
            try {
                this.mCarManager.setSecrowLtSeatTiltPosition(pos);
            } catch (Exception e) {
                LogUtils.e(TAG, "setSecrowLtSeatTiltPosition failed:" + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public int getRLSeatTiltPos() {
        if (this.mCarConfig.isSupportRearSeatCtrl()) {
            try {
                try {
                    return getIntProperty(557849890);
                } catch (Exception e) {
                    LogUtils.e(TAG, "getRLSeatTiltPos:" + e.getMessage(), false);
                }
            } catch (Exception unused) {
                return this.mCarManager.getSecrowLtSeatTiltPosition();
            }
        }
        return 50;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public int getRLSeatHorPos() {
        if (this.mCarConfig.isSupportRearSeatCtrl()) {
            try {
                try {
                    return getIntProperty(557850030);
                } catch (Exception e) {
                    LogUtils.e(TAG, "getRLSeatHorPos:" + e.getMessage(), false);
                }
            } catch (Exception unused) {
                return this.mCarManager.getSecRowLtSeatHorzPos();
            }
        }
        return 50;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setRLSeatHorPos(int pos) {
        if (this.mCarConfig.isSupportRearSeatCtrl()) {
            try {
                this.mCarManager.setSecRowLtSeatHorzPos(pos);
            } catch (Exception e) {
                LogUtils.e(TAG, "setRLSeatHorPos failed:" + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public int getRLSeatLegHorPos() {
        return getRLSeatLegPos();
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setRLSeatLegHorPos(int pos) {
        setRLSeatLegPos(pos);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public int getRLSeatLegVerPos() {
        if (this.mCarConfig.isSupportRearSeatCtrl()) {
            try {
                try {
                    return getIntProperty(557850032);
                } catch (Exception e) {
                    LogUtils.e(TAG, "getRLSeatLegVerPos:" + e.getMessage(), false);
                }
            } catch (Exception unused) {
                return this.mCarManager.getSecRowLtSeatLegVerticalPos();
            }
        }
        return 50;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setRLSeatLegVerPos(int pos) {
        if (this.mCarConfig.isSupportRearSeatCtrl()) {
            try {
                this.mCarManager.getSecRowLtSeatLegVerticalPos();
            } catch (Exception e) {
                LogUtils.e(TAG, "setRLSeatLegVerPos failed:" + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setRLSeatHeadVerPos(int pos) {
        if (this.mCarConfig.isSupportRearSeatCtrl()) {
            try {
                this.mCarManager.setSecRowLtSeatHeadVerticalPos(pos);
            } catch (Exception e) {
                LogUtils.e(TAG, "setRLSeatHeadVerPos failed:" + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public int getRLSeatHeadVerPos() {
        if (this.mCarConfig.isSupportRearSeatCtrl()) {
            try {
                try {
                    return getIntProperty(557850033);
                } catch (Exception e) {
                    LogUtils.e(TAG, "getRLSeatHeadVerPos:" + e.getMessage(), false);
                }
            } catch (Exception unused) {
                return this.mCarManager.getSecRowLtSeatHeadVerticalPos();
            }
        }
        return 50;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setRLSeatHeadHorPos(int pos) {
        if (this.mCarConfig.isSupportRearSeatCtrl()) {
            try {
                this.mCarManager.setSecRowLtSeatHeadHorzPos(pos);
            } catch (Exception e) {
                LogUtils.e(TAG, "setRLSeatHeadHorPos failed:" + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public int getRLSeatHeadHorPos() {
        if (this.mCarConfig.isSupportRearSeatCtrl()) {
            try {
                try {
                    return getIntProperty(557850034);
                } catch (Exception e) {
                    LogUtils.e(TAG, "getRLSeatHeadHorPos:" + e.getMessage(), false);
                }
            } catch (Exception unused) {
                return this.mCarManager.getSecRowLtSeatHeadHorzPos();
            }
        }
        return 50;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public int getRLSeatZeroAngle() {
        if (this.mCarConfig.isSupportSecRowZeroGravity()) {
            try {
                try {
                    return getIntProperty(557850031);
                } catch (Exception e) {
                    LogUtils.e(TAG, "getRLSeatHeadHorPos:" + e.getMessage(), false);
                }
            } catch (Exception unused) {
                return this.mCarManager.getSecRowLtSeatAnglePos();
            }
        }
        return 50;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setRRSeatTiltPos(int pos) {
        if (this.mCarConfig.isSupportRearSeatCtrl()) {
            try {
                this.mCarManager.setSecrowRtSeatTiltPosition(pos);
            } catch (Exception e) {
                LogUtils.e(TAG, "setSecrowRtSeatTiltPosition failed:" + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public int getRRSeatTiltPos() {
        if (this.mCarConfig.isSupportRearSeatCtrl()) {
            try {
                try {
                    return getIntProperty(557849891);
                } catch (Exception e) {
                    LogUtils.e(TAG, "getRRSeatTiltPos:" + e.getMessage(), false);
                }
            } catch (Exception unused) {
                return this.mCarManager.getSecrowRtSeatTiltPosition();
            }
        }
        return 50;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setRRSeatHorPos(int pos) {
        if (this.mCarConfig.isSupportRearSeatCtrl()) {
            try {
                this.mCarManager.setSecRowRtSeatHorzPos(pos);
            } catch (Exception e) {
                LogUtils.e(TAG, "setRRSeatHorPos failed:" + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public int getRRSeatHorPos() {
        if (this.mCarConfig.isSupportRearSeatCtrl()) {
            try {
                try {
                    return getIntProperty(557850035);
                } catch (Exception e) {
                    LogUtils.e(TAG, "getRRSeatHorPos:" + e.getMessage(), false);
                }
            } catch (Exception unused) {
                return this.mCarManager.getSecRowRtSeatHorzPos();
            }
        }
        return 50;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setRRSeatLegHorPos(int pos) {
        setRRSeatLegPos(pos);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public int getRRSeatLegHorPos() {
        return getRRSeatLegPos();
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setRRSeatLegVerPos(int pos) {
        if (this.mCarConfig.isSupportRearSeatCtrl()) {
            try {
                this.mCarManager.setSecRowRtSeatHeadVerticalPos(pos);
            } catch (Exception e) {
                LogUtils.e(TAG, "setRRSeatLegVerPos failed:" + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public int getRRSeatLegVerPos() {
        if (this.mCarConfig.isSupportRearSeatCtrl()) {
            try {
                try {
                    return getIntProperty(557850037);
                } catch (Exception e) {
                    LogUtils.e(TAG, "getRRSeatLegVerPos:" + e.getMessage(), false);
                }
            } catch (Exception unused) {
                return this.mCarManager.getSecRowRtSeatLegVerticalPos();
            }
        }
        return 50;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setRRSeatHeadVerPos(int pos) {
        if (this.mCarConfig.isSupportRearSeatCtrl()) {
            try {
                this.mCarManager.setSecRowRtSeatHeadVerticalPos(pos);
            } catch (Exception e) {
                LogUtils.e(TAG, "setRRSeatHeadVerPos failed:" + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public int getRRSeatHeadVerPos() {
        if (this.mCarConfig.isSupportRearSeatCtrl()) {
            try {
                try {
                    return getIntProperty(557850038);
                } catch (Exception e) {
                    LogUtils.e(TAG, "getRRSeatHeadVerPos:" + e.getMessage(), false);
                }
            } catch (Exception unused) {
                return this.mCarManager.getSecRowRtSeatHeadVerticalPos();
            }
        }
        return 50;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setRRSeatHeadHorPos(int pos) {
        if (this.mCarConfig.isSupportRearSeatCtrl()) {
            try {
                this.mCarManager.setSecRowRtSeatHeadHorzPos(pos);
            } catch (Exception e) {
                LogUtils.e(TAG, "setRRSeatHeadHorPos failed:" + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public int getRRSeatHeadHorPos() {
        if (this.mCarConfig.isSupportRearSeatCtrl()) {
            try {
                try {
                    return getIntProperty(557850039);
                } catch (Exception e) {
                    LogUtils.e(TAG, "getRRSeatHeadHorPos:" + e.getMessage(), false);
                }
            } catch (Exception unused) {
                return this.mCarManager.getSecRowRtSeatHeadHorzPos();
            }
        }
        return 50;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public int getRRSeatZeroAngle() {
        if (this.mCarConfig.isSupportSecRowZeroGravity()) {
            try {
                try {
                    return getIntProperty(557850036);
                } catch (Exception e) {
                    LogUtils.e(TAG, "getRLSeatHeadHorPos:" + e.getMessage(), false);
                }
            } catch (Exception unused) {
                return this.mCarManager.getSecRowRtSeatAnglePos();
            }
        }
        return 50;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setRLSeatLegPos(int pos) {
        if (this.mCarConfig.isSupportRearSeatCtrl()) {
            try {
                this.mCarManager.setSecRowLeftSeatLegHorzPosition(pos);
            } catch (Exception e) {
                LogUtils.e(TAG, "setSecRowLeftSeatCushExtPosition:" + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public int getRLSeatLegPos() {
        if (this.mCarConfig.isSupportRearSeatCtrl()) {
            try {
                try {
                    return getIntProperty(557849961);
                } catch (Exception e) {
                    LogUtils.e(TAG, "getSecRowLeftSeatCushExtPosition:" + e.getMessage(), false);
                }
            } catch (Exception unused) {
                return this.mCarManager.getSecRowLeftSeatLegHorzPosition();
            }
        }
        return 50;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setRRSeatLegPos(int pos) {
        if (this.mCarConfig.isSupportRearSeatCtrl()) {
            try {
                this.mCarManager.setSecRowRightSeatLegHorzPosition(pos);
            } catch (Exception e) {
                LogUtils.e(TAG, "setSecRowRightSeatCushExtPosition:" + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public int getRRSeatLegPos() {
        if (this.mCarConfig.isSupportRearSeatCtrl()) {
            try {
                try {
                    return getIntProperty(557849962);
                } catch (Exception e) {
                    LogUtils.e(TAG, "getSecRowLeftSeatCushExtPosition:" + e.getMessage(), false);
                }
            } catch (Exception unused) {
                return this.mCarManager.getSecRowRightSeatLegHorzPosition();
            }
        }
        return 50;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setRLSeatFold(boolean fold) {
        try {
            this.mCarManager.setSecrowLeftSeatUnlockReq(fold ? 1 : 0);
        } catch (Exception e) {
            LogUtils.e(TAG, "setRLSeatFold, e=" + e, false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setRLSeatStopMove() {
        try {
            this.mCarManager.setSecrowLtSeatSTopMoveReq(1);
        } catch (Exception e) {
            LogUtils.e(TAG, "setRLSeatStopMove, e=" + e);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setRRSeatFold(boolean fold) {
        try {
            this.mCarManager.setSecrowRightSeatUnlockReq(fold ? 1 : 0);
        } catch (Exception e) {
            LogUtils.e(TAG, "setRRSeatFold, e=" + e, false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setRRSeatStopMove() {
        try {
            this.mCarManager.setSecrowRtSeatSTopMoveReq(1);
        } catch (Exception e) {
            LogUtils.e(TAG, "setRRSeatStopMove, e=" + e, false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setDrvSeatLayFlat() {
        try {
            this.mCarManager.setDriverSeatTiltLevelOff(1);
        } catch (Exception e) {
            LogUtils.e(TAG, "setDrvSeatLayFlat, e=" + e, false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setPsnSeatLayFlat() {
        try {
            this.mCarManager.setPassengerSeatTitlLevelOff(1);
        } catch (Exception e) {
            LogUtils.e(TAG, "setPsnSeatLayFlat, e=" + e, false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setEsbEnable(boolean enable) {
        try {
            this.mCarMsbManager.setMsbStatus(enable ? 1 : 0);
            if (this.mIsMainProcess) {
                this.mDataSync.setDrvSeatEsb(enable);
            } else {
                this.mEsbSw = Boolean.valueOf(enable);
                CarControl.PrivateControl.putBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.ESB, enable);
            }
        } catch (Exception e) {
            LogUtils.e(TAG, "setEsbEnable1: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setEsbEnable(boolean enable, boolean saveEnable) {
        try {
            this.mCarMsbManager.setMsbStatus(enable ? 1 : 0);
            if (this.mIsMainProcess) {
                this.mDataSync.setDrvSeatEsb(saveEnable);
                CarControl.PrivateControl.putBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.ESB_NOTIFY, saveEnable);
            } else {
                this.mEsbSw = Boolean.valueOf(saveEnable);
                CarControl.PrivateControl.putBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.ESB, saveEnable);
            }
        } catch (Exception e) {
            LogUtils.e(TAG, "setEsbEnable2: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public boolean getEsbEnable() {
        int msbStatus;
        try {
            try {
                msbStatus = getIntProperty(557849635);
            } catch (Exception e) {
                LogUtils.e(TAG, "getEsbEnable: " + e.getMessage(), false);
                if (this.mDataSync == null) {
                    return true;
                }
                return this.mDataSync.getDrvSeatEsb();
            }
        } catch (Exception unused) {
            msbStatus = this.mCarMsbManager.getMsbStatus();
        }
        return msbStatus == 1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public boolean getEsbModeSp() {
        return this.mIsMainProcess ? this.mDataSync.getDrvSeatEsb() : this.mEsbSw.booleanValue();
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public boolean isPsnSeatOccupied() {
        int i;
        try {
            try {
                i = getIntProperty(557849679);
            } catch (Exception unused) {
                i = this.mCarSrsManager.getPsnOnSeat();
            }
        } catch (Exception e) {
            LogUtils.e(TAG, "isPsnSeatOccupied: " + e.getMessage(), false);
            i = 0;
        }
        return i == 1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public boolean isRlSeatOccupied() {
        int i;
        if (this.mCarConfig.isSupportVipSeat()) {
            try {
                try {
                    i = getIntProperty(557849800);
                } catch (Exception unused) {
                    i = this.mCarSrsManager.getRearLeftSeatOccupancyStatus();
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "isRlSeatOccupied: " + e.getMessage(), false);
                i = 0;
            }
            return i == 1;
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public boolean isRmSeatOccupied() {
        int i;
        if (this.mCarConfig.isSupportVipSeat()) {
            try {
                try {
                    i = getIntProperty(557849801);
                } catch (Exception unused) {
                    i = this.mCarSrsManager.getRearMiddleSeatOccupancyStatus();
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "isRmSeatOccupied: " + e.getMessage(), false);
                i = 0;
            }
            return i == 1;
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public boolean isRrSeatOccupied() {
        int i;
        if (this.mCarConfig.isSupportVipSeat()) {
            try {
                try {
                    i = getIntProperty(557849802);
                } catch (Exception unused) {
                    i = this.mCarSrsManager.getRearRightSeatOccupancyStatus();
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "isRrSeatOccupied: " + e.getMessage(), false);
                i = 0;
            }
            return i == 1;
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void stopDrvSeatMove() {
        if (this.mCarConfig.isSupportDrvSeatStop()) {
            try {
                this.mCarManager.stopDriverSeatMoving(1);
            } catch (Exception e) {
                LogUtils.e(TAG, "stopDrvSeatMove: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void stopPsnSeatMove() {
        if (this.mCarConfig.isSupportPsnSeatStop()) {
            try {
                this.mCarManager.stopPassengerSeatMoving(1);
            } catch (Exception e) {
                LogUtils.e(TAG, "stopPsnSeatMove: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setBackBeltSw(boolean enable) {
        try {
            this.mCarSrsManager.setBackBeltSw(enable ? 1 : 0);
            if (this.mCarConfig.isSupportRearBeltWarningSwitch() && !BaseFeatureOption.getInstance().isSupportRsbWarningReset()) {
                if (this.mIsMainProcess) {
                    this.mDataSync.setRsbWarning(enable);
                } else {
                    CarControl.PrivateControl.putBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.REAR_SEATBELT_WARNING, enable);
                }
            }
        } catch (Exception e) {
            LogUtils.e(TAG, "setBackBeltSw: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public boolean getBackBeltWStatus() {
        int backBeltSwStatus;
        try {
            try {
                backBeltSwStatus = getIntProperty(557849636);
            } catch (Exception e) {
                LogUtils.e(TAG, "getBackBeltWStatus: " + e.getMessage(), false);
                return true;
            }
        } catch (Exception unused) {
            backBeltSwStatus = this.mCarSrsManager.getBackBeltSwStatus();
        }
        return backBeltSwStatus == 1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public int getDrvBeltStatus() {
        try {
            return this.mCarSrsManager.getDriverBeltStatus();
        } catch (Exception e) {
            LogUtils.e(TAG, "getDrvBeltStatus: " + e.getMessage(), false);
            return 0;
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public int getPsnBeltStatus() {
        try {
            try {
                return getIntProperty(557849613);
            } catch (Exception unused) {
                return this.mCarSrsManager.getPsnBeltStatus();
            }
        } catch (Exception e) {
            LogUtils.e(TAG, "getPsnBeltStatus: " + e.getMessage(), false);
            return 0;
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public int getDrvSeatPosIdx() {
        if (this.mIsMainProcess) {
            return this.mDataSync.getDrvSeatPosIdx();
        }
        return 0;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public int getPsnSeatPosIdx(int selectId) {
        if (this.mIsMainProcess) {
            return this.mDataSync.getPsnSeatPosIdx(selectId);
        }
        return 0;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public int getCurrentSelectedPsnHabit() {
        return this.mDataSync.getCurrentSelectedPsnHabit();
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public int getCurrentSelectedRLHabit() {
        return this.mDataSync.getCurrentSelectedRLHabit();
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public int getCurrentSelectedRRHabit() {
        return this.mDataSync.getCurrentSelectedRRHabit();
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public int[] getDrvSeatPos(int index) {
        int[] drvSeatSavedPos = this.mIsMainProcess ? this.mDataSync.getDrvSeatSavedPos(index) : new int[5];
        return drvSeatSavedPos == null ? CarBaseConfig.getInstance().getMSMSeatDefaultPos() : drvSeatSavedPos;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public int[] getPsnSelectSeatPos(int selectId) {
        if (this.mCarConfig.isSupportMsmP()) {
            return this.mIsMainProcess ? this.mDataSync.getPsnSelectSeatPos(selectId) : new int[4];
        }
        return null;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public int[] getRLSelectSeatPos(int selectId) {
        if (CarBaseConfig.getInstance().isSupportSecRowMemory()) {
            return this.mIsMainProcess ? this.mDataSync.getRLSelectSeatPos(selectId) : new int[4];
        }
        return null;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public int[] getRRSelectSeatPos(int selectId) {
        if (CarBaseConfig.getInstance().isSupportSecRowMemory()) {
            return this.mIsMainProcess ? this.mDataSync.getRRSelectSeatPos(selectId) : new int[4];
        }
        return null;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void saveDrvSeatPos(int index, int[] position) {
        if (this.mIsMainProcess) {
            this.mDataSync.setDrvSeatPosIdx(index);
            this.mDataSync.saveDrvSeatPos(position);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public int[] getPsnSeatPos() {
        if (this.mCarConfig.isSupportMsmP()) {
            return this.mIsMainProcess ? this.mDataSync.getPsnSeatPos() : new int[4];
        }
        return null;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public int[] getRLSeatPos() {
        if (CarBaseConfig.getInstance().isSupportSecRowMemory()) {
            return this.mIsMainProcess ? this.mDataSync.getRLSeatPos() : new int[7];
        }
        return null;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public int[] getRRSeatPos() {
        if (CarBaseConfig.getInstance().isSupportSecRowMemory()) {
            return this.mIsMainProcess ? this.mDataSync.getRRSeatPos() : new int[7];
        }
        return null;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void savePsnSeatPos(int[] position) {
        if (this.mIsMainProcess && this.mCarConfig.isSupportMsmP()) {
            this.mDataSync.savePsnSelectSeatPos(position, getCurrentSelectedPsnHabit());
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void savePsnSeatPos(int[] position, int index, int selectId) {
        if (this.mIsMainProcess && this.mCarConfig.isSupportMsmP()) {
            this.mDataSync.saveCurrentSelectPsnHabit(selectId);
            this.mDataSync.setPsnSeatPosIdx(index, selectId);
            this.mDataSync.savePsnSelectSeatPos(position, selectId);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void savePsnSeatPos(int[] position, int selectId) {
        if (this.mIsMainProcess && this.mCarConfig.isSupportMsmP()) {
            this.mDataSync.savePsnSelectSeatPos(position, selectId);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void saveRLSeatPos(int[] position, int selectId) {
        if (this.mIsMainProcess && CarBaseConfig.getInstance().isSupportSecRowMemory()) {
            this.mDataSync.saveRLSelectSeatPos(position, selectId);
            this.mDataSync.saveCurrentSelectRLHabit(selectId);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void saveRRSeatPos(int[] position, int selectId) {
        if (this.mIsMainProcess && CarBaseConfig.getInstance().isSupportSecRowMemory()) {
            this.mDataSync.saveRRSelectSeatPos(position, selectId);
            this.mDataSync.saveCurrentSelectRRHabit(selectId);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void savePsnSeatName(String posName, int selectId) {
        if (this.mIsMainProcess && CarBaseConfig.getInstance().isSupportMsmP()) {
            this.mDataSync.savePsnSeatName(posName, selectId);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void saveRLSeatName(String posName, int selectId) {
        if (this.mIsMainProcess && CarBaseConfig.getInstance().isSupportSecRowMemory()) {
            this.mDataSync.saveRLSeatName(posName, selectId);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void saveRRSeatName(String posName, int selectId) {
        if (this.mIsMainProcess && CarBaseConfig.getInstance().isSupportSecRowMemory()) {
            this.mDataSync.saveRRSeatName(posName, selectId);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public int[] getPsnSeatDefaultPos() {
        int[] mSMSeatDefaultPos = this.mCarConfig.getMSMSeatDefaultPos();
        return new int[]{mSMSeatDefaultPos[0], mSMSeatDefaultPos[1], mSMSeatDefaultPos[2], mSMSeatDefaultPos[4]};
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public int[] getRLSeatNowPos() {
        return new int[]{getRLSeatHorPos(), getRLSeatZeroAngle(), getRLSeatTiltPos(), getRLSeatLegPos(), getRLSeatLegHorPos(), getRLSeatHeadVerPos(), getRLSeatHeadHorPos()};
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public int[] getRRSeatNowPos() {
        return new int[]{getRRSeatHorPos(), getRRSeatZeroAngle(), getRRSeatTiltPos(), getRRSeatLegPos(), getRRSeatLegHorPos(), getRRSeatHeadVerPos(), getRRSeatHeadHorPos()};
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public String getPsnSeatName(int selectId) {
        return (this.mCarConfig.isSupportMsmP() && this.mIsMainProcess) ? this.mDataSync.getPsnSeatName(selectId) : ResUtils.getString(R.string.seat_default_name, Integer.valueOf(selectId));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public String getRLSeatName(int selectId) {
        return (this.mCarConfig.isSupportSecRowMemory() && this.mIsMainProcess) ? this.mDataSync.getRLSeatName(selectId) : ResUtils.getString(R.string.seat_default_name, Integer.valueOf(selectId));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public String getRRSeatName(int selectId) {
        return (this.mCarConfig.isSupportSecRowMemory() && this.mIsMainProcess) ? this.mDataSync.getRRSeatName(selectId) : ResUtils.getString(R.string.seat_default_name, Integer.valueOf(selectId));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setDriverAllPositions(int horizonPos, int verticalPos, int tiltPos, int legPos, int cushionPos) {
        setDriverAllPositions(0, horizonPos, verticalPos, tiltPos, legPos, cushionPos);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setDriverAllPositions(int memoryReq, int horizonPos, int verticalPos, int tiltPos, int legPos, int cushionPos) {
        try {
            this.mCarManager.setDriverAllPositions(memoryReq, horizonPos, verticalPos, tiltPos, legPos, cushionPos);
        } catch (Exception e) {
            LogUtils.e(TAG, "setDriverAllPositions, e=" + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setDriverAllPositionsToMcu(int horizonPos, int verticalPos, int tiltPos, int legPos, int cushionPos) {
        if (this.mIsMainProcess) {
            try {
                this.mCarManager.saveDriverAllPositionsToMcu(horizonPos, verticalPos, tiltPos, legPos, cushionPos);
            } catch (Exception e) {
                LogUtils.e(TAG, "setDriverAllPositionsToMcu, e=" + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setPsnAllPositions(int horizonPos, int verticalPos, int tiltPos, int legPos) {
        if (this.mCarConfig.isSupportMsmP()) {
            try {
                this.mCarManager.setPassengerAllPositions(0, horizonPos, verticalPos, tiltPos, legPos);
            } catch (Exception e) {
                LogUtils.e(TAG, "setPsnAllPositions: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setPsnAllPositions(int memoryReq, int horizonPos, int verticalPos, int tiltPos, int legPos) {
        if (this.mCarConfig.isSupportMsmP()) {
            try {
                this.mCarManager.setPassengerAllPositions(memoryReq, horizonPos, verticalPos, tiltPos, legPos);
            } catch (Exception e) {
                LogUtils.e(TAG, "setPsnAllPositions: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setPsnAllPositionsToMcu(int horizonPos, int verticalPos, int tiltPos, int legPos) {
        if (this.mCarConfig.isSupportPsnWelcomeMode() && !BaseFeatureOption.getInstance().isSupportDomainControllerSeatWelcome() && this.mIsMainProcess) {
            try {
                this.mCarManager.savePassengerAllPositionsToMcu(horizonPos, verticalPos, tiltPos, legPos);
            } catch (Exception e) {
                LogUtils.e(TAG, "setPsnAllPositionsToMcu, e=" + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setRLAllPositions(int horizonPos, int anglePos, int tiltPos, int cushionPos, int legPos, int headVer, int headHor) {
        if (CarBaseConfig.getInstance().isSupportSecRowMemory()) {
            try {
                this.mCarManager.setSecRowLeftSeatPos(0, horizonPos, anglePos, tiltPos, cushionPos, legPos, headVer, headHor);
            } catch (Exception e) {
                LogUtils.e(TAG, "setRLAllPositions: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setRLAllPositions(int memoryReq, int horizonPos, int anglePos, int tiltPos, int cushionPos, int legPos, int headVer, int headHor) {
        if (CarBaseConfig.getInstance().isSupportSecRowMemory()) {
            try {
                this.mCarManager.setSecRowLeftSeatPos(memoryReq, horizonPos, anglePos, tiltPos, cushionPos, legPos, headVer, headHor);
            } catch (Exception e) {
                LogUtils.e(TAG, "setRLAllPositions: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setRRAllPositions(int horizonPos, int anglePos, int tiltPos, int cushionPos, int legPos, int headVer, int headHor) {
        if (CarBaseConfig.getInstance().isSupportSecRowMemory()) {
            try {
                this.mCarManager.setSecRowRightSeatPos(0, horizonPos, anglePos, tiltPos, cushionPos, legPos, headVer, headHor);
            } catch (Exception e) {
                LogUtils.e(TAG, "setRRAllPositions: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setRRAllPositions(int memoryReq, int horizonPos, int anglePos, int tiltPos, int cushionPos, int legPos, int headVer, int headHor) {
        if (CarBaseConfig.getInstance().isSupportSecRowMemory()) {
            try {
                this.mCarManager.setSecRowRightSeatPos(memoryReq, horizonPos, anglePos, tiltPos, cushionPos, legPos, headVer, headHor);
            } catch (Exception e) {
                LogUtils.e(TAG, "setRRAllPositions: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public String readFollowedVehicleLostConfig() {
        if (CarBaseConfig.getInstance().isFollowVehicleLostConfigUseNew()) {
            return "2";
        }
        String string = Settings.Secure.getString(App.getInstance().getContentResolver(), GlobalConstant.GLOBAL.FOLLOWED_VEHICLE_LOST_CONFIG);
        LogUtils.i(TAG, "readFollowedVehicleLostConfig : " + (TextUtils.isEmpty(string) ? "null" : string), false);
        return ("1".equals(string) || "2".equals(string)) ? string : "2";
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setFollowedVehicleLostConfig(String config) {
        Settings.Secure.putString(App.getInstance().getContentResolver(), GlobalConstant.GLOBAL.FOLLOWED_VEHICLE_LOST_CONFIG, config);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setDriverSeatLumbControlSwitchEnable(boolean enable) {
        if (this.mCarConfig.isSupportSeatMassage()) {
            try {
                if (this.mIsMainProcess) {
                    this.mDataSync.setDrvLumbControlSw(enable);
                }
                this.mCarManager.setDriverSeatLumbControlSwitchEnable(enable ? 1 : 0);
                handleDrvLumbControlEnableChanged(enable);
            } catch (Exception e) {
                LogUtils.e(TAG, "setDriverSeatLumbControlSwitchEnable, e=" + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public boolean getDriverSeatLumbControlSwitchEnable() {
        if (this.mCarConfig.isSupportSeatMassage() && this.mIsMainProcess) {
            return this.mDataSync.getDrvLumbControlSw();
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setPassengerSeatLumbControlSwitchEnable(boolean enable) {
        if (this.mCarConfig.isSupportSeatMassage()) {
            try {
                if (this.mIsMainProcess) {
                    this.mDataSync.setPsnLumbControlSw(enable);
                }
                this.mCarManager.setPassengerSeatLumbControlSwitchEnable(enable ? 1 : 0);
                handlePsnLumbControlEnableChanged(enable);
            } catch (Exception e) {
                LogUtils.e(TAG, "setPassengerSeatLumbControlSwitchEnable, e=" + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public boolean getPassengerSeatLumbControlSwitchEnable() {
        if (this.mCarConfig.isSupportSeatMassage() && this.mIsMainProcess) {
            return this.mDataSync.getPsnLumbControlSw();
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setSecRowLtSeatEZ(int eZStatus) {
        if (this.mIsMainProcess) {
            try {
                LogUtils.d(TAG, "setSecRowLtSeatEZ   eZStatus = " + eZStatus);
                this.mCarManager.setSecRowLeftSeatEasyEntryReq(eZStatus);
            } catch (Exception e) {
                LogUtils.e(TAG, "setSecRowLtSeatEZ, e=" + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setSecRowRtSeatEZ(int eZStatus) {
        if (this.mIsMainProcess) {
            try {
                LogUtils.d(TAG, "setSecRowRtSeatEZ   eZStatus = " + eZStatus);
                this.mCarManager.setSecRowRightSeatEasyEntryReq(eZStatus);
            } catch (Exception e) {
                LogUtils.e(TAG, "setSecRowRtSeatEZ, e=" + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setSecRowLtSeatZeroGrav(int zeroGravStatus) {
        if (this.mIsMainProcess) {
            try {
                LogUtils.d(TAG, "setSecRowLtSeatZeroGrav   zeroGravStatus = " + zeroGravStatus);
                this.mCarManager.setSecRowLeftSeatZeroGravReq(zeroGravStatus);
            } catch (Exception e) {
                LogUtils.e(TAG, "setSecRowLtSeatZeroGrav, e=" + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setSecRowRtSeatZeroGrav(int zeroGravStatus) {
        if (this.mIsMainProcess) {
            try {
                LogUtils.d(TAG, "setSecRowRtSeatZeroGrav   zeroGravStatus = " + zeroGravStatus);
                this.mCarManager.setSecRowRightSeatZeroGravReq(zeroGravStatus);
            } catch (Exception e) {
                LogUtils.e(TAG, "setSecRowRtSeatZeroGrav, e=" + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setTrdRowSeatStow(int stowStatus) {
        if (this.mIsMainProcess) {
            try {
                LogUtils.d(TAG, "setTrdRowSeatStow   stowStatus = " + stowStatus);
                this.mCarManager.setMsmtSeatStowReq(stowStatus);
            } catch (Exception e) {
                LogUtils.e(TAG, "setTrdRowSeatStow, e=" + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setTrdRowLtSeatFol(int trdRowLtSeatFolStatus) {
        if (this.mIsMainProcess) {
            try {
                LogUtils.d(TAG, "setTrdRowLtSeatFol   trdRowLtSeatFolStatus = " + trdRowLtSeatFolStatus);
                this.mCarManager.setMsmtLeftSeatFoldReq(trdRowLtSeatFolStatus);
            } catch (Exception e) {
                LogUtils.e(TAG, "setTrdRowLtSeatFol, e=" + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setTrdRowRtSeatFol(int trdRowRtSeatFolStatus) {
        if (this.mIsMainProcess) {
            try {
                LogUtils.d(TAG, "setTrdRowRtSeatFol   trdRowRtSeatFolStatus = " + trdRowRtSeatFolStatus);
                this.mCarManager.setMsmtRightSeatFoldReq(trdRowRtSeatFolStatus);
            } catch (Exception e) {
                LogUtils.e(TAG, "setTrdRowRtSeatFol, e=" + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public int getSecRowLtSeatZeroGrav() {
        try {
            try {
                return getIntProperty(557850021);
            } catch (Exception e) {
                LogUtils.e(TAG, "getSecRowLtSeatZeroGrav:" + e.getMessage(), false);
                return 0;
            }
        } catch (Exception unused) {
            return this.mCarManager.getSecRowLeftSeatFuncSt();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public int getSecRowRtSeatZeroGrav() {
        try {
            try {
                return getIntProperty(557850022);
            } catch (Exception e) {
                LogUtils.e(TAG, "getSecRowRtSeatZeroGrav:" + e.getMessage(), false);
                return 0;
            }
        } catch (Exception unused) {
            return this.mCarManager.getSecRowRightSeatFuncSt();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public int getTrdRowSeatStow() {
        try {
            try {
                return getIntProperty(557850027);
            } catch (Exception e) {
                LogUtils.e(TAG, "getTrdRowSeatStow:" + e.getMessage(), false);
                return 0;
            }
        } catch (Exception unused) {
            return this.mCarManager.getMsmtSeatStowFunSt();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public int getTrdRowLtSeatFol() {
        try {
            try {
                return getIntProperty(557850025);
            } catch (Exception e) {
                LogUtils.e(TAG, "getTrdRowLtSeatFol:" + e.getMessage(), false);
                return 0;
            }
        } catch (Exception unused) {
            return this.mCarManager.getMsmtLeftSeatFoldFunSt();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public int getTrdRowRtSeatFol() {
        try {
            try {
                return getIntProperty(557850026);
            } catch (Exception e) {
                LogUtils.e(TAG, "getTrdRowRtSeatFol:" + e.getMessage(), false);
                return 0;
            }
        } catch (Exception unused) {
            return this.mCarManager.getMsmtRightSeatFoldFunSt();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public int getTrdRowLtSeatTiltPos() {
        try {
            try {
                return getIntProperty(557850040);
            } catch (Exception e) {
                LogUtils.e(TAG, "getTrdRowLtSeatTiltPos:" + e.getMessage(), false);
                return 0;
            }
        } catch (Exception unused) {
            return this.mCarManager.getTrdRowLtSeatTiltPos();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public int getTrdRowRtSeatTiltPos() {
        try {
            try {
                return getIntProperty(557850042);
            } catch (Exception e) {
                LogUtils.e(TAG, "getTrdRowRtSeatTiltPos:" + e.getMessage(), false);
                return 0;
            }
        } catch (Exception unused) {
            return this.mCarManager.getTrdRowRtSeatTiltPos();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public int getTrdRowLtSeatHeadVerticalPos() {
        try {
            try {
                return getIntProperty(557850041);
            } catch (Exception e) {
                LogUtils.e(TAG, "getTrdRowLtSeatHeadVerticalPos:" + e.getMessage(), false);
                return 0;
            }
        } catch (Exception unused) {
            return this.mCarManager.getTrdRowLtSeatHeadVerticalPos();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public int getTrdRowRtSeatHeadVerticalPos() {
        try {
            try {
                return getIntProperty(557850043);
            } catch (Exception e) {
                LogUtils.e(TAG, "getTrdRowRtSeatHeadVerticalPos:" + e.getMessage(), false);
                return 0;
            }
        } catch (Exception unused) {
            return this.mCarManager.getTrdRowRtSeatHeadVerticalPos();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public int getTrdRowMidSeatHeadVerticalPos() {
        try {
            try {
                return getIntProperty(557850044);
            } catch (Exception e) {
                LogUtils.e(TAG, "getTrdRowMidSeatHeadVerticalPos:" + e.getMessage(), false);
                return 0;
            }
        } catch (Exception unused) {
            return this.mCarManager.getTrdRowMidSeatHeadVerticalPos();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setTrdRowLtSeatTiltPos(int pos) {
        if (this.mIsMainProcess) {
            try {
                LogUtils.d(TAG, "setTrdRowLtSeatTiltPos   pos = " + pos);
                this.mCarManager.setTrdRowLtSeatTiltPos(pos);
            } catch (Exception e) {
                LogUtils.e(TAG, "setTrdRowLtSeatTiltPos, e=" + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setTrdRowRtSeatTiltPos(int pos) {
        if (this.mIsMainProcess) {
            try {
                LogUtils.d(TAG, "setTrdRowRtSeatTiltPos   pos = " + pos);
                this.mCarManager.setTrdRowRtSeatTiltPos(pos);
            } catch (Exception e) {
                LogUtils.e(TAG, "setTrdRowRtSeatTiltPos, e=" + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setTrdRowLtSeatHeadVerticalPos(int pos) {
        if (this.mIsMainProcess) {
            try {
                LogUtils.d(TAG, "setTrdRowLtSeatHeadVerticalPos   pos = " + pos);
                this.mCarManager.setTrdRowLtSeatHeadVerticalPos(pos);
            } catch (Exception e) {
                LogUtils.e(TAG, "setTrdRowLtSeatHeadVerticalPos, e=" + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setTrdRowRtSeatHeadVerticalPos(int pos) {
        if (this.mIsMainProcess) {
            try {
                LogUtils.d(TAG, "setTrdRowRtSeatHeadVerticalPos   pos = " + pos);
                this.mCarManager.setTrdRowRtSeatHeadVerticalPos(pos);
            } catch (Exception e) {
                LogUtils.e(TAG, "setTrdRowRtSeatHeadVerticalPos, e=" + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setTrdRowMidSeatHeadVerticalPos(int pos) {
        if (this.mIsMainProcess) {
            try {
                LogUtils.d(TAG, "setTrdRowMidSeatHeadVerticalPos   pos = " + pos);
                this.mCarManager.setTrdRowMidSeatHeadVerticalPos(pos);
            } catch (Exception e) {
                LogUtils.e(TAG, "setTrdRowMidSeatHeadVerticalPos, e=" + e.getMessage(), false);
            }
        }
    }

    private void mockMsmProperty() {
        this.mCarPropertyMap.put(557849686, new CarPropertyValue<>(557849686, Integer.valueOf(this.mCarConfig.getMSMSeatDefaultPos()[0])));
        this.mCarPropertyMap.put(557849687, new CarPropertyValue<>(557849687, Integer.valueOf(this.mCarConfig.getMSMSeatDefaultPos()[1])));
        this.mCarPropertyMap.put(557849688, new CarPropertyValue<>(557849688, Integer.valueOf(this.mCarConfig.getMSMSeatDefaultPos()[2])));
        mockExtMsmProperty();
    }

    protected void registerContentObserver() {
        if (this.mContentObserver == null) {
            this.mContentObserver = new ContentObserver(ThreadUtils.getHandler(0)) { // from class: com.xiaopeng.carcontrol.carmanager.impl.MsmController.5
                @Override // android.database.ContentObserver
                public void onChange(boolean selfChange, Uri uri) {
                    String lastPathSegment = uri.getLastPathSegment();
                    if (CarControl.PrivateControl.ESB_NOTIFY.equals(lastPathSegment)) {
                        MsmController.this.mEsbSw = Boolean.valueOf(CarControl.PrivateControl.getBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.ESB, true));
                        LogUtils.i(MsmController.TAG, "onChange: " + lastPathSegment + ", newValue=" + MsmController.this.mEsbSw);
                        MsmController msmController = MsmController.this;
                        msmController.handleEsbMode(msmController.mEsbSw.booleanValue() ? 1 : 0);
                    }
                }
            };
        }
        App.getInstance().getContentResolver().registerContentObserver(CarControl.PrivateControl.getUriFor(CarControl.PrivateControl.ESB_NOTIFY), false, this.mContentObserver);
    }

    protected void mockExtMsmProperty() {
        this.mCarPropertyMap.put(557849689, new CarPropertyValue<>(557849689, Integer.valueOf(this.mCarConfig.getMSMSeatDefaultPos()[3])));
    }

    private void mockMsbProperty() {
        this.mCarPropertyMap.put(557849635, new CarPropertyValue<>(557849635, Integer.valueOf((this.mDataSync == null || this.mDataSync.getDrvSeatEsb()) ? 1 : 0)));
    }

    private void mockSrsProperty() {
        int i = 0;
        this.mCarPropertyMap.put(557849679, new CarPropertyValue<>(557849679, 0));
        this.mCarPropertyMap.put(557849636, new CarPropertyValue<>(557849636, Integer.valueOf((this.mDataSync == null || this.mDataSync.getRsbWarning()) ? 1 : 1)));
        this.mCarPropertyMap.put(557849612, new CarPropertyValue<>(557849612, 0));
        this.mCarPropertyMap.put(557849613, new CarPropertyValue<>(557849613, 0));
    }

    private void handleDSeatHorz(int pos) {
        if (!isSeatPosValid(pos)) {
            LogUtils.w(TAG, "handleDSeatHorz pos is not valid:" + pos, false);
            return;
        }
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IMsmController.Callback) it.next()).onDSeatHorzPosChanged(pos);
            }
        }
    }

    private void handleDSeatVer(int pos) {
        if (!isSeatPosValid(pos)) {
            LogUtils.w(TAG, "handleDSeatVer pos is not valid:" + pos, false);
            return;
        }
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IMsmController.Callback) it.next()).onDSeatVerPosChanged(pos);
            }
        }
    }

    private void handleDSeatTilt(int pos) {
        if (!isSeatPosValid(pos)) {
            LogUtils.w(TAG, "handleDSeatTilt pos is not valid:" + pos, false);
            return;
        }
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IMsmController.Callback) it.next()).onDSeatTiltPosChanged(pos);
            }
        }
    }

    private void handleDSeatLeg(int pos) {
        if (!isSeatPosValid(pos)) {
            LogUtils.w(TAG, "handleDSeatLeg pos is not valid:" + pos, false);
            return;
        }
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IMsmController.Callback) it.next()).onDSeatLegPosChanged(pos);
            }
        }
    }

    private void handleDSeatCushion(int pos) {
        if (!isSeatPosValid(pos)) {
            LogUtils.w(TAG, "handleDSeatCushion pos is invalid:" + pos, false);
            return;
        }
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IMsmController.Callback) it.next()).onDSeatCushionPosChanged(pos);
            }
        }
    }

    protected void handlePSeatHorz(int pos) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IMsmController.Callback) it.next()).onPSeatHorzPosChanged(pos);
            }
        }
    }

    protected void handlePsnSeatWelcomeModeUpdate(int state) {
        boolean z = state == 1;
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IMsmController.Callback) it.next()).onPsnWelcomeModeChanged(z);
            }
        }
    }

    private void handlePSeatVer(int pos) {
        if (!isSeatPosValid(pos)) {
            LogUtils.w(TAG, "handlePSeatVer pos is invalid:" + pos, false);
            return;
        }
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IMsmController.Callback) it.next()).onPSeatVerPosChanged(pos);
            }
        }
    }

    protected void handlePSeatTilt(int pos) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IMsmController.Callback) it.next()).onPSeatTiltPosChanged(pos);
            }
        }
    }

    private void handlePSeatLeg(int pos) {
        if (!isSeatPosValid(pos)) {
            LogUtils.w(TAG, "handlePSeatLeg pos is invalid:" + pos, false);
            return;
        }
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IMsmController.Callback) it.next()).onPSeatLegPosChanged(pos);
            }
        }
    }

    private void handleRLSeatTilt(int pos) {
        if (!isSeatPosValid(pos)) {
            LogUtils.w(TAG, "handleRLSeatTilt pos is invalid:" + pos, false);
            return;
        }
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IMsmController.Callback) it.next()).onRLSeatTiltPosChanged(pos);
            }
        }
    }

    private void handleRRSeatTilt(int pos) {
        if (!isSeatPosValid(pos)) {
            LogUtils.w(TAG, "handleRRSeatTilt pos is invalid:" + pos, false);
            return;
        }
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IMsmController.Callback) it.next()).onRRSeatTiltPosChanged(pos);
            }
        }
    }

    private void handleRLSeatLeg(int pos) {
        if (!isSeatPosValid(pos)) {
            LogUtils.w(TAG, "handleRLSeatLeg pos is invalid:" + pos, false);
            return;
        }
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IMsmController.Callback) it.next()).onRLSeatLegPosChanged(pos);
            }
        }
    }

    private void handleRRSeatLeg(int pos) {
        if (!isSeatPosValid(pos)) {
            LogUtils.w(TAG, "handleRRSeatLeg pos is invalid:" + pos, false);
            return;
        }
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IMsmController.Callback) it.next()).onRRSeatLegPosChanged(pos);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleEsbMode(int status) {
        boolean z = status == 1;
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IMsmController.Callback) it.next()).onEsbChanged(z);
            }
        }
    }

    private void handlePsnOnSeat(int status) {
        boolean z = status == 1;
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IMsmController.Callback) it.next()).onPsnOnSeatChanged(z);
            }
        }
    }

    private void handleDrvSeatLumberCenterPressed(int status) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IMsmController.Callback) it.next()).onDrvSeatLumberSwitchPressStateChanged(status);
            }
        }
    }

    private void handlePsnSeatLumberCenterPressed(int status) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IMsmController.Callback) it.next()).onPsnSeatLumberSwitchPressStateChanged(status);
            }
        }
    }

    private void handleSecRowLtSeatHorzPosChanged(int pos) {
        if (!isSeatPosValid(pos)) {
            LogUtils.w(TAG, "handleRLSeatVer pos is invalid:" + pos, false);
            return;
        }
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IMsmController.Callback) it.next()).onSecRowLtSeatHorzPosChanged(pos);
            }
        }
    }

    private void handleSecRowRtSeatHorzPosChanged(int pos) {
        if (!isSeatPosValid(pos)) {
            LogUtils.w(TAG, "handleRLSeatVer pos is invalid:" + pos, false);
            return;
        }
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IMsmController.Callback) it.next()).onSecRowRtSeatHorzPosChanged(pos);
            }
        }
    }

    private void handleSecRowLtSeatLeSuHeigPosChanged(int pos) {
        if (!isSeatPosValid(pos)) {
            LogUtils.w(TAG, "handleRLSeatVer pos is invalid:" + pos, false);
            return;
        }
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IMsmController.Callback) it.next()).onSecRowLtSeatLeSuHeigPosChanged(pos);
            }
        }
    }

    private void handleSecRowLtSeatHeReHeigPosChanged(int pos) {
        if (!isSeatPosValid(pos)) {
            LogUtils.w(TAG, "handleRLSeatVer pos is invalid:" + pos, false);
            return;
        }
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IMsmController.Callback) it.next()).onSecRowLtSeatHeReHeigPosChanged(pos);
            }
        }
    }

    private void handleSecRowLtSeatHeReHorzPosChanged(int pos) {
        if (!isSeatPosValid(pos)) {
            LogUtils.w(TAG, "handleRLSeatVer pos is invalid:" + pos, false);
            return;
        }
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IMsmController.Callback) it.next()).onSecRowLtSeatHeReHorzPosChanged(pos);
            }
        }
    }

    private void handleSecRowRtSeatLeSuHeigPosChanged(int pos) {
        if (!isSeatPosValid(pos)) {
            LogUtils.w(TAG, "handleRLSeatVer pos is invalid:" + pos, false);
            return;
        }
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IMsmController.Callback) it.next()).onSecRowRtSeatLeSuHeigPosChanged(pos);
            }
        }
    }

    private void handleSecRowRtSeatHeReHeigPosChanged(int pos) {
        if (!isSeatPosValid(pos)) {
            LogUtils.w(TAG, "handleRLSeatVer pos is invalid:" + pos, false);
            return;
        }
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IMsmController.Callback) it.next()).onSecRowRtSeatHeReHeigPosChanged(pos);
            }
        }
    }

    private void handleSecRowRtSeatHeReHorzPosChanged(int pos) {
        if (!isSeatPosValid(pos)) {
            LogUtils.w(TAG, "handleRLSeatVer pos is invalid:" + pos, false);
            return;
        }
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IMsmController.Callback) it.next()).onSecRowRtSeatHeReHorzPosChanged(pos);
            }
        }
    }

    private void handleSecRowLtSeatZeroGravPosChanged(int pos) {
        if (!isSeatPosValid(pos)) {
            LogUtils.w(TAG, "handleRLSeatVer pos is invalid:" + pos, false);
            return;
        }
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IMsmController.Callback) it.next()).onSecRowLtSeatZeroGravPosChanged(pos);
            }
        }
    }

    private void handleSecRowRtSeatZeroGravPosChanged(int pos) {
        if (!isSeatPosValid(pos)) {
            LogUtils.w(TAG, "handleRLSeatVer pos is invalid:" + pos, false);
            return;
        }
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IMsmController.Callback) it.next()).onSecRowRtSeatZeroGravPosChanged(pos);
            }
        }
    }

    private void handleSecRowLtEzOrZeroGStatusChanged(int status) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IMsmController.Callback) it.next()).onSecRowLtEzOrZeroGStatusChanged(status);
            }
        }
    }

    private void handleSecRowRtEzOrZeroGStatusChanged(int status) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IMsmController.Callback) it.next()).onSecRowRtEzOrZeroGStatusChanged(status);
            }
        }
    }

    private void handleTrdRowLtFoldStatusChanged(int status) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IMsmController.Callback) it.next()).onTrdRowLtFoldStatusChanged(status);
            }
        }
    }

    private void handleTrdRowRtFoldStatusChanged(int status) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IMsmController.Callback) it.next()).onTrdRowRtFoldStatusChanged(status);
            }
        }
    }

    private void handleTrdRowStowStatusChanged(int status) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IMsmController.Callback) it.next()).onTrdRowStowStatusChanged(status);
            }
        }
    }

    private void handleTrdRowLtSeatTiltPosChanged(int pos) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IMsmController.Callback) it.next()).onTrdRowLtSeatTiltPosChangedpos(pos);
            }
        }
    }

    private void handleTrdRowRtSeatTiltPosChanged(int pos) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IMsmController.Callback) it.next()).onTrdRowRtSeatTiltPosChanged(pos);
            }
        }
    }

    private void handleTrdRowLtSeatHeadVerticalPosChanged(int pos) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IMsmController.Callback) it.next()).onTrdRowLtSeatHeadVerticalPosChanged(pos);
            }
        }
    }

    private void handleTrdRowRtSeatHeadVerticalPosChanged(int pos) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IMsmController.Callback) it.next()).onTrdRowRtSeatHeadVerticalPosChanged(pos);
            }
        }
    }

    private void handleTrdRowMidSeatHeadVerticalPosChanged(int pos) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IMsmController.Callback) it.next()).onTrdRowMidSeatHeadVerticalPosChanged(pos);
            }
        }
    }

    private void handleBackBeltStatus(int status) {
        boolean z = status == 1;
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IMsmController.Callback) it.next()).onBackBeltWaringChanged(z);
            }
        }
    }

    private void handleDriverBeltStatus(int status) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IMsmController.Callback) it.next()).onDrvBeltWaringChanged(status);
            }
        }
    }

    private void handlePsnBeltStatus(int status) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IMsmController.Callback) it.next()).onPsnBeltWaringChanged(status);
            }
        }
    }

    private void handleRlOnSeat(int status) {
        boolean z = status == 1;
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IMsmController.Callback) it.next()).onRlOnSeatChanged(z);
            }
        }
    }

    private void handleRmOnSeat(int status) {
        boolean z = status == 1;
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IMsmController.Callback) it.next()).onRmOnSeatChanged(z);
            }
        }
    }

    private void handleRrOnSeat(int status) {
        boolean z = status == 1;
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IMsmController.Callback) it.next()).onRrOnSeatChanged(z);
            }
        }
    }

    private void handleDrvHeadrestStatus(int status) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IMsmController.Callback) it.next()).onDrvHeadrestChanged(status == 0);
            }
        }
    }

    private void handlePsnHeadrestStatus(int status) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IMsmController.Callback) it.next()).onPsnHeadrestChanged(status == 0);
            }
        }
    }

    private void handlePsnSrsEnableStatus(int status) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                IMsmController.Callback callback = (IMsmController.Callback) it.next();
                boolean z = true;
                if (status != 1) {
                    z = false;
                }
                callback.onPsnSrsEnableChanged(z);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleMassagerIntensityChange(int index, int intensity) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IMsmController.Callback) it.next()).onMassagerIntensityChange(index, intensity);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleMassagerStatusChange(int index, String effectId, int intensity, boolean running) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IMsmController.Callback) it.next()).onMassagerStatusChange(index, effectId, intensity, running);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleMassagerEffectChange(int index, String effectId) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IMsmController.Callback) it.next()).onMassagerEffectChange(index, effectId);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleMassageFailed(int index, String effect, int opCode, int errCode) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IMsmController.Callback) it.next()).onMassageFailed(index, effect, opCode, errCode);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleRhythmIntensityChange(Set<Integer> ids, int intensity) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IMsmController.Callback) it.next()).onRhythmIntensityChange(ids, intensity);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleRhythmStatusChange(Set<Integer> ids, boolean running) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IMsmController.Callback) it.next()).onRhythmStatusChange(ids, running);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleRhythmModeChange(int mode) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IMsmController.Callback) it.next()).onRhythmModeChange(mode);
            }
        }
    }

    private void handleDrvLumbControlEnableChanged(boolean enable) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IMsmController.Callback) it.next()).onDrvSeatLumberControlEnableChanged(enable);
            }
        }
    }

    private void handlePsnLumbControlEnableChanged(boolean enable) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IMsmController.Callback) it.next()).onPsnSeatLumberControlEnableChanged(enable);
            }
        }
    }
}
