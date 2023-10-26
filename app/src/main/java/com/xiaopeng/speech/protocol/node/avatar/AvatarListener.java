package com.xiaopeng.speech.protocol.node.avatar;

import com.xiaopeng.speech.INodeListener;
import com.xiaopeng.speech.jarvisproto.DMListening;
import com.xiaopeng.speech.jarvisproto.DMRecognized;

/* loaded from: classes2.dex */
public interface AvatarListener extends INodeListener {
    void onAvatarExpression(String str);

    void onAvatarWakerupDisable(String str);

    void onAvatarWakerupEnable(String str);

    void onListening(DMListening dMListening);

    void onSilence(DMRecognized dMRecognized);

    void onSpeaking();

    void onStandby();

    void onUnderstanding();

    void onWidgetCard(String str);

    void onWidgetCustom(String str);

    void onWidgetList(String str);

    void onWidgetMedia(String str);

    void onWidgetSearch(String str);

    void onWidgetText(String str);
}
