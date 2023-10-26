package com.xiaopeng.speech.protocol.node.carcontrol;

import com.xiaopeng.speech.annotation.ICommandProcessor;
import com.xiaopeng.speech.protocol.event.MeditationMusicEvent;

/* loaded from: classes2.dex */
public class MeditationMusicNode_Processor implements ICommandProcessor {
    private MeditationMusicNode mTarget;

    public MeditationMusicNode_Processor(MeditationMusicNode meditationMusicNode) {
        this.mTarget = meditationMusicNode;
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public void performCommand(String str, String str2) {
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case -2031117940:
                if (str.equals(MeditationMusicEvent.MEDITATION_MUSIC_CONTROL_PAUSE)) {
                    c = 0;
                    break;
                }
                break;
            case 1520748375:
                if (str.equals(MeditationMusicEvent.MEDITATION_MUSIC_CONTROL_RESUME)) {
                    c = 1;
                    break;
                }
                break;
            case 1874087069:
                if (str.equals(MeditationMusicEvent.MEDITATION_MUSIC_CONTROL_NEXT)) {
                    c = 2;
                    break;
                }
                break;
            case 1874158557:
                if (str.equals(MeditationMusicEvent.MEDITATION_MUSIC_CONTROL_PREV)) {
                    c = 3;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                this.mTarget.onPause(str, str2);
                return;
            case 1:
                this.mTarget.onResume(str, str2);
                return;
            case 2:
                this.mTarget.onNext(str, str2);
                return;
            case 3:
                this.mTarget.onPrev(str, str2);
                return;
            default:
                return;
        }
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public String[] getSubscribeEvents() {
        return new String[]{MeditationMusicEvent.MEDITATION_MUSIC_CONTROL_PREV, MeditationMusicEvent.MEDITATION_MUSIC_CONTROL_NEXT, MeditationMusicEvent.MEDITATION_MUSIC_CONTROL_PAUSE, MeditationMusicEvent.MEDITATION_MUSIC_CONTROL_RESUME};
    }
}
