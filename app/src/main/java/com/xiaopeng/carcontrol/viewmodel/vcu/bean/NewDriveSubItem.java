package com.xiaopeng.carcontrol.viewmodel.vcu.bean;

import android.text.TextUtils;

/* loaded from: classes2.dex */
public class NewDriveSubItem {
    private final String SAVED_SUB_ITEM_INTERVAL = "_";
    private int mAsHeight;
    private int mAsSoft;
    private int mEnergyRecovery;
    private int mEpsMode;
    private int mEspMode;
    private boolean mEspMudMode;
    private int mMotorPower;
    private int mPowerResponse;

    public NewDriveSubItem(String str) {
        String[] split;
        if (TextUtils.isEmpty(str) || (split = str.split("_")) == null || split.length != 8) {
            return;
        }
        this.mAsHeight = Integer.parseInt(split[0]);
        this.mAsSoft = Integer.parseInt(split[1]);
        this.mPowerResponse = Integer.parseInt(split[2]);
        this.mEnergyRecovery = Integer.parseInt(split[3]);
        this.mEpsMode = Integer.parseInt(split[4]);
        this.mEspMode = Integer.parseInt(split[5]);
        this.mEspMudMode = Integer.parseInt(split[6]) == 1;
        this.mMotorPower = Integer.parseInt(split[7]);
    }

    public int getAsHeight() {
        return this.mAsHeight;
    }

    public void setAsHeight(int asHeight) {
        this.mAsHeight = asHeight;
    }

    public int getAsSoft() {
        return this.mAsSoft;
    }

    public void setAsSoft(int asSoft) {
        this.mAsSoft = asSoft;
    }

    public int getPowerResponse() {
        return this.mPowerResponse;
    }

    public void setPowerResponse(int powerResponse) {
        this.mPowerResponse = powerResponse;
    }

    public int getEnergyRecovery() {
        return this.mEnergyRecovery;
    }

    public void setEnergyRecovery(int energyRecovery) {
        this.mEnergyRecovery = energyRecovery;
    }

    public int getEpsMode() {
        return this.mEpsMode;
    }

    public void setEpsMode(int epsMode) {
        this.mEpsMode = epsMode;
    }

    public int getEspMode() {
        return this.mEspMode;
    }

    public void setEspMode(int espMode) {
        this.mEspMode = espMode;
    }

    public boolean isEspMudMode() {
        return this.mEspMudMode;
    }

    public void setEspMudMode(boolean espMudMode) {
        this.mEspMudMode = espMudMode;
    }

    public int getMotorPower() {
        return this.mMotorPower;
    }

    public void setMotorPower(int motorPower) {
        this.mMotorPower = motorPower;
    }

    public String toSavedStr() {
        return this.mAsHeight + "_" + this.mAsSoft + "_" + this.mPowerResponse + "_" + this.mEnergyRecovery + "_" + this.mEpsMode + "_" + this.mEspMode + "_" + (this.mEspMudMode ? 1 : 0) + "_" + this.mMotorPower;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("mAsHeight=").append(this.mAsHeight);
        sb.append(", mAsSoft=").append(this.mAsSoft);
        sb.append(", mPowerResponse=").append(this.mPowerResponse);
        sb.append(", mEnergyRecovery=").append(this.mEnergyRecovery);
        sb.append(", mEpsMode=").append(this.mEpsMode);
        sb.append(", mEspMode=").append(this.mEspMode);
        sb.append(", mEspMudMode=").append(this.mEspMudMode);
        sb.append(", mMotorPower=").append(this.mMotorPower);
        return sb.toString();
    }
}
