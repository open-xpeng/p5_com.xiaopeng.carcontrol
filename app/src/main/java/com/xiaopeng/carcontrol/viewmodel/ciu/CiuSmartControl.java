package com.xiaopeng.carcontrol.viewmodel.ciu;

import android.provider.Settings;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.direct.ElementDirectManager;
import com.xiaopeng.carcontrol.direct.IElementDirect;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;

/* loaded from: classes2.dex */
public final class CiuSmartControl {
    private static final String CIU_PAGE_URL = "xiaopeng://carcontrol/ciucontrol";
    private static final String TAG = "CiuSmartControl";
    private ICiuViewModel mCiuViewModel;

    /* loaded from: classes2.dex */
    private static class SingleHolder {
        private static CiuSmartControl sInstance = new CiuSmartControl();

        private SingleHolder() {
        }
    }

    public static CiuSmartControl getInstance() {
        return SingleHolder.sInstance;
    }

    private CiuSmartControl() {
        this.mCiuViewModel = (ICiuViewModel) ViewModelManager.getInstance().getViewModelImpl(ICiuViewModel.class);
    }

    public void setSettingAutoBrightnessdEnable(boolean enable) {
        Settings.System.putInt(App.getInstance().getContentResolver(), ICiuViewModel.SETTING_KEY_SCREEN_BRIGHTNESS_MODE, enable ? 1 : 0);
    }

    public boolean isSettingAutoBrightnessEnable() {
        return Settings.System.getInt(App.getInstance().getContentResolver(), ICiuViewModel.SETTING_KEY_SCREEN_BRIGHTNESS_MODE, 1) == 1;
    }

    public void onAutoBrightnessSwChanged() {
        if (this.mCiuViewModel.isCiuExist()) {
            boolean isSettingAutoBrightnessEnable = isSettingAutoBrightnessEnable();
            LogUtils.d(TAG, "setting auto brightness switch change to " + isSettingAutoBrightnessEnable, false);
            if (isSettingAutoBrightnessEnable) {
                ThreadUtils.runOnMainThread(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.ciu.-$$Lambda$CiuSmartControl$yL4SKBwPv8p8iORPPtJBycEN6Xo
                    @Override // java.lang.Runnable
                    public final void run() {
                        CiuSmartControl.this.lambda$onAutoBrightnessSwChanged$0$CiuSmartControl();
                    }
                });
            }
        }
    }

    public /* synthetic */ void lambda$onAutoBrightnessSwChanged$0$CiuSmartControl() {
        this.mCiuViewModel.setDmsSwEnable(true);
    }

    public static void startCiuControlPage() {
        IElementDirect elementDirect = ElementDirectManager.getElementDirect();
        if (elementDirect != null) {
            if (elementDirect.checkSupport(CIU_PAGE_URL)) {
                LogUtils.i(TAG, "Jump to CiuControlFragment : " + CIU_PAGE_URL, false);
                elementDirect.showPageDirect(App.getInstance(), CIU_PAGE_URL);
                return;
            }
            LogUtils.w(TAG, "Start Ciu control page failed: CIU not existed", false);
        }
    }
}
