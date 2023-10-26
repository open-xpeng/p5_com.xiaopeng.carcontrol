package com.xiaopeng.speech.protocol.query.speech.camera;

import com.xiaopeng.speech.IQueryCaller;

/* loaded from: classes2.dex */
public interface ISpeechCameraQueryCaller extends IQueryCaller {
    int getCameraAngle();

    int getCameraDisplayMode();

    boolean getCameraHeight();

    boolean getHasPanoCamera();

    boolean getHasRoofCamera();

    int getRoofCameraPosition();

    int getRoofCameraState();
}
