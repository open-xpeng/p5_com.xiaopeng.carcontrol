package com.xiaopeng.carcontrol.bean;

import android.text.TextUtils;
import com.google.gson.Gson;
import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import com.xiaopeng.carcontrol.util.LogUtils;

/* loaded from: classes.dex */
public class Mirror {
    public static final int MIRROR_POS_THRESHOLD = 2;
    private static final String TAG = "Mirror";
    public boolean isFolded;
    public int leftHPos = 50;
    public int leftVPos = 50;
    public int rightHPos = 50;
    public int rightVPos = 50;
    public int leftHPosR = BaseFeatureOption.getInstance().getDefaultMirrorReversePos()[0];
    public int leftVPosR = BaseFeatureOption.getInstance().getDefaultMirrorReversePos()[1];
    public int rightHPosR = BaseFeatureOption.getInstance().getDefaultMirrorReversePos()[2];
    public int rightVPosR = BaseFeatureOption.getInstance().getDefaultMirrorReversePos()[3];

    public String toString() {
        return new Gson().toJson(new int[]{this.leftHPos, this.leftVPos, this.rightHPos, this.rightVPos, this.leftHPosR, this.leftVPosR, this.rightHPosR, this.rightVPosR});
    }

    public static Mirror fromJson(String jsonString) {
        Mirror mirror = new Mirror();
        if (!TextUtils.isEmpty(jsonString)) {
            try {
                int[] iArr = (int[]) new Gson().fromJson(jsonString, (Class<Object>) int[].class);
                mirror.leftHPos = iArr[0];
                mirror.leftVPos = iArr[1];
                mirror.rightHPos = iArr[2];
                mirror.rightVPos = iArr[3];
                mirror.leftHPosR = iArr[4];
                mirror.leftVPosR = iArr[5];
                mirror.rightHPosR = iArr[6];
                mirror.rightVPosR = iArr[7];
            } catch (Exception e) {
                LogUtils.w(TAG, e.getMessage() + ", jsonString", false);
            }
        }
        return mirror;
    }

    public static boolean isEquals(float[] position1, float[] position2) {
        return position1 != null && position1.length >= 4 && position2 != null && position2.length >= 4 && Math.abs(position1[0] - position2[0]) <= 2.0f && Math.abs(position1[1] - position2[1]) <= 2.0f && Math.abs(position1[2] - position2[2]) <= 2.0f && Math.abs(position1[3] - position2[3]) <= 2.0f;
    }

    public static boolean isEquals(int[] position1, int[] position2) {
        return position1 != null && position1.length >= 4 && position2 != null && position2.length >= 4 && Math.abs(position1[0] - position2[0]) <= 2 && Math.abs(position1[1] - position2[1]) <= 2 && Math.abs(position1[2] - position2[2]) <= 2 && Math.abs(position1[3] - position2[3]) <= 2;
    }

    public boolean isEqualsToPosition(int[] position) {
        return Math.abs(this.leftHPos - position[0]) <= 2 && Math.abs(this.leftVPos - position[1]) <= 2 && Math.abs(this.rightHPos - position[2]) <= 2 && Math.abs(this.rightVPos - position[3]) <= 2;
    }

    public void updateAllPosition(String mirrorSavedPos) {
        if (TextUtils.isEmpty(mirrorSavedPos)) {
            return;
        }
        try {
            int[] iArr = (int[]) new Gson().fromJson(mirrorSavedPos, (Class<Object>) int[].class);
            if (iArr == null || iArr.length < 8) {
                return;
            }
            this.leftHPos = iArr[0];
            this.leftVPos = iArr[1];
            this.rightHPos = iArr[2];
            this.rightVPos = iArr[3];
            this.leftHPosR = iArr[4];
            this.leftVPosR = iArr[5];
            this.rightHPosR = iArr[6];
            this.rightVPosR = iArr[7];
        } catch (Exception e) {
            LogUtils.w(TAG, e.getMessage() + ", jsonString", false);
        }
    }

    public void updateNormalPosition(int[] position) {
        if (position == null || position.length < 4) {
            return;
        }
        this.leftHPos = position[0];
        this.leftVPos = position[1];
        this.rightHPos = position[2];
        this.rightVPos = position[3];
    }

    public int[] getMirrorPosition() {
        return new int[]{this.leftHPos, this.leftVPos, this.rightHPos, this.rightVPos};
    }
}
