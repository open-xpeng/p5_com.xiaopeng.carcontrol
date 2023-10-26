package com.xiaopeng.carcontrol.model;

import android.content.SharedPreferences;
import android.text.TextUtils;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.GlobalConstant;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.speech.common.SpeechConstant;

/* loaded from: classes2.dex */
public class FunctionModel {
    private static final int DEF_UNLOCK_REQ_SRC = 0;
    public static final String KEY_HVAC_AUTO_DEFOG_SWITCH = "auto_defog_work_switch";
    private static final String PREF_DRIVER_GENDER_INTEGER = "pref_driver_gender_integer";
    private static final String PREF_DRIVER_HEIGHT_INTEGER = "pref_driver_height_integer";
    private static final String PREF_DRIVER_WEIGHT_INTEGER = "pref_driver_weight_integer";
    private static final String PREF_IS_P_GEAR_REMIND_UNABLE = "pref_is_p_gear_remind_unable";
    private static final String PREF_KEY_CORRECT_RMIRROR_POS_TS = "correct_rmirror_pos_ts";
    private static final String PREF_KEY_KEEP_DRV_SEAT_TIME = "drv_keep_seat_menu_time";
    private static final String PREF_KEY_KEEP_PSN_SEAT_TIME = "psn_keep_seat_menu_time";
    private static final String PREF_KEY_REQUEST_CAR_PLATE_TS = "request_car_plate_ts";
    private static final String PREF_KEY_SDC_SHOW_NS_TS = "sdc_show_narrow_space_tts";
    private static final String PREF_NETWORK_DEV = "pref_network_env";
    private static final String PREF_NEW_STEER_KEY_BOOL = "pref_new_key";
    private static final String PREF_UNLOCK_REQ_SRC = "unlock_req_src";
    private static final String PREF_UPLOAD_RESUME_BI_TIME = "upload_resume_bi_time";
    private static final String SP_FILE_NAME = "function_sp";
    private static final String SP_KEY_AIR_PROTECT_TS = "air_protect_ts";
    private static final String SP_KEY_CHARGE_PORT_RETRY_TIME = "charge_port_retry_time";
    private static final String SP_KEY_DRIVE_MODE_CHANGED_BY_USER_BOOL = "drive_mode_changed_by_user";
    private static final String SP_KEY_HIGH_SPD_CLOSE_WIN_TS = "smart_win_ts";
    private static final String SP_KEY_HVAC_AIR_PURGE_MODE = "hvac_air_purge_mode";
    private static final String SP_KEY_HVAC_DEODORANT = "hvac_deodorant";
    private static final String SP_KEY_HVAC_DEODORANT_COUNTDOWN = "hvac_deodorant_countdown";
    private static final String SP_KEY_HVAC_DEODORANT_TIME = "hvac_deodorant_time";
    private static final String SP_KEY_HVAC_DRV_TEMP_SYNC_MODE = "hvac_drv_temp_sync_mode";
    private static final String SP_KEY_HVAC_DRV_TEMP_SYNC_TIME = "hvac_drv_temp_sync_time";
    private static final String SP_KEY_HVAC_PURGE_CLICK_TIME = "hvac_purge_click_time";
    private static final String SP_KEY_HVAC_RAPID_COOLING = "hvac_rapid_cooling";
    private static final String SP_KEY_HVAC_RAPID_COOLING_COUNTDOWN = "hvac_rapid_cooling_countdown";
    private static final String SP_KEY_HVAC_RAPID_COOLING_TIME = "hvac_rapid_cooling_time";
    private static final String SP_KEY_HVAC_RAPID_HEAT = "hvac_rapid_heat";
    private static final String SP_KEY_HVAC_RAPID_HEAT_COUNTDOWN = "hvac_rapid_heat_count_down";
    private static final String SP_KEY_HVAC_RAPID_HEAT_TIME = "hvac_rapid_heat_time";
    private static final String SP_KEY_HVAC_SMART_AC_PTC = "hvac_smart_ac_ptc";
    private static final String SP_KEY_HVAC_SMART_AQS = "hvac_smart_aqs";
    private static final String SP_KEY_HVAC_SMART_AUTO = "hvac_smart_auto";
    private static final String SP_KEY_HVAC_SMART_BLOW_MODE = "hvac_smart_blow_mode";
    private static final String SP_KEY_HVAC_SMART_CIRCULATION = "hvac_smart_circulation";
    private static final String SP_KEY_HVAC_SMART_DRV_SEAT_HEAT = "hvac_smart_drv_seat_heat";
    private static final String SP_KEY_HVAC_SMART_DRV_SEAT_VENT = "hvac_smart_drv_seat_vent";
    private static final String SP_KEY_HVAC_SMART_DRV_TEMP = "hvac_smart_drv_temp";
    private static final String SP_KEY_HVAC_SMART_DRV_VENT_MODE = "hvac_smart_drv_vent_mode";
    private static final String SP_KEY_HVAC_SMART_DRV_VENT_POSITION = "hvac_smart_drv_vent_position";
    private static final String SP_KEY_HVAC_SMART_ECON = "hvac_smart_econ";
    private static final String SP_KEY_HVAC_SMART_FAN_SPEED = "hvac_smart_fan_speed";
    private static final String SP_KEY_HVAC_SMART_MODE_CLICK_TIME = "hvac_smart_mode_click_time";
    private static final String SP_KEY_HVAC_SMART_PSN_SEAT_HEAT = "hvac_smart_psn_seat_heat";
    private static final String SP_KEY_HVAC_SMART_PSN_SEAT_VENT = "hvac_smart_psn_seat_vent";
    private static final String SP_KEY_HVAC_SMART_PSN_TEMP = "hvac_smart_psn_temp";
    private static final String SP_KEY_HVAC_SMART_PSN_VENT_MODE = "hvac_smart_psn_vent_mode";
    private static final String SP_KEY_HVAC_SMART_PSN_VENT_POSITION = "hvac_smart_psn_vent_position";
    private static final String SP_KEY_HVAC_SMART_QUALITY_PURGE = "hvac_smart_quality_purge";
    private static final String SP_KEY_HVAC_SMART_RL_SEAT_HEAT = "hvac_smart_rl_seat_heat";
    private static final String SP_KEY_HVAC_SMART_RR_SEAT_HEAT = "hvac_smart_rr_seat_heat";
    private static final String SP_KEY_HVAC_SMART_SINGLE_ACTIVE = "hvac_smart_single_active";
    private static final String SP_KEY_HVAC_SMART_SINGLE_MODE = "hvac_smart_single_mode";
    private static final String SP_KEY_HVAC_SMART_STEER_HEAT = "hvac_smart_steer_heat";
    private static final String SP_KEY_IGON_NANO_TS = "igon_nano_ts";
    private static final String SP_KEY_IGON_TS = "igon_ts";
    private static final String SP_KEY_LOW_POWER_100_TS = "low_power_100_ts";
    private static final String SP_KEY_LOW_POWER_30_TS = "low_power_30_ts";
    private static final String SP_KEY_LOW_POWER_60_TS = "low_power_60_ts";
    private static final String SP_KEY_OFFGAS_PROTEXT_TC = "offgas_protect_ts";
    private static final String SP_KEY_REMIND_WARNING_TS = "remind_warning_ts";
    private static final String SP_KEY_SMART_HVAC_TS = "smart_hvac_ts";
    private static final String SP_KEY_TELESCOPE_REMIND_TS = "telescope_remind_ts";
    private static final String SP_KEY_WIN_LOCK_TIP_TS = "win_lock_tip_ts";
    private static final String TAG = "FunctionModel";
    private long mIgonNanoTime;
    private long mIgonTime;
    private boolean mInDrvSeatMenuShowTime;
    private boolean mInPsnSeatMenuShowTime;
    private final SharedPreferences mSp;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class LazyHolder {
        private static FunctionModel sInstance = new FunctionModel();

        private LazyHolder() {
        }
    }

