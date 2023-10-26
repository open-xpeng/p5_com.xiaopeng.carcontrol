package com.xiaopeng.speech.jarvisproto;

/* loaded from: classes2.dex */
public class DMWake extends JarvisProto {
    public static final String EVENT = "jarvis.dm.wake";
    public String reason;
    public String sessionId;
    public WakeupResult wakeupResult;

    @Override // com.xiaopeng.speech.jarvisproto.JarvisProto
    public String getEvent() {
        return EVENT;
    }

    @Override // com.xiaopeng.speech.jarvisproto.JarvisProto
    public String getJsonData() {
        return null;
    }
}
