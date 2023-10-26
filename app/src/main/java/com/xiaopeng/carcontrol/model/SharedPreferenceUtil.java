package com.xiaopeng.carcontrol.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.text.TextUtils;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.GlobalConstant;
import com.xiaopeng.carcontrol.InterGlobalConstant;
import com.xiaopeng.carcontrol.carmanager.impl.IcmController;
import com.xiaopeng.carcontrol.carmanager.impl.VcuController;
import com.xiaopeng.xvs.xid.sync.api.ICarControlSync;

/* loaded from: classes2.dex */
public final class SharedPreferenceUtil {
    public static final String PREF_FILE_NAME = "CarControl";
    private static SharedPreferences sSharedPref;

    static {
        synchronized (SharedPreferenceUtil.class) {
            if (sSharedPref == null && App.getInstance() != null) {
                sSharedPref = App.getInstance().getSharedPreferences(PREF_FILE_NAME, 0);
            }
        }
    }

    public static synchronized void initSharedPreferences(Context context) {
        synchronized (SharedPreferenceUtil.class) {
            if (sSharedPref == null) {
                sSharedPref = context.getSharedPreferences(PREF_FILE_NAME, 0);
            }
        }
    }

    public static boolean getLluOrderPlay() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_LLU_CONFIRM_DIALOG_ORDER_PLAY, false);
    }

    public static void setLluOrderPlay(boolean autoVolume) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_LLU_CONFIRM_DIALOG_ORDER_PLAY, autoVolume).apply();
    }

    public static boolean getLluAutoVolume() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_LLU_CONFIRM_DIALOG_AUTO_VOLUME, true);
    }

    public static void setLluAutoVolume(boolean autoVolume) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_LLU_CONFIRM_DIALOG_AUTO_VOLUME, autoVolume).apply();
    }

    public static boolean getLluAutoWindow() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_LLU_CONFIRM_DIALOG_AUTO_WINDOW, true);
    }

    public static void setLluAutoWindow(boolean autoWindow) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_LLU_CONFIRM_DIALOG_AUTO_WINDOW, autoWindow).apply();
    }

    public static boolean hasConfig(String key) {
        return !TextUtils.isEmpty(key) && sSharedPref.contains(key);
    }

    public static int getHeadLampState() {
        return sSharedPref.getInt(GlobalConstant.PREFS.PREF_HEAD_LAMP_INT, 3);
    }

    public static void setHeadLampState(int state) {
        sSharedPref.edit().putInt(GlobalConstant.PREFS.PREF_HEAD_LAMP_INT, state).apply();
    }

    public static int getLampHeightLevel() {
        return sSharedPref.getInt(GlobalConstant.PREFS.PREF_LAMP_HEIGHT_LEVEL_INT, 0);
    }

    public static void setLampHeightLevel(int level) {
        sSharedPref.edit().putInt(GlobalConstant.PREFS.PREF_LAMP_HEIGHT_LEVEL_INT, level).apply();
    }

    public static boolean isAutoLampHeight() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_AUTO_LAMP_HEIGHT_BOOL, true);
    }

    public static void setAutoLampHeight(boolean auto) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_AUTO_LAMP_HEIGHT_BOOL, auto).apply();
    }

    public static boolean getParkLampB() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_PARK_LAMP_B_BOOL, true);
    }

    public static void setParkLampB(boolean active) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_PARK_LAMP_B_BOOL, active).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean isLightMeHomeEnabled() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_LIGHT_ME_HOME_BOOL, true);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setLightMeHome(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_LIGHT_ME_HOME_BOOL, enable).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int getLightMeHomeTime() {
        return sSharedPref.getInt(GlobalConstant.PREFS.PREF_LIGHT_ME_HOME_TIME_INT, 1);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setLightMeHomeTime(int time) {
        sSharedPref.edit().putInt(GlobalConstant.PREFS.PREF_LIGHT_ME_HOME_TIME_INT, time).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean getAutoDoorHandle() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_AUTO_DOOR_HANDLE, GlobalConstant.DEFAULT.AUTO_DOOR_HANDLE);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setAutoDoorHandle(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_AUTO_DOOR_HANDLE, enable).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean getDriveAutoLock() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_DRIVE_AUTO_LOCK_BOOL, true);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setDriveAutoLock(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_DRIVE_AUTO_LOCK_BOOL, enable).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean getParkAutoUnlock() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_PARK_AUTO_UNLOCK_BOOL, false);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setParkAutoUnlock(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_PARK_AUTO_UNLOCK_BOOL, enable).apply();
    }

    static boolean getNearAutoUnlock() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_NEAR_AUTO_UNLOCK_BOOL, true);
    }

    static void setNearAutoUnlock(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_NEAR_AUTO_UNLOCK_BOOL, enable).apply();
    }

    static boolean getLeaveAutoLock() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_LEAVE_AUTO_LOCK_BOOL, true);
    }

    static void setLeaveAutoLock(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_LEAVE_AUTO_LOCK_BOOL, enable).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int getUnlockResponse() {
        return sSharedPref.getInt(GlobalConstant.PREFS.PREF_UNLOCK_RESPONSE_INT, GlobalConstant.DEFAULT.UNLOCK_RESPONSE);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setUnlockResponse(int response) {
        sSharedPref.edit().putInt(GlobalConstant.PREFS.PREF_UNLOCK_RESPONSE_INT, response).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean getLeftChildLockSw() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_LEFT_CHILD_LOCK_BOOL, false);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setLeftChildLockSw(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_LEFT_CHILD_LOCK_BOOL, enable).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean getRightChildLockSw() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_RIGHT_CHILD_LOCK_BOOL, false);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setRightChildLockSw(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_RIGHT_CHILD_LOCK_BOOL, enable).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean getLeftDoorHotKeySw() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_LEFT_DOOR_HOT_KEY_BOOL, false);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setLeftDoorHotKeySw(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_LEFT_DOOR_HOT_KEY_BOOL, enable).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean getRightDoorHotKeySw() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_RIGHT_DOOR_HOT_KEY_BOOL, false);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setRightDoorHotKeySw(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_RIGHT_DOOR_HOT_KEY_BOOL, enable).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean isWelcomeModeEnabled() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_WELCOME_MODE_BOOL, true);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setWelcomeMode(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_WELCOME_MODE_BOOL, enable).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean isPsnWelcomeModeEnabled() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_PSN_WELCOME_MODE_BOOL, true);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setPsnWelcomeMode(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_PSN_WELCOME_MODE_BOOL, enable).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean isRearSeatWelcomeModeEnabled() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_REAR_SEAT_WELCOME_MODE_BOOL, true);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setRearSeatWelcomeMode(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_REAR_SEAT_WELCOME_MODE_BOOL, enable).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean getCarpetLightWelcomeMode() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_CARPET_LIGHT_WELCOME_MODE_BOOL, true);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setCarpetLightWelcomeMode(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_CARPET_LIGHT_WELCOME_MODE_BOOL, enable).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean getPollingLightWelcomeMode() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_POLLING_LIGHT_WELCOME_MODE_BOOL, true);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setPollingLightWelcomeMode(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_POLLING_LIGHT_WELCOME_MODE_BOOL, enable).apply();
    }

    static int getDomeLight() {
        return sSharedPref.getInt(GlobalConstant.PREFS.PREF_DOME_INT, 1);
    }

    public static void setDomeLight(int state) {
        sSharedPref.edit().putInt(GlobalConstant.PREFS.PREF_DOME_INT, state).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int getDomeLightBright() {
        return sSharedPref.getInt(GlobalConstant.PREFS.PREF_DOME_BRIGHT_INT, 3);
    }

    public static void setDomeLightBright(int brightness) {
        sSharedPref.edit().putInt(GlobalConstant.PREFS.PREF_DOME_BRIGHT_INT, brightness).apply();
    }

    public static boolean getMeterMenuTemperature() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_MM_TEMP_BOOL, true);
    }

    public static void setMeterMenuTemperature(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_MM_TEMP_BOOL, enable).apply();
    }

    public static boolean getMeterMenuWindPower() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_MM_WIND_POWER_BOOL, true);
    }

    public static void setMeterMenuWindPower(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_MM_WIND_POWER_BOOL, enable).apply();
    }

    public static boolean getMeterMenuWindMode() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_MM_WIND_MODE_BOOL, true);
    }

    public static void setMeterMenuWindMode(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_MM_WIND_MODE_BOOL, enable).apply();
    }

    public static boolean getMeterMenuMediaSource() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_MM_MEDIA_SRC_BOOL, IcmController.getDefaultMenuMediaSourceValue());
    }

    public static void setMeterMenuMediaSource(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_MM_MEDIA_SRC_BOOL, enable).apply();
    }

    public static boolean getMeterMenuScreenLight() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_MM_SCREEN_BRIGHT_BOOL, true);
    }

    public static void setMeterMenuScreenLight(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_MM_SCREEN_BRIGHT_BOOL, enable).apply();
    }

    public static boolean getSpeedLimit() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_SPEED_LIMIT_BOOL, false);
    }

    public static void setSpeedLimit(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_SPEED_LIMIT_BOOL, enable).apply();
    }

    public static int getSpeedLimitValue() {
        return sSharedPref.getInt(GlobalConstant.PREFS.PREF_SPEED_LIMIT_VALUE_INT, 80);
    }

    public static void setSpeedLimitValue(int value) {
        sSharedPref.edit().putInt(GlobalConstant.PREFS.PREF_SPEED_LIMIT_VALUE_INT, value).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int getWiperIntervalGear() {
        return sSharedPref.getInt(GlobalConstant.PREFS.PREF_WIPER_INTERVAL_GEAR_INT, 3);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setWiperIntervalGear(int gear) {
        sSharedPref.edit().putInt(GlobalConstant.PREFS.PREF_WIPER_INTERVAL_GEAR_INT, gear).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int getCiuWiperIntervalGear() {
        return sSharedPref.getInt(GlobalConstant.PREFS.PREF_CIU_WIPER_INTERVAL_GEAR_INT, 3);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setCiuWiperIntervalGear(int gear) {
        sSharedPref.edit().putInt(GlobalConstant.PREFS.PREF_CIU_WIPER_INTERVAL_GEAR_INT, gear).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean isCiuRainSwEnabled() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_CIU_RAIN_BOOL, false);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setCiuRainSwEnabled(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_CIU_RAIN_BOOL, enable).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean getCwcSw() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_CWC_BOOL, true);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setCwcSw(boolean enabled) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_CWC_BOOL, enabled).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean getCwcFRSw() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_CWC_FR_BOOL, true);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setCwcFRSw(boolean enabled) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_CWC_FR_BOOL, enabled).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean getCwcRLSw() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_CWC_RL_BOOL, true);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setCwcRLSw(boolean enabled) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_CWC_RL_BOOL, enabled).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean getCwcRRSw() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_CWC_RR_BOOL, true);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setCwcRRSw(boolean enabled) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_CWC_RR_BOOL, enabled).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean getMicrophoneMute() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_MICROPHONE_MUTE_BOOL, false);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setMicrophoneMute(boolean mute) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_MICROPHONE_MUTE_BOOL, mute).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean isEsbEnabled() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_ELECTRIC_BELT_BOOL, true);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setEsbEnable(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_ELECTRIC_BELT_BOOL, enable).apply();
    }

    public static boolean isRsbWarningEnabled() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_REAR_BELT_WARNING_BOOL, true);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setRsbWarningEnable(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_REAR_BELT_WARNING_BOOL, enable).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean getEbwSw() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_EBW_BOOL, GlobalConstant.DEFAULT.EMERGENCY_BREAK_WARNING);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setEbwSw(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_EBW_BOOL, enable).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean isAvasLowSpdEnabled() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_AVAS_BOOL, true);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setAvasLowSpdEnable(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_AVAS_BOOL, enable).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int getAvasLowSpdEffect() {
        return sSharedPref.getInt(GlobalConstant.PREFS.PREF_AVAS_INT, 1);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setAvasLowSpdEffect(int effect) {
        sSharedPref.edit().putInt(GlobalConstant.PREFS.PREF_AVAS_INT, effect).apply();
    }

    static int getAvasLowSpdVolume() {
        return sSharedPref.getInt(GlobalConstant.PREFS.PREF_AVAS_VOLUME_INT, 100);
    }

    static void setAvasLowSpdVolume(int volume) {
        sSharedPref.edit().putInt(GlobalConstant.PREFS.PREF_AVAS_VOLUME_INT, volume).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int getAvasFriendEffect() {
        return sSharedPref.getInt(GlobalConstant.PREFS.PREF_FRIEND_INT, 1);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setAvasFriendEffect(int effect) {
        sSharedPref.edit().putInt(GlobalConstant.PREFS.PREF_FRIEND_INT, effect).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int getBootEffect() {
        return sSharedPref.getInt(GlobalConstant.PREFS.PREF_BOOT_EFFECT_INT, 1);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setBootEffect(int effect) {
        sSharedPref.edit().putInt(GlobalConstant.PREFS.PREF_BOOT_EFFECT_INT, effect).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int getBootEffectOld() {
        return sSharedPref.getInt(GlobalConstant.PREFS.PREF_BOOT_EFFECT_OLD_INT, 1);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setBootEffectOld(int effect) {
        sSharedPref.edit().putInt(GlobalConstant.PREFS.PREF_BOOT_EFFECT_OLD_INT, effect).apply();
    }

    public static boolean isAvhEnabled() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_AVH_AUTO_PARKING_BOOL, true);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setAvhEnabled(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_AVH_AUTO_PARKING_BOOL, enable).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int getSteeringEps() {
        return sSharedPref.getInt(GlobalConstant.PREFS.PREF_WHEEL_EPS_INT, 0);
    }

    public static void setSteeringEps(int eps) {
        sSharedPref.edit().putInt(GlobalConstant.PREFS.PREF_WHEEL_EPS_INT, eps).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int getCdcMode() {
        return sSharedPref.getInt(GlobalConstant.PREFS.PREF_CDC_INT, 1);
    }

    public static void setCdcMode(int cdc) {
        sSharedPref.edit().putInt(GlobalConstant.PREFS.PREF_CDC_INT, cdc).apply();
    }

    static boolean isFcwEnabled() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_FCW_BOOL, true);
    }

    static void setFcwEnable(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_FCW_BOOL, enable).apply();
    }

    static boolean getRcwSw() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_RCW_BOOL, true);
    }

    static void setRcwSw(boolean enabled) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_RCW_BOOL, enabled).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean getLdwSw() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_LDW_BOOL, true);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setLdwSw(boolean enabled) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_LDW_BOOL, enabled).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean getElkSw() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_ELK_BOOL, false);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setElkSw(boolean enabled) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_ELK_BOOL, enabled).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean getBsdSw() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_BSD_BOOL, true);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setBsdSw(boolean enabled) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_BSD_BOOL, enabled).apply();
    }

    static boolean getRctaSw() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_RCTA_BOOL, false);
    }

    static void setRctaSw(boolean enabled) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_RCTA_BOOL, enabled).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean getSideReverseWarningSw() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_SIDE_REVERSE_WARNING_BOOL, true);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setSideReverseWarningSw(boolean enabled) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_SIDE_REVERSE_WARNING_BOOL, enabled).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean getDowSw() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_DOW_BOOL, true);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setDowEnable(boolean enabled) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_DOW_BOOL, enabled).apply();
    }

    static int getAutoFarLampState() {
        return sSharedPref.getInt(GlobalConstant.PREFS.PREF_AUTO_FAR_LAMP_INT, 0);
    }

    static void setAutoFarLampState(int state) {
        sSharedPref.edit().putInt(GlobalConstant.PREFS.PREF_AUTO_FAR_LAMP_INT, state).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean getAutoParkSw() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_AUTO_PARK_BOOL, false);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setAutoParkSw(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_AUTO_PARK_BOOL, enable).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean getMemParkSw() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_MEM_PARK_BOOL, false);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setMemParkSw(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_MEM_PARK_BOOL, enable).apply();
    }

    static boolean getRemoteCallCarSw() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_REMOTE_CALL_CAR_BOOL, false);
    }

    static void setRemoteCallCarSw(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_REMOTE_CALL_CAR_BOOL, enable).apply();
    }

    static int getAutoParkSoundType() {
        return sSharedPref.getInt(GlobalConstant.PREFS.PREF_AUTO_PARK_SOUND_INT, 1);
    }

    static void setAutoParkSoundType(int type) {
        sSharedPref.edit().putInt(GlobalConstant.PREFS.PREF_AUTO_PARK_SOUND_INT, type).apply();
    }

    static boolean getPhoneCtrlParkSw() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_PHONE_CTRL_PARK_BOOL, false);
    }

    static void setPhoneCtrlParkEnable(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_PHONE_CTRL_PARK_BOOL, enable).apply();
    }

    static int getPhoneCtrlParkType() {
        return sSharedPref.getInt(GlobalConstant.PREFS.PREF_PHONE_CTRL_PARK_TYPE_INT, 2);
    }

    static void setPhoneCtrlParkType(int type) {
        sSharedPref.edit().putInt(GlobalConstant.PREFS.PREF_PHONE_CTRL_PARK_TYPE_INT, type).apply();
    }

    static boolean getKeyCtrlParkEnabled() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_KEY_CTRL_PARK_BOOL, false);
    }

    static void setKeyCtrlParkEnable(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_KEY_CTRL_PARK_BOOL, enable).apply();
    }

    static int getKeyCtrlParkType() {
        return sSharedPref.getInt(GlobalConstant.PREFS.PREF_KEY_CTRL_PARK_TYPE_INT, 2);
    }

    static void setKeyCtrlParkType(int type) {
        sSharedPref.edit().putInt(GlobalConstant.PREFS.PREF_KEY_CTRL_PARK_TYPE_INT, type).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean getLccSw() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_LCC_BOOL, false);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setLccSw(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_LCC_BOOL, enable).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean getIslcSw() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_ISLC_BOOL, true);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setIslcSw(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_ISLC_BOOL, enable).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int getIslaSw() {
        return sSharedPref.getInt(GlobalConstant.PREFS.PREF_ISLA_INT, 1);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setIslaSw(int state) {
        sSharedPref.edit().putInt(GlobalConstant.PREFS.PREF_ISLA_INT, state).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int getIslaSpdRange() {
        return sSharedPref.getInt(GlobalConstant.PREFS.PREF_ISLA_SPD_RANGE_INT, 2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setIslaSpdRange(int spdRange) {
        sSharedPref.edit().putInt(GlobalConstant.PREFS.PREF_ISLA_SPD_RANGE_INT, spdRange).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean getIslaConfirmMode() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_ISLA_CONFIRM_MODE_BOOL, true);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setIslaConfirmMode(boolean mode) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_ISLA_CONFIRM_MODE_BOOL, mode).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean getAlcSw() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_ALC_BOOL, false);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setAlcSw(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_ALC_BOOL, enable).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setNraState(int state) {
        sSharedPref.edit().putInt(GlobalConstant.PREFS.PREF_NRA_INT, state).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int getNraState() {
        return sSharedPref.getInt(GlobalConstant.PREFS.PREF_NRA_INT, 2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setLssState(int state) {
        sSharedPref.edit().putInt(GlobalConstant.PREFS.PREF_LSS_INT, state).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int getLssState() {
        return sSharedPref.getInt(GlobalConstant.PREFS.PREF_LSS_INT, 0);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean getNgpSw() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_NGP_BOOL, false);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setNgpSw(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_NGP_BOOL, enable).apply();
    }

    static boolean isNgpFastLane() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_NGP_FAST_LANE_BOOL, false);
    }

    static void setNgpFastLane(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_NGP_FAST_LANE_BOOL, enable).apply();
    }

    static boolean isNgpTruckOffset() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_NGP_TRUCK_OFFSET_BOOL, false);
    }

    static void setNgpTruckOffset(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_NGP_TRUCK_OFFSET_BOOL, enable).apply();
    }

    static boolean isNgpTipWin() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_NGP_TIP_WIN_BOOL, false);
    }

    static void setNgpTipWin(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_NGP_TIP_WIN_BOOL, enable).apply();
    }

    static boolean isNgpAutoLcs() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_NGP_AUTO_LCS_BOOL, false);
    }

    static void setNgpAutoLcs(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_NGP_AUTO_LCS_BOOL, enable).apply();
    }

    static int getNgpLcMode() {
        return sSharedPref.getInt(GlobalConstant.PREFS.PREF_NGP_LC_MODE_INT, 1);
    }

    static void setNgpLcMode(int mode) {
        sSharedPref.edit().putInt(GlobalConstant.PREFS.PREF_NGP_LC_MODE_INT, mode).apply();
    }

    static int getNgpRemindMode() {
        return sSharedPref.getInt(GlobalConstant.PREFS.PREF_NGP_REMIND_MODE_INT, 0);
    }

    static void setNgpRemindMode(int mode) {
        sSharedPref.edit().putInt(GlobalConstant.PREFS.PREF_NGP_REMIND_MODE_INT, mode).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean getCityNgpSw() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_CITY_NGP_BOOL, false);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setCityNgpSw(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_CITY_NGP_BOOL, enable).apply();
    }

    public static int getEnergyRecycleGrade() {
        return sSharedPref.getInt(GlobalConstant.PREFS.PREF_ENERGY_RECYCLE_GRADE_INT, VcuController.getDefaultEnergyRecycleGrade());
    }

    public static void setEnergyRecycleGrade(int grade) {
        sSharedPref.edit().putInt(GlobalConstant.PREFS.PREF_ENERGY_RECYCLE_GRADE_INT, grade).apply();
    }

    public static int getDriveMode() {
        return sSharedPref.getInt(GlobalConstant.PREFS.PREF_DRIVER_MODE_INT, GlobalConstant.DEFAULT.DRIVER_MODE);
    }

    public static void setDriveMode(int mode) {
        sSharedPref.edit().putInt(GlobalConstant.PREFS.PREF_DRIVER_MODE_INT, mode).apply();
    }

    public static int getAwdSetting() {
        return sSharedPref.getInt(GlobalConstant.PREFS.AWD_SETTING_MODE_INT, 2);
    }

    public static void setAwdSetting(int mode) {
        sSharedPref.edit().putInt(GlobalConstant.PREFS.AWD_SETTING_MODE_INT, mode).apply();
    }

    public static boolean getFirstEnterXpowerFlag() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.FIRST_ENTER_XPOWER_FLAG, true);
    }

    public static void setFirstEnterXpowerFlag(boolean flag) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.FIRST_ENTER_XPOWER_FLAG, flag).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean getEspSw() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_ESP_MODE_BOOL, true);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setEspSw(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_ESP_MODE_BOOL, enable).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String getDrvSeatData(String key) {
        return sSharedPref.getString(key, null);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void saveDrvSeatData(String key, String seatData) {
        sSharedPref.edit().putString(key, seatData).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int getDrvSeatMemoryIndex() {
        return sSharedPref.getInt(GlobalConstant.PREFS.PREF_DRV_SEAT_MEMORY_INDEX, 0);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int getPsnSeatMemoryIndex(int seleceId) {
        if (seleceId != 0) {
            if (seleceId != 1) {
                if (seleceId != 2) {
                    return 0;
                }
                return sSharedPref.getInt(GlobalConstant.PREFS.PREF_PSN_SEAT_MEMORY_INDEX2, 0);
            }
            return sSharedPref.getInt(GlobalConstant.PREFS.PREF_PSN_SEAT_MEMORY_INDEX1, 0);
        }
        return sSharedPref.getInt(GlobalConstant.PREFS.PREF_PSN_SEAT_MEMORY_INDEX0, 0);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setDrvSeatMemoryIndex(int index) {
        sSharedPref.edit().putInt(GlobalConstant.PREFS.PREF_DRV_SEAT_MEMORY_INDEX, index).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void saveCurrentSelectPsnHabit(int selectId) {
        sSharedPref.edit().putInt(GlobalConstant.PREFS.KEY_CURRENT_SELECTED_PSN_HABBIT_ID, selectId).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void saveCurrentSelectRLHabit(int selectId) {
        sSharedPref.edit().putInt(GlobalConstant.PREFS.KEY_CURRENT_SELECTED_RL_HABBIT_ID, selectId).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void saveCurrentSelectRRHabit(int selectId) {
        sSharedPref.edit().putInt(GlobalConstant.PREFS.KEY_CURRENT_SELECTED_RR_HABBIT_ID, selectId).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int getCurrentSelectedPsnHabit() {
        return sSharedPref.getInt(GlobalConstant.PREFS.KEY_CURRENT_SELECTED_PSN_HABBIT_ID, 0);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int getCurrentSelectedRLHabit() {
        return sSharedPref.getInt(GlobalConstant.PREFS.KEY_CURRENT_SELECTED_RL_HABBIT_ID, 0);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int getCurrentSelectedRRHabit() {
        return sSharedPref.getInt(GlobalConstant.PREFS.KEY_CURRENT_SELECTED_RR_HABBIT_ID, 0);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setPsnSeatMemoryIndex(int index, int selectId) {
        if (selectId == 0) {
            sSharedPref.edit().putInt(GlobalConstant.PREFS.PREF_PSN_SEAT_MEMORY_INDEX0, index).apply();
        } else if (selectId == 1) {
            sSharedPref.edit().putInt(GlobalConstant.PREFS.PREF_PSN_SEAT_MEMORY_INDEX1, index).apply();
        } else if (selectId != 2) {
        } else {
            sSharedPref.edit().putInt(GlobalConstant.PREFS.PREF_PSN_SEAT_MEMORY_INDEX2, index).apply();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String getPsnSeatData(String key) {
        return sSharedPref.getString(key, null);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String getRLSeatData(String key) {
        return sSharedPref.getString(key, null);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String getRRSeatData(String key) {
        return sSharedPref.getString(key, null);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String getPsnSelectSeatData(int selectId) {
        if (selectId != 0) {
            if (selectId != 1) {
                if (selectId != 2) {
                    return null;
                }
                return sSharedPref.getString("CHAIR_POSITION_THREE", null);
            }
            return sSharedPref.getString("CHAIR_POSITION_TWO", null);
        }
        return sSharedPref.getString(GlobalConstant.PREFS.KEY_CHAIR_POS_PSN_SELECT_ONE, null);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String getRLSelectSeatData(int selectId) {
        if (selectId != 0) {
            if (selectId != 1) {
                if (selectId != 2) {
                    return null;
                }
                return sSharedPref.getString(GlobalConstant.PREFS.KEY_CHAIR_POS_RL_SELECT_THREE, null);
            }
            return sSharedPref.getString(GlobalConstant.PREFS.KEY_CHAIR_POS_RL_SELECT_TWO, null);
        }
        return sSharedPref.getString(GlobalConstant.PREFS.KEY_CHAIR_POS_RL_SELECT_ONE, null);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String getRRSelectSeatData(int selectId) {
        if (selectId != 0) {
            if (selectId != 1) {
                if (selectId != 2) {
                    return null;
                }
                return sSharedPref.getString(GlobalConstant.PREFS.KEY_CHAIR_POS_RR_SELECT_THREE, null);
            }
            return sSharedPref.getString(GlobalConstant.PREFS.KEY_CHAIR_POS_RR_SELECT_TWO, null);
        }
        return sSharedPref.getString(GlobalConstant.PREFS.KEY_CHAIR_POS_RR_SELECT_ONE, null);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String getPsnSeatName(int selectId) {
        if (selectId != 0) {
            if (selectId != 1) {
                if (selectId != 2) {
                    return null;
                }
                return sSharedPref.getString(GlobalConstant.PREFS.KEY_CHAIR_NAME_PSN_SELECT_THREE, null);
            }
            return sSharedPref.getString(GlobalConstant.PREFS.KEY_CHAIR_NAME_PSN_SELECT_TWO, null);
        }
        return sSharedPref.getString(GlobalConstant.PREFS.KEY_CHAIR_NAME_PSN_SELECT_ONE, null);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String getRLSeatName(int selectId) {
        if (selectId != 0) {
            if (selectId != 1) {
                if (selectId != 2) {
                    return null;
                }
                return sSharedPref.getString(GlobalConstant.PREFS.KEY_CHAIR_NAME_RL_SELECT_THREE, null);
            }
            return sSharedPref.getString(GlobalConstant.PREFS.KEY_CHAIR_NAME_RL_SELECT_TWO, null);
        }
        return sSharedPref.getString(GlobalConstant.PREFS.KEY_CHAIR_NAME_RL_SELECT_ONE, null);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String getRRSeatName(int selectId) {
        if (selectId != 0) {
            if (selectId != 1) {
                if (selectId != 2) {
                    return null;
                }
                return sSharedPref.getString(GlobalConstant.PREFS.KEY_CHAIR_NAME_RR_SELECT_THREE, null);
            }
            return sSharedPref.getString(GlobalConstant.PREFS.KEY_CHAIR_NAME_RR_SELECT_TWO, null);
        }
        return sSharedPref.getString(GlobalConstant.PREFS.KEY_CHAIR_NAME_RR_SELECT_ONE, null);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void savePsnSeatData(String key, String seatData) {
        sSharedPref.edit().putString(key, seatData).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void savePsnSelectSeatData(int selectId, String seatData) {
        sSharedPref.edit().putString(ICarControlSync.CHAIR_POSITION_PSN, seatData).apply();
        if (selectId == 0) {
            sSharedPref.edit().putString(GlobalConstant.PREFS.KEY_CHAIR_POS_PSN_SELECT_ONE, seatData).apply();
        } else if (selectId == 1) {
            sSharedPref.edit().putString("CHAIR_POSITION_TWO", seatData).apply();
        } else if (selectId != 2) {
        } else {
            sSharedPref.edit().putString("CHAIR_POSITION_THREE", seatData).apply();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void saveRLSelectSeatData(int selectId, String seatData) {
        sSharedPref.edit().putString("CHAIR_POSITION_RL", seatData).apply();
        if (selectId == 0) {
            sSharedPref.edit().putString(GlobalConstant.PREFS.KEY_CHAIR_POS_RL_SELECT_ONE, seatData).apply();
        } else if (selectId == 1) {
            sSharedPref.edit().putString(GlobalConstant.PREFS.KEY_CHAIR_POS_RL_SELECT_TWO, seatData).apply();
        } else if (selectId != 2) {
        } else {
            sSharedPref.edit().putString(GlobalConstant.PREFS.KEY_CHAIR_POS_RL_SELECT_THREE, seatData).apply();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void saveRRSelectSeatData(int selectId, String seatData) {
        sSharedPref.edit().putString("CHAIR_POSITION_RR", seatData).apply();
        if (selectId == 0) {
            sSharedPref.edit().putString(GlobalConstant.PREFS.KEY_CHAIR_POS_RR_SELECT_ONE, seatData).apply();
        } else if (selectId == 1) {
            sSharedPref.edit().putString(GlobalConstant.PREFS.KEY_CHAIR_POS_RR_SELECT_TWO, seatData).apply();
        } else if (selectId != 2) {
        } else {
            sSharedPref.edit().putString(GlobalConstant.PREFS.KEY_CHAIR_POS_RR_SELECT_THREE, seatData).apply();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void savePsnSeatName(int selectId, String seatName) {
        if (selectId == 0) {
            sSharedPref.edit().putString(GlobalConstant.PREFS.KEY_CHAIR_NAME_PSN_SELECT_ONE, seatName).apply();
        } else if (selectId == 1) {
            sSharedPref.edit().putString(GlobalConstant.PREFS.KEY_CHAIR_NAME_PSN_SELECT_TWO, seatName).apply();
        } else if (selectId != 2) {
        } else {
            sSharedPref.edit().putString(GlobalConstant.PREFS.KEY_CHAIR_NAME_PSN_SELECT_THREE, seatName).apply();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void saveRLSeatName(int selectId, String seatName) {
        if (selectId == 0) {
            sSharedPref.edit().putString(GlobalConstant.PREFS.KEY_CHAIR_NAME_RL_SELECT_ONE, seatName).apply();
        } else if (selectId == 1) {
            sSharedPref.edit().putString(GlobalConstant.PREFS.KEY_CHAIR_NAME_RL_SELECT_TWO, seatName).apply();
        } else if (selectId != 2) {
        } else {
            sSharedPref.edit().putString(GlobalConstant.PREFS.KEY_CHAIR_NAME_RL_SELECT_THREE, seatName).apply();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void saveRRSeatName(int selectId, String seatName) {
        if (selectId == 0) {
            sSharedPref.edit().putString(GlobalConstant.PREFS.KEY_CHAIR_NAME_RR_SELECT_ONE, seatName).apply();
        } else if (selectId == 1) {
            sSharedPref.edit().putString(GlobalConstant.PREFS.KEY_CHAIR_NAME_RR_SELECT_TWO, seatName).apply();
        } else if (selectId != 2) {
        } else {
            sSharedPref.edit().putString(GlobalConstant.PREFS.KEY_CHAIR_NAME_RR_SELECT_THREE, seatName).apply();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int getMirrorReverseMode() {
        return sSharedPref.getInt(GlobalConstant.PREFS.PREF_MIRROR_REVERSE_INT, 3);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setMirrorReverseMode(int mode) {
        sSharedPref.edit().putInt(GlobalConstant.PREFS.PREF_MIRROR_REVERSE_INT, mode).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean getMirrorAutoFoldEnable() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_MIRROR_AUTO_FOLD_BOOL, true);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setMirrorAutoFoldEnable(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_MIRROR_AUTO_FOLD_BOOL, enable).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String getRearMirrorData() {
        return sSharedPref.getString(GlobalConstant.PREFS.PREF_MIRROR_MEMORY_STRING, null);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setRearMirrorData(String mirrorData) {
        sSharedPref.edit().putString(GlobalConstant.PREFS.PREF_MIRROR_MEMORY_STRING, mirrorData).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean getMirrorAutoDown() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_MIRROR_DOWN_BOOL, true);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setMirrorAutoDown(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_MIRROR_DOWN_BOOL, enable).apply();
    }

    public static boolean isAutoDriveModeEnabled() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_AUTO_DRIVE_MODE_BOOL, false);
    }

    static void setAutoDriveModeEnable(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_AUTO_DRIVE_MODE_BOOL, enable).apply();
    }

    public static int getAirAutoProtectMode() {
        return sSharedPref.getInt(GlobalConstant.PREFS.PREF_AIR_AUTO_PROTECT_INT, 1);
    }

    static void setAirAutoProtect(int mode) {
        sSharedPref.edit().putInt(GlobalConstant.PREFS.PREF_AIR_AUTO_PROTECT_INT, mode).apply();
    }

    public static boolean isSmartHvacEnabled() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_SMART_HVAC_BOOL, false);
    }

    static void setSmartHvac(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_SMART_HVAC_BOOL, enable).apply();
    }

    static int getHvacAqsLevel() {
        return sSharedPref.getInt(GlobalConstant.PREFS.PREF_HVAC_AQS_LEVEL, 2);
    }

    static void setHvacAqsLevel(int level) {
        sSharedPref.edit().putInt(GlobalConstant.PREFS.PREF_HVAC_AQS_LEVEL, level).apply();
    }

    static int getHvacCirculationInterval() {
        return sSharedPref.getInt(GlobalConstant.PREFS.PREF_HVAC_CIRCULATION_INTERVAL, 2);
    }

    static void setHvacCirculationInterval(int interval) {
        sSharedPref.edit().putInt(GlobalConstant.PREFS.PREF_HVAC_CIRCULATION_INTERVAL, interval).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean isHvacSelfDryEnabled() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_HVAC_SELF_DRY, true);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setHvacSelfDry(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_HVAC_SELF_DRY, enable).apply();
    }

    public static boolean isHvacBlowModeAuto() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_HVAC_BLOW_MODE_AUTO, false);
    }

    public static void setHvacBlowModeAuto(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_HVAC_BLOW_MODE_AUTO, enable).apply();
    }

    static boolean isWindowSyncEnabled() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_WINDOW_SYNC_BOOL, false);
    }

    static void setWindowSyncEnable(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_WINDOW_SYNC_BOOL, enable).apply();
    }

    public static boolean getHighSpdCloseWinSw() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_AUTO_CLOSE_WINDOW_BOOL, false);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setHighSpdCloseWinSw(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_AUTO_CLOSE_WINDOW_BOOL, enable).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean getAutoWindowLockSw() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_LOCK_CLOSE_WIN_BOOL, true);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setAutoWindowLockSw(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_LOCK_CLOSE_WIN_BOOL, enable).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean getWindowLockState() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_WIN_LOCK_STATE, false);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setWindowLockState(boolean on) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_WIN_LOCK_STATE, on).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean isLluSwEnabled() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_LLU_SWITCH_BOOL, true);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setLluSw(boolean enabled) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_LLU_SWITCH_BOOL, enabled).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean isLluUnlockSwEnabled() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_LLU_UNLOCK_SW_BOOL, true);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setLluUnlockSw(boolean enabled) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_LLU_UNLOCK_SW_BOOL, enabled).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean isLluLockSwEnabled() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_LLU_LOCK_SW_BOOL, true);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setLluLockSw(boolean enabled) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_LLU_LOCK_SW_BOOL, enabled).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean isLluChargeSwEnabled() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_LLU_CHARGE_SW_BOOL, true);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setLluChargeSw(boolean enabled) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_LLU_CHARGE_SW_BOOL, enabled).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean isAtlSwEnabled() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_ATL_SWITCH_BOOL, true);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setAtlSwEnable(boolean isOn) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_ATL_SWITCH_BOOL, isOn).apply();
    }

    static boolean isAtlAutoBrightness() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.ATL_AUTO_BRIGHTNESS_BOOL, true);
    }

    static void setAtlAutoBrightness(boolean enabled) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.ATL_AUTO_BRIGHTNESS_BOOL, enabled).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int getAtlManualBrightness() {
        return sSharedPref.getInt(GlobalConstant.PREFS.ATL_MANUAL_BRIGHTNESS_INT, 100);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setAtlManualBrightness(int brightness) {
        sSharedPref.edit().putInt(GlobalConstant.PREFS.ATL_MANUAL_BRIGHTNESS_INT, brightness).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String getAtlEffectMode() {
        return sSharedPref.getString(GlobalConstant.PREFS.ATL_EFFECT_MODE_STR, "stable_effect");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setAtlEffectMode(String effectMode) {
        sSharedPref.edit().putString(GlobalConstant.PREFS.ATL_EFFECT_MODE_STR, effectMode).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean isAtlDualColorSwEnabled() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.ATL_DUAL_COLOR_MODE_BOOL, false);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setAtlDualColorSw(boolean enabled) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.ATL_DUAL_COLOR_MODE_BOOL, enabled).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int getAtlSingleColor() {
        return sSharedPref.getInt(GlobalConstant.PREFS.ATL_SINGLE_COLOR_INT, 14);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setAtlSingleColor(int colorId) {
        sSharedPref.edit().putInt(GlobalConstant.PREFS.ATL_SINGLE_COLOR_INT, colorId).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String getAtlDualColor() {
        return sSharedPref.getString(GlobalConstant.PREFS.ATL_DUAL_COLOR_STR, null);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setAtlDualColor(String dualColorStr) {
        sSharedPref.edit().putString(GlobalConstant.PREFS.ATL_DUAL_COLOR_STR, dualColorStr).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int getWheelXKey() {
        return sSharedPref.getInt(GlobalConstant.PREFS.PREF_WHEEL_X_KEY_INT, 0);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setWheelXKey(int key) {
        sSharedPref.edit().putInt(GlobalConstant.PREFS.PREF_WHEEL_X_KEY_INT, key).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int getDoorKey() {
        return sSharedPref.getInt(GlobalConstant.PREFS.PREF_DOOR_KEY_INT, 0);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setDoorKey(int key) {
        sSharedPref.edit().putInt(GlobalConstant.PREFS.PREF_DOOR_KEY_INT, key).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean getDoorBossSw() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_DOOR_BOSS_SW_BOOL, false);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setDoorBossSw(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_DOOR_BOSS_SW_BOOL, enable).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int getAvasExternalVolume() {
        return sSharedPref.getInt(GlobalConstant.PREFS.PREF_AVAS_EXTERNAL_VOLUME_INT, 100);
    }

    static void setAvasExternalVolume(int volume) {
        sSharedPref.edit().putInt(GlobalConstant.PREFS.PREF_AVAS_EXTERNAL_VOLUME_INT, volume).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean getCiuCameraSw() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_CIU_CAMERA_BOOL, true);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setCiuCameraSw(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_CIU_CAMERA_BOOL, enable).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean getCiuFaceSw() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_CIU_FACE_BOOL, true);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setCiuFaceSw(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_CIU_FACE_BOOL, enable).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean getCiuFatigueSw() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_CIU_FATIGUE_BOOL, false);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setCiuFatigueSw(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_CIU_FATIGUE_BOOL, enable).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean getCiuDistractSw() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_CIU_DISTRACT_BOOL, false);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setCiuDistractSw(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_CIU_DISTRACT_BOOL, enable).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setSoldierSwStatus(int status) {
        sSharedPref.edit().putInt(GlobalConstant.PREFS.PREF_SOLDIER_STATUS_INT, status).apply();
    }

    public static boolean getWheelKeyProtectSw() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.WHEEL_KEY_PROTECT_BOOL, true);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setWheelKeyProtectSw(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.WHEEL_KEY_PROTECT_BOOL, enable).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean getNGearWarningSwitch() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.N_GEAR_WARNING_SW_BOOL, true);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setNGearWarningSwitch(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.N_GEAR_WARNING_SW_BOOL, enable).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int getSdcKeyCfg() {
        return sSharedPref.getInt(GlobalConstant.PREFS.PREF_SDC_KEY_CFG_INT, 1);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setSdcKeyCfg(int cfg) {
        sSharedPref.edit().putInt(GlobalConstant.PREFS.PREF_SDC_KEY_CFG_INT, cfg).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean getSdcWinAutoDown() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_SDC_AUTO_WIN_DOWN_BOOL, true);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setSdcWinAutoDown(boolean status) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_SDC_AUTO_WIN_DOWN_BOOL, status).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int getDrvSeatHeatLevel() {
        return sSharedPref.getInt(GlobalConstant.PREFS.PREF_DRV_SEAT_HEAT_LEVEL_INT, 0);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setDrvSeatHeatLevel(int level) {
        sSharedPref.edit().putInt(GlobalConstant.PREFS.PREF_DRV_SEAT_HEAT_LEVEL_INT, level).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int getDrvSeatVentLevel() {
        return sSharedPref.getInt(GlobalConstant.PREFS.PREF_DRV_SEAT_VENT_LEVEL_INT, 0);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setDrvSeatVentLevel(int level) {
        sSharedPref.edit().putInt(GlobalConstant.PREFS.PREF_DRV_SEAT_VENT_LEVEL_INT, level).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int getPsnSeatHeatLevel() {
        return sSharedPref.getInt(GlobalConstant.PREFS.PREF_PSN_SEAT_HEAT_LEVEL_INT, 0);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setPsnSeatHeatLevel(int level) {
        sSharedPref.edit().putInt(GlobalConstant.PREFS.PREF_PSN_SEAT_HEAT_LEVEL_INT, level).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String getCarLicensePlate() {
        return sSharedPref.getString(GlobalConstant.PREFS.PREF_CAR_LICENSE_PLATE, null);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setCarLicensePlate(String licensePlate) {
        sSharedPref.edit().putString(GlobalConstant.PREFS.PREF_CAR_LICENSE_PLATE, licensePlate).apply();
    }

    public static boolean getXpedal() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_XPEDAL_BOOL, false);
    }

    public static void setXpedal(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_XPEDAL_BOOL, enable).apply();
    }

    public static boolean isAntiSicknessEnabled() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_ANTI_SICKNESS_BOOL, false);
    }

    public static void setAntiSickness(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_ANTI_SICKNESS_BOOL, enable).apply();
    }

    public static boolean isCarControlMoveBack() {
        return Settings.System.getInt(App.getInstance().getContentResolver(), GlobalConstant.PREFS.PREF_CARCONTROL_MOVE_BACK, 0) == 1;
    }

    public static void setCarControlMoveBack(boolean enable) {
        Settings.System.putInt(App.getInstance().getContentResolver(), GlobalConstant.PREFS.PREF_CARCONTROL_MOVE_BACK, enable ? 1 : 0);
    }

    public static String getLLuDanceLastPlayRscId() {
        return sSharedPref.getString(GlobalConstant.LluDance.LAST_PLAY_RSC_ID, "");
    }

    public static void setLLuDanceLastPlayRscId(String rscId) {
        sSharedPref.edit().putString(GlobalConstant.LluDance.LAST_PLAY_RSC_ID, rscId).apply();
    }

    public static String getLLuDanceCurrentSelectRscId() {
        return sSharedPref.getString(GlobalConstant.LluDance.CURRENT_SELECT_RSC_ID, "");
    }

    public static void setLLuDanceCurrentSelectRscId(String rscId) {
        sSharedPref.edit().putString(GlobalConstant.LluDance.CURRENT_SELECT_RSC_ID, rscId).apply();
    }

    public static int getIcmBrightness() {
        return sSharedPref.getInt(GlobalConstant.PREFS.PREF_ICM_BRIGHTNESS_INT, 0);
    }

    public static void setIcmBrightness(int value) {
        sSharedPref.edit().putInt(GlobalConstant.PREFS.PREF_ICM_BRIGHTNESS_INT, value).apply();
    }

    public static boolean getIcmMeditation() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_ICM_MEDITATION_BOOL, false);
    }

    public static void setIcmMeditation(boolean enter) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_ICM_MEDITATION_BOOL, enter).apply();
    }

    public static int getMediDayNightMode() {
        return sSharedPref.getInt(GlobalConstant.PREFS.PREF_MEDITATION_DAY_NIHGT_INT, 0);
    }

    public static void setMediDayNightMode(int mode) {
        sSharedPref.edit().putInt(GlobalConstant.PREFS.PREF_MEDITATION_DAY_NIHGT_INT, mode).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setRRSeatHeatLevel(int level) {
        sSharedPref.edit().putInt(InterGlobalConstant.PREFS.PREF_RR_SEAT_HEAT_LEVEL_INT, level).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int getRRSeatHeatLevel() {
        return sSharedPref.getInt(InterGlobalConstant.PREFS.PREF_RR_SEAT_HEAT_LEVEL_INT, 0);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setRLSeatHeatLevel(int level) {
        sSharedPref.edit().putInt(InterGlobalConstant.PREFS.PREF_RL_SEAT_HEAT_LEVEL_INT, level).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int getRLSeatHeatLevel() {
        return sSharedPref.getInt(InterGlobalConstant.PREFS.PREF_RL_SEAT_HEAT_LEVEL_INT, 0);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setSteerHeatLevel(int level) {
        sSharedPref.edit().putInt(InterGlobalConstant.PREFS.PREF_STEER_HEAT_LEVEL_INT, level).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int getSteerHeatLevel() {
        return sSharedPref.getInt(InterGlobalConstant.PREFS.PREF_STEER_HEAT_LEVEL_INT, 0);
    }

    public static int getSdcMaxAutoDoorOpeningAngle() {
        return sSharedPref.getInt(GlobalConstant.PREFS.PREF_SDC_MAX_AUTO_DOOR_OPENING_ANGLE, 100);
    }

    public static void setSdcMaxAutoDoorOpeningAngle(int angle) {
        sSharedPref.edit().putInt(GlobalConstant.PREFS.PREF_SDC_MAX_AUTO_DOOR_OPENING_ANGLE, angle).apply();
    }

    public static int getLeftSlideDoorMode() {
        return sSharedPref.getInt(GlobalConstant.PREFS.PREF_LEFT_SLIDER_DOOR_MODE, 0);
    }

    public static void setLeftSlideDoorMode(int mode) {
        sSharedPref.edit().putInt(GlobalConstant.PREFS.PREF_LEFT_SLIDER_DOOR_MODE, mode).apply();
    }

    public static int getRightSlideDoorMode() {
        return sSharedPref.getInt(GlobalConstant.PREFS.PREF_RIGHT_SLIDER_DOOR_MODE, 0);
    }

    public static void setRightSlideDoorMode(int mode) {
        sSharedPref.edit().putInt(GlobalConstant.PREFS.PREF_RIGHT_SLIDER_DOOR_MODE, mode).apply();
    }

    public static boolean isLCCVideoWatched() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_LCC_VIDEO_WATCHED_BOOL, false);
    }

    public static void setLCCVideoWatched(boolean watched) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_LCC_VIDEO_WATCHED_BOOL, watched).apply();
    }

    public static boolean isMemParkVideoWatched() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_MEM_PARK_VIDEO_WATCHED_BOOL, false);
    }

    public static void setMemParkVideoWatched(boolean watched) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_MEM_PARK_VIDEO_WATCHED_BOOL, watched).apply();
    }

    public static void setUserDefineDriveModeInfo(int driveMode, String info) {
        sSharedPref.edit().putString(driveMode > 11 ? "pref_user_define_drive_mode_info_str_" + driveMode : GlobalConstant.PREFS.PREF_USER_DEFINE_MODE_INFO_STR, info).apply();
    }

    public static String getUserDefineDriveModeInfo(int index) {
        return sSharedPref.getString(index > 11 ? "pref_user_define_drive_mode_info_str_" + index : GlobalConstant.PREFS.PREF_USER_DEFINE_MODE_INFO_STR, GlobalConstant.DEFAULT.USER_DEFINE_DRIVE_MODE_CFG);
    }

    public static void setUserDefineDriveModeInfo(String info) {
        sSharedPref.edit().putString(GlobalConstant.PREFS.PREF_USER_DEFINE_MODE_INFO_STR, info).apply();
    }

    public static String getUserDefineDriveModeInfo() {
        return sSharedPref.getString(GlobalConstant.PREFS.PREF_USER_DEFINE_MODE_INFO_STR, GlobalConstant.DEFAULT.USER_DEFINE_DRIVE_MODE_CFG);
    }

    public static void setUserDefineDriveMode(int mode) {
        sSharedPref.edit().putInt(GlobalConstant.PREFS.PREF_USER_DEFINE_MODE_INT, mode).apply();
    }

    public static int getUserDefineDriveMode() {
        return sSharedPref.getInt(GlobalConstant.PREFS.PREF_USER_DEFINE_MODE_INT, 11);
    }

    public static void setNewDriveXPedalMode(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_NEW_DRIVE_XPEDAL_BOOL, enable).apply();
    }

    public static boolean isNewDriveXPedalModeEnabled() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_NEW_DRIVE_XPEDAL_BOOL, false);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setAsWelcomeMode(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_AS_WELCOME_MODE_BOOL, enable).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean isAsWelcomeModeEnabled() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_AS_WELCOME_MODE_BOOL, false);
    }

    public static void setFirstStartSpaceCapsuleMode(boolean isFirst) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_FIRST_START_SPACE_CAPSULE_BOOL, isFirst).apply();
    }

    public static boolean isFirstStartSpaceCapsuleMode() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_FIRST_START_SPACE_CAPSULE_BOOL, true);
    }

    public static void setSpaceCapsuleSleepMusic(String uri) {
        sSharedPref.edit().putString(GlobalConstant.PREFS.PREF_SPACE_CAPSULE_SLEEP_MUSIC, uri).apply();
    }

    public static String getSpaceCapsuleSleepMusic() {
        return sSharedPref.getString(GlobalConstant.PREFS.PREF_SPACE_CAPSULE_SLEEP_MUSIC, "");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setNfcKeyEnable(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_NFC_KEY_ENABLE_BOOL, enable).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean isNfcKeyEnabled() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_NFC_KEY_ENABLE_BOOL, true);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setPsnSrsEnable(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_PSN_SRS_ENABLE_BOOL, enable).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean isPsnSrsEnable() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_PSN_SRS_ENABLE_BOOL, true);
    }

    public static void setMeditationLastPlayIndex(int index) {
        sSharedPref.edit().putInt(GlobalConstant.PREFS.PREF_MEDITATION_LAST_PLAY_INDEX_INT, index).apply();
    }

    public static int getMeditationLastPlayIndex() {
        return sSharedPref.getInt(GlobalConstant.PREFS.PREF_MEDITATION_LAST_PLAY_INDEX_INT, 0);
    }

    public static boolean getMeditationDownloadResult(String name) {
        return sSharedPref.getBoolean(name, false);
    }

    public static void setMeditationDownloadResult(String name) {
        sSharedPref.edit().putBoolean(name, true).apply();
    }

    public static long getVenusDownloadId() {
        return sSharedPref.getLong(GlobalConstant.PREFS.PREF_MEDITATION_ID_VENUS, -1L);
    }

    public static long getMomentDownloadId() {
        return sSharedPref.getLong(GlobalConstant.PREFS.PREF_MEDITATION_ID_MOMENT, -1L);
    }

    public static long getMedDownloadId() {
        return sSharedPref.getLong(GlobalConstant.PREFS.PREF_MEDITATION_ID_MED, -1L);
    }

    public static long getForestDownloadId() {
        return sSharedPref.getLong(GlobalConstant.PREFS.PREF_FOREST_ID_MED, -1L);
    }

    public static long getSeaDownloadId() {
        return sSharedPref.getLong(GlobalConstant.PREFS.PREF_SEA_ID_MED, -1L);
    }

    public static void setVenusDownloadId(long id) {
        sSharedPref.edit().putLong(GlobalConstant.PREFS.PREF_MEDITATION_ID_VENUS, id).apply();
    }

    public static void setMomentDownloadId(long id) {
        sSharedPref.edit().putLong(GlobalConstant.PREFS.PREF_MEDITATION_ID_MOMENT, id).apply();
    }

    public static void setMedDownloadId(long id) {
        sSharedPref.edit().putLong(GlobalConstant.PREFS.PREF_MEDITATION_ID_MED, id).apply();
    }

    public static void setForestDownloadId(long id) {
        sSharedPref.edit().putLong(GlobalConstant.PREFS.PREF_FOREST_ID_MED, id).apply();
    }

    public static void setSeaDownloadId(long id) {
        sSharedPref.edit().putLong(GlobalConstant.PREFS.PREF_SEA_ID_MED, id).apply();
    }

    public static void setMeditationAtlSw(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_MEDITATION_ATL_SW, enable).apply();
    }

    public static boolean getMeditationAtlSw() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_MEDITATION_ATL_SW, true);
    }

    public static String getSteerPos() {
        return getSteerPos(true);
    }

    public static String getSteerPos(boolean enalbeDefaultValue) {
        return sSharedPref.getString(GlobalConstant.PREFS.PREF_STEER_POS_STRING, enalbeDefaultValue ? GlobalConstant.DEFAULT.STEER_POS : null);
    }

    public static void setSteerPos(String pos) {
        sSharedPref.edit().putString(GlobalConstant.PREFS.PREF_STEER_POS_STRING, pos).apply();
    }

    public static void setTrailerHitchStatus(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.TTM_STATUS_BOOL, enable).apply();
    }

    public static boolean getTrailerHitchStatus() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.TTM_STATUS_BOOL, false);
    }

    public static void setEasyLoadingStatus(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.EASY_LOADING_BOOL, enable).apply();
    }

    public static boolean getEasyLoadingStatus() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.EASY_LOADING_BOOL, false);
    }

    public static void setAutoEasyLoadStatus(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.AUTO_EASY_LOAD_BOOL, enable).apply();
    }

    public static boolean getAutoEasyLoadStatus() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.AUTO_EASY_LOAD_BOOL, false);
    }

    public static boolean isTrunkSensorEnable() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_TRUNK_SENSOR_ENABLE_BOOL, true);
    }

    public static void setTrunkSensorEnable(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_TRUNK_SENSOR_ENABLE_BOOL, enable).apply();
    }

    public static int getTrunkFullOpenPos() {
        return sSharedPref.getInt(GlobalConstant.PREFS.PREF_TRUNK_OPEN_POS_INT, 6);
    }

    public static void setTrunkFullOpenPos(int position) {
        sSharedPref.edit().putInt(GlobalConstant.PREFS.PREF_TRUNK_OPEN_POS_INT, position).apply();
    }

    public static boolean isAsCustomModeEnable() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_AS_CUSTOM_ENABLE_BOOL, false);
    }

    public static void setAsCustomModeEnable(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_AS_CUSTOM_ENABLE_BOOL, enable).apply();
    }

    public static int getAsHeight() {
        return sSharedPref.getInt(GlobalConstant.PREFS.PREF_AS_HEIGHT_LVL_INT, 3);
    }

    public static void setAsHeight(int height) {
        sSharedPref.edit().putInt(GlobalConstant.PREFS.PREF_AS_HEIGHT_LVL_INT, height).apply();
    }

    public static void setAsCampingModeStatus(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_AS_CAMP_MODE_BOOL, enable).apply();
    }

    public static boolean getAsCampingModeStatus() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_AS_CAMP_MODE_BOOL, false);
    }

    public static void setAsLocationStatus(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_AS_LOCATION_SW_BOOL, enable).apply();
    }

    public static boolean getAsLocationStatus() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_AS_LOCATION_SW_BOOL, true);
    }

    public static void setAsLocationControlSw(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_AS_LOCATION_CONTROL_SW_BOOL, enable).apply();
    }

    public static boolean getAsLocationControlSw() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_AS_LOCATION_CONTROL_SW_BOOL, true);
    }

    public static void setSpecialDriveMode(int mode) {
        sSharedPref.edit().putInt(GlobalConstant.PREFS.PREF_SPEC_DRIVE_MODE_INT, mode).apply();
    }

    public static int getSpecialDriveMode() {
        return sSharedPref.getInt(GlobalConstant.PREFS.PREF_SPEC_DRIVE_MODE_INT, 0);
    }

    public static boolean isNormalDriveModeEnable() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_NORMAL_SPEC_DRIVE_MODEL_BOOL, true);
    }

    public static void setNormalDriveModeEnable(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_NORMAL_SPEC_DRIVE_MODEL_BOOL, enable).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setSdcBrakeCloseCfg(int cfg) {
        sSharedPref.edit().putInt(GlobalConstant.PREFS.PREF_SDC_BRAKE_CLOSE_CFG, cfg).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int getSdcBrakeCloseCfg() {
        return sSharedPref.getInt(GlobalConstant.PREFS.PREF_SDC_BRAKE_CLOSE_CFG, 0);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setCmsReverseSw(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_CMS_REVERSE_SW, enable).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean getCmsReverseSw() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_CMS_REVERSE_SW, true);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setCmsTurnSw(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_CMS_TURN_SW, enable).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean getCmsTurnSw() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_CMS_TURN_SW, true);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setCmsHighSpdSw(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_CMS_HIGH_SPD_SW, enable).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean getCmsHighSpdSw() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_CMS_HIGH_SPD_SW, true);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setCmsLowSpdSw(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_CMS_LOW_SPD_SW, enable).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean getCmsLowSpdSw() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_CMS_LOW_SPD_SW, true);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setCmsAutoBrightSw(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_CMS_AUTO_BRIGHT_SW, enable).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean getCmsAutoBrightSw() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_CMS_AUTO_BRIGHT_SW, true);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setCmsBright(int brightness) {
        sSharedPref.edit().putInt(GlobalConstant.PREFS.PREF_CMS_BRIGHTNESS, brightness).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int getCmsBright() {
        return sSharedPref.getInt(GlobalConstant.PREFS.PREF_CMS_BRIGHTNESS, 50);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setCmsPos(String pos) {
        sSharedPref.edit().putString(GlobalConstant.PREFS.PREF_CMS_POS, pos).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String getCmsPos() {
        return sSharedPref.getString(GlobalConstant.PREFS.PREF_CMS_POS, null);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setCmsViewAngle(int viewAngle) {
        sSharedPref.edit().putInt(GlobalConstant.PREFS.PREF_CMS_VIEW_ANGLE, viewAngle).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int getCmsViewAngle() {
        return sSharedPref.getInt(GlobalConstant.PREFS.PREF_CMS_VIEW_ANGLE, 1);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setCmsObjectRecognizeSw(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_CMS_OBJECT_RECOGNIZE_SW, enable).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean getCmsObjectRecognizeSw() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_CMS_OBJECT_RECOGNIZE_SW, true);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setCmsViewRecoverySw(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_CMS_VIEW_RECOVERY_SW, enable).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean getCmsViewRecoverySw() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_CMS_VIEW_RECOVERY_SW, true);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setImsAutoVisionSw(int state) {
        sSharedPref.edit().putInt(GlobalConstant.PREFS.PREF_IMS_AUTO_VISION_SW, state).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int getImsAutoVisionSw() {
        return sSharedPref.getInt(GlobalConstant.PREFS.PREF_IMS_AUTO_VISION_SW, 1);
    }

    static void setImsMode(int mode) {
        sSharedPref.edit().putInt(GlobalConstant.PREFS.PREF_IMS_MODE, mode).apply();
    }

    static int getImsMode() {
        return sSharedPref.getInt(GlobalConstant.PREFS.PREF_IMS_MODE, 1);
    }

    public static void setEspCstSw(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_ESP_CST_SW, enable).apply();
    }

    public static boolean getEspCstSw() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_ESP_CST_SW, false);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setEspBpfMode(int mode) {
        sSharedPref.edit().putInt(GlobalConstant.PREFS.PREF_ESP_BPF, mode).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int getEspBpfMode() {
        return sSharedPref.getInt(GlobalConstant.PREFS.PREF_ESP_BPF, 1);
    }

    public static void setDrvLumbControlSw(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_DRV_LUMB_SW, enable).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean getDrvLumbControlSw() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_DRV_LUMB_SW, false);
    }

    public static void setPsnLumbControlSw(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_PSN_LUMB_SW, enable).apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean getPsnLumbControlSw() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_PSN_LUMB_SW, false);
    }

    public static boolean isFirstOpenCngpSw() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_FIRST_OPEN_CNGP_SW_BOOL, true);
    }

    public static void setFirstOpenCngpSw(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_FIRST_OPEN_CNGP_SW_BOOL, enable).apply();
    }

    public static boolean isFirstOpenXngpSw() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_FIRST_OPEN_XNGP_SW_BOOL, true);
    }

    public static void setFirstOpenXngpSw(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_FIRST_OPEN_XNGP_SW_BOOL, enable).apply();
    }

    public static void setRearLogoLightSw(boolean enable) {
        sSharedPref.edit().putBoolean(GlobalConstant.PREFS.PREF_REAR_LOGO_LIGHT_SW, enable).apply();
    }

    public static boolean getRearLogoLightSw() {
        return sSharedPref.getBoolean(GlobalConstant.PREFS.PREF_REAR_LOGO_LIGHT_SW, false);
    }

    public static void setSpaceCapsuleUGEnterShowCheckBox(boolean isSelected) {
        sSharedPref.edit().putBoolean(GlobalConstant.SPACE.SPACE_CAPSULE_SLEEP_UG_SHOW_NEXT_TIME_ENTER, isSelected).apply();
    }

    public static boolean isSpaceCapsuleUGEnterShowCheckBoxSelected() {
        return sSharedPref.getBoolean(GlobalConstant.SPACE.SPACE_CAPSULE_SLEEP_UG_SHOW_NEXT_TIME_ENTER, false);
    }

    public static void setSpaceCapsuleUGExitShowCheckBox(boolean isSelected) {
        sSharedPref.edit().putBoolean(GlobalConstant.SPACE.SPACE_CAPSULE_SLEEP_UG_SHOW_NEXT_TIME_EXIT, isSelected).apply();
    }

    public static boolean isSpaceCapsuleUGExitShowCheckBoxSelected() {
        return sSharedPref.getBoolean(GlobalConstant.SPACE.SPACE_CAPSULE_SLEEP_UG_SHOW_NEXT_TIME_EXIT, false);
    }

    public static void setSpaceCapsuleSleepBgmOpen(boolean isOpen) {
        sSharedPref.edit().putBoolean(GlobalConstant.SPACE.SPACE_CAPSULE_SLEEP_BGM_OPEN, isOpen).apply();
    }

    public static boolean isSpaceCapsuleSleepBgmOpen() {
        return sSharedPref.getBoolean(GlobalConstant.SPACE.SPACE_CAPSULE_SLEEP_BGM_OPEN, true);
    }
}
