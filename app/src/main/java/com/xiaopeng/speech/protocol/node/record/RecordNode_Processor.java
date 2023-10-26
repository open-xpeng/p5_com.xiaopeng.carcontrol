package com.xiaopeng.speech.protocol.node.record;

import com.xiaopeng.speech.annotation.ICommandProcessor;
import com.xiaopeng.speech.jarvisproto.AsrCloundResult;
import com.xiaopeng.speech.jarvisproto.RecordBegin;
import com.xiaopeng.speech.jarvisproto.RecordEnd;
import com.xiaopeng.speech.jarvisproto.RecordError;
import com.xiaopeng.speech.jarvisproto.RecordMaxLength;
import com.xiaopeng.speech.jarvisproto.SpeechBegin;
import com.xiaopeng.speech.jarvisproto.SpeechEnd;
import com.xiaopeng.speech.jarvisproto.SpeechVolume;

/* loaded from: classes2.dex */
public class RecordNode_Processor implements ICommandProcessor {
    private RecordNode mTarget;

    public RecordNode_Processor(RecordNode recordNode) {
        this.mTarget = recordNode;
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public void performCommand(String str, String str2) {
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case -2099844195:
                if (str.equals(SpeechVolume.EVENT)) {
                    c = 0;
                    break;
                }
                break;
            case -477025291:
                if (str.equals(RecordBegin.EVENT)) {
                    c = 1;
                    break;
                }
                break;
            case -473856684:
                if (str.equals(RecordError.EVENT)) {
                    c = 2;
                    break;
                }
                break;
            case 223082937:
                if (str.equals(AsrCloundResult.EVENT)) {
                    c = 3;
                    break;
                }
                break;
            case 744773574:
                if (str.equals(SpeechBegin.EVENT)) {
                    c = 4;
                    break;
                }
                break;
            case 747146040:
                if (str.equals(SpeechEnd.EVENT)) {
                    c = 5;
                    break;
                }
                break;
            case 871014183:
                if (str.equals(RecordEnd.EVENT)) {
                    c = 6;
                    break;
                }
                break;
            case 1711895190:
                if (str.equals(RecordMaxLength.EVENT)) {
                    c = 7;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                this.mTarget.onSpeechVolume(str, str2);
                return;
            case 1:
                this.mTarget.onRecordBegin(str, str2);
                return;
            case 2:
                this.mTarget.onRecordError(str, str2);
                return;
            case 3:
                this.mTarget.onAsrResult(str, str2);
                return;
            case 4:
                this.mTarget.onSpeechBegin(str, str2);
                return;
            case 5:
                this.mTarget.onSpeechEnd(str, str2);
                return;
            case 6:
                this.mTarget.onRecordEnd(str, str2);
                return;
            case 7:
                this.mTarget.onRecordMaxLength(str, str2);
                return;
            default:
                return;
        }
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public String[] getSubscribeEvents() {
        return new String[]{AsrCloundResult.EVENT, RecordBegin.EVENT, RecordEnd.EVENT, RecordError.EVENT, SpeechBegin.EVENT, SpeechEnd.EVENT, SpeechVolume.EVENT, RecordMaxLength.EVENT};
    }
}
