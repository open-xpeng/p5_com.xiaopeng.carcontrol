package com.xiaopeng.lib.framework.netchannelmodule.remotestorage.exception;

import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.remotestorage.StorageException;

/* loaded from: classes2.dex */
public class StorageExceptionImpl extends StorageException {
    private static final long serialVersionUID = 100;
    private int mReasonCode;

    public StorageExceptionImpl(int i) {
        super("");
        this.mReasonCode = i;
    }

    public StorageExceptionImpl(int i, String str) {
        super(str);
        this.mReasonCode = i;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.remotestorage.StorageException
    public int getReasonCode() {
        return this.mReasonCode;
    }
}
