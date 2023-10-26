package com.xiaopeng.speech.protocol.node.record;

import com.xiaopeng.speech.INodeListener;
import com.xiaopeng.speech.protocol.node.record.bean.AsrResult;
import com.xiaopeng.speech.protocol.node.record.bean.RecordErrReason;
import com.xiaopeng.speech.protocol.node.record.bean.Volume;

/* loaded from: classes2.dex */
public interface RecordListener extends INodeListener {
    void onAsrResult(AsrResult asrResult);

    void onRecordBegin();

    void onRecordEnd(boolean z);

    void onRecordError(RecordErrReason recordErrReason);

    void onRecordMaxLength();

    void onSpeechBegin();

    void onSpeechEnd();

    void onSpeechVolume(Volume volume);
}
