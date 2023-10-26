package com.xiaopeng.speech.protocol.query.camera;

import com.xiaopeng.speech.IQueryCaller;

/* loaded from: classes2.dex */
public interface ICameraCaller extends IQueryCaller {
    int getBusinessCameraStatus(String str);

    int getCameraThreeDSupport();

    int getCameraTransparentChassisSupport();

    int getSupportPanoramicStatus();

    int getSupportRearStatus();

    int getSupportTopStatus();

    default boolean isCameraRecording() {
        return false;
    }
}
