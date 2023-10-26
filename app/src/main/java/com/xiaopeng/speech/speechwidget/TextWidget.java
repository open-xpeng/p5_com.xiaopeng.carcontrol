package com.xiaopeng.speech.speechwidget;

/* loaded from: classes2.dex */
public class TextWidget extends SpeechWidget {
    public TextWidget() {
        super("text");
    }

    public TextWidget setText(String str) {
        return (TextWidget) super.addContent("text", str);
    }

    public String getText() {
        return getContent("text");
    }
}
