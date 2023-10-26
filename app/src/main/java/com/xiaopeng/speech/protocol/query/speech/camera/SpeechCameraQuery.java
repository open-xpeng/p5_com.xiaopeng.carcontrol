package com.xiaopeng.speech.protocol.query.speech.camera;

import com.xiaopeng.speech.SpeechQuery;

/* loaded from: classes2.dex */
public class SpeechCameraQuery extends SpeechQuery<ISpeechCameraQueryCaller> {
    /* JADX INFO: Access modifiers changed from: protected */
    public int getCameraAngle(String str, String str2) {
        return ((ISpeechCameraQueryCaller) this.mQueryCaller).getCameraAngle();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean getCameraHeight(String str, String str2) {
        return ((ISpeechCameraQueryCaller) this.mQueryCaller).getCameraHeight();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getCameraDisplayMode(String str, String str2) {
        return ((ISpeechCameraQueryCaller) this.mQueryCaller).getCameraDisplayMode();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean getHasPanoCamera(String str, String str2) {
        return ((ISpeechCameraQueryCaller) this.mQueryCaller).getHasPanoCamera();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean getHasRoofCamera(String str, String str2) {
        return ((ISpeechCameraQueryCaller) this.mQueryCaller).getHasRoofCamera();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getRoofCameraState(String str, String str2) {
        return ((ISpeechCameraQueryCaller) this.mQueryCaller).getRoofCameraState();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getRoofCameraPosition(String str, String str2) {
        return ((ISpeechCameraQueryCaller) this.mQueryCaller).getRoofCameraPosition();
    }
}
