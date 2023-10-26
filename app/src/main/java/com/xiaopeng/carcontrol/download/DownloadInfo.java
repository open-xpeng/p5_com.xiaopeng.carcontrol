package com.xiaopeng.carcontrol.download;

/* loaded from: classes2.dex */
public class DownloadInfo {
    public long downloadedSize;
    public int id;
    public int index;
    public String name;
    public int percent;
    public int status;
    public long totalSize;

    public String toString() {
        return "DownloadInfo{id=" + this.id + ", index=" + this.index + ", name='" + this.name + "', totalSize=" + this.totalSize + ", downloadedSize=" + this.downloadedSize + ", percent=" + this.percent + ", status=" + this.status + '}';
    }
}
