package com.xiaopeng.carcontrol.viewmodel.service;

import android.database.ContentObserver;
import android.provider.Settings;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.carmanager.BaseCarController;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;
import com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel;
import com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel;

/* loaded from: classes2.dex */
public class ShowCarControl {
    private static final String SHOW_CAR_DRIVE_SWITCH = "vcu_disable";
    private static final String SHOW_CAR_HVAC_SWITCH = "airCondition_disable";
    private static final String TAG = "ShowCarControl";
    private IHvacViewModel mHvacViewModel;
    private boolean mIsShowCarDriveDisable;
    private boolean mIsShowCarHvacDisable;
    private IVcuViewModel mVcuViewModel;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class SingleHolder {
        private static ShowCarControl showCarControl = new ShowCarControl();

        private SingleHolder() {
        }
    }

    public static ShowCarControl getInstance() {
        return SingleHolder.showCarControl;
    }

    private ShowCarControl() {
        this.mIsShowCarHvacDisable = getShowCarHvacMode();
        this.mIsShowCarDriveDisable = getShowCarDriveMode();
        BaseCarController.EXHIBITION_MODE = this.mIsShowCarHvacDisable;
    }

    public void registerResolver() {
        this.mHvacViewModel = (IHvacViewModel) ViewModelManager.getInstance().getViewModelImpl(IHvacViewModel.class);
        this.mVcuViewModel = (IVcuViewModel) ViewModelManager.getInstance().getViewModelImpl(IVcuViewModel.class);
        App.getInstance().getContentResolver().registerContentObserver(Settings.System.getUriFor(SHOW_CAR_HVAC_SWITCH), true, new ContentObserver(ThreadUtils.getHandler(0)) { // from class: com.xiaopeng.carcontrol.viewmodel.service.ShowCarControl.1
            @Override // android.database.ContentObserver
            public void onChange(boolean selfChange) {
                ShowCarControl showCarControl = ShowCarControl.this;
                showCarControl.mIsShowCarHvacDisable = showCarControl.getShowCarHvacMode();
                ShowCarControl.this.onShowCarModeChange();
            }
        });
        App.getInstance().getContentResolver().registerContentObserver(Settings.System.getUriFor(SHOW_CAR_DRIVE_SWITCH), true, new ContentObserver(ThreadUtils.getHandler(0)) { // from class: com.xiaopeng.carcontrol.viewmodel.service.ShowCarControl.2
            @Override // android.database.ContentObserver
            public void onChange(boolean selfChange) {
                ShowCarControl showCarControl = ShowCarControl.this;
                showCarControl.mIsShowCarDriveDisable = showCarControl.getShowCarDriveMode();
                ShowCarControl.this.onShowCarModeChange();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean getShowCarHvacMode() {
        int i;
        try {
            i = Settings.System.getInt(App.getInstance().getContentResolver(), SHOW_CAR_HVAC_SWITCH);
        } catch (Settings.SettingNotFoundException e) {
            LogUtils.e(TAG, e.getMessage());
            i = 0;
        }
        LogUtils.i(TAG, "ShowCarHvac:" + i, false);
        return i == 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean getShowCarDriveMode() {
        int i;
        try {
            i = Settings.System.getInt(App.getInstance().getContentResolver(), SHOW_CAR_DRIVE_SWITCH);
        } catch (Settings.SettingNotFoundException e) {
            LogUtils.e(TAG, e.getMessage());
            i = 0;
        }
        LogUtils.i(TAG, "ShowCarDrive:" + i, false);
        return i == 1;
    }

    public boolean isShowCarHvacDisable() {
        return this.mIsShowCarHvacDisable;
    }

    public boolean isShowCarDriveDisable() {
        return this.mIsShowCarDriveDisable;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onShowCarModeChange() {
        if (this.mIsShowCarHvacDisable && this.mHvacViewModel.isHvacPowerModeOn()) {
            this.mHvacViewModel.setHvacPowerMode(false);
        }
        BaseCarController.EXHIBITION_MODE = this.mIsShowCarHvacDisable;
        this.mVcuViewModel.controlExhibitionMode(this.mIsShowCarDriveDisable);
    }
}
