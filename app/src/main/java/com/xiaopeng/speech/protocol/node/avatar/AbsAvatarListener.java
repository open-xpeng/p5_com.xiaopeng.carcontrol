package com.xiaopeng.speech.protocol.node.avatar;

import com.xiaopeng.speech.jarvisproto.DMListening;
import com.xiaopeng.speech.jarvisproto.DMRecognized;

/* loaded from: classes2.dex */
public abstract class AbsAvatarListener implements AvatarListener {
    @Override // com.xiaopeng.speech.protocol.node.avatar.AvatarListener
    public void onAvatarExpression(String str) {
    }

    @Override // com.xiaopeng.speech.protocol.node.avatar.AvatarListener
    public void onAvatarWakerupDisable(String str) {
    }

    @Override // com.xiaopeng.speech.protocol.node.avatar.AvatarListener
    public void onAvatarWakerupEnable(String str) {
    }

    @Override // com.xiaopeng.speech.protocol.node.avatar.AvatarListener
    public void onListening(DMListening dMListening) {
    }

    @Override // com.xiaopeng.speech.protocol.node.avatar.AvatarListener
    public void onSilence(DMRecognized dMRecognized) {
    }

    @Override // com.xiaopeng.speech.protocol.node.avatar.AvatarListener
    public void onSpeaking() {
    }

    @Override // com.xiaopeng.speech.protocol.node.avatar.AvatarListener
    public void onStandby() {
    }

    @Override // com.xiaopeng.speech.protocol.node.avatar.AvatarListener
    public void onUnderstanding() {
    }

    @Override // com.xiaopeng.speech.protocol.node.avatar.AvatarListener
    public void onWidgetCard(String str) {
    }

    @Override // com.xiaopeng.speech.protocol.node.avatar.AvatarListener
    public void onWidgetCustom(String str) {
    }

    @Override // com.xiaopeng.speech.protocol.node.avatar.AvatarListener
    public void onWidgetList(String str) {
    }

    @Override // com.xiaopeng.speech.protocol.node.avatar.AvatarListener
    public void onWidgetMedia(String str) {
    }

    @Override // com.xiaopeng.speech.protocol.node.avatar.AvatarListener
    public void onWidgetSearch(String str) {
    }

    @Override // com.xiaopeng.speech.protocol.node.avatar.AvatarListener
    public void onWidgetText(String str) {
    }
}
