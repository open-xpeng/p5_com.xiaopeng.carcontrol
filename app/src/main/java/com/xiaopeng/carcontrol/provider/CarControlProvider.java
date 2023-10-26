package com.xiaopeng.carcontrol.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.SparseArray;
import com.alibaba.fastjson.parser.JSONLexer;
import com.xiaopeng.carcontrol.carmanager.CarClientWrapper;
import com.xiaopeng.carcontrol.carmanager.XuiClientWrapper;
import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import com.xiaopeng.carcontrol.model.DataSyncModel;
import com.xiaopeng.carcontrol.model.DebugFuncModel;
import com.xiaopeng.carcontrol.model.FunctionModel;
import com.xiaopeng.carcontrol.model.SharedPreferenceUtil;
import com.xiaopeng.carcontrol.provider.CarControl;
import com.xiaopeng.carcontrol.provider.SettingsState;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.carcontrol.view.dialog.dropdownmenu.DropDownMenuManager;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;
import com.xiaopeng.carcontrol.viewmodel.cabin.CabinSmartControl;
import com.xiaopeng.carcontrol.viewmodel.cabin.SeatSmartControl;
import com.xiaopeng.carcontrol.viewmodel.carsettings.CarBodySmartControl;
import com.xiaopeng.carcontrol.viewmodel.chassis.ChassisSmartControl;
import com.xiaopeng.carcontrol.viewmodel.lamp.LampSmartControl;
import com.xiaopeng.carcontrol.viewmodel.service.IServiceViewModel;
import com.xiaopeng.carcontrol.viewmodel.vcu.VcuSmartControl;

/* loaded from: classes2.dex */
public class CarControlProvider extends ContentProvider {
    private static final String LOG_TAG = "CarControlProvider";
    private static final Bundle NULL_SETTING_BUNDLE;
    public static final int SETTINGS_TYPE_PRIVATE = 2;
    public static final int SETTINGS_TYPE_QUICK = 0;
    public static final int SETTINGS_TYPE_SYSTEM = 1;
    private DataSyncModel mDataSync;
    private IServiceViewModel mServiceVm;
    private SettingsRegistry mSettingsRegistry;
    private final Object mLock = new Object();
    private volatile boolean mIsServiceVmInit = false;

    @Override // android.content.ContentProvider
    public String getType(Uri uri) {
        return null;
    }

