package com.xiaopeng.speech.jarvisproto;

/* loaded from: classes2.dex */
public class RecordBegin extends JarvisProto {
    public static final String EVENT = "jarvis.record.begin";

    @Override // com.xiaopeng.speech.jarvisproto.JarvisProto
    public String getEvent() {
        return EVENT;
    }

    @Override // com.xiaopeng.speech.jarvisproto.JarvisProto
    public String getJsonData() {
        return "";
    }
}
