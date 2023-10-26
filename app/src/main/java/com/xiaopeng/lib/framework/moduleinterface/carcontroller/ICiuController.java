package com.xiaopeng.lib.framework.moduleinterface.carcontroller;

/* loaded from: classes2.dex */
public interface ICiuController extends ILifeCycle {
    public static final int AUTO_LOCK_ST_FAIL = 3;
    public static final int AUTO_LOCK_ST_NO_REQUES = 0;
    public static final int AUTO_LOCK_ST_PROCESS = 1;
    public static final int AUTO_LOCK_ST_SUCESS = 2;
    public static final int CIU_ACTIVATE_DOING = 1;
    public static final int CIU_ACTIVATE_FAIL = 2;
    public static final int CIU_ACTIVATE_NONE = 0;
    public static final int CIU_ACTIVATE_SUCCESS = 3;
    public static final int CIU_DELAY_OFF_CONFIGURE = 2;
    public static final int CIU_DELAY_OFF_NONE = 0;
    public static final int CIU_DELAY_OFF_UPLOAD = 1;
    public static final int CIU_INVALID = 0;
    public static final int CIU_MODE_DATA_UPLOAD = 3;
    public static final int CIU_MODE_DELIVERY_CONFIGURATION = 1;
    public static final int CIU_MODE_DELIVERY_CONFIGURATION_SUCCESS = 2;
    public static final int CIU_MODE_NONE = 0;
    public static final int CIU_RAIN_SW_AUTO = 0;
    public static final int CIU_RAIN_SW_LEVEL1 = 1;
    public static final int CIU_RAIN_SW_LEVEL2 = 2;
    public static final int CIU_RAIN_SW_LEVEL3 = 3;
    public static final int CIU_RAIN_SW_LEVEL4 = 4;
    public static final int CIU_VALID = 1;
    public static final int DELETE_ALL = 0;
    public static final int DELETE_RESULT_FAIL = 1;
    public static final int DELETE_RESULT_NOT_ACTIVE = 0;
    public static final int DELETE_RESULT_SUCCESS = 2;
    public static final int DELETE_UID = 1;
    public static final int DISTRACTIONSTATUS_OFF = 0;
    public static final int DISTRACTIONSTATUS_ON = 1;
    public static final int DISTRACT_STATUS_FAULT = 3;
    public static final int DISTRACT_STATUS_NOT_ACTIVE = 0;
    public static final int DISTRACT_STATUS_OFF = 2;
    public static final int DISTRACT_STATUS_ON = 1;
    public static final int DMS_MODE_EXTENDED = 1;
    public static final int DMS_MODE_NORMAL = 0;
    public static final int DMS_STATE_OFF = 0;
    public static final int DMS_STATE_ON = 1;
    public static final int FACE_ACTION_REQUEST_DOWN = 5;
    public static final int FACE_ACTION_REQUEST_FRONT = 1;
    public static final int FACE_ACTION_REQUEST_LEFT = 2;
    public static final int FACE_ACTION_REQUEST_NO_REQUEST = 0;
    public static final int FACE_ACTION_REQUEST_RIGHT = 3;
    public static final int FACE_ACTION_REQUEST_UP = 4;
    public static final int FACE_ID_ERROR_TYPE_NO_ERROR = 0;
    public static final int FACE_ID_ERROR_TYPE_REPEAT_FACE = 2;
    public static final int FACE_ID_ERROR_TYPE_REPEAT_UID = 1;
    public static final int FACE_ID_MODE_NORMAL = 1;
    public static final int FACE_ID_MODE_PRIMAL = 0;
    public static final int FACE_ID_STATUS_FAIL = 1;
    public static final int FACE_ID_STATUS_OVERTIME = 2;
    public static final int FACE_ID_STATUS_SUCCESS = 0;
    public static final int FACE_ID_SW_STATUS_FAULT = 2;
    public static final int FACE_ID_SW_STATUS_OFF = 0;
    public static final int FACE_ID_SW_STATUS_ON = 1;
    public static final int FACE_STORAGE_OVERTIME = 2;
    public static final int FACE_STORAGE_PROCESS = 0;
    public static final int FACE_STORAGE_SUCCEED = 1;
    public static final int FATIGUESTATUS_OFF = 0;
    public static final int FATIGUESTATUS_ON = 1;
    public static final int FATIGUE_STATUS_FAULT = 3;
    public static final int FATIGUE_STATUS_NOT_ACTIVE = 0;
    public static final int FATIGUE_STATUS_OFF = 2;
    public static final int FATIGUE_STATUS_ON = 1;
    public static final int FAT_IG_STATUS_FAULT = 2;
    public static final int FAT_IG_STATUS_OFF = 0;
    public static final int FAT_IG_STATUS_ON = 1;