    public static FunctionModel getInstance() {
        return LazyHolder.sInstance;
    }

    private FunctionModel() {
        SharedPreferences sharedPreferences = App.getInstance().getSharedPreferences(SP_FILE_NAME, 0);
        this.mSp = sharedPreferences;
        this.mIgonTime = sharedPreferences.getLong(SP_KEY_IGON_TS, 0L);
        this.mIgonNanoTime = sharedPreferences.getLong(SP_KEY_IGON_NANO_TS, 0L);
        if (this.mIgonTime == 0) {
            LogUtils.d(TAG, "IGON timestamp is null, use current time", false);
            setIgonTime(System.currentTimeMillis());
        }
        if (this.mIgonNanoTime == 0) {
            setIgonNanoTime(System.nanoTime());
        }
        this.mInDrvSeatMenuShowTime = true;
        this.mInPsnSeatMenuShowTime = true;
    }

    public void setIgonTime(long timestamp) {
        this.mIgonTime = timestamp;
        this.mSp.edit().putLong(SP_KEY_IGON_TS, timestamp).apply();
    }

    public void setIgonNanoTime(long timestamp) {
        this.mIgonNanoTime = timestamp;
        this.mSp.edit().putLong(SP_KEY_IGON_NANO_TS, timestamp).apply();
    }

