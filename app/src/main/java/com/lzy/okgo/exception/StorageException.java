package com.lzy.okgo.exception;

/* loaded from: classes.dex */
public class StorageException extends Exception {
    private static final long serialVersionUID = 178946465;

    public StorageException() {
    }

    public StorageException(String str) {
        super(str);
    }

    public StorageException(String str, Throwable th) {
        super(str, th);
    }

    public StorageException(Throwable th) {
        super(th);
    }

    public static StorageException NOT_AVAILABLE() {
        return new StorageException("SDCard isn't available, please check SD card and permission: WRITE_EXTERNAL_STORAGE, and you must pay attention to Android6.0 RunTime Permissions!");
    }
}
