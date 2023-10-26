package com.xiaopeng.speech.jarvisproto;

/* loaded from: classes2.dex */
public class AvatarExpressionEvent extends JarvisProto {
    public static final String EVENT = "jarvis.avatar.expression";
    public String data;

    @Override // com.xiaopeng.speech.jarvisproto.JarvisProto
    public String getEvent() {
        return EVENT;
    }

    @Override // com.xiaopeng.speech.jarvisproto.JarvisProto
    public String getJsonData() {
        return this.data;
    }
}
