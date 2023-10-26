package com.xiaopeng.carcontrol.view.dialog.panel;

import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.statistic.BtnEnum;
import com.xiaopeng.carcontrol.statistic.PageEnum;
import com.xiaopeng.carcontrol.statistic.StatisticUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.carcontrol.view.dialog.SeatVentSettingDialog;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;
import com.xiaopeng.carcontrol.viewmodel.hvac.HvacViewModel;
import com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel;
import com.xiaopeng.carcontrol.viewmodel.hvac.SeatHeatLevel;
import com.xiaopeng.carcontrol.viewmodel.hvac.SeatVentLevel;
import com.xiaopeng.carcontrolmodule.R;

/* loaded from: classes2.dex */
public class SeatHeatVentHelper {
    private final SeatVentSettingDialog.ISeatHeatVentChangeListener mISeatHeatVentChangeListener;
    private SeatVentSettingDialog mSeatVentSettingDialog;
    private final HvacViewModel mViewModel;

    private SeatHeatVentHelper() {
        this.mISeatHeatVentChangeListener = new SeatVentSettingDialog.ISeatHeatVentChangeListener() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.SeatHeatVentHelper.1
            @Override // com.xiaopeng.carcontrol.view.dialog.SeatVentSettingDialog.ISeatHeatVentChangeListener
            public void onHeatChanged(int type, SeatHeatLevel level) {
                SeatHeatVentHelper.this.setSeatHeatLevelData(type, level);
            }

            @Override // com.xiaopeng.carcontrol.view.dialog.SeatVentSettingDialog.ISeatHeatVentChangeListener
            public void onVentChanged(int type, SeatVentLevel level) {
                if (type == 0) {
                    SeatHeatVentHelper.this.mViewModel.setHvacSeatVentLevel(level);
                    StatisticUtils.sendHvacStatistic(PageEnum.HVAC_PANEL_PAGE, BtnEnum.HVAC_DRV_SEAT_VENT, Integer.valueOf(level.ordinal()));
                } else if (type == 1) {
                    SeatHeatVentHelper.this.mViewModel.setHvacPsnSeatVentLevel(level);
                    StatisticUtils.sendHvacStatistic(PageEnum.HVAC_NEW_PAGE, BtnEnum.HVAC_PSN_SEAT_VENT, Integer.valueOf(level.ordinal()));
                }
            }

            @Override // com.xiaopeng.carcontrol.view.dialog.SeatVentSettingDialog.ISeatHeatVentChangeListener
            public void onAllClose() {
                SeatHeatVentHelper.this.mViewModel.setHvacSeatVentLevel(SeatVentLevel.Off);
                SeatHeatVentHelper.this.mViewModel.setHvacSeatHeatLevel(SeatHeatLevel.Off);
                SeatHeatVentHelper.this.mViewModel.setHvacPsnSeatHeatLevel(SeatHeatLevel.Off);
                SeatHeatVentHelper.this.mViewModel.setHvacRLSeatHeatLevel(SeatHeatLevel.Off);
                SeatHeatVentHelper.this.mViewModel.setHvacPsnSeatVentLevel(SeatVentLevel.Off);
                ThreadUtils.postDelayed(2, new Runnable() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.SeatHeatVentHelper.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        SeatHeatVentHelper.this.mViewModel.setHvacRRSeatHeatLevel(SeatHeatLevel.Off);
                    }
                }, 150L);
            }
        };
        this.mViewModel = (HvacViewModel) ViewModelManager.getInstance().getViewModelImpl(IHvacViewModel.class);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class SingletonHolder {
        static SeatHeatVentHelper sInstance = new SeatHeatVentHelper();

        private SingletonHolder() {
        }
    }

    public static SeatHeatVentHelper getInstance() {
        return SingletonHolder.sInstance;
    }

    public void showSeatVentHeatDialog() {
        if (this.mViewModel == null) {
            return;
        }
        if (this.mSeatVentSettingDialog == null) {
            this.mSeatVentSettingDialog = new SeatVentSettingDialog(App.getInstance(), R.layout.layout_seat_vent_setting_panel, this.mViewModel);
        }
        this.mSeatVentSettingDialog.setOnChangedListener(this.mISeatHeatVentChangeListener);
        this.mSeatVentSettingDialog.setSeatVentLevel(0, this.mViewModel.getHvacSeatVentLevel());
        this.mSeatVentSettingDialog.setSeatHeatLevel(0, this.mViewModel.getHvacSeatHeatLevel());
        this.mSeatVentSettingDialog.setSeatHeatLevel(1, this.mViewModel.getHvacPsnSeatHeatLevel());
        this.mSeatVentSettingDialog.setSeatHeatLevel(2, this.mViewModel.getHvacRLSeatHeatLevel());
        this.mSeatVentSettingDialog.setSeatHeatLevel(3, this.mViewModel.getHvacRRSeatHeatLevel());
        this.mSeatVentSettingDialog.setSeatVentLevel(1, this.mViewModel.getHvacPsnSeatVentLevel());
        this.mSeatVentSettingDialog.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSeatHeatLevelData(int type, SeatHeatLevel level) {
        if (type == 0) {
            this.mViewModel.setHvacSeatHeatLevel(level);
            StatisticUtils.sendHvacStatistic(PageEnum.HVAC_PANEL_PAGE, BtnEnum.HVAC_DRV_SEAT_HEAT, Integer.valueOf(level.ordinal()));
        } else if (type == 1) {
            this.mViewModel.setHvacPsnSeatHeatLevel(level);
            StatisticUtils.sendHvacStatistic(PageEnum.HVAC_PANEL_PAGE, BtnEnum.HVAC_PSN_SEAT_HEAT, Integer.valueOf(level.ordinal()));
        } else if (type == 2) {
            this.mViewModel.setHvacRLSeatHeatLevel(level);
            StatisticUtils.sendHvacStatistic(PageEnum.HVAC_NEW_PAGE, BtnEnum.HVAC_RL_SEAT_HEAT, Integer.valueOf(level.ordinal()));
        } else if (type != 3) {
        } else {
            this.mViewModel.setHvacRRSeatHeatLevel(level);
            StatisticUtils.sendHvacStatistic(PageEnum.HVAC_NEW_PAGE, BtnEnum.HVAC_RR_SEAT_HEAT, Integer.valueOf(level.ordinal()));
        }
    }

    public void onDestroy() {
        SeatVentSettingDialog seatVentSettingDialog = this.mSeatVentSettingDialog;
        if (seatVentSettingDialog != null) {
            seatVentSettingDialog.dismiss();
        }
    }
}
