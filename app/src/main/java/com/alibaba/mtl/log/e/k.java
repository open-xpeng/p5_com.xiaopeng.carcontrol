package com.alibaba.mtl.log.e;

import android.content.Context;
import android.util.Log;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/* compiled from: MutiProcessLock.java */
/* loaded from: classes.dex */
public class k {
    static File a;

    /* renamed from: a  reason: collision with other field name */
    static FileChannel f61a;

    /* renamed from: a  reason: collision with other field name */
    static FileLock f62a;

    public static synchronized boolean c(Context context) {
        FileLock fileLock;
        synchronized (k.class) {
            if (a == null) {
                a = new File(context.getFilesDir() + File.separator + "ap.Lock");
            }
            boolean exists = a.exists();
            if (!exists) {
                try {
                    exists = a.createNewFile();
                } catch (IOException unused) {
                }
            }
            if (exists) {
                if (f61a == null) {
                    try {
                        f61a = new RandomAccessFile(a, "rw").getChannel();
                    } catch (Exception unused2) {
                        return false;
                    }
                }
                try {
                    fileLock = f61a.tryLock();
                    if (fileLock != null) {
                        f62a = fileLock;
                        return true;
                    }
                } catch (Throwable unused3) {
                    fileLock = null;
                }
                Log.d("TAG", "mLock:" + fileLock);
                return false;
            }
            return true;
        }
    }

    public static synchronized void release() {
        synchronized (k.class) {
            FileLock fileLock = f62a;
            if (fileLock != null) {
                try {
                    fileLock.release();
                } catch (IOException unused) {
                }
                f62a = null;
            }
            FileChannel fileChannel = f61a;
            if (fileChannel != null) {
                try {
                    fileChannel.close();
                } catch (Exception unused2) {
                }
                f61a = null;
            }
        }
    }
}
