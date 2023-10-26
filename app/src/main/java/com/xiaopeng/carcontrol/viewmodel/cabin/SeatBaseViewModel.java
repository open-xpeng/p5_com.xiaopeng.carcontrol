package com.xiaopeng.carcontrol.viewmodel.cabin;

import android.os.Handler;
import android.provider.Settings;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.carmanager.CarClientWrapper;
import com.xiaopeng.carcontrol.carmanager.controller.IBcmController;
import com.xiaopeng.carcontrol.carmanager.controller.IMcuController;
import com.xiaopeng.carcontrol.carmanager.controller.IMsmController;
import com.xiaopeng.carcontrol.carmanager.controller.IScenarioController;
import com.xiaopeng.carcontrol.carmanager.controller.IScuController;
import com.xiaopeng.carcontrol.carmanager.controller.IVcuController;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import com.xiaopeng.carcontrol.helper.NotificationHelper;
import com.xiaopeng.carcontrol.provider.CarControl;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.carcontrol.viewmodel.space.VipCapsuleSeatControl;
import com.xiaopeng.carcontrol.viewmodel.space.VipDrvSeatControl;
import com.xiaopeng.carcontrol.viewmodel.space.VipPsnSeatControl;
import com.xiaopeng.carcontrol.viewmodel.space.VipSeatControl;
import com.xiaopeng.carcontrol.viewmodel.space.VipSeatStatus;
import com.xiaopeng.carcontrolmodule.R;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.app.XDialogInterface;

