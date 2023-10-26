package com.xiaopeng.carcontrol.viewmodel.carsettings;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.SparseArray;
import androidx.lifecycle.MutableLiveData;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.GlobalConstant;
import com.xiaopeng.carcontrol.carmanager.CarClientWrapper;
import com.xiaopeng.carcontrol.carmanager.controller.IBcmController;
import com.xiaopeng.carcontrol.carmanager.controller.ICiuController;
import com.xiaopeng.carcontrol.carmanager.controller.IEspController;
import com.xiaopeng.carcontrol.carmanager.controller.ITboxController;
import com.xiaopeng.carcontrol.carmanager.controller.IVcuController;
import com.xiaopeng.carcontrol.carmanager.impl.AvasController;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import com.xiaopeng.carcontrol.helper.NotificationHelper;
import com.xiaopeng.carcontrol.model.FunctionModel;
import com.xiaopeng.carcontrol.provider.CarControl;
import com.xiaopeng.carcontrol.quicksetting.QuickSettingConstants;
import com.xiaopeng.carcontrol.quicksetting.QuickSettingManager;
import com.xiaopeng.carcontrol.speech.VuiManager;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ResUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.carcontrolmodule.R;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.app.XDialogInterface;
import java.util.function.Supplier;

/* loaded from: classes2.dex */
public abstract class CarBodyBaseViewModel implements ICarBodyViewModel {
    private static final float CHARGE_PORT_SPEED_THRESHOLD = 3.0f;
    private static final long CHARGE_PORT_TIMEOUT = 13000;
    private static final int MAX_REPEAT_TIME = 3;
    private static final String TAG = "CarBodyBaseViewModel";
    private AvasController mAvasController;
    IBcmController mBcmController;
    ICiuController mCiuController;
    IEspController mEspController;
    private int mLChargePortState;
    private int mRChargePortState;
    private SteerControlStrategy mSteerControlStrategy;
    ITboxController mTboxController;
    private IVcuController mVcuController;
    private SparseArray<ChargePortResetTask> mResetTaskCache = new SparseArray<>();
    private SparseArray<ChargePortTimeoutTask> mTimeoutTaskCache = new SparseArray<>();
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private final MutableLiveData<Boolean> mDCPortDialogShowData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mACPortDialogShowData = new MutableLiveData<>();
    final IBcmController.Callback mBcmCallback = new AnonymousClass1();
    ICiuController.Callback mCiuCallBack = new ICiuController.Callback() { // from class: com.xiaopeng.carcontrol.viewmodel.carsettings.CarBodyBaseViewModel.2
        @Override // com.xiaopeng.carcontrol.carmanager.controller.ICiuController.Callback
        public void onCiuWiperIntervalChanged(int level) {
            CarBodyBaseViewModel.this.handleCiuRainWiperIntervalChanged(level);
        }
    };
    ITboxController.Callback mTboxCallBack = new ITboxController.Callback() { // from class: com.xiaopeng.carcontrol.viewmodel.carsettings.CarBodyBaseViewModel.3
        @Override // com.xiaopeng.carcontrol.carmanager.controller.ITboxController.Callback
        public void onSoldierStateChanged(int status) {
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.ITboxController.Callback
        public void onAutoPowerOffConfigStatusChanged(boolean status) {
            CarBodyBaseViewModel.this.handleAutoPowerOffConfigChanged(status);
        }
    };
    private ContentObserver mContentObserver = new ContentObserver(ThreadUtils.getHandler(0)) { // from class: com.xiaopeng.carcontrol.viewmodel.carsettings.CarBodyBaseViewModel.4
        @Override // android.database.ContentObserver
        public void onChange(boolean selfChange, Uri uri) {
            LogUtils.d(CarBodyBaseViewModel.TAG, "onChange: " + uri);
            String lastPathSegment = uri.getLastPathSegment();
            if (CarControl.Quick.LEFT_CHARGE_PORT_STATE.equals(lastPathSegment)) {
                CarBodyBaseViewModel.this.handleChargePortChanged(true, CarControl.Quick.getInt(App.getInstance().getContentResolver(), lastPathSegment, -1));
            } else if (CarControl.Quick.RIGHT_CHARGE_PORT_STATE.equals(lastPathSegment)) {
                CarBodyBaseViewModel.this.handleChargePortChanged(false, CarControl.Quick.getInt(App.getInstance().getContentResolver(), lastPathSegment, -1));
            }
        }
    };

    protected abstract void handleArsWorkModeChanged(int mode);

    protected abstract void handleAutoPowerOffConfigChanged(boolean enable);

    protected abstract void handleChildLockChanged(boolean leftLocked, boolean rightLocked);

    protected abstract void handleChildModeChanged(boolean locked);

    protected abstract void handleCiuRainWiperIntervalChanged(int wiper);

    protected abstract void handleCwcFRSwChanged(boolean enable);

    protected abstract void handleCwcRLSwChanged(boolean enable);

    protected abstract void handleCwcRRSwChanged(boolean enable);

    protected abstract void handleCwcSwChanged(boolean enable);

    protected abstract void handleDriveLockChanged(boolean enabled);

    protected abstract void handleInductionLock(boolean enabled);

    protected abstract void handleLeaveAutoLock(boolean enabled);

    protected abstract void handleLeftChildLockChanged(boolean locked);

    protected abstract void handleLeftHotKeyChanged(boolean locked);

    protected abstract void handleNearAutoUnlock(boolean enabled);

    protected abstract void handleNfcStopSwChanged(boolean enable);

    protected abstract void handleParkUnlockChanged(boolean enabled);

    protected abstract void handleRearWiperRepairModeChanged(boolean mode);

    protected abstract void handleRightChildLockChanged(boolean locked);

    protected abstract void handleRightHotKeyChanged(boolean locked);

    protected abstract void handleStealModeChanged(boolean enable);

    protected abstract void handleUnlockTypeChanged(int type);

    protected abstract void handleWiperIntervalModeChanged(int wiper);

    protected abstract void handleWiperRepairModeChanged(boolean mode);

    protected abstract void handleWiperSensitivityChanged(int level);

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel
    public boolean isCiuRainSwEnable() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel
    public void setCiuRainSwEnable(boolean enable) {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.carcontrol.viewmodel.carsettings.CarBodyBaseViewModel$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    public class AnonymousClass1 implements IBcmController.Callback {
        AnonymousClass1() {
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onDriveAutoLockChanged(boolean enabled) {
            CarBodyBaseViewModel.this.handleDriveLockChanged(enabled);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onParkingAutoUnlockChanged(boolean enabled) {
            CarBodyBaseViewModel.this.handleParkUnlockChanged(enabled);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onNearAutoUnlockChanged(boolean enabled) {
            CarBodyBaseViewModel.this.handleNearAutoUnlock(enabled);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onLeaveAutoLockChanged(boolean enabled) {
            CarBodyBaseViewModel.this.handleLeaveAutoLock(enabled);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onPollingOpenCfgChanged(boolean enabled) {
            CarBodyBaseViewModel.this.handleInductionLock(enabled);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onUnlockResponseChanged(int type) {
            CarBodyBaseViewModel.this.handleUnlockTypeChanged(type);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onChildModeChanged(boolean enable) {
            CarBodyBaseViewModel.this.handleChildModeChanged(enable);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onChildLockChanged(int state) {
            CarBodyBaseViewModel.this.parseChildLockState(state);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onLeftChildLockChanged(boolean locked) {
            CarBodyBaseViewModel.this.handleLeftChildLockChanged(locked);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onLeftDoorHotKeyChanged(boolean locked) {
            CarBodyBaseViewModel.this.handleLeftHotKeyChanged(locked);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onRightDoorHotKeyChanged(boolean locked) {
            CarBodyBaseViewModel.this.handleRightHotKeyChanged(locked);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onRightChildLockChanged(boolean locked) {
            CarBodyBaseViewModel.this.handleRightChildLockChanged(locked);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onWiperIntervalChanged(int wiper) {
            CarBodyBaseViewModel.this.handleWiperIntervalModeChanged(wiper);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onWiperSensitivityChanged(int level) {
            CarBodyBaseViewModel.this.handleWiperSensitivityChanged(level);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onWiperRepairModeChanged(boolean mode) {
            CarBodyBaseViewModel.this.handleWiperRepairModeChanged(mode);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onRearWiperRepairModeChanged(boolean mode) {
            CarBodyBaseViewModel.this.handleRearWiperRepairModeChanged(mode);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onChargePortChanged(boolean isLeft, final int state, boolean isMock) {
            LogUtils.d(CarBodyBaseViewModel.TAG, "onChargePortChanged:isLeft=" + isLeft + ",state=" + state + ",isMock=" + isMock);
            int i = isLeft ? 1 : 2;
            if (CarBodyBaseViewModel.this.mResetTaskCache.get(i) != null) {
                LogUtils.d(CarBodyBaseViewModel.TAG, "onChargePortChanged: ChargePortResetTask is Running, can not update UI");
                return;
            }
            if (state == 1) {
                if (CarBodyBaseViewModel.this.mTimeoutTaskCache.get(i) == null) {
                    LogUtils.d(CarBodyBaseViewModel.TAG, "onChargePortChanged: start ChargePortTimeoutTask");
                    ChargePortTimeoutTask chargePortTimeoutTask = new ChargePortTimeoutTask(CarBodyBaseViewModel.this, isLeft, null);
                    CarBodyBaseViewModel.this.mHandler.postDelayed(chargePortTimeoutTask, CarBodyBaseViewModel.CHARGE_PORT_TIMEOUT);
                    CarBodyBaseViewModel.this.mTimeoutTaskCache.put(i, chargePortTimeoutTask);
                } else {
                    LogUtils.d(CarBodyBaseViewModel.TAG, "onChargePortChanged: ChargePortTimeoutTask is running");
                }
            } else {
                ChargePortTimeoutTask chargePortTimeoutTask2 = (ChargePortTimeoutTask) CarBodyBaseViewModel.this.mTimeoutTaskCache.get(i);
                if (chargePortTimeoutTask2 != null) {
                    LogUtils.d(CarBodyBaseViewModel.TAG, "onChargePortChanged: remove ChargePortTimeoutTask");
                    CarBodyBaseViewModel.this.mHandler.removeCallbacks(chargePortTimeoutTask2);
                    CarBodyBaseViewModel.this.mTimeoutTaskCache.remove(i);
                }
            }
            CarBodyBaseViewModel.this.handleChargePortChanged(isLeft, state);
            QuickSettingManager.getInstance().onSignalCallbackSingleThread(isLeft ? QuickSettingConstants.KEY_AC_CHARGE_INT : QuickSettingConstants.KEY_DC_CHARGE_INT, new Supplier() { // from class: com.xiaopeng.carcontrol.viewmodel.carsettings.-$$Lambda$CarBodyBaseViewModel$1$t3ff6t_s4dP74z_ryZb2nqE1ydA
                @Override // java.util.function.Supplier
                public final Object get() {
                    Integer valueOf;
                    valueOf = Integer.valueOf(state);
                    return valueOf;
                }
            });
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onStealthModeChanged(boolean enable) {
            CarBodyBaseViewModel.this.handleStealModeChanged(enable);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onNfcStopSwChanged(boolean enable) {
            CarBodyBaseViewModel.this.handleNfcStopSwChanged(enable);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onCwcSwChanged(boolean enable) {
            CarBodyBaseViewModel.this.handleCwcSwChanged(enable);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onCwcFRSwChanged(boolean enable) {
            CarBodyBaseViewModel.this.handleCwcFRSwChanged(enable);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onCwcRLSwChanged(boolean enable) {
            CarBodyBaseViewModel.this.handleCwcRLSwChanged(enable);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onCwcRRSwChanged(boolean enable) {
            CarBodyBaseViewModel.this.handleCwcRRSwChanged(enable);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onArsWorkModeChanged(int mode) {
            CarBodyBaseViewModel.this.handleArsWorkModeChanged(mode);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel
    public void enterSteerScene() {
        this.mSteerControlStrategy.enterScene();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel
    public void exitSteerScene() {
        this.mSteerControlStrategy.exitScene();
    }

    /* loaded from: classes2.dex */
    private class ChargePortTimeoutTask implements Runnable {
        private final boolean isLeft;

        /* synthetic */ ChargePortTimeoutTask(CarBodyBaseViewModel carBodyBaseViewModel, boolean z, AnonymousClass1 anonymousClass1) {
            this(z);
        }

        private ChargePortTimeoutTask(boolean isLeft) {
            this.isLeft = isLeft;
        }

        @Override // java.lang.Runnable
        public void run() {
            int i = this.isLeft ? 1 : 2;
            int chargePortUnlock = CarBodyBaseViewModel.this.getChargePortUnlock(i);
            LogUtils.d(CarBodyBaseViewModel.TAG, "ChargePortTimeoutTask: isLeft = " + this.isLeft + ", curState = " + chargePortUnlock);
            if (chargePortUnlock == 1 || chargePortUnlock == -1) {
                CarBodyBaseViewModel.this.showResetDialog(this.isLeft);
                chargePortUnlock = -1;
            }
            CarBodyBaseViewModel.this.mTimeoutTaskCache.remove(i);
            CarBodyBaseViewModel.this.mBcmCallback.onChargePortChanged(this.isLeft, chargePortUnlock, true);
            CarBodyBaseViewModel.this.mHandler.removeCallbacks(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public CarBodyBaseViewModel() {
        CarClientWrapper carClientWrapper = CarClientWrapper.getInstance();
        this.mBcmController = (IBcmController) carClientWrapper.getController(CarClientWrapper.XP_BCM_SERVICE);
        this.mVcuController = (IVcuController) carClientWrapper.getController(CarClientWrapper.XP_VCU_SERVICE);
        this.mAvasController = (AvasController) carClientWrapper.getController(CarClientWrapper.XP_AVAS_SERVICE);
        this.mCiuController = (ICiuController) CarClientWrapper.getInstance().getController(CarClientWrapper.XP_CIU_SERVICE);
        this.mTboxController = (ITboxController) CarClientWrapper.getInstance().getController(CarClientWrapper.XP_TBOX_SERVICE);
        this.mEspController = (IEspController) CarClientWrapper.getInstance().getController(CarClientWrapper.XP_ESP_SERVICE);
        if (CarBaseConfig.getInstance().isSupportControlSteer()) {
            this.mSteerControlStrategy = new SteerControlStrategy();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void initChargePort() {
        LogUtils.d(TAG, "initChargePort");
        if (App.isMainProcess()) {
            if (CarBaseConfig.getInstance().isSupportCtrlChargePort()) {
                if (CarBaseConfig.getInstance().isSingleChargePort()) {
                    if (CarBaseConfig.getInstance().isChargePortSignalErr()) {
                        int chargePortUnlock = this.mBcmController.getChargePortUnlock(1);
                        this.mBcmCallback.onChargePortChanged(true, chargePortUnlock != 1 ? chargePortUnlock : -1, true);
                        return;
                    }
                    int chargePortUnlock2 = this.mBcmController.getChargePortUnlock(2);
                    this.mBcmCallback.onChargePortChanged(false, chargePortUnlock2 != 1 ? chargePortUnlock2 : -1, true);
                    return;
                }
                int chargePortUnlock3 = this.mBcmController.getChargePortUnlock(1);
                if (chargePortUnlock3 == 1) {
                    chargePortUnlock3 = -1;
                }
                this.mBcmCallback.onChargePortChanged(true, chargePortUnlock3, true);
                int chargePortUnlock4 = this.mBcmController.getChargePortUnlock(2);
                this.mBcmCallback.onChargePortChanged(false, chargePortUnlock4 != 1 ? chargePortUnlock4 : -1, true);
                return;
            }
            return;
        }
        App.getInstance().getContentResolver().registerContentObserver(CarControl.Quick.getUriFor(CarControl.Quick.LEFT_CHARGE_PORT_STATE), false, this.mContentObserver);
        App.getInstance().getContentResolver().registerContentObserver(CarControl.Quick.getUriFor(CarControl.Quick.RIGHT_CHARGE_PORT_STATE), false, this.mContentObserver);
        handleChargePortChanged(true, CarControl.Quick.getInt(App.getInstance().getContentResolver(), CarControl.Quick.LEFT_CHARGE_PORT_STATE, -1));
        handleChargePortChanged(false, CarControl.Quick.getInt(App.getInstance().getContentResolver(), CarControl.Quick.RIGHT_CHARGE_PORT_STATE, -1));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel
    public void setDriveAutoLock(boolean enable) {
        this.mBcmController.setDriveAutoLock(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel
    public boolean getDriveAutoLock() {
        return this.mBcmController.getDriveAutoLock();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel
    public void setParkingAutoUnlock(boolean enable) {
        this.mBcmController.setParkingAutoUnlock(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel
    public boolean getNearUnlock() {
        return this.mBcmController.getNearAutoUnlock();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel
    public void setNearUnlock(boolean enable) {
        this.mBcmController.setNearAutoUnlock(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel
    public boolean getLeaveAutoLock() {
        return this.mBcmController.getLeaveAutoLock();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel
    public boolean getInductionLock() {
        return this.mBcmController.getPollingOpenCfg();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel
    public void setInductionLock(boolean enable) {
        this.mBcmController.setPollingOpenCfg(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel
    public void setLeaveAutoLock(boolean enable) {
        this.mBcmController.setLeaveAutoLock(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel
    public boolean getParkingAutoUnlock() {
        return this.mBcmController.getParkingAutoUnlock();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel
    public void setUnlockResponse(int type) {
        if (!CarBaseConfig.getInstance().isNewAvasArch()) {
            this.mBcmController.setUnlockResponse(type);
        } else if (type == 0 || type == 1) {
            this.mBcmController.setUnlockResponse(type);
            setUnlockResponseForAvas(false);
        } else if (type == 2) {
            this.mBcmController.setUnlockResponse(type);
            setUnlockResponseForAvas(true);
        } else {
            LogUtils.w(TAG, "Un-support type: " + type, false);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel
    public int getUnlockResponse() {
        return this.mBcmController.getUnlockResponseSp();
    }

    void setUnlockResponseForAvas(boolean enable) {
        if (CarBaseConfig.getInstance().isNewAvasArch()) {
            LogUtils.d(TAG, "setUnlockResponseForAvas " + enable, false);
            if (CarBaseConfig.getInstance().isSupportNewAvasUnlockResponse()) {
                this.mAvasController.setLockAvasSw(enable);
                return;
            }
            this.mAvasController.setAvasWakeWaitSwitch(enable);
            this.mAvasController.setAvasWakeWaitFullChargeSwitch(enable);
            this.mAvasController.setAvasSleepSwitch(enable);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel
    public void setChildModeEnable(boolean enable) {
        LogUtils.i(TAG, "set child mode enable: " + enable);
        this.mBcmController.setChildModeEnable(enable, true);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel
    public boolean isChildModeEnable() {
        return this.mBcmController.isChildModeEnable();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel
    public void setChildLock(boolean leftSide, boolean lock) {
        this.mBcmController.setChildLock(leftSide, lock, true);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel
    public void setChildLock(boolean leftSide, boolean lock, boolean needSave) {
        this.mBcmController.setChildLock(leftSide, lock, needSave);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel
    public void setAllChildLock(boolean lock, boolean needSave) {
        this.mBcmController.setAllChildLock(lock, needSave);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel
    public boolean isLeftChildLocked() {
        return this.mBcmController.getChildLeftLock();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel
    public boolean isRightChildLocked() {
        return this.mBcmController.getChildRightLock();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel
    public void setLeftDoorHotKey(boolean enable) {
        this.mBcmController.setLeftDoorHotKey(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel
    public void setRightDoorHotKey(boolean enable) {
        this.mBcmController.setRightDoorHotKey(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel
    public boolean isLeftDoorHotKeyEnable() {
        return this.mBcmController.isLeftDoorHotKeyEnable();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel
    public boolean isRightDoorHotKeyEnable() {
        return this.mBcmController.isRightDoorHotKeyEnable();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel
    public void openCarBonnet() {
        this.mBcmController.openCarBonnet();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setWiperInterval(int interval) {
        this.mBcmController.setWiperInterval(interval);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getWiperInterval() {
        return this.mBcmController.getWiperInterval();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel
    public void setWiperRepairMode(boolean mode) {
        if (mode == getWiperRepairMode()) {
            LogUtils.d(TAG, "setWiperRepairMode: target state == current state = " + mode, false);
            handleWiperRepairModeChanged(mode);
            return;
        }
        this.mBcmController.setWiperRepairMode(mode);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel
    public boolean getWiperRepairMode() {
        return this.mBcmController.getWiperRepairMode();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel
    public void setRearWiperRepairMode(boolean mode) {
        if (mode == getRearWiperRepairMode()) {
            LogUtils.d(TAG, "setRearWiperRepairMode: target state == current state = " + mode, false);
            handleRearWiperRepairModeChanged(mode);
            return;
        }
        this.mBcmController.setRearWiperRepairMode(mode);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel
    public boolean getRearWiperRepairMode() {
        return this.mBcmController.getRearWiperRepairMode();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel
    public boolean isWiperSpeedSwitchOff() {
        return this.mBcmController.isWiperSpeedSwitchOff();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel
    public boolean isRearWiperSpeedSwitchOff() {
        return this.mBcmController.isRearWiperSpeedSwitchOff();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel
    public boolean isWiperFault() {
        return this.mBcmController.isWiperFault();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel
    public boolean isRearWiperFault() {
        return this.mBcmController.isRearWiperFault();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel
    public boolean isFWiperActiveSt() {
        return this.mBcmController.getFWiperActiveSt() == 1;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel
    public void setEbwEnable(boolean enable) {
        this.mBcmController.setEbwEnable(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel
    public boolean isEbwEnabled() {
        return this.mBcmController.isEbwEnabled();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel
    public boolean isChargeGunUnlinked(boolean isLeft) {
        int chargeGunStatus = this.mVcuController.getChargeGunStatus();
        LogUtils.i(TAG, "isChargeGunUnlinked: isLeft=" + isLeft + ", chargeGunStatus=" + chargeGunStatus);
        return CarBaseConfig.getInstance().isSingleChargePort() ? (chargeGunStatus == 1 || chargeGunStatus == 2 || chargeGunStatus == 3 || chargeGunStatus == 4 || chargeGunStatus == 5) ? false : true : isLeft ? (chargeGunStatus == 1 || chargeGunStatus == 3 || chargeGunStatus == 4 || chargeGunStatus == 5) ? false : true : (chargeGunStatus == 2 || chargeGunStatus == 3) ? false : true;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel
    public boolean isChargePortResetable(boolean isLeft) {
        return getChargePortUnlock(isLeft ? 1 : 2) == -1 && this.mVcuController.getCarSpeed() < 3.0f && isChargeGunUnlinked(isLeft);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel
    public boolean isChargePortEnable(boolean isLeft, boolean showToast) {
        int chargePortDisableString = getChargePortDisableString(isLeft);
        if (chargePortDisableString != 0) {
            if (showToast) {
                NotificationHelper.getInstance().showToast(chargePortDisableString, true);
                return false;
            }
            return false;
        }
        return true;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel
    public int getChargePortDisableString(boolean isLeft) {
        int chargePortUnlock = getChargePortUnlock(isLeft ? 1 : 2);
        if (chargePortUnlock == -1) {
            if (this.mVcuController.getCarSpeed() >= 3.0f) {
                return R.string.charge_port_unable_with_running;
            }
            if (!isChargeGunUnlinked(isLeft)) {
                return R.string.charge_port_unable_with_charge_gun_linked;
            }
            return R.string.charge_port_unknown_default;
        }
        if (chargePortUnlock != 0) {
            if (chargePortUnlock == 1) {
                return R.string.charge_port_running;
            }
            if (chargePortUnlock != 2) {
                if (chargePortUnlock == 3) {
                    return isLeft ? R.string.charge_port_ac_fault_prompt : R.string.charge_port_dc_fault_prompt;
                }
            } else if (BaseFeatureOption.getInstance().isSupportChargePortPGearConstrain()) {
                if (this.mVcuController.getGearLevel() != 4) {
                    return R.string.charge_port_not_p_gear;
                }
            } else if (this.mVcuController.getCarSpeed() >= 3.0f) {
                return R.string.charge_port_unable_with_running;
            }
        } else if (!isChargeGunUnlinked(isLeft)) {
            return R.string.charge_port_unable_with_charge_gun_linked;
        }
        return 0;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel
    public void setChargePortUnlock(final int port, final boolean unlock) {
        final boolean z = port == 1;
        String str = TAG;
        LogUtils.d(str, "setChargePortUnlock: isLeft = " + z + ", unlock = " + unlock);
        int chargePortUnlock = getChargePortUnlock(port);
        LogUtils.d(str, "test case: curPortStatus = " + chargePortUnlock);
        if ((chargePortUnlock == 0 && unlock) || (chargePortUnlock == 2 && !unlock)) {
            LogUtils.d(str, "onChargePortControl: port " + port + "is already unlock: " + unlock);
        } else if (App.isMainProcess()) {
            this.mBcmCallback.onChargePortChanged(z, 1, true);
            LogUtils.d(str, "do setChargePortUnlock");
            this.mBcmController.setChargePortUnlock(port, unlock ? 1 : 0);
        } else {
            ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.carsettings.-$$Lambda$CarBodyBaseViewModel$i2nir-PPTIfPBwC7Lb0rGfnS3Yg
                @Override // java.lang.Runnable
                public final void run() {
                    boolean z2 = z;
                    CarControl.Quick.putBool(App.getInstance().getContentResolver(), isLeft ? CarControl.Quick.LEFT_CHARGE_PORT : CarControl.Quick.RIGHT_CHARGE_PORT, unlock);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showResetDialog(final boolean isLeft) {
        int chargePortRetryEnable = FunctionModel.getInstance().getChargePortRetryEnable();
        LogUtils.i(TAG, "showResetDialog: retryTime=" + chargePortRetryEnable);
        if (chargePortRetryEnable < 3) {
            FunctionModel.getInstance().setChargePortRetryTime(chargePortRetryEnable + 1);
            NotificationHelper.getInstance().showDialog(ResUtils.getString(R.string.charge_port_control_failed), ResUtils.getString(R.string.charge_port_control_failed_content), ResUtils.getString(R.string.btn_cancel), null, ResUtils.getString(R.string.btn_reset), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.viewmodel.carsettings.-$$Lambda$CarBodyBaseViewModel$WZbgIIeMUbSGND7k_u4B3OK3TVU
                @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                public final void onClick(XDialog xDialog, int i) {
                    CarBodyBaseViewModel.this.lambda$showResetDialog$1$CarBodyBaseViewModel(isLeft, xDialog, i);
                }
            }, VuiManager.SCENE_CHARGE_PORT_RESET);
        }
    }

    public /* synthetic */ void lambda$showResetDialog$1$CarBodyBaseViewModel(final boolean isLeft, XDialog xDialog, int i) {
        resetChargePort(isLeft);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel
    public void resetChargePort(final boolean isLeft) {
        String str = TAG;
        LogUtils.d(str, "resetChargePort: isLeft=" + isLeft);
        if (App.isMainProcess()) {
            int i = isLeft ? 1 : 2;
            if (this.mResetTaskCache.get(i) != null) {
                LogUtils.d(str, "resetChargePort: ChargePortResetTask is running, isLeft = " + isLeft);
                return;
            }
            this.mBcmCallback.onChargePortChanged(isLeft, 1, true);
            LogUtils.d(str, "resetChargePort: start ChargePortResetTask");
            ChargePortResetTask chargePortResetTask = new ChargePortResetTask(this, isLeft, null);
            this.mHandler.post(chargePortResetTask);
            this.mResetTaskCache.put(i, chargePortResetTask);
            return;
        }
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.carsettings.-$$Lambda$CarBodyBaseViewModel$Asj_Z14mIT_YrWO9pPcghxjcx1g
            @Override // java.lang.Runnable
            public final void run() {
                CarControl.Quick.putBool(App.getInstance().getContentResolver(), CarControl.Quick.RESET_CHARGE_PORT, isLeft);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class ChargePortResetTask implements Runnable {
        private final boolean isLeft;
        private int mRepeatCount;

        /* synthetic */ ChargePortResetTask(CarBodyBaseViewModel carBodyBaseViewModel, boolean z, AnonymousClass1 anonymousClass1) {
            this(z);
        }

        private ChargePortResetTask(boolean isLeft) {
            this.mRepeatCount = 0;
            this.isLeft = isLeft;
        }

        @Override // java.lang.Runnable
        public void run() {
            int i = this.isLeft ? 1 : 2;
            int i2 = this.mRepeatCount;
            if (i2 == 0) {
                LogUtils.i(CarBodyBaseViewModel.TAG, "ChargePortResetTask: unlock, isLeft: " + this.isLeft + ", repeatCount: " + this.mRepeatCount);
                CarBodyBaseViewModel.this.mBcmController.setChargePortUnlock(i, 1);
                CarBodyBaseViewModel.this.mHandler.postDelayed(this, 6000L);
                this.mRepeatCount++;
            } else if (i2 == 1) {
                LogUtils.i(CarBodyBaseViewModel.TAG, "ChargePortResetTask: lock, isLeft: " + this.isLeft + ", repeatCount: " + this.mRepeatCount);
                CarBodyBaseViewModel.this.mBcmController.setChargePortUnlock(i, 0);
                CarBodyBaseViewModel.this.mHandler.postDelayed(this, 6000L);
                this.mRepeatCount++;
            } else {
                int chargePortUnlock = CarBodyBaseViewModel.this.mBcmController.getChargePortUnlock(this.isLeft ? 1 : 2);
                LogUtils.i(CarBodyBaseViewModel.TAG, "ChargePortResetTask: result state=" + chargePortUnlock + ", isLeft: " + this.isLeft);
                if (chargePortUnlock == 1 || chargePortUnlock == -1) {
                    CarBodyBaseViewModel.this.showResetDialog(this.isLeft);
                    chargePortUnlock = -1;
                }
                CarBodyBaseViewModel.this.mResetTaskCache.remove(i);
                CarBodyBaseViewModel.this.mBcmCallback.onChargePortChanged(this.isLeft, chargePortUnlock, true);
                CarBodyBaseViewModel.this.mHandler.removeCallbacks(this);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel
    public int getChargePortUnlock(int port) {
        int i;
        boolean z = port == 1;
        if (App.isMainProcess()) {
            i = z ? this.mLChargePortState : this.mRChargePortState;
        } else {
            i = CarControl.Quick.getInt(App.getInstance().getContentResolver(), z ? CarControl.Quick.LEFT_CHARGE_PORT_STATE : CarControl.Quick.RIGHT_CHARGE_PORT_STATE, -1);
        }
        LogUtils.d(TAG, "getChargePortUnlock: isLeft=" + z + ", state=" + i);
        return i;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel
    public void setStealthMode(boolean enable) {
        this.mBcmController.setStealthMode(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel
    public boolean getStealthMode() {
        return this.mBcmController.getStealthMode();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel
    public void setStopNfcCardSw(boolean enable) {
        this.mBcmController.setStopNfcCardSw(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel
    public boolean getStopNfcCardSw() {
        return this.mBcmController.getStopNfcCardSw();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setWiperSensitivity(int level) {
        this.mBcmController.setWiperSensitivity(level, true);
    }

    public int getWiperSensitivity() {
        return this.mBcmController.getWiperSensitivity();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel
    public void setCwcSwEnable(boolean isChecked) {
        setCwcSwEnable(isChecked, true);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel
    public void setCwcSwEnable(boolean isChecked, boolean storeEnable) {
        this.mBcmController.setCwcSwEnable(isChecked, storeEnable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel
    public boolean isCwcSwEnable() {
        return this.mBcmController.isCwcSwEnable();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel
    public void setCwcFRSwEnable(boolean isChecked) {
        this.mBcmController.setCwcFRSwEnable(isChecked);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel
    public boolean isCwcFRSwEnable() {
        return this.mBcmController.isCwcFRSwEnable();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel
    public void setCwcRLSwEnable(boolean enable) {
        this.mBcmController.setCwcRLSwEnable(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel
    public boolean isCwcRLSwEnable() {
        return this.mBcmController.isCwcRLSwEnable();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel
    public void setCwcRRSwEnable(boolean enable) {
        this.mBcmController.setCwcRRSwEnable(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel
    public boolean isCwcRRSwEnable() {
        return this.mBcmController.isCwcRRSwEnable();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel
    public boolean onSteerControl(int keyCode) {
        return this.mSteerControlStrategy.onSteerControl(keyCode);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel
    public void cancelAllControlSteer() {
        this.mSteerControlStrategy.cancelControl();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel
    public void setArsWorkMode(int mode) {
        this.mBcmController.setArsWorkMode(mode);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel
    public int getArsWorkMode() {
        return this.mBcmController.getArsWorkMode();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel
    public void moveSteerToExhibitionPos() {
        this.mBcmController.setSteerPos(GlobalConstant.DEFAULT.STEER_EXHIBITION_POS);
        this.mBcmController.saveSteerPos(GlobalConstant.DEFAULT.STEER_EXHIBITION_POS);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void parseChildLockState(int state) {
        boolean z = false;
        boolean z2 = state == 4 || state == 3;
        if (state == 4 || state == 2) {
            z = true;
        }
        handleChildLockChanged(z2, z);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void handleChargePortChanged(boolean isLeft, int state) {
        LogUtils.d(TAG, "handleChargePortChanged:isLeft=" + isLeft + ", state=" + state);
        if (App.isMainProcess()) {
            if (isLeft) {
                this.mLChargePortState = state;
            } else {
                this.mRChargePortState = state;
            }
            CarControl.Quick.putInt(App.getInstance().getContentResolver(), isLeft ? CarControl.Quick.LEFT_CHARGE_PORT_STATE : CarControl.Quick.RIGHT_CHARGE_PORT_STATE, state);
        }
    }
}