    /* loaded from: classes2.dex */
    public static class CiuAutoLockStEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class CiuCarWashEventMsg extends AbstractEventMsg<Boolean> {
    }

    /* loaded from: classes2.dex */
    public static class CiuConfigurationActiveEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class CiuDVREnableEventMsg extends AbstractEventMsg<Boolean> {
    }

    /* loaded from: classes2.dex */
    public static class CiuDVRFormatSdEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class CiuDVRLockFBEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class CiuDVRLokEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class CiuDVRModeEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class CiuDVRStatusEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class CiuDelayOffEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class CiuDeliveryUploadModeEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class CiuPhotoProcessEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class CiuRainSwEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class CiuSdCompatEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class CiuSdStEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class CiuValidEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class CiuVideoOutputEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class CiuVideoTimeLenEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class DeleteResultEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class DistractionLevelEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class DistractionStatusEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class DmsStatusEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class ErrorTypeEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class FaceActionEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class FaceIdPrimalStatusEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class FaceIdStatusEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class FaceIdSwEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class FaceShieldEventMsg extends AbstractEventMsg<Boolean> {
    }

    /* loaded from: classes2.dex */
    public static class FaceUIdEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class FatIgStatusEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class FatigueLevelEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class FatigueStatusEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class LightIntensityEventMsg extends AbstractEventMsg<Integer> {
    }

    int getCiuAutoLockSt() throws Exception;

    boolean getCiuCarWash() throws Exception;

    int getCiuConfigurationActive() throws Exception;

    int getCiuDelayOff() throws Exception;

    int getCiuDeliveryUploadMode() throws Exception;

    int getCiuRainSw() throws Exception;

    int getCiuValid() throws Exception;

    int getDVRFormatStatus() throws Exception;

    int getDVRStatus() throws Exception;

    int getDeleteResult() throws Exception;

    int getDistractionLevel() throws Exception;

    int getDistractionStatus() throws Exception;

    int getDmsState() throws Exception;

    int getDvrLockFB() throws Exception;

    int getDvrMode() throws Exception;

    int getErrorType() throws Exception;

    int getFaceAction() throws Exception;

    int getFaceIdPrimalStatus() throws Exception;

    int getFaceIdStatus() throws Exception;

    int getFaceIdSw() throws Exception;

    boolean getFaceShield() throws Exception;

    int getFatIgStatus() throws Exception;

    int getFatigueLevel() throws Exception;

    int getFatigueStatus() throws Exception;

    int getLightIntensity() throws Exception;

    int getSdStatus() throws Exception;

    int getSdcardStatus() throws Exception;

    int getUid() throws Exception;

    boolean isDvrEnable() throws Exception;

    void photoProcess() throws Exception;

    void setCiuCarWash(boolean z) throws Exception;

    void setCiuConfigurationActive(int i) throws Exception;

    void setCiuDelayOff(int i) throws Exception;

    void setCiuDeliveryUploadMode(int i) throws Exception;

    void setCiuRainSw(int i) throws Exception;

    void setDeleteFaceId(int i) throws Exception;

    void setDeleteMulti(int i, int i2) throws Exception;

    void setDistractionStatus(int i) throws Exception;

    void setDmsMode(int i) throws Exception;

    void setDmsStatus(int i) throws Exception;

    void setDmsStatus(int i, int i2, int i3, int i4) throws Exception;

    void setDvrEnable(boolean z) throws Exception;

    void setDvrLockMode(int i) throws Exception;

    void setDvrMode(int i) throws Exception;

    void setFaceActionRequest(int i) throws Exception;

    void setFaceIdMode(int i) throws Exception;

    void setFaceIdSw(int i) throws Exception;

    void setFatIgStatus(int i) throws Exception;

    void setFatigueStatus(int i) throws Exception;

    void setFirmFaceCancel(int i) throws Exception;

    void setFormatMode(int i) throws Exception;

    void setNotifyCiuAutoLightStatus(boolean z) throws Exception;

    void setRegHint(int i) throws Exception;

    void setRegisterRequestMulti(int i, int i2, int i3) throws Exception;

    void setStartRegFlag(int i) throws Exception;

    void setStartRegFlow(int i) throws Exception;

    void setUid(int i) throws Exception;

    void setVideoOutputMode(int i) throws Exception;

    void setVideoTimeLenMode(int i) throws Exception;
}
