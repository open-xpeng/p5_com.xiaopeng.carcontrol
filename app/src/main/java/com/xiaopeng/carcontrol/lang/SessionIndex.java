package com.xiaopeng.carcontrol.lang;

/* loaded from: classes2.dex */
public class SessionIndex {
    private int mSession;

    public synchronized int increase() {
        int i;
        i = this.mSession + 1;
        this.mSession = i;
        return i;
    }

    public synchronized int current() {
        return this.mSession;
    }

    public synchronized boolean check(int session) {
        return session == this.mSession;
    }

    public static final SessionIndex newSession() {
        return new SessionIndex();
    }
}
