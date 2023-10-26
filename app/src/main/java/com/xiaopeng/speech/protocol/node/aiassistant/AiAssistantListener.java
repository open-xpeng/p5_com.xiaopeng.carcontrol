package com.xiaopeng.speech.protocol.node.aiassistant;

import com.xiaopeng.speech.INodeListener;

/* loaded from: classes2.dex */
public interface AiAssistantListener extends INodeListener {
    void onMessageClose();

    void onMessageOpen();

    void onOpenVideo(String str, String str2, String str3);

    void onPlayVideo();

    void onPlayVideoTtsend();

    void onXiaoPChangeClothe(int i);

    void onXiaoPDance(int i);
}