    public void setHighSpdCloseWinTs(long timestamp) {
        this.mSp.edit().putLong(SP_KEY_HIGH_SPD_CLOSE_WIN_TS, timestamp).apply();
    }

    public boolean isHighSpdCloseWinFuncAllowed() {
        long j = this.mSp.getLong(SP_KEY_HIGH_SPD_CLOSE_WIN_TS, 0L);
        LogUtils.d(TAG, "isHighSpdCloseWinFuncAllowed igon: " + this.mIgonTime + ", last smart win timestamp: " + j, false);
        return this.mIgonTime >= j;
    }

    public void setWinLockTipTs(long timestamp) {
        this.mSp.edit().putLong(SP_KEY_WIN_LOCK_TIP_TS, timestamp).apply();
    }

    public boolean isWinLockTipAllowed() {
        long j = this.mSp.getLong(SP_KEY_WIN_LOCK_TIP_TS, 0L);
        LogUtils.d(TAG, "isWinLockTipAllowed igon: " + this.mIgonTime + ", last win lock tip timestamp: " + j, false);
        return this.mIgonTime >= j;
    }

    public void setAirProtectTs(long timestamp) {
        this.mSp.edit().putLong(SP_KEY_AIR_PROTECT_TS, timestamp).apply();
    }

    public boolean isAirProtectFuncAllowed() {
        long j = this.mSp.getLong(SP_KEY_AIR_PROTECT_TS, 0L);
        if (j == 0) {
            j = this.mIgonNanoTime;
        }
        LogUtils.d(TAG, "isAirProtectFuncAllowed igon: " + this.mIgonNanoTime + ", last air protect timestamp: " + j, false);
        return this.mIgonNanoTime - j >= 0;
    }

    public void setOffgasProtectTs(long timestamp) {
        this.mSp.edit().putLong(SP_KEY_OFFGAS_PROTEXT_TC, timestamp).apply();
    }

    public boolean isOffgasProtectFuncAllowed() {
        long j = this.mSp.getLong(SP_KEY_OFFGAS_PROTEXT_TC, 0L);
        LogUtils.d(TAG, "isOffgasProtectFuncAllowed:" + this.mIgonTime + ", last offset protect timestamp:" + j, false);
        return this.mIgonTime >= j;
    }

    public void setSmartHvacTs(long timestamp) {
        this.mSp.edit().putLong(SP_KEY_SMART_HVAC_TS, timestamp).apply();
    }

    public boolean isSmartHvacFuncAllowed() {
        long j = this.mSp.getLong(SP_KEY_SMART_HVAC_TS, 0L);
        LogUtils.d(TAG, "isSmartHvacFuncAllowed igon: " + this.mIgonTime + ", last smart hvac timestamp: " + j, false);
        return this.mIgonTime >= j;
    }

    public void setLowPower30Ts(long timestamp) {
        setLowPowerTs(SP_KEY_LOW_POWER_30_TS, timestamp);
    }

    public boolean isFuncLowPower30Allowed() {
        return isFuncLowPowerAllowed(SP_KEY_LOW_POWER_30_TS);
    }

    public void setLowPower60Ts(long timestamp) {
        setLowPowerTs(SP_KEY_LOW_POWER_60_TS, timestamp);
    }

    public boolean isFuncLowPower60Allowed() {
        return isFuncLowPowerAllowed(SP_KEY_LOW_POWER_60_TS);
    }

    public void setLowPower100Ts(long timestamp) {
        setLowPowerTs(SP_KEY_LOW_POWER_100_TS, timestamp);
    }

    public boolean isFuncLowPower100Allowed() {
        return isFuncLowPowerAllowed(SP_KEY_LOW_POWER_100_TS);
    }

    private void setLowPowerTs(String type, long timestamp) {
        this.mSp.edit().putLong(type, timestamp).apply();
    }

    private boolean isFuncLowPowerAllowed(String type) {
        long j = this.mSp.getLong(type, 0L);
        LogUtils.d(TAG, "isFuncLowPowerAllowed igon: " + this.mIgonTime + ", last low power type: " + type + " with timestamp: " + j, false);
        return this.mIgonTime >= j;
    }

