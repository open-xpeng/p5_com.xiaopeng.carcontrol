package com.xiaopeng.speech.protocol.node.dialog;

import com.xiaopeng.speech.jarvisproto.DMWait;
import com.xiaopeng.speech.protocol.node.dialog.bean.DialogEndReason;
import com.xiaopeng.speech.protocol.node.dialog.bean.WakeupReason;

/* loaded from: classes2.dex */
public abstract class AbsDialogListener implements DialogListener {
    @Override // com.xiaopeng.speech.protocol.node.dialog.DialogListener
    public void onDialogContinue() {
    }

    @Override // com.xiaopeng.speech.protocol.node.dialog.DialogListener
    public void onDialogEnd(DialogEndReason dialogEndReason) {
    }

    @Override // com.xiaopeng.speech.protocol.node.dialog.DialogListener
    public void onDialogError() {
    }

    @Override // com.xiaopeng.speech.protocol.node.dialog.DialogListener
    public void onDialogStart(WakeupReason wakeupReason) {
    }

    @Override // com.xiaopeng.speech.protocol.node.dialog.DialogListener
    public void onDialogStatusChanged(String str) {
    }

    @Override // com.xiaopeng.speech.protocol.node.dialog.DialogListener
    public void onDialogWait(DMWait dMWait) {
    }

    public void onResourceUpdateded() {
    }

    public void onUploadResult() {
    }

    @Override // com.xiaopeng.speech.protocol.node.dialog.DialogListener
    public void onVadBegin() {
    }

    @Override // com.xiaopeng.speech.protocol.node.dialog.DialogListener
    public void onVadEnd() {
    }

    @Override // com.xiaopeng.speech.protocol.node.dialog.DialogListener
    public void onWakeupResult() {
    }
}
