package com.xiaopeng.carcontrol.view.unity;

/* loaded from: classes2.dex */
public interface IUnityControl {
    public static final int UNITY_FREE_VIEW_ANGLE = 1;
    public static final int UNITY_LAMP_VIEW_ANGLE = 2;
    public static final int UNITY_MIRROR_VIEW_ANGLE = 5;
    public static final int UNITY_WIN_L_VIEW_ANGLE = 3;
    public static final int UNITY_WIN_R_VIEW_ANGLE = 4;
    public static final int UNITY_WIPER_VIEW_ANGLE = 6;

    void switchUnityAngle(int viewAngle);
}
