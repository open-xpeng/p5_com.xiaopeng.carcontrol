package com.xiaopeng.speech;

import com.xiaopeng.speech.actor.Actor;

/* loaded from: classes2.dex */
public interface ITransporter {

    /* loaded from: classes2.dex */
    public interface Receiver {
        void onCall(Actor actor);
    }

    /* loaded from: classes2.dex */
    public interface Sender {
        void send(Actor actor);
    }
}
