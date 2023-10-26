package com.xiaopeng.carcontrol.view.dialog.dropdownmenu;

import android.view.View;
import android.widget.Button;
import androidx.lifecycle.Observer;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.helper.NotificationHelper;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;
import com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel;
import com.xiaopeng.carcontrol.viewmodel.cabin.TrunkType;
import com.xiaopeng.carcontrol.viewmodel.cabin.WindowDoorViewModel;
import com.xiaopeng.carcontrol.viewmodel.carsettings.CarBodyViewModel;
import com.xiaopeng.carcontrol.viewmodel.carsettings.ChargePort;
import com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel;
import com.xiaopeng.carcontrol.viewmodel.chassis.ChassisViewModel;
import com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel;
import com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel;
import com.xiaopeng.carcontrol.viewmodel.vcu.VcuViewModel;
import com.xiaopeng.carcontrolmodule.R;
import com.xiaopeng.xui.widget.XButton;

/* loaded from: classes2.dex */
public class PGearCtrlMenu extends AbstractDropDownMenu {
    private CarBodyViewModel mCarBodyVm;
    private ChassisViewModel mChassisVm;
    private VcuViewModel mVcuVm;
    private WindowDoorViewModel mWinDoorVm;

    @Override // com.xiaopeng.carcontrol.view.dialog.dropdownmenu.AbstractDropDownMenu
    public int getDropdownMenuGroupId() {
        return 0;
    }

    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    protected boolean needInitVm() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    protected void onInitViewModel() {
        this.mCarBodyVm = (CarBodyViewModel) ViewModelManager.getInstance().getViewModelImpl(ICarBodyViewModel.class);
        this.mWinDoorVm = (WindowDoorViewModel) ViewModelManager.getInstance().getViewModelImpl(IWindowDoorViewModel.class);
        this.mVcuVm = (VcuViewModel) ViewModelManager.getInstance().getViewModelImpl(IVcuViewModel.class);
        this.mChassisVm = (ChassisViewModel) ViewModelManager.getInstance().getViewModelImpl(IChassisViewModel.class);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    public boolean onInterceptShow() {
        if (super.onInterceptShow()) {
            return true;
        }
        return this.mVcuVm.isQuickControlVisible();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    public int getLayoutId() {
        return R.layout.p_gear_ctrl_menu;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.view.dialog.dropdownmenu.AbstractDropDownMenu, com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    public void onInitView() {
        super.onInitView();
        findViewById(R.id.open_trunk_btn).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.dialog.dropdownmenu.-$$Lambda$PGearCtrlMenu$LKSlH64N8w6KRpJNSEWdcvFhwlM
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PGearCtrlMenu.this.lambda$onInitView$0$PGearCtrlMenu(view);
            }
        });
        if (CarBaseConfig.getInstance().isSupportElcTrunk()) {
            Button button = (Button) findViewById(R.id.close_trunk_btn);
            button.setVisibility(0);
            button.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.dialog.dropdownmenu.-$$Lambda$PGearCtrlMenu$IJAOoxktPloU_t3YPHVVZgTVn3E
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PGearCtrlMenu.this.lambda$onInitView$1$PGearCtrlMenu(view);
                }
            });
        }
        XButton xButton = (XButton) findViewById(R.id.ac_btn);
        if (CarBaseConfig.getInstance().isSingleChargePort()) {
            if (CarBaseConfig.getInstance().isChargePortSignalErr()) {
                xButton.setText(R.string.p_gear_ctrl_open_charge);
            } else {
                xButton.setVisibility(8);
            }
        }
        if (!CarBaseConfig.getInstance().isSingleChargePort() || CarBaseConfig.getInstance().isChargePortSignalErr()) {
            xButton.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.dialog.dropdownmenu.-$$Lambda$PGearCtrlMenu$In6uapfwDSPm3WWP5BnJgzwbtJ4
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PGearCtrlMenu.this.lambda$onInitView$2$PGearCtrlMenu(view);
                }
            });
        }
        XButton xButton2 = (XButton) findViewById(R.id.dc_btn);
        if (!CarBaseConfig.getInstance().isChargePortSignalErr()) {
            if (CarBaseConfig.getInstance().isSingleChargePort()) {
                xButton2.setText(R.string.p_gear_ctrl_open_charge);
            }
            xButton2.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.dialog.dropdownmenu.-$$Lambda$PGearCtrlMenu$si_sptedMmBvUNZSCC6fNAR-UV0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PGearCtrlMenu.this.lambda$onInitView$3$PGearCtrlMenu(view);
                }
            });
        } else {
            xButton2.setVisibility(8);
        }
        if (CarBaseConfig.getInstance().isSupportAirSuspension()) {
            Button button2 = (Button) findViewById(R.id.easy_loading_btn);
            button2.setVisibility(0);
            button2.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.dialog.dropdownmenu.-$$Lambda$PGearCtrlMenu$7V-5BGPTzs6Hqsbuf1S8jp-lmTM
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PGearCtrlMenu.this.lambda$onInitView$4$PGearCtrlMenu(view);
                }
            });
        }
        findViewById(R.id.do_not_remind).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.dialog.dropdownmenu.-$$Lambda$PGearCtrlMenu$eKMmliUitlB8I_UXBpOptoZ31Qo
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PGearCtrlMenu.this.lambda$onInitView$5$PGearCtrlMenu(view);
            }
        });
        if (CarBaseConfig.getInstance().isSupportSdc()) {
            View findViewById = findViewById(R.id.fl_door_btn);
            findViewById.setVisibility(0);
            findViewById.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.dialog.dropdownmenu.-$$Lambda$PGearCtrlMenu$k_FZZoAqGCrUuvpmD1JriLbiyf0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PGearCtrlMenu.this.lambda$onInitView$6$PGearCtrlMenu(view);
                }
            });
            View findViewById2 = findViewById(R.id.fr_door_btn);
            findViewById2.setVisibility(0);
            findViewById2.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.dialog.dropdownmenu.-$$Lambda$PGearCtrlMenu$nL7l3_cnZWeslJxVK3YdsLELaMA
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PGearCtrlMenu.this.lambda$onInitView$7$PGearCtrlMenu(view);
                }
            });
        }
    }

    public /* synthetic */ void lambda$onInitView$0$PGearCtrlMenu(View v) {
        if (CarBaseConfig.getInstance().isSupportElcTrunk()) {
            this.mWinDoorVm.controlRearTrunk(TrunkType.Open);
        } else {
            this.mWinDoorVm.openRearTrunk();
        }
        startAutoCloseTask();
    }

    public /* synthetic */ void lambda$onInitView$1$PGearCtrlMenu(View v) {
        this.mWinDoorVm.controlRearTrunk(TrunkType.Close);
        startAutoCloseTask();
    }

    public /* synthetic */ void lambda$onInitView$2$PGearCtrlMenu(View v) {
        this.mCarBodyVm.controlChargePort(ChargePort.Left, true);
        startAutoCloseTask();
    }

    public /* synthetic */ void lambda$onInitView$3$PGearCtrlMenu(View v) {
        this.mCarBodyVm.controlChargePort(ChargePort.Right, true);
        startAutoCloseTask();
    }

    public /* synthetic */ void lambda$onInitView$4$PGearCtrlMenu(View v) {
        if (this.mVcuVm.getCarSpeed() >= 3.0f) {
            NotificationHelper.getInstance().showToast(R.string.as_easy_loading_open_failed_for_moving);
        } else if (this.mChassisVm.getAirSuspensionRepairMode() || this.mChassisVm.getTrailerModeStatus()) {
            NotificationHelper.getInstance().showToast(R.string.as_easy_loading_open_failed_for_trailer_mode);
        } else {
            this.mChassisVm.setEasyLoadingSwitch(true);
            startAutoCloseTask();
        }
    }

    public /* synthetic */ void lambda$onInitView$5$PGearCtrlMenu(View v) {
        NotificationHelper.getInstance().showToast(R.string.p_gear_do_not_remind_toast);
        this.mVcuVm.setParkDropdownMenuEnable(false, false);
        dismiss();
    }

    public /* synthetic */ void lambda$onInitView$6$PGearCtrlMenu(View v) {
        this.mWinDoorVm.controlSdc(true, true);
        startAutoCloseTask(3000L);
    }

    public /* synthetic */ void lambda$onInitView$7$PGearCtrlMenu(View v) {
        this.mWinDoorVm.controlSdc(false, true);
        startAutoCloseTask(3000L);
    }

    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    protected void onRegisterLiveData() {
        setLiveDataObserver(this.mVcuVm.getQuickControlVisibleData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.dialog.dropdownmenu.-$$Lambda$PGearCtrlMenu$mxb4QjVh1EpegG_EIn_LdAyPei8
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                PGearCtrlMenu.this.lambda$onRegisterLiveData$8$PGearCtrlMenu((Boolean) obj);
            }
        });
    }

    public /* synthetic */ void lambda$onRegisterLiveData$8$PGearCtrlMenu(Boolean visible) {
        if (visible.booleanValue()) {
            dismiss();
        }
    }
}
