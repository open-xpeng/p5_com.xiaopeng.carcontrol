package com.xiaopeng.lib.framework.moduleinterface.syncmodule;

import java.util.List;

/* loaded from: classes2.dex */
public final class SyncRestoreEvent {
    public List<SyncData> list;
    public long uid;

    public SyncRestoreEvent(long j, List<SyncData> list) {
        this.uid = j;
        this.list = list;
    }

    public String toString() {
        StringBuilder append = new StringBuilder().append("SyncRestoreEvent { uid:").append(this.uid).append("; list size:");
        List<SyncData> list = this.list;
        return append.append(list == null ? 0 : list.size()).append("; }").toString();
    }
}