    @Override // android.content.ContentProvider
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Override // android.content.ContentProvider
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    static {
        Bundle bundle = new Bundle();
        NULL_SETTING_BUNDLE = bundle;
        bundle.putString("value", null);
    }

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        synchronized (this.mLock) {
            this.mSettingsRegistry = new SettingsRegistry();
        }
        SharedPreferenceUtil.initSharedPreferences(getContext());
        this.mDataSync = DataSyncModel.getInstance();
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.provider.-$$Lambda$CarControlProvider$BKYrVBjeFmxi1ai_k3EuLGxb3fM
            @Override // java.lang.Runnable
            public final void run() {
                CarControlProvider.this.lambda$onCreate$0$CarControlProvider();
            }
        });
        return true;
    }

    public /* synthetic */ void lambda$onCreate$0$CarControlProvider() {
        if (CarClientWrapper.getInstance().isCarServiceConnected()) {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                LogUtils.e(LOG_TAG, "Wait car service connect occurs error: ", (Throwable) e, false);
            }
            this.mServiceVm = (IServiceViewModel) ViewModelManager.getInstance().getViewModelImpl(IServiceViewModel.class);
            this.mIsServiceVmInit = true;
        }
    }

    @Override // android.content.ContentProvider
    public Uri insert(Uri uri, ContentValues values) {
        throw new UnsupportedOperationException("No external insert");
    }

    @Override // android.content.ContentProvider
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException("No external delete");
    }

    @Override // android.content.ContentProvider
    public Bundle call(String method, String name, Bundle extras) {
        method.hashCode();
        char c = 65535;
        switch (method.hashCode()) {
            case -1004559309:
                if (method.equals("PUT_private")) {
                    c = 0;
                    break;
                }
                break;
            case -683831139:
                if (method.equals("PUT_quick")) {
                    c = 1;
                    break;
                }
                break;
            case 337337727:
                if (method.equals("PUT_system")) {
                    c = 2;
                    break;
                }
                break;
            case 583195556:
                if (method.equals("GET_quick")) {
                    c = 3;
                    break;
                }
                break;
            case 960459608:
                if (method.equals("GET_system")) {
                    c = 4;
                    break;
                }
                break;
            case 1132349818:
                if (method.equals("GET_private")) {
                    c = 5;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                insertPrivateSetting(name, getSettingValue(extras));
                return null;
            case 1:
                insertQuickSetting(name, getSettingValue(extras));
                return null;
            case 2:
                insertSystemSetting(name, getSettingValue(extras));
                return null;
            case 3:
                return getQuickSetting(name);
            case 4:
                return getSystemSetting(name);
            case 5:
                return getPrivateSetting(name);
            default:
                LogUtils.w(LOG_TAG, "call() with invalid method: " + method);
                return null;
        }
    }

    private Bundle packageValueForCallResult(SettingsState.Setting setting) {
        if (setting == null || setting.isNull()) {
            return NULL_SETTING_BUNDLE;
        }
        Bundle bundle = new Bundle();
        bundle.putString("value", !setting.isNull() ? setting.getValue() : null);
        return bundle;
    }

    private Bundle packageValueForCallResult(String value) {
        if (value == null) {
            return NULL_SETTING_BUNDLE;
        }
        Bundle bundle = new Bundle();
        bundle.putString("value", value);
        return bundle;
    }

    private Bundle getSystemSetting(String name) {
        Bundle packageValueForCallResult;
        if (name == null) {
            return null;
        }
        name.hashCode();
        char c = 65535;
        switch (name.hashCode()) {
            case -1805405542:
                if (name.equals(CarControl.System.HVAC_PSN_TEMP_SET)) {
                    c = 0;
                    break;
                }
                break;
            case -1465325475:
                if (name.equals(CarControl.System.HVAC_BACK_DEFROST_SET)) {
                    c = 1;
                    break;
                }
                break;
            case -687444191:
                if (name.equals(CarControl.System.HVAC_FRONT_DEFROST_SET)) {
                    c = 2;
                    break;
                }
                break;
            case 9429924:
                if (name.equals(CarControl.System.HVAC_DRV_SYNC_SET)) {
                    c = 3;
                    break;
                }
                break;
            case 182851161:
                if (name.equals(CarControl.System.HVAC_POWER_SET)) {
                    c = 4;
                    break;
                }
                break;
            case 765246168:
                if (name.equals(CarControl.System.CENTRAL_LOCK_SET)) {
                    c = 5;
                    break;
                }
                break;
            case 1165412445:
                if (name.equals(CarControl.System.HVAC_DRV_TEMP_SET)) {
                    c = 6;
                    break;
                }
                break;
            case 1333579233:
                if (name.equals(CarControl.System.HVAC_PSN_SYNC_SET)) {
                    c = 7;
                    break;
                }
                break;
            case 2075545307:
                if (name.equals(CarControl.System.DRIVE_MODE_SET)) {
                    c = '\b';
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case '\b':
                LogUtils.e(LOG_TAG, "Key: " + name + " is not readable", false);
                return null;
            default:
                synchronized (this.mLock) {
                    packageValueForCallResult = packageValueForCallResult(this.mSettingsRegistry.getSettingLocked(1, name));
                }
                return packageValueForCallResult;
        }
    }

    private Bundle getQuickSetting(String name) {
        Bundle packageValueForCallResult;
        if (name == null) {
            return null;
        }
        name.hashCode();
        if (name.equals(CarControl.Quick.MIRROR_FOLD) || name.equals(CarControl.Quick.OPEN_REAR_TRUNK)) {
            LogUtils.e(LOG_TAG, "Key: " + name + " is not readable", false);
            return null;
        }
        synchronized (this.mLock) {
            packageValueForCallResult = packageValueForCallResult(this.mSettingsRegistry.getSettingLocked(0, name));
        }
        return packageValueForCallResult;
    }

    private Bundle getPrivateSetting(String name) {
        Bundle packageValueForCallResult;
        if (name == null) {
            return null;
        }
        name.hashCode();
        char c = 65535;
        switch (name.hashCode()) {
            case -1948915804:
                if (name.equals(CarControl.PrivateControl.APA_SAFE_EXAM_RESULT)) {
                    c = 0;
                    break;
                }
                break;
            case -1876670281:
                if (name.equals(CarControl.PrivateControl.LCC_VIDEO_WATCH_STATE)) {
                    c = 1;
                    break;
                }
                break;
            case -1858392354:
                if (name.equals(CarControl.PrivateControl.LCC_SAFE_EXAM_RESULT)) {
                    c = 2;
                    break;
                }
                break;
            case -1472065136:
                if (name.equals(CarControl.PrivateControl.NORMAL_DRIVE_MODE)) {
                    c = 3;
                    break;
                }
                break;
            case -1405770928:
                if (name.equals(CarControl.PrivateControl.AVH_SW)) {
                    c = 4;
                    break;
                }
                break;
            case -1293786559:
                if (name.equals(CarControl.PrivateControl.ESP_SW)) {
                    c = 5;
                    break;
                }
                break;
            case -1272884516:
                if (name.equals(CarControl.PrivateControl.UNLOCK_RESPONSE)) {
                    c = 6;
                    break;
                }
                break;
            case -881156614:
                if (name.equals(CarControl.PrivateControl.USER_DEFINE_DRIVE_MODE)) {
                    c = 7;
                    break;
                }
                break;
            case -816122768:
                if (name.equals(CarControl.PrivateControl.SPEC_DRIVE_MODE_VALUE)) {
                    c = '\b';
                    break;
                }
                break;
            case -710601245:
                if (name.equals(CarControl.PrivateControl.REAR_SEAT_WELCOME_SW)) {
                    c = '\t';
                    break;
                }
                break;
            case -380486374:
                if (name.equals(CarControl.PrivateControl.SUPER_LCC_SAFE_EXAM_RESULT)) {
                    c = '\n';
                    break;
                }
                break;
            case -374920237:
                if (name.equals(CarControl.PrivateControl.USER_DEFINE_DRIVE_MODE_INFO)) {
                    c = 11;
                    break;
                }
                break;
            case -127570606:
                if (name.equals(CarControl.PrivateControl.SET_NRA_STATE)) {
                    c = '\f';
                    break;
                }
                break;
            case -82532238:
                if (name.equals(CarControl.PrivateControl.DRIVE_MODE_USER)) {
                    c = '\r';
                    break;
                }
                break;
            case 100724:
                if (name.equals(CarControl.PrivateControl.ESB)) {
                    c = 14;
                    break;
                }
                break;
            case 33581987:
                if (name.equals(CarControl.PrivateControl.BOOT_EFFECT_BEFORE_SW)) {
                    c = 15;
                    break;
                }
                break;
            case 480568617:
                if (name.equals(CarControl.PrivateControl.NGP_SAFE_EXAM_RESULT)) {
                    c = 16;
                    break;
                }
                break;
            case 577035373:
                if (name.equals(CarControl.PrivateControl.CAR_LICENSE_PLATE)) {
                    c = 17;
                    break;
                }
                break;
            case 620543222:
                if (name.equals(CarControl.PrivateControl.AVAS_EXTERNAL_VOLUME)) {
                    c = 18;
                    break;
                }
                break;
            case 635053862:
                if (name.equals(CarControl.PrivateControl.MEM_PARK_SAFE_EXAM_RESULT)) {
                    c = 19;
                    break;
                }
                break;
            case 863973952:
                if (name.equals(CarControl.PrivateControl.AVAS_EXTERNAL_SW)) {
                    c = 20;
                    break;
                }
                break;
            case 1038558861:
                if (name.equals(CarControl.PrivateControl.WIN_HIGH_SPD)) {
                    c = 21;
                    break;
                }
                break;
            case 1350425855:
                if (name.equals(CarControl.PrivateControl.MEM_PARK_VIDEO_WATCH_STATE)) {
                    c = 22;
                    break;
                }
                break;
            case 1646433897:
                if (name.equals(CarControl.PrivateControl.NEW_DRIVE_MODE_VALUE)) {
                    c = 23;
                    break;
                }
                break;
            case 1698370588:
                if (name.equals(CarControl.PrivateControl.PSN_SEAT_WELCOME_SW)) {
                    c = 24;
                    break;
                }
                break;
            case 1803969973:
                if (name.equals(CarControl.PrivateControl.WIPER_SENSITIVE_LEVEL)) {
                    c = 25;
                    break;
                }
                break;
            case 1957461621:
                if (name.equals(CarControl.PrivateControl.SUPER_VPA_SAFE_EXAM_RESULT)) {
                    c = JSONLexer.EOI;
                    break;
                }
                break;
            case 1969872294:
                if (name.equals(CarControl.PrivateControl.CNGP_SAFE_EXAM_RESULT)) {
                    c = 27;
                    break;
                }
                break;
            case 1981740357:
                if (name.equals(CarControl.PrivateControl.DRV_SEAT_CHECK)) {
                    c = 28;
                    break;
                }
                break;
            case 2093683332:
                if (name.equals(CarControl.PrivateControl.XPILOT_ISLA_SW)) {
                    c = 29;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                return packageValueForCallResult(Integer.toString(this.mDataSync.getApaSafeExamResult() ? 1 : 0));
            case 1:
                return packageValueForCallResult(Integer.toString(this.mDataSync.isLccVideoWatched() ? 1 : 0));
            case 2:
                return packageValueForCallResult(Integer.toString(this.mDataSync.getLccSafeExamResult() ? 1 : 0));
            case 3:
                return packageValueForCallResult(Integer.toString(this.mDataSync.getNormalDriveModeEnable() ? 1 : 0));
            case 4:
                return packageValueForCallResult(Integer.toString(this.mDataSync.getAvh() ? 1 : 0));
            case 5:
                return packageValueForCallResult(Integer.toString(this.mDataSync.getEsp() ? 1 : 0));
            case 6:
                return packageValueForCallResult(Integer.toString(this.mDataSync.getUnlockResponse()));
            case 7:
                return packageValueForCallResult(Integer.toString(this.mDataSync.getUserDefineDriveMode()));
            case '\b':
                return packageValueForCallResult(Integer.toString(this.mDataSync.getSpecialDriveMode()));
            case '\t':
                return packageValueForCallResult(Integer.toString(this.mDataSync.getRearSeatWelcomeMode() ? 1 : 0));
            case '\n':
                return packageValueForCallResult(Integer.toString(this.mDataSync.getSuperLccSafeExamResult() ? 1 : 0));
            case 11:
                return packageValueForCallResult(this.mDataSync.getUserDefineDriveModeInfo());
            case '\f':
                return packageValueForCallResult(Integer.toString(this.mDataSync.getNraState()));
            case '\r':
                return packageValueForCallResult(Integer.toString(FunctionModel.getInstance().isDriveModeChangedByUser() ? 1 : 0));
            case 14:
                return packageValueForCallResult(Integer.toString(this.mDataSync.getDrvSeatEsb() ? 1 : 0));
            case 15:
                return packageValueForCallResult(Integer.toString(this.mDataSync.getBootEffectBeforeSw()));
            case 16:
                return packageValueForCallResult(Integer.toString(this.mDataSync.getNgpSafeExamResult() ? 1 : 0));
            case 17:
                return packageValueForCallResult(this.mDataSync.getCarLicensePlate());
            case 18:
                return packageValueForCallResult(Integer.toString(this.mDataSync.getAvasOtherVolume()));
            case 19:
                return packageValueForCallResult(Integer.toString(this.mDataSync.getMemParkSafeExamResult() ? 1 : 0));
            case 20:
                return packageValueForCallResult(Integer.toString(DebugFuncModel.getInstance().isAvasExternalSwEnabled() ? 1 : 0));
            case 21:
                return packageValueForCallResult(Integer.toString(this.mDataSync.isWinHighSpdEnabled() ? 1 : 0));
            case 22:
                return packageValueForCallResult(Integer.toString(this.mDataSync.isMemParkVideoWatched() ? 1 : 0));
            case 23:
                return packageValueForCallResult(Integer.toString(this.mDataSync.getDriveMode()));
            case 24:
                return packageValueForCallResult(Integer.toString(this.mDataSync.getPsnWelcomeMode() ? 1 : 0));
            case 25:
                return packageValueForCallResult(Integer.toString(this.mDataSync.getWiperSensitivity()));
            case 26:
                return packageValueForCallResult(Integer.toString(this.mDataSync.getSuperVpaSafeExamResult() ? 1 : 0));
            case 27:
                return packageValueForCallResult(Integer.toString(this.mDataSync.getCngpSafeExamResult() ? 1 : 0));
            case 28:
                return packageValueForCallResult(Integer.toString(SeatSmartControl.getInstance().isDrvSeatEqualMemory() ? 1 : 0));
            case 29:
                return packageValueForCallResult(Integer.toString(this.mDataSync.getIslaSw()));
            default:
                synchronized (this.mLock) {
                    packageValueForCallResult = packageValueForCallResult(this.mSettingsRegistry.getSettingLocked(2, name));
                }
                return packageValueForCallResult;
        }
    }

    private boolean insertQuickSetting(String name, String value) {
        validateSettingValue(name, value);
        return this.mSettingsRegistry.updateQuickControlLocked(name, value, false, false);
    }

    private boolean insertSystemSetting(String name, String value) {
        validateSettingValue(name, value);
        return this.mSettingsRegistry.updateSystemControlLocked(name, value, false, false);
    }

    private boolean insertPrivateSetting(String name, String value) {
        validateSettingValue(name, value);
        return this.mSettingsRegistry.updatePrivateControlLocked(name, value, false, false);
    }

    private void validateSettingValue(String name, String value) {
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(value)) {
            throw new IllegalArgumentException("Invalid value: " + value + " for setting: " + name);
        }
    }

    private static String getSettingValue(Bundle args) {
        if (args != null) {
            return args.getString("value");
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public final class SettingsRegistry {
        private final SparseArray<SettingsState> mSettingsStates = new SparseArray<>();
        private final Handler mHandler = new MyHandler(ThreadUtils.getHandler(2).getLooper());

        SettingsRegistry() {
        }

        boolean ensureSettingsLocked() {
            ensureSettingsStateLocked(0);
            ensureSettingsStateLocked(1);
            ensureSettingsStateLocked(2);
            return true;
        }

        private SettingsState peekSettingsStateLocked(int key) {
            SettingsState settingsState = this.mSettingsStates.get(key);
            if (settingsState != null) {
                return settingsState;
            }
            if (ensureSettingsLocked()) {
                return this.mSettingsStates.get(key);
            }
            return null;
        }

        public SettingsState getSettingsLocked(int type) {
            return peekSettingsStateLocked(type);
        }

        private void ensureSettingsStateLocked(int key) {
            if (this.mSettingsStates.get(key) == null) {
                this.mSettingsStates.put(key, new SettingsState(key));
            }
        }

        boolean updateSystemControlLocked(String name, String value, boolean makeDefault, boolean forceNotify) {
            if (BaseFeatureOption.getInstance().isSupportSmartOSFive() && name.contains("hvac")) {
                return true;
            }
            if (!CarControl.System.HVAC_PM25.equals(name)) {
                LogUtils.debug(CarControlProvider.LOG_TAG, "updateSystemControlLocked name: " + name + ", value: " + value);
            }
            name.hashCode();
            char c = 65535;
            switch (name.hashCode()) {
                case -1805405542:
                    if (name.equals(CarControl.System.HVAC_PSN_TEMP_SET)) {
                        c = 0;
                        break;
                    }
                    break;
                case -1569397420:
                    if (name.equals(CarControl.System.HVAC_DRV_TEMP_STEP_SET)) {
                        c = 1;
                        break;
                    }
                    break;
                case -1465325475:
                    if (name.equals(CarControl.System.HVAC_BACK_DEFROST_SET)) {
                        c = 2;
                        break;
                    }
                    break;
                case -687444191:
                    if (name.equals(CarControl.System.HVAC_FRONT_DEFROST_SET)) {
                        c = 3;
                        break;
                    }
                    break;
                case 9429924:
                    if (name.equals(CarControl.System.HVAC_DRV_SYNC_SET)) {
                        c = 4;
                        break;
                    }
                    break;
                case 182851161:
                    if (name.equals(CarControl.System.HVAC_POWER_SET)) {
                        c = 5;
                        break;
                    }
                    break;
                case 765246168:
                    if (name.equals(CarControl.System.CENTRAL_LOCK_SET)) {
                        c = 6;
                        break;
                    }
                    break;
                case 1165412445:
                    if (name.equals(CarControl.System.HVAC_DRV_TEMP_SET)) {
                        c = 7;
                        break;
                    }
                    break;
                case 1236238596:
                    if (name.equals(CarControl.System.CAR_CONTROL_READY)) {
                        c = '\b';
                        break;
                    }
                    break;
                case 1333579233:
                    if (name.equals(CarControl.System.HVAC_PSN_SYNC_SET)) {
                        c = '\t';
                        break;
                    }
                    break;
                case 2075545307:
                    if (name.equals(CarControl.System.DRIVE_MODE_SET)) {
                        c = '\n';
                        break;
                    }
                    break;
                case 2099370295:
                    if (name.equals(CarControl.System.HVAC_PSN_TEMP_STEP_SET)) {
                        c = 11;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    if (CarControlProvider.this.mIsServiceVmInit && CarControlProvider.this.mServiceVm != null) {
                        try {
                            CarControlProvider.this.mServiceVm.setHvacTempPsn(Float.parseFloat(value));
                            return true;
                        } catch (NumberFormatException unused) {
                            LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                            break;
                        }
                    } else {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Service ViewModel not initialized");
                        break;
                    }
                case 1:
                    if (CarControlProvider.this.mIsServiceVmInit && CarControlProvider.this.mServiceVm != null) {
                        try {
                            CarControlProvider.this.mServiceVm.setHvacTempDrvStep(Integer.parseInt(value) == 1);
                            return true;
                        } catch (NumberFormatException unused2) {
                            LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                            break;
                        }
                    } else {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Service ViewModel not initialized");
                        break;
                    }
                case 2:
                    if (CarControlProvider.this.mIsServiceVmInit && CarControlProvider.this.mServiceVm != null) {
                        try {
                            CarControlProvider.this.mServiceVm.setMirrorHeatEnable(Integer.parseInt(value) == 1);
                            return true;
                        } catch (NumberFormatException unused3) {
                            LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                            break;
                        }
                    } else {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Service ViewModel not initialized");
                        break;
                    }
                    break;
                case 3:
                    if (CarControlProvider.this.mIsServiceVmInit && CarControlProvider.this.mServiceVm != null) {
                        try {
                            CarControlProvider.this.mServiceVm.setHvacFrontDefrost(Integer.parseInt(value) == 1);
                            return true;
                        } catch (NumberFormatException unused4) {
                            LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                            break;
                        }
                    } else {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Service ViewModel not initialized");
                        break;
                    }
                case 4:
                    if (CarControlProvider.this.mIsServiceVmInit && CarControlProvider.this.mServiceVm != null) {
                        try {
                            CarControlProvider.this.mServiceVm.setHvacDriverSyncMode(Integer.parseInt(value) == 1);
                            return true;
                        } catch (NumberFormatException unused5) {
                            LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                            break;
                        }
                    } else {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Service ViewModel not initialized");
                        break;
                    }
                    break;
                case 5:
                    if (CarControlProvider.this.mIsServiceVmInit && CarControlProvider.this.mServiceVm != null) {
                        try {
                            CarControlProvider.this.mServiceVm.setHvacPowerMode(Integer.parseInt(value) == 1);
                            return true;
                        } catch (NumberFormatException unused6) {
                            LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                            break;
                        }
                    } else {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Service ViewModel not initialized");
                        break;
                    }
                case 6:
                    if (CarControlProvider.this.mIsServiceVmInit && CarControlProvider.this.mServiceVm != null) {
                        try {
                            CarControlProvider.this.mServiceVm.setCentralLock(Integer.parseInt(value) == 1);
                            return true;
                        } catch (NumberFormatException unused7) {
                            LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                            break;
                        }
                    } else {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Service ViewModel not initialized");
                        break;
                    }
                case 7:
                    if (CarControlProvider.this.mIsServiceVmInit && CarControlProvider.this.mServiceVm != null) {
                        try {
                            CarControlProvider.this.mServiceVm.setHvacTempDriver(Float.parseFloat(value));
                            return true;
                        } catch (NumberFormatException unused8) {
                            LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                            break;
                        }
                    } else {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Service ViewModel not initialized");
                        break;
                    }
                    break;
                case '\b':
                    return insertSettingLocked(1, name, value, makeDefault, forceNotify);
                case '\t':
                    if (CarControlProvider.this.mIsServiceVmInit && CarControlProvider.this.mServiceVm != null) {
                        try {
                            CarControlProvider.this.mServiceVm.setHvacPsnSyncMode(Integer.parseInt(value) == 1);
                            return true;
                        } catch (NumberFormatException unused9) {
                            LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                            break;
                        }
                    } else {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Service ViewModel not initialized");
                        break;
                    }
                case '\n':
                    if (CarControlProvider.this.mIsServiceVmInit && CarControlProvider.this.mServiceVm != null) {
                        try {
                            CarControlProvider.this.mServiceVm.setDriveMode(Integer.parseInt(value));
                            return true;
                        } catch (NumberFormatException unused10) {
                            LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                            break;
                        }
                    } else {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Service ViewModel not initialized");
                        break;
                    }
                case 11:
                    if (CarControlProvider.this.mIsServiceVmInit && CarControlProvider.this.mServiceVm != null) {
                        try {
                            CarControlProvider.this.mServiceVm.setHvacTempPsnStep(Integer.parseInt(value) == 1);
                            return true;
                        } catch (NumberFormatException unused11) {
                            LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                            break;
                        }
                    } else {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Service ViewModel not initialized");
                        break;
                    }
                default:
                    return insertSettingLocked(1, name, value, makeDefault, forceNotify);
            }
            return false;
        }

        boolean updateQuickControlLocked(String name, String value, boolean makeDefault, boolean forceNotify) {
            LogUtils.debug(CarControlProvider.LOG_TAG, "updateQuickControlLocked name: " + name + ", value: " + value);
            name.hashCode();
            char c = 65535;
            switch (name.hashCode()) {
                case -2062838391:
                    if (name.equals(CarControl.Quick.RIGHT_CHARGE_PORT)) {
                        c = 0;
                        break;
                    }
                    break;
                case -1888240580:
                    if (name.equals(CarControl.Quick.RESET_CHARGE_PORT)) {
                        c = 1;
                        break;
                    }
                    break;
                case -1744447359:
                    if (name.equals(CarControl.Quick.MIRROR_FOLD)) {
                        c = 2;
                        break;
                    }
                    break;
                case -961942796:
                    if (name.equals(CarControl.Quick.LEFT_CHARGE_PORT)) {
                        c = 3;
                        break;
                    }
                    break;
                case -366343290:
                    if (name.equals(CarControl.Quick.LEFT_CHARGE_PORT_STATE)) {
                        c = 4;
                        break;
                    }
                    break;
                case 1227141723:
                    if (name.equals(CarControl.Quick.RIGHT_CHARGE_PORT_STATE)) {
                        c = 5;
                        break;
                    }
                    break;
                case 1263627374:
                    if (name.equals(CarControl.Quick.OPEN_REAR_TRUNK)) {
                        c = 6;
                        break;
                    }
                    break;
                case 1391385427:
                    if (name.equals(CarControl.Quick.MIRROR_FOLD_STATE)) {
                        c = 7;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                case 3:
                    try {
                        CarBodySmartControl.getInstance().controlChargePort(CarControl.Quick.LEFT_CHARGE_PORT.equals(name), Integer.parseInt(value) == 1);
                        break;
                    } catch (NumberFormatException unused) {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                        return false;
                    }
                case 1:
                    try {
                        CarBodySmartControl.getInstance().resetChargePort(Integer.parseInt(value) == 1);
                        break;
                    } catch (NumberFormatException unused2) {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                        return false;
                    }
                case 2:
                    try {
                        CabinSmartControl.getInstance().controlMirrorFold(Integer.parseInt(value) == 1);
                        break;
                    } catch (NumberFormatException unused3) {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                        return false;
                    }
                case 4:
                case 5:
                    return insertSettingLocked(0, name, value, makeDefault, forceNotify);
                case 6:
                    CabinSmartControl.getInstance().unlockTrunk();
                    break;
                case 7:
                    return insertSettingLocked(0, name, value, makeDefault, forceNotify);
                default:
                    return insertSettingLocked(0, name, value, makeDefault, forceNotify);
            }
            return true;
        }

        private void notifyForSettingsChange(int type, String name) {
            this.mHandler.obtainMessage(1, 0, 0, getNotificationUriFor(type, name)).sendToTarget();
        }

        private Uri getNotificationUriFor(int type, String name) {
            if (type == 0) {
                Uri uri = CarControl.Quick.CONTENT_URI;
                return name != null ? Uri.withAppendedPath(uri, name) : uri;
            } else if (type == 1) {
                Uri uri2 = CarControl.System.CONTENT_URI;
                return name != null ? Uri.withAppendedPath(uri2, name) : uri2;
            } else if (type == 2) {
                Uri uri3 = CarControl.PrivateControl.CONTENT_URI;
                return name != null ? Uri.withAppendedPath(uri3, name) : uri3;
            } else {
                throw new IllegalArgumentException("Invalid control type:" + type);
            }
        }

        SettingsState.Setting getSettingLocked(int type, String name) {
            SettingsState peekSettingsStateLocked = peekSettingsStateLocked(type);
            if (peekSettingsStateLocked == null) {
                return null;
            }
            return peekSettingsStateLocked.getSettingLocked(name);
        }

        boolean insertSettingLocked(int type, String name, String value, boolean makeDefault, boolean forceNotify) {
            if (TextUtils.isEmpty(name)) {
                return false;
            }
            SettingsState peekSettingsStateLocked = peekSettingsStateLocked(type);
            boolean insertSettingLocked = peekSettingsStateLocked != null ? peekSettingsStateLocked.insertSettingLocked(name, value, makeDefault) : false;
            if (forceNotify || insertSettingLocked) {
                notifyForSettingsChange(type, name);
                return true;
            }
            return true;
        }

        boolean updatePrivateControlLocked(String name, String value, boolean makeDefault, boolean forceNotify) {
            name.hashCode();
            boolean z = true;
            char c = 65535;
            switch (name.hashCode()) {
                case -2098705231:
                    if (name.equals(CarControl.PrivateControl.CHILD_LEFT_DOOR_HOT_KEY)) {
                        c = 0;
                        break;
                    }
                    break;
                case -2020254266:
                    if (name.equals(CarControl.PrivateControl.TTM_SWITCH_MODE)) {
                        c = 1;
                        break;
                    }
                    break;
                case -1992231427:
                    if (name.equals(CarControl.PrivateControl.ATL_DUAL_COLOR_SW)) {
                        c = 2;
                        break;
                    }
                    break;
                case -1991966638:
                    if (name.equals(CarControl.PrivateControl.REMOVE_DROPDOWN_MENU)) {
                        c = 3;
                        break;
                    }
                    break;
                case -1983869884:
                    if (name.equals(CarControl.PrivateControl.APA_SAFE_EXAM_RESULT_NOTIFY)) {
                        c = 4;
                        break;
                    }
                    break;
                case -1948915804:
                    if (name.equals(CarControl.PrivateControl.APA_SAFE_EXAM_RESULT)) {
                        c = 5;
                        break;
                    }
                    break;
                case -1938542533:
                    if (name.equals(CarControl.PrivateControl.SDC_BRAKE_CLOSE_DOOR_CFG)) {
                        c = 6;
                        break;
                    }
                    break;
                case -1918880126:
                    if (name.equals(CarControl.PrivateControl.MEM_PARK_SAFE_EXAM_RESULT_NOTIFY)) {
                        c = 7;
                        break;
                    }
                    break;
                case -1876670281:
                    if (name.equals(CarControl.PrivateControl.LCC_VIDEO_WATCH_STATE)) {
                        c = '\b';
                        break;
                    }
                    break;
                case -1858392354:
                    if (name.equals(CarControl.PrivateControl.LCC_SAFE_EXAM_RESULT)) {
                        c = '\t';
                        break;
                    }
                    break;
                case -1846888258:
                    if (name.equals(CarControl.PrivateControl.AS_CAMPING_MODE)) {
                        c = '\n';
                        break;
                    }
                    break;
                case -1791914038:
                    if (name.equals(CarControl.PrivateControl.LCC_SAFE_EXAM_RESULT_NOTIFY)) {
                        c = 11;
                        break;
                    }
                    break;
                case -1784856833:
                    if (name.equals(CarControl.PrivateControl.PARK_AUTO_UNLOCK)) {
                        c = '\f';
                        break;
                    }
                    break;
                case -1769521377:
                    if (name.equals(CarControl.PrivateControl.NGP_SAFE_EXAM_RESULT_NOTIFY)) {
                        c = '\r';
                        break;
                    }
                    break;
                case -1734162181:
                    if (name.equals(CarControl.PrivateControl.DRIVE_MODE_UI)) {
                        c = 14;
                        break;
                    }
                    break;
                case -1699764354:
                    if (name.equals(CarControl.PrivateControl.BOOT_EFFECT)) {
                        c = 15;
                        break;
                    }
                    break;
                case -1692639000:
                    if (name.equals(CarControl.PrivateControl.SET_ALC_SW)) {
                        c = 16;
                        break;
                    }
                    break;
                case -1642251909:
                    if (name.equals(CarControl.PrivateControl.CUSTOM_KEY_WHEEL)) {
                        c = 17;
                        break;
                    }
                    break;
                case -1625221967:
                    if (name.equals(CarControl.PrivateControl.SET_CWC_SW)) {
                        c = 18;
                        break;
                    }
                    break;
                case -1590155457:
                    if (name.equals(CarControl.PrivateControl.UNITY_PRELOAD)) {
                        c = 19;
                        break;
                    }
                    break;
                case -1582344441:
                    if (name.equals(CarControl.PrivateControl.ESP_SW_NOTIFY)) {
                        c = 20;
                        break;
                    }
                    break;
                case -1486920389:
                    if (name.equals(CarControl.PrivateControl.WIN_HIGH_SPD_NOTIFY)) {
                        c = 21;
                        break;
                    }
                    break;
                case -1472065136:
                    if (name.equals(CarControl.PrivateControl.NORMAL_DRIVE_MODE)) {
                        c = 22;
                        break;
                    }
                    break;
                case -1407498806:
                    if (name.equals(CarControl.PrivateControl.ATL_SW)) {
                        c = 23;
                        break;
                    }
                    break;
                case -1405770928:
                    if (name.equals(CarControl.PrivateControl.AVH_SW)) {
                        c = 24;
                        break;
                    }
                    break;
                case -1390293486:
                    if (name.equals(CarControl.PrivateControl.SET_MEM_PARK_SW)) {
                        c = 25;
                        break;
                    }
                    break;
                case -1386030028:
                    if (name.equals(CarControl.PrivateControl.SET_LCC_SW)) {
                        c = JSONLexer.EOI;
                        break;
                    }
                    break;
                case -1385828538:
                    if (name.equals(CarControl.PrivateControl.ATL_DUAL_COLOR)) {
                        c = 27;
                        break;
                    }
                    break;
                case -1324690359:
                    if (name.equals(CarControl.PrivateControl.SET_NGP_SW)) {
                        c = 28;
                        break;
                    }
                    break;
                case -1293786559:
                    if (name.equals(CarControl.PrivateControl.ESP_SW)) {
                        c = 29;
                        break;
                    }
                    break;
                case -1272884516:
                    if (name.equals(CarControl.PrivateControl.UNLOCK_RESPONSE)) {
                        c = 30;
                        break;
                    }
                    break;
                case -1099698194:
                    if (name.equals(CarControl.PrivateControl.LLU_SW)) {
                        c = 31;
                        break;
                    }
                    break;
                case -1051670612:
                    if (name.equals(CarControl.PrivateControl.NEW_DRIVE_XPEDAL_MODE)) {
                        c = ' ';
                        break;
                    }
                    break;
                case -940754136:
                    if (name.equals(CarControl.PrivateControl.SEAT_WELCOME_SW)) {
                        c = '!';
                        break;
                    }
                    break;
                case -881156614:
                    if (name.equals(CarControl.PrivateControl.USER_DEFINE_DRIVE_MODE)) {
                        c = '\"';
                        break;
                    }
                    break;
                case -854915328:
                    if (name.equals(CarControl.PrivateControl.CHILD_LEFT_LOCK)) {
                        c = '#';
                        break;
                    }
                    break;
                case -816122768:
                    if (name.equals(CarControl.PrivateControl.SPEC_DRIVE_MODE_VALUE)) {
                        c = '$';
                        break;
                    }
                    break;
                case -789916995:
                    if (name.equals(CarControl.PrivateControl.REQUEST_CAR_LICENSE_PLATE)) {
                        c = '%';
                        break;
                    }
                    break;
                case -710601245:
                    if (name.equals(CarControl.PrivateControl.REAR_SEAT_WELCOME_SW)) {
                        c = '&';
                        break;
                    }
                    break;
                case -481634221:
                    if (name.equals(CarControl.PrivateControl.SUPER_VPA_SAFE_EXAM_RESULT_NOTIFY)) {
                        c = '\'';
                        break;
                    }
                    break;
                case -445919753:
                    if (name.equals(CarControl.PrivateControl.ATL_EFFECT)) {
                        c = '(';
                        break;
                    }
                    break;
                case -441136641:
                    if (name.equals(CarControl.PrivateControl.EASY_LOADING_MODE)) {
                        c = ')';
                        break;
                    }
                    break;
                case -380486374:
                    if (name.equals(CarControl.PrivateControl.SUPER_LCC_SAFE_EXAM_RESULT)) {
                        c = '*';
                        break;
                    }
                    break;
                case -374920237:
                    if (name.equals(CarControl.PrivateControl.USER_DEFINE_DRIVE_MODE_INFO)) {
                        c = '+';
                        break;
                    }
                    break;
                case -343775101:
                    if (name.equals(CarControl.PrivateControl.SDC_MAX_AUTO_DOOR_OPENING_ANGLE)) {
                        c = ',';
                        break;
                    }
                    break;
                case -294304242:
                    if (name.equals(CarControl.PrivateControl.SUPER_LCC_SAFE_EXAM_RESULT_NOTIFY)) {
                        c = '-';
                        break;
                    }
                    break;
                case -279706511:
                    if (name.equals(CarControl.PrivateControl.CHILD_RIGHT_LOCK)) {
                        c = '.';
                        break;
                    }
                    break;
                case -269621567:
                    if (name.equals(CarControl.PrivateControl.SET_LSS_STATE)) {
                        c = '/';
                        break;
                    }
                    break;
                case -250001118:
                    if (name.equals(CarControl.PrivateControl.CHILD_RIGHT_DOOR_HOT_KEY)) {
                        c = '0';
                        break;
                    }
                    break;
                case -230207226:
                    if (name.equals(CarControl.PrivateControl.SET_AUTO_PARK_SW)) {
                        c = '1';
                        break;
                    }
                    break;
                case -195120536:
                    if (name.equals(CarControl.PrivateControl.SDC_AUTO_WIN_DOWN)) {
                        c = '2';
                        break;
                    }
                    break;
                case -127570606:
                    if (name.equals(CarControl.PrivateControl.SET_NRA_STATE)) {
                        c = '3';
                        break;
                    }
                    break;
                case -82532238:
                    if (name.equals(CarControl.PrivateControl.DRIVE_MODE_USER)) {
                        c = '4';
                        break;
                    }
                    break;
                case 99423:
                    if (name.equals(CarControl.PrivateControl.DHC)) {
                        c = '5';
                        break;
                    }
                    break;
                case 100648:
                    if (name.equals(CarControl.PrivateControl.STEERING_EPS)) {
                        c = '6';
                        break;
                    }
                    break;
                case 100724:
                    if (name.equals(CarControl.PrivateControl.ESB)) {
                        c = '7';
                        break;
                    }
                    break;
                case 113238:
                    if (name.equals(CarControl.PrivateControl.REAR_SEATBELT_WARNING)) {
                        c = '8';
                        break;
                    }
                    break;
                case 10550357:
                    if (name.equals(CarControl.PrivateControl.AS_WELCOME_MODE)) {
                        c = '9';
                        break;
                    }
                    break;
                case 31266679:
                    if (name.equals(CarControl.PrivateControl.SDC_KEY_CFG)) {
                        c = ':';
                        break;
                    }
                    break;
                case 33581987:
                    if (name.equals(CarControl.PrivateControl.BOOT_EFFECT_BEFORE_SW)) {
                        c = ';';
                        break;
                    }
                    break;
                case 448480220:
                    if (name.equals(CarControl.PrivateControl.ENERGY_RECYCLE)) {
                        c = '<';
                        break;
                    }
                    break;
                case 480568617:
                    if (name.equals(CarControl.PrivateControl.NGP_SAFE_EXAM_RESULT)) {
                        c = '=';
                        break;
                    }
                    break;
                case 500654478:
                    if (name.equals("key_door")) {
                        c = '>';
                        break;
                    }
                    break;
                case 577035373:
                    if (name.equals(CarControl.PrivateControl.CAR_LICENSE_PLATE)) {
                        c = '?';
                        break;
                    }
                    break;
                case 620543222:
                    if (name.equals(CarControl.PrivateControl.AVAS_EXTERNAL_VOLUME)) {
                        c = '@';
                        break;
                    }
                    break;
                case 635053862:
                    if (name.equals(CarControl.PrivateControl.MEM_PARK_SAFE_EXAM_RESULT)) {
                        c = 'A';
                        break;
                    }
                    break;
                case 797716478:
                    if (name.equals(CarControl.PrivateControl.N_PROTECT)) {
                        c = 'B';
                        break;
                    }
                    break;
                case 808308137:
                    if (name.equals(CarControl.PrivateControl.AVAS_EFFECT)) {
                        c = 'C';
                        break;
                    }
                    break;
                case 863973952:
                    if (name.equals(CarControl.PrivateControl.AVAS_EXTERNAL_SW)) {
                        c = 'D';
                        break;
                    }
                    break;
                case 904245522:
                    if (name.equals(CarControl.PrivateControl.SET_CITY_NGP_SW)) {
                        c = 'E';
                        break;
                    }
                    break;
                case 1038558861:
                    if (name.equals(CarControl.PrivateControl.WIN_HIGH_SPD)) {
                        c = 'F';
                        break;
                    }
                    break;
                case 1069960506:
                    if (name.equals(CarControl.PrivateControl.SET_HEAD_LAMP)) {
                        c = 'G';
                        break;
                    }
                    break;
                case 1094992699:
                    if (name.equals(CarControl.PrivateControl.SAY_HI_EFFECT)) {
                        c = 'H';
                        break;
                    }
                    break;
                case 1186664482:
                    if (name.equals(CarControl.PrivateControl.PSN_SRS_ENABLE)) {
                        c = 'I';
                        break;
                    }
                    break;
                case 1303509810:
                    if (name.equals(CarControl.PrivateControl.AVAS_VOLUME)) {
                        c = 'J';
                        break;
                    }
                    break;
                case 1322950071:
                    if (name.equals(CarControl.PrivateControl.ATL_BRIGHTNESS)) {
                        c = 'K';
                        break;
                    }
                    break;
                case 1350425855:
                    if (name.equals(CarControl.PrivateControl.MEM_PARK_VIDEO_WATCH_STATE)) {
                        c = 'L';
                        break;
                    }
                    break;
                case 1463283319:
                    if (name.equals(CarControl.PrivateControl.NFC_KEY_ENABLE)) {
                        c = 'M';
                        break;
                    }
                    break;
                case 1468675595:
                    if (name.equals(CarControl.PrivateControl.WHEEL_KEY_PROTECT)) {
                        c = 'N';
                        break;
                    }
                    break;
                case 1469224728:
                    if (name.equals(CarControl.PrivateControl.AVH_SW_NOTIFY)) {
                        c = 'O';
                        break;
                    }
                    break;
                case 1646433897:
                    if (name.equals(CarControl.PrivateControl.NEW_DRIVE_MODE_VALUE)) {
                        c = 'P';
                        break;
                    }
                    break;
                case 1657020316:
                    if (name.equals(CarControl.PrivateControl.CONNECT_RESOURCE_MGR)) {
                        c = 'Q';
                        break;
                    }
                    break;
                case 1698370588:
                    if (name.equals(CarControl.PrivateControl.PSN_SEAT_WELCOME_SW)) {
                        c = 'R';
                        break;
                    }
                    break;
                case 1803969973:
                    if (name.equals(CarControl.PrivateControl.WIPER_SENSITIVE_LEVEL)) {
                        c = 'S';
                        break;
                    }
                    break;
                case 1949213441:
                    if (name.equals(CarControl.PrivateControl.LOCK_CLOSE_WIN)) {
                        c = 'T';
                        break;
                    }
                    break;
                case 1957461621:
                    if (name.equals(CarControl.PrivateControl.SUPER_VPA_SAFE_EXAM_RESULT)) {
                        c = 'U';
                        break;
                    }
                    break;
                case 1959790720:
                    if (name.equals(CarControl.PrivateControl.REAR_FOG_SW)) {
                        c = 'V';
                        break;
                    }
                    break;
                case 1969872294:
                    if (name.equals(CarControl.PrivateControl.CNGP_SAFE_EXAM_RESULT)) {
                        c = 'W';
                        break;
                    }
                    break;
                case 2066444434:
                    if (name.equals(CarControl.PrivateControl.ATL_SINGLE_COLOR)) {
                        c = 'X';
                        break;
                    }
                    break;
                case 2093683332:
                    if (name.equals(CarControl.PrivateControl.XPILOT_ISLA_SW)) {
                        c = 'Y';
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    try {
                        CarControlProvider.this.mDataSync.setLeftDoorHotKey(Integer.parseInt(value) == 1);
                        break;
                    } catch (NumberFormatException unused) {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                        return false;
                    }
                case 1:
                    try {
                        CarControlProvider.this.mDataSync.setTrailerHitchStatus(Integer.parseInt(value) == 1);
                        break;
                    } catch (NumberFormatException unused2) {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                        return false;
                    }
                case 2:
                    try {
                        DataSyncModel dataSyncModel = CarControlProvider.this.mDataSync;
                        if (Integer.parseInt(value) != 1) {
                            z = false;
                        }
                        dataSyncModel.setAtlDualColorSw(z);
                        return insertSettingLocked(2, name, value, makeDefault, forceNotify);
                    } catch (NumberFormatException unused3) {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                        return false;
                    }
                case 3:
                    try {
                        if (Integer.parseInt(value) % 2 == 0) {
                            DropDownMenuManager.getInstance().resumeAllDropdownMenu();
                        } else {
                            DropDownMenuManager.getInstance().removeAllDropdownMenu();
                        }
                        return false;
                    } catch (NumberFormatException unused4) {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                        return false;
                    }
                case 4:
                    return insertSettingLocked(2, name, value, makeDefault, true);
                case 5:
                    try {
                        CarControlProvider.this.mDataSync.setApaSafeExamResult(Integer.parseInt(value) == 1);
                        break;
                    } catch (NumberFormatException unused5) {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                        return false;
                    }
                case 6:
                    try {
                        CarControlProvider.this.mDataSync.setSdcBrakeCloseCfg(Integer.parseInt(value));
                        break;
                    } catch (NumberFormatException unused6) {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                        return false;
                    }
                case 7:
                    return insertSettingLocked(2, name, value, makeDefault, true);
                case '\b':
                    try {
                        CarControlProvider.this.mDataSync.setLccVideoWatched(Integer.parseInt(value) == 1);
                        break;
                    } catch (NumberFormatException unused7) {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                        return false;
                    }
                case '\t':
                    try {
                        CarControlProvider.this.mDataSync.setLccSafeExamResult(Integer.parseInt(value) == 1);
                        break;
                    } catch (NumberFormatException unused8) {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                        return false;
                    }
                case '\n':
                    try {
                        CarControlProvider.this.mDataSync.setAsCampingModeStatus(Integer.parseInt(value) == 1);
                        break;
                    } catch (NumberFormatException unused9) {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                        return false;
                    }
                case 11:
                    return insertSettingLocked(2, name, value, makeDefault, true);
                case '\f':
                    try {
                        CarControlProvider.this.mDataSync.setParkAutoUnlock(Integer.parseInt(value) == 1);
                        break;
                    } catch (NumberFormatException unused10) {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                        return false;
                    }
                case '\r':
                    return insertSettingLocked(2, name, value, makeDefault, true);
                case 14:
                    try {
                        VcuSmartControl.getInstance().setDriveModeByUser(Integer.parseInt(value));
                        break;
                    } catch (NumberFormatException unused11) {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                        return false;
                    }
                case 15:
                    try {
                        CarControlProvider.this.mDataSync.setBootEffect(Integer.parseInt(value));
                        break;
                    } catch (NumberFormatException unused12) {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                        return false;
                    }
                case 16:
                    try {
                        CarControlProvider.this.mDataSync.setAlcSw(Integer.parseInt(value) == 1);
                        break;
                    } catch (NumberFormatException unused13) {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                        return false;
                    }
                case 17:
                case '>':
                    return insertSettingLocked(2, name, value, makeDefault, forceNotify);
                case 18:
                    try {
                        CarControlProvider.this.mDataSync.setCwcSw(Integer.parseInt(value) == 1);
                        break;
                    } catch (NumberFormatException unused14) {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                        return false;
                    }
                case 19:
                    try {
                        return insertSettingLocked(2, name, value, makeDefault, forceNotify);
                    } catch (NumberFormatException unused15) {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                        return false;
                    }
                case 20:
                    return insertSettingLocked(2, name, value, makeDefault, forceNotify);
                case 21:
                    return insertSettingLocked(2, name, value, makeDefault, forceNotify);
                case 22:
                    return insertSettingLocked(2, name, value, makeDefault, true);
                case 23:
                    try {
                        DataSyncModel dataSyncModel2 = CarControlProvider.this.mDataSync;
                        if (Integer.parseInt(value) != 1) {
                            z = false;
                        }
                        dataSyncModel2.setAtlSw(z);
                        return insertSettingLocked(2, name, value, makeDefault, forceNotify);
                    } catch (NumberFormatException unused16) {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                        return false;
                    }
                case 24:
                    try {
                        ChassisSmartControl chassisSmartControl = ChassisSmartControl.getInstance();
                        if (Integer.parseInt(value) != 1) {
                            z = false;
                        }
                        chassisSmartControl.setAvhSw(z);
                        return insertSettingLocked(2, name, value, makeDefault, forceNotify);
                    } catch (NumberFormatException unused17) {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                        return false;
                    }
                case 25:
                    try {
                        CarControlProvider.this.mDataSync.setMemParkSw(Integer.parseInt(value) == 1);
                        break;
                    } catch (NumberFormatException unused18) {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                        return false;
                    }
                case 26:
                    try {
                        CarControlProvider.this.mDataSync.setLccSw(Integer.parseInt(value) == 1);
                        break;
                    } catch (NumberFormatException unused19) {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                        return false;
                    }
                case 27:
                    try {
                        String[] split = value.split(",");
                        CarControlProvider.this.mDataSync.setAtlDualColor(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
                        return insertSettingLocked(2, name, value, makeDefault, forceNotify);
                    } catch (NumberFormatException unused20) {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                        return false;
                    }
                case 28:
                    try {
                        CarControlProvider.this.mDataSync.setNgpSw(Integer.parseInt(value) == 1);
                        break;
                    } catch (NumberFormatException unused21) {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                        return false;
                    }
                case 29:
                    try {
                        DataSyncModel dataSyncModel3 = CarControlProvider.this.mDataSync;
                        if (Integer.parseInt(value) != 1) {
                            z = false;
                        }
                        dataSyncModel3.setEsp(z);
                        return insertSettingLocked(2, name, value, makeDefault, forceNotify);
                    } catch (Exception unused22) {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + "for name: " + name);
                        return false;
                    }
                case 30:
                    try {
                        CarControlProvider.this.mDataSync.setUnlockResponse(Integer.parseInt(value));
                        return insertSettingLocked(2, name, value, makeDefault, forceNotify);
                    } catch (NumberFormatException unused23) {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                        return false;
                    }
                case 31:
                    try {
                        DataSyncModel dataSyncModel4 = CarControlProvider.this.mDataSync;
                        if (Integer.parseInt(value) != 1) {
                            z = false;
                        }
                        dataSyncModel4.setLluSw(z);
                        return insertSettingLocked(2, name, value, makeDefault, forceNotify);
                    } catch (NumberFormatException unused24) {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                        return false;
                    }
                case ' ':
                    try {
                        CarControlProvider.this.mDataSync.setNewDriveXPedalMode(Integer.parseInt(value) == 1);
                        break;
                    } catch (NumberFormatException unused25) {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                        return false;
                    }
                case '!':
                    try {
                        CarControlProvider.this.mDataSync.setWelcomeMode(Integer.parseInt(value) == 1);
                        break;
                    } catch (NumberFormatException unused26) {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                        return false;
                    }
                case '\"':
                    try {
                        CarControlProvider.this.mDataSync.setUserDefineDriveMode(Integer.parseInt(value));
                        return insertSettingLocked(2, name, value, makeDefault, forceNotify);
                    } catch (NumberFormatException unused27) {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                        return false;
                    }
                case '#':
                    try {
                        CarControlProvider.this.mDataSync.setChildLeftLock(Integer.parseInt(value) == 1);
                        break;
                    } catch (NumberFormatException unused28) {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                        return false;
                    }
                case '$':
                    return insertSettingLocked(2, name, value, makeDefault, true);
                case '%':
                    if (CarControlProvider.this.mIsServiceVmInit && CarControlProvider.this.mServiceVm != null) {
                        CarControlProvider.this.mServiceVm.requestCarLicensePlate();
                        break;
                    }
                    break;
                case '&':
                    try {
                        CarControlProvider.this.mDataSync.setRearSeatWelcomeMode(Integer.parseInt(value) == 1);
                        break;
                    } catch (NumberFormatException unused29) {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                        return false;
                    }
                case '\'':
                    return insertSettingLocked(2, name, value, makeDefault, true);
                case '(':
                    CarControlProvider.this.mDataSync.setAtlEffect(value);
                    return insertSettingLocked(2, name, value, makeDefault, forceNotify);
                case ')':
                    try {
                        CarControlProvider.this.mDataSync.setEasyLoadingStatus(Integer.parseInt(value) == 1);
                        break;
                    } catch (NumberFormatException unused30) {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                        return false;
                    }
                case '*':
                    try {
                        CarControlProvider.this.mDataSync.setSuperLccSafeExamResult(Integer.parseInt(value) == 1);
                        break;
                    } catch (NumberFormatException unused31) {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                        return false;
                    }
                case '+':
                    return insertSettingLocked(2, name, value, makeDefault, true);
                case ',':
                    try {
                        CarControlProvider.this.mDataSync.setSdcMaxAutoDoorOpeningAngle(Integer.parseInt(value));
                        break;
                    } catch (NumberFormatException unused32) {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                        return false;
                    }
                case '-':
                    return insertSettingLocked(2, name, value, makeDefault, true);
                case '.':
                    try {
                        CarControlProvider.this.mDataSync.setChildRightLock(Integer.parseInt(value) == 1);
                        break;
                    } catch (NumberFormatException unused33) {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                        return false;
                    }
                case '/':
                    try {
                        CarControlProvider.this.mDataSync.setLssState(Integer.parseInt(value));
                        break;
                    } catch (NumberFormatException unused34) {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                        return false;
                    }
                case '0':
                    try {
                        CarControlProvider.this.mDataSync.setRightDoorHotKey(Integer.parseInt(value) == 1);
                        break;
                    } catch (NumberFormatException unused35) {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                        return false;
                    }
                case '1':
                    try {
                        CarControlProvider.this.mDataSync.setAutoParkSw(Integer.parseInt(value) == 1);
                        break;
                    } catch (NumberFormatException unused36) {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                        return false;
                    }
                case '2':
                    try {
                        CarControlProvider.this.mDataSync.setSdcWinAutoDown(Integer.parseInt(value) == 1);
                        break;
                    } catch (NumberFormatException unused37) {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                        return false;
                    }
                case '3':
                    try {
                        CarControlProvider.this.mDataSync.setNraState(Integer.parseInt(value));
                        return insertSettingLocked(2, name, value, makeDefault, forceNotify);
                    } catch (NumberFormatException unused38) {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                        return false;
                    }
                case '4':
                    try {
                        FunctionModel.getInstance().setDriveModeChangedByUser(Integer.parseInt(value) == 1);
                        break;
                    } catch (NumberFormatException unused39) {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                        return false;
                    }
                case '5':
                    try {
                        CarControlProvider.this.mDataSync.setAutoDhc(Integer.parseInt(value) == 1);
                        break;
                    } catch (NumberFormatException unused40) {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                        return false;
                    }
                case '6':
                    try {
                        CarControlProvider.this.mDataSync.setSteerMode(Integer.parseInt(value));
                        return insertSettingLocked(2, name, value, makeDefault, forceNotify);
                    } catch (Exception unused41) {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + "for name: " + name);
                        return false;
                    }
                case '7':
                    try {
                        CarControlProvider.this.mDataSync.setDrvSeatEsb(Integer.parseInt(value) == 1);
                        break;
                    } catch (NumberFormatException unused42) {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                        return false;
                    }
                case '8':
                    try {
                        CarControlProvider.this.mDataSync.setRsbWarning(Integer.parseInt(value) == 1);
                        break;
                    } catch (NumberFormatException unused43) {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                        return false;
                    }
                case '9':
                    try {
                        CarControlProvider.this.mDataSync.setAsWelcomeMode(Integer.parseInt(value) == 1);
                        break;
                    } catch (NumberFormatException unused44) {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                        return false;
                    }
                case ':':
                    try {
                        CarControlProvider.this.mDataSync.setSdcKeyCfg(Integer.parseInt(value));
                        break;
                    } catch (NumberFormatException unused45) {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                        return false;
                    }
                case ';':
                    try {
                        CarControlProvider.this.mDataSync.setBootEffectBeforeSw(Integer.parseInt(value));
                        break;
                    } catch (NumberFormatException unused46) {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                        return false;
                    }
                case '<':
                    try {
                        VcuSmartControl.getInstance().setEnergyRecycleGrade(Integer.parseInt(value));
                        break;
                    } catch (NumberFormatException unused47) {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                        return false;
                    }
                case '=':
                    try {
                        CarControlProvider.this.mDataSync.setNgpSafeExamResult(Integer.parseInt(value) == 1);
                        break;
                    } catch (NumberFormatException unused48) {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                        return false;
                    }
                case '?':
                    return insertSettingLocked(2, name, value, makeDefault, forceNotify);
                case '@':
                    try {
                        CarControlProvider.this.mDataSync.setAvasOtherVolume(Integer.parseInt(value));
                        break;
                    } catch (NumberFormatException unused49) {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                        return false;
                    }
                case 'A':
                    try {
                        CarControlProvider.this.mDataSync.setMemParkSafeExamResult(Integer.parseInt(value) == 1);
                        break;
                    } catch (NumberFormatException unused50) {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                        return false;
                    }
                case 'B':
                    try {
                        CarControlProvider.this.mDataSync.setNGearWarningSwitch(Integer.parseInt(value) == 1);
                        break;
                    } catch (NumberFormatException unused51) {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                        return false;
                    }
                case 'C':
                    try {
                        CarControlProvider.this.mDataSync.setAvasEffect(Integer.parseInt(value));
                        return insertSettingLocked(2, name, value, makeDefault, forceNotify);
                    } catch (NumberFormatException unused52) {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                        return false;
                    }
                case 'D':
                    try {
                        DebugFuncModel.getInstance().setAvasExternalSw(Integer.parseInt(value) == 1);
                        break;
                    } catch (NumberFormatException unused53) {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                        return false;
                    }
                case 'E':
                    try {
                        CarControlProvider.this.mDataSync.setCityNgpSw(Integer.parseInt(value) == 1);
                        break;
                    } catch (NumberFormatException unused54) {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                        return false;
                    }
                case 'F':
                    try {
                        CabinSmartControl cabinSmartControl = CabinSmartControl.getInstance();
                        if (Integer.parseInt(value) != 1) {
                            z = false;
                        }
                        cabinSmartControl.setWinHighSpdSw(z);
                        return insertSettingLocked(2, name, value, makeDefault, forceNotify);
                    } catch (NumberFormatException unused55) {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                        return false;
                    }
                case 'G':
                    try {
                        CarControlProvider.this.mDataSync.setHeadLampState(Integer.parseInt(value));
                        break;
                    } catch (NumberFormatException unused56) {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                        return false;
                    }
                case 'H':
                    try {
                        CarControlProvider.this.mDataSync.setSayHiEffect(Integer.parseInt(value));
                        return insertSettingLocked(2, name, value, makeDefault, forceNotify);
                    } catch (NumberFormatException unused57) {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                        return false;
                    }
                case 'I':
                    try {
                        CarControlProvider.this.mDataSync.setPsnSrsEnable(Integer.parseInt(value) == 1);
                        break;
                    } catch (NumberFormatException unused58) {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                        return false;
                    }
                case 'J':
                    try {
                        CarControlProvider.this.mDataSync.setAvasVolume(Integer.parseInt(value));
                        return insertSettingLocked(2, name, value, makeDefault, forceNotify);
                    } catch (NumberFormatException unused59) {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                        return false;
                    }
                case 'K':
                    try {
                        CarControlProvider.this.mDataSync.setAtlBright(Integer.parseInt(value));
                        return insertSettingLocked(2, name, value, makeDefault, forceNotify);
                    } catch (NumberFormatException unused60) {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                        return false;
                    }
                case 'L':
                    try {
                        CarControlProvider.this.mDataSync.setMemParkVideoWatched(Integer.parseInt(value) == 1);
                        break;
                    } catch (NumberFormatException unused61) {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                        return false;
                    }
                case 'M':
                    try {
                        CarControlProvider.this.mDataSync.setNfcKeyEnable(Integer.parseInt(value) == 1);
                        break;
                    } catch (NumberFormatException unused62) {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                        return false;
                    }
                case 'N':
                    try {
                        CarControlProvider.this.mDataSync.setWheelKeyProtect(Integer.parseInt(value) == 1);
                        break;
                    } catch (NumberFormatException unused63) {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                        return false;
                    }
                case 'O':
                    return insertSettingLocked(2, name, value, makeDefault, forceNotify);
                case 'P':
                    return insertSettingLocked(2, name, value, makeDefault, true);
                case 'Q':
                    XuiClientWrapper.getInstance().connectToResourceManager();
                    break;
                case 'R':
                    try {
                        CarControlProvider.this.mDataSync.setPsnWelcomeMode(Integer.parseInt(value) == 1);
                        break;
                    } catch (NumberFormatException unused64) {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                        return false;
                    }
                case 'S':
                    try {
                        CarControlProvider.this.mDataSync.setWiperSensitivity(Integer.parseInt(value));
                        break;
                    } catch (NumberFormatException unused65) {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                        return false;
                    }
                case 'T':
                    try {
                        CarControlProvider.this.mDataSync.setLockCloseWin(Integer.parseInt(value) == 1);
                        break;
                    } catch (NumberFormatException unused66) {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                        return false;
                    }
                case 'U':
                    try {
                        CarControlProvider.this.mDataSync.setSuperVpaSafeExamResult(Integer.parseInt(value) == 1);
                        break;
                    } catch (NumberFormatException unused67) {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                        return false;
                    }
                case 'V':
                    try {
                        LampSmartControl.getInstance().setRearFogSw(Integer.parseInt(value) == 1);
                        break;
                    } catch (NumberFormatException unused68) {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                        return false;
                    }
                case 'W':
                    try {
                        CarControlProvider.this.mDataSync.setCngpSafeExamResult(Integer.parseInt(value) == 1);
                        break;
                    } catch (NumberFormatException unused69) {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                        return false;
                    }
                case 'X':
                    try {
                        CarControlProvider.this.mDataSync.setAtlSingleColor(Integer.parseInt(value));
                        return insertSettingLocked(2, name, value, makeDefault, forceNotify);
                    } catch (NumberFormatException unused70) {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                        return false;
                    }
                case 'Y':
                    try {
                        CarControlProvider.this.mDataSync.setIslaSw(Integer.parseInt(value));
                        break;
                    } catch (NumberFormatException unused71) {
                        LogUtils.w(CarControlProvider.LOG_TAG, "Invalid value: " + value + " for name: " + name);
                        return false;
                    }
                default:
                    return insertSettingLocked(2, name, value, makeDefault, forceNotify);
            }
            return true;
        }

        /* loaded from: classes2.dex */
        private final class MyHandler extends Handler {
            private static final int MSG_NOTIFY_URI_CHANGED = 1;

            MyHandler(Looper looper) {
                super(looper);
            }

            @Override // android.os.Handler
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    Uri uri = (Uri) msg.obj;
                    try {
                        if (CarControlProvider.this.getContext() != null) {
                            CarControlProvider.this.getContext().getContentResolver().notifyChange(uri, (ContentObserver) null, true);
                        }
                    } catch (Exception e) {
                        LogUtils.e(CarControlProvider.LOG_TAG, "Failed to notify: " + uri, e);
                    }
                }
            }
        }
    }
}
