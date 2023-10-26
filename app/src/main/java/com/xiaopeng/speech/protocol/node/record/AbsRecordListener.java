package com.xiaopeng.speech.protocol.node.record;

import com.xiaopeng.speech.protocol.node.record.bean.AsrResult;
import com.xiaopeng.speech.protocol.node.record.bean.RecordErrReason;
import com.xiaopeng.speech.protocol.node.record.bean.Volume;

/* loaded from: classes2.dex */
public abstract class AbsRecordListener implements RecordListener {
    @Override // com.xiaopeng.speech.protocol.node.record.RecordListener
    public void onAsrResult(AsrResult asrResult) {
    }

    @Override // com.xiaopeng.speech.protocol.node.record.RecordListener
    public void onRecordBegin() {
    }

    @Override // com.xiaopeng.speech.protocol.node.record.RecordListener
    public void onRecordEnd(boolean z) {
    }

    @Override // com.xiaopeng.speech.protocol.node.record.RecordListener
    public void onRecordError(RecordErrReason recordErrReason) {
    }

    @Override // com.xiaopeng.speech.protocol.node.record.RecordListener
    public void onRecordMaxLength() {
    }

    @Override // com.xiaopeng.speech.protocol.node.record.RecordListener
    public void onSpeechBegin() {
    }

    @Override // com.xiaopeng.speech.protocol.node.record.RecordListener
    public void onSpeechEnd() {
    }

    @Override // com.xiaopeng.speech.protocol.node.record.RecordListener
    public void onSpeechVolume(Volume volume) {
    }
}
