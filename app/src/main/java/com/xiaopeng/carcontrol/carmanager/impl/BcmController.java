package com.xiaopeng.carcontrol.carmanager.impl;

import android.car.Car;
import android.car.CarNotConnectedException;
import android.car.hardware.CarEcuManager;
import android.car.hardware.CarPropertyValue;
import android.car.hardware.bcm.CarBcmManager;
import android.car.hardware.dhc.CarDhcManager;
import android.content.ContentResolver;
import android.database.ContentObserver;
import android.net.Uri;
import android.provider.Settings;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.BusinessConstant;
import com.xiaopeng.carcontrol.GlobalConstant;
import com.xiaopeng.carcontrol.carmanager.BaseCarController;
import com.xiaopeng.carcontrol.carmanager.CarClientWrapper;
import com.xiaopeng.carcontrol.carmanager.controller.IBcmController;
import com.xiaopeng.carcontrol.carmanager.impl.oldarch.BcmOldController;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import com.xiaopeng.carcontrol.lang.NoConcurrenceRunnable;
import com.xiaopeng.carcontrol.model.FunctionModel;
import com.xiaopeng.carcontrol.provider.CarControl;
import com.xiaopeng.carcontrol.util.CarStatusUtils;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.xvs.xid.sync.api.ISync;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public class BcmController extends BaseCarController<CarBcmManager, IBcmController.Callback> implements IBcmController {
    private static final String CHILD_MODE_SW = "child_mode_sw";
    private static final String LEFT_DOOR_HOT_KEY_SW = "left_door_hot_key_sw";
    private static final String RIGHT_DOOR_HOT_KEY_SW = "right_door_hot_key_sw";
    protected static final String TAG = "BcmController";
    private CarDhcManager mCarDhcManager;
    private ContentObserver mContentObserver;
    private final CarBcmManager.CarBcmEventCallback mCarBcmEventCallback = new CarBcmManager.CarBcmEventCallback() { // from class: com.xiaopeng.carcontrol.carmanager.impl.BcmController.1
        public void onErrorEvent(int propertyId, int zone) {
        }

        public void onChangeEvent(CarPropertyValue carPropertyValue) {
            switch (carPropertyValue.getPropertyId()) {
                case 557849660:
                case 557849661:
                case 557849662:
                case 557849663:
                    break;
                default:
                    LogUtils.i(BcmController.TAG, "onChangeEvent: " + carPropertyValue, false);
                    break;
            }
            BcmController.this.handleCarEventsUpdate(carPropertyValue);
        }
    };
    private final CarDhcManager.CarDhcEventCallback mCarDhcEventCallback = new CarDhcManager.CarDhcEventCallback() { // from class: com.xiaopeng.carcontrol.carmanager.impl.BcmController.2
        public void onChangeEvent(CarPropertyValue carPropertyValue) {
            LogUtils.i(BcmController.TAG, "onDhcChangeEvent: " + carPropertyValue, false);
            BcmController.this.handleCarEventsUpdate(carPropertyValue);
        }

        public void onErrorEvent(int propertyId, int zone) {
            LogUtils.e(BcmController.TAG, "onDhcErrorEvent: " + propertyId, false);
        }
    };
    private Integer mUnlockResponse = null;
    private Boolean mIsHighSpdWinClose = null;
    NoConcurrenceRunnable mFixElcTrunkState = new NoConcurrenceRunnable(new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$BcmController$MOQHaG5jOhllk9zGssvhFyi-CKc
        @Override // java.lang.Runnable
        public final void run() {
            BcmController.this.lambda$new$10$BcmController();
        }
    });
    protected final List<Integer> mDhcPropIds = getDhcIdsRegisterPropIds();

    private int convertSunShadePos(int pos) {
        if (pos > 100) {
            return 100;
        }
        if (pos < 0) {
            return 0;
        }
        return pos;
    }

    private int measureSeatVentLevel(int level) {
        if (level < 0) {
            return 0;
        }
        if (level > 3) {
            return 3;
        }
        return level;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static boolean parseBcmStatus(int status) {
        return status == 1;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static int parseCduSwitchCmd(boolean enable) {
        return enable ? 1 : 0;
    }

    private static int parseChildLockCmd(int lockCmd) {
        if (lockCmd != 2) {
            if (lockCmd != 3) {
                return lockCmd != 4 ? 1 : 6;
            }
            return 4;
        }
        return 5;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public boolean getCmsAutoBrightSw() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public float[] getCmsLocation(boolean ignoreCache) {
        return new float[4];
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public float[] getSavedCmsLocation() {
        return null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    public void initXuiManager() {
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public boolean isRearWiperFault() {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int measureSeatHeatLevel(int level) {
        if (level < 0) {
            return 0;
        }
        if (level > 3) {
            return 3;
        }
        return level;
    }

    protected int parseChildLockCmd(boolean leftSide, boolean lock) {
        return leftSide ? lock ? 4 : 2 : lock ? 5 : 3;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void saveCmsLocation(float[] pos) {
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setCmsAutoBrightSw(boolean enable) {
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setCmsLocation(float leftHPos, float leftVPos, float rightHPos, float rightVPos) {
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setCmsLocation(float[] pos) {
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setCmsLowSpdAssistSw(boolean enable) {
    }

    public BcmController(Car carClient) {
        if (this.mIsMainProcess) {
            return;
        }
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$BcmController$IuTWIjDRK0wBbedWO2ouAG2_VjY
            @Override // java.lang.Runnable
            public final void run() {
                BcmController.this.lambda$new$0$BcmController();
            }
        });
    }

    public /* synthetic */ void lambda$new$0$BcmController() {
        ContentResolver contentResolver = App.getInstance().getContentResolver();
        this.mUnlockResponse = Integer.valueOf(CarControl.PrivateControl.getInt(contentResolver, CarControl.PrivateControl.UNLOCK_RESPONSE, GlobalConstant.DEFAULT.UNLOCK_RESPONSE));
        this.mIsHighSpdWinClose = Boolean.valueOf(CarControl.PrivateControl.getBool(contentResolver, CarControl.PrivateControl.WIN_HIGH_SPD, false));
        registerContentObserver();
    }

    /* loaded from: classes.dex */
    public static class BcmControllerFactory {
        public static BcmController createCarController(Car carClient) {
            if (CarBaseConfig.getInstance().isNewBcmArch()) {
                return new BcmController(carClient);
            }
            return new BcmOldController(carClient);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    public void initCarManager(Car carClient) {
        CarDhcManager carDhcManager;
        LogUtils.d(TAG, "Init start");
        try {
            this.mCarManager = (CarBcmManager) carClient.getCarManager(CarClientWrapper.XP_BCM_SERVICE);
            if (this.mCarConfig.isSupportDhc()) {
                this.mCarDhcManager = (CarDhcManager) carClient.getCarManager("xp_dhc");
            }
            if (this.mCarManager != 0) {
                this.mCarManager.registerPropCallback(this.mPropertyIds, this.mCarBcmEventCallback);
            }
            if (this.mCarConfig.isSupportDhc() && (carDhcManager = this.mCarDhcManager) != null) {
                carDhcManager.registerPropCallback(this.mDhcPropIds, this.mCarDhcEventCallback);
            }
        } catch (CarNotConnectedException e) {
            LogUtils.e(TAG, (String) null, (Throwable) e, false);
        }
        LogUtils.d(TAG, "Init end");
    }

    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    protected List<Integer> getRegisterPropertyIds() {
        ArrayList arrayList = new ArrayList();
        if (this.mIsMainProcess || !BaseFeatureOption.getInstance().isSignalRegisterDynamically()) {
            arrayList.add(557849633);
        }
        if (((this.mCarConfig.isSupportUnity3D() && !this.mIsMainProcess) || (!this.mCarConfig.isSupportUnity3D() && this.mIsMainProcess)) && !BaseFeatureOption.getInstance().isSignalRegisterDynamically()) {
            arrayList.add(557849602);
        }
        if (this.mCarConfig.isSupportShowEbw() && this.mIsMainProcess) {
            arrayList.add(Integer.valueOf((int) IBcmController.ID_BCM_EBW));
        }
        if (this.mIsMainProcess || (this.mCarConfig.isSupportUnity3D() && !BaseFeatureOption.getInstance().isSignalRegisterDynamically())) {
            arrayList.add(557915161);
        }
        if (this.mCarConfig.isSupportDaytimeRunningLight()) {
            arrayList.add(557915329);
        }
        if (this.mIsMainProcess && this.mCarConfig.isSupportShowDriveAutoLock()) {
            arrayList.add(557849628);
        }
        arrayList.add(557849629);
        if (this.mIsMainProcess) {
            arrayList.add(557849609);
        }
        if ((!this.mCarConfig.isSupportElcTrunk() && (this.mIsMainProcess || !BaseFeatureOption.getInstance().isSignalRegisterDynamically())) || (this.mCarConfig.isSupportElcTrunk() && !this.mCarConfig.isNewBcmArch())) {
            arrayList.add(557849610);
        }
        if (this.mIsMainProcess) {
            arrayList.add(557849607);
            if (!BaseFeatureOption.getInstance().isSupportSmartOSFive()) {
                arrayList.add(557849637);
                arrayList.add(557849665);
                arrayList.add(557849638);
                arrayList.add(356517139);
                arrayList.add(557849701);
                if (this.mCarConfig.isSupportPsnSeatVent()) {
                    arrayList.add(356517140);
                }
            }
        }
        if (this.mCarConfig.isSupportLlu() && (this.mIsMainProcess || !BaseFeatureOption.getInstance().isSignalRegisterDynamically())) {
            if (!this.mCarConfig.isSupportNewParkLampFmB()) {
                arrayList.add(557849757);
            } else if (this.mCarConfig.isSupportSaberLightFeedBack()) {
                arrayList.add(557849820);
            }
            if (this.mCarConfig.isSupportParkingLampOutput()) {
                arrayList.add(557915294);
            }
        }
        if (this.mCarConfig.isSupportSensorTrunk() && ((this.mCarConfig.isSupportUnity3D() && !this.mIsMainProcess) || (!this.mCarConfig.isSupportUnity3D() && this.mIsMainProcess))) {
            arrayList.add(557849869);
        }
        if (this.mCarConfig.isSupportTrunkSetPosition() && ((this.mCarConfig.isSupportUnity3D() && !this.mIsMainProcess) || (!this.mCarConfig.isSupportUnity3D() && this.mIsMainProcess))) {
            arrayList.add(557849871);
        }
        if (this.mCarConfig.isSupportElcTrunk()) {
            arrayList.add(557849903);
            arrayList.add(557849946);
        }
        if (this.mCarConfig.isSupportTrunkOverHeatingProtected()) {
            arrayList.add(557849986);
        }
        addExtPropertyIds(arrayList);
        return arrayList;
    }

    protected void addExtPropertyIds(List<Integer> propertyIds) {
        if (this.mCarConfig.isNewBcmArch() && ((this.mCarConfig.isSupportUnity3D() && !this.mIsMainProcess) || (!this.mCarConfig.isSupportUnity3D() && this.mIsMainProcess))) {
            propertyIds.add(557849640);
        }
        if (this.mIsMainProcess) {
            propertyIds.add(559946855);
            propertyIds.add(559946856);
            propertyIds.add(559946854);
            propertyIds.add(559946857);
        }
        if (!BaseFeatureOption.getInstance().isSupportSmartOSFive() && this.mCarConfig.isSupportRearSeatHeat()) {
            propertyIds.add(557849770);
            propertyIds.add(557849769);
            propertyIds.add(557849771);
            propertyIds.add(557849772);
        }
        if (this.mCarConfig.isSupportPollingOpenCfg()) {
            propertyIds.add(557849646);
        }
        if (this.mCarConfig.isSupportPollingLock()) {
            propertyIds.add(557849717);
            propertyIds.add(557849716);
        }
        if (((this.mCarConfig.isSupportUnity3D() && !this.mIsMainProcess) || !this.mCarConfig.isSupportUnity3D()) && !BaseFeatureOption.getInstance().isSignalRegisterDynamically()) {
            propertyIds.add(557849626);
            propertyIds.add(289410561);
        }
        if (this.mIsMainProcess && BaseFeatureOption.getInstance().isSupportLowBeamOffConfirm()) {
            propertyIds.add(557849987);
        }
        if (this.mCarConfig.isSupportSdc()) {
            if ((this.mCarConfig.isSupportUnity3D() && !this.mIsMainProcess) || !this.mCarConfig.isSupportUnity3D()) {
                propertyIds.add(557849773);
                propertyIds.add(557849784);
                propertyIds.add(557849785);
                propertyIds.add(557849775);
                propertyIds.add(557849939);
            }
            if ((this.mCarConfig.isSupportUnity3D() && this.mIsMainProcess) || !this.mCarConfig.isSupportUnity3D()) {
                propertyIds.add(557849788);
                propertyIds.add(557849789);
                propertyIds.add(557849807);
                propertyIds.add(557849808);
                propertyIds.add(557849798);
                propertyIds.add(557849799);
            }
        }
        if (this.mCarConfig.isSupportSlideDoor()) {
            propertyIds.add(557849979);
            propertyIds.add(557849982);
            propertyIds.add(557849980);
            propertyIds.add(557849983);
            propertyIds.add(557849981);
            propertyIds.add(557849984);
        }
        if (this.mCarConfig.isSupportLightMeHomeNew()) {
            propertyIds.add(557849796);
        } else if (this.mIsMainProcess) {
            propertyIds.add(557849627);
            propertyIds.add(557849650);
        }
        if (this.mCarConfig.isSupportTurnActive() && this.mIsMainProcess && !BaseFeatureOption.getInstance().isSignalRegisterDynamically()) {
            propertyIds.add(557915328);
        }
        if (this.mCarConfig.isSupportWiperSenCfg()) {
            propertyIds.add(557849754);
            if (this.mCarConfig.isWiperAdjustByHardKey()) {
                propertyIds.add(557849930);
                propertyIds.add(557849931);
            }
        }
        if (this.mCarConfig.isSupportWiperRepair() && ((!this.mIsMainProcess && this.mCarConfig.isSupportUnity3D()) || !this.mCarConfig.isSupportUnity3D())) {
            propertyIds.add(557849611);
        }
        if (this.mCarConfig.isSupportRearWiper() && ((!this.mIsMainProcess && this.mCarConfig.isSupportUnity3D()) || !this.mCarConfig.isSupportUnity3D())) {
            propertyIds.add(557849860);
        }
        if ((!this.mIsMainProcess && this.mCarConfig.isSupportUnity3D()) || !this.mCarConfig.isSupportUnity3D()) {
            propertyIds.add(557849630);
        }
        if (this.mCarConfig.isSupportChildLock()) {
            if (this.mCarConfig.isSupportNewChildLock()) {
                if (this.mIsMainProcess) {
                    propertyIds.add(557849858);
                    propertyIds.add(557849859);
                }
            } else if ((this.mCarConfig.isSupportUnity3D() && (!this.mIsMainProcess || this.mCarConfig.isSupportChildMode())) || (!this.mCarConfig.isSupportUnity3D() && this.mIsMainProcess)) {
                propertyIds.add(557849675);
            }
        }
        if (this.mCarConfig.isSupportAutoWindowLock() && ((!this.mIsMainProcess && this.mCarConfig.isSupportUnity3D()) || !this.mCarConfig.isSupportUnity3D())) {
            propertyIds.add(557849715);
        }
        if (this.mCarConfig.isSupportWindowLock()) {
            propertyIds.add(557848731);
        }
        if (this.mCarConfig.isSupportCtrlBonnet() && (this.mIsMainProcess || !BaseFeatureOption.getInstance().isSignalRegisterDynamically())) {
            propertyIds.add(557849641);
        }
        if (this.mCarConfig.isSupportCtrlChargePort() && this.mIsMainProcess) {
            if (!CarBaseConfig.getInstance().isSingleChargePort() || CarBaseConfig.getInstance().isChargePortSignalErr()) {
                propertyIds.add(557849642);
            }
            if (!this.mCarConfig.isChargePortSignalErr()) {
                propertyIds.add(557849643);
            }
        }
        if (this.mCarConfig.isSupportCwc()) {
            if (this.mIsMainProcess) {
                propertyIds.add(557849713);
                propertyIds.add(557849714);
                if (!this.mCarConfig.isSupportUnity3D()) {
                    propertyIds.add(557849797);
                }
            } else {
                propertyIds.add(557849797);
            }
        }
        if (this.mCarConfig.isSupportCwcFR()) {
            propertyIds.add(557849988);
            propertyIds.add(557849989);
            propertyIds.add(557849990);
        }
        if (this.mCarConfig.isSupportCwcRL()) {
            propertyIds.add(557850049);
            propertyIds.add(557850050);
            propertyIds.add(557850051);
        }
        if (this.mCarConfig.isSupportCwcRR()) {
            propertyIds.add(557850052);
            propertyIds.add(557850053);
            propertyIds.add(557850054);
        }
        if (this.mCarConfig.isSupportNfc()) {
            if (CarStatusUtils.isEURegion()) {
                propertyIds.add(557849655);
            } else if (!this.mIsMainProcess) {
                propertyIds.add(557849655);
            }
        }
        if (this.mCarConfig.isSupportDigitalKeyTip() && this.mIsMainProcess) {
            propertyIds.add(557849787);
            propertyIds.add(557849786);
            propertyIds.add(557849759);
        }
        if (this.mCarConfig.isSupportSunShade()) {
            propertyIds.add(557849804);
            propertyIds.add(557849805);
        }
        if (this.mCarConfig.isSupportMirrorDown()) {
            propertyIds.add(557849676);
        }
        if (this.mCarConfig.isSupportBcmCtrlSeatBelt()) {
            propertyIds.add(557849601);
            propertyIds.add(557849648);
            propertyIds.add(557849635);
            propertyIds.add(557849636);
        }
        if (this.mCarConfig.isSupportWindowStates()) {
            propertyIds.add(560012320);
        }
        if ((!this.mCarConfig.isSupportAdjMirrorByCdu() && this.mCarConfig.isSupportMirrorMemory()) || this.mCarConfig.isSupportMirrorDown()) {
            propertyIds.add(557915345);
        }
        if (this.mCarConfig.isSupportAirSuspension()) {
            if ((this.mCarConfig.isSupportUnity3D() && !this.mIsMainProcess) || !this.mCarConfig.isSupportUnity3D()) {
                propertyIds.add(557849829);
                propertyIds.add(557858828);
            }
            propertyIds.add(557849895);
            if ((this.mCarConfig.isSupportUnity3D() && this.mIsMainProcess) || !this.mCarConfig.isSupportUnity3D()) {
                propertyIds.add(557858842);
                propertyIds.add(557858833);
                propertyIds.add(557858819);
                propertyIds.add(557858823);
            }
        }
        if (!BaseFeatureOption.getInstance().isSupportSmartOSFive()) {
            CarBaseConfig carBaseConfig = this.mCarConfig;
            if (CarBaseConfig.getInstance().isSupportSteerHeat()) {
                propertyIds.add(557849854);
            }
            if (this.mCarConfig.isSupportFrontMirrorHeat()) {
                propertyIds.add(557849846);
            }
        }
        if (this.mCarConfig.isSupportLampHeight() && this.mIsMainProcess) {
            propertyIds.add(557849851);
            propertyIds.add(557849852);
        }
        if (this.mCarConfig.isSupportTrailerHook()) {
            propertyIds.add(557858305);
            propertyIds.add(557858310);
            propertyIds.add(557858308);
            propertyIds.add(557858307);
        }
        if (this.mCarConfig.isSupportNewDhc() && ((!this.mIsMainProcess && this.mCarConfig.isSupportUnity3D()) || !this.mCarConfig.isSupportUnity3D())) {
            propertyIds.add(557849830);
        }
        if (BaseFeatureOption.getInstance().isSupportMirrorAutoFoldOff()) {
            propertyIds.add(557849948);
        }
        if (((this.mCarConfig.isSupportUnity3D() && !this.mIsMainProcess) || (!this.mCarConfig.isSupportUnity3D() && this.mIsMainProcess)) && this.mCarConfig.isSupportDomeLight()) {
            if (this.mCarConfig.isSupportDomeLightIndependentCtrl()) {
                propertyIds.add(557849864);
                propertyIds.add(557849865);
                propertyIds.add(557849866);
                propertyIds.add(557849861);
                if (this.mCarConfig.isSupportDomeLightThirdRow()) {
                    propertyIds.add(557850055);
                    propertyIds.add(557850056);
                }
            }
            if (this.mCarConfig.isSupportDomeLightBrightnessCtrl()) {
                propertyIds.add(557849863);
            }
        }
        if (this.mCarConfig.isSupportCms() && ((this.mCarConfig.isSupportUnity3D() && !this.mIsMainProcess) || (!this.mCarConfig.isSupportUnity3D() && this.mIsMainProcess))) {
            propertyIds.add(557859331);
            propertyIds.add(557859332);
            propertyIds.add(557859333);
            propertyIds.add(557859335);
            propertyIds.add(557859345);
        }
        if (this.mCarConfig.isSupportIms()) {
            propertyIds.add(557860866);
            propertyIds.add(557860867);
            propertyIds.add(557860865);
            propertyIds.add(557860871);
            propertyIds.add(557860870);
            propertyIds.add(557860869);
        }
        if (this.mCarConfig.isSupportArs()) {
            propertyIds.add(557859844);
            propertyIds.add(557859841);
            propertyIds.add(557859842);
            propertyIds.add(557859843);
            propertyIds.add(557859846);
        }
        if (this.mCarConfig.isSupportRearLogoLight()) {
            propertyIds.add(557849985);
        }
        if (this.mCarConfig.isSupportCarpetLightWelcomeMode()) {
            propertyIds.add(557849991);
        }
        if (this.mCarConfig.isSupportPollingLightWelcomeMode()) {
            propertyIds.add(557849992);
        }
    }

    protected List<Integer> getDhcIdsRegisterPropIds() {
        ArrayList arrayList = new ArrayList();
        if (this.mCarConfig.isSupportDhc() && !this.mCarConfig.isSupportNewDhc() && ((this.mCarConfig.isSupportUnity3D() && !this.mIsMainProcess) || !this.mCarConfig.isSupportUnity3D())) {
            arrayList.add(557849711);
        }
        return arrayList;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    protected void disconnect() {
        CarDhcManager carDhcManager;
        List<Integer> list;
        if (this.mCarManager != 0) {
            try {
                this.mCarManager.unregisterPropCallback(this.mPropertyIds, this.mCarBcmEventCallback);
            } catch (CarNotConnectedException e) {
                LogUtils.e(TAG, (String) null, (Throwable) e, false);
            }
        }
        if (!this.mCarConfig.isSupportDhc() || (carDhcManager = this.mCarDhcManager) == null || (list = this.mDhcPropIds) == null) {
            return;
        }
        try {
            carDhcManager.unregisterPropCallback(list, this.mCarDhcEventCallback);
        } catch (CarNotConnectedException e2) {
            LogUtils.e(TAG, (String) null, (Throwable) e2, false);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    public void handleCarEventsUpdate(final CarPropertyValue<?> value) {
        switch (value.getPropertyId()) {
            case 557846584:
            case 557849759:
            case 557849786:
            case 557849787:
                SINGLE_THREAD_POOL.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$BcmController$LmmlNruMav2ta4pFQhk0CHp7MH4
                    @Override // java.lang.Runnable
                    public final void run() {
                        BcmController.this.lambda$handleCarEventsUpdate$1$BcmController(value);
                    }
                });
                return;
            default:
                ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$BcmController$X-0eURD8oB3xrJvfGcrMPCdiUfo
                    @Override // java.lang.Runnable
                    public final void run() {
                        BcmController.this.lambda$handleCarEventsUpdate$2$BcmController(value);
                    }
                });
                return;
        }
    }

    public /* synthetic */ void lambda$handleCarEventsUpdate$1$BcmController(final CarPropertyValue value) {
        this.mCarPropertyMap.put(Integer.valueOf(value.getPropertyId()), value);
        handleEventsUpdate(value);
    }

    public /* synthetic */ void lambda$handleCarEventsUpdate$2$BcmController(final CarPropertyValue value) {
        this.mCarPropertyMap.put(Integer.valueOf(value.getPropertyId()), value);
        handleEventsUpdate(value);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    protected void handleEventsUpdate(CarPropertyValue<?> value) {
        switch (value.getPropertyId()) {
            case 289410561:
                handleHighBeamLamp(((Integer) getValue(value)).intValue());
                return;
            case 356517139:
                handleDriverSeatBlowLevel(((Integer) getValue(value)).intValue());
                return;
            case 356517140:
                handlePsnSeatVentLevel(((Integer) getValue(value)).intValue());
                return;
            case 557846584:
                handleKeyAuthState(((Integer) getValue(value)).intValue());
                return;
            case 557848731:
                handleWindowLockState(((Integer) getValue(value)).intValue());
                return;
            case 557849601:
                handleWelcomeMode(((Integer) getValue(value)).intValue());
                return;
            case 557849602:
                handleRearFogLamp(((Integer) getValue(value)).intValue());
                return;
            case 557849607:
                handleDrvSeatOccupied(((Integer) getValue(value)).intValue());
                return;
            case 557849609:
                handleCentralLockState(((Integer) getValue(value)).intValue());
                return;
            case 557849610:
                handleTrunkState(((Integer) getValue(value)).intValue());
                return;
            case 557849611:
                handleWiperRepairMode(((Integer) getValue(value)).intValue());
                return;
            case 557849626:
                handlePositionLamp(((Integer) getValue(value)).intValue());
                return;
            case 557849627:
                handleLightMeHome(((Integer) getValue(value)).intValue());
                return;
            case 557849628:
                handleDriveAutoLock(((Integer) getValue(value)).intValue());
                return;
            case 557849629:
                handleParkingAutoUnlock(((Integer) getValue(value)).intValue());
                return;
            case 557849630:
            case 557849874:
            case 557849875:
            case 557849876:
                lambda$setUnlockResponse$5$BcmController(((Integer) getValue(value)).intValue());
                return;
            case IBcmController.ID_BCM_EBW /* 557849631 */:
                handleEbwState(((Integer) getValue(value)).intValue());
                return;
            case 557849633:
                handleLowBeamLamp(((Integer) getValue(value)).intValue());
                return;
            case 557849635:
                handleEsbChanged(((Integer) getValue(value)).intValue());
                return;
            case 557849636:
                handleRsbWarningChanged(((Integer) getValue(value)).intValue());
                return;
            case 557849637:
                handleBackDefrost(((Integer) getValue(value)).intValue());
                return;
            case 557849638:
                handleDriverSeatHeatLevel(((Integer) getValue(value)).intValue());
                return;
            case 557849640:
                handleHeadLampMode(((Integer) getValue(value)).intValue());
                return;
            case 557849641:
                handleBonnetState(((Integer) getValue(value)).intValue());
                return;
            case 557849642:
                handleChargePort(true, ((Integer) getValue(value)).intValue());
                return;
            case 557849643:
                handleChargePort(false, ((Integer) getValue(value)).intValue());
                return;
            case 557849646:
                handlePollingOpenCfg(((Integer) getValue(value)).intValue());
                return;
            case 557849648:
                handleDrvBeltWarningChanged(((Integer) getValue(value)).intValue());
                return;
            case 557849650:
            case 557849796:
                handleLightMeHomeTime(((Integer) getValue(value)).intValue());
                return;
            case 557849654:
                handleDomeLightState(((Integer) getValue(value)).intValue());
                return;
            case 557849655:
                handleNfcStopSwChanged(((Integer) getValue(value)).intValue());
                return;
            case 557849660:
            case 557849661:
            case 557849662:
            case 557849663:
                handleMirrorPosition();
                return;
            case 557849665:
                handleMirrorHeat(isMirrorHeatEnabled() ? 1 : 0);
                return;
            case 557849675:
                handleChildLock(((Integer) getValue(value)).intValue());
                return;
            case 557849676:
                handleMirrorAutoDown(((Integer) getValue(value)).intValue());
                return;
            case 557849701:
                handlePsnSeatHeatLevel(((Integer) getValue(value)).intValue());
                return;
            case 557849711:
            case 557849830:
                handleAutoDoorHandle(((Integer) getValue(value)).intValue());
                return;
            case 557849713:
                handleCwcChargeStateChanged(((Integer) getValue(value)).intValue());
                return;
            case 557849714:
                handleCwcChargeErrorStateChanged(((Integer) getValue(value)).intValue());
                return;
            case 557849715:
                handleAutoWindowLockSwChange(((Integer) getValue(value)).intValue());
                return;
            case 557849716:
                handleLeaveAutoLock(((Integer) getValue(value)).intValue());
                return;
            case 557849717:
                handleNearAutoUnlock(((Integer) getValue(value)).intValue());
                return;
            case 557849719:
                handleStealthModeChanged(((Integer) getValue(value)).intValue());
                return;
            case 557849754:
                handleWiperSensitivity(((Integer) getValue(value)).intValue());
                return;
            case 557849757:
            case 557849820:
                handlParkLightFmbCfg(((Integer) getValue(value)).intValue());
                return;
            case 557849759:
                handleUnlockReqSrc(((Integer) getValue(value)).intValue());
                return;
            case 557849769:
                handleRRSeatHeatLevel(((Integer) getValue(value)).intValue());
                return;
            case 557849770:
                handleRLSeatHeatLevel(((Integer) getValue(value)).intValue());
                return;
            case 557849771:
                handleRLSeatHeatERR(((Integer) getValue(value)).intValue());
                return;
            case 557849772:
                handleRRSeatHeatERR(((Integer) getValue(value)).intValue());
                return;
            case 557849773:
                handleSdcKeyCfgState(((Integer) getValue(value)).intValue());
                return;
            case 557849775:
                handleSdcMaxDoorOpeningAngleChange(((Integer) getValue(value)).intValue());
                return;
            case 557849784:
            case 557849785:
                handleSdcWinAutoDown(((Integer) getValue(value)).intValue());
                return;
            case 557849786:
                handleBcmReadyEnableState(((Integer) getValue(value)).intValue());
                return;
            case 557849787:
                handleBcmBrkPedalState(((Integer) getValue(value)).intValue());
                return;
            case 557849788:
                handleLeftSdcDoorPos(((Integer) getValue(value)).intValue());
                return;
            case 557849789:
                handleRightSdcDoorPos(((Integer) getValue(value)).intValue());
                return;
            case 557849797:
                handleCwcSwChange(((Integer) getValue(value)).intValue());
                return;
            case 557849804:
                handleSunShadePosChange(((Integer) getValue(value)).intValue());
                return;
            case 557849805:
                handleSunShadeInitializationChange(((Integer) getValue(value)).intValue());
                return;
            case 557849807:
                handleLeftSdcSystemRunningState(((Integer) getValue(value)).intValue());
                return;
            case 557849808:
                handleRightSdcSystemRunningState(((Integer) getValue(value)).intValue());
                return;
            case 557849829:
                handleAsWelcomeMode(((Integer) getValue(value)).intValue());
                return;
            case 557849846:
                handleFrontMirrorHeat(getFrontMirrorHeat() ? 1 : 0);
                return;
            case 557849851:
                handleLampHeightLevel(((Integer) getValue(value)).intValue());
                return;
            case 557849852:
                handleAutoLampHeight(((Integer) getValue(value)).intValue());
                return;
            case 557849854:
                handleSteerHeatLevel(((Integer) getValue(value)).intValue());
                return;
            case 557849858:
                handleLeftChildLock(((Integer) getValue(value)).intValue());
                return;
            case 557849859:
                handleRightChildLock(((Integer) getValue(value)).intValue());
                return;
            case 557849860:
                handleRearWiperRepairMode(((Integer) getValue(value)).intValue());
                return;
            case 557849861:
                handleDomeLightSt(3, ((Integer) getValue(value)).intValue());
                return;
            case 557849863:
                handleDomeLightBright(((Integer) getValue(value)).intValue());
                return;
            case 557849864:
                handleDomeLightSt(0, ((Integer) getValue(value)).intValue());
                return;
            case 557849865:
                handleDomeLightSt(1, ((Integer) getValue(value)).intValue());
                return;
            case 557849866:
                handleDomeLightSt(2, ((Integer) getValue(value)).intValue());
                return;
            case 557849869:
                handleTrunkSensorEnable(((Integer) getValue(value)).intValue());
                return;
            case 557849871:
                handleTrunkFullOpenPos(((Integer) getValue(value)).intValue());
                return;
            case 557849895:
                handleAsMaintenanceMode(((Integer) getValue(value)).intValue());
                return;
            case 557849903:
                handleElcTrunkState(((Integer) getValue(value)).intValue());
                return;
            case 557849930:
                handleWiperSensitivityUp(((Integer) getValue(value)).intValue());
                return;
            case 557849931:
                handleWiperSensitivityDown(((Integer) getValue(value)).intValue());
                return;
            case 557849939:
                handleSdcBrakeCloseDoorCfgChange(((Integer) getValue(value)).intValue());
                return;
            case 557849946:
                handleElcTrunkPos(((Integer) getValue(value)).intValue());
                return;
            case 557849948:
                handleMirrorAutoFold(((Integer) getValue(value)).intValue());
                return;
            case 557849979:
                handleLeftSlideDoorModeChange(((Integer) getValue(value)).intValue());
                return;
            case 557849980:
                handleLeftSlideDoorStateChange(((Integer) getValue(value)).intValue());
                return;
            case 557849981:
                handleLeftSlideDoorLockStateChange(((Integer) getValue(value)).intValue());
                return;
            case 557849982:
                handleRightSlideDoorModeChange(((Integer) getValue(value)).intValue());
                return;
            case 557849983:
                handleRightSlideDoorStateChange(((Integer) getValue(value)).intValue());
                return;
            case 557849984:
                handleRightSlideDoorLockStateChange(((Integer) getValue(value)).intValue());
                return;
            case 557849985:
                handleRearLogoLightSwChange(((Integer) getValue(value)).intValue());
                return;
            case 557849986:
                handleTrunkWorkStatusChange(((Integer) getValue(value)).intValue());
                return;
            case 557849987:
                handleLowBeamOffConfirm(((Integer) getValue(value)).intValue());
                return;
            case 557849988:
                handleCwcFRSwChange(((Integer) getValue(value)).intValue());
                return;
            case 557849989:
                handleCwcFRChargeStateChanged(((Integer) getValue(value)).intValue());
                return;
            case 557849990:
                handleCwcFRChargeErrorStateChanged(((Integer) getValue(value)).intValue());
                return;
            case 557849991:
                handleCarpetLightWelcomeStateChange(((Integer) getValue(value)).intValue());
                return;
            case 557849992:
                handlePollingWelcomeStateChange(((Integer) getValue(value)).intValue());
                return;
            case 557850049:
                handleCwcRLSwChange(((Integer) getValue(value)).intValue());
                return;
            case 557850050:
                handleCwcRLChargeStateChanged(((Integer) getValue(value)).intValue());
                return;
            case 557850051:
                handleCwcRLChargeErrorStateChanged(((Integer) getValue(value)).intValue());
                return;
            case 557850052:
                handleCwcRRSwChange(((Integer) getValue(value)).intValue());
                return;
            case 557850053:
                handleCwcRRChargeStateChanged(((Integer) getValue(value)).intValue());
                return;
            case 557850054:
                handleCwcRRChargeErrorStateChanged(((Integer) getValue(value)).intValue());
                return;
            case 557850055:
                handleDomeLightSt(7, ((Integer) getValue(value)).intValue());
                return;
            case 557850056:
                handleDomeLightSt(8, ((Integer) getValue(value)).intValue());
                return;
            case 557858305:
                handleTrailerHitchStatus(((Integer) getValue(value)).intValue());
                return;
            case 557858307:
                handleTtmSysErr(((Integer) getValue(value)).intValue());
                return;
            case 557858308:
                handleTtmLampConnectStatus(((Integer) getValue(value)).intValue());
                return;
            case 557858310:
                handleTtmHookMotorStatus(((Integer) getValue(value)).intValue());
                return;
            case 557858819:
                handleAsCampingMode(((Integer) getValue(value)).intValue());
                return;
            case 557858823:
                handleAsLevlingMode(((Integer) getValue(value)).intValue());
                return;
            case 557858828:
                handleEasyLoadingMode(((Integer) getValue(value)).intValue());
                return;
            case 557858833:
                handleAsSoftChanged(((Integer) getValue(value)).intValue());
                return;
            case 557858842:
                handleAsHeightChanged(((Integer) getValue(value)).intValue());
                return;
            case 557859329:
                handleCmsAutoBrightSwChange(((Integer) getValue(value)).intValue());
                return;
            case 557859330:
                handleCmsBrightChange(((Integer) getValue(value)).intValue());
                return;
            case 557859331:
                handleCmsReverseAssistSwChange(((Integer) getValue(value)).intValue());
                return;
            case 557859332:
                handleCmsTurnAssistSwChange(((Integer) getValue(value)).intValue());
                return;
            case 557859333:
                handleCmsHighSpdSwChange(((Integer) getValue(value)).intValue());
                return;
            case 557859334:
                handleCmsLowSpdSwChange(((Integer) getValue(value)).intValue());
                return;
            case 557859335:
                handleCmsObjectRecognizeSwChange(((Integer) getValue(value)).intValue());
                return;
            case 557859345:
                handleCmsViewAngleChange(((Integer) getValue(value)).intValue());
                return;
            case 557859841:
                handleArsWorkModeChange(((Integer) getValue(value)).intValue());
                return;
            case 557859842:
                handleArsWorkStChange(((Integer) getValue(value)).intValue());
                return;
            case 557859843:
                handleArsPosChange(((Integer) getValue(value)).intValue());
                return;
            case 557859844:
                handleArsInitStChange(((Integer) getValue(value)).intValue());
                return;
            case 557859846:
                handleArsFaultStChange(((Integer) getValue(value)).intValue());
                return;
            case 557860865:
                handleImsModeChange(((Integer) getValue(value)).intValue());
                return;
            case 557860866:
                handleImsAutoVisionSwChange(((Integer) getValue(value)).intValue());
                return;
            case 557860867:
                handleImsBrightLevelChange(((Integer) getValue(value)).intValue());
                return;
            case 557860869:
                handleImsVisionVerticalLevelChange(((Integer) getValue(value)).intValue());
                return;
            case 557860870:
                handleImsVisionAngleLevelChange(((Integer) getValue(value)).intValue());
                return;
            case 557860871:
                handleImsSystemStChange(((Integer) getValue(value)).intValue());
                return;
            case 557915161:
                try {
                    handleDoorState(getIntArrayProperty(value));
                    return;
                } catch (Exception e) {
                    LogUtils.e(TAG, (String) null, e);
                    return;
                }
            case 557915294:
                handleParkingLampStates(getIntArrayProperty(value));
                return;
            case 557915328:
                handleTurnLampState(getIntArrayProperty(value));
                return;
            case 557915329:
                handleDaytimeRunningLightChange(getIntArrayProperty(value));
                return;
            case 557915345:
                handleMirrorAdjust(getIntArrayProperty(value));
                return;
            case 559946854:
                handleWindowPosition(2, ((Float) getValue(value)).floatValue());
                return;
            case 559946855:
                handleWindowPosition(0, ((Float) getValue(value)).floatValue());
                return;
            case 559946856:
                handleWindowPosition(1, ((Float) getValue(value)).floatValue());
                return;
            case 559946857:
                handleWindowPosition(3, ((Float) getValue(value)).floatValue());
                return;
            case 560012320:
                handleWinPosChanged(getFloatArrayProperty(value));
                return;
            case 560022032:
                handleCmsPosChange(getValue(value));
                return;
            default:
                LogUtils.w(TAG, "handle unknown event: " + value);
                return;
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getHeadLampState() {
        try {
            try {
                return getIntProperty(557849640);
            } catch (Exception e) {
                LogUtils.e(TAG, "Call getHeadLampGroup exception: " + e.getMessage(), false);
                return 3;
            }
        } catch (Exception unused) {
            return this.mCarManager.getHeadLampGroup();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getHeadLampStateSp() {
        return this.mDataSync.getHeadLampState();
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0025 A[Catch: Exception -> 0x003e, TryCatch #0 {Exception -> 0x003e, blocks: (B:4:0x0003, B:6:0x000b, B:8:0x0013, B:12:0x001c, B:14:0x0025, B:16:0x0029, B:17:0x002f), top: B:22:0x0003 }] */
    /* JADX WARN: Removed duplicated region for block: B:23:? A[RETURN, SYNTHETIC] */
    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void setHeadLampState(int r3, boolean r4) {
        /*
            r2 = this;
            r0 = 3
            if (r3 != r0) goto L1b
            com.xiaopeng.carcontrol.config.CarBaseConfig r0 = r2.mCarConfig     // Catch: java.lang.Exception -> L3e
            boolean r0 = r0.isSupportLlu()     // Catch: java.lang.Exception -> L3e
            if (r0 == 0) goto L1b
            com.xiaopeng.carcontrol.config.CarBaseConfig r0 = r2.mCarConfig     // Catch: java.lang.Exception -> L3e
            boolean r0 = r0.isSupportNewParkLampFmB()     // Catch: java.lang.Exception -> L3e
            if (r0 != 0) goto L1b
            boolean r0 = r2.isParkLampIncludeFmB()     // Catch: java.lang.Exception -> L3e
            if (r0 == 0) goto L1b
            r0 = 4
            goto L1c
        L1b:
            r0 = r3
        L1c:
            C extends android.car.hardware.CarEcuManager r1 = r2.mCarManager     // Catch: java.lang.Exception -> L3e
            android.car.hardware.bcm.CarBcmManager r1 = (android.car.hardware.bcm.CarBcmManager) r1     // Catch: java.lang.Exception -> L3e
            r1.setHeadLampGroup(r0)     // Catch: java.lang.Exception -> L3e
            if (r4 == 0) goto L5c
            boolean r4 = r2.mIsMainProcess     // Catch: java.lang.Exception -> L3e
            if (r4 == 0) goto L2f
            com.xiaopeng.carcontrol.model.DataSyncModel r4 = r2.mDataSync     // Catch: java.lang.Exception -> L3e
            r4.setHeadLampState(r3)     // Catch: java.lang.Exception -> L3e
            goto L5c
        L2f:
            com.xiaopeng.carcontrol.App r4 = com.xiaopeng.carcontrol.App.getInstance()     // Catch: java.lang.Exception -> L3e
            android.content.ContentResolver r4 = r4.getContentResolver()     // Catch: java.lang.Exception -> L3e
            java.lang.String r0 = "set_head_lamp"
            com.xiaopeng.carcontrol.provider.CarControl.PrivateControl.putInt(r4, r0, r3)     // Catch: java.lang.Exception -> L3e
            goto L5c
        L3e:
            r3 = move-exception
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r0 = "setHeadLampState: "
            java.lang.StringBuilder r4 = r4.append(r0)
            java.lang.String r3 = r3.getMessage()
            java.lang.StringBuilder r3 = r4.append(r3)
            java.lang.String r3 = r3.toString()
            r4 = 0
            java.lang.String r0 = "BcmController"
            com.xiaopeng.carcontrol.util.LogUtils.e(r0, r3, r4)
        L5c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.carcontrol.carmanager.impl.BcmController.setHeadLampState(int, boolean):void");
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getLowBeamOffConfirmSt() {
        int i;
        try {
            try {
                i = getIntProperty(557849987);
            } catch (Exception e) {
                LogUtils.e(TAG, "Call getLowBeamOffConfirmSt exception: " + e.getMessage(), false);
                i = 0;
            }
        } catch (Exception unused) {
            i = this.mCarManager.getLowBeamOffConfirmSt();
        }
        LogUtils.i(TAG, "getLowBeamOffConfirmSt: " + i, false);
        return i;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setLowBeamOffConfirmSt(int state) {
        try {
            this.mCarManager.setLowBeamOffConfirmSt(state);
        } catch (Exception e) {
            LogUtils.e(TAG, "setLowBeamOffConfirmSt: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getLampHeightLevel() {
        try {
            try {
                return getIntProperty(557849851);
            } catch (Exception unused) {
                return this.mCarManager.getHeadLampLevelingReqValue();
            }
        } catch (Throwable th) {
            LogUtils.e(TAG, "getLampHeightLevel" + th.getMessage(), false);
            return this.mDataSync.getLampHeightLevel();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setLampHeightLevel(int level) {
        setLampHeightLevel(level, true);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setLampHeightLevel(int level, boolean needSave) {
        if (level >= 0 && level <= 3 && needSave) {
            this.mDataSync.setLampHeightLevel(level);
            this.mDataSync.setAutoLampHeight(false);
        }
        try {
            this.mCarManager.setHeadLampLevelingReqValue(level);
        } catch (Throwable th) {
            LogUtils.e(TAG, "setLampHeightLevel: " + th.getMessage(), false);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:?, code lost:
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:4:0x000a, code lost:
        if (getIntProperty(557849852) == 5) goto L6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:6:0x000d, code lost:
        r1 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x0018, code lost:
        if (r4.mCarManager.getHeadLampLevelingCtrlMode() != 5) goto L5;
     */
    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean isAutoLampHeight() {
        /*
            r4 = this;
            r0 = 557849852(0x21401cfc, float:6.509049E-19)
            r1 = 1
            r2 = 5
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
            C extends android.car.hardware.CarEcuManager r0 = r4.mCarManager     // Catch: java.lang.Throwable -> L1b
            android.car.hardware.bcm.CarBcmManager r0 = (android.car.hardware.bcm.CarBcmManager) r0     // Catch: java.lang.Throwable -> L1b
            int r0 = r0.getHeadLampLevelingCtrlMode()     // Catch: java.lang.Throwable -> L1b
            if (r0 != r2) goto Ld
            goto Le
        L1b:
            r0 = move-exception
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "isAutoLampHeight failed: "
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r0 = r0.getMessage()
            java.lang.StringBuilder r0 = r1.append(r0)
            java.lang.String r0 = r0.toString()
            java.lang.String r1 = "BcmController"
            com.xiaopeng.carcontrol.util.LogUtils.e(r1, r0, r3)
        L38:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.carcontrol.carmanager.impl.BcmController.isAutoLampHeight():boolean");
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setAutoLampHeight(boolean auto) {
        setAutoLampHeight(auto, true);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setAutoLampHeight(boolean auto, boolean needSave) {
        if (this.mIsMainProcess && needSave) {
            this.mDataSync.setAutoLampHeight(auto);
        }
        try {
            this.mCarManager.setHeadLampLevelingReqValue(auto ? 5 : this.mDataSync.getLampHeightLevel());
        } catch (Throwable th) {
            LogUtils.e(TAG, "setAutoHeadLampHeight: " + th.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public boolean getParkLamp() {
        int i = 0;
        try {
            try {
                i = getIntProperty(557849626);
            } catch (Exception e) {
                LogUtils.e(TAG, "getParkLamp: " + e.getMessage(), false);
            }
        } catch (Exception unused) {
            i = this.mCarManager.getLocationLampState();
        }
        return parseBcmStatus(i);
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0032, code lost:
        if (getIntProperty(557849757) == 1) goto L12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0061, code lost:
        if (r5.mCarManager.getParkLightRelatedFMBLightConfigState() == 1) goto L12;
     */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:23:0x0051 -> B:15:0x0034). Please submit an issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:26:0x0061 -> B:15:0x0034). Please submit an issue!!! */
    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean isParkLampIncludeFmB() {
        /*
            r5 = this;
            com.xiaopeng.carcontrol.config.CarBaseConfig r0 = r5.mCarConfig
            boolean r0 = r0.isSupportLlu()
            r1 = 0
            if (r0 == 0) goto L82
            r0 = 1
            com.xiaopeng.carcontrol.config.CarBaseConfig r2 = r5.mCarConfig     // Catch: java.lang.Exception -> L37
            boolean r2 = r2.isSupportNewParkLampFmB()     // Catch: java.lang.Exception -> L37
            if (r2 == 0) goto L2b
            com.xiaopeng.carcontrol.config.CarBaseConfig r2 = r5.mCarConfig     // Catch: java.lang.Exception -> L37
            boolean r2 = r2.isSupportSaberLightFeedBack()     // Catch: java.lang.Exception -> L37
            if (r2 == 0) goto L24
            r2 = 557849820(0x21401cdc, float:6.5090325E-19)
            int r2 = r5.getIntProperty(r2)     // Catch: java.lang.Exception -> L37
            if (r2 != r0) goto L35
            goto L34
        L24:
            com.xiaopeng.carcontrol.model.DataSyncModel r2 = r5.mDataSync     // Catch: java.lang.Exception -> L37
            boolean r1 = r2.getParkLampB()     // Catch: java.lang.Exception -> L37
            goto L35
        L2b:
            r2 = 557849757(0x21401c9d, float:6.509E-19)
            int r2 = r5.getIntProperty(r2)     // Catch: java.lang.Exception -> L37
            if (r2 != r0) goto L35
        L34:
            r1 = r0
        L35:
            r0 = r1
            goto L81
        L37:
            com.xiaopeng.carcontrol.config.CarBaseConfig r2 = r5.mCarConfig     // Catch: java.lang.Exception -> L64
            boolean r2 = r2.isSupportNewParkLampFmB()     // Catch: java.lang.Exception -> L64
            if (r2 == 0) goto L59
            com.xiaopeng.carcontrol.config.CarBaseConfig r2 = r5.mCarConfig     // Catch: java.lang.Exception -> L64
            boolean r2 = r2.isSupportSaberLightFeedBack()     // Catch: java.lang.Exception -> L64
            if (r2 == 0) goto L52
            C extends android.car.hardware.CarEcuManager r2 = r5.mCarManager     // Catch: java.lang.Exception -> L64
            android.car.hardware.bcm.CarBcmManager r2 = (android.car.hardware.bcm.CarBcmManager) r2     // Catch: java.lang.Exception -> L64
            int r2 = r2.getSaberLightSwitchStatus()     // Catch: java.lang.Exception -> L64
            if (r2 != r0) goto L35
            goto L63
        L52:
            com.xiaopeng.carcontrol.model.DataSyncModel r2 = r5.mDataSync     // Catch: java.lang.Exception -> L64
            boolean r1 = r2.getParkLampB()     // Catch: java.lang.Exception -> L64
            goto L35
        L59:
            C extends android.car.hardware.CarEcuManager r2 = r5.mCarManager     // Catch: java.lang.Exception -> L64
            android.car.hardware.bcm.CarBcmManager r2 = (android.car.hardware.bcm.CarBcmManager) r2     // Catch: java.lang.Exception -> L64
            int r2 = r2.getParkLightRelatedFMBLightConfigState()     // Catch: java.lang.Exception -> L64
            if (r2 != r0) goto L35
        L63:
            goto L34
        L64:
            r2 = move-exception
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "isParkLampIncludeFmB: "
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r2 = r2.getMessage()
            java.lang.StringBuilder r2 = r3.append(r2)
            java.lang.String r2 = r2.toString()
            java.lang.String r3 = "BcmController"
            com.xiaopeng.carcontrol.util.LogUtils.e(r3, r2, r1)
        L81:
            return r0
        L82:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.carcontrol.carmanager.impl.BcmController.isParkLampIncludeFmB():boolean");
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setParkLampIncludeFmB(boolean active) {
        setParkLampIncludeFmB(active, true);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setParkLampIncludeFmB(boolean active, boolean needSp) {
        try {
            int i = 1;
            if (!this.mCarConfig.isSupportNewParkLampFmB()) {
                CarBcmManager carBcmManager = this.mCarManager;
                if (!active) {
                    i = 0;
                }
                carBcmManager.setParkLightRelatedFMBLightConfig(i);
                if (getHeadLampState() == 3) {
                    this.mCarManager.setHeadLampGroup(active ? 4 : 3);
                }
            } else {
                CarBcmManager carBcmManager2 = this.mCarManager;
                if (!active) {
                    i = 0;
                }
                carBcmManager2.setSaberLightSw(i);
            }
            if (this.mIsMainProcess && needSp) {
                this.mDataSync.setParkLampB(active);
            }
        } catch (Exception e) {
            LogUtils.e(TAG, "setParkLampIncludeFmB: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int[] getParkingLampStates() {
        int[] iArr = new int[3];
        if (this.mCarConfig.isSupportLlu()) {
            try {
                try {
                    iArr = getIntArrayProperty(557915294);
                } catch (Exception e) {
                    LogUtils.e(TAG, "getParkingLampsStates: " + e.getMessage(), false);
                    iArr = new int[]{0, 0, 0};
                }
            } catch (Exception unused) {
                iArr = this.mCarManager.getParkingLampsStates();
            }
        }
        LogUtils.i(TAG, "getParkingLampsStates: " + Arrays.toString(iArr), false);
        return iArr;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public boolean getLowBeamLamp() {
        int i;
        try {
            try {
                i = getIntProperty(557849633);
            } catch (Exception unused) {
                i = 0;
            }
        } catch (Exception unused2) {
            i = this.mCarManager.getNearLampState();
        }
        return parseBcmStatus(i);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public boolean getHighBeamLamp() {
        int i;
        try {
            try {
                i = getIntProperty(289410561);
            } catch (Exception unused) {
                i = 0;
            }
        } catch (Exception unused2) {
            i = this.mCarManager.getFarLampState();
        }
        return parseBcmStatus(i);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public boolean getLightMeHome() {
        int lightMeHome;
        if (this.mCarConfig.isSupportLightMeHomeNew()) {
            return getLightMeHomeTime() != 0;
        }
        try {
            try {
                lightMeHome = getIntProperty(557849627);
            } catch (Exception unused) {
                if (this.mDataSync == null) {
                    return true;
                }
                return this.mDataSync.getLightMeHome();
            }
        } catch (Exception unused2) {
            lightMeHome = this.mCarManager.getLightMeHome();
        }
        return lightMeHome == 2;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setLightMeHome(boolean enable, boolean needSave) {
        if (this.mCarConfig.isSupportLightMeHomeNew()) {
            setLightMeHomeTime(enable ? this.mDataSync.getLightMeHomeTime() : 0, needSave);
            return;
        }
        try {
            this.mCarManager.setLightMeHome(enable ? 2 : 1);
            if (needSave && this.mIsMainProcess) {
                this.mDataSync.setLightMeHome(enable);
            }
        } catch (Exception e) {
            LogUtils.e(TAG, "setLightMeHome: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getLightMeHomeTime() {
        int i = 1;
        if (this.mCarConfig.isSupportLightMeHomeNew()) {
            try {
                try {
                    return getIntProperty(557849796);
                } catch (Exception unused) {
                    return this.mCarManager.getFollowMeHomeCfg();
                }
            } catch (Exception unused2) {
                if (this.mDataSync != null) {
                    i = this.mDataSync.getLightMeHomeTime();
                }
            }
        } else {
            try {
                try {
                    return getIntProperty(557849650);
                } catch (Exception unused3) {
                    if (this.mDataSync != null) {
                        i = this.mDataSync.getLightMeHomeTime();
                    }
                }
            } catch (Exception unused4) {
                return this.mCarManager.getFollowMeTime();
            }
        }
        return i;
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x002e, code lost:
        if (r7.mIsMainProcess == false) goto L31;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0030, code lost:
        r7.mDataSync.setLightMeHomeTime(r8);
        r7.mDataSync.setLightMeHome(true);
     */
    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void setLightMeHomeTime(int r8, boolean r9) {
        /*
            r7 = this;
            com.xiaopeng.carcontrol.config.CarBaseConfig r0 = r7.mCarConfig
            boolean r0 = r0.isSupportLightMeHomeNew()
            java.lang.String r1 = "setLightMeHomeTime: "
            java.lang.String r2 = "BcmController"
            r3 = 3
            r4 = 2
            r5 = 0
            r6 = 1
            if (r0 == 0) goto L55
            C extends android.car.hardware.CarEcuManager r0 = r7.mCarManager     // Catch: java.lang.Exception -> L3b
            android.car.hardware.bcm.CarBcmManager r0 = (android.car.hardware.bcm.CarBcmManager) r0     // Catch: java.lang.Exception -> L3b
            r0.setFollowMeHomeCfg(r8)     // Catch: java.lang.Exception -> L3b
            if (r8 == r6) goto L2a
            if (r8 == r4) goto L2a
            if (r8 != r3) goto L1e
            goto L2a
        L1e:
            if (r9 == 0) goto L87
            boolean r8 = r7.mIsMainProcess     // Catch: java.lang.Exception -> L3b
            if (r8 == 0) goto L87
            com.xiaopeng.carcontrol.model.DataSyncModel r8 = r7.mDataSync     // Catch: java.lang.Exception -> L3b
            r8.setLightMeHome(r5)     // Catch: java.lang.Exception -> L3b
            goto L87
        L2a:
            if (r9 == 0) goto L87
            boolean r9 = r7.mIsMainProcess     // Catch: java.lang.Exception -> L3b
            if (r9 == 0) goto L87
            com.xiaopeng.carcontrol.model.DataSyncModel r9 = r7.mDataSync     // Catch: java.lang.Exception -> L3b
            r9.setLightMeHomeTime(r8)     // Catch: java.lang.Exception -> L3b
            com.xiaopeng.carcontrol.model.DataSyncModel r8 = r7.mDataSync     // Catch: java.lang.Exception -> L3b
            r8.setLightMeHome(r6)     // Catch: java.lang.Exception -> L3b
            goto L87
        L3b:
            r8 = move-exception
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.StringBuilder r9 = r9.append(r1)
            java.lang.String r8 = r8.getMessage()
            java.lang.StringBuilder r8 = r9.append(r8)
            java.lang.String r8 = r8.toString()
            com.xiaopeng.carcontrol.util.LogUtils.e(r2, r8, r5)
            goto L87
        L55:
            if (r8 == r6) goto L5b
            if (r8 == r4) goto L5b
            if (r8 != r3) goto L87
        L5b:
            C extends android.car.hardware.CarEcuManager r0 = r7.mCarManager     // Catch: java.lang.Exception -> L6e
            android.car.hardware.bcm.CarBcmManager r0 = (android.car.hardware.bcm.CarBcmManager) r0     // Catch: java.lang.Exception -> L6e
            r0.setFollowMeTime(r8)     // Catch: java.lang.Exception -> L6e
            if (r9 == 0) goto L87
            boolean r9 = r7.mIsMainProcess     // Catch: java.lang.Exception -> L6e
            if (r9 == 0) goto L87
            com.xiaopeng.carcontrol.model.DataSyncModel r9 = r7.mDataSync     // Catch: java.lang.Exception -> L6e
            r9.setLightMeHomeTime(r8)     // Catch: java.lang.Exception -> L6e
            goto L87
        L6e:
            r8 = move-exception
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.StringBuilder r9 = r9.append(r1)
            java.lang.String r8 = r8.getMessage()
            java.lang.StringBuilder r8 = r9.append(r8)
            java.lang.String r8 = r8.toString()
            com.xiaopeng.carcontrol.util.LogUtils.e(r2, r8, r5)
        L87:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.carcontrol.carmanager.impl.BcmController.setLightMeHomeTime(int, boolean):void");
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public boolean getRearFogLamp() {
        int i = 0;
        try {
            i = getIntProperty(557849602);
        } catch (Exception e) {
            try {
                i = this.mCarManager.getRearFogLamp();
            } catch (Exception unused) {
                LogUtils.e(TAG, "getRearFogLamp: " + e.getMessage(), false);
            }
        }
        return parseBcmStatus(i);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setRearFogLamp(boolean enable) {
        try {
            LogUtils.i(TAG, "setRearFogLamp: " + enable, false);
            this.mCarManager.setRearFogLamp(parseCduSwitchCmd(enable));
        } catch (Exception e) {
            LogUtils.e(TAG, "setRearFogLamp: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int[] getTurnLampState() {
        if (this.mCarConfig.isSupportTurnActive()) {
            try {
                try {
                    return getIntArrayProperty(557915328);
                } catch (Exception unused) {
                    return this.mCarManager.getLeftAndRightTurnLampsActiveStatus();
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "getTurnLampState: " + e.getMessage(), false);
                return null;
            }
        }
        return null;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public boolean isLedDrlEnabled() {
        int i;
        if (this.mCarConfig.isSupportDrl()) {
            try {
                try {
                    i = getIntProperty(557849651);
                } catch (Exception unused) {
                    i = this.mCarManager.getDayLightMode();
                }
            } catch (Exception unused2) {
                i = 0;
            }
            return i == 1;
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setLedDrlEnable(boolean enable) {
        if (this.mCarConfig.isSupportDrl()) {
            try {
                this.mCarManager.setDayLightMode(enable ? 1 : 0);
            } catch (Exception e) {
                LogUtils.e(TAG, "setLedDrlEnable: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getDomeLightState() {
        if (this.mCarConfig.isSupportDomeLight()) {
            try {
                try {
                    return getIntProperty(557849654);
                } catch (Exception e) {
                    LogUtils.e(TAG, "getDomeLightState: " + e.getMessage(), false);
                    if (this.mDataSync == null) {
                        return 1;
                    }
                    return this.mDataSync.getDomeLight();
                }
            } catch (Exception unused) {
                return this.mCarManager.getDomeLightCfg();
            }
        }
        return -1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setDomeLight(int type) {
        if (this.mCarConfig.isSupportDomeLight() && type == 1) {
            try {
                this.mCarManager.setDomeLightCfg(type);
            } catch (Exception e) {
                LogUtils.e(TAG, "setDomeLight: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getDomeLightStatus(int lightType) {
        int trdLeftDomeLightSwitchStatus;
        if (!this.mCarConfig.isSupportDomeLight() || !this.mCarConfig.isSupportDomeLightIndependentCtrl()) {
            return 0;
        }
        if (lightType == 0) {
            try {
                return this.mCarManager.getFrontLeftDomeLightSwitchStatus();
            } catch (Exception e) {
                LogUtils.w(TAG, "getFrontLeftDomeLightSwitchStatus: " + e.getMessage(), false);
                return 0;
            }
        } else if (lightType == 1) {
            try {
                return this.mCarManager.getFrontRightDomeLightSwitchStatus();
            } catch (Exception e2) {
                LogUtils.w(TAG, "getFrontRightDomeLightSwitchStatus: " + e2.getMessage(), false);
                return 0;
            }
        } else if (lightType == 2) {
            try {
                return this.mCarManager.getRearLeftDomeLightSwitchStatus();
            } catch (Exception e3) {
                LogUtils.w(TAG, "getRearLeftDomeLightSwitchStatus: " + e3.getMessage(), false);
                return 0;
            }
        } else if (lightType == 3) {
            try {
                return this.mCarManager.getRearRightDomeLightSwitchStatus();
            } catch (Exception e4) {
                LogUtils.w(TAG, "getRearRightDomeLightSwitchStatus: " + e4.getMessage(), false);
                return 0;
            }
        } else {
            try {
                if (lightType == 7) {
                    try {
                        trdLeftDomeLightSwitchStatus = this.mCarManager.getTrdLeftDomeLightSwitchStatus();
                    } catch (Exception e5) {
                        LogUtils.w(TAG, "getTrdLeftDomeLightSwitchStatus: " + e5.getMessage(), false);
                    }
                    return this.mCarManager.getTrdRightDomeLightSwitchStatus();
                } else if (lightType != 8) {
                    return 0;
                }
                return this.mCarManager.getTrdRightDomeLightSwitchStatus();
            } catch (Exception e6) {
                LogUtils.w(TAG, "getTrdRightDomeLightSwitchStatus: " + e6.getMessage(), false);
                return trdLeftDomeLightSwitchStatus;
            }
            trdLeftDomeLightSwitchStatus = 0;
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setDomeLightSw(int lightType, boolean enable) {
        if (lightType == 0) {
            try {
                this.mCarManager.setFrontLeftDomeLightSwitchStatus(enable ? 1 : 0);
            } catch (Exception e) {
                LogUtils.w(TAG, "setFrontLeftDomeLightSwitchStatus: " + e.getMessage(), false);
            }
        } else if (lightType == 1) {
            try {
                this.mCarManager.setFrontRightDomeLightSwitchStatus(enable ? 1 : 0);
            } catch (Exception e2) {
                LogUtils.w(TAG, "setFrontRightDomeLightSwitchStatus: " + e2.getMessage(), false);
            }
        } else if (lightType == 2) {
            try {
                this.mCarManager.setRearLeftDomeLightSwitchStatus(enable ? 1 : 0);
            } catch (Exception e3) {
                LogUtils.w(TAG, "setRearLeftDomeLightSwitchStatus: " + e3.getMessage(), false);
            }
        } else if (lightType == 3) {
            try {
                this.mCarManager.setRearRightDomeLightSwitchStatus(enable ? 1 : 0);
            } catch (Exception e4) {
                LogUtils.w(TAG, "setRearRightDomeLightSwitchStatus: " + e4.getMessage(), false);
            }
        } else if (lightType == 7) {
            if (this.mCarConfig.isSupportDomeLightThirdRow()) {
                try {
                    this.mCarManager.setTrdLeftDomeLightSwitchStatus(enable ? 1 : 0);
                } catch (Exception e5) {
                    LogUtils.w(TAG, "setTrdLeftDomeLightSwitchStatus: " + e5.getMessage(), false);
                }
            }
        } else if (lightType == 8 && this.mCarConfig.isSupportDomeLightThirdRow()) {
            try {
                this.mCarManager.setTrdRightDomeLightSwitchStatus(enable ? 1 : 0);
            } catch (Exception e6) {
                LogUtils.w(TAG, "setTrdRightDomeLightSwitchStatus: " + e6.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getDomeLightBright() {
        int i;
        if (this.mCarConfig.isSupportDomeLightBrightnessCtrl()) {
            try {
                i = getIntProperty(557849863);
            } catch (Exception e) {
                try {
                    i = this.mCarManager.getDomeLightBrightLevel();
                } catch (Exception unused) {
                    LogUtils.e(TAG, "getDomeLightBrightLevel: " + e.getMessage(), false);
                    i = 1;
                }
            }
            int i2 = i;
            return (i2 == 0 && this.mIsMainProcess) ? this.mDataSync.getDomeLightBright() : i2;
        }
        return 0;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setDomeLightBright(int brightness) {
        setDomeLightBright(brightness, true);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setDomeLightBright(int brightness, boolean needSave) {
        if (this.mCarConfig.isSupportDomeLightBrightnessCtrl()) {
            try {
                this.mCarManager.setDomeLightBrightLevel(brightness);
                if (this.mIsMainProcess && needSave) {
                    this.mDataSync.setDomeLightBright(brightness);
                }
            } catch (Exception e) {
                LogUtils.i(TAG, "setDomeLightBrightLevel failed: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public boolean getDoorLockState() {
        int i;
        try {
            i = getIntProperty(557849609);
        } catch (Exception e) {
            try {
                i = this.mCarManager.getDoorLockState();
            } catch (Exception unused) {
                LogUtils.e(TAG, "getDoorLockState: " + e.getMessage(), false);
                i = 0;
            }
        }
        return i == 1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setCentralLock(boolean lock) {
        try {
            this.mCarManager.setDoorLock(lock ? 1 : 0);
        } catch (Exception e) {
            LogUtils.e(TAG, "setCentralLock: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getDoorState(int index) {
        int[] doorsState;
        if (index < 0 || index >= 4 || (doorsState = getDoorsState()) == null || doorsState.length < 4) {
            return -1;
        }
        return doorsState[index];
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int[] getDoorsState() {
        try {
            try {
                return getIntArrayProperty(557915161);
            } catch (Exception unused) {
                return null;
            }
        } catch (Exception unused2) {
            return this.mCarManager.getDoorsState();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setAutoDoorHandleEnable(boolean enable, boolean needSave) {
        try {
            if (this.mCarConfig.isSupportDhc()) {
                int i = 1;
                if (this.mCarConfig.isSupportNewDhc()) {
                    CarBcmManager carBcmManager = this.mCarManager;
                    if (!enable) {
                        i = 0;
                    }
                    carBcmManager.setHandleAutoSwitch(i);
                } else {
                    CarDhcManager carDhcManager = this.mCarDhcManager;
                    if (!enable) {
                        i = 0;
                    }
                    carDhcManager.setDhcActiveSw(i);
                }
            }
            if (needSave) {
                if (this.mIsMainProcess) {
                    this.mDataSync.setAutoDhc(enable);
                } else {
                    CarControl.PrivateControl.putBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.DHC, enable);
                }
            }
        } catch (Exception e) {
            LogUtils.e(TAG, "setAutoDoorHandleEnable: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public boolean isAutoDoorHandleEnabled() {
        int dhcActiveSw;
        int handleAutoState;
        if (this.mCarConfig.isSupportDhc()) {
            if (this.mCarConfig.isSupportNewDhc()) {
                try {
                    try {
                        handleAutoState = getIntProperty(557849830);
                    } catch (Exception e) {
                        LogUtils.e(TAG, "getHandleAutoState: " + e.getMessage(), false);
                        return GlobalConstant.DEFAULT.AUTO_DOOR_HANDLE;
                    }
                } catch (Exception unused) {
                    handleAutoState = this.mCarManager.getHandleAutoState();
                }
                return handleAutoState == 1;
            }
            try {
                try {
                    dhcActiveSw = getIntProperty(557849711);
                } catch (Exception e2) {
                    LogUtils.e(TAG, "getDhcActiveSw: " + e2.getMessage(), false);
                    return GlobalConstant.DEFAULT.AUTO_DOOR_HANDLE;
                }
            } catch (Exception unused2) {
                dhcActiveSw = this.mCarDhcManager.getDhcActiveSw();
            }
            return dhcActiveSw == 1;
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public float[] getAllWindowPosition() {
        float[] fArr = {getWinPos(0), getWinPos(1), getWinPos(2), getWinPos(3)};
        LogUtils.i(TAG, "getAllWindowPosition: " + Arrays.toString(fArr), false);
        return fArr;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void controlWindowAuto(boolean open) {
        if (this.mCarConfig.isSupportWindowAutoCtrl()) {
            try {
                this.mCarManager.setAutoWindowCmd(open ? 2 : 1);
                return;
            } catch (Exception e) {
                LogUtils.e(TAG, "setAutoWindowCmd: " + e.getMessage(), false);
                return;
            }
        }
        float f = open ? 0.0f : 100.0f;
        setWindowsMovePositions(f, f, f, f);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setWindowMoveCmd(int window, int cmd) {
        try {
            this.mCarManager.setWindowMoveCmd(window, cmd);
        } catch (Exception e) {
            LogUtils.e(TAG, "setWindowMoveCmd: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setWinPos(int windowType, float position) {
        if (this.mCarConfig.isSupportWindowPos()) {
            try {
                this.mCarManager.setWindowMovePosition(windowType, position);
            } catch (Exception e) {
                LogUtils.e(TAG, "setWinPos: " + e.getMessage(), false);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:43:0x00cc  */
    /* JADX WARN: Removed duplicated region for block: B:57:? A[RETURN, SYNTHETIC] */
    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public float getWinPos(int r6) {
        /*
            r5 = this;
            r0 = 0
            java.lang.String r1 = "getWinPos: "
            java.lang.String r2 = "BcmController"
            r3 = 0
            if (r6 == 0) goto L99
            r4 = 1
            if (r6 == r4) goto L6e
            r4 = 2
            if (r6 == r4) goto L41
            r4 = 3
            if (r6 == r4) goto L12
            return r3
        L12:
            r6 = 559946857(0x21601c69, float:7.5931753E-19)
            float r6 = r5.getFloatProperty(r6)     // Catch: java.lang.Exception -> L1b
            goto Lc5
        L1b:
            C extends android.car.hardware.CarEcuManager r6 = r5.mCarManager     // Catch: java.lang.Exception -> L25
            android.car.hardware.bcm.CarBcmManager r6 = (android.car.hardware.bcm.CarBcmManager) r6     // Catch: java.lang.Exception -> L25
            float r6 = r6.getRrdmWinPstState()     // Catch: java.lang.Exception -> L25
            goto Lc5
        L25:
            r6 = move-exception
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.StringBuilder r1 = r4.append(r1)
            java.lang.String r6 = r6.getMessage()
            java.lang.StringBuilder r6 = r1.append(r6)
            java.lang.String r6 = r6.toString()
            com.xiaopeng.carcontrol.util.LogUtils.e(r2, r6, r0)
        L3e:
            r6 = r3
            goto Lc5
        L41:
            r6 = 559946854(0x21601c66, float:7.5931737E-19)
            float r6 = r5.getFloatProperty(r6)     // Catch: java.lang.Exception -> L4a
            goto Lc5
        L4a:
            C extends android.car.hardware.CarEcuManager r6 = r5.mCarManager     // Catch: java.lang.Exception -> L54
            android.car.hardware.bcm.CarBcmManager r6 = (android.car.hardware.bcm.CarBcmManager) r6     // Catch: java.lang.Exception -> L54
            float r6 = r6.getRldmWinPstState()     // Catch: java.lang.Exception -> L54
            goto Lc5
        L54:
            r6 = move-exception
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.StringBuilder r1 = r4.append(r1)
            java.lang.String r6 = r6.getMessage()
            java.lang.StringBuilder r6 = r1.append(r6)
            java.lang.String r6 = r6.toString()
            com.xiaopeng.carcontrol.util.LogUtils.e(r2, r6, r0)
            goto L3e
        L6e:
            r6 = 559946856(0x21601c68, float:7.593175E-19)
            float r6 = r5.getFloatProperty(r6)     // Catch: java.lang.Exception -> L76
            goto Lc5
        L76:
            C extends android.car.hardware.CarEcuManager r6 = r5.mCarManager     // Catch: java.lang.Exception -> L7f
            android.car.hardware.bcm.CarBcmManager r6 = (android.car.hardware.bcm.CarBcmManager) r6     // Catch: java.lang.Exception -> L7f
            float r6 = r6.getFrdmWinPstState()     // Catch: java.lang.Exception -> L7f
            goto Lc5
        L7f:
            r6 = move-exception
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.StringBuilder r1 = r4.append(r1)
            java.lang.String r6 = r6.getMessage()
            java.lang.StringBuilder r6 = r1.append(r6)
            java.lang.String r6 = r6.toString()
            com.xiaopeng.carcontrol.util.LogUtils.e(r2, r6, r0)
            goto L3e
        L99:
            r6 = 559946855(0x21601c67, float:7.593174E-19)
            float r6 = r5.getFloatProperty(r6)     // Catch: java.lang.Exception -> La1
            goto Lc5
        La1:
            C extends android.car.hardware.CarEcuManager r6 = r5.mCarManager     // Catch: java.lang.Exception -> Laa
            android.car.hardware.bcm.CarBcmManager r6 = (android.car.hardware.bcm.CarBcmManager) r6     // Catch: java.lang.Exception -> Laa
            float r6 = r6.getFldmWinPstState()     // Catch: java.lang.Exception -> Laa
            goto Lc5
        Laa:
            r6 = move-exception
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.StringBuilder r1 = r4.append(r1)
            java.lang.String r6 = r6.getMessage()
            java.lang.StringBuilder r6 = r1.append(r6)
            java.lang.String r6 = r6.toString()
            com.xiaopeng.carcontrol.util.LogUtils.e(r2, r6, r0)
            goto L3e
        Lc5:
            r0 = 1120403456(0x42c80000, float:100.0)
            int r0 = (r6 > r0 ? 1 : (r6 == r0 ? 0 : -1))
            if (r0 <= 0) goto Lcc
            goto Lcd
        Lcc:
            r3 = r6
        Lcd:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.carcontrol.carmanager.impl.BcmController.getWinPos(int):float");
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void controlWinVent() {
        int[] windowVentPos = CarBaseConfig.getInstance().getWindowVentPos();
        setWindowsMovePositions(windowVentPos[0], windowVentPos[1], windowVentPos[2], windowVentPos[3]);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setWindowsMovePositions(float flPos, float frPos, float rlPos, float rrPos) {
        LogUtils.i(TAG, "setWindowsMovePositions: [" + flPos + ", " + frPos + ", " + rlPos + ", " + rrPos + "]", false);
        try {
            this.mCarManager.setWindowsMovePositions(flPos, frPos, rlPos, rrPos);
        } catch (Exception e) {
            LogUtils.e(TAG, "setWindowsMovePositions: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void controlMirror(boolean isFold) {
        if (this.mCarConfig.isSupportMirrorFold()) {
            try {
                this.mCarManager.setRearViewMirror(isFold ? 0 : 1);
            } catch (Exception e) {
                LogUtils.e(TAG, "controlMirror: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getReverseMirrorMode() {
        if (this.mCarConfig.isSupportMirrorMemory()) {
            if (this.mIsMainProcess) {
                return this.mDataSync.getMirrorReverseMode();
            }
            return 3;
        }
        return 0;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setReverseMirrorMode(int mode) {
        setReverseMirrorMode(mode, true);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setReverseMirrorMode(final int mode, final boolean needMove) {
        if (this.mCarConfig.isSupportMirrorMemory()) {
            if (this.mIsMainProcess) {
                this.mDataSync.setMirrorReverseMode(mode);
            }
            ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$BcmController$1JWZQFHkXUCpUIYXsob9T0K_T7U
                @Override // java.lang.Runnable
                public final void run() {
                    BcmController.this.lambda$setReverseMirrorMode$3$BcmController(mode, needMove);
                }
            });
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public boolean isMirrorAutoDown() {
        int i;
        if (this.mCarConfig.isSupportMirrorDown()) {
            try {
                try {
                    i = getIntProperty(557849676);
                } catch (Exception unused) {
                    i = this.mCarManager.getRearViewAutoDownCfg();
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "isMirrorAutoDown: " + e.getMessage(), false);
                i = 1;
            }
            return i == 2;
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setMirrorAutoDown(boolean enable) {
        if (this.mCarConfig.isSupportMirrorDown()) {
            try {
                if (this.mIsMainProcess) {
                    this.mDataSync.setMirrorAutoDownSw(enable);
                }
                this.mCarManager.setRearViewAutoDownCfg(enable ? 2 : 1);
            } catch (Exception e) {
                LogUtils.e(TAG, "setMirrorAutoDown: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public boolean isMirrorAutoFoldEnable() {
        int i;
        try {
            i = getIntProperty(557849948);
        } catch (Exception e) {
            try {
                i = this.mCarManager.getMirrorAutoFoldSwitchStatus();
            } catch (Exception unused) {
                LogUtils.e(TAG, "getMirrorAutoFoldSwitchStatus: " + e.getMessage(), false);
                i = 0;
            }
        }
        return i == 1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setMirrorAutoFoldEnable(boolean enable) {
        try {
            if (this.mIsMainProcess) {
                this.mDataSync.setMirrorAutoFoldEnable(enable);
            }
            this.mCarManager.setMirrorAutoFoldSwitchStatus(enable ? 1 : 0);
        } catch (Exception e) {
            LogUtils.e(TAG, "setMirrorAutoFoldEnable: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void controlLeftMirrorMove(int control, int direction) {
        if (this.mCarConfig.isSupportAdjMirrorByCdu()) {
            try {
                this.mCarManager.setLeftMirrorCtrl(control, direction);
            } catch (Exception e) {
                LogUtils.e(TAG, "controlLeftMirrorMove: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void controlRightMirrorMove(int control, int direction) {
        if (this.mCarConfig.isSupportAdjMirrorByCdu()) {
            try {
                this.mCarManager.setRightMirrorCtrl(control, direction);
            } catch (Exception e) {
                LogUtils.e(TAG, "controlRightMirrorMove: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setMirrorLocation(int leftHPos, int leftVPos, int rightHPos, int rightVPos) {
        if (this.mCarConfig.isSupportMirrorPositionSignalSet()) {
            try {
                this.mCarManager.setAllExteriorMirrorsPositions(leftHPos, leftVPos, rightHPos, rightVPos);
            } catch (Exception e) {
                LogUtils.e(TAG, "setMirrorLocation: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getLeftMirrorLRPos() {
        return getLeftMirrorLRPos(false);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getLeftMirrorLRPos(boolean ignoreCache) {
        int leftMirrorLrPos;
        if (this.mCarConfig.isSupportMirrorMemory() || this.mCarConfig.isSupportMirrorDown()) {
            if (!ignoreCache) {
                try {
                    try {
                        return getIntProperty(557849660);
                    } catch (Exception unused) {
                        leftMirrorLrPos = this.mCarManager.getLeftMirrorLrPos();
                    }
                } catch (Exception e) {
                    LogUtils.e(TAG, "getLeftMirrorLRPos failed: " + e.getMessage(), false);
                    return 50;
                }
            } else {
                try {
                    leftMirrorLrPos = this.mCarManager.getLeftMirrorLrPos();
                    LogUtils.i(TAG, "getLeftMirrorLRPos: " + leftMirrorLrPos, false);
                } catch (Exception e2) {
                    LogUtils.e(TAG, "getLeftMirrorLRPos failed: " + e2.getMessage(), false);
                    return 50;
                }
            }
            return leftMirrorLrPos;
        }
        return -1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getLeftMirrorUDPos() {
        return getLeftMirrorUDPos(false);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getLeftMirrorUDPos(boolean ignoreCache) {
        int leftMirrorUdPos;
        if (this.mCarConfig.isSupportMirrorMemory() || this.mCarConfig.isSupportMirrorDown()) {
            if (!ignoreCache) {
                try {
                    try {
                        return getIntProperty(557849662);
                    } catch (Exception unused) {
                        leftMirrorUdPos = this.mCarManager.getLeftMirrorUdPos();
                    }
                } catch (Exception e) {
                    LogUtils.e(TAG, "getLeftMirrorUDPos failed: " + e.getMessage(), false);
                    return 50;
                }
            } else {
                try {
                    leftMirrorUdPos = this.mCarManager.getLeftMirrorUdPos();
                    LogUtils.i(TAG, "getLeftMirrorUdPos: " + leftMirrorUdPos, false);
                } catch (Exception e2) {
                    LogUtils.e(TAG, "getLeftMirrorUDPos failed: " + e2.getMessage(), false);
                    return 50;
                }
            }
            return leftMirrorUdPos;
        }
        return -1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getRightMirrorLRPos() {
        return getRightMirrorLRPos(false);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getRightMirrorLRPos(boolean ignoreCache) {
        int rightMirrorLrPos;
        if (this.mCarConfig.isSupportMirrorMemory() || this.mCarConfig.isSupportMirrorDown()) {
            if (!ignoreCache) {
                try {
                    try {
                        return getIntProperty(557849661);
                    } catch (Exception unused) {
                        rightMirrorLrPos = this.mCarManager.getRightMirrorLrPos();
                    }
                } catch (Exception e) {
                    LogUtils.e(TAG, "getRightMirrorLRPos failed: " + e.getMessage(), false);
                    return 50;
                }
            } else {
                try {
                    rightMirrorLrPos = this.mCarManager.getRightMirrorLrPos();
                    LogUtils.i(TAG, "getRightMirrorLrPos: " + rightMirrorLrPos, false);
                } catch (Exception e2) {
                    LogUtils.e(TAG, "getRightMirrorLRPos failed: " + e2.getMessage(), false);
                    return 50;
                }
            }
            return rightMirrorLrPos;
        }
        return -1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getRightMirrorUDPos() {
        return getRightMirrorUDPos(false);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getRightMirrorUDPos(boolean ignoreCache) {
        int rightMirrorUdPos;
        if (this.mCarConfig.isSupportMirrorMemory() || this.mCarConfig.isSupportMirrorDown()) {
            if (!ignoreCache) {
                try {
                    try {
                        return getIntProperty(557849663);
                    } catch (Exception unused) {
                        rightMirrorUdPos = this.mCarManager.getRightMirrorUdPos();
                    }
                } catch (Exception e) {
                    LogUtils.e(TAG, "getRightMirrorUDPos failed: " + e.getMessage(), false);
                    return 50;
                }
            } else {
                try {
                    rightMirrorUdPos = this.mCarManager.getRightMirrorUdPos();
                    LogUtils.i(TAG, "getRightMirrorUdPos: " + rightMirrorUdPos, false);
                } catch (Exception e2) {
                    LogUtils.e(TAG, "getRightMirrorUDPos failed: " + e2.getMessage(), false);
                    return 50;
                }
            }
            return rightMirrorUdPos;
        }
        return -1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int[] getMirrorPosition() {
        if (this.mCarConfig.isSupportMirrorMemory() || this.mCarConfig.isSupportMirrorDown()) {
            return new int[]{getLeftMirrorLRPos(), getLeftMirrorUDPos(), getRightMirrorLRPos(), getRightMirrorUDPos()};
        }
        return null;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int[] getMirrorPosition(boolean ignoreCache) {
        if (this.mCarConfig.isSupportMirrorMemory() || this.mCarConfig.isSupportMirrorDown()) {
            return new int[]{getLeftMirrorLRPos(ignoreCache), getLeftMirrorUDPos(ignoreCache), getRightMirrorLRPos(ignoreCache), getRightMirrorUDPos(ignoreCache)};
        }
        return null;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int[] getRearViewMirrorsAdjustStates() {
        int[] iArr = {0, 0, 0, 0};
        if (this.mCarConfig.isSupportAdjMirrorByCdu()) {
            return iArr;
        }
        try {
            try {
                return getIntArrayProperty(557915345);
            } catch (Exception unused) {
                return this.mCarManager.getRearViewMirrorsAdjustStates();
            }
        } catch (Exception e) {
            LogUtils.e(TAG, "getRearViewMirrorsAdjustStates: " + e.getMessage(), false);
            return iArr;
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getBonnetState() {
        if (this.mCarConfig.isSupportCtrlBonnet()) {
            try {
                try {
                    return getIntProperty(557849641);
                } catch (Exception unused) {
                    return this.mCarManager.isBcmBonnetOpened();
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "getBonnetState: " + e.getMessage(), false);
                return 0;
            }
        }
        return 0;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getRearTrunk() {
        try {
            try {
                return getIntProperty(557849610);
            } catch (Exception e) {
                LogUtils.e(TAG, "getRearTrunk: " + e.getMessage(), false);
                return 0;
            }
        } catch (Exception unused) {
            return this.mCarManager.getTrunk();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getElcTrunkState() {
        try {
            try {
                return getIntProperty(557849903);
            } catch (Exception e) {
                LogUtils.e(TAG, "getElcTrunkState: " + e.getMessage(), false);
                return 1;
            }
        } catch (Exception unused) {
            return this.mCarManager.getTrunkOpennerStatus();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getElcTrunkPos() {
        int i;
        try {
            try {
                i = getIntProperty(557849946);
            } catch (Exception e) {
                LogUtils.e(TAG, "getElcTrunkPos: " + e.getMessage(), false);
                i = 1;
            }
        } catch (Exception unused) {
            i = this.mCarManager.getTrunkActualPosition();
        }
        return fixElcTrunkPos(i, ISync.SYNC_CALL_METHOD_GET);
    }

    private int fixElcTrunkPos(int position, String logTag) {
        int i = 1;
        int i2 = position == 0 ? 1 : position;
        if (i2 == 2) {
            try {
                int trunkOpennerStatus = this.mCarManager.getTrunkOpennerStatus();
                if (trunkOpennerStatus != 1 && trunkOpennerStatus != 9) {
                    i = i2;
                }
                i2 = i;
            } catch (Throwable th) {
                LogUtils.e(TAG, "fixElcTrunkPos: " + th.getMessage(), false);
            }
        }
        if (position != i2) {
            LogUtils.i(TAG, "fixElcTrunkPos: " + position + "->" + i2 + ", " + logTag, false);
        }
        return i2;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void controlRearTrunk(int controlType) {
        try {
            this.mCarManager.setTrunk(controlType);
        } catch (Exception e) {
            LogUtils.e(TAG, "controlRearTrunk: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public boolean isTrunkSensorEnable() {
        if (this.mCarConfig.isSupportSensorTrunk()) {
            try {
                try {
                    if (getIntProperty(557849869) != 1) {
                        return false;
                    }
                } catch (Exception unused) {
                    if (this.mCarManager.getFootKickSwitchStatus() != 1) {
                        return false;
                    }
                }
                return true;
            } catch (Exception e) {
                LogUtils.e(TAG, "getFootKickSwitchStatus failed: " + e.getMessage(), false);
                return false;
            }
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setTrunkSensorEnable(boolean enable) {
        if (this.mCarConfig.isSupportSensorTrunk()) {
            try {
                this.mCarManager.setFootKickSwitchStatus(enable ? 1 : 0);
                if (this.mIsMainProcess) {
                    this.mDataSync.setSensorTrunkSw(enable);
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "setFootKickSwitchStatus failed: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getTrunkFullOpenPos() {
        if (this.mCarConfig.isSupportTrunkSetPosition()) {
            try {
                try {
                    return getIntProperty(557849871);
                } catch (Exception e) {
                    LogUtils.e(TAG, "getTrunkSetPositionResponcePosition failed: " + e.getMessage(), false);
                }
            } catch (Exception unused) {
                return this.mCarManager.getTrunkSetPositionResponcePosition();
            }
        }
        return 6;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setTrunkFullOpenPos(int pos) {
        if (this.mCarConfig.isSupportTrunkSetPosition()) {
            try {
                this.mCarManager.setTrunkSetPositionRequest(pos);
            } catch (Exception e) {
                LogUtils.e(TAG, "setTrunkSetPositionRequest failed: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getTrunkWorkModeStatus() {
        int i;
        if (this.mCarConfig.isSupportElcTrunk()) {
            try {
                try {
                    i = getIntProperty(557849986);
                } catch (Throwable th) {
                    LogUtils.e(TAG, "getTrunkWorkModeStatus: " + th.getMessage(), false);
                }
            } catch (Exception unused) {
                i = this.mCarManager.getTrunkWorkModeStatus();
            }
            LogUtils.i(TAG, "getTrunkWorkModeStatus: " + i, false);
            return i;
        }
        i = 0;
        LogUtils.i(TAG, "getTrunkWorkModeStatus: " + i, false);
        return i;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public boolean getDriveAutoLock() {
        int driveAutoLock;
        try {
            try {
                driveAutoLock = getIntProperty(557849628);
            } catch (Exception e) {
                LogUtils.e(TAG, "getDriveAutoLock: " + e.getMessage(), false);
                if (this.mDataSync == null) {
                    return true;
                }
                return this.mDataSync.getDriveAutoLock();
            }
        } catch (Exception unused) {
            driveAutoLock = this.mCarManager.getDriveAutoLock();
        }
        return parseBcmStatus(driveAutoLock);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setDriveAutoLock(boolean enable) {
        try {
            this.mCarManager.setDriveAutoLock(parseCduSwitchCmd(enable));
            if (this.mIsMainProcess) {
                this.mDataSync.setDriveAutoLock(enable);
            }
        } catch (Exception e) {
            LogUtils.e(TAG, "setDriveAutoLock: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public boolean getParkingAutoUnlock() {
        int parkingAutoUnlock;
        try {
            try {
                parkingAutoUnlock = getIntProperty(557849629);
            } catch (Exception e) {
                LogUtils.e(TAG, "getParkingAutoUnlock: " + e.getMessage(), false);
                if (this.mDataSync == null) {
                    return false;
                }
                return this.mDataSync.getParkAutoUnlock();
            }
        } catch (Exception unused) {
            parkingAutoUnlock = this.mCarManager.getParkingAutoUnlock();
        }
        return parseBcmStatus(parkingAutoUnlock);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setParkingAutoUnlock(boolean enable) {
        try {
            this.mCarManager.setParkingAutoUnlock(parseCduSwitchCmd(enable));
            if (this.mIsMainProcess) {
                this.mDataSync.setParkAutoUnlock(enable);
            } else {
                CarControl.PrivateControl.putBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.PARK_AUTO_UNLOCK, enable);
            }
        } catch (Exception e) {
            LogUtils.e(TAG, "setParkingAutoUnlock: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public boolean getNearAutoUnlock() {
        if (this.mCarConfig.isSupportPollingLock()) {
            try {
                try {
                    if (getIntProperty(557849717) != 1) {
                        return false;
                    }
                } catch (Exception unused) {
                    if (this.mCarManager.getNearePollingUnLockSw() != 1) {
                        return false;
                    }
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "getNearAutoUnlock: " + e.getMessage(), false);
                if (this.mDataSync != null) {
                    return this.mDataSync.getPollingUnlock();
                }
            }
            return true;
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setNearAutoUnlock(boolean enable) {
        if (this.mCarConfig.isSupportPollingLock()) {
            try {
                this.mCarManager.setNearPollingUnLockSw(enable ? 1 : 0);
            } catch (Exception e) {
                LogUtils.e(TAG, "setNearAutoUnlock: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public boolean getLeaveAutoLock() {
        if (this.mCarConfig.isSupportPollingLock()) {
            try {
                try {
                    if (getIntProperty(557849716) != 1) {
                        return false;
                    }
                } catch (Exception unused) {
                    if (this.mCarManager.getLeavePollingLockSw() != 1) {
                        return false;
                    }
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "getLeaveAutoLock: " + e.getMessage(), false);
                if (this.mDataSync != null) {
                    return this.mDataSync.getPollingLock();
                }
            }
            return true;
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setLeaveAutoLock(boolean enable) {
        if (this.mCarConfig.isSupportPollingLock()) {
            try {
                this.mCarManager.setLeavePollingLockSw(enable ? 1 : 0);
            } catch (Exception e) {
                LogUtils.e(TAG, "setLeaveAutoLock: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getUnlockResponse() {
        try {
            try {
                return getIntProperty(557849630);
            } catch (Exception e) {
                LogUtils.e(TAG, "getUnlockResponse: " + e.getMessage(), false);
                return this.mDataSync == null ? GlobalConstant.DEFAULT.UNLOCK_RESPONSE : this.mDataSync.getUnlockResponse();
            }
        } catch (Exception unused) {
            return this.mCarManager.getUnlockResponse();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getUnlockResponseSp() {
        int unlockResponse = this.mIsMainProcess ? this.mDataSync.getUnlockResponse() : ((Integer) getContentProviderValue(CarControl.PrivateControl.UNLOCK_RESPONSE, this.mUnlockResponse, Integer.valueOf(GlobalConstant.DEFAULT.UNLOCK_RESPONSE))).intValue();
        LogUtils.i(TAG, "getUnlockResponseSp, value: " + unlockResponse, false);
        return unlockResponse;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setUnlockResponse(final int type) {
        try {
            LogUtils.i(TAG, "setUnlockResponse: " + type, false);
            int i = 1;
            if (this.mCarConfig.isSupportNewUnlockResponseArch()) {
                this.mCarManager.setLockHazardLightSwitchStatus(1);
                CarBcmManager carBcmManager = this.mCarManager;
                if (type != 0) {
                    i = 0;
                }
                carBcmManager.setLockHornSwitchStatus(i);
            } else if (type == 2) {
                this.mCarManager.setUnlockResponse(1);
            } else {
                this.mCarManager.setUnlockResponse(type);
            }
            if (this.mCarConfig.isSupportUnity3D()) {
                CarControl.PrivateControl.putInt(App.getInstance().getContentResolver(), CarControl.PrivateControl.UNLOCK_RESPONSE, type);
                if (this.mIsMainProcess) {
                    return;
                }
                this.mUnlockResponse = Integer.valueOf(type);
                ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$BcmController$nTZ89r2IMlX3zkAd-sI4hVIollI
                    @Override // java.lang.Runnable
                    public final void run() {
                        BcmController.this.lambda$setUnlockResponse$4$BcmController(type);
                    }
                });
                return;
            }
            this.mDataSync.setUnlockResponse(type);
            ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$BcmController$O3u1nbu50J6SktmFHnVON0ALy0I
                @Override // java.lang.Runnable
                public final void run() {
                    BcmController.this.lambda$setUnlockResponse$5$BcmController(type);
                }
            });
        } catch (Exception e) {
            LogUtils.e(TAG, "setUnlockResponse: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public boolean getLockAvasSw() {
        int i;
        if (this.mCarConfig.isSupportNewUnlockResponseArch()) {
            try {
                try {
                    i = getIntProperty(557849876);
                } catch (Exception unused) {
                    i = this.mCarManager.getLockAvasSwitchStatus();
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "getLockAvasSw: " + e.getMessage(), false);
                i = 1;
            }
            return i == 1;
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setLockAvasSw(boolean enable) {
        if (this.mCarConfig.isSupportNewUnlockResponseArch()) {
            try {
                this.mCarManager.setLockAvasSwitchStatus(enable ? 1 : 0);
            } catch (Exception e) {
                LogUtils.e(TAG, "setLockAvasSw: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getChildLock() {
        if (!this.mCarConfig.isSupportChildLock() || this.mCarConfig.isSupportNewChildLock()) {
            return -1;
        }
        try {
            try {
                return getIntProperty(557849675);
            } catch (Exception e) {
                LogUtils.e(TAG, "getChildLock: " + e.getMessage(), false);
                return 1;
            }
        } catch (Exception unused) {
            return this.mCarManager.getChildLockCfg();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setChildLock(int lockCmd) {
        if (!this.mCarConfig.isSupportChildLock() || this.mCarConfig.isSupportNewChildLock()) {
            return;
        }
        try {
            this.mCarManager.setChildLockCfg(lockCmd);
        } catch (Exception e) {
            LogUtils.e(TAG, "setChildLock1: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public boolean getChildLeftLock() {
        int i;
        if (this.mCarConfig.isSupportChildLock()) {
            if (!this.mCarConfig.isSupportNewChildLock()) {
                int childLock = getChildLock();
                return childLock == 4 || childLock == 3;
            }
            try {
                try {
                    i = getIntProperty(557849858);
                } catch (Exception unused) {
                    i = this.mCarManager.getLeftChildLockSwitchStatus();
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "getLeftChildLockSwitchStatus: " + e.getMessage(), false);
                i = 2;
            }
            return i == 4;
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public boolean getChildRightLock() {
        int i;
        if (this.mCarConfig.isSupportChildLock()) {
            if (!this.mCarConfig.isSupportNewChildLock()) {
                int childLock = getChildLock();
                return childLock == 4 || childLock == 2;
            }
            try {
                try {
                    i = getIntProperty(557849859);
                } catch (Exception unused) {
                    i = this.mCarManager.getRightChildLockSwitchStatus();
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "getRightChildLockSwitchStatus: " + e.getMessage(), false);
                i = 3;
            }
            return i == 5;
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setAllChildLock(boolean lock, boolean needSave) {
        if (this.mCarConfig.isSupportChildLock()) {
            try {
                if (this.mCarConfig.isSupportNewChildLock()) {
                    this.mCarManager.setLeftChildLockSwitchStatus(lock ? 4 : 2);
                    this.mCarManager.setRightChildLockSwitchStatus(lock ? 5 : 3);
                } else {
                    this.mCarManager.setChildLockCfg(lock ? 6 : 1);
                }
                if (needSave) {
                    if (this.mIsMainProcess) {
                        this.mDataSync.setChildLeftLock(lock);
                        this.mDataSync.setChildRightLock(lock);
                        return;
                    }
                    CarControl.PrivateControl.putBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.CHILD_LEFT_LOCK, lock);
                    CarControl.PrivateControl.putBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.CHILD_RIGHT_LOCK, lock);
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "setAllChildLock: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setChildLock(boolean left, boolean lock) {
        setChildLock(left, lock, true);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setChildLock(boolean leftSide, boolean lock, boolean needSave) {
        if (this.mCarConfig.isSupportChildLock()) {
            try {
                if (!this.mCarConfig.isSupportNewChildLock()) {
                    this.mCarManager.setChildLockCfg(parseChildLockCmd(leftSide, lock));
                } else if (leftSide) {
                    this.mCarManager.setLeftChildLockSwitchStatus(lock ? 4 : 2);
                } else {
                    this.mCarManager.setRightChildLockSwitchStatus(lock ? 5 : 3);
                }
                if (needSave) {
                    if (this.mIsMainProcess) {
                        if (leftSide) {
                            this.mDataSync.setChildLeftLock(lock);
                        } else {
                            this.mDataSync.setChildRightLock(lock);
                        }
                    } else if (leftSide) {
                        CarControl.PrivateControl.putBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.CHILD_LEFT_LOCK, lock);
                    } else {
                        CarControl.PrivateControl.putBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.CHILD_RIGHT_LOCK, lock);
                    }
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "setChildLock2: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public boolean isChildModeEnable() {
        return Settings.System.getInt(App.getInstance().getContentResolver(), CHILD_MODE_SW, 0) == 1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setChildModeEnable(boolean enable, boolean fromUser) {
        Settings.System.putInt(App.getInstance().getContentResolver(), CHILD_MODE_SW, enable ? 1 : 0);
        handleChildMode(enable);
        boolean z = false;
        if (enable) {
            final boolean z2 = fromUser ? !this.mDataSync.isChildModeOpened() : false;
            LogUtils.i(TAG, "need force open child mode? " + z2);
            if (CarBaseConfig.getInstance().isSupportNewChildLock()) {
                setChildLock(true, this.mDataSync.getChildLeftLock() || z2);
                setChildLock(false, this.mDataSync.getChildRightLock() || z2);
            } else {
                setChildLock(true, this.mDataSync.getChildLeftLock() || z2);
                ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$BcmController$S0n8r5dTYc25zwp2qi9ShbRFXRY
                    @Override // java.lang.Runnable
                    public final void run() {
                        BcmController.this.lambda$setChildModeEnable$6$BcmController(z2);
                    }
                });
            }
            setLeftDoorHotKey(this.mDataSync.getLeftDoorHotKey() || z2);
            if (this.mDataSync.getRightDoorHotKey() || z2) {
                z = true;
            }
            setRightDoorHotKey(z);
        } else {
            if (CarBaseConfig.getInstance().isSupportNewChildLock()) {
                setChildLock(true, false, false);
                setChildLock(false, false, false);
            } else {
                setChildLock(1);
            }
            setLeftDoorHotKey(false, false);
            setRightDoorHotKey(false, false);
        }
        if (fromUser && enable) {
            try {
                this.mCarManager.setWindowLockState(1);
            } catch (Exception e) {
                LogUtils.d(TAG, "setChildModeEnable" + e.getMessage());
            }
        }
    }

    public /* synthetic */ void lambda$setChildModeEnable$6$BcmController(final boolean finalNeedForceOpen) {
        try {
            Thread.sleep(650L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setChildLock(false, this.mDataSync.getChildRightLock() || finalNeedForceOpen);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setLeftDoorHotKey(boolean enable) {
        setLeftDoorHotKey(enable, true);
    }

    private void setLeftDoorHotKey(boolean enable, boolean needSave) {
        Settings.System.putInt(App.getInstance().getContentResolver(), LEFT_DOOR_HOT_KEY_SW, enable ? 1 : 0);
        handleLeftHotKey(enable);
        if (needSave) {
            if (this.mIsMainProcess) {
                this.mDataSync.setLeftDoorHotKey(enable);
            } else {
                CarControl.PrivateControl.putBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.CHILD_LEFT_DOOR_HOT_KEY, enable);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setRightDoorHotKey(boolean enable) {
        setRightDoorHotKey(enable, true);
    }

    private void setRightDoorHotKey(boolean enable, boolean needSave) {
        Settings.System.putInt(App.getInstance().getContentResolver(), RIGHT_DOOR_HOT_KEY_SW, enable ? 1 : 0);
        handleRightHotKey(enable);
        if (needSave) {
            if (this.mIsMainProcess) {
                this.mDataSync.setRightDoorHotKey(enable);
            } else {
                CarControl.PrivateControl.putBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.CHILD_RIGHT_DOOR_HOT_KEY, enable);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public boolean isLeftDoorHotKeyEnable() {
        return Settings.System.getInt(App.getInstance().getContentResolver(), LEFT_DOOR_HOT_KEY_SW, 0) == 1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public boolean isRightDoorHotKeyEnable() {
        return Settings.System.getInt(App.getInstance().getContentResolver(), RIGHT_DOOR_HOT_KEY_SW, 0) == 1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setPollingOpenCfg(boolean enable) {
        if (!this.mCarConfig.isSupportPollingOpenCfg() || this.mCarManager == 0) {
            return;
        }
        LogUtils.i(TAG, "setPollingOpenCfg enable:" + enable, false);
        int i = enable ? 1 : 0;
        try {
            this.mCarManager.setPollingOpenCfg(i);
            LogUtils.i(TAG, "setPollingOpenCfg:" + i, false);
        } catch (Exception e) {
            LogUtils.e(TAG, "setPollingOpenCfg: " + e.getMessage(), false);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0051  */
    /* JADX WARN: Removed duplicated region for block: B:24:? A[RETURN, SYNTHETIC] */
    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean getPollingOpenCfg() {
        /*
            r6 = this;
            java.lang.String r0 = "BcmController"
            com.xiaopeng.carcontrol.config.CarBaseConfig r1 = r6.mCarConfig
            boolean r1 = r1.isSupportPollingOpenCfg()
            r2 = 0
            if (r1 == 0) goto L52
            C extends android.car.hardware.CarEcuManager r1 = r6.mCarManager
            if (r1 == 0) goto L4d
            C extends android.car.hardware.CarEcuManager r1 = r6.mCarManager     // Catch: java.lang.Throwable -> L30
            android.car.hardware.bcm.CarBcmManager r1 = (android.car.hardware.bcm.CarBcmManager) r1     // Catch: java.lang.Throwable -> L30
            int r1 = r1.getPollingOpenCfg()     // Catch: java.lang.Throwable -> L30
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L2e
            r3.<init>()     // Catch: java.lang.Throwable -> L2e
            java.lang.String r4 = "getPollingOpenCfg over status:"
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch: java.lang.Throwable -> L2e
            java.lang.StringBuilder r3 = r3.append(r1)     // Catch: java.lang.Throwable -> L2e
            java.lang.String r3 = r3.toString()     // Catch: java.lang.Throwable -> L2e
            com.xiaopeng.carcontrol.util.LogUtils.i(r0, r3, r2)     // Catch: java.lang.Throwable -> L2e
            goto L4e
        L2e:
            r3 = move-exception
            goto L32
        L30:
            r3 = move-exception
            r1 = r2
        L32:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "getPollingOpenCfg: "
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r3 = r3.getMessage()
            java.lang.StringBuilder r3 = r4.append(r3)
            java.lang.String r3 = r3.toString()
            com.xiaopeng.carcontrol.util.LogUtils.e(r0, r3, r2)
            goto L4e
        L4d:
            r1 = r2
        L4e:
            r0 = 1
            if (r1 != r0) goto L52
            r2 = r0
        L52:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.carcontrol.carmanager.impl.BcmController.getPollingOpenCfg():boolean");
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void openCarBonnet() {
        if (this.mCarConfig.isSupportCtrlBonnet()) {
            try {
                this.mCarManager.openBcmBonnet();
            } catch (Exception e) {
                LogUtils.e(TAG, "openCarBonnet: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setWiperInterval(int interval) {
        if (this.mCarConfig.isSupportWiperInterval()) {
            this.mDataSync.setWiperInterval(interval);
            try {
                this.mCarManager.setWiperInterval(interval);
            } catch (Exception e) {
                LogUtils.e(TAG, "setWiperInterval: " + e.getMessage(), false);
            }
            handleWiperInterval(interval);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getWiperInterval() {
        if (this.mCarConfig.isSupportWiperInterval()) {
            return this.mDataSync.getWiperInterval();
        }
        return -1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setWiperRepairMode(boolean on) {
        if (this.mCarConfig.isSupportWiperRepair()) {
            try {
                this.mCarManager.setWiperServiceMode(parseCduSwitchCmd(on));
            } catch (Exception e) {
                LogUtils.e(TAG, "setWiperRepairMode: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public boolean getWiperRepairMode() {
        if (this.mCarConfig.isSupportWiperRepair()) {
            try {
                try {
                    return parseBcmStatus(getIntProperty(557849611));
                } catch (Exception e) {
                    LogUtils.e(TAG, "getWiperRepairMode: " + e.getMessage(), false);
                    return false;
                }
            } catch (Exception unused) {
                return parseBcmStatus(this.mCarManager.getWiperServiceMode());
            }
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setRearWiperRepairMode(boolean on) {
        if (this.mCarConfig.isSupportRearWiper()) {
            try {
                this.mCarManager.setRearWiperServiceSwitchStatus(parseCduSwitchCmd(on));
            } catch (Exception e) {
                LogUtils.e(TAG, "setRearWiperRepairMode: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public boolean getRearWiperRepairMode() {
        if (this.mCarConfig.isSupportRearWiper()) {
            try {
                try {
                    return parseBcmStatus(getIntProperty(557849860));
                } catch (Exception e) {
                    LogUtils.e(TAG, "getRearWiperRepairMode: " + e.getMessage(), false);
                    return false;
                }
            } catch (Exception unused) {
                return parseBcmStatus(this.mCarManager.getRearWiperServiceSwitchStatus());
            }
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public boolean isWiperSpeedSwitchOff() {
        if (this.mCarConfig.isSupportNewWiperSpdSt()) {
            int[] iArr = null;
            try {
                iArr = this.mCarManager.getWiperSpeedSwitchesStatus();
            } catch (Exception e) {
                LogUtils.e(TAG, "isWiperSpeedSwitchOff: " + e.getMessage(), false);
            }
            if (iArr == null || iArr.length < 3) {
                return false;
            }
            LogUtils.i(TAG, "wiperSpeedSwitch state : " + Arrays.toString(iArr), false);
            return iArr[0] == 0 && iArr[1] == 0 && iArr[2] == 0;
        }
        int i = -1;
        try {
            i = this.mCarManager.getWiperSpeedSwitchState();
        } catch (Exception e2) {
            LogUtils.e(TAG, "isWiperSpeedSwitchOff: " + e2.getMessage(), false);
        }
        LogUtils.i(TAG, "wiperSpeedSwitch state is: " + i, false);
        return i == 0;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public boolean isRearWiperSpeedSwitchOff() {
        if (CarBaseConfig.getInstance().isSupportRearWiper()) {
            int i = -1;
            try {
                i = this.mCarManager.getRearWiperMotorStatus();
            } catch (Exception e) {
                LogUtils.e(TAG, "isRearWiperSpeedSwitchOff: " + e.getMessage(), false);
            }
            LogUtils.i(TAG, "Rear wiper Motor Status is: " + i, false);
            return i == 0;
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public boolean isWiperFault() {
        if (this.mCarConfig.isSupportWiperFault()) {
            try {
                if (this.mCarManager.getFWiperMotorErr() == 1) {
                    return true;
                }
            } catch (Error | Exception e) {
                LogUtils.e(TAG, "getFWiperMotorErr failed: " + e.getMessage(), false);
                return true;
            }
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getFWiperActiveSt() {
        try {
            return this.mCarManager.getFrontWiperActiveStatus();
        } catch (Exception unused) {
            return 0;
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public boolean getSeatWelcomeMode() {
        int chairWelcomeMode;
        try {
            try {
                chairWelcomeMode = getIntProperty(557849601);
            } catch (Exception e) {
                LogUtils.e(TAG, "getSeatWelcomeMode: " + e.getMessage(), false);
                return true;
            }
        } catch (Exception unused) {
            chairWelcomeMode = this.mCarManager.getChairWelcomeMode();
        }
        return parseBcmStatus(chairWelcomeMode);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setSeatWelcomeMode(boolean enable) {
        this.mDataSync.setWelcomeMode(enable);
        try {
            this.mCarManager.setChairWelcomeMode(parseCduSwitchCmd(enable));
        } catch (Exception e) {
            LogUtils.e(TAG, "setSeatWelcomeMode: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public boolean getRearSeatWelcomeMode() {
        boolean bool;
        if (this.mCarConfig.isSupportRearSeatWelcomeMode()) {
            if (this.mIsMainProcess) {
                bool = this.mDataSync.getRearSeatWelcomeMode();
            } else {
                bool = CarControl.PrivateControl.getBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.REAR_SEAT_WELCOME_SW, false);
            }
            LogUtils.i(TAG, "getRearSeatWelcomeMode: " + bool, false);
            return bool;
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setRearSeatWelcomeMode(final boolean enable, boolean needSave) {
        if (this.mCarConfig.isSupportRearSeatWelcomeMode()) {
            LogUtils.i(TAG, "setRearSeatWelcomeMode: " + enable, false);
            try {
                this.mCarManager.setSecRowSeatEasyEntrySwitchStatus(enable ? 1 : 0);
                if (needSave) {
                    if (this.mIsMainProcess) {
                        this.mDataSync.setRearSeatWelcomeMode(enable);
                    } else {
                        CarControl.PrivateControl.putBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.REAR_SEAT_WELCOME_SW, enable);
                    }
                }
                ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$BcmController$OYACOhrYXlkacxii4cCSmS1bQlk
                    @Override // java.lang.Runnable
                    public final void run() {
                        BcmController.this.lambda$setRearSeatWelcomeMode$7$BcmController(enable);
                    }
                });
            } catch (Exception e) {
                LogUtils.e(TAG, "setRearSeatWelcomeMode failed: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public boolean getCarpetLightWelcomeMode() {
        int i;
        if (this.mCarConfig.isSupportCarpetLightWelcomeMode()) {
            try {
                try {
                    i = getIntProperty(557849991);
                } catch (Exception unused) {
                    i = this.mCarManager.getCarpetLightWelcomeState();
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "getCarpetLightWelcomeState: " + e.getMessage(), false);
                i = 1;
            }
            return i == 1;
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setCarpetLightWelcomeMode(boolean enable) {
        if (this.mCarConfig.isSupportCarpetLightWelcomeMode()) {
            try {
                this.mCarManager.setCarpetLightWelcomeSw(enable ? 1 : 0);
                if (this.mIsMainProcess) {
                    this.mDataSync.setCarpetLightWelcomeMode(enable);
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "setPollingWelcomeSW: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public boolean getPollingLightWelcomeMode() {
        int i;
        if (this.mCarConfig.isSupportPollingLightWelcomeMode()) {
            try {
                try {
                    i = getIntProperty(557849992);
                } catch (Exception unused) {
                    i = this.mCarManager.getPollingWelcomeState();
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "getPollingWelcomeState: " + e.getMessage(), false);
                i = 1;
            }
            return i == 1;
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setPollingLightWelcomeMode(boolean enable) {
        if (this.mCarConfig.isSupportPollingLightWelcomeMode()) {
            try {
                this.mCarManager.setPollingWelcomeSW(enable ? 1 : 0);
                if (this.mIsMainProcess) {
                    this.mDataSync.setPollingLightWelcomeMode(enable);
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "setPollingWelcomeSW: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public boolean isEsbEnabled() {
        int i = 0;
        if (this.mCarConfig.isSupportBcmCtrlSeatBelt()) {
            try {
                try {
                    i = getIntProperty(557849635);
                } catch (Exception unused) {
                    i = this.mCarManager.getElectricSeatBelt();
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "isEsbEnabled: " + e.getMessage(), false);
            }
            return parseBcmStatus(i);
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public boolean getEsbModeSp() {
        if (this.mCarConfig.isSupportBcmCtrlSeatBelt()) {
            if (this.mIsMainProcess) {
                return this.mDataSync.getDrvSeatEsb();
            }
            return true;
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setEsbEnable(boolean enable) {
        LogUtils.i(TAG, "setEsbEnable: " + enable, false);
        if (this.mCarConfig.isSupportBcmCtrlSeatBelt()) {
            try {
                this.mCarManager.setElectricSeatBelt(parseCduSwitchCmd(enable));
                if (this.mIsMainProcess) {
                    this.mDataSync.setDrvSeatEsb(enable);
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "setEsbEnable: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setEsbEnable(boolean enable, boolean saveEnable) {
        LogUtils.i(TAG, "setEsbEnable: " + enable + ", saveEnable: " + saveEnable, false);
        if (this.mCarConfig.isSupportBcmCtrlSeatBelt()) {
            try {
                this.mCarManager.setElectricSeatBelt(parseCduSwitchCmd(enable));
                if (this.mIsMainProcess) {
                    this.mDataSync.setDrvSeatEsb(saveEnable);
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "setEsbEnable: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public boolean isRsbWarningEnabled() {
        int i;
        int i2 = 0;
        if (this.mCarConfig.isSupportBcmCtrlSeatBelt()) {
            try {
                try {
                    i = getIntProperty(557849636);
                } catch (Exception unused) {
                    i = this.mCarManager.getRearSeatBeltWarning();
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "isRsbWarningEnabled: " + e.getMessage(), false);
                if (this.mIsMainProcess && this.mDataSync.getRsbWarning()) {
                    i2 = 1;
                }
                i = i2;
            }
            return parseBcmStatus(i);
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setRsbWarning(boolean enable) {
        if (this.mCarConfig.isSupportBcmCtrlSeatBelt()) {
            if (this.mIsMainProcess) {
                this.mDataSync.setRsbWarning(enable);
            }
            try {
                this.mCarManager.setRearSeatBeltWarning(parseCduSwitchCmd(enable));
            } catch (Exception e) {
                LogUtils.e(TAG, "setRsbWarning: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getDrvBeltStatus() {
        if (this.mCarConfig.isSupportBcmCtrlSeatBelt()) {
            try {
                try {
                    return getIntProperty(557849648);
                } catch (Exception e) {
                    LogUtils.e(TAG, "getDrvBeltStatus: " + e.getMessage(), false);
                    return 1;
                }
            } catch (Exception unused) {
                return this.mCarManager.getDriverBeltWarning();
            }
        }
        return -1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public boolean isEbwEnabled() {
        int i = 0;
        try {
            try {
                i = getIntProperty(IBcmController.ID_BCM_EBW);
            } catch (Exception e) {
                LogUtils.e(TAG, "isEbwEnabled: " + e.getMessage(), false);
            }
        } catch (Exception unused) {
            i = this.mCarManager.getEmergencyBrakeWarning();
        }
        return parseBcmStatus(i);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setEbwEnable(boolean enable) {
        try {
            this.mCarManager.setEmergencyBrakeWarning(parseCduSwitchCmd(enable));
        } catch (Exception e) {
            LogUtils.e(TAG, "setEbwEnable: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public boolean isDrvSeatOccupied() {
        int i;
        try {
            try {
                i = getIntProperty(557849607);
            } catch (Exception e) {
                LogUtils.e(TAG, "isDrvSeatOccupied: " + e.getMessage(), false);
                i = 0;
            }
        } catch (Exception unused) {
            i = this.mCarManager.getDriverOnSeat();
        }
        return i == 1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public boolean getBackDefrost() {
        int i = 0;
        try {
            try {
                i = getIntProperty(557849637);
            } catch (Exception e) {
                LogUtils.e(TAG, "getBackDefrost: " + e.getMessage(), false);
            }
        } catch (Exception unused) {
            i = this.mCarManager.getBcmBackDefrostMode();
        }
        return parseBcmStatus(i);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setBackDefrost(boolean enable) {
        try {
            this.mCarManager.setBcmBackDefrostMode(parseCduSwitchCmd(enable));
        } catch (Exception e) {
            LogUtils.e(TAG, "setBackDefrost: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getSeatHeatLevel() {
        try {
            try {
                return getIntProperty(557849638);
            } catch (Exception e) {
                LogUtils.e(TAG, "getSeatHeatLevel: " + e.getMessage(), false);
                return 0;
            }
        } catch (Exception unused) {
            return this.mCarManager.getBcmSeatHeatLevel();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setSeatHeatLevel(int level, boolean storeEnable) {
        try {
            int measureSeatHeatLevel = measureSeatHeatLevel(level);
            this.mCarManager.setBcmSeatHeatLevel(measureSeatHeatLevel);
            if (this.mIsMainProcess && storeEnable) {
                this.mDataSync.setDrvSeatHeatLevel(measureSeatHeatLevel);
                if (measureSeatHeatLevel > 0) {
                    this.mDataSync.setDrvSeatVentLevel(0);
                }
            }
            if (storeEnable || measureSeatHeatLevel == 0 || getSeatVentLevel() == 0) {
                return;
            }
            setSeatVentLevel(0, false);
        } catch (Exception e) {
            LogUtils.e(TAG, "setSeatHeatLevel: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void restoreSeatHeatLevel() {
        if (this.mIsMainProcess) {
            try {
                this.mCarManager.setBcmSeatHeatLevel(measureSeatHeatLevel(this.mDataSync.getDrvSeatHeatLevel()));
            } catch (Exception e) {
                LogUtils.e(TAG, "restoreSeatHeatLevel: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getSeatVentLevel() {
        try {
            try {
                return getIntProperty(356517139);
            } catch (Exception e) {
                LogUtils.e(TAG, "getSeatVentLevel: " + e.getMessage(), false);
                return 0;
            }
        } catch (Exception unused) {
            return this.mCarManager.getBcmSeatBlowLevel();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setSeatVentLevel(int level, boolean storeEnable) {
        try {
            int measureSeatVentLevel = measureSeatVentLevel(level);
            this.mCarManager.setBcmSeatBlowLevel(measureSeatVentLevel);
            if (this.mIsMainProcess && storeEnable) {
                this.mDataSync.setDrvSeatVentLevel(measureSeatVentLevel);
                if (measureSeatVentLevel > 0) {
                    this.mDataSync.setDrvSeatHeatLevel(0);
                }
            }
            if (storeEnable || measureSeatVentLevel == 0 || getSeatHeatLevel() == 0) {
                return;
            }
            setSeatHeatLevel(0, false);
        } catch (Exception e) {
            LogUtils.e(TAG, "setSeatVentLevel: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void restoreSeatVentLevel() {
        if (this.mIsMainProcess) {
            try {
                this.mCarManager.setBcmSeatBlowLevel(measureSeatVentLevel(this.mDataSync.getDrvSeatVentLevel()));
            } catch (Exception e) {
                LogUtils.e(TAG, "restoreSeatVentLevel: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getPsnSeatHeatLevel() {
        try {
            try {
                return getIntProperty(557849701);
            } catch (Exception e) {
                LogUtils.e(TAG, "getPsnSeatHeatLevel: " + e.getMessage(), false);
                return 0;
            }
        } catch (Exception unused) {
            return this.mCarManager.getBcmPsnSeatHeatLevel();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setPsnSeatHeatLevel(int level) {
        try {
            int measureSeatHeatLevel = measureSeatHeatLevel(level);
            this.mCarManager.setBcmPsnSeatHeatLevel(measureSeatHeatLevel);
            if (this.mIsMainProcess) {
                this.mDataSync.setPsnSeatHeatLevel(measureSeatHeatLevel);
            }
        } catch (Exception e) {
            LogUtils.e(TAG, "setPsnSeatHeatLevel: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void restorePsnSeatHeatLevel() {
        if (this.mIsMainProcess) {
            try {
                this.mCarManager.setBcmPsnSeatHeatLevel(measureSeatHeatLevel(this.mDataSync.getPsnSeatHeatLevel()));
            } catch (Exception e) {
                LogUtils.e(TAG, "restorePsnSeatHeatLevel: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getPsnSeatVentLevel() {
        try {
            try {
                return getIntProperty(356517140);
            } catch (Exception e) {
                LogUtils.e(TAG, "getPsnSeatVentLevel:" + e, false);
                return 0;
            }
        } catch (Exception unused) {
            return this.mCarManager.getPassengerSeatBlowLevel();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setPsnSeatVentLevel(int level) {
        try {
            this.mCarManager.setPassengerSeatBlowLevel(measureSeatVentLevel(level));
        } catch (Exception e) {
            LogUtils.e(TAG, "setPsnSeatVentLevel, e=" + e, false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getSteerHeatLevel() {
        try {
            try {
                return getIntProperty(557849854);
            } catch (Exception e) {
                LogUtils.e(TAG, "getSteerHeatLevel, e=" + e, false);
                return 0;
            }
        } catch (Exception unused) {
            return this.mCarManager.getSteeringWheelHeatingStatus();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void restoreSteerHeatLevel() {
        if (this.mIsMainProcess) {
            CarBaseConfig carBaseConfig = this.mCarConfig;
            if (CarBaseConfig.getInstance().isSupportSteerHeat()) {
                try {
                    this.mCarManager.setSteeringWheelHeatingStatus(this.mDataSync.getSteerHeatLevel());
                } catch (Exception e) {
                    LogUtils.e(TAG, "restoreSteerHeatLevel, e=" + e, false);
                }
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setSteerHeatLevel(int level, boolean storeEnable) {
        try {
            if (this.mIsMainProcess && storeEnable) {
                this.mDataSync.setSteerHeatLevel(level);
            }
            this.mCarManager.setSteeringWheelHeatingStatus(level);
        } catch (Exception e) {
            LogUtils.e(TAG, "setSteerHeatLevel, e=" + e);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getRRSeatHeatLevel() {
        if (this.mCarConfig.isSupportRearSeatHeat()) {
            try {
                try {
                    return getIntProperty(557849769);
                } catch (Exception unused) {
                    return this.mCarManager.getRearRightSeatHeatState();
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "getRRSeatHeatLevel: " + e.getMessage(), false);
                return 0;
            }
        }
        return -1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setRRSeatHeatLevel(int level) {
        if (this.mCarConfig.isSupportRearSeatHeat()) {
            try {
                int measureSeatHeatLevel = measureSeatHeatLevel(level);
                this.mCarManager.setRearRightSeatHeatSw(measureSeatHeatLevel);
                if (this.mIsMainProcess) {
                    this.mDataSync.setRRSeatHeatLevel(measureSeatHeatLevel);
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "setRRSeatHeatLevel: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void restoreRRSeatHeatLevel() {
        if (this.mCarConfig.isSupportRearSeatHeat() && this.mIsMainProcess) {
            try {
                this.mCarManager.setRearRightSeatHeatSw(measureSeatHeatLevel(this.mDataSync.getRRSeatHeatLevel()));
            } catch (Exception e) {
                LogUtils.e(TAG, "restoreRRSeatHeatLevel: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getRLSeatHeatLevel() {
        if (this.mCarConfig.isSupportRearSeatHeat()) {
            try {
                try {
                    return getIntProperty(557849770);
                } catch (Exception unused) {
                    return this.mCarManager.getRearLeftSeatHeatState();
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "getRLSeatHeatLevel: " + e.getMessage(), false);
                return 0;
            }
        }
        return -1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setRLSeatHeatLevel(int level) {
        if (this.mCarConfig.isSupportRearSeatHeat()) {
            try {
                int measureSeatHeatLevel = measureSeatHeatLevel(level);
                this.mCarManager.setRearLeftSeatHeatSw(measureSeatHeatLevel);
                if (this.mIsMainProcess) {
                    this.mDataSync.setRLSeatHeatLevel(measureSeatHeatLevel);
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "setRLSeatHeatLevel: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void restoreRLSeatHeatLevel() {
        if (this.mCarConfig.isSupportRearSeatHeat() && this.mIsMainProcess) {
            try {
                this.mCarManager.setRearLeftSeatHeatSw(measureSeatHeatLevel(this.mDataSync.getRLSeatHeatLevel()));
            } catch (Exception e) {
                LogUtils.e(TAG, "restoreRLSeatHeatLevel: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public boolean isMirrorHeatEnabled() {
        int i = 0;
        try {
            try {
                i = getIntProperty(557849665);
            } catch (Exception e) {
                LogUtils.e(TAG, "isMirrorHeatEnabled: " + e.getMessage(), false);
            }
        } catch (Exception unused) {
            i = this.mCarManager.getBcmBackMirrorHeatMode();
        }
        return parseBcmStatus(i);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setMirrorHeat(boolean enable) {
        try {
            this.mCarManager.setBcmBackMirrorHeatMode(parseCduSwitchCmd(enable));
        } catch (Exception e) {
            LogUtils.e(TAG, "setMirrorHeat: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setChargePortUnlock(int port, int state) {
        if (this.mCarConfig.isSupportCtrlChargePort()) {
            try {
                this.mCarManager.setChargePortUnlock(port, state);
            } catch (Exception e) {
                LogUtils.e(TAG, "setChargePortUnlock: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getChargePortUnlock(int port) {
        if (this.mCarConfig.isSupportCtrlChargePort()) {
            try {
                try {
                    return getIntProperty(port == 1 ? 557849642 : 557849643);
                } catch (Exception e) {
                    LogUtils.e(TAG, "getChargePortUnlock: " + e.getMessage(), false);
                }
            } catch (Exception unused) {
                return this.mCarManager.getChargePortStatus(port);
            }
        }
        return -1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setHighSpdCloseWin(final boolean enable) {
        if (this.mIsMainProcess) {
            this.mDataSync.setHighSpdCloseWin(enable);
            ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$BcmController$SYv3e4UojQNS5TsCHmPGXIc2S4g
                @Override // java.lang.Runnable
                public final void run() {
                    BcmController.this.lambda$setHighSpdCloseWin$8$BcmController(enable);
                }
            });
            return;
        }
        this.mIsHighSpdWinClose = Boolean.valueOf(enable);
        CarControl.PrivateControl.putBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.WIN_HIGH_SPD, enable);
    }

    public /* synthetic */ void lambda$setHighSpdCloseWin$8$BcmController(final boolean enable) {
        if (enable) {
            FunctionModel.getInstance().setHighSpdCloseWinTs(0L);
        }
        handleHighSpdCloseWinChanged(enable);
        CarControl.PrivateControl.putBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.WIN_HIGH_SPD_NOTIFY, true);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public boolean isHighSpdCloseWinEnabled() {
        if (this.mIsMainProcess) {
            return this.mDataSync.isWinHighSpdEnabled();
        }
        LogUtils.i(TAG, "isHighSpdCloseWinEnabled: " + this.mIsHighSpdWinClose, false);
        return ((Boolean) getContentProviderValue(CarControl.PrivateControl.WIN_HIGH_SPD, this.mIsHighSpdWinClose, false)).booleanValue();
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0024, code lost:
        if (r0[3] != 1) goto L44;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x0056, code lost:
        if (r0[r6] == 1) goto L17;
     */
    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean isWindowInitFailed(int r6) {
        /*
            r5 = this;
            com.xiaopeng.carcontrol.config.CarBaseConfig r0 = r5.mCarConfig
            boolean r0 = r0.isSupportWindowInitFailed()
            r1 = 0
            if (r0 == 0) goto L76
            C extends android.car.hardware.CarEcuManager r0 = r5.mCarManager     // Catch: java.lang.Throwable -> L59
            android.car.hardware.bcm.CarBcmManager r0 = (android.car.hardware.bcm.CarBcmManager) r0     // Catch: java.lang.Throwable -> L59
            int[] r0 = r0.getWindowsInitSignalLostRequestStatus()     // Catch: java.lang.Throwable -> L59
            if (r0 == 0) goto L76
            int r2 = r0.length     // Catch: java.lang.Throwable -> L59
            r3 = 4
            if (r2 < r3) goto L76
            r2 = 3
            r3 = 2
            r4 = 1
            switch(r6) {
                case 0: goto L54;
                case 1: goto L54;
                case 2: goto L54;
                case 3: goto L54;
                case 4: goto L43;
                case 5: goto L3a;
                case 6: goto L31;
                case 7: goto L28;
                case 8: goto L1e;
                default: goto L1d;
            }     // Catch: java.lang.Throwable -> L59
        L1d:
            goto L76
        L1e:
            r6 = r0[r4]     // Catch: java.lang.Throwable -> L59
            if (r6 == r4) goto L26
            r6 = r0[r2]     // Catch: java.lang.Throwable -> L59
            if (r6 != r4) goto L76
        L26:
            r1 = r4
            goto L76
        L28:
            r6 = r0[r1]     // Catch: java.lang.Throwable -> L59
            if (r6 == r4) goto L26
            r6 = r0[r3]     // Catch: java.lang.Throwable -> L59
            if (r6 != r4) goto L76
            goto L26
        L31:
            r6 = r0[r3]     // Catch: java.lang.Throwable -> L59
            if (r6 == r4) goto L26
            r6 = r0[r2]     // Catch: java.lang.Throwable -> L59
            if (r6 != r4) goto L76
            goto L26
        L3a:
            r6 = r0[r1]     // Catch: java.lang.Throwable -> L59
            if (r6 == r4) goto L26
            r6 = r0[r4]     // Catch: java.lang.Throwable -> L59
            if (r6 != r4) goto L76
            goto L26
        L43:
            r6 = r0[r1]     // Catch: java.lang.Throwable -> L59
            if (r6 == r4) goto L26
            r6 = r0[r4]     // Catch: java.lang.Throwable -> L59
            if (r6 == r4) goto L26
            r6 = r0[r3]     // Catch: java.lang.Throwable -> L59
            if (r6 == r4) goto L26
            r6 = r0[r2]     // Catch: java.lang.Throwable -> L59
            if (r6 != r4) goto L76
            goto L26
        L54:
            r6 = r0[r6]     // Catch: java.lang.Throwable -> L59
            if (r6 != r4) goto L76
            goto L26
        L59:
            r6 = move-exception
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r2 = "isWindowInitFailed: "
            java.lang.StringBuilder r0 = r0.append(r2)
            java.lang.String r6 = r6.getMessage()
            java.lang.StringBuilder r6 = r0.append(r6)
            java.lang.String r6 = r6.toString()
            java.lang.String r0 = "BcmController"
            com.xiaopeng.carcontrol.util.LogUtils.e(r0, r6, r1)
        L76:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.carcontrol.carmanager.impl.BcmController.isWindowInitFailed(int):boolean");
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setAutoWindowLockSw(boolean enable) {
        if (this.mCarConfig.isSupportAutoWindowLock()) {
            try {
                this.mCarManager.setAutoWindowLockSw(enable ? 1 : 0);
                if (this.mIsMainProcess) {
                    this.mDataSync.setLockCloseWin(enable);
                } else {
                    CarControl.PrivateControl.putBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.LOCK_CLOSE_WIN, enable);
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "setAutoWindowLockSw: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public boolean getAutoWindowLockSw() {
        if (this.mCarConfig.isSupportAutoWindowLock()) {
            boolean z = true;
            try {
                try {
                    if (getIntProperty(557849715) != 1) {
                        return false;
                    }
                } catch (Exception unused) {
                    if (this.mCarManager.getAutoWindowLockSw() != 1) {
                        return false;
                    }
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "getAutoWindowLockSw: " + e.getMessage(), false);
                if (this.mDataSync != null) {
                    z = this.mDataSync.getLockCloseWin();
                }
            }
            return z;
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getCwcChargeSt() {
        if (this.mCarConfig.isSupportCwc()) {
            try {
                try {
                    return getIntProperty(557849713);
                } catch (Exception unused) {
                    return this.mCarManager.getCwcChargeSt();
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "getCwcChargeSt: " + e.getMessage(), false);
                return 0;
            }
        }
        return -1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getCwcChargeErrorSt() {
        if (this.mCarConfig.isSupportCwc()) {
            try {
                try {
                    return getIntProperty(557849714);
                } catch (Exception unused) {
                    return this.mCarManager.getCwcChargeErrorSt();
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "getCwcChargeErrorSt: " + e.getMessage(), false);
                return 0;
            }
        }
        return -1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setStealthMode(boolean enable) {
        try {
            this.mCarManager.setStealthMode(enable ? 1 : 0);
        } catch (Exception e) {
            LogUtils.e(TAG, "setStealthMode: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public boolean getStealthMode() {
        try {
            try {
                return getIntProperty(557849719) == 1;
            } catch (Exception e) {
                LogUtils.e(TAG, "getStealthMode: " + e.getMessage(), false);
                return false;
            }
        } catch (Exception unused) {
            this.mCarManager.getStealthMode();
            return false;
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void saveMirrorPos(String mirrorPos) {
        if ((this.mCarConfig.isSupportMirrorMemory() || this.mCarConfig.isSupportMirrorDown()) && this.mIsMainProcess) {
            this.mDataSync.setMirrorData(mirrorPos);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public String getMirrorSavedPos() {
        if (this.mCarConfig.isSupportMirrorMemory() && this.mIsMainProcess) {
            return this.mDataSync.getMirrorData();
        }
        return null;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setStopNfcCardSw(boolean enable) {
        if (this.mCarConfig.isSupportNfc()) {
            try {
                this.mCarManager.setStopNfcCardSw(parseCduSwitchCmd(enable));
                if (this.mIsMainProcess) {
                    this.mDataSync.setNfcKeyEnable(enable);
                } else {
                    CarControl.PrivateControl.putBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.NFC_KEY_ENABLE, enable);
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "setStopNfcCardSw: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public boolean getStopNfcCardSw() {
        int i;
        if (this.mCarConfig.isSupportNfc()) {
            try {
                try {
                    i = getIntProperty(557849655);
                } catch (Exception unused) {
                    i = this.mCarManager.getStopNfcCardSw();
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "getStopNfcCardSw: " + e.getMessage(), false);
                i = 1;
            }
            return parseBcmStatus(i);
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setWiperSensitivity(int level, boolean needSave) {
        setWiperSensitivity(level, needSave, false);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setWiperSensitivity(int level, boolean needSave, boolean inactive) {
        if (this.mCarConfig.isSupportWiperSenCfg()) {
            LogUtils.i(TAG, "setWiperSensitivity: " + level + ", needSave: " + needSave + ", inactive: " + inactive, false);
            if (needSave) {
                try {
                    if (this.mIsMainProcess) {
                        this.mDataSync.setWiperSensitivity(level);
                    } else {
                        CarControl.PrivateControl.putInt(App.getInstance().getContentResolver(), CarControl.PrivateControl.WIPER_SENSITIVE_LEVEL, level);
                    }
                } catch (Exception e) {
                    LogUtils.e(TAG, "setWiperSensitivity: " + e.getMessage(), false);
                    return;
                }
            }
            if (inactive) {
                this.mCarManager.setWiperRainDetectSensitivityAndInactive(convertWiperSensitiveLevel(level));
            } else {
                this.mCarManager.setWiperRainDetectSensitivity(convertWiperSensitiveLevel(level));
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getWiperSensitivity() {
        int wiperSensitivity;
        if (this.mCarConfig.isSupportWiperSenCfg()) {
            try {
                try {
                    wiperSensitivity = getIntProperty(557849754);
                } catch (Exception e) {
                    LogUtils.e(TAG, "getWiperSensitivity: " + e.getMessage(), false);
                    wiperSensitivity = this.mDataSync == null ? GlobalConstant.DEFAULT.WIPER_Sensitivity : this.mDataSync.getWiperSensitivity();
                }
            } catch (Exception unused) {
                wiperSensitivity = this.mCarManager.getWiperRainDetectSensitivity();
            }
            return convertWiperSensitiveLevel(wiperSensitivity);
        }
        return -1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public boolean getWindowLockState() {
        try {
            try {
                if (getIntProperty(557848731) != 1) {
                    return false;
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "getWindowLockState: " + e.getMessage(), false);
                return false;
            }
        } catch (Exception unused) {
            if (this.mCarManager.getWindowLockState() != 1) {
                return false;
            }
        }
        return true;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setWindowLockState(boolean enable) {
        try {
            this.mCarManager.setWindowLockState(enable ? 1 : 0);
            if (this.mIsMainProcess) {
                this.mDataSync.setWindowLockState(enable);
            }
        } catch (Exception e) {
            LogUtils.e(TAG, "setWindowLockState: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getBcmReadyEnableState() {
        if (this.mCarConfig.isSupportDigitalKeyTip()) {
            try {
                return this.mCarManager.getReadyEnableState();
            } catch (Error | Exception e) {
                LogUtils.e(TAG, "getBcmReadyEnableState: " + e.getMessage(), false);
                return 0;
            }
        }
        return -1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getKeyAuthState() {
        if (this.mCarConfig.isSupportDigitalKeyTip()) {
            try {
                return this.mCarManager.getKeyAuthState();
            } catch (Error | Exception e) {
                LogUtils.e(TAG, "getKeyAuthState: " + e.getMessage(), false);
                return 0;
            }
        }
        return -1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getUnlockReqSrc() {
        if (this.mCarConfig.isSupportDigitalKeyTip()) {
            try {
                return this.mCarManager.getDoorUnlockRequestSource();
            } catch (Exception e) {
                LogUtils.e(TAG, "getUnlockReqSrc: " + e.getMessage(), false);
                return 0;
            }
        }
        return -1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void controlLeftSdc(int cmd) {
        if (this.mCarConfig.isSupportSdc()) {
            try {
                this.mCarManager.setLeftSdcAutoControl(cmd);
            } catch (Exception e) {
                LogUtils.e(TAG, "controlLeftSdc: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void controlRightSdc(int cmd) {
        if (this.mCarConfig.isSupportSdc()) {
            try {
                this.mCarManager.setRightSdcAutoControl(cmd);
            } catch (Exception e) {
                LogUtils.e(TAG, "controlRightSdc: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getLeftSdcDoorPos() {
        if (this.mCarConfig.isSupportSdc()) {
            try {
                try {
                    return getIntProperty(557849788);
                } catch (Error | Exception e) {
                    LogUtils.e(TAG, "getLeftSdcDoorPos: " + e.getMessage(), false);
                    return 0;
                }
            } catch (Exception unused) {
                return this.mCarManager.getLeftSdcDoorPosition();
            }
        }
        return 0;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getRightSdcDoorPos() {
        if (this.mCarConfig.isSupportSdc()) {
            try {
                try {
                    return getIntProperty(557849789);
                } catch (Error | Exception e) {
                    LogUtils.e(TAG, "getRightSdcDoorPos: " + e.getMessage(), false);
                    return 0;
                }
            } catch (Exception unused) {
                return this.mCarManager.getRightSdcDoorPosition();
            }
        }
        return 0;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getLeftSdcSysRunningState() {
        int i;
        if (this.mCarConfig.isSupportSdc()) {
            try {
                i = this.mCarManager.getLeftSdcSystemRunningState();
            } catch (Exception e) {
                LogUtils.e(TAG, "getLeftSdcSysRunningState: " + e.getMessage(), false);
                i = 0;
            }
            LogUtils.i(TAG, "getLeftSdcSysRunningState: " + i, false);
            return i;
        }
        return 0;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getRightSdcSysRunningState() {
        int i;
        if (this.mCarConfig.isSupportSdc()) {
            try {
                i = this.mCarManager.getRightSdcSystemRunningState();
            } catch (Exception e) {
                LogUtils.e(TAG, "getRightSdcSysRunningState: " + e.getMessage(), false);
                i = 0;
            }
            LogUtils.i(TAG, "getRightSdcSysRunningState: " + i, false);
            return i;
        }
        return 0;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setSdcKeyOpenCtrlCfg(int cfg) {
        if (this.mCarConfig.isSupportSdc()) {
            try {
                this.mCarManager.setSdcKeyOpenCtrlCfg(cfg);
                if (this.mIsMainProcess) {
                    this.mDataSync.setSdcKeyCfg(cfg);
                } else {
                    CarControl.PrivateControl.putInt(App.getInstance().getContentResolver(), CarControl.PrivateControl.SDC_KEY_CFG, cfg);
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "setSdcKeyOpenCtrlCfg: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getSdcKeyOpenCtrlCfg() {
        if (this.mCarConfig.isSupportSdc()) {
            try {
                return getIntProperty(557849773);
            } catch (Exception e) {
                try {
                    return this.mCarManager.getSdcKeyOpenCtrlCfg();
                } catch (Exception unused) {
                    LogUtils.e(TAG, "Call getSdcKeyOpenCtrlCfg exception: " + e.getMessage(), false);
                    return 1;
                }
            }
        }
        return 1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setSdcKeyCloseCtrlCfg(int cfg) {
        if (this.mCarConfig.isSupportSdc()) {
            try {
                this.mCarManager.setSdcKeyCloseCtrlCfg(cfg);
            } catch (Exception e) {
                LogUtils.e(TAG, "setSdcKeyCloseCtrlCfg: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setSdcWindowsAutoDownSwitch(boolean open) {
        if (this.mCarConfig.isSupportSdc()) {
            try {
                int i = 2;
                this.mCarManager.setLeftSdcWindowsAutoDownSwitch(open ? 2 : 1);
                CarBcmManager carBcmManager = this.mCarManager;
                if (!open) {
                    i = 1;
                }
                carBcmManager.setRightSdcWindowsAutoDownSwitch(i);
                if (this.mIsMainProcess) {
                    this.mDataSync.setSdcWinAutoDown(open);
                } else {
                    CarControl.PrivateControl.putBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.SDC_AUTO_WIN_DOWN, open);
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "setSdcWindowsAutoDownSwitch: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public boolean getSdcWindowsAutoDownSwitch() {
        int i;
        int i2;
        if (this.mCarConfig.isSupportSdc()) {
            try {
                try {
                    i = getIntProperty(557849784);
                    i2 = getIntProperty(557849785);
                } catch (Exception unused) {
                    i = this.mCarManager.getLeftSdcWindowsAutoDownSwitchState();
                    i2 = this.mCarManager.getRightSdcWindowsAutoDownSwitchState();
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "getSdcWindowsAutoDownSwitch: " + e.getMessage(), false);
                i = 1;
                i2 = 1;
            }
            return i == 2 || i2 == 2;
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setSdcMaxAutoDoorOpeningAngle(int angle) {
        if (this.mCarConfig.isSupportSdc()) {
            if (this.mIsMainProcess) {
                this.mDataSync.setSdcMaxAutoDoorOpeningAngle(angle);
            } else {
                CarControl.PrivateControl.putInt(App.getInstance().getContentResolver(), CarControl.PrivateControl.SDC_MAX_AUTO_DOOR_OPENING_ANGLE, angle);
            }
            try {
                this.mCarManager.setSdcMaxAutoDoorOpeningAngle(angle);
            } catch (Exception e) {
                LogUtils.e(TAG, "setSdcMaxAutoDoorOpeningAngle: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getSdcMaxAutoDoorOpeningAngle() {
        int i;
        if (this.mCarConfig.isSupportSdc()) {
            try {
                try {
                    i = getIntProperty(557849775);
                } catch (Exception unused) {
                    i = this.mCarManager.getSdcMaxAutoDoorOpeningAngle();
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "getSdcMaxAutoDoorOpeningAngle: " + e.getMessage(), false);
                i = 100;
            }
            if (i < 40) {
                return 40;
            }
            if (i <= 100) {
                return i;
            }
        }
        return 100;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setSdcBrakeCloseDoorCfg(int cfg) {
        if (this.mCarConfig.isSupportSdc()) {
            if (this.mIsMainProcess) {
                this.mDataSync.setSdcBrakeCloseCfg(cfg);
            } else {
                CarControl.PrivateControl.putInt(App.getInstance().getContentResolver(), CarControl.PrivateControl.SDC_BRAKE_CLOSE_DOOR_CFG, cfg);
            }
            try {
                this.mCarManager.setsdcBrakeCloseDoorCfg(cfg);
                LogUtils.e(TAG, "setsdcBrakeCloseDoorCfg : " + cfg, false);
            } catch (Error | Exception e) {
                LogUtils.e(TAG, "setsdcBrakeCloseDoorCfg failed: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getSdcBrakeCloseDoorCfg() {
        if (this.mCarConfig.isSupportSdc()) {
            try {
                try {
                    return getIntProperty(557849939);
                } catch (Exception unused) {
                    return this.mCarManager.getsdcBrakeCloseDoorCfg();
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "getsdcBrakeCloseDoorCfg failed: " + e.getMessage(), false);
                return 0;
            }
        }
        return 0;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getLeftSlideDoorMode() {
        if (CarBaseConfig.getInstance().isSupportSlideDoor()) {
            try {
                try {
                    return getIntProperty(557849979);
                } catch (Exception unused) {
                    return this.mCarManager.getLeftSlideDoorMoode();
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "getLeftSlideDoorMode: " + e.getMessage(), false);
                return 0;
            }
        }
        return 0;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setLeftSlideDoorMode(int mode) {
        if (CarBaseConfig.getInstance().isSupportSlideDoor()) {
            try {
                this.mCarManager.setLeftSlideDoorMode(mode);
                if (this.mIsMainProcess) {
                    this.mDataSync.setLeftSlideDoorMode(mode);
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "setLeftSlideDoorMode: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getRightSlideDoorMode() {
        if (CarBaseConfig.getInstance().isSupportSlideDoor()) {
            try {
                try {
                    return getIntProperty(557849982);
                } catch (Exception unused) {
                    return this.mCarManager.getRightSlideDoorMoode();
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "getLeftSlideDoorMode: " + e.getMessage(), false);
                return 0;
            }
        }
        return 0;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setRightSlideDoorMode(int mode) {
        if (CarBaseConfig.getInstance().isSupportSlideDoor()) {
            try {
                this.mCarManager.setRightSlideDoorMode(mode);
                if (this.mIsMainProcess) {
                    this.mDataSync.setRightSlideDoorMode(mode);
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "setRightSlideDoorMode: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getLeftSlideDoorState() {
        if (CarBaseConfig.getInstance().isSupportSlideDoor()) {
            try {
                try {
                    return getIntProperty(557849983);
                } catch (Exception unused) {
                    return this.mCarManager.getLeftSlideDoorStatus();
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "getLeftSlideDoorState: " + e.getMessage(), false);
                return 0;
            }
        }
        return 0;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getRightSlideDoorState() {
        if (CarBaseConfig.getInstance().isSupportSlideDoor()) {
            try {
                try {
                    return getIntProperty(557849980);
                } catch (Exception unused) {
                    return this.mCarManager.getLeftSlideDoorStatus();
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "getRightSlideDoorState: " + e.getMessage(), false);
                return 0;
            }
        }
        return 0;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void controlLeftSlideDoor(int cmd) {
        if (CarBaseConfig.getInstance().isSupportSlideDoor()) {
            try {
                this.mCarManager.setLeftSlideDoorCtrl(cmd);
            } catch (Exception e) {
                LogUtils.e(TAG, "controlLeftSlideDoor: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void controlRightSlideDoor(int cmd) {
        if (CarBaseConfig.getInstance().isSupportSlideDoor()) {
            try {
                this.mCarManager.setRightSlideDoorCtrl(cmd);
            } catch (Exception e) {
                LogUtils.e(TAG, "controlRightSlideDoor: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getLeftSlideDoorLockState() {
        if (CarBaseConfig.getInstance().isSupportSlideDoor()) {
            try {
                try {
                    return getIntProperty(557849981);
                } catch (Exception unused) {
                    return this.mCarManager.getLeftSlideDoorLockSt();
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "getLeftSlideDoorLockState: " + e.getMessage(), false);
                return 0;
            }
        }
        return 0;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getRightSlideDoorLockState() {
        if (CarBaseConfig.getInstance().isSupportSlideDoor()) {
            try {
                try {
                    return getIntProperty(557849984);
                } catch (Exception unused) {
                    return this.mCarManager.getRightSlideDoorLockSt();
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "getRightSlideDoorLockState: " + e.getMessage(), false);
                return 0;
            }
        }
        return 0;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setCwcSwEnable(boolean enable, boolean storeEnable) {
        if (this.mCarConfig.isSupportCwc()) {
            try {
                this.mCarManager.setCwcSwitch(parseCduSwitchCmd(enable));
                if (storeEnable) {
                    if (this.mIsMainProcess) {
                        this.mDataSync.setCwcSw(enable);
                    } else {
                        CarControl.PrivateControl.putBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.SET_CWC_SW, enable);
                    }
                }
            } catch (Error | Exception e) {
                LogUtils.e(TAG, "setCwcSwEnable: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setCwcSwEnable(boolean enable) {
        setCwcSwEnable(enable, true);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public boolean isCwcSwEnable() {
        if (this.mCarConfig.isSupportCwc()) {
            int i = 1;
            try {
                try {
                    i = getIntProperty(557849797);
                } catch (Error | Exception e) {
                    LogUtils.e(TAG, "isCwcSwEnable: " + e.getMessage(), false);
                }
            } catch (Exception unused) {
                i = this.mCarManager.getCwcSwitchState();
            }
            return parseBcmStatus(i);
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setCwcFRSwEnable(boolean enable) {
        if (this.mCarConfig.isSupportCwcFR()) {
            try {
                this.mCarManager.setFRCwcSwitch(parseCduSwitchCmd(enable));
                if (this.mIsMainProcess) {
                    this.mDataSync.setCwcFRSw(enable);
                }
            } catch (Error | Exception e) {
                LogUtils.e(TAG, "setCwcFRSwEnable: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public boolean isCwcFRSwEnable() {
        if (this.mCarConfig.isSupportCwcFR()) {
            int i = 1;
            try {
                try {
                    i = getIntProperty(557849988);
                } catch (Error | Exception e) {
                    LogUtils.e(TAG, "isCwcFRSwEnable: " + e.getMessage(), false);
                }
            } catch (Exception unused) {
                i = this.mCarManager.getFRCwcSwitchState();
            }
            return parseBcmStatus(i);
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setCwcRLSwEnable(boolean enable) {
        if (this.mCarConfig.isSupportCwcRL()) {
            try {
                this.mCarManager.setRLCwcSwitch(parseCduSwitchCmd(enable));
                if (this.mIsMainProcess) {
                    this.mDataSync.setCwcRLSw(enable);
                }
            } catch (Error | Exception e) {
                LogUtils.e(TAG, "setCwcRLSwEnable: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public boolean isCwcRLSwEnable() {
        if (this.mCarConfig.isSupportCwcRL()) {
            int i = 1;
            try {
                try {
                    i = getIntProperty(557850049);
                } catch (Error | Exception e) {
                    LogUtils.e(TAG, "isCwcRLSwEnable: " + e.getMessage(), false);
                }
            } catch (Exception unused) {
                i = this.mCarManager.getRLCwcSwitchState();
            }
            return parseBcmStatus(i);
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setCwcRRSwEnable(boolean enable) {
        if (this.mCarConfig.isSupportCwcRR()) {
            try {
                this.mCarManager.setRRCwcSwitch(parseCduSwitchCmd(enable));
                if (this.mIsMainProcess) {
                    this.mDataSync.setCwcRRSw(enable);
                }
            } catch (Error | Exception e) {
                LogUtils.e(TAG, "setCwcRLSwEnable: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public boolean isCwcRRSwEnable() {
        if (this.mCarConfig.isSupportCwcRR()) {
            int i = 1;
            try {
                try {
                    i = getIntProperty(557850052);
                } catch (Error | Exception e) {
                    LogUtils.e(TAG, "isCwcRRSwEnable: " + e.getMessage(), false);
                }
            } catch (Exception unused) {
                i = this.mCarManager.getRRCwcSwitchState();
            }
            return parseBcmStatus(i);
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setAirSuspensionHeight(int height) {
        setAirSuspensionHeight(height, false);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setAirSuspensionHeight(int height, boolean needSave) {
        if (this.mCarConfig.isSupportAirSuspension()) {
            try {
                this.mCarManager.setTargetAsHeightLvlConfigValue(height);
                if (needSave && this.mIsMainProcess) {
                    this.mDataSync.setAsHeight(height);
                }
                LogUtils.i(TAG, "setAirSuspensionHeight: " + height, false);
            } catch (Exception e) {
                LogUtils.e(TAG, "setHeightLvlConfigValue: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getAirSuspensionHeight() {
        int i;
        if (this.mCarConfig.isSupportAirSuspension()) {
            try {
                try {
                    i = getIntProperty(557858842);
                } catch (Exception unused) {
                    i = this.mCarManager.getTargetAsHeightLvlConfigValue();
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "getAirSuspensionHeight: " + e.getMessage(), false);
                i = 3;
            }
        } else {
            i = 0;
        }
        LogUtils.i(TAG, "getAirSuspensionHeight: " + i, false);
        return i;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getAirSuspensionHeightSp() {
        if (this.mCarConfig.isSupportAirSuspension() && this.mIsMainProcess) {
            return this.mDataSync.getAsHeight();
        }
        return 0;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setAsLocationSw(boolean enable) {
        setAsLocationSw(enable, true);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setAsLocationSw(boolean enable, boolean storeEnable) {
        LogUtils.d(TAG, "setAsLocationSw, enable = " + enable + "storeEnable = " + storeEnable, false);
        if (this.mCarConfig.isSupportXpu() && this.mCarConfig.isSupportAirSuspension()) {
            Settings.System.putInt(App.getInstance().getContentResolver(), IBcmController.AS_LOCATION_SW, enable ? 1 : 0);
            if (storeEnable && this.mIsMainProcess) {
                this.mDataSync.setAsLocationStatus(enable);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public boolean getAsLocationSw() {
        int i = (this.mCarConfig.isSupportXpu() && this.mCarConfig.isSupportAirSuspension()) ? Settings.System.getInt(App.getInstance().getContentResolver(), IBcmController.AS_LOCATION_SW, 1) : 0;
        LogUtils.d(TAG, "getAsLocationSw, value = " + i, false);
        return i == 1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setAsLocationValue(int value) {
        LogUtils.d(TAG, "setAsLocationValue, value = " + value, false);
        if (this.mCarConfig.isSupportXpu() && this.mCarConfig.isSupportAirSuspension()) {
            Settings.System.putInt(App.getInstance().getContentResolver(), IBcmController.AS_LOCATION_SW, value);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setAsLocationControlSw(boolean on) {
        if (this.mCarConfig.isSupportAirSuspension() && this.mIsMainProcess) {
            this.mDataSync.setAsLocationControlSw(on);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public boolean getAsLocationControlSw() {
        if (this.mCarConfig.isSupportAirSuspension() && this.mIsMainProcess) {
            return this.mDataSync.getAsLocationControlSw();
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setAirSuspensionSoft(int soft) {
        if (this.mCarConfig.isSupportAirSuspension()) {
            try {
                this.mCarManager.setSoftLvlConfigValue(soft);
                LogUtils.i(TAG, "setSoftLvlConfigValue: " + soft, false);
            } catch (Exception e) {
                LogUtils.e(TAG, "isSupportAirSuspension: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getAirSuspensionSoft() {
        int i;
        if (this.mCarConfig.isSupportAirSuspension()) {
            try {
                try {
                    i = getIntProperty(557858833);
                } catch (Exception unused) {
                    i = this.mCarManager.getSoftLvlConfigValue();
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "getSoftLvlConfigValue: " + e.getMessage(), false);
                i = 2;
            }
        } else {
            i = 0;
        }
        LogUtils.i(TAG, "getAirSuspensionSoft: " + i, false);
        return i;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setAsWelcomeMode(boolean enable) {
        if (this.mCarConfig.isSupportAsWelcomeMode()) {
            try {
                this.mCarManager.setSuspenWelcomeSwitch(enable ? 1 : 0);
                if (this.mIsMainProcess) {
                    this.mDataSync.setAsWelcomeMode(enable);
                } else {
                    CarControl.PrivateControl.putBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.AS_WELCOME_MODE, enable);
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "setAsWelcomeMode: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public boolean getAsWelcomeMode() {
        if (this.mCarConfig.isSupportAirSuspension()) {
            try {
                try {
                    if (getIntProperty(557849829) != 1) {
                        return false;
                    }
                } catch (Exception unused) {
                    if (this.mCarManager.getSuspenWelcomeSwitchState() != 1) {
                        return false;
                    }
                }
                return true;
            } catch (Exception e) {
                LogUtils.e(TAG, "getSuspenWelcomeSwitchState failed: " + e.getMessage(), false);
                return false;
            }
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setEasyLoadingSwitch(boolean enable) {
        if (this.mCarConfig.isSupportAirSuspension()) {
            try {
                this.mCarManager.setEasyLoadingSwitch(parseCduSwitchCmd(enable));
                if (this.mIsMainProcess) {
                    this.mDataSync.setEasyLoadingStatus(enable);
                } else {
                    CarControl.PrivateControl.putBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.EASY_LOADING_MODE, enable);
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "setEasyLoadingSwitch: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public boolean getEasyLoadingSwitch() {
        if (this.mCarConfig.isSupportAirSuspension()) {
            try {
                try {
                    return parseBcmStatus(getIntProperty(557858828));
                } catch (Exception e) {
                    LogUtils.e(TAG, "getEasyLoadingSwitch: " + e.getMessage(), false);
                    return false;
                }
            } catch (Exception unused) {
                return parseBcmStatus(this.mCarManager.getEasyLoadingState());
            }
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setAirSuspensionRepairMode(boolean enable) {
        if (this.mCarConfig.isSupportAirSuspension()) {
            try {
                this.mCarManager.setEngineeringModeStatus(enable ? 1 : 0);
            } catch (Exception e) {
                LogUtils.e(TAG, "setAirSuspensionRepairMode: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public boolean getAirSuspensionRepairMode() {
        if (this.mCarConfig.isSupportAirSuspension()) {
            try {
                try {
                    if (1 != getIntProperty(557849895)) {
                        return false;
                    }
                } catch (Exception unused) {
                    if (this.mCarManager.getEngineeringModeStatus() != 1) {
                        return false;
                    }
                }
                return true;
            } catch (Exception e) {
                LogUtils.e(TAG, "getAirSuspensionRepairMode: " + e.getMessage(), false);
                return false;
            }
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setEngineeringModeStatus(int status) {
        if (this.mCarConfig.isSupportAirSuspension()) {
            try {
                this.mCarManager.setEngineeringModeStatus(status);
            } catch (Exception e) {
                LogUtils.e(TAG, "setEngineeringModeStatus: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getEngineeringModeStatus() {
        if (this.mCarConfig.isSupportAirSuspension()) {
            try {
                try {
                    return getIntProperty(557849895);
                } catch (Exception unused) {
                    return this.mCarManager.getEngineeringModeStatus();
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "getAirSuspensionTransportMode: " + e.getMessage(), false);
                return 0;
            }
        }
        return 0;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getAsRequestEspState() {
        if (this.mCarConfig.isSupportAirSuspension()) {
            try {
                return this.mCarManager.getAsEspPataRequestStatus();
            } catch (Exception e) {
                LogUtils.w(TAG, "getAsEspPataRequestStatus failed: " + e.getMessage(), false);
                return 0;
            }
        }
        return 0;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setAsCampingModeSwitch(boolean enable) {
        if (this.mCarConfig.isSupportAirSuspension()) {
            try {
                this.mCarManager.setAsCampingModeSwitchStatus(parseCduSwitchCmd(enable));
            } catch (Exception e) {
                LogUtils.e(TAG, "setAsCampingModeSwitch: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public boolean getAsCampingModeSwitch() {
        if (this.mCarConfig.isSupportAirSuspension()) {
            try {
                try {
                    if (1 != getIntProperty(557858819)) {
                        return false;
                    }
                } catch (Exception unused) {
                    if (this.mCarManager.getAsCampingModeSwitchStatus() != 1) {
                        return false;
                    }
                }
                return true;
            } catch (Exception e) {
                LogUtils.e(TAG, "getAsCampingModeSwitchStatus: " + e.getMessage(), false);
                return false;
            }
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getAsAutoLevelingResult() {
        if (this.mCarConfig.isSupportAirSuspension()) {
            try {
                try {
                    return getIntProperty(557858823);
                } catch (Exception unused) {
                    return this.mCarManager.getAsAutoLevelingResult();
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "getAsAutoLevelingResult: " + e.getMessage(), false);
                return 0;
            }
        }
        return 0;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public boolean getAsHeightChangingStatus() {
        if (this.mCarConfig.isSupportAirSuspension()) {
            try {
                return this.mCarManager.getAsHeightChangingStatus() != 0;
            } catch (Exception e) {
                LogUtils.e(TAG, "getAsHeightChangingStatus: " + e.getMessage(), false);
                return false;
            }
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getAsLockModeStatus() {
        if (this.mCarConfig.isSupportAirSuspension()) {
            try {
                return this.mCarManager.getAsLockModeStatus();
            } catch (Exception e) {
                LogUtils.e(TAG, "getAsHeightChangingStatus: " + e.getMessage(), false);
                return 2;
            }
        }
        return 2;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public boolean getAsHoistModeSwitchStatus() {
        if (this.mCarConfig.isSupportAirSuspension()) {
            try {
                return this.mCarManager.getAsHoistModeSwitchStatus() == 1;
            } catch (Exception e) {
                LogUtils.e(TAG, "getAsHoistModeSwitchStatus: " + e.getMessage(), false);
                return false;
            }
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setAsDrivingMode(int mode) {
        if (CarBaseConfig.getInstance().isSupportAirSuspension()) {
            try {
                this.mCarManager.setAsDrivingMode(mode);
            } catch (Exception e) {
                LogUtils.e(TAG, "setAsDrivingMode: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getAsDrivingMode() {
        if (this.mCarConfig.isSupportAirSuspension()) {
            try {
                return this.mCarManager.getAsDrivingMode();
            } catch (Exception e) {
                LogUtils.e(TAG, "getAsDrivingMode: " + e.getMessage(), false);
                return 0;
            }
        }
        return 0;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setFrontMirrorHeat(boolean enable) {
        if (this.mCarConfig.isSupportFrontMirrorHeat()) {
            try {
                this.mCarManager.setFrontMirrorHeatSwitchStatus(parseCduSwitchCmd(enable));
            } catch (Exception e) {
                LogUtils.e(TAG, "setFrontMirrorHeatSwitchStatus: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public boolean getFrontMirrorHeat() {
        if (this.mCarConfig.isSupportFrontMirrorHeat()) {
            try {
                try {
                    return parseBcmStatus(getIntProperty(557849846));
                } catch (Exception e) {
                    LogUtils.e(TAG, "getFrontMirrorHeatSwitchStatus: " + e.getMessage(), false);
                }
            } catch (Exception unused) {
                return parseBcmStatus(this.mCarManager.getFrontMirrorHeatSwitchStatus());
            }
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setTransportMode(boolean enable) {
        if (CarBaseConfig.getInstance().isSupportTrailerMode()) {
            try {
                this.mCarManager.setTransportModeSwitchStatus(enable ? 1 : 0);
            } catch (Exception e) {
                LogUtils.e(TAG, "setTransportModeSwitchStatus: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setTrailerHitchSwitchStatus(boolean enable) {
        if (CarBaseConfig.getInstance().isSupportTrailerHook()) {
            try {
                this.mCarManager.setTrailerHitchSwitchStatus(enable ? 1 : 0);
            } catch (Exception e) {
                LogUtils.e(TAG, "setTrailerHitchSwitchStatus: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getTrailerHitchSwitchStatus() {
        if (CarBaseConfig.getInstance().isSupportTrailerHook()) {
            try {
                try {
                    return getIntProperty(557858305);
                } catch (Exception unused) {
                    return this.mCarManager.getTrailerHitchSwitchStatus();
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "getTrailerHitchSwitchStatus: " + e.getMessage(), false);
                return 0;
            }
        }
        return -1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setAsTrailerModeSwitchStatus(boolean enable) {
        if (CarBaseConfig.getInstance().isSupportTrailerHook()) {
            try {
                this.mCarManager.setAsTrailerModeSwitchStatus(enable ? 1 : 0);
            } catch (Exception e) {
                LogUtils.e(TAG, "setAsTrailerModeSwitchStatus: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setCustomerModeFlag(boolean enable, boolean storeEnable) {
        if (CarBaseConfig.getInstance().isSupportAirSuspension()) {
            try {
                this.mCarManager.setCustomerModeFlagSwitchStatus(enable ? 1 : 0);
                if (storeEnable && this.mIsMainProcess) {
                    this.mDataSync.setAsCustomModeSwitch(enable);
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "setAsTrailerModeSwitchStatus: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setCustomerModeFlag(boolean enable) {
        setCustomerModeFlag(enable, true);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public boolean getCustomerModeFlag() {
        if (CarBaseConfig.getInstance().isSupportAirSuspension() && this.mIsMainProcess) {
            return this.mDataSync.getAsCustomModeSwitch();
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0035  */
    /* JADX WARN: Removed duplicated region for block: B:15:? A[RETURN, SYNTHETIC] */
    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean getTtmSystemError() {
        /*
            r4 = this;
            com.xiaopeng.carcontrol.config.CarBaseConfig r0 = com.xiaopeng.carcontrol.config.CarBaseConfig.getInstance()
            boolean r0 = r0.isSupportTrailerHook()
            r1 = 0
            if (r0 == 0) goto L31
            C extends android.car.hardware.CarEcuManager r0 = r4.mCarManager     // Catch: java.lang.Exception -> L14
            android.car.hardware.bcm.CarBcmManager r0 = (android.car.hardware.bcm.CarBcmManager) r0     // Catch: java.lang.Exception -> L14
            int r0 = r0.getTtmSystemErrorStatus()     // Catch: java.lang.Exception -> L14
            goto L32
        L14:
            r0 = move-exception
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "getTtmSystemErrorStatus: "
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r0 = r0.getMessage()
            java.lang.StringBuilder r0 = r2.append(r0)
            java.lang.String r0 = r0.toString()
            java.lang.String r2 = "BcmController"
            com.xiaopeng.carcontrol.util.LogUtils.e(r2, r0, r1)
        L31:
            r0 = r1
        L32:
            r2 = 1
            if (r0 != r2) goto L36
            r1 = r2
        L36:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.carcontrol.carmanager.impl.BcmController.getTtmSystemError():boolean");
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0035  */
    /* JADX WARN: Removed duplicated region for block: B:15:? A[RETURN, SYNTHETIC] */
    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean getAsYellowLampRequest() {
        /*
            r4 = this;
            com.xiaopeng.carcontrol.config.CarBaseConfig r0 = com.xiaopeng.carcontrol.config.CarBaseConfig.getInstance()
            boolean r0 = r0.isSupportAirSuspension()
            r1 = 0
            if (r0 == 0) goto L31
            C extends android.car.hardware.CarEcuManager r0 = r4.mCarManager     // Catch: java.lang.Exception -> L14
            android.car.hardware.bcm.CarBcmManager r0 = (android.car.hardware.bcm.CarBcmManager) r0     // Catch: java.lang.Exception -> L14
            int r0 = r0.getAsYellowLampRequest()     // Catch: java.lang.Exception -> L14
            goto L32
        L14:
            r0 = move-exception
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "getAsYellowLampRequest: "
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r0 = r0.getMessage()
            java.lang.StringBuilder r0 = r2.append(r0)
            java.lang.String r0 = r0.toString()
            java.lang.String r2 = "BcmController"
            com.xiaopeng.carcontrol.util.LogUtils.e(r2, r0, r1)
        L31:
            r0 = r1
        L32:
            r2 = 1
            if (r0 != r2) goto L36
            r1 = r2
        L36:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.carcontrol.carmanager.impl.BcmController.getAsYellowLampRequest():boolean");
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0035  */
    /* JADX WARN: Removed duplicated region for block: B:15:? A[RETURN, SYNTHETIC] */
    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean getAsRedLampRequest() {
        /*
            r4 = this;
            com.xiaopeng.carcontrol.config.CarBaseConfig r0 = com.xiaopeng.carcontrol.config.CarBaseConfig.getInstance()
            boolean r0 = r0.isSupportAirSuspension()
            r1 = 0
            if (r0 == 0) goto L31
            C extends android.car.hardware.CarEcuManager r0 = r4.mCarManager     // Catch: java.lang.Exception -> L14
            android.car.hardware.bcm.CarBcmManager r0 = (android.car.hardware.bcm.CarBcmManager) r0     // Catch: java.lang.Exception -> L14
            int r0 = r0.getAsRedLampRequest()     // Catch: java.lang.Exception -> L14
            goto L32
        L14:
            r0 = move-exception
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "getAsRedLampRequest: "
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r0 = r0.getMessage()
            java.lang.StringBuilder r0 = r2.append(r0)
            java.lang.String r0 = r0.toString()
            java.lang.String r2 = "BcmController"
            com.xiaopeng.carcontrol.util.LogUtils.e(r2, r0, r1)
        L31:
            r0 = r1
        L32:
            r2 = 1
            if (r0 != r2) goto L36
            r1 = r2
        L36:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.carcontrol.carmanager.impl.BcmController.getAsRedLampRequest():boolean");
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setTrailerModeStatus(final boolean enable) {
        if (CarBaseConfig.getInstance().isSupportTrailerRv()) {
            try {
                this.mCarManager.setTrailerModeStatus(enable ? 1 : 0);
                if (this.mIsMainProcess) {
                    this.mDataSync.setTrailerHitchStatus(enable);
                } else {
                    CarControl.PrivateControl.putBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.TTM_SWITCH_MODE, enable);
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "setTrailerModeStatus: " + e.getMessage(), false);
            }
            ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$BcmController$cTn2K49UqG3DSn1fzu_4h5F8GiU
                @Override // java.lang.Runnable
                public final void run() {
                    BcmController.this.lambda$setTrailerModeStatus$9$BcmController(enable);
                }
            });
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public boolean getTrailerModeStatus() {
        return this.mIsMainProcess ? this.mDataSync.getTrailerHitchStatus() : CarControl.PrivateControl.getBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.TTM_SWITCH_MODE, false);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getTtmLampConnectStatus() {
        if (CarBaseConfig.getInstance().isSupportTrailerHook()) {
            try {
                try {
                    return getIntProperty(557858308);
                } catch (Exception unused) {
                    return this.mCarManager.getTtmLampConnectStatus();
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "getTtmLampConnectStatus: " + e.getMessage(), false);
                return 0;
            }
        }
        return 0;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getTtmDenormalizeStatus() {
        if (CarBaseConfig.getInstance().isSupportTrailerHook()) {
            try {
                return this.mCarManager.getTtmDenormalizeStatus();
            } catch (Exception e) {
                LogUtils.e(TAG, "getTtmDenormalizeStatus: " + e.getMessage(), false);
                return 1;
            }
        }
        return 1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getTtmHookMotorStatus() {
        if (CarBaseConfig.getInstance().isSupportTrailerHook()) {
            try {
                try {
                    return getIntProperty(557858310);
                } catch (Exception unused) {
                    return this.mCarManager.getTtmHookMotorStatus();
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "getTtmHookMotorStatus: " + e.getMessage(), false);
                return 0;
            }
        }
        return 0;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getDaytimeRunningLightsOutputStatus() {
        int[] iArr;
        try {
            try {
                iArr = getIntArrayProperty(557915329);
            } catch (Exception e) {
                LogUtils.e(TAG, "getDaytimeRunningLightsOutputStatus failed: " + e.getMessage(), false);
                iArr = new int[]{0, 0};
            }
        } catch (Exception unused) {
            iArr = this.mCarManager.getDaytimeRunningLightsOutputStatus();
        }
        return convertDaytimeRunningLightState(iArr);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setCmsViewRecovery(boolean enable) {
        if (this.mCarConfig.isSupportCms()) {
            try {
                this.mCarManager.setCMSViewRecovery(enable ? 1 : 0);
            } catch (Exception e) {
                LogUtils.e(TAG, "setCmsViewRecovery failed: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setCmsObjectRecognizeSw(boolean enable) {
        if (this.mCarConfig.isSupportCms()) {
            try {
                this.mCarManager.setCMSDanObjectRecSw(enable ? 1 : 0);
                if (this.mIsMainProcess) {
                    this.mDataSync.setCmsObjectRecognizeSw(enable);
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "setCmsDanObjectRecSw failed: " + e.getMessage(), false);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x003b  */
    /* JADX WARN: Removed duplicated region for block: B:20:? A[RETURN, SYNTHETIC] */
    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean getCmsObjectRecognizeSw() {
        /*
            r4 = this;
            com.xiaopeng.carcontrol.config.CarBaseConfig r0 = r4.mCarConfig
            boolean r0 = r0.isSupportCms()
            r1 = 0
            if (r0 == 0) goto L37
            r0 = 557859335(0x21404207, float:6.5139517E-19)
            int r0 = r4.getIntProperty(r0)     // Catch: java.lang.Exception -> L11
            goto L38
        L11:
            r0 = move-exception
            C extends android.car.hardware.CarEcuManager r2 = r4.mCarManager     // Catch: java.lang.Exception -> L1b
            android.car.hardware.bcm.CarBcmManager r2 = (android.car.hardware.bcm.CarBcmManager) r2     // Catch: java.lang.Exception -> L1b
            int r0 = r2.getCMSDanObjectRecSwSt()     // Catch: java.lang.Exception -> L1b
            goto L38
        L1b:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "getCmsDanObjectRecSw: "
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r0 = r0.getMessage()
            java.lang.StringBuilder r0 = r2.append(r0)
            java.lang.String r0 = r0.toString()
            java.lang.String r2 = "BcmController"
            com.xiaopeng.carcontrol.util.LogUtils.e(r2, r0, r1)
        L37:
            r0 = r1
        L38:
            r2 = 1
            if (r0 != r2) goto L3c
            r1 = r2
        L3c:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.carcontrol.carmanager.impl.BcmController.getCmsObjectRecognizeSw():boolean");
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setCmsReverseAssistSw(boolean enable) {
        if (this.mCarConfig.isSupportCms()) {
            try {
                this.mCarManager.setCMSReverseAssitSw(enable ? 1 : 0);
                if (this.mIsMainProcess) {
                    this.mDataSync.setCmsReverseSw(enable);
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "setCmsReverseAssistSw failed: " + e.getMessage(), false);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x003b  */
    /* JADX WARN: Removed duplicated region for block: B:20:? A[RETURN, SYNTHETIC] */
    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean getCmsReverseAssistSw() {
        /*
            r4 = this;
            com.xiaopeng.carcontrol.config.CarBaseConfig r0 = r4.mCarConfig
            boolean r0 = r0.isSupportCms()
            r1 = 0
            if (r0 == 0) goto L37
            r0 = 557859331(0x21404203, float:6.5139496E-19)
            int r0 = r4.getIntProperty(r0)     // Catch: java.lang.Exception -> L11
            goto L38
        L11:
            r0 = move-exception
            C extends android.car.hardware.CarEcuManager r2 = r4.mCarManager     // Catch: java.lang.Exception -> L1b
            android.car.hardware.bcm.CarBcmManager r2 = (android.car.hardware.bcm.CarBcmManager) r2     // Catch: java.lang.Exception -> L1b
            int r0 = r2.getCMSReverseAssitSwSt()     // Catch: java.lang.Exception -> L1b
            goto L38
        L1b:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "getCMSReverseAssitSwSt: "
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r0 = r0.getMessage()
            java.lang.StringBuilder r0 = r2.append(r0)
            java.lang.String r0 = r0.toString()
            java.lang.String r2 = "BcmController"
            com.xiaopeng.carcontrol.util.LogUtils.e(r2, r0, r1)
        L37:
            r0 = r1
        L38:
            r2 = 1
            if (r0 != r2) goto L3c
            r1 = r2
        L3c:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.carcontrol.carmanager.impl.BcmController.getCmsReverseAssistSw():boolean");
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setCmsTurnAssistSw(boolean enable) {
        if (this.mCarConfig.isSupportCms()) {
            try {
                this.mCarManager.setCMSTurnExtSw(enable ? 1 : 0);
                if (this.mIsMainProcess) {
                    this.mDataSync.setCmsTurnSw(enable);
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "setCMSTurnExtSw failed: " + e.getMessage(), false);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x003b  */
    /* JADX WARN: Removed duplicated region for block: B:20:? A[RETURN, SYNTHETIC] */
    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean getCmsTurnAssistSw() {
        /*
            r4 = this;
            com.xiaopeng.carcontrol.config.CarBaseConfig r0 = r4.mCarConfig
            boolean r0 = r0.isSupportCms()
            r1 = 0
            if (r0 == 0) goto L37
            r0 = 557859332(0x21404204, float:6.51395E-19)
            int r0 = r4.getIntProperty(r0)     // Catch: java.lang.Exception -> L11
            goto L38
        L11:
            r0 = move-exception
            C extends android.car.hardware.CarEcuManager r2 = r4.mCarManager     // Catch: java.lang.Exception -> L1b
            android.car.hardware.bcm.CarBcmManager r2 = (android.car.hardware.bcm.CarBcmManager) r2     // Catch: java.lang.Exception -> L1b
            int r0 = r2.getCMSTurnExtSwSt()     // Catch: java.lang.Exception -> L1b
            goto L38
        L1b:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "getCMSTurnExtSwSt: "
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r0 = r0.getMessage()
            java.lang.StringBuilder r0 = r2.append(r0)
            java.lang.String r0 = r0.toString()
            java.lang.String r2 = "BcmController"
            com.xiaopeng.carcontrol.util.LogUtils.e(r2, r0, r1)
        L37:
            r0 = r1
        L38:
            r2 = 1
            if (r0 != r2) goto L3c
            r1 = r2
        L3c:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.carcontrol.carmanager.impl.BcmController.getCmsTurnAssistSw():boolean");
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setCmsHighSpdAssistSw(boolean enable) {
        if (this.mCarConfig.isSupportCms()) {
            try {
                this.mCarManager.setCMSHighSpeedViewSw(enable ? 1 : 0);
                if (this.mIsMainProcess) {
                    this.mDataSync.setCmsHighSpdSw(enable);
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "setCMSHighSpeedViewSw failed: " + e.getMessage(), false);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x003b  */
    /* JADX WARN: Removed duplicated region for block: B:20:? A[RETURN, SYNTHETIC] */
    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean getCmsHighSpdAssistSw() {
        /*
            r4 = this;
            com.xiaopeng.carcontrol.config.CarBaseConfig r0 = r4.mCarConfig
            boolean r0 = r0.isSupportCms()
            r1 = 0
            if (r0 == 0) goto L37
            r0 = 557859333(0x21404205, float:6.5139506E-19)
            int r0 = r4.getIntProperty(r0)     // Catch: java.lang.Exception -> L11
            goto L38
        L11:
            r0 = move-exception
            C extends android.car.hardware.CarEcuManager r2 = r4.mCarManager     // Catch: java.lang.Exception -> L1b
            android.car.hardware.bcm.CarBcmManager r2 = (android.car.hardware.bcm.CarBcmManager) r2     // Catch: java.lang.Exception -> L1b
            int r0 = r2.getCMSHighSpeedViewSwSt()     // Catch: java.lang.Exception -> L1b
            goto L38
        L1b:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "getCMSHighSpeedViewSwSt: "
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r0 = r0.getMessage()
            java.lang.StringBuilder r0 = r2.append(r0)
            java.lang.String r0 = r0.toString()
            java.lang.String r2 = "BcmController"
            com.xiaopeng.carcontrol.util.LogUtils.e(r2, r0, r1)
        L37:
            r0 = r1
        L38:
            r2 = 1
            if (r0 != r2) goto L3c
            r1 = r2
        L3c:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.carcontrol.carmanager.impl.BcmController.getCmsHighSpdAssistSw():boolean");
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public boolean getCmsLowSpdAssistSw() {
        this.mCarConfig.isSupportCms();
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setCmsBright(int brightness) {
        try {
            if (this.mCarConfig.isSupportCms()) {
                this.mCarManager.setCMSBrightWithFlag(brightness, 1);
                if (this.mIsMainProcess) {
                    this.mDataSync.setCmsBright(brightness);
                }
            }
        } catch (Throwable th) {
            LogUtils.e(TAG, "setCMSBright failed: " + th.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getCmsBright() {
        if (this.mCarConfig.isSupportCms()) {
            try {
                return getIntProperty(557924883);
            } catch (Throwable th) {
                try {
                    return this.mCarManager.getCMSBrightWithSource()[0];
                } catch (Throwable unused) {
                    LogUtils.e(TAG, "getCmsBright: " + th.getMessage(), false);
                    return 0;
                }
            }
        }
        return 0;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setARSFoldOrUnfold(boolean fold) {
        if (this.mCarConfig.isSupportArs()) {
            try {
                this.mCarManager.setARSFoldOrUnfold(fold ? 1 : 2);
            } catch (Throwable th) {
                LogUtils.e(TAG, "setARSWorkingMode: " + th.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setArsWorkMode(int mode) {
        if (this.mCarConfig.isSupportArs()) {
            try {
                this.mCarManager.setARSWorkingMode(mode);
            } catch (Throwable th) {
                LogUtils.e(TAG, "setARSWorkingMode: " + th.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getArsWorkMode() {
        int i;
        if (this.mCarConfig.isSupportArs()) {
            try {
                i = getIntProperty(557859841);
            } catch (Throwable th) {
                try {
                    i = this.mCarManager.getARSWorkingMode();
                } catch (Throwable unused) {
                    LogUtils.e(TAG, "getArsWorkMode failed: " + th.getMessage(), false);
                }
            }
            LogUtils.i(TAG, "getArsWorkMode: " + i, false);
            return i;
        }
        i = 0;
        LogUtils.i(TAG, "getArsWorkMode: " + i, false);
        return i;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void startArsInit() {
        if (this.mCarConfig.isSupportArs()) {
            try {
                this.mCarManager.setARSInitState(1);
            } catch (Throwable th) {
                LogUtils.e(TAG, "startArsInit: " + th.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getArsInitSt() {
        int i;
        int i2;
        if (this.mCarConfig.isSupportArs()) {
            try {
                i = getIntProperty(557859844);
            } catch (Throwable th) {
                try {
                    i2 = this.mCarManager.getARSInitState();
                    try {
                        LogUtils.i(TAG, "getARSInitState: " + i2);
                    } catch (Throwable unused) {
                        LogUtils.e(TAG, "getARSInitState: " + th.getMessage(), false);
                        i = i2;
                        LogUtils.i(TAG, "getArsInitSt: " + i, false);
                        return i;
                    }
                } catch (Throwable unused2) {
                    i2 = 0;
                }
                i = i2;
            }
        } else {
            i = 0;
        }
        LogUtils.i(TAG, "getArsInitSt: " + i, false);
        return i;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getArsWorkSt() {
        int i;
        if (this.mCarConfig.isSupportArs()) {
            try {
                i = getIntProperty(557859842);
            } catch (Throwable th) {
                try {
                    i = this.mCarManager.getARSWorkingState();
                } catch (Throwable unused) {
                    LogUtils.e(TAG, "getARSWorkingState: " + th.getMessage(), false);
                }
            }
            LogUtils.i(TAG, "getArsWorkSt: " + i, false);
            return i;
        }
        i = 0;
        LogUtils.i(TAG, "getArsWorkSt: " + i, false);
        return i;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getArsPosition() {
        int i;
        if (this.mCarConfig.isSupportArs()) {
            try {
                i = getIntProperty(557859843);
            } catch (Throwable th) {
                try {
                    i = this.mCarManager.getARSPosition();
                } catch (Throwable unused) {
                    LogUtils.e(TAG, "getARSPosition: " + th.getMessage(), false);
                }
            }
            LogUtils.i(TAG, "getArsPosition: " + i, false);
            return i;
        }
        i = 0;
        LogUtils.i(TAG, "getArsPosition: " + i, false);
        return i;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getArsFaultState() {
        int i;
        if (this.mCarConfig.isSupportArs()) {
            try {
                i = getIntProperty(557859846);
            } catch (Throwable th) {
                try {
                    i = this.mCarManager.getARSFaultState();
                } catch (Throwable unused) {
                    LogUtils.e(TAG, "getArsFaultState: " + th.getMessage(), false);
                }
            }
            LogUtils.i(TAG, "getArsFaultState: " + i, false);
            return i;
        }
        i = 0;
        LogUtils.i(TAG, "getArsFaultState: " + i, false);
        return i;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void saveSteerPos(int[] pos) {
        if (this.mIsMainProcess) {
            this.mDataSync.setSteerData(pos);
        }
        try {
            this.mCarManager.setColumnPositionSaveToMcu(pos[0], pos[1]);
        } catch (Exception e) {
            LogUtils.e(TAG, "saveSteerPos: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int[] getSteerSavedPos() {
        return this.mIsMainProcess ? this.mDataSync.getSteerData(false) : new int[]{50, 50};
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int[] getSteerPos() {
        int i;
        int i2 = 50;
        try {
            i = getIntProperty(557849901);
        } catch (Exception unused) {
            i = 50;
        }
        try {
            i2 = getIntProperty(557849900);
        } catch (Exception unused2) {
            try {
                i = this.mCarManager.getColumnHorizonalPosition();
                i2 = this.mCarManager.getColumnVerticalPosition();
            } catch (Exception e) {
                LogUtils.e(TAG, "getSteerPos: " + e.getMessage(), false);
            }
            return new int[]{i2, i};
        }
        return new int[]{i2, i};
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setSteerPos(int[] pos) {
        if (pos == null || pos.length < 2) {
            return;
        }
        try {
            this.mCarManager.setColumnPositionMove(pos[0], pos[1]);
        } catch (Exception e) {
            LogUtils.e(TAG, "setSteerPos: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setSteerVerPos(int pos) {
        try {
            this.mCarManager.setColumnVerticalPosition(pos);
        } catch (Exception e) {
            LogUtils.e(TAG, "setSteerVerPos: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setSteerHorPos(int pos) {
        try {
            this.mCarManager.setColumnHorizonalPosition(pos);
        } catch (Exception e) {
            LogUtils.e(TAG, "setSteerHorPos: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void controlVerSteer(int controlType, int direction) {
        if (direction != -1) {
            try {
                this.mCarManager.setColumnVerticalMove(controlType, direction);
            } catch (Exception e) {
                LogUtils.e(TAG, "controlVerSteer: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void controlInOutSteer(int controlType, int direction) {
        if (direction != -1) {
            try {
                this.mCarManager.setColumnHorizonalMove(controlType, direction);
            } catch (Exception e) {
                LogUtils.e(TAG, "controlInOutSteer: " + e.getMessage(), false);
            }
        }
    }

    protected void registerContentObserver() {
        if (this.mContentObserver == null) {
            this.mContentObserver = new ContentObserver(ThreadUtils.getHandler(0)) { // from class: com.xiaopeng.carcontrol.carmanager.impl.BcmController.3
                @Override // android.database.ContentObserver
                public void onChange(boolean selfChange, Uri uri) {
                    String lastPathSegment = uri.getLastPathSegment();
                    if (CarControl.PrivateControl.UNLOCK_RESPONSE.equals(lastPathSegment)) {
                        int i = CarControl.PrivateControl.getInt(App.getInstance().getContentResolver(), CarControl.PrivateControl.UNLOCK_RESPONSE, GlobalConstant.DEFAULT.UNLOCK_RESPONSE);
                        LogUtils.d(BcmController.TAG, "onChange: " + lastPathSegment + ", unlock response =" + i);
                        BcmController.this.mUnlockResponse = Integer.valueOf(i);
                        BcmController.this.lambda$setUnlockResponse$5$BcmController(i);
                        return;
                    }
                    if (CarControl.PrivateControl.WIN_HIGH_SPD_NOTIFY.equals(lastPathSegment)) {
                        BcmController.this.mIsHighSpdWinClose = Boolean.valueOf(CarControl.PrivateControl.getBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.WIN_HIGH_SPD, false));
                        LogUtils.d(BcmController.TAG, "onChange: " + lastPathSegment + ", newValue=" + BcmController.this.mIsHighSpdWinClose);
                        BcmController bcmController = BcmController.this;
                        bcmController.handleHighSpdCloseWinChanged(bcmController.mIsHighSpdWinClose.booleanValue());
                    } else if (BcmController.CHILD_MODE_SW.equals(lastPathSegment)) {
                        boolean z = Settings.System.getInt(App.getInstance().getContentResolver(), BcmController.CHILD_MODE_SW, 0) == 1;
                        LogUtils.d(BcmController.TAG, "selfChange: " + selfChange + ", newValue=" + z);
                        BcmController.this.handleChildMode(z);
                    } else if (IBcmController.REAR_SCREEN_STATE.equals(lastPathSegment)) {
                        int i2 = Settings.System.getInt(App.getInstance().getContentResolver(), IBcmController.REAR_SCREEN_STATE, 0);
                        LogUtils.d(BcmController.TAG, "onChange: " + lastPathSegment + ", rear screen state = " + i2);
                        BcmController.this.handleRearScreenState(i2);
                    }
                }
            };
        }
        App.getInstance().getContentResolver().registerContentObserver(CarControl.PrivateControl.getUriFor(CarControl.PrivateControl.UNLOCK_RESPONSE), false, this.mContentObserver);
        App.getInstance().getContentResolver().registerContentObserver(CarControl.PrivateControl.getUriFor(CarControl.PrivateControl.WIN_HIGH_SPD_NOTIFY), false, this.mContentObserver);
        App.getInstance().getContentResolver().registerContentObserver(Settings.System.getUriFor(CHILD_MODE_SW), true, this.mContentObserver);
        App.getInstance().getContentResolver().registerContentObserver(Settings.System.getUriFor(IBcmController.REAR_SCREEN_STATE), true, this.mContentObserver);
    }

    private void mockBcmValue() {
        this.mCarPropertyMap.put(557849640, new CarPropertyValue<>(557849640, 3));
        int i = 0;
        this.mCarPropertyMap.put(557849633, new CarPropertyValue<>(557849633, 0));
        this.mCarPropertyMap.put(289410561, new CarPropertyValue<>(289410561, 0));
        this.mCarPropertyMap.put(557849626, new CarPropertyValue<>(557849626, 0));
        this.mCarPropertyMap.put(557849627, new CarPropertyValue<>(557849627, Integer.valueOf((this.mDataSync == null || this.mDataSync.getLightMeHome()) ? 2 : 1)));
        this.mCarPropertyMap.put(557849602, new CarPropertyValue<>(557849602, 0));
        this.mCarPropertyMap.put(557849623, new CarPropertyValue<>(557849623, 0));
        this.mCarPropertyMap.put(557849624, new CarPropertyValue<>(557849624, 0));
        this.mCarPropertyMap.put(Integer.valueOf((int) IBcmController.ID_BCM_EBW), new CarPropertyValue<>((int) IBcmController.ID_BCM_EBW, 0));
        this.mCarPropertyMap.put(557915161, new CarPropertyValue<>(557915161, new Integer[]{0, 0, 0, 0}));
        this.mCarPropertyMap.put(559946855, new CarPropertyValue<>(559946855, Float.valueOf(100.0f)));
        this.mCarPropertyMap.put(559946856, new CarPropertyValue<>(559946856, Float.valueOf(100.0f)));
        this.mCarPropertyMap.put(559946854, new CarPropertyValue<>(559946854, Float.valueOf(100.0f)));
        this.mCarPropertyMap.put(559946857, new CarPropertyValue<>(559946857, Float.valueOf(100.0f)));
        this.mCarPropertyMap.put(557849610, new CarPropertyValue<>(557849610, 0));
        this.mCarPropertyMap.put(557849628, new CarPropertyValue<>(557849628, Integer.valueOf((this.mDataSync == null || this.mDataSync.getDriveAutoLock()) ? 1 : 0)));
        ConcurrentHashMap<Integer, CarPropertyValue<?>> concurrentHashMap = this.mCarPropertyMap;
        if (this.mDataSync != null && this.mDataSync.getParkAutoUnlock()) {
            i = 1;
        }
        concurrentHashMap.put(557849629, new CarPropertyValue<>(557849629, Integer.valueOf(i)));
        this.mCarPropertyMap.put(557849630, new CarPropertyValue<>(557849630, Integer.valueOf(this.mDataSync == null ? GlobalConstant.DEFAULT.UNLOCK_RESPONSE : this.mDataSync.getUnlockResponse())));
        this.mCarPropertyMap.put(557849609, new CarPropertyValue<>(557849609, 0));
        this.mCarPropertyMap.put(557849754, new CarPropertyValue<>(557849754, Integer.valueOf(this.mDataSync == null ? GlobalConstant.DEFAULT.WIPER_Sensitivity : this.mDataSync.getWiperSensitivity())));
        this.mCarPropertyMap.put(557849607, new CarPropertyValue<>(557849607, 0));
        this.mCarPropertyMap.put(557849637, new CarPropertyValue<>(557849637, 0));
        this.mCarPropertyMap.put(557849665, new CarPropertyValue<>(557849665, 0));
        this.mCarPropertyMap.put(557849638, new CarPropertyValue<>(557849638, 0));
        this.mCarPropertyMap.put(356517139, new CarPropertyValue<>(356517139, 0));
        mockExtBcmValue();
    }

    protected void mockExtBcmValue() {
        int i = 0;
        if (this.mCarConfig.isSupportRearSeatHeat()) {
            this.mCarPropertyMap.put(557849770, new CarPropertyValue<>(557849770, 0));
            this.mCarPropertyMap.put(557849769, new CarPropertyValue<>(557849769, 0));
            this.mCarPropertyMap.put(557849771, new CarPropertyValue<>(557849771, 0));
            this.mCarPropertyMap.put(557849772, new CarPropertyValue<>(557849772, 0));
        }
        if (this.mCarConfig.isSupportPollingOpenCfg()) {
            this.mCarPropertyMap.put(557849646, new CarPropertyValue<>(557849646, false));
        }
        if (this.mCarConfig.isSupportPollingLock()) {
            this.mCarPropertyMap.put(557849717, new CarPropertyValue<>(557849717, Integer.valueOf((this.mDataSync == null || this.mDataSync.getPollingUnlock()) ? 1 : 0)));
            this.mCarPropertyMap.put(557849716, new CarPropertyValue<>(557849716, Integer.valueOf((this.mDataSync == null || this.mDataSync.getPollingLock()) ? 1 : 0)));
        }
        this.mCarPropertyMap.put(557849650, new CarPropertyValue<>(557849650, Integer.valueOf(this.mDataSync == null ? 1 : this.mDataSync.getLightMeHomeTime())));
        this.mCarPropertyMap.put(557849660, new CarPropertyValue<>(557849660, 50));
        this.mCarPropertyMap.put(557849662, new CarPropertyValue<>(557849662, 50));
        this.mCarPropertyMap.put(557849661, new CarPropertyValue<>(557849661, 50));
        this.mCarPropertyMap.put(557849663, new CarPropertyValue<>(557849663, 50));
        this.mCarPropertyMap.put(557849675, new CarPropertyValue<>(557849675, 1));
        this.mCarPropertyMap.put(557849641, new CarPropertyValue<>(557849641, 0));
        if (this.mIsMainProcess) {
            this.mCarPropertyMap.put(557849642, new CarPropertyValue<>(557849642, -1));
            this.mCarPropertyMap.put(557849643, new CarPropertyValue<>(557849643, -1));
        }
        this.mCarPropertyMap.put(557849611, new CarPropertyValue<>(557849611, 0));
        this.mCarPropertyMap.put(557849701, new CarPropertyValue<>(557849701, 0));
        ConcurrentHashMap<Integer, CarPropertyValue<?>> concurrentHashMap = this.mCarPropertyMap;
        if (this.mDataSync == null || this.mDataSync.getLockCloseWin()) {
            i = 1;
        }
        concurrentHashMap.put(557849715, new CarPropertyValue<>(557849715, Integer.valueOf(i)));
        if (this.mIsMainProcess && this.mCarConfig.isSupportCwc()) {
            this.mCarPropertyMap.put(557849713, new CarPropertyValue<>(557849713, 0));
            this.mCarPropertyMap.put(557849714, new CarPropertyValue<>(557849714, 0));
        }
        this.mCarPropertyMap.put(557849719, new CarPropertyValue<>(557849719, 0));
        this.mCarPropertyMap.put(557849655, new CarPropertyValue<>(557849655, 1));
        this.mCarPropertyMap.put(557849620, new CarPropertyValue<>(557849620, 0));
        this.mCarPropertyMap.put(557849720, new CarPropertyValue<>(557849720, 0));
        this.mCarPropertyMap.put(557849621, new CarPropertyValue<>(557849621, 0));
        this.mCarPropertyMap.put(557849622, new CarPropertyValue<>(557849622, 0));
    }

    private void mockDhcValue() {
        this.mCarPropertyMap.put(557849711, new CarPropertyValue<>(557849711, Integer.valueOf((this.mDataSync != null ? !this.mDataSync.getAutoDhc() : !GlobalConstant.DEFAULT.AUTO_DOOR_HANDLE) ? 0 : 1)));
    }

    protected void handleLightMeHome(int state) {
        if (state != 2 && state != 1) {
            LogUtils.w(TAG, "handleLightMeHome unknown state: " + state, false);
            return;
        }
        boolean z = state == 2;
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onLightMeHomeChanged(z);
            }
        }
    }

    protected void handleLightMeHomeTime(int time) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onLightMeHomeTimeChanged(time);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void handleHeadLampMode(int state) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onHeadLampModeChanged(state);
            }
        }
    }

    private void handleLampHeightLevel(int level) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onLampHeightLevelChanged(level);
            }
        }
    }

    private void handleAutoLampHeight(int value) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onAutoLampHeightChanged(value == 5);
            }
        }
    }

    private void handleLowBeamLamp(int status) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onLowBeamLampStateChanged(parseBcmStatus(status));
            }
        }
    }

    private void handleLowBeamOffConfirm(int status) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                IBcmController.Callback callback = (IBcmController.Callback) it.next();
                boolean z = true;
                if (status != 1) {
                    z = false;
                }
                callback.onLowBeamOffConfirmStateChanged(z);
            }
        }
    }

    private void handlePositionLamp(int status) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onPositionLampStateChanged(parseBcmStatus(status));
            }
        }
    }

    private void handleHighBeamLamp(int status) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onHighBeamLampStateChanged(parseBcmStatus(status));
            }
        }
    }

    private void handleRearFogLamp(int status) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onRearFogLampChanged(parseBcmStatus(status));
            }
        }
    }

    private void handleTurnLampState(int[] values) {
        if (values == null || values.length < 2) {
            return;
        }
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onTurnLampStateChanged(values);
            }
        }
    }

    private void handleDomeLightState(int state) {
        if (this.mIsMainProcess) {
            this.mDataSync.setDomeLight(state);
        }
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onDomeLightStateChanged(state);
            }
        }
    }

    protected void handleEbwState(int status) {
        boolean parseBcmStatus = parseBcmStatus(status);
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onEbwChanged(parseBcmStatus);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: handleMirrorReverseMode */
    public void lambda$setReverseMirrorMode$3$BcmController(int mode, boolean needMove) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onMirrorReverseModeChanged(mode, needMove);
            }
        }
    }

    private void handleMirrorPosition() {
        if (this.mIsMainProcess) {
            int[] mirrorPosition = getMirrorPosition(true);
            synchronized (this.mCallbackLock) {
                Iterator it = this.mCallbacks.iterator();
                while (it.hasNext()) {
                    ((IBcmController.Callback) it.next()).onMirrorPosChanged(mirrorPosition);
                }
            }
        }
    }

    private void handleDoorState(int[] state) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onDoorStateChanged(state);
            }
        }
    }

    private void handleAutoDoorHandle(int status) {
        boolean z = false;
        if (!this.mCarConfig.isSupportNewDhc() ? status == 1 : status == 1) {
            z = true;
        }
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onAutoDoorHandleChanged(z);
            }
        }
    }

    private void handleWindowPosition(int window, float pos) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                IBcmController.Callback callback = (IBcmController.Callback) it.next();
                if (pos > 100.0f) {
                    LogUtils.e(TAG, "handleWindowPosition window:" + window + ", error pos:" + pos + ", cast to 0");
                    pos = 0.0f;
                }
                callback.onWindowPosChanged(window, pos);
            }
        }
    }

    private void handleWiperInterval(int wiper) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onWiperIntervalChanged(wiper);
            }
        }
    }

    private void handleBonnetState(int state) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onBonnetStateChanged(state);
            }
        }
    }

    private void handleTrunkState(int state) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onTrunkStateChanged(state);
            }
        }
    }

    public /* synthetic */ void lambda$new$10$BcmController() {
        try {
            LogUtils.i(TAG, "handleElcTrunkState, state: 9, time out");
            handleCarEventsUpdate(new CarPropertyValue<>(557849903, 10));
        } catch (Exception e) {
            LogUtils.i(TAG, "handleElcTrunkState, state: 9, time out, and exception:" + e);
        }
    }

    private void handleElcTrunkState(int state) {
        if (state == 9 || state == 1) {
            try {
                int trunkActualPosition = this.mCarManager.getTrunkActualPosition();
                if (trunkActualPosition == 2) {
                    handleElcTrunkPos(trunkActualPosition);
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "handleElcTrunkState: " + e.getMessage(), false);
            }
        }
        if (state == 9) {
            LogUtils.i(TAG, "handleElcTrunkState, state: " + state + ", ignored");
            ThreadUtils.postDelayed(this.mFixElcTrunkState.newSession(), 4000L);
            return;
        }
        this.mFixElcTrunkState.cancel();
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onElcTrunkStateChanged(state);
            }
        }
    }

    private void handleElcTrunkPos(int pos) {
        int fixElcTrunkPos = fixElcTrunkPos(pos, "handleCallback");
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onElcTrunkPosChanged(fixElcTrunkPos);
            }
        }
    }

    private void handleTrunkFullOpenPos(int level) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onTrunkFullOpenPosChanged(level);
            }
        }
    }

    private void handleTrunkSensorEnable(int value) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                IBcmController.Callback callback = (IBcmController.Callback) it.next();
                boolean z = true;
                if (value != 1) {
                    z = false;
                }
                callback.onTrunkSensorEnableChanged(z);
            }
        }
    }

    private void handleTrunkWorkStatusChange(int value) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onTrunkWorkStatusChange(value);
            }
        }
    }

    private void handleDriveAutoLock(int status) {
        boolean parseBcmStatus = parseBcmStatus(status);
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onDriveAutoLockChanged(parseBcmStatus);
            }
        }
    }

    private void handleParkingAutoUnlock(int status) {
        boolean parseBcmStatus = parseBcmStatus(status);
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onParkingAutoUnlockChanged(parseBcmStatus);
            }
        }
    }

    private void handleLeaveAutoLock(int state) {
        boolean z = state == 1;
        if (this.mIsMainProcess) {
            this.mDataSync.setPollingLock(z);
        }
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onLeaveAutoLockChanged(z);
            }
        }
    }

    private void handlePollingOpenCfg(int state) {
        boolean z = state == 1;
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onPollingOpenCfgChanged(z);
            }
        }
    }

    private void handleNearAutoUnlock(int state) {
        boolean z = state == 1;
        if (this.mIsMainProcess) {
            this.mDataSync.setPollingUnlock(z);
        }
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onNearAutoUnlockChanged(z);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: handleUnlockResponse */
    public void lambda$setUnlockResponse$5$BcmController(int type) {
        int unlockResponse = this.mIsMainProcess ? this.mDataSync.getUnlockResponse() : this.mUnlockResponse.intValue();
        LogUtils.d(TAG, "handleUnlockResponse, type: " + type + ", newType: " + unlockResponse);
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onUnlockResponseChanged(unlockResponse);
            }
        }
    }

    private void handleChildLock(int state) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onChildLockChanged(state);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleChildMode(boolean state) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onChildModeChanged(state);
            }
        }
    }

    private void handleLeftChildLock(int state) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onLeftChildLockChanged(state == 4);
            }
        }
    }

    private void handleRightChildLock(int state) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onRightChildLockChanged(state == 5);
            }
        }
    }

    private void handleLeftHotKey(boolean state) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onLeftDoorHotKeyChanged(state);
            }
        }
    }

    private void handleRightHotKey(boolean state) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onRightDoorHotKeyChanged(state);
            }
        }
    }

    private void handleCentralLockState(int status) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                IBcmController.Callback callback = (IBcmController.Callback) it.next();
                boolean z = true;
                if (status != 1) {
                    z = false;
                }
                callback.onCentralLockStateChanged(z);
            }
        }
    }

    private void handleChargePort(boolean isLeft, int state) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onChargePortChanged(isLeft, state, false);
            }
        }
    }

    private void handleWiperRepairMode(int status) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onWiperRepairModeChanged(parseBcmStatus(status));
            }
        }
    }

    private void handleRearWiperRepairMode(int status) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onRearWiperRepairModeChanged(parseBcmStatus(status));
            }
        }
    }

    private void handleWiperSensitivity(int level) {
        int convertWiperSensitiveLevel = convertWiperSensitiveLevel(level);
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onWiperSensitivityChanged(convertWiperSensitiveLevel);
            }
        }
    }

    private void handleWiperSensitivityUp(int value) {
        if (value == 1) {
            synchronized (this.mCallbackLock) {
                Iterator it = this.mCallbacks.iterator();
                while (it.hasNext()) {
                    ((IBcmController.Callback) it.next()).onWiperSensitivityKeyUp();
                }
            }
        }
    }

    private void handleWiperSensitivityDown(int value) {
        if (value == 1) {
            synchronized (this.mCallbackLock) {
                Iterator it = this.mCallbacks.iterator();
                while (it.hasNext()) {
                    ((IBcmController.Callback) it.next()).onWiperSensitivityKeyDown();
                }
            }
        }
    }

    private void handleDrvSeatOccupied(int status) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                IBcmController.Callback callback = (IBcmController.Callback) it.next();
                boolean z = true;
                if (status != 1) {
                    z = false;
                }
                callback.onDrvSeatOccupiedChanged(z);
            }
        }
    }

    private void handleBackDefrost(int status) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onBackDefrostChanged(parseBcmStatus(status));
            }
        }
    }

    private void handleMirrorHeat(int status) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onMirrorHeatModeChanged(parseBcmStatus(status));
            }
        }
    }

    private void handleFrontMirrorHeat(int status) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onFrontMirrorHeatModeChanged(parseBcmStatus(status));
            }
        }
    }

    protected void handleDriverSeatHeatLevel(int level) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onDriverSeatHeatLevelChanged(level);
            }
        }
        if (level == 0 || getSeatVentLevel() == 0) {
            return;
        }
        setSeatVentLevel(0, true);
    }

    protected void handleDriverSeatBlowLevel(int level) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onDriverSeatBlowLevelChanged(level);
            }
        }
        if (level == 0 || getSeatHeatLevel() == 0) {
            return;
        }
        setSeatHeatLevel(0, true);
    }

    private void handlePsnSeatHeatLevel(int level) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onPsnSeatHeatLevelChanged(level);
            }
        }
        if (level == 0 || getPsnSeatVentLevel() == 0) {
            return;
        }
        setPsnSeatVentLevel(0);
    }

    private void handlePsnSeatVentLevel(int level) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onPsnSeatVentLevelChanged(level);
            }
        }
        if (level == 0 || getPsnSeatHeatLevel() == 0) {
            return;
        }
        setPsnSeatHeatLevel(0);
    }

    private void handleSteerHeatLevel(int level) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onSteerHeatLevelChanged(level);
            }
        }
    }

    private void handleRLSeatHeatLevel(int level) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onRLSeatHeatLevelChanged(level);
            }
        }
    }

    private void handleRRSeatHeatLevel(int level) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onRRSeatHeatLevelChanged(level);
            }
        }
    }

    private void handleRLSeatHeatERR(int code) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onRLSeatHeatLevelERR(code);
            }
        }
    }

    private void handleRRSeatHeatERR(int code) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onRRSeatHeatLevelERR(code);
            }
        }
    }

    protected void handleAutoWindowLockSwChange(int state) {
        boolean z = state == 1;
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onAutoWindowLockSwChanged(z);
            }
        }
    }

    protected void handleWindowLockState(int state) {
        boolean z = state == 1;
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onWindowLockStateChanged(z);
            }
        }
    }

    private void handleCwcChargeErrorStateChanged(int state) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onCwcChargeErrorStateChanged(state);
            }
        }
    }

    private void handleCwcChargeStateChanged(int state) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onCwcChargeStateChanged(state);
            }
        }
    }

    private void handleCwcFRChargeErrorStateChanged(int state) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onCwcFRChargeErrorStateChanged(state);
            }
        }
    }

    private void handleCwcFRChargeStateChanged(int state) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onCwcFRChargeStateChanged(state);
            }
        }
    }

    private void handleCwcRLChargeErrorStateChanged(int state) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onCwcRLChargeErrorStateChanged(state);
            }
        }
    }

    private void handleCwcRLChargeStateChanged(int state) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onCwcRLChargeStateChanged(state);
            }
        }
    }

    private void handleCwcRRChargeErrorStateChanged(int state) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onCwcRRChargeErrorStateChanged(state);
            }
        }
    }

    private void handleCwcRRChargeStateChanged(int state) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onCwcRRChargeStateChanged(state);
            }
        }
    }

    private void handleStealthModeChanged(int state) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                IBcmController.Callback callback = (IBcmController.Callback) it.next();
                boolean z = true;
                if (state != 1) {
                    z = false;
                }
                callback.onStealthModeChanged(z);
            }
        }
    }

    private void handleNfcStopSwChanged(int status) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                IBcmController.Callback callback = (IBcmController.Callback) it.next();
                boolean z = true;
                if (status != 1) {
                    z = false;
                }
                callback.onNfcStopSwChanged(z);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleHighSpdCloseWinChanged(boolean enabled) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onHighSpdCloseWinChanged(enabled);
            }
        }
    }

    private void handlParkLightFmbCfg(int status) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onParkLightFmbChanged(status);
            }
        }
    }

    private void handleParkingLampStates(int[] states) {
        if (states == null || states.length < 3) {
            return;
        }
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onParkingLampStatesChanged(states);
            }
        }
    }

    private void handleBcmBrkPedalState(int status) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onBcmBrkPedalStateChanged(status);
            }
        }
    }

    private void handleBcmReadyEnableState(int status) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onBcmReadyStateChanged(status);
            }
        }
    }

    private void handleKeyAuthState(int status) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onKeyAuthStateChanged(status);
            }
        }
    }

    private void handleUnlockReqSrc(int reqSrc) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onUnlockReqSrcChanged(reqSrc);
            }
        }
    }

    private void handleLeftSdcDoorPos(int pos) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onLeftSdcDoorPosChanged(pos);
            }
        }
    }

    private void handleRightSdcDoorPos(int pos) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onRightSdcDoorPosChanged(pos);
            }
        }
    }

    private void handleLeftSdcSystemRunningState(int state) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onLeftSdcSysRunningStateChanged(state);
            }
        }
    }

    private void handleRightSdcSystemRunningState(int state) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onRightSdcSysRunningStateChanged(state);
            }
        }
    }

    private void handleSdcKeyCfgState(int cfg) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onSdcKeyCfgChanged(cfg);
            }
        }
    }

    private void handleSdcWinAutoDown(int status) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                IBcmController.Callback callback = (IBcmController.Callback) it.next();
                if (status == 2) {
                    callback.onSdcWinAutoDown(true);
                } else {
                    callback.onSdcWinAutoDown(getSdcWindowsAutoDownSwitch());
                }
            }
        }
    }

    private int convertWiperSensitiveLevel(int rawLevel) {
        if (this.mCarConfig.isWiperSensitiveNegative()) {
            int i = rawLevel != 1 ? rawLevel != 2 ? rawLevel != 3 ? rawLevel != 4 ? rawLevel : 1 : 2 : 3 : 4;
            LogUtils.i(TAG, "convertWiperLevel for Pack3: " + i + ", raw level: " + rawLevel, false);
            return i;
        }
        return rawLevel;
    }

    private void handleSdcMaxDoorOpeningAngleChange(int angle) {
        if (angle < 40) {
            angle = 40;
        } else if (angle > 100) {
            angle = 100;
        }
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onSdcMaxDoorOpeningAngleChanged(angle);
            }
        }
    }

    private void handleSdcBrakeCloseDoorCfgChange(int cfg) {
        if (cfg < 0 || cfg > 2) {
            return;
        }
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onSdcBrakeCloseDoorCfgChanged(cfg);
            }
        }
    }

    private void handleCwcSwChange(int status) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onCwcSwChanged(parseBcmStatus(status));
            }
        }
    }

    private void handleCwcFRSwChange(int status) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onCwcFRSwChanged(parseBcmStatus(status));
            }
        }
    }

    private void handleCwcRLSwChange(int status) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onCwcRLSwChanged(parseBcmStatus(status));
            }
        }
    }

    private void handleCwcRRSwChange(int status) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onCwcRRSwChanged(parseBcmStatus(status));
            }
        }
    }

    private void handleLeftSlideDoorModeChange(int mode) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onLeftSlideDoorModeChanged(mode);
            }
        }
    }

    private void handleRightSlideDoorModeChange(int mode) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onRightSlideDoorModeChanged(mode);
            }
        }
    }

    private void handleLeftSlideDoorStateChange(int status) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onLeftSlideDoorStateChanged(status);
            }
        }
    }

    private void handleRightSlideDoorStateChange(int status) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onRightSlideDoorStateChanged(status);
            }
        }
    }

    private void handleLeftSlideDoorLockStateChange(int state) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onLeftSlideDoorLockStateChanged(state);
            }
        }
    }

    private void handleRightSlideDoorLockStateChange(int state) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onRightSlideDoorLockStateChanged(state);
            }
        }
    }

    private void handleSunShadePosChange(int pos) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onSunShadePosChanged(convertSunShadePos(pos));
            }
        }
    }

    private void handleSunShadeInitializationChange(int state) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                IBcmController.Callback callback = (IBcmController.Callback) it.next();
                boolean z = true;
                if (state != 1) {
                    z = false;
                }
                callback.onSunShadeInitializationChanged(z);
            }
        }
    }

    private void handleMirrorAutoDown(int status) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onMirrorAutoDownChanged(status == 2);
            }
        }
    }

    private void handleMirrorAutoFold(int status) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                IBcmController.Callback callback = (IBcmController.Callback) it.next();
                boolean z = true;
                if (status != 1) {
                    z = false;
                }
                callback.onMirrorAutoFoldStateChanged(z);
            }
        }
    }

    private void handleWelcomeMode(int status) {
        boolean parseBcmStatus = parseBcmStatus(status);
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onWelcomeModeChanged(parseBcmStatus);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: handleRearSeatWelcomeModeUpdate */
    public void lambda$setRearSeatWelcomeMode$7$BcmController(boolean enabled) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onRearSeatWelcomeModeChanged(enabled);
            }
        }
    }

    private void handleDrvBeltWarningChanged(final int state) {
        synchronized (this.mCallbackLock) {
            this.mCallbacks.forEach(new Consumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$BcmController$7dwINZKstKmIBtsBKGdnl6nnrcw
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((IBcmController.Callback) obj).onDrvBeltWaringChanged(state);
                }
            });
        }
    }

    private void handleEsbChanged(int status) {
        boolean parseBcmStatus = parseBcmStatus(status);
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onEsbChanged(parseBcmStatus);
            }
        }
    }

    private void handleRsbWarningChanged(int status) {
        boolean parseBcmStatus = parseBcmStatus(status);
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onBackBeltWaringChanged(parseBcmStatus);
            }
        }
    }

    private void handleWinPosChanged(float[] winsPos) {
        if (winsPos == null || winsPos.length != 4) {
            return;
        }
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onWindowsPosChanged(winsPos);
            }
        }
    }

    private void handleMirrorAdjust(int[] mirrorStates) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onMirrorAdjust(mirrorStates);
            }
        }
    }

    private void handleAsHeightChanged(int mode) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onAsHeightModeChanged(mode);
            }
        }
    }

    private void handleAsSoftChanged(int mode) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onAsSoftModeChanged(mode);
            }
        }
    }

    private void handleAsWelcomeMode(int mode) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onAsWelcomeModeChanged(parseBcmStatus(mode));
            }
        }
    }

    private void handleEasyLoadingMode(int mode) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onAsEasyLoadingModeChanged(parseBcmStatus(mode));
            }
        }
    }

    private void handleAsMaintenanceMode(int mode) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onAsMaintainModeChanged(mode);
            }
        }
    }

    private void handleAsCampingMode(int mode) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onAsCampingModeChanged(parseBcmStatus(mode));
            }
        }
    }

    private void handleAsLevlingMode(int mode) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onAsLevlingModeChanged(mode);
            }
        }
    }

    private void handleTrailerHitchStatus(int status) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onTrailerHitchStatusChanged(status);
            }
        }
    }

    private void handleTtmHookMotorStatus(int status) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onTtmHookMotorStatusChanged(status);
            }
        }
    }

    private void handleTtmLampConnectStatus(int status) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                IBcmController.Callback callback = (IBcmController.Callback) it.next();
                boolean z = true;
                if (status != 1) {
                    z = false;
                }
                callback.onTtmLampConnectStatusChanged(z);
            }
        }
    }

    private void handleTtmSysErr(int status) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                IBcmController.Callback callback = (IBcmController.Callback) it.next();
                boolean z = true;
                if (status != 1) {
                    z = false;
                }
                callback.onTtmSysErrStatusChanged(z);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: handleTrailerModeStatus */
    public void lambda$setTrailerModeStatus$9$BcmController(boolean enable) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onTrailerModeStatusChanged(enable);
            }
        }
    }

    private void handleDomeLightSt(int lightType, int state) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onDomeLightStChanged(lightType, state);
            }
        }
    }

    private void handleDomeLightBright(int value) {
        if (value == 0) {
            LogUtils.i(TAG, "handleDomeLightBright with invalid value: " + value);
            return;
        }
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onDomeLightBrightChanged(value);
            }
        }
    }

    private void handleDaytimeRunningLightChange(int[] status) {
        int convertDaytimeRunningLightState = convertDaytimeRunningLightState(status);
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onDaytimeRunningLightChanged(convertDaytimeRunningLightState);
            }
        }
    }

    private int convertDaytimeRunningLightState(int[] status) {
        if (status != null && status.length >= 2) {
            if (status[0] == 1 && status[1] == 1) {
                return 3;
            }
            if (status[0] == 0 && status[1] == 1) {
                return 2;
            }
            if (status[0] == 1 && status[1] == 0) {
                return 1;
            }
        }
        return 0;
    }

    private void handleCmsObjectRecognizeSwChange(int state) {
        boolean z = state == 1;
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onCmsObjectRecognizeSwChanged(z);
            }
        }
    }

    private void handleCmsReverseAssistSwChange(int state) {
        boolean z = state == 1;
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onCmsReverseSwChanged(z);
            }
        }
    }

    private void handleCmsTurnAssistSwChange(int state) {
        boolean z = state == 1;
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onCmsTurnSwChanged(z);
            }
        }
    }

    private void handleCmsHighSpdSwChange(int state) {
        boolean z = state == 1;
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onCmsHighSpdSwChanged(z);
            }
        }
    }

    private void handleCmsLowSpdSwChange(int state) {
        boolean z = state == 1;
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onCmsLowSpdSwChanged(z);
            }
        }
    }

    private void handleCmsAutoBrightSwChange(int state) {
        boolean z = state == 1;
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onCmsAutoBrightSwChanged(z);
            }
        }
    }

    private void handleCmsBrightChange(int brightness) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onCmsBrightChanged(brightness);
            }
        }
    }

    private void handleCmsViewAngleChange(int viewAngle) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onCmsViewAngleChanged(viewAngle);
            }
        }
    }

    private void handleCmsPosChange(Object pos) {
        float[] fArr;
        if (pos instanceof float[]) {
            fArr = (float[]) pos;
        } else if (pos instanceof Float[]) {
            Float[] fArr2 = (Float[]) pos;
            float[] fArr3 = new float[fArr2.length];
            for (int i = 0; i < fArr2.length; i++) {
                fArr3[i] = fArr2[i].floatValue();
            }
            fArr = fArr3;
        } else {
            fArr = null;
        }
        if (fArr == null) {
            LogUtils.i(TAG, "handleCmsPosChange error params: " + pos);
            return;
        }
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onCmsPosChanged(fArr);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleRearScreenState(int state) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onRearScreenStateChanged(state);
            }
        }
    }

    private void handleImsAutoVisionSwChange(int sw) {
        boolean z = sw == 1;
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onImsAutoVisionSwChanged(z);
            }
        }
    }

    private void handleImsBrightLevelChange(int level) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onImsBrightLevelChanged(level);
            }
        }
    }

    private void handleImsModeChange(int mode) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onImsModeChanged(mode);
            }
        }
    }

    private void handleImsSystemStChange(int state) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onImsSystemStChanged(state);
            }
        }
    }

    private void handleImsVisionAngleLevelChange(int level) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onImsVisionAngleLevelChanged(level);
            }
        }
    }

    private void handleImsVisionVerticalLevelChange(int level) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onImsVisionVerticalLevelChanged(level);
            }
        }
    }

    private void handleArsInitStChange(int state) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onArsInitStChanged(state);
            }
        }
    }

    private void handleArsWorkModeChange(int mode) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onArsWorkModeChanged(mode);
            }
        }
    }

    private void handleArsWorkStChange(int state) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onArsWorkStChanged(state);
            }
        }
    }

    private void handleArsPosChange(int position) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onArsPosChanged(position);
            }
        }
    }

    private void handleArsFaultStChange(int state) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onArsFaultStateChange(state);
            }
        }
    }

    private void handleRearLogoLightSwChange(int state) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                IBcmController.Callback callback = (IBcmController.Callback) it.next();
                boolean z = true;
                if (state != 1) {
                    z = false;
                }
                callback.onRearLogoLightSwChanged(z);
            }
        }
    }

    private void handleCarpetLightWelcomeStateChange(int state) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                IBcmController.Callback callback = (IBcmController.Callback) it.next();
                boolean z = true;
                if (state != 1) {
                    z = false;
                }
                callback.onCarpetLightWelcomeChanged(z);
            }
        }
    }

    private void handlePollingWelcomeStateChange(int state) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                IBcmController.Callback callback = (IBcmController.Callback) it.next();
                boolean z = true;
                if (state != 1) {
                    z = false;
                }
                callback.onPollingLightWelcomeChanged(z);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    public void getContentProviderValueBySync(String key) {
        ContentResolver contentResolver = App.getInstance().getContentResolver();
        key.hashCode();
        if (key.equals(CarControl.PrivateControl.UNLOCK_RESPONSE)) {
            Integer valueOf = Integer.valueOf(CarControl.PrivateControl.getInt(contentResolver, CarControl.PrivateControl.UNLOCK_RESPONSE, GlobalConstant.DEFAULT.UNLOCK_RESPONSE));
            this.mUnlockResponse = valueOf;
            lambda$setUnlockResponse$5$BcmController(valueOf.intValue());
        } else if (key.equals(CarControl.PrivateControl.WIN_HIGH_SPD)) {
            Boolean valueOf2 = Boolean.valueOf(CarControl.PrivateControl.getBool(contentResolver, CarControl.PrivateControl.WIN_HIGH_SPD, false));
            this.mIsHighSpdWinClose = valueOf2;
            handleHighSpdCloseWinChanged(valueOf2.booleanValue());
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setSunShadePos(int pos) {
        if (this.mCarConfig.isSupportSunShade()) {
            try {
                this.mCarManager.setShadeControllerPosition(pos);
            } catch (Error | Exception e) {
                LogUtils.e(TAG, "setSunShadePos: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getSunShadePos() {
        int i;
        if (this.mCarConfig.isSupportSunShade()) {
            try {
                try {
                    i = getIntProperty(557849804);
                } catch (Exception unused) {
                    i = 0;
                }
            } catch (Exception unused2) {
                i = this.mCarManager.getShadeControllerPosition();
            }
            return convertSunShadePos(i);
        }
        return -1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void resetSunShade() {
        try {
            this.mCarManager.setShadeControllerInitialization(1);
        } catch (Exception e) {
            LogUtils.e(TAG, "resetSunShade: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public boolean isSunShadeInitialized() {
        int i;
        if (this.mCarConfig.isSupportSunShade()) {
            try {
                try {
                    i = getIntProperty(557849805);
                } catch (Exception unused) {
                    i = this.mCarManager.getShadeControllerInitializationSt();
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "isSunShadeInitialized: " + e.getMessage(), false);
                i = 1;
            }
            return i == 1;
        }
        return true;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void controlSunShade(int control) {
        if (this.mCarConfig.isSupportSunShade()) {
            try {
                this.mCarManager.setShadeControllerComfortCommand(control);
            } catch (Error | Exception e) {
                LogUtils.e(TAG, "controlSunShade: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public boolean isSunShadeHotProtect() {
        try {
            return this.mCarManager.getScThermalProtectStatus() == 1;
        } catch (Exception e) {
            LogUtils.e(TAG, "isSunShadeHotProtect: " + e.getMessage(), false);
            return false;
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public boolean isSunShadeAntiPinch() {
        try {
            return this.mCarManager.getScAntiPinchStatus() == 1;
        } catch (Exception e) {
            LogUtils.e(TAG, "isSunShadeAntiPinch: " + e.getMessage(), false);
            return false;
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public boolean isSunShadeIceBreak() {
        try {
            return this.mCarManager.getScIceBreakMode() == 1;
        } catch (Exception e) {
            LogUtils.e(TAG, "isSunShadeIceBreak: " + e.getMessage(), false);
            return false;
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public float[] getCmsLocation() {
        return getCmsLocation(false);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setCmsViewAngle(int viewAngle) {
        if (this.mCarConfig.isSupportCms()) {
            try {
                this.mCarManager.setCMSViewAngle(viewAngle);
            } catch (Exception e) {
                LogUtils.e(TAG, "setCmsViewAngle: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getCmsViewAngle() {
        if (this.mCarConfig.isSupportCms()) {
            try {
                return getIntProperty(557859345);
            } catch (Exception e) {
                LogUtils.e(TAG, "getCmsViewAngle failed: " + e.getMessage(), false);
                try {
                    return this.mCarManager.getCMSViewAngle();
                } catch (Exception unused) {
                    LogUtils.e(TAG, "getCmsViewAngle failed2: " + e.getMessage(), false);
                    return 0;
                }
            }
        }
        return 0;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setImsAutoVisionSw(int sw) {
        if (this.mCarConfig.isSupportIms()) {
            try {
                this.mCarManager.setImsAutoVisionSw(sw);
                this.mDataSync.setImsAutoVisionSw(sw);
            } catch (Exception e) {
                LogUtils.e(TAG, "setImsAutoVisionSw: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getImsAutoVisionSw() {
        int imsAutoVisionSw;
        if (this.mCarConfig.isSupportIms()) {
            try {
                try {
                    return getIntProperty(557860866);
                } catch (Exception unused) {
                    imsAutoVisionSw = this.mCarManager.getImsAutoVisionSt();
                    return imsAutoVisionSw;
                }
            } catch (Exception e) {
                LogUtils.d(TAG, "getImsAutoVisionSw: " + e.getMessage(), false);
                imsAutoVisionSw = this.mDataSync.getImsAutoVisionSw();
                return imsAutoVisionSw;
            }
        }
        return 0;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setImsBrightLevel(int level) {
        if (this.mCarConfig.isSupportIms()) {
            try {
                this.mCarManager.setImsBrightLevel(level);
            } catch (Exception e) {
                LogUtils.e(TAG, "setImsBrightLevel: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getImsBrightLevel() {
        if (this.mCarConfig.isSupportIms()) {
            try {
                try {
                    return getIntProperty(557860867);
                } catch (Exception unused) {
                    return this.mCarManager.getImsBrightLevel();
                }
            } catch (Exception e) {
                LogUtils.d(TAG, "getImsBrightLevel: " + e.getMessage(), false);
                return 1;
            }
        }
        return 1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setImsMode(int mode) {
        setImsMode(mode, false);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setImsMode(int mode, boolean needSave) {
        if (this.mCarConfig.isSupportIms()) {
            try {
                this.mCarManager.setImsModeReq(mode);
                if (needSave) {
                    this.mDataSync.setImsMode(mode);
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "setImsMode: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getImsMode() {
        if (this.mCarConfig.isSupportIms()) {
            try {
                try {
                    return getIntProperty(557860865);
                } catch (Exception e) {
                    LogUtils.d(TAG, "getImsMode: " + e.getMessage(), false);
                    return this.mDataSync.getImsMode();
                }
            } catch (Exception unused) {
                return this.mCarManager.getImsModeState();
            }
        }
        return 1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getImsSystemSt() {
        if (this.mCarConfig.isSupportIms()) {
            try {
                try {
                    return getIntProperty(557860871);
                } catch (Exception unused) {
                    return this.mCarManager.getImsSystemSt();
                }
            } catch (Exception e) {
                LogUtils.d(TAG, "getImsSystemSt: " + e.getMessage(), false);
                return 0;
            }
        }
        return 0;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setImsVisionCtrl(int control, int direction) {
        if (this.mCarConfig.isSupportIms()) {
            try {
                this.mCarManager.setImsVisionCtrl(control, direction);
            } catch (Exception e) {
                LogUtils.e(TAG, "setImsVisionCtrl: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getImsVisionAngleLevel() {
        if (this.mCarConfig.isSupportIms()) {
            try {
                try {
                    return getIntProperty(557860870);
                } catch (Exception unused) {
                    return this.mCarManager.getImsVisionAngleLevl();
                }
            } catch (Exception e) {
                LogUtils.d(TAG, "getImsVisionAngleLevel: " + e.getMessage(), false);
                return 0;
            }
        }
        return 0;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getImsVisionVerticalLevel() {
        if (this.mCarConfig.isSupportIms()) {
            try {
                try {
                    return getIntProperty(557860869);
                } catch (Exception unused) {
                    return this.mCarManager.getImsVisionVerticalLevel();
                }
            } catch (Exception e) {
                LogUtils.d(TAG, "getImsVisionVerticalLevel: " + e.getMessage(), false);
                return 1;
            }
        }
        return 1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getRearScreenState() {
        return Settings.System.getInt(App.getInstance().getContentResolver(), IBcmController.REAR_SCREEN_STATE, 0);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setRearLogoLightSw(boolean enable) {
        if (this.mCarConfig.isSupportRearLogoLight()) {
            try {
                this.mCarManager.setRearLogLight(enable ? 1 : 0);
                this.mDataSync.setRearLogoLightSw(enable);
            } catch (Exception e) {
                LogUtils.e(TAG, "setRearLogoLightSw: " + e.getMessage(), false);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x003b  */
    /* JADX WARN: Removed duplicated region for block: B:20:? A[RETURN, SYNTHETIC] */
    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean getRearLogoLightSw() {
        /*
            r4 = this;
            com.xiaopeng.carcontrol.config.CarBaseConfig r0 = r4.mCarConfig
            boolean r0 = r0.isSupportRearLogoLight()
            r1 = 0
            if (r0 == 0) goto L37
            r0 = 557849985(0x21401d81, float:6.509118E-19)
            int r0 = r4.getIntProperty(r0)     // Catch: java.lang.Exception -> L11
            goto L38
        L11:
            r0 = move-exception
            C extends android.car.hardware.CarEcuManager r2 = r4.mCarManager     // Catch: java.lang.Exception -> L1b
            android.car.hardware.bcm.CarBcmManager r2 = (android.car.hardware.bcm.CarBcmManager) r2     // Catch: java.lang.Exception -> L1b
            int r0 = r2.getRearLogLight()     // Catch: java.lang.Exception -> L1b
            goto L38
        L1b:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "getRearLogLight: "
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r0 = r0.getMessage()
            java.lang.StringBuilder r0 = r2.append(r0)
            java.lang.String r0 = r0.toString()
            java.lang.String r2 = "BcmController"
            com.xiaopeng.carcontrol.util.LogUtils.e(r2, r0, r1)
        L37:
            r0 = r1
        L38:
            r2 = 1
            if (r0 != r2) goto L3c
            r1 = r2
        L3c:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.carcontrol.carmanager.impl.BcmController.getRearLogoLightSw():boolean");
    }

    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    protected CarEcuManager.CarEcuEventCallback getCarEventCallback() {
        return this.mCarBcmEventCallback;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    protected void buildPropIdList(List<Integer> container, String key) {
        key.hashCode();
        char c = 65535;
        switch (key.hashCode()) {
            case -1304863042:
                if (key.equals(BusinessConstant.KEY_MAIN_LAMP)) {
                    c = 0;
                    break;
                }
                break;
            case 344830367:
                if (key.equals(BusinessConstant.KEY_MIRROR)) {
                    c = 1;
                    break;
                }
                break;
            case 500654478:
                if (key.equals("key_door")) {
                    c = 2;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                container.addAll(buildMainLampIdList());
                return;
            case 1:
                container.addAll(buildMirrorIdList());
                return;
            case 2:
                container.addAll(buildDoorIdList());
                return;
            default:
                return;
        }
    }

    private List<Integer> buildMirrorIdList() {
        ArrayList arrayList = new ArrayList();
        if (this.mCarConfig.isSupportCms()) {
            arrayList.add(560022032);
        } else {
            arrayList.add(557849660);
            arrayList.add(557849662);
            arrayList.add(557849661);
            arrayList.add(557849663);
        }
        return arrayList;
    }

    private List<Integer> buildDoorIdList() {
        ArrayList arrayList = new ArrayList();
        if (!this.mCarConfig.isSupportElcTrunk()) {
            arrayList.add(557849610);
        }
        arrayList.add(557849641);
        arrayList.add(557915161);
        if (CarBaseConfig.getInstance().isSupportSdc()) {
            arrayList.add(557849788);
            arrayList.add(557849789);
            arrayList.add(557849807);
            arrayList.add(557849808);
        }
        return arrayList;
    }

    private List<Integer> buildMainLampIdList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(557849626);
        arrayList.add(557849633);
        arrayList.add(289410561);
        arrayList.add(557915328);
        if (!(this.mCarConfig.isSupportLlu() && (this.mIsMainProcess || !BaseFeatureOption.getInstance().isSignalRegisterDynamically()))) {
            if (!this.mCarConfig.isSupportNewParkLampFmB()) {
                arrayList.add(557849757);
            } else if (this.mCarConfig.isSupportSaberLightFeedBack()) {
                arrayList.add(557849820);
            }
        }
        arrayList.add(557849602);
        return arrayList;
    }
}
