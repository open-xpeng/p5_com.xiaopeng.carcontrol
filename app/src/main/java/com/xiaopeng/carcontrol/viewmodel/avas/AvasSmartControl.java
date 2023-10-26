package com.xiaopeng.carcontrol.viewmodel.avas;

import android.provider.Settings;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;

/* loaded from: classes2.dex */
public final class AvasSmartControl {
    private static final String TAG = "AvasSmartControl";
    private IAvasViewModel mAvasViewModel;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class SingleHolder {
        private static AvasSmartControl sInstance = new AvasSmartControl();

        private SingleHolder() {
        }
    }

    public static AvasSmartControl getInstance() {
        return SingleHolder.sInstance;
    }

    private AvasSmartControl() {
        this.mAvasViewModel = (IAvasViewModel) ViewModelManager.getInstance().getViewModelImpl(IAvasViewModel.class);
    }

    public void setSettingLowSpdEnable() {
        if (isSettingLowSpdEnable()) {
            return;
        }
        LogUtils.d(TAG, "Write low speed switch with true to SettingsProvider");
        Settings.System.putInt(App.getInstance().getContentResolver(), IAvasViewModel.SETTING_KEY_LOW_SPD_SW, 1);
    }

    public boolean isSettingLowSpdEnable() {
        return Settings.System.getInt(App.getInstance().getContentResolver(), IAvasViewModel.SETTING_KEY_LOW_SPD_SW, 1) == 1;
    }

    public void onLowSpdSwChanged() {
        boolean isSettingLowSpdEnable = isSettingLowSpdEnable();
        LogUtils.d(TAG, "setting low speed switch change to " + isSettingLowSpdEnable);
        this.mAvasViewModel.setLowSpdEnable(isSettingLowSpdEnable);
    }
}