    public boolean isDriveModeChangedByUser() {
        boolean z = this.mSp.getBoolean(SP_KEY_DRIVE_MODE_CHANGED_BY_USER_BOOL, false);
        LogUtils.d(TAG, "isDriveModeChangedByUser : " + z, false);
        return z;
    }

    public void setDriveModeChangedByUser(boolean changedByUser) {
        LogUtils.d(TAG, "setDriveModeChangedByUser = " + changedByUser);
        this.mSp.edit().putBoolean(SP_KEY_DRIVE_MODE_CHANGED_BY_USER_BOOL, changedByUser).apply();
    }

    public int getLastDriveMode() {
        return this.mSp.getInt(GlobalConstant.PREFS.PREF_LAST_DRIVER_MODE_INT, GlobalConstant.DEFAULT.LAST_DRIVER_MODE);
    }

    public void setLastDriveMode(int currentMode) {
        this.mSp.edit().putInt(GlobalConstant.PREFS.PREF_LAST_DRIVER_MODE_INT, currentMode).apply();
    }

    public boolean getLastEspMode() {
        return this.mSp.getBoolean(GlobalConstant.PREFS.PREF_LAST_ESP_MODE_BOOL, true);
    }

    public void setLastEspMode(boolean on) {
        this.mSp.edit().putBoolean(GlobalConstant.PREFS.PREF_LAST_ESP_MODE_BOOL, on).apply();
    }

    public int getSnowModeEnergyCache() {
        return this.mSp.getInt(GlobalConstant.PREFS.PREF_SNOW_MODE_ENERGY_CACHE, -1);
    }

    public void setSnowModeEnergyCache(int grade) {
        this.mSp.edit().putInt(GlobalConstant.PREFS.PREF_SNOW_MODE_ENERGY_CACHE, grade).apply();
    }

    public boolean isHvacRapidCoolingEnable() {
        return this.mSp.getBoolean(SP_KEY_HVAC_RAPID_COOLING, false);
    }

    public void setHvacRapidCoolingEnable(boolean enable) {
        this.mSp.edit().putBoolean(SP_KEY_HVAC_RAPID_COOLING, enable).apply();
    }

    public long getHvacRapidCoolingTime() {
        return this.mSp.getLong(SP_KEY_HVAC_RAPID_COOLING_TIME, 0L);
    }

    public void setHvacRapidCoolingTime(long time) {
        this.mSp.edit().putLong(SP_KEY_HVAC_RAPID_COOLING_TIME, time).apply();
    }

    public int getHvacRapidCoolingCountdown() {
        return this.mSp.getInt(SP_KEY_HVAC_RAPID_COOLING_COUNTDOWN, 0);
    }

    public void setHvacRapidCoolingCountdown(int countdown) {
        this.mSp.edit().putInt(SP_KEY_HVAC_RAPID_COOLING_COUNTDOWN, countdown).apply();
    }

    public boolean isHvacDeodorantEnable() {
        return this.mSp.getBoolean(SP_KEY_HVAC_DEODORANT, false);
    }

    public void setHvacDeodorantEnable(boolean enable) {
        this.mSp.edit().putBoolean(SP_KEY_HVAC_DEODORANT, enable).apply();
    }

    public long getHvacDeodorantTime() {
        return this.mSp.getLong(SP_KEY_HVAC_DEODORANT_TIME, 0L);
    }

    public void setHvacDeodorantTime(long time) {
        this.mSp.edit().putLong(SP_KEY_HVAC_DEODORANT_TIME, time).apply();
    }

    public int getHvacDeodorantCountdown() {
        return this.mSp.getInt(SP_KEY_HVAC_DEODORANT_COUNTDOWN, 0);
    }

    public void setHvacDeodorantCountdown(int countdown) {
        this.mSp.edit().putInt(SP_KEY_HVAC_DEODORANT_COUNTDOWN, countdown).apply();
    }

    public boolean isHvacRapidHeatEnable() {
        return this.mSp.getBoolean(SP_KEY_HVAC_RAPID_HEAT, false);
    }

    public void setHvacRapidHeatEnable(boolean enable) {
        this.mSp.edit().putBoolean(SP_KEY_HVAC_RAPID_HEAT, enable).apply();
    }

    public long getHvacRapidHeatTime() {
        return this.mSp.getLong(SP_KEY_HVAC_RAPID_HEAT_TIME, 0L);
    }

    public void setHvacRapidHeatTime(long time) {
        this.mSp.edit().putLong(SP_KEY_HVAC_RAPID_HEAT_TIME, time).apply();
    }

