package com.xiaopeng.speech.protocol.node.social;

import com.xiaopeng.speech.INodeListener;

/* loaded from: classes2.dex */
public interface SocialListener extends INodeListener {
    void onBackButtonClick();

    void onSocialCancel(String str);

    void onSocialConfirm(String str);

    void onSocialCreateTopic();

    void onSocialGrabMic();

    void onSocialGrabMicCancel();

    void onSocialMotorcadeClose();

    void onSocialMotorcadeOpen();

    void onSocialQuitChat();

    void onSocialReplyTopic();

    void onVoiceButtonClick();
}
