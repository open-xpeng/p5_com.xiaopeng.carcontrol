package com.xiaopeng.carcontrol;

import android.app.Application;
import android.car.Car;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.Settings;
import com.xiaopeng.carcontrol.GlobalConstant;
import com.xiaopeng.carcontrol.carmanager.CarClientWrapper;
import com.xiaopeng.carcontrol.carmanager.XuiClientWrapper;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.config.DxCarConfig;
import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import com.xiaopeng.carcontrol.helper.SoundHelper;
import com.xiaopeng.carcontrol.helper.SpeechHelper;
import com.xiaopeng.carcontrol.model.DataSyncModel;
import com.xiaopeng.carcontrol.model.SharedPreferenceUtil;
import com.xiaopeng.carcontrol.provider.CarControl;
import com.xiaopeng.carcontrol.quicksetting.QuickSettingManager;
import com.xiaopeng.carcontrol.sdkImpl.CarBodyManagerImpl;
import com.xiaopeng.carcontrol.sdkImpl.LampManagerImpl;
import com.xiaopeng.carcontrol.sdkImpl.SeatManagerImpl;
import com.xiaopeng.carcontrol.sdkImpl.WindowControlManagerImpl;
import com.xiaopeng.carcontrol.util.CarStatusUtils;
import com.xiaopeng.carcontrol.util.ContextUtils;
import com.xiaopeng.carcontrol.util.EnvironmentUtils;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.NetworkUtil;
import com.xiaopeng.carcontrol.util.ResUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.carcontrol.viewmodel.avas.AvasSmartControl;
import com.xiaopeng.carcontrol.viewmodel.avas.IAvasViewModel;
import com.xiaopeng.carcontrol.viewmodel.cabin.SeatSmartControl;
import com.xiaopeng.carcontrol.viewmodel.selfcheck.SelfCheckUtil;
import com.xiaopeng.carcontrol.viewmodel.service.ShowCarControl;
import com.xiaopeng.lib.bughunter.BugHunter;
import com.xiaopeng.lib.bughunter.StartPerfUtils;
import com.xiaopeng.lib.framework.aiassistantmodule.AiAssistantModuleEntry;
import com.xiaopeng.lib.framework.module.Module;
import com.xiaopeng.lludancemanager.util.ResUtil;
import com.xiaopeng.smartcontrol.sdk.server.CarControlSDKImpl;
import com.xiaopeng.speech.SpeechClient;
import com.xiaopeng.xui.Xui;
import com.xiaopeng.xvs.xid.XId;
import java.io.File;

/* loaded from: classes.dex */
public abstract class App extends Application {
    private static final String CAR_IMG_TOP_SUFFIX = "_top";
    private static final String CAR_PROP_HANDLE_THREAD = "CarPropHandleThread";
    private static final String DRIVE_PAGE_CAR_IMG_PREFIX = "img_drive_carbody_color_";
    private static final int LOG_TIME = 600000;
    private static final String MAIN_PAGE_CAR_IMG_PREFIX = "img_main_carbody_color_";
    public static final String TAG = "CarControlApp";
    private static boolean mIsSpFileExistAtStartup = false;
    protected static App sAppInstance;
    protected static Boolean sIsMainProcess;

    protected void initSsDoorDetection() {
    }

    protected void initSyncFunction() {
    }

    abstract void onAppCreate();

    abstract void preloadControlPanel();

    protected void reloadControlPanel(Configuration config) {
    }

    protected void startDemoService() {
    }

    public static App getInstance() {
        return sAppInstance;
    }

    public static boolean isMainProcess() {
        if (sIsMainProcess == null) {
            sIsMainProcess = Boolean.valueOf((CarBaseConfig.getInstance().isSupportUnity3D() && getProcessName().contains("unity")) ? false : true);
        }
        return sIsMainProcess.booleanValue();
    }

    public static boolean getSpStateAtStartup() {
        return mIsSpFileExistAtStartup;
    }

    @Override // android.app.Application
    public void onCreate() {
        super.onCreate();
        LogUtils.init(this);
        LogUtils.d(TAG, "onCreate", false);
        sAppInstance = this;
        init();
    }

