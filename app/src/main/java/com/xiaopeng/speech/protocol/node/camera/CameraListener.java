package com.xiaopeng.speech.protocol.node.camera;

import com.xiaopeng.speech.INodeListener;
import com.xiaopeng.speech.protocol.bean.ChangeValue;

/* loaded from: classes2.dex */
public interface CameraListener extends INodeListener {
    void onCameraPhotoalbumOpen();

    void onCameraThreeDOff();

    void onCameraThreeDOn();

    void onCameraTransparentChassisOff();

    void onCameraTransparentChassisOn();

    void onCarcorderLock();

    void onCarcorderRecord();

    void onCarcorderTake();

    void onFrontOn();

    void onFrontRecord();

    void onFrontTake();

    void onLeftOn();

    void onLeftRecord();

    void onLeftTake();

    void onOverallOn(boolean z);

    void onRearOff();

    void onRearOn();

    void onRearOnNew();

    void onRearRecord(boolean z);

    void onRearTake(boolean z);

    void onRightOn();

    void onRightRecord();

    void onRightTake();

    void onTopOff(boolean z);

    void onTopOn(boolean z);

    void onTopRecord();

    void onTopRecordEnd();

    void onTopRotateFront(boolean z);

    void onTopRotateLeft(ChangeValue changeValue, boolean z);

    void onTopRotateRear(boolean z);

    void onTopRotateRight(ChangeValue changeValue, boolean z);

    void onTopTake();

    void onTransparentChassisCMD(String str);
}
