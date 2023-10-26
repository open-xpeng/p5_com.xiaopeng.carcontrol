package com.xiaopeng.speech.annotation;

/* loaded from: classes2.dex */
public interface ICommandProcessor {
    String[] getSubscribeEvents();

    void performCommand(String str, String str2);
}
