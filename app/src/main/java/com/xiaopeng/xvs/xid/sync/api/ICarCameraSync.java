package com.xiaopeng.xvs.xid.sync.api;

/* loaded from: classes2.dex */
public interface ICarCameraSync {
    public static final String KEY_BEV_MODE = "CAMERA_KEY_BEV_MODE";
    public static final String KEY_CAR_DRIVING_SCENE_NRA_LEVEL = "KEY_CAR_DRIVING_SCENE_NRA_LEVEL";
    public static final String KEY_TRANS_BODY = "CAMERA_KEY_TRANS_BODY";
    public static final String KEY_TRANS_CHASSIS = "CAMERA_KEY_TRANS_CHASSIS";
    public static final String KEY_TURN_ASSISTANT_SW = "TURN_ASSISTANT_SW";

    String getBevMode(String str);

    String getCarDrivingSceneNRALevel(String str);

    String getTransBody(String str);

    String getTransChassis(String str);

    String getTurnAssistantSW(String str);

    void putBevMode(String str);

    void putTransBody(String str);

    void putTransChassis(String str);

    void putTurnAssistantSW(String str);

    void setCarDrivingSceneNRALevel(String str);
}