    public int getHvacRapidHeatCountdown() {
        return this.mSp.getInt(SP_KEY_HVAC_RAPID_HEAT_COUNTDOWN, 0);
    }

    public void setHvacRapidHeatCountdown(int time) {
        this.mSp.edit().putInt(SP_KEY_HVAC_RAPID_HEAT_COUNTDOWN, time).apply();
    }

    public void setHvacPurgeClickTime(long time) {
        this.mSp.edit().putLong(SP_KEY_HVAC_PURGE_CLICK_TIME, time).apply();
    }

    public long getHvacPurgeClickTime() {
        return this.mSp.getLong(SP_KEY_HVAC_PURGE_CLICK_TIME, 0L);
    }

    public void setHvacSmartModeClickTime(long time) {
        this.mSp.edit().putLong(SP_KEY_HVAC_SMART_MODE_CLICK_TIME, time).apply();
    }

    public long getHvacSmartModeClickTime() {
        return this.mSp.getLong(SP_KEY_HVAC_SMART_MODE_CLICK_TIME, 0L);
    }

    public boolean isHvacSmartAutoEnable() {
        return this.mSp.getBoolean(SP_KEY_HVAC_SMART_AUTO, false);
    }

    public void setHvacSmartAutoEnable(boolean enable) {
        this.mSp.edit().putBoolean(SP_KEY_HVAC_SMART_AUTO, enable).apply();
    }

    public int getHvacSmartAcPtcMode() {
        return this.mSp.getInt(SP_KEY_HVAC_SMART_AC_PTC, 0);
    }

    public void setHvacSmartAcPtcMode(int mode) {
        this.mSp.edit().putInt(SP_KEY_HVAC_SMART_AC_PTC, mode).apply();
    }

    public float getHvacSmartDrvTemp() {
        return this.mSp.getFloat(SP_KEY_HVAC_SMART_DRV_TEMP, 0.0f);
    }

    public void setHvacSmartDrvTemp(float temp) {
        this.mSp.edit().putFloat(SP_KEY_HVAC_SMART_DRV_TEMP, temp).apply();
    }

    public float getHvacSmartPsnTemp() {
        return this.mSp.getFloat(SP_KEY_HVAC_SMART_PSN_TEMP, 0.0f);
    }

    public void setHvacSmartPsnTemp(float temp) {
        this.mSp.edit().putFloat(SP_KEY_HVAC_SMART_PSN_TEMP, temp).apply();
    }

    public int getHvacSmartFanSpeed() {
        return this.mSp.getInt(SP_KEY_HVAC_SMART_FAN_SPEED, 1);
    }

    public void setHvacSmartFanSpeed(int speed) {
        this.mSp.edit().putInt(SP_KEY_HVAC_SMART_FAN_SPEED, speed).apply();
    }

    public int getHvacSmartEconMode() {
        return this.mSp.getInt(SP_KEY_HVAC_SMART_ECON, 0);
    }

    public void setHvacSmartEconMode(int mode) {
        this.mSp.edit().putInt(SP_KEY_HVAC_SMART_ECON, mode).apply();
    }

    public int getHvacSmartBlowMode() {
        return this.mSp.getInt(SP_KEY_HVAC_SMART_BLOW_MODE, 0);
    }

    public void setHvacSmartBlowMode(int mode) {
        this.mSp.edit().putInt(SP_KEY_HVAC_SMART_BLOW_MODE, mode).apply();
    }

    public int getHvacSmartCirculation() {
        return this.mSp.getInt(SP_KEY_HVAC_SMART_CIRCULATION, 0);
    }

    public void setHvacSmartCirculation(int mode) {
        this.mSp.edit().putInt(SP_KEY_HVAC_SMART_CIRCULATION, mode).apply();
    }

    public int getHvacSmartAqsMode() {
        return this.mSp.getInt(SP_KEY_HVAC_SMART_AQS, 0);
    }

    public void setHvacSmartAqsMode(int mode) {
        this.mSp.edit().putInt(SP_KEY_HVAC_SMART_AQS, mode).apply();
    }

    public int getHvacSmartDrvVentMode() {
        return this.mSp.getInt(SP_KEY_HVAC_SMART_DRV_VENT_MODE, 0);
    }

    public void setHvacSmartDrvVentMode(int mode) {
        this.mSp.edit().putInt(SP_KEY_HVAC_SMART_DRV_VENT_MODE, mode).apply();
    }

    public String getHvacSmartDrvVentPosition() {
        return this.mSp.getString(SP_KEY_HVAC_SMART_DRV_VENT_POSITION, "");
    }

