package com.xiaopeng.appstore.storeprovider.store.bean;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes.dex */
public class ResourceSource {
    public static final int LOCAL = 1;
    public static final int REMOTE = 2;

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes.dex */
    public @interface Type {
    }
}
