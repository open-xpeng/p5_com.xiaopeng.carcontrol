package com.xiaopeng.carcontrol.model;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.provider.Settings;
import android.text.TextUtils;
import androidx.collection.ArrayMap;
import com.alibaba.fastjson.parser.JSONLexer;
import com.google.gson.Gson;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.GlobalConstant;
import com.xiaopeng.carcontrol.bean.SyncGroupEventMsg;
import com.xiaopeng.carcontrol.carmanager.controller.IIcmController;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import com.xiaopeng.carcontrol.quicksetting.QuickSettingConstants;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ResUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;
import com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel;
import com.xiaopeng.carcontrol.viewmodel.cabin.SeatViewModel;
import com.xiaopeng.carcontrolmodule.R;
import com.xiaopeng.lib.framework.moduleinterface.syncmodule.SyncData;
import com.xiaopeng.lib.framework.moduleinterface.syncmodule.SyncRestoreEvent;
import com.xiaopeng.lludancemanager.Constant;
import com.xiaopeng.xvs.tools.Tools;
import com.xiaopeng.xvs.xid.XId;
import com.xiaopeng.xvs.xid.account.api.AccountInfo;
import com.xiaopeng.xvs.xid.sync.api.ICarControlSync;
import com.xiaopeng.xvs.xid.sync.api.OnSyncChangedListener;
import com.xiaopeng.xvs.xid.sync.api.SyncType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class DataSyncModel implements OnSyncChangedListener {
    private static final String ACCOUNT_AVATAR = "avatar";
    private static final String ACCOUNT_TYPE_XP_VEHICLE = "com.xiaopeng.accountservice.ACCOUNT_TYPE_XP_VEHICLE";
    private static final String ACCOUNT_UID = "uid";
    private static final String ACCOUNT_UPDATE = "update";
    private static final String AUTH_INFO_EXTRA_APP_ID = "app_id";
    private static final String AUTH_TYPE_AUTH_CODE = "com.xiaopeng.accountservice.AUTH_TYPE_AUTH_CODE";
    private static final String AUTH_TYPE_AUTH_OTP = "com.xiaopeng.accountservice.AUTH_TYPE_AUTH_OTP";
    public static final int CHAIR_POS_INDEX_NEW = -1;
    static final String EMPTY = "";
    static final String KEY_ANTISICK_MODE = "ANTISICK_MODE";
    static final String KEY_AS_WELCOME_MODE = "AS_WELCOME_MODE";
    static final String KEY_ATL_AUTO_BRIGHT_SW = "ATL_AUTO_BRIGHT_SW";
    static final String KEY_ATL_BRIGHT_CFG = "ATL_BRIGHT";
    static final String KEY_ATL_DUAL_COLOR = "ATL_DUAL_COLOR";
    static final String KEY_ATL_DUAL_COLOR_SW = "ATL_DUAL_COLOR_SW";
    static final String KEY_ATL_EFFECT = "ATL_EFFECT";
    static final String KEY_ATL_SINGLE_COLOR = "ATL_SINGLE_COLOR";
    static final String KEY_ATL_SW = "ATL_SW";
    static final String KEY_AUTO_DHC = "AUTO_DHC";
    private static final String KEY_AUTO_DRIVE_MODE = "AUTO_DRIVE_MODE";
    static final String KEY_AUTO_LAMP_HEIGHT = "AUTO_LAMP_HEIGHT";
    private static final String KEY_AUTO_PARKING = "AUTO_PARK_SW";
    static final String KEY_AVAS_BOOT_EFFECT = "AVAS_BOOT_EFFECT";
    static final String KEY_AVAS_BOOT_EFFECT_BEFORE_SW = "AVAS_BOOT_EFFECT_BEFORE_SW";
    static final String KEY_AVAS_EFFECT = "AVAS_EFFECT";
    static final String KEY_AVAS_EFFECT_NEW = "AVAS_EFFECT_NEW";
    static final String KEY_AVAS_OTHER_VOLUME = "AVAS_OTHER_VOLUME";
    static final String KEY_AVAS_SAYHI_EFFECT = "AVAS_SAYHI_EFFECT";
    static final String KEY_AVAS_SW = "AVAS_SW";
    static final String KEY_AVAS_VOLUME = "AVAS_VOLUME";
    static final String KEY_AVH = "CHASSIS_AVH";
    static final String KEY_BLIND_DETECTION_WARNING = "BLIND_DETECTION_WARNING";
    static final String KEY_BRAKE_WARNING = "BRAKE_WARNING";
    static final String KEY_CDC_MODE = "CDC_MODE";
    static final String KEY_CHAIR_POS_INDEX = "CHAIR_POS_INDEX";
    static final String KEY_CHAIR_POS_NEW = "CHAIR_POSITION_NEW";
    static final String KEY_CHAIR_POS_ONE = "CHAIR_POSITION";
    static final String KEY_CHAIR_POS_PSN = "CHAIR_POSITION_PSN";
    static final String KEY_CHAIR_POS_PSN_SELECT_ID = "CHAIR_POSITION_PSN_SELECT_ID";
    static final String KEY_CHAIR_POS_RL = "CHAIR_POSITION_RL";
    static final String KEY_CHAIR_POS_RR = "CHAIR_POSITION_RR";
    static final String KEY_CHAIR_POS_THREE = "CHAIR_POSITION_THREE";
    static final String KEY_CHAIR_POS_TWO = "CHAIR_POSITION_TWO";
    static final String KEY_CHILD_LEFT_LOCK = "CHILD_LEFT_LOCK";
    private static final String KEY_CHILD_LOCK = "CHILD_LOCK";
    static final String KEY_CHILD_RIGHT_LOCK = "CHILD_RIGHT_LOCK";
    static final String KEY_CITY_NGP_SW = "CITY_NGP_SW";
    static final String KEY_CMS_AUTO_BRIGHTNESS_SW = "CMS_AUTO_BRIGHTNESS_SW";
    static final String KEY_CMS_BRIGHTNESS = "CMS_BRIGHTNESS";
    static final String KEY_CMS_HIGH_SPD_SW = "CMS_HIGH_SPD_SW";
    static final String KEY_CMS_LOW_SPD_SW = "CMS_LOW_SPD_SW";
    static final String KEY_CMS_OBJECT_RECOGNIZE_SW = "CMS_OBJECT_RECOGNIZE_SW";
    static final String KEY_CMS_POS = "CMS_POS";
    static final String KEY_CMS_REVERSE_SW = "CMS_REVERSE_SW";
    static final String KEY_CMS_TURN_EXTN_SW = "CMS_TURN_EXTN_SW";
    static final String KEY_CMS_VIEW_ANGLE = "CMS_VIEW_ANGLE";
    static final String KEY_CMS_VIEW_RECOVERY_SW = "CMS_VIEW_RECOVERY_SW";
    static final String KEY_CWC_SW = "CWC_SW";
    static final String KEY_DOME_LIGHT_BRIGHT = "DOME_LIGHT_BRIGHT";
    static final String KEY_DOOR_BOSS_KEY = "DOOR_BOSS_KEY";
    static final String KEY_DOOR_BOSS_KEY_SW = "DOOR_BOSS_KEY_SW";
    static final String KEY_DRIVE_AUTO_LOCK = "DRIVE_AUTO_LOCK";
    static final String KEY_DRIVE_MODE = "DRIVE_MODE";
    static final String KEY_DRV_SEAT_ESB = "DRV_SEAT_ESB";
    private static final String KEY_ESP = "CHASSIS_ESP";
    static final String KEY_HIGH_SPD_CLOSE_WIN = "HIGH_SPD_CLOSE_WIN";
    private static final String KEY_HVAC_AIR_PROTECT = "HVAC_AIR_PROTECT";
    private static final String KEY_HVAC_AQS = "HVAC_AQS";
    private static final String KEY_HVAC_CIRCLE = "HVAC_CIRCLE";
    private static final String KEY_HVAC_SELF_DRY = "HVAC_SELF_DRY";
    private static final String KEY_HVAC_SMART = "HVAC_SMART";
    private static final String KEY_KEY_PARK_CFG = "KEY_PARK_CFG";
    private static final String KEY_KEY_PARK_SW = "KEY_PARK_SW";
    static final String KEY_LAMP_HEIGHT_LEVEL = "LAMP_HEIGHT_LEVEL";
    static final String KEY_LANE_DEPARTURE_WARNING = "LANE_DEPARTURE_WARNING";
    static final String KEY_LEFT_DOOR_HOT_KEY = "LEFT_DOOR_HOT_KEY";
    static final String KEY_LIGHT_AUTO_SW = "LIGHT_AUTO_SW";
    static final String KEY_LIGHT_DOME_MODE = "LIGHT_DOME_MODE";
    static final String KEY_LIGHT_ME_HOME = "LIGHT_ME_HOME";
    static final String KEY_LIGHT_ME_HOME_TIME = "LIGHT_ME_HOME_TIME";
    static final String KEY_LLU_CHARGE_SW = "LLU_CHARGE_SW";
    static final String KEY_LLU_LOCK_SW = "LLU_LOCK_SW";
    static final String KEY_LLU_SW = "LLU_SW";
    static final String KEY_LLU_UNLOCK_SW = "LLU_UNLOCK_SW";
    static final String KEY_LOCK_CLOSE_WIN = "LOCK_CLOSE_WIN";
    static final String KEY_METER_DEFINE_MEDIA_SOURCE = "METER_DEFINE_MEDIA_SOURCE";
    static final String KEY_METER_DEFINE_SCREEN_LIGHT = "METER_DEFINE_SCREEN_LIGHT";
    static final String KEY_METER_DEFINE_TEMPERATURE = "METER_DEFINE_TEMPERATURE";
    static final String KEY_METER_DEFINE_WIND_MODE = "METER_DEFINE_WIND_MODE";
    static final String KEY_METER_DEFINE_WIND_POWER = "METER_DEFINE_WIND_POWER";
    static final String KEY_MICROPHONE_MUTE = "MICROPHONE_MUTE";
    static final String KEY_MIRROR_AUTO_DOWN = "MIRROR_AUTO_DOWN";
    static final String KEY_MIRROR_AUTO_FOLD = "MIRROR_AUTO_FOLD";
    static final String KEY_MIRROR_POS = "MIRROR_POS";
    static final String KEY_MIRROR_REVERSE = "MIRROR_REVERSE";
    private static final String KEY_NGP_AUTO_LCS = "NGP_AUTO_LCS";
    private static final String KEY_NGP_FAST_LANE = "NGP_FAST_LANE";
    static final String KEY_NGP_LC_MODE = "NGP_LC_MODE";
    static final String KEY_NGP_REMIND_MODE = "NGP_REMIND_MODE";
    static final String KEY_NGP_SW = "NGP_SW";
    static final String KEY_NGP_TIP_WINDOW = "NGP_TIP_WIN";
    private static final String KEY_NGP_TRUCK_OFFSET = "NGP_TRUCK_OFFSET";
    static final String KEY_N_GEAR_PROTECT = "N_GEAR_PROTECT";
    static final String KEY_PARK_LAMP_B = "PARK_LAMP_B";
    private static final String KEY_PHONE_PARK_CFG = "PHONE_PARK_CFG";
    private static final String KEY_PHONE_PARK_SW = "PHONE_PARK_SW";
    private static final String KEY_POLLING_LOCK = "POLLING_LOCK";
    private static final String KEY_POLLING_UNLOCK = "POLLING_UNLOCK";
    static final String KEY_REAR_SEAT_WARNING = "REAR_SEAT_WARNING";
    static final String KEY_REAR_SEAT_WELCOME_MODE = "REAR_SEAT_WELCOME_MODE";
    static final String KEY_RECYCLE_GRADE = "KEY_RECYCLE_GRADE";
    private static final String KEY_REMOTE_CALL_SW = "REMOTE_CALL_SW";
    static final String KEY_RIGHT_DOOR_HOT_KEY = "RIGHT_DOOR_HOT_KEY";
    static final String KEY_SDC_BRAKE_CLOSE = "SDC_BREAK_CLOSE";
    static final String KEY_SENSOR_TRUNK_SW = "SENSOR_TRUNK_SW";
    static final String KEY_SIDE_REVERSING_WARNING = "SIDE_REVERSING_WARNING";
    static final String KEY_SINGLE_PEDAL_MODE = "SINGLEPEDAL";
    static final String KEY_SMART_SPEED_LIMIT = "SMART_SPEED_LIMIT";
    static final String KEY_SPEED_LIMIT = "SPEED_LIMIT";
    static final String KEY_SPEED_LIMIT_VALUE = "SPEED_LIMIT_VALUE";
    static final String KEY_STEER_MODE = "STEER_MODE";
    static final String KEY_STEER_POS = "STEER_POS";
    static final String KEY_STOP_AUTO_UNLOCK = "STOP_AUTO_UNLOCK";
    static final String KEY_TRUNK_OPEN_POS = "TRUNK_OPEN_POS";
    static final String KEY_UNLOCK_RESPONSE = "UNLOCK_RESPONSE";
    static final String KEY_WELCOME_MODE = "WELCOME_MODE";
    static final String KEY_WHEEL_KEY_PROTECT = "WHEEL_KEY_PROTECT";
    static final String KEY_WHEEL_TOUCH_DIRECTION = "WHEEL_TOUCH_DIRECTION";
    static final String KEY_WHEEL_TOUCH_HINT = "WHEEL_TOUCH_HINT";
    static final String KEY_WHEEL_TOUCH_SPEED = "WHEEL_TOUCH_SPEED";
    static final String KEY_WHEEL_X_KEY = "WHEEL_X_KEY";
    static final String KEY_WIPER_SENSITIVITY = "WIPER_SENSITIVITY";
    static final String KEY_XPEDAL_MODE = "XPEDAL_MODE";
    static final String KEY_XPILOT_ALC = "XPILOT_ALC";
    static final String KEY_XPILOT_AUTO_PARK_SW = "XPILOT_AUTO_PARK_SW";
    private static final String KEY_XPILOT_DOW = "XPILOT_DOW";
    private static final String KEY_XPILOT_FCW = "XPILOT_FCW";
    static final String KEY_XPILOT_LCC = "XPILOT_LCC";
    static final String KEY_XPILOT_LSS_STATE = "XPILOT_LSS_STATE";
    static final String KEY_XPILOT_MEM_PARK_SW = "XPILOT_MEM_PARK_SW";
    private static final String KEY_XPILOT_NRA = "XPILOT_NRA";
    private static final String KEY_XPILOT_RCTA = "XPILOT_RCTA";
    private static final String KEY_XPILOT_RCW = "XPILOT_RCW";
    private static final String TAG = "DataSyncModel";
    protected AccountManager mAccountManager;
    protected ICarControlSync mCarControlSync;
    protected DataSyncChangeListener mDataSyncChangeListener;
    private GroupChangeListener mGroupChangeListener;
    public volatile boolean mIsDataSynced = false;
    protected volatile long mUid = -1;
    protected CarControlSyncDataValue mSyncDataValue = new CarControlSyncDataValue();
    protected final ArrayMap<String, SyncData> mSyncFullMap = new ArrayMap<>();

    /* loaded from: classes2.dex */
    public interface DataSyncChangeListener {
        void onAccountDataSynced(CarControlSyncDataEvent syncDataFlag);
    }

    /* loaded from: classes2.dex */
    public interface GroupChangeListener {
        void onAccountOrGroupChange(SyncGroupEventMsg syncGroupEventMsg);
    }

    public boolean getAutoDriveMode() {
        return false;
    }

    public boolean getBsdSw() {
        return false;
    }

    public int getCiuWiperInterval() {
        return -1;
    }

    public String getComfortDriveModeInfo() {
        return GlobalConstant.DEFAULT.NORMAL_DRIVE_MODE_COMFORT_CFG;
    }

    public boolean getDowSw() {
        return false;
    }

    public boolean getDriveAutoLock() {
        return true;
    }

    public boolean getEbwEnable() {
        return true;
    }

    public String getEcoDriveModeInfo() {
        return GlobalConstant.DEFAULT.NORMAL_DRIVE_MODE_ECO_CFG;
    }

    public boolean getElkSw() {
        return false;
    }

    public int getHvacAirProtectMode() {
        return 1;
    }

    public int getHvacAqsLevel() {
        return 2;
    }

    public int getHvacCircle() {
        return 2;
    }

    public boolean getHvacSmart() {
        return false;
    }

    public boolean getIslcSw() {
        return false;
    }

    public boolean getLdwSw() {
        return false;
    }

    public boolean getMeterDefineMediaSource() {
        return false;
    }

    public boolean getMeterDefineScreenLight() {
        return false;
    }

    public boolean getMeterDefineTemperature() {
        return false;
    }

    public boolean getMeterDefineWindMode() {
        return false;
    }

    public boolean getMeterDefineWindPower() {
        return false;
    }

    public boolean getMirrorAutoDownSw() {
        return false;
    }

    public String getNormalDriveModeInfo() {
        return GlobalConstant.DEFAULT.NORMAL_DRIVE_MODE_NORMAL_CFG;
    }

    public boolean getRctaSw() {
        return false;
    }

    public boolean getRcwSw() {
        return false;
    }

    public boolean getSideReversingWarning() {
        return false;
    }

    public String getSpecialEcoPlusDriveModeInfo() {
        return GlobalConstant.DEFAULT.SPECIAL_DRIVE_MODE_ECO_PLUS_CFG;
    }

    public String getSpecialMudDriveModeInfo() {
        return GlobalConstant.DEFAULT.NORMAL_DRIVE_MODE_MUD_CFG;
    }

    public String getSpecialXPowerDriveModeInfo() {
        return GlobalConstant.DEFAULT.SPECIAL_DRIVE_MODE_X_POWER_CFG;
    }

    public boolean getSpeedLimit() {
        return false;
    }

    public int getSpeedLimitValue() {
        return 0;
    }

    public String getSportDriveModeInfo() {
        return GlobalConstant.DEFAULT.NORMAL_DRIVE_MODE_SPORT_CFG;
    }

    public int getWheelTouchDirection() {
        return 1;
    }

    public int getWheelTouchSpeed() {
        return 1;
    }

    public int getWiperInterval() {
        return 0;
    }

    public boolean isCiuRainEnable() {
        return false;
    }

    public void setAtlAutoBrightSw(boolean enable) {
    }

    public void setAutoDriveMode(boolean enable) {
    }

    public void setAutoLightSw(boolean enable) {
    }

    public void setAvasOtherVolume(int volume) {
    }

    public void setAvasSw(boolean enable) {
    }

    public void setAvasVolume(int volume) {
    }

    public void setBsdSw(boolean enable) {
    }

    public void setCiuRainEnable(boolean enable) {
    }

    public void setCiuWiperInterval(int interval) {
    }

    public void setDomeLight(int mode) {
    }

    public void setDowSw(boolean enable) {
    }

    public void setDriveAutoLock(boolean enable) {
    }

    public void setEbwEnable(boolean enabled) {
    }

    public void setElkSw(boolean enable) {
    }

    public void setHvacAirProtectMode(int mode) {
    }

    public void setHvacAqsLevel(int level) {
    }

    public void setHvacCircle(int mode) {
    }

    public void setHvacSmart(boolean enable) {
    }

    public void setIslcSw(boolean enable) {
    }

    public void setLdwSw(boolean enable) {
    }

    public void setMeterDefineMediaSource(boolean value) {
    }

    public void setMeterDefineScreenLight(boolean value) {
    }

    public void setMeterDefineTemperature(boolean value) {
    }

    public void setMeterDefineWindMode(boolean value) {
    }

    public void setMeterDefineWindPower(boolean value) {
    }

    public void setMirrorAutoDownSw(boolean enabled) {
    }

    public void setRctaSw(boolean enable) {
    }

    public void setRcwSw(boolean enable) {
    }

    public void setSideReversingWarning(boolean enable) {
    }

    public void setSpeedLimit(boolean value) {
    }

    public void setSpeedLimitValue(int value) {
    }

    public void setWheelTouchDirection(int direction) {
    }

    public void setWheelTouchHint(boolean enable) {
    }

    public void setWheelTouchSpeed(int speed) {
    }

    public void setWiperInterval(int interval) {
    }

    /* loaded from: classes2.dex */
    private static final class SingleHolder {
        private static final DataSyncModel INSTANCE = DataSyncModelFactory.createInstance();

        private SingleHolder() {
        }
    }

    public static DataSyncModel getInstance() {
        return SingleHolder.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public DataSyncModel() {
        generateAllDefaultSyncData();
    }

    public void init() {
        XId.getSyncApi().init();
        Tools.getSyncTransferTools().initDb(App.getInstance());
        this.mSyncDataValue.setLightMeHomeSw(SharedPreferenceUtil.isLightMeHomeEnabled());
        XId.getSyncApi().setSyncChangedListener(this, true);
        this.mCarControlSync = XId.getSyncApi().getCarControlSync();
        this.mAccountManager = XId.getAccountManager();
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.OnSyncChangedListener
    public void OnSyncChanged(final long uid, final SyncType syncType) {
        AccountInfo accountInfo = XId.getAccountApi().getAccountInfo();
        LogUtils.i(TAG, "OnSyncChanged: " + uid + ", syncType: " + syncType + ", accountInfo: " + (accountInfo == null ? "null" : accountInfo.toString()), false);
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.model.-$$Lambda$DataSyncModel$wPli4kMQFsByoya60djamPS6g-Q
            @Override // java.lang.Runnable
            public final void run() {
                DataSyncModel.this.lambda$OnSyncChanged$0$DataSyncModel(uid, syncType);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: syncChanged */
    public synchronized void lambda$OnSyncChanged$0$DataSyncModel(long uid, SyncType syncType) {
        LogUtils.i(TAG, "syncChanged: " + uid + ", syncType:" + syncType, false);
        if (syncType == SyncType.SYNC_GROUP_CREATED) {
            SyncGroupEventMsg syncGroupEventMsg = new SyncGroupEventMsg();
            syncGroupEventMsg.setName(SyncGroupEventMsg.TYPE_SYNC_GROUP_CREATED);
            GroupChangeListener groupChangeListener = this.mGroupChangeListener;
            if (groupChangeListener != null) {
                groupChangeListener.onAccountOrGroupChange(syncGroupEventMsg);
            }
        } else if (syncType == SyncType.SYNC_GROUP_SELECTED) {
            SyncGroupEventMsg syncGroupEventMsg2 = new SyncGroupEventMsg();
            syncGroupEventMsg2.setName(SyncGroupEventMsg.TYPE_SYNC_GROUP_SELECTED);
            GroupChangeListener groupChangeListener2 = this.mGroupChangeListener;
            if (groupChangeListener2 != null) {
                groupChangeListener2.onAccountOrGroupChange(syncGroupEventMsg2);
            }
        }
        if (uid != this.mUid) {
            this.mUid = uid;
            SyncGroupEventMsg syncGroupEventMsg3 = new SyncGroupEventMsg();
            syncGroupEventMsg3.setUid(this.mUid);
            syncGroupEventMsg3.setName(SyncGroupEventMsg.TYPE_SYNC_ACCOUNT_CHANGE);
            GroupChangeListener groupChangeListener3 = this.mGroupChangeListener;
            if (groupChangeListener3 != null) {
                groupChangeListener3.onAccountOrGroupChange(syncGroupEventMsg3);
            }
        }
        this.mSyncDataValue = new CarControlSyncDataValue();
        transferLocalData(syncType);
    }

    public void saveAllDataForSync() {
        CarBaseConfig carBaseConfig = CarBaseConfig.getInstance();
        LogUtils.i(TAG, "saveAllDataForSync");
        this.mCarControlSync.putDriveMode(String.valueOf(getDriveMode()));
        this.mCarControlSync.putXpedalMode(String.valueOf(isXpedal()));
        this.mCarControlSync.putAntisickMode(String.valueOf(isAntiSicknessEnabled()));
        this.mCarControlSync.putRecycleGrade(String.valueOf(getRecycleGrade()));
        this.mCarControlSync.putSteerMode(String.valueOf(getSteerMode()));
        this.mCarControlSync.putAutoHoldSw(String.valueOf(getAvh()));
        if (carBaseConfig.isSupportControlSteer()) {
            this.mCarControlSync.putSteerPos(String.valueOf(getSteerData()));
        }
        this.mCarControlSync.setWheelXKey(String.valueOf(getWheelXKey()));
        this.mCarControlSync.setDoorBossKey(String.valueOf(getDoorBossKey()));
        this.mCarControlSync.setDoorBossKeySW(String.valueOf(getDoorBossKeySw()));
        if (carBaseConfig.isSupportWheelKeyProtect()) {
            this.mCarControlSync.putWheelKeyProtectSw(String.valueOf(getWheelKeyProtect()));
        }
        if (carBaseConfig.isSupportNeutralGearProtect()) {
            this.mCarControlSync.putNGearProtectSw(String.valueOf(getNGearWarningSwitch()));
        }
        this.mCarControlSync.setLightMeHome(String.valueOf(getLightMeHome()));
        this.mCarControlSync.setLightMeHomeTime(String.valueOf(getLightMeHomeTime()));
        this.mCarControlSync.setParkLampB(String.valueOf(getParkLampB()));
        if (carBaseConfig.isSupportLampHeight()) {
            this.mCarControlSync.putLampHeightLevel(String.valueOf(getLampHeightLevel()));
            this.mCarControlSync.putAutoLampHeight(String.valueOf(isAutoLampHeight()));
        }
        this.mCarControlSync.setLluSW(String.valueOf(getLluSw()));
        this.mCarControlSync.setUnLockSW(String.valueOf(getLluUnlockSw()));
        this.mCarControlSync.setLockSW(String.valueOf(getLluLockSw()));
        this.mCarControlSync.setChargeSW(String.valueOf(getLluChargeSw()));
        this.mCarControlSync.setATLSW(String.valueOf(getAtlSw()));
        this.mCarControlSync.setATLSingleColor(String.valueOf(getAtlSingleColor()));
        if (carBaseConfig.isSupportFullAtl()) {
            this.mCarControlSync.setATLLight(String.valueOf(getAtlBright()));
            this.mCarControlSync.setATLEffect(getAtlEffect());
            this.mCarControlSync.setATLDualColorSW(String.valueOf(getAtlDualColorSw()));
            this.mCarControlSync.setATLDualColor(getAtlDualColorStr());
        }
        if (carBaseConfig.isSupportShowDriveAutoLock()) {
            this.mCarControlSync.putDriveAutoLock(String.valueOf(getDriveAutoLock()));
        }
        this.mCarControlSync.setStopAutoUnLock(String.valueOf(getParkAutoUnlock()));
        this.mCarControlSync.setUnLockResponse(String.valueOf(getUnlockResponse()));
        this.mCarControlSync.setAutoDHC(String.valueOf(getAutoDhc()));
        this.mCarControlSync.setChildLeftLock(String.valueOf(getChildLeftLock()));
        this.mCarControlSync.setChildRightLock(String.valueOf(getChildRightLock()));
        this.mCarControlSync.setChairPosNew(getDrvSeatSavedPosStr(0));
        this.mCarControlSync.setWelcomeMode(String.valueOf(getWelcomeMode()));
        this.mCarControlSync.setDrvSeatESB(String.valueOf(getDrvSeatEsb()));
        this.mCarControlSync.putRearSeatWarning(String.valueOf(getRsbWarning()));
        this.mCarControlSync.setMirrorPos(getMirrorData());
        this.mCarControlSync.setMirrorReverse(String.valueOf(getMirrorReverseMode()));
        this.mCarControlSync.setHighSPDCloseWin(String.valueOf(isWinHighSpdEnabled()));
        this.mCarControlSync.setLockCloseWin(String.valueOf(getLockCloseWin()));
        this.mCarControlSync.setWiperSensitivity(String.valueOf(getWiperSensitivity()));
        this.mCarControlSync.setAvasEffect(String.valueOf(getAvasEffect()));
        this.mCarControlSync.setAvasSayHiEffect(String.valueOf(getSayHiEffect()));
        this.mCarControlSync.setAvasBootEffect(String.valueOf(getBootEffect()));
        this.mCarControlSync.setAvasBootEffectBefore(String.valueOf(getBootEffectBeforeSw()));
        if (carBaseConfig.isSupportMeterSetting()) {
            this.mCarControlSync.putMeterTemperature(String.valueOf(getMeterDefineTemperature()));
            this.mCarControlSync.putMeterWindPower(String.valueOf(getMeterDefineWindPower()));
            this.mCarControlSync.putMeterWindMode(String.valueOf(getMeterDefineWindMode()));
            this.mCarControlSync.putMeterMediaSource(String.valueOf(getMeterDefineMediaSource()));
            this.mCarControlSync.putMeterScreenLight(String.valueOf(getMeterDefineScreenLight()));
            this.mCarControlSync.putSpeedLimitSw(String.valueOf(getSpeedLimit()));
            this.mCarControlSync.putSpeedLimitValue(String.valueOf(getSpeedLimitValue()));
        }
        this.mCarControlSync.putXPilotLcc(String.valueOf(getLccSw()));
        this.mCarControlSync.putXPilotAlc(String.valueOf(getAlcSw()));
        this.mCarControlSync.setKeyXPilotNra(String.valueOf(getNraState()));
        this.mCarControlSync.putLdwSw(String.valueOf(getLdwSw()));
        this.mCarControlSync.putBsdSw(String.valueOf(getBsdSw()));
        this.mCarControlSync.putRctaSw(String.valueOf(getSideReversingWarning()));
        this.mCarControlSync.putIsLcSw(String.valueOf(getIslcSw()));
        this.mCarControlSync.putEbwSw(String.valueOf(getEbwEnable()));
    }

    protected void saveToSync(String key, String value) {
        if (value == null) {
            value = "";
        }
        key.hashCode();
        char c = 65535;
        switch (key.hashCode()) {
            case -2093602326:
                if (key.equals("AVAS_EFFECT_NEW")) {
                    c = 0;
                    break;
                }
                break;
            case -2072339025:
                if (key.equals("AUTO_DHC")) {
                    c = 1;
                    break;
                }
                break;
            case -2064069196:
                if (key.equals("MIRROR_POS")) {
                    c = 2;
                    break;
                }
                break;
            case -2053899136:
                if (key.equals("WELCOME_MODE")) {
                    c = 3;
                    break;
                }
                break;
            case -2046338034:
                if (key.equals("LLU_SW")) {
                    c = 4;
                    break;
                }
                break;
            case -2034083381:
                if (key.equals("SDC_BREAK_CLOSE")) {
                    c = 5;
                    break;
                }
                break;
            case -2009682473:
                if (key.equals("CMS_REVERSE_SW")) {
                    c = 6;
                    break;
                }
                break;
            case -1993846292:
                if (key.equals("NGP_SW")) {
                    c = 7;
                    break;
                }
                break;
            case -1947634921:
                if (key.equals("STOP_AUTO_UNLOCK")) {
                    c = '\b';
                    break;
                }
                break;
            case -1905292307:
                if (key.equals("SMART_SPEED_LIMIT")) {
                    c = '\t';
                    break;
                }
                break;
            case -1893638336:
                if (key.equals("CITY_NGP_SW")) {
                    c = '\n';
                    break;
                }
                break;
            case -1847626897:
                if (key.equals("CMS_VIEW_ANGLE")) {
                    c = 11;
                    break;
                }
                break;
            case -1836895086:
                if (key.equals("ATL_SINGLE_COLOR")) {
                    c = '\f';
                    break;
                }
                break;
            case -1803065045:
                if (key.equals("WIPER_SENSITIVITY")) {
                    c = '\r';
                    break;
                }
                break;
            case -1764641283:
                if (key.equals("REAR_SEAT_WARNING")) {
                    c = 14;
                    break;
                }
                break;
            case -1744672547:
                if (key.equals("LIGHT_ME_HOME")) {
                    c = 15;
                    break;
                }
                break;
            case -1713755382:
                if (key.equals("CMS_TURN_EXTN_SW")) {
                    c = 16;
                    break;
                }
                break;
            case -1701595424:
                if (key.equals("CHILD_LEFT_LOCK")) {
                    c = 17;
                    break;
                }
                break;
            case -1700194432:
                if (key.equals("ATL_BRIGHT")) {
                    c = 18;
                    break;
                }
                break;
            case -1643565903:
                if (key.equals("CHILD_RIGHT_LOCK")) {
                    c = 19;
                    break;
                }
                break;
            case -1625480681:
                if (key.equals("ATL_EFFECT")) {
                    c = 20;
                    break;
                }
                break;
            case -1617403938:
                if (key.equals("DOOR_BOSS_KEY")) {
                    c = 21;
                    break;
                }
                break;
            case -1610746924:
                if (key.equals("WHEEL_X_KEY")) {
                    c = 22;
                    break;
                }
                break;
            case -1579563632:
                if (key.equals("PARK_LAMP_B")) {
                    c = 23;
                    break;
                }
                break;
            case -1557454709:
                if (key.equals("AVAS_BOOT_EFFECT_BEFORE_SW")) {
                    c = 24;
                    break;
                }
                break;
            case -1541458650:
                if (key.equals("DRIVE_AUTO_LOCK")) {
                    c = 25;
                    break;
                }
                break;
            case -1539870381:
                if (key.equals("LAMP_HEIGHT_LEVEL")) {
                    c = JSONLexer.EOI;
                    break;
                }
                break;
            case -1538235034:
                if (key.equals("METER_DEFINE_TEMPERATURE")) {
                    c = 27;
                    break;
                }
                break;
            case -1478943494:
                if (key.equals("CHASSIS_AVH")) {
                    c = 28;
                    break;
                }
                break;
            case -1387301007:
                if (key.equals("STEER_MODE")) {
                    c = 29;
                    break;
                }
                break;
            case -1312786092:
                if (key.equals("SIDE_REVERSING_WARNING")) {
                    c = 30;
                    break;
                }
                break;
            case -1261824064:
                if (key.equals("CDC_MODE")) {
                    c = 31;
                    break;
                }
                break;
            case -1237232026:
                if (key.equals("AVAS_BOOT_EFFECT")) {
                    c = ' ';
                    break;
                }
                break;
            case -1229535421:
                if (key.equals("CMS_HIGH_SPD_SW")) {
                    c = '!';
                    break;
                }
                break;
            case -1074313586:
                if (key.equals("LLU_LOCK_SW")) {
                    c = '\"';
                    break;
                }
                break;
            case -1046662064:
                if (key.equals("N_GEAR_PROTECT")) {
                    c = '#';
                    break;
                }
                break;
            case -949545772:
                if (key.equals("LEFT_DOOR_HOT_KEY")) {
                    c = '$';
                    break;
                }
                break;
            case -793075812:
                if (key.equals("METER_DEFINE_WIND_POWER")) {
                    c = '%';
                    break;
                }
                break;
            case -776853057:
                if (key.equals("DOME_LIGHT_BRIGHT")) {
                    c = '&';
                    break;
                }
                break;
            case -718409716:
                if (key.equals("METER_DEFINE_WIND_MODE")) {
                    c = '\'';
                    break;
                }
                break;
            case -579353305:
                if (key.equals("CMS_BRIGHTNESS")) {
                    c = '(';
                    break;
                }
                break;
            case -460390746:
                if (key.equals("STEER_POS")) {
                    c = ')';
                    break;
                }
                break;
            case -189155774:
                if (key.equals("MIRROR_REVERSE")) {
                    c = '*';
                    break;
                }
                break;
            case -90100558:
                if (key.equals("MIRROR_AUTO_DOWN")) {
                    c = '+';
                    break;
                }
                break;
            case -67940086:
                if (key.equals("CMS_VIEW_RECOVERY_SW")) {
                    c = ',';
                    break;
                }
                break;
            case -23314618:
                if (key.equals("ATL_DUAL_COLOR")) {
                    c = '-';
                    break;
                }
                break;
            case 34229204:
                if (key.equals("SENSOR_TRUNK_SW")) {
                    c = '.';
                    break;
                }
                break;
            case 133229215:
                if (key.equals("REAR_SEAT_WELCOME_MODE")) {
                    c = '/';
                    break;
                }
                break;
            case 156690368:
                if (key.equals("XPEDAL_MODE")) {
                    c = '0';
                    break;
                }
                break;
            case 174755007:
                if (key.equals("CMS_OBJECT_RECOGNIZE_SW")) {
                    c = '1';
                    break;
                }
                break;
            case 175199125:
                if (key.equals("SPEED_LIMIT_VALUE")) {
                    c = '2';
                    break;
                }
                break;
            case 264215791:
                if (key.equals("LIGHT_ME_HOME_TIME")) {
                    c = '3';
                    break;
                }
                break;
            case 332513133:
                if (key.equals("AS_WELCOME_MODE")) {
                    c = '4';
                    break;
                }
                break;
            case 368525578:
                if (key.equals("TRUNK_OPEN_POS")) {
                    c = '5';
                    break;
                }
                break;
            case 398383838:
                if (key.equals("AUTO_LAMP_HEIGHT")) {
                    c = '6';
                    break;
                }
                break;
            case 436482578:
                if (key.equals("SINGLEPEDAL")) {
                    c = '7';
                    break;
                }
                break;
            case 768324089:
                if (key.equals("XPILOT_ALC")) {
                    c = '8';
                    break;
                }
                break;
            case 768334381:
                if (key.equals("XPILOT_LCC")) {
                    c = '9';
                    break;
                }
                break;
            case 768336766:
                if (key.equals(KEY_XPILOT_NRA)) {
                    c = ':';
                    break;
                }
                break;
            case 847038181:
                if (key.equals("LLU_CHARGE_SW")) {
                    c = ';';
                    break;
                }
                break;
            case 910743793:
                if (key.equals("DRV_SEAT_ESB")) {
                    c = '<';
                    break;
                }
                break;
            case 935622111:
                if (key.equals(KEY_XPILOT_LSS_STATE)) {
                    c = '=';
                    break;
                }
                break;
            case 940124504:
                if (key.equals("CMS_AUTO_BRIGHTNESS_SW")) {
                    c = '>';
                    break;
                }
                break;
            case 973722404:
                if (key.equals("METER_DEFINE_MEDIA_SOURCE")) {
                    c = '?';
                    break;
                }
                break;
            case 995853699:
                if (key.equals("CHAIR_POSITION")) {
                    c = '@';
                    break;
                }
                break;
            case 1092131672:
                if (key.equals("DRIVE_MODE")) {
                    c = 'A';
                    break;
                }
                break;
            case 1123973375:
                if (key.equals("RIGHT_DOOR_HOT_KEY")) {
                    c = 'B';
                    break;
                }
                break;
            case 1157470821:
                if (key.equals("DOOR_BOSS_KEY_SW")) {
                    c = 'C';
                    break;
                }
                break;
            case 1157930411:
                if (key.equals("WHEEL_KEY_PROTECT")) {
                    c = 'D';
                    break;
                }
                break;
            case 1159133768:
                if (key.equals("BRAKE_WARNING")) {
                    c = 'E';
                    break;
                }
                break;
            case 1188152707:
                if (key.equals("SPEED_LIMIT")) {
                    c = 'F';
                    break;
                }
                break;
            case 1196932328:
                if (key.equals("XPILOT_AUTO_PARK_SW")) {
                    c = 'G';
                    break;
                }
                break;
            case 1219011069:
                if (key.equals("ATL_DUAL_COLOR_SW")) {
                    c = 'H';
                    break;
                }
                break;
            case 1254373246:
                if (key.equals("LANE_DEPARTURE_WARNING")) {
                    c = 'I';
                    break;
                }
                break;
            case 1296615849:
                if (key.equals("AVAS_EFFECT")) {
                    c = 'J';
                    break;
                }
                break;
            case 1327759445:
                if (key.equals("LLU_UNLOCK_SW")) {
                    c = 'K';
                    break;
                }
                break;
            case 1426514381:
                if (key.equals("CHAIR_POS_INDEX")) {
                    c = 'L';
                    break;
                }
                break;
            case 1454744856:
                if (key.equals("BLIND_DETECTION_WARNING")) {
                    c = 'M';
                    break;
                }
                break;
            case 1502708176:
                if (key.equals(KEY_XPILOT_MEM_PARK_SW)) {
                    c = 'N';
                    break;
                }
                break;
            case 1617210910:
                if (key.equals("CMS_POS")) {
                    c = 'O';
                    break;
                }
                break;
            case 1620191170:
                if (key.equals("CHAIR_POSITION_THREE")) {
                    c = 'P';
                    break;
                }
                break;
            case 1643621469:
                if (key.equals("CMS_LOW_SPD_SW")) {
                    c = 'Q';
                    break;
                }
                break;
            case 1742921963:
                if (key.equals("KEY_RECYCLE_GRADE")) {
                    c = 'R';
                    break;
                }
                break;
            case 1767447616:
                if (key.equals("HIGH_SPD_CLOSE_WIN")) {
                    c = 'S';
                    break;
                }
                break;
            case 1869840752:
                if (key.equals("CHAIR_POSITION_TWO")) {
                    c = 'T';
                    break;
                }
                break;
            case 1940828650:
                if (key.equals("ATL_SW")) {
                    c = 'U';
                    break;
                }
                break;
            case 2000589396:
                if (key.equals("CWC_SW")) {
                    c = 'V';
                    break;
                }
                break;
            case 2016455996:
                if (key.equals("AVAS_SAYHI_EFFECT")) {
                    c = 'W';
                    break;
                }
                break;
            case 2044840337:
                if (key.equals("METER_DEFINE_SCREEN_LIGHT")) {
                    c = 'X';
                    break;
                }
                break;
            case 2053283554:
                if (key.equals("ANTISICK_MODE")) {
                    c = 'Y';
                    break;
                }
                break;
            case 2070951617:
                if (key.equals("LOCK_CLOSE_WIN")) {
                    c = 'Z';
                    break;
                }
                break;
            case 2135346908:
                if (key.equals("UNLOCK_RESPONSE")) {
                    c = '[';
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                this.mCarControlSync.setAvasEffectNew(value);
                return;
            case 1:
                this.mCarControlSync.setAutoDHC(value);
                return;
            case 2:
                this.mCarControlSync.setMirrorPos(value);
                return;
            case 3:
                this.mCarControlSync.setWelcomeMode(value);
                return;
            case 4:
                this.mCarControlSync.setLluSW(value);
                return;
            case 5:
                this.mCarControlSync.setSdcBreakCloseType(value);
                return;
            case 6:
                this.mCarControlSync.setCmsReverseSw(value);
                return;
            case 7:
                this.mCarControlSync.putNgpSw(value);
                return;
            case '\b':
                this.mCarControlSync.setStopAutoUnLock(value);
                return;
            case '\t':
                this.mCarControlSync.putIsLcSw(value);
                return;
            case '\n':
                this.mCarControlSync.setCityNgpSw(value);
                return;
            case 11:
                this.mCarControlSync.setCmsViewAngle(value);
                return;
            case '\f':
                this.mCarControlSync.setATLSingleColor(value);
                return;
            case '\r':
                this.mCarControlSync.setWiperSensitivity(value);
                return;
            case 14:
                this.mCarControlSync.putRearSeatWarning(value);
                return;
            case 15:
                this.mCarControlSync.setLightMeHome(value);
                return;
            case 16:
                this.mCarControlSync.setCmsTurnExtnSw(value);
                return;
            case 17:
                this.mCarControlSync.setChildLeftLock(value);
                return;
            case 18:
                this.mCarControlSync.setATLLight(value);
                return;
            case 19:
                this.mCarControlSync.setChildRightLock(value);
                return;
            case 20:
                this.mCarControlSync.setATLEffect(value);
                return;
            case 21:
                this.mCarControlSync.setDoorBossKey(value);
                return;
            case 22:
                this.mCarControlSync.setWheelXKey(value);
                return;
            case 23:
                this.mCarControlSync.setParkLampB(value);
                return;
            case 24:
                this.mCarControlSync.setAvasBootEffectBefore(value);
                return;
            case 25:
                this.mCarControlSync.putDriveAutoLock(value);
                return;
            case 26:
                this.mCarControlSync.putLampHeightLevel(value);
                return;
            case 27:
                this.mCarControlSync.putMeterTemperature(value);
                return;
            case 28:
                this.mCarControlSync.putAutoHoldSw(value);
                return;
            case 29:
                this.mCarControlSync.putSteerMode(value);
                return;
            case 30:
                this.mCarControlSync.putRctaSw(value);
                return;
            case 31:
                this.mCarControlSync.setCdcMode(value);
                return;
            case ' ':
                this.mCarControlSync.setAvasBootEffect(value);
                return;
            case '!':
                this.mCarControlSync.setCmsHighSpdSw(value);
                return;
            case '\"':
                this.mCarControlSync.setLockSW(value);
                return;
            case '#':
                this.mCarControlSync.putNGearProtectSw(value);
                return;
            case '$':
                this.mCarControlSync.putLeftDoorHotKey(value);
                return;
            case '%':
                this.mCarControlSync.putMeterWindPower(value);
                return;
            case '&':
                this.mCarControlSync.setDomeLightBright(value);
                return;
            case '\'':
                this.mCarControlSync.putMeterWindMode(value);
                return;
            case '(':
                this.mCarControlSync.setCmsBrightness(value);
                return;
            case ')':
                this.mCarControlSync.putSteerPos(value);
                return;
            case '*':
                this.mCarControlSync.setMirrorReverse(value);
                return;
            case '+':
                this.mCarControlSync.putMirrorAutoDownSw(value);
                return;
            case ',':
                this.mCarControlSync.setCmsViewRecoverySw(value);
                return;
            case '-':
                this.mCarControlSync.setATLDualColor(value);
                return;
            case '.':
                this.mCarControlSync.setSensorTrunkSw(value);
                return;
            case '/':
                this.mCarControlSync.setRearSeatWelcomeMode(value);
                return;
            case '0':
                this.mCarControlSync.putXpedalMode(value);
                return;
            case '1':
                this.mCarControlSync.setCmsObjectRecognizeSw(value);
                return;
            case '2':
                this.mCarControlSync.putSpeedLimitValue(value);
                return;
            case '3':
                this.mCarControlSync.setLightMeHomeTime(value);
                return;
            case '4':
                this.mCarControlSync.setAsWelcomeMode(value);
                return;
            case '5':
                this.mCarControlSync.setTrunkOpenPos(value);
                return;
            case '6':
                this.mCarControlSync.putAutoLampHeight(value);
                return;
            case '7':
                this.mCarControlSync.setSinglePedal(value);
                return;
            case '8':
                this.mCarControlSync.putXPilotAlc(value);
                return;
            case '9':
                this.mCarControlSync.putXPilotLcc(value);
                return;
            case ':':
                this.mCarControlSync.setKeyXPilotNra(value);
                return;
            case ';':
                this.mCarControlSync.setChargeSW(value);
                return;
            case '<':
                this.mCarControlSync.setDrvSeatESB(value);
                return;
            case '=':
                this.mCarControlSync.putLssState(value);
                return;
            case '>':
                this.mCarControlSync.setCmsAutoBrightnessSw(value);
                return;
            case '?':
                this.mCarControlSync.putMeterMediaSource(value);
                return;
            case '@':
                this.mCarControlSync.setChairPositionOne(value);
                return;
            case 'A':
                this.mCarControlSync.putDriveMode(value);
                return;
            case 'B':
                this.mCarControlSync.putRightDoorHotKey(value);
                return;
            case 'C':
                this.mCarControlSync.setDoorBossKeySW(value);
                return;
            case 'D':
                this.mCarControlSync.putWheelKeyProtectSw(value);
                return;
            case 'E':
                this.mCarControlSync.putEbwSw(value);
                return;
            case 'F':
                this.mCarControlSync.putSpeedLimitSw(value);
                return;
            case 'G':
                this.mCarControlSync.putAutoParkSw(value);
                return;
            case 'H':
                this.mCarControlSync.setATLDualColorSW(value);
                return;
            case 'I':
                this.mCarControlSync.putLdwSw(value);
                return;
            case 'J':
                this.mCarControlSync.setAvasEffect(value);
                return;
            case 'K':
                this.mCarControlSync.setUnLockSW(value);
                return;
            case 'L':
                this.mCarControlSync.setChairPosIndex(value);
                return;
            case 'M':
                this.mCarControlSync.putBsdSw(value);
                return;
            case 'N':
                this.mCarControlSync.putMemParkSw(value);
                return;
            case 'O':
                this.mCarControlSync.setCmsPos(value);
                return;
            case 'P':
                this.mCarControlSync.setChairPositionThree(value);
                return;
            case 'Q':
                this.mCarControlSync.setCmsLowSpdSw(value);
                return;
            case 'R':
                this.mCarControlSync.putRecycleGrade(value);
                return;
            case 'S':
                this.mCarControlSync.setHighSPDCloseWin(value);
                return;
            case 'T':
                this.mCarControlSync.setChairPositionTwo(value);
                return;
            case 'U':
                this.mCarControlSync.setATLSW(value);
                return;
            case 'V':
                this.mCarControlSync.setCwcSw(value);
                return;
            case 'W':
                this.mCarControlSync.setAvasSayHiEffect(value);
                return;
            case 'X':
                this.mCarControlSync.putMeterScreenLight(value);
                return;
            case 'Y':
                this.mCarControlSync.putAntisickMode(value);
                return;
            case 'Z':
                this.mCarControlSync.setLockCloseWin(value);
                return;
            case '[':
                this.mCarControlSync.setUnLockResponse(value);
                return;
            default:
                return;
        }
    }

    private void transferLocalData(SyncType syncType) {
        Map<String, String> uidSyncDbOnWorkThread = Tools.getSyncTransferTools().getUidSyncDbOnWorkThread(this.mUid);
        StringBuilder sb = new StringBuilder();
        String str = "";
        String chairPosNew = this.mCarControlSync.getChairPosNew("");
        for (Map.Entry<String, String> entry : uidSyncDbOnWorkThread.entrySet()) {
            if ("CHAIR_POS_INDEX".equals(entry.getKey())) {
                str = entry.getValue();
            }
            saveToSync(entry.getKey(), entry.getValue());
            sb.append(entry.getKey());
            sb.append(QuickSettingConstants.JOINER);
            sb.append(TextUtils.isEmpty(entry.getValue()) ? "null" : entry.getValue());
            sb.append(";");
        }
        LogUtils.d(TAG, "transferLocalData size:" + uidSyncDbOnWorkThread.size() + ",syncSeatPosNew:" + chairPosNew + ",localSeatIndex:" + str + ",content:" + sb.toString(), false);
        if (!TextUtils.isEmpty(chairPosNew) && !TextUtils.isEmpty(str)) {
            transferSeatData();
            LogUtils.d(TAG, "Twice transfer data, seat maybe move", false);
        }
        syncChanged(syncType);
    }

    private void syncChanged(SyncType syncType) {
        SyncRestoreEvent syncRestoreEvent = new SyncRestoreEvent(this.mUid, new ArrayList());
        fillSyncDataList(syncRestoreEvent.list);
        LogUtils.i(TAG, "SyncRestoreEvent: " + syncRestoreEvent.toString() + "chairPos:" + this.mCarControlSync.getChairPosNew(""), false);
        SeatViewModel seatViewModel = (SeatViewModel) ViewModelManager.getInstance().getViewModelImpl(ISeatViewModel.class);
        seatViewModel.removeDropDownRun();
        if (isGuest()) {
            String currentSaveSeatPos = getCurrentSaveSeatPos(getCurrentSeatPos());
            LogUtils.i(TAG, "save current seat pos to guest :" + currentSaveSeatPos, false);
            this.mSyncDataValue.setDrvSeatPosNew(currentSaveSeatPos);
            SharedPreferenceUtil.saveDrvSeatData("CHAIR_POSITION_NEW", currentSaveSeatPos);
            handleSyncRestoreEvent(syncRestoreEvent);
        } else if (syncRestoreEvent.list.isEmpty()) {
            this.mSyncDataValue.setMirrorReverseMode(SharedPreferenceUtil.getMirrorReverseMode());
            this.mSyncDataValue.setHighSpdCloseWin(SharedPreferenceUtil.getHighSpdCloseWinSw());
            LogUtils.i(TAG, "saveAllDataForSync", false);
            saveAllDataForSync();
            fillSyncDataList(syncRestoreEvent.list);
            handleSyncRestoreEvent(syncRestoreEvent);
        } else if (TextUtils.isEmpty(this.mCarControlSync.getChairPosNew(""))) {
            syncRestoreEvent.list.add(new SyncData("CHAIR_POSITION_NEW", transferSeatData()));
            int parseInt = Integer.parseInt(this.mCarControlSync.getChairPosIndex("0"));
            if (parseInt > 0 && parseInt < 3) {
                XId.getSyncApi().requestSelectSyncGroupIndex(parseInt);
            } else {
                handleSyncRestoreEvent(syncRestoreEvent);
            }
        } else {
            if (SyncType.SYNC_GROUP_CREATED == syncType && !seatViewModel.isWelcomeModeState()) {
                String saveCurrentDrvSeatPosNew = saveCurrentDrvSeatPosNew(new int[]{seatViewModel.getDSeatHorzPos(), seatViewModel.getDSeatVerPos(), seatViewModel.getDSeatTiltPos(), seatViewModel.getDSeatLegPos(), seatViewModel.getDSeatCushionPos()});
                for (SyncData syncData : syncRestoreEvent.list) {
                    if (syncData != null && "CHAIR_POSITION_NEW".equals(syncData.key)) {
                        LogUtils.i(TAG, "update chair pos:" + saveCurrentDrvSeatPosNew, false);
                        syncData.value = saveCurrentDrvSeatPosNew;
                    }
                }
            }
            handleSyncRestoreEvent(syncRestoreEvent);
        }
    }

    private String saveCurrentDrvSeatPosNew(int[] seatPos) {
        if (BaseFeatureOption.getInstance().isSupportTiltPosSavedReversed() && seatPos != null && seatPos.length > 3) {
            seatPos[2] = 100 - seatPos[2];
        }
        String json = new Gson().toJson(seatPos);
        LogUtils.i(TAG, "saveCurrentDrvSeatPosNew: " + json);
        this.mSyncDataValue.setDrvSeatPosNew(json);
        this.mCarControlSync.setChairPosNew(json);
        SharedPreferenceUtil.saveDrvSeatData("CHAIR_POSITION_NEW", json);
        return json;
    }

    private String transferSeatData() {
        String currentSaveSeatPos = getCurrentSaveSeatPos(getCurrentSeatPos());
        String chairPositionOne = this.mCarControlSync.getChairPositionOne("");
        String chairPositionTwo = this.mCarControlSync.getChairPositionTwo("");
        String chairPositionThree = this.mCarControlSync.getChairPositionThree("");
        LogUtils.i(TAG, "currentChairPos:" + currentSaveSeatPos + ",posOne:" + (TextUtils.isEmpty(chairPositionOne) ? "null" : chairPositionOne) + ",posTwo:" + (TextUtils.isEmpty(chairPositionTwo) ? "null" : chairPositionTwo) + ",posThree:" + (TextUtils.isEmpty(chairPositionThree) ? "null" : chairPositionThree) + ",index:" + this.mCarControlSync.getChairPosIndex("null"), false);
        if (TextUtils.isEmpty(chairPositionOne)) {
            chairPositionOne = currentSaveSeatPos;
        }
        this.mSyncDataValue.setDrvSeatPosNew(chairPositionOne);
        this.mCarControlSync.setChairPosNew(0, chairPositionOne);
        if (TextUtils.isEmpty(chairPositionTwo)) {
            chairPositionTwo = currentSaveSeatPos;
        }
        this.mCarControlSync.setChairPosNew(1, chairPositionTwo);
        if (!TextUtils.isEmpty(chairPositionThree)) {
            currentSaveSeatPos = chairPositionThree;
        }
        this.mCarControlSync.setChairPosNew(2, currentSaveSeatPos);
        return chairPositionOne;
    }

    private int[] getCurrentSeatPos() {
        SeatViewModel seatViewModel = (SeatViewModel) ViewModelManager.getInstance().getViewModelImpl(ISeatViewModel.class);
        int[] iArr = new int[5];
        if (seatViewModel.isWelcomeModeState() || !seatViewModel.isDrvTiltMovingSafe()) {
            return Arrays.copyOf(CarBaseConfig.getInstance().getMSMSeatDefaultPos(), 5);
        }
        iArr[0] = seatViewModel.getDSeatHorzPos();
        iArr[1] = seatViewModel.getDSeatVerPos();
        iArr[2] = seatViewModel.getDSeatTiltPos();
        iArr[3] = seatViewModel.getDSeatLegPos();
        iArr[4] = seatViewModel.getDSeatCushionPos();
        return iArr;
    }

    private String getCurrentSaveSeatPos(int[] seatPos) {
        if (BaseFeatureOption.getInstance().isSupportTiltPosSavedReversed() && seatPos != null && seatPos.length >= 3) {
            seatPos[2] = 100 - seatPos[2];
        }
        return new Gson().toJson(seatPos);
    }

    protected long getCurrentAccountId() {
        Account[] accountsByType = this.mAccountManager.getAccountsByType("com.xiaopeng.accountservice.ACCOUNT_TYPE_XP_VEHICLE");
        if (accountsByType.length > 0) {
            Account account = accountsByType[0];
            LogUtils.i(TAG, "getCurrentAccountId accounts.length=" + accountsByType.length + ";account[0].name=" + account.name);
            try {
                String userData = this.mAccountManager.getUserData(account, "uid");
                LogUtils.i(TAG, "getCurrentAccountId uid=" + userData, false);
                return Long.parseLong(userData);
            } catch (Exception e) {
                LogUtils.w(TAG, "getCurrentAccountId Exception=" + e.getMessage(), false);
                return -1L;
            }
        }
        LogUtils.d(TAG, "getCurrentAccountId account is empty");
        return -1L;
    }

    public boolean isGuest() {
        return this.mUid == -1;
    }

    protected void handleSyncRestoreEvent(SyncRestoreEvent event) {
        CarControlSyncDataEvent carControlSyncDataEvent = new CarControlSyncDataEvent();
        ArrayMap arrayMap = new ArrayMap(this.mSyncFullMap);
        for (SyncData syncData : event.list) {
            LogUtils.i(TAG, "data = " + syncData);
            fillSyncData(syncData, carControlSyncDataEvent);
            if (isGuest()) {
                arrayMap.remove(syncData.key);
            }
        }
        if (isGuest()) {
            for (SyncData syncData2 : arrayMap.values()) {
                fillSyncData(syncData2, carControlSyncDataEvent);
            }
        } else {
            if (CarBaseConfig.getInstance().isSupportMirrorMemory() && !carControlSyncDataEvent.isMirrorReverse()) {
                this.mSyncDataValue.setMirrorReverseMode(SharedPreferenceUtil.getMirrorReverseMode());
                carControlSyncDataEvent.setMirrorReverse(true);
            }
            if (!carControlSyncDataEvent.isHighSpdCloseWin()) {
                this.mSyncDataValue.setHighSpdCloseWin(SharedPreferenceUtil.getHighSpdCloseWinSw());
                carControlSyncDataEvent.setHighSpdCloseWin(true);
            }
        }
        if (event.list.isEmpty()) {
            carControlSyncDataEvent.setDataEmpty(true);
        }
        this.mIsDataSynced = true;
        DataSyncChangeListener dataSyncChangeListener = this.mDataSyncChangeListener;
        if (dataSyncChangeListener != null) {
            dataSyncChangeListener.onAccountDataSynced(carControlSyncDataEvent);
        }
    }

    protected void fillSyncDataList(List<SyncData> list) {
        CarBaseConfig carBaseConfig = CarBaseConfig.getInstance();
        String driveMode = this.mCarControlSync.getDriveMode("");
        if (!TextUtils.isEmpty(driveMode)) {
            list.add(new SyncData("DRIVE_MODE", driveMode));
        } else {
            String valueOf = String.valueOf(SharedPreferenceUtil.getDriveMode());
            list.add(new SyncData("DRIVE_MODE", valueOf));
            this.mCarControlSync.putDriveMode(valueOf);
        }
        String xpedalMode = this.mCarControlSync.getXpedalMode("");
        if (!TextUtils.isEmpty(xpedalMode)) {
            list.add(new SyncData("XPEDAL_MODE", xpedalMode));
        } else {
            String valueOf2 = String.valueOf(SharedPreferenceUtil.getXpedal());
            list.add(new SyncData("XPEDAL_MODE", valueOf2));
            this.mCarControlSync.putXpedalMode(valueOf2);
        }
        String antisickMode = this.mCarControlSync.getAntisickMode("");
        if (!TextUtils.isEmpty(antisickMode)) {
            list.add(new SyncData("ANTISICK_MODE", antisickMode));
        } else {
            String valueOf3 = String.valueOf(SharedPreferenceUtil.isAntiSicknessEnabled());
            list.add(new SyncData("ANTISICK_MODE", valueOf3));
            this.mCarControlSync.putAntisickMode(valueOf3);
        }
        String recycleGrade = this.mCarControlSync.getRecycleGrade("");
        if (!TextUtils.isEmpty(recycleGrade)) {
            list.add(new SyncData("KEY_RECYCLE_GRADE", recycleGrade));
        } else {
            String valueOf4 = String.valueOf(SharedPreferenceUtil.getEnergyRecycleGrade());
            list.add(new SyncData("KEY_RECYCLE_GRADE", valueOf4));
            this.mCarControlSync.putRecycleGrade(valueOf4);
        }
        String steerMode = this.mCarControlSync.getSteerMode("");
        if (!TextUtils.isEmpty(steerMode)) {
            list.add(new SyncData("STEER_MODE", steerMode));
        } else {
            String valueOf5 = String.valueOf(SharedPreferenceUtil.getSteeringEps());
            list.add(new SyncData("STEER_MODE", valueOf5));
            this.mCarControlSync.putSteerMode(valueOf5);
        }
        String autoHoldSw = this.mCarControlSync.getAutoHoldSw("");
        if (!TextUtils.isEmpty(autoHoldSw)) {
            list.add(new SyncData("CHASSIS_AVH", autoHoldSw));
        } else {
            String valueOf6 = String.valueOf(SharedPreferenceUtil.isAvhEnabled());
            list.add(new SyncData("CHASSIS_AVH", valueOf6));
            this.mCarControlSync.putAutoHoldSw(valueOf6);
        }
        String asWelcomeMode = this.mCarControlSync.getAsWelcomeMode("");
        if (!TextUtils.isEmpty(asWelcomeMode)) {
            list.add(new SyncData("AS_WELCOME_MODE", asWelcomeMode));
        } else {
            String valueOf7 = String.valueOf(SharedPreferenceUtil.isAsWelcomeModeEnabled());
            list.add(new SyncData("AS_WELCOME_MODE", valueOf7));
            this.mCarControlSync.setAsWelcomeMode(valueOf7);
        }
        if (carBaseConfig.isSupportCdcControl()) {
            String cdcMode = this.mCarControlSync.getCdcMode("");
            if (!TextUtils.isEmpty(cdcMode)) {
                list.add(new SyncData("CDC_MODE", cdcMode));
            } else {
                String valueOf8 = String.valueOf(SharedPreferenceUtil.getCdcMode());
                list.add(new SyncData("CDC_MODE", valueOf8));
                this.mCarControlSync.setCdcMode(valueOf8);
            }
        }
        if (carBaseConfig.isSupportDriveModeNewArch()) {
            String singlePedal = this.mCarControlSync.getSinglePedal("");
            if (!TextUtils.isEmpty(singlePedal)) {
                list.add(new SyncData("SINGLEPEDAL", singlePedal));
            } else {
                String valueOf9 = String.valueOf(SharedPreferenceUtil.isNewDriveXPedalModeEnabled());
                list.add(new SyncData("SINGLEPEDAL", valueOf9));
                this.mCarControlSync.setSinglePedal(valueOf9);
            }
        }
        if (carBaseConfig.isSupportControlSteer()) {
            String steerPos = this.mCarControlSync.getSteerPos("");
            if (!TextUtils.isEmpty(steerPos)) {
                list.add(new SyncData("STEER_POS", steerPos));
            }
        }
        String wheelXKey = this.mCarControlSync.getWheelXKey("");
        if (!TextUtils.isEmpty(wheelXKey)) {
            list.add(new SyncData("WHEEL_X_KEY", wheelXKey));
        }
        if (carBaseConfig.isSupportDoorKeySetting()) {
            String doorBossKey = this.mCarControlSync.getDoorBossKey("");
            if (!TextUtils.isEmpty(doorBossKey)) {
                list.add(new SyncData("DOOR_BOSS_KEY", doorBossKey));
            }
        }
        if (carBaseConfig.isSupportBossKey()) {
            String doorBossKeySW = this.mCarControlSync.getDoorBossKeySW("");
            if (!TextUtils.isEmpty(doorBossKeySW)) {
                list.add(new SyncData("DOOR_BOSS_KEY_SW", doorBossKeySW));
            }
        }
        if (carBaseConfig.isSupportWheelKeyProtect()) {
            String wheelKeyProtectSw = this.mCarControlSync.getWheelKeyProtectSw("");
            if (!TextUtils.isEmpty(wheelKeyProtectSw)) {
                list.add(new SyncData("WHEEL_KEY_PROTECT", wheelKeyProtectSw));
            } else {
                String valueOf10 = String.valueOf(Settings.System.getInt(App.getInstance().getContentResolver(), IIcmController.KEY_WHEEL_KEY_PROTECT, 1) == 1);
                list.add(new SyncData("WHEEL_KEY_PROTECT", valueOf10));
                this.mCarControlSync.putWheelKeyProtectSw(valueOf10);
            }
        }
        if (carBaseConfig.isSupportNeutralGearProtect()) {
            String nGearProtectSw = this.mCarControlSync.getNGearProtectSw("");
            if (!TextUtils.isEmpty(nGearProtectSw)) {
                list.add(new SyncData("N_GEAR_PROTECT", nGearProtectSw));
            } else {
                String valueOf11 = String.valueOf(SharedPreferenceUtil.getNGearWarningSwitch());
                list.add(new SyncData("N_GEAR_PROTECT", valueOf11));
                this.mCarControlSync.putNGearProtectSw(valueOf11);
            }
        }
        if (carBaseConfig.isSupportLampHeight()) {
            String lampHeightLevel = this.mCarControlSync.getLampHeightLevel("");
            if (!TextUtils.isEmpty(lampHeightLevel)) {
                list.add(new SyncData("LAMP_HEIGHT_LEVEL", lampHeightLevel));
            }
            String autoLampHeight = this.mCarControlSync.getAutoLampHeight("");
            if (!TextUtils.isEmpty(autoLampHeight)) {
                list.add(new SyncData("AUTO_LAMP_HEIGHT", autoLampHeight));
            }
        }
        String lightMeHome = this.mCarControlSync.getLightMeHome("");
        if (!TextUtils.isEmpty(lightMeHome)) {
            list.add(new SyncData("LIGHT_ME_HOME", lightMeHome));
        }
        if (carBaseConfig.isSupportLightMeHomeTime()) {
            String lightMeHomeTime = this.mCarControlSync.getLightMeHomeTime("");
            if (!TextUtils.isEmpty(lightMeHomeTime)) {
                list.add(new SyncData("LIGHT_ME_HOME_TIME", lightMeHomeTime));
            }
        }
        if (carBaseConfig.isSupportLlu()) {
            String parkLampB = this.mCarControlSync.getParkLampB("");
            if (!TextUtils.isEmpty(parkLampB)) {
                list.add(new SyncData("PARK_LAMP_B", parkLampB));
            }
        }
        if (carBaseConfig.isSupportDomeLightIndependentCtrl()) {
            String domeLightBright = this.mCarControlSync.getDomeLightBright("");
            if (!TextUtils.isEmpty(domeLightBright)) {
                list.add(new SyncData("DOME_LIGHT_BRIGHT", domeLightBright));
            }
        }
        if (carBaseConfig.isSupportLlu()) {
            String lluSW = this.mCarControlSync.getLluSW("");
            if (!TextUtils.isEmpty(lluSW)) {
                list.add(new SyncData("LLU_SW", lluSW));
            } else {
                String valueOf12 = String.valueOf(SharedPreferenceUtil.isLluSwEnabled());
                list.add(new SyncData("LLU_SW", valueOf12));
                this.mCarControlSync.setLluSW(valueOf12);
            }
            String unLockSW = this.mCarControlSync.getUnLockSW("");
            if (!TextUtils.isEmpty(unLockSW)) {
                list.add(new SyncData("LLU_UNLOCK_SW", unLockSW));
            } else {
                String valueOf13 = String.valueOf(SharedPreferenceUtil.isLluUnlockSwEnabled());
                list.add(new SyncData("LLU_UNLOCK_SW", valueOf13));
                this.mCarControlSync.setUnLockSW(valueOf13);
            }
            String lockSW = this.mCarControlSync.getLockSW("");
            if (!TextUtils.isEmpty(lockSW)) {
                list.add(new SyncData("LLU_LOCK_SW", lockSW));
            } else {
                String valueOf14 = String.valueOf(SharedPreferenceUtil.isLluLockSwEnabled());
                list.add(new SyncData("LLU_LOCK_SW", valueOf14));
                this.mCarControlSync.setLockSW(valueOf14);
            }
            String chargeSW = this.mCarControlSync.getChargeSW("");
            if (!TextUtils.isEmpty(chargeSW)) {
                list.add(new SyncData("LLU_CHARGE_SW", chargeSW));
            } else {
                String valueOf15 = String.valueOf(SharedPreferenceUtil.isLluChargeSwEnabled());
                list.add(new SyncData("LLU_CHARGE_SW", valueOf15));
                this.mCarControlSync.setChargeSW(valueOf15);
            }
        }
        if (carBaseConfig.isSupportAtl()) {
            String atlsw = this.mCarControlSync.getATLSW("");
            if (!TextUtils.isEmpty(atlsw)) {
                list.add(new SyncData("ATL_SW", atlsw));
            }
            String aTLSingleColor = this.mCarControlSync.getATLSingleColor("");
            if (!TextUtils.isEmpty(aTLSingleColor)) {
                list.add(new SyncData("ATL_SINGLE_COLOR", aTLSingleColor));
            }
            if (carBaseConfig.isSupportFullAtl()) {
                String aTLLight = this.mCarControlSync.getATLLight("");
                if (!TextUtils.isEmpty(aTLLight)) {
                    list.add(new SyncData("ATL_BRIGHT", aTLLight));
                }
                String aTLEffect = this.mCarControlSync.getATLEffect("");
                if (!TextUtils.isEmpty(aTLEffect)) {
                    list.add(new SyncData("ATL_EFFECT", aTLEffect));
                }
                String aTLDualColorSW = this.mCarControlSync.getATLDualColorSW("");
                if (!TextUtils.isEmpty(aTLDualColorSW)) {
                    list.add(new SyncData("ATL_DUAL_COLOR_SW", aTLDualColorSW));
                }
                String aTLDualColor = this.mCarControlSync.getATLDualColor("");
                if (!TextUtils.isEmpty(aTLDualColor)) {
                    list.add(new SyncData("ATL_DUAL_COLOR", aTLDualColor));
                }
            }
        }
        if (carBaseConfig.isSupportShowDriveAutoLock()) {
            String driveAutoLock = this.mCarControlSync.getDriveAutoLock("");
            if (!TextUtils.isEmpty(driveAutoLock)) {
                list.add(new SyncData("DRIVE_AUTO_LOCK", driveAutoLock));
            }
        }
        String stopAutoUnLock = this.mCarControlSync.getStopAutoUnLock("");
        if (!TextUtils.isEmpty(stopAutoUnLock)) {
            list.add(new SyncData("STOP_AUTO_UNLOCK", stopAutoUnLock));
        }
        String unLockResponse = this.mCarControlSync.getUnLockResponse("");
        if (!TextUtils.isEmpty(unLockResponse)) {
            list.add(new SyncData("UNLOCK_RESPONSE", unLockResponse));
        }
        String autoDHC = this.mCarControlSync.getAutoDHC("");
        if (!TextUtils.isEmpty(autoDHC)) {
            list.add(new SyncData("AUTO_DHC", autoDHC));
        } else {
            String valueOf16 = String.valueOf(SharedPreferenceUtil.getAutoDoorHandle());
            list.add(new SyncData("AUTO_DHC", valueOf16));
            this.mCarControlSync.setAutoDHC(valueOf16);
        }
        if (carBaseConfig.isSupportChildLock()) {
            String childLeftLock = this.mCarControlSync.getChildLeftLock("");
            if (!TextUtils.isEmpty(childLeftLock)) {
                list.add(new SyncData("CHILD_LEFT_LOCK", childLeftLock));
            }
            String childRightLock = this.mCarControlSync.getChildRightLock("");
            if (!TextUtils.isEmpty(childRightLock)) {
                list.add(new SyncData("CHILD_RIGHT_LOCK", childRightLock));
            }
        }
        if (carBaseConfig.isSupportChildMode()) {
            String leftDoorHotKey = this.mCarControlSync.getLeftDoorHotKey("");
            if (!TextUtils.isEmpty(leftDoorHotKey)) {
                list.add(new SyncData("LEFT_DOOR_HOT_KEY", leftDoorHotKey));
            }
            String rightDoorHotKey = this.mCarControlSync.getRightDoorHotKey("");
            if (!TextUtils.isEmpty(rightDoorHotKey)) {
                list.add(new SyncData("RIGHT_DOOR_HOT_KEY", rightDoorHotKey));
            }
        }
        String chairPositionOne = this.mCarControlSync.getChairPositionOne("");
        if (!TextUtils.isEmpty(chairPositionOne)) {
            list.add(new SyncData("CHAIR_POSITION", chairPositionOne));
        }
        String chairPositionTwo = this.mCarControlSync.getChairPositionTwo("");
        if (!TextUtils.isEmpty(chairPositionTwo)) {
            list.add(new SyncData("CHAIR_POSITION_TWO", chairPositionTwo));
        }
        String chairPositionThree = this.mCarControlSync.getChairPositionThree("");
        if (!TextUtils.isEmpty(chairPositionThree)) {
            list.add(new SyncData("CHAIR_POSITION_THREE", chairPositionThree));
        }
        String chairPosIndex = this.mCarControlSync.getChairPosIndex("");
        if (!TextUtils.isEmpty(chairPosIndex)) {
            list.add(new SyncData("CHAIR_POS_INDEX", chairPosIndex));
        }
        String chairPosNew = this.mCarControlSync.getChairPosNew("");
        if (!TextUtils.isEmpty(chairPosNew)) {
            list.add(new SyncData("CHAIR_POSITION_NEW", chairPosNew));
        }
        String welcomeMode = this.mCarControlSync.getWelcomeMode("");
        if (!TextUtils.isEmpty(welcomeMode)) {
            list.add(new SyncData("WELCOME_MODE", welcomeMode));
        }
        String rearSeatWelcomeMode = this.mCarControlSync.getRearSeatWelcomeMode("");
        if (!TextUtils.isEmpty(rearSeatWelcomeMode)) {
            list.add(new SyncData("REAR_SEAT_WELCOME_MODE", rearSeatWelcomeMode));
        } else {
            String valueOf17 = String.valueOf(SharedPreferenceUtil.isRearSeatWelcomeModeEnabled());
            list.add(new SyncData("REAR_SEAT_WELCOME_MODE", valueOf17));
            this.mCarControlSync.setRearSeatWelcomeMode(valueOf17);
        }
        String drvSeatESB = this.mCarControlSync.getDrvSeatESB("");
        if (!TextUtils.isEmpty(drvSeatESB)) {
            list.add(new SyncData("DRV_SEAT_ESB", drvSeatESB));
        } else {
            String valueOf18 = String.valueOf(SharedPreferenceUtil.isEsbEnabled());
            list.add(new SyncData("DRV_SEAT_ESB", valueOf18));
            this.mCarControlSync.setDrvSeatESB(valueOf18);
        }
        String rearSeatWarning = this.mCarControlSync.getRearSeatWarning("");
        if (!TextUtils.isEmpty(rearSeatWarning)) {
            list.add(new SyncData("REAR_SEAT_WARNING", rearSeatWarning));
        } else {
            rearSeatWarning = String.valueOf(SharedPreferenceUtil.isRsbWarningEnabled());
            list.add(new SyncData("REAR_SEAT_WARNING", rearSeatWarning));
            this.mCarControlSync.putRearSeatWarning(rearSeatWarning);
        }
        if (carBaseConfig.isSupportMirrorMemory()) {
            String mirrorPos = this.mCarControlSync.getMirrorPos("");
            if (!TextUtils.isEmpty(mirrorPos)) {
                list.add(new SyncData("MIRROR_POS", mirrorPos));
            }
            rearSeatWarning = this.mCarControlSync.getMirrorReverse("");
            if (!TextUtils.isEmpty(rearSeatWarning)) {
                list.add(new SyncData("MIRROR_REVERSE", rearSeatWarning));
            }
        }
        if (carBaseConfig.isSupportMirrorDown()) {
            String mirrorPos2 = this.mCarControlSync.getMirrorPos("");
            if (!TextUtils.isEmpty(mirrorPos2)) {
                list.add(new SyncData("MIRROR_POS", mirrorPos2));
            }
            rearSeatWarning = this.mCarControlSync.getMirrorAutoDownSw("");
            if (!TextUtils.isEmpty(rearSeatWarning)) {
                list.add(new SyncData("MIRROR_AUTO_DOWN", rearSeatWarning));
            } else {
                rearSeatWarning = String.valueOf(SharedPreferenceUtil.getMirrorAutoDown());
                list.add(new SyncData("MIRROR_AUTO_DOWN", rearSeatWarning));
                this.mCarControlSync.putRearSeatWarning(rearSeatWarning);
            }
        }
        if (BaseFeatureOption.getInstance().isSupportMirrorAutoFoldOff()) {
            if (!TextUtils.isEmpty(rearSeatWarning)) {
                list.add(new SyncData(KEY_MIRROR_AUTO_FOLD, rearSeatWarning));
            } else {
                list.add(new SyncData(KEY_MIRROR_AUTO_FOLD, String.valueOf(SharedPreferenceUtil.getMirrorAutoFoldEnable())));
            }
        }
        if (carBaseConfig.isSupportSensorTrunk()) {
            String sensorTrunkSw = this.mCarControlSync.getSensorTrunkSw("");
            if (!TextUtils.isEmpty(sensorTrunkSw)) {
                list.add(new SyncData("SENSOR_TRUNK_SW", sensorTrunkSw));
            } else {
                String valueOf19 = String.valueOf(SharedPreferenceUtil.isTrunkSensorEnable());
                list.add(new SyncData("SENSOR_TRUNK_SW", valueOf19));
                this.mCarControlSync.setSensorTrunkSw(valueOf19);
            }
        }
        if (carBaseConfig.isSupportTrunkSetPosition()) {
            String trunkOpenPos = this.mCarControlSync.getTrunkOpenPos("");
            if (!TextUtils.isEmpty(trunkOpenPos)) {
                list.add(new SyncData("TRUNK_OPEN_POS", trunkOpenPos));
            } else {
                String valueOf20 = String.valueOf(SharedPreferenceUtil.getTrunkFullOpenPos());
                list.add(new SyncData("TRUNK_OPEN_POS", valueOf20));
                this.mCarControlSync.setTrunkOpenPos(valueOf20);
            }
        }
        String highSPDCloseWin = this.mCarControlSync.getHighSPDCloseWin("");
        if (!TextUtils.isEmpty(highSPDCloseWin)) {
            list.add(new SyncData("HIGH_SPD_CLOSE_WIN", highSPDCloseWin));
        }
        if (carBaseConfig.isSupportAutoCloseWin()) {
            String lockCloseWin = this.mCarControlSync.getLockCloseWin("");
            if (!TextUtils.isEmpty(lockCloseWin)) {
                list.add(new SyncData("LOCK_CLOSE_WIN", lockCloseWin));
            }
        }
        if (carBaseConfig.isSupportWiperSenCfg()) {
            String wiperSensitivity = this.mCarControlSync.getWiperSensitivity("");
            if (!TextUtils.isEmpty(wiperSensitivity)) {
                list.add(new SyncData("WIPER_SENSITIVITY", wiperSensitivity));
            }
        }
        if (carBaseConfig.isSupportCwc()) {
            String cwcSw = this.mCarControlSync.getCwcSw("");
            if (!TextUtils.isEmpty(cwcSw)) {
                list.add(new SyncData("CWC_SW", cwcSw));
            } else {
                String valueOf21 = String.valueOf(SharedPreferenceUtil.getCwcSw());
                list.add(new SyncData("CWC_SW", valueOf21));
                this.mCarControlSync.setCwcSw(valueOf21);
            }
        }
        if (carBaseConfig.isNewAvasArch()) {
            if (BaseFeatureOption.getInstance().isSupportAvasNewMemKey()) {
                String avasEffectNew = this.mCarControlSync.getAvasEffectNew("");
                if (!TextUtils.isEmpty(avasEffectNew)) {
                    list.add(new SyncData("AVAS_EFFECT_NEW", avasEffectNew));
                } else {
                    String valueOf22 = String.valueOf(SharedPreferenceUtil.getAvasLowSpdEffect());
                    list.add(new SyncData("AVAS_EFFECT_NEW", valueOf22));
                    this.mCarControlSync.setAvasEffectNew(valueOf22);
                }
            } else {
                String avasEffect = this.mCarControlSync.getAvasEffect("");
                if (!TextUtils.isEmpty(avasEffect)) {
                    list.add(new SyncData("AVAS_EFFECT", avasEffect));
                }
            }
            String avasSayHiEffect = this.mCarControlSync.getAvasSayHiEffect("");
            if (!TextUtils.isEmpty(avasSayHiEffect)) {
                list.add(new SyncData("AVAS_SAYHI_EFFECT", avasSayHiEffect));
            }
            String avasBootEffect = this.mCarControlSync.getAvasBootEffect("");
            if (!TextUtils.isEmpty(avasBootEffect)) {
                list.add(new SyncData("AVAS_BOOT_EFFECT", avasBootEffect));
            }
            String avasBootEffectBefore = this.mCarControlSync.getAvasBootEffectBefore("");
            if (!TextUtils.isEmpty(avasBootEffectBefore)) {
                list.add(new SyncData("AVAS_BOOT_EFFECT_BEFORE_SW", avasBootEffectBefore));
            }
        } else {
            String avasEffectNew2 = this.mCarControlSync.getAvasEffectNew("");
            if (!TextUtils.isEmpty(avasEffectNew2)) {
                list.add(new SyncData("AVAS_EFFECT_NEW", avasEffectNew2));
            } else {
                String valueOf23 = String.valueOf(SharedPreferenceUtil.getAvasLowSpdEffect());
                list.add(new SyncData("AVAS_EFFECT_NEW", valueOf23));
                this.mCarControlSync.setAvasEffectNew(valueOf23);
            }
        }
        if (carBaseConfig.isSupportMeterSetting()) {
            String meterTemperature = this.mCarControlSync.getMeterTemperature("");
            if (!TextUtils.isEmpty(meterTemperature)) {
                list.add(new SyncData("METER_DEFINE_TEMPERATURE", meterTemperature));
            }
            String meterWindPower = this.mCarControlSync.getMeterWindPower("");
            if (!TextUtils.isEmpty(meterWindPower)) {
                list.add(new SyncData("METER_DEFINE_WIND_POWER", meterWindPower));
            }
            String meterWindMode = this.mCarControlSync.getMeterWindMode("");
            if (!TextUtils.isEmpty(meterWindMode)) {
                list.add(new SyncData("METER_DEFINE_WIND_MODE", meterWindMode));
            }
            String meterMediaSource = this.mCarControlSync.getMeterMediaSource("");
            if (!TextUtils.isEmpty(meterMediaSource)) {
                list.add(new SyncData("METER_DEFINE_MEDIA_SOURCE", meterMediaSource));
            }
            String meterScreenLight = this.mCarControlSync.getMeterScreenLight("");
            if (!TextUtils.isEmpty(meterScreenLight)) {
                list.add(new SyncData("METER_DEFINE_SCREEN_LIGHT", meterScreenLight));
            }
            String speedLimitSw = this.mCarControlSync.getSpeedLimitSw("");
            if (!TextUtils.isEmpty(speedLimitSw)) {
                list.add(new SyncData("SPEED_LIMIT", speedLimitSw));
            }
            String speedLimitValue = this.mCarControlSync.getSpeedLimitValue("");
            if (!TextUtils.isEmpty(speedLimitValue)) {
                list.add(new SyncData("SPEED_LIMIT_VALUE", speedLimitValue));
            }
        }
        String autoParkSw = this.mCarControlSync.getAutoParkSw("");
        if (!TextUtils.isEmpty(autoParkSw)) {
            list.add(new SyncData("XPILOT_AUTO_PARK_SW", autoParkSw));
        }
        String memParkSw = this.mCarControlSync.getMemParkSw("");
        if (!TextUtils.isEmpty(memParkSw)) {
            list.add(new SyncData(KEY_XPILOT_MEM_PARK_SW, memParkSw));
        }
        String xPilotLcc = this.mCarControlSync.getXPilotLcc("");
        if (!TextUtils.isEmpty(xPilotLcc)) {
            list.add(new SyncData("XPILOT_LCC", xPilotLcc));
        }
        String xPilotAlc = this.mCarControlSync.getXPilotAlc("");
        if (!TextUtils.isEmpty(xPilotAlc)) {
            list.add(new SyncData("XPILOT_ALC", xPilotAlc));
        }
        String keyXPilotNra = this.mCarControlSync.getKeyXPilotNra("");
        if (!TextUtils.isEmpty(keyXPilotNra)) {
            list.add(new SyncData(KEY_XPILOT_NRA, keyXPilotNra));
        }
        String ngpSw = this.mCarControlSync.getNgpSw("");
        if (!TextUtils.isEmpty(ngpSw)) {
            list.add(new SyncData("NGP_SW", ngpSw));
        }
        if (carBaseConfig.isSupportCNgp()) {
            String cityNgpSw = this.mCarControlSync.getCityNgpSw("");
            if (!TextUtils.isEmpty(cityNgpSw)) {
                list.add(new SyncData("CITY_NGP_SW", cityNgpSw));
            }
        }
        if (!carBaseConfig.isNewScuArch()) {
            if (!carBaseConfig.isSupportLka()) {
                String ldwSw = this.mCarControlSync.getLdwSw("");
                if (!TextUtils.isEmpty(ldwSw)) {
                    list.add(new SyncData("LANE_DEPARTURE_WARNING", ldwSw));
                }
            } else {
                String lssState = this.mCarControlSync.getLssState("");
                if (!TextUtils.isEmpty(lssState)) {
                    list.add(new SyncData(KEY_XPILOT_LSS_STATE, lssState));
                }
            }
            String bsdSw = this.mCarControlSync.getBsdSw("");
            if (!TextUtils.isEmpty(bsdSw)) {
                list.add(new SyncData("BLIND_DETECTION_WARNING", bsdSw));
            }
            String rctaSw = this.mCarControlSync.getRctaSw("");
            if (!TextUtils.isEmpty(rctaSw)) {
                list.add(new SyncData("SIDE_REVERSING_WARNING", rctaSw));
            }
            if (carBaseConfig.isSupportIslc()) {
                String isLcSw = this.mCarControlSync.getIsLcSw("");
                if (!TextUtils.isEmpty(isLcSw)) {
                    list.add(new SyncData("SMART_SPEED_LIMIT", isLcSw));
                }
            }
            String ebwSw = this.mCarControlSync.getEbwSw("");
            if (!TextUtils.isEmpty(ebwSw)) {
                list.add(new SyncData("BRAKE_WARNING", ebwSw));
            }
        }
        if (carBaseConfig.isSupportSdc()) {
            String sdcBreakCloseType = this.mCarControlSync.getSdcBreakCloseType("");
            if (!TextUtils.isEmpty(sdcBreakCloseType)) {
                list.add(new SyncData("SDC_BREAK_CLOSE", sdcBreakCloseType));
            }
        }
        if (carBaseConfig.isSupportCms()) {
            String cmsAutoBrightnessSw = this.mCarControlSync.getCmsAutoBrightnessSw("");
            if (!TextUtils.isEmpty(cmsAutoBrightnessSw)) {
                list.add(new SyncData("CMS_AUTO_BRIGHTNESS_SW", cmsAutoBrightnessSw));
            }
            String cmsBrightness = this.mCarControlSync.getCmsBrightness("");
            if (!TextUtils.isEmpty(cmsBrightness)) {
                list.add(new SyncData("CMS_BRIGHTNESS", cmsBrightness));
            }
            String cmsHighSpdSw = this.mCarControlSync.getCmsHighSpdSw("");
            if (!TextUtils.isEmpty(cmsHighSpdSw)) {
                list.add(new SyncData("CMS_HIGH_SPD_SW", cmsHighSpdSw));
            }
            String cmsLowSpdSw = this.mCarControlSync.getCmsLowSpdSw("");
            if (!TextUtils.isEmpty(cmsLowSpdSw)) {
                list.add(new SyncData("CMS_LOW_SPD_SW", cmsLowSpdSw));
            }
            String cmsReverseSw = this.mCarControlSync.getCmsReverseSw("");
            if (!TextUtils.isEmpty(cmsReverseSw)) {
                list.add(new SyncData("CMS_REVERSE_SW", cmsReverseSw));
            }
            String cmsTurnExtnSw = this.mCarControlSync.getCmsTurnExtnSw("");
            if (!TextUtils.isEmpty(cmsTurnExtnSw)) {
                list.add(new SyncData("CMS_TURN_EXTN_SW", cmsTurnExtnSw));
            }
            String cmsObjectRecognizeSw = this.mCarControlSync.getCmsObjectRecognizeSw("");
            if (!TextUtils.isEmpty(cmsObjectRecognizeSw)) {
                list.add(new SyncData("CMS_OBJECT_RECOGNIZE_SW", cmsObjectRecognizeSw));
            }
            String cmsViewRecoverySw = this.mCarControlSync.getCmsViewRecoverySw("");
            if (!TextUtils.isEmpty(cmsViewRecoverySw)) {
                list.add(new SyncData("CMS_VIEW_RECOVERY_SW", cmsViewRecoverySw));
            }
            String cmsPos = this.mCarControlSync.getCmsPos("");
            if (!TextUtils.isEmpty(cmsPos)) {
                list.add(new SyncData("CMS_VIEW_RECOVERY_SW", cmsPos));
            }
            String cmsViewAngle = this.mCarControlSync.getCmsViewAngle("");
            if (TextUtils.isEmpty(cmsViewAngle)) {
                return;
            }
            list.add(new SyncData("CMS_VIEW_ANGLE", cmsViewAngle));
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void fillSyncData(SyncData data, CarControlSyncDataEvent syncDataFlag) {
        boolean z;
        int i;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        boolean z6;
        String str = data.key;
        str.hashCode();
        int i2 = 50;
        boolean z7 = false;
        int i3 = 0;
        int i4 = 0;
        char c = 65535;
        switch (str.hashCode()) {
            case -2093602326:
                if (str.equals("AVAS_EFFECT_NEW")) {
                    c = 0;
                    break;
                }
                break;
            case -2072339025:
                if (str.equals("AUTO_DHC")) {
                    c = 1;
                    break;
                }
                break;
            case -2064069196:
                if (str.equals("MIRROR_POS")) {
                    c = 2;
                    break;
                }
                break;
            case -2053899136:
                if (str.equals("WELCOME_MODE")) {
                    c = 3;
                    break;
                }
                break;
            case -2046338034:
                if (str.equals("LLU_SW")) {
                    c = 4;
                    break;
                }
                break;
            case -2034083381:
                if (str.equals("SDC_BREAK_CLOSE")) {
                    c = 5;
                    break;
                }
                break;
            case -2009682473:
                if (str.equals("CMS_REVERSE_SW")) {
                    c = 6;
                    break;
                }
                break;
            case -1993846292:
                if (str.equals("NGP_SW")) {
                    c = 7;
                    break;
                }
                break;
            case -1947634921:
                if (str.equals("STOP_AUTO_UNLOCK")) {
                    c = '\b';
                    break;
                }
                break;
            case -1905292307:
                if (str.equals("SMART_SPEED_LIMIT")) {
                    c = '\t';
                    break;
                }
                break;
            case -1893638336:
                if (str.equals("CITY_NGP_SW")) {
                    c = '\n';
                    break;
                }
                break;
            case -1847626897:
                if (str.equals("CMS_VIEW_ANGLE")) {
                    c = 11;
                    break;
                }
                break;
            case -1836895086:
                if (str.equals("ATL_SINGLE_COLOR")) {
                    c = '\f';
                    break;
                }
                break;
            case -1803065045:
                if (str.equals("WIPER_SENSITIVITY")) {
                    c = '\r';
                    break;
                }
                break;
            case -1764641283:
                if (str.equals("REAR_SEAT_WARNING")) {
                    c = 14;
                    break;
                }
                break;
            case -1744672547:
                if (str.equals("LIGHT_ME_HOME")) {
                    c = 15;
                    break;
                }
                break;
            case -1713755382:
                if (str.equals("CMS_TURN_EXTN_SW")) {
                    c = 16;
                    break;
                }
                break;
            case -1701595424:
                if (str.equals("CHILD_LEFT_LOCK")) {
                    c = 17;
                    break;
                }
                break;
            case -1700194432:
                if (str.equals("ATL_BRIGHT")) {
                    c = 18;
                    break;
                }
                break;
            case -1643565903:
                if (str.equals("CHILD_RIGHT_LOCK")) {
                    c = 19;
                    break;
                }
                break;
            case -1625480681:
                if (str.equals("ATL_EFFECT")) {
                    c = 20;
                    break;
                }
                break;
            case -1617403938:
                if (str.equals("DOOR_BOSS_KEY")) {
                    c = 21;
                    break;
                }
                break;
            case -1610746924:
                if (str.equals("WHEEL_X_KEY")) {
                    c = 22;
                    break;
                }
                break;
            case -1579563632:
                if (str.equals("PARK_LAMP_B")) {
                    c = 23;
                    break;
                }
                break;
            case -1557454709:
                if (str.equals("AVAS_BOOT_EFFECT_BEFORE_SW")) {
                    c = 24;
                    break;
                }
                break;
            case -1541458650:
                if (str.equals("DRIVE_AUTO_LOCK")) {
                    c = 25;
                    break;
                }
                break;
            case -1539870381:
                if (str.equals("LAMP_HEIGHT_LEVEL")) {
                    c = JSONLexer.EOI;
                    break;
                }
                break;
            case -1538235034:
                if (str.equals("METER_DEFINE_TEMPERATURE")) {
                    c = 27;
                    break;
                }
                break;
            case -1478943494:
                if (str.equals("CHASSIS_AVH")) {
                    c = 28;
                    break;
                }
                break;
            case -1387301007:
                if (str.equals("STEER_MODE")) {
                    c = 29;
                    break;
                }
                break;
            case -1312786092:
                if (str.equals("SIDE_REVERSING_WARNING")) {
                    c = 30;
                    break;
                }
                break;
            case -1261824064:
                if (str.equals("CDC_MODE")) {
                    c = 31;
                    break;
                }
                break;
            case -1237232026:
                if (str.equals("AVAS_BOOT_EFFECT")) {
                    c = ' ';
                    break;
                }
                break;
            case -1229535421:
                if (str.equals("CMS_HIGH_SPD_SW")) {
                    c = '!';
                    break;
                }
                break;
            case -1074313586:
                if (str.equals("LLU_LOCK_SW")) {
                    c = '\"';
                    break;
                }
                break;
            case -1046662064:
                if (str.equals("N_GEAR_PROTECT")) {
                    c = '#';
                    break;
                }
                break;
            case -949545772:
                if (str.equals("LEFT_DOOR_HOT_KEY")) {
                    c = '$';
                    break;
                }
                break;
            case -793075812:
                if (str.equals("METER_DEFINE_WIND_POWER")) {
                    c = '%';
                    break;
                }
                break;
            case -776853057:
                if (str.equals("DOME_LIGHT_BRIGHT")) {
                    c = '&';
                    break;
                }
                break;
            case -718409716:
                if (str.equals("METER_DEFINE_WIND_MODE")) {
                    c = '\'';
                    break;
                }
                break;
            case -579353305:
                if (str.equals("CMS_BRIGHTNESS")) {
                    c = '(';
                    break;
                }
                break;
            case -460390746:
                if (str.equals("STEER_POS")) {
                    c = ')';
                    break;
                }
                break;
            case -189155774:
                if (str.equals("MIRROR_REVERSE")) {
                    c = '*';
                    break;
                }
                break;
            case -90100558:
                if (str.equals("MIRROR_AUTO_DOWN")) {
                    c = '+';
                    break;
                }
                break;
            case -90041327:
                if (str.equals(KEY_MIRROR_AUTO_FOLD)) {
                    c = ',';
                    break;
                }
                break;
            case -67940086:
                if (str.equals("CMS_VIEW_RECOVERY_SW")) {
                    c = '-';
                    break;
                }
                break;
            case -23314618:
                if (str.equals("ATL_DUAL_COLOR")) {
                    c = '.';
                    break;
                }
                break;
            case 34229204:
                if (str.equals("SENSOR_TRUNK_SW")) {
                    c = '/';
                    break;
                }
                break;
            case 133229215:
                if (str.equals("REAR_SEAT_WELCOME_MODE")) {
                    c = '0';
                    break;
                }
                break;
            case 156690368:
                if (str.equals("XPEDAL_MODE")) {
                    c = '1';
                    break;
                }
                break;
            case 174755007:
                if (str.equals("CMS_OBJECT_RECOGNIZE_SW")) {
                    c = '2';
                    break;
                }
                break;
            case 175199125:
                if (str.equals("SPEED_LIMIT_VALUE")) {
                    c = '3';
                    break;
                }
                break;
            case 264215791:
                if (str.equals("LIGHT_ME_HOME_TIME")) {
                    c = '4';
                    break;
                }
                break;
            case 332513133:
                if (str.equals("AS_WELCOME_MODE")) {
                    c = '5';
                    break;
                }
                break;
            case 368525578:
                if (str.equals("TRUNK_OPEN_POS")) {
                    c = '6';
                    break;
                }
                break;
            case 398383838:
                if (str.equals("AUTO_LAMP_HEIGHT")) {
                    c = '7';
                    break;
                }
                break;
            case 436482578:
                if (str.equals("SINGLEPEDAL")) {
                    c = '8';
                    break;
                }
                break;
            case 768324089:
                if (str.equals("XPILOT_ALC")) {
                    c = '9';
                    break;
                }
                break;
            case 768334381:
                if (str.equals("XPILOT_LCC")) {
                    c = ':';
                    break;
                }
                break;
            case 768336766:
                if (str.equals(KEY_XPILOT_NRA)) {
                    c = ';';
                    break;
                }
                break;
            case 847038181:
                if (str.equals("LLU_CHARGE_SW")) {
                    c = '<';
                    break;
                }
                break;
            case 910743793:
                if (str.equals("DRV_SEAT_ESB")) {
                    c = '=';
                    break;
                }
                break;
            case 935622111:
                if (str.equals(KEY_XPILOT_LSS_STATE)) {
                    c = '>';
                    break;
                }
                break;
            case 940124504:
                if (str.equals("CMS_AUTO_BRIGHTNESS_SW")) {
                    c = '?';
                    break;
                }
                break;
            case 973722404:
                if (str.equals("METER_DEFINE_MEDIA_SOURCE")) {
                    c = '@';
                    break;
                }
                break;
            case 995853699:
                if (str.equals("CHAIR_POSITION")) {
                    c = 'A';
                    break;
                }
                break;
            case 1092131672:
                if (str.equals("DRIVE_MODE")) {
                    c = 'B';
                    break;
                }
                break;
            case 1123973375:
                if (str.equals("RIGHT_DOOR_HOT_KEY")) {
                    c = 'C';
                    break;
                }
                break;
            case 1157470821:
                if (str.equals("DOOR_BOSS_KEY_SW")) {
                    c = 'D';
                    break;
                }
                break;
            case 1157930411:
                if (str.equals("WHEEL_KEY_PROTECT")) {
                    c = 'E';
                    break;
                }
                break;
            case 1159133768:
                if (str.equals("BRAKE_WARNING")) {
                    c = 'F';
                    break;
                }
                break;
            case 1188152707:
                if (str.equals("SPEED_LIMIT")) {
                    c = 'G';
                    break;
                }
                break;
            case 1196932328:
                if (str.equals("XPILOT_AUTO_PARK_SW")) {
                    c = 'H';
                    break;
                }
                break;
            case 1219011069:
                if (str.equals("ATL_DUAL_COLOR_SW")) {
                    c = 'I';
                    break;
                }
                break;
            case 1254373246:
                if (str.equals("LANE_DEPARTURE_WARNING")) {
                    c = 'J';
                    break;
                }
                break;
            case 1296615849:
                if (str.equals("AVAS_EFFECT")) {
                    c = 'K';
                    break;
                }
                break;
            case 1327759445:
                if (str.equals("LLU_UNLOCK_SW")) {
                    c = 'L';
                    break;
                }
                break;
            case 1426514381:
                if (str.equals("CHAIR_POS_INDEX")) {
                    c = 'M';
                    break;
                }
                break;
            case 1454744856:
                if (str.equals("BLIND_DETECTION_WARNING")) {
                    c = 'N';
                    break;
                }
                break;
            case 1502708176:
                if (str.equals(KEY_XPILOT_MEM_PARK_SW)) {
                    c = 'O';
                    break;
                }
                break;
            case 1617210910:
                if (str.equals("CMS_POS")) {
                    c = 'P';
                    break;
                }
                break;
            case 1620191170:
                if (str.equals("CHAIR_POSITION_THREE")) {
                    c = 'Q';
                    break;
                }
                break;
            case 1643621469:
                if (str.equals("CMS_LOW_SPD_SW")) {
                    c = 'R';
                    break;
                }
                break;
            case 1742921963:
                if (str.equals("KEY_RECYCLE_GRADE")) {
                    c = 'S';
                    break;
                }
                break;
            case 1767447616:
                if (str.equals("HIGH_SPD_CLOSE_WIN")) {
                    c = 'T';
                    break;
                }
                break;
            case 1869834436:
                if (str.equals("CHAIR_POSITION_NEW")) {
                    c = 'U';
                    break;
                }
                break;
            case 1869840752:
                if (str.equals("CHAIR_POSITION_TWO")) {
                    c = 'V';
                    break;
                }
                break;
            case 1940828650:
                if (str.equals("ATL_SW")) {
                    c = 'W';
                    break;
                }
                break;
            case 2000589396:
                if (str.equals("CWC_SW")) {
                    c = 'X';
                    break;
                }
                break;
            case 2016455996:
                if (str.equals("AVAS_SAYHI_EFFECT")) {
                    c = 'Y';
                    break;
                }
                break;
            case 2044840337:
                if (str.equals("METER_DEFINE_SCREEN_LIGHT")) {
                    c = 'Z';
                    break;
                }
                break;
            case 2053283554:
                if (str.equals("ANTISICK_MODE")) {
                    c = '[';
                    break;
                }
                break;
            case 2070951617:
                if (str.equals("LOCK_CLOSE_WIN")) {
                    c = '\\';
                    break;
                }
                break;
            case 2135346908:
                if (str.equals("UNLOCK_RESPONSE")) {
                    c = ']';
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                if (isGuest()) {
                    this.mSyncDataValue.setAvasEffect(SharedPreferenceUtil.getAvasLowSpdEffect());
                } else {
                    int parseInt = Integer.parseInt(data.value);
                    this.mSyncDataValue.setAvasEffect(parseInt);
                    SharedPreferenceUtil.setAvasLowSpdEffect(parseInt);
                }
                syncDataFlag.setAvasEffect(true);
                return;
            case 1:
                if (isGuest()) {
                    this.mSyncDataValue.setAutoDhc(SharedPreferenceUtil.getAutoDoorHandle());
                } else {
                    boolean parseBoolean = Boolean.parseBoolean(data.value);
                    this.mSyncDataValue.setAutoDhc(parseBoolean);
                    SharedPreferenceUtil.setAutoDoorHandle(parseBoolean);
                }
                syncDataFlag.setAutoDhc(true);
                return;
            case 2:
                if (isGuest()) {
                    this.mSyncDataValue.setMirrorPos(SharedPreferenceUtil.getRearMirrorData());
                } else {
                    this.mSyncDataValue.setMirrorPos(data.value);
                    SharedPreferenceUtil.setRearMirrorData(data.value);
                }
                if (TextUtils.isEmpty(data.value)) {
                    return;
                }
                this.mSyncDataValue.setNeedRestore(true);
                syncDataFlag.setMirrorPos(true);
                return;
            case 3:
                if (isGuest()) {
                    this.mSyncDataValue.setWelcomeMode(SharedPreferenceUtil.isWelcomeModeEnabled());
                } else {
                    boolean parseBoolean2 = Boolean.parseBoolean(data.value);
                    this.mSyncDataValue.setWelcomeMode(parseBoolean2);
                    SharedPreferenceUtil.setWelcomeMode(parseBoolean2);
                }
                syncDataFlag.setWelcomeMode(true);
                return;
            case 4:
                if (isGuest()) {
                    this.mSyncDataValue.setLluSw(SharedPreferenceUtil.isLluSwEnabled());
                } else {
                    boolean parseBoolean3 = Boolean.parseBoolean(data.value);
                    this.mSyncDataValue.setLluSw(parseBoolean3);
                    SharedPreferenceUtil.setLluSw(parseBoolean3);
                }
                syncDataFlag.setLluSw(true);
                return;
            case 5:
                if (isGuest()) {
                    this.mSyncDataValue.setSdcBrakeCloseCfg(SharedPreferenceUtil.getSdcBrakeCloseCfg());
                } else {
                    try {
                        i3 = Integer.parseInt(data.value);
                    } catch (Exception unused) {
                        LogUtils.w(TAG, "Fill Sdc Brake close type sync data error, value:" + data.value, false);
                    }
                    this.mSyncDataValue.setSdcBrakeCloseCfg(i3);
                    SharedPreferenceUtil.setSdcBrakeCloseCfg(i3);
                }
                syncDataFlag.setSdcBrakeCloseType(true);
                return;
            case 6:
                if (isGuest()) {
                    this.mSyncDataValue.setCmsReverseSw(SharedPreferenceUtil.getCmsReverseSw());
                } else {
                    try {
                        z = Boolean.parseBoolean(data.value);
                    } catch (Exception unused2) {
                        LogUtils.w(TAG, "Fill KEY_CMS_REVERSE_SW data error, value:" + data.value, false);
                        z = true;
                    }
                    this.mSyncDataValue.setCmsReverseSw(z);
                    SharedPreferenceUtil.setCmsReverseSw(z);
                }
                syncDataFlag.setCmsReverseSw(true);
                return;
            case 7:
                if (isGuest()) {
                    SharedPreferenceUtil.setNgpSw(false);
                    this.mSyncDataValue.setNgpSw(SharedPreferenceUtil.getNgpSw());
                } else {
                    boolean parseBoolean4 = Boolean.parseBoolean(data.value);
                    this.mSyncDataValue.setNgpSw(parseBoolean4);
                    SharedPreferenceUtil.setNgpSw(parseBoolean4);
                }
                syncDataFlag.setNgpSw(true);
                return;
            case '\b':
                if (isGuest()) {
                    this.mSyncDataValue.setParkAutoUnlock(SharedPreferenceUtil.getParkAutoUnlock());
                } else {
                    boolean parseBoolean5 = Boolean.parseBoolean(data.value);
                    this.mSyncDataValue.setParkAutoUnlock(parseBoolean5);
                    SharedPreferenceUtil.setParkAutoUnlock(parseBoolean5);
                }
                syncDataFlag.setParkAutoUnlock(true);
                return;
            case '\t':
                if (isGuest()) {
                    this.mSyncDataValue.setIslcSw(SharedPreferenceUtil.getIslcSw());
                } else {
                    boolean parseBoolean6 = Boolean.parseBoolean(data.value);
                    this.mSyncDataValue.setIslcSw(parseBoolean6);
                    SharedPreferenceUtil.setIslcSw(parseBoolean6);
                }
                syncDataFlag.setIslc(true);
                return;
            case '\n':
                if (isGuest()) {
                    SharedPreferenceUtil.setCityNgpSw(false);
                    this.mSyncDataValue.setCityNgpSw(SharedPreferenceUtil.getCityNgpSw());
                } else {
                    boolean parseBoolean7 = Boolean.parseBoolean(data.value);
                    this.mSyncDataValue.setCityNgpSw(parseBoolean7);
                    SharedPreferenceUtil.setCityNgpSw(parseBoolean7);
                }
                syncDataFlag.setCityNgpSw(true);
                return;
            case 11:
                if (isGuest()) {
                    this.mSyncDataValue.setCmsViewAngle(SharedPreferenceUtil.getCmsViewAngle());
                } else {
                    try {
                        i = Integer.parseInt(data.value);
                    } catch (Exception unused3) {
                        LogUtils.w(TAG, "Fill KEY_CMS_VIEW_ANGLE data error, value:" + data.value, false);
                        i = 1;
                    }
                    this.mSyncDataValue.setCmsViewAngle(i);
                    SharedPreferenceUtil.setCmsViewAngle(i);
                }
                syncDataFlag.setCmsViewAngle(true);
                return;
            case '\f':
                if (isGuest()) {
                    this.mSyncDataValue.setAtlSingleColor(SharedPreferenceUtil.getAtlSingleColor());
                } else {
                    int parseInt2 = Integer.parseInt(data.value);
                    this.mSyncDataValue.setAtlSingleColor(parseInt2);
                    SharedPreferenceUtil.setAtlSingleColor(parseInt2);
                }
                syncDataFlag.setAtlSingleColor(true);
                return;
            case '\r':
                if (isGuest()) {
                    this.mSyncDataValue.setWiperSensitivity(SharedPreferenceUtil.getWiperIntervalGear());
                } else {
                    int parseInt3 = Integer.parseInt(data.value);
                    this.mSyncDataValue.setWiperSensitivity(parseInt3);
                    SharedPreferenceUtil.setWiperIntervalGear(parseInt3);
                }
                syncDataFlag.setWiperSensitivity(true);
                return;
            case 14:
                if (isGuest()) {
                    this.mSyncDataValue.setRsbWarning(SharedPreferenceUtil.isRsbWarningEnabled());
                } else {
                    boolean parseBoolean8 = Boolean.parseBoolean(data.value);
                    this.mSyncDataValue.setRsbWarning(parseBoolean8);
                    SharedPreferenceUtil.setRsbWarningEnable(parseBoolean8);
                }
                syncDataFlag.setRsbWarning(true);
                return;
            case 15:
                if (isGuest()) {
                    this.mSyncDataValue.setLightMeHomeSw(SharedPreferenceUtil.isLightMeHomeEnabled());
                } else {
                    boolean parseBoolean9 = Boolean.parseBoolean(data.value);
                    this.mSyncDataValue.setLightMeHomeSw(parseBoolean9);
                    SharedPreferenceUtil.setLightMeHome(parseBoolean9);
                }
                syncDataFlag.setLightMeHome(true);
                return;
            case 16:
                if (isGuest()) {
                    this.mSyncDataValue.setCmsTurnSw(SharedPreferenceUtil.getCmsTurnSw());
                } else {
                    try {
                        z2 = Boolean.parseBoolean(data.value);
                    } catch (Exception unused4) {
                        LogUtils.w(TAG, "Fill KEY_CMS_TURN_EXTN_SW data error, value:" + data.value, false);
                        z2 = true;
                    }
                    this.mSyncDataValue.setCmsTurnSw(z2);
                    SharedPreferenceUtil.setCmsTurnSw(z2);
                }
                syncDataFlag.setCmsTurnSw(true);
                return;
            case 17:
                if (isGuest()) {
                    this.mSyncDataValue.setChildLeftLock(SharedPreferenceUtil.getLeftChildLockSw());
                } else {
                    boolean parseBoolean10 = Boolean.parseBoolean(data.value);
                    this.mSyncDataValue.setChildLeftLock(parseBoolean10);
                    SharedPreferenceUtil.setLeftChildLockSw(parseBoolean10);
                }
                syncDataFlag.setChildLeftLock(true);
                return;
            case 18:
                if (isGuest()) {
                    this.mSyncDataValue.setAtlBrightCfg(SharedPreferenceUtil.getAtlManualBrightness());
                } else {
                    int parseInt4 = Integer.parseInt(data.value);
                    this.mSyncDataValue.setAtlBrightCfg(parseInt4);
                    SharedPreferenceUtil.setAtlManualBrightness(parseInt4);
                }
                syncDataFlag.setAtlBright(true);
                return;
            case 19:
                if (isGuest()) {
                    this.mSyncDataValue.setChildRightLock(SharedPreferenceUtil.getRightChildLockSw());
                } else {
                    boolean parseBoolean11 = Boolean.parseBoolean(data.value);
                    this.mSyncDataValue.setChildRightLock(parseBoolean11);
                    SharedPreferenceUtil.setRightChildLockSw(parseBoolean11);
                }
                syncDataFlag.setChildRightLock(true);
                return;
            case 20:
                if (isGuest()) {
                    this.mSyncDataValue.setAtlEffectCfg(SharedPreferenceUtil.getAtlEffectMode());
                } else {
                    this.mSyncDataValue.setAtlEffectCfg(data.value);
                    SharedPreferenceUtil.setAtlEffectMode(data.value);
                }
                syncDataFlag.setAtlEffect(!TextUtils.isEmpty(data.value));
                return;
            case 21:
                if (isGuest()) {
                    this.mSyncDataValue.setDoorBossKey(SharedPreferenceUtil.getDoorKey());
                } else {
                    int parseInt5 = Integer.parseInt(data.value);
                    this.mSyncDataValue.setDoorBossKey(parseInt5);
                    SharedPreferenceUtil.setDoorKey(parseInt5);
                }
                syncDataFlag.setDoorBossKey(true);
                return;
            case 22:
                if (isGuest()) {
                    this.mSyncDataValue.setWheelXKey(SharedPreferenceUtil.getWheelXKey());
                } else {
                    int parseInt6 = Integer.parseInt(data.value);
                    this.mSyncDataValue.setWheelXKey(parseInt6);
                    SharedPreferenceUtil.setWheelXKey(parseInt6);
                }
                syncDataFlag.setWheelXKey(true);
                return;
            case 23:
                if (isGuest()) {
                    this.mSyncDataValue.setParkLampB(SharedPreferenceUtil.getParkLampB());
                } else {
                    boolean parseBoolean12 = Boolean.parseBoolean(data.value);
                    this.mSyncDataValue.setParkLampB(parseBoolean12);
                    SharedPreferenceUtil.setParkLampB(parseBoolean12);
                }
                syncDataFlag.setParkLampB(true);
                return;
            case 24:
                if (isGuest()) {
                    this.mSyncDataValue.setBootEffectBeforeSw(SharedPreferenceUtil.getBootEffectOld());
                } else {
                    int parseInt7 = Integer.parseInt(data.value);
                    this.mSyncDataValue.setBootEffectBeforeSw(parseInt7);
                    SharedPreferenceUtil.setBootEffectOld(parseInt7);
                }
                syncDataFlag.setBootEffectBeforeSw(true);
                return;
            case 25:
                if (isGuest()) {
                    this.mSyncDataValue.setDriveAutoLock(SharedPreferenceUtil.getDriveAutoLock());
                } else {
                    boolean parseBoolean13 = Boolean.parseBoolean(data.value);
                    this.mSyncDataValue.setDriveAutoLock(parseBoolean13);
                    SharedPreferenceUtil.setDriveAutoLock(parseBoolean13);
                }
                syncDataFlag.setDriveAutoLock(true);
                return;
            case 26:
                if (isGuest()) {
                    this.mSyncDataValue.setLampHeightLevel(SharedPreferenceUtil.getLampHeightLevel());
                } else {
                    int parseInt8 = Integer.parseInt(data.value);
                    this.mSyncDataValue.setLampHeightLevel(parseInt8);
                    SharedPreferenceUtil.setLampHeightLevel(parseInt8);
                }
                syncDataFlag.setLampHeightLevel(true);
                return;
            case 27:
                if (isGuest()) {
                    this.mSyncDataValue.setMeterDefineTemperature(SharedPreferenceUtil.getMeterMenuTemperature());
                } else {
                    boolean parseBoolean14 = Boolean.parseBoolean(data.value);
                    this.mSyncDataValue.setMeterDefineTemperature(parseBoolean14);
                    SharedPreferenceUtil.setMeterMenuTemperature(parseBoolean14);
                }
                syncDataFlag.setMeterDefineTemperature(true);
                return;
            case 28:
                if (isGuest()) {
                    this.mSyncDataValue.setAvh(SharedPreferenceUtil.isAvhEnabled());
                } else {
                    boolean parseBoolean15 = Boolean.parseBoolean(data.value);
                    this.mSyncDataValue.setAvh(parseBoolean15);
                    SharedPreferenceUtil.setAvhEnabled(parseBoolean15);
                }
                syncDataFlag.setAvh(true);
                return;
            case 29:
                if (isGuest()) {
                    this.mSyncDataValue.setSteerMode(SharedPreferenceUtil.getSteeringEps());
                } else {
                    int parseInt9 = Integer.parseInt(data.value);
                    this.mSyncDataValue.setSteerMode(parseInt9);
                    SharedPreferenceUtil.setSteeringEps(parseInt9);
                }
                syncDataFlag.setSteerMode(true);
                return;
            case 30:
                if (isGuest()) {
                    this.mSyncDataValue.setSideReversingWarning(SharedPreferenceUtil.getSideReverseWarningSw());
                } else {
                    boolean parseBoolean16 = Boolean.parseBoolean(data.value);
                    this.mSyncDataValue.setSideReversingWarning(parseBoolean16);
                    SharedPreferenceUtil.setSideReverseWarningSw(parseBoolean16);
                }
                syncDataFlag.setSideReversingWarning(true);
                return;
            case 31:
                if (isGuest()) {
                    this.mSyncDataValue.setCdcMode(SharedPreferenceUtil.getCdcMode());
                } else {
                    int parseInt10 = Integer.parseInt(data.value);
                    this.mSyncDataValue.setCdcMode(parseInt10);
                    SharedPreferenceUtil.setCdcMode(parseInt10);
                }
                syncDataFlag.setCdcMode(true);
                return;
            case ' ':
                if (isGuest()) {
                    this.mSyncDataValue.setBootEffect(SharedPreferenceUtil.getBootEffect());
                } else {
                    int parseInt11 = Integer.parseInt(data.value);
                    this.mSyncDataValue.setBootEffect(parseInt11);
                    SharedPreferenceUtil.setBootEffect(parseInt11);
                }
                syncDataFlag.setBootEffect(true);
                return;
            case '!':
                if (isGuest()) {
                    this.mSyncDataValue.setCmsHighSpdSw(SharedPreferenceUtil.getCmsHighSpdSw());
                } else {
                    try {
                        z3 = Boolean.parseBoolean(data.value);
                    } catch (Exception unused5) {
                        LogUtils.w(TAG, "Fill KEY_CMS_HIGH_SPD_SW data error, value:" + data.value, false);
                        z3 = true;
                    }
                    this.mSyncDataValue.setCmsHighSpdSw(z3);
                    SharedPreferenceUtil.setCmsHighSpdSw(z3);
                }
                syncDataFlag.setCmsHighSpdSw(true);
                return;
            case '\"':
                if (isGuest()) {
                    this.mSyncDataValue.setLluLockSw(SharedPreferenceUtil.isLluLockSwEnabled());
                } else {
                    boolean parseBoolean17 = Boolean.parseBoolean(data.value);
                    this.mSyncDataValue.setLluLockSw(parseBoolean17);
                    SharedPreferenceUtil.setLluLockSw(parseBoolean17);
                }
                syncDataFlag.setLluLockSw(true);
                return;
            case '#':
                if (isGuest()) {
                    this.mSyncDataValue.setNGearProtectSw(SharedPreferenceUtil.getNGearWarningSwitch());
                } else {
                    boolean parseBoolean18 = Boolean.parseBoolean(data.value);
                    this.mSyncDataValue.setNGearProtectSw(parseBoolean18);
                    SharedPreferenceUtil.setNGearWarningSwitch(parseBoolean18);
                }
                syncDataFlag.setNGearProtectSw(true);
                return;
            case '$':
                if (isGuest()) {
                    this.mSyncDataValue.setLeftDoorHotKey(SharedPreferenceUtil.getLeftDoorHotKeySw());
                } else {
                    boolean parseBoolean19 = Boolean.parseBoolean(data.value);
                    this.mSyncDataValue.setLeftDoorHotKey(parseBoolean19);
                    SharedPreferenceUtil.setLeftDoorHotKeySw(parseBoolean19);
                }
                syncDataFlag.setLeftDoorHotKey(true);
                return;
            case '%':
                if (isGuest()) {
                    this.mSyncDataValue.setMeterDefineWindPower(SharedPreferenceUtil.getMeterMenuWindPower());
                } else {
                    boolean parseBoolean20 = Boolean.parseBoolean(data.value);
                    this.mSyncDataValue.setMeterDefineWindPower(parseBoolean20);
                    SharedPreferenceUtil.setMeterMenuWindPower(parseBoolean20);
                }
                syncDataFlag.setMeterDefineWindPower(true);
                return;
            case '&':
                if (isGuest()) {
                    this.mSyncDataValue.setDomeLightBright(SharedPreferenceUtil.getDomeLightBright());
                } else {
                    int parseInt12 = Integer.parseInt(data.value);
                    this.mSyncDataValue.setDomeLightBright(parseInt12);
                    SharedPreferenceUtil.setDomeLightBright(parseInt12);
                }
                syncDataFlag.setDomeLightBright(true);
                return;
            case '\'':
                if (isGuest()) {
                    this.mSyncDataValue.setMeterDefineWindMode(SharedPreferenceUtil.getMeterMenuWindMode());
                } else {
                    boolean parseBoolean21 = Boolean.parseBoolean(data.value);
                    this.mSyncDataValue.setMeterDefineWindMode(parseBoolean21);
                    SharedPreferenceUtil.setMeterMenuWindMode(parseBoolean21);
                }
                syncDataFlag.setMeterDefineWindMode(true);
                return;
            case '(':
                if (isGuest()) {
                    this.mSyncDataValue.setCmsBright(SharedPreferenceUtil.getCmsBright());
                } else {
                    try {
                        i2 = Integer.parseInt(data.value);
                    } catch (Exception unused6) {
                        LogUtils.w(TAG, "Fill KEY_CMS_BRIGHTNESS data error, value:" + data.value, false);
                    }
                    this.mSyncDataValue.setCmsBright(i2);
                    SharedPreferenceUtil.setCmsBright(i2);
                }
                syncDataFlag.setCmsBright(true);
                return;
            case ')':
                if (isGuest()) {
                    this.mSyncDataValue.setSteerPos(SharedPreferenceUtil.getSteerPos());
                } else {
                    String str2 = data.value;
                    this.mSyncDataValue.setSteerPos(str2);
                    SharedPreferenceUtil.setSteerPos(str2);
                }
                syncDataFlag.setSteerPos(true);
                return;
            case '*':
                if (isGuest()) {
                    this.mSyncDataValue.setMirrorReverseMode(SharedPreferenceUtil.getMirrorReverseMode());
                } else {
                    int parseInt13 = Integer.parseInt(data.value);
                    this.mSyncDataValue.setMirrorReverseMode(parseInt13);
                    SharedPreferenceUtil.setMirrorReverseMode(parseInt13);
                }
                syncDataFlag.setMirrorReverse(true);
                return;
            case '+':
                if (isGuest()) {
                    this.mSyncDataValue.setMirrorAutoDown(SharedPreferenceUtil.getMirrorAutoDown());
                } else {
                    boolean parseBoolean22 = Boolean.parseBoolean(data.value);
                    this.mSyncDataValue.setMirrorAutoDown(parseBoolean22);
                    SharedPreferenceUtil.setMirrorAutoDown(parseBoolean22);
                }
                syncDataFlag.setMirrorAutoDown(true);
                return;
            case ',':
                if (isGuest()) {
                    this.mSyncDataValue.setMirrorAutoFoldEnable(SharedPreferenceUtil.getMirrorAutoFoldEnable());
                } else {
                    boolean parseBoolean23 = Boolean.parseBoolean(data.value);
                    this.mSyncDataValue.setMirrorAutoFoldEnable(parseBoolean23);
                    SharedPreferenceUtil.setMirrorAutoFoldEnable(parseBoolean23);
                }
                syncDataFlag.setMirrorAutoFold(true);
                return;
            case '-':
                if (isGuest()) {
                    this.mSyncDataValue.setCmsViewRecoverySw(SharedPreferenceUtil.getCmsViewRecoverySw());
                } else {
                    try {
                        z4 = Boolean.parseBoolean(data.value);
                    } catch (Exception unused7) {
                        LogUtils.w(TAG, "Fill KEY_CMS_VIEW_RECOVERY_SW data error, value:" + data.value, false);
                        z4 = true;
                    }
                    this.mSyncDataValue.setCmsViewRecoverySw(z4);
                    SharedPreferenceUtil.setCmsViewRecoverySw(z4);
                }
                syncDataFlag.setCmsViewRecoverySw(true);
                return;
            case '.':
                if (isGuest()) {
                    this.mSyncDataValue.setAtlDualColor(SharedPreferenceUtil.getAtlDualColor());
                } else {
                    this.mSyncDataValue.setAtlDualColor(data.value);
                    SharedPreferenceUtil.setAtlDualColor(data.value);
                }
                syncDataFlag.setAtlDualColor(!TextUtils.isEmpty(data.value));
                return;
            case '/':
                if (isGuest()) {
                    this.mSyncDataValue.setSensorTrunkSw(SharedPreferenceUtil.isTrunkSensorEnable());
                } else {
                    boolean parseBoolean24 = Boolean.parseBoolean(data.value);
                    this.mSyncDataValue.setSensorTrunkSw(parseBoolean24);
                    SharedPreferenceUtil.setTrunkSensorEnable(parseBoolean24);
                }
                syncDataFlag.setSensorTrunkSw(true);
                return;
            case '0':
                if (isGuest()) {
                    this.mSyncDataValue.setRearSeatWelcomeMode(SharedPreferenceUtil.isRearSeatWelcomeModeEnabled());
                } else {
                    boolean parseBoolean25 = Boolean.parseBoolean(data.value);
                    this.mSyncDataValue.setRearSeatWelcomeMode(parseBoolean25);
                    SharedPreferenceUtil.setRearSeatWelcomeMode(parseBoolean25);
                }
                syncDataFlag.setRearSeatWelcomeMode(true);
                return;
            case '1':
                if (isGuest()) {
                    this.mSyncDataValue.setXpedalSw(SharedPreferenceUtil.getXpedal());
                } else {
                    boolean parseBoolean26 = Boolean.parseBoolean(data.value);
                    this.mSyncDataValue.setXpedalSw(parseBoolean26);
                    SharedPreferenceUtil.setXpedal(parseBoolean26);
                }
                syncDataFlag.setXpedal(true);
                return;
            case '2':
                if (isGuest()) {
                    this.mSyncDataValue.setCmsObjectRecognizeSw(SharedPreferenceUtil.getCmsObjectRecognizeSw());
                } else {
                    try {
                        z5 = Boolean.parseBoolean(data.value);
                    } catch (Exception unused8) {
                        LogUtils.w(TAG, "Fill KEY_CMS_OBJECT_RECOGNIZE_SW data error, value:" + data.value, false);
                        z5 = true;
                    }
                    this.mSyncDataValue.setCmsObjectRecognizeSw(z5);
                    SharedPreferenceUtil.setCmsObjectRecognizeSw(z5);
                }
                syncDataFlag.setCmsObjectRecognizeSw(true);
                return;
            case '3':
                if (isGuest()) {
                    this.mSyncDataValue.setSpeedLimitValue(SharedPreferenceUtil.getSpeedLimitValue());
                } else {
                    int parseInt14 = Integer.parseInt(data.value);
                    this.mSyncDataValue.setSpeedLimitValue(parseInt14);
                    SharedPreferenceUtil.setSpeedLimitValue(parseInt14);
                }
                syncDataFlag.setSpeedLimitValue(true);
                return;
            case '4':
                if (isGuest()) {
                    this.mSyncDataValue.setLightMeHomeCfg(SharedPreferenceUtil.getLightMeHomeTime());
                } else {
                    int parseInt15 = Integer.parseInt(data.value);
                    this.mSyncDataValue.setLightMeHomeCfg(parseInt15);
                    SharedPreferenceUtil.setLightMeHomeTime(parseInt15);
                }
                syncDataFlag.setLightMeHomeTime(true);
                return;
            case '5':
                if (isGuest()) {
                    this.mSyncDataValue.setAsWelcomeMode(SharedPreferenceUtil.isAsWelcomeModeEnabled());
                } else {
                    boolean parseBoolean27 = Boolean.parseBoolean(data.value);
                    this.mSyncDataValue.setAsWelcomeMode(parseBoolean27);
                    SharedPreferenceUtil.setAsWelcomeMode(parseBoolean27);
                }
                syncDataFlag.setAsWelcomeMode(true);
                return;
            case '6':
                if (isGuest()) {
                    this.mSyncDataValue.setTrunkFullOpenPos(SharedPreferenceUtil.getTrunkFullOpenPos());
                } else {
                    int parseInt16 = Integer.parseInt(data.value);
                    this.mSyncDataValue.setTrunkFullOpenPos(parseInt16);
                    SharedPreferenceUtil.setTrunkFullOpenPos(parseInt16);
                }
                syncDataFlag.setTrunkFullOpenPos(true);
                return;
            case '7':
                if (isGuest()) {
                    this.mSyncDataValue.setAutoLampHeight(SharedPreferenceUtil.isAutoLampHeight());
                } else {
                    boolean parseBoolean28 = Boolean.parseBoolean(data.value);
                    this.mSyncDataValue.setAutoLampHeight(parseBoolean28);
                    SharedPreferenceUtil.setAutoLampHeight(parseBoolean28);
                }
                syncDataFlag.setAutoLampHeight(true);
                return;
            case '8':
                if (isGuest()) {
                    this.mSyncDataValue.setSinglePedal(SharedPreferenceUtil.isNewDriveXPedalModeEnabled());
                } else {
                    boolean parseBoolean29 = Boolean.parseBoolean(data.value);
                    this.mSyncDataValue.setSinglePedal(parseBoolean29);
                    SharedPreferenceUtil.setNewDriveXPedalMode(parseBoolean29);
                }
                syncDataFlag.setSinglePedal(true);
                return;
            case '9':
                if (!CarBaseConfig.getInstance().isNewScuArch()) {
                    LogUtils.i(TAG, "Sync alc, Dx not use account sync data, use local sp data.");
                    return;
                }
                if (isGuest()) {
                    this.mSyncDataValue.setAlcSw(SharedPreferenceUtil.getAlcSw());
                } else {
                    boolean parseBoolean30 = Boolean.parseBoolean(data.value);
                    this.mSyncDataValue.setAlcSw(parseBoolean30);
                    SharedPreferenceUtil.setAlcSw(parseBoolean30);
                }
                syncDataFlag.setAlc(true);
                return;
            case ':':
                if (!CarBaseConfig.getInstance().isNewScuArch()) {
                    LogUtils.i(TAG, "Sync lcc, Dx not use account sync data, use local sp data.");
                    return;
                }
                if (isGuest()) {
                    this.mSyncDataValue.setLccSw(SharedPreferenceUtil.getLccSw());
                } else {
                    boolean parseBoolean31 = Boolean.parseBoolean(data.value);
                    this.mSyncDataValue.setLccSw(parseBoolean31);
                    SharedPreferenceUtil.setLccSw(parseBoolean31);
                }
                syncDataFlag.setLcc(true);
                return;
            case ';':
                if (isGuest()) {
                    this.mSyncDataValue.setNraState(SharedPreferenceUtil.getNraState());
                } else {
                    int parseInt17 = Integer.parseInt(data.value);
                    this.mSyncDataValue.setNraState(parseInt17);
                    SharedPreferenceUtil.setNraState(parseInt17);
                }
                syncDataFlag.setNra(true);
                return;
            case '<':
                if (isGuest()) {
                    this.mSyncDataValue.setLluChargeSw(SharedPreferenceUtil.isLluChargeSwEnabled());
                } else {
                    boolean parseBoolean32 = Boolean.parseBoolean(data.value);
                    this.mSyncDataValue.setLluChargeSw(parseBoolean32);
                    SharedPreferenceUtil.setLluChargeSw(parseBoolean32);
                }
                syncDataFlag.setLluChargeSw(true);
                return;
            case '=':
                if (isGuest()) {
                    this.mSyncDataValue.setDrvSeatEsb(SharedPreferenceUtil.isEsbEnabled());
                } else {
                    boolean parseBoolean33 = Boolean.parseBoolean(data.value);
                    this.mSyncDataValue.setDrvSeatEsb(parseBoolean33);
                    SharedPreferenceUtil.setEsbEnable(parseBoolean33);
                }
                syncDataFlag.setDrvSeatEsb(true);
                return;
            case '>':
                if (isGuest()) {
                    this.mSyncDataValue.setLssState(SharedPreferenceUtil.getLssState());
                } else {
                    try {
                        i4 = Integer.parseInt(data.value);
                    } catch (Exception unused9) {
                        LogUtils.w(TAG, "Fill lss sync data error. value:" + data.value, false);
                    }
                    this.mSyncDataValue.setLssState(i4);
                    SharedPreferenceUtil.setLssState(i4);
                }
                syncDataFlag.setLss(true);
                return;
            case '?':
                if (isGuest()) {
                    this.mSyncDataValue.setCmsAutoBrightSw(SharedPreferenceUtil.getCmsAutoBrightSw());
                } else {
                    try {
                        z7 = Boolean.parseBoolean(data.value);
                    } catch (Exception unused10) {
                        LogUtils.w(TAG, "Fill KEY_CMS_AUTO_BRIGHTNESS_SW data error, value:" + data.value, false);
                    }
                    this.mSyncDataValue.setCmsAutoBrightSw(z7);
                    SharedPreferenceUtil.setCmsAutoBrightSw(z7);
                }
                syncDataFlag.setCmsAutoBrightSw(true);
                return;
            case '@':
                if (isGuest()) {
                    this.mSyncDataValue.setMeterDefineMediaSource(SharedPreferenceUtil.getMeterMenuMediaSource());
                } else {
                    boolean parseBoolean34 = Boolean.parseBoolean(data.value);
                    this.mSyncDataValue.setMeterDefineMediaSource(parseBoolean34);
                    SharedPreferenceUtil.setMeterMenuMediaSource(parseBoolean34);
                }
                syncDataFlag.setMeterDefineMediaSource(true);
                return;
            case 'A':
                if (isGuest()) {
                    this.mSyncDataValue.setDrvSeatPos1(SharedPreferenceUtil.getDrvSeatData("CHAIR_POSITION"));
                    return;
                }
                this.mSyncDataValue.setDrvSeatPos1(data.value);
                SharedPreferenceUtil.saveDrvSeatData("CHAIR_POSITION", data.value);
                return;
            case 'B':
                if (isGuest()) {
                    this.mSyncDataValue.setDriveMode(SharedPreferenceUtil.getDriveMode());
                } else {
                    int parseInt18 = Integer.parseInt(data.value);
                    this.mSyncDataValue.setDriveMode(parseInt18);
                    SharedPreferenceUtil.setDriveMode(parseInt18);
                }
                syncDataFlag.setDriveMode(true);
                return;
            case 'C':
                if (isGuest()) {
                    this.mSyncDataValue.setRightDoorHotKey(SharedPreferenceUtil.getRightDoorHotKeySw());
                } else {
                    boolean parseBoolean35 = Boolean.parseBoolean(data.value);
                    this.mSyncDataValue.setRightDoorHotKey(parseBoolean35);
                    SharedPreferenceUtil.setRightDoorHotKeySw(parseBoolean35);
                }
                syncDataFlag.setRightDoorHotKey(true);
                return;
            case 'D':
                if (isGuest()) {
                    this.mSyncDataValue.setDoorBossKeySw(SharedPreferenceUtil.getDoorBossSw());
                } else {
                    boolean parseBoolean36 = Boolean.parseBoolean(data.value);
                    this.mSyncDataValue.setDoorBossKeySw(parseBoolean36);
                    SharedPreferenceUtil.setDoorBossSw(parseBoolean36);
                }
                syncDataFlag.setDoorBossKeySw(true);
                return;
            case 'E':
                if (isGuest()) {
                    this.mSyncDataValue.setWheelKeyProtect(SharedPreferenceUtil.getWheelKeyProtectSw());
                } else {
                    boolean parseBoolean37 = Boolean.parseBoolean(data.value);
                    this.mSyncDataValue.setWheelKeyProtect(parseBoolean37);
                    SharedPreferenceUtil.setWheelKeyProtectSw(parseBoolean37);
                }
                syncDataFlag.setWheelKeyProtect(true);
                return;
            case 'F':
                if (isGuest()) {
                    this.mSyncDataValue.setBrakeWarning(SharedPreferenceUtil.getEbwSw());
                } else {
                    boolean parseBoolean38 = Boolean.parseBoolean(data.value);
                    this.mSyncDataValue.setBrakeWarning(parseBoolean38);
                    SharedPreferenceUtil.setEbwSw(parseBoolean38);
                }
                syncDataFlag.setBrakeWarning(true);
                return;
            case 'G':
                if (isGuest()) {
                    this.mSyncDataValue.setSpeedLimit(SharedPreferenceUtil.getSpeedLimit());
                } else {
                    boolean parseBoolean39 = Boolean.parseBoolean(data.value);
                    this.mSyncDataValue.setSpeedLimit(parseBoolean39);
                    SharedPreferenceUtil.setSpeedLimit(parseBoolean39);
                }
                syncDataFlag.setSpeedLimit(true);
                return;
            case 'H':
                if (!CarBaseConfig.getInstance().isNewScuArch()) {
                    LogUtils.i(TAG, "Sync auto park, Dx not use account sync data, use local sp data.");
                    return;
                }
                if (isGuest()) {
                    this.mSyncDataValue.setAutoParkSw(SharedPreferenceUtil.getAutoParkSw());
                } else {
                    boolean parseBoolean40 = Boolean.parseBoolean(data.value);
                    this.mSyncDataValue.setAutoParkSw(parseBoolean40);
                    SharedPreferenceUtil.setAutoParkSw(parseBoolean40);
                }
                syncDataFlag.setAutoParkSw(true);
                return;
            case 'I':
                if (isGuest()) {
                    this.mSyncDataValue.setAtlDualColorSw(SharedPreferenceUtil.isAtlDualColorSwEnabled());
                } else {
                    boolean parseBoolean41 = Boolean.parseBoolean(data.value);
                    this.mSyncDataValue.setAtlDualColorSw(parseBoolean41);
                    SharedPreferenceUtil.setAtlDualColorSw(parseBoolean41);
                }
                syncDataFlag.setAtlDualColorSw(true);
                return;
            case 'J':
                if (isGuest()) {
                    this.mSyncDataValue.setLdwSw(SharedPreferenceUtil.getLdwSw());
                } else {
                    boolean parseBoolean42 = Boolean.parseBoolean(data.value);
                    this.mSyncDataValue.setLdwSw(parseBoolean42);
                    SharedPreferenceUtil.setLdwSw(parseBoolean42);
                }
                syncDataFlag.setLdw(true);
                return;
            case 'K':
                if (isGuest()) {
                    this.mSyncDataValue.setAvasEffect(SharedPreferenceUtil.getAvasLowSpdEffect());
                } else {
                    int parseInt19 = Integer.parseInt(data.value);
                    this.mSyncDataValue.setAvasEffect(parseInt19);
                    SharedPreferenceUtil.setAvasLowSpdEffect(parseInt19);
                }
                syncDataFlag.setAvasEffect(true);
                return;
            case 'L':
                if (isGuest()) {
                    this.mSyncDataValue.setLluUnlockSw(SharedPreferenceUtil.isLluUnlockSwEnabled());
                } else {
                    boolean parseBoolean43 = Boolean.parseBoolean(data.value);
                    this.mSyncDataValue.setLluUnlockSw(parseBoolean43);
                    SharedPreferenceUtil.setLluUnlockSw(parseBoolean43);
                }
                syncDataFlag.setLluUnlockSw(true);
                return;
            case 'M':
                if (isGuest()) {
                    this.mSyncDataValue.setDrvSeatPosIdx(SharedPreferenceUtil.getDrvSeatMemoryIndex());
                    return;
                }
                int parseInt20 = Integer.parseInt(data.value);
                this.mSyncDataValue.setDrvSeatPosIdx(parseInt20);
                SharedPreferenceUtil.setDrvSeatMemoryIndex(parseInt20);
                return;
            case 'N':
                if (isGuest()) {
                    this.mSyncDataValue.setBsdSw(SharedPreferenceUtil.getBsdSw());
                } else {
                    boolean parseBoolean44 = Boolean.parseBoolean(data.value);
                    this.mSyncDataValue.setBsdSw(parseBoolean44);
                    SharedPreferenceUtil.setBsdSw(parseBoolean44);
                }
                syncDataFlag.setBsd(true);
                return;
            case 'O':
                if (isGuest()) {
                    this.mSyncDataValue.setMemParkSw(SharedPreferenceUtil.getMemParkSw());
                } else {
                    boolean parseBoolean45 = Boolean.parseBoolean(data.value);
                    this.mSyncDataValue.setMemParkSw(parseBoolean45);
                    SharedPreferenceUtil.setMemParkSw(parseBoolean45);
                }
                syncDataFlag.setMemParkSw(true);
                return;
            case 'P':
                if (isGuest()) {
                    this.mSyncDataValue.setCmsPos(SharedPreferenceUtil.getCmsPos());
                } else {
                    String str3 = data.value;
                    this.mSyncDataValue.setCmsPos(str3);
                    SharedPreferenceUtil.setCmsPos(str3);
                }
                syncDataFlag.setCmsPos(true);
                return;
            case 'Q':
                if (isGuest()) {
                    this.mSyncDataValue.setDrvSeatPos3(SharedPreferenceUtil.getDrvSeatData("CHAIR_POSITION_THREE"));
                    return;
                }
                this.mSyncDataValue.setDrvSeatPos3(data.value);
                SharedPreferenceUtil.saveDrvSeatData("CHAIR_POSITION_THREE", data.value);
                return;
            case 'R':
                if (isGuest()) {
                    this.mSyncDataValue.setCmsLowSpdSw(SharedPreferenceUtil.getCmsLowSpdSw());
                } else {
                    try {
                        z6 = Boolean.parseBoolean(data.value);
                    } catch (Exception unused11) {
                        LogUtils.w(TAG, "Fill KEY_CMS_LOW_SPD_SW data error, value:" + data.value, false);
                        z6 = true;
                    }
                    this.mSyncDataValue.setCmsLowSpdSw(z6);
                    SharedPreferenceUtil.setCmsLowSpdSw(z6);
                }
                syncDataFlag.setCmsLowSpdSw(true);
                return;
            case 'S':
                if (isGuest()) {
                    this.mSyncDataValue.setRecycleGrade(SharedPreferenceUtil.getEnergyRecycleGrade());
                } else {
                    int parseInt21 = Integer.parseInt(data.value);
                    this.mSyncDataValue.setRecycleGrade(parseInt21);
                    SharedPreferenceUtil.setEnergyRecycleGrade(parseInt21);
                }
                syncDataFlag.setRecycleGrade(true);
                return;
            case 'T':
                if (isGuest()) {
                    this.mSyncDataValue.setHighSpdCloseWin(SharedPreferenceUtil.getHighSpdCloseWinSw());
                } else {
                    boolean parseBoolean46 = Boolean.parseBoolean(data.value);
                    this.mSyncDataValue.setHighSpdCloseWin(parseBoolean46);
                    SharedPreferenceUtil.setHighSpdCloseWinSw(parseBoolean46);
                }
                syncDataFlag.setHighSpdCloseWin(true);
                return;
            case 'U':
                if (isGuest()) {
                    this.mSyncDataValue.setDrvSeatPosNew(SharedPreferenceUtil.getDrvSeatData("CHAIR_POSITION_NEW"));
                    return;
                }
                this.mSyncDataValue.setDrvSeatPosNew(data.value);
                SharedPreferenceUtil.saveDrvSeatData("CHAIR_POSITION_NEW", data.value);
                return;
            case 'V':
                if (isGuest()) {
                    this.mSyncDataValue.setDrvSeatPos2(SharedPreferenceUtil.getDrvSeatData("CHAIR_POSITION_TWO"));
                    return;
                }
                this.mSyncDataValue.setDrvSeatPos2(data.value);
                SharedPreferenceUtil.saveDrvSeatData("CHAIR_POSITION_TWO", data.value);
                return;
            case 'W':
                if (isGuest()) {
                    this.mSyncDataValue.setAtlSw(SharedPreferenceUtil.isAtlSwEnabled());
                } else {
                    boolean parseBoolean47 = Boolean.parseBoolean(data.value);
                    this.mSyncDataValue.setAtlSw(parseBoolean47);
                    SharedPreferenceUtil.setAtlSwEnable(parseBoolean47);
                }
                syncDataFlag.setAtlSw(true);
                return;
            case 'X':
                if (isGuest()) {
                    this.mSyncDataValue.setCwcSw(SharedPreferenceUtil.getCwcSw());
                } else {
                    boolean parseBoolean48 = Boolean.parseBoolean(data.value);
                    this.mSyncDataValue.setCwcSw(parseBoolean48);
                    SharedPreferenceUtil.setCwcSw(parseBoolean48);
                }
                syncDataFlag.setCwcSw(true);
                return;
            case 'Y':
                if (isGuest()) {
                    this.mSyncDataValue.setSayHiEffect(SharedPreferenceUtil.getAvasFriendEffect());
                } else {
                    int parseInt22 = Integer.parseInt(data.value);
                    this.mSyncDataValue.setSayHiEffect(parseInt22);
                    SharedPreferenceUtil.setAvasFriendEffect(parseInt22);
                }
                syncDataFlag.setSayHiEffect(true);
                return;
            case 'Z':
                if (isGuest()) {
                    this.mSyncDataValue.setMeterDefineScreenLight(SharedPreferenceUtil.getMeterMenuScreenLight());
                } else {
                    boolean parseBoolean49 = Boolean.parseBoolean(data.value);
                    this.mSyncDataValue.setMeterDefineScreenLight(parseBoolean49);
                    SharedPreferenceUtil.setMeterMenuScreenLight(parseBoolean49);
                }
                syncDataFlag.setMeterDefineScreenLight(true);
                return;
            case '[':
                if (isGuest()) {
                    this.mSyncDataValue.setAntiSickSw(SharedPreferenceUtil.isAntiSicknessEnabled());
                } else {
                    boolean parseBoolean50 = Boolean.parseBoolean(data.value);
                    this.mSyncDataValue.setAntiSickSw(parseBoolean50);
                    SharedPreferenceUtil.setAntiSickness(parseBoolean50);
                }
                syncDataFlag.setAntisick(true);
                return;
            case '\\':
                if (isGuest()) {
                    this.mSyncDataValue.setLockCloseWin(SharedPreferenceUtil.getAutoWindowLockSw());
                } else {
                    boolean parseBoolean51 = Boolean.parseBoolean(data.value);
                    this.mSyncDataValue.setLockCloseWin(parseBoolean51);
                    SharedPreferenceUtil.setAutoWindowLockSw(parseBoolean51);
                }
                syncDataFlag.setLockCloseWin(true);
                return;
            case ']':
                if (isGuest()) {
                    this.mSyncDataValue.setUnlockResponse(SharedPreferenceUtil.getUnlockResponse());
                } else {
                    int parseInt23 = Integer.parseInt(data.value);
                    this.mSyncDataValue.setUnlockResponse(parseInt23);
                    SharedPreferenceUtil.setUnlockResponse(parseInt23);
                }
                syncDataFlag.setUnlockResponse(true);
                return;
            default:
                return;
        }
    }

    public void setDriveMode(int mode) {
        LogUtils.i(TAG, "setDriveMode: " + mode);
        this.mSyncDataValue.setDriveMode(mode);
        if (!isGuest()) {
            save("DRIVE_MODE", String.valueOf(mode));
        }
        SharedPreferenceUtil.setDriveMode(mode);
    }

    public int getDriveMode() {
        if (isGuest()) {
            return SharedPreferenceUtil.getDriveMode();
        }
        return this.mSyncDataValue.getDriveMode();
    }

    public void setAwdSetting(int mode) {
        LogUtils.i(TAG, "setAwdSetting: " + mode);
        SharedPreferenceUtil.setAwdSetting(mode);
    }

    public int getAwdSetting() {
        return SharedPreferenceUtil.getAwdSetting();
    }

    public boolean getFirstEnterXpowerFlag() {
        return SharedPreferenceUtil.getFirstEnterXpowerFlag();
    }

    public void setFirstEnterXpowerFlag(boolean flag) {
        LogUtils.i(TAG, "setFirstEnterXpowerFlag: " + flag);
        SharedPreferenceUtil.setFirstEnterXpowerFlag(flag);
    }

    public void setXpedal(boolean enable) {
        LogUtils.i(TAG, "setXpedal: " + enable);
        this.mSyncDataValue.setXpedalSw(enable);
        if (!isGuest()) {
            saveToSync("XPEDAL_MODE", String.valueOf(enable));
        }
        SharedPreferenceUtil.setXpedal(enable);
    }

    public boolean isXpedal() {
        if (isGuest()) {
            return SharedPreferenceUtil.getXpedal();
        }
        return this.mSyncDataValue.getXpedalSw();
    }

    public void setAntiSickness(boolean enable) {
        LogUtils.i(TAG, "setAntiSickness: " + enable);
        this.mSyncDataValue.setAntiSickSw(enable);
        if (!isGuest()) {
            saveToSync("ANTISICK_MODE", String.valueOf(enable));
        }
        SharedPreferenceUtil.setAntiSickness(enable);
    }

    public boolean isAntiSicknessEnabled() {
        if (isGuest()) {
            return SharedPreferenceUtil.isAntiSicknessEnabled();
        }
        return this.mSyncDataValue.getAntiSickSw();
    }

    public void setRecycleGrade(int grade) {
        LogUtils.i(TAG, "setRecycleGrade: " + grade);
        this.mSyncDataValue.setRecycleGrade(grade);
        if (!isGuest()) {
            save("KEY_RECYCLE_GRADE", String.valueOf(grade));
        }
        SharedPreferenceUtil.setEnergyRecycleGrade(grade);
    }

    public int getRecycleGrade() {
        if (isGuest()) {
            return SharedPreferenceUtil.getEnergyRecycleGrade();
        }
        return this.mSyncDataValue.getRecycleGrade();
    }

    public void setSteerMode(int mode) {
        LogUtils.i(TAG, "setSteerMode: " + mode);
        this.mSyncDataValue.setSteerMode(mode);
        if (!isGuest()) {
            save("STEER_MODE", String.valueOf(mode));
        }
        SharedPreferenceUtil.setSteeringEps(mode);
    }

    public int getSteerMode() {
        if (isGuest()) {
            return SharedPreferenceUtil.getSteeringEps();
        }
        return this.mSyncDataValue.getSteerMode();
    }

    public void setCdcMode(int mode) {
        LogUtils.i(TAG, "setKeyCdcModeMode: " + mode);
        this.mSyncDataValue.setCdcMode(mode);
        if (!isGuest()) {
            save("CDC_MODE", String.valueOf(mode));
        }
        SharedPreferenceUtil.setCdcMode(mode);
    }

    public int getCdcMode() {
        if (isGuest()) {
            return SharedPreferenceUtil.getCdcMode();
        }
        return this.mSyncDataValue.getCdcMode();
    }

    public void setAvh(boolean enable) {
        LogUtils.i(TAG, "setAvh: " + enable);
        this.mSyncDataValue.setAvh(enable);
        if (!isGuest()) {
            save("CHASSIS_AVH", String.valueOf(enable));
        }
        SharedPreferenceUtil.setAvhEnabled(enable);
    }

    public boolean getAvh() {
        if (isGuest()) {
            return SharedPreferenceUtil.isAvhEnabled();
        }
        return this.mSyncDataValue.isAvh();
    }

    public void setEsp(boolean enable) {
        LogUtils.i(TAG, "setEsp: " + enable);
        SharedPreferenceUtil.setEspSw(enable);
    }

    public boolean getEsp() {
        return SharedPreferenceUtil.getEspSw();
    }

    public void setSpecialDriveMode(int mode) {
        LogUtils.i(TAG, "setSpecialDriveMode: " + mode);
        SharedPreferenceUtil.setSpecialDriveMode(mode);
    }

    public int getSpecialDriveMode() {
        return SharedPreferenceUtil.getSpecialDriveMode();
    }

    public void setNormalDriveModeEnable(boolean enable) {
        LogUtils.i(TAG, "setNormalDriveModeEnable: " + enable);
        SharedPreferenceUtil.setNormalDriveModeEnable(enable);
    }

    public boolean getNormalDriveModeEnable() {
        return SharedPreferenceUtil.isNormalDriveModeEnable();
    }

    public boolean getWheelTouchHint() {
        return this.mSyncDataValue.isWheelTouchHint();
    }

    public void setWheelXKey(int key) {
        LogUtils.i(TAG, "setWheelXKey: " + key);
        this.mSyncDataValue.setWheelXKey(key);
        if (!isGuest()) {
            save("WHEEL_X_KEY", String.valueOf(key));
        }
        SharedPreferenceUtil.setWheelXKey(key);
    }

    public int getWheelXKey() {
        if (isGuest()) {
            return SharedPreferenceUtil.getWheelXKey();
        }
        return this.mSyncDataValue.getWheelXKey();
    }

    public void setDoorBossKey(int key) {
        LogUtils.i(TAG, "setDoorBossKey: " + key);
        this.mSyncDataValue.setDoorBossKey(key);
        if (!isGuest()) {
            save("DOOR_BOSS_KEY", String.valueOf(key));
        }
        SharedPreferenceUtil.setDoorKey(key);
    }

    public int getDoorBossKey() {
        if (isGuest()) {
            return SharedPreferenceUtil.getDoorKey();
        }
        return this.mSyncDataValue.getDoorBossKey();
    }

    public void setDoorBossKeySw(boolean onOff) {
        LogUtils.i(TAG, "setDoorBossKeySw: " + onOff);
        this.mSyncDataValue.setDoorBossKeySw(onOff);
        if (!isGuest()) {
            save("DOOR_BOSS_KEY_SW", String.valueOf(onOff));
        }
        SharedPreferenceUtil.setDoorBossSw(onOff);
    }

    public boolean getDoorBossKeySw() {
        boolean doorBossSw = isGuest() ? SharedPreferenceUtil.getDoorBossSw() : this.mSyncDataValue.getDoorBossKeySw();
        LogUtils.i(TAG, "getDoorBossKeySw enable: " + doorBossSw);
        return doorBossSw;
    }

    public void setWheelKeyProtect(boolean enable) {
        LogUtils.i(TAG, "setWheelKeyProtect: " + enable);
        this.mSyncDataValue.setWheelKeyProtect(enable);
        if (!isGuest()) {
            save("WHEEL_KEY_PROTECT", String.valueOf(enable));
        }
        SharedPreferenceUtil.setWheelKeyProtectSw(enable);
    }

    public boolean getWheelKeyProtect() {
        if (isGuest()) {
            return SharedPreferenceUtil.getWheelKeyProtectSw();
        }
        return this.mSyncDataValue.getWheelKeyProtect();
    }

    public boolean isHeadLampStateExisted() {
        return SharedPreferenceUtil.hasConfig(GlobalConstant.PREFS.PREF_HEAD_LAMP_INT);
    }

    public void setHeadLampState(int groupId) {
        LogUtils.d(TAG, "setHeadLampState: " + groupId, false);
        SharedPreferenceUtil.setHeadLampState(groupId);
    }

    public int getHeadLampState() {
        int headLampState = SharedPreferenceUtil.getHeadLampState();
        LogUtils.d(TAG, "getHeadLampState: " + headLampState, false);
        return headLampState;
    }

    public int getLampHeightLevel() {
        int lampHeightLevel;
        if (isGuest()) {
            lampHeightLevel = SharedPreferenceUtil.getLampHeightLevel();
        } else {
            lampHeightLevel = this.mSyncDataValue.getLampHeightLevel();
        }
        LogUtils.d(TAG, "getLampHeightLevel: " + lampHeightLevel, false);
        return lampHeightLevel;
    }

    public void setLampHeightLevel(int level) {
        LogUtils.i(TAG, "setLampHeightLevel: " + level, false);
        this.mSyncDataValue.setLampHeightLevel(level);
        if (!isGuest()) {
            save("LAMP_HEIGHT_LEVEL", String.valueOf(level));
        }
        SharedPreferenceUtil.setLampHeightLevel(level);
    }

    public boolean isAutoLampHeight() {
        boolean isAutoLampHeight;
        if (isGuest()) {
            isAutoLampHeight = SharedPreferenceUtil.isAutoLampHeight();
        } else {
            isAutoLampHeight = this.mSyncDataValue.isAutoLampHeight();
        }
        LogUtils.d(TAG, "isAutoLampHeight: " + isAutoLampHeight, false);
        return isAutoLampHeight;
    }

    public void setAutoLampHeight(boolean auto) {
        LogUtils.i(TAG, "setAutoLampHeight: " + auto, false);
        this.mSyncDataValue.setAutoLampHeight(auto);
        if (!isGuest()) {
            save("AUTO_LAMP_HEIGHT", String.valueOf(auto));
        }
        SharedPreferenceUtil.setAutoLampHeight(auto);
    }

    public void setParkLampB(boolean active) {
        LogUtils.i(TAG, "setParkLampB: " + active, false);
        this.mSyncDataValue.setParkLampB(active);
        if (!isGuest()) {
            save("PARK_LAMP_B", String.valueOf(active));
        }
        SharedPreferenceUtil.setParkLampB(active);
    }

    public boolean getParkLampB() {
        boolean parkLampB = SharedPreferenceUtil.getParkLampB();
        if (!isGuest()) {
            parkLampB = this.mSyncDataValue.getParkLampB();
        }
        LogUtils.d(TAG, "getParkLampB: " + parkLampB, false);
        return parkLampB;
    }

    public void setLightMeHome(boolean enable) {
        LogUtils.i(TAG, "setLightMeHome: " + enable);
        this.mSyncDataValue.setLightMeHomeSw(enable);
        if (!isGuest()) {
            save("LIGHT_ME_HOME", String.valueOf(enable));
        }
        SharedPreferenceUtil.setLightMeHome(enable);
    }

    public boolean getLightMeHome() {
        if (isGuest()) {
            return SharedPreferenceUtil.isLightMeHomeEnabled();
        }
        return this.mSyncDataValue.isLightMeHomeSw();
    }

    public void setLightMeHomeTime(int time) {
        LogUtils.i(TAG, "setLightMeHomeTime: " + time);
        this.mSyncDataValue.setLightMeHomeCfg(time);
        if (!isGuest()) {
            save("LIGHT_ME_HOME_TIME", String.valueOf(time));
        }
        SharedPreferenceUtil.setLightMeHomeTime(time);
    }

    public int getLightMeHomeTime() {
        if (isGuest()) {
            return SharedPreferenceUtil.getLightMeHomeTime();
        }
        return this.mSyncDataValue.getLightMeHomeCfg();
    }

    public boolean getAutoLightSw() {
        return this.mSyncDataValue.isAutoLightSw();
    }

    public int getDomeLight() {
        return this.mSyncDataValue.getDomeLightCfg();
    }

    public void setDomeLightBright(int bright) {
        LogUtils.i(TAG, "setDomeLightBright: " + bright);
        this.mSyncDataValue.setDomeLightBright(bright);
        if (!isGuest()) {
            save("DOME_LIGHT_BRIGHT", String.valueOf(bright));
        }
        SharedPreferenceUtil.setDomeLightBright(bright);
    }

    public int getDomeLightBright() {
        if (isGuest()) {
            return SharedPreferenceUtil.getDomeLightBright();
        }
        return this.mSyncDataValue.getDomeLightBright();
    }

    public void setLluSw(boolean enable) {
        LogUtils.i(TAG, "setLluSw: " + enable);
        this.mSyncDataValue.setLluSw(enable);
        if (!isGuest()) {
            save("LLU_SW", String.valueOf(enable));
        }
        SharedPreferenceUtil.setLluSw(enable);
    }

    public boolean getLluSw() {
        if (isGuest()) {
            return SharedPreferenceUtil.isLluSwEnabled();
        }
        return this.mSyncDataValue.isLluSw();
    }

    public void setLluUnlockSw(boolean enable) {
        LogUtils.i(TAG, "setLluUnlockSw: " + enable);
        this.mSyncDataValue.setLluUnlockSw(enable);
        if (!isGuest()) {
            save("LLU_UNLOCK_SW", String.valueOf(enable));
        }
        SharedPreferenceUtil.setLluUnlockSw(enable);
    }

    public boolean getLluUnlockSw() {
        if (isGuest()) {
            return SharedPreferenceUtil.isLluUnlockSwEnabled();
        }
        return this.mSyncDataValue.isLluUnlockSw();
    }

    public void setLluLockSw(boolean enable) {
        LogUtils.i(TAG, "setLluLockSw: " + enable);
        this.mSyncDataValue.setLluLockSw(enable);
        if (!isGuest()) {
            save("LLU_LOCK_SW", String.valueOf(enable));
        }
        SharedPreferenceUtil.setLluLockSw(enable);
    }

    public boolean getLluLockSw() {
        if (isGuest()) {
            return SharedPreferenceUtil.isLluLockSwEnabled();
        }
        return this.mSyncDataValue.isLluLockSw();
    }

    public void setLluChargeSw(boolean enable) {
        LogUtils.i(TAG, "setLluChargeSw: " + enable);
        this.mSyncDataValue.setLluChargeSw(enable);
        if (!isGuest()) {
            save("LLU_CHARGE_SW", String.valueOf(enable));
        }
        SharedPreferenceUtil.setLluChargeSw(enable);
    }

    public boolean getLluChargeSw() {
        if (isGuest()) {
            return SharedPreferenceUtil.isLluChargeSwEnabled();
        }
        return this.mSyncDataValue.isLluChargeSw();
    }

    public void setAtlSw(boolean enable) {
        LogUtils.i(TAG, "setAtlSw: " + enable);
        this.mSyncDataValue.setAtlSw(enable);
        if (!isGuest()) {
            save("ATL_SW", String.valueOf(enable));
        }
        SharedPreferenceUtil.setAtlSwEnable(enable);
    }

    public boolean getAtlSw() {
        if (isGuest()) {
            return SharedPreferenceUtil.isAtlSwEnabled();
        }
        return this.mSyncDataValue.isAtlSw();
    }

    public boolean getAtlAutoBrightSw() {
        return this.mSyncDataValue.isAtlAutoBrightSw();
    }

    public void setAtlBright(int brightness) {
        LogUtils.i(TAG, "setAtlBright: " + brightness);
        this.mSyncDataValue.setAtlBrightCfg(brightness);
        if (!isGuest()) {
            save("ATL_BRIGHT", String.valueOf(brightness));
        }
        SharedPreferenceUtil.setAtlManualBrightness(brightness);
    }

    public int getAtlBright() {
        if (isGuest()) {
            return SharedPreferenceUtil.getAtlManualBrightness();
        }
        return this.mSyncDataValue.getAtlBrightCfg();
    }

    public void setAtlEffect(String effect) {
        LogUtils.i(TAG, "setAtlEffect: " + effect);
        this.mSyncDataValue.setAtlEffectCfg(effect);
        if (!isGuest()) {
            save("ATL_EFFECT", effect);
        }
        SharedPreferenceUtil.setAtlEffectMode(effect);
    }

    public String getAtlEffect() {
        if (isGuest()) {
            return SharedPreferenceUtil.getAtlEffectMode();
        }
        return this.mSyncDataValue.getAtlEffectCfg();
    }

    public void setAtlDualColorSw(boolean enable) {
        LogUtils.i(TAG, "setAtlDualColorSw: " + enable);
        this.mSyncDataValue.setAtlDualColorSw(enable);
        if (!isGuest()) {
            save("ATL_DUAL_COLOR_SW", String.valueOf(enable));
        }
        SharedPreferenceUtil.setAtlDualColorSw(enable);
    }

    public boolean getAtlDualColorSw() {
        if (isGuest()) {
            return SharedPreferenceUtil.isAtlDualColorSwEnabled();
        }
        return this.mSyncDataValue.isAtlDualColorSw();
    }

    public void setAtlSingleColor(int colorId) {
        LogUtils.i(TAG, "setAtlSingleColor: " + colorId);
        this.mSyncDataValue.setAtlSingleColor(colorId);
        if (!isGuest()) {
            save("ATL_SINGLE_COLOR", String.valueOf(colorId));
        }
        SharedPreferenceUtil.setAtlSingleColor(colorId);
    }

    public int getAtlSingleColor() {
        if (isGuest()) {
            return SharedPreferenceUtil.getAtlSingleColor();
        }
        return this.mSyncDataValue.getAtlSingleColor();
    }

    public void setAtlDualColor(int color1, int color2) {
        String json = new Gson().toJson(new int[]{color1, color2});
        LogUtils.i(TAG, "setAtlDualColor: " + json);
        this.mSyncDataValue.setAtlDualColor(json);
        if (!isGuest()) {
            save("ATL_DUAL_COLOR", json);
        }
        SharedPreferenceUtil.setAtlDualColor(json);
    }

    public int[] getAtlDualColor() {
        String atlDualColorStr = getAtlDualColorStr();
        if (TextUtils.isEmpty(atlDualColorStr)) {
            return null;
        }
        return (int[]) new Gson().fromJson(atlDualColorStr, (Class<Object>) int[].class);
    }

    String getAtlDualColorStr() {
        String atlDualColor;
        if (isGuest()) {
            atlDualColor = SharedPreferenceUtil.getAtlDualColor();
        } else {
            atlDualColor = this.mSyncDataValue.getAtlDualColor();
        }
        LogUtils.i(TAG, "getAtlDualColorStr: " + atlDualColor);
        return atlDualColor == null ? "" : atlDualColor;
    }

    public void setParkAutoUnlock(boolean enable) {
        LogUtils.i(TAG, "setParkAutoUnlock: " + enable);
        this.mSyncDataValue.setParkAutoUnlock(enable);
        if (!isGuest()) {
            save("STOP_AUTO_UNLOCK", String.valueOf(enable));
        }
        SharedPreferenceUtil.setParkAutoUnlock(enable);
    }

    public boolean getParkAutoUnlock() {
        if (isGuest()) {
            return SharedPreferenceUtil.getParkAutoUnlock();
        }
        return this.mSyncDataValue.isParkAutoUnlock();
    }

    public void setPollingLock(boolean enable) {
        LogUtils.i(TAG, "setPollingLock: " + enable);
        this.mSyncDataValue.setPollingLock(enable);
        save(KEY_POLLING_LOCK, String.valueOf(enable));
    }

    public boolean getPollingLock() {
        return this.mSyncDataValue.isPollingLock();
    }

    public void setPollingUnlock(boolean enable) {
        LogUtils.i(TAG, "setPollingUnlock: " + enable);
        this.mSyncDataValue.setPollingUnlock(enable);
        save(KEY_POLLING_UNLOCK, String.valueOf(enable));
    }

    public boolean getPollingUnlock() {
        return this.mSyncDataValue.isPollingUnlock();
    }

    public void setUnlockResponse(int mode) {
        LogUtils.i(TAG, "setUnlockResponse: " + mode);
        this.mSyncDataValue.setUnlockResponse(mode);
        if (!isGuest()) {
            save("UNLOCK_RESPONSE", String.valueOf(mode));
        }
        SharedPreferenceUtil.setUnlockResponse(mode);
    }

    public int getUnlockResponse() {
        if (isGuest()) {
            return SharedPreferenceUtil.getUnlockResponse();
        }
        return this.mSyncDataValue.getUnlockResponse();
    }

    public void setAutoDhc(boolean enable) {
        LogUtils.i(TAG, "setAutoDhc: " + enable);
        this.mSyncDataValue.setAutoDhc(enable);
        if (!isGuest()) {
            save("AUTO_DHC", String.valueOf(enable));
        }
        SharedPreferenceUtil.setAutoDoorHandle(enable);
    }

    public boolean getAutoDhc() {
        if (isGuest()) {
            return SharedPreferenceUtil.getAutoDoorHandle();
        }
        return this.mSyncDataValue.isAutoDhc();
    }

    public void setChildLock(int state) {
        LogUtils.i(TAG, "setChildLock: " + state);
        this.mSyncDataValue.setChildLock(state);
        save(KEY_CHILD_LOCK, String.valueOf(state));
    }

    public int getChildLock() {
        return this.mSyncDataValue.getChildLock();
    }

    public void setChildLeftLock(boolean state) {
        LogUtils.i(TAG, "setChildLeftLock: " + state);
        this.mSyncDataValue.setChildLeftLock(state);
        if (!isGuest()) {
            save("CHILD_LEFT_LOCK", String.valueOf(state));
        }
        SharedPreferenceUtil.setLeftChildLockSw(state);
    }

    public boolean getChildLeftLock() {
        if (isGuest()) {
            return SharedPreferenceUtil.getLeftChildLockSw();
        }
        return this.mSyncDataValue.getChildLeftLock();
    }

    public void setChildRightLock(boolean state) {
        LogUtils.i(TAG, "setChildRightLock: " + state);
        this.mSyncDataValue.setChildRightLock(state);
        if (!isGuest()) {
            save("CHILD_RIGHT_LOCK", String.valueOf(state));
        }
        SharedPreferenceUtil.setRightChildLockSw(state);
    }

    public boolean getChildRightLock() {
        if (isGuest()) {
            return SharedPreferenceUtil.getRightChildLockSw();
        }
        return this.mSyncDataValue.getChildRightLock();
    }

    public boolean getLeftDoorHotKey() {
        if (isGuest()) {
            return SharedPreferenceUtil.getLeftDoorHotKeySw();
        }
        return this.mSyncDataValue.getLeftDoorHotKey();
    }

    public void setLeftDoorHotKey(boolean state) {
        LogUtils.i(TAG, "setLeftDoorHotKey: " + state);
        this.mSyncDataValue.setLeftDoorHotKey(state);
        if (!isGuest()) {
            save("LEFT_DOOR_HOT_KEY", String.valueOf(state));
        }
        SharedPreferenceUtil.setLeftDoorHotKeySw(state);
    }

    public boolean getRightDoorHotKey() {
        if (isGuest()) {
            return SharedPreferenceUtil.getRightDoorHotKeySw();
        }
        return this.mSyncDataValue.getRightDoorHotKey();
    }

    public void setRightDoorHotKey(boolean state) {
        LogUtils.i(TAG, "setRightDoorHotKey: " + state);
        this.mSyncDataValue.setRightDoorHotKey(state);
        if (!isGuest()) {
            save("RIGHT_DOOR_HOT_KEY", String.valueOf(state));
        }
        SharedPreferenceUtil.setRightDoorHotKeySw(state);
    }

    public void saveDrvSeatPos(int[] seatPos) {
        String json;
        if (seatPos == null) {
            return;
        }
        if (BaseFeatureOption.getInstance().isSupportTiltPosSavedReversed()) {
            int[] copyOf = Arrays.copyOf(seatPos, seatPos.length);
            copyOf[2] = 100 - seatPos[2];
            json = new Gson().toJson(copyOf);
        } else {
            json = new Gson().toJson(seatPos);
        }
        LogUtils.d(TAG, "saveDrvSeatPos:" + json, false);
        if (!isGuest()) {
            this.mCarControlSync.setChairPosNew(json);
        }
        this.mSyncDataValue.setDrvSeatPosNew(json);
        SharedPreferenceUtil.saveDrvSeatData("CHAIR_POSITION_NEW", json);
    }

    public int[] getDrvSeatSavedPos(int index) {
        String drvSeatSavedPosStr = getDrvSeatSavedPosStr(index);
        if (TextUtils.isEmpty(drvSeatSavedPosStr)) {
            return null;
        }
        int[] iArr = (int[]) new Gson().fromJson(drvSeatSavedPosStr, (Class<Object>) int[].class);
        if (BaseFeatureOption.getInstance().isSupportTiltPosSavedReversed()) {
            iArr[2] = 100 - iArr[2];
            LogUtils.i(TAG, "isSupportTiltPosSavedReversed, position[2]: " + iArr[2], false);
        }
        if (iArr == null || iArr.length >= 5) {
            return iArr;
        }
        int[] copyOf = Arrays.copyOf(iArr, 5);
        if (iArr.length < 4) {
            copyOf[3] = CarBaseConfig.getInstance().getMSMSeatDefaultPos()[3];
        }
        copyOf[4] = CarBaseConfig.getInstance().getMSMSeatDefaultPos()[4];
        saveDrvSeatPos(copyOf);
        return copyOf;
    }

    protected String getDrvSeatSavedPosStr(int index) {
        String drvSeatPosNew;
        if (isGuest()) {
            drvSeatPosNew = SharedPreferenceUtil.getDrvSeatData("CHAIR_POSITION_NEW");
        } else {
            drvSeatPosNew = this.mSyncDataValue.getDrvSeatPosNew();
        }
        LogUtils.i(TAG, "getDrvSeatPosNew:" + drvSeatPosNew, false);
        return drvSeatPosNew == null ? "" : drvSeatPosNew;
    }

    public int[] getDrvSeatSavedPos() {
        return getDrvSeatSavedPos(getDrvSeatPosIdx());
    }

    public boolean isDrvSeatSavedPosEmpty() {
        return isDrvSeatSavedPosEmpty(getDrvSeatPosIdx());
    }

    public boolean isDrvSeatSavedPosEmpty(int index) {
        if (TextUtils.isEmpty(this.mSyncDataValue.getDrvSeatPosNew())) {
            LogUtils.i(TAG, "DrvSeat saved pos is null");
            return true;
        }
        return false;
    }

    public void setDrvSeatPosIdx(int idx) {
        LogUtils.i(TAG, "setDrvSeatPosIdx: " + idx, false);
        this.mSyncDataValue.setDrvSeatPosIdx(idx);
        if (!isGuest()) {
            save("CHAIR_POS_INDEX", String.valueOf(idx));
        }
        SharedPreferenceUtil.setDrvSeatMemoryIndex(idx);
    }

    public int getDrvSeatPosIdx() {
        if (isGuest()) {
            return SharedPreferenceUtil.getDrvSeatMemoryIndex();
        }
        return this.mSyncDataValue.getDrvSeatPosIdx();
    }

    public int getPsnSeatPosIdx(int selectId) {
        return SharedPreferenceUtil.getPsnSeatMemoryIndex(selectId);
    }

    public void setPsnSeatPosIdx(int idx, int selectId) {
        LogUtils.i(TAG, "setPsnSeatPosIdx: " + idx, false);
        SharedPreferenceUtil.setPsnSeatMemoryIndex(idx, selectId);
    }

    public void saveCurrentSelectPsnHabit(int selectId) {
        SharedPreferenceUtil.saveCurrentSelectPsnHabit(selectId);
    }

    public void saveCurrentSelectRLHabit(int selectId) {
        SharedPreferenceUtil.saveCurrentSelectRLHabit(selectId);
    }

    public void saveCurrentSelectRRHabit(int selectId) {
        SharedPreferenceUtil.saveCurrentSelectRRHabit(selectId);
    }

    public int getCurrentSelectedPsnHabit() {
        return SharedPreferenceUtil.getCurrentSelectedPsnHabit();
    }

    public int getCurrentSelectedRLHabit() {
        return SharedPreferenceUtil.getCurrentSelectedRLHabit();
    }

    public int getCurrentSelectedRRHabit() {
        return SharedPreferenceUtil.getCurrentSelectedRRHabit();
    }

    public void savePsnSeatPos(int[] pos) {
        try {
            String json = new Gson().toJson(pos);
            LogUtils.i(TAG, "savePsnSeatPos: " + json);
            SharedPreferenceUtil.savePsnSeatData("CHAIR_POSITION_PSN", json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void savePsnSelectSeatPos(int[] pos, int selectId) {
        try {
            String json = new Gson().toJson(pos);
            LogUtils.i(TAG, "savePsnSeatPos: " + json);
            SharedPreferenceUtil.savePsnSelectSeatData(selectId, json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveRLSelectSeatPos(int[] pos, int selectId) {
        try {
            String json = new Gson().toJson(pos);
            LogUtils.i(TAG, "saveRLSelectSeatPos: " + json);
            SharedPreferenceUtil.saveRLSelectSeatData(selectId, json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveRRSelectSeatPos(int[] pos, int selectId) {
        try {
            String json = new Gson().toJson(pos);
            LogUtils.i(TAG, "saveRRSelectSeatPos: " + json);
            SharedPreferenceUtil.saveRRSelectSeatData(selectId, json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void savePsnSeatName(String posName, int selectId) {
        try {
            LogUtils.i(TAG, "savePsnSeatName: " + posName);
            SharedPreferenceUtil.savePsnSeatName(selectId, posName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveRLSeatName(String posName, int selectId) {
        try {
            LogUtils.i(TAG, "saveRLSeatName: " + posName);
            SharedPreferenceUtil.saveRLSeatName(selectId, posName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveRRSeatName(String posName, int selectId) {
        try {
            LogUtils.i(TAG, "saveRRSeatName: " + posName);
            SharedPreferenceUtil.saveRRSeatName(selectId, posName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int[] getPsnSeatPos() {
        String psnSeatSavedPosStr = getPsnSeatSavedPosStr();
        LogUtils.i(TAG, "getPsnSeatPos:" + psnSeatSavedPosStr);
        if (TextUtils.isEmpty(psnSeatSavedPosStr)) {
            return null;
        }
        return (int[]) new Gson().fromJson(psnSeatSavedPosStr, (Class<Object>) int[].class);
    }

    public int[] getRLSeatPos() {
        String rLSeatSavedPosStr = getRLSeatSavedPosStr();
        LogUtils.i(TAG, "getRLSeatPos:" + rLSeatSavedPosStr);
        if (!TextUtils.isEmpty(rLSeatSavedPosStr)) {
            return (int[]) new Gson().fromJson(rLSeatSavedPosStr, (Class<Object>) int[].class);
        }
        return CarBaseConfig.getInstance().getSecRowMSMSeatDefaultPos();
    }

    public int[] getRRSeatPos() {
        String rRSeatSavedPosStr = getRRSeatSavedPosStr();
        LogUtils.i(TAG, "getRRSeatPos:" + rRSeatSavedPosStr);
        if (!TextUtils.isEmpty(rRSeatSavedPosStr)) {
            return (int[]) new Gson().fromJson(rRSeatSavedPosStr, (Class<Object>) int[].class);
        }
        return CarBaseConfig.getInstance().getSecRowMSMSeatDefaultPos();
    }

    public int[] getPsnSelectSeatPos(int selectId) {
        String psnSeatSavedPosStr = getPsnSeatSavedPosStr(selectId);
        LogUtils.i(TAG, "getPsnSeatPos:" + psnSeatSavedPosStr);
        if (!TextUtils.isEmpty(psnSeatSavedPosStr)) {
            return (int[]) new Gson().fromJson(psnSeatSavedPosStr, (Class<Object>) int[].class);
        }
        return CarBaseConfig.getInstance().getMSMSeatDefaultPos();
    }

    public int[] getRLSelectSeatPos(int selectId) {
        String rLSeatSavedPosStr = getRLSeatSavedPosStr(selectId);
        LogUtils.i(TAG, "getRLSelectSeatPos:" + rLSeatSavedPosStr);
        if (!TextUtils.isEmpty(rLSeatSavedPosStr)) {
            return (int[]) new Gson().fromJson(rLSeatSavedPosStr, (Class<Object>) int[].class);
        }
        return CarBaseConfig.getInstance().getSecRowMSMSeatDefaultPos();
    }

    public int[] getRRSelectSeatPos(int selectId) {
        String rRSeatSavedPosStr = getRRSeatSavedPosStr(selectId);
        LogUtils.i(TAG, "getRRSelectSeatPos:" + rRSeatSavedPosStr);
        if (!TextUtils.isEmpty(rRSeatSavedPosStr)) {
            return (int[]) new Gson().fromJson(rRSeatSavedPosStr, (Class<Object>) int[].class);
        }
        return CarBaseConfig.getInstance().getSecRowMSMSeatDefaultPos();
    }

    protected String getPsnSeatSavedPosStr() {
        return SharedPreferenceUtil.getPsnSeatData("CHAIR_POSITION_PSN");
    }

    protected String getRLSeatSavedPosStr() {
        return SharedPreferenceUtil.getRLSeatData(KEY_CHAIR_POS_RL);
    }

    protected String getRRSeatSavedPosStr() {
        return SharedPreferenceUtil.getRRSeatData(KEY_CHAIR_POS_RR);
    }

    protected String getPsnSeatSavedPosStr(int selectId) {
        return SharedPreferenceUtil.getPsnSelectSeatData(selectId);
    }

    protected String getRLSeatSavedPosStr(int selectId) {
        return SharedPreferenceUtil.getRLSelectSeatData(selectId);
    }

    protected String getRRSeatSavedPosStr(int selectId) {
        return SharedPreferenceUtil.getRRSelectSeatData(selectId);
    }

    public String getPsnSeatName(int selectId) {
        String psnSeatName = SharedPreferenceUtil.getPsnSeatName(selectId);
        LogUtils.i(TAG, "getPsnSeatName:" + psnSeatName);
        return !TextUtils.isEmpty(psnSeatName) ? psnSeatName : ResUtils.getString(R.string.seat_default_name, Integer.valueOf(selectId));
    }

    public String getRLSeatName(int selectId) {
        String rLSeatName = SharedPreferenceUtil.getRLSeatName(selectId);
        LogUtils.i(TAG, "getRLSeatName:" + rLSeatName);
        return !TextUtils.isEmpty(rLSeatName) ? rLSeatName : ResUtils.getString(R.string.seat_default_name, Integer.valueOf(selectId));
    }

    public String getRRSeatName(int selectId) {
        String rRSeatName = SharedPreferenceUtil.getRRSeatName(selectId);
        LogUtils.i(TAG, "getRRSeatName:" + rRSeatName);
        return !TextUtils.isEmpty(rRSeatName) ? rRSeatName : ResUtils.getString(R.string.seat_default_name, Integer.valueOf(selectId));
    }

    public void setWelcomeMode(boolean enable) {
        LogUtils.i(TAG, "setWelcomeMode: " + enable);
        this.mSyncDataValue.setWelcomeMode(enable);
        if (!isGuest()) {
            save("WELCOME_MODE", String.valueOf(enable));
        }
        SharedPreferenceUtil.setWelcomeMode(enable);
    }

    public boolean getWelcomeMode() {
        if (isGuest()) {
            return SharedPreferenceUtil.isWelcomeModeEnabled();
        }
        return this.mSyncDataValue.isWelcomeMode();
    }

    public void setPsnWelcomeMode(boolean enable) {
        LogUtils.i(TAG, "setPsnWelcomeMode: " + enable);
        SharedPreferenceUtil.setPsnWelcomeMode(enable);
    }

    public boolean getPsnWelcomeMode() {
        return SharedPreferenceUtil.isPsnWelcomeModeEnabled();
    }

    public void setRearSeatWelcomeMode(boolean enable) {
        LogUtils.i(TAG, "setRearSeatWelcomeMode: " + enable);
        this.mSyncDataValue.setRearSeatWelcomeMode(enable);
        if (!isGuest()) {
            save("REAR_SEAT_WELCOME_MODE", String.valueOf(enable));
        }
        SharedPreferenceUtil.setRearSeatWelcomeMode(enable);
    }

    public boolean getRearSeatWelcomeMode() {
        if (isGuest()) {
            return SharedPreferenceUtil.isRearSeatWelcomeModeEnabled();
        }
        return this.mSyncDataValue.isRearSeatWelcomeMode();
    }

    public void setCarpetLightWelcomeMode(boolean enable) {
        SharedPreferenceUtil.setCarpetLightWelcomeMode(enable);
    }

    public boolean getCarpetLightWelcomeMode() {
        return SharedPreferenceUtil.getCarpetLightWelcomeMode();
    }

    public void setPollingLightWelcomeMode(boolean enable) {
        SharedPreferenceUtil.setPollingLightWelcomeMode(enable);
    }

    public boolean getPollingLightWelcomeMode() {
        return SharedPreferenceUtil.getPollingLightWelcomeMode();
    }

    public void setDrvSeatEsb(boolean enable) {
        LogUtils.i(TAG, "setDrvSeatEsb: " + enable);
        this.mSyncDataValue.setDrvSeatEsb(enable);
        if (!isGuest()) {
            save("DRV_SEAT_ESB", String.valueOf(enable));
        }
        SharedPreferenceUtil.setEsbEnable(enable);
    }

    public boolean getDrvSeatEsb() {
        if (isGuest()) {
            return SharedPreferenceUtil.isEsbEnabled();
        }
        return this.mSyncDataValue.isDrvSeatEsb();
    }

    public void setRsbWarning(boolean enable) {
        LogUtils.i(TAG, "setRsbWarning: " + enable);
        this.mSyncDataValue.setRsbWarning(enable);
        if (!isGuest()) {
            save("REAR_SEAT_WARNING", String.valueOf(enable));
        }
        SharedPreferenceUtil.setRsbWarningEnable(enable);
    }

    public boolean getRsbWarning() {
        if (isGuest()) {
            return SharedPreferenceUtil.isRsbWarningEnabled();
        }
        return this.mSyncDataValue.isRsbWarning();
    }

    public void setSteerData(int[] postions) {
        String arrays = Arrays.toString(postions);
        LogUtils.i(TAG, "setSteerData: " + arrays);
        this.mSyncDataValue.setSteerPos(arrays);
        if (!isGuest()) {
            save("STEER_POS", String.valueOf(arrays));
        }
        SharedPreferenceUtil.setSteerPos(arrays);
    }

    public int[] getSteerData(boolean enableDefaultValue) {
        String steerPos = isGuest() ? SharedPreferenceUtil.getSteerPos(enableDefaultValue) : this.mSyncDataValue.getSteerPos();
        if (steerPos != null && steerPos.length() > 0) {
            try {
                int[] iArr = new int[2];
                String[] split = steerPos.replace("[", "").replace("]", "").split(",");
                for (int i = 0; i < 2; i++) {
                    iArr[i] = Integer.parseInt(split[i].trim());
                }
                return iArr;
            } catch (Exception e) {
                LogUtils.e(TAG, "getSteerData error " + e.getMessage());
            }
        }
        if (enableDefaultValue) {
            return new int[]{50, 50};
        }
        return null;
    }

    public int[] getSteerData() {
        return getSteerData(true);
    }

    public void setMirrorData(String mirrorPos) {
        if (this.mSyncDataValue.isNeedRestore()) {
            LogUtils.i(TAG, "Wait ServiceViewModel to restore mirror position, do not save current position: " + mirrorPos);
        } else if (TextUtils.equals(getMirrorData(), mirrorPos)) {
        } else {
            LogUtils.i(TAG, "setMirrorData when position changed: " + mirrorPos);
            this.mSyncDataValue.setMirrorPos(mirrorPos);
            if (!isGuest()) {
                save("MIRROR_POS", mirrorPos);
            }
            SharedPreferenceUtil.setRearMirrorData(mirrorPos);
        }
    }

    public String getMirrorData() {
        if (isGuest()) {
            return SharedPreferenceUtil.getRearMirrorData();
        }
        return this.mSyncDataValue.getMirrorPos();
    }

    public void setMirrorRestoreFinished() {
        this.mSyncDataValue.setNeedRestore(false);
    }

    public void setMirrorReverseMode(int mode) {
        LogUtils.i(TAG, "setMirrorReverseMode: " + mode);
        this.mSyncDataValue.setMirrorReverseMode(mode);
        if (!isGuest()) {
            save("MIRROR_REVERSE", String.valueOf(mode));
        }
        SharedPreferenceUtil.setMirrorReverseMode(mode);
    }

    public int getMirrorReverseMode() {
        int mirrorReverseMode;
        if (isGuest()) {
            mirrorReverseMode = SharedPreferenceUtil.getMirrorReverseMode();
        } else {
            mirrorReverseMode = this.mSyncDataValue.getMirrorReverseMode();
        }
        LogUtils.d(TAG, "getMirrorReverseMode reverseMode=" + mirrorReverseMode, false);
        return mirrorReverseMode;
    }

    public void setMirrorAutoFoldEnable(boolean enable) {
        LogUtils.i(TAG, "setMirrorAutoFoldEnable: " + enable);
        this.mSyncDataValue.setMirrorAutoFoldEnable(enable);
        if (!isGuest()) {
            save(KEY_MIRROR_AUTO_FOLD, String.valueOf(enable));
        }
        SharedPreferenceUtil.setMirrorAutoFoldEnable(enable);
    }

    public boolean getMirrorAutoFoldEnable() {
        boolean mirrorAutoFoldEnable;
        if (isGuest()) {
            mirrorAutoFoldEnable = SharedPreferenceUtil.getMirrorAutoFoldEnable();
        } else {
            mirrorAutoFoldEnable = this.mSyncDataValue.getMirrorAutoFoldEnable();
        }
        LogUtils.d(TAG, "getMirrorAutoFoldEnable isMirrorAutoFoldEnable=" + mirrorAutoFoldEnable, false);
        return mirrorAutoFoldEnable;
    }

    public void setSensorTrunkSw(boolean enable) {
        LogUtils.i(TAG, "setSensorTrunkSw: " + enable);
        this.mSyncDataValue.setSensorTrunkSw(enable);
        if (!isGuest()) {
            save("SENSOR_TRUNK_SW", String.valueOf(enable));
        }
        SharedPreferenceUtil.setTrunkSensorEnable(enable);
    }

    public boolean getSensorTrunkSw() {
        if (isGuest()) {
            return SharedPreferenceUtil.isTrunkSensorEnable();
        }
        return this.mSyncDataValue.getSensorTrunkSw();
    }

    public void setTrunkFullOpenPos(int position) {
        LogUtils.i(TAG, "setTrunkFullOpenPos: " + position);
        this.mSyncDataValue.setTrunkFullOpenPos(position);
        if (!isGuest()) {
            save("TRUNK_OPEN_POS", String.valueOf(position));
        }
        SharedPreferenceUtil.setTrunkFullOpenPos(position);
    }

    public int getTrunkFullOpenPos() {
        if (isGuest()) {
            return SharedPreferenceUtil.getTrunkFullOpenPos();
        }
        return this.mSyncDataValue.getTrunkFullOpenPos();
    }

    public void setHighSpdCloseWin(boolean enable) {
        LogUtils.i(TAG, "setHighSpdCloseWin: " + enable);
        this.mSyncDataValue.setHighSpdCloseWin(enable);
        if (!isGuest()) {
            save("HIGH_SPD_CLOSE_WIN", String.valueOf(enable));
        }
        SharedPreferenceUtil.setHighSpdCloseWinSw(enable);
    }

    public boolean isWinHighSpdEnabled() {
        if (isGuest()) {
            return SharedPreferenceUtil.getHighSpdCloseWinSw();
        }
        return this.mSyncDataValue.isHighSpdCloseWin();
    }

    public void setLockCloseWin(boolean enable) {
        LogUtils.i(TAG, "setLockCloseWin: " + enable);
        this.mSyncDataValue.setLockCloseWin(enable);
        if (!isGuest()) {
            save("LOCK_CLOSE_WIN", String.valueOf(enable));
        }
        SharedPreferenceUtil.setAutoWindowLockSw(enable);
    }

    public boolean getLockCloseWin() {
        if (isGuest()) {
            return SharedPreferenceUtil.getAutoWindowLockSw();
        }
        return this.mSyncDataValue.isLockCloseWin();
    }

    public void setWindowLockState(boolean on) {
        LogUtils.i(TAG, "setWindowLockState: " + on);
        SharedPreferenceUtil.setWindowLockState(on);
    }

    public boolean getWindowLockState() {
        return SharedPreferenceUtil.getWindowLockState();
    }

    public void setWiperSensitivity(int level) {
        LogUtils.i(TAG, "setWiperSensitivity: " + level);
        this.mSyncDataValue.setWiperSensitivity(level);
        if (!isGuest()) {
            save("WIPER_SENSITIVITY", String.valueOf(level));
        }
        SharedPreferenceUtil.setWiperIntervalGear(level);
    }

    public int getWiperSensitivity() {
        if (isGuest()) {
            return SharedPreferenceUtil.getWiperIntervalGear();
        }
        return this.mSyncDataValue.getWiperSensitivity();
    }

    public void setCwcSw(boolean enabled) {
        LogUtils.i(TAG, "setCwcSw: " + enabled);
        this.mSyncDataValue.setCwcSw(enabled);
        if (!isGuest()) {
            save("CWC_SW", String.valueOf(enabled));
        }
        SharedPreferenceUtil.setCwcSw(enabled);
    }

    public boolean getCwcSw() {
        if (isGuest()) {
            return SharedPreferenceUtil.getCwcSw();
        }
        return this.mSyncDataValue.isCwcSwEnabled();
    }

    public void setCwcFRSw(boolean enabled) {
        LogUtils.i(TAG, "setCwcFRSw: " + enabled);
        SharedPreferenceUtil.setCwcFRSw(enabled);
    }

    public boolean getCwcFRSw() {
        return SharedPreferenceUtil.getCwcFRSw();
    }

    public void setCwcRLSw(boolean enabled) {
        LogUtils.i(TAG, "setCwcRLSw: " + enabled);
        SharedPreferenceUtil.setCwcRLSw(enabled);
    }

    public boolean getCwcRLSw() {
        return SharedPreferenceUtil.getCwcRLSw();
    }

    public void setCwcRRSw(boolean enabled) {
        LogUtils.i(TAG, "setCwcRRSw: " + enabled);
        SharedPreferenceUtil.setCwcRRSw(enabled);
    }

    public boolean getCwcRRSw() {
        return SharedPreferenceUtil.getCwcRRSw();
    }

    public void setMicrophoneMute(boolean mute) {
        LogUtils.i(TAG, "setMicrophoneMute: " + mute);
        this.mSyncDataValue.setMicrophoneMute(mute);
        if (!isGuest()) {
            save("MICROPHONE_MUTE", String.valueOf(mute));
        }
        SharedPreferenceUtil.setMicrophoneMute(mute);
    }

    public boolean getMicrophoneMute() {
        if (isGuest()) {
            return SharedPreferenceUtil.getMicrophoneMute();
        }
        return this.mSyncDataValue.isMicrophoneMute();
    }

    public void setHvacSelfDry(boolean enable) {
        LogUtils.i(TAG, "setHvacSelfDry: " + enable);
        SharedPreferenceUtil.setHvacSelfDry(enable);
    }

    public boolean getHvacSelfDry() {
        return SharedPreferenceUtil.isHvacSelfDryEnabled();
    }

    public boolean getAvasSw() {
        return this.mSyncDataValue.isAvasSw();
    }

    public void setAvasEffect(int effect) {
        LogUtils.i(TAG, "setAvasEffect: " + effect);
        this.mSyncDataValue.setAvasEffect(effect);
        if (!isGuest()) {
            save("AVAS_EFFECT", String.valueOf(effect));
        }
        SharedPreferenceUtil.setAvasLowSpdEffect(effect);
    }

    public int getAvasEffect() {
        if (isGuest()) {
            return SharedPreferenceUtil.getAvasLowSpdEffect();
        }
        return this.mSyncDataValue.getAvasEffect();
    }

    public int getAvasVolume() {
        return this.mSyncDataValue.getAvasVolume();
    }

    public void setSayHiEffect(int effect) {
        LogUtils.i(TAG, "setSayHiEffect: " + effect);
        this.mSyncDataValue.setSayHiEffect(effect);
        if (!isGuest()) {
            save("AVAS_SAYHI_EFFECT", String.valueOf(effect));
        }
        SharedPreferenceUtil.setAvasFriendEffect(effect);
    }

    public int getSayHiEffect() {
        if (isGuest()) {
            return SharedPreferenceUtil.getAvasFriendEffect();
        }
        return this.mSyncDataValue.getSayHiEffect();
    }

    public int getAvasOtherVolume() {
        return SharedPreferenceUtil.getAvasExternalVolume();
    }

    public void setBootEffect(int effect) {
        LogUtils.i(TAG, "setBootEffect: " + effect);
        this.mSyncDataValue.setBootEffect(effect);
        if (!isGuest()) {
            save("AVAS_BOOT_EFFECT", String.valueOf(effect));
        }
        SharedPreferenceUtil.setBootEffect(effect);
    }

    public int getBootEffect() {
        if (isGuest()) {
            return SharedPreferenceUtil.getBootEffect();
        }
        return this.mSyncDataValue.getBootEffect();
    }

    public void setBootEffectBeforeSw(int effect) {
        LogUtils.i(TAG, "setBootEffectBeforeSw: " + effect);
        this.mSyncDataValue.setBootEffectBeforeSw(effect);
        if (!isGuest()) {
            save("AVAS_BOOT_EFFECT_BEFORE_SW", String.valueOf(effect));
        }
        SharedPreferenceUtil.setBootEffectOld(effect);
    }

    public int getBootEffectBeforeSw() {
        if (isGuest()) {
            return SharedPreferenceUtil.getBootEffectOld();
        }
        return this.mSyncDataValue.getBootEffectBeforeSw();
    }

    public void setFcwSw(boolean enable) {
        LogUtils.i(TAG, "setFcwSw: " + enable);
        this.mSyncDataValue.setFcwSw(enable);
    }

    public boolean getFcwSw() {
        return this.mSyncDataValue.isFcwSw();
    }

    public boolean getAutoParkSw() {
        if (isGuest()) {
            return SharedPreferenceUtil.getAutoParkSw();
        }
        return this.mSyncDataValue.isAutoParkSw();
    }

    public void setAutoParkSw(boolean enable) {
        LogUtils.i(TAG, "setAutoParkSw: " + enable);
        this.mSyncDataValue.setAutoParkSw(enable);
        if (!isGuest()) {
            save("XPILOT_AUTO_PARK_SW", String.valueOf(enable));
        }
        SharedPreferenceUtil.setAutoParkSw(enable);
    }

    public boolean getMemParkSw() {
        if (isGuest()) {
            return SharedPreferenceUtil.getMemParkSw();
        }
        return this.mSyncDataValue.getMemParkSw();
    }

    public void setMemParkSw(boolean enable) {
        LogUtils.i(TAG, "setMemParkSw: " + enable);
        this.mSyncDataValue.setMemParkSw(enable);
        if (!isGuest()) {
            save(KEY_XPILOT_MEM_PARK_SW, String.valueOf(enable));
        }
        SharedPreferenceUtil.setMemParkSw(enable);
    }

    public void setLccSw(boolean enable) {
        setLccSw(enable, true);
    }

    public void setLccSw(boolean enable, boolean needSaveToAccount) {
        LogUtils.i(TAG, "setLccSw: " + enable);
        this.mSyncDataValue.setLccSw(enable);
        if (needSaveToAccount && !isGuest()) {
            save("XPILOT_LCC", String.valueOf(enable));
        }
        SharedPreferenceUtil.setLccSw(enable);
    }

    public boolean getLccSw() {
        if (isGuest()) {
            return SharedPreferenceUtil.getLccSw();
        }
        return this.mSyncDataValue.isLccSw();
    }

    public void setNraState(int nraSt) {
        LogUtils.i(TAG, "setNraState: " + nraSt);
        this.mSyncDataValue.setNraState(nraSt);
        if (!isGuest()) {
            save(KEY_XPILOT_NRA, String.valueOf(nraSt));
        }
        SharedPreferenceUtil.setNraState(nraSt);
    }

    public int getNraState() {
        if (isGuest()) {
            return SharedPreferenceUtil.getNraState();
        }
        return this.mSyncDataValue.getNraState();
    }

    public void setIslaSw(int sasSt) {
        LogUtils.i(TAG, "setIslaSw: " + sasSt);
        SharedPreferenceUtil.setIslaSw(sasSt);
    }

    public int getIslaSw() {
        return SharedPreferenceUtil.getIslaSw();
    }

    public void setIslaSpdRange(int spdRange) {
        LogUtils.i(TAG, "setIslaSpdRange: " + spdRange);
        SharedPreferenceUtil.setIslaSpdRange(spdRange);
    }

    public int getIslaSpdRange() {
        return SharedPreferenceUtil.getIslaSpdRange();
    }

    public void setIslaConfirmMode(boolean mode) {
        LogUtils.i(TAG, "setIslaConfirmMode: " + mode);
        SharedPreferenceUtil.setIslaConfirmMode(mode);
    }

    public boolean getIslaConfirmMode() {
        return SharedPreferenceUtil.getIslaConfirmMode();
    }

    public void setAlcSw(boolean enable) {
        setAlcSw(enable, true);
    }

    public void setAlcSw(boolean enable, boolean needSaveToAccount) {
        LogUtils.i(TAG, "setAlcSw: " + enable);
        this.mSyncDataValue.setAlcSw(enable);
        if (needSaveToAccount && !isGuest()) {
            save("XPILOT_ALC", String.valueOf(enable));
        }
        SharedPreferenceUtil.setAlcSw(enable);
    }

    public boolean getAlcSw() {
        if (isGuest()) {
            return SharedPreferenceUtil.getAlcSw();
        }
        return this.mSyncDataValue.isAlcSw();
    }

    public void setLccSafeExamResult(boolean result) {
        LogUtils.i(TAG, "setLccSafeExamResult: " + result);
        setLccSafeExamResult(String.valueOf(this.mUid), result);
    }

    public void setLccSafeExamResult(String uid, boolean result) {
        if (uid == null || Constant.DEFAULT_ERROR_RSC_ID.equals(uid)) {
            LogUtils.i(TAG, "Guest mode, setLccSafeExamResult: false");
            return;
        }
        LogUtils.i(TAG, "setLccSafeExamResult: " + result + " for Uid: " + uid, false);
        LccExamModel.getInstance().setLccExamResult(uid, result);
    }

    public boolean getLccSafeExamResult() {
        if (this.mUid <= 0) {
            this.mUid = getCurrentAccountId();
        }
        return getLccSafeExamResult(String.valueOf(this.mUid));
    }

    public boolean getLccSafeExamResult(String uid) {
        if (uid == null || Constant.DEFAULT_ERROR_RSC_ID.equals(uid)) {
            LogUtils.i(TAG, "Guest mode, LCC exam result should keep false");
            return false;
        }
        boolean lccExamResult = LccExamModel.getInstance().getLccExamResult(uid);
        LogUtils.i(TAG, "getLccSafeExamResult: " + lccExamResult + " for Uid: " + uid, false);
        return lccExamResult;
    }

    public boolean shouldUpdateLccSafeExam() {
        boolean z = (isGuest() || LccExamModel.getInstance().isLccExamUpdated()) ? false : true;
        LogUtils.i(TAG, "Should update lcc safe exam? " + z);
        if (z) {
            LccExamModel.getInstance().setLccExamUpdate();
        }
        return z;
    }

    public boolean isChildModeOpened() {
        boolean isChildModeOpened = ChildModeModel.getInstance().isChildModeOpened();
        LogUtils.i(TAG, "Has child mode been opened by user? " + isChildModeOpened);
        if (!isChildModeOpened) {
            ChildModeModel.getInstance().setChildModeOpened();
        }
        return isChildModeOpened;
    }

    public void setApaSafeExamResult(String uid, boolean result) {
        if (uid == null || Constant.DEFAULT_ERROR_RSC_ID.equals(uid)) {
            LogUtils.i(TAG, "Guest mode, setApaSafeExamResult: false");
            return;
        }
        LogUtils.i(TAG, "setApaSafeExamResult: " + result + " for Uid: " + uid, false);
        ApaExamModel.getInstance().setApaExamResult(uid, result);
    }

    public boolean getApaSafeExamResult() {
        if (this.mUid <= 0) {
            this.mUid = getCurrentAccountId();
        }
        return getApaSafeExamResult(String.valueOf(this.mUid));
    }

    public void setApaSafeExamResult(boolean result) {
        LogUtils.i(TAG, "setApaSafeExamResult: " + result);
        setApaSafeExamResult(String.valueOf(this.mUid), result);
    }

    public boolean getApaSafeExamResult(String uid) {
        if (uid == null || Constant.DEFAULT_ERROR_RSC_ID.equals(uid)) {
            LogUtils.i(TAG, "Guest mode, Apa exam result should keep false");
            return false;
        }
        boolean apaExamResult = ApaExamModel.getInstance().getApaExamResult(uid);
        LogUtils.i(TAG, "getApaSafeExamResult: " + apaExamResult + " for Uid: " + uid, false);
        return apaExamResult;
    }

    public boolean getCngpSafeExamResult() {
        if (this.mUid <= 0) {
            this.mUid = getCurrentAccountId();
        }
        return getCngpSafeExamResult(String.valueOf(this.mUid));
    }

    public void setCngpSafeExamResult(boolean result) {
        LogUtils.i(TAG, "setCngpSafeExamResult: " + result);
        setCngpSafeExamResult(String.valueOf(this.mUid), result);
    }

    public boolean getCngpSafeExamResult(String uid) {
        if (uid == null || Constant.DEFAULT_ERROR_RSC_ID.equals(uid)) {
            LogUtils.i(TAG, "Guest mode, Cngp exam result should keep false");
            return false;
        }
        boolean cngpExamResult = CngpExamModel.getInstance().getCngpExamResult(uid);
        LogUtils.i(TAG, "getCngpSafeExamResult: " + cngpExamResult + " for Uid: " + uid, false);
        return cngpExamResult;
    }

    public void setCngpSafeExamResult(String uid, boolean result) {
        if (uid == null || Constant.DEFAULT_ERROR_RSC_ID.equals(uid)) {
            LogUtils.i(TAG, "Guest mode, setCngpSafeExamResult: false");
            return;
        }
        LogUtils.i(TAG, "setCngpSafeExamResult: " + result + " for Uid: " + uid, false);
        CngpExamModel.getInstance().setCngpExamResult(uid, result);
    }

    public boolean getXngpSafeExamResult() {
        if (this.mUid <= 0) {
            this.mUid = getCurrentAccountId();
        }
        return getXngpSafeExamResult(String.valueOf(this.mUid));
    }

    public void setXngpSafeExamResult(boolean result) {
        LogUtils.i(TAG, "setXngpSafeExamResult: " + result);
        setXngpSafeExamResult(String.valueOf(this.mUid), result);
    }

    public boolean getXngpSafeExamResult(String uid) {
        if (uid == null || Constant.DEFAULT_ERROR_RSC_ID.equals(uid)) {
            LogUtils.i(TAG, "Guest mode, Xngp exam result should keep false");
            return false;
        }
        boolean xngpExamResult = XngpExamModel.getInstance().getXngpExamResult(uid);
        LogUtils.i(TAG, "getXngpSafeExamResult: " + xngpExamResult + " for Uid: " + uid, false);
        return xngpExamResult;
    }

    public void setXngpSafeExamResult(String uid, boolean result) {
        if (uid == null || Constant.DEFAULT_ERROR_RSC_ID.equals(uid)) {
            LogUtils.i(TAG, "Guest mode, setXngpSafeExamResult: false");
            return;
        }
        LogUtils.i(TAG, "setXngpSafeExamResult: " + result + " for Uid: " + uid, false);
        XngpExamModel.getInstance().setXngpExamResult(uid, result);
    }

    public boolean shouldUpdateXngpSafeExam() {
        boolean z = (isGuest() || XngpExamModel.getInstance().isXngpExamUpdated()) ? false : true;
        LogUtils.i(TAG, "Should update xngp safe exam? " + z);
        if (z) {
            XngpExamModel.getInstance().setXngpExamUpdate();
        }
        return z;
    }

    public boolean shouldUpdateApaSafeExam() {
        boolean z = (isGuest() || ApaExamModel.getInstance().isApaExamUpdated()) ? false : true;
        LogUtils.i(TAG, "Should update apa safe exam? " + z);
        if (z) {
            ApaExamModel.getInstance().setApaExamUpdate();
        }
        return z;
    }

    public boolean shouldUpdateSuperVpaSafeExam() {
        boolean z = (isGuest() || SuperVpaExamModel.getInstance().isSuperVpaUpdated()) ? false : true;
        LogUtils.i(TAG, "Should update super vpa safe exam? " + z);
        if (z) {
            SuperVpaExamModel.getInstance().setSuperVpaUpdated();
        }
        return z;
    }

    public boolean shouldUpdateSuperLccSafeExam() {
        boolean z = (isGuest() || SuperLccExamModel.getInstance().isSuperLccExamUpdated()) ? false : true;
        LogUtils.i(TAG, "Should update super lcc safe exam? " + z);
        if (z) {
            SuperLccExamModel.getInstance().setSuperLccExamUpdate();
        }
        return z;
    }

    public void setLccVideoWatched(boolean watched) {
        LogUtils.i(TAG, "setLccVideoWatched: " + watched);
        SharedPreferenceUtil.setLCCVideoWatched(watched);
    }

    public boolean isLccVideoWatched() {
        boolean isLCCVideoWatched = SharedPreferenceUtil.isLCCVideoWatched();
        LogUtils.i(TAG, "isLccVideoWatched: " + isLCCVideoWatched);
        return isLCCVideoWatched;
    }

    public void setMemParkVideoWatched(boolean watched) {
        LogUtils.i(TAG, "setMemParkVideoWatched: " + watched);
        SharedPreferenceUtil.setMemParkVideoWatched(watched);
    }

    public boolean isMemParkVideoWatched() {
        boolean isMemParkVideoWatched = SharedPreferenceUtil.isMemParkVideoWatched();
        LogUtils.i(TAG, "isMemParkVideoWatched: " + isMemParkVideoWatched);
        return isMemParkVideoWatched;
    }

    public void setNgpSafeExamResult(boolean result) {
        setNgpSafeExamResult(String.valueOf(this.mUid), result);
    }

    public void setNgpSafeExamResult(String uid, boolean result) {
        if (uid == null || Constant.DEFAULT_ERROR_RSC_ID.equals(uid)) {
            LogUtils.i(TAG, "Guest mode, setNgpSafeExamResult: false");
            return;
        }
        LogUtils.i(TAG, "setNgpSafeExamResult: " + result + " for Uid: " + uid);
        NgpExamModel.getInstance().setNgpExamResult(uid, result);
    }

    public boolean getNgpSafeExamResult() {
        return getNgpSafeExamResult(String.valueOf(getCurrentAccountId()));
    }

    public boolean getNgpSafeExamResult(String uid) {
        if (uid == null || Constant.DEFAULT_ERROR_RSC_ID.equals(uid)) {
            LogUtils.i(TAG, "Guest mode, NGP exam result should keep false");
            return false;
        }
        boolean ngpExamResult = NgpExamModel.getInstance().getNgpExamResult(uid);
        LogUtils.i(TAG, "getNgpSafeExamResult: " + ngpExamResult + " for Uid: " + uid);
        return ngpExamResult;
    }

    public void setMemParkSafeExamResult(String uid, boolean result) {
        if (uid == null || Constant.DEFAULT_ERROR_RSC_ID.equals(uid)) {
            LogUtils.i(TAG, "Guest mode, setMemParkSafeExamResult: false");
            return;
        }
        LogUtils.i(TAG, "setMemParkSafeExamResult: " + result + " for Uid: " + uid);
        MemParkExamModel.getInstance().setMemParkExamResult(uid, result);
    }

    public boolean getMemParkSafeExamResult() {
        return getMemParkSafeExamResult(String.valueOf(getCurrentAccountId()));
    }

    public void setMemParkSafeExamResult(boolean result) {
        setMemParkSafeExamResult(String.valueOf(this.mUid), result);
    }

    public boolean getSuperVpaSafeExamResult() {
        return getSuperVpaSafeExamResult(String.valueOf(getCurrentAccountId()));
    }

    public void setSuperVpaSafeExamResult(boolean result) {
        setSuperVpaSafeExamResult(String.valueOf(this.mUid), result);
    }

    public boolean getSuperLccSafeExamResult() {
        return getSuperLccSafeExamResult(String.valueOf(getCurrentAccountId()));
    }

    public void setSuperLccSafeExamResult(boolean result) {
        setSuperLccSafeExamResult(String.valueOf(this.mUid), result);
    }

    public boolean getSuperVpaSafeExamResult(String uid) {
        if (uid == null || Constant.DEFAULT_ERROR_RSC_ID.equals(uid)) {
            LogUtils.i(TAG, "Guest mode, Super Vpa result should keep false");
            return false;
        }
        boolean superVpaExamResult = SuperVpaExamModel.getInstance().getSuperVpaExamResult(uid);
        LogUtils.i(TAG, "getSuperVpaSafeExamResult: " + superVpaExamResult + " for Uid: " + uid);
        return superVpaExamResult;
    }

    public void setSuperVpaSafeExamResult(String uid, boolean result) {
        if (uid == null || Constant.DEFAULT_ERROR_RSC_ID.equals(uid)) {
            LogUtils.i(TAG, "Guest mode, setSuperVpaSafeExamResult: false");
            return;
        }
        LogUtils.i(TAG, "setSuperVpaExamResult: " + result + " for Uid: " + uid);
        SuperVpaExamModel.getInstance().setSuperVpaExamResult(uid, result);
    }

    public boolean getSuperLccSafeExamResult(String uid) {
        if (uid == null || Constant.DEFAULT_ERROR_RSC_ID.equals(uid)) {
            LogUtils.i(TAG, "Guest mode, Super Lcc result should keep false");
            return false;
        }
        boolean superLccExamResult = SuperLccExamModel.getInstance().getSuperLccExamResult(uid);
        LogUtils.i(TAG, "getSuperLccSafeExamResult: " + superLccExamResult + " for Uid: " + uid);
        return superLccExamResult;
    }

    public void setSuperLccSafeExamResult(String uid, boolean result) {
        if (uid == null || Constant.DEFAULT_ERROR_RSC_ID.equals(uid)) {
            LogUtils.i(TAG, "Guest mode, setSuperLccSafeExamResult: false");
            return;
        }
        LogUtils.i(TAG, "setSuperLccExamResult: " + result + " for Uid: " + uid);
        SuperLccExamModel.getInstance().setSuperLccExamResult(uid, result);
    }

    public boolean getMemParkSafeExamResult(String uid) {
        if (uid == null || Constant.DEFAULT_ERROR_RSC_ID.equals(uid)) {
            LogUtils.i(TAG, "Guest mode, Mem exam result should keep false");
            return false;
        }
        boolean memParkExamResult = MemParkExamModel.getInstance().getMemParkExamResult(uid);
        LogUtils.i(TAG, "getMemParkSafeExamResult: " + memParkExamResult + " for Uid: " + uid);
        return memParkExamResult;
    }

    public boolean isMemParkExamChecked() {
        boolean isMemParkChecked = MemParkExamModel.getInstance().isMemParkChecked();
        LogUtils.i(TAG, "Is mem park file has been checked (NOT OTA)?: " + isMemParkChecked);
        if (!isMemParkChecked) {
            MemParkExamModel.getInstance().setMemParkChecked();
        }
        return isMemParkChecked;
    }

    public void setNgpSw(boolean enable) {
        LogUtils.i(TAG, "setNgpSw: " + enable, false);
        this.mSyncDataValue.setNgpSw(enable);
        if (!isGuest()) {
            save("NGP_SW", String.valueOf(enable));
        }
        SharedPreferenceUtil.setNgpSw(enable);
    }

    public boolean getNgpSw() {
        if (isGuest()) {
            return SharedPreferenceUtil.getNgpSw();
        }
        return this.mSyncDataValue.getNgpSw();
    }

    public void setCityNgpSw(boolean enable) {
        LogUtils.i(TAG, "setCityNgpSw: " + enable, false);
        this.mSyncDataValue.setCityNgpSw(enable);
        if (!isGuest()) {
            save("CITY_NGP_SW", String.valueOf(enable));
        }
        SharedPreferenceUtil.setCityNgpSw(enable);
    }

    public boolean getCityNgpSw() {
        if (isGuest()) {
            return SharedPreferenceUtil.getCityNgpSw();
        }
        return this.mSyncDataValue.getCityNgpSw();
    }

    public int getLssState() {
        if (isGuest()) {
            return SharedPreferenceUtil.getLssState();
        }
        return this.mSyncDataValue.getLssState();
    }

    public void setLssState(int state) {
        LogUtils.i(TAG, "setLssState: " + state + " user has already login ?" + (!isGuest()));
        this.mSyncDataValue.setLssState(state);
        if (!isGuest()) {
            save(KEY_XPILOT_LSS_STATE, String.valueOf(state));
        }
        SharedPreferenceUtil.setLssState(state);
    }

    public void setCiuCameraSw(boolean enable) {
        LogUtils.i(TAG, "setCiuCameraSw: " + enable);
        SharedPreferenceUtil.setCiuCameraSw(enable);
    }

    public boolean getCiuCameraSw() {
        return SharedPreferenceUtil.getCiuCameraSw();
    }

    public void setCiuFaceSw(boolean enable) {
        LogUtils.i(TAG, "setCiuFaceSw: " + enable);
        SharedPreferenceUtil.setCiuFaceSw(enable);
    }

    public boolean getCiuFaceSw() {
        return SharedPreferenceUtil.getCiuFaceSw();
    }

    public void setCiuFatigueSw(boolean enable) {
        LogUtils.i(TAG, "setCiuFatigueSw: " + enable);
        SharedPreferenceUtil.setCiuFatigueSw(enable);
    }

    public boolean getCiuFatigueSw() {
        return SharedPreferenceUtil.getCiuFatigueSw();
    }

    public void setCiuDistractSw(boolean enable) {
        LogUtils.i(TAG, "setCiuDistractSw: " + enable);
        SharedPreferenceUtil.setCiuDistractSw(enable);
    }

    public boolean getCiuDistractSw() {
        return SharedPreferenceUtil.getCiuDistractSw();
    }

    public void setSoldierSwStatus(int status) {
        LogUtils.i(TAG, "setSoldierSwStatus: " + status);
        SharedPreferenceUtil.setSoldierSwStatus(status);
    }

    public void setNGearWarningSwitch(boolean enable) {
        LogUtils.i(TAG, "setNGearWarningSwitch:" + enable);
        this.mSyncDataValue.setNGearProtectSw(enable);
        if (!isGuest()) {
            save("N_GEAR_PROTECT", String.valueOf(enable));
        }
        SharedPreferenceUtil.setNGearWarningSwitch(enable);
    }

    public boolean getNGearWarningSwitch() {
        boolean nGearProtectSw;
        if (isGuest()) {
            nGearProtectSw = SharedPreferenceUtil.getNGearWarningSwitch();
        } else {
            nGearProtectSw = this.mSyncDataValue.getNGearProtectSw();
        }
        LogUtils.i(TAG, "getNGearWarningSwitch:" + nGearProtectSw);
        return nGearProtectSw;
    }

    public void setSdcKeyCfg(int cfg) {
        LogUtils.i(TAG, "setSdcKeyCfg: " + cfg);
        SharedPreferenceUtil.setSdcKeyCfg(cfg);
    }

    public int getSdcKeyCfg() {
        return SharedPreferenceUtil.getSdcKeyCfg();
    }

    public void setSdcWinAutoDown(boolean status) {
        LogUtils.i(TAG, "setSdcWinAutoDown: " + status);
        SharedPreferenceUtil.setSdcWinAutoDown(status);
    }

    public boolean getSdcWinAutoDown() {
        return SharedPreferenceUtil.getSdcWinAutoDown();
    }

    public int getSdcMaxAutoDoorOpeningAngle() {
        return SharedPreferenceUtil.getSdcMaxAutoDoorOpeningAngle();
    }

    public void setSdcMaxAutoDoorOpeningAngle(int angle) {
        LogUtils.i(TAG, "setSdcMaxAutoDoorOpeningAngle: " + angle);
        SharedPreferenceUtil.setSdcMaxAutoDoorOpeningAngle(angle);
    }

    public void setLeftSlideDoorMode(int mode) {
        LogUtils.i(TAG, "setLeftSlideDoorMode: " + mode);
        SharedPreferenceUtil.setLeftSlideDoorMode(mode);
    }

    public int getLeftSlideDoorMode() {
        return SharedPreferenceUtil.getLeftSlideDoorMode();
    }

    public void setRightSlideDoorMode(int mode) {
        LogUtils.i(TAG, "setRightSlideDoorMode: " + mode);
        SharedPreferenceUtil.setRightSlideDoorMode(mode);
    }

    public int getRightSlideDoorMode() {
        return SharedPreferenceUtil.getRightSlideDoorMode();
    }

    public void setDrvSeatHeatLevel(int level) {
        LogUtils.i(TAG, "setDrvSeatHeatLevel: " + level);
        SharedPreferenceUtil.setDrvSeatHeatLevel(level);
    }

    public int getDrvSeatHeatLevel() {
        int drvSeatHeatLevel = SharedPreferenceUtil.getDrvSeatHeatLevel();
        LogUtils.i(TAG, "getDrvSeatHeatLevel: " + drvSeatHeatLevel);
        return drvSeatHeatLevel;
    }

    public void setDrvSeatVentLevel(int level) {
        LogUtils.i(TAG, "setDrvSeatVentLevel: " + level);
        SharedPreferenceUtil.setDrvSeatVentLevel(level);
    }

    public int getDrvSeatVentLevel() {
        int drvSeatVentLevel = SharedPreferenceUtil.getDrvSeatVentLevel();
        LogUtils.i(TAG, "getDrvSeatVentLevel: " + drvSeatVentLevel);
        return drvSeatVentLevel;
    }

    public void setPsnSeatHeatLevel(int level) {
        LogUtils.i(TAG, "setPsnSeatHeatLevel: " + level);
        SharedPreferenceUtil.setPsnSeatHeatLevel(level);
    }

    public int getPsnSeatHeatLevel() {
        int psnSeatHeatLevel = SharedPreferenceUtil.getPsnSeatHeatLevel();
        LogUtils.i(TAG, "getPsnSeatHeatLevel: " + psnSeatHeatLevel);
        return psnSeatHeatLevel;
    }

    public void setRRSeatHeatLevel(int level) {
        LogUtils.i(TAG, "setRRSeatHeatLevel: " + level);
        SharedPreferenceUtil.setRRSeatHeatLevel(level);
    }

    public int getRRSeatHeatLevel() {
        int rRSeatHeatLevel = SharedPreferenceUtil.getRRSeatHeatLevel();
        LogUtils.i(TAG, "getRRSeatHeatLevel: " + rRSeatHeatLevel);
        return rRSeatHeatLevel;
    }

    public void setRLSeatHeatLevel(int level) {
        LogUtils.i(TAG, "setRLSeatHeatLevel: " + level);
        SharedPreferenceUtil.setRLSeatHeatLevel(level);
    }

    public int getRLSeatHeatLevel() {
        int rLSeatHeatLevel = SharedPreferenceUtil.getRLSeatHeatLevel();
        LogUtils.i(TAG, "getRLSeatHeatLevel: " + rLSeatHeatLevel);
        return rLSeatHeatLevel;
    }

    public void setSteerHeatLevel(int level) {
        LogUtils.i(TAG, "setSteerHeatLevel:" + level);
        SharedPreferenceUtil.setSteerHeatLevel(level);
    }

    public int getSteerHeatLevel() {
        int steerHeatLevel = SharedPreferenceUtil.getSteerHeatLevel();
        LogUtils.i(TAG, "getSteerHeatLevel:" + steerHeatLevel);
        return steerHeatLevel;
    }

    public void setCarLicensePlate(String licensePlate) {
        LogUtils.i(TAG, "setCarLicensePlate: " + licensePlate);
        SharedPreferenceUtil.setCarLicensePlate(licensePlate);
    }

    public String getCarLicensePlate() {
        return SharedPreferenceUtil.getCarLicensePlate();
    }

    public void setUserDefineDriveModeInfo(int driveMode, String info) {
        LogUtils.i(TAG, "setUserDefineDriveModeInfo, index: " + driveMode + ", info: " + info);
        SharedPreferenceUtil.setUserDefineDriveModeInfo(driveMode, info);
    }

    public String getUserDefineDriveModeInfo(int driveMode) {
        return SharedPreferenceUtil.getUserDefineDriveModeInfo(driveMode);
    }

    public void setUserDefineDriveModeInfo(String info) {
        LogUtils.i(TAG, "setUserDefineDriveModeInfo,  info: " + info);
        SharedPreferenceUtil.setUserDefineDriveModeInfo(info);
    }

    public String getUserDefineDriveModeInfo() {
        return SharedPreferenceUtil.getUserDefineDriveModeInfo();
    }

    public void setUserDefineDriveMode(int mode) {
        LogUtils.i(TAG, "setUserDefineDriveMode: " + mode);
        SharedPreferenceUtil.setUserDefineDriveMode(mode);
    }

    public int getUserDefineDriveMode() {
        return SharedPreferenceUtil.getUserDefineDriveMode();
    }

    public void setNewDriveXPedalMode(boolean enable) {
        LogUtils.i(TAG, "setNewDriveXPedalMode: " + enable);
        this.mSyncDataValue.setSinglePedal(enable);
        if (!isGuest()) {
            saveToSync("SINGLEPEDAL", String.valueOf(enable));
        }
        SharedPreferenceUtil.setNewDriveXPedalMode(enable);
    }

    public boolean istNewDriveXPedalModeEnable() {
        if (isGuest()) {
            return SharedPreferenceUtil.isNewDriveXPedalModeEnabled();
        }
        return this.mSyncDataValue.getSinglePedal();
    }

    public void setAsWelcomeMode(boolean enable) {
        LogUtils.i(TAG, "setAsWelcomeMode: " + enable);
        this.mSyncDataValue.setAsWelcomeMode(enable);
        if (!isGuest()) {
            save("AS_WELCOME_MODE", String.valueOf(enable));
        }
        SharedPreferenceUtil.setAsWelcomeMode(enable);
    }

    public boolean getAsWelcomeMode() {
        boolean asWelcomeMode;
        if (isGuest()) {
            asWelcomeMode = SharedPreferenceUtil.isAsWelcomeModeEnabled();
        } else {
            asWelcomeMode = this.mSyncDataValue.getAsWelcomeMode();
        }
        LogUtils.i(TAG, "getAsWelcomeMode:" + asWelcomeMode);
        return asWelcomeMode;
    }

    public void setNfcKeyEnable(boolean enable) {
        LogUtils.i(TAG, "setNfcKeyEnable: " + enable);
        SharedPreferenceUtil.setNfcKeyEnable(enable);
    }

    public boolean getNfcKeyEnable() {
        return SharedPreferenceUtil.isNfcKeyEnabled();
    }

    public void setPsnSrsEnable(boolean enable) {
        LogUtils.i(TAG, "setPsnSrsEnable: " + enable);
        SharedPreferenceUtil.setPsnSrsEnable(enable);
    }

    public boolean getPsnSrsEnable() {
        return SharedPreferenceUtil.isPsnSrsEnable();
    }

    public void setTrailerHitchStatus(boolean enable) {
        LogUtils.i(TAG, "setTrailerHitchStatus: " + enable);
        SharedPreferenceUtil.setTrailerHitchStatus(enable);
    }

    public boolean getTrailerHitchStatus() {
        return SharedPreferenceUtil.getTrailerHitchStatus();
    }

    public void setEasyLoadingStatus(boolean enable) {
        LogUtils.i(TAG, "setEasyLoadingStatus: " + enable);
        SharedPreferenceUtil.setEasyLoadingStatus(enable);
    }

    public boolean getEasyLoadingStatus() {
        return SharedPreferenceUtil.getEasyLoadingStatus();
    }

    public void setAutoEasyLoadStatus(boolean enable) {
        LogUtils.i(TAG, "setAutoEasyLoadStatus: " + enable);
        SharedPreferenceUtil.setAutoEasyLoadStatus(enable);
    }

    public boolean getAutoEasyLoadStatus() {
        return SharedPreferenceUtil.getAutoEasyLoadStatus();
    }

    public boolean getAsCustomModeSwitch() {
        return SharedPreferenceUtil.isAsCustomModeEnable();
    }

    public void setAsCustomModeSwitch(boolean enable) {
        LogUtils.i(TAG, "setAsCustomModeSwitch: " + enable, false);
        SharedPreferenceUtil.setAsCustomModeEnable(enable);
    }

    public int getAsHeight() {
        return SharedPreferenceUtil.getAsHeight();
    }

    public void setAsHeight(int height) {
        LogUtils.i(TAG, "setAsHeight: " + height, false);
        SharedPreferenceUtil.setAsHeight(height);
    }

    public void setAsCampingModeStatus(boolean enable) {
        LogUtils.i(TAG, "setAsCampingModeStatus: " + enable);
        SharedPreferenceUtil.setAsCampingModeStatus(enable);
    }

    public boolean getAsCampingModeStatus() {
        return SharedPreferenceUtil.getAsCampingModeStatus();
    }

    public void setAsLocationStatus(boolean enable) {
        LogUtils.i(TAG, "setAsLocationStatus: " + enable);
        SharedPreferenceUtil.setAsLocationStatus(enable);
    }

    public boolean getAsLocationStatus() {
        return SharedPreferenceUtil.getAsLocationStatus();
    }

    public void setAsLocationControlSw(boolean enable) {
        LogUtils.i(TAG, "setAsLocationControlSw: " + enable);
        SharedPreferenceUtil.setAsLocationControlSw(enable);
    }

    public boolean getAsLocationControlSw() {
        return SharedPreferenceUtil.getAsLocationControlSw();
    }

    public void setSdcBrakeCloseCfg(int cfg) {
        LogUtils.i(TAG, "setSdcBrakeCloseCfg:" + cfg);
        this.mSyncDataValue.setSdcBrakeCloseCfg(cfg);
        if (!isGuest()) {
            save("SDC_BREAK_CLOSE", String.valueOf(cfg));
        }
        SharedPreferenceUtil.setSdcBrakeCloseCfg(cfg);
    }

    public int getSdcBrakeCloseCfg() {
        int sdcBrakeCloseCfg;
        if (isGuest()) {
            sdcBrakeCloseCfg = SharedPreferenceUtil.getSdcBrakeCloseCfg();
        } else {
            sdcBrakeCloseCfg = this.mSyncDataValue.getSdcBrakeCloseCfg();
        }
        LogUtils.i(TAG, "getSdcBrakeCloseCfg:" + sdcBrakeCloseCfg);
        return sdcBrakeCloseCfg;
    }

    public void setCmsReverseSw(boolean enable) {
        LogUtils.i(TAG, "setCmsReverseSw:" + enable);
        this.mSyncDataValue.setCmsReverseSw(enable);
        if (!isGuest()) {
            save("CMS_REVERSE_SW", String.valueOf(enable));
        }
        SharedPreferenceUtil.setCmsReverseSw(enable);
    }

    public boolean getCmsReverseSw() {
        boolean cmsReverseSw;
        if (isGuest()) {
            cmsReverseSw = SharedPreferenceUtil.getCmsReverseSw();
        } else {
            cmsReverseSw = this.mSyncDataValue.getCmsReverseSw();
        }
        LogUtils.i(TAG, "getCmsReverseSw:" + cmsReverseSw);
        return cmsReverseSw;
    }

    public void setCmsTurnSw(boolean enable) {
        LogUtils.i(TAG, "setCmsTurnSw:" + enable);
        this.mSyncDataValue.setCmsTurnSw(enable);
        if (!isGuest()) {
            save("CMS_TURN_EXTN_SW", String.valueOf(enable));
        }
        SharedPreferenceUtil.setCmsTurnSw(enable);
    }

    public boolean getCmsTurnSw() {
        boolean cmsTurnSw;
        if (isGuest()) {
            cmsTurnSw = SharedPreferenceUtil.getCmsTurnSw();
        } else {
            cmsTurnSw = this.mSyncDataValue.getCmsTurnSw();
        }
        LogUtils.i(TAG, "getCmsTurnSw:" + cmsTurnSw);
        return cmsTurnSw;
    }

    public void setCmsHighSpdSw(boolean enable) {
        LogUtils.i(TAG, "setCmsHighSpdSw:" + enable);
        this.mSyncDataValue.setCmsHighSpdSw(enable);
        if (!isGuest()) {
            save("CMS_HIGH_SPD_SW", String.valueOf(enable));
        }
        SharedPreferenceUtil.setCmsHighSpdSw(enable);
    }

    public boolean getCmsHighSpdSw() {
        boolean cmsHighSpdSw;
        if (isGuest()) {
            cmsHighSpdSw = SharedPreferenceUtil.getCmsHighSpdSw();
        } else {
            cmsHighSpdSw = this.mSyncDataValue.getCmsHighSpdSw();
        }
        LogUtils.i(TAG, "getCmsHighSpdSw:" + cmsHighSpdSw);
        return cmsHighSpdSw;
    }

    public void setCmsLowSpdSw(boolean enable) {
        LogUtils.i(TAG, "setCmsLowSpdSw:" + enable);
        this.mSyncDataValue.setCmsLowSpdSw(enable);
        if (!isGuest()) {
            save("CMS_LOW_SPD_SW", String.valueOf(enable));
        }
        SharedPreferenceUtil.setCmsLowSpdSw(enable);
    }

    public boolean getCmsLowSpdSw() {
        boolean cmsLowSpdSw;
        if (isGuest()) {
            cmsLowSpdSw = SharedPreferenceUtil.getCmsLowSpdSw();
        } else {
            cmsLowSpdSw = this.mSyncDataValue.getCmsLowSpdSw();
        }
        LogUtils.i(TAG, "getCmsLowSpdSw:" + cmsLowSpdSw);
        return cmsLowSpdSw;
    }

    public void setCmsAutoBrightSw(boolean enable) {
        LogUtils.i(TAG, "setCmsAutoBrightSw:" + enable);
        this.mSyncDataValue.setCmsAutoBrightSw(enable);
        if (!isGuest()) {
            save("CMS_AUTO_BRIGHTNESS_SW", String.valueOf(enable));
        }
        SharedPreferenceUtil.setCmsAutoBrightSw(enable);
    }

    public boolean getCmsAutoBrightSw() {
        boolean cmsAutoBrightSw;
        if (isGuest()) {
            cmsAutoBrightSw = SharedPreferenceUtil.getCmsAutoBrightSw();
        } else {
            cmsAutoBrightSw = this.mSyncDataValue.getCmsAutoBrightSw();
        }
        LogUtils.i(TAG, "getCmsAutoBrightSw:" + cmsAutoBrightSw);
        return cmsAutoBrightSw;
    }

    public void setCmsBright(int brightness) {
        LogUtils.i(TAG, "setCmsBright:" + brightness);
        this.mSyncDataValue.setCmsBright(brightness);
        if (!isGuest()) {
            save("CMS_BRIGHTNESS", String.valueOf(brightness));
        }
        SharedPreferenceUtil.setCmsBright(brightness);
    }

    public int getCmsBright() {
        int cmsBright;
        if (isGuest()) {
            cmsBright = SharedPreferenceUtil.getCmsBright();
        } else {
            cmsBright = this.mSyncDataValue.getCmsBright();
        }
        LogUtils.i(TAG, "getCmsBright:" + cmsBright);
        return cmsBright;
    }

    public void setCmsPos(String data) {
        LogUtils.i(TAG, "setCmsPos:" + data);
        this.mSyncDataValue.setCmsPos(data);
        if (!isGuest()) {
            save("CMS_POS", data);
        }
        SharedPreferenceUtil.setCmsPos(data);
    }

    public String getCmsPos() {
        String cmsPos;
        if (isGuest()) {
            cmsPos = SharedPreferenceUtil.getCmsPos();
        } else {
            cmsPos = this.mSyncDataValue.getCmsPos();
        }
        LogUtils.i(TAG, "getCmsPos:" + cmsPos);
        return cmsPos;
    }

    public void setCmsViewAngle(int viewAngle) {
        LogUtils.i(TAG, "setCmsViewAngle:" + viewAngle);
        this.mSyncDataValue.setCmsViewAngle(viewAngle);
        if (!isGuest()) {
            save("CMS_VIEW_ANGLE", String.valueOf(viewAngle));
        }
        SharedPreferenceUtil.setCmsViewAngle(viewAngle);
    }

    public int getCmsViewAngle() {
        int cmsViewAngle;
        if (isGuest()) {
            cmsViewAngle = SharedPreferenceUtil.getCmsViewAngle();
        } else {
            cmsViewAngle = this.mSyncDataValue.getCmsViewAngle();
        }
        LogUtils.i(TAG, "getCmsViewAngle:" + cmsViewAngle);
        return cmsViewAngle;
    }

    public void setCmsObjectRecognizeSw(boolean objectRecognizeSw) {
        LogUtils.i(TAG, "setCmsObjectRecognizeSw:" + objectRecognizeSw);
        this.mSyncDataValue.setCmsObjectRecognizeSw(objectRecognizeSw);
        if (!isGuest()) {
            save("CMS_OBJECT_RECOGNIZE_SW", String.valueOf(objectRecognizeSw));
        }
        SharedPreferenceUtil.setCmsObjectRecognizeSw(objectRecognizeSw);
    }

    public boolean getCmsObjectRecognizeSw() {
        boolean cmsObjectRecognizeSw;
        if (isGuest()) {
            cmsObjectRecognizeSw = SharedPreferenceUtil.getCmsObjectRecognizeSw();
        } else {
            cmsObjectRecognizeSw = this.mSyncDataValue.getCmsObjectRecognizeSw();
        }
        LogUtils.i(TAG, "getCmsViewAngle:" + cmsObjectRecognizeSw);
        return cmsObjectRecognizeSw;
    }

    public void setImsAutoVisionSw(int sw) {
        LogUtils.i(TAG, "setImsAutoVisionSw:" + sw);
        SharedPreferenceUtil.setImsAutoVisionSw(sw);
    }

    public int getImsAutoVisionSw() {
        int imsAutoVisionSw = SharedPreferenceUtil.getImsAutoVisionSw();
        LogUtils.i(TAG, "getImsAutoVisionSw:" + imsAutoVisionSw);
        return imsAutoVisionSw;
    }

    public void setImsMode(int state) {
        LogUtils.i(TAG, "setImsMode:" + state);
        SharedPreferenceUtil.setImsAutoVisionSw(state);
    }

    public int getImsMode() {
        int imsAutoVisionSw = SharedPreferenceUtil.getImsAutoVisionSw();
        LogUtils.i(TAG, "getImsMode:" + imsAutoVisionSw);
        return imsAutoVisionSw;
    }

    public boolean getParkDropdownMenuEnable() {
        return !FunctionModel.getInstance().isPGearRemindUnable();
    }

    public void setParkDropdownMenuEnable(boolean enable) {
        LogUtils.i(TAG, "setParkDropdownMenuEnable:" + enable, false);
        FunctionModel.getInstance().setPGearRemindUnable(!enable);
    }

    public boolean getEspCstSw() {
        return SharedPreferenceUtil.getEspCstSw();
    }

    public void setEspCstSw(boolean enable) {
        SharedPreferenceUtil.setEspCstSw(enable);
    }

    public int getEspBpfMode() {
        return SharedPreferenceUtil.getEspBpfMode();
    }

    public void setEspBpfMode(int mode) {
        SharedPreferenceUtil.setEspBpfMode(mode);
    }

    public void setDrvLumbControlSw(boolean enable) {
        LogUtils.i(TAG, "setDrvLumbControlSw:" + enable, false);
        SharedPreferenceUtil.setDrvLumbControlSw(enable);
    }

    public boolean getDrvLumbControlSw() {
        return SharedPreferenceUtil.getDrvLumbControlSw();
    }

    public void setPsnLumbControlSw(boolean enable) {
        LogUtils.i(TAG, "setPsnLumbControlSw:" + enable, false);
        SharedPreferenceUtil.setPsnLumbControlSw(enable);
    }

    public boolean getPsnLumbControlSw() {
        return SharedPreferenceUtil.getPsnLumbControlSw();
    }

    public void setRearLogoLightSw(boolean enable) {
        LogUtils.i(TAG, "setRearLogoLightSw:" + enable, false);
        SharedPreferenceUtil.setRearLogoLightSw(enable);
    }

    public boolean getRearLogoLightSw() {
        return SharedPreferenceUtil.getRearLogoLightSw();
    }

    void generateAllDefaultSyncData() {
        CarBaseConfig carBaseConfig = CarBaseConfig.getInstance();
        this.mSyncFullMap.clear();
        this.mSyncFullMap.put("DRIVE_MODE", new SyncData("DRIVE_MODE", String.valueOf(GlobalConstant.DEFAULT.DRIVER_MODE)));
        this.mSyncFullMap.put("XPEDAL_MODE", new SyncData("XPEDAL_MODE", String.valueOf(false)));
        this.mSyncFullMap.put("ANTISICK_MODE", new SyncData("ANTISICK_MODE", String.valueOf(false)));
        this.mSyncFullMap.put("KEY_RECYCLE_GRADE", new SyncData("KEY_RECYCLE_GRADE", String.valueOf(GlobalConstant.DEFAULT.ENERGY_RECYCLE_GRADE)));
        this.mSyncFullMap.put("STEER_MODE", new SyncData("STEER_MODE", String.valueOf(0)));
        this.mSyncFullMap.put("CHASSIS_AVH", new SyncData("CHASSIS_AVH", String.valueOf(true)));
        if (carBaseConfig.isSupportAirSuspension()) {
            this.mSyncFullMap.put("AS_WELCOME_MODE", new SyncData("AS_WELCOME_MODE", String.valueOf(false)));
        }
        if (carBaseConfig.isSupportControlSteer()) {
            this.mSyncFullMap.put("STEER_POS", new SyncData("STEER_POS", Arrays.toString(new int[]{50, 50})));
        }
        if (carBaseConfig.isSupportCdcControl()) {
            this.mSyncFullMap.put("CDC_MODE", new SyncData("CDC_MODE", String.valueOf(1)));
        }
        if (carBaseConfig.isSupportDriveModeNewArch()) {
            this.mSyncFullMap.put("SINGLEPEDAL", new SyncData("SINGLEPEDAL", String.valueOf(false)));
        }
        this.mSyncFullMap.put("WHEEL_X_KEY", new SyncData("WHEEL_X_KEY", String.valueOf(0)));
        if (carBaseConfig.isSupportDoorKeySetting()) {
            this.mSyncFullMap.put("DOOR_BOSS_KEY", new SyncData("DOOR_BOSS_KEY", String.valueOf(0)));
        }
        if (carBaseConfig.isSupportBossKey()) {
            this.mSyncFullMap.put("DOOR_BOSS_KEY_SW", new SyncData("DOOR_BOSS_KEY_SW", String.valueOf(false)));
        }
        if (carBaseConfig.isSupportWheelKeyProtect()) {
            this.mSyncFullMap.put("WHEEL_KEY_PROTECT", new SyncData("WHEEL_KEY_PROTECT", String.valueOf(true)));
        }
        if (carBaseConfig.isSupportNeutralGearProtect()) {
            this.mSyncFullMap.put("N_GEAR_PROTECT", new SyncData("N_GEAR_PROTECT", String.valueOf(true)));
        }
        this.mSyncFullMap.put("LIGHT_ME_HOME", new SyncData("LIGHT_ME_HOME", String.valueOf(true)));
        if (carBaseConfig.isSupportLightMeHomeTime()) {
            this.mSyncFullMap.put("LIGHT_ME_HOME_TIME", new SyncData("LIGHT_ME_HOME_TIME", String.valueOf(1)));
        }
        if (carBaseConfig.isSupportLampHeight()) {
            this.mSyncFullMap.put("LAMP_HEIGHT_LEVEL", new SyncData("LAMP_HEIGHT_LEVEL", String.valueOf(0)));
            this.mSyncFullMap.put("AUTO_LAMP_HEIGHT", new SyncData("AUTO_LAMP_HEIGHT", String.valueOf(true)));
        }
        if (carBaseConfig.isSupportLlu()) {
            this.mSyncFullMap.put("PARK_LAMP_B", new SyncData("PARK_LAMP_B", String.valueOf(true)));
            this.mSyncFullMap.put("LLU_SW", new SyncData("LLU_SW", String.valueOf(true)));
            this.mSyncFullMap.put("LLU_UNLOCK_SW", new SyncData("LLU_UNLOCK_SW", String.valueOf(true)));
            this.mSyncFullMap.put("LLU_LOCK_SW", new SyncData("LLU_LOCK_SW", String.valueOf(true)));
            this.mSyncFullMap.put("LLU_CHARGE_SW", new SyncData("LLU_CHARGE_SW", String.valueOf(true)));
        }
        if (carBaseConfig.isSupportAtl()) {
            this.mSyncFullMap.put("ATL_SW", new SyncData("ATL_SW", String.valueOf(true)));
            this.mSyncFullMap.put("ATL_SINGLE_COLOR", new SyncData("ATL_SINGLE_COLOR", String.valueOf(14)));
            if (carBaseConfig.isSupportFullAtl()) {
                this.mSyncFullMap.put("ATL_BRIGHT", new SyncData("ATL_BRIGHT", String.valueOf(100)));
                this.mSyncFullMap.put("ATL_EFFECT", new SyncData("ATL_EFFECT", "stable_effect"));
                this.mSyncFullMap.put("ATL_DUAL_COLOR_SW", new SyncData("ATL_DUAL_COLOR_SW", String.valueOf(false)));
                this.mSyncFullMap.put("ATL_DUAL_COLOR", new SyncData("ATL_DUAL_COLOR", null));
            }
        }
        if (carBaseConfig.isSupportShowDriveAutoLock()) {
            this.mSyncFullMap.put("DRIVE_AUTO_LOCK", new SyncData("DRIVE_AUTO_LOCK", String.valueOf(true)));
        }
        this.mSyncFullMap.put("STOP_AUTO_UNLOCK", new SyncData("STOP_AUTO_UNLOCK", String.valueOf(false)));
        this.mSyncFullMap.put("UNLOCK_RESPONSE", new SyncData("UNLOCK_RESPONSE", String.valueOf(GlobalConstant.DEFAULT.UNLOCK_RESPONSE)));
        if (carBaseConfig.isSupportDhc()) {
            this.mSyncFullMap.put("AUTO_DHC", new SyncData("AUTO_DHC", String.valueOf(GlobalConstant.DEFAULT.AUTO_DOOR_HANDLE)));
        }
        if (carBaseConfig.isSupportChildLock()) {
            this.mSyncFullMap.put("CHILD_LEFT_LOCK", new SyncData("CHILD_LEFT_LOCK", String.valueOf(false)));
            this.mSyncFullMap.put("CHILD_RIGHT_LOCK", new SyncData("CHILD_RIGHT_LOCK", String.valueOf(false)));
        }
        if (carBaseConfig.isSupportChildMode()) {
            this.mSyncFullMap.put("LEFT_DOOR_HOT_KEY", new SyncData("LEFT_DOOR_HOT_KEY", String.valueOf(false)));
            this.mSyncFullMap.put("RIGHT_DOOR_HOT_KEY", new SyncData("RIGHT_DOOR_HOT_KEY", String.valueOf(false)));
        }
        this.mSyncFullMap.put("CHAIR_POSITION", new SyncData("CHAIR_POSITION", null));
        this.mSyncFullMap.put("CHAIR_POSITION_TWO", new SyncData("CHAIR_POSITION_TWO", null));
        this.mSyncFullMap.put("CHAIR_POSITION_THREE", new SyncData("CHAIR_POSITION_THREE", null));
        this.mSyncFullMap.put("CHAIR_POS_INDEX", new SyncData("CHAIR_POS_INDEX", String.valueOf(0)));
        this.mSyncFullMap.put("WELCOME_MODE", new SyncData("WELCOME_MODE", String.valueOf(true)));
        if (carBaseConfig.isSupportRearSeatWelcomeMode()) {
            this.mSyncFullMap.put("REAR_SEAT_WELCOME_MODE", new SyncData("REAR_SEAT_WELCOME_MODE", String.valueOf(true)));
        }
        this.mSyncFullMap.put("DRV_SEAT_ESB", new SyncData("DRV_SEAT_ESB", String.valueOf(true)));
        this.mSyncFullMap.put("REAR_SEAT_WARNING", new SyncData("REAR_SEAT_WARNING", String.valueOf(true)));
        if (carBaseConfig.isSupportMirrorMemory()) {
            this.mSyncFullMap.put("MIRROR_POS", new SyncData("MIRROR_POS", null));
            this.mSyncFullMap.put("MIRROR_REVERSE", new SyncData("MIRROR_REVERSE", String.valueOf(3)));
        }
        if (carBaseConfig.isSupportMirrorDown()) {
            this.mSyncFullMap.put("MIRROR_POS", new SyncData("MIRROR_POS", null));
            this.mSyncFullMap.put("MIRROR_AUTO_DOWN", new SyncData("MIRROR_AUTO_DOWN", String.valueOf(true)));
        }
        if (BaseFeatureOption.getInstance().isSupportMirrorAutoFoldOff()) {
            this.mSyncFullMap.put(KEY_MIRROR_AUTO_FOLD, new SyncData(KEY_MIRROR_AUTO_FOLD, String.valueOf(true)));
        }
        if (carBaseConfig.isSupportSensorTrunk()) {
            this.mSyncFullMap.put("SENSOR_TRUNK_SW", new SyncData("SENSOR_TRUNK_SW", String.valueOf(true)));
        }
        if (carBaseConfig.isSupportTrunkSetPosition()) {
            this.mSyncFullMap.put("TRUNK_OPEN_POS", new SyncData("TRUNK_OPEN_POS", String.valueOf(6)));
        }
        this.mSyncFullMap.put("HIGH_SPD_CLOSE_WIN", new SyncData("HIGH_SPD_CLOSE_WIN", String.valueOf(false)));
        if (carBaseConfig.isSupportAutoCloseWin()) {
            this.mSyncFullMap.put("LOCK_CLOSE_WIN", new SyncData("LOCK_CLOSE_WIN", String.valueOf(true)));
        }
        if (carBaseConfig.isSupportWiperSenCfg()) {
            this.mSyncFullMap.put("WIPER_SENSITIVITY", new SyncData("WIPER_SENSITIVITY", String.valueOf(GlobalConstant.DEFAULT.WIPER_Sensitivity)));
        }
        if (carBaseConfig.isSupportCwc()) {
            this.mSyncFullMap.put("CWC_SW", new SyncData("CWC_SW", String.valueOf(true)));
        }
        if (BaseFeatureOption.getInstance().isSupportAvasNewMemKey()) {
            this.mSyncFullMap.put("AVAS_EFFECT_NEW", new SyncData("AVAS_EFFECT_NEW", String.valueOf(1)));
        } else {
            this.mSyncFullMap.put("AVAS_EFFECT", new SyncData("AVAS_EFFECT", String.valueOf(1)));
        }
        if (carBaseConfig.isNewAvasArch()) {
            this.mSyncFullMap.put("AVAS_SAYHI_EFFECT", new SyncData("AVAS_SAYHI_EFFECT", String.valueOf(1)));
            this.mSyncFullMap.put("AVAS_BOOT_EFFECT", new SyncData("AVAS_BOOT_EFFECT", String.valueOf(1)));
            this.mSyncFullMap.put("AVAS_BOOT_EFFECT_BEFORE_SW", new SyncData("AVAS_BOOT_EFFECT_BEFORE_SW", String.valueOf(1)));
        }
        if (carBaseConfig.isSupportMeterSetting()) {
            this.mSyncFullMap.put("METER_DEFINE_TEMPERATURE", new SyncData("METER_DEFINE_TEMPERATURE", String.valueOf(true)));
            this.mSyncFullMap.put("METER_DEFINE_WIND_POWER", new SyncData("METER_DEFINE_WIND_POWER", String.valueOf(true)));
            this.mSyncFullMap.put("METER_DEFINE_WIND_MODE", new SyncData("METER_DEFINE_WIND_MODE", String.valueOf(true)));
            this.mSyncFullMap.put("METER_DEFINE_MEDIA_SOURCE", new SyncData("METER_DEFINE_MEDIA_SOURCE", String.valueOf(true)));
            this.mSyncFullMap.put("METER_DEFINE_SCREEN_LIGHT", new SyncData("METER_DEFINE_SCREEN_LIGHT", String.valueOf(true)));
            this.mSyncFullMap.put("SPEED_LIMIT", new SyncData("SPEED_LIMIT", String.valueOf(false)));
            this.mSyncFullMap.put("SPEED_LIMIT_VALUE", new SyncData("SPEED_LIMIT_VALUE", String.valueOf(80)));
        }
        if (carBaseConfig.isSupportAutoPark()) {
            this.mSyncFullMap.put("XPILOT_AUTO_PARK_SW", new SyncData("XPILOT_AUTO_PARK_SW", String.valueOf(false)));
        }
        if (carBaseConfig.isSupportMemPark()) {
            this.mSyncFullMap.put(KEY_XPILOT_MEM_PARK_SW, new SyncData(KEY_XPILOT_MEM_PARK_SW, String.valueOf(false)));
        }
        this.mSyncFullMap.put("XPILOT_LCC", new SyncData("XPILOT_LCC", String.valueOf(false)));
        this.mSyncFullMap.put("XPILOT_ALC", new SyncData("XPILOT_ALC", String.valueOf(false)));
        this.mSyncFullMap.put(KEY_XPILOT_NRA, new SyncData(KEY_XPILOT_NRA, String.valueOf(2)));
        if (carBaseConfig.isSupportNgp()) {
            this.mSyncFullMap.put("NGP_SW", new SyncData("NGP_SW", String.valueOf(false)));
        }
        if (carBaseConfig.isSupportCNgp()) {
            this.mSyncFullMap.put("CITY_NGP_SW", new SyncData("CITY_NGP_SW", String.valueOf(false)));
        }
        if (!carBaseConfig.isNewScuArch()) {
            if (!carBaseConfig.isSupportLka()) {
                this.mSyncFullMap.put("LANE_DEPARTURE_WARNING", new SyncData("LANE_DEPARTURE_WARNING", String.valueOf(true)));
            } else {
                this.mSyncFullMap.put(KEY_XPILOT_LSS_STATE, new SyncData(KEY_XPILOT_LSS_STATE, String.valueOf(0)));
            }
            this.mSyncFullMap.put("BLIND_DETECTION_WARNING", new SyncData("BLIND_DETECTION_WARNING", String.valueOf(true)));
            this.mSyncFullMap.put("SIDE_REVERSING_WARNING", new SyncData("SIDE_REVERSING_WARNING", String.valueOf(true)));
            if (carBaseConfig.isSupportIslc()) {
                this.mSyncFullMap.put("SMART_SPEED_LIMIT", new SyncData("SMART_SPEED_LIMIT", String.valueOf(true)));
            }
            this.mSyncFullMap.put("BRAKE_WARNING", new SyncData("BRAKE_WARNING", String.valueOf(GlobalConstant.DEFAULT.EMERGENCY_BREAK_WARNING)));
        }
        if (carBaseConfig.isSupportSdc()) {
            this.mSyncFullMap.put("SDC_BREAK_CLOSE", new SyncData("SDC_BREAK_CLOSE", String.valueOf(0)));
        }
        if (carBaseConfig.isSupportCms()) {
            this.mSyncFullMap.put("CMS_AUTO_BRIGHTNESS_SW", new SyncData("CMS_AUTO_BRIGHTNESS_SW", String.valueOf(true)));
            this.mSyncFullMap.put("CMS_BRIGHTNESS", new SyncData("CMS_BRIGHTNESS", String.valueOf(50)));
            this.mSyncFullMap.put("CMS_HIGH_SPD_SW", new SyncData("CMS_HIGH_SPD_SW", String.valueOf(true)));
            this.mSyncFullMap.put("CMS_LOW_SPD_SW", new SyncData("CMS_LOW_SPD_SW", String.valueOf(true)));
            this.mSyncFullMap.put("CMS_REVERSE_SW", new SyncData("CMS_REVERSE_SW", String.valueOf(true)));
            this.mSyncFullMap.put("CMS_TURN_EXTN_SW", new SyncData("CMS_TURN_EXTN_SW", String.valueOf(true)));
            this.mSyncFullMap.put("CMS_OBJECT_RECOGNIZE_SW", new SyncData("CMS_OBJECT_RECOGNIZE_SW", String.valueOf(true)));
            this.mSyncFullMap.put("CMS_VIEW_RECOVERY_SW", new SyncData("CMS_VIEW_RECOVERY_SW", String.valueOf(true)));
            this.mSyncFullMap.put("CMS_POS", new SyncData("CMS_POS", null));
            this.mSyncFullMap.put("CMS_VIEW_ANGLE", new SyncData("CMS_VIEW_ANGLE", String.valueOf(1)));
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void save(String key, String value) {
        LogUtils.i(TAG, "save key = " + key + ", value = " + value);
        saveToSync(key, value);
    }

    public void setGroupChangeListener(GroupChangeListener listener) {
        this.mGroupChangeListener = listener;
    }

    public void setDataSyncChangeListener(DataSyncChangeListener listener) {
        this.mDataSyncChangeListener = listener;
    }
}
