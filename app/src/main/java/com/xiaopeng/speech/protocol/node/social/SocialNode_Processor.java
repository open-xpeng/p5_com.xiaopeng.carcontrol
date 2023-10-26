package com.xiaopeng.speech.protocol.node.social;

import com.xiaopeng.speech.annotation.ICommandProcessor;
import com.xiaopeng.speech.jarvisproto.BackButtonClick;
import com.xiaopeng.speech.jarvisproto.VoiceButtonClick;
import com.xiaopeng.speech.protocol.event.SocialEvent;

/* loaded from: classes2.dex */
public class SocialNode_Processor implements ICommandProcessor {
    private SocialNode mTarget;

    public SocialNode_Processor(SocialNode socialNode) {
        this.mTarget = socialNode;
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public void performCommand(String str, String str2) {
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case -1489293258:
                if (str.equals(SocialEvent.SOCIAL_MOTORCADE_OPEN)) {
                    c = 0;
                    break;
                }
                break;
            case -1174519421:
                if (str.equals(VoiceButtonClick.EVENT)) {
                    c = 1;
                    break;
                }
                break;
            case -1000693031:
                if (str.equals(SocialEvent.SOCIAL_REPLY_TOPIC)) {
                    c = 2;
                    break;
                }
                break;
            case -967605051:
                if (str.equals(SocialEvent.SOCIAL_QUIT_CHAT)) {
                    c = 3;
                    break;
                }
                break;
            case -470207025:
                if (str.equals(SocialEvent.SOCIAL_CREATE_TOPIC)) {
                    c = 4;
                    break;
                }
                break;
            case -411700335:
                if (str.equals(SocialEvent.SOCIAL_GRAB_MIC_CANCEL)) {
                    c = 5;
                    break;
                }
                break;
            case 1065357708:
                if (str.equals(SocialEvent.SOCIAL_MOTORCADE_CLOSE)) {
                    c = 6;
                    break;
                }
                break;
            case 1292442478:
                if (str.equals(SocialEvent.SOCIAL_CONFIRM)) {
                    c = 7;
                    break;
                }
                break;
            case 1454210858:
                if (str.equals(BackButtonClick.EVENT)) {
                    c = '\b';
                    break;
                }
                break;
            case 1829874700:
                if (str.equals(SocialEvent.SOCIAL_CANCEL)) {
                    c = '\t';
                    break;
                }
                break;
            case 2077193399:
                if (str.equals(SocialEvent.SOCIAL_GRAB_MIC)) {
                    c = '\n';
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                this.mTarget.onSocialMotorcadeOpen(str, str2);
                return;
            case 1:
                this.mTarget.onVoiceButtonClick(str, str2);
                return;
            case 2:
                this.mTarget.onSocialReplyTopic(str, str2);
                return;
            case 3:
                this.mTarget.onSocialQuitChat(str, str2);
                return;
            case 4:
                this.mTarget.onSocialCreateTopic(str, str2);
                return;
            case 5:
                this.mTarget.onSocialGrabMicCancel(str, str2);
                return;
            case 6:
                this.mTarget.onSocialMotorcadeClose(str, str2);
                return;
            case 7:
                this.mTarget.onSocialConfirm(str, str2);
                return;
            case '\b':
                this.mTarget.onBackButtonClick(str, str2);
                return;
            case '\t':
                this.mTarget.onSocialCancel(str, str2);
                return;
            case '\n':
                this.mTarget.onSocialGrabMic(str, str2);
                return;
            default:
                return;
        }
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public String[] getSubscribeEvents() {
        return new String[]{SocialEvent.SOCIAL_MOTORCADE_OPEN, SocialEvent.SOCIAL_MOTORCADE_CLOSE, SocialEvent.SOCIAL_GRAB_MIC, SocialEvent.SOCIAL_GRAB_MIC_CANCEL, SocialEvent.SOCIAL_CREATE_TOPIC, SocialEvent.SOCIAL_REPLY_TOPIC, SocialEvent.SOCIAL_QUIT_CHAT, SocialEvent.SOCIAL_CONFIRM, SocialEvent.SOCIAL_CANCEL, VoiceButtonClick.EVENT, BackButtonClick.EVENT};
    }
}
