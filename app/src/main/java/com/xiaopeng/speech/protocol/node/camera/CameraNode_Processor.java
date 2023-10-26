package com.xiaopeng.speech.protocol.node.camera;

import com.alibaba.fastjson.parser.JSONLexer;
import com.xiaopeng.speech.annotation.ICommandProcessor;
import com.xiaopeng.speech.protocol.event.CameraEvent;

/* loaded from: classes2.dex */
public class CameraNode_Processor implements ICommandProcessor {
    private CameraNode mTarget;

    public CameraNode_Processor(CameraNode cameraNode) {
        this.mTarget = cameraNode;
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public void performCommand(String str, String str2) {
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case -1960069370:
                if (str.equals(CameraEvent.FRONT_TAKE)) {
                    c = 0;
                    break;
                }
                break;
            case -1876486015:
                if (str.equals(CameraEvent.CAMERA_PHOTOALBUM_OPEN)) {
                    c = 1;
                    break;
                }
                break;
            case -1734758414:
                if (str.equals(CameraEvent.TOP_ON)) {
                    c = 2;
                    break;
                }
                break;
            case -1275719440:
                if (str.equals(CameraEvent.TRANSPARENT_CHASSIS_CMD)) {
                    c = 3;
                    break;
                }
                break;
            case -1212502159:
                if (str.equals(CameraEvent.CAMERA_TRANSPARENT_CHASSIS_OFF)) {
                    c = 4;
                    break;
                }
                break;
            case -1080240309:
                if (str.equals(CameraEvent.RIGHT_ON)) {
                    c = 5;
                    break;
                }
                break;
            case -1041501812:
                if (str.equals(CameraEvent.LEFT_ON)) {
                    c = 6;
                    break;
                }
                break;
            case -886954978:
                if (str.equals(CameraEvent.FRONT_ON)) {
                    c = 7;
                    break;
                }
                break;
            case -655385126:
                if (str.equals(CameraEvent.TOP_TAKE)) {
                    c = '\b';
                    break;
                }
                break;
            case -499850625:
                if (str.equals(CameraEvent.REAR_OFF)) {
                    c = '\t';
                    break;
                }
                break;
            case -392525311:
                if (str.equals(CameraEvent.REAR_ON_NEW)) {
                    c = '\n';
                    break;
                }
                break;
            case -313369279:
                if (str.equals(CameraEvent.REAR_RECORD)) {
                    c = 11;
                    break;
                }
                break;
            case -155721484:
                if (str.equals(CameraEvent.LEFT_TAKE)) {
                    c = '\f';
                    break;
                }
                break;
            case -16124209:
                if (str.equals(CameraEvent.REAR_ON)) {
                    c = '\r';
                    break;
                }
                break;
            case 184229895:
                if (str.equals(CameraEvent.TOP_ROTATE_LEFT)) {
                    c = 14;
                    break;
                }
                break;
            case 184408484:
                if (str.equals(CameraEvent.TOP_ROTATE_REAR)) {
                    c = 15;
                    break;
                }
                break;
            case 446223610:
                if (str.equals(CameraEvent.OVERALL_ON)) {
                    c = 16;
                    break;
                }
                break;
            case 621719934:
                if (str.equals(CameraEvent.LEFT_RECORD)) {
                    c = 17;
                    break;
                }
                break;
            case 1207813021:
                if (str.equals(CameraEvent.CAMERA_TRANSPARENT_CHASSIS_ON)) {
                    c = 18;
                    break;
                }
                break;
            case 1271288563:
                if (str.equals(CameraEvent.RIGHT_TAKE)) {
                    c = 19;
                    break;
                }
                break;
            case 1411014185:
                if (str.equals(CameraEvent.TOP_ROTATE_FRONT)) {
                    c = 20;
                    break;
                }
                break;
            case 1421820444:
                if (str.equals(CameraEvent.TOP_ROTATE_RIGHT)) {
                    c = 21;
                    break;
                }
                break;
            case 1584286675:
                if (str.equals(CameraEvent.CAMERA_THREE_D_ON)) {
                    c = 22;
                    break;
                }
                break;
            case 1684644215:
                if (str.equals(CameraEvent.REAR_TAKE)) {
                    c = 23;
                    break;
                }
                break;
            case 1810189072:
                if (str.equals(CameraEvent.FRONT_RECORD)) {
                    c = 24;
                    break;
                }
                break;
            case 1868246523:
                if (str.equals(CameraEvent.CAMERA_THREE_D_OFF)) {
                    c = 25;
                    break;
                }
                break;
            case 1883807677:
                if (str.equals(CameraEvent.RIGHT_RECORD)) {
                    c = JSONLexer.EOI;
                    break;
                }
                break;
            case 2057063868:
                if (str.equals(CameraEvent.TOP_OFF)) {
                    c = 27;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                this.mTarget.onFrontTake(str, str2);
                return;
            case 1:
                this.mTarget.onCameraPhotoalbumOpen(str, str2);
                return;
            case 2:
                this.mTarget.onTopOn(str, str2);
                return;
            case 3:
                this.mTarget.onTransparentChassisCMD(str, str2);
                return;
            case 4:
                this.mTarget.onCameraTransparentChassisOff(str, str2);
                return;
            case 5:
                this.mTarget.onRightOn(str, str2);
                return;
            case 6:
                this.mTarget.onLeftOn(str, str2);
                return;
            case 7:
                this.mTarget.onFrontOn(str, str2);
                return;
            case '\b':
                this.mTarget.onTopTake(str, str2);
                return;
            case '\t':
                this.mTarget.onRearOff(str, str2);
                return;
            case '\n':
                this.mTarget.onRearOnNew(str, str2);
                return;
            case 11:
                this.mTarget.onRearRecord(str, str2);
                return;
            case '\f':
                this.mTarget.onLeftTake(str, str2);
                return;
            case '\r':
                this.mTarget.onRearOn(str, str2);
                return;
            case 14:
                this.mTarget.onTopRotateLeft(str, str2);
                return;
            case 15:
                this.mTarget.onTopRotateRear(str, str2);
                return;
            case 16:
                this.mTarget.onOverallOn(str, str2);
                return;
            case 17:
                this.mTarget.onLeftRecord(str, str2);
                return;
            case 18:
                this.mTarget.onCameraTransparentChassisOn(str, str2);
                return;
            case 19:
                this.mTarget.onRightTake(str, str2);
                return;
            case 20:
                this.mTarget.onTopRotateFront(str, str2);
                return;
            case 21:
                this.mTarget.onTopRotateRight(str, str2);
                return;
            case 22:
                this.mTarget.onCameraThreeDOn(str, str2);
                return;
            case 23:
                this.mTarget.onRearTake(str, str2);
                return;
            case 24:
                this.mTarget.onFrontRecord(str, str2);
                return;
            case 25:
                this.mTarget.onCameraThreeDOff(str, str2);
                return;
            case 26:
                this.mTarget.onRightRecord(str, str2);
                return;
            case 27:
                this.mTarget.onTopOff(str, str2);
                return;
            default:
                return;
        }
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public String[] getSubscribeEvents() {
        return new String[]{CameraEvent.OVERALL_ON, CameraEvent.REAR_TAKE, CameraEvent.REAR_RECORD, CameraEvent.FRONT_TAKE, CameraEvent.FRONT_RECORD, CameraEvent.LEFT_TAKE, CameraEvent.LEFT_RECORD, CameraEvent.RIGHT_TAKE, CameraEvent.RIGHT_RECORD, CameraEvent.LEFT_ON, CameraEvent.RIGHT_ON, CameraEvent.REAR_ON, CameraEvent.REAR_ON_NEW, CameraEvent.FRONT_ON, CameraEvent.REAR_OFF, CameraEvent.TOP_OFF, CameraEvent.TOP_ON, CameraEvent.TOP_TAKE, CameraEvent.TOP_ROTATE_LEFT, CameraEvent.TOP_ROTATE_RIGHT, CameraEvent.TOP_ROTATE_FRONT, CameraEvent.TOP_ROTATE_REAR, CameraEvent.TRANSPARENT_CHASSIS_CMD, CameraEvent.CAMERA_THREE_D_ON, CameraEvent.CAMERA_THREE_D_OFF, CameraEvent.CAMERA_TRANSPARENT_CHASSIS_ON, CameraEvent.CAMERA_TRANSPARENT_CHASSIS_OFF, CameraEvent.CAMERA_PHOTOALBUM_OPEN};
    }
}