/* loaded from: classes2.dex */
public abstract class SeatBaseViewModel implements ISeatViewModel, IMsmController.Callback, IMcuController.Callback {
    static final int SEAT_MEMORY_MAX_COUNT = 3;
    IBcmController mBcmController;
    protected volatile long mDrvDoorClosedTime;
    String mFollowedVehicleLostConfig;
    Handler mHandler;
    private boolean mIsDrvDoorOpen;
    private boolean mIsPsnDoorOpen;
    IMcuController mMcuController;
    IMsmController mMsmController;
    protected volatile long mPsnDoorClosedTime;
    private XDialog mPsnSrsConfirmDialog;
    IScenarioController mScenarioController;
    IScuController mScuController;
    IVcuController mVcuController;
    protected final String TAG = getClass().getSimpleName();
    boolean mIsSeatUiCtrlResume = false;
    Runnable mPabRunnable = new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.cabin.-$$Lambda$SeatBaseViewModel$WUqVxdIahp5SmBDGS036YEm0a30
        @Override // java.lang.Runnable
        public final void run() {
            SeatBaseViewModel.this.lambda$new$0$SeatBaseViewModel();
        }
    };
    final IBcmController.Callback mBcmCallback = new IBcmController.Callback() { // from class: com.xiaopeng.carcontrol.viewmodel.cabin.SeatBaseViewModel.1
        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onDrvSeatOccupiedChanged(boolean occupied) {
            SeatBaseViewModel.this.handleDrvSeatOccupied(occupied);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onBackBeltWaringChanged(boolean status) {
            SeatBaseViewModel.this.handleBackBeltWaringChanged(status);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onWelcomeModeChanged(boolean enabled) {
            SeatBaseViewModel.this.handleWelcomeModeChanged(enabled);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onRearSeatWelcomeModeChanged(boolean enabled) {
            SeatBaseViewModel.this.handleRearSeatWelcomeModeChanged(enabled);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onEsbChanged(boolean enabled) {
            SeatBaseViewModel.this.lambda$setEsbMode$1$SeatBaseViewModel(enabled);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onDoorStateChanged(int[] doorState) {
            SeatBaseViewModel.this.handleDoorStateChanged(doorState);
        }
    };

    protected abstract void handleBackBeltWaringChanged(boolean status);

    protected abstract void handleDrvLumbControlEnableChanged(boolean enable);

    protected abstract void handleDrvSeatOccupied(boolean occupied);

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: handleEsbChanged */
    public abstract void lambda$setEsbMode$1$SeatBaseViewModel(boolean enabled);

    protected abstract void handleEsbConfigChanged(String configMode);

    protected abstract void handlePsnLumbControlEnableChanged(boolean enable);

    protected abstract void handlePsnSrsEnableChanged(boolean enable);

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: handlePsnSrsNoResponse */
    public abstract void lambda$new$0$SeatBaseViewModel();

    protected abstract void handlePsnWelcomeModeChanged(boolean enabled);

    protected abstract void handleRearSeatWelcomeModeChanged(boolean enabled);

    protected abstract void handleWelcomeModeChanged(boolean enabled);

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public boolean haveDefaultSeat(int pos) {
        return true;
    }

    abstract boolean isSupportEsbConfig();

    /* JADX INFO: Access modifiers changed from: package-private */
    public SeatBaseViewModel() {
        this.mFollowedVehicleLostConfig = "2";
        CarClientWrapper carClientWrapper = CarClientWrapper.getInstance();
        this.mMsmController = (IMsmController) carClientWrapper.getController(CarClientWrapper.XP_MSM_SERVICE);
        this.mBcmController = (IBcmController) carClientWrapper.getController(CarClientWrapper.XP_BCM_SERVICE);
        this.mMcuController = (IMcuController) carClientWrapper.getController(CarClientWrapper.XP_MCU_SERVICE);
        this.mVcuController = (IVcuController) carClientWrapper.getController(CarClientWrapper.XP_VCU_SERVICE);
        this.mScenarioController = (IScenarioController) CarClientWrapper.getInstance().getController(CarClientWrapper.XP_USER_SCENARIO_SERVICE);
        if (isSupportEsbConfig()) {
            this.mScuController = (IScuController) carClientWrapper.getController(CarClientWrapper.XP_SCU_SERVICE);
            this.mFollowedVehicleLostConfig = this.mMsmController.readFollowedVehicleLostConfig();
        }
        this.mScenarioController = (IScenarioController) carClientWrapper.getController(CarClientWrapper.XP_USER_SCENARIO_SERVICE);
        this.mHandler = ThreadUtils.getHandler(0);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public boolean isWelcomeModeEnabled() {
        if (CarBaseConfig.getInstance().isSupportMcuSeatWelcome()) {
            return this.mMcuController.getSeatWelcomeMode();
        }
        return this.mBcmController.getSeatWelcomeMode();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setWelcomeMode(boolean enable) {
        LogUtils.i(this.TAG, "setDrvWelcomeMode: " + enable, false);
        if (CarBaseConfig.getInstance().isSupportMcuSeatWelcome()) {
            this.mMcuController.setSeatWelcomeMode(enable);
        } else {
            this.mBcmController.setSeatWelcomeMode(enable);
        }
        if (CarBaseConfig.getInstance().isSupportAirSuspension() && BaseFeatureOption.getInstance().isSupportAsWelcomeWithSeat()) {
            setAsWelcomeMode(enable, isPsnWelcomeEnabled());
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setDrvWelcomeModeActiveSt(boolean active) {
        this.mMsmController.setDrvWelcomeModeActiveSt(active);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public boolean isPsnWelcomeEnabled() {
        return this.mMsmController.getPsnSeatWelcomeMode();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setPsnWelcomeMode(boolean enable) {
        LogUtils.i(this.TAG, "setPsnWelcomeMode: " + enable, false);
        this.mMsmController.setPsnSeatWelcomeMode(enable);
        if (CarBaseConfig.getInstance().isSupportAirSuspension() && BaseFeatureOption.getInstance().isSupportAsWelcomeWithSeat()) {
            setAsWelcomeMode(isWelcomeModeEnabled(), enable);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setPsnWelcomeModeActiveSt(boolean active) {
        this.mMsmController.setPsnWelcomeModeActiveSt(active);
    }

    private void setAsWelcomeMode(boolean drvWelcomeMode, boolean psnWelcomeMode) {
        boolean z = drvWelcomeMode || psnWelcomeMode;
        LogUtils.i(this.TAG, "setAsWelcomeMode: " + z, false);
        this.mBcmController.setAsWelcomeMode(z);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public boolean isRearSeatWelcomeEnabled() {
        return this.mBcmController.getRearSeatWelcomeMode();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setRearSeatWelcomeMode(boolean enable) {
        this.mBcmController.setRearSeatWelcomeMode(enable, true);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setSeatUiCtrlResume(boolean resume) {
        this.mIsSeatUiCtrlResume = resume;
    }

    public boolean isDrvSeatOccupied() {
        return this.mBcmController.isDrvSeatOccupied();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public boolean isDrvBeltUnbuckled() {
        return this.mMsmController.getDrvBeltStatus() == 1;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public boolean isPsnSeatOccupied() {
        return this.mMsmController.isPsnSeatOccupied();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public boolean isRlSeatOccupied() {
        return this.mMsmController.isRlSeatOccupied();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public boolean isRmSeatOccupied() {
        return this.mMsmController.isRmSeatOccupied();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public boolean isRrSeatOccupied() {
        return this.mMsmController.isRrSeatOccupied();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public boolean isPsnBeltUnbuckled() {
        return this.mMsmController.getPsnBeltStatus() == 1;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setEsbMode(final boolean enable) {
        if (isSupportEsbConfig()) {
            if ("2".equals(this.mFollowedVehicleLostConfig)) {
                LogUtils.i(this.TAG, "Use new config, force set electric belt sw to true, and set SCU OTA tag with: " + (enable ? 2 : 0));
                this.mMsmController.setEsbEnable(true, enable);
                this.mScuController.setScuOtaTagStatus(enable ? 2 : 0);
            } else {
                LogUtils.i(this.TAG, "Use old config, set electric belt sw with param: " + enable + ", and set SCU OTA tag with: " + (enable ? 3 : 1));
                this.mMsmController.setEsbEnable(enable);
                this.mScuController.setScuOtaTagStatus(enable ? 3 : 1);
            }
        } else {
            this.mMsmController.setEsbEnable(enable);
        }
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.cabin.-$$Lambda$SeatBaseViewModel$IIGvh3_MPkj2P2aBzMR7WewkW60
            @Override // java.lang.Runnable
            public final void run() {
                SeatBaseViewModel.this.lambda$setEsbMode$1$SeatBaseViewModel(enable);
            }
        });
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public boolean getEsbMode() {
        if ("2".equals(this.mFollowedVehicleLostConfig)) {
            return this.mMsmController.getEsbModeSp();
        }
        return this.mMsmController.getEsbEnable();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public String getEsbConfigMode() {
        return this.mFollowedVehicleLostConfig;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setBackBeltSw(boolean on) {
        this.mMsmController.setBackBeltSw(on);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public boolean getBackBeltSw() {
        return this.mMsmController.getBackBeltWStatus();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setPsnSrsEnable(boolean enable) {
        this.mMsmController.setPsnSrsEnable(enable);
        this.mHandler.postDelayed(this.mPabRunnable, 1000L);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public boolean getPsnSrsEnable() {
        return this.mMsmController.getPsnSrsEnable();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void showPsnSrsCloseDialog() {
        LogUtils.d(this.TAG, "showMicrophoneConfirmDialog");
        if (this.mPsnSrsConfirmDialog == null) {
            XDialog xDialog = new XDialog(App.getInstance(), R.style.XDialogView);
            this.mPsnSrsConfirmDialog = xDialog;
            xDialog.setTitle(R.string.psn_srs_tv).setMessage(R.string.psn_srs_dialog_content).setPositiveButton(R.string.btn_confirm, new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.viewmodel.cabin.-$$Lambda$SeatBaseViewModel$NupB9NKcrNsIU0BsbuFZ7C0NOaY
                @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                public final void onClick(XDialog xDialog2, int i) {
                    SeatBaseViewModel.this.lambda$showPsnSrsCloseDialog$2$SeatBaseViewModel(xDialog2, i);
                }
            }).setNegativeButton(R.string.btn_cancel);
            this.mPsnSrsConfirmDialog.setSystemDialog(2048);
        }
        XDialog xDialog2 = this.mPsnSrsConfirmDialog;
        if (xDialog2 != null) {
            xDialog2.show();
        }
    }

    public /* synthetic */ void lambda$showPsnSrsCloseDialog$2$SeatBaseViewModel(XDialog dialog, int i) {
        setPsnSrsEnable(false);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController.Callback
    public void onPsnSrsEnableChanged(boolean enable) {
        handlePsnSrsEnableChanged(enable);
        Settings.System.putInt(App.getInstance().getContentResolver(), CarControl.PrivateControl.PSN_SRS_ENABLE, enable ? 1 : 0);
        this.mHandler.removeCallbacks(this.mPabRunnable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setDSeatHorzPos(int pos) {
        this.mMsmController.setDSeatHorzPos(pos);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public int getDSeatHorzPos() {
        return this.mMsmController.getDSeatHorzPos();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setDSeatVerPos(int pos) {
        this.mMsmController.setDSeatVerPos(pos);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public int getDSeatVerPos() {
        return this.mMsmController.getDSeatVerPos();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setDSeatTiltPos(int pos) {
        this.mMsmController.setDSeatTiltPos(pos);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public int getDSeatTiltPos() {
        return this.mMsmController.getDSeatTiltPos();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setDSeatLegPos(int pos) {
        this.mMsmController.setDSeatLegPos(pos);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public int getDSeatLegPos() {
        return this.mMsmController.getDSeatLegPos();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setDSeatCushionPos(int pos) {
        this.mMsmController.setDSeatCushionPos(pos);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public int getDSeatCushionPos() {
        return this.mMsmController.getDSeatCushionPos();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setPSeatHorzPos(int pos) {
        try {
            this.mMsmController.setPSeatHorzPos(pos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public int getPSeatHorzPos() {
        return this.mMsmController.getPSeatHorzPos();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setPSeatVerPos(int pos) {
        this.mMsmController.setPSeatVerPos(pos);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public int getPSeatVerPos() {
        return this.mMsmController.getPSeatVerPos();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setPSeatTiltPos(int pos) {
        try {
            this.mMsmController.setPSeatTiltPos(pos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public int getPSeatTiltPos() {
        return this.mMsmController.getPSeatTiltPos();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setPSeatLegPos(int pos) {
        this.mMsmController.setPSeatLegPos(pos);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public int getPSeatLegPos() {
        return this.mMsmController.getPSeatLegPos();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setSeatMenuNotShowKeepTime(boolean drv, long keepTime) {
        this.mMsmController.setSeatMenuNotShowKeepTime(drv, keepTime);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public int getRLSeatTiltPos() {
        return this.mMsmController.getRLSeatTiltPos();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public int getRLSeatHorPos() {
        return this.mMsmController.getRLSeatHorPos();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public int getRLSeatLegVerPos() {
        return this.mMsmController.getRLSeatLegVerPos();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public int getRLSeatLegHorPos() {
        return this.mMsmController.getRLSeatLegHorPos();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public int getRRSeatTiltPos() {
        return this.mMsmController.getRRSeatTiltPos();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public int getRRSeatLegVerPos() {
        return this.mMsmController.getRRSeatLegVerPos();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public int getRRSeatLegHorPos() {
        return this.mMsmController.getRRSeatLegHorPos();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public int getRRSeatHorPos() {
        return this.mMsmController.getRRSeatHorPos();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setRLSeatLegPos(int pos) {
        this.mMsmController.setRLSeatLegPos(pos);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public int getRLSeatLegPos() {
        return this.mMsmController.getRLSeatTiltPos();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setRRSeatLegPos(int pos) {
        this.mMsmController.setRRSeatLegPos(pos);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public int getRRSeatLegPos() {
        return this.mMsmController.getRRSeatTiltPos();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setRLSeatTiltPos(int pos) {
        this.mMsmController.setRLSeatTiltPos(pos);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setRLSeatHorPos(int pos) {
        this.mMsmController.setRLSeatHorPos(pos);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setRLSeatLegVerPos(int pos) {
        this.mMsmController.setRLSeatLegVerPos(pos);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setRLSeatLegHorPos(int pos) {
        this.mMsmController.setRLSeatLegHorPos(pos);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setRLSeatHeadVerPos(int pos) {
        this.mMsmController.setRLSeatHeadVerPos(pos);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public int getRLSeatHeadVerPos() {
        return this.mMsmController.getRLSeatHeadVerPos();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setRLSeatHeadHorPos(int pos) {
        this.mMsmController.setRLSeatHeadHorPos(pos);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public int getRLSeatHeadHorPos() {
        return this.mMsmController.getRLSeatHeadHorPos();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setRRSeatTiltPos(int pos) {
        this.mMsmController.setRRSeatTiltPos(pos);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setRRSeatHorPos(int pos) {
        this.mMsmController.setRRSeatHorPos(pos);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setRRSeatLegVerPos(int pos) {
        this.mMsmController.setRRSeatLegVerPos(pos);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setRRSeatLegHorPos(int pos) {
        this.mMsmController.setRRSeatLegHorPos(pos);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setRRSeatHeadVerPos(int pos) {
        this.mMsmController.setRRSeatHeadVerPos(pos);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public int getRRSeatHeadVerPos() {
        return this.mMsmController.getRRSeatHeadVerPos();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setRRSeatHeadHorPos(int pos) {
        this.mMsmController.setRRSeatHeadHorPos(pos);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public int getRRSeatHeadHorPos() {
        return this.mMsmController.getRRSeatHeadHorPos();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setRLSeatFold(boolean fold) {
        this.mMsmController.setRLSeatFold(fold);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setRLSeatStopMove() {
        this.mMsmController.setRLSeatStopMove();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setRRSeatFold(boolean fold) {
        this.mMsmController.setRRSeatFold(fold);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setRRSeatStopMove() {
        this.mMsmController.setRRSeatStopMove();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setDrvSeatLayFlat() {
        this.mMsmController.setDrvSeatLayFlat();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setPsnSeatLayFlat() {
        this.mMsmController.setPsnSeatLayFlat();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setDSeatHorzMove(int control, int direction) {
        this.mMsmController.setDSeatHorzMove(control, direction);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setDSeatVerMove(int control, int direction) {
        this.mMsmController.setDSeatVerMove(control, direction);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setDSeatTiltMove(int control, int direction) {
        this.mMsmController.setDSeatTiltMove(control, direction);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setDSeatLegMove(int control, int direction) {
        this.mMsmController.setDSeatLegMove(control, direction);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setDSeatCushionMove(int control, int direction) {
        this.mMsmController.setDSeatCushionMove(control, direction);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setDSeatLumbHorzMove(int control, int direction) {
        this.mMsmController.setDSeatLumbHorzMove(control, direction);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setDSeatLumbVerMove(int control, int direction) {
        this.mMsmController.setDSeatLumbVerMove(control, direction);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setPSeatHorzMove(int control, int direction) {
        this.mMsmController.setPSeatHorzMove(control, direction);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setPSeatVerMove(int control, int direction) {
        this.mMsmController.setPSeatVerMove(control, direction);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setPSeatTiltMove(int control, int direction) {
        this.mMsmController.setPSeatTiltMove(control, direction);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setPSeatLegMove(int control, int direction) {
        this.mMsmController.setPSeatLegMove(control, direction);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setPSeatLumbHorzMove(int control, int direction) {
        this.mMsmController.setPSeatLumbHorzMove(control, direction);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setPSeatLumbVerMove(int control, int direction) {
        this.mMsmController.setPSeatLumbVerMove(control, direction);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setRLSeatTiltMove(int control, int direction) {
        this.mMsmController.setRLSeatTiltMove(control, direction);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setRLSeaHorzMove(int control, int direction) {
        this.mMsmController.setRLSeaHorzMove(control, direction);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setRLSeatLumbHorzMove(int control, int direction) {
        this.mMsmController.setRLSeatLumbHorzMove(control, direction);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setRLSeatLumbVerMove(int control, int direction) {
        this.mMsmController.setRLSeatLumbVerMove(control, direction);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setRLSeatLegMove(int control, int direction) {
        this.mMsmController.setRLSeatLegMove(control, direction);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setRLSeatLegVerMove(int control, int direction) {
        this.mMsmController.setRLSeatLegVerMove(control, direction);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setRLSeatHeadRestHorzMove(int control, int direction) {
        this.mMsmController.setRLSeatHeadRestHorzMove(control, direction);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setRLSeatHeadRestVerMove(int control, int direction) {
        this.mMsmController.setRLSeatHeadRestVerMove(control, direction);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setRRSeatTiltMove(int control, int direction) {
        this.mMsmController.setRRSeatTiltMove(control, direction);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setRRSeaHorzMove(int control, int direction) {
        this.mMsmController.setRRSeaHorzMove(control, direction);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setRRSeatLegMove(int control, int direction) {
        this.mMsmController.setRRSeatLegMove(control, direction);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setRRSeatLumbHorzMove(int control, int direction) {
        this.mMsmController.setRRSeatLumbHorzMove(control, direction);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setRRSeatLumbVerMove(int control, int direction) {
        this.mMsmController.setRRSeatLumbVerMove(control, direction);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setRRSeatLegVerMove(int control, int direction) {
        this.mMsmController.setRRSeatLegVerMove(control, direction);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setRRSeatHeadRestHorzMove(int control, int direction) {
        this.mMsmController.setRRSeatHeadRestHorzMove(control, direction);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setRRSeatHeadRestVerMove(int control, int direction) {
        this.mMsmController.setRRSeatHeadRestVerMove(control, direction);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setTrdRowLeftSeatTiltMove(int control, int direction) {
        this.mMsmController.setTrdRowLeftSeatTiltMove(control, direction);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setTrdRowRightSeatTiltMove(int control, int direction) {
        this.mMsmController.setTrdRowRightSeatTiltMove(control, direction);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setTrdRowLeftSeatHeadVerMove(int control, int direction) {
        this.mMsmController.setTrdRowLeftSeatHeadVerMove(control, direction);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setTrdRowRightSeatHeadVerMove(int control, int direction) {
        this.mMsmController.setTrdRowRightSeatHeadVerMove(control, direction);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setTrdRowMidSeatHeadVerMove(int control, int direction) {
        this.mMsmController.setTrdRowMidSeatHeadVerMove(control, direction);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public int getDrvSeatPosIdx() {
        return this.mMsmController.getDrvSeatPosIdx();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setDriverAllPositionsToMcu() {
        if (isWelcomeModeEnabled()) {
            IMsmController iMsmController = this.mMsmController;
            int[] drvSeatPos = iMsmController.getDrvSeatPos(iMsmController.getDrvSeatPosIdx());
            LogUtils.d(this.TAG, "setDriverAllPositionsToMcu index:" + this.mMsmController.getDrvSeatPosIdx());
            if (drvSeatPos == null || drvSeatPos.length < 4) {
                return;
            }
            LogUtils.d(this.TAG, "setDriverAllPositionsToMcu saved seat position hor:" + drvSeatPos[0] + ",ver:" + drvSeatPos[1] + ",tilt:" + drvSeatPos[2] + ",leg:" + drvSeatPos[3] + ",cushion:" + drvSeatPos[4]);
            this.mMsmController.setDriverAllPositionsToMcu(drvSeatPos[0], drvSeatPos[1], drvSeatPos[2], drvSeatPos[3], drvSeatPos[4]);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setDriverAllPositionsToDomain() {
        if (isWelcomeModeEnabled()) {
            IMsmController iMsmController = this.mMsmController;
            int[] drvSeatPos = iMsmController.getDrvSeatPos(iMsmController.getDrvSeatPosIdx());
            LogUtils.d(this.TAG, "setDriverAllPositionsToDomain index:" + this.mMsmController.getDrvSeatPosIdx());
            if (drvSeatPos == null || drvSeatPos.length < 4) {
                return;
            }
            LogUtils.d(this.TAG, "setDriverAllPositionsToDomain request:1, saved seat position hor:" + drvSeatPos[0] + ",ver:" + drvSeatPos[1] + ",tilt:" + drvSeatPos[2] + ",leg:" + drvSeatPos[3] + ",cushion:" + drvSeatPos[4]);
            this.mMsmController.setDriverAllPositions(1, drvSeatPos[0], drvSeatPos[1], drvSeatPos[2], drvSeatPos[3], drvSeatPos[4]);
        }
    }

    public boolean isDrvDoorOpened() {
        return this.mBcmController.getDoorState(0) == 1;
    }

    public boolean isPsnDoorOpened() {
        return this.mBcmController.getDoorState(1) == 1;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getStallState() {
        return this.mVcuController.getGearLevel();
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMcuController.Callback
    public void onWelcomeModeChanged(boolean enabled) {
        handleWelcomeModeChanged(enabled);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController.Callback
    public void onPsnWelcomeModeChanged(boolean enabled) {
        handlePsnWelcomeModeChanged(enabled);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController.Callback
    public void onEsbChanged(boolean enabled) {
        lambda$setEsbMode$1$SeatBaseViewModel(enabled);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController.Callback
    public void onDrvSeatLumberControlEnableChanged(boolean enable) {
        handleDrvLumbControlEnableChanged(enable);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController.Callback
    public void onPsnSeatLumberControlEnableChanged(boolean enable) {
        handlePsnLumbControlEnableChanged(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public float getCarSpeed() {
        return this.mVcuController.getCarSpeed();
    }

    protected void handleDoorStateChanged(int[] state) {
        if (state == null || state.length < 4) {
            return;
        }
        if (state[0] == 1) {
            this.mIsDrvDoorOpen = true;
        } else if (this.mIsDrvDoorOpen) {
            this.mIsDrvDoorOpen = false;
            this.mDrvDoorClosedTime = System.currentTimeMillis();
        }
        if (state[1] == 1) {
            this.mIsPsnDoorOpen = true;
        } else if (this.mIsPsnDoorOpen) {
            this.mIsPsnDoorOpen = false;
            this.mPsnDoorClosedTime = System.currentTimeMillis();
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public boolean isSupportMsm() {
        return CarBaseConfig.getInstance().isSupportMsmD();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public boolean isSupportMSMP() {
        return CarBaseConfig.getInstance().isSupportMsmP();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void onFollowedVehicleLostConfigChanged(boolean forceUpdate) {
        if (isSupportEsbConfig()) {
            String readFollowedVehicleLostConfig = this.mMsmController.readFollowedVehicleLostConfig();
            LogUtils.i(this.TAG, "onFollowedVehicleLostConfigChanged to " + readFollowedVehicleLostConfig);
            if (readFollowedVehicleLostConfig != null) {
                if (forceUpdate || !readFollowedVehicleLostConfig.equals(this.mFollowedVehicleLostConfig)) {
                    this.mFollowedVehicleLostConfig = readFollowedVehicleLostConfig;
                    if (App.isMainProcess()) {
                        boolean esbMode = getEsbMode();
                        LogUtils.i(this.TAG, "Resume saved esb mode: " + esbMode);
                        setEsbMode(esbMode);
                    }
                    handleEsbConfigChanged(this.mFollowedVehicleLostConfig);
                }
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setFollowedVehicleLostConfig(String config) {
        this.mMsmController.setFollowedVehicleLostConfig(config);
    }

    protected boolean checkPsnChairMovable() {
        if (isPsnDoorOpened()) {
            NotificationHelper.getInstance().showToast(R.string.smart_chair_door_error);
            return false;
        } else if (isPsnSeatOccupied()) {
            return true;
        } else {
            NotificationHelper.getInstance().showToast(R.string.smart_chair_psn_error);
            return false;
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public boolean checkChairMovable() {
        if (getCarSpeed() >= 3.0f) {
            NotificationHelper.getInstance().showToast(R.string.smart_chair_speed_error);
            return false;
        } else if (isDrvDoorOpened()) {
            NotificationHelper.getInstance().showToast(R.string.smart_chair_door_error);
            return false;
        } else if (isDrvSeatOccupied()) {
            return true;
        } else {
            NotificationHelper.getInstance().showToast(R.string.smart_chair_driver_error);
            return false;
        }
    }

    protected boolean checkChairMovable(boolean ignoreDrvDoorState) {
        return checkChairMovable(ignoreDrvDoorState, false);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean checkChairMovable(boolean ignoreDrvDoorState, boolean ignoreDrvState) {
        boolean z = !isDrvDoorOpened();
        boolean isDrvSeatOccupied = this.mBcmController.isDrvSeatOccupied();
        boolean z2 = getCarSpeed() <= 3.0f;
        LogUtils.i(this.TAG, "checkSeatMovable, doorState = " + z + ", driverState = " + isDrvSeatOccupied + ", speedState = " + z2 + ", ignoreDrvDoorState: " + ignoreDrvDoorState + ", ignoreDrvState: " + ignoreDrvState, false);
        return (ignoreDrvDoorState || z) && (ignoreDrvState || isDrvSeatOccupied) && z2;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public boolean isLocalIgOn() {
        return this.mMcuController.getIgStatusFromMcu() == 1;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void enterMeditationState(int tiltPos) {
        if (Math.abs(tiltPos - this.mMsmController.getDSeatTiltPos()) < 2 || tiltPos == -1 || !checkChairMovable(false)) {
            return;
        }
        LogUtils.i(this.TAG, "Start to move seat tilt position");
        this.mMsmController.setDSeatTiltPos(tiltPos);
        this.mMsmController.setDrvWelcomeModeActiveSt(false);
        this.mMsmController.setPsnWelcomeModeActiveSt(false);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public boolean isDrvHeadrestNormal() {
        return this.mMsmController.isDrvHeadrestNormal();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public boolean isPsnHeadrestNormal() {
        return this.mMsmController.isPsnHeadrestNormal();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void stopDrvSeatMove() {
        this.mMsmController.stopDrvSeatMove();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void stopPsnSeatMove() {
        this.mMsmController.stopPsnSeatMove();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void stopCapsuleSeatMove() {
        this.mMsmController.stopDrvSeatMove();
        this.mMsmController.stopPsnSeatMove();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public boolean isDrvSeatLieFlat() {
        LogUtils.i(this.TAG, "isDrvSeatLieFlat seatHor:" + getDSeatHorzPos() + ",seatVer:" + getDSeatVerPos() + ",seatTilt:" + getDSeatTiltPos());
        return BaseFeatureOption.getInstance().isEcuDoSeatLayFlat() ? Math.abs(getDSeatTiltPos() - 0) <= 4 && !isDrvHeadrestNormal() : Math.abs(getDSeatHorzPos() + (-100)) < 2 && Math.abs(getDSeatVerPos() - VipSeatControl.VIP_SEAT_VER_POS) < 2 && Math.abs(getDSeatTiltPos() - 0) < 4 && !isDrvHeadrestNormal();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public boolean isPsnSeatLieFlat() {
        LogUtils.i(this.TAG, "isPsnSeatLieFlat, seatHor:" + getPSeatHorzPos() + ",seatTilt:" + getPSeatTiltPos());
        return BaseFeatureOption.getInstance().isEcuDoSeatLayFlat() ? Math.abs(getPSeatTiltPos() - 0) <= 4 && !isPsnHeadrestNormal() : Math.abs(getPSeatHorzPos() + (-100)) < 2 && Math.abs(getPSeatTiltPos() - 0) < 4 && !isPsnHeadrestNormal();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public boolean isCapsuleSeatLieFlat() {
        return isDrvSeatLieFlat() && isPsnSeatLieFlat();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public boolean isRLSeatLieFlat() {
        return Math.abs(getRLSeatTiltPos() - 0) < 2;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public boolean isRRSeatLieFlat() {
        return Math.abs(getRRSeatTiltPos() - 0) < 2;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public boolean isDrvTiltMovingSafe() {
        return this.mMsmController.isDrvTiltMovingSafe();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public boolean isPsnTiltMovingSafe() {
        return this.mMsmController.isPsnTiltMovingSafe();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public boolean seatSaveOutMode() {
        String currentUserScenario = this.mScenarioController.getCurrentUserScenario();
        int userScenarioStatus = this.mScenarioController.getUserScenarioStatus(currentUserScenario);
        LogUtils.i(this.TAG, "scenario:" + currentUserScenario + ",status:" + userScenarioStatus);
        return (!(IScenarioController.SCENARIO_VIPSEAT_MODE.equals(currentUserScenario) || IScenarioController.SPACE_CAPSULE_SLEEP_MODE.equals(currentUserScenario) || IScenarioController.SPACE_CAPSULE_MOVIE_MODE.equals(currentUserScenario) || IScenarioController.MEDITATION_MODE.equals(currentUserScenario) || IScenarioController.SCENARIO_TRAILED_MODE.equals(currentUserScenario) || IScenarioController.SCENARIO_5D_CINEMA_MODE.equals(currentUserScenario)) || userScenarioStatus == 0) && VipDrvSeatControl.getInstance().getSeatStatus() == VipSeatStatus.Normal && VipPsnSeatControl.getInstance().getSeatStatus() == VipSeatStatus.Normal && VipCapsuleSeatControl.getInstance().getSeatStatus() == VipSeatStatus.Normal;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void moveDrvSeatToSafeForRearSleep() {
        this.mMsmController.setDriverAllPositions(VipSeatControl.SLEEP_REST_SEAT_SAFE_DRV_HOR_POS, VipSeatControl.SLEEP_REST_SEAT_SAFE_DRV_VER_POS, VipSeatControl.SLEEP_REST_SEAT_SAFE_DRV_TILT_POS, getDSeatLegPos(), getDSeatCushionPos());
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void movePsnSeatToSafeForRearSleep() {
        this.mMsmController.setPsnAllPositions(VipSeatControl.SLEEP_REST_SEAT_SAFE_PSN_HOR_POS, VipSeatControl.SLEEP_REST_SEAT_SAFE_PSN_VER_POS, VipSeatControl.SLEEP_REST_SEAT_SAFE_PSN_TILT_POS, getPSeatLegPos());
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public boolean isDrvSeatCorrectForRearFold() {
        LogUtils.i(this.TAG, "isDrvSeatCorrectForRearFold, seatHor:" + getDSeatHorzPos() + ", seatVer:" + getDSeatVerPos() + ",seatTilt:" + getDSeatTiltPos());
        return Math.abs(getDSeatHorzPos() - VipSeatControl.SLEEP_REST_SEAT_SAFE_DRV_HOR_POS) < 2 && Math.abs(getDSeatVerPos() - VipSeatControl.SLEEP_REST_SEAT_SAFE_DRV_VER_POS) < 2 && Math.abs(getDSeatTiltPos() - VipSeatControl.SLEEP_REST_SEAT_SAFE_DRV_TILT_POS) < 4;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public boolean isPsnSeatCorrectForRearFold() {
        LogUtils.i(this.TAG, "isPsnSeatCorrectForRearFold, seatHor:" + getPSeatHorzPos() + ", seatVer:" + getPSeatVerPos() + ",seatTilt:" + getPSeatTiltPos());
        return Math.abs(getPSeatHorzPos() - VipSeatControl.SLEEP_REST_SEAT_SAFE_PSN_HOR_POS) < 2 && Math.abs(getPSeatVerPos() - VipSeatControl.SLEEP_REST_SEAT_SAFE_PSN_VER_POS) < 2 && Math.abs(getPSeatTiltPos() - VipSeatControl.SLEEP_REST_SEAT_SAFE_PSN_TILT_POS) < 4;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setDrvLumbControlSw(boolean enable) {
        this.mMsmController.setDriverSeatLumbControlSwitchEnable(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public boolean getDrvLumbControlSw() {
        return this.mMsmController.getDriverSeatLumbControlSwitchEnable();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setPsnLumbControlSw(boolean enable) {
        this.mMsmController.setPassengerSeatLumbControlSwitchEnable(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public boolean getPsnLumbControlSw() {
        return this.mMsmController.getPassengerSeatLumbControlSwitchEnable();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setSecRowLtSeatEZ(int eZStatus) {
        this.mMsmController.setSecRowLtSeatEZ(eZStatus);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setSecRowRtSeatEZ(int eZStatus) {
        this.mMsmController.setSecRowRtSeatEZ(eZStatus);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setSecRowLtSeatZeroGrav(int zeroGravStatus) {
        this.mMsmController.setSecRowLtSeatZeroGrav(zeroGravStatus);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setSecRowRtSeatZeroGrav(int zeroGravStatus) {
        this.mMsmController.setSecRowRtSeatZeroGrav(zeroGravStatus);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setTrdRowSeatStow(int stowStatus) {
        this.mMsmController.setTrdRowSeatStow(stowStatus);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setTrdRowLtSeatFol(int trdRowLtSeatFolStatus) {
        this.mMsmController.setTrdRowLtSeatFol(trdRowLtSeatFolStatus);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setTrdRowRtSeatFol(int trdRowRtSeatFolStatus) {
        this.mMsmController.setTrdRowRtSeatFol(trdRowRtSeatFolStatus);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public int getSecRowLtSeatZeroGrav() {
        return this.mMsmController.getSecRowLtSeatZeroGrav();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public int getSecRowRtSeatZeroGrav() {
        return this.mMsmController.getSecRowRtSeatZeroGrav();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public int getTrdRowSeatStow() {
        return this.mMsmController.getTrdRowSeatStow();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public int getTrdRowLtSeatFol() {
        return this.mMsmController.getTrdRowLtSeatFol();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public int getTrdRowRtSeatFol() {
        return this.mMsmController.getTrdRowRtSeatFol();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public int getTrdRowLtSeatTiltPos() {
        return this.mMsmController.getTrdRowLtSeatTiltPos();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public int getTrdRowRtSeatTiltPos() {
        return this.mMsmController.getTrdRowRtSeatTiltPos();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public int getTrdRowLtSeatHeadVerticalPos() {
        return this.mMsmController.getTrdRowLtSeatHeadVerticalPos();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public int getTrdRowRtSeatHeadVerticalPos() {
        return this.mMsmController.getTrdRowRtSeatHeadVerticalPos();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public int getTrdRowMidSeatHeadVerticalPos() {
        return this.mMsmController.getTrdRowMidSeatHeadVerticalPos();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setTrdRowLtSeatTiltPos(int pos) {
        this.mMsmController.setTrdRowLtSeatTiltPos(pos);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setTrdRowRtSeatTiltPos(int pos) {
        this.mMsmController.setTrdRowRtSeatTiltPos(pos);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setTrdRowLtSeatHeadVerticalPos(int pos) {
        this.mMsmController.setTrdRowLtSeatHeadVerticalPos(pos);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setTrdRowRtSeatHeadVerticalPos(int pos) {
        this.mMsmController.setTrdRowRtSeatHeadVerticalPos(pos);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setTrdRowMidSeatHeadVerticalPos(int pos) {
        this.mMsmController.setTrdRowMidSeatHeadVerticalPos(pos);
    }
}