    public void setHvacSmartDrvVentPosition(String position) {
        this.mSp.edit().putString(SP_KEY_HVAC_SMART_DRV_VENT_POSITION, position).apply();
    }

    public int getHvacSmartPsnVentMode() {
        return this.mSp.getInt(SP_KEY_HVAC_SMART_PSN_VENT_MODE, 0);
    }

    public void setHvacSmartPsnVentMode(int mode) {
        this.mSp.edit().putInt(SP_KEY_HVAC_SMART_PSN_VENT_MODE, mode).apply();
    }

    public String getHvacSmartPsnVentPosition() {
        return this.mSp.getString(SP_KEY_HVAC_SMART_PSN_VENT_POSITION, "");
    }

    public void setHvacSmartPsnVentPosition(String position) {
        this.mSp.edit().putString(SP_KEY_HVAC_SMART_PSN_VENT_POSITION, position).apply();
    }

    public boolean getHvacSmartQualityPurge() {
        return this.mSp.getBoolean(SP_KEY_HVAC_SMART_QUALITY_PURGE, false);
    }

    public void setHvacSmartQualityPurge(boolean enable) {
        this.mSp.edit().putBoolean(SP_KEY_HVAC_SMART_QUALITY_PURGE, enable).apply();
    }

    public void setHvacSingleMode(boolean enable) {
        this.mSp.edit().putBoolean(SP_KEY_HVAC_SMART_SINGLE_MODE, enable).apply();
    }

    public boolean isHvacSingleMode() {
        return this.mSp.getBoolean(SP_KEY_HVAC_SMART_SINGLE_MODE, true);
    }

    public void setHvacSingleModeActive(boolean enable) {
        this.mSp.edit().putBoolean(SP_KEY_HVAC_SMART_SINGLE_ACTIVE, enable).apply();
    }

    public boolean isHvacSingleModeActive() {
        return this.mSp.getBoolean(SP_KEY_HVAC_SMART_SINGLE_ACTIVE, false);
    }

    public void setHvacSmartDrvSeatVent(int level) {
        this.mSp.edit().putInt(SP_KEY_HVAC_SMART_DRV_SEAT_VENT, level).apply();
    }

    public int getHvacSmartDrvSeatVent() {
        return this.mSp.getInt(SP_KEY_HVAC_SMART_DRV_SEAT_VENT, 0);
    }

    public void setHvacSmartPsnSeatVent(int level) {
        this.mSp.edit().putInt(SP_KEY_HVAC_SMART_PSN_SEAT_VENT, level).apply();
    }

    public int getHvacSmartPsnSeatVent() {
        return this.mSp.getInt(SP_KEY_HVAC_SMART_PSN_SEAT_VENT, 0);
    }

    public void setHvacSmartDrvSeatHeat(int level) {
        this.mSp.edit().putInt(SP_KEY_HVAC_SMART_DRV_SEAT_HEAT, level).apply();
    }

    public int getHvacSmartDrvSeatHeat() {
        return this.mSp.getInt(SP_KEY_HVAC_SMART_DRV_SEAT_HEAT, 0);
    }

    public void setHvacSmartPsnSeatHeat(int level) {
        this.mSp.edit().putInt(SP_KEY_HVAC_SMART_PSN_SEAT_HEAT, level).apply();
    }

    public int getHvacSmartPsnSeatHeat() {
        return this.mSp.getInt(SP_KEY_HVAC_SMART_PSN_SEAT_HEAT, 0);
    }

    public void setHvacSmartRlSeatHeat(int level) {
        this.mSp.edit().putInt(SP_KEY_HVAC_SMART_RL_SEAT_HEAT, level).apply();
    }

    public int getHvacSmartRlSeatHeat() {
        return this.mSp.getInt(SP_KEY_HVAC_SMART_RL_SEAT_HEAT, 0);
    }

    public void setHvacSmartRrSeatHeat(int level) {
        this.mSp.edit().putInt(SP_KEY_HVAC_SMART_RR_SEAT_HEAT, level).apply();
    }

    public int getHvacSmartRrSeatHeat() {
        return this.mSp.getInt(SP_KEY_HVAC_SMART_RR_SEAT_HEAT, 0);
    }

    public void setHvacSmartSteerHeat(int level) {
        this.mSp.edit().putInt(SP_KEY_HVAC_SMART_STEER_HEAT, level).apply();
    }

