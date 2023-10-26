package com.xiaopeng.speech.protocol.node.tts;

import com.xiaopeng.speech.annotation.ICommandProcessor;
import com.xiaopeng.speech.protocol.event.TtsEvent;

/* loaded from: classes2.dex */
public class TtsNode_Processor implements ICommandProcessor {
    private TtsNode mTarget;

    public TtsNode_Processor(TtsNode ttsNode) {
        this.mTarget = ttsNode;
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public void performCommand(String str, String str2) {
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case -1878648165:
                if (str.equals(TtsEvent.SPEECH_TTS_START)) {
                    c = 0;
                    break;
                }
                break;
            case -1163978412:
                if (str.equals(TtsEvent.SPEECH_TTS_END)) {
                    c = 1;
                    break;
                }
                break;
            case -201524934:
                if (str.equals(TtsEvent.TTS_TIMBRE_SETTING)) {
                    c = 2;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                this.mTarget.onTtsStart(str, str2);
                return;
            case 1:
                this.mTarget.onTtsEnd(str, str2);
                return;
            case 2:
                this.mTarget.onTtsTimbreSetting(str, str2);
                return;
            default:
                return;
        }
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public String[] getSubscribeEvents() {
        return new String[]{TtsEvent.SPEECH_TTS_START, TtsEvent.SPEECH_TTS_END, TtsEvent.TTS_TIMBRE_SETTING};
    }
}
