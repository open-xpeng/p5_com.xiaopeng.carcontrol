package com.xiaopeng.carcontrol.model;

import android.content.SharedPreferences;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.util.LogUtils;

/* loaded from: classes2.dex */
public class HvacDataModel {
    public static final String KEY_HVAC_MODE_STATE_AC = "hvac_mode_state_ac";
    public static final String KEY_HVAC_MODE_STATE_AUTO = "hvac_mode_state_auto";
    public static final String KEY_HVAC_MODE_STATE_CIRCULATION_MODE = "hvac_mode_state_circulation_mode";
    public static final String KEY_HVAC_MODE_STATE_DRV_TEMP = "hvac_mode_state_drv_temp";
    public static final String KEY_HVAC_MODE_STATE_PSN_SEAT_HEAT = "hvac_mode_state_psn_seat_heat";
    public static final String KEY_HVAC_MODE_STATE_PSN_SEAT_VENT = "hvac_mode_state_psn_seat_vent";
    public static final String KEY_HVAC_MODE_STATE_PSN_TEMP = "hvac_mode_state_psn_temp";
    public static final String KEY_HVAC_MODE_STATE_RL_SEAT_HEAT = "hvac_mode_state_rl_seat_heat";
    public static final String KEY_HVAC_MODE_STATE_RR_SEAT_HEAT = "hvac_mode_state_rr_seat_heat";
    public static final String KEY_HVAC_MODE_STATE_SEAT_HEAT = "hvac_mode_state_seat_heat";
    public static final String KEY_HVAC_MODE_STATE_SEAT_VENT = "hvac_mode_state_seat_vent";
    public static final String KEY_HVAC_MODE_STATE_SWITCH = "hvac_mode_state_switch";
    public static final String KEY_HVAC_MODE_STATE_WIND_BLOW_MODE = "hvac_mode_state_wind_blow_mode";
    public static final String KEY_HVAC_MODE_STATE_WIND_LEVEL = "hvac_mode_state_wind_level";
    private static final String SP_FILE_NAME = "hvac_mode_sp";
    private static final String TAG = "HvacDataModel";
    private final SharedPreferences mSp;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class LazyHolder {
        private static HvacDataModel sInstance = new HvacDataModel();

        private LazyHolder() {
        }
    }

    public static HvacDataModel getInstance() {
        return LazyHolder.sInstance;
    }

    private HvacDataModel() {
        this.mSp = App.getInstance().getSharedPreferences(SP_FILE_NAME, 0);
    }

    public void setHvacModeStateDvrTemp(float state) {
        this.mSp.edit().putFloat(KEY_HVAC_MODE_STATE_DRV_TEMP, state).apply();
    }

    public float getHvacModeStateDvrTemp() {
        float f = this.mSp.getFloat(KEY_HVAC_MODE_STATE_DRV_TEMP, 18.0f);
        LogUtils.d(TAG, "dvrTemp: " + f, false);
        return f;
    }

    public void setHvacModeStatePsnTemp(float state) {
        this.mSp.edit().putFloat(KEY_HVAC_MODE_STATE_PSN_TEMP, state).apply();
    }

    public float getHvacModeStatePsnTemp() {
        float f = this.mSp.getFloat(KEY_HVAC_MODE_STATE_PSN_TEMP, 18.0f);
        LogUtils.d(TAG, "psmTemp: " + f, false);
        return f;
    }

    public void setHvacModeStateWindLevel(int state) {
        LogUtils.d(TAG, "set windLevel: " + state, false);
        this.mSp.edit().putInt(KEY_HVAC_MODE_STATE_WIND_LEVEL, state).apply();
    }

    public int getHvacModeStateWindLevel() {
        int i = this.mSp.getInt(KEY_HVAC_MODE_STATE_WIND_LEVEL, 0);
        LogUtils.d(TAG, "windLevel: " + i, false);
        return i;
    }

    public void setHvacModeStateWindBlowMode(int state) {
        LogUtils.d(TAG, "set windBlowMode: " + state, false);
        this.mSp.edit().putInt(KEY_HVAC_MODE_STATE_WIND_BLOW_MODE, state).apply();
    }

    public int getHvacModeStateWindBlowMode() {
        int i = this.mSp.getInt(KEY_HVAC_MODE_STATE_WIND_BLOW_MODE, 0);
        LogUtils.d(TAG, "windBlowMode: " + i, false);
        return i;
    }

