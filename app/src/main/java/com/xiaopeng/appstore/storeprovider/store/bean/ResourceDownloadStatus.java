package com.xiaopeng.appstore.storeprovider.store.bean;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes.dex */
public class ResourceDownloadStatus {
    public static final int RM_DOWNLOADING = 3;
    public static final int RM_DOWNLOAD_CANCEL = 4;
    public static final int RM_DOWNLOAD_FAILED = 7;
    public static final int RM_DOWNLOAD_FINISHED = 6;
    public static final int RM_DOWNLOAD_PAUSED = 5;
    public static final int RM_DOWNLOAD_WAITING = 2;
    public static final int RM_INSTALL_COMPLETE = 8;
    public static final int RM_INSTALL_FAILED = 9;
    public static final int RM_NOT_DOWNLOAD = 1;

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes.dex */
    public @interface Status {
    }
}
