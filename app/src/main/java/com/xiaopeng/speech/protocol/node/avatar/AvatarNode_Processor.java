package com.xiaopeng.speech.protocol.node.avatar;

import com.xiaopeng.speech.annotation.ICommandProcessor;
import com.xiaopeng.speech.jarvisproto.AvatarExpressionEvent;
import com.xiaopeng.speech.jarvisproto.AvatarWakeupDisable;
import com.xiaopeng.speech.jarvisproto.AvatarWakeupEnable;
import com.xiaopeng.speech.jarvisproto.DMListening;
import com.xiaopeng.speech.jarvisproto.DMRecognized;
import com.xiaopeng.speech.jarvisproto.DMSpeaking;
import com.xiaopeng.speech.jarvisproto.DMUnderstanding;
import com.xiaopeng.speech.protocol.event.ContextEvent;

/* loaded from: classes2.dex */
public class AvatarNode_Processor implements ICommandProcessor {
    private AvatarNode mTarget;

    public AvatarNode_Processor(AvatarNode avatarNode) {
        this.mTarget = avatarNode;
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public void performCommand(String str, String str2) {
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case -1543075995:
                if (str.equals(AvatarWakeupDisable.EVENT)) {
                    c = 0;
                    break;
                }
                break;
            case -1187871553:
                if (str.equals(DMListening.EVENT)) {
                    c = 1;
                    break;
                }
                break;
            case -671951780:
                if (str.equals(ContextEvent.WIDGET_CUSTOM)) {
                    c = 2;
                    break;
                }
                break;
            case -229200237:
                if (str.equals(ContextEvent.WIDGET_SEARCH)) {
                    c = 3;
                    break;
                }
                break;
            case -206519100:
                if (str.equals(AvatarExpressionEvent.EVENT)) {
                    c = 4;
                    break;
                }
                break;
            case 260029798:
                if (str.equals(AvatarWakeupEnable.EVENT)) {
                    c = 5;
                    break;
                }
                break;
            case 402709913:
                if (str.equals(ContextEvent.WIDGET_MEDIA)) {
                    c = 6;
                    break;
                }
                break;
            case 843973307:
                if (str.equals(ContextEvent.WIDGET_CARD)) {
                    c = 7;
                    break;
                }
                break;
            case 844249161:
                if (str.equals(ContextEvent.WIDGET_LIST)) {
                    c = '\b';
                    break;
                }
                break;
            case 844483800:
                if (str.equals(ContextEvent.WIDGET_TEXT)) {
                    c = '\t';
                    break;
                }
                break;
            case 1330018892:
                if (str.equals(DMSpeaking.EVENT)) {
                    c = '\n';
                    break;
                }
                break;
            case 1386014226:
                if (str.equals(DMRecognized.EVENT)) {
                    c = 11;
                    break;
                }
                break;
            case 1566189864:
                if (str.equals(DMUnderstanding.EVENT)) {
                    c = '\f';
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                this.mTarget.onAvatarWakerupDisable(str, str2);
                return;
            case 1:
                this.mTarget.onListening(str, str2);
                return;
            case 2:
                this.mTarget.onWidgetCustom(str, str2);
                return;
            case 3:
                this.mTarget.onWidgetSearch(str, str2);
                return;
            case 4:
                this.mTarget.onAvatarExpression(str, str2);
                return;
            case 5:
                this.mTarget.onAvatarWakerupEnable(str, str2);
                return;
            case 6:
                this.mTarget.onWidgetMedia(str, str2);
                return;
            case 7:
                this.mTarget.onWidgetCard(str, str2);
                return;
            case '\b':
                this.mTarget.onWidgetList(str, str2);
                return;
            case '\t':
                this.mTarget.onWidgetText(str, str2);
                return;
            case '\n':
                this.mTarget.onSpeaking(str, str2);
                return;
            case 11:
                this.mTarget.onSilence(str, str2);
                return;
            case '\f':
                this.mTarget.onUnderstanding(str, str2);
                return;
            default:
                return;
        }
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public String[] getSubscribeEvents() {
        return new String[]{DMRecognized.EVENT, DMListening.EVENT, DMSpeaking.EVENT, DMUnderstanding.EVENT, AvatarExpressionEvent.EVENT, ContextEvent.WIDGET_CUSTOM, ContextEvent.WIDGET_TEXT, ContextEvent.WIDGET_LIST, ContextEvent.WIDGET_MEDIA, ContextEvent.WIDGET_CARD, ContextEvent.WIDGET_SEARCH, AvatarWakeupEnable.EVENT, AvatarWakeupDisable.EVENT};
    }
}
