package com.xiaopeng.carcontrol;

import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.IBinder;
import com.alibaba.fastjson.parser.JSONLexer;
import com.xiaopeng.carcontrol.GlobalConstant;
import com.xiaopeng.carcontrol.carmanager.CarClientWrapper;
import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import com.xiaopeng.carcontrol.helper.NotificationHelper;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.carcontrol.view.dialog.panel.ControlPanelManager;
import com.xiaopeng.carcontrol.view.dialog.panel.SeatHeatVentHelper;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;
import com.xiaopeng.carcontrol.viewmodel.audio.IAudioViewModel;
import com.xiaopeng.carcontrol.viewmodel.cabin.CabinSmartControl;
import com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel;
import com.xiaopeng.carcontrol.viewmodel.ciu.CiuSmartControl;
import com.xiaopeng.carcontrol.viewmodel.lamp.ILampViewModel;
import com.xiaopeng.carcontrol.viewmodel.meter.XKeyForCustomer;
import com.xiaopeng.carcontrol.viewmodel.scenario.ScenarioSmartControl;
import com.xiaopeng.carcontrol.viewmodel.service.IServiceViewModel;
import org.eclipse.paho.client.mqttv3.internal.ClientDefaults;

/* loaded from: classes.dex */
public class CarControlService extends Service {
    private static final String TAG = "CarControlService";
    private volatile boolean mIsCarSvcReady = false;
    private volatile boolean mHasPendingCustomKeyAction = false;
    private volatile boolean mHasPendingMirrorAction = false;
    private volatile int mMirrorPanelType = -1;

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public void onCreate() {
        LogUtils.d(TAG, "onCreate", false);
        super.onCreate();
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.-$$Lambda$CarControlService$cR5wFMhJMEfPxULNJwpMzgaoGOc
            @Override // java.lang.Runnable
            public final void run() {
                CarControlService.this.lambda$onCreate$1$CarControlService();
            }
        });
    }

    public /* synthetic */ void lambda$onCreate$1$CarControlService() {
        if (CarClientWrapper.getInstance().isCarServiceConnected()) {
            this.mIsCarSvcReady = true;
            if (this.mHasPendingCustomKeyAction) {
                if (BaseFeatureOption.getInstance().isSupportNapa()) {
                    postShowDialogToServiceVm(GlobalConstant.UnityDialogScene.DIALOG_SHOW_CUSTOM_X_KEY);
                } else {
                    ControlPanelManager.getInstance().show(GlobalConstant.ACTION.ACTION_SHOW_CUSTOM_X_KEY_PANEL, 2048);
                }
                this.mHasPendingCustomKeyAction = false;
            }
            if (this.mHasPendingMirrorAction) {
                ThreadUtils.runOnMainThread(new Runnable() { // from class: com.xiaopeng.carcontrol.-$$Lambda$CarControlService$jxXR6j5BLsDlKF5qZiNpFcPAQBQ
                    @Override // java.lang.Runnable
                    public final void run() {
                        CarControlService.this.lambda$null$0$CarControlService();
                    }
                });
                this.mHasPendingMirrorAction = false;
            }
        }
    }

    public /* synthetic */ void lambda$null$0$CarControlService() {
        ControlPanelManager.getInstance().show(GlobalConstant.ACTION.ACTION_SHOW_MIRROR_CONTROL_PANEL, this.mMirrorPanelType);
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null || intent.getAction() == null) {
            return 1;
        }
        handleIntent(intent);
        return 1;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private void handleIntent(Intent intent) {
        char c;
        String action = intent.getAction();
        String str = TAG;
        LogUtils.d(str, "handleIntent action: " + action);
        if (action == null) {
            return;
        }
        action.hashCode();
        switch (action.hashCode()) {
            case -2046137710:
                if (action.equals(GlobalConstant.ACTION.ACTION_SMART_VEHICLE)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -1923065403:
                if (action.equals(GlobalConstant.ACTION.ACTION_SHOW_ISLA_SETTING_PANEL)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -1770966776:
                if (action.equals(GlobalConstant.ACTION.ACTION_SHOW_SEAT_HEAT_DIALOG)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -1547385518:
                if (action.equals(GlobalConstant.ACTION.ACTION_SHOW_SAYHI_SETTING_PANEL)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -1503258958:
                if (action.equals(GlobalConstant.ACTION.ACTION_SHOW_MIRROR_CONTROL_PANEL)) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -1415423349:
                if (action.equals(GlobalConstant.ACTION.ACTION_SHOW_DRIVE_SETTINGS_CONTROL_PANEL)) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case -1399376451:
                if (action.equals(GlobalConstant.ACTION.ACTION_SHOW_NGP_SETTING_PANEL)) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case -1398145583:
                if (action.equals("com.xiaopeng.xui.userscenario")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case -1336049951:
                if (action.equals(GlobalConstant.ACTION.ACTION_SHOW_AS_HEIGHT_SETTINGS_CONTROL_PANEL)) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case -1284310542:
                if (action.equals(GlobalConstant.ACTION.ACTION_SHOW_SEAT_BUTTON_SETTING_PANEL)) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case -1201244584:
                if (action.equals(GlobalConstant.ACTION.ACTION_BOSSKEY)) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            case -976516877:
                if (action.equals(GlobalConstant.ACTION.ACTION_OPERATOR_MICROPHONE)) {
                    c = 11;
                    break;
                }
                c = 65535;
                break;
            case -851336866:
                if (action.equals(GlobalConstant.ACTION.ACTION_SHOW_PSN_SEAT_BUTTON_SETTING_PANEL)) {
                    c = '\f';
                    break;
                }
                c = 65535;
                break;
            case -819688848:
                if (action.equals(GlobalConstant.ACTION.ACTION_SHOW_MICROPHONE_UNMUTE_DIALOG)) {
                    c = '\r';
                    break;
                }
                c = 65535;
                break;
            case -651278328:
                if (action.equals(GlobalConstant.ACTION.ACTION_SHOW_CMS_MIRROR_CONTROL_PANEL)) {
                    c = 14;
                    break;
                }
                c = 65535;
                break;
            case -579350471:
                if (action.equals(GlobalConstant.ACTION.ACTION_SHOW_REAR_LOGO_LIGHT_CONFIRM_DIALOG)) {
                    c = 15;
                    break;
                }
                c = 65535;
                break;
            case -150117801:
                if (action.equals(GlobalConstant.ACTION.ACTION_SHOW_TRAILER_MODE_CONTROL_PANEL)) {
                    c = 16;
                    break;
                }
                c = 65535;
                break;
            case -126886621:
                if (action.equals(GlobalConstant.ACTION.ACTION_SHOW_WINDOW_CONTROL_PANEL)) {
                    c = 17;
                    break;
                }
                c = 65535;
                break;
            case -8359548:
                if (action.equals(GlobalConstant.ACTION.ACTION_SHOW_STEER_CONTROL_PANEL)) {
                    c = 18;
                    break;
                }
                c = 65535;
                break;
            case 156077562:
                if (action.equals(GlobalConstant.ACTION.ACTION_SHOW_VIP_SEAT_CONTROL_PANEL)) {
                    c = 19;
                    break;
                }
                c = 65535;
                break;
            case 378439973:
                if (action.equals(GlobalConstant.ACTION.ACTION_SHOW_SPECIAL_SAS_SETTING_PANEL)) {
                    c = 20;
                    break;
                }
                c = 65535;
                break;
            case 378782077:
                if (action.equals(GlobalConstant.ACTION.ACTION_SHOW_DRIVE_CONTROL_PANEL)) {
                    c = 21;
                    break;
                }
                c = 65535;
                break;
            case 575128097:
                if (action.equals(GlobalConstant.ACTION.ACTION_DO_CUSTOM_X_KEY)) {
                    c = 22;
                    break;
                }
                c = 65535;
                break;
            case 1167445716:
                if (action.equals(GlobalConstant.ACTION.ACTION_SHOW_SUNSHADE_CONTROL_PANEL)) {
                    c = 23;
                    break;
                }
                c = 65535;
                break;
            case 1205794226:
                if (action.equals(GlobalConstant.ACTION.ACTION_SHOW_MATTRESS)) {
                    c = 24;
                    break;
                }
                c = 65535;
                break;
            case 1328922193:
                if (action.equals(GlobalConstant.ACTION.ACTION_SHOW_CUSTOM_DOOR_KEY_PANEL)) {
                    c = 25;
                    break;
                }
                c = 65535;
                break;
            case 1372171270:
                if (action.equals(GlobalConstant.ACTION.ACTION_SHOW_CNGP_MAP_PANEL)) {
                    c = JSONLexer.EOI;
                    break;
                }
                c = 65535;
                break;
            case 1418820542:
                if (action.equals(GlobalConstant.ACTION.ACTION_SHOW_LAMP_SETTING_PANEL)) {
                    c = 27;
                    break;
                }
                c = 65535;
                break;
            case 1509523064:
                if (action.equals(GlobalConstant.ACTION.ACTION_SHOW_SEAT_CONTROL_PANEL)) {
                    c = 28;
                    break;
                }
                c = 65535;
                break;
            case 1796216830:
                if (action.equals(GlobalConstant.ACTION.ACTION_SHOW_PSN_SRS_CLOSE_DIALOG)) {
                    c = 29;
                    break;
                }
                c = 65535;
                break;
            case 1813593024:
                if (action.equals(GlobalConstant.ACTION.ACTION_SHOW_CHILD_MODE_CONTROL_PANEL)) {
                    c = 30;
                    break;
                }
                c = 65535;
                break;
            case 1982452299:
                if (action.equals(GlobalConstant.ACTION.ACTION_SHOW_LLU_SETTING_PANEL)) {
                    c = 31;
                    break;
                }
                c = 65535;
                break;
            case 2023547241:
                if (action.equals(GlobalConstant.ACTION.ACTION_SHOW_MICROPHONE_MUTE_DIALOG)) {
                    c = ' ';
                    break;
                }
                c = 65535;
                break;
            case 2035322744:
                if (action.equals(GlobalConstant.ACTION.ACTION_SHOW_CUSTOM_X_KEY_PANEL)) {
                    c = '!';
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                CiuSmartControl.startCiuControlPage();
                return;
            case 1:
            case 3:
            case 5:
            case 6:
            case '\b':
            case '\t':
            case '\f':
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 23:
            case 25:
            case 26:
            case 27:
            case 28:
            case 30:
            case 31:
                ControlPanelManager.getInstance().show(action, 2048);
                return;
            case 2:
                SeatHeatVentHelper.getInstance().showSeatVentHeatDialog();
                return;
            case 4:
                this.mMirrorPanelType = intent.getBooleanExtra(GlobalConstant.EXTRA.KEY_SHOW_MIRROR_PANEL_INNER, false) ? 2008 : ControlPanelManager.SUPER_SYSTEM_TYPE;
                if (this.mIsCarSvcReady) {
                    ControlPanelManager.getInstance().show(action, this.mMirrorPanelType);
                    return;
                }
                this.mHasPendingMirrorAction = true;
                LogUtils.d(str, "Car Api not connected, pending show mirror panel action");
                return;
            case 7:
                ScenarioSmartControl.getInstance().parseScenarioIntent(intent);
                return;
            case '\n':
                String stringExtra = intent.getStringExtra(GlobalConstant.EXTRA.KEY_KEY_TYPE);
                int intExtra = intent.getIntExtra(GlobalConstant.EXTRA.KEY_KEY_FUNC, 0);
                LogUtils.d(str, "keyType: " + stringExtra + ", keyFunc: " + intExtra);
                if (stringExtra.equals(GlobalConstant.EXTRA.VALUE_X_SHORT) && intExtra == 0) {
                    NotificationHelper.getInstance().showToast(com.xiaopeng.carcontrolmodule.R.string.custom_door_key_no_setting);
                    return;
                }
                return;
            case 11:
                IAudioViewModel iAudioViewModel = (IAudioViewModel) ViewModelManager.getInstance().getViewModelImplSync(IAudioViewModel.class);
                if (iAudioViewModel != null) {
                    iAudioViewModel.setMicrophoneMute(intent.getBooleanExtra(GlobalConstant.EXTRA.KEY_IS_MUTE_MICROPHONE, false));
                    return;
                } else {
                    LogUtils.w(str, "Get AudioViewModel failed", false);
                    return;
                }
            case '\r':
                IAudioViewModel iAudioViewModel2 = (IAudioViewModel) ViewModelManager.getInstance().getViewModelImplSync(IAudioViewModel.class);
                if (iAudioViewModel2 != null) {
                    iAudioViewModel2.confirmUnmuteMicrophone(intent.getBooleanExtra(GlobalConstant.EXTRA.KEY_DISMISS_MICROPHONE_UNMUTE, false), intent.getIntExtra(GlobalConstant.EXTRA.KEY_MICROPHONE_DISPLAY_ID, -1));
                    return;
                } else {
                    LogUtils.w(str, "Get AudioViewModel failed", false);
                    return;
                }
            case 14:
                ControlPanelManager.getInstance().show(action, 2048);
                return;
            case 15:
                ILampViewModel iLampViewModel = (ILampViewModel) ViewModelManager.getInstance().getViewModelImplSync(ILampViewModel.class);
                if (iLampViewModel != null) {
                    iLampViewModel.showRearLogoLightConfirmDialog();
                    return;
                } else {
                    LogUtils.w(str, "Get LampViewModel failed", false);
                    return;
                }
            case 22:
                if (GlobalConstant.EXTRA.VALUE_X_SHORT.equals(intent.getStringExtra(GlobalConstant.EXTRA.KEY_KEY_TYPE)) && XKeyForCustomer.UnlockTrunk.toSwsCmd() == intent.getIntExtra(GlobalConstant.EXTRA.KEY_KEY_FUNC, 0)) {
                    ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.-$$Lambda$CarControlService$VkMMR79kDzCd2mVFRi8VHP6eLhg
                        @Override // java.lang.Runnable
                        public final void run() {
                            CabinSmartControl.getInstance().unlockTrunk();
                        }
                    });
                    return;
                }
                return;
            case 24:
                Intent intent2 = new Intent(GlobalConstant.ACTION.ACTION_SPACE_CAPSULE);
                intent2.putExtra(GlobalConstant.EXTRA.KEY_SPACE_MODE_STATUS, true);
                intent2.putExtra(GlobalConstant.EXTRA.KEY_MODE_TYPE, 8);
                intent2.setFlags(ClientDefaults.MAX_MSG_SIZE);
                intent2.addFlags(1024);
                App.getInstance().startActivity(intent2);
                return;
            case 29:
                ISeatViewModel iSeatViewModel = (ISeatViewModel) ViewModelManager.getInstance().getViewModelImplSync(ISeatViewModel.class);
                if (iSeatViewModel != null) {
                    iSeatViewModel.showPsnSrsCloseDialog();
                    return;
                } else {
                    LogUtils.w(str, "Get SeatViewModel failed", false);
                    return;
                }
            case ' ':
                IAudioViewModel iAudioViewModel3 = (IAudioViewModel) ViewModelManager.getInstance().getViewModelImplSync(IAudioViewModel.class);
                if (iAudioViewModel3 != null) {
                    iAudioViewModel3.confirmMuteMicrophone(intent.getIntExtra(GlobalConstant.EXTRA.KEY_MICROPHONE_DISPLAY_ID, -1));
                    return;
                } else {
                    LogUtils.w(str, "Get AudioViewModel failed", false);
                    return;
                }
            case '!':
                if (this.mIsCarSvcReady) {
                    ControlPanelManager.getInstance().show(action, 2048);
                    return;
                }
                this.mHasPendingCustomKeyAction = true;
                LogUtils.d(str, "Car Api not connected, pending show custom key panel action");
                return;
            default:
                return;
        }
    }

    @Override // android.app.Service, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private void postShowDialogToServiceVm(final String dialogScene) {
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.-$$Lambda$CarControlService$_2gXN8wIiNZc-hTTMfVYvYrqa3w
            @Override // java.lang.Runnable
            public final void run() {
                ((IServiceViewModel) ViewModelManager.getInstance().getViewModelImpl(IServiceViewModel.class)).onShowDialog(dialogScene);
            }
        });
    }
}
