package com.xiaopeng.speech.common.util;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class SimpleCallbackList<E> {
    private List<E> mList = new ArrayList();

    public void addCallback(E e) {
        synchronized (this.mList) {
            if (e != null) {
                if (!this.mList.contains(e)) {
                    this.mList.add(e);
                }
            }
        }
    }

    public void removeCallback(E e) {
        if (e != null) {
            synchronized (this.mList) {
                this.mList.remove(e);
            }
        }
    }

    public E[] collectCallbacks() {
        E[] eArr;
        synchronized (this.mList) {
            eArr = this.mList.size() > 0 ? (E[]) this.mList.toArray() : null;
        }
        return eArr;
    }

    public int size() {
        int size;
        synchronized (this.mList) {
            size = this.mList.size();
        }
        return size;
    }
}
