package com.xiaopeng.carcontrol.viewmodel.cabin;

import com.xiaopeng.carcontrol.viewmodel.IBaseViewModel;

/* loaded from: classes2.dex */
public interface IMirrorViewModel extends IBaseViewModel {
    void cancelAllControlMirror();

    boolean checkCurPos();

    void checkRecoverMirror(float speed);

    void controlMirror(boolean isFold);

    void controlMirrorMoveOld(boolean isLeft, MirrorDirection direction, boolean isLongControl);

    void enterScene();

    void exitScene();

    boolean getCmsAutoBrightSw();

    int getCmsBright();

    boolean getCmsHighSpdAssistSw();

    boolean getCmsLowSpdAssistSw();

    boolean getCmsObjectRecognizeSw();

    boolean getCmsReverseAssistSw();

    boolean getCmsTurnAssistSw();

    int getCmsViewAngle();

    boolean getImsAutoVisionSw();

    int getImsBrightLevel();

    int getImsMode();

    int getImsSystemSt();

    int getImsVisionAngleLevel();

    int getImsVisionVerticalLevel();

    boolean getLMReverseSw();

    MirrorFoldState getMirrorFoldState();

    boolean getRMReverseSw();

    MirrorReverseMode getReverseMirrorMode();

    boolean isMirrorAutoDown();

    boolean isMirrorAutoFoldEnable();

    boolean onKey(int keyCode, int action);

    boolean onKeyIms(int keyCode, int action);

    void recoverMirror();

    void resetMirrorFoldState();

    void resetRecoverMirrorFlag();

    void reverseMirror();

    void setCmsAutoBrightSw(boolean enable);

    void setCmsBright(int brightness);

    void setCmsHighSpdAssistSw(boolean enable);

    void setCmsLowSpdAssistSw(boolean enable);

    void setCmsObjectRecognizeSw(boolean enable);

    void setCmsReverseAssistSw(boolean enable);

    void setCmsTurnAssistSw(boolean enable);

    void setCmsViewAngle(int viewAngle);

    void setCmsViewRecovery(boolean enable);

    void setImsAutoVisionSw(boolean sw);

    void setImsBrightLevel(int level);

    void setImsMode(int mode);

    void setImsVisionCtrl(int control, int direction);

    void setLMReverseSw(boolean on);

    void setMirrorAutoDown(boolean enable);

    void setMirrorAutoFoldEnable(boolean enable);

    void setRMReverseSw(boolean on);

    void setReverseMirrorMode(int mode);

    @Deprecated
    boolean shouldReverseMirror();

    void showMirrorCtrlPanel(boolean show);

    void syncAccountMirrorPos(String mirrorPosition, boolean needMoveMirror);
}
