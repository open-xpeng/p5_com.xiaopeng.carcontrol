package com.xiaopeng.speech.protocol.node.combo;

import com.xiaopeng.speech.annotation.ICommandProcessor;
import com.xiaopeng.speech.protocol.event.ComboEvent;

/* loaded from: classes2.dex */
public class ComboNode_Processor implements ICommandProcessor {
    private ComboNode mTarget;

    public ComboNode_Processor(ComboNode comboNode) {
        this.mTarget = comboNode;
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public void performCommand(String str, String str2) {
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case -2090731182:
                if (str.equals(ComboEvent.EXIT_USER_MODE)) {
                    c = 0;
                    break;
                }
                break;
            case -2050544765:
                if (str.equals(ComboEvent.FAST_CLOSE_MODE_INVISIBLE)) {
                    c = 1;
                    break;
                }
                break;
            case -1861214251:
                if (str.equals(ComboEvent.DATA_MODE_WAIT_TTS)) {
                    c = 2;
                    break;
                }
                break;
            case -1439089727:
                if (str.equals(ComboEvent.MODE_FRIDGE)) {
                    c = 3;
                    break;
                }
                break;
            case -1170878131:
                if (str.equals(ComboEvent.MODE_BIO_OFF)) {
                    c = 4;
                    break;
                }
                break;
            case -814414287:
                if (str.equals(ComboEvent.MODE_WAIT)) {
                    c = 5;
                    break;
                }
                break;
            case -780117185:
                if (str.equals(ComboEvent.MODE_VENTILATE_OFF)) {
                    c = 6;
                    break;
                }
                break;
            case -719028020:
                if (str.equals(ComboEvent.MODE_BIO)) {
                    c = 7;
                    break;
                }
                break;
            case -597830030:
                if (str.equals(ComboEvent.MODE_INVISIBLE_OFF)) {
                    c = '\b';
                    break;
                }
                break;
            case -260120463:
                if (str.equals(ComboEvent.MODE_INVISIBLE)) {
                    c = '\t';
                    break;
                }
                break;
            case 174005252:
                if (str.equals(ComboEvent.DATA_MODE_VENTILATE_TTS)) {
                    c = '\n';
                    break;
                }
                break;
            case 356292407:
                if (str.equals(ComboEvent.DATA_MODE_INVISIBLE_TTS)) {
                    c = 11;
                    break;
                }
                break;
            case 802818498:
                if (str.equals(ComboEvent.MODE_FRIDGE_OFF)) {
                    c = '\f';
                    break;
                }
                break;
            case 1008063422:
                if (str.equals(ComboEvent.MODE_VENTILATE)) {
                    c = '\r';
                    break;
                }
                break;
            case 1059045797:
                if (str.equals(ComboEvent.DATA_MODE_FRIDGE_TTS)) {
                    c = 14;
                    break;
                }
                break;
            case 1114869502:
                if (str.equals(ComboEvent.DATA_MODE_MEDITATION_TTS)) {
                    c = 15;
                    break;
                }
                break;
            case 1618652110:
                if (str.equals(ComboEvent.ENTER_USER_MODE)) {
                    c = 16;
                    break;
                }
                break;
            case 1682644018:
                if (str.equals(ComboEvent.MODE_WAIT_OFF)) {
                    c = 17;
                    break;
                }
                break;
            case 2039944914:
                if (str.equals(ComboEvent.DATA_MODE_BIO_TTS)) {
                    c = 18;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                this.mTarget.exitUserModel(str, str2);
                return;
            case 1:
                this.mTarget.onFastCloseModeInvisible(str, str2);
                return;
            case 2:
                this.mTarget.onDataModeWaitTts(str, str2);
                return;
            case 3:
                this.mTarget.onModeFridge(str, str2);
                return;
            case 4:
                this.mTarget.onModeBioOff(str, str2);
                return;
            case 5:
                this.mTarget.onModeWait(str, str2);
                return;
            case 6:
                this.mTarget.onModeVentilateOff(str, str2);
                return;
            case 7:
                this.mTarget.onModeBio(str, str2);
                return;
            case '\b':
                this.mTarget.onModeInvisibleOff(str, str2);
                return;
            case '\t':
                this.mTarget.onModeInvisible(str, str2);
                return;
            case '\n':
                this.mTarget.onDataModeVentilateTts(str, str2);
                return;
            case 11:
                this.mTarget.onDataModeInvisibleTts(str, str2);
                return;
            case '\f':
                this.mTarget.onModeFridgeOff(str, str2);
                return;
            case '\r':
                this.mTarget.onModeVentilate(str, str2);
                return;
            case 14:
                this.mTarget.onDataModeFridgeTts(str, str2);
                return;
            case 15:
                this.mTarget.onDataModeMeditationTts(str, str2);
                return;
            case 16:
                this.mTarget.enterUserMode(str, str2);
                return;
            case 17:
                this.mTarget.onModeWaitOff(str, str2);
                return;
            case 18:
                this.mTarget.onDataModeBioTts(str, str2);
                return;
            default:
                return;
        }
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public String[] getSubscribeEvents() {
        return new String[]{ComboEvent.MODE_BIO, ComboEvent.MODE_VENTILATE, ComboEvent.MODE_INVISIBLE, ComboEvent.MODE_INVISIBLE_OFF, ComboEvent.MODE_FRIDGE, ComboEvent.DATA_MODE_INVISIBLE_TTS, ComboEvent.DATA_MODE_MEDITATION_TTS, ComboEvent.FAST_CLOSE_MODE_INVISIBLE, ComboEvent.DATA_MODE_BIO_TTS, ComboEvent.DATA_MODE_VENTILATE_TTS, ComboEvent.DATA_MODE_FRIDGE_TTS, ComboEvent.MODE_BIO_OFF, ComboEvent.MODE_VENTILATE_OFF, ComboEvent.MODE_FRIDGE_OFF, ComboEvent.DATA_MODE_WAIT_TTS, ComboEvent.MODE_WAIT, ComboEvent.MODE_WAIT_OFF, ComboEvent.ENTER_USER_MODE, ComboEvent.EXIT_USER_MODE};
    }
}