    public int getHvacSmartSteerHeat() {
        return this.mSp.getInt(SP_KEY_HVAC_SMART_STEER_HEAT, 0);
    }

    public void setHvacDrvTempSyncTime() {
        this.mSp.edit().putLong(SP_KEY_HVAC_DRV_TEMP_SYNC_TIME, System.currentTimeMillis()).apply();
    }

    public boolean isHvacDrvTempSyncAllowed() {
        long j = this.mSp.getLong(SP_KEY_HVAC_DRV_TEMP_SYNC_TIME, 0L);
        LogUtils.d(TAG, "isHvacSingleTempModeAllowed:" + this.mIgonTime + ",saveTime:" + j, false);
        return this.mIgonTime >= j;
    }

    public void setHvacDrvTempSyncMode(boolean enable) {
        this.mSp.edit().putBoolean(SP_KEY_HVAC_DRV_TEMP_SYNC_MODE, enable).apply();
    }

    public boolean isHvacDrvTempSyncMode() {
        return this.mSp.getBoolean(SP_KEY_HVAC_DRV_TEMP_SYNC_MODE, false);
    }

    public void setHvacAirPurgeMode(boolean enable) {
        this.mSp.edit().putBoolean("hvac_air_purge_mode", enable).apply();
    }

    public boolean isHvacAirPurgeEnable() {
        return this.mSp.getBoolean("hvac_air_purge_mode", false);
    }

    public void setSmartSeatParmData(int gender, int height, int weight) {
        LogUtils.d(TAG, "setSmartSeatParmData gender=" + gender + " height=" + height + " weight=" + weight);
        this.mSp.edit().putInt(PREF_DRIVER_GENDER_INTEGER, gender).apply();
        this.mSp.edit().putInt(PREF_DRIVER_HEIGHT_INTEGER, height).apply();
        this.mSp.edit().putInt(PREF_DRIVER_WEIGHT_INTEGER, weight).apply();
    }

    public int getDriverWeight() {
        return this.mSp.getInt(PREF_DRIVER_WEIGHT_INTEGER, 100);
    }

    public int getDriverHeight() {
        return this.mSp.getInt(PREF_DRIVER_HEIGHT_INTEGER, SpeechConstant.SoundLocation.MAX_ANGLE);
    }

    public int getDriverGender() {
        return this.mSp.getInt(PREF_DRIVER_GENDER_INTEGER, 0);
    }

    public int getEnv() {
        return this.mSp.getInt(PREF_NETWORK_DEV, 3);
    }

    public void setChargePortRetryTime(int time) {
        this.mSp.edit().putInt(SP_KEY_CHARGE_PORT_RETRY_TIME, time).apply();
    }

    public int getChargePortRetryEnable() {
        return this.mSp.getInt(SP_KEY_CHARGE_PORT_RETRY_TIME, 0);
    }

    public boolean isNewSteerKey() {
        boolean z = this.mSp.getBoolean(PREF_NEW_STEER_KEY_BOOL, false);
        LogUtils.d(TAG, "Current wheel firmware version: " + z, false);
        return z;
    }

    public void setNewSteerKey() {
        LogUtils.d(TAG, "Switch to new wheel firmware version", false);
        this.mSp.edit().putBoolean(PREF_NEW_STEER_KEY_BOOL, true).apply();
    }

    public boolean isPGearRemindUnable() {
        boolean z = this.mSp.getBoolean(PREF_IS_P_GEAR_REMIND_UNABLE, false);
        LogUtils.d(TAG, "isPGearRemindUnable=" + z, false);
        return z;
    }

    public void setPGearRemindUnable() {
        setPGearRemindUnable(true);
    }

    public void setPGearRemindUnable(boolean isUnable) {
        this.mSp.edit().putBoolean(PREF_IS_P_GEAR_REMIND_UNABLE, isUnable).apply();
    }

    public int getUnlockReqSrc() {
        return this.mSp.getInt(PREF_UNLOCK_REQ_SRC, 0);
    }

    public void setUnlockReqSrc(int reqSrc) {
        this.mSp.edit().putInt(PREF_UNLOCK_REQ_SRC, reqSrc).apply();
    }

    public long getUploadResumeBITime() {
        return this.mSp.getLong(PREF_UPLOAD_RESUME_BI_TIME, 0L);
    }

    public void setUploadResumeBITime(long time) {
        this.mSp.edit().putLong(PREF_UPLOAD_RESUME_BI_TIME, time).apply();
    }

    public void setRequestCarPlateTs(long timestamp) {
        this.mSp.edit().putLong(PREF_KEY_REQUEST_CAR_PLATE_TS, timestamp).apply();
    }

