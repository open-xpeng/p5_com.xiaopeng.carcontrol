package com.xiaopeng.carcontrol.viewmodel.chassis;

import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.carmanager.CarClientWrapper;
import com.xiaopeng.carcontrol.carmanager.controller.IBcmController;
import com.xiaopeng.carcontrol.carmanager.controller.IEpsController;
import com.xiaopeng.carcontrol.carmanager.controller.IEspController;
import com.xiaopeng.carcontrol.carmanager.controller.IMsmController;
import com.xiaopeng.carcontrol.carmanager.controller.IVcuController;
import com.xiaopeng.carcontrol.carmanager.controller.IXpuController;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.helper.NotificationHelper;
import com.xiaopeng.carcontrol.provider.CarControl;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;
import com.xiaopeng.carcontrolmodule.R;
import com.xiaopeng.speech.protocol.bean.recommend.RecommendBean;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class ChassisSmartControl {
    private static final int AS_MOVE_UP_TYPE_AUTO = 2;
    private static final int AS_MOVE_UP_TYPE_MANUAL = 1;
    private static final long DELAY_SET_AVH = 200;
    private static final String TAG = "ChassisSmartControl";
    IBcmController mBcmController;
    private ChassisViewModel mChassisVm;
    private IEpsController mEpsController;
    IEspController mEspController;
    IMsmController mMsmController;
    private final Runnable mSetAvhTask = new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.chassis.-$$Lambda$Fa74xbqrudkm-iL1tdY6eWa-xsg
        @Override // java.lang.Runnable
        public final void run() {
            ChassisSmartControl.this.setAvhImpl();
        }
    };
    IVcuController mVcuController;
    IXpuController mXpuController;

    public static boolean parseAvhStatus(int status) {
        return status == 1 || status == 2;
    }

    /* loaded from: classes2.dex */
    private static class Holder {
        private static final ChassisSmartControl sInstance = new ChassisSmartControl();

        private Holder() {
        }
    }

    ChassisSmartControl() {
        CarClientWrapper carClientWrapper = CarClientWrapper.getInstance();
        if (carClientWrapper.isCarServiceConnected()) {
            this.mEspController = (IEspController) carClientWrapper.getController(CarClientWrapper.XP_ESP_SERVICE);
            this.mEpsController = (IEpsController) carClientWrapper.getController(CarClientWrapper.XP_EPS_SERVICE);
            this.mBcmController = (IBcmController) carClientWrapper.getController(CarClientWrapper.XP_BCM_SERVICE);
            this.mVcuController = (IVcuController) carClientWrapper.getController(CarClientWrapper.XP_VCU_SERVICE);
            initOtherControllers(carClientWrapper);
            this.mChassisVm = (ChassisViewModel) ViewModelManager.getInstance().getViewModelImpl(IChassisViewModel.class);
        }
    }

    void initOtherControllers(CarClientWrapper carClientWrapper) {
        this.mMsmController = (IMsmController) carClientWrapper.getController(CarClientWrapper.XP_MSM_SERVICE);
        this.mXpuController = (IXpuController) carClientWrapper.getController(CarClientWrapper.XP_XPU_SERVICE);
    }

    public static ChassisSmartControl getInstance() {
        return Holder.sInstance;
    }

    public void checkEspCondition() {
        checkEspCondition(false);
    }

    public void checkEspCondition(boolean force) {
        boolean isEvSysReady = this.mVcuController.isEvSysReady();
        if (CarBaseConfig.getInstance().isNewEspArch() && !isEvSysReady) {
            LogUtils.d(TAG, "checkEspCondition car not ready!", false);
            return;
        }
        if (CarBaseConfig.getInstance().isSupportEspSportMode()) {
            int esp = this.mEspController.getEsp();
            int steeringEps = this.mEpsController.getSteeringEps();
            boolean z = esp == 1 || esp == 2;
            boolean espSw = this.mEspController.getEspSw();
            LogUtils.d(TAG, "checkEspCondition: CurrentEspState: " + esp + ", savedEsp: " + espSw + ", CurrentEps: " + steeringEps, false);
            if ((esp == 1 || esp == 3) && steeringEps == 2) {
                this.mEspController.setEspSw(true);
                this.mEspController.setEspModeSport();
                return;
            } else if (esp == 2 && (steeringEps == 0 || steeringEps == 1)) {
                this.mEspController.setEspSw(true);
                this.mEspController.setEsp(true);
                return;
            } else if (z != espSw) {
                this.mEspController.setEsp(espSw);
                return;
            } else if (this.mEspController.getEspFault()) {
                return;
            } else {
                CarControl.PrivateControl.putBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.ESP_SW_NOTIFY, true);
                return;
            }
        }
        boolean z2 = this.mEspController.getEsp() == 4;
        boolean espSw2 = this.mEspController.getEspSw();
        LogUtils.d(TAG, "CurrentEsp: " + z2 + ", savedEsp: " + espSw2 + ", force set flag: " + force + "isEvSysReady, " + isEvSysReady, false);
        if (force || (isEvSysReady && z2 != espSw2)) {
            this.mEspController.setEsp(espSw2);
        }
    }

    public void checkAvhCondition() {
        if (isAvhPrepared(true)) {
            LogUtils.d(TAG, "Delay 200ms to set avh");
            ThreadUtils.postDelayed(0, this.mSetAvhTask, DELAY_SET_AVH);
            return;
        }
        LogUtils.d(TAG, "Remove delayed task of setting avh, because avh is not prepared");
        ThreadUtils.removeRunnable(this.mSetAvhTask);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setAvhImpl() {
        if (CarBaseConfig.getInstance().isNewEspArch()) {
            LogUtils.i(TAG, "200ms delayed, start to set avh");
            boolean avhSw = this.mEspController.getAvhSw();
            boolean parseAvhStatus = parseAvhStatus(this.mEspController.getAvh());
            LogUtils.i(TAG, "setAvh current avh mode: " + parseAvhStatus + ", sw avh mode: " + avhSw, false);
            if (avhSw != parseAvhStatus) {
                this.mEspController.setAvh(avhSw);
                return;
            }
            return;
        }
        LogUtils.i(TAG, "200ms delayed, check avh prepared again");
        if (isAvhPrepared(true)) {
            boolean avhSw2 = this.mEspController.getAvhSw();
            boolean parseAvhStatus2 = parseAvhStatus(this.mEspController.getAvh());
            LogUtils.i(TAG, "setAvh current avh mode: " + parseAvhStatus2 + ", sw avh mode: " + avhSw2, false);
            if (avhSw2 != parseAvhStatus2) {
                this.mEspController.setAvh(avhSw2);
                return;
            }
            return;
        }
        LogUtils.i(TAG, "AVH condition not prepared, do not switch AVH now", false);
    }

    public boolean isAvhPrepared(boolean checkAvhFault) {
        boolean z;
        boolean isDrvDoorClosed = this.mChassisVm.isDrvDoorClosed();
        boolean isEvSysReady = this.mVcuController.isEvSysReady();
        boolean isDrvBeltBuckled = this.mChassisVm.isDrvBeltBuckled();
        LogUtils.d(TAG, "isDoorClose:" + isDrvDoorClosed + ", isEvSysReady:" + isEvSysReady + ", isBeltBuckled:" + isDrvBeltBuckled);
        if (checkAvhFault) {
            z = this.mEspController.getAvhFault();
            LogUtils.d(TAG, "isAvhFault: " + z);
        } else {
            z = false;
        }
        return !z && isDrvDoorClosed && isEvSysReady && isDrvBeltBuckled;
    }

    public void onSmartAsSaveResult(String param) {
        int i;
        LogUtils.d(TAG, "onSmartAsSaveResult:" + param);
        try {
            i = new JSONObject(param).optInt(RecommendBean.SHOW_TIME_RESULT);
        } catch (Exception e) {
            e.printStackTrace();
            i = 0;
        }
        NotificationHelper.getInstance().showToast(i == 1 ? R.string.as_height_save_success_tips : R.string.as_height_save_error_tips, true);
        this.mChassisVm.updateAsSaveResult(i == 1);
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x00b0 A[Catch: Exception -> 0x00d2, TryCatch #0 {Exception -> 0x00d2, blocks: (B:13:0x003a, B:15:0x0047, B:17:0x004f, B:21:0x0058, B:23:0x0060, B:25:0x0066, B:34:0x00b0, B:36:0x00b8, B:37:0x00c3, B:38:0x00c8), top: B:43:0x003a }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onSmartAsMoveUp(java.lang.String r8) {
        /*
            r7 = this;
            boolean r0 = android.text.TextUtils.isEmpty(r8)
            if (r0 == 0) goto L7
            return
        L7:
            com.xiaopeng.carcontrol.config.CarBaseConfig r0 = com.xiaopeng.carcontrol.config.CarBaseConfig.getInstance()
            boolean r0 = r0.isSupportAirSuspension()
            if (r0 != 0) goto L12
            return
        L12:
            com.xiaopeng.carcontrol.carmanager.controller.IVcuController r0 = r7.mVcuController
            int r0 = r0.getGearLevel()
            r1 = 1
            java.lang.String r2 = "ChassisSmartControl"
            r3 = 0
            if (r0 == r1) goto L24
            java.lang.String r8 = "Not in drive gear, do not handle adjust AS height request"
            com.xiaopeng.carcontrol.util.LogUtils.w(r2, r8, r3)
            return
        L24:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r4 = "onSmartAsMoveUp:"
            java.lang.StringBuilder r0 = r0.append(r4)
            java.lang.StringBuilder r0 = r0.append(r8)
            java.lang.String r0 = r0.toString()
            com.xiaopeng.carcontrol.util.LogUtils.d(r2, r0)
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch: java.lang.Exception -> Ld2
            r0.<init>(r8)     // Catch: java.lang.Exception -> Ld2
            java.lang.String r8 = "type"
            int r8 = r0.optInt(r8)     // Catch: java.lang.Exception -> Ld2
            if (r8 != r1) goto L55
            com.xiaopeng.carcontrol.carmanager.controller.IBcmController r4 = r7.mBcmController     // Catch: java.lang.Exception -> Ld2
            boolean r4 = r4.getAsLocationControlSw()     // Catch: java.lang.Exception -> Ld2
            if (r4 != 0) goto L55
            java.lang.String r8 = "Do not open As Location Manual Control Sw and Return"
            com.xiaopeng.carcontrol.util.LogUtils.w(r2, r8, r3)     // Catch: java.lang.Exception -> Ld2
            return
        L55:
            r4 = 2
            if (r8 != r4) goto L66
            com.xiaopeng.carcontrol.carmanager.controller.IBcmController r8 = r7.mBcmController     // Catch: java.lang.Exception -> Ld2
            boolean r8 = r8.getAsLocationSw()     // Catch: java.lang.Exception -> Ld2
            if (r8 != 0) goto L66
            java.lang.String r8 = "Do not open As Location Auto Control Sw and Return"
            com.xiaopeng.carcontrol.util.LogUtils.w(r2, r8, r3)     // Catch: java.lang.Exception -> Ld2
            return
        L66:
            java.lang.String r8 = "level"
            int r8 = r0.optInt(r8)     // Catch: java.lang.Exception -> Ld2
            com.xiaopeng.carcontrol.carmanager.controller.IXpuController r0 = r7.mXpuController     // Catch: java.lang.Exception -> Ld2
            int r0 = r0.getAsTargetMinHeightRequest()     // Catch: java.lang.Exception -> Ld2
            com.xiaopeng.carcontrol.carmanager.controller.IXpuController r4 = r7.mXpuController     // Catch: java.lang.Exception -> Ld2
            int r4 = r4.getAsTargetMaxHeightRequest()     // Catch: java.lang.Exception -> Ld2
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> Ld2
            r5.<init>()     // Catch: java.lang.Exception -> Ld2
            java.lang.String r6 = "onSmartAsMoveUp level: "
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch: java.lang.Exception -> Ld2
            java.lang.StringBuilder r5 = r5.append(r8)     // Catch: java.lang.Exception -> Ld2
            java.lang.String r6 = "mAsMinTarget: "
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch: java.lang.Exception -> Ld2
            java.lang.StringBuilder r5 = r5.append(r0)     // Catch: java.lang.Exception -> Ld2
            java.lang.String r6 = ", mAsMaxTarget: "
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch: java.lang.Exception -> Ld2
            java.lang.StringBuilder r5 = r5.append(r4)     // Catch: java.lang.Exception -> Ld2
            java.lang.String r5 = r5.toString()     // Catch: java.lang.Exception -> Ld2
            com.xiaopeng.carcontrol.util.LogUtils.d(r2, r5, r3)     // Catch: java.lang.Exception -> Ld2
            if (r0 != 0) goto La6
            if (r4 == 0) goto Lab
        La6:
            if (r8 < r4) goto Lad
            if (r8 <= r0) goto Lab
            goto Lad
        Lab:
            r0 = r1
            goto Lae
        Lad:
            r0 = r3
        Lae:
            if (r0 == 0) goto Lc8
            com.xiaopeng.carcontrol.carmanager.controller.IBcmController r0 = r7.mBcmController     // Catch: java.lang.Exception -> Ld2
            int r0 = r0.getAirSuspensionHeight()     // Catch: java.lang.Exception -> Ld2
            if (r0 == r8) goto Lc3
            com.xiaopeng.carcontrol.carmanager.controller.IBcmController r0 = r7.mBcmController     // Catch: java.lang.Exception -> Ld2
            r0.setAirSuspensionHeight(r8, r3)     // Catch: java.lang.Exception -> Ld2
            com.xiaopeng.carcontrol.carmanager.controller.IBcmController r8 = r7.mBcmController     // Catch: java.lang.Exception -> Ld2
            r8.setCustomerModeFlag(r1)     // Catch: java.lang.Exception -> Ld2
            goto Lc8
        Lc3:
            java.lang.String r8 = "Current As equals to target height"
            com.xiaopeng.carcontrol.util.LogUtils.i(r2, r8, r3)     // Catch: java.lang.Exception -> Ld2
        Lc8:
            com.xiaopeng.carcontrol.helper.NotificationHelper r8 = com.xiaopeng.carcontrol.helper.NotificationHelper.getInstance()     // Catch: java.lang.Exception -> Ld2
            int r0 = com.xiaopeng.carcontrolmodule.R.string.as_height_adjust_tips     // Catch: java.lang.Exception -> Ld2
            r8.showToast(r0, r3)     // Catch: java.lang.Exception -> Ld2
            goto Ld6
        Ld2:
            r8 = move-exception
            r8.printStackTrace()
        Ld6:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.carcontrol.viewmodel.chassis.ChassisSmartControl.onSmartAsMoveUp(java.lang.String):void");
    }

    public void onSmartAsMoveDown(String param) {
        if (CarBaseConfig.getInstance().isSupportAirSuspension()) {
            LogUtils.d(TAG, "onSmartAsMoveDown");
            if (CarBaseConfig.getInstance().isSupportXSport() && (1 == this.mVcuController.getXSportDrivingMode() || 3 == this.mVcuController.getXSportDrivingMode())) {
                LogUtils.d(TAG, "in XSportMode, do not Resume As Height", false);
            } else {
                this.mChassisVm.resumeAsHeight();
            }
        }
    }

    public void setAvhSw(boolean enable) {
        this.mChassisVm.setAvhSw(enable);
    }
}