    public void init() {
        ContextUtils.init(this);
        StartPerfUtils.appOnCreateBegin();
        LogUtils.setProcessName(isMainProcess());
        isMainProcess();
        HandlerThread handlerThread = new HandlerThread(CAR_PROP_HANDLE_THREAD);
        handlerThread.setPriority(isMainProcess() ? 5 : 1);
        handlerThread.start();
        CarClientWrapper.getInstance().connectToCar(new Handler(handlerThread.getLooper()));
        XuiClientWrapper.getInstance().connectToXui();
        initBugHunter();
        Xui.init(this);
        Xui.setFontScaleDynamicChangeEnable(true);
        onAppCreate();
        if (isMainProcess()) {
            File sharedPreferencesPath = getSharedPreferencesPath(SharedPreferenceUtil.PREF_FILE_NAME);
            if (sharedPreferencesPath != null && sharedPreferencesPath.exists()) {
                mIsSpFileExistAtStartup = true;
            }
            LogUtils.i(TAG, "mIsSpFileExistAtStartup = " + mIsSpFileExistAtStartup, false);
            printVersionInfo();
            registerModules();
            NetworkUtil.init(this);
            initSync();
            ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.-$$Lambda$App$c2HRlWeiJu2Z5ZTR5QIX19E1eRk
                @Override // java.lang.Runnable
                public final void run() {
                    App.this.lambda$init$1$App();
                }
            });
        } else {
            initXid();
            ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.-$$Lambda$App$_l65-bL79VUMNHzjqOwlVXLNc3U
                @Override // java.lang.Runnable
                public final void run() {
                    App.this.lambda$init$2$App();
                }
            });
        }
        SoundHelper.init();
        SpeechHelper.getInstance().initSpeechService();
        StartPerfUtils.appOnCreateEnd();
    }

    public /* synthetic */ void lambda$init$1$App() {
        LogUtils.d(TAG, "Check if car service connected");
        if (CarClientWrapper.getInstance().isCarServiceConnected()) {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                LogUtils.e(TAG, "Wait car service connect exception: " + e.getMessage());
            }
            QuickSettingManager.getInstance().init();
            if (BaseFeatureOption.getInstance().isSupportNewSelfCheckArch()) {
                SelfCheckUtil.startMonitor();
            }
            observeSettingLowSpdEnable();
            observeSettingFollowedVehicleLostConfig();
            SpeechClient.instance().init(this);
            SpeechClient.instance().setAppName(ResUtils.getString(com.xiaopeng.carcontrolmodule.R.string.app_car_control), ResUtils.getString(com.xiaopeng.carcontrolmodule.R.string.app_car_control_hint));
            ShowCarControl.getInstance().registerResolver();
            startService(new Intent(this, CarControlService.class));
            preloadControlPanel();
            startDemoService();
            if (BaseFeatureOption.getInstance().isSupportLluSyncShow()) {
                initSyncFunction();
            }
            if (CarBaseConfig.getInstance().isSupportSdc()) {
                initSsDoorDetection();
            }
            initSmartControlSDK();
        }
    }

    public /* synthetic */ void lambda$init$2$App() {
        LogUtils.d(TAG, "Check if car service connected");
        if (CarClientWrapper.getInstance().isCarServiceConnected()) {
            observeSettingFollowedVehicleLostConfig();
        }
    }

    protected void initBugHunter() {
        BugHunter.init(this);
    }

    private void initSmartControlSDK() {
        if (BaseFeatureOption.getInstance().isSupportCarControlSDK()) {
            CarControlSDKImpl.getInstance().init(this);
            CarControlSDKImpl.getInstance().registerSeatImpl(new SeatManagerImpl());
            CarControlSDKImpl.getInstance().registerCarBodyImpl(new CarBodyManagerImpl());
            CarControlSDKImpl.getInstance().registerLampImpl(new LampManagerImpl());
            CarControlSDKImpl.getInstance().registerWindowImpl(new WindowControlManagerImpl());
        }
    }

    @Override // android.app.Application
    public void onTerminate() {
        CarClientWrapper.getInstance().disconnect();
        if (LogUtils.isLogBackUp()) {
            LogUtils.setLogBackUp(false);
        }
        super.onTerminate();
    }

    public void notifyPreloadComplete(boolean isReady) {
        CarControl.System.putBool(getContentResolver(), CarControl.System.CAR_CONTROL_READY, isReady);
        LogUtils.d(TAG, "notifyPreloadComplete " + isReady, false);
        setUnityPreload(false);
    }

    private void printVersionInfo() {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("\n").append("====print CarControl information====").append("\n");
            sb.append("swVersion : ").append(Build.DISPLAY).append("\n");
            sb.append("hwVersion : ").append(Car.getHardwareVersion()).append("\n");
            sb.append("vehicleType : ").append(Car.getHardwareCarType()).append("\n");
            sb.append("cduType : ").append(Car.getXpCduType()).append("\n");
            sb.append("buildVersion : ").append(com.xiaopeng.carcontrolmodule.BuildConfig.BUILD_VERSION).append("\n");
            try {
                sb.append("app version : ").append(getPackageManager().getPackageInfo(getPackageName(), 0).versionName).append("\n");
            } catch (Exception e) {
                LogUtils.e(TAG, e.getMessage(), false);
            }
            LogUtils.d(TAG, sb.toString(), false);
        } catch (Exception e2) {
            LogUtils.e(TAG, e2.getMessage(), false);
        }
    }

    private void registerModules() {
        Module.register(AiAssistantModuleEntry.class, new AiAssistantModuleEntry(getApplicationContext()));
    }

    private void initSync() {
        initXid();
        DataSyncModel.getInstance().init();
    }

    private void initXid() {
        XId.init(this, "xp_car_setting_car", EnvironmentUtils.getAppSecret(), CarStatusUtils.getProductModel());
    }

    private void observeSettingLowSpdEnable() {
        getContentResolver().registerContentObserver(Settings.System.getUriFor(IAvasViewModel.SETTING_KEY_LOW_SPD_SW), true, new ContentObserver(ThreadUtils.getHandler(0)) { // from class: com.xiaopeng.carcontrol.App.1
            @Override // android.database.ContentObserver
            public void onChange(boolean selfChange) {
                AvasSmartControl.getInstance().onLowSpdSwChanged();
            }
        });
    }

    private void observeSettingFollowedVehicleLostConfig() {
        getContentResolver().registerContentObserver(Settings.Secure.getUriFor(GlobalConstant.GLOBAL.FOLLOWED_VEHICLE_LOST_CONFIG), true, new ContentObserver(ThreadUtils.getHandler(0)) { // from class: com.xiaopeng.carcontrol.App.2
            @Override // android.database.ContentObserver
            public void onChange(boolean selfChange) {
                SeatSmartControl.getInstance().onFollowedVehicleLostConfigChanged(false);
            }
        });
    }

    public boolean isUnityPreload() {
        return CarControl.PrivateControl.getBool(getContentResolver(), CarControl.PrivateControl.UNITY_PRELOAD, false);
    }

    public void setUnityPreload(boolean value) {
        CarControl.PrivateControl.putBool(getContentResolver(), CarControl.PrivateControl.UNITY_PRELOAD, value);
    }

    public void reconnectToResourceManager() {
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.-$$Lambda$App$i6pvYllCsB1mxZNzEP-w9urLPFk
            @Override // java.lang.Runnable
            public final void run() {
                XuiClientWrapper.getInstance().connectToResourceManager();
            }
        });
    }

    @Override // android.app.Application, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public static int getMainControlPageCarBodyImg() {
        boolean isSupportTopCamera = DxCarConfig.getInstance().isSupportTopCamera();
        int bodyColor = DxCarConfig.getBodyColor();
        String sb = (isSupportTopCamera ? new StringBuilder().append(MAIN_PAGE_CAR_IMG_PREFIX).append(bodyColor).append(CAR_IMG_TOP_SUFFIX) : new StringBuilder().append(MAIN_PAGE_CAR_IMG_PREFIX).append(bodyColor)).toString();
        int identifier = getInstance().getResources().getIdentifier(sb, ResUtil.DRAWABLE, getInstance().getPackageName());
        LogUtils.d(TAG, "colorCode: " + bodyColor + ", res name: " + sb + " , MainControlPageCarBodyImg: " + identifier, false);
        if (identifier == 0) {
            identifier = com.xiaopeng.carcontrolmodule.R.drawable.img_main_carbody_color_1;
        }
        LogUtils.d(TAG, "colorCode: " + bodyColor + ", MainControlPageCarBodyImg: " + identifier);
        return identifier;
    }
}
