package com.xiaopeng.speech.protocol.query.camera;

import com.xiaopeng.speech.SpeechQuery;

/* loaded from: classes2.dex */
public class CameraQuery extends SpeechQuery<ICameraCaller> {
    /* JADX INFO: Access modifiers changed from: protected */
    public int getSupportTopStatus(String str, String str2) {
        return ((ICameraCaller) this.mQueryCaller).getSupportTopStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getSupportPanoramicStatus(String str, String str2) {
        return ((ICameraCaller) this.mQueryCaller).getSupportPanoramicStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getSupportRearStatus(String str, String str2) {
        return ((ICameraCaller) this.mQueryCaller).getSupportRearStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getBusinessCameraStatus(String str, String str2) {
        return ((ICameraCaller) this.mQueryCaller).getBusinessCameraStatus(str2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getCameraThreeDSupport(String str, String str2) {
        return ((ICameraCaller) this.mQueryCaller).getCameraThreeDSupport();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getCameraTransparentChassisSupport(String str, String str2) {
        return ((ICameraCaller) this.mQueryCaller).getCameraTransparentChassisSupport();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isCameraRecording(String str, String str2) {
        return ((ICameraCaller) this.mQueryCaller).isCameraRecording();
    }
}
