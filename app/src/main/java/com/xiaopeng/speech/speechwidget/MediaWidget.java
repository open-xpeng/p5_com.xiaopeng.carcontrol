package com.xiaopeng.speech.speechwidget;

/* loaded from: classes2.dex */
public class MediaWidget extends ListWidget {
    public MediaWidget() {
        super("media");
    }

    @Override // com.xiaopeng.speech.speechwidget.ListWidget, com.xiaopeng.speech.speechwidget.SpeechWidget
    public MediaWidget fromJson(String str) {
        return (MediaWidget) super.fromJson(str);
    }
}