    public void setHvacModeStateAutoMode(boolean state) {
        this.mSp.edit().putBoolean(KEY_HVAC_MODE_STATE_AUTO, state).apply();
    }

    public boolean getHvacModeStateAutoMode() {
        boolean z = this.mSp.getBoolean(KEY_HVAC_MODE_STATE_AUTO, false);
        LogUtils.d(TAG, "autoMode: " + z, false);
        return z;
    }

    public void setHvacModeStateACMode(boolean state) {
        this.mSp.edit().putBoolean(KEY_HVAC_MODE_STATE_AC, state).apply();
    }

    public boolean getHvacModeStateAcMode() {
        boolean z = this.mSp.getBoolean(KEY_HVAC_MODE_STATE_AC, false);
        LogUtils.d(TAG, "acMode: " + z, false);
        return z;
    }

    public void setHvacModeStateCirculationMode(int state) {
        this.mSp.edit().putInt(KEY_HVAC_MODE_STATE_CIRCULATION_MODE, state).apply();
    }

    public int getHvacModeStateCirculationMode() {
        int i = this.mSp.getInt(KEY_HVAC_MODE_STATE_CIRCULATION_MODE, 0);
        LogUtils.d(TAG, "circulationMode: " + i, false);
        return i;
    }

    public void setHvacModeStateSeatVent(int state) {
        this.mSp.edit().putInt(KEY_HVAC_MODE_STATE_SEAT_VENT, state).apply();
    }

    public int getHvacModeStateSeatVent() {
        int i = this.mSp.getInt(KEY_HVAC_MODE_STATE_SEAT_VENT, 0);
        LogUtils.d(TAG, "Vent seatLevel: " + i, false);
        return i;
    }

    public void setHvacModeStatePsnSeatVent(int state) {
        this.mSp.edit().putInt(KEY_HVAC_MODE_STATE_PSN_SEAT_VENT, state).apply();
    }

    public int getHvacModeStatePsnSeatVent() {
        int i = this.mSp.getInt(KEY_HVAC_MODE_STATE_PSN_SEAT_VENT, 0);
        LogUtils.d(TAG, "PsnVent seatLevel: " + i, false);
        return i;
    }

    public void setHvacModeStateSeatHeat(int state) {
        this.mSp.edit().putInt(KEY_HVAC_MODE_STATE_SEAT_HEAT, state).apply();
    }

    public int getHvacModeStateSeatHeat() {
        int i = this.mSp.getInt(KEY_HVAC_MODE_STATE_SEAT_HEAT, 0);
        LogUtils.d(TAG, "Heat seatLevel: " + i, false);
        return i;
    }

    public void setHvacModeStatePsnSeatHeat(int state) {
        this.mSp.edit().putInt(KEY_HVAC_MODE_STATE_PSN_SEAT_HEAT, state).apply();
    }

    public int getHvacModeStatePsnSeatHeat() {
        int i = this.mSp.getInt(KEY_HVAC_MODE_STATE_PSN_SEAT_HEAT, 0);
        LogUtils.d(TAG, "PsnHeat seatLevel: " + i, false);
        return i;
    }

    public void setHvacModeStateRlSeatHeat(int state) {
        this.mSp.edit().putInt(KEY_HVAC_MODE_STATE_RL_SEAT_HEAT, state).apply();
    }

    public int getHvacModeStateRlSeatHeat() {
        int i = this.mSp.getInt(KEY_HVAC_MODE_STATE_RL_SEAT_HEAT, 0);
        LogUtils.d(TAG, "RlHeat seatLevel: " + i, false);
        return i;
    }

    public void setHvacModeStateRrSeatHeat(int state) {
        this.mSp.edit().putInt(KEY_HVAC_MODE_STATE_RR_SEAT_HEAT, state).apply();
    }

    public int getHvacModeStateRrSeatHeat() {
        int i = this.mSp.getInt(KEY_HVAC_MODE_STATE_RR_SEAT_HEAT, 0);
        LogUtils.d(TAG, "RrHeat seatLevel: " + i, false);
        return i;
    }

    public boolean getHvacModeDataSwitch() {
        boolean z = this.mSp.getBoolean(KEY_HVAC_MODE_STATE_SWITCH, false);
        LogUtils.d(TAG, "modeDataSwitch: " + z, false);
        return z;
    }

    public void setHvacModeDataSwitch(boolean state) {
        this.mSp.edit().putBoolean(KEY_HVAC_MODE_STATE_SWITCH, state).apply();
    }
}
