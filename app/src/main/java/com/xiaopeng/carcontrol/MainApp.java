package com.xiaopeng.carcontrol;

import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.provider.Settings;
import com.xiaopeng.carcontrol.GlobalConstant;
import com.xiaopeng.carcontrol.bean.xpilot.XPilotCategory;
import com.xiaopeng.carcontrol.bean.xpilot.category.XPilotCategoryManager;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import com.xiaopeng.carcontrol.debug.DemoManager;
import com.xiaopeng.carcontrol.direct.DxElementDirect;
import com.xiaopeng.carcontrol.direct.ElementDirectManager;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.carcontrol.view.control.hvac.vent.PreloadBitmap;
import com.xiaopeng.carcontrol.view.dialog.panel.CngpMapPanel;
import com.xiaopeng.carcontrol.view.dialog.panel.ControlPanelManager;
import com.xiaopeng.carcontrol.view.dialog.panel.DoorKeyControlPanel;
import com.xiaopeng.carcontrol.view.dialog.panel.IslaSettingPanel;
import com.xiaopeng.carcontrol.view.dialog.panel.LluSayHiControlPanel;
import com.xiaopeng.carcontrol.view.dialog.panel.LluSettingPanel;
import com.xiaopeng.carcontrol.view.dialog.panel.NgpSettingPanel;
import com.xiaopeng.carcontrol.view.dialog.panel.SunshadeControlPanel;
import com.xiaopeng.carcontrol.view.dialog.panel.VipSeatControlPanel;
import com.xiaopeng.carcontrol.view.dialog.panel.WindowControlPanel;
import com.xiaopeng.carcontrol.view.dialog.panel.customkey.SteerKeyControlPanel;
import com.xiaopeng.carcontrol.view.dialog.poppanel.PopPanelManager;
import com.xiaopeng.carcontrol.view.dialog.poppanel.PsnSeatCtrlMenu;
import com.xiaopeng.carcontrol.view.dialog.poppanel.SeatMirrorCtrlMenu;
import com.xiaopeng.carcontrol.viewmodel.ciu.CiuSmartControl;
import com.xiaopeng.carcontrol.viewmodel.ciu.ICiuViewModel;
import com.xiaopeng.carcontrol.viewmodel.lamp.ILampViewModel;
import com.xiaopeng.carcontrol.viewmodel.lamp.LampSmartControl;
import com.xiaopeng.lib.apirouter.server.VuiMainObserver_Manifest;
import com.xiaopeng.lib.bughunter.anr.UILooperObserver;
import com.xiaopeng.lludancemanager.LluDanceAlarmReceiver;
import com.xiaopeng.lludancemanager.helper.LluDanceAlarmHelper;
import com.xiaopeng.speech.vui.VuiEngine;
import com.xiaopeng.speech.vui.utils.LogUtils;
import com.xiaopeng.xui.Xui;
import java.util.HashMap;
import org.eclipse.paho.client.mqttv3.internal.ClientDefaults;

/* loaded from: classes.dex */
public final class MainApp extends App {
    private static final String TAG = "D21_MainApp";
    private LluDanceAlarmReceiver mLluDanceAlarmReceiver;

