package com.xiaopeng.speech.actorapi;

import android.os.Parcel;
import com.xiaopeng.speech.speechwidget.SpeechWidget;
import com.xiaopeng.speech.speechwidget.SupportWidget;

/* loaded from: classes2.dex */
public class SupportActor extends ResultActor {
    public static final String NAME = "SupportActor";
    public static final String TTS_DEFAULT_NOT_SUPPORT = "暂不支持此功能";
    public static final String TTS_DEFAULT_SUPPORT = "好的";

    public SupportActor(String str) {
        super(str);
        this.mName = NAME;
    }

    public SupportActor(String str, Parcel parcel) {
        super(str, parcel);
    }

    public SupportActor setResult(SupportWidget supportWidget) {
        return (SupportActor) super.setResult((SpeechWidget) supportWidget);
    }

    @Override // com.xiaopeng.speech.actorapi.ResultActor
    public SupportActor setResult(String str) {
        return (SupportActor) super.setResult(str);
    }

    public SupportWidget getWidget() {
        return new SupportWidget().fromJson(this.mResultData);
    }
}
