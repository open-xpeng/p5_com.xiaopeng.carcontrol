package com.xiaopeng.carcontrol.download;

import android.util.ArrayMap;

/* loaded from: classes2.dex */
public class RequestInfo {
    public ArrayMap<String, String> header;
    public long id;
    public String localPath;
    public String name;
    public int size;
    public String uri;

    public String toString() {
        return "RequestInfo{id=" + this.id + ", name='" + this.name + "', uri='" + this.uri + "', size=" + this.size + ", localPath='" + this.localPath + "', header=" + this.header + '}';
    }
}
