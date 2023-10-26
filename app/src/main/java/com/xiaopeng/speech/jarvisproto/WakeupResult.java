package com.xiaopeng.speech.jarvisproto;

/* loaded from: classes2.dex */
public class WakeupResult extends WakeupResultBase {
    public static final String EVENT = "jarvis.wakeup.result";
    public static final String REASON_COMMAND = "command";
    public static final String REASON_INTERRUPT = "interrupt";
    public static final String REASON_MAJOR = "major";

    @Override // com.xiaopeng.speech.jarvisproto.JarvisProto
    public String getEvent() {
        return EVENT;
    }
}
