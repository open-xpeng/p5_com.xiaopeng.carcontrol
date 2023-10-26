package com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.remotestorage;

/* loaded from: classes2.dex */
public interface Callback {
    void onFailure(String str, String str2, StorageException storageException);

    void onStart(String str, String str2);

    void onSuccess(String str, String str2);
}