    @Override // com.xiaopeng.carcontrol.App, android.app.Application
    public void onCreate() {
        super.onCreate();
        if (isMainProcess()) {
            if (BaseFeatureOption.getInstance().isSupportVui()) {
                Xui.setVuiEnable(true);
                VuiEngine.getInstance(this).setLoglevel(LogUtils.LOG_DEBUG_LEVEL);
                VuiEngine.getInstance(this).subscribe(VuiMainObserver_Manifest.DESCRIPTOR);
            }
            if (CarBaseConfig.getInstance().isSupportCiuConfig()) {
                observeSettingAutoBrighEnable();
            }
            observeSettingWaitMode();
            if (CarBaseConfig.getInstance().isSupportLluDance()) {
                registerBroadcastReceiverForLluDance();
            }
        }
        XPilotCategoryManager.getInstance().init(XPilotCategory.getInstance());
        if (isMainProcess()) {
            ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.-$$Lambda$MainApp$ZhaoZxQSkYdH68fC099BLIxqLmY
                @Override // java.lang.Runnable
                public final void run() {
                    MainApp.this.lambda$onCreate$0$MainApp();
                }
            });
        }
        IpcModuleService.init();
    }

    public /* synthetic */ void lambda$onCreate$0$MainApp() {
        PreloadBitmap.getInstance().createBitmap(new int[]{R.drawable.bg_pressed}, this);
        PreloadBitmap.getInstance().readText(new int[]{R.raw.background_vertex_shader, R.raw.background_fragment_shader}, this);
    }

    @Override // com.xiaopeng.carcontrol.App
    void onAppCreate() {
        ElementDirectManager.init(DxElementDirect.getInstance());
        getInstance().setTheme(R.style.AppTheme);
    }

    public /* synthetic */ void lambda$startDemoService$1$MainApp() {
        DemoManager.init(this);
    }

    @Override // com.xiaopeng.carcontrol.App
    protected void startDemoService() {
        ThreadUtils.runOnMainThreadDelay(new Runnable() { // from class: com.xiaopeng.carcontrol.-$$Lambda$MainApp$kSuNkfRQoZETM9a8UrpZdzDxudY
            @Override // java.lang.Runnable
            public final void run() {
                MainApp.this.lambda$startDemoService$1$MainApp();
            }
        }, UILooperObserver.ANR_TRIGGER_TIME);
    }

    @Override // com.xiaopeng.carcontrol.App
    void preloadControlPanel() {
        CarBaseConfig carBaseConfig = CarBaseConfig.getInstance();
        HashMap hashMap = new HashMap();
        hashMap.put(GlobalConstant.ACTION.ACTION_SHOW_CUSTOM_X_KEY_PANEL, SteerKeyControlPanel.class);
        hashMap.put(GlobalConstant.ACTION.ACTION_SHOW_CUSTOM_DOOR_KEY_PANEL, DoorKeyControlPanel.class);
        if (carBaseConfig.isSupportLlu()) {
            hashMap.put(GlobalConstant.ACTION.ACTION_SHOW_LLU_SETTING_PANEL, LluSettingPanel.class);
            hashMap.put(GlobalConstant.ACTION.ACTION_SHOW_SAYHI_SETTING_PANEL, LluSayHiControlPanel.class);
        }
        if (carBaseConfig.isSupportSunShade()) {
            hashMap.put(GlobalConstant.ACTION.ACTION_SHOW_SUNSHADE_CONTROL_PANEL, SunshadeControlPanel.class);
        }
        if (carBaseConfig.isSupportVipSeat()) {
            hashMap.put(GlobalConstant.ACTION.ACTION_SHOW_VIP_SEAT_CONTROL_PANEL, VipSeatControlPanel.class);
        }
        if (carBaseConfig.isSupportWindowPos()) {
            hashMap.put(GlobalConstant.ACTION.ACTION_SHOW_WINDOW_CONTROL_PANEL, WindowControlPanel.class);
        }
        if (carBaseConfig.isSupportNgp()) {
            hashMap.put(GlobalConstant.ACTION.ACTION_SHOW_NGP_SETTING_PANEL, NgpSettingPanel.class);
        }
        if (carBaseConfig.isSupportCNgp()) {
            hashMap.put(GlobalConstant.ACTION.ACTION_SHOW_CNGP_MAP_PANEL, CngpMapPanel.class);
        }
        if (carBaseConfig.isSupportIsla()) {
            hashMap.put(GlobalConstant.ACTION.ACTION_SHOW_ISLA_SETTING_PANEL, IslaSettingPanel.class);
        }
        ControlPanelManager.getInstance().initPanels(hashMap);
        HashMap hashMap2 = new HashMap();
        if (CarBaseConfig.getInstance().isSupportMsmD()) {
            hashMap2.put("seat_mirror_menu", SeatMirrorCtrlMenu.class);
        }
        if (CarBaseConfig.getInstance().isSupportMsmP()) {
            hashMap2.put("psn_seat_menu", PsnSeatCtrlMenu.class);
        }
        PopPanelManager.getInstance().initMenus(hashMap2);
    }

    public static void openNoviceGuide(String guideType, int tabIndex) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.xiaopeng.usermanual", "com.xiaopeng.noviceguide.NoviceGuideActivity"));
        intent.putExtra("guide_type", guideType);
        intent.putExtra("tab_index", tabIndex);
        intent.putExtra("car_type", CarBaseConfig.getCarCduType());
        intent.setFlags(ClientDefaults.MAX_MSG_SIZE);
        try {
            App.getInstance().startActivity(intent);
        } catch (Exception e) {
            com.xiaopeng.carcontrol.util.LogUtils.w(TAG, "Failed to start activity of UserManual" + e.getMessage(), false);
        }
    }

    private void observeSettingAutoBrighEnable() {
        getContentResolver().registerContentObserver(Settings.System.getUriFor(ICiuViewModel.SETTING_KEY_SCREEN_BRIGHTNESS_MODE), true, new ContentObserver(ThreadUtils.getHandler(0)) { // from class: com.xiaopeng.carcontrol.MainApp.1
            @Override // android.database.ContentObserver
            public void onChange(boolean selfChange) {
                CiuSmartControl.getInstance().onAutoBrightnessSwChanged();
            }
        });
    }

    private void observeSettingWaitMode() {
        getContentResolver().registerContentObserver(Settings.Secure.getUriFor(ILampViewModel.SETTING_KEY_WAIT_MODE), false, new ContentObserver(ThreadUtils.getHandler(0)) { // from class: com.xiaopeng.carcontrol.MainApp.2
            @Override // android.database.ContentObserver
            public void onChange(boolean selfChange) {
                int i = Settings.Secure.getInt(MainApp.this.getContentResolver(), ILampViewModel.SETTING_KEY_WAIT_MODE, 0);
                com.xiaopeng.carcontrol.util.LogUtils.i(MainApp.TAG, "onWaitMode changed to " + i, false);
                LampSmartControl.getInstance().controlXpWaitMode(i);
            }
        });
    }

    @Override // com.xiaopeng.carcontrol.App, android.app.Application, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (isMainProcess()) {
            ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.-$$Lambda$MainApp$-XdoExu_E8kZPWSvr1TAiLLG1TQ
                @Override // java.lang.Runnable
                public final void run() {
                    MainApp.this.lambda$onConfigurationChanged$2$MainApp();
                }
            });
        }
    }

    public /* synthetic */ void lambda$onConfigurationChanged$2$MainApp() {
        PreloadBitmap.getInstance().createBitmap(new int[]{R.drawable.bg_pressed}, this);
    }

    private void registerBroadcastReceiverForLluDance() {
        this.mLluDanceAlarmReceiver = new LluDanceAlarmReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(LluDanceAlarmHelper.DANCE_ORDER_TIME_ALARM_ACTION);
        intentFilter.addAction(LluDanceAlarmHelper.ACTIVITY_CHANGED_RECEIVER_ACTION);
        try {
            LluDanceAlarmReceiver lluDanceAlarmReceiver = this.mLluDanceAlarmReceiver;
            if (lluDanceAlarmReceiver != null) {
                registerReceiver(lluDanceAlarmReceiver, intentFilter);
            } else {
                com.xiaopeng.carcontrol.util.LogUtils.d(TAG, "register dance alarm receiver but receiver is null , is main process = " + isMainProcess());
            }
        } catch (IllegalArgumentException e) {
            com.xiaopeng.carcontrol.util.LogUtils.d(TAG, "register dance alarm receiver IllegalArgumentException , is main process = " + isMainProcess());
            e.printStackTrace();
        }
    }
}