    public boolean isRequestCarPlateAllowed() {
        long j = this.mSp.getLong(PREF_KEY_REQUEST_CAR_PLATE_TS, 0L);
        LogUtils.i(TAG, "isRequestCarPlateAllowed igon: " + this.mIgonTime + ", last request car plate timestamp: " + j, false);
        return this.mIgonTime >= j;
    }

    public void setRearMirrorPosCorrectTs(long timestamp) {
        this.mSp.edit().putLong(PREF_KEY_CORRECT_RMIRROR_POS_TS, timestamp).apply();
    }

    public boolean isCorrectRMirrorPosAllowed() {
        long j = this.mSp.getLong(PREF_KEY_CORRECT_RMIRROR_POS_TS, 0L);
        LogUtils.i(TAG, "isCorrectRMirrorPosAllowed igon: " + this.mIgonTime + ", last correct rear mirror pos timestamp: " + j, false);
        return this.mIgonTime >= j;
    }

    public void setShowSdcNarrowSpaceTs(long timestamp) {
        this.mSp.edit().putLong(PREF_KEY_SDC_SHOW_NS_TS, timestamp).apply();
    }

    public boolean isShowSdcNarrowSpaceAllowed() {
        long j = this.mSp.getLong(PREF_KEY_SDC_SHOW_NS_TS, 0L);
        LogUtils.i(TAG, "isShowSdcNarrowSpaceAllowed igon: " + this.mIgonTime + ", last show narrow space tts timestamp: " + j, false);
        return this.mIgonTime >= j;
    }

    public void setRemindWarningTs(long timestamp) {
        this.mSp.edit().putLong(SP_KEY_REMIND_WARNING_TS, timestamp).apply();
    }

    public boolean isRemindWarningAllowed() {
        long j = this.mSp.getLong(SP_KEY_REMIND_WARNING_TS, 0L);
        LogUtils.i(TAG, "isRemindWarningAllowed igon: " + this.mIgonTime + ", last remind warning timestamp: " + j, false);
        return this.mIgonTime >= j;
    }

    public void setTelescopeRemindTs(long timestamp) {
        this.mSp.edit().putLong(SP_KEY_TELESCOPE_REMIND_TS, timestamp).apply();
    }

    public boolean isTelescopeRemindAllowed() {
        long j = this.mSp.getLong(SP_KEY_TELESCOPE_REMIND_TS, 0L);
        if (j == 0) {
            j = this.mIgonNanoTime;
        }
        LogUtils.i(TAG, "isTelescopeRemindAllowed igon: " + this.mIgonNanoTime + ", last remind timestamp: " + j, false);
        return this.mIgonNanoTime >= j;
    }

    public void setAutoDefogSwitch(boolean state) {
        this.mSp.edit().putString(KEY_HVAC_AUTO_DEFOG_SWITCH, Boolean.toString(state)).apply();
    }

    public boolean isDefaultAutoDefogSwitch() {
        String string = this.mSp.getString(KEY_HVAC_AUTO_DEFOG_SWITCH, null);
        LogUtils.i(TAG, "isDefaultAutoDefogSwitch state: " + string, false);
        return TextUtils.isEmpty(string);
    }

    public void setDrvMenuNotShowTime(long timestamp) {
        this.mSp.edit().putLong(PREF_KEY_KEEP_DRV_SEAT_TIME, timestamp).apply();
        this.mInDrvSeatMenuShowTime = false;
    }

    public boolean isDrvMenuInShowTime() {
        if (!this.mInDrvSeatMenuShowTime) {
            this.mInDrvSeatMenuShowTime = System.currentTimeMillis() - this.mSp.getLong(PREF_KEY_KEEP_DRV_SEAT_TIME, 0L) > 0;
        }
        return this.mInDrvSeatMenuShowTime;
    }

    public void setPsnMenuNotShowTime(long timestamp) {
        this.mSp.edit().putLong(PREF_KEY_KEEP_PSN_SEAT_TIME, timestamp).apply();
        this.mInPsnSeatMenuShowTime = false;
    }

    public boolean isPsnMenuInShowTime() {
        if (!this.mInPsnSeatMenuShowTime) {
            this.mInPsnSeatMenuShowTime = System.currentTimeMillis() - this.mSp.getLong(PREF_KEY_KEEP_PSN_SEAT_TIME, 0L) > 0;
        }
        return this.mInPsnSeatMenuShowTime;
    }
}
