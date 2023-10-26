package com.xiaopeng.speech.protocol.query.speech.camera;

import com.xiaopeng.speech.annotation.IQueryProcessor;
import com.xiaopeng.speech.protocol.event.query.speech.SpeechCameraEvent;

/* loaded from: classes2.dex */
public class SpeechCameraQuery_Processor implements IQueryProcessor {
    private SpeechCameraQuery mTarget;

    public SpeechCameraQuery_Processor(SpeechCameraQuery speechCameraQuery) {
        this.mTarget = speechCameraQuery;
    }

    @Override // com.xiaopeng.speech.annotation.IQueryProcessor
    public Object querySensor(String str, String str2) {
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case -2010416950:
                if (str.equals(SpeechCameraEvent.CAMERA_ANGLE)) {
                    c = 0;
                    break;
                }
                break;
            case 137950515:
                if (str.equals(SpeechCameraEvent.CAMERA_HEIGHT_STATE)) {
                    c = 1;
                    break;
                }
                break;
            case 753266552:
                if (str.equals(SpeechCameraEvent.CAMERA_DISPLAY_MODE)) {
                    c = 2;
                    break;
                }
                break;
            case 1558434298:
                if (str.equals(SpeechCameraEvent.CAMERA_ROOF_POSITION)) {
                    c = 3;
                    break;
                }
                break;
            case 1886102880:
                if (str.equals(SpeechCameraEvent.CAMERA_ROOF_STATE)) {
                    c = 4;
                    break;
                }
                break;
            case 1936425711:
                if (str.equals(SpeechCameraEvent.CAMERA_HAS_PANO)) {
                    c = 5;
                    break;
                }
                break;
            case 1936498769:
                if (str.equals(SpeechCameraEvent.CAMERA_HAS_ROOF)) {
                    c = 6;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                return Integer.valueOf(this.mTarget.getCameraAngle(str, str2));
            case 1:
                return Boolean.valueOf(this.mTarget.getCameraHeight(str, str2));
            case 2:
                return Integer.valueOf(this.mTarget.getCameraDisplayMode(str, str2));
            case 3:
                return Integer.valueOf(this.mTarget.getRoofCameraPosition(str, str2));
            case 4:
                return Integer.valueOf(this.mTarget.getRoofCameraState(str, str2));
            case 5:
                return Boolean.valueOf(this.mTarget.getHasPanoCamera(str, str2));
            case 6:
                return Boolean.valueOf(this.mTarget.getHasRoofCamera(str, str2));
            default:
                return null;
        }
    }

    @Override // com.xiaopeng.speech.annotation.IQueryProcessor
    public String[] getQueryEvents() {
        return new String[]{SpeechCameraEvent.CAMERA_ANGLE, SpeechCameraEvent.CAMERA_HEIGHT_STATE, SpeechCameraEvent.CAMERA_DISPLAY_MODE, SpeechCameraEvent.CAMERA_HAS_PANO, SpeechCameraEvent.CAMERA_HAS_ROOF, SpeechCameraEvent.CAMERA_ROOF_STATE, SpeechCameraEvent.CAMERA_ROOF_POSITION};
    }
}
