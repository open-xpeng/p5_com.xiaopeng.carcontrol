package com.xiaopeng.carcontrol.viewmodel.hvac;

import com.xiaopeng.carcontrol.IpcRouterService;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.helper.NotificationHelper;
import com.xiaopeng.carcontrol.helper.SoundHelper;
import com.xiaopeng.carcontrol.helper.SpeechHelper;
import com.xiaopeng.carcontrol.model.FunctionModel;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ResUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.carcontrolmodule.R;

/* loaded from: classes2.dex */
public class AirPurgeControl {
    private static final int AQ_ENTER_THRESHOLD = 100;
    private static final int AQ_ENTER_WIND_LEVEL = 4;
    private static final int AQ_EXIT_THRESHOLD = 75;
    private static final String TAG = "AirPurgeControl";
    private Runnable countdownRun = new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.hvac.AirPurgeControl.1
        @Override // java.lang.Runnable
        public void run() {
            AirPurgeControl.this.mIsManualControl = false;
            if (AirPurgeControl.this.mHvacViewModel.getHvacInnerPM25() < 100) {
                AirPurgeControl.this.mHvacViewModel.setHvacQualityPurgeMode(false);
            }
        }
    };
    private IHvacViewModel mHvacViewModel;
    private boolean mIsManualControl;
    private boolean mNeedRecover;

    /* JADX INFO: Access modifiers changed from: package-private */
    public AirPurgeControl(IHvacViewModel mHvacViewModel) {
        this.mHvacViewModel = mHvacViewModel;
    }

    private void airProtectRemind() {
        NotificationHelper.getInstance().sendMessageToMessageCenter(NotificationHelper.SCENE_XFREE_BREATH, ResUtils.getString(R.string.hvac_auto_air_protect_title), ResUtils.getString(R.string.hvac_auto_air_protect_content), ResUtils.getString(R.string.hvac_auto_air_protect_prompt_tts), ResUtils.getString(R.string.hvac_auto_air_protect_wake_words), ResUtils.getString(R.string.hvac_auto_air_protect_response_tts), ResUtils.getString(R.string.hvac_auto_air_protect_btn_title), false, 30000L, new IpcRouterService.ICallback() { // from class: com.xiaopeng.carcontrol.viewmodel.hvac.-$$Lambda$AirPurgeControl$Y6JjChhgZpwuXOsgk1ikZd_k1dI
            @Override // com.xiaopeng.carcontrol.IpcRouterService.ICallback
            public final void onCallback(String str) {
                AirPurgeControl.this.lambda$airProtectRemind$0$AirPurgeControl(str);
            }
        });
    }

    public /* synthetic */ void lambda$airProtectRemind$0$AirPurgeControl(String content) {
        doAirProtect(false);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void onInnerAqChange(int aqLevel) {
        if (!CarBaseConfig.getInstance().isSupportInnerPm25() || aqLevel == 1023) {
            return;
        }
        if (aqLevel >= 100 && FunctionModel.getInstance().isAirProtectFuncAllowed() && !this.mHvacViewModel.isHvacQualityPurgeEnable()) {
            FunctionModel.getInstance().setAirProtectTs(System.nanoTime());
            LogUtils.d(TAG, "airProtect:" + aqLevel + ", isAuto:" + this.mHvacViewModel.isHvacAutoModeOn());
            if (this.mHvacViewModel.isHvacAutoModeOn()) {
                doAirProtect(true);
            } else {
                airProtectRemind();
            }
        } else if (!this.mHvacViewModel.isHvacQualityPurgeEnable() || aqLevel >= 75 || this.mIsManualControl) {
        } else {
            this.mHvacViewModel.setHvacQualityPurgeMode(false);
        }
    }

    private void doAirProtect(boolean auto) {
        if (CarBaseConfig.getInstance().isSupportInnerPm25()) {
            this.mHvacViewModel.setHvacQualityPurgeMode(true);
            String string = ResUtils.getString(auto ? R.string.hvac_auto_air_auto_toast : R.string.hvac_auto_air_remind_toast);
            NotificationHelper.getInstance().showToast(string);
            if (auto) {
                SpeechHelper.getInstance().speak(string);
            } else {
                SoundHelper.play(this.mHvacViewModel.getAirAutoProtectSound(), false, false);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void enterAirPurge() {
        LogUtils.i(TAG, "enterAirPurge", false);
        this.mNeedRecover = false;
        if (!this.mHvacViewModel.isHvacPowerModeOn()) {
            this.mHvacViewModel.setHvacPowerMode(true);
        } else if (this.mHvacViewModel.isHvacRapidCoolingEnable()) {
            this.mHvacViewModel.setHvacRapidCoolingEnable(false);
        } else if (this.mHvacViewModel.isHvacDeodorantEnable()) {
            this.mHvacViewModel.setHvacDeodorantEnable(false);
        } else if (this.mHvacViewModel.isHvacFrontDefrostOn()) {
            this.mHvacViewModel.setHvacFrontDefrost(false);
        } else {
            controlAirPurge();
            return;
        }
        ThreadUtils.postDelayed(2, new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.hvac.-$$Lambda$AirPurgeControl$I4tQoLjxLJeRfJHYKsss6_fUODg
            @Override // java.lang.Runnable
            public final void run() {
                AirPurgeControl.this.controlAirPurge();
            }
        }, 500L);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void exitAirPurge() {
        LogUtils.i(TAG, "exitAirPurge", false);
        this.mIsManualControl = false;
        ThreadUtils.removeRunnable(this.countdownRun);
        if (!this.mHvacViewModel.isHvacPowerModeOn()) {
            this.mNeedRecover = true;
            return;
        }
        this.mNeedRecover = false;
        recoverPurge();
    }

    private void recoverPurge() {
        this.mNeedRecover = false;
        if (!this.mHvacViewModel.isHvacPowerModeOn() || this.mHvacViewModel.isHvacAutoModeOn()) {
            return;
        }
        if (FunctionModel.getInstance().isHvacSmartAutoEnable()) {
            this.mHvacViewModel.setHvacAutoMode(true);
        } else if (FunctionModel.getInstance().getHvacSmartFanSpeed() != 14) {
            this.mHvacViewModel.setHvacWindSpeedLevel(FunctionModel.getInstance().getHvacSmartFanSpeed());
        }
        int hvacSmartAqsMode = FunctionModel.getInstance().getHvacSmartAqsMode();
        if (this.mHvacViewModel.getHvacAqsMode() == hvacSmartAqsMode || this.mHvacViewModel.getHvacAqsMode() != 1) {
            return;
        }
        this.mHvacViewModel.setHvacAqsMode(hvacSmartAqsMode);
    }

    public void initAirPurgeMode() {
        boolean isHvacQualityPurgeEnable = this.mHvacViewModel.isHvacQualityPurgeEnable();
        LogUtils.d(TAG, "initAirPurgeMode:" + this.mNeedRecover + ",purgeEnable:" + isHvacQualityPurgeEnable, false);
        if (isHvacQualityPurgeEnable) {
            enterAirPurge();
        } else if (this.mNeedRecover) {
            recoverPurge();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void controlAirPurge() {
        if (this.mHvacViewModel.getHvacInnerPM25() < 100) {
            this.mIsManualControl = true;
            ThreadUtils.postDelayed(2, this.countdownRun, 60000L);
        }
        HvacSmartControl.getInstance().memoryHvacSmartStatus();
        if (this.mHvacViewModel.isHvacFanSpeedAutoEnable()) {
            this.mHvacViewModel.setHvacWindSpeedUp();
            ThreadUtils.postDelayed(2, new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.hvac.-$$Lambda$AirPurgeControl$4kmzR-hsZm9TxykpNq22iDNnYzs
                @Override // java.lang.Runnable
                public final void run() {
                    AirPurgeControl.this.setAirPurgeStatus();
                }
            }, 300L);
            return;
        }
        setAirPurgeStatus();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setAirPurgeStatus() {
        if (this.mHvacViewModel.getHvacWindSpeedLevel() < 4) {
            this.mHvacViewModel.setHvacWindSpeedLevel(4);
        }
        if (this.mHvacViewModel.getHvacAqsMode() != 1) {
            this.mHvacViewModel.setHvacAqsMode(1);
        }
    }

    public boolean isPurgeProtectTime() {
        long currentTimeMillis = System.currentTimeMillis() - FunctionModel.getInstance().getHvacPurgeClickTime();
        return currentTimeMillis >= 0 && currentTimeMillis <= 1000;
    }
}
