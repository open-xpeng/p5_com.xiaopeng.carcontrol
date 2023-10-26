package com.xiaopeng.libconfig.ipc.event;

/* loaded from: classes2.dex */
public class NavigationEvent {
    private int index;
    private boolean state;

    public NavigationEvent setState(boolean z) {
        this.state = z;
        return this;
    }

    public NavigationEvent setIndex(int i) {
        this.index = i;
        return this;
    }

    public boolean getState() {
        return this.state;
    }

    public int getIndex() {
        return this.index;
    }

    public String toString() {
        return "{\"state\":" + this.state + ",\"index\":" + this.index + "}";
    }
}
